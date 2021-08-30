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
import com.revature.app.exception.SkillNotFoundException;
import com.revature.app.model.Category;
import com.revature.app.model.Skill;
import com.revature.app.service.CategoryService;
import com.revature.app.service.SkillService;

@CrossOrigin(origins = "*")
@RestController
public class SkillController {

	@Autowired
	private SkillService skillService;
	
	@Autowired
	private CategoryService catServ;
	
	



	
	
	private static Logger logger = LoggerFactory.getLogger(SkillController.class);
	
	String goodLog = "User called the endpoint ";
	
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
			String logString = String.format(goodLog, "to get information about a skill in the database with id %s");
			logString = String.format(logString, skillID);
			logger.info(logString);
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
	
	//this will set the id foriegn key in the skill table to the primary key of categrory in the category tabloe
	//params {String name: "angular", category:{ id: 1, categoryName: "front end",categoryDescription:"front end techs" }}
	
	
	//no id no , no categoryName, no categoryDescription 
	//String name: "angular", category:{ id: "", categoryName: "",categoryDescription:"" }
	//then set the id foreign key of category in skill table to null
	//can add skill now with linking to curicular or categrory
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
		}
	}
	
	//must have category id in the category object in side skillDTO
	//or there will be no link to the specific category in the category table
	
	
	@PutMapping(path="skill/{id}")
	public Object updateSkill(@PathVariable("id") String skillID, @RequestBody SkillDTO skillDTO) {
		Skill skill = null;
	
	 ;
		
		int cateID =skillDTO.getCategory().getCategoryId();
	 
		//System.out.println(cateID);
		//System.out.println(catServ.findCategory(cateID));
	 	//	return ResponseEntity.status(202).body(skill);
	 
		try {
		
			//query for  cate Integer.parseInt(sgory in db
			if(cateID!=0) {
				
				skillDTO.setCategory(catServ.findCategory(cateID) )	       ;
			}else {
				
				skillDTO.setCategory(null);
			}
			
		
			skill = skillService.updateSkill(skillID, skillDTO);
			String logString = String.format(goodLog, "to update a skill in the database with id %s");
			logString = String.format(logString, skillID);
			logger.info(logString);
			return ResponseEntity.status(202).body(skill);
		} catch (EmptyParameterException e) {
			logger.warn("User left a parameter blank while trying to update a skill in the database");
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
			String logString = String.format(goodLog, "to delete a skill in the database with id %s");
			logString = String.format(logString, skillID);
			logger.info(logString);
			return skill.getSkillId();
		} catch (EmptyParameterException e) {
			logger.warn("User left a parameter blank while trying to delete a skill from the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (BadParameterException e) {
			logger.warn("User gave a bad parameter while trying to delete a skill from the database");
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
