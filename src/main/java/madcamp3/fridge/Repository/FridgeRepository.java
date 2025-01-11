package madcamp3.fridge.Repository;

import madcamp3.fridge.Domain.Fridge;
import madcamp3.fridge.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FridgeRepository extends JpaRepository<FridgeRepository, Long> {
    Optional<Fridge> findByUser(User user);
}
