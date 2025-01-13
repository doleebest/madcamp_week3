package madcamp3.fridge.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KakaoUserInfo {
    private Long id;
    private String nickname;
    private String profileImage;
}
