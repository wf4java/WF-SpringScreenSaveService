package wf.spring.screen_save.models.exceptions.authentication;

import org.springframework.http.HttpStatus;
import wf.spring.screen_save.models.exceptions.AbstractHttpException;


public class IncorrectVerificationCodeException extends AbstractHttpException {

    private final static HttpStatus STATUS = HttpStatus.UNPROCESSABLE_ENTITY;

    public IncorrectVerificationCodeException() {
        super(STATUS);
    }

    public IncorrectVerificationCodeException(String message) {
        super(message, STATUS);
    }

}
