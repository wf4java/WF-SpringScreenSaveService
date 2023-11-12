package wf.spring.screen_save.utils.validators.constraint;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import wf.spring.screen_save.utils.validators.annotation.LongId;


@RequiredArgsConstructor
public class LongIdValidator implements ConstraintValidator<LongId, Long> {

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
        if(id == null) return true;
        return id >= 0;
    }

}
