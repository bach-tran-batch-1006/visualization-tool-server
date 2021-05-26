package com.revature.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.app.dao.CurriculumDao;
import com.revature.app.dto.CurriculumDto;
import com.revature.app.model.Curriculum;

@Service
public class CurriculumService {
	
	@Autowired
	private CurriculumDao curriculumDao;

	public Curriculum addCurriculum(CurriculumDto curriculumDto) {
		Curriculum curriculum = new Curriculum(0, curriculumDto.getName(), curriculumDto.getSkillList());

		curriculum = curriculumDao.save(curriculum);

		if(curriculum.getCurriculumId() == 0) {
			throw new RuntimeException("Couldn't add curriculum into the database");
		}
		return curriculum;
	}

}
