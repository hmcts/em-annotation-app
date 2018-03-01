package uk.gov.hmcts.reform.em.annotation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AnnotationSetNotFoundException extends RuntimeException {

    public AnnotationSetNotFoundException(String message) {
        super(message);
    }

}
