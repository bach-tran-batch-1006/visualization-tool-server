package com.revature.app.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.app.dao.CategoryDAO;
import com.revature.app.dto.CategoryDTO;
import com.revature.app.model.Category;
import com.revature.app.service.CategoryService;



@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
@ActiveProfiles("test")
@SpringBootTest
public class CategoryIntegrationTest {
	
	
	private MockMvc mockMvc;
	
	private ObjectMapper om;
	
	@Autowired
    WebApplicationContext webApplicationContext;
	

    @Autowired
    CategoryService categoryService;
    
    @Autowired
    CategoryDAO categoryDAO;
    
	@BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.om = new ObjectMapper();
       
    }
	
	@Test 
	void test_getAllCategories_TestEndpoint() throws Exception {
		this.mockMvc.perform(get("/category"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test 
	@Commit
	@Order(0)
	void test_addCategory_TestEndpoint() throws Exception {
		Category expected = new Category(1, "Language","Programming Language");
		String expectedJson = om.writeValueAsString(expected);
		
		CategoryDTO input = new CategoryDTO("Language", "Programming Language");
		String inputJson = om.writeValueAsString(input);
		
		this.mockMvc.perform(post("/category")
			.contentType(MediaType.APPLICATION_JSON).content(inputJson))
			.andExpect(content().json(expectedJson))
			.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test 
	void test_addCategory_CategoryBlankInputException_TestEndpoint() throws Exception {
		
		
		CategoryDTO input = new CategoryDTO("", "Programming Language");
		String inputJson = om.writeValueAsString(input);
		
		this.mockMvc.perform(post("/category")
			.contentType(MediaType.APPLICATION_JSON).content(inputJson))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	

	@Test 
	@Commit
	@Order(1)
	void test_updateCategory_TestEndpoint() throws Exception {
		Category expected = new Category(1, "DevOps","DevOps Description");
		String expectedJson = om.writeValueAsString(expected);
		
		CategoryDTO input = new CategoryDTO("DevOps", "DevOps Description");
		String inputJson = om.writeValueAsString(input);
		
		this.mockMvc.perform(put("/category/1")
			.contentType(MediaType.APPLICATION_JSON).content(inputJson))
			.andExpect(content().json(expectedJson))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void test_updateCategory_CategoryBlankInputException_TestEndpoint() throws Exception {
		CategoryDTO input = new CategoryDTO("", "Programming Language");
		String inputJson = om.writeValueAsString(input);
		
		this.mockMvc.perform(put("/category/1")
			.contentType(MediaType.APPLICATION_JSON).content(inputJson))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void test_updateCategory_CategoryInvalidIdException_TestEndpoint() throws Exception {
		CategoryDTO input = new CategoryDTO("Language", "Programming Language");
		String inputJson = om.writeValueAsString(input);
		
		this.mockMvc.perform(put("/category/99")
			.contentType(MediaType.APPLICATION_JSON).content(inputJson))
			.andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	@Test 
	@Commit
	@Order(2)
	void test_deleteCategory_TestEndpoint() throws Exception {
		this.mockMvc.perform(delete("/category/1")).andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	@Test
	void test_deleteCategory_CategoryInvalidIdException_TestEndpoint() throws Exception {
		CategoryDTO input = new CategoryDTO("Language", "Programming Language");
		String inputJson = om.writeValueAsString(input);
		
		this.mockMvc.perform(delete("/category/99")
			.contentType(MediaType.APPLICATION_JSON).content(inputJson))
			.andExpect(MockMvcResultMatchers.status().is(404));
	}
}
