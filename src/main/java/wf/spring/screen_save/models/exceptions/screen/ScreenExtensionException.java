package wf.spring.screen_save.models.exceptions.screen;

import org.springframework.http.HttpStatus;
import wf.spring.screen_save.models.exceptions.AbstractHttpException;

public class ScreenExtensionException extends AbstractHttpException {

    private final static HttpStatus STATUS = HttpStatus.UNSUPPORTED_MEDIA_TYPE;

    public ScreenExtensionException() {
        super(STATUS);
    }

    public ScreenExtensionException(String message) {
        super(message, STATUS);
    }

}
