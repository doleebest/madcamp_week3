package madcamp3.fridge.Service;

import lombok.extern.slf4j.Slf4j;
import madcamp3.fridge.Domain.DetectedItem;
import madcamp3.fridge.Domain.User;
import madcamp3.fridge.Dto.DetectedItemCreateRequest;
import madcamp3.fridge.Dto.DetectedItemUpdateRequest;
import madcamp3.fridge.Dto.DetectionResult;
import madcamp3.fridge.Repository.DetectedItemRepository;
import madcamp3.fridge.Repository.UserRepository;
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
    private final UserRepository userRepository;

    @Autowired
    public ObjectDetectionService(RestTemplate restTemplate, DetectedItemRepository repository, UserRepository userRepository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<DetectedItem> detectAndSaveItems(MultipartFile image, String userId) throws IOException {
        byte[] imageBytes = image.getBytes();

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

        ResponseEntity<DetectionResult[]> response = restTemplate.exchange(
                YOLO_API_URL,
                HttpMethod.POST,
                requestEntity,
                DetectionResult[].class
        );

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<DetectedItem> savedItems = new ArrayList<>();
        if (response.getBody() != null) {
            for (DetectionResult result : response.getBody()) {
                DetectedItem item = DetectedItem.builder()
                        .user(user)
                        .itemName(result.getLabel())
                        .detectedAt(LocalDateTime.now())
                        .amount(result.getAmount())
                        .unit(result.getUnit())
                        .build();
                savedItems.add(repository.save(item));
            }
        }

        return savedItems;
    }

    public DetectedItem updateItem(Long id, String userId, DetectedItemUpdateRequest request) {
        DetectedItem item = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + id));

        if (!userId.equals(item.getUserId())) {
            throw new IllegalArgumentException("Not authorized to update this item");
        }

        item.setItemName(request.getItemName() != null ? request.getItemName() : item.getItemName());
        item.setAmount(request.getAmount() != null ? request.getAmount() : item.getAmount());
        item.setUnit(request.getUnit() != null ? request.getUnit() : item.getUnit());

        return repository.save(item);
    }

    public DetectedItem addItemManually(String userId, DetectedItemCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        DetectedItem newItem = DetectedItem.builder()
                .user(user)
                .itemName(request.getItemName())
                .amount(request.getAmount())
                .unit(request.getUnit())
                .detectedAt(LocalDateTime.now())
                .build();

        return repository.save(newItem);
    }

    public void deleteItem(Long itemId, String userId) {
        DetectedItem item = repository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        if (!userId.equals(item.getUserId())) {
            throw new IllegalArgumentException("Not authorized to delete this item");
        }

        repository.delete(item);
    }

    public List<DetectedItem> getItemsByUserId(String userId) {
        return repository.findByUser_Id(userId);
    }

}
