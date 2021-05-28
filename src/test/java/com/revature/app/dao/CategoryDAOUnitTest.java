package com.revature.app.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.app.model.Category;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
class CategoryDAOUnitTest {
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	@Order(0)
	@Commit
	void testCreateCategory_postive() {
		Category expected = new Category(1, "Language", "Programming language");
		Category testInput = new Category(1, "Language", "Programming language");
		
		
		Category actual = categoryDAO.save(testInput);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void testCreateCategory_negative() {
			Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> {
				categoryDAO.save(null);
			});
	}
	
	@Test
	@Order(1)
	void testGetCategoryById_postive() {
		Category expected = new Category(1, "Language", "Programming language");
		Category actual = categoryDAO.findById(1);
		
		assertEquals(expected, actual);
	}
	
	@Test
	@Order(2)
	void testGetCategoryById_negative() {
		try {
		categoryDAO.findById(99);
		} catch (javax.persistence.EntityNotFoundException e) {
			assertEquals(e.getMessage(), "Unable to find com.revature.app.model.Category with id 99");
		}
	}
	
	@Test
	@Order(3)
	void testGetAllCategories_positive() {
		Category anotherCategory = new Category(2, "Dev Ops", "DevOps is a set of practices that combines software development and IT operations.");
		categoryDAO.save(anotherCategory);
		
		Category category1 = new Category(1, "Language", "Programming language");
		Category category2 = new Category(2, "Dev Ops", "DevOps is a set of practices that combines software development and IT operations.");
		List<Category> expected = new ArrayList<>();
		expected.add(category1);
		expected.add(category2);
		
		List<Category> actual = categoryDAO.findAll();
		
		assertEquals(expected, actual);
	}
	

	@Test
	@Order(4)
	void testDeleteCategory_postive() {
		
			categoryDAO.deleteById(1);
			assertEquals(categoryDAO.findById(1), null);		
	}


}
