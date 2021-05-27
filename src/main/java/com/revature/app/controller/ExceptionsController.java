package com.revature.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.revature.app.message.dto.Message;

@ControllerAdvice
public class ExceptionsController extends ResponseEntityExceptionHandler {
/*
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Visualization not Found part 2")
	@ExceptionHandler(VisualizationNotFound.class)
	public void visualizationnotfound() {

		//return new Message("not working");
		//Message message = new Message("Visualization Not found");
		//return new ResponseEntity(message, HttpStatus.INTERNAL_SERVER_ERROR);

	}
*/
}
