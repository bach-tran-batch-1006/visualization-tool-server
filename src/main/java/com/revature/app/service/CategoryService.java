package com.revature.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.app.dao.CategoryDAO;
import com.revature.app.dto.CategoryDTO;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.CategoryBlankInputException;
import com.revature.app.exception.CategoryInvalidIdException;
import com.revature.app.exception.CategoryNotFoundException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.ForeignKeyConstraintException;
import com.revature.app.exception.SkillNotAddedException;
import com.revature.app.exception.SkillNotDeletedException;
import com.revature.app.exception.SkillNotFoundException;
import com.revature.app.exception.SkillNotUpdatedException;
import com.revature.app.model.Category;
import com.revature.app.model.Skill;

@Service
public class CategoryService {

	@Autowired
	private CategoryDAO categoryDAO;
	
	private static Logger logger = LoggerFactory.getLogger(CategoryService.class);
	
	String badParam = "The skill ID provided must be of type int";
	String emptyParam = "The skill ID was left blank";

	@Transactional
	public Category addCategory(CategoryDTO inputCategory) throws CategoryBlankInputException {
		Category category = null;
		if(inputCategory.getCategoryName().trim().equals("")) {
			throw new CategoryBlankInputException("The category name was left blank");
		}
		category = new Category(0, inputCategory.getCategoryName(), inputCategory.getCategoryDescription());
		category = categoryDAO.save(category);
		return category;
	}

	@Transactional
	public List<Category> getAllCategories() {
		return categoryDAO.findAll();

	}

	@Transactional
	public Category updateCategory(String catId, CategoryDTO inputCategory) throws BadParameterException, CategoryNotFoundException, EmptyParameterException {
		Category categoryToUpdate = null;
		try {
			if(catId.trim().equals("")){
				throw new EmptyParameterException(emptyParam);
			}
			if(inputCategory.getCategoryName().trim().equals("")){
				throw new EmptyParameterException("The category name was left blank");
			}
			int id = Integer.parseInt(catId);
			categoryToUpdate = categoryDAO.findById(id);
			if(categoryToUpdate == null) {
				throw new CategoryNotFoundException("The category could not be updated because it couldn't be found");
			} else {
				categoryToUpdate.setCategoryName(inputCategory.getCategoryName());
				categoryToUpdate.setCategoryDescription(inputCategory.getCategoryDescription());
				categoryToUpdate = categoryDAO.save(categoryToUpdate);
			}
			return categoryToUpdate;
		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		}
	}
	
	@Transactional
	public Category deleteCategory(String catId) throws EmptyParameterException, CategoryNotFoundException, BadParameterException, ForeignKeyConstraintException {
		Category categoryToDelete = null;
		try {
			if(catId.trim().equals("")){
				throw new EmptyParameterException(emptyParam);
			}
			int id = Integer.parseInt(catId);
			categoryToDelete = categoryDAO.findById(id);
			if(categoryToDelete == null) {
				throw new CategoryNotFoundException("The skill could not be deleted because it couldn't be found");
			} else {
				categoryDAO.delete(categoryToDelete);
			}
			return categoryToDelete;
		} catch (NumberFormatException e) {
			throw new BadParameterException(badParam);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			throw new ForeignKeyConstraintException("Please remove this category from all skills before attempting to delete this category");
		}
	}
	

}
