package com.revature.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.app.dao.CategoryDAO;
import com.revature.app.dto.CategoryDTO;
import com.revature.app.model.Category;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryDAO categoryDAO;

	@Transactional
	public Category addCategory(CategoryDTO inputCategory) {
		return null;
	}
	
	@Transactional
	public List<Category> getAllCategories() {
		categoryDAO.findAll();
		return null;
	}
	
	@Transactional
	public Category updateCategory(int id, CategoryDTO inputCategory) {
		return null;
	}
	
	@Transactional
	public Category updateCategory(int id) {
		return null;
	}
}
