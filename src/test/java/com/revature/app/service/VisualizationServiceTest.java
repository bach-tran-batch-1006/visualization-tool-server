package com.revature.app.service;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.revature.app.dao.VisualizationDao;
import com.revature.app.model.Curriculum;
import com.revature.app.model.Visualization;

@ExtendWith(MockitoExtension.class)
public class VisualizationServiceTest {
	
	@Mock
	private static VisualizationDao mockVisualizationDao;
	
	@InjectMocks	
	private VisualizationService visualizationService;
	
	@BeforeAll
	public static void setUp() {
		
	}
	
	@BeforeEach
	public void beforeTest() {
		visualizationService = new VisualizationService();
		
	}
	
	
	@Test
	public void test_getVisualization_happy() {
		Curriculum curr1 = new Curriculum(1, "", null);
		
		List<Curriculum> curriculaList = new ArrayList<Curriculum>();
		curriculaList.add(curr1);
		
		Visualization expected = new Visualization(1, "Java Visualization", curriculaList);
		
		Visualization actual = visualizationService.getVisualization();
		
		assertEquals(expected, actual);
		
	}

	

}
