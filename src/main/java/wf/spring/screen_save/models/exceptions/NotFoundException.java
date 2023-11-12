package wf.spring.screen_save.models.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends AbstractHttpException {

    private final static HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public NotFoundException() {
        super(STATUS);
    }

    public NotFoundException(String message) {
        super(message, STATUS);
    }

}
