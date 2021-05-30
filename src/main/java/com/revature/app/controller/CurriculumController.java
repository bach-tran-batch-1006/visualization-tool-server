package com.revature.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.app.dto.CurriculumDto;
import com.revature.app.exception.CurriculumNotFoundException;
import com.revature.app.exception.EmptyCurriculumException;
import com.revature.app.model.Curriculum;
import com.revature.app.service.CurriculumService;

@RestController
public class CurriculumController {

	@Autowired
	private CurriculumService service;

	@GetMapping(path = "curriculum")
	public Object getAllCurriculum() throws EmptyCurriculumException {
		List<Curriculum> curricula;

		curricula = service.getAllCurriculum();
		if (curricula.isEmpty()) {
			return ResponseEntity.status(400).body(curricula);
		}

		return ResponseEntity.status(200).body(curricula);
	}

	@GetMapping(path = "curriculum/{id}")
	public Object getCurriculumById(@PathVariable("id") String curriculumId) {
		try {
			return service.getCurriculumByID(Integer.parseInt(curriculumId));
		} catch (NumberFormatException | CurriculumNotFoundException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}
	}

	@PostMapping(path = "curriculum")
	public Object addCurriculum(@RequestBody CurriculumDto dto) {
		try {
			return service.addCurriculum(dto);
		} catch (Exception e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@PutMapping(path = "curriculum/{id}")
	public Object updateCurriculumById(@PathVariable("id") String curriculumId, @RequestBody CurriculumDto dto) {
		return service.updateCurriculumByID(Integer.parseInt(curriculumId), dto);
	}

	@DeleteMapping(path = "curriculum/{id}")
	public Object deleteCurriculumByID(@PathVariable("id") String curriculumId) throws CurriculumNotFoundException {

		Curriculum curriculum = service.deleteCurriculumByID(Integer.parseInt(curriculumId));
		return ResponseEntity.status(200).body(curriculum);

	}

}