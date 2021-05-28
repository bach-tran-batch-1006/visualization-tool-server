package com.revature.app.controller;

import static org.mockito.Mockito.lenient;
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
import com.revature.app.exception.VisualizationNotFoundException;
import com.revature.app.model.Visualization;
import com.revature.app.service.VisualizationService;

@ExtendWith(MockitoExtension.class)
class VisualizationControllerTest {

	private MockMvc mockmvc;

	private ObjectMapper objectmapper;
	@Mock
	private VisualizationService mockservice;

	@InjectMocks
	private VisualizationController vscontroller;

	@BeforeEach
	void setup() throws VisualizationNotFoundException, BadParameterException {
		this.mockmvc = MockMvcBuilders.standaloneSetup(vscontroller).build();
		this.objectmapper = new ObjectMapper();

		VisualizationDTO visualizationdto = new VisualizationDTO("first", null);
		VisualizationDTO visualizationdtoblank= new VisualizationDTO("", null);
		Visualization visualization = new Visualization(1, "first", null);
		Visualization updatevisualization = new Visualization(1, "newname", null);
		VisualizationDTO nameupdate= new VisualizationDTO("newname", null);
		List <Visualization> all = new ArrayList<>();
		all.add(visualization);
		lenient().when(mockservice.createVisualization(visualizationdto)).thenReturn(visualization);
		lenient().when(mockservice.findVisualizationByID(1)).thenReturn(visualization);
		lenient().when(mockservice.findVisualizationByID(35)).thenThrow(new VisualizationNotFoundException());
		lenient().when(mockservice.updateVisualizationByID(1, nameupdate)).thenReturn(updatevisualization);
		lenient().when(mockservice.updateVisualizationByID(98, nameupdate)).thenThrow(new VisualizationNotFoundException());
		lenient().when(mockservice.deleteVisualizationByID(1)).thenReturn(1);
		lenient().when(mockservice.deleteVisualizationByID(98)).thenThrow(new VisualizationNotFoundException());
		lenient().when(mockservice.findAllVisualization()).thenReturn(all);
		lenient().when(mockservice.createVisualization(visualizationdtoblank)).thenThrow(new BadParameterException());
		lenient().when(mockservice.updateVisualizationByID(1,visualizationdtoblank)).thenThrow(new BadParameterException());
		}

	@Test
	void CreateEndpoint() throws Exception {

		VisualizationDTO body = new VisualizationDTO("first", null);
		String bodystring = this.objectmapper.writeValueAsString(body);

		Visualization visualization = new Visualization(1, "first", null);
		String expected = this.objectmapper.writeValueAsString(visualization);

		this.mockmvc.perform(post("/visualization").contentType(MediaType.APPLICATION_JSON).content(bodystring))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andDo(print())
				.andExpect(MockMvcResultMatchers.content().json(expected));

	}
	
	@Test
	void CreatEndpointvalidBlankVisualization() throws Exception {
		VisualizationDTO bodyupdate = new VisualizationDTO("", null);
		String bodystring = this.objectmapper.writeValueAsString(bodyupdate);


		this.mockmvc.perform(post("/visualization").contentType(MediaType.APPLICATION_JSON).content(bodystring))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

	@Test
	void FindEndpoint() throws Exception {

	

		Visualization visualization = new Visualization(1, "first", null);
		String expected = this.objectmapper.writeValueAsString(visualization);
		mockmvc.perform(get("/visualization/1")).andExpect(MockMvcResultMatchers.content().json(expected)).andDo(print())
		.andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	void FindEndpointInvalidVisualizationDoNotExist() throws Exception {
		mockmvc.perform(get("/visualization/35")).andExpect(MockMvcResultMatchers.status().isNotFound());

	}

	@Test
	void UpdateEndpointvalidVisualization() throws Exception {
		VisualizationDTO bodyupdate = new VisualizationDTO("newname", null);
		String bodystring = this.objectmapper.writeValueAsString(bodyupdate);

		Visualization visualization = new Visualization(1, "newname", null);
		String expected = this.objectmapper.writeValueAsString(visualization);

		this.mockmvc.perform(put("/visualization/1").contentType(MediaType.APPLICATION_JSON).content(bodystring))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expected));

	}


	@Test
	void UpdateEndpointDoNotExistVisualization() throws Exception {
		VisualizationDTO bodyupdate = new VisualizationDTO("newname", null);
		String bodystring = this.objectmapper.writeValueAsString(bodyupdate);

		Visualization visualization = new Visualization(1, "newname", null);
		String expected = this.objectmapper.writeValueAsString(visualization);

		this.mockmvc.perform(put("/visualization/98").contentType(MediaType.APPLICATION_JSON).content(bodystring))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
			
	

	}
	
	@Test
	void updateEndpointinvalidBlankVisualization() throws Exception {
		VisualizationDTO bodyupdate = new VisualizationDTO("", null);
		String bodystring = this.objectmapper.writeValueAsString(bodyupdate);


		this.mockmvc.perform(put("/visualization/1").contentType(MediaType.APPLICATION_JSON).content(bodystring))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

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
	void GetallEndpointvalidVisualizations() throws Exception {
		Visualization visualization = new Visualization(1, "first", null);
		List <Visualization> all = new ArrayList<>();
		all.add(visualization);
		String allexpect= this.objectmapper.writeValueAsString(all);
		mockmvc.perform(get("/visualization").contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.content().json(allexpect))
		.andExpect(MockMvcResultMatchers.status().isOk());

	}

}
