package com.revature.app.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.web.WebAppConfiguration;

import com.revature.app.model.Visualization;

// @ExtendWith(SpringExtension)
@WebAppConfiguration
@DirtiesContext
// @TestMethodOrder(MethodOrder.OrderAnnotation.class)
public class VisualizationDaoTest {
	
	// @Autowired
	// SessionFactory sessionFactory;
	
	@Autowired
	private VisualizationDao visualizationDao;
	
	
	@Test
	@Transactional
	@Commit
	void test_getVisualization_happy() {
		ArrayList<Visualization> actual = visualizationDao.getVisualization();
		assertTrue(actual.size() != 0);
	}
	

}
