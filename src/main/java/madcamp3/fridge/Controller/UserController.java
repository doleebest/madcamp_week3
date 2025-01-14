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
@RequestMapping("/api")  // API 기본 경로
@RequiredArgsConstructor
public class UserController {

    private final SecurityUtil securityUtil;
    private final UserPhoneNumberService userPhoneNumberService;


    // CORS 설정: localhost:3000에서 요청 허용
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/user")
    public ResponseEntity<User> getUser() {
        User user = securityUtil.getCurrentUser();
        return ResponseEntity.ok(user);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/user/parents-phone")
    public ResponseEntity<UserPhoneNumber> saveParentsPhone(@RequestBody ParentsPhoneRequest request) {
        User currentUser = securityUtil.getCurrentUser();
        UserPhoneNumber savedNumber = userPhoneNumberService.saveParentsPhone(
                currentUser.getEmail(),
                request.getParentsPhone()
        );
        return ResponseEntity.ok(savedNumber);
    }
}
