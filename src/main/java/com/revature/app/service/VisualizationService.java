package com.revature.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.app.dao.VisualizationDao;
import com.revature.app.dto.VisualizationDTO;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.VisualizationNotFoundException;
import com.revature.app.model.Category;
import com.revature.app.model.Skill;
import com.revature.app.model.Visualization;

@Service
public class VisualizationService {
	
	String badParam = "The visualization ID provided must be of type int";
	String emptyParam = "The visualization ID was left blank";
	String emptyName = "The visualization name was left blank";

	@Autowired
	private VisualizationDao visualizationDao;

	@Transactional
	public Visualization createVisualization(VisualizationDTO visualizationDto) throws EmptyParameterException {
		Visualization visualization = new Visualization();
		if (visualizationDto.getTitle().trim().equals("")) {
			throw new EmptyParameterException(emptyName);
		}
		visualization.setVisualizationId(0);
		visualization.setCurriculumList(visualizationDto.getCurricula());
		visualization.setVisualizationName(visualizationDto.getTitle());
		visualization = visualizationDao.save(visualization);
		return visualization;
	}

	@Transactional(rollbackOn = {VisualizationNotFoundException.class})
	public Visualization findVisualizationByID(String visId) throws VisualizationNotFoundException, EmptyParameterException, BadParameterException {
		try {
			if(visId.trim().equals("")){
				throw new EmptyParameterException(emptyParam);
			}
			int id = Integer.parseInt(visId);
			Visualization vis = visualizationDao.findById(id);
			if (vis == null) {
				throw new VisualizationNotFoundException("Visualization not found");
			}
			return vis;
		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		}
	}

	@Transactional
	public Visualization updateVisualizationByID(String visID, VisualizationDTO visualizationDto) throws VisualizationNotFoundException, BadParameterException, EmptyParameterException {
		try {
			if(visID.trim().equals("")){
				throw new EmptyParameterException(emptyParam);
			}
			if(visualizationDto.getTitle().trim().equals("")){
				throw new EmptyParameterException(emptyName);
			}
			int id = Integer.parseInt(visID);
			Visualization vis = visualizationDao.findById(id);
			if (vis == null) {
				throw new VisualizationNotFoundException("Visualization not found");
			} else {
				vis.setCurriculumList(visualizationDto.getCurricula());
				vis.setVisualizationName(visualizationDto.getTitle());
				vis = visualizationDao.save(vis);
			}
			return vis;		
		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		}
	}

	@Transactional
	public int deleteVisualizationByID(String visID) throws VisualizationNotFoundException, BadParameterException, EmptyParameterException {
		try {
			if(visID.trim().equals("")){
				throw new EmptyParameterException(emptyParam);
			}
			int id = Integer.parseInt(visID);
			Visualization vis = visualizationDao.findById(id);
			if (vis == null) {
				throw new VisualizationNotFoundException("Visualization not found");
			}
			visualizationDao.deleteById(id);
			return id;
		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		}
	}

	
	public List<Visualization> findAllVisualization() {
		return visualizationDao.findAll();
	}

	
	@Transactional(rollbackOn = {VisualizationNotFoundException.class})
	public List<Skill> getAllSkillsByVisualization(String visID) throws EmptyParameterException, BadParameterException, VisualizationNotFoundException {
		try {
			if(visID.trim().equals("")){
				throw new EmptyParameterException(emptyParam);
			}
			int id = Integer.parseInt(visID);
			Visualization vis = visualizationDao.findById(id);
			if (vis == null) {
				throw new VisualizationNotFoundException("Visualization not found");
			}
			//The above code is just a sanity check to make sure that the visualization exists before getting
			//the skills by the visualization 
			
			//Now it runs the query of the database to get all the skills
			List<Skill> skillList = visualizationDao.skillVisList(id);
			return skillList;
		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		}
	}
	
	@Transactional(rollbackOn = {VisualizationNotFoundException.class})
	public List<Category> getAllCategoriesByVisualization(String visID) throws EmptyParameterException, BadParameterException, VisualizationNotFoundException {
		try {
			if(visID.trim().equals("")){
				throw new EmptyParameterException(emptyParam);
			}
			int id = Integer.parseInt(visID);
			Visualization vis = visualizationDao.findById(id);
			if (vis == null) {
				throw new VisualizationNotFoundException("Visualization not found");
			}
			//The above code is just a sanity check to make sure that the visualization exists before getting
			//the skills by the visualization 
			
			//Now it runs the query of the database to get all the skills
			List<Category> catList = visualizationDao.catVisList(id);
			return catList;
		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		}
	}

}
