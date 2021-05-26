package com.revature.app.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.revature.app.dao.CurriculumDao;
import com.revature.app.dto.CurriculumDto;
import com.revature.app.model.Curriculum;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest
@ActiveProfiles("test")
public class CurriculumServiceUnitTest {

	@Mock
	private CurriculumDao curriculumDao;

	@InjectMocks
	private CurriculumService curriculumService;

	@Test
	void test_addCurriculum_success() {
		when(curriculumDao.save(new Curriculum(0, "BackEnd Developer", null))).thenReturn(new Curriculum(1, "Back-End Developer", null));
		
		Curriculum actual = curriculumService.addCurriculum(new CurriculumDto("BackEnd Developer"));
		Curriculum expected = new Curriculum(1, "Back-End Developer", null);
		
		assertEquals(expected, actual);
	}


}
