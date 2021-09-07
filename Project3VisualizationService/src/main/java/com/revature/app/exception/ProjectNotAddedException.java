package com.revature.app.exception;

@SuppressWarnings("serial")
public class ProjectNotAddedException extends Exception{
    public ProjectNotAddedException() {
    }

    public ProjectNotAddedException(String message) {
        super(message);
    }

    public ProjectNotAddedException(Throwable cause) {
        super(cause);
    }

    public ProjectNotAddedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectNotAddedException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
