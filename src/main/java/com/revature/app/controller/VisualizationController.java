package com.revature.app.controller;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.revature.app.dto.MessageDTO;
import com.revature.app.dto.VisualizationDTO;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.VisualizationNotFoundException;
import com.revature.app.model.Skill;
import com.revature.app.model.Visualization;
import com.revature.app.service.VisualizationService;

@CrossOrigin(origins = "*")
@RestController
public class VisualizationController {

	@Autowired
	private VisualizationService visualizationService;

	@PostMapping(path = "visualization")
	@ResponseStatus(HttpStatus.CREATED)
	public Object createVisualization(@RequestBody VisualizationDTO visualizationdto) {
		try {
			Visualization newVis = visualizationService.createVisualization(visualizationdto);
			return newVis;
		} catch (EmptyParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("visualization/{id}")
	public Object findById(@PathVariable("id") String id) {

		try {
			return visualizationService.findVisualizationByID(id);
		} catch (VisualizationNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (EmptyParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (BadParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("visualization")
	public List<Visualization> findAll()  {
		return this.visualizationService.findAllVisualization();
	}

	
	@PutMapping("visualization/{id}")
	public Object updateVisualization(@PathVariable("id") String id, @RequestBody VisualizationDTO visualizationdto) {

		try {
			Visualization updatedVis = visualizationService.updateVisualizationByID(id, visualizationdto);
			return updatedVis;
		} catch (VisualizationNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (BadParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (EmptyParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping("visualization/{id}")
	public Object deleteVisualization(@PathVariable("id") String id) {
		try {
			Integer deletedID = visualizationService.deleteVisualizationByID(id);
			return deletedID;
		} catch (VisualizationNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (BadParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (EmptyParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@GetMapping("visualization/{id}/skills")
	public Object getAllUniqueSkillsByVisualization(@PathVariable("id") String id){
		try {
			List<Skill> skillList = visualizationService.getAllSkillsByVisualization(id);
			return skillList;
		} catch (VisualizationNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (BadParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (EmptyParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

}
