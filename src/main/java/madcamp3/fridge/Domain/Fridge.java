package madcamp3.fridge.Domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Fridge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 냉장고 이름

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "fridge", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetectedItem> detectedItems = new ArrayList<>();
}
