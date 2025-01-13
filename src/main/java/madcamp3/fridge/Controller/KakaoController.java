package madcamp3.fridge.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import madcamp3.fridge.Domain.User;
import madcamp3.fridge.Dto.KakaoUserInfo;
import madcamp3.fridge.Service.KakaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/oauth/kakao")
@RequiredArgsConstructor
@Slf4j
public class KakaoController {
    private final KakaoService kakaoService;

    @PostMapping("/login")
    public ResponseEntity<?> kakaoLogin(@RequestHeader("Authorization") String accessToken) {
        try {
            KakaoUserInfo userInfo = kakaoService.getKakaoUserInfo(accessToken);
            log.error("Bearer", accessToken);
            User user = kakaoService.saveOrUpdate(userInfo);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process Kakao login: " + e.getMessage());
        }
    }
}