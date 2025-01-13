package madcamp3.fridge.Repository;

import madcamp3.fridge.Domain.DetectedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DetectedItemRepository extends JpaRepository<DetectedItem, Long> {
    List<DetectedItem> findByDetectedAtBetween(LocalDateTime start, LocalDateTime end);

    List<DetectedItem> findByUser_Email(String email);
    List<DetectedItem> findByUser_Id(String userId);  // 이 메소드 추가
}
