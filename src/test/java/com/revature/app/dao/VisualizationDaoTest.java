package com.revature.app.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.app.model.Category;
import com.revature.app.model.Curriculum;
import com.revature.app.model.Skill;
import com.revature.app.model.Visualization;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class VisualizationDaoTest {

	@Autowired
	private VisualizationDao visualDao;
	
	@Autowired EntityManagerFactory emf;
	
	private EntityManager em;
	
	@BeforeEach
	public void setup() {
		em = emf.createEntityManager();
	}

	// Initial Tests

	@Test
	@Order(0)
	void testVisualizationListEmpty() {
		List<Visualization> list = visualDao.findAll();

		assertEquals(0, list.size());
	}

	// creates

	@Test
	@Commit
	@Order(10)
	void testCreateVisualization() {
		String payload = "test";
		List<Curriculum> list = new ArrayList<>();
		
		Visualization expected = new Visualization();
		
		
		expected.setVisualizationName(payload);
		expected.setCurriculumList(list);

		Visualization actual = visualDao.save(expected);

		assertNotEquals(0, actual.getVisualizationId());
		assertEquals(payload, actual.getVisualizationName());

	}

	@Test
	@Order(15)
	void testVisualizationListNotEmpty() {
		List<Visualization> list = visualDao.findAll();

		assertNotEquals(0, list.size());
	}

	// reads

	@Test
	@Order(20)
	void testGetValidVisualization() {
		Visualization actual = visualDao.getById(1);

		assertNotNull(actual);
		assertEquals(1, actual.getVisualizationId());
	}

	@Test
	@Order(21)
	void testGetInvalidVisualization() {
		try {
			Visualization v = visualDao.getById(Integer.MAX_VALUE);
			System.out.println(v);
			fail("Exception not caught");
		} catch (javax.persistence.EntityNotFoundException e) {
			assertEquals("Unable to find com.revature.app.model.Visualization with id 2147483647", e.getMessage());
		}
	}

	// updates

	@Test
	@Commit
	@Order(30)
	void testUpdateValidVisualization() {
		String payload = "new name";
		Visualization actual = visualDao.getById(1);
		actual.setVisualizationName(payload);
		Visualization expected = visualDao.save(actual);

		assertNotNull(expected);
		assertEquals(payload, expected.getVisualizationName());
	}

	// deletes
	@Test
	@Commit
	@Order(40)
	void testDeleteValidVisualization() {
		visualDao.deleteById(1);
	}

	@Test
	@Commit
	@Order(41)
	void testDeleteInvalidVisualization() {
		try {
			Visualization v = visualDao.getById(Integer.MAX_VALUE);
			 System.out.println(v);
			 fail("Exception not caught");
		} catch (javax.persistence.EntityNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	@Test
	@Commit
	@Order(50)
	void test_skillVisList() {
		Session session = em.unwrap(Session.class);
		
		//Add a categories to the database
		Category testCat1 = new Category(0, "TestCat1", "TestDescription");
		Category testCat2 = new Category(0, "TestCat1", "TestDescription");
		Category testCat3 = new Category(0, "TestCat1", "TestDescription");
		em.getTransaction().begin();
		em.persist(testCat1);
		em.getTransaction().commit();
		em.getTransaction().begin();
		em.persist(testCat2);
		em.getTransaction().commit();
		em.getTransaction().begin();
		em.persist(testCat3);
		em.getTransaction().commit();
		
		//Add 3 skills to the database
		Skill testSkill1 = new Skill(0, "TestSkill1", session.get(Category.class, 1));
		Skill testSkill2 = new Skill(0, "TestSkill2", session.get(Category.class, 2));
		Skill testSkill3 = new Skill(0, "TestSkill3", session.get(Category.class, 3));
		em.getTransaction().begin();
		em.persist(testSkill1);
		em.getTransaction().commit();
		em.getTransaction().begin();
		em.persist(testSkill2);
		em.getTransaction().commit();
		em.getTransaction().begin();
		em.persist(testSkill3);
		em.getTransaction().commit();
		
		//Add 2 curriculums to the database where the second skill is shared
		ArrayList<Skill> skillList1 = new ArrayList<Skill>();
		skillList1.add(session.get(Skill.class, 1));
		skillList1.add(session.get(Skill.class, 2));
		ArrayList<Skill> skillList2 = new ArrayList<Skill>();
		skillList2.add(session.get(Skill.class, 2));
		skillList2.add(session.get(Skill.class, 3));
		Curriculum testCurr1 = new Curriculum(0, "TestCurriculum1", skillList1);
		Curriculum testCurr2 = new Curriculum(0, "TestCurriculum2", skillList2);
		em.getTransaction().begin();
		em.persist(testCurr1);
		em.getTransaction().commit();
		em.getTransaction().begin();
		em.persist(testCurr2);
		em.getTransaction().commit();
		
		//Re-add a visualization to the database that holds the two curriculums
		ArrayList<Curriculum> currList = new ArrayList<Curriculum>();
		currList.add(session.get(Curriculum.class, 1));
		currList.add(session.get(Curriculum.class, 2));
		Visualization visTest = new Visualization(0, "TestVis", currList);
		Visualization sanityCheck = visualDao.save(visTest);
		//Because of previous tests, the id of testVis will be 2 and not 1 
		
		//Create the expected list of skills
		ArrayList<Skill> expected = new ArrayList<Skill>(); 
		expected.add(session.get(Skill.class, 1)); 
		expected.add(session.get(Skill.class, 2)); 
		expected.add(session.get(Skill.class, 3)); 
		
		//Print out the sanityCheck to make sure that everything is persisted in the database
		System.out.println(sanityCheck);
		
		//Now actually test the method
		List<Skill> actual = visualDao.skillVisList(2);
		assertEquals(expected, (ArrayList<Skill>) actual);
	}
	
	@Test
	@Commit
	@Order(51)
	void test_catVisList() {
		Session session = em.unwrap(Session.class);
		
		//Print out the sanityCheck to make sure that everything is persisted in the database
		Visualization sanityCheck = session.get(Visualization.class, 2);
		System.out.println(sanityCheck);
		
		//Create the expected list of categories
		ArrayList<Category> expected = new ArrayList<Category>();
		expected.add(session.get(Category.class, 1)); 
		expected.add(session.get(Category.class, 2)); 
		expected.add(session.get(Category.class, 3)); 
		
		//Now actually test the method
		List<Category> actual = visualDao.catVisList(2);
		assertEquals(expected, (ArrayList<Category>) actual);
	}

	
	
	
}
