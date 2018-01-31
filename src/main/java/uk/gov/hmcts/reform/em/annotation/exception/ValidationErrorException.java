package uk.gov.hmcts.reform.em.annotation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by pawel on 13/10/2017.
 */
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class ValidationErrorException extends RuntimeException {

    public ValidationErrorException(String message) {
        super(message);
    }

}
