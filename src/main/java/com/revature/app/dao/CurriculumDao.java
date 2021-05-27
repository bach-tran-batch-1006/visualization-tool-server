package com.revature.app.dao;


import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.app.model.Curriculum;


@Repository
public interface CurriculumDao extends JpaRepository<Curriculum, Integer>{	
	

	public Curriculum findByCurriculumId(int curriculumId);

	public List<Curriculum> findAll();


}