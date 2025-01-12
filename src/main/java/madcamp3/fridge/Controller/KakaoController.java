package madcamp3.fridge.Controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import madcamp3.fridge.Service.KakaoLoginService;
import madcamp3.fridge.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@Slf4j
// @RequiredArgsConstructor
public class KakaoController {

    private final KakaoLoginService kakaoService;
    private final UserService userService;  // UserService 추가

    @Autowired
    public KakaoController(KakaoLoginService kakaoService, UserService userService) {
        this.kakaoService = kakaoService;
        this.userService = userService;
    }

    @Value("${kakao.client.id}")
    private String kakaoClientId;

    @Value("${kakao.redirect.uri}")
    private String kakaoRedirectUri;

    @Value("${kakao.client.secret}")
    private String kakaoClientSecret;

    // Authorization Code 요청
    @GetMapping("/auth/kakao/login")
    public String login() {
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize"
                + "?client_id=" + kakaoClientId
                + "&redirect_uri=" + kakaoRedirectUri
                + "&response_type=code";

        log.info("KakaoClientId: " + kakaoClientId);
        log.info("RedirectUri: " + kakaoRedirectUri);
        log.info("Full Auth URL: " + kakaoAuthUrl);

        return "redirect:" + kakaoAuthUrl;
    }

    // Callback: Access Token 요청
    @GetMapping("/auth/kakao/callback")
    public String callback(@RequestParam String code) throws IOException {
        try {
            // KakaoLoginService의 메서드 사용
            String accessToken = kakaoService.getKakaoAccessToken(code);
            JsonObject userInfo = kakaoService.getKakaoUserInfo(accessToken);
            userService.saveOrUpdateUser(userInfo);
            return "로그인 성공! Access Token: " + accessToken + "\nUser Info: " + userInfo;
        } catch (Exception e) {
            log.error("Error in callback: ", e);
            return "로그인 실패: " + e.getMessage();
        }
    }

    private String getAccessToken(String code) throws IOException {
        String tokenUrl = "https://kauth.kakao.com/oauth/token";
        URL url = new URL(tokenUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        connection.setDoOutput(true);

        String encodedRedirectUri = URLEncoder.encode(kakaoRedirectUri, StandardCharsets.UTF_8.toString());

        String body = String.format("grant_type=authorization_code&client_id=%s&redirect_uri=%s&code=%s&client_secret=%s",
                kakaoClientId,
                encodedRedirectUri,
                code,
                kakaoClientSecret);  // client_secret 추가

        log.info("Token request body: " + body);

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()))) {
            bw.write(body);
            bw.flush();
        }

        int responseCode = connection.getResponseCode();
        log.info("Token response code: " + responseCode);  // 디버깅용 로그

        if (responseCode == 401) {
            log.error("Authorization failed - check client_id and redirect_uri");
            throw new IOException("Authorization failed");
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        }

        log.info("Token response: " + response.toString());  // 디버깅용 로그

        JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
        return jsonObject.get("access_token").getAsString();
    }

    private JsonObject getUserInfo(String accessToken) throws IOException {
        String apiUrl = "https://kapi.kakao.com/v2/user/me";
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + accessToken);

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        return JsonParser.parseString(sb.toString()).getAsJsonObject();
    }

}
