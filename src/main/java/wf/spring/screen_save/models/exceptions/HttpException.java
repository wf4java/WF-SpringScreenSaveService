package wf.spring.screen_save.models.exceptions;

import org.springframework.http.HttpStatus;

public interface HttpException {

    public HttpStatus getHttpStatus();

    public String getMessage();

}
