package madcamp3.fridge.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import madcamp3.fridge.Domain.UserPhoneNumber;
import madcamp3.fridge.Dto.HealthScoreResponse;
import madcamp3.fridge.Edamam.EdamamNutritionService;
import madcamp3.fridge.Repository.UserPhoneNumberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class HealthAlertService {
    private final EdamamNutritionService nutritionService;
    private final CoolSmsService smsService;
    private final UserPhoneNumberRepository userPhoneNumberRepository;

    @Transactional
    public void checkHealthScoreAndAlert(String userId) {
        HealthScoreResponse healthScore = nutritionService.calculateOverallHealthScore();

        if (healthScore.getHealthScore() <= 60.0) {
            UserPhoneNumber userPhone = userPhoneNumberRepository.findByUserId(userId)
                    .orElseThrow(() -> new RuntimeException("등록된 전화번호가 없습니다."));

            String messageContent = createAlertMessage(healthScore);
            smsService.sendHealthAlert(userPhone.getParentsPhone(), messageContent);

            log.info("Health alert sent for user: {}. Score: {}", userId, healthScore.getHealthScore());
        }
    }

    private String createAlertMessage(HealthScoreResponse healthScore) {
        StringBuilder message = new StringBuilder();
        message.append(String.format("[자녀의 냉장고 건강도 알림]\n\n"));
        message.append(String.format("현재 건강도: %.1f점\n\n", healthScore.getHealthScore()));

        if (!healthScore.getSuggestions().isEmpty()) {
            message.append("개선사항:\n");
            for (String suggestion : healthScore.getSuggestions()) {
                message.append("- ").append(suggestion).append("\n");
            }
        }

        message.append("\n건강한 식단을 위해 관심이 필요합니다!");
        return message.toString();
    }
}