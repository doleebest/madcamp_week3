package madcamp3.fridge.Repository;

import madcamp3.fridge.Domain.UserPhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPhoneNumberRepository extends JpaRepository<UserPhoneNumber, String> {
    Optional<UserPhoneNumber> findByUserEmail(String userEmail);
}
