package com.revature.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.app.dto.VisualizationDTO;
import com.revature.app.exception.VisualizationNotFoundException;
import com.revature.app.model.Visualization;
import com.revature.app.service.VisualizationService;

@RestController
public class VisualizationController {

	@Autowired
	private VisualizationService visualizationService;

	@PostMapping(path = "visualization")
	public Visualization createVisualization(@RequestBody VisualizationDTO visualizationdto) {
		
		return this.visualizationService.createVisualization(visualizationdto);
	}

	@GetMapping("visualization/{id}")
	public Visualization findById(@PathVariable("id") int id) throws VisualizationNotFoundException {

		return visualizationService.findVisualizationByID(id);

	}

	@GetMapping("visualization")
	public List<Visualization> findAll()  {

		return this.visualizationService.findAllVisualization();

	}

	@GetMapping("visualization/name/{name}")
	public List<Visualization> findByName(@PathVariable("name") String visName) throws VisualizationNotFoundException {
		
		return this.visualizationService.findByName(visName);
	}

	@PutMapping("visualization/{id}")
	public Visualization updateVisualization(@PathVariable("id") int id, @RequestBody VisualizationDTO visualizationdto) throws VisualizationNotFoundException {

		return this.visualizationService.updateVisualizationByID(id, visualizationdto);

	}

	@DeleteMapping("visualization/{id}")
	public int deleteVisualization(@PathVariable("id") int id) throws VisualizationNotFoundException {
		
		return this.visualizationService.deleteVisualizationByID(id);
		
	}

}
