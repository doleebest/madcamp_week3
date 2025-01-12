package madcamp3.fridge.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoLoginService {

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.redirect.uri}")
    private String redirectUri;

    public String getKakaoAccessToken(String code) {
        try {
            String tokenUri = "https://kauth.kakao.com/oauth/token";

            // URL 인코딩 처리
            String encodedRedirectUri = URLEncoder.encode(redirectUri, StandardCharsets.UTF_8.toString());

            String requestBody = String.format("grant_type=authorization_code&client_id=%s&redirect_uri=%s&code=%s",
                    clientId, encodedRedirectUri, code);

            URL url = new URL(tokenUri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            conn.setDoOutput(true);

            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()))) {
                bw.write(requestBody);
                bw.flush();
            }

            // 응답 읽기
            StringBuilder result = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
            }

            JSONObject jsonToken = new JSONObject(result.toString());
            return jsonToken.getString("access_token");

        } catch (Exception e) {
            log.error("Failed to get access token", e);
            throw new RuntimeException("Failed to get access token");
        }
    }

    public JsonObject getKakaoUserInfo(String accessToken) {
        try {
            String apiUrl = "https://kapi.kakao.com/v2/user/me";
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            StringBuilder result = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
            }

            return JsonParser.parseString(result.toString()).getAsJsonObject();
        } catch (Exception e) {
            log.error("Failed to get user info", e);
            throw new RuntimeException("Failed to get user info");
        }
    }
}