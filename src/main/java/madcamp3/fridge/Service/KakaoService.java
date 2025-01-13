package madcamp3.fridge.Service;

import madcamp3.fridge.Domain.User;
import madcamp3.fridge.Dto.KakaoUserInfo;
import madcamp3.fridge.Repository.UserRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KakaoService {
    private final UserRepository userRepository;

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.client.secret}")
    private String clientSecret;

    public KakaoUserInfo getKakaoUserInfo(String accessToken) {
        try {
            // 카카오 API 호출
            String userInfoUri = "https://kapi.kakao.com/v2/user/me";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken);

            HttpEntity<String> entity = new HttpEntity<>(null, headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    userInfoUri,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            // JSON 파싱
            JsonElement element = JsonParser.parseString(response.getBody());
            JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            JsonObject profile = kakaoAccount.getAsJsonObject("profile");

            KakaoUserInfo userInfo = new KakaoUserInfo();
            userInfo.setId(element.getAsJsonObject().get("id").getAsLong());
            userInfo.setNickname(profile.get("nickname").getAsString());
            userInfo.setProfileImage(profile.get("profile_image_url").getAsString());

            return userInfo;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get Kakao user info", e);
        }
    }

    @Transactional
    public User saveOrUpdate(KakaoUserInfo kakaoUserInfo) {
        User user = userRepository.findByKakaoId(kakaoUserInfo.getId())
                .map(existingUser -> {
                    existingUser.setNickname(kakaoUserInfo.getNickname());
                    existingUser.setProfileImage(kakaoUserInfo.getProfileImage());
                    return existingUser;
                })
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setKakaoId(kakaoUserInfo.getId());
                    newUser.setNickname(kakaoUserInfo.getNickname());
                    newUser.setProfileImage(kakaoUserInfo.getProfileImage());
                    return newUser;
                });

        return userRepository.save(user);
    }
}
