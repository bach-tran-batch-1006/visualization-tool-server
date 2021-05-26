package com.revature.app.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

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
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class VisualizationControllerTest {

	//allow test to mock and http request
	@Autowired
  private MockMvc mockmvc;
	
	@Test
	void CreateEndpoint() throws Exception {
		  //perform define the type of request
		  //MockMvcMatchers return the status of testing that endpoint
		  //will be http verb post for now I used get so I can do it from browser instead of postman will change later
	    mockmvc.perform(get("/create"))
         .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void FindEndpoint() throws Exception {

	     mockmvc.perform(get("/find/1"))
         .andExpect(MockMvcResultMatchers.status().isOk());
    
	}
	
	@Test
	void FindEndpointInvalidVisualizationDoNotExist() throws Exception {
	     mockmvc.perform(get("/find/35"))
         .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    
	}	

	@Test
	void UpdateEndpointvalidVisualization() throws Exception {
		
	     mockmvc.perform(put("/update/1/newtitle"))
         .andExpect(MockMvcResultMatchers.status().isOk());
    
	}
					
	@Test
	void UpdateEndpointInvalidVisualization() throws Exception {
		
	     mockmvc.perform(put("/update/1/newtitle"))
         .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    
	}
	
	@Test
	void  deleteEndpointvalidVisualization() throws Exception {
		
	     mockmvc.perform(delete("/delete/1"))
         .andExpect(MockMvcResultMatchers.status().isOk());
    
	}
	
	@Test
	void  deleteEndpointInvalidVisualization() throws Exception {
		
	     mockmvc.perform(delete("/delete/35"))
         .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    
	}

	@Test
	void  GetallEndpointvalidVisualizations() throws Exception {
	
	     mockmvc.perform(get("/getall"))
         .andExpect(MockMvcResultMatchers.status().isOk());
    
	}

}
