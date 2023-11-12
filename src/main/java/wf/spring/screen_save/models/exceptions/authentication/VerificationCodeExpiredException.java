package wf.spring.screen_save.models.exceptions.authentication;

import org.springframework.http.HttpStatus;
import wf.spring.screen_save.models.exceptions.AbstractHttpException;


public class VerificationCodeExpiredException extends AbstractHttpException {

    private final static HttpStatus STATUS = HttpStatus.PRECONDITION_FAILED;

    public VerificationCodeExpiredException() {
        super(STATUS);
    }

    public VerificationCodeExpiredException(String message) {
        super(message, STATUS);
    }

}
