package wf.spring.screen_save.utils.validators;



import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import wf.spring.screen_save.models.entities.Person;
import wf.spring.screen_save.services.PersonService;


@Component
@RequiredArgsConstructor
public class PersonRegisterValidator implements Validator {


    private final PersonService personService;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        Person person = (Person) target;

        if(personService.existsByEmail(person.getEmail())) errors.rejectValue("email", "email-conflict", "This email is already taken");
        if(personService.existsByUsername(person.getUsername())) errors.rejectValue("username", "username-conflict", "This username is already taken");
    }
}
