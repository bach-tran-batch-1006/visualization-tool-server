package com.revature.app.dao;


/*import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;


//import org.junit.jupiter.api.BeforeEach;
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


import com.revature.app.model.Projects;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)*/
class ProjectDaoUnitTest {
	
	/*//testing with sonarCloud
	@Autowired
	private ProjectDao pDao;

//	@Autowired
//	EntityManagerFactory emf;
//
//	private EntityManager em;
//
//	@BeforeEach
//	public void setup() {
//		em = emf.createEntityManager();
//	}

	@Test
	@Transactional
	@Commit
	@Order(0)
	void test_addCurriculum_success() {
		Projects actual = pDao.save(new Projects("Project 1", new ArrayList<>()));
		Projects expected = new Projects("Project 1", new ArrayList<>());
		expected.setProjectId(1);
		assertEquals(expected, actual);
	}

	@Test
	@Transactional
	@Commit
	@Order(1)
	void test_getCurriculumbyID_success() {

		Projects actual = pDao.findById(1);
		Projects expected = new Projects("Project 1", new ArrayList<>());
		expected.setProjectId(1);
		assertEquals(expected, actual);
	}

	@Test
	@Transactional
	@Commit
	@Order(2)
	void test_getAllCurriculum_success() {
		pDao.save(new Projects("Project 2", new ArrayList<>()));
		pDao.save(new Projects("Project 3", new ArrayList<>()));
		List<Projects> actual = pDao.findAll();
		System.out.println("actual " + actual);

		List<Projects> expected = new ArrayList<>();
		Projects expected1 = new Projects("Project 1", new ArrayList<>());
		expected1.setProjectId(1);
		Projects expected2 = new Projects("Project 2", new ArrayList<>());
		expected2.setProjectId(2);
		Projects expected3 = new Projects("Project 3", new ArrayList<>());
		expected3.setProjectId(3);
		
		expected.add(expected1);
		expected.add(expected2);
		expected.add(expected3);

		assertEquals(expected, actual);
	}

	@Test
	@Transactional
	@Commit
	@Order(3)
	void test_updateCurriculumByID_success() {
		//pDao.findById(1);
		Projects actual = pDao.save(new Projects("update Developer", new ArrayList<>()));
		Projects expected = new Projects("update Developer", new ArrayList<>());
		expected.setProjectId(4);
		assertEquals(expected, actual);
	}

	@Test
	@Transactional
	@Commit
	@Order(4)
	void test_deleteCurriculumByID_success() {
		pDao.deleteById(1);
		
		Projects actual = pDao.findById(1);
		Projects expected = null;
		assertEquals(expected, actual);
	}*/
}
