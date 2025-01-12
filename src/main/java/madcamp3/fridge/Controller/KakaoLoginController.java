package madcamp3.fridge.Controller;

import lombok.RequiredArgsConstructor;
import madcamp3.fridge.Service.KakaoLoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class KakaoLoginController {
    private final KakaoLoginService kakaoLoginService;

    @GetMapping("/kakao/callback")
    public ResponseEntity<String> kakaoCallback(@RequestParam String code) {
        String accessToken = kakaoLoginService.getKakaoAccessToken(code);
        String userId = kakaoLoginService.getKakaoUserInfo(accessToken);
        return ResponseEntity.ok(userId);
    }
}
