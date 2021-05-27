package com.revature.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.app.dao.SkillDAO;
import com.revature.app.dto.SkillDTO;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.SkillNotAddedException;
import com.revature.app.exception.SkillNotDeletedException;
import com.revature.app.exception.SkillNotFoundException;
import com.revature.app.exception.SkillNotUpdatedException;
import com.revature.app.model.Skill;

@Service
public class SkillService {

	@Autowired
	private SkillDAO skillDAO;

	@Transactional
	public List<Skill> getAllSkills(){
		return skillDAO.findAll();
	}
	
	@Transactional(rollbackOn = {SkillNotFoundException.class})
	public Skill getSkillByID(String skillID) throws BadParameterException, EmptyParameterException, SkillNotFoundException {
		Skill skill = null;
		try {
			if(skillID.trim().equals("")){
				throw new EmptyParameterException("The skill ID was left blank");
			}
			int id = Integer.parseInt(skillID);
			skill = skillDAO.findById(id);
			if(skill == null) {
				throw new SkillNotFoundException("The skill with ID " + skillID + " could not be found.");
			} else {
				return skill;
			}
		} catch (NumberFormatException e) {
			throw new BadParameterException("The skill ID provided must be of type int");
		}
	}
	
	@Transactional(rollbackOn = {SkillNotAddedException.class})
	public Skill addSkill(SkillDTO skillDTO) throws EmptyParameterException, SkillNotAddedException {
		Skill skill = null;
		if(skillDTO.getName().trim().equals("")) {
			throw new EmptyParameterException("The skill name was left blank");
		}
		skill = new Skill(skillDTO);
		skill = skillDAO.save(skill);
		if(skill == null || skill.getSkillId() == 0) {
			throw new SkillNotAddedException("The skill could not be added due to a database issue");
		}
		return skill;
	}

	@Transactional(rollbackOn = {SkillNotUpdatedException.class, SkillNotFoundException.class})
	public Skill updateSkill(String skillID, SkillDTO upSkill) throws EmptyParameterException, SkillNotUpdatedException, BadParameterException, SkillNotFoundException{
		Skill skill = null;
		try {
			if(skillID.trim().equals("")){
				throw new EmptyParameterException("The skill ID was left blank");
			}
			if(upSkill.getName().trim().equals("")){
				throw new EmptyParameterException("The skill name was left blank");
			}
			int id = Integer.parseInt(skillID);
			skill = skillDAO.findById(id);
			if(skill == null) {
				throw new SkillNotFoundException("The skill could not be updated because it couldn't be found");
			} else {
				skill.updateFromDTO(upSkill);
				skill = skillDAO.save(skill);
				if(skill == null) {
					throw new SkillNotUpdatedException("The skill could not be updated due to a database issue");
				}
			}
			return skill;
		} catch (NumberFormatException e) {
			throw new BadParameterException("The skill ID provided must be of type int");
		}
	}

	@Transactional(rollbackOn = {SkillNotDeletedException.class, SkillNotFoundException.class})
	public Skill deleteSkill(String skillID) throws EmptyParameterException, BadParameterException, SkillNotDeletedException, SkillNotFoundException {
		Skill skill = null;
		try {
			if(skillID.trim().equals("")){
				throw new EmptyParameterException("The skill ID was left blank");
			}
			int id = Integer.parseInt(skillID);
			skill = skillDAO.findById(id);
			if(skill == null) {
				throw new SkillNotFoundException("The skill could not be deleted because it couldn't be found");
			} else {
				skillDAO.delete(skill);
				if(skillDAO.findById(id) != null) {
					throw new SkillNotDeletedException("The skill could not be deleted because of a database issue");
				}
			}
			return skill;
		} catch (NumberFormatException e) {
			throw new BadParameterException("The skill ID provided must be of type int");
		}
	}
	
}
