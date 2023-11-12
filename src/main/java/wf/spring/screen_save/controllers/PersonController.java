package wf.spring.screen_save.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import wf.spring.screen_save.dto.ErrorMessageResponseDTO;
import wf.spring.screen_save.dto.person.PersonChangePasswordRequestDTO;
import wf.spring.screen_save.models.entities.Person;
import wf.spring.screen_save.models.exceptions.*;
import wf.spring.screen_save.services.PersonService;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;


    @PostMapping("/change_password")
    public void changePassword(@RequestBody @Valid PersonChangePasswordRequestDTO personChangePasswordRequestDTO, BindingResult bindingResult, @AuthenticationPrincipal Person principal) {
        if(bindingResult.hasErrors())
            throw new BadRequestException(bindingResult);

        personService.changePassword(principal.getId(), personChangePasswordRequestDTO.getPassword());
    }


    @ExceptionHandler({NotFoundException.class, BadRequestException.class, AccessException.class, ConflictException.class})
    private ResponseEntity<ErrorMessageResponseDTO> exceptionHandler(HttpException e){
        return new ResponseEntity<>(new ErrorMessageResponseDTO(e.getMessage()), e.getHttpStatus());
    }

}
