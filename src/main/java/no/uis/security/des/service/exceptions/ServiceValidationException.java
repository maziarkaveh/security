package no.uis.security.des.service.exceptions;

public class ServiceValidationException extends RuntimeException {
    public ServiceValidationException() {
        super("Configuration for service is not valid");
    }

    public ServiceValidationException(String s) {
        super(s);
    }

    public ServiceValidationException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
