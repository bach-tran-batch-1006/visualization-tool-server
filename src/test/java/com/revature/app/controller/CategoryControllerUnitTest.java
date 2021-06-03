package com.revature.app.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.app.dto.CategoryDTO;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.CategoryBlankInputException;
import com.revature.app.exception.CategoryNotFoundException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.ForeignKeyConstraintException;
import com.revature.app.model.Category;
import com.revature.app.service.CategoryService;

@ExtendWith(MockitoExtension.class)
class CategoryControllerUnitTest {

	private MockMvc mockMvc;
	@Mock
	private CategoryService categoryService;
	@InjectMocks
	private CategoryController categoryController;

	private ObjectMapper om = new ObjectMapper();
	
	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
	}

	@Test
	void testAddCategory_positive() throws Exception {
		Category expected = new Category(1, "Language", "Programming Language");
		String expectedJson = om.writeValueAsString(expected);
		CategoryDTO inputCategory = new CategoryDTO("Language", "Programming Language");
		String inputJson = om.writeValueAsString(inputCategory);
		
		when(categoryService.addCategory(inputCategory)).thenReturn(expected);
		
		this.mockMvc.perform(post("/category").contentType(MediaType.APPLICATION_JSON).content(inputJson))
		.andExpect(status().isCreated()).andExpect(content().json(expectedJson));
	}
	
	@Test
	void testAddCategory_negative_BlankExceptionWithStatusCode() throws Exception {
		
		
		CategoryDTO inputCategory = new CategoryDTO("", "Programming Language");
		String inputJson = om.writeValueAsString(inputCategory);
		
		when(categoryService.addCategory(inputCategory)).thenThrow(CategoryBlankInputException.class);
		
		this.mockMvc.perform(post("/category").contentType(MediaType.APPLICATION_JSON).content(inputJson))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	void testGetAllCategories_positive() throws Exception {
		List<Category> expected = new ArrayList<>();
		Category category1 = new Category(1, "Language", "Programming Language");
		Category category2 = new Category(1, "DevOps", "DevOps Description");
		
		expected.add(category1);
		expected.add(category2);
		
		String expectedJson = om.writeValueAsString(expected);
		
		when(categoryService.getAllCategories()).thenReturn(expected);
		
		this.mockMvc.perform(get("/category")).andExpect(status().isOk()).andExpect(content().json(expectedJson));
		
	}
	
	@Test
	void testUpdateCategory_positive() throws Exception {
		Category expected = new Category(1, "Language", "Programming Language");
		String expectedJson = om.writeValueAsString(expected);
		CategoryDTO inputCategory = new CategoryDTO("Language", "Programming Language");
		String inputJson = om.writeValueAsString(inputCategory);
		
		when(categoryService.updateCategory(eq("1"),eq(inputCategory))).thenReturn(expected);
		
		this.mockMvc.perform(put("/category/1").contentType(MediaType.APPLICATION_JSON).content(inputJson))
		.andExpect(status().isOk()).andExpect(content().json(expectedJson));
	}
	
	@Test
	void testUpdateCategory_negative_CategoryBlankInputException_() throws Exception {
		CategoryDTO inputCategory = new CategoryDTO("", "Programming Language");
		String inputJson = om.writeValueAsString(inputCategory);
		
		when(categoryService.updateCategory(eq("1"),eq(inputCategory))).thenThrow(EmptyParameterException.class);
		
		this.mockMvc.perform(put("/category/1").contentType(MediaType.APPLICATION_JSON).content(inputJson))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	void testUpdateCategory_negative_CategoryInvalidIdException_() throws Exception {
		
		
		CategoryDTO inputCategory = new CategoryDTO("Language", "Programming Language");
		String inputJson = om.writeValueAsString(inputCategory);
		
		when(categoryService.updateCategory(eq("3"),eq(inputCategory))).thenThrow(CategoryNotFoundException.class);
		
		this.mockMvc.perform(put("/category/3").contentType(MediaType.APPLICATION_JSON).content(inputJson))
		.andExpect(status().is(404));
	}
	
	@Test
	void test_deleteCategory_positive() throws Exception{
		when(categoryService.deleteCategory(eq("3"))).thenReturn(null);
		this.mockMvc.perform(delete("/category/3")).andExpect(status().isNoContent());
	}

	@Test
	void test_deleteCategory_negative_CategoryNotFoundException() throws Exception {
		when(categoryService.deleteCategory(eq("3"))).thenThrow(CategoryNotFoundException.class);
		this.mockMvc.perform(delete("/category/3")).andExpect(status().is(404));
	}
	
	@Test
	void test_deleteCategory_negative_ForeignKeyException() throws Exception {
		when(categoryService.deleteCategory(eq("3"))).thenThrow(ForeignKeyConstraintException.class);
		this.mockMvc.perform(delete("/category/3")).andExpect(status().isBadRequest());
	}
	
	@Test
	void test_deleteCategory_negative_BadParameter() throws Exception {
		when(categoryService.deleteCategory(eq("test"))).thenThrow(BadParameterException.class);
		this.mockMvc.perform(delete("/category/test")).andExpect(status().isBadRequest());
	}
	
	@Test
	void test_deleteCategory_negative_EmptyParameter() throws Exception {
		when(categoryService.deleteCategory(eq(" "))).thenThrow(EmptyParameterException.class);
		this.mockMvc.perform(delete("/category/ ")).andExpect(status().isBadRequest());
	}
	
	
}
