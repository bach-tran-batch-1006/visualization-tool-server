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

//import com.revature.app.dao.CurriculumDao;
import com.revature.app.dao.PrimerDao;
//import com.revature.app.dao.ProjectDao;
import com.revature.app.dao.VisualizationDao;
import com.revature.app.dto.PrimerDto;
//import com.revature.app.dao.VisualizationDao;
//import com.revature.app.dto.CurriculumDto;
//import com.revature.app.dto.ProjectDto;
import com.revature.app.exception.BadParameterException;
//import com.revature.app.exception.CurriculumNotAddedException;
//import com.revature.app.exception.CurriculumNotFoundException;
import com.revature.app.exception.EmptyCurriculumException;
import com.revature.app.exception.EmptyParameterException;
//import com.revature.app.exception.ForeignKeyConstraintException;
import com.revature.app.exception.PrimerNotAddedException;
import com.revature.app.exception.PrimerNotFoundException;
//import com.revature.app.exception.ProjectNotAddedException;
//import com.revature.app.exception.ProjectNotFoundException;
import com.revature.app.model.Primer;
//import com.revature.app.model.Category;
//import com.revature.app.model.Projects;
//import com.revature.app.model.Skill;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PrimerServiceUnitTest {

	@Mock
	private VisualizationDao vDao;
	
	@Mock
	private PrimerDao pDao;

	@InjectMocks
	private PrimerServices pService;

	@Test
	void test_addPrimer_success() throws PrimerNotAddedException, EmptyParameterException {
		when(pDao.save(new Primer("BackEnd Developer", new ArrayList<>())))
				.thenReturn(new Primer("BackEnd Developer", new ArrayList<>()));

		Primer actual = pService.addPrimer(new PrimerDto("BackEnd Developer", new ArrayList<>()));
		Primer expected = new Primer("BackEnd Developer", new ArrayList<>());

		assertEquals(expected, actual);
	}
	
	@Test
	void test_addPrimer_blankPrimersName_failed() throws PrimerNotAddedException {
		try {
			PrimerDto primerDto = new PrimerDto(" ", new ArrayList<Integer>());
			pService.addPrimer(primerDto);
		} catch (EmptyParameterException e) {
			assertEquals("The Primer name was left blank", e.getMessage());
		}
	}

	@Test
	void test_getPrimerById_success() throws PrimerNotFoundException, BadParameterException, EmptyParameterException {
		when(pDao.findByPrimerId(1)).thenReturn(new Primer("BackEnd Developer", new ArrayList<>()));
		Primer actual = pService.getPrimerByID("1");
		Primer expected = new Primer("BackEnd Developer", new ArrayList<>());
		assertEquals(expected, actual);
	}

	@Test
	void test_getPrimer_Idnotexist() throws BadParameterException, EmptyParameterException {
		try {
			//new ProjectDto("Language",0,0,0);
			pService.getPrimerByID("10");
			
		} catch (PrimerNotFoundException e) {
			assertEquals("The primer with ID 10 could not be found.", e.getMessage());
		}
	}
	
	@Test
	void test_getPrimer_badID() throws PrimerNotFoundException, EmptyParameterException {
		try {
			pService.getPrimerByID("test");
			fail("BadParameterException was not thrown");
		} catch (BadParameterException e) {
			assertEquals("The Primer ID provided must be of type int", e.getMessage());
		}
	}
	
	@Test
	void test_getProject_emptyID() throws PrimerNotFoundException, BadParameterException {
		try {
			try {
				pService.getPrimerByID("   ");
			} catch (PrimerNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fail("EmptyParameterException was not thrown");
		} catch (EmptyParameterException e) {
			assertEquals("The Primer ID was left blank", e.getMessage());
		}
	}
//

	@Test
	void test_getAllPrimers_success() throws EmptyCurriculumException {
		List<Primer> returnPrimer = new ArrayList<Primer>();
		returnPrimer.add(new Primer("BackEnd Developer", new ArrayList<>()));
		when(pDao.findAll()).thenReturn(returnPrimer);
		System.out.println("return Primer" + returnPrimer);

		List<Primer> actual = pService.getAllPrimers();
		List<Primer> expected = new ArrayList<Primer>();
		expected.add(new Primer("BackEnd Developer", new ArrayList<>()));

		assertEquals(expected, actual);
	}

//
	@Test
	void test_updatebyID_success() throws EmptyParameterException, PrimerNotFoundException, BadParameterException {
		when(pDao.findByPrimerId(1)).thenReturn(new Primer("BackEnd Developer", new ArrayList<>()));
		when(pDao.save(new Primer("Update Developer", new ArrayList<>())))
				.thenReturn(new Primer("Update Developer", new ArrayList<>()));

		Primer actual = pService.updatePrimerByID("1", new PrimerDto("Update Developer"));
		Primer expected = new Primer("Update Developer", new ArrayList<>());

		assertEquals(expected, actual);
	}

	
	@Test
	void test_updatePrimer_badID() throws EmptyParameterException, PrimerNotFoundException {
		try {
			PrimerDto upPrim = new PrimerDto("TestCurr", null);
			pService.updatePrimerByID("test", upPrim);
			fail("BadParameterException was not thrown");
		} catch (BadParameterException e) {
			assertEquals("The Primer ID provided must be of type int", e.getMessage());
		}
	}
	
	@Test
	void test_updatePrimer_emptyID() throws PrimerNotFoundException, BadParameterException {
		try {
			PrimerDto upPrimer = new PrimerDto("TestPe", null);
			pService.updatePrimerByID("   ", upPrimer);
			fail("EmptyParameterException was not thrown");
		} catch (EmptyParameterException e) {
			assertEquals("The Primer ID was left blank", e.getMessage());
		}
	}
	
	@Test
	void test_updatePrimer_emptyName() throws PrimerNotFoundException, BadParameterException {
		try {
			PrimerDto upPrim = new PrimerDto("    ", null);
			pService.updatePrimerByID("1", upPrim);
			fail("EmptyParameterException was not thrown");
		} catch (EmptyParameterException e) {
			assertEquals("The primer name was left blank", e.getMessage());
		}
	}
	
	@Test
	void test_updatePrimer_PrimerNotFound() throws EmptyParameterException, BadParameterException {
		try {
			PrimerDto upPrimer = new PrimerDto("TestCurr", null);
			pService.updatePrimerByID("1", upPrimer);
			fail("PrimerNotFoundException was not thrown");
		} catch (PrimerNotFoundException e) {
			assertEquals("The primer could not be updated because it couldn't be found", e.getMessage());
		}
	}
	
//
	@Test
	void test_delete_success() throws PrimerNotFoundException, EmptyParameterException, BadParameterException{
		when(pDao.findByPrimerId(1)).thenReturn(new Primer("Delete Developer", new ArrayList<>()));

		Primer expected = new Primer("Delete Developer", new ArrayList<>());
		Primer actual = pService.deletePrimerByID("1");
		
		assertEquals(expected, actual);
	}

	@Test
	void test_delete_notFound() throws EmptyParameterException, BadParameterException {
		try {
			pService.deletePrimerByID("1");
		} catch(PrimerNotFoundException e) {
			assertEquals("The primer could not be deleted because it couldn't be found", e.getMessage());
		}

	}
	
	@Test
	void test_delete_badID() throws PrimerNotFoundException, EmptyParameterException {
		try {
			pService.deletePrimerByID("test");
			fail("BadParameterException was not thrown");
		} catch (BadParameterException e) {
			assertEquals("The Primer ID provided must be of type int", e.getMessage());
		}
	}
	
	@Test
	void test_delete_emptyID() throws PrimerNotFoundException, BadParameterException {
		try {
			try {
				pService.deletePrimerByID("    ");
			} catch (PrimerNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fail("EmptyParameterException was not thrown");
		} catch (EmptyParameterException e) {
			assertEquals("The Primer ID was left blank", e.getMessage());
		}
	}
}