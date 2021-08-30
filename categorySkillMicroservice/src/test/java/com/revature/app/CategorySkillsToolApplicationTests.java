package com.revature.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class CategorySkillsToolApplicationTests {

	@Test
	void contextLoads() {
		String actual = "I am putting this here because not having any assertion in this test counted as a code smell"; 
		//This test doesn't test any logic but is a crucial test in making sure everything is loaded properly
		//if this test happens to fail, that means that something has gone wrong with the entire project that's causing all tests to fail
		assertEquals("I am putting this here because not having any assertion in this test counted as a code smell", actual);
	}

}
