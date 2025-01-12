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
        String kakaoId = String.valueOf(userInfo.get("id").getAsLong());
        JsonObject properties = userInfo.getAsJsonObject("properties");

        String nickname = properties.has("nickname") ?
                properties.get("nickname").getAsString() : "Unknown";
        String profileImage = properties.has("profile_image") ?
                properties.get("profile_image").getAsString() : null;

        User user = userRepository.findByKakaoId(kakaoId)
                .map(existingUser -> existingUser.update(nickname, profileImage))
                .orElseGet(() -> User.builder()
                        .kakaoId(kakaoId)
                        .nickname(nickname)
                        .profileImage(profileImage)
                        .build());

        userRepository.save(user);
    }
}

