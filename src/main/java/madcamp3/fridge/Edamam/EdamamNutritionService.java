package madcamp3.fridge.Edamam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import madcamp3.fridge.Domain.DetectedItem;
import madcamp3.fridge.Repository.DetectedItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class EdamamNutritionService {
    private final EdamamConfig edamamConfig;
    private final RestTemplate restTemplate;
    //private final KakaoMessageService kakaoMessageService;
    private final DetectedItemRepository itemRepository;

    public HealthScoreResponse calculateOverallHealthScore() {
        List<DetectedItem> items = itemRepository.findAll();
        if (items.isEmpty()) {
            return new HealthScoreResponse(0.0,
                    Collections.singletonList("냉장고가 비어있습니다."),
                    false);
        }

        double totalScore = 0;
        List<String> suggestions = new ArrayList<>();
        Map<String, Double> itemScores = new HashMap<>();

        for (DetectedItem item : items) {
            double score = getHealthScore(item.getItemName());
            itemScores.put(item.getItemName(), score);
            totalScore += score;
        }

        double averageScore = totalScore / items.size();

//        // 건강도가 낮은 경우 카카오톡 알림 발송
//        if (averageScore <= 60.0) {
//            String message = createAlertMessage(averageScore, itemScores);
//            kakaoMessageService.sendMessage(message);
//        }
//
//        return createHealthScoreResponse(averageScore, itemScores);
    // }

    private double getHealthScore(String foodName) {
        try {
            String encodedFood = URLEncoder.encode(foodName, StandardCharsets.UTF_8);
            String url = String.format("%s?app_id=%s&app_key=%s&ingr=%s",
                    edamamConfig.getBaseUrl(),
                    edamamConfig.getAppId(),
                    edamamConfig.getAppKey(),
                    encodedFood);

            ResponseEntity<EdamamResponse> response =
                    restTemplate.getForEntity(url, EdamamResponse.class);

            if (response.getBody() != null) {
                return calculateScoreFromNutrition(response.getBody());
            }
        } catch (Exception e) {
            log.error("Error getting nutrition info for {}: {}", foodName, e.getMessage());
        }
        return 50.0; // 기본 점수
    }

    private double calculateScoreFromNutrition(EdamamResponse nutrition) {
        double score = 70.0; // 기본 점수

        // 영양소 기반 점수 계산
        if (nutrition.getHealthLabels().contains("VEGETARIAN")) score += 5;
        if (nutrition.getHealthLabels().contains("LOW_FAT")) score += 5;
        if (nutrition.getHealthLabels().contains("LOW_SODIUM")) score += 5;

        // 영양소별 점수 조정
        Double calories = nutrition.getTotalNutrients().getCalories();
        if (calories != null && calories < 300) score += 5;

        return Math.min(100, Math.max(0, score));
    }

    private String createAlertMessage(double averageScore, Map<String, Double> itemScores) {
        StringBuilder message = new StringBuilder();
        message.append(String.format("⚠️ 자녀분의 냉장고 건강도 알림 ⚠️\n\n현재 건강도: %.1f점\n\n", averageScore));
        message.append("📊 식품별 건강도:\n");

        itemScores.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(entry ->
                        message.append(String.format("- %s: %.1f점\n", entry.getKey(), entry.getValue()))
                );

        message.append("\n건강한 식단을 위해 부모님의 맘스터치가 필요합니다!");

        return message.toString();
    }
}
