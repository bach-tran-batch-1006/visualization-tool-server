package com.revature.app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.app.dao.CurriculumDao;
import com.revature.app.dto.CurriculumDto;
import com.revature.app.model.Curriculum;

@Service
@Transactional
public class CurriculumService {

	@Autowired
	CurriculumDao curriculumDao;
	
	@Transactional
	public Curriculum addCurriculum(CurriculumDto curriculumDto) {
		
		Curriculum curriculum = new Curriculum(-1, curriculumDto.getName(), curriculumDto.getSkillList());
		
		curriculumDao.save(curriculum);
		
		if(curriculum.getCurriculumId() == -1) {
			throw new RuntimeException("Couldn't add curriculum into the database");
		}
		return curriculum;

	}

}
