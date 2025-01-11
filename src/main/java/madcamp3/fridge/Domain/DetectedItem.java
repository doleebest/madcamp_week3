package madcamp3.fridge.Domain;

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

    // 추가된 필드
    private Double amount;    // 양
    private String unit;      // 단위 (g, kg, ml, L, 개, 개입, 매, 팩 등)

    @ManyToOne
    @JoinColumn(name = "frdige_id")
    private Fridge fridge;

    @Builder
    public DetectedItem(
            Long id,
            String itemName,
            Double confidence,
            LocalDateTime detectedAt,
            String imageUrl,
            LocalDateTime expirationAt,
            Double amount,
            String unit
    ) {
        this.id = id;
        this.itemName = itemName;
        this.confidence = confidence;
        this.detectedAt = detectedAt;
        this.imageUrl = imageUrl;
        this.expirationAt = expirationAt;
        this.amount = amount;
        this.unit = unit;
    }

    // 수량 업데이트를 위한 메서드
    public void updateQuantity(Double amount, String unit) {
        this.amount = amount;
        this.unit = unit;
    }
}