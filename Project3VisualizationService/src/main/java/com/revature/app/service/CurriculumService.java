package com.revature.app.service;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.revature.app.dao.CurriculumDao;
import com.revature.app.dao.VisualizationDao;
import com.revature.app.dto.CurriculumDto;
//import com.revature.app.dto.VisualizationDTO;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.CurriculumNotAddedException;
import com.revature.app.exception.CurriculumNotFoundException;
import com.revature.app.exception.EmptyCurriculumException;
import com.revature.app.exception.EmptyParameterException;
//import com.revature.app.exception.ForeignKeyConstraintException;
//import com.revature.app.exception.VisualizationNotFoundException;
import com.revature.app.model.Curriculum;
import com.revature.app.model.Visualization;

@Service
public class CurriculumService {

	String badParam = "The curriculum ID provided must be of type int";
	String emptyParam = "The curriculum ID was left blank";
	String emptyName = "The curriculum name was left blank";
	
	@Autowired
	private CurriculumDao curriculumDao;
	
	@Autowired
	private VisualizationDao vDao;

	
	@Transactional(rollbackOn = { CurriculumNotAddedException.class })
	public Curriculum addCurriculum(CurriculumDto curriculumDto) throws EmptyParameterException {
		Curriculum curriculum = new Curriculum(curriculumDto.getName(), curriculumDto.getSkillList());
		if (curriculumDto.getName().trim().equals("")) {
			throw new EmptyParameterException(emptyName);
		}
		curriculum.setP1(curriculumDto.getP1());
		curriculum.setP2(curriculumDto.getP2());
		curriculum.setP3(curriculumDto.getP3());

//		if(curriculumDto.getP1()!=0) {
//			curriculum.setP1(curriculumDto.getP1());
//		}
//		if(curriculumDto.getP2()!=0) {
//			curriculum.setP2(curriculumDto.getP2());
//		}
//		if(curriculumDto.getP3() != 0) {
//			curriculum.setP3(curriculumDto.getP3());
//		}
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
	public Curriculum updateCurriculumByID(String curId, CurriculumDto curriculumDto)
			throws EmptyParameterException, CurriculumNotFoundException, BadParameterException {
		Curriculum curriculum = null;
		try {
			if (curId.trim().equals("")) {
				throw new EmptyParameterException(emptyParam);
			}
			if (curriculumDto.getName().trim().equals("")) {
				throw new EmptyParameterException("The curriculum name was left blank");
			}
			int id = Integer.parseInt(curId);
			curriculum = curriculumDao.findByCurriculumId(id);
			if (curriculum == null) {
				throw new CurriculumNotFoundException("The category could not be updated because it couldn't be found");
			} else {
				curriculum.setCurriculumName(curriculumDto.getName());
				curriculum.setSkillList(curriculumDto.getSkillList());
				curriculum.setP1(curriculumDto.getP1());
				curriculum.setP2(curriculumDto.getP2());
				curriculum.setP3(curriculumDto.getP3());

				curriculum = curriculumDao.save(curriculum);
			}
			return curriculum;
		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		}
	}

	@Transactional(rollbackOn = {CurriculumNotFoundException.class})
	public Curriculum deleteCurriculumByID(String curId) throws CurriculumNotFoundException, EmptyParameterException, BadParameterException {
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
				List<Visualization> vList = vDao.findAll();
				for(Visualization v : vList) {
					if(v.getCurriculumList().contains(id)) {
						List<Integer> newCurric = v.getCurriculumList();
						newCurric.remove(id);
						v.setCurriculumList(newCurric);
						vDao.save(v);
					}
				}
				curriculumDao.deleteById(curriculum.getCurriculumId());
				curriculumDao.flush();
			}
			return curriculum;
		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		}
//		} catch (DataIntegrityViolationException e) {
//			throw new ForeignKeyConstraintException("Please remove this curriculum from all visualizations before attempting to delete this curriculum");
//		}
	}
	
	//dependency on categories 
	
	/*
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
			return curriculumDao.catCurList(id);
		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		}
	} */
	
}