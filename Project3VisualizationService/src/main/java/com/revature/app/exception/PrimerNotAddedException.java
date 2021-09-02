package com.revature.app.exception;

@SuppressWarnings("serial")
public class PrimerNotAddedException extends Exception {

    public PrimerNotAddedException() {
    }

    public PrimerNotAddedException(String message) {
        super(message);
    }

    public PrimerNotAddedException(Throwable cause) {
        super(cause);
    }

    public PrimerNotAddedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrimerNotAddedException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
