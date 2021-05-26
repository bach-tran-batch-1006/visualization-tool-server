package com.revature.app.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.app.dto.CurriculumDto;
import com.revature.app.model.Curriculum;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CurriculumDaoUnitTest {

	@Autowired
	private CurriculumDao curriculumDao;
	
	@BeforeAll
	public static void setUp(){
		
	}
	
	@Test
	@Disabled
	void test_addCurriculum_success() {
		Curriculum actual = curriculumDao.save(new Curriculum("Curriculum0"));
		Curriculum expected = new Curriculum("Curriculum0");
		assertEquals(expected, actual);
	}
	
	@Test
	@Disabled
	void test_getAllCurriculum_success(){
		
		//assertEquals(expected, actual);
	}
	
	@Test
	@Disabled
	void test_getCurriculumByID_success() {
		
	}
	
	@Test
	@Disabled
	void test_getCurriculumByID_notFound() {
		
	}
	
	@Test
	@Disabled
	void test_updateCurriculum_success() {
		
	}
	
	@Test
	@Disabled
	void test_updateCurriculum_ID_notFound() {
		
	}
	
	@Test
	@Disabled
	void test_deleteCurriculumByID_success() {
		
	}
	
	@Test
	@Disabled
	void test_deleteCurriculu_ID_notFound() {
		
	}

}
