package madcamp3.fridge.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {
    @Id
    private Long id;

    private String kakaoId;
    private String nickname;
    private String profileImage;
    //private String email;

    // User 업데이트 메서드 (옵션)
    public User update(String nickname, String profileImage, String email) {
        return User.builder()
                .kakaoId(this.kakaoId) // 기존 kakaoId 유지
                .nickname(nickname)
                .profileImage(profileImage)
                .email(email)
                .build();
    }
}
