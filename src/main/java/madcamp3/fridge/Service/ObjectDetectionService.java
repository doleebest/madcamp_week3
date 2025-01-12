package madcamp3.fridge.Service;

import lombok.extern.slf4j.Slf4j;
import madcamp3.fridge.Domain.DetectedItem;
import madcamp3.fridge.Dto.DetectedItemCreateRequest;
import madcamp3.fridge.Dto.DetectedItemUpdateRequest;
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

    public List<DetectedItem> detectAndSaveItems(MultipartFile image, String userId) throws IOException {
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

        List<DetectedItem> savedItems = new ArrayList<>();
        if (response.getBody() != null) {
            for (DetectionResult result : response.getBody()) {
                DetectedItem item = DetectedItem.builder()
                        .userId(userId)
                        .itemName(result.getLabel())
                        .confidence(result.getConfidence())
                        .detectedAt(LocalDateTime.now())
                        .expirationAt(LocalDateTime.now().plusDays(7))
                        .amount(result.getAmount())
                        .unit(result.getUnit())
                        .build();
                savedItems.add(repository.save(item));
            }
        }


        return savedItems;

    }

    public List<DetectedItem> getAllItems() {
        return repository.findAll();
    }

    // detect 된 아이템 정보 수정
    public DetectedItem updateItem(Long id, String userId, DetectedItemUpdateRequest request){
        DetectedItem item = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + id));

        // 본인 것만 수정 가능하도록 체크
        if (!item.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Not authorized to update this item");
        }

        // Builder 패턴을 사용하여 업데이트
        DetectedItem updatedItem = DetectedItem.builder()
                .id(item.getId())
                .itemName(request.getItemName() != null ? request.getItemName() : item.getItemName())
                .amount(request.getAmount() != null ? request.getAmount() : item.getAmount())
                .unit(request.getUnit() != null ? request.getUnit() : item.getUnit())
                .expirationAt(request.getExpirationAt() != null ? request.getExpirationAt() : item.getExpirationAt())
                .confidence(item.getConfidence())
                .detectedAt(item.getDetectedAt())
                .imageUrl(item.getImageUrl())
                .build();

        return repository.save(item);
    }

    // 아이템 수동 추가
    public DetectedItem addItemManually(String userId, DetectedItemCreateRequest request) { // 파라미터 순서도 컨트롤러랑 맞춰야 함.
        DetectedItem newItem = DetectedItem.builder()
                .itemName(request.getItemName())
                .amount(request.getAmount())
                .unit(request.getUnit())
                .detectedAt(LocalDateTime.now())
                .expirationAt(request.getExpirationAt())
                .confidence(1.0) // 수동 추가는 100% 확실
                .build();

        return repository.save(newItem);
    }

    public List<DetectedItem> getItemsByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    // 물체 삭제 (본인 것만 삭제 가능)
    public void deleteItem(Long itemId, String userId) {
        DetectedItem item = repository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        // 본인 것만 삭제 가능하도록 체크
        if (!item.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Not authorized to delete this item");
        }

        repository.delete(item);
    }
}

