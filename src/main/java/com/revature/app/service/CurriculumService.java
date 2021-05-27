package com.revature.app.service;


import java.util.ArrayList;

import javax.persistence.NoResultException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.app.dao.CurriculumDao;
import com.revature.app.dto.CurriculumDto;
import com.revature.app.exception.CurriculumNotFoundException;
import com.revature.app.exception.EmptyCurriculumException;
import com.revature.app.model.Curriculum;

import lombok.Getter;
import lombok.Setter;

@Service
public class CurriculumService {

	@Autowired
	@Setter
	@Getter
	private CurriculumDao curriculumDao;

	public Curriculum addCurriculum(CurriculumDto curriculumDto) {
		Curriculum curriculum = new Curriculum(0, curriculumDto.getName(), curriculumDto.getSkillList());

		curriculum = curriculumDao.save(curriculum);

		if (curriculum.getCurriculumId() == 0) {
			throw new RuntimeException("Couldn't add curriculum into the database");
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

	public ArrayList<Curriculum> getAllCurriculum() throws EmptyCurriculumException {

		ArrayList<Curriculum> curricula = new ArrayList<Curriculum>();

		curricula = (ArrayList<Curriculum>) curriculumDao.findAll();

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
