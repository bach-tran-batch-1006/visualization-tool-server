package com.revature.app.service;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.app.dao.CurriculumDao;
import com.revature.app.dao.ProjectDao;
import com.revature.app.dto.ProjectDto;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.EmptyCurriculumException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.ProjectNotAddedException;
import com.revature.app.exception.ProjectNotFoundException;
import com.revature.app.model.Curriculum;
import com.revature.app.model.Projects;


@Service
public class ProjectsService {

	String badParam = "The Project ID provided must be of type int";
	String emptyParam = "The Project ID was left blank";
	String emptyName = "The Projects name was left blank";
	
	@Autowired
	private ProjectDao pDao;
	
	@Autowired
	private CurriculumDao cDao;
	
	
	@Transactional(rollbackOn = { ProjectNotAddedException.class })
	public Projects addProject(ProjectDto projectDto) throws EmptyParameterException {
		Projects project = new Projects(projectDto.getName(), projectDto.getSkillList());
		if (projectDto.getName().trim().equals("")) {
			throw new EmptyParameterException(emptyName);
		}
		project = pDao.save(project);
		return project;
	}

	@Transactional(rollbackOn = {ProjectNotFoundException.class})
	public Projects getProjectByID(String projId) throws ProjectNotFoundException, BadParameterException, EmptyParameterException {
		Projects project = null;
		try {
			if(projId.trim().equals("")){
				throw new EmptyParameterException(emptyParam);
			}
			int id = Integer.parseInt(projId);
			project = pDao.findById(id);
			if(project == null) {
				throw new ProjectNotFoundException("The project with ID " + projId + " could not be found.");
			} else {
				return project;
			}
		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		}
	}

	@Transactional(rollbackOn = {EmptyCurriculumException.class})
	public List<Projects> getAllProjects() {
		List<Projects> projects;
		projects = pDao.findAll();
		return projects;
	}

	@Transactional
	public Projects updateProjectByID(String projId, ProjectDto projectDto)
			throws EmptyParameterException, ProjectNotFoundException, BadParameterException {
		Projects project = null;
		try {
			if (projId.trim().equals("")) {
				throw new EmptyParameterException(emptyParam);
			}
			if (projectDto.getName().trim().equals("")) {
				throw new EmptyParameterException("The project name was left blank");
			}
			int id = Integer.parseInt(projId);
			project = pDao.findById(id);
			if (project == null) {
				throw new ProjectNotFoundException("The Project could not be updated because it couldn't be found");
			} else {
				project.setProjectName(projectDto.getName());
				project.setSkillList(projectDto.getSkillList());
				project = pDao.save(project);
			}
			return project;
		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		}
	}
	//deletes the project and sets associated project in curricula to 0
	@Transactional
	public Projects deleteProjectByID(String projID) throws ProjectNotFoundException, BadParameterException, EmptyParameterException {
		try {
			if(projID.trim().equals("")){
				throw new EmptyParameterException(emptyParam);
			}
			int id = Integer.parseInt(projID);
			Projects project = pDao.findById(id);
			if (project == null) {
				throw new ProjectNotFoundException("The Project could not be deleted because it couldn't be found");
			}else {
				List<Curriculum> curricula = cDao.findAll();
				for(Curriculum c : curricula) {
					if(c.getP1() == id) {
						c.setP1(0);
						cDao.save(c);
					} 
					if (c.getP2() == id) {
						c.setP2(0);
						cDao.save(c);
					} 
					if(c.getP3()==id) {
						c.setP3(0);
						cDao.save(c);
					}
				}
			}
			pDao.deleteById(id);
			return project;
		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		}
	}
}
	