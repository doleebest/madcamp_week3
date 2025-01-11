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

    @PostMapping("/detect")
    public ResponseEntity<List<DetectedItem>> detectItems(@RequestParam("image") MultipartFile image) {
        try {
            List<DetectedItem> detectedItems = detectionService.detectAndSaveItems(image);
            return ResponseEntity.ok(detectedItems);
        } catch (IOException e) {
            log.error("Error processing image: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/items")
    public ResponseEntity<List<DetectedItem>> getDetectedItems() {
        List<DetectedItem> items = detectionService.getAllItems();
        return ResponseEntity.ok(items);
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<DetectedItem> updateItem(
            @PathVariable Long id,
            @RequestBody DetectedItemUpdateRequest request) {
        try {
            DetectedItem updateItem = detectionService.updateItem(id, request);
            return ResponseEntity.ok(updateItem);
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/items/manual")
    public ResponseEntity<DetectedItem> addItemManually(
            @RequestBody DetectedItemCreateRequest request){
        DetectedItem newItem = detectionService.addItemManually(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newItem);
    }
}
