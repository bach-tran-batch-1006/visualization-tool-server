package com.revature.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.app.model.Curriculum;
import com.revature.app.service.CurriculumService;

@RestController
@RequestMapping("/api")
public class CurriculumController {

	@Autowired
	private CurriculumService curriculumService;

	@GetMapping("/curriculum")
	public List<Curriculum> getAllCurricula() {
		return curriculumService.getAllCurricula();
	}

}
