package com.revature.app.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

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
import com.revature.app.dto.SkillDTO;
import com.revature.app.exception.BadParameterException;
import com.revature.app.exception.EmptyParameterException;
import com.revature.app.exception.ForeignKeyConstraintException;
import com.revature.app.exception.SkillNotAddedException;
import com.revature.app.exception.SkillNotDeletedException;
import com.revature.app.exception.SkillNotFoundException;
import com.revature.app.exception.SkillNotUpdatedException;
import com.revature.app.model.Category;
import com.revature.app.model.Skill;
import com.revature.app.service.SkillService;


@ExtendWith(MockitoExtension.class)
class SkillControllerUnitTest {

	private MockMvc mockMvc;
	
	private ObjectMapper om;
	
	@Mock
	private SkillService mockSkillService;
	
	@InjectMocks
	private SkillController skillController;
	
	
	@BeforeEach
	void setup() throws BadParameterException, EmptyParameterException, SkillNotFoundException, SkillNotAddedException, SkillNotUpdatedException, SkillNotDeletedException, ForeignKeyConstraintException {
		om = new ObjectMapper();
		mockMvc = MockMvcBuilders.standaloneSetup(skillController).build();
		Skill skill1 = new Skill(1, "Skill1", new Category(1, "Cat1", null));
		SkillDTO skillDTO1 = new SkillDTO("TestSkill", new Category(1, "TestCat", "Description"));
		SkillDTO skillDTO2 = new SkillDTO(" ", new Category(1, "TestCat", "Description"));
		SkillDTO skillDTO3 = new SkillDTO("ProblemSkill", new Category(1, "TestCat", "Description"));
		SkillDTO skillDTO4 = new SkillDTO("ProblemCat", new Category(1, "   ", "Description"));
		
		lenient().when(mockSkillService.getSkillByID(eq("1"))).thenReturn(skill1);
		lenient().when(mockSkillService.getSkillByID(eq("2"))).thenThrow(new SkillNotFoundException());
		lenient().when(mockSkillService.getSkillByID(eq(" "))).thenThrow(new EmptyParameterException());
		lenient().when(mockSkillService.getSkillByID(eq("test"))).thenThrow(new BadParameterException());
		
		lenient().when(mockSkillService.addSkill(skillDTO1)).thenReturn(skill1);
		lenient().when(mockSkillService.addSkill(skillDTO2)).thenThrow(new EmptyParameterException());
		lenient().when(mockSkillService.addSkill(skillDTO3)).thenThrow(new SkillNotAddedException());
		lenient().when(mockSkillService.addSkill(skillDTO4)).thenThrow(new SkillNotAddedException());
		
		lenient().when(mockSkillService.updateSkill(eq("1"), eq(skillDTO1))).thenReturn(skill1);
		lenient().when(mockSkillService.updateSkill(eq("2"), eq(skillDTO1))).thenThrow(new SkillNotUpdatedException());
		lenient().when(mockSkillService.updateSkill(eq(" "), eq(skillDTO1))).thenThrow(new EmptyParameterException());
		lenient().when(mockSkillService.updateSkill(eq("test"), eq(skillDTO1))).thenThrow(new BadParameterException());
		lenient().when(mockSkillService.updateSkill(eq("1"), eq(skillDTO2))).thenThrow(new EmptyParameterException());
		lenient().when(mockSkillService.updateSkill(eq("1"), eq(skillDTO3))).thenThrow(new SkillNotFoundException());
		lenient().when(mockSkillService.updateSkill(eq("1"), eq(skillDTO4))).thenThrow(new SkillNotUpdatedException());
		
		lenient().when(mockSkillService.deleteSkill(eq("1"))).thenReturn(skill1);
		lenient().when(mockSkillService.deleteSkill(eq("2"))).thenThrow(new SkillNotDeletedException());
		lenient().when(mockSkillService.deleteSkill(eq("3"))).thenThrow(new SkillNotFoundException());
		lenient().when(mockSkillService.deleteSkill(eq(" "))).thenThrow(new EmptyParameterException());
		lenient().when(mockSkillService.deleteSkill(eq("test"))).thenThrow(new BadParameterException());
		lenient().when(mockSkillService.deleteSkill(eq("4"))).thenThrow(new ForeignKeyConstraintException());
	}
	
	
	@Test
	void test_getAllSkills_happy() throws Exception {
		Skill skill1 = new Skill(1, "", new Category(1, "", null));
		Skill skill2 = new Skill(2, "", new Category(1, "", null));
		List<Skill> expected = new ArrayList<Skill>();
		expected.add(skill1);
		expected.add(skill2);
		when(mockSkillService.getAllSkills()).thenReturn(expected);
		mockMvc.perform(get("/allSkills")).andExpect(MockMvcResultMatchers.status().is(200));
	}


//
	@Test
	void test_getSkillByID_happy() throws Exception {
		Skill expected = new Skill(1, "Skill1", new Category(1, "Cat1", null));
		//Skill actual = skillController.getSkillByID();
		mockMvc.perform(get("/skill/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void test_getSkillbyID_BadID() throws Exception {
		mockMvc.perform(get("/skill/2")).andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	@Test
	void test_getSkillbyID_BadParameter() throws Exception {
		mockMvc.perform(get("/skill/test")).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void test_getSkillbyID_EmptyParameter() throws Exception {
		mockMvc.perform(get("/skill/ ")).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
//	
	@Test
	void test_addSkill_happy() throws Exception {
		SkillDTO skillDTO = new SkillDTO("TestSkill", new Category(1, "TestCat", "Description"));
		String body = om.writeValueAsString(skillDTO);
		mockMvc.perform(
				post("/skill")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body)
				).andExpect(MockMvcResultMatchers.status().is(201));
	}
	
	@Test
	void test_addSkill_emptyName() throws Exception {
		SkillDTO skillDTO = new SkillDTO(" ", new Category(1, "TestCat", "Description"));
		String body = om.writeValueAsString(skillDTO);
		mockMvc.perform(
				post("/skill")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body)
				).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void test_addSkill_badCategory() throws Exception {
		SkillDTO problemSkill = new SkillDTO("ProblemCat", new Category(1, "   ", "Description"));
		String body = om.writeValueAsString(problemSkill);
		mockMvc.perform(
				post("/skill")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body)
				).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void test_addSkill_duplicateSkill() throws Exception {
		SkillDTO problemSkill = new SkillDTO("ProblemSkill", new Category(1, "TestCat", "Description"));
		String body = om.writeValueAsString(problemSkill);
		mockMvc.perform(
				post("/skill")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body)
				).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
//	
	@Test
	void test_updateSkill_happy() throws Exception {
		SkillDTO skillDTO = new SkillDTO("TestSkill", new Category(1, "TestCat", "Description"));
		String body = om.writeValueAsString(skillDTO);
		mockMvc.perform(
				put("/skill/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body)
				).andExpect(MockMvcResultMatchers.status().is(202));
	}
	
	@Test
	void test_updateSkill_emptyName() throws Exception {
		SkillDTO skillDTO = new SkillDTO(" ", new Category(1, "TestCat", "Description"));
		String body = om.writeValueAsString(skillDTO);
		mockMvc.perform(
				put("/skill/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body)
				).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void test_updateSkill_emptyPathParam() throws Exception {
		SkillDTO skillDTO = new SkillDTO("TestSkill", new Category(1, "TestCat", "Description"));
		String body = om.writeValueAsString(skillDTO);
		mockMvc.perform(
				put("/skill/ ")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body)
				).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void test_updateSkill_badID() throws Exception {
		SkillDTO skillDTO = new SkillDTO("ProblemSkill", new Category(1, "TestCat", "Description"));
		String body = om.writeValueAsString(skillDTO);
		mockMvc.perform(
				put("/skill/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body)
				).andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	@Test
	void test_updateSkill_badParameter() throws Exception {
		SkillDTO skillDTO = new SkillDTO("TestSkill", new Category(1, "TestCat", "Description"));
		String body = om.writeValueAsString(skillDTO);
		mockMvc.perform(
				put("/skill/test")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body)
				).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void test_updateSkill_badCategory() throws Exception {
		SkillDTO skillDTO = new SkillDTO("ProblemCat", new Category(1, "   ", "Description"));
		String body = om.writeValueAsString(skillDTO);
		mockMvc.perform(
				put("/skill/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body)
				).andExpect(MockMvcResultMatchers.status().is(400));
	}

//	
	@Test
	void test_deleteSkill_happy() throws Exception {
		mockMvc.perform(delete("/skill/1")).andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	@Test
	void test_deleteSkill_badID() throws Exception {
		mockMvc.perform(delete("/skill/2")).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void test_deleteSkill_skillDoesNotExist() throws Exception {
		mockMvc.perform(delete("/skill/3")).andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	@Test
	void test_deleteSkill_emptyParameter() throws Exception {
		mockMvc.perform(delete("/skill/ ")).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void test_deleteSkill_badParameter() throws Exception {
		mockMvc.perform(delete("/skill/test")).andExpect(MockMvcResultMatchers.status().is(400));
	}
	
	@Test
	void test_deleteSkill_foreignKey() throws Exception {
		mockMvc.perform(delete("/skill/4")).andExpect(MockMvcResultMatchers.status().is(400));
	}
	

}
