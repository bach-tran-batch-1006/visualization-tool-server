package com.revature.app.exception;

@SuppressWarnings("serial")
public class CategoryNotFoundException extends Exception {

	public CategoryNotFoundException() {
	}

	public CategoryNotFoundException(String message) {
		super(message);
	}

	public CategoryNotFoundException(Throwable cause) {
		super(cause);
	}

	public CategoryNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public CategoryNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
