package wf.spring.screen_save.models.exceptions;

import org.springframework.http.HttpStatus;

public class ServiceException extends AbstractHttpException {

    private final static HttpStatus STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public ServiceException() {
        super(STATUS);
    }

    public ServiceException(String message) {
        super(message, STATUS);
    }

}
