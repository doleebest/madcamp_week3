package madcamp3.fridge.Domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fridges")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Fridge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;  // 카카오 사용자 ID

    @OneToMany(mappedBy = "fridge", cascade = CascadeType.ALL)
    private List<DetectedItem> items = new ArrayList<>();

    @Builder
    public Fridge(String userId) {
        this.userId = userId;
    }

    public void addItem(DetectedItem item) {
        this.items.add(item);
        item.setFridge(this);
    }
}