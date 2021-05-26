package com.revature.app.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.app.model.Curriculum;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class CurriculumDaoUnitTest {
	
	@Autowired
	private CurriculumDao curriculumDao;

	@BeforeAll
	public static void setUp(){

	}

	@Test
	void test_addCurriculum_success() {
		Curriculum actual = curriculumDao.save(new Curriculum(0, "BackEnd Developer", null));
		Curriculum expected = new Curriculum(1, "BackEnd Developer", null);
		assertEquals(expected, actual);
	}

}
