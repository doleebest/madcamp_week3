package madcamp3.fridge.Dto;

import java.util.List;

// DetectionResult.java
public class DetectionResult {
    private String label;
    private Double confidence;
    private List<Double> bbox;  // [x1, y1, x2, y2] 형태의 바운딩 박스 좌표

    // Getters and Setters
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public List<Double> getBbox() {
        return bbox;
    }

    public void setBbox(List<Double> bbox) {
        this.bbox = bbox;
    }

    // 선택적: Builder 패턴이나 생성자를 추가할 수 있습니다
    public DetectionResult() {}

    public DetectionResult(String label, Double confidence, List<Double> bbox) {
        this.label = label;
        this.confidence = confidence;
        this.bbox = bbox;
    }
}