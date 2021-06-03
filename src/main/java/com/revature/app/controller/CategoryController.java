package com.revature.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.revature.app.dto.CategoryDTO;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.CategoryBlankInputException;
import com.revature.app.exception.CategoryNotFoundException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.ForeignKeyConstraintException;
import com.revature.app.model.Category;
import com.revature.app.service.CategoryService;

@CrossOrigin(origins = "*")
@RestController
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	private static Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@PostMapping(path = "category")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Category addCategory(@RequestBody CategoryDTO categoryDTO) throws CategoryBlankInputException {
		try {
			Category category = categoryService.addCategory(categoryDTO);
			logger.info("User called the endpoint to add a category to the database");
			return category;
		} catch (CategoryBlankInputException e) {
			logger.warn("User left a parameter blank while trying to add a category to the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping(path = "category")
	public List<Category> getAllCategories() {
		logger.info("User called the endpoint to get all categories from the database");
		return categoryService.getAllCategories();
	}

	@PutMapping(path = "category/{id}")
	public Category updateCategory(@PathVariable("id") String id, @RequestBody CategoryDTO categoryDTO) {
		try {
			Category category = categoryService.updateCategory(id, categoryDTO);
			logger.info("User called the endpoint to update information about category with id " + id);
			return category;
		} catch (EmptyParameterException e) {
			logger.warn("User left a parameter blank while trying to update a category in the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (BadParameterException e) {
			logger.warn("User gave a bad parameter while trying to update a category in the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (CategoryNotFoundException e) {
			logger.warn("User asked for information about a category in the database that did not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@DeleteMapping(path = "category/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public Object deleteCategory(@PathVariable("id") String id) {
		try {
			categoryService.deleteCategory(id);
			logger.info("User deleted the category with the id " + id);
			return id;
		} catch (EmptyParameterException e) {
			logger.warn("User left a parameter blank while trying to delete a category from the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (CategoryNotFoundException e) {
			logger.warn("User attempted to delete a category in the database that did not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (BadParameterException e) {
			logger.warn("User gave a bad parameter while trying to delete a category from the database");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (ForeignKeyConstraintException e) {
			logger.warn("User attempted to delete a category from the database but it was blocked because of a foreign key constraint");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

}
