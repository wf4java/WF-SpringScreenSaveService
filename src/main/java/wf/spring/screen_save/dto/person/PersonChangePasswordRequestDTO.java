package wf.spring.screen_save.dto.person;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import wf.spring.screen_save.utils.validators.annotation.Password;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PersonChangePasswordRequestDTO {

    @Password
    @NotNull
    private String password;

}
