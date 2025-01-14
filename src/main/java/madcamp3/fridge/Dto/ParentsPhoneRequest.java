package madcamp3.fridge.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParentsPhoneRequest {
    private String parentsPhone;

    @Builder
    public ParentsPhoneRequest(String parentsPhone) {
        this.parentsPhone = parentsPhone;
    }
}