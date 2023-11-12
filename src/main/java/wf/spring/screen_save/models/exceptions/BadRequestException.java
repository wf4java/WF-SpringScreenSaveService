package wf.spring.screen_save.models.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Getter
public class BadRequestException extends AbstractHttpException {

    private final static HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public BadRequestException() {
        super(STATUS);
    }

    public BadRequestException(String message) {
        super(message, STATUS);
    }

    public BadRequestException(BindingResult bindingResult){
        super(getStringFromBindingResult(bindingResult), STATUS);
    }

    private static String getStringFromBindingResult(BindingResult bindingResult){
        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError error : bindingResult.getFieldErrors()) {
            if (!stringBuilder.isEmpty()) stringBuilder.append("\n");
            stringBuilder.append(error.getField()).append(" - ").append(error.getDefaultMessage());
        }
        return stringBuilder.toString();
    }

}
