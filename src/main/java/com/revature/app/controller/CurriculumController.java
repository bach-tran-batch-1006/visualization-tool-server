package com.revature.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.revature.app.dto.CurriculumDto;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.CurriculumNotAddedException;
import com.revature.app.exception.CurriculumNotFoundException;
import com.revature.app.exception.EmptyCurriculumException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.ForeignKeyConstraintException;
import com.revature.app.model.Category;
import com.revature.app.model.Curriculum;
import com.revature.app.service.CurriculumService;

@CrossOrigin(origins = "*")
@RestController
public class CurriculumController {

	@Autowired
	private CurriculumService service;

	@GetMapping(path = "curriculum")
	public Object getAllCurriculum() {
		List<Curriculum> curricula = null;
		curricula = service.getAllCurriculum();
		return curricula;
	}

	@GetMapping(path = "curriculum/{id}")
	public Object getCurriculumById(@PathVariable("id") String curriculumId) {
		Curriculum curriculum = null;
		try {
			curriculum = service.getCurriculumByID(curriculumId);
			return curriculum;
		} catch (CurriculumNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (BadParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (EmptyParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
			
	}

	@PostMapping(path = "curriculum")
	public Object addCurriculum(@RequestBody CurriculumDto dto) {
		Curriculum curriculum = null;
		try {
			curriculum = service.addCurriculum(dto);
			return curriculum;
		} catch (CurriculumNotAddedException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (EmptyParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping(path = "curriculum/{id}")
	public Object updateCurriculumById(@PathVariable("id") String curriculumId, @RequestBody CurriculumDto dto) {
		return service.updateCurriculumByID(Integer.parseInt(curriculumId), dto);
	}

	@DeleteMapping(path = "curriculum/{id}")
	public Object deleteCurriculumByID(@PathVariable("id") String curriculumId) {
		Curriculum curriculum = null;
		try {
			curriculum = service.deleteCurriculumByID(curriculumId);
			return curriculum;
		} catch (CurriculumNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (EmptyParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (BadParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch(ForeignKeyConstraintException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@GetMapping(path="curriculum/{id}/category")
	public Object getAllCategoriesById(@PathVariable("id") String curriculumId) {
		List<Category> catList;
		try {
			catList = service.getAllCategoriesByCurriculum(curriculumId);
			return catList;
		} catch (EmptyParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (BadParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (CurriculumNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
		
		
	}

}