package wf.spring.screen_save.mappers;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import wf.spring.screen_save.dto.auth.RegisterRequestDTO;
import wf.spring.screen_save.models.entities.Person;


@Component
public class PersonMapper {

    public Person toPerson(@NonNull RegisterRequestDTO registerRequestDTO) {
        Person person = new Person();

        person.setUsername(registerRequestDTO.getUsername());
        person.setPassword(registerRequestDTO.getPassword());
        person.setEmail(registerRequestDTO.getEmail());

        return person;
    }



}
