package com.revature.app.expections;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND,
reason="Visualization not Found"
		)
public class VisualizationNotFound extends Exception {

	public VisualizationNotFound() {

	}

	public VisualizationNotFound(String message) {
		super(message);
	}

	public VisualizationNotFound(Throwable cause) {
		super(cause);

	}

	public VisualizationNotFound(String message, Throwable cause) {
		super(message, cause);

	}

	public VisualizationNotFound(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}
	

}
