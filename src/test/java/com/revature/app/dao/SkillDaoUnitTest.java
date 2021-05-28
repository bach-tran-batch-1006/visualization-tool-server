package com.revature.app.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.app.dto.SkillDTO;
import com.revature.app.model.Category;
import com.revature.app.model.Skill;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SkillDaoUnitTest {

	@Autowired
	SkillDAO skillDAO;
	
	@Autowired
	EntityManagerFactory emf;
	
	private EntityManager em;
	
	@BeforeEach
	public void setup() {
		em = emf.createEntityManager();
		
	}
	
	
	@Test
	@Transactional
	@Order(2)
	@Commit
	void test_getAllSkills_happy() {
		Session session = em.unwrap(Session.class);
		skillDAO.save(new Skill(0, "Test", session.get(Category.class, 1)));
		//Adding in another one to make sure we have two
		List<Skill> actual = skillDAO.findAll();
		assertTrue(actual.size() == 2);
	}
	
	@Test
	@Transactional
	@Order(0)
	@Commit
	void test_getAllSkills_noSkills() {
		List<Skill> actual = skillDAO.findAll();
		assertTrue(actual.size() == 0);
	}

//
	@Test
	@Transactional
	@Order(2)
	@Commit
	void test_getSkillByID_happy() {
		Session session = em.unwrap(Session.class);
		Skill expected = new Skill(1, "Test", session.get(Category.class, 1));
		Skill actual = skillDAO.findById(1);
		assertEquals(expected, actual);
	}
	
	@Test
	@Transactional
	@Order(0)
	@Commit
	void test_getSkillbyID_SkillDoesntExist() {
		Skill actual = skillDAO.findById(2);
		assertEquals(null, actual);
	}
	
//	
	@Test
	@Transactional
	@Order(1)
	@Commit
	void test_addSkill_happy() {
		Category testCat = new Category(0, "Test", "TestDescription");
		em.getTransaction().begin();
		em.persist(testCat);
		em.getTransaction().commit();
		Session session = em.unwrap(Session.class);
		Skill expected = new Skill(1, "Test", session.get(Category.class, 1));
		Skill actual = skillDAO.save(new Skill(0, "Test", session.get(Category.class, 1)));
		assertEquals(expected, actual);
	}
	
//	
	@Test
	@Transactional
	@Order(3)
	@Commit
	void test_updateSkill_happy() {
		Session session = em.unwrap(Session.class);
		SkillDTO dto = new SkillDTO("NewName", session.get(Category.class, 1));
		Skill expected = new Skill(1, "NewName", session.get(Category.class, 1));
		Skill actual = skillDAO.getById(1);
		actual.updateFromDTO(dto);
		actual = skillDAO.save(actual);
		assertEquals(actual, expected);
	}
	
	@Test
	@Transactional
	@Order(0)
	@Commit
	void test_updateSkill_IDDoesntExist() {
		Skill actual = skillDAO.findById(5);
		assertEquals(null, actual);
	}

//	
	@Test
	@Transactional
	@Order(5)
	@Commit
	void test_deleteSkill_happy() {
		Skill actual = skillDAO.findById(2);
		skillDAO.delete(actual);
		assertTrue(skillDAO.findById(2) == null);
	}
	
	@Test
	@Transactional
	@Order(0)
	@Commit
	void test_deleteSkill_skillDoesntExist() {
		Skill actual = skillDAO.findById(0);
		assertEquals(null, actual);
	}
	
}
