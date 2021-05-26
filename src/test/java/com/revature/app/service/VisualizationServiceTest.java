package com.revature.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.revature.app.dao.VisualizationDao;
import com.revature.app.model.Curriculum;
import com.revature.app.model.Visualization;

@ExtendWith(MockitoExtension.class)
public class VisualizationServiceTest {
	
	@InjectMocks
	
	private static VisualizationDao mockVisualizationDao;
	
	@Autowired
	private VisualizationService visualizationService;
	
	@BeforeAll
	public static void setUp() {
		
		mockVisualizationDao = mock(VisualizationDao.class);
		
	}
	
	@BeforeEach
	public void beforeTest() {
		visualizationService = new VisualizationService();
		
	}
	
	@Test
	public void test_getVisualization_happy() {
		ArrayList<Curriculum> curriculaList = new ArrayList<Curriculum>();
		
		
		Visualization expected = new Visualization(1, "Java Visualization", curriculaList);
		Visualization actual = visualizationService.getVisualization();
		assertEquals(expected, actual);
		
	}

	

}
