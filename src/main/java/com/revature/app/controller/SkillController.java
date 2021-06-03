package com.revature.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.revature.app.dto.SkillDTO;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.ForeignKeyConstraintException;
import com.revature.app.exception.SkillNotAddedException;
import com.revature.app.exception.SkillNotDeletedException;
import com.revature.app.exception.SkillNotFoundException;
import com.revature.app.exception.SkillNotUpdatedException;
import com.revature.app.model.Skill;
import com.revature.app.service.SkillService;

@CrossOrigin(origins = "*")
@RestController
public class SkillController {

	@Autowired
	private SkillService skillService;
	
	private static Logger logger = LoggerFactory.getLogger(SkillController.class);
	
	@GetMapping(path="allSkills")
	public Object getAllSkills() {
		List<Skill> skillList;
		skillList = skillService.getAllSkills();
		logger.info("User called the endpoint to get all skills from the database");
		return skillList;
	}
	
	@GetMapping(path="skill/{id}")
	public Object getSkillByID(@PathVariable("id") String skillID) {
		try {
			Skill skill = skillService.getSkillByID(skillID);
			logger.info("User called the endpoint to get information about skill with id " + skillID);
			return skill;
		} catch (BadParameterException e) {
			logger.warn("User gave a bad parameter while trying to get information about a skill in the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (EmptyParameterException e) {
			logger.warn("User left a parameter blank while trying to get information about a skill in the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (SkillNotFoundException e) {
			logger.warn("User requested information about a skill in the database that did not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@PostMapping(path="skill")
	public Object addSkills(@RequestBody SkillDTO skillDTO) {
		Skill skill = null;
		try {
			skill = skillService.addSkill(skillDTO);
			logger.info("User called the endpoint to add a skill to the database");
			return ResponseEntity.status(201).body(skill);
		} catch (EmptyParameterException e) {
			logger.warn("User left a parameter blank while trying to add a skill to the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (SkillNotAddedException e) {
			logger.error("Something went wrong with the database while adding a skill");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@PutMapping(path="skill/{id}")
	public Object updateSkill(@PathVariable("id") String skillID, @RequestBody SkillDTO skillDTO) {
		Skill skill = null;
		try {
			skill = skillService.updateSkill(skillID, skillDTO);
			logger.info("User called the endpoint to update information about skill with id " + skillID);
			return ResponseEntity.status(202).body(skill);
		} catch (EmptyParameterException e) {
			logger.warn("User left a parameter blank while trying to update a skill in the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (SkillNotUpdatedException e) {
			logger.error("Something went wrong with the database while updating a skill");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (BadParameterException e) {
			logger.warn("User gave a bad parameter while trying to update a skill in the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (SkillNotFoundException e) {
			logger.warn("User asked for information about a skill in the database that did not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@DeleteMapping(path="skill/{id}")
	public Object deleteSkill(@PathVariable("id") String skillID) {
		Skill skill = null;
		try {
			skill = skillService.deleteSkill(skillID);
			logger.info("User deleted the skill with the id " + skillID);
			return skill.getSkillId();
		} catch (EmptyParameterException e) {
			logger.warn("User left a parameter blank while trying to delete a skill from the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (BadParameterException e) {
			logger.warn("User gave a bad parameter while trying to delete a skill from the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (SkillNotDeletedException e) {
			logger.error("Something went wrong with the database while deleting a skill");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (SkillNotFoundException e) {
			logger.warn("User attempted to delete a skill in the database that did not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (ForeignKeyConstraintException e) {
			logger.warn("User attempted to delete a skill from the database but it was blocked because of a foreign key constraint");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
