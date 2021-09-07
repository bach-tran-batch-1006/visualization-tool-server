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
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.app.dto.PrimerDto;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.PrimerNotFoundException;
import com.revature.app.service.PrimerServices;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)*/
class PrimerControllerTest {

	/*private MockMvc mockMvc;

	@Mock
	PrimerServices pService;

	@InjectMocks
	private PrimerController pController;

	private ObjectMapper om = new ObjectMapper();
	
	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(pController).build();
	}
	
	@Test
	void test_addPrimer_negative_BlankExceptionWithStatusCode() throws Exception {
		
		PrimerDto input = new PrimerDto(" ", new ArrayList<Integer>());
		String inputJson = om.writeValueAsString(input);
		
		when(pService.addPrimer(input)).thenThrow(EmptyParameterException.class);
		
		this.mockMvc.perform(post("/primer").contentType(MediaType.APPLICATION_JSON).content(inputJson))
		.andExpect(status().isBadRequest());
	}
		
	@Test
	void test_getPrimerByID_negative_BlankExceptionWithStatusCode() throws Exception {
		when(pService.getPrimerByID("1000")).thenThrow(PrimerNotFoundException.class);
		mockMvc.perform(get("/primer/1000")).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	
	@Test
	void test_getPrimerByID_badID() throws Exception {
		when(pService.getPrimerByID("test")).thenThrow(BadParameterException.class);
		mockMvc.perform(get("/primer/test")).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void test_getPrimerByID_emptyID() throws Exception {
		when(pService.getPrimerByID(" ")).thenThrow(EmptyParameterException.class);
		mockMvc.perform(get("/primer/ ")).andExpect(MockMvcResultMatchers.status().is(400));
	}

//
	@Test
	void test_updatePrimer_PrimerNotFound() throws Exception {
		PrimerDto input = new PrimerDto("testPrimer", new ArrayList<Integer>());
		String body = om.writeValueAsString(input);
		when(pService.updatePrimerByID("2", input)).thenThrow(PrimerNotFoundException.class);
		mockMvc.perform(
				put("/primer/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body)
				).andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	@Test
	void test_updatePrimer_badID() throws Exception {
		PrimerDto input = new PrimerDto("testPrimer", new ArrayList<Integer>());
		String body = om.writeValueAsString(input);
		when(pService.updatePrimerByID("test", input)).thenThrow(BadParameterException.class);
		mockMvc.perform(
				put("/primer/test")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body)
				).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void test_updatePrimer_emptyID() throws Exception {
		PrimerDto input = new PrimerDto("testPrimer", new ArrayList<Integer>());
		String body = om.writeValueAsString(input);
		when(pService.updatePrimerByID(" ", input)).thenThrow(EmptyParameterException.class);
		mockMvc.perform(
				put("/primer/ ")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body)
				).andExpect(MockMvcResultMatchers.status().is(400));
	}

//
	@Test
	void test_deletePrimer_PrimerNotFound() throws Exception {
		when(pService.deletePrimerByID("2")).thenThrow(PrimerNotFoundException.class);
		mockMvc.perform(delete("/primer/2")).andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	@Test
	void test_deletePrimer_badID() throws Exception {
		when(pService.deletePrimerByID("test")).thenThrow(BadParameterException.class);
		mockMvc.perform(delete("/primer/test")).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void test_deletePrimer_emptyID() throws Exception {
		when(pService.deletePrimerByID(" ")).thenThrow(EmptyParameterException.class);
		mockMvc.perform(delete("/primer/ ")).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
//	@Test
//	void test_deleteCurriculum_foreignKey() throws Exception {
//		when(pService.deletePrimerByID("3")).thenThrow(ForeignKeyConstraintException.class);
//		mockMvc.perform(delete("/primer/3")).andExpect(MockMvcResultMatchers.status().is(400));
//	}*/

}
