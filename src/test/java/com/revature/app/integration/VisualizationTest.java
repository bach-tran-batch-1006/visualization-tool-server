package com.revature.app.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
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
import com.revature.app.dao.VisualizationDao;
import com.revature.app.dto.VisualizationDTO;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.VisualizationNotFoundException;
import com.revature.app.model.Category;
import com.revature.app.model.Curriculum;
import com.revature.app.model.Skill;
import com.revature.app.model.Visualization;
import com.revature.app.service.VisualizationService;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
@ActiveProfiles("test")
@SpringBootTest
class VisualizationTest {
	@Autowired
	WebApplicationContext webapplicationcontext;

	private MockMvc mockmvc;

	private ObjectMapper objectmapper;

	@Autowired
	private VisualizationService mockservice;

	@Autowired
	VisualizationDao visualizationdao;

	@Autowired
	EntityManagerFactory emf;
	private EntityManager em;

	@Test
	@Order(1)
	@Transactional
	@Commit
	void findVisualizationDoNotExist() throws Exception {


		
		MockHttpServletRequestBuilder build = MockMvcRequestBuilders.get("/visualization/98");
			

		this.mockmvc.perform(build).andExpect(MockMvcResultMatchers.status().isNotFound());

	}

	@BeforeEach
	void setup() throws BadParameterException, VisualizationNotFoundException {
		this.mockmvc = MockMvcBuilders.webAppContextSetup(this.webapplicationcontext).build();
		this.objectmapper = new ObjectMapper();
		em = emf.createEntityManager();
	}

	@Test
	@Order(2)
	@Transactional
	@Commit
	void CreateEndpoint() throws Exception {

		Category testcat = new Category(0, "testcat", "hopethiswork");
		Skill skill1 = new Skill(0, "testskill", testcat);

		em.getTransaction().begin();
		em.persist(testcat);
		em.getTransaction().commit();

		em.getTransaction().begin();
		em.persist(skill1);
		em.getTransaction().commit();

		List<Skill> skillList = new ArrayList();
		skillList.add(skill1);

		Curriculum curriculum = new Curriculum(0, "testname", skillList);

		em.getTransaction().begin();
		em.persist(curriculum);
		em.getTransaction().commit();

		List<Curriculum> curlist = new ArrayList<>();
		curlist.add(curriculum);

		VisualizationDTO vsdto = new VisualizationDTO("first", curlist);
		Visualization expected = new Visualization(1, "first", curlist);

		objectmapper = new ObjectMapper();
		String Jsondto = objectmapper.writeValueAsString(vsdto);

		String vsExpected = this.objectmapper.writeValueAsString(expected);

		MockHttpServletRequestBuilder build = MockMvcRequestBuilders.post("/visualization")
				.contentType(MediaType.APPLICATION_JSON).content(Jsondto);

		this.mockmvc.perform(build).andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.content().json(vsExpected));

	}

	@Test
	@Order(3)
	@Transactional
	@Commit
	void CreateBlank() throws Exception {

		List<Skill> skillList = new ArrayList();
		Curriculum curriculum = new Curriculum(0, "testname", skillList);

		em.getTransaction().begin();
		em.persist(curriculum);
		em.getTransaction().commit();

		List<Curriculum> list = new ArrayList<>();
		// list.add(curriculum);
		VisualizationDTO vsdto = new VisualizationDTO("", list);
		Visualization expected = new Visualization(1, "first", list);

		objectmapper = new ObjectMapper();
		String Jsondto = objectmapper.writeValueAsString(vsdto);

		String vsExpected = this.objectmapper.writeValueAsString(expected);

		MockHttpServletRequestBuilder build = MockMvcRequestBuilders.post("/visualization")
				.contentType(MediaType.APPLICATION_JSON).content(Jsondto);

		this.mockmvc.perform(build).andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

	@Test
	@Order(4)
	@Transactional
	@Commit
	void findVisualization() throws Exception {

		
		Session session = em.unwrap(Session.class);
		Visualization expected= (session.get(Visualization.class, 1));
		

		objectmapper = new ObjectMapper();

		String vsExpected = this.objectmapper.writeValueAsString(expected);

		MockHttpServletRequestBuilder build = MockMvcRequestBuilders.get("/visualization/1");

		this.mockmvc.perform(build).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(vsExpected));

	}
	
	
	@Test
	@Order(5)
	@Transactional
	@Commit
	void updateVisualization() throws Exception {

		
		Session session = em.unwrap(Session.class);
		Visualization expected= (session.get(Visualization.class, 1));
		VisualizationDTO vsdto= new VisualizationDTO("newname", expected.getCurriculumList());

		
	   Visualization newvs= expected;
	   newvs.setVisualizationName("newname");
       String Jsondto = this.objectmapper.writeValueAsString(vsdto);
		String vsExpected = this.objectmapper.writeValueAsString(newvs);

		MockHttpServletRequestBuilder build = MockMvcRequestBuilders.put("/visualization/1")
				.contentType(MediaType.APPLICATION_JSON).content(Jsondto);
		
		
		this.mockmvc.perform(build).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(vsExpected));
		
	}
	
	@Test
	@Order(6)
	@Transactional
	@Commit
	void updateVisualizationDoNotExist() throws Exception {

		
		Session session = em.unwrap(Session.class);
		Visualization expected= (session.get(Visualization.class, 1));
		VisualizationDTO vsdto= new VisualizationDTO("newname", expected.getCurriculumList());

		
	
       String Jsondto = this.objectmapper.writeValueAsString(vsdto);
	

		MockHttpServletRequestBuilder build = MockMvcRequestBuilders.put("/visualization/98")
				.contentType(MediaType.APPLICATION_JSON).content(Jsondto);
		
		
		this.mockmvc.perform(build).andExpect(MockMvcResultMatchers.status().isNotFound());
		
		
	}
	@Test
	@Order(7)
	@Transactional
	@Commit
	void updateVisualizationBlankName() throws Exception {

		
		Session session = em.unwrap(Session.class);
		Visualization expected= (session.get(Visualization.class, 1));
		VisualizationDTO vsdto= new VisualizationDTO("", expected.getCurriculumList());

		
	
       String Jsondto = this.objectmapper.writeValueAsString(vsdto);
	

		MockHttpServletRequestBuilder build = MockMvcRequestBuilders.put("/visualization/1")
				.contentType(MediaType.APPLICATION_JSON).content(Jsondto);
		
		
		this.mockmvc.perform(build).andExpect(MockMvcResultMatchers.status().isBadRequest());
		
		
	}

	
	
	@Test
	@Order(8)
	@Transactional
	@Commit
	void getallVisualization() throws Exception {

		
		Session session = em.unwrap(Session.class);
		Visualization expected= (session.get(Visualization.class, 1));
		
		List<Visualization> result = new ArrayList<>();
		result.add(expected);
		VisualizationDTO vsdto= new VisualizationDTO("newname", expected.getCurriculumList());

	
       String Jsondto = this.objectmapper.writeValueAsString(vsdto);
		String vsExpected = this.objectmapper.writeValueAsString(result);

		MockHttpServletRequestBuilder build = MockMvcRequestBuilders.get("/visualization");
				
		
		
		this.mockmvc.perform(build).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(vsExpected));
		
	}
	
	@Test
	@Order(9)
	@Transactional
	@Commit
	void DeleteVisualization() throws Exception {

		MockHttpServletRequestBuilder build = MockMvcRequestBuilders.delete("/visualization/1")
				.contentType(MediaType.APPLICATION_JSON).content("1");

		this.mockmvc.perform(build).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	@Order(10)
	@Transactional
	@Commit
	void DeleteVisualizationDoNotExist() throws Exception {

		MockHttpServletRequestBuilder build = MockMvcRequestBuilders.delete("/visualization/98");

		this.mockmvc.perform(build).andExpect(MockMvcResultMatchers.status().isNotFound());

	}
	

}
