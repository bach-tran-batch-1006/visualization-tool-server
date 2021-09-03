package com.revature.app.exception;

@SuppressWarnings("serial")
public class EmptyParameterException extends Exception {

	public EmptyParameterException() {
	}

	public EmptyParameterException(String message) {
		super(message);
	}

}
