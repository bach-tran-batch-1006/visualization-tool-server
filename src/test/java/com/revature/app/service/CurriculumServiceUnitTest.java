package com.revature.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.ForeignKeyConstraintException;
import com.revature.app.exception.VisualizationNotFoundException;
import com.revature.app.model.Category;
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
	void test_addCurriculum_success() throws CurriculumNotAddedException, EmptyParameterException {
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
		} catch (EmptyParameterException e) {
			assertEquals("The curriculum name was left blank", e.getMessage());
		}
	}

	@Test
	void test_addCurriculum_NoSkill_failed() throws EmptyParameterException {
		try {
			CurriculumDto curriculumDto = new CurriculumDto("Language", null);
			curriculumService.addCurriculum(curriculumDto);
		} catch (CurriculumNotAddedException e) {
			assertEquals("Couldn't add curriculum into the database.", e.getMessage());
		}
	}

	@Test
	void test_getCurriculumById_success() throws CurriculumNotFoundException, BadParameterException, EmptyParameterException {
		when(curriculumDao.findByCurriculumId(1)).thenReturn(new Curriculum(1, "BackEnd Developer", new ArrayList<>()));
		Curriculum actual = curriculumService.getCurriculumByID("1");
		Curriculum expected = new Curriculum(1, "BackEnd Developer", new ArrayList<>());
		assertEquals(expected, actual);
	}

	@Test
	void test_getCurriculum_Idnotexist() throws BadParameterException, EmptyParameterException {
		try {
			new CurriculumDto("Language", null);
			curriculumService.getCurriculumByID("10");
			
		} catch (CurriculumNotFoundException e) {
			assertEquals("The curriculum with ID 10 could not be found.", e.getMessage());
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
	void test_delete_success() throws CurriculumNotFoundException, EmptyParameterException, BadParameterException, ForeignKeyConstraintException {
		when(curriculumDao.findByCurriculumId(1)).thenReturn(new Curriculum(1, "Delete Developer", new ArrayList<>()));

		Curriculum expected = new Curriculum(1, "Delete Developer", new ArrayList<>());
		Curriculum actual = curriculumService.deleteCurriculumByID("1");
		
		assertEquals(expected, actual);
	}

	@Test
	void test_delete_notFound() throws EmptyParameterException, BadParameterException, ForeignKeyConstraintException {
		try {
			curriculumService.deleteCurriculumByID("1");
		} catch(CurriculumNotFoundException e) {
			assertEquals("The curriculum could not be deleted because it couldn't be found", e.getMessage());
		}

	}

//
	@Test
	public void test_getAllCategoryiesByCurriculum_happy() throws EmptyParameterException, BadParameterException, CurriculumNotFoundException {
		when(curriculumDao.findByCurriculumId(1)).thenReturn(new Curriculum(1, "test", null));
		Category testCat1 = new Category(0, "TestCat1", "TestDescription");
		Category testCat2 = new Category(0, "TestCat2", "TestDescription");
		List<Category> expected = new ArrayList<Category>();
		expected.add(testCat1);
		expected.add(testCat2);
		when(curriculumDao.catCurList(1)).thenReturn(expected);
		List<Category> actual = curriculumService.getAllCategoriesByCurriculum("1");
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_getAllCategoryiesByCurriculum_emptyParameter() throws BadParameterException, CurriculumNotFoundException {
		try {
			curriculumService.getAllCategoriesByCurriculum(" ");
			fail("EmptyParameterException was not thrown");
		} catch (EmptyParameterException e) {
			assertEquals(e.getMessage(), "The curriculum ID was left blank");
		}
	}
	
	@Test
	public void test_getAllCategoryiesByCurriculum_badParameter() throws EmptyParameterException, CurriculumNotFoundException {
		try {
			curriculumService.getAllCategoriesByCurriculum("test");
			fail("BadParameterException was not thrown");
		} catch (BadParameterException e) {
			assertEquals(e.getMessage(), "The curriculum ID provided must be of type int");
		}
	}
	
	@Test
	public void test_getAllCategoryiesByCurriculum_visualizationNotFound() throws EmptyParameterException, BadParameterException, CurriculumNotFoundException {
		try {
			curriculumService.getAllCategoriesByCurriculum("20202020");
			fail("VisualizationNotFoundException was not thrown");
		} catch (CurriculumNotFoundException e) {
			assertEquals(e.getMessage(), "Curriculum not found");
		}
	}

}
