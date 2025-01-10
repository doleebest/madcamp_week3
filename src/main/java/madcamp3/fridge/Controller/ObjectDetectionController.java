package madcamp3.fridge.Controller;

import madcamp3.fridge.Domain.DetectedItem;
import madcamp3.fridge.Service.ObjectDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/detection")
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
}
