package madcamp3.fridge.Domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "detected_items")
@NoArgsConstructor(access = AccessLevel.PUBLIC) // 객체를 생성할 때 기본자가 필요. 객체를 인스턴스화.
public class DetectedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private Double confidence; // 정확도
    private LocalDateTime detectedAt;
    private String imageUrl;
    private LocalDateTime expirationAt;

    @Builder
    public DetectedItem(Long id, String itemName, Double confidence, LocalDateTime detectedAt, String imageUrl, LocalDateTime expirationAt){
        this.id = id;
        this.itemName = itemName;
        this.confidence = confidence;
        this.detectedAt = detectedAt;
        this.imageUrl = imageUrl;
        this.expirationAt = expirationAt;
    }
}
