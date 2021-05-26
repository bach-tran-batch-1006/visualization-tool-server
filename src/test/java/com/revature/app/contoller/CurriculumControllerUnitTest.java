package com.revature.app.contoller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.revature.app.controller.CurriculumController;
import com.revature.app.exception.CurriculumNotFoundException;
import com.revature.app.model.Curriculum;
import com.revature.app.service.CurriculumService;


@ExtendWith(SpringExtension.class)
@WebMvcTest(CurriculumController.class)
public class CurriculumControllerUnitTest {
	
	@MockBean
	CurriculumService curriculumService;
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	void test_addCurriculum_success() {
		Curriculum expected = new Curriculum(1, "Software Engineer", null);
		Curriculum actual = curriculumService.addCurriculum(new Curriculum(1, "Software Engineer", null));
		assertEquals(expected, actual);
	}
	
	@Test
	void test_getAllCurriculum_success() {
		Curriculum curriculum1 = new Curriculum(1, "Software Engineer", null);
		Curriculum curriculum2 = new Curriculum(2, "Back-End Engineer", null);
		List<Curriculum> expected = new ArrayList<Curriculum>();
		expected.add(curriculum1);
		expected.add(curriculum2);
		when(curriculumService.getAllCurriculum()).thenReturn(expected);
		List<Curriculum> actual = curriculumService.getAllCurriculum();
		assertEquals(expected, actual);
	}
	
	@Test
	void test_getCurriculumByID_success() {
		Curriculum curriculum1 = new Curriculum(1, "Software Engineer", null);
		Curriculum curriculum2 = new Curriculum(2, "Back-End Engineer", null);
		List<Curriculum> curricula = new ArrayList<Curriculum>();
		curricula.add(curriculum1);
		curricula.add(curriculum2);
		Curriculum expected = curricula.get(0);
		when(curriculumService.getCurriculumByID(1)).thenReturn(curriculum1);
		Curriculum actual = curriculumService.getCurriculumByID(1);
		assertEquals(expected, actual);
	}
	
	@Test
	void test_getCurriculumByID_notFound() throws CurriculumNotFoundException {
//		try {
//		when(curriculumService.getCurriculumByID(1)).thenThrow(CurriculumNotFoundException.class);
//		fail("CurriculumNotFoundException was not thrown!");
//		} catch (CurriculumNotFoundException e) {
//			assertEquals(e.getMessage(), "Curriculum not found!");
//		}
	}
	
	@Test
	void test_updateCurriculumByID_success() {
		Curriculum curriculum1 = new Curriculum(1, "Software Engineer", null);
		Curriculum curriculum2 = new Curriculum(2, "Back-End Engineer", null);
		List<Curriculum> curricula = new ArrayList<Curriculum>();
		curricula.add(curriculum1);
		curricula.add(curriculum2);
		Curriculum expected = curricula.get(0);
		when(curriculumService.getCurriculumByID(1)).thenReturn(curriculum1);
		Curriculum actual = curriculumService.getCurriculumByID(1);
		assertEquals(expected, actual);
	}
	
	@Test
	void test_updateCurriculumByID_notFound() {
		
	}
	
	@Test
	void test_deleteCurriculumByID_success() {
		
	}
	
	@Test
	void test_deleteCurriculumByID_notFound() {
		
	}
	
}
