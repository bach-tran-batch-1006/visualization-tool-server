package com.revature.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.app.model.Visualization;

@Repository
public interface VisualizationDao extends JpaRepository<Visualization, Integer> {
	
	
	//READ
	public Visualization getVisualizationByID(int visualizationID);
	
	public List<Visualization> findAll();
	
	//UPDATE
	public Visualization updateVisualization(int visualizationID, String payload);
	
	//DELETE
	public boolean deleteVisualization(int visualizationID);
}