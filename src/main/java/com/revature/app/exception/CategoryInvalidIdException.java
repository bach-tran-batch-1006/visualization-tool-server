package com.revature.app.exception;

public class CategoryInvalidIdException extends Exception {

	public CategoryInvalidIdException() {
		
	}

	public CategoryInvalidIdException(String message) {
		super(message);
		
	}

	public CategoryInvalidIdException(Throwable cause) {
		super(cause);
		
	}

	public CategoryInvalidIdException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public CategoryInvalidIdException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
