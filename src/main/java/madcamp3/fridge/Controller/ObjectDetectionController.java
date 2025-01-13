package madcamp3.fridge.Controller;

import lombok.extern.slf4j.Slf4j;
import madcamp3.fridge.Domain.DetectedItem;
import madcamp3.fridge.Service.ObjectDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import madcamp3.fridge.Dto.DetectedItemUpdateRequest;
import madcamp3.fridge.Dto.DetectedItemCreateRequest;  // 추가


import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/detection")
@Slf4j
public class ObjectDetectionController {
    private final ObjectDetectionService detectionService;

    @Autowired
    public ObjectDetectionController(ObjectDetectionService detectionService) {
        this.detectionService = detectionService;
    }

    // 물체 인식 및 저장
    @PostMapping("/detect")
    public ResponseEntity<List<DetectedItem>> detectItems(
            @RequestParam("image") MultipartFile image,
            @RequestParam("userEmail") String userEmail) {  // userId -> userEmail
        try {
            List<DetectedItem> detectedItems = detectionService.detectAndSaveItems(image, userEmail);
            return ResponseEntity.ok(detectedItems);
        } catch (IOException e) {
            log.error("Error processing image: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 특정 사용자의 물체 목록 조회
    @GetMapping("/items")
    public ResponseEntity<List<DetectedItem>> getDetectedItems(@RequestParam("userEmail") String userEmail) {  // userId -> userEmail
        List<DetectedItem> items = detectionService.getItemsByUserId(userEmail);
        return ResponseEntity.ok(items);
    }

    // 물체 정보 수정
    @PutMapping("/items/{id}")
    public ResponseEntity<DetectedItem> updateItem(
            @PathVariable Long id,
            @RequestParam("userEmail") String userEmail,  // userId -> userEmail
            @RequestBody DetectedItemUpdateRequest request) {
        try {
            DetectedItem updateItem = detectionService.updateItem(id, userEmail, request);
            return ResponseEntity.ok(updateItem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 수동으로 물체 추가
    @PostMapping("/items/manual")
    public ResponseEntity<DetectedItem> addItemManually(
            @RequestParam("userEmail") String userEmail,  // userId -> userEmail
            @RequestBody DetectedItemCreateRequest request) {
        DetectedItem newItem = detectionService.addItemManually(userEmail, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newItem);
    }

    // 물체 삭제
    @DeleteMapping("/items/{itemId}")  // 따옴표 제거
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long itemId,
            @RequestParam("userEmail") String userEmail) {  // userId -> userEmail
        detectionService.deleteItem(itemId, userEmail);
        return ResponseEntity.noContent().build();
    }
}