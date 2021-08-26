package com.revature.app.controller;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.revature.app.exception.ForeignKeyConstraintException;
//import com.revature.app.model.Skill;
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
		
//		CurriculumDto input = new CurriculumDto(" ", new ArrayList<Skill>());
//		String inputJson = om.writeValueAsString(input);
		
//		when(curriculumService.addCurriculum(input)).thenThrow(EmptyParameterException.class);
		
//		this.mockMvc.perform(post("/curriculum").contentType(MediaType.APPLICATION_JSON).content(inputJson))
//		.andExpect(status().isBadRequest());
	}
		
	@Test
	void test_getCurriculumByID_negative_BlankExceptionWithStatusCode() throws Exception {
		when(curriculumService.getCurriculumByID("1000")).thenThrow(CurriculumNotFoundException.class);
		mockMvc.perform(get("/curriculum/1000")).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	
	@Test
	void test_getCurriculumByID_badID() throws Exception {
		when(curriculumService.getCurriculumByID("test")).thenThrow(BadParameterException.class);
		mockMvc.perform(get("/curriculum/test")).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void test_getCurriculumByID_emptyID() throws Exception {
		when(curriculumService.getCurriculumByID(" ")).thenThrow(EmptyParameterException.class);
		mockMvc.perform(get("/curriculum/ ")).andExpect(MockMvcResultMatchers.status().is(400));
	}

//
	@Test
	void test_updateCurriculum_curriculumNotFound() throws Exception {
//		CurriculumDto input = new CurriculumDto("testCurriculum", new ArrayList<Skill>());
//		String body = om.writeValueAsString(input);
//		when(curriculumService.updateCurriculumByID("2", input)).thenThrow(CurriculumNotFoundException.class);
		mockMvc.perform(
				put("/curriculum/2")
				.contentType(MediaType.APPLICATION_JSON)
//				.content(body)
				).andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	@Test
	void test_updateCurriculum_badID() throws Exception {
//		CurriculumDto input = new CurriculumDto("testCurriculum", new ArrayList<Skill>());
//		String body = om.writeValueAsString(input);
//		when(curriculumService.updateCurriculumByID("test", input)).thenThrow(BadParameterException.class);
		mockMvc.perform(
				put("/curriculum/test")
				.contentType(MediaType.APPLICATION_JSON)
//				.content(body)
				).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void test_updateCurriculum_emptyID() throws Exception {
//		CurriculumDto input = new CurriculumDto("testCurriculum", new ArrayList<Skill>());
//		String body = om.writeValueAsString(input);
//		when(curriculumService.updateCurriculumByID(" ", input)).thenThrow(EmptyParameterException.class);
		mockMvc.perform(
				put("/curriculum/ ")
				.contentType(MediaType.APPLICATION_JSON)
//				.content(body)
				).andExpect(MockMvcResultMatchers.status().is(400));
	}

//
	@Test
	void test_deleteCurriculum_curriculumNotFound() throws Exception {
		when(curriculumService.deleteCurriculumByID("2")).thenThrow(CurriculumNotFoundException.class);
		mockMvc.perform(delete("/curriculum/2")).andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	@Test
	void test_deleteCurriculum_badID() throws Exception {
		when(curriculumService.deleteCurriculumByID("test")).thenThrow(BadParameterException.class);
		mockMvc.perform(delete("/curriculum/test")).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void test_deleteCurriculum_emptyID() throws Exception {
		when(curriculumService.deleteCurriculumByID(" ")).thenThrow(EmptyParameterException.class);
		mockMvc.perform(delete("/curriculum/ ")).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void test_deleteCurriculum_foreignKey() throws Exception {
		when(curriculumService.deleteCurriculumByID("3")).thenThrow(ForeignKeyConstraintException.class);
		mockMvc.perform(delete("/curriculum/3")).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
//
	@Test
	void test_getAllCategoriesByID_happy() throws Exception {
		lenient().when(curriculumService.getAllCategoriesByCurriculum("1")).thenReturn(null);
		mockMvc.perform(get("/curriculum/1/categories")).andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	@Test
	void test_getAllCategoriesByID_curriculumNotFound() throws Exception {
		lenient().when(curriculumService.getAllCategoriesByCurriculum("2")).thenThrow(CurriculumNotFoundException.class);
		mockMvc.perform(get("/curriculum/2/categories")).andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	@Test
	void test_getAllCategoriesByID_emptyID() throws Exception {
		lenient().when(curriculumService.getAllCategoriesByCurriculum(" ")).thenThrow(EmptyParameterException.class);
		mockMvc.perform(get("/curriculum/ /categories")).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void test_getAllCategoriesByID_badID() throws Exception {
		lenient().when(curriculumService.getAllCategoriesByCurriculum("test")).thenThrow(BadParameterException.class);
		mockMvc.perform(get("/curriculum/test/categories")).andExpect(MockMvcResultMatchers.status().is(400));
	}
		
}
