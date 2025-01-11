// HealthScoreResponse.java (DTO 패키지에 생성)
package madcamp3.fridge.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthScoreResponse {
    private double healthScore;
    private List<String> suggestions;
    private boolean isHealthy;
}