package uk.gov.hmcts.reform.em.annotation.exception;

public class AppConfigurationException extends RuntimeException {

    public AppConfigurationException(String message, Throwable e) {
        super(message, e);
    }
}
