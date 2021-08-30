package com.revature.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.revature.app.dao.SkillDAO;
import com.revature.app.dto.SkillDTO;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.ForeignKeyConstraintException;
import com.revature.app.exception.SkillNotFoundException;
import com.revature.app.model.Category;
import com.revature.app.model.Skill;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Service

@NoArgsConstructor
@AllArgsConstructor(onConstructor= @__(@Autowired))
public class SkillService {


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
	
	
	

	
	
	@Transactional
	public Skill addSkill(SkillDTO skillDTO) throws EmptyParameterException {
		Skill skill = null;
		if(skillDTO.getName().trim().equals("")) {
			throw new EmptyParameterException("The skill name was left blank");
		}
		skill = new Skill(skillDTO);
		//set null for category in the skill controller;
		//if null is found here set category null
		//set category here will work 
		
		skill = skillDAO.save(skill);
		return skill;
	}

	@Transactional(rollbackOn = {SkillNotFoundException.class})
	public Skill updateSkill(String skillID, SkillDTO upSkill) throws EmptyParameterException, BadParameterException, SkillNotFoundException{
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
			}
			return skill;
		} catch (NumberFormatException e) {
			throw new BadParameterException("The skill ID provided must be of type int");
		}
	}

	@Transactional(rollbackOn = {SkillNotFoundException.class, ForeignKeyConstraintException.class})
	public Skill deleteSkill(String skillID) throws EmptyParameterException, BadParameterException,  SkillNotFoundException, ForeignKeyConstraintException {
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
				skillDAO.flush();
			}
			return skill;
		} catch (NumberFormatException e) {
			throw new BadParameterException("The skill ID provided must be of type int");
		} catch (DataIntegrityViolationException e) {
			throw new ForeignKeyConstraintException("Please remove this skill from all curricula before attempting to delete this skill");
		}
	}
	
}
