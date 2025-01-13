package madcamp3.fridge.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "detected_items")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class DetectedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private Double confidence;
    private LocalDateTime detectedAt;
    private String imageUrl;
    private LocalDateTime expirationAt;

    private Double amount;    // 양
    private String unit;      // 단위 (g, kg, ml, L, 개, 개입, 매, 팩 등)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;

    @Builder
    public DetectedItem(
            Long id,
            String itemName,
            String userId,
            // Double confidence,
            LocalDateTime detectedAt,
            String imageUrl,
            // LocalDateTime expirationAt,
            Double amount,
            String unit,
            User user
    ) {
        this.id = id;
        this.itemName = itemName;
        // this.confidence = confidence;
        this.detectedAt = detectedAt;
        this.imageUrl = imageUrl;
        // this.expirationAt = expirationAt;
        this.amount = amount;
        this.unit = unit;
        this.user = user;
    }

    // 수량 업데이트를 위한 메서드
    public void updateQuantity(Double amount, String unit) {
        this.amount = amount;
        this.unit = unit;
    }

    public String getUserId() {
        return user != null ? user.getId() : null;
    }
}