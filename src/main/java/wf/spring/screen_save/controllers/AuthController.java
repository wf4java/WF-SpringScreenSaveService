package wf.spring.screen_save.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import wf.spring.screen_save.dto.ErrorMessageResponseDTO;
import wf.spring.screen_save.dto.auth.*;
import wf.spring.screen_save.mappers.PersonMapper;
import wf.spring.screen_save.models.entities.Person;
import wf.spring.screen_save.models.exceptions.*;
import wf.spring.screen_save.security.PersonDetails;
import wf.spring.screen_save.services.JwtAuthService;
import wf.spring.screen_save.services.PersonService;
import wf.spring.screen_save.services.VerificationService;
import wf.spring.screen_save.utils.validators.PersonRegisterValidator;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtAuthService jwtAuthService;
    private final PersonMapper personMapper;
    private final PersonService personService;
    private final PersonRegisterValidator personRegisterValidator;
    private final VerificationService verificationService;


    @PostMapping("/jwt")
    public JwtTokenResponseDTO jwt(@RequestBody @Valid AuthenticationRequestDTO authenticationRequestDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            throw new BadRequestException(bindingResult);

        return new JwtTokenResponseDTO(jwtAuthService.validateAndGenerateToken(authenticationRequestDTO.getUsername(), authenticationRequestDTO.getPassword()));
    }


    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            throw new BadRequestException(bindingResult);

        Person person = personMapper.toPerson(registerRequestDTO);
        personRegisterValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors())
            throw new BadRequestException(bindingResult);

        personService.save(person);
    }


    @PostMapping("/send_code")
    public void sendCode(@RequestBody @Valid SendVerificationCodeRequestDTO sendVerificationCodeRequestDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            throw new BadRequestException(bindingResult);

        PersonDetails personDetails = jwtAuthService.validateForActive(sendVerificationCodeRequestDTO.getUsername(), sendVerificationCodeRequestDTO.getPassword());

        verificationService.sendCode(personDetails);
    }

    @PostMapping("/verification")
    public void verification(@RequestBody @Valid VerificationRequestDTO verificationRequestDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            throw new BadRequestException(bindingResult);

        PersonDetails personDetails = jwtAuthService.validateForActive(verificationRequestDTO.getUsername(), verificationRequestDTO.getPassword());

        verificationService.verificationPerson(personDetails, verificationRequestDTO.getVerificationCode());
    }


    @ExceptionHandler
    public ResponseEntity<ErrorMessageResponseDTO> authenticationExceptionHandler(AuthenticationException e){
        return new ResponseEntity<>(new ErrorMessageResponseDTO(e), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({NotFoundException.class, BadRequestException.class, AccessException.class, ConflictException.class})
    private ResponseEntity<ErrorMessageResponseDTO> exceptionHandler(HttpException e){
        return new ResponseEntity<>(new ErrorMessageResponseDTO(e.getMessage()), e.getHttpStatus());
    }

}
