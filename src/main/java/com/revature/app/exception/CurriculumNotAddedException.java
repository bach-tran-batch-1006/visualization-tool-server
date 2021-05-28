package com.revature.app.exception;

public class CurriculumNotAddedException extends Exception {

	public CurriculumNotAddedException() {
	}

	public CurriculumNotAddedException(String message) {
		super(message);
	}

	public CurriculumNotAddedException(Throwable cause) {
		super(cause);
	}

	public CurriculumNotAddedException(String message, Throwable cause) {
		super(message, cause);
	}

	public CurriculumNotAddedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
