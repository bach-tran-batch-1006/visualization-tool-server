package com.revature.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.app.dto.Visualization.VisualizationDTO;
import com.revature.app.expections.VisualizationNotFound;
import com.revature.app.model.Visualization;
import com.revature.app.service.VisualizationService;

@RestController
public class VisualizationController {

	@Autowired
	VisualizationService visualizationservice;

	@PostMapping(path = "visualization")
	public Visualization createVisualization(@RequestBody VisualizationDTO visualizationdto) {
		return this.visualizationservice.createVisualization(visualizationdto);
	}

	@GetMapping("visualization/{id}")
	public Visualization findbyid(@PathVariable("id") String ID) throws VisualizationNotFound {

		Integer id = Integer.parseInt(ID);

		Visualization result = this.visualizationservice.findVisualizationByID(id);
		return result;

	}
	
	@GetMapping("visualization")
	public ArrayList<Visualization> findAll() throws VisualizationNotFound {

				return this.visualizationservice.FindAllVisualization();
		

	}

	@GetMapping("visualization/name/{name}")
	public Visualization findbyname(@PathVariable("name") String Name) throws VisualizationNotFound {
		return this.visualizationservice.findByName(Name);
	}

	@PutMapping("visualization/{id}")
	public Visualization put(@PathVariable("id") String ID, @RequestBody VisualizationDTO visualizationdto)
			throws VisualizationNotFound {

		Integer id = Integer.parseInt(ID);

		return this.visualizationservice.UpdateVisualizationByID(id, visualizationdto);

	}

	@DeleteMapping("visualization/{id}")
	public int delete(@PathVariable("id") String ID) throws VisualizationNotFound {
		Integer id = Integer.parseInt(ID);
		return this.visualizationservice.deleteVisualizationByID(id);
	}

}
