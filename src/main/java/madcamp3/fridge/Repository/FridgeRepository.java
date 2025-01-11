package madcamp3.fridge.Repository;

import madcamp3.fridge.Domain.Fridge;
import madcamp3.fridge.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FridgeRepository extends JpaRepository<FridgeRepository, Long> {
    Optional<Fridge> findByUserId(String userId);
}
