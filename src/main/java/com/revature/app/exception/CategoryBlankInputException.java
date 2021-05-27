package com.revature.app.exception;

public class CategoryBlankInputException extends Exception {

	public CategoryBlankInputException() {
	
	}

	public CategoryBlankInputException(String message) {
		super(message);
		
	}

	public CategoryBlankInputException(Throwable cause) {
		super(cause);
	
	}

	public CategoryBlankInputException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public CategoryBlankInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	
	}

}
