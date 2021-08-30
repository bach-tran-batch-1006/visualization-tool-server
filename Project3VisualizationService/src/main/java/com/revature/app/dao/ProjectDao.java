package com.revature.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.app.model.Projects;

@Repository
public interface ProjectDao extends JpaRepository<Projects,Integer> {

	public List<Projects> findAll();
	public Projects findById(int id);
	
	
	
}
