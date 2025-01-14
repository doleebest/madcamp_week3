package madcamp3.fridge.Service;

import lombok.RequiredArgsConstructor;
import madcamp3.fridge.Domain.UserPhoneNumber;
import madcamp3.fridge.Repository.UserPhoneNumberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserPhoneNumberService {
    private final UserPhoneNumberRepository userPhoneNumberRepository;

    @Transactional
    public UserPhoneNumber saveParentsPhone(String userEmail, String parentsPhone) {
        // 기존 번호가 있는지 확인
        UserPhoneNumber existingNumber = userPhoneNumberRepository
                .findByUserEmail(userEmail)
                .orElse(null);

        if (existingNumber != null) {
            // 기존 번호 업데이트
            existingNumber.updateParentsPhone(parentsPhone);
            return existingNumber;
        } else {
            // 새로운 번호 저장
            return userPhoneNumberRepository.save(
                    UserPhoneNumber.builder()
                            .userEmail(userEmail)
                            .motherPhone(parentsPhone)
                            .build()
            );
        }
    }
}