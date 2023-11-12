package wf.spring.screen_save.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

public class VerificationRequestDTO {

    @NotNull
    @Size(min = 6, max = 6)
    private String verificationCode;

    @NotEmpty
    @Username
    private String username;

    @NotNull
    @Password
    private String password;

}
