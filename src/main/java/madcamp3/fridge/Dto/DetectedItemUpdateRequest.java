package madcamp3.fridge.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetectedItemUpdateRequest {
    private String itemName;
    private Double amount;
    private String unit;
    private LocalDateTime expirationAt;
}
