package com.revature.app.exception;

@SuppressWarnings("serial")
public class BadParameterException extends Exception {

	public BadParameterException() {
	}

	public BadParameterException(String message) {
		super(message);
	}
}
