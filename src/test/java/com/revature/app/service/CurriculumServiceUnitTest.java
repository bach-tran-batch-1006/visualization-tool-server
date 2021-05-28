package com.revature.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.revature.app.dao.CurriculumDao;
import com.revature.app.dto.CurriculumDto;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.CurriculumNotAddedException;
import com.revature.app.exception.CurriculumNotFoundException;
import com.revature.app.exception.EmptyCurriculumException;
import com.revature.app.model.Curriculum;
import com.revature.app.model.Skill;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CurriculumServiceUnitTest {

	@Mock
	private CurriculumDao curriculumDao;

	@InjectMocks
	private CurriculumService curriculumService;

	@Test
	void test_addCurriculum_success() throws BadParameterException, CurriculumNotAddedException {
		when(curriculumDao.save(new Curriculum(0, "BackEnd Developer", new ArrayList<>())))
				.thenReturn(new Curriculum(1, "BackEnd Developer", new ArrayList<>()));

		Curriculum actual = curriculumService.addCurriculum(new CurriculumDto("BackEnd Developer", new ArrayList<>()));
		Curriculum expected = new Curriculum(1, "BackEnd Developer", new ArrayList<>());

		assertEquals(expected, actual);
	}
	
	@Test
	void test_addCurriculum_blankCurriculumName_failed() throws CurriculumNotAddedException {
		try {
			CurriculumDto curriculumDto = new CurriculumDto(" ", new ArrayList<Skill>());
			curriculumService.addCurriculum(curriculumDto);
		} catch (BadParameterException e) {
			assertEquals("Curriculum can not be blank.", e.getMessage());
		}
	}

	@Test
	void test_addCurriculum_NoSkill_failed() throws CurriculumNotAddedException, BadParameterException {

		try {
			CurriculumDto curriculumDto = new CurriculumDto("Language", null);
			curriculumService.addCurriculum(curriculumDto);
		} catch (CurriculumNotAddedException e) {
			assertEquals("Couldn't add curriculum into the database.", e.getMessage());
		}
	}

	@Test
	void test_getCurriculumById_success() throws CurriculumNotFoundException {

		when(curriculumDao.findByCurriculumId(1)).thenReturn(new Curriculum(1, "BackEnd Developer", new ArrayList<>()));

		Curriculum actual = curriculumService.getCurriculumByID(1);
		Curriculum expected = new Curriculum(1, "BackEnd Developer", new ArrayList<>());

		assertEquals(expected, actual);
	}

	@Test
	void test_getCurriculum_Idnotexist() throws CurriculumNotFoundException {
		when(curriculumDao.findByCurriculumId(Integer.MAX_VALUE)).thenReturn(null);
		try {
			curriculumService.getCurriculumByID(Integer.MAX_VALUE);
			fail("Exception not thrown");
		} catch (CurriculumNotFoundException e) {
			assertEquals("Curriculum not found", e.getMessage());
		}
	}

	@Test
	void test_getAllCurriculum_success() throws EmptyCurriculumException {
		List<Curriculum> returnCu = new ArrayList<Curriculum>();
		returnCu.add(new Curriculum(1, "BackEnd Developer", new ArrayList<>()));
		when(curriculumDao.findAll()).thenReturn(returnCu);
		System.out.println("return curriculum" + returnCu);

		List<Curriculum> actual = curriculumService.getAllCurriculum();
		List<Curriculum> expected = new ArrayList<Curriculum>();
		expected.add(new Curriculum(1, "BackEnd Developer", new ArrayList<>()));

		assertEquals(expected, actual);
	}

	@Test()
	void test_getAllCurriculum_NoCurriculumFound() {
		try {
			if(! curriculumService.getAllCurriculum().isEmpty()) {
				fail("No Curriculum found");
			}
		}catch(EmptyCurriculumException e) {
			assertEquals("No Curriculum found", e.getMessage());
		}
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
	void test_delete_success() throws CurriculumNotFoundException {
		
		when(curriculumDao.findByCurriculumId(1)).thenReturn(new Curriculum(1, "Delete Developer", new ArrayList<>()));

		Curriculum expected = new Curriculum(1, "Delete Developer", new ArrayList<>());
		Curriculum actual = curriculumService.deleteCurriculumByID(1);
		
		assertEquals(expected, actual);
	}
	

	@Test
	void test_delete_failed() {
		try {
			curriculumService.deleteCurriculumByID(1);
			
		}catch(CurriculumNotFoundException e) {
			assertEquals("The curriculum could not be deleted because it couldn't be found", e.getMessage());
		}

	}

}
