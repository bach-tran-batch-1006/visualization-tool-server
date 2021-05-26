package com.revature.app.contoller;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.revature.app.model.Curriculum;
import com.revature.app.service.CurriculumService;

public class CurriculumControllerUnitTest {
	
	@Mock
	CurriculumService curriculumService;

	@Test
	public void test_getAllCurricula_success() {
		
		Curriculum curriculum = new Curriculum(0, "Software Engineer", null);
		
		/*
		 * List<Curriculum> curricula = Arrays.asList(curriculum);
		 * 
		 * given(curriculumService.getAllCurricula()).willReturn()
		 */
	}
}
