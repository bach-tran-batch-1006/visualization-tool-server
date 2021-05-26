package com.revature.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.app.model.Curriculum;
import com.revature.app.service.CurriculumService;

@RestController
@RequestMapping("/curriculum")
public class CurriculumController {

	@Autowired
	private CurriculumService curriculumService;

	@PostMapping("/curriculum")
	public Curriculum addCurriculum(@RequestBody Curriculum curriculum) {
		return curriculumService.addCurriculum(curriculum);
	}
	
	@GetMapping("/curriculum")
	public List<Curriculum> getAllCurriculum() {
		return curriculumService.getAllCurriculum();
	}
	
	@GetMapping("/curriculum/{id}")
	public Curriculum getCurriculumByID(@PathVariable("curriculumId") int curriculumId) {
		return curriculumService.getCurriculumByID(curriculumId);
	}
	
	@PutMapping("/curriculum/{id}")
	public Curriculum updateCurriculumByID(@RequestBody Curriculum curriculum, int curriculumId) {
		curriculumService.updateCurriculumByID(curriculum, curriculumId);
		return curriculum;
	}

}
