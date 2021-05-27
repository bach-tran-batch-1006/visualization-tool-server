package com.revature.app.service;

import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

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
import com.revature.app.exception.CurriculumNotFoundException;
import com.revature.app.exception.EmptyCurriculumException;
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

		when(curriculumDao.save(new Curriculum(0, "BackEnd Developer", new ArrayList<>())))
				.thenReturn(new Curriculum(1, "BackEnd Developer", new ArrayList<>()));

		Curriculum actual = curriculumService.addCurriculum(new CurriculumDto("BackEnd Developer", new ArrayList<>()));
		Curriculum expected = new Curriculum(1, "BackEnd Developer", new ArrayList<>());

		assertEquals(expected, actual);
	}

	@Test
	void test_getCurriculumById_success() throws CurriculumNotFoundException {

		when(curriculumDao.findByCurriculumId(1)).thenReturn(new Curriculum(1, "BackEnd Developer", new ArrayList<>()));

		Curriculum actual = curriculumService.getCurriculumByID(1);
		Curriculum expected = new Curriculum(1, "BackEnd Developer", new ArrayList<>());

		assertEquals(expected, actual);
	}

	@Test
	void test_getAllCurriculum_success() throws EmptyCurriculumException {
		ArrayList<Curriculum> returnCu = new ArrayList<Curriculum>();
		returnCu.add(new Curriculum(1, "BackEnd Developer", new ArrayList<>()));
		when(curriculumDao.findAll()).thenReturn(returnCu);
		System.out.println("return curriculum" + returnCu);

		ArrayList<Curriculum> actual = curriculumService.getAllCurriculum();
		ArrayList<Curriculum> expected = new ArrayList<Curriculum>();
		expected.add(new Curriculum(1, "BackEnd Developer", new ArrayList<>()));

		assertEquals(expected, actual);
	}

	@Test
	void test_updatebyID_success() {
		when(curriculumDao.findByCurriculumId(1)).thenReturn(new Curriculum(1, "BackEnd Developer", new ArrayList<>()));
		when(curriculumDao.save(new Curriculum(1, "Update Developer", new ArrayList<>())))
				.thenReturn(new Curriculum(1, "Update Developer", new ArrayList<>()));

		Curriculum actual = curriculumService.updateCurriculumByID(1, new CurriculumDto("Update Developer"));
		Curriculum expected = new Curriculum(1, "Update Developer", new ArrayList<>());

		assertEquals(expected, actual);
	}

	@Test 
	void test_delete_success() {
		curriculumDao.deleteById(1);
		verify(curriculumDao, times(1)).deleteById(eq(1));
	}

}
