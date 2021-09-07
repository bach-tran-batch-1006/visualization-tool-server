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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.app.dto.UserDTO;
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
	/*
	@Test
	void testCreateUser_positive() throws Exception {
		UserController uControllerNoArg=new UserController();
		User expected = new User(1,"first","last","email","pass");
		String expectedJson = om.writeValueAsString(expected);
		
		UserDTO inputUser= new UserDTO("first","last","email","pass");
		String inputJson = om.writeValueAsString(inputUser);
		
		when(uServ.registerUser(inputUser)).thenReturn(expected);
		
		this.mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON).content(inputJson))
		.andExpect(status().isCreated()).andExpect(content().json(expectedJson));
	}
	
	@Test
	void testCreateUser_negative() throws Exception {
		
		User expected = null;
		String expectedJson = om.writeValueAsString(expected);
		
		UserDTO inputUser= new UserDTO("first","last","email","pass");
		String inputJson = om.writeValueAsString(inputUser);
		
		when(uServ.registerUser(inputUser)).thenReturn(expected);
		
		this.mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON).content(inputJson))
		.andExpect(status().isConflict());
	}
	
	@Test
	void testLoginUser_positive() throws Exception {
		
		User expected = new User("email","pass");
		String expectedJson = om.writeValueAsString(expected);
		
		User inputUser= new User("email","pass");
		String inputJson = om.writeValueAsString(inputUser);
		
		when(uServ.loginUser("email","pass")).thenReturn(expected);
		
		this.mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON).content(inputJson))
		.andExpect(status().isOk()).andExpect(content().json(expectedJson));
	}
	
	@Test
	void testLoginUser_negative() throws Exception {
		
		User expected = new User("email","pass");
		String expectedJson = om.writeValueAsString(expected);
		
		User inputUser= new User("email","pass");
		String inputJson = om.writeValueAsString(inputUser);
		
		when(uServ.loginUser("email","pass")).thenReturn(null);
		
		this.mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON).content(inputJson))
		.andExpect(status().isForbidden());
	}
	
	@Test
	void testGetUserById_positive() throws Exception {

		User expected = new User(1,"first","last","email","pass");
		String expectedJson = om.writeValueAsString(expected);
		
		when(uServ.getUserById(1)).thenReturn(expected);
		this.mockMvc.perform(get("/user/id/1")).andExpect(status().isOk()).andExpect(content().json(expectedJson));
		
		
	}
	
	@Test
	void testGetUserById_negative() throws Exception {

		User expected = new User(1,"first","last","email","pass");
		String expectedJson = om.writeValueAsString(expected);
		
		when(uServ.getUserById(1)).thenReturn(null);
		this.mockMvc.perform(get("/user/id/1")).andExpect(status().isForbidden());
		
		
	}
	*/
}
