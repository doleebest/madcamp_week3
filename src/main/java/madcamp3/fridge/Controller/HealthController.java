package madcamp3.fridge.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import madcamp3.fridge.Dto.HealthScoreResponse;
import madcamp3.fridge.Service.HealthAlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
@Slf4j
public class HealthController {
    private final HealthAlertService healthAlertService;

    @GetMapping("/check")
    public ResponseEntity<HealthScoreResponse> checkHealthScore(@RequestParam String userEmail) {
        try {
            HealthScoreResponse response = healthAlertService.checkHealthScoreAndAlert(userEmail);
            return ResponseEntity.ok(response);  // HealthScoreResponse 객체를 직접 반환
        } catch (Exception e) {
            log.error("Error checking health score: ", e);
            return ResponseEntity.internalServerError().body(null);
        }
    }
}