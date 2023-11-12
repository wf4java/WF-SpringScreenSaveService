package wf.spring.screen_save.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wf.spring.screen_save.utils.validators.annotation.Password;
import wf.spring.screen_save.utils.validators.annotation.Username;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class SendVerificationCodeRequestDTO {

    @NotEmpty
    @Username
    private String username;

    @NotNull
    @Password
    private String password;

}
