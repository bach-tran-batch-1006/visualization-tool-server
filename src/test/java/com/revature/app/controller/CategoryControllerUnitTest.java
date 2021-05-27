package com.revature.app.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.app.dto.CategoryDTO;
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
	void test_testEndpoint() throws Exception {
		mockMvc.perform(get("/categoryTest")).andExpect(MockMvcResultMatchers.status().isOk());
	}

}
