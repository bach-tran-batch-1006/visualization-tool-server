package com.revature.app.exception;

@SuppressWarnings("serial")
public class ForeignKeyConstraintException extends Exception {

	public ForeignKeyConstraintException() {
	}

	public ForeignKeyConstraintException(String message) {
		super(message);
	}

	public ForeignKeyConstraintException(Throwable cause) {
		super(cause);
	}

	public ForeignKeyConstraintException(String message, Throwable cause) {
		super(message, cause);
	}

	public ForeignKeyConstraintException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
