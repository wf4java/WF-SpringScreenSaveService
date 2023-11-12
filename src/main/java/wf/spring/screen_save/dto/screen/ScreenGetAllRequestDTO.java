package wf.spring.screen_save.dto.screen;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import wf.spring.screen_save.utils.validators.annotation.LongId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScreenGetAllRequestDTO {

    @LongId
    private long offset;

    @NotNull
    @Min(1)
    @Max(100)
    private int limit;

}
