package wf.spring.screen_save.utils.validators.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import wf.spring.screen_save.utils.EncodeUtils;
import wf.spring.screen_save.utils.validators.annotation.ShortId;


@RequiredArgsConstructor
public class ShortIdValidator implements ConstraintValidator<ShortId, String> {


    @Override
    public boolean isValid(String shortLink, ConstraintValidatorContext context) {
        if(shortLink == null) return true;
        if(shortLink.isBlank()) return false;

        if(shortLink.length() > 16) return false;

        return EncodeUtils.Encoder.RADIX_62.isValid(shortLink);
    }

}
