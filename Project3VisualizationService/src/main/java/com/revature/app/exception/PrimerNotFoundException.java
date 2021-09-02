package com.revature.app.exception;

@SuppressWarnings("serial")
public class PrimerNotFoundException extends Exception {

    public PrimerNotFoundException() {
    }

    public PrimerNotFoundException(String message) {
        super(message);
    }

    public PrimerNotFoundException(Throwable cause) {
        super(cause);
    }

    public PrimerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrimerNotFoundException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}