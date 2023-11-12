package wf.spring.screen_save.utils.validators.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import wf.spring.screen_save.utils.validators.constraint.LongIdValidator;

import java.lang.annotation.*;



@Constraint(validatedBy = LongIdValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LongId {

    String message() default "Invalid id";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
