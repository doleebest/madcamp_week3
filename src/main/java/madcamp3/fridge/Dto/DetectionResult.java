package madcamp3.fridge.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetectionResult {
    private String label;
    private Double confidence;
    private List<Double> bbox;
}