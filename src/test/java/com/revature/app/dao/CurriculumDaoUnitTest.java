package com.revature.app.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;

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
import org.springframework.transaction.annotation.Transactional;

import com.revature.app.model.Category;
import com.revature.app.model.Curriculum;
import com.revature.app.model.Skill;
import com.revature.app.model.Visualization;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
class CurriculumDaoUnitTest {
	
	//testing with sonarCloud
	@Autowired
	private CurriculumDao curriculumDao;

	@Autowired
	EntityManagerFactory emf;

	private EntityManager em;

	@BeforeEach
	public void setup() {
		em = emf.createEntityManager();
	}

	@Test
	@Transactional
	@Commit
	@Order(0)
	void test_addCurriculum_success() {
		Curriculum actual = curriculumDao.save(new Curriculum(0, "BackEnd Developer", new ArrayList<>()));
		Curriculum expected = new Curriculum(1, "BackEnd Developer", new ArrayList<>());
		assertEquals(expected, actual);
	}

	@Test
	@Transactional
	@Commit
	@Order(1)
	void test_getCurriculumbyID_success() {

		Curriculum actual = curriculumDao.getById(1);
		Curriculum expected = new Curriculum(1, "BackEnd Developer", new ArrayList<>());
		assertEquals(expected, actual);
	}

	@Test
	@Transactional
	@Commit
	@Order(2)
	void test_getAllCurriculum_success() {
		curriculumDao.save(new Curriculum(0, "BackEnd Developer", new ArrayList<>()));
		List<Curriculum> actual = curriculumDao.findAll();
		System.out.println("actual " + actual);

		List<Curriculum> expected = new ArrayList<>();
		expected.add(new Curriculum(1, "BackEnd Developer", new ArrayList<>()));
		expected.add(new Curriculum(2, "BackEnd Developer", new ArrayList<>()));

		assertEquals(expected, actual);
	}

	@Test
	@Transactional
	@Commit
	@Order(3)
	void test_updateCurriculumByID_success() {
		curriculumDao.findByCurriculumId(1);
		Curriculum actual = curriculumDao.save(new Curriculum(1, "update Developer", new ArrayList<>()));
		Curriculum expected = new Curriculum(1, "update Developer", new ArrayList<>());
		assertEquals(expected, actual);
	}

	@Test
	@Transactional
	@Commit
	@Order(4)
	void test_deleteCurriculumByID_success() {
		curriculumDao.deleteById(1);
		
		Curriculum actual = curriculumDao.findByCurriculumId(1);
		Curriculum expected = null;
		assertEquals(expected, actual);
	}
	
	
	@Test
	@Commit
	@Order(50)
	void test_skillVisList() {
		Session session = em.unwrap(Session.class);
		
		//Add a category to the database, all skills will share this
		Category testCat1 = new Category(0, "TestCat1", "TestDescription");
		Category testCat2 = new Category(0, "TestCat2", "TestDescription");
		Category testCat3 = new Category(0, "TestCat3", "TestDescription");
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
		Skill testSkill3 = new Skill(0, "TestSkill3", session.get(Category.class, 1));
		Skill testSkill4 = new Skill(0, "TestSkill4", session.get(Category.class, 3));
		Skill testSkill5 = new Skill(0, "TestSkill5", session.get(Category.class, 2));
		em.getTransaction().begin();
		em.persist(testSkill1);
		em.getTransaction().commit();
		em.getTransaction().begin();
		em.persist(testSkill2);
		em.getTransaction().commit();
		em.getTransaction().begin();
		em.persist(testSkill3);
		em.getTransaction().commit();
		em.getTransaction().begin();
		em.persist(testSkill4);
		em.getTransaction().commit();
		em.getTransaction().begin();
		em.persist(testSkill5);
		em.getTransaction().commit();
		
		//Add 2 curriculums to the database where the second skill is shared
		ArrayList<Skill> skillList = new ArrayList<Skill>();
		skillList.add(session.get(Skill.class, 1));
		skillList.add(session.get(Skill.class, 2));
		skillList.add(session.get(Skill.class, 3));
		skillList.add(session.get(Skill.class, 4));
		skillList.add(session.get(Skill.class, 5));
		Curriculum testCurr = new Curriculum(0, "TestCurriculum1", skillList);
		em.getTransaction().begin();
		em.persist(testCurr);
		em.getTransaction().commit();
		
		
		//Create the expected list of categories
		ArrayList<Category> expected = new ArrayList<Category>(); 
		expected.add(session.get(Category.class, 1)); 
		expected.add(session.get(Category.class, 2)); 
		expected.add(session.get(Category.class, 3)); 
		
		//Sanity check
		System.out.println(session.get(Curriculum.class, 3));
		
		//Now actually test the method
		List<Category> actual = curriculumDao.catCurList(3);
		assertEquals(expected, (ArrayList<Category>) actual);
	}
	
	
}
