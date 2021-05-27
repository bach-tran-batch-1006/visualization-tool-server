package com.revature.app.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.revature.app.CurriculaVisualizationToolApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CurriculaVisualizationToolApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc

class CategoryControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void test_testEndpoint() throws Exception {
		mockMvc.perform(get("/test"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
