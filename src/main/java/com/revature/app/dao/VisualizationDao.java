package com.revature.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.app.model.Skill;
import com.revature.app.model.Visualization;

@Repository
public interface VisualizationDao extends JpaRepository<Visualization, Integer> {

	public List<Visualization> findAll();
	
	public Visualization findById(int visualizationId);
	
	@Query(value = "SELECT distinct s FROM Visualization v "
			+ "JOIN v.curriculumList c "
			+ "JOIN c.skillList s "
			+ "WHERE v.visualizationId = ?1 "
			+ "ORDER BY s.skillName ")
	public List<Skill> skillVisList(int visualizationId);
}
