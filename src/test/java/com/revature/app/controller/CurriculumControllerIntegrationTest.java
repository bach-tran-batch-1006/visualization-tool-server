package com.revature.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.app.dao.CurriculumDao;
import com.revature.app.dto.CurriculumDto;
import com.revature.app.model.Category;
import com.revature.app.model.Curriculum;
import com.revature.app.model.Skill;
import com.revature.app.service.CurriculumService;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CurriculumControllerIntegrationTest {
	@Autowired
	WebApplicationContext webApplicationContext;
	
	@Autowired
	CurriculumService service;
	
	@Autowired
	CurriculumDao dao;
	
	@Autowired
	private MockMvc mockMvc;
	private ObjectMapper om;
	
	@Autowired
	EntityManagerFactory emf;
	private EntityManager em;
	
	@BeforeEach
	void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		this.om = new ObjectMapper();
		em = emf.createEntityManager();
	}
	
	public CurriculumDto generateTestDto() {
		Skill testSkill = new Skill(0, "Test", new Category(0, "TestCat", "Description"));
		ArrayList<Skill> skills = new ArrayList<Skill>();
		skills.add(testSkill);
		CurriculumDto testDto = new CurriculumDto("TestCurriculum", skills);
		return testDto;
	}
	
	public MockHttpServletRequestBuilder getHttpRequest(String path, CurriculumDto params) throws JsonProcessingException {
		String contentString = om.writeValueAsString(params);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(path)
				.contentType(MediaType.APPLICATION_JSON)
				.content(contentString);
		
		return request;
	}
	
	public MockHttpServletRequestBuilder postHttpRequest(String path, CurriculumDto params) throws JsonProcessingException {
		String contentString = om.writeValueAsString(params);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(path)
				.contentType(MediaType.APPLICATION_JSON)
				.content(contentString);
		
		return request;
	}
	
	public MockHttpServletRequestBuilder putHttpRequest(String path, CurriculumDto params) throws JsonProcessingException {
		String contentString = om.writeValueAsString(params);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(path)
				.contentType(MediaType.APPLICATION_JSON)
				.content(contentString);
		
		return request;
	}
	
	public MockHttpServletRequestBuilder deleteHttpRequest(String path, CurriculumDto params) throws JsonProcessingException {
		String contentString = om.writeValueAsString(params);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(path)
				.contentType(MediaType.APPLICATION_JSON)
				.content(contentString);
		
		return request;
	}
	
	public MvcResult performTest(MockHttpServletRequestBuilder actual, int status, CurriculumDto expected) throws Exception {
		Curriculum expectedOb = new Curriculum(expected);
		expectedOb.setCurriculumId(1);
		String expectedAsJson = om.writeValueAsString(expectedOb);
		
		return this.mockMvc.perform(actual).andExpect(MockMvcResultMatchers.status().is(status))
				.andExpect(MockMvcResultMatchers.content().json(expectedAsJson)).andReturn();
	}
	
	public MvcResult performTest(MockHttpServletRequestBuilder actual, int status, List<Curriculum> expected) throws Exception {
		String expectedAsJson = om.writeValueAsString(expected);
		
		return this.mockMvc.perform(actual).andExpect(MockMvcResultMatchers.status().is(status))
				.andExpect(MockMvcResultMatchers.content().json(expectedAsJson)).andReturn();
	}
	
	@Test
	@Order(0)
	@Transactional
	@Commit
	void test_addCurriculum_success() throws Exception {
		CurriculumDto expected = generateTestDto();
		MockHttpServletRequestBuilder request = postHttpRequest("/curriculum", expected);
		performTest(request, 200, expected);
	}

	@Disabled
	@Test
	@Order(1)
	@Transactional
	void test_getCurriculumById_success() throws Exception {
		CurriculumDto expected = generateTestDto();
		MockHttpServletRequestBuilder request = getHttpRequest("/curriculum/1", expected);
		performTest(request, 200, expected);
	}
	
	@Disabled
	@Test
	@Order(2)
	@Transactional
	void test_getAllCurriculum_success() throws Exception {
		CurriculumDto secondDto = generateTestDto();
		secondDto.setName("AnotherOne");
		CurriculumDto firstDto = generateTestDto();
		ArrayList<Curriculum> expected = new ArrayList<Curriculum>();
		expected.add(new Curriculum(firstDto));
		expected.add(new Curriculum(secondDto));
		
		//temporarily save a second Curriculum to the db
		em.getTransaction().begin();
		em.persist(new Curriculum(secondDto));
		em.getTransaction().commit();
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.get("/curriculum");
		
		performTest(request, 200, expected);
	}
	
	@Disabled
	@Test
	@Order(3)
	@Transactional
	void test_updateCurriculumById_success() throws Exception {
		CurriculumDto expected = generateTestDto();
		expected.setName("updateSuccess");
		MockHttpServletRequestBuilder request = putHttpRequest("/curriculum/1", expected);
		performTest(request, 200, expected);
	}
	
	@Disabled
	@Test
	@Order(4)
	@Transactional
	void test_deleteCurriculumById_success() throws Exception {
		CurriculumDto expected = generateTestDto();
		MockHttpServletRequestBuilder request = deleteHttpRequest("/curriculum/1", expected);
		performTest(request, 200, expected);
	}
}