package com.revature.app.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.springframework.http.MediaType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.app.CurriculaVisualizationToolApplication;
import com.revature.app.dto.VisualizationDTO;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CurriculaVisualizationToolApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
class VisualizationControllerTest {

	@Autowired
	private MockMvc mockmvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	private ObjectMapper objectmapper;

	@BeforeEach
	void setup() {
		this.mockmvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		this.objectmapper = new ObjectMapper();
	}

	@Test
	@Order(1)
	@Transactional
	@Commit
	void CreateEndpoint() throws Exception {
		VisualizationDTO newuser = new VisualizationDTO();
		newuser.setTitle("firstuser");

		objectmapper = new ObjectMapper();
		String Jsondto = objectmapper.writeValueAsString(newuser);
		MockHttpSession session = new MockHttpSession();

		MockHttpServletRequestBuilder build = MockMvcRequestBuilders.post("/visualization")
				.contentType(MediaType.APPLICATION_JSON).content(Jsondto).session(session);

		this.mockmvc.perform(build).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	@Order(2)
	@Transactional
	@Commit
	void FindEndpoint() throws Exception {

		VisualizationDTO newuser = new VisualizationDTO();
		newuser.setTitle("firstuser2");

		objectmapper = new ObjectMapper();
		String Jsondto = objectmapper.writeValueAsString(newuser);
		MockHttpSession session = new MockHttpSession();

		MockHttpServletRequestBuilder build = MockMvcRequestBuilders.put("/visualization/1")
				.contentType(MediaType.APPLICATION_JSON).content(Jsondto).session(session);

		mockmvc.perform(build).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	@Order(3)
	@Transactional
	@Commit
	void FindEndpointInvalidVisualizationDoNotExist() throws Exception {
		mockmvc.perform(get("/visualization/35")).andExpect(MockMvcResultMatchers.status().isNotFound());

	}

	@Test
	@Order(4)
	@Transactional
	@Commit
	void UpdateEndpointvalidVisualization() throws Exception {

		VisualizationDTO newuser = new VisualizationDTO();
		newuser.setTitle("firstuser");

		objectmapper = new ObjectMapper();
		String Jsondto = objectmapper.writeValueAsString(newuser);
		MockHttpSession session = new MockHttpSession();

		MockHttpServletRequestBuilder build = MockMvcRequestBuilders.put("/visualization/1")
				.contentType(MediaType.APPLICATION_JSON).content(Jsondto).session(session);

		mockmvc.perform(build).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	@Order(5)
	@Transactional
	@Commit
	void UpdateEndpointInvalidVisualization() throws Exception {

		VisualizationDTO newuser = new VisualizationDTO();
		newuser.setTitle("firstuser");

		objectmapper = new ObjectMapper();
		String Jsondto = objectmapper.writeValueAsString(newuser);
		MockHttpSession session = new MockHttpSession();

		MockHttpServletRequestBuilder build = MockMvcRequestBuilders.put("/visualization/98")
				.contentType(MediaType.APPLICATION_JSON).content(Jsondto).session(session);
		mockmvc.perform(build).andExpect(MockMvcResultMatchers.status().isNotFound());

	}

	@Test
	@Order(6)
	@Transactional
	@Commit

	void deleteEndpointvalidVisualization() throws Exception {

		mockmvc.perform(delete("/visualization/1")).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	@Order(7)
	@Transactional
	@Commit
	void deleteEndpointInvalidVisualization() throws Exception {

		mockmvc.perform(delete("/visualization/35")).andExpect(MockMvcResultMatchers.status().isNotFound());

	}

	@Test
	@Order(8)
	@Transactional
	@Commit
	void GetallEndpointvalidVisualizations() throws Exception {

		mockmvc.perform(get("/visualization")).andExpect(MockMvcResultMatchers.status().isOk());

	}

}
