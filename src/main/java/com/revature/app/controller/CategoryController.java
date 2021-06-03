package com.revature.app.controller;

import java.util.List;

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
import com.revature.app.exception.CategoryInvalidIdException;
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

	@PostMapping(path = "category")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Category addCategory(@RequestBody CategoryDTO categoryDTO) throws CategoryBlankInputException {
		try {
			return categoryService.addCategory(categoryDTO);
		} catch (CategoryBlankInputException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

		}
	}

	@GetMapping(path = "category")
	public List<Category> getAllCategories() {
		return categoryService.getAllCategories();
	}

	@PutMapping(path = "category/{id}")
	public Category updateCategory(@PathVariable("id") String id, @RequestBody CategoryDTO categoryDTO) {
		try {
			return categoryService.updateCategory(id, categoryDTO);
		} catch (EmptyParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (BadParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (CategoryNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@DeleteMapping(path = "category/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public Object deleteCategory(@PathVariable("id") String id) {
		try {
			categoryService.deleteCategory(id);
			return id;
		} catch (EmptyParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (CategoryNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (BadParameterException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (ForeignKeyConstraintException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

}
