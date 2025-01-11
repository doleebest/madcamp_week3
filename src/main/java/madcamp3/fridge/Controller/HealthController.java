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

    @GetMapping("/check/{userId}")
    public ResponseEntity<String> checkHealthScore(@PathVariable String userId) {
        try {
            healthAlertService.checkHealthScoreAndAlert(userId);
            return ResponseEntity.ok("건강도 체크 완료");
        } catch (Exception e) {
            log.error("Error checking health score: ", e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}