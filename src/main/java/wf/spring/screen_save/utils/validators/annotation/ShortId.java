package wf.spring.screen_save.utils.validators.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import wf.spring.screen_save.utils.validators.constraint.ShortIdValidator;

import java.lang.annotation.*;



@Constraint(validatedBy = ShortIdValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ShortId {

    String message() default "Invalid short link pattern";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}