package com.revature.app.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.app.dao.SkillDAO;
import com.revature.app.dto.SkillDTO;
import com.revature.app.model.Category;
import com.revature.app.model.Curriculum;
import com.revature.app.model.Skill;
import com.revature.app.service.SkillService;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
@ActiveProfiles("test")
@SpringBootTest
class SkillIntegrationTest {

	@Autowired
	WebApplicationContext webApplicationContext;
	
	@Autowired
	SkillService skillService;
	
	@Autowired
	SkillDAO skillDAO;
	
	@Autowired
	EntityManagerFactory emf;
	private EntityManager em;

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	
	
	
	@BeforeEach
	void setup() {
		//this.skillService = new SkillService();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		this.objectMapper = new ObjectMapper();
		em = emf.createEntityManager();
	}

	
	@Test
	@Order(2)
	@Transactional
	void test_getAllSkills_happy() throws Exception {
		Category testCat = new Category(1, "TestCat", "Description");
		SkillDTO skillDTO = new SkillDTO("TestSkill", testCat);
		Skill skill1 = new Skill(skillDTO);
		skill1.setSkillId(1);
		List<Skill> expected = new ArrayList<Skill>();
		expected.add(skill1);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/allSkills");
		
		this.mockMvc
			.perform(builder)
			.andExpect(MockMvcResultMatchers.status().is(200));
	}

//
	@Test
	@Order(2)
	@Transactional
	void test_getSkillByID_happy() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/skill/1");
		
		this.mockMvc
			.perform(builder)
			.andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	@Test
	@Order(0)
	@Transactional
	void test_getSkillbyID_BadID() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/skill/1");
		
		this.mockMvc
			.perform(builder)
			.andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	@Test
	@Order(2)
	void test_getSkillbyID_BadParameter() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/skill/test");

		this.mockMvc
			.perform(builder)
			.andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	@Order(2)
	void test_getSkillbyID_EmptyParameter() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/skill/  ");

		this.mockMvc
			.perform(builder)
			.andExpect(MockMvcResultMatchers.status().is(400));
	}
	
//	
	@Test
	@Order(1)
	@Transactional
	@Commit
	void test_addSkill_happy() throws Exception {
		Category testCat = new Category(0, "TestCat", "Description");
		em.getTransaction().begin();
		em.persist(testCat);
		em.getTransaction().commit();
		
		Session session = em.unwrap(Session.class);
		
		SkillDTO skillDTO = new SkillDTO("TestSkill", session.get(Category.class, 1));
		String skillDTOJson = objectMapper.writeValueAsString(skillDTO);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/skill")
				.contentType(MediaType.APPLICATION_JSON)
				.content(skillDTOJson);
		
		Skill expected = new Skill(1, "TestSkill", testCat);
		String expectedJsonResponse = objectMapper.writeValueAsString(expected);
		
		this.mockMvc
			.perform(builder)
			.andExpect(MockMvcResultMatchers.status().is(201))
			.andExpect(MockMvcResultMatchers.content().json(expectedJsonResponse)).andReturn();
	}
	
	@Test
	@Order(1)
	@Transactional
	void test_addSkill_emptyName() throws Exception {
		Category testCat = new Category(1, "TestCat", "Description");
		SkillDTO skillDTO = new SkillDTO("   ", testCat);
		String skillDTOJson = objectMapper.writeValueAsString(skillDTO);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/skill")
				.contentType(MediaType.APPLICATION_JSON)
				.content(skillDTOJson);

		this.mockMvc
			.perform(builder)
			.andExpect(MockMvcResultMatchers.status().is(400)).andReturn();
	}
	
	
//	
	@Test
	@Order(3)
	@Transactional
	@Commit
	void test_updateSkill_happy() throws Exception {
		Category testCat = new Category(1, "TestCat", "Description");
		SkillDTO skillDTO = new SkillDTO("UpdatedTestSkill", testCat);
		String skillDTOJson = objectMapper.writeValueAsString(skillDTO);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.put("/skill/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(skillDTOJson);
		
		Skill expected = new Skill(1, "UpdatedTestSkill", testCat);
		String expectedJsonResponse = objectMapper.writeValueAsString(expected);
		
		this.mockMvc
			.perform(builder)
			.andExpect(MockMvcResultMatchers.status().is(202))
			.andExpect(MockMvcResultMatchers.content().json(expectedJsonResponse)).andReturn();
	}
	
	@ParameterizedTest
	@Order(3)
	@Transactional
	@CsvSource({
		" , 1",
		"UpdatedTestSkill, ",
		"UpdatedTestSkill, test"
	})
	void test_updateSkill_negativeTests(String dtoName, String path) throws Exception {
		if(dtoName == null) {
			dtoName = " ";
		}
		if(path == null) {
			path = " ";
		}
		//We are trying to test for empty values rather than null, but csvSource cannot differentiate between the two without
		//changing files that are ignored by the github
		Category testCat = new Category(1, "TestCat", "Description");
		SkillDTO skillDTO = new SkillDTO(dtoName, testCat);
		String skillDTOJson = objectMapper.writeValueAsString(skillDTO);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.put("/skill/" + path)
				.contentType(MediaType.APPLICATION_JSON)
				.content(skillDTOJson);

		this.mockMvc
			.perform(builder)
			.andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	
	@Test
	@Order(0)
	@Transactional
	void test_updateSkill_skillDoesNotExist() throws Exception {
		Category testCat = new Category(1, "TestCat", "Description");
		SkillDTO skillDTO = new SkillDTO("UpdatedTestSkill", testCat);
		String skillDTOJson = objectMapper.writeValueAsString(skillDTO);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.put("/skill/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(skillDTOJson);
	
		this.mockMvc
			.perform(builder)
			.andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	

//	
	@Test
	@Order(4)
	@Transactional
	void test_deleteSkill_happy() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.delete("/skill/1");
		this.mockMvc
			.perform(builder)
			.andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	@Test
	@Order(0)
	@Transactional
	void test_deleteSkill_skillDoesNotExist() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.delete("/skill/1");

		this.mockMvc
			.perform(builder)
			.andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	@Test
	@Order(4)
	@Transactional
	void test_deleteSkill_emptyParameter() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.delete("/skill/  ");
		
		this.mockMvc
			.perform(builder)
			.andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	@Order(4)
	@Transactional
	void test_deleteSkill_badParameter() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.delete("/skill/test");

		this.mockMvc
			.perform(builder)
			.andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	@Order(50)
	void test_deleteSkill_foreignKeyFailure() throws Exception {
		Session session = em.unwrap(Session.class);
		
		//Add a new skill to the database directly that will fail to be deleted in the test
		Skill testSkill = new Skill(0, "TestForeignSkill", session.get(Category.class, 1));
		em.getTransaction().begin();
		em.persist(testSkill);
		em.getTransaction().commit();
		
		//Add a curriculum that holds the skill
		ArrayList<Skill> skillList = new ArrayList<Skill>();
		skillList.add(session.get(Skill.class, 2));
		Curriculum testCurr = new Curriculum(0, "TestCurr", skillList);
		em.getTransaction().begin();
		em.persist(testCurr);
		em.getTransaction().commit();
		
		//Print out the category and skill as a sanity check
		System.out.println(session.get(Skill.class, 2));
		System.out.println(session.get(Curriculum.class, 1));
		
		//Now to test the method
		this.mockMvc.perform(delete("/skill/2")).andExpect(MockMvcResultMatchers.status().is(400));

	}
	

}
