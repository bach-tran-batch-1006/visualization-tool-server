/*package com.revature.app.controller;

//import static org.mockito.Mockito.lenient;
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
import com.revature.app.dto.ProjectDto;
import com.revature.app.exception.BadParameterException;
//import com.revature.app.exception.CurriculumNotFoundException;
import com.revature.app.exception.EmptyParameterException;
//import com.revature.app.exception.ForeignKeyConstraintException;
import com.revature.app.exception.ProjectNotFoundException;
//import com.revature.app.service.CurriculumService;
import com.revature.app.service.ProjectsService;

@ExtendWith(MockitoExtension.class)*/
class ProjectControllerUnitTest {

	/*private MockMvc mockMvc;

	@Mock
	ProjectsService pServ;

	@InjectMocks
	private ProjectController pController;

	private ObjectMapper om = new ObjectMapper();
	
	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(pController).build();
	}
	
	@Test
	void test_addProject_negative_BlankExceptionWithStatusCode() throws Exception {
		
		ProjectDto input = new ProjectDto(" ", new ArrayList<Integer>());
		String inputJson = om.writeValueAsString(input);
		
		when(pServ.addProject(input)).thenThrow(EmptyParameterException.class);
		
		this.mockMvc.perform(post("/projects").contentType(MediaType.APPLICATION_JSON).content(inputJson))
		.andExpect(status().isBadRequest());
	}
		
	@Test
	void test_getProjectByID_negative_BlankExceptionWithStatusCode() throws Exception {
		when(pServ.getProjectByID("1000")).thenThrow(ProjectNotFoundException.class);
		mockMvc.perform(get("/projects/1000")).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	
	@Test
	void test_getProjectByID_badID() throws Exception {
		when(pServ.getProjectByID("test")).thenThrow(BadParameterException.class);
		mockMvc.perform(get("/projects/test")).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void test_getProjectByID_emptyID() throws Exception {
		when(pServ.getProjectByID(" ")).thenThrow(EmptyParameterException.class);
		mockMvc.perform(get("/projects/ ")).andExpect(MockMvcResultMatchers.status().is(400));
	}

//
	@Test
	void test_updateProject_projectNotFound() throws Exception {
		ProjectDto input = new ProjectDto("testProject", new ArrayList<Integer>());
		String body = om.writeValueAsString(input);
		when(pServ.updateProjectByID("2", input)).thenThrow(ProjectNotFoundException.class);
		mockMvc.perform(
				put("/projects/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body)
				).andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	@Test
	void test_updateProject_badID() throws Exception {
		ProjectDto input = new ProjectDto("testProjects", new ArrayList<Integer>());
		String body = om.writeValueAsString(input);
		when(pServ.updateProjectByID("test", input)).thenThrow(BadParameterException.class);
		mockMvc.perform(
				put("/projects/test")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body)
				).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void test_updateProject_emptyID() throws Exception {
		ProjectDto input = new ProjectDto("testProjects", new ArrayList<Integer>());
		String body = om.writeValueAsString(input);
		when(pServ.updateProjectByID(" ", input)).thenThrow(EmptyParameterException.class);
		mockMvc.perform(
				put("/projects/ ")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body)
				).andExpect(MockMvcResultMatchers.status().is(400));
	}

//
	@Test
	void test_deleteProject_projectNotFound() throws Exception {
		when(pServ.deleteProjectByID("2")).thenThrow(ProjectNotFoundException.class);
		mockMvc.perform(delete("/projects/2")).andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	@Test
	void test_deleteProject_badID() throws Exception {
		when(pServ.deleteProjectByID("test")).thenThrow(BadParameterException.class);
		mockMvc.perform(delete("/projects/test")).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void test_deleteProject_emptyID() throws Exception {
		when(pServ.deleteProjectByID(" ")).thenThrow(EmptyParameterException.class);
		mockMvc.perform(delete("/projects/ ")).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	/* void test_deleteProject_foreignKey() throws Exception {
		when(pServ.deleteProjectByID("3")).thenThrow(ForeignKeyConstraintException.class);
		mockMvc.perform(delete("/projects/3")).andExpect(MockMvcResultMatchers.status().is(400));
	} */

}
