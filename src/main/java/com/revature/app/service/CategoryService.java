package com.revature.app.service;

import java.util.List;

import javax.transaction.Transactional;

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

	@Transactional
	public Category addCategory(CategoryDTO inputCategory) throws CategoryBlankInputException {
		try {
			if (inputCategory.getCategoryName().trim().equals("") || inputCategory.getCategoryName() == null) {
				throw new CategoryBlankInputException("Category name cannot be blank");
			}

		} catch (NullPointerException e) {
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
			categoryToUpdate = categoryDAO.getById(id);

			if (categoryToUpdate == null) {
				throw new CategoryInvalidIdException("Category ID: " + id + " does not exist");
			}

		} else {
			throw new CategoryInvalidIdException("ID: " + id + " cannot be a negative number");
		}

		try {
			if (inputCategory.getCategoryName().trim().equals("") || inputCategory.getCategoryName() == null) {
				throw new CategoryBlankInputException("Category name cannot be blank");
			}

		} catch (NullPointerException e) {
			throw new CategoryBlankInputException("Category name cannot be blank");
		}

		categoryToUpdate.setCategoryName(inputCategory.getCategoryName());
		categoryToUpdate.setCategoryDescription(inputCategory.getCategoryDescription());

		categoryDAO.save(categoryToUpdate);
		categoryDAO.flush();

		return categoryToUpdate;
	}
	
	@Transactional
	public void deleteCategory(int id) throws CategoryInvalidIdException {
		Category categoryToDelete = null;
		if (id > 0) {
			categoryToDelete = categoryDAO.getById(id);

			if (categoryToDelete == null) {
				throw new CategoryInvalidIdException("Category ID: " + id + " does not exist");
			}

		} else {
			throw new CategoryInvalidIdException("ID: " + id + " cannot be a negative number");
		}
		
		categoryDAO.delete(categoryToDelete);
		
		
		
	}

}
