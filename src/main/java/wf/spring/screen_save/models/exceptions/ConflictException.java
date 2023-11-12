package wf.spring.screen_save.models.exceptions;

import org.springframework.http.HttpStatus;

public class ConflictException extends AbstractHttpException {

    private final static HttpStatus STATUS = HttpStatus.CONFLICT;

    public ConflictException() {
        super(STATUS);
    }

    public ConflictException(String message) {
        super(message, STATUS);
    }

}
