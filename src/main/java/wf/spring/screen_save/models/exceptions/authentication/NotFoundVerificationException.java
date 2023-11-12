package wf.spring.screen_save.models.exceptions.authentication;

import org.springframework.http.HttpStatus;
import wf.spring.screen_save.models.exceptions.AbstractHttpException;


public class NotFoundVerificationException extends AbstractHttpException {

    private final static HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public NotFoundVerificationException() {
        super(STATUS);
    }

    public NotFoundVerificationException(String message) {
        super(message, STATUS);
    }

}
