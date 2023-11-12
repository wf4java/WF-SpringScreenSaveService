package wf.spring.screen_save.models.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AccessException extends AbstractHttpException {

    private final static HttpStatus STATUS = HttpStatus.FORBIDDEN;

    public AccessException() {
        super(STATUS);
    }

    public AccessException(String message) {
        super(message, STATUS);
    }

}
