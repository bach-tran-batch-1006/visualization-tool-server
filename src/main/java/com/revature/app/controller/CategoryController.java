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
import com.revature.app.exception.CategoryBlankInputException;
import com.revature.app.exception.CategoryInvalidIdException;
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
	public Category updateCategory(@PathVariable("id") int id, @RequestBody CategoryDTO categoryDTO) {
		try {
			return categoryService.updateCategory(id, categoryDTO);
		} catch (CategoryBlankInputException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (CategoryInvalidIdException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

		}
	}

	@DeleteMapping(path = "category/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteCategory(@PathVariable("id") int id) {
		try {
			categoryService.deleteCategory(id);
		} catch (CategoryInvalidIdException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping(path = "categoryTest")
	public String testEndpoint() {
		return "Category Working!";
	}
}
