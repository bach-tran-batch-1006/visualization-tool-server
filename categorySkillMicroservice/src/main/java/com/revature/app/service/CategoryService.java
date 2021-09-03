package com.revature.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.*;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.app.dao.CategoryDAO;

import com.revature.app.dto.CategoryDTO;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.CategoryBlankInputException;
import com.revature.app.exception.CategoryNotFoundException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.ForeignKeyConstraintException;
import com.revature.app.model.Category;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Service
@NoArgsConstructor
@AllArgsConstructor(onConstructor= @__(@Autowired))
@Getter @Setter @ToString 
public class CategoryService {


	private CategoryDAO categoryDAO;
	



	@Transactional
	public Category addCategory(CategoryDTO inputCategory) throws CategoryBlankInputException {
		Category category = null;
		if(inputCategory.getCategoryName().trim().equals("")) {
			throw new CategoryBlankInputException("The category name was left blank");
		}
		category = new Category(inputCategory.getCategoryName(), inputCategory.getCategoryDescription(),  inputCategory.getUserid());
		category = categoryDAO.save(category);
		return category;
	}

	@Transactional
	public List<Category> getAllCategories(int id) {
		return categoryDAO.findAllByuserid(id);

	}

	
	

	@Transactional
	public Category findCategory(int id) {
		
		Category carte = null;
		
		carte = categoryDAO.findById(id);
		if ( carte!= null) {
			
			return carte;
		}
		return carte;
	}
	
	@Transactional
	public Category updateCategory(String catId, CategoryDTO inputCategory) throws BadParameterException, CategoryNotFoundException, EmptyParameterException {
		Category categoryToUpdate = null;
		try {
			if(catId.trim().equals("")){
				throw new EmptyParameterException("The category ID was left blank");
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
			throw new BadParameterException("The category ID provided must be of type int");
		}
	}
	
	@Transactional(rollbackOn = {CategoryNotFoundException.class, ForeignKeyConstraintException.class})
	public Category deleteCategory(String catId) throws EmptyParameterException, CategoryNotFoundException, BadParameterException, ForeignKeyConstraintException {
		Category categoryToDelete = null;
		try {
			if(catId.trim().equals("")){
				throw new EmptyParameterException("The category ID was left blank");
			}
			int id = Integer.parseInt(catId);
			categoryToDelete = categoryDAO.findById(id);
			if(categoryToDelete == null) {
				throw new CategoryNotFoundException("The skill could not be deleted because it couldn't be found");
			} else {
				
				
				categoryDAO.delete(categoryToDelete);
				categoryDAO.flush();
			}
			return categoryToDelete;
		} catch (NumberFormatException e) {
			throw new BadParameterException("The category ID provided must be of type int");
		} catch (DataIntegrityViolationException e) {
			throw new ForeignKeyConstraintException("Please remove this category from all skills before attempting to delete this category");
		}
	}



}
