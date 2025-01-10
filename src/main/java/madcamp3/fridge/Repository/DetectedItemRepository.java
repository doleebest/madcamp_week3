package madcamp3.fridge.Repository;

import madcamp3.fridge.Domain.DetectedItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DetectedItemRepository extends JpaRepository<DetectedItem, Long> {
    List<DetectedItem> findByDetectedAtBetween(LocalDateTime start, LocalDateTime end);

}
