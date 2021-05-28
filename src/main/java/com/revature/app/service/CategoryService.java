package com.revature.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.app.dao.CategoryDAO;
import com.revature.app.dto.CategoryDTO;
import com.revature.app.exception.CategoryBlankInputException;
import com.revature.app.exception.CategoryInvalidIdException;
import com.revature.app.model.Category;

@Service
public class CategoryService {

	@Autowired
	private CategoryDAO categoryDAO;
	
	private static Logger logger = LoggerFactory.getLogger(CategoryService.class);

	@Transactional
	public Category addCategory(CategoryDTO inputCategory) throws CategoryBlankInputException {
		try {
			if (inputCategory.getCategoryName().trim().equals("") || inputCategory.getCategoryName() == null) {
				logger.warn("blank input name: CategoryService.addCategory()");
				throw new CategoryBlankInputException("Category name cannot be blank");
			}

		} catch (NullPointerException e) {
			logger.warn("null input name: CategoryService.addCategory()" );
			throw new CategoryBlankInputException("Category name cannot be blank");
		}

		Category category = new Category();
		category.setCategoryName(inputCategory.getCategoryName());
		category.setCategoryDescription(inputCategory.getCategoryDescription());

		category = categoryDAO.save(category);
		categoryDAO.flush();
		return category;
	}

	@Transactional
	public List<Category> getAllCategories() {
		return categoryDAO.findAll();

	}

	@Transactional
	public Category updateCategory(int id, CategoryDTO inputCategory)
			throws CategoryBlankInputException, CategoryInvalidIdException {
		Category categoryToUpdate = null;

		if (id > 0) {
			categoryToUpdate = categoryDAO.findById(id);

			if (categoryToUpdate == null) {
				logger.warn("Category Id not found: CategoryService.updateCategory()" );
				throw new CategoryInvalidIdException("Category ID: " + id + " does not exist");
			}

		} else {
			logger.warn("Id is negative: CategoryService.updateCategory()" );
			throw new CategoryInvalidIdException("ID: " + id + " cannot be a negative number");
		}

		try {
			if (inputCategory.getCategoryName().trim().equals("") || inputCategory.getCategoryName() == null) {
				logger.warn("Category name is blank: CategoryService.updateCategory()" );
				throw new CategoryBlankInputException("Category name cannot be blank");
			}

		} catch (NullPointerException e) {
			logger.warn("Category name is null: CategoryService.updateCategory()" );
			throw new CategoryBlankInputException("Category name cannot be blank");
		}

		categoryToUpdate.setCategoryName(inputCategory.getCategoryName());
		categoryToUpdate.setCategoryDescription(inputCategory.getCategoryDescription());

		categoryDAO.save(categoryToUpdate);
		categoryDAO.flush();

		return categoryToUpdate;
	}
	
	@Transactional
	public String deleteCategory(int id) throws CategoryInvalidIdException {
		Category categoryToDelete = null;
		if (id > 0) {
			categoryToDelete = categoryDAO.findById(id);

			if (categoryToDelete == null) {
				logger.warn("Category Id not found: CategoryService.deleteCategory()" );
				throw new CategoryInvalidIdException("Category ID: " + id + " does not exist");
			}

		} else {
			logger.warn("Category Id is negative: CategoryService.deleteCategory()" );
			throw new CategoryInvalidIdException("ID: " + id + " cannot be a negative number");
		}
		
		categoryDAO.delete(categoryToDelete);
		
		return "Success";
		
	}
	

}
