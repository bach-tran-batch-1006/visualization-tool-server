package com.revature.app.controller;

import java.util.List;

import javax.transaction.Transactional;

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

import com.revature.app.dto.MessageDTO;
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
	
	@GetMapping(path="allSkills")
	public Object getAllSkills() {
		List<Skill> skillList;
		skillList = skillService.getAllSkills();
		return skillList;
	}
	
	@GetMapping(path="skill/{id}")
	public Object getSkillByID(@PathVariable("id") String skillID) {
		try {
			Skill skill = skillService.getSkillByID(skillID);
			return skill;
		} catch (BadParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (EmptyParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (SkillNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@PostMapping(path="skill")
	public Object addSkills(@RequestBody SkillDTO skillDTO) {
		Skill skill = null;
		try {
			skill = skillService.addSkill(skillDTO);
			return ResponseEntity.status(201).body(skill);
		} catch (EmptyParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (SkillNotAddedException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@PutMapping(path="skill/{id}")
	public Object updateSkill(@PathVariable("id") String skillID, @RequestBody SkillDTO skillDTO) {
		Skill skill = null;
		try {
			skill = skillService.updateSkill(skillID, skillDTO);
			return ResponseEntity.status(202).body(skill);
		} catch (EmptyParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (SkillNotUpdatedException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (BadParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (SkillNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@DeleteMapping(path="skill/{id}")
	public Object deleteSkill(@PathVariable("id") String skillID) {
		Skill skill = null;
		try {
			skill = skillService.deleteSkill(skillID);
			return skill.getSkillId();
		} catch (EmptyParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (BadParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (SkillNotDeletedException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (SkillNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (ForeignKeyConstraintException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	
	}
	
}
