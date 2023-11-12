package wf.spring.screen_save.models;

import org.springframework.http.HttpStatus;
import wf.spring.screen_save.models.exceptions.AbstractHttpException;

public class ServiceException extends AbstractHttpException {

    private final static HttpStatus STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public ServiceException() {
        super(STATUS);
    }

    public ServiceException(String message) {
        super(message, STATUS);
    }

}
