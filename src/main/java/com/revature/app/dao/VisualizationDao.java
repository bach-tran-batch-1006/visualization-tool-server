package com.revature.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.app.model.Visualization;

@Repository
public interface VisualizationDao extends JpaRepository<Visualization, Integer> {

}