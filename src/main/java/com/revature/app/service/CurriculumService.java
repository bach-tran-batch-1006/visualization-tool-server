package com.revature.app.service;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.app.dao.CurriculumDao;
import com.revature.app.dto.CurriculumDto;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.CurriculumNotAddedException;
import com.revature.app.exception.CurriculumNotFoundException;
import com.revature.app.exception.EmptyCurriculumException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.ForeignKeyConstraintException;
import com.revature.app.model.Category;
import com.revature.app.model.Curriculum;

@Service
public class CurriculumService {

	String badParam = "The curriculum ID provided must be of type int";
	String emptyParam = "The curriculum ID was left blank";
	String emptyName = "The curriculum name was left blank";
	
	@Autowired
	private CurriculumDao curriculumDao;

	@Transactional(rollbackOn = {CurriculumNotAddedException.class})
	public Curriculum addCurriculum(CurriculumDto curriculumDto) throws EmptyParameterException {
		Curriculum curriculum = new Curriculum(0, curriculumDto.getName(), curriculumDto.getSkillList());
		if(curriculumDto.getName().trim().equals("")) {
			throw new EmptyParameterException(emptyName);
		}
		curriculum = curriculumDao.save(curriculum);
		return curriculum;
	}

	@Transactional(rollbackOn = {CurriculumNotFoundException.class})
	public Curriculum getCurriculumByID(String curId) throws CurriculumNotFoundException, BadParameterException, EmptyParameterException {
		Curriculum curriculum = null;
		try {
			if(curId.trim().equals("")){
				throw new EmptyParameterException(emptyParam);
			}
			int id = Integer.parseInt(curId);
			curriculum = curriculumDao.findByCurriculumId(id);
			if(curriculum == null) {
				throw new CurriculumNotFoundException("The curriculum with ID " + curId + " could not be found.");
			} else {
				return curriculum;
			}
		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		}
	}

	@Transactional(rollbackOn = {EmptyCurriculumException.class})
	public List<Curriculum> getAllCurriculum() {
		List<Curriculum> curricula;
		curricula = curriculumDao.findAll();
		return curricula;
	}

	@Transactional
	public Curriculum updateCurriculumByID(String curId, CurriculumDto curriculumDto) throws EmptyParameterException, CurriculumNotFoundException, BadParameterException {
		Curriculum curriculum = null;
		try {
			if(curId.trim().equals("")){
				throw new EmptyParameterException(emptyParam);
			}
			if(curriculumDto.getName().trim().equals("")){
				throw new EmptyParameterException("The curriculum name was left blank");
			}
			int id = Integer.parseInt(curId);
			curriculum = curriculumDao.findByCurriculumId(id);
			if(curriculum == null) {
				throw new CurriculumNotFoundException("The category could not be updated because it couldn't be found");
			} else {
				curriculum.setCurriculumName(curriculumDto.getName());
				curriculum.setSkillList(curriculumDto.getSkillList());
				curriculum = curriculumDao.save(curriculum);
			}
			return curriculum;
		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		}
	}

	@Transactional(rollbackOn = {CurriculumNotFoundException.class, ForeignKeyConstraintException.class})
	public Curriculum deleteCurriculumByID(String curId) throws CurriculumNotFoundException, EmptyParameterException, BadParameterException, ForeignKeyConstraintException {
		Curriculum curriculum = null;
		try {
			if(curId.trim().equals("")){
				throw new EmptyParameterException(emptyParam);
			}
			int id = Integer.parseInt(curId);
			curriculum = curriculumDao.findByCurriculumId(id);
			if(curriculum == null) {
				throw new CurriculumNotFoundException("The curriculum could not be deleted because it couldn't be found");
			} else {
				curriculumDao.delete(curriculum);
			}
			return curriculum;
		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			throw new ForeignKeyConstraintException("Please remove this curriculum from all visualizations before attempting to delete this curriculum");
		}
	}
	
	@Transactional(rollbackOn = {CurriculumNotFoundException.class})
	public List<Category> getAllCategoriesByCurriculum(String curID) throws EmptyParameterException, BadParameterException, CurriculumNotFoundException {
		try {
			if(curID.trim().equals("")){
				throw new EmptyParameterException(emptyParam);
			}
			int id = Integer.parseInt(curID);
			Curriculum cur = curriculumDao.findByCurriculumId(id);
			if (cur == null) {
				throw new CurriculumNotFoundException("Curriculum not found");
			}
			//The above code is just a sanity check to make sure that the visualization exists before getting
			//the skills by the visualization 
			
			//Now it runs the query of the database to get all the skills
			List<Category> catList = curriculumDao.catCurList(id);
			return catList;
		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		}
	}
	
}
