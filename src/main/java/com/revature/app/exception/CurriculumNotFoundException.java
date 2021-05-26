package com.revature.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CurriculumNotFoundException extends RuntimeException {

	public CurriculumNotFoundException(String message) {
		super(message);
	}
	
}