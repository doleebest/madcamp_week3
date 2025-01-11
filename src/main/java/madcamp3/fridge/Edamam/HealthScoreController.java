package madcamp3.fridge.Edamam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
@Slf4j

public class HealthScoreController {
    private final EdamamNutritionService nutritionService;
    @GetMapping("/score")
    public ResponseEntity<HealthScoreResponse> getHealthScore() {
        try {
            HealthScoreResponse response = nutritionService.calculateOverallHealthScore();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error calculating health score: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
