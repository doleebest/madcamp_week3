package madcamp3.fridge.Controller;

import madcamp3.fridge.Domain.User;
import madcamp3.fridge.Domain.UserPhoneNumber;
import madcamp3.fridge.Dto.ParentsPhoneRequest;
import madcamp3.fridge.Service.UserPhoneNumberService;
import madcamp3.fridge.config.auth.SecurityUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final SecurityUtil securityUtil;
    private final UserPhoneNumberService userPhoneNumberService;

    @CrossOrigin(origins = "*")  // 안드로이드와 통신을 위해 CORS 설정 변경
    @PostMapping("/user/parents-phone")
    public ResponseEntity<UserPhoneNumber> saveParentsPhone(
            @RequestParam String userEmail,  // URL 파라미터로 userEmail 받기
            @RequestBody ParentsPhoneRequest request) {
        UserPhoneNumber savedNumber = userPhoneNumberService.saveParentsPhone(
                userEmail,  // URL에서 받은 이메일 사용
                request.getParentsPhone()
        );
        return ResponseEntity.ok(savedNumber);
    }
}
