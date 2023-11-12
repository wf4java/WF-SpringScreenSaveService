package wf.spring.screen_save.models.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AbstractHttpException extends RuntimeException implements HttpException {

    private final HttpStatus httpStatus;

    public AbstractHttpException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public AbstractHttpException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
