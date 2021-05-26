package com.revature.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.app.model.Category;
import com.revature.app.service.CategoryService;

@RestController
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping(path = "category")
	public Category addCategory() {
		
		return null;
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
