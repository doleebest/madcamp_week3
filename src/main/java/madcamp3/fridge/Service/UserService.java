package madcamp3.fridge.Service;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import madcamp3.fridge.Domain.User;
import madcamp3.fridge.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void saveOrUpdateUser(JsonObject userInfo) {
        String kakaoId = userInfo.get("id").getAsString();
        String nickname = userInfo.getAsJsonObject("properties").get("nickname").getAsString();
        String profileImage = userInfo.getAsJsonObject("properties").get("profile_image").getAsString();
        String email = userInfo.getAsJsonObject("kakao_account").get("email").getAsString();

        // 기존 사용자 조회
        User user = userRepository.findByKakaoId(kakaoId)
                .orElse(User.builder()
                        .kakaoId(kakaoId)
                        .nickname(nickname)
                        .profileImage(profileImage)
                        .email(email)
                        .build());

        // 업데이트: 새 정보를 적용
        User updatedUser = user.update(nickname, profileImage, email);

        // 저장
        userRepository.save(updatedUser);
    }
}
