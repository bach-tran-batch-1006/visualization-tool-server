package com.revature.app.exception;

public class EmptyCurriculumException extends Exception {

	public EmptyCurriculumException() {
	}

	public EmptyCurriculumException(String message) {
		super(message);
	}

	public EmptyCurriculumException(Throwable cause) {
		super(cause);
	}

	public EmptyCurriculumException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmptyCurriculumException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
