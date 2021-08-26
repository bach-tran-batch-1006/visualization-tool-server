package com.revature.app.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.revature.app.dto.CurriculumDto;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.CurriculumNotFoundException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.ForeignKeyConstraintException;
//import com.revature.app.model.Category;
import com.revature.app.model.Curriculum;
import com.revature.app.service.CurriculumService;

@CrossOrigin(origins = "*")
@RestController
public class CurriculumController {
	@Bean
    //@LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Autowired
    private RestTemplate rest;

	@Autowired
	private CurriculumService service;
	
	private static Logger logger = LoggerFactory.getLogger(CurriculumController.class);
	
	String goodLog = "User called the endpoint ";

	@GetMapping(path = "curriculum")
	public Object getAllCurriculum() {
		List<Curriculum> curricula = null;
		curricula = service.getAllCurriculum();
		logger.info("User called the endpoint to get all curricula from the database");
		return curricula;
	}

	@GetMapping(path = "curriculum/{id}")
	public Object getCurriculumById(@PathVariable("id") String curriculumId) {
		Curriculum curriculum = null;
		try {
			curriculum = service.getCurriculumByID(curriculumId);
			String logString = String.format(goodLog, "to get information about a curriculum in the database with id %s");
			logString = String.format(logString, curriculumId);
			logger.info(logString);
			return curriculum;
		} catch (CurriculumNotFoundException e) {
			logger.warn("User requested information about a curriculum in the database that did not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (BadParameterException e) {
			logger.warn("User gave a bad parameter while trying to get information about a curriculum in the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (EmptyParameterException e) {
			logger.warn("User left a parameter blank while trying to get information about a curriculum in the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping(path = "curriculum")
	public Object addCurriculum(@RequestBody CurriculumDto dto) {
		Curriculum curriculum = null;
		try {
			curriculum = service.addCurriculum(dto);
			logger.info("User called the endpoint to add a curriculum to the database");
			return curriculum;
		} catch (EmptyParameterException e) {
			logger.warn("User left a parameter blank while trying to add a curriculum to the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping(path = "curriculum/{id}")
	public Object updateCurriculumById(@PathVariable("id") String curriculumId, @RequestBody CurriculumDto dto) {
		try {
			Curriculum curriculum = service.updateCurriculumByID(curriculumId, dto);
			String logString = String.format(goodLog, "to update a curriculum in the database with id %s");
			logString = String.format(logString, curriculumId);
			logger.info(logString);
			return curriculum;
		} catch (EmptyParameterException e) {
			logger.warn("User left a parameter blank while trying to update a curriculum in the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (CurriculumNotFoundException e) {
			logger.warn("User asked for information about a curriculum in the database that did not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (BadParameterException e) {
			logger.warn("User gave a bad parameter while trying to update a curriculum in the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping(path = "curriculum/{id}")
	public Object deleteCurriculumByID(@PathVariable("id") String curriculumId) {
		Curriculum curriculum = null;
		try {
			curriculum = service.deleteCurriculumByID(curriculumId);
			String logString = String.format(goodLog, "to delete a curriculum from the database with id %s");
			logString = String.format(logString, curriculumId);
			logger.info(logString);
			return curriculum.getCurriculumId();
		} catch (CurriculumNotFoundException e) {
			logger.warn("User attempted to delete a curriculum in the database that did not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (EmptyParameterException e) {
			logger.warn("User left a parameter blank while trying to delete a curriculum from the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (BadParameterException e) {
			logger.warn("User gave a bad parameter while trying to delete a curriculum from the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch(ForeignKeyConstraintException e) {
			logger.warn("User attempted to delete a curriculum from the database but it was blocked because of a foreign key constraint");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@GetMapping(path="curriculum/{id}/categories")
	public Set<Integer> getAllCategoriesById(@PathVariable("id") String curriculumId) {
		Set<Integer> cats = new HashSet<Integer>();
		try {
			String url = "http://localhost:8089/skill/";
			for (int i : service.getCurriculumByID(curriculumId).getSkillList()) {
				ResponseEntity<Integer> res = this.rest.getForEntity((url + i + "/category"), Integer.class);
				cats.add(res.getBody());
			}
		} catch (CurriculumNotFoundException e) {
			logger.warn("User attempted to access a curriculum in the database that did not exist while trying to return categories");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (BadParameterException e) {
			logger.warn("User gave a bad parameter while trying to get information about a curriculum's categories in the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (EmptyParameterException e) {
			logger.warn("User gave a bad parameter while trying to access a curriculum's categories in the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (RestClientException e) {
			logger.warn("Failed to contact request microservice");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		
	return cats;
	}
}