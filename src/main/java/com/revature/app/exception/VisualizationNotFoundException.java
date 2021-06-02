package com.revature.app.exception;


@SuppressWarnings("serial")
public class VisualizationNotFoundException extends Exception {

	public VisualizationNotFoundException() {
	}

	public VisualizationNotFoundException(String message) {
		super(message);
	}

	public VisualizationNotFoundException(Throwable cause) {
		super(cause);
	}

	public VisualizationNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public VisualizationNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
