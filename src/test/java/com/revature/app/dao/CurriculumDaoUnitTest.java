package com.revature.app.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CurriculumDaoUnitTest {

	@Test
	void simpleTest() {
		assertEquals(1,1);
	}
	
	
	@Test
	@Disabled
	void addCurriculumTest() {
		
	}
}
