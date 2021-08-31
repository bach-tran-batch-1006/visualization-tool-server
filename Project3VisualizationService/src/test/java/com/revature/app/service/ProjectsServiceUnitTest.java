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
import com.revature.app.dao.ProjectDao;
import com.revature.app.dao.VisualizationDao;
import com.revature.app.dto.CurriculumDto;
import com.revature.app.dto.ProjectDto;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.CurriculumNotAddedException;
import com.revature.app.exception.CurriculumNotFoundException;
import com.revature.app.exception.EmptyCurriculumException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.ForeignKeyConstraintException;
import com.revature.app.exception.ProjectNotAddedException;
import com.revature.app.exception.ProjectNotFoundException;
//import com.revature.app.model.Category;
import com.revature.app.model.Projects;
//import com.revature.app.model.Projects;
//import com.revature.app.model.Skill;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProjectsServiceUnitTest {

	@Mock
	private CurriculumDao curriculumDao;
	
	@Mock
	private ProjectDao pDao;

	@InjectMocks
	private ProjectsService pService;

	@Test
	void test_addProject_success() throws ProjectNotAddedException, EmptyParameterException {
		when(pDao.save(new Projects("BackEnd Developer", new ArrayList<>())))
				.thenReturn(new Projects("BackEnd Developer", new ArrayList<>()));

		Projects actual = pService.addProject(new ProjectDto("BackEnd Developer", new ArrayList<>()));
		Projects expected = new Projects("BackEnd Developer", new ArrayList<>());

		assertEquals(expected, actual);
	}
	
	@Test
	void test_addProject_blankProjectsName_failed() throws ProjectNotAddedException {
		try {
			ProjectDto projectDto = new ProjectDto(" ", new ArrayList<Integer>());
			pService.addProject(projectDto);
		} catch (EmptyParameterException e) {
			assertEquals("The Projects name was left blank", e.getMessage());
		}
	}

	@Test
	void test_getProjectById_success() throws ProjectNotFoundException, BadParameterException, EmptyParameterException {
		when(pDao.findById(1)).thenReturn(new Projects("BackEnd Developer", new ArrayList<>()));
		Projects actual = pService.getProjectByID("1");
		Projects expected = new Projects("BackEnd Developer", new ArrayList<>());
		assertEquals(expected, actual);
	}

	@Test
	void test_getProject_Idnotexist() throws BadParameterException, EmptyParameterException {
		try {
			//new ProjectDto("Language",0,0,0);
			pService.getProjectByID("10");
			
		} catch (ProjectNotFoundException e) {
			assertEquals("The project with ID 10 could not be found.", e.getMessage());
		}
	}
	
	@Test
	void test_getProject_badID() throws ProjectNotFoundException, EmptyParameterException {
		try {
			pService.getProjectByID("test");
			fail("BadParameterException was not thrown");
		} catch (BadParameterException e) {
			assertEquals("The Project ID provided must be of type int", e.getMessage());
		}
	}
	
	@Test
	void test_getProject_emptyID() throws ProjectNotFoundException, BadParameterException {
		try {
			pService.getProjectByID("   ");
			fail("EmptyParameterException was not thrown");
		} catch (EmptyParameterException e) {
			assertEquals("The Project ID was left blank", e.getMessage());
		}
	}
//

	@Test
	void test_getAllProject_success() throws EmptyCurriculumException {
		List<Projects> returnProj = new ArrayList<Projects>();
		returnProj.add(new Projects("BackEnd Developer", new ArrayList<>()));
		when(pDao.findAll()).thenReturn(returnProj);
		System.out.println("return Project" + returnProj);

		List<Projects> actual = pService.getAllProjects();
		List<Projects> expected = new ArrayList<Projects>();
		expected.add(new Projects("BackEnd Developer", new ArrayList<>()));

		assertEquals(expected, actual);
	}

//
	@Test
	void test_updatebyID_success() throws EmptyParameterException, ProjectNotFoundException, BadParameterException {
		when(pDao.findById(1)).thenReturn(new Projects("BackEnd Developer", new ArrayList<>()));
		when(pDao.save(new Projects("Update Developer", new ArrayList<>())))
				.thenReturn(new Projects("Update Developer", new ArrayList<>()));

		Projects actual = pService.updateProjectByID("1", new ProjectDto("Update Developer"));
		Projects expected = new Projects("Update Developer", new ArrayList<>());

		assertEquals(expected, actual);
	}

	
	@Test
	void test_updateProject_badID() throws EmptyParameterException, ProjectNotFoundException {
		try {
			ProjectDto upCurr = new ProjectDto("TestCurr", null);
			pService.updateProjectByID("test", upCurr);
			fail("BadParameterException was not thrown");
		} catch (BadParameterException e) {
			assertEquals("The curriculum ID provided must be of type int", e.getMessage());
		}
	}
	
	@Test
	void test_updateCurriculum_emptyID() throws ProjectNotFoundException, BadParameterException {
		try {
			ProjectDto upCurr = new ProjectDto("TestCurr", null);
			pService.updateProjectByID("   ", upCurr);
			fail("EmptyParameterException was not thrown");
		} catch (EmptyParameterException e) {
			assertEquals("The curriculum ID was left blank", e.getMessage());
		}
	}
	
	@Test
	void test_updateCurriculum_emptyName() throws ProjectNotFoundException, BadParameterException {
		try {
			ProjectDto upCurr = new ProjectDto("    ", null);
			pService.updateProjectByID("1", upCurr);
			fail("EmptyParameterException was not thrown");
		} catch (EmptyParameterException e) {
			assertEquals("The curriculum name was left blank", e.getMessage());
		}
	}
	
	@Test
	void test_updateCurriculum_curriculumNotFound() throws EmptyParameterException, BadParameterException {
		try {
			ProjectDto upCurr = new ProjectDto("TestCurr", null);
			pService.updateProjectByID("1", upCurr);
			fail("CurriculumNotFoundException was not thrown");
		} catch (ProjectNotFoundException e) {
			assertEquals("The category could not be updated because it couldn't be found", e.getMessage());
		}
	}
	
//
	@Test
	void test_delete_success() throws ProjectNotFoundException, EmptyParameterException, BadParameterException, ForeignKeyConstraintException {
		when(pDao.findById(1)).thenReturn(new Projects("Delete Developer", new ArrayList<>()));

		Projects expected = new Projects("Delete Developer", new ArrayList<>());
		Projects actual = null;
		try {
			actual = pService.deleteProjectByID("1");
		} catch (ProjectNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmptyParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(expected, actual);
	}

	@Test
	void test_delete_notFound() throws EmptyParameterException, BadParameterException, ForeignKeyConstraintException {
		try {
			pService.deleteProjectByID("1");
		} catch(ProjectNotFoundException e) {
			assertEquals("The curriculum could not be deleted because it couldn't be found", e.getMessage());
		}

	}
	
	@Test
	void test_delete_badID() throws ProjectNotFoundException, EmptyParameterException, ForeignKeyConstraintException {
		try {
			pService.deleteProjectByID("test");
			fail("BadParameterException was not thrown");
		} catch (BadParameterException e) {
			assertEquals("The curriculum ID provided must be of type int", e.getMessage());
		}
	}
	
	@Test
	void test_delete_emptyID() throws ProjectNotFoundException, BadParameterException, ForeignKeyConstraintException {
		try {
			pService.deleteProjectByID("    ");
			fail("EmptyParameterException was not thrown");
		} catch (EmptyParameterException e) {
			assertEquals("The curriculum ID was left blank", e.getMessage());
		}
	}
}