package madcamp3.fridge.Domain;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String kakaoId; // 카카오 고유 ID
    private String username; // 사용자 이름
}
