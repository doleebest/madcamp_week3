package madcamp3.fridge.Domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_phone_numbers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;  // 카카오 사용자 ID
    private String parentsPhone;  // 엄마 전화번호

    @Builder
    public UserPhoneNumber(String userId, String motherPhone) {
        this.userId = userId;
        this.parentsPhone = motherPhone;
    }
}