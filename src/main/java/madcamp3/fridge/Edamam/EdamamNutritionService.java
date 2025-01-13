package madcamp3.fridge.Edamam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import madcamp3.fridge.Domain.DetectedItem;
import madcamp3.fridge.Dto.HealthScoreResponse;
import madcamp3.fridge.Repository.DetectedItemRepository;
import org.springframework.http.ResponseEntity;
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
    private final DetectedItemRepository itemRepository;

    public HealthScoreResponse calculateOverallHealthScore(String userEmail) {
        // User 엔티티의 email로 DetectedItem을 조회
        List<DetectedItem> items = itemRepository.findByUser_Email(userEmail);

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

        // 영양 균형 체크 및 제안사항 생성
        if (averageScore < 60.0) {
            suggestions.add("전반적으로 더 건강한 식재료가 필요합니다.");
        }
        checkNutritionalBalance(items, suggestions);

        return new HealthScoreResponse(averageScore, suggestions, averageScore > 60.0);
    }

    private void checkNutritionalBalance(List<DetectedItem> items, List<String> suggestions) {
        boolean hasVegetables = false;
        boolean hasFruits = false;
        boolean hasProtein = false;

        for (DetectedItem item : items) {
            String name = item.getItemName().toLowerCase();
            if (isVegetable(name)) hasVegetables = true;
            if (isFruit(name)) hasFruits = true;
            if (isProtein(name)) hasProtein = true;
        }

        if (!hasVegetables) suggestions.add("채소류가 부족합니다.");
        if (!hasFruits) suggestions.add("과일류가 부족합니다.");
        if (!hasProtein) suggestions.add("단백질 식품이 부족합니다.");
    }

    private boolean isVegetable(String name) {
        Set<String> vegetables = Set.of("양배추", "당근", "시금치", "브로콜리", "양파", "마늘", "파");
        return vegetables.contains(name);
    }

    private boolean isFruit(String name) {
        Set<String> fruits = Set.of("사과", "바나나", "오렌지", "포도", "딸기", "키위");
        return fruits.contains(name);
    }

    private boolean isProtein(String name) {
        Set<String> proteins = Set.of("닭고기", "돼지고기", "소고기", "계란", "두부", "생선");
        return proteins.contains(name);
    }

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

        if (nutrition.getHealthLabels() != null) {
            if (nutrition.getHealthLabels().contains("VEGETARIAN")) score += 5;
            if (nutrition.getHealthLabels().contains("LOW_FAT")) score += 5;
            if (nutrition.getHealthLabels().contains("LOW_SODIUM")) score += 5;
        }

        if (nutrition.getTotalNutrients() != null) {
            Double calories = nutrition.getTotalNutrients().getCalories();
            if (calories != null && calories < 300) score += 5;
        }

        return Math.min(100, Math.max(0, score));
    }
}