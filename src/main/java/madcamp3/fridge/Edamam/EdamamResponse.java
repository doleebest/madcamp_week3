// EdamamResponse.java
package madcamp3.fridge.Edamam;

import lombok.Data;
import java.util.List;

@Data
public class EdamamResponse {
    private List<String> healthLabels;
    private TotalNutrients totalNutrients;

    @Data
    public static class TotalNutrients {
        private Double calories;
    }
}