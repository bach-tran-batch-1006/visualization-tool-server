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

import com.revature.app.dto.PrimerDto;
import com.revature.app.exception.BadParameterException;

import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.PrimerNotFoundException;
import com.revature.app.model.Primer;
import com.revature.app.service.PrimerServices;

@CrossOrigin(origins = "*")
@RestController
public class PrimerController {
//	@Bean
//    //@LoadBalanced
//    RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
//    
    @Autowired
    private RestTemplate rest;

	@Autowired
	private PrimerServices service;
	
	private static Logger logger = LoggerFactory.getLogger(PrimerController.class);
	
	String goodLog = "User called the endpoint ";

	@GetMapping(path = "primer")
	public Object getAllPrimers() {
		List<Primer> primer = null;
		primer = service.getAllPrimers();
		logger.info("User called the endpoint to get all primer from the database");
		return primer;
	}

	@GetMapping(path = "primer/{id}")
	public Object getPrimerById(@PathVariable("id") String primerId) {
		Primer primer = null;
		try {
			primer = service.getPrimerByID(primerId);
			String logString = String.format(goodLog, "to get information about a primer in the database with id %s");
			logString = String.format(logString, primerId);
			logger.info(logString);
			return primer;
		} catch (PrimerNotFoundException e) {
			logger.warn("User requested information about a primer in the database that did not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (BadParameterException e) {
			logger.warn("User gave a bad parameter while trying to get information about a primer in the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (EmptyParameterException e) {
			logger.warn("User left a parameter blank while trying to get information about a primer in the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping(path = "primer")
	public Object addPrimer(@RequestBody PrimerDto dto) {
		Primer primer = null;
		try {
			primer = service.addPrimer(dto);
			logger.info("User called the endpoint to add a primer to the database");
			return primer;
		} catch (EmptyParameterException e) {
			logger.warn("User left a parameter blank while trying to add a primer to the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping(path = "primer/{id}")
	public Object updatePrimerById(@PathVariable("id") String primerId, @RequestBody PrimerDto dto) {
		try {
			Primer primer = service.updatePrimerByID(primerId, dto);
			String logString = String.format(goodLog, "to update a primer in the database with id %s");
			logString = String.format(logString, primerId);
			logger.info(logString);
			return primer;
		} catch (EmptyParameterException e) {
			logger.warn("User left a parameter blank while trying to update a primer in the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (PrimerNotFoundException e) {
			logger.warn("User asked for information about a primer in the primer that did not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (BadParameterException e) {
			logger.warn("User gave a bad parameter while trying to update a primer in the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping(path = "primer/{id}")
	public Object deletePrimerByID(@PathVariable("id") String primerId) {
		Primer primer = null;
		try {
			primer = service.deletePrimerByID(primerId);
			String logString = String.format(goodLog, "to delete a primer from the database with id %s");
			logString = String.format(logString, primerId);
			logger.info(logString);
			return primer.getPrimerId();
		} catch (PrimerNotFoundException e) {
			logger.warn("User attempted to delete a primer in the database that did not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (EmptyParameterException e) {
			logger.warn("User left a parameter blank while trying to delete a primer from the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (BadParameterException e) {
			logger.warn("User gave a bad parameter while trying to delete a primer from the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} /*catch(ForeignKeyConstraintException e) {
			logger.warn("User attempted to delete a curriculum from the database but it was blocked because of a foreign key constraint");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} */
	}
	
	@GetMapping(path="primer/{id}/categories")
	public Set<Integer> getAllCategoriesById(@PathVariable("id") String primerId) {
		Set<Integer> cats = new HashSet<Integer>();
		try {
			String url = "http://localhost:8089/skill/";
			for (int i : service.getPrimerByID(primerId).getSkillList()) {
				ResponseEntity<Integer> res = this.rest.getForEntity((url + i + "/category"), Integer.class);
				if(res.getBody()!=null) {
					cats.add(res.getBody());
				}	
			}
			return cats;
		} catch (PrimerNotFoundException e) {
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
	
	}
}
