package wf.spring.screen_save.dto.screen;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import wf.spring.screen_save.utils.validators.annotation.ShortId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScreenGetRequestDTO {

    @ShortId
    @NotNull
    private String shortId;

}
