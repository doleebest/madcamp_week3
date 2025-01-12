package madcamp3.fridge.Controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
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

@Controller
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

    // Authorization Code 요청
    @GetMapping("/auth/kakao/login")
    public String login() {
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize"
                + "?client_id=" + kakaoClientId
                + "&redirect_uri=" + kakaoRedirectUri
                + "&response_type=code";
        return "redirect:" + kakaoAuthUrl;
    }

    // Callback: Access Token 요청
    @GetMapping("/auth/kakao/callback")
    public String callback(@RequestParam String code) throws IOException {
        // 1. Access Token 요청
        String accessToken = getAccessToken(code);

        // 2. 사용자 정보 가져오기
        JsonObject userInfo = getUserInfo(accessToken);

        // 3. 사용자 정보 저장
        userService.saveOrUpdateUser(userInfo);

        // 4. Access Token 반환 (Postman에서 확인 가능)
        return "Access Token: " + accessToken + "\nUser Info: " + userInfo.toString();


    }

    private String getAccessToken(String code) throws IOException {
        String tokenUrl = "https://kauth.kakao.com/oauth/token";
        URL url = new URL(tokenUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        String body = "grant_type=authorization_code"
                + "&client_id=" + kakaoClientId
                + "&redirect_uri=" + kakaoRedirectUri
                + "&code=" + code;

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()))) {
            bw.write(body);
            bw.flush();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        JsonObject jsonObject = JsonParser.parseString(sb.toString()).getAsJsonObject();
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
