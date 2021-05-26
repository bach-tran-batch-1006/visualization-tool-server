package com.revature.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.app.model.Category;

//@NoArgsConstructor @AllArgsConstructor
@Repository
//@Transactional
public interface CategoryDAO extends JpaRepository<Category, Integer>{
	
}
