package com.revature.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.app.model.Category;
import com.revature.app.model.Skill;


@Repository
//@Transactional
public interface CategoryDAO extends JpaRepository<Category, Integer>{
	
	public List<Category> findAll();
    
	public Category findById(int categoryId);
}
