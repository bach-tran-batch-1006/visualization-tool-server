package com.revature.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.app.dao.VisualizationDao;
import com.revature.app.dto.VisualizationDTO;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.VisualizationNotFoundException;
import com.revature.app.model.Category;
import com.revature.app.model.Skill;
import com.revature.app.model.Visualization;

@ExtendWith(MockitoExtension.class)
public class VisualizationServiceTest {

	@Mock
	private VisualizationDao mockVisualizationDao;

	@InjectMocks
	private VisualizationService visualizationService;

	@BeforeEach
	public void beforeTest() throws VisualizationNotFoundException {
		Visualization visual1 = new Visualization(1, "Mock Visual", null);
		Visualization visual2 = new Visualization(2, "Java React", null);
		
		Visualization preUpdate = new Visualization(0, "currVis", null);
		Visualization newVisual = new Visualization(10, "currVisual", null); 

		Skill skill1 = new Skill(1, "", new Category(1, "", null));
		Skill skill2 = new Skill(2, "", new Category(1, "", null));
		List<Skill> skillList = new ArrayList<Skill>();
		skillList.add(skill1);
		skillList.add(skill2);

		ArrayList<Visualization> visualList = new ArrayList<Visualization>();
		visualList.add(visual1);
		
		//Get Methods
		lenient().when(mockVisualizationDao.findAll()).thenReturn(visualList);
		lenient().when(mockVisualizationDao.findById(2)).thenReturn(visual2);
		lenient().when(mockVisualizationDao.findById(20202020)).thenReturn(null);
		
		//Update and Create
		lenient().when(mockVisualizationDao.findById(10)).thenReturn(preUpdate);
		lenient().when(mockVisualizationDao.save(preUpdate)).thenReturn(newVisual);
		
		//DeleteMethod
		lenient().when(mockVisualizationDao.findById(5)).thenReturn(visual1);
		
		
		//getAllSkillsByVisualization
		lenient().when(mockVisualizationDao.findById(1)).thenReturn(visual1);
		lenient().when(mockVisualizationDao.skillVisList(1)).thenReturn(skillList);
	
	}
	

	@Test
	public void test_findAllVisualization_happy() {
		List<Visualization> expected = new ArrayList<Visualization>();
		expected.add(new Visualization(1, "Mock Visual", null));
		List<Visualization> actual = visualizationService.findAllVisualization();
		assertEquals(expected, actual);
	}
	
//
	@Test
	public void test_findVisualizationByID_happy() throws VisualizationNotFoundException, EmptyParameterException, BadParameterException{
		Visualization expected = new Visualization(2, "Java React", null);
		Visualization actual = visualizationService.findVisualizationByID("2");
		assertEquals(expected, actual);
	}

	@Test
	public void test_findVisualizationByID_notFound() throws EmptyParameterException, BadParameterException {
		try {
			visualizationService.findVisualizationByID("20202020");
			fail("VisualizationNotFound Exception not thrown");
		} catch (VisualizationNotFoundException e) {
			assertEquals(e.getMessage(), "Visualization not found");
		}
	}
	
	@Test
	public void test_findVisualizationByID_BadParameter() throws VisualizationNotFoundException, EmptyParameterException  {
		try {
			visualizationService.findVisualizationByID("test");
			fail("BadParameterException not thrown");
		} catch (BadParameterException e) {
			assertEquals(e.getMessage(), "The visualization ID provided must be of type int");
		}
	}
	
	@Test
	public void test_findVisualizationByID_EmptyParameter() throws VisualizationNotFoundException, BadParameterException  {
		try {
			visualizationService.findVisualizationByID("   ");
			fail("EmptyParameterException not thrown");
		} catch (EmptyParameterException e) {
			assertEquals(e.getMessage(), "The visualization ID was left blank");
		}
	}

	
//
	@Test
	public void test_updateVisualizationById_happy() throws VisualizationNotFoundException, BadParameterException, EmptyParameterException {	
		VisualizationDTO visDto = new VisualizationDTO("currVis", null);
		Visualization expected = new Visualization(10, "currVisual", null); 
		Visualization actual = visualizationService.updateVisualizationByID("10", visDto);
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_updateVisualizationById_emptyID() throws VisualizationNotFoundException, BadParameterException {	
		try {
			VisualizationDTO visDto = new VisualizationDTO("TestVis", null);
			visualizationService.updateVisualizationByID("   ", visDto);
			fail("EmptyParameterException not thrown");
		} catch (EmptyParameterException e) {
			assertEquals(e.getMessage(), "The visualization ID was left blank");
		}
	}
	
	@Test
	public void test_updateVisualizationById_emptyNewName() throws VisualizationNotFoundException, BadParameterException {	
		try {
			VisualizationDTO visDto = new VisualizationDTO("", null);
			visualizationService.updateVisualizationByID("1", visDto);
			fail("EmptyParameterException not thrown");
		} catch (EmptyParameterException e) {
			assertEquals(e.getMessage(), "The visualization name was left blank");
		}
	}
	
	@Test
	public void test_updateVisualizationById_badParameter() throws VisualizationNotFoundException, EmptyParameterException {	
		try {
			VisualizationDTO visDto = new VisualizationDTO("TestVis", null);
			visualizationService.updateVisualizationByID("test", visDto);
			fail("BadParameterException not thrown");
		} catch (BadParameterException e) {
			assertEquals(e.getMessage(), "The visualization ID provided must be of type int");
		}
	}
	
	@Test
	public void test_updateVisualizationById_visualizationNotFound() throws BadParameterException, EmptyParameterException {	
		try {
			VisualizationDTO visDto = new VisualizationDTO("TestVis", null);
			visualizationService.updateVisualizationByID("20202020", visDto);
			fail("VisualizationNotFound Exception not thrown");
		} catch (VisualizationNotFoundException e) {
			assertEquals(e.getMessage(), "Visualization not found");
		}
	}
	
	 
//
	@Test
	public void test_deleteVisualizationById_happy() throws VisualizationNotFoundException, BadParameterException, EmptyParameterException {
		int expected = 5;
		int actual = visualizationService.deleteVisualizationByID("5");
		assertEquals(expected, actual);
	};
	
	@Test
	public void test_deleteVisualizationById_emptyID() throws VisualizationNotFoundException, BadParameterException {	
		try {
			visualizationService.deleteVisualizationByID("   ");
			fail("EmptyParameterException not thrown");
		} catch (EmptyParameterException e) {
			assertEquals(e.getMessage(), "The visualization ID was left blank");
		}
	}
	
	@Test
	public void test_deleteVisualizationById_badParameter() throws VisualizationNotFoundException, EmptyParameterException {	
		try {
			visualizationService.deleteVisualizationByID("test");
			fail("BadParameterException not thrown");
		} catch (BadParameterException e) {
			assertEquals(e.getMessage(), "The visualization ID provided must be of type int");
		}
	}
	
	@Test
	public void test_deleteVisualizationById_visualizationNotFound() throws BadParameterException, EmptyParameterException {	
		try {
			visualizationService.deleteVisualizationByID("20202020");
			fail("VisualizationNotFound Exception not thrown");
		} catch (VisualizationNotFoundException e) {
			assertEquals(e.getMessage(), "Visualization not found");
		}
	}
	

//
	@Test
	public void test_createVisualization_happy() throws EmptyParameterException{
		VisualizationDTO visDto = new VisualizationDTO("currVis", null);
		Visualization expected = new Visualization(10, "currVisual", null); 
		Visualization actual = visualizationService.createVisualization(visDto);
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_createVisualization_emptyName() {
		try {
			VisualizationDTO visDto = new VisualizationDTO("", null);
			visualizationService.createVisualization(visDto);
			fail("EmptyParameterException not thrown");
		} catch (EmptyParameterException e) {
			assertEquals(e.getMessage(), "The visualization name was left blank");
		}
	}
	

//
	@Test
	public void test_getAllSkillsByVisualization_happy() throws EmptyParameterException, BadParameterException, VisualizationNotFoundException {
		Skill skill1 = new Skill(1, "", new Category(1, "", null));
		Skill skill2 = new Skill(2, "", new Category(1, "", null));
		List<Skill> expected = new ArrayList<Skill>();
		expected.add(skill1);
		expected.add(skill2);
		List<Skill> actual = visualizationService.getAllSkillsByVisualization("1");
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_getAllSkillsByVisualization_emptyParameter() throws BadParameterException, VisualizationNotFoundException {
		try {
			visualizationService.getAllSkillsByVisualization(" ");
			fail("EmptyParameterException was not thrown");
		} catch (EmptyParameterException e) {
			assertEquals(e.getMessage(), "The visualization ID was left blank");
		}
	}
	
	@Test
	public void test_getAllSkillsByVisualization_badParameter() throws EmptyParameterException, VisualizationNotFoundException {
		try {
			visualizationService.getAllSkillsByVisualization("test");
			fail("BadParameterException was not thrown");
		} catch (BadParameterException e) {
			assertEquals(e.getMessage(), "The visualization ID provided must be of type int");
		}
	}
	
	@Test
	public void test_getAllSkillsByVisualization_visualizationNotFound() throws EmptyParameterException, BadParameterException {
		try {
			visualizationService.getAllSkillsByVisualization("20202020");
			fail("VisualizationNotFoundException was not thrown");
		} catch (VisualizationNotFoundException e) {
			assertEquals(e.getMessage(), "Visualization not found");
		}
	}

}
