package com.revature.app.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

import org.junit.jupiter.api.Assertions;
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


import com.revature.app.model.Curriculum;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class CurriculumDaoUnitTest {
	
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

}
