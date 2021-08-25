package com.revature.app.controller;

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
import com.revature.app.model.User;
import com.revature.app.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerUnitTest {
	private MockMvc mockMvc;
	@Mock
	private UserService uServ;
	@InjectMocks
	private UserController uController;
	
	private ObjectMapper om = new ObjectMapper();
	
	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(uController).build();
	}
	
	@Test
	void testCreateUser_positive() {
		//User expected = new User("first","last","email","pass");
		User notUser = new User("first","last","email","pass1");
		
		User expected = new User("first","last","email","pass");
		//String expectedJson = om.writeValueAsString(expected);
//		CategoryDTO inputUser= new User("first","last","email","pass");
//		String inputJson = om.writeValueAsString(inputUser);
//		
//		when(categoryService.addCategory(inputCategory)).thenReturn(expected);
//		
//		this.mockMvc.perform(post("/category").contentType(MediaType.APPLICATION_JSON).content(inputJson))
//		.andExpect(status().isCreated()).andExpect(content().json(expectedJson));
	}
	
}
