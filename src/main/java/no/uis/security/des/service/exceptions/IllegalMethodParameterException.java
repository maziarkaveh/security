package no.uis.security.des.service.exceptions;


public class IllegalMethodParameterException extends RuntimeException {
    public IllegalMethodParameterException() {
        super("parameter/s of the method is/are not correct");
    }

    public IllegalMethodParameterException(String s) {
        super(s);
    }

    public IllegalMethodParameterException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
