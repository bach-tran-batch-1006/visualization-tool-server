package com.revature.app.controller;

/*import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.app.dto.VisualizationDTO;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.VisualizationNotFoundException;
import com.revature.app.model.Visualization;
import com.revature.app.service.VisualizationService;

@ExtendWith(MockitoExtension.class)*/
class VisualizationControllerTest {

	/*private MockMvc mockmvc;

	private ObjectMapper objectmapper;
	
	@Mock
	private VisualizationService mockService;

	@InjectMocks
	private VisualizationController vscontroller;

	@BeforeEach
	void setup() throws VisualizationNotFoundException, BadParameterException, EmptyParameterException {
		this.mockmvc = MockMvcBuilders.standaloneSetup(vscontroller).build();
		this.objectmapper = new ObjectMapper();

		VisualizationDTO visualizationdto = new VisualizationDTO("first", null, null);
		VisualizationDTO visualizationdtoblank= new VisualizationDTO("", null, null);
		Visualization visualization = new Visualization("first", null);
		Visualization updatevisualization = new Visualization("newname", null);
		VisualizationDTO nameupdate= new VisualizationDTO("newname", null, null);
		List <Visualization> all = new ArrayList<>();
		all.add(visualization);
		lenient().when(mockService.createVisualization(visualizationdto)).thenReturn(visualization);
		
		lenient().when(mockService.findVisualizationByID("1")).thenReturn(visualization);
		lenient().when(mockService.findVisualizationByID("35")).thenThrow(new VisualizationNotFoundException());
		lenient().when(mockService.findVisualizationByID(" ")).thenThrow(new EmptyParameterException());
		lenient().when(mockService.findVisualizationByID("test")).thenThrow(new BadParameterException());
		
		
		lenient().when(mockService.updateVisualizationByID("1", nameupdate)).thenReturn(updatevisualization);
		lenient().when(mockService.updateVisualizationByID("98", nameupdate)).thenThrow(new VisualizationNotFoundException());
		lenient().when(mockService.updateVisualizationByID("test", nameupdate)).thenThrow(new BadParameterException());
		
		
		lenient().when(mockService.deleteVisualizationByID("1")).thenReturn(1);
		lenient().when(mockService.deleteVisualizationByID("98")).thenThrow(new VisualizationNotFoundException());
		lenient().when(mockService.deleteVisualizationByID(" ")).thenThrow(new EmptyParameterException());
		lenient().when(mockService.deleteVisualizationByID("test")).thenThrow(new BadParameterException());
		
		
		lenient().when(mockService.findAllVisualization()).thenReturn(all);
		lenient().when(mockService.createVisualization(visualizationdtoblank)).thenThrow(new EmptyParameterException());
		lenient().when(mockService.updateVisualizationByID("1",visualizationdtoblank)).thenThrow(new EmptyParameterException());
		

	}

	@Test
	void CreateEndpoint() throws Exception {

		VisualizationDTO body = new VisualizationDTO("first", null, null);
		String bodystring = this.objectmapper.writeValueAsString(body);

		Visualization visualization = new Visualization("first", null);
		String expected = this.objectmapper.writeValueAsString(visualization);

		this.mockmvc.perform(post("/visualization").contentType(MediaType.APPLICATION_JSON).content(bodystring))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andDo(print())
				.andExpect(MockMvcResultMatchers.content().json(expected));

	}
	
	@Test
	void CreatEndpointvalidBlankVisualization() throws Exception {
		VisualizationDTO bodyupdate = new VisualizationDTO("", null, null);
		String bodystring = this.objectmapper.writeValueAsString(bodyupdate);


		this.mockmvc.perform(post("/visualization").contentType(MediaType.APPLICATION_JSON).content(bodystring))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

//
	@Test
	void FindEndpoint() throws Exception {
		Visualization visualization = new Visualization("first", null);
		String expected = this.objectmapper.writeValueAsString(visualization);
		mockmvc.perform(get("/visualization/1")).andExpect(MockMvcResultMatchers.content().json(expected)).andDo(print())
		.andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	void FindEndpointInvalidVisualizationDoNotExist() throws Exception {
		mockmvc.perform(get("/visualization/35")).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	void test_findByID_EmptyID() throws Exception {
		mockmvc.perform(get("/visualization/ ")).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void test_findByID_BadID() throws Exception {
		mockmvc.perform(get("/visualization/test")).andExpect(MockMvcResultMatchers.status().is(400));
	}

//

	@Test
	void UpdateEndpointvalidVisualization() throws Exception {
		VisualizationDTO bodyupdate = new VisualizationDTO("newname", null, null);
		String bodystring = this.objectmapper.writeValueAsString(bodyupdate);

		Visualization visualization = new Visualization("newname", null);
		String expected = this.objectmapper.writeValueAsString(visualization);

		this.mockmvc.perform(put("/visualization/1").contentType(MediaType.APPLICATION_JSON).content(bodystring))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expected));

	}


	@Test
	void UpdateEndpointDoNotExistVisualization() throws Exception {
		VisualizationDTO bodyupdate = new VisualizationDTO("newname", null, null);
		String bodystring = this.objectmapper.writeValueAsString(bodyupdate);

		this.mockmvc.perform(put("/visualization/98").contentType(MediaType.APPLICATION_JSON).content(bodystring))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	void updateEndpointinvalidBlankVisualization() throws Exception {
		VisualizationDTO bodyupdate = new VisualizationDTO("", null, null);
		String bodystring = this.objectmapper.writeValueAsString(bodyupdate);


		this.mockmvc.perform(put("/visualization/1").contentType(MediaType.APPLICATION_JSON).content(bodystring))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void test_updateVisualization_badID() throws Exception {
		VisualizationDTO bodyupdate = new VisualizationDTO("newname", null, null);
		String bodystring = this.objectmapper.writeValueAsString(bodyupdate);
		this.mockmvc.perform(put("/visualization/test").contentType(MediaType.APPLICATION_JSON).content(bodystring))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

//
	@Test
	void deleteEndpointvalidVisualization() throws Exception {
		mockmvc.perform(delete("/visualization/1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json("1"));
	}

	@Test
	void deleteEndpointDoNotExistVisualization() throws Exception {
		mockmvc.perform(delete("/visualization/98")).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	void test_deleteVisualization_badID() throws Exception {
		mockmvc.perform(delete("/visualization/test")).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void test_deleteVisualization_emptyID() throws Exception {
		mockmvc.perform(delete("/visualization/ ")).andExpect(MockMvcResultMatchers.status().is(400));
	}

	
//
	@Test
	void GetallEndpointvalidVisualizations() throws Exception {
		Visualization visualization = new Visualization("first", null);
		List <Visualization> all = new ArrayList<>();
		all.add(visualization);
		String allexpect= this.objectmapper.writeValueAsString(all);
		mockmvc.perform(get("/visualization").contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.content().json(allexpect))
		.andExpect(MockMvcResultMatchers.status().isOk());

	}
	
//
	@Test
	void test_getAllUniqueSkillsByVisualization_happy() throws Exception {
		mockmvc.perform(get("/visualization/1/skills")).andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	@Test
	void test_getAllUniqueSkillsByVisualization_VisualizationNotFound() throws Exception {
		mockmvc.perform(get("/visualization/2/skills")).andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	@Test
	void test_getAllUniqueSkillsByVisualization_badID() throws Exception {
		mockmvc.perform(get("/visualization/test/skills")).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void test_getAllUniqueSkillsByVisualization_emptyID() throws Exception {
		mockmvc.perform(get("/visualization/ /skills")).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
//
	@Test
	void test_getAllUniqueCategoriesByVisualization_happy() throws Exception {
		mockmvc.perform(get("/visualization/1/categories")).andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	@Test
	void test_getAllUniqueCategoriesByVisualization_visualizationNotFound() throws Exception {
		mockmvc.perform(get("/visualization/2/categories")).andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	@Test
	void test_getAllUniqueCategoriesByVisualization_badID() throws Exception {
		mockmvc.perform(get("/visualization/test/categories")).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void test_getAllUniqueCategoriesByVisualization_emptyID() throws Exception {
		mockmvc.perform(get("/visualization/ /categories")).andExpect(MockMvcResultMatchers.status().is(400));
	}*/

}
