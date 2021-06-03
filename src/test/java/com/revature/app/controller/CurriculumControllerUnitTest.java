package com.revature.app.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

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
import com.revature.app.dto.CurriculumDto;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.CurriculumNotFoundException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.model.Skill;
import com.revature.app.service.CurriculumService;

@ExtendWith(MockitoExtension.class)
class CurriculumControllerUnitTest {

	private MockMvc mockMvc;

	@Mock
	CurriculumService curriculumService;

	@InjectMocks
	private CurriculumController curriculumController;

	private ObjectMapper om = new ObjectMapper();
	
	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(curriculumController).build();
	}
	
	@Test
	void test_addCurriculum_negative_BlankExceptionWithStatusCode() throws Exception {
		
		CurriculumDto input = new CurriculumDto(" ", new ArrayList<Skill>());
		String inputJson = om.writeValueAsString(input);
		
		when(curriculumService.addCurriculum(input)).thenThrow(EmptyParameterException.class);
		
		this.mockMvc.perform(post("/curriculum").contentType(MediaType.APPLICATION_JSON).content(inputJson))
		.andExpect(status().isBadRequest());
	}
		
	@Test
	void test_getCurriculumByID_negative_BlankExceptionWithStatusCode() throws Exception {
			
		String inputJson = om.writeValueAsString("1000");
		
		when(curriculumService.getCurriculumByID("1000")).thenThrow(CurriculumNotFoundException.class);
		
		mockMvc.perform(get("/curriculum/1000")).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	/*
	 * @Test void test_getCurriculumAll_negative_BlankExceptionWithStatusCode()
	 * throws Exception {
	 * 
	 * String inputJson = om.writeValueAsString("1000");
	 * 
	 * when(curriculumService.deleteCurriculumByID("i")).thenThrow(
	 * NumberFormatException.class);
	 * 
	 * mockMvc.perform(get("/curriculum/1000")).andExpect(MockMvcResultMatchers.
	 * status().isNotFound()); }
	 */
		
}
