package com.revature.app.service;


import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.app.dao.CurriculumDao;
import com.revature.app.dto.CurriculumDto;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.CurriculumNotAddedException;
import com.revature.app.exception.CurriculumNotFoundException;
import com.revature.app.exception.EmptyCurriculumException;
import com.revature.app.model.Curriculum;

@Service
public class CurriculumService {

	@Autowired
	private CurriculumDao curriculumDao;

	public Curriculum addCurriculum(CurriculumDto curriculumDto) throws BadParameterException, CurriculumNotAddedException {
		Curriculum curriculum = new Curriculum(0, curriculumDto.getName(), curriculumDto.getSkillList());
		
		if(curriculumDto.getName().trim().equals("")) {
			throw new BadParameterException("Curriculum can not be blank.");
		}
		
		curriculum = curriculumDao.save(curriculum);

		if (curriculum == null || curriculum.getCurriculumId() == 0) {
			throw new CurriculumNotAddedException("Couldn't add curriculum into the database.");
		}
		return curriculum;
	}

	public Curriculum getCurriculumByID(int i) throws CurriculumNotFoundException {
		try {
			return curriculumDao.findByCurriculumId(i);
		} catch (NoResultException e) {
			throw new CurriculumNotFoundException("Curriculum not found");
		}
	}

	public List<Curriculum> getAllCurriculum() throws EmptyCurriculumException {

		List<Curriculum> curricula;

		curricula = curriculumDao.findAll();

		if (curricula.isEmpty()) {
			throw new EmptyCurriculumException("No Curriculum found");
		}
		return curricula;
	}

	public Curriculum updateCurriculumByID(int i, CurriculumDto curriculumDto) {
	  
	  Curriculum curriculumToUpdate = curriculumDao.findByCurriculumId(i);
	  
	  curriculumToUpdate.setCurriculumName(curriculumDto.getName());
	  curriculumToUpdate.setSkillList(curriculumDto.getSkillList());
	 
	  return curriculumDao.save(curriculumToUpdate);
	 }

	public void deleteCurriculumByID(int i) {
		
		curriculumDao.deleteById(1);
	}
}
