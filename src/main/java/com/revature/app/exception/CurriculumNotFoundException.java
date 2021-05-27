package com.revature.app.exception;

public class CurriculumNotFoundException extends Exception {

	public CurriculumNotFoundException() {
	}

	public CurriculumNotFoundException(String message) {
		super(message);
	}

	public CurriculumNotFoundException(Throwable cause) {
		super(cause);
	}

	public CurriculumNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public CurriculumNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
