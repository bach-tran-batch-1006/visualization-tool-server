package com.revature.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	
	@GetMapping(path="test")
	public String test() {
		return "Hello World";
	}
}
