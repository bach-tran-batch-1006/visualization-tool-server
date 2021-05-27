package com.revature.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revature.app.dto.CategoryDTO;
import com.revature.app.exception.CategoryBlankInputException;
import com.revature.app.model.Category;
import com.revature.app.service.CategoryService;

@RestController
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping(path = "category")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Category addCategory(@RequestBody CategoryDTO categoryDTO) throws CategoryBlankInputException {
		return categoryService.addCategory(categoryDTO);
	}
	
	@GetMapping(path = "category")
	public List<Category> getAllCategories(){

		return null;
	}
	
	@PutMapping(path = "category/{id}")
	public Category updateCategory(@PathVariable("id") int id) {
		return null;
	}
	
	@DeleteMapping(path="category/{id}")
	public void deleteCategory(@PathVariable("id") int id) {
		
	}
	
	@GetMapping(path = "categoryTest")
	public String testEndpoint() {
		return "Category Working!";
	}
}
