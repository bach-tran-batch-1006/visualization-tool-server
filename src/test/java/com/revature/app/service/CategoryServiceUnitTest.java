package com.revature.app.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.revature.app.dao.CategoryDAO;
import com.revature.app.dto.CategoryDTO;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.CategoryBlankInputException;
import com.revature.app.exception.CategoryInvalidIdException;
import com.revature.app.exception.CategoryNotFoundException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.model.Category;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CategoryServiceUnitTest {
	
	@Mock
	private CategoryDAO categoryDAO;
	
	@InjectMocks
	private CategoryService categoryService;
	

	@Test
	void testGetAllCategories_positive() {
		List<Category> expected = new ArrayList<>();
		when(categoryDAO.findAll()).thenReturn(expected);
		List<Category> actual = categoryService.getAllCategories();
		assertEquals(expected, actual);
	}
	
	@Test
	@Order(0)
	void testAddCategory_positive() throws CategoryBlankInputException {
		Category expected = new Category(1, "Language", "Programming Language");
		CategoryDTO categoryDTO = new CategoryDTO("Language", "Programming Language");
		
		lenient().when(categoryDAO.save(any(Category.class))).thenReturn(expected);
		
		Category actual = categoryService.addCategory(categoryDTO);
		
		assertEquals(expected, actual);
	}
    
	@ParameterizedTest
	@EmptySource
	void testAddCategory_negative_BlankInputException(String categoryName) {
			assertThrows(CategoryBlankInputException.class, () -> {
				categoryService.addCategory(new CategoryDTO(categoryName, "Programming Language"));
			});
	}
	
	@Test
	void testUpdateCategory_positive() throws CategoryBlankInputException, CategoryInvalidIdException, BadParameterException, CategoryNotFoundException, EmptyParameterException {
		Category oldCategory = new Category(1, "Language", "Programming Language");
		Category expected = new Category(1, "DevOps", "set of practices that combines software development and IT operations.");
		CategoryDTO categoryDTO = new CategoryDTO("DevOps", "set of practices that combines software development and IT operations.");
		
		lenient().when(categoryDAO.findById(eq(1))).thenReturn(oldCategory);
		lenient().when(categoryDAO.save(oldCategory)).thenReturn(expected);
		Category actual = categoryService.updateCategory("1", categoryDTO);
		assertEquals(expected, actual);
	}
	
	@ParameterizedTest
	@EmptySource
	void testUpdateCategory_negative_blankInputException(String categoryName) {
		CategoryDTO inputCategoryDTO = new CategoryDTO(categoryName, "Programming Language");
		
		
		assertThrows(EmptyParameterException.class, () -> {
			lenient().when(categoryDAO.findById(eq(1))).thenReturn(new Category(1, "Language", "Programming Language"));
			categoryService.updateCategory("1", inputCategoryDTO);
		});	
	}
	
	@ParameterizedTest
	@MethodSource("invalidIds")
	void testUpdateCategory_negative_invalidIdException(String id) {
		
		CategoryDTO inputCategoryDTO = new CategoryDTO("Language", "Programming Language");
		
		assertThrows(CategoryNotFoundException.class, () -> {
			categoryService.updateCategory(id, inputCategoryDTO);
		});
	}
	
	private static Stream<Arguments> invalidIds() {
		return Stream.of(
				Arguments.of("-1"),
				Arguments.of("999")
				);
	}
	
	
	@ParameterizedTest
	@MethodSource("invalidIds")
	void testDeleteCategory_negative_invalidIdException(String id) {
		assertThrows(CategoryNotFoundException.class, () -> {
			categoryService.deleteCategory(id);
		});
	}
	
	
    
}

