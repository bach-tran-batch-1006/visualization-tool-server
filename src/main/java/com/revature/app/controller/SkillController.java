package com.revature.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.app.dto.MessageDTO;
import com.revature.app.dto.SkillDTO;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.SkillNotAddedException;
import com.revature.app.exception.SkillNotDeletedException;
import com.revature.app.exception.SkillNotFoundException;
import com.revature.app.exception.SkillNotUpdatedException;
import com.revature.app.model.Skill;
import com.revature.app.service.SkillService;

@RestController
public class SkillController {

	@Autowired
	private SkillService skillService;
	
	@GetMapping(path="allSkills")
	public Object getAllSkills() {
		List<Skill> skillList;
		skillList = skillService.getAllSkills();
		if(skillList.size() <= 0) {
			return ResponseEntity.status(404).body(new MessageDTO("The list of skills is empty"));
		} else {
			return ResponseEntity.status(200).body(skillList);
			
		}
	}
	
	@GetMapping(path="skill/{id}")
	public Object getSkillByID(@PathVariable("id") String skillID) {
		try {
			Skill skill = skillService.getSkillByID(skillID);
			return ResponseEntity.status(200).body(skill);
		} catch (BadParameterException e) {
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		} catch (EmptyParameterException e) {
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		} catch (SkillNotFoundException e) {
			return ResponseEntity.status(404).body(new MessageDTO(e.getMessage()));
		}
	}
	
	@PostMapping(path="skill")
	public Object addSkills(@RequestBody SkillDTO skillDTO) {
		Skill skill = null;
		try {
			skill = skillService.addSkill(skillDTO);
			return ResponseEntity.status(201).body(skill);
		} catch (EmptyParameterException e) {
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		} catch (SkillNotAddedException e) {
			return ResponseEntity.status(403).body(new MessageDTO(e.getMessage()));
		}
	}
	
	@PutMapping(path="skill/{id}")
	public Object updateSkill(@PathVariable("id") String skillID, @RequestBody SkillDTO skillDTO) {
		Skill skill = null;
		try {
			skill = skillService.updateSkill(skillID, skillDTO);
			return ResponseEntity.status(202).body(skill);
		} catch (EmptyParameterException e) {
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		} catch (SkillNotUpdatedException e) {
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		} catch (BadParameterException e) {
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		} catch (SkillNotFoundException e) {
			return ResponseEntity.status(404).body(new MessageDTO(e.getMessage()));
		}
	}
	
	@DeleteMapping(path="skill/{id}")
	public Object deleteSkill(@PathVariable("id") String skillID) {
		Skill skill = null;
		try {
			skill = skillService.deleteSkill(skillID);
			return ResponseEntity.status(200).body(skill);
		} catch (EmptyParameterException e) {
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		} catch (BadParameterException e) {
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		} catch (SkillNotDeletedException e) {
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		} catch (SkillNotFoundException e) {
			return ResponseEntity.status(404).body(new MessageDTO(e.getMessage()));
		}
	
	}
	
}
