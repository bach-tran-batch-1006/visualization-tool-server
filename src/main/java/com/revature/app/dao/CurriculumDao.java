package com.revature.app.dao;


import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.app.model.Category;
import com.revature.app.model.Curriculum;


@Repository
public interface CurriculumDao extends JpaRepository<Curriculum, Integer>{	
	

	public Curriculum findByCurriculumId(int curriculumId);

	public List<Curriculum> findAll();
	
	@Query(value = "SELECT distinct a FROM Curriculum c "
			+ "JOIN c.skillList s "
			+ "JOIN s.category a "
			+ "WHERE c.curriculumId = ?1 "
			+ "ORDER BY a.categoryName ")
	public List<Category> catCurList(int curriculumId);

}