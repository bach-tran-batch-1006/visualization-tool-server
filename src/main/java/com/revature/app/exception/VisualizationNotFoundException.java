package com.revature.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Visualization not found")
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
