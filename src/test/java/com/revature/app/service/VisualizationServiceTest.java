package com.revature.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.revature.app.dao.VisualizationDao;
import com.revature.app.dto.VisualizationDTO;
import com.revature.app.exception.VisualizationNotFoundException;
import com.revature.app.model.Visualization;

@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness=Strictness.LENIENT)
public class VisualizationServiceTest {

	@Mock
	private static VisualizationDao mockVisualizationDao;

	@InjectMocks
	private VisualizationService visualizationService;

	@BeforeEach
	public void beforeTest() throws VisualizationNotFoundException {
		
		Visualization visual1 = new Visualization(1, "Mock Visual", null);
		Optional<Visualization> visual2 = Optional.ofNullable(new Visualization(2, "Java React", null));
		
		Visualization visual3 = new Visualization(2, "Java React", null);
		
		Optional<Visualization> newVisual = Optional.of(new Visualization(10, "currVisual", null)); 

		List<Visualization> visualList = new ArrayList<Visualization>();
		visualList.add(visual1);
		
		
		lenient().when(mockVisualizationDao.findAll()).thenReturn(visualList);
		lenient().when(mockVisualizationDao.findByVisualizationName("Mock Visual")).thenReturn(visualList);
		lenient().when(mockVisualizationDao.findByVisualizationName("qwjlekjfqoweifjqwe")).thenReturn(null);
		lenient().when(mockVisualizationDao.findById(2)).thenReturn(visual2);
		lenient().when(mockVisualizationDao.findById(20202020)).thenThrow(new RuntimeException("Visualization not found"));
		
		lenient().when(mockVisualizationDao.save(visual3)).thenReturn(visual3);
		
		lenient().when(mockVisualizationDao.findById(10)).thenReturn(newVisual);
		lenient().when(mockVisualizationDao.save(newVisual)).thenReturn(newVisual);
	
	}
	

	@Test
	public void test_findAllVisualization_happy() {

		List<Visualization> expected = new ArrayList<Visualization>();
		expected.add(new Visualization(1, "Mock Visual", null));

		List<Visualization> actual = visualizationService.findAllVisualization();

		assertEquals(expected, actual);

	}

	@Test
	public void test_findByName_happy() throws Exception{

		List<Visualization> expected = new ArrayList<Visualization>();
		expected.add(new Visualization(1, "Mock Visual", null));

		List<Visualization> actual = visualizationService.findByName("Mock Visual");

		assertEquals(expected, actual);

	}

	@Test
	public void test_findByName_notFound() throws Exception {
		try {
			
			visualizationService.findByName("qwjlekjfqoweifjqwe");
			fail("Exception was not thrown");

		} catch (VisualizationNotFoundException e) {
			assertEquals(e.getMessage(), "Visualization not found");
		}
	}

	@Test
	public void test_findVisualizationByID_happy() throws VisualizationNotFoundException{
		
		Visualization expected = new Visualization(2, "Java React", null);

		Visualization actual = visualizationService.findVisualizationByID(2);

		assertEquals(expected, actual);
	}

	@Test
	public void test_findVisualizationByID_notFound() {
		try {
			visualizationService.findVisualizationByID(20202020);
			fail("Visualization not found");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Visualization not found");
		}
	}

	@Test
	public void test_updateVisualizationById_happy() throws VisualizationNotFoundException {	
	
		Visualization newVisual = new Visualization(10, "Updated Vis", null); 
		lenient().when(mockVisualizationDao.save(any(Visualization.class))).thenReturn(newVisual);
		
		VisualizationDTO newVisDTO = new VisualizationDTO("Updated Vis", null);
		Visualization updated = visualizationService.updateVisualizationByID(newVisual.getVisualizationId(), newVisDTO);
		
		Visualization expected = new Visualization(10, "Updated Vis", null);
	
		assertEquals(expected, updated);

	}
	 

	@Test
	public void test_deleteVisualizationById_happy() throws VisualizationNotFoundException {

			Visualization visual1 = new Visualization(1, "Mock Visual", null);

			mockVisualizationDao.deleteById(visual1.getVisualizationId());
			
			verify(mockVisualizationDao, times(1)).deleteById(visual1.getVisualizationId());
				
	};
	

	@Test
	public void test_createVisualization_happy(){
		
		VisualizationDTO visDto = new VisualizationDTO("Mock Visualization", null);
	
		Visualization expected =  mockVisualizationDao.save(new Visualization(visDto));

		Visualization actual = visualizationService.createVisualization(new VisualizationDTO("Mock Visualization", null));

		assertEquals(actual, expected);
	}

}

