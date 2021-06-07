package com.revature.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.revature.app.dto.VisualizationDTO;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.VisualizationNotFoundException;
import com.revature.app.model.Category;
import com.revature.app.model.Skill;
import com.revature.app.model.Visualization;
import com.revature.app.service.VisualizationService;

@CrossOrigin(origins = "*")
@RestController
public class VisualizationController {

	@Autowired
	private VisualizationService visualizationService;
	
	private static Logger logger = LoggerFactory.getLogger(VisualizationController.class);
	
	String goodLog = "User called the endpoint ";

	@PostMapping(path = "visualization")
	@ResponseStatus(HttpStatus.CREATED)
	public Object createVisualization(@RequestBody VisualizationDTO visualizationdto) {
		try {
			Visualization newVis = visualizationService.createVisualization(visualizationdto);
			logger.info("User called the endpoint to add a visualization to the database");
			return newVis;
		} catch (EmptyParameterException e) {
			logger.warn("User left a parameter blank while trying to add a visualization to the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("visualization/{id}")
	public Object findById(@PathVariable("id") String id) {
		try {
			Visualization vis = visualizationService.findVisualizationByID(id);
			String logString = String.format(goodLog, "to get information about a visualization in the database with id %s");
			logString = String.format(logString, id);
			logger.info(logString);
			return vis;
		} catch (VisualizationNotFoundException e) {
			logger.warn("User requested information about a visualization in the database that did not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (EmptyParameterException e) {
			logger.warn("User left a parameter blank while trying to get information about a visualization in the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (BadParameterException e) {
			logger.warn("User gave a bad parameter while trying to get information about a visualization in the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("visualization")
	public List<Visualization> findAll()  {
		logger.info("User called the endpoint to get all visualizations from the database");
		return this.visualizationService.findAllVisualization();
	}

	
	@PutMapping("visualization/{id}")
	public Object updateVisualization(@PathVariable("id") String id, @RequestBody VisualizationDTO visualizationdto) {
		try {
			Visualization updatedVis = visualizationService.updateVisualizationByID(id, visualizationdto);
			String logString = String.format(goodLog, "to update a visualization in the database with id %s");
			logString = String.format(logString, id);
			logger.info(logString);
			return updatedVis;
		} catch (VisualizationNotFoundException e) {
			logger.warn("User asked for information about a visualization in the database that did not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (BadParameterException e) {
			logger.warn("User gave a bad parameter while trying to update a visualization in the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (EmptyParameterException e) {
			logger.warn("User left a parameter blank while trying to update a visualization in the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping("visualization/{id}")
	public Object deleteVisualization(@PathVariable("id") String id) {
		try {
			Integer deletedID = visualizationService.deleteVisualizationByID(id);
			String logString = String.format(goodLog, "to delete a visualization from the database with id %s");
			logString = String.format(logString, id);
			logger.info(logString);
			return deletedID;
		} catch (VisualizationNotFoundException e) {
			logger.warn("User attempted to delete a visualization in the database that did not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (BadParameterException e) {
			logger.warn("User gave a bad parameter while trying to delete a visualization from the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (EmptyParameterException e) {
			logger.warn("User left a parameter blank while trying to delete a visualization from the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@GetMapping("visualization/{id}/skills")
	public Object getAllUniqueSkillsByVisualization(@PathVariable("id") String id){
		try {
			List<Skill> skillList = visualizationService.getAllSkillsByVisualization(id);
			String logString = String.format(goodLog, "to get all unique skills in a visualization from the database with id %s");
			logString = String.format(logString, id);
			logger.info(logString);
			return skillList;
		} catch (VisualizationNotFoundException e) {
			logger.warn("User attempted to get all the skills by Visualization for a visualization that didn't exist in the database");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (BadParameterException e) {
			logger.warn("User gave a bad parameter while trying to get all the skills by Visualization");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (EmptyParameterException e) {
			logger.warn("User left a parameter blank while trying to get all the skills by Visualization");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@GetMapping("visualization/{id}/categories")
	public Object getAllUniqueCategoriesByVisualization(@PathVariable("id") String id){
		try {
			List<Category> catList = visualizationService.getAllCategoriesByVisualization(id);
			String logString = String.format(goodLog, "to get all unique categories in a visualization from the database with id %s");
			logString = String.format(logString, id);
			logger.info(logString);
			return catList;
		} catch (VisualizationNotFoundException e) {
			logger.warn("User attempted to get all the categories by Visualization for a visualization that didn't exist in the database");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (BadParameterException e) {
			logger.warn("User gave a bad parameter while trying to get all the categories by Visualization");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (EmptyParameterException e) {
			logger.warn("User left a parameter blank while trying to get all the categories by Visualization");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

}
