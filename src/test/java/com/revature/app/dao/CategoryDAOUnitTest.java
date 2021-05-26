package com.revature.app.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.app.dto.CategoryDTO;
import com.revature.app.model.Category;

@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CategoryDAOUnitTest {
	
	@Autowired
	private CategoryDAO categoryDAO;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	@DirtiesContext
	void testCreateCategory_postive() {
		Category expected = new Category(1, "Language", "Programming language");
		CategoryDTO testInput = new CategoryDTO("Language", "Programming language");
		
		Category actual = categoryDAO.createCategory(testInput);
		
		assertEquals(expected, actual);
	}
	
	@Test
	@DirtiesContext
	void testCreateCategory_negative() {
		fail("Not yet implemented");
	}
	
	@Test
	@DirtiesContext
	void testGetCategory_postive() {
		fail("Not yet implemented");
	}
	
	@Test
	@DirtiesContext
	void testGetCategory_negative() {
		fail("Not yet implemented");
	}
	
	@Test
	@DirtiesContext
	void testUpdateCategory_postive() {
		fail("Not yet implemented");
	}
	
	@Test
	@DirtiesContext
	void testUpdateCategory_negative() {
		fail("Not yet implemented");
	}

	@Test
	@DirtiesContext
	void testDeleteCategory_postive() {
		fail("Not yet implemented");
	}

	@Test
	@DirtiesContext
	void testDeleteCategory_negative() {
		fail("Not yet implemented");
	}

}
