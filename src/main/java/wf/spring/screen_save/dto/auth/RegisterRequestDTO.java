package wf.spring.screen_save.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import wf.spring.screen_save.utils.validators.annotation.Password;
import wf.spring.screen_save.utils.validators.annotation.Username;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterRequestDTO {


    @NotNull
    @Username
    private String username;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Password
    private String password;




}