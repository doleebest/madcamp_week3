package madcamp3.fridge.Service;

import lombok.extern.slf4j.Slf4j;
import madcamp3.fridge.Domain.DetectedItem;
import madcamp3.fridge.Dto.DetectionResult;
import madcamp3.fridge.Repository.DetectedItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ObjectDetectionService {
    private final String YOLO_API_URL = "http://localhost:5001/detect";
    private final RestTemplate restTemplate;
    private final DetectedItemRepository repository;

    @Autowired
    public ObjectDetectionService(RestTemplate restTemplate, DetectedItemRepository repository){
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    public List<DetectedItem> detectAndSaveItems(MultipartFile image) throws IOException {
        // 이미지를 바이트 배열로 변환
        byte[] imageBytes = image.getBytes();

        // yolo api 호출을 위한 요청 객체 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", new ByteArrayResource(imageBytes) {
            @Override
            public String getFilename() {
                return image.getOriginalFilename();
            }
        });

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // YOLO API 호출
        ResponseEntity<DetectionResult[]> response = restTemplate.exchange(
                YOLO_API_URL,
                HttpMethod.POST,
                requestEntity,
                DetectionResult[].class
        );

        // 결과를 DB에 저장
        List<DetectedItem> savedItems = new ArrayList<>();
        if (response.getBody() != null) {
            for (DetectionResult result : response.getBody()) {
                DetectedItem item = DetectedItem.builder()
                        .itemName(result.getLabel())
                        .confidence(result.getConfidence())
                        .detectedAt(LocalDateTime.now())
                        // .imageUrl("tempURL") // 이미지 저장은 굳이 안해도 됨
                        .expirationAt(LocalDateTime.now().plusDays(7)) // 기본 유통기한 7일로 설정, 필요에 따라 조정
                        .build();
                savedItems.add(repository.save(item));
            }
        }

        return savedItems;

    }

    public List<DetectedItem> getAllItems() {
        return repository.findAll();
    }
}
