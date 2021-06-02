package com.revature.app.dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.app.model.Skill;

@Repository
public interface SkillDAO extends JpaRepository<Skill, Integer>{

	public List<Skill> findAll();
	
	public Skill findById(int visualizationId);
	
	
	
	
}
