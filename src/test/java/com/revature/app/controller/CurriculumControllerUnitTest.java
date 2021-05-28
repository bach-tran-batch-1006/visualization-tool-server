package com.revature.app.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.app.service.CurriculumService;

@ExtendWith(MockitoExtension.class)
class CurriculumControllerUnitTest {

	private MockMvc mockMvc;

	private ObjectMapper objectmapper;
	
	@Mock
	private CurriculumService mockService;
	
	@InjectMocks
	private CurriculumController curriculumController;
	
	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(curriculumController).build();
	}
}
