package madcamp3.fridge.Service;

import com.google.gson.JsonObject;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoLoginService {
    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.client.secret}")
    private String clientSecret;

    @Value("${kakao.redirect.uri}")
    private String redirectUri;

    public String getKakaoAccessToken(String code) {
        try {
            // HTTP Header 생성
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // HTTP Body 생성
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "authorization_code");
            body.add("client_id", clientId);
            body.add("redirect_uri", redirectUri);
            body.add("code", code);
            body.add("client_secret", clientSecret);

            // 로깅 추가
            log.info("Requesting access token with code: {}", code);
            log.info("Request body: {}", body);

            // HTTP 요청 보내기
            HttpEntity<MultiValueMap<String, String>> requestEntity =
                    new HttpEntity<>(body, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://kauth.kakao.com/oauth/token",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            // 응답 로깅
            log.info("Token response: {}", response.getBody());

            // 응답에서 액세스 토큰 추출
            JSONObject jsonObject = new JSONObject(response.getBody());
            return jsonObject.getString("access_token");
        } catch (Exception e) {
            log.error("Error getting Kakao access token: ", e);
            throw new RuntimeException("Failed to get Kakao access token");
        }
    }

    public JsonObject getKakaoUserInfo(String accessToken) {
        try {
            // HTTP Header 생성
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // HTTP 요청 보내기
            HttpEntity<MultiValueMap<String, String>> requestEntity =
                    new HttpEntity<>(null, headers);
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> response = restTemplate.exchange(
                    "https://kapi.kakao.com/v2/user/me",
                    HttpMethod.GET,
                    requestEntity,
                    String.class
            );

            log.info("User info response: {}", response.getBody());

            // 응답을 JsonObject로 변환하여 반환
            JSONObject jsonResponse = new JSONObject(response.getBody());
            com.google.gson.JsonObject googleJsonObject =
                    com.google.gson.JsonParser.parseString(response.getBody())
                            .getAsJsonObject();
            return googleJsonObject;

        } catch (Exception e) {
            log.error("Error getting Kakao user info: ", e);
            throw new RuntimeException("Failed to get Kakao user info");
        }
    }
}