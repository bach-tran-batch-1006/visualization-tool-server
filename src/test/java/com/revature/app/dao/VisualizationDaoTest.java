package com.revature.app.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.app.model.Visualization;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VisualizationDaoTest {

	@Autowired
	private VisualizationDao visualDao;

	// Initial Tests

	@Test
	@Order(0)
	void testVisualizationListEmpty() {
		List<Visualization> list = visualDao.findAll();

		assertEquals(0, list.size());
	}
	
	//creates

	@Test
	@Commit
	@Order(10)
	void testCreateVisualization() {
		String payload = "test";
		Visualization expected = new Visualization();

		expected.setVisualizationName(payload);

		Visualization actual = visualDao.save(expected);

		assertNotEquals(0, actual.getVisualizationId());
		assertEquals(payload, actual.getVisualizationName());

	}

	@Test
	@Order(15)
	void testVisualizationListNotEmpty() {
		List<Visualization> list = visualDao.findAll();

		assertNotEquals(0, list.size());
	}
	
	//reads
	
	@Test
	@Order(20)
	void testGetValidVisualization() {
		Visualization actual = visualDao.getById(1);
		
		assertNotNull(actual);
		assertEquals(1, actual.getVisualizationId());
	}
	
	@Test
	@Order(21)
	void testGetInvalidVisualization() {
		try {
			 Visualization v = visualDao.getById(Integer.MAX_VALUE);
			 System.out.println(v);
			 fail("Exception not caught");
		} catch (javax.persistence.EntityNotFoundException e) {
			assertEquals("Unable to find com.revature.app.model.Visualization with id 2147483647", e.getMessage());
		}
	}
	
	//updates
	
	@Test
	@Order(30)
	void testUpdateValidVisualization() {
		String payload = "new name";
		Visualization actual = visualDao.getById(1);
		actual.setVisualizationName(payload);
		Visualization newActual = visualDao.save(actual);
		
		assertNotNull(newActual);
		assertEquals(payload, newActual.getVisualizationName());
	}
	
	//deletes
	@Test
	@Order(40)
	void testDeleteValidVisualization() {
		visualDao.deleteById(1);
	}
	
	@Test
	@Order(41)
	void testDeleteInvalidVisualization() {
		try {
			visualDao.getById(Integer.MAX_VALUE);
		} catch (javax.persistence.EntityNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}
