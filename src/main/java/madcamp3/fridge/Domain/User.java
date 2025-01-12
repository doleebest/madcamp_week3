package madcamp3.fridge.Domain;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String kakaoId; // 카카오 고유 ID

    private String email;
    private String nickname;
    private String profileImage;
}
