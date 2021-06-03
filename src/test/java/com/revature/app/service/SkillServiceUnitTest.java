package com.revature.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.app.dao.SkillDAO;
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


@ExtendWith(MockitoExtension.class)
class SkillServiceUnitTest {
	
	@Mock
	private SkillDAO mockSkillDAO;
	
	@InjectMocks
	private SkillService skillService;
	
	@BeforeAll
	public static void setUp() {
		
	}
	
	@BeforeEach
	public void beforeTest() {
		Skill skill1 = new Skill(1, "", new Category(1, "", null));
		Skill skill2 = new Skill(0, "", new Category(1, "", null));
		Skill skill3 = new Skill(1, "TestSkill", new Category(1, "TestCat", null));
		SkillDTO skillDTO1 = new SkillDTO("TestSkill", new Category(1, "TestCat", null));
		SkillDTO skillDTO2 = new SkillDTO("Duplicate", new Category(1, "TestCat", null));
		SkillDTO skillDTO3 = new SkillDTO("Test", new Category(0, "BadCat", null));
		SkillDTO skillDTO4 = new SkillDTO("TestSkill", new Category(1, "TestCat", null));
		
		//Get
		lenient().when(mockSkillDAO.findById(eq(1))).thenReturn(skill1);
		lenient().when(mockSkillDAO.findById(eq(0))).thenReturn(null);
		
		//Add
		lenient().when(mockSkillDAO.save(new Skill(skillDTO1))).thenReturn(skill3);
		lenient().when(mockSkillDAO.save(new Skill(skillDTO2))).thenReturn(null);
		lenient().when(mockSkillDAO.save(new Skill(skillDTO3))).thenReturn(null);
		
		//Update
		lenient().when(mockSkillDAO.save(new Skill(skillDTO4))).thenReturn(skill3);
		lenient().when(mockSkillDAO.findById(eq(2))).thenReturn(skill3);
		lenient().when(mockSkillDAO.findById(eq(3))).thenReturn(skill2);
		lenient().when(mockSkillDAO.save(skill3)).thenReturn(skill3);
		
		//Delete
		lenient().when(mockSkillDAO.findById(4)).thenReturn(skill3).thenReturn(null);
		lenient().when(mockSkillDAO.findById(5)).thenReturn(skill3);
	}
	
	
	@Test
	void test_getAllSkills_happy() {
		Skill skill1 = new Skill(1, "", new Category(1, "", null));
		Skill skill2 = new Skill(2, "", new Category(1, "", null));
		List<Skill> expected = new ArrayList<Skill>();
		expected.add(skill1);
		expected.add(skill2);
		when(mockSkillDAO.findAll()).thenReturn(expected);
		List<Skill> actual = skillService.getAllSkills();
		assertEquals(expected, actual);
	}
	
	@Test
	void test_getAllSkills_noSkills() {
		List<Skill> expected = new ArrayList<Skill>();
		when(mockSkillDAO.findAll()).thenReturn(expected);
		List<Skill> actual = skillService.getAllSkills();
		assertEquals(expected, actual);
	}

//
	@Test
	void test_getSkillByID_happy() throws BadParameterException, EmptyParameterException, SkillNotFoundException {
		Skill expected = new Skill(1, "", new Category(1, "", null));
		Skill actual = skillService.getSkillByID("1");
		assertEquals(expected, actual);
	}
	
	@Test
	void test_getSkillbyID_BadID() {
		try {
			skillService.getSkillByID("0");
			fail("EmptyParameterException was not thrown");
		} catch (EmptyParameterException e) {
			fail("Wrong exception was thrown");
		} catch (BadParameterException e) {
			fail("Wrong exception was thrown");
		} catch (SkillNotFoundException e) {
			assertEquals(e.getMessage(), "The skill with ID 0 could not be found.");
		}
	}
	
	@Test
	void test_getSkillbyID_BadParameter() {
		try {
			skillService.getSkillByID("test");
			fail("EmptyParameterException was not thrown");
		} catch (EmptyParameterException e) {
			fail("Wrong exception was thrown");
		} catch (BadParameterException e) {
			assertEquals(e.getMessage(), "The skill ID provided must be of type int");
		} catch (SkillNotFoundException e) {
			fail("Wrong exception was thrown");
		}
	}
	
	@Test
	void test_getSkillbyID_EmptyParameter() {
		try {
			skillService.getSkillByID("   ");
			fail("EmptyParameterException was not thrown");
		} catch (EmptyParameterException e) {
			assertEquals(e.getMessage(), "The skill ID was left blank");
		} catch (BadParameterException e) {
			fail("Wrong exception was thrown");
		} catch (SkillNotFoundException e) {
			fail("Wrong exception was thrown");
		}
	}
	
//	
	@Test
	void test_addSkill_happy() throws EmptyParameterException, SkillNotAddedException {
		SkillDTO skillDTO = new SkillDTO("TestSkill", new Category(1, "TestCat", null));
		Skill expected = new Skill(1, "TestSkill", new Category(1, "TestCat", null));
		Skill actual = skillService.addSkill(skillDTO);
		assertEquals(expected, actual);
	}
	
	@Test
	void test_addSkill_emptyName() throws SkillNotAddedException {
		try {
			SkillDTO skillDTO = new SkillDTO("  ", new Category(1, "TestCat", null));
			skillService.addSkill(skillDTO);
			fail("EmptyParameterException was not thrown");
		} catch (EmptyParameterException e) {
			assertEquals(e.getMessage(), "The skill name was left blank");
		}
	}
	
	@Test
	void test_addSkill_badCategory() throws EmptyParameterException {
		try {
			SkillDTO skillDTO = new SkillDTO("Test", new Category(0, "BadCat", null));
			skillService.addSkill(skillDTO);
			fail("SkillNotAddedException was not thrown");
		} catch (SkillNotAddedException e) {
			assertEquals(e.getMessage(), "The skill could not be added due to a database issue");
		}
	}
	
	@Test
	void test_addSkill_duplicateSkill() throws EmptyParameterException {
		try {
			SkillDTO skillDTO = new SkillDTO("Duplicate", new Category(1, "TestCat", null));
			skillService.addSkill(skillDTO);
			fail("SkillNotAddedException was not thrown");
		} catch (SkillNotAddedException e) {
			assertEquals(e.getMessage(), "The skill could not be added due to a database issue");
		}
	}
	
//	
	@Test
	void test_updateSkill_happy() throws EmptyParameterException, SkillNotUpdatedException, BadParameterException, SkillNotFoundException {
		SkillDTO upSkill = new SkillDTO("TestSkill", new Category(1, "TestCat", null));
		Skill expected = new Skill(1, "TestSkill", new Category(1, "TestCat", null));
		Skill actual = skillService.updateSkill("3", upSkill);
		assertEquals(expected, actual);
	}
	
	@Test
	void test_updateSkill_skillNotUpdated() throws EmptyParameterException, SkillNotUpdatedException, BadParameterException, SkillNotFoundException {		
		try {
			SkillDTO upSkill = new SkillDTO("TestSkill", new Category(1, "TestCat", null));
			skillService.updateSkill("2", upSkill);
		} catch (SkillNotUpdatedException e) {
			assertEquals(e.getMessage(), "The skill could not be updated due to a database issue");
		}
	}
	
	@Test
	void test_updateSkill_noSkillToUpdate() throws EmptyParameterException, SkillNotUpdatedException, BadParameterException, SkillNotFoundException {
		try {
			SkillDTO upSkill = new SkillDTO("TestSkill", new Category(1, "TestCat", null));
			skillService.updateSkill("5", upSkill);
		} catch (SkillNotFoundException e) {
			assertEquals(e.getMessage(), "The skill could not be updated because it couldn't be found");
		}
	}
	
	@Test
	void test_updateSkill_emptyID() throws SkillNotUpdatedException, SkillNotFoundException {
		try {
			SkillDTO upSkill = new SkillDTO("", new Category(1, "", null));
			skillService.updateSkill("   ", upSkill);
			fail("EmptyParameterException was not thrown");
		} catch (EmptyParameterException e) {
			assertEquals(e.getMessage(), "The skill ID was left blank");
		} catch (BadParameterException e) {
			fail("Wrong exception was thrown");
		}
	}
	
	@Test
	void test_updateSkill_badParameter() throws SkillNotUpdatedException, EmptyParameterException, SkillNotFoundException {
		try {
			SkillDTO upSkill = new SkillDTO("Test", new Category(1, "", null));
			skillService.updateSkill("test", upSkill);
			fail("BadParameterException was not thrown");
		} catch (BadParameterException e) {
			assertEquals(e.getMessage(), "The skill ID provided must be of type int");
		}
	}
	
	@Test
	void test_updateSkill_emptyName() throws SkillNotUpdatedException, BadParameterException, SkillNotFoundException {
		try {
			SkillDTO upSkill = new SkillDTO("", new Category(1, "", null));
			skillService.updateSkill("1", upSkill);
			fail("EmptyParameterException was not thrown");
		} catch (EmptyParameterException e) {
			assertEquals(e.getMessage(), "The skill name was left blank");
		}
	}

//	
	@Test
	void test_deleteSkill_happy() throws EmptyParameterException, BadParameterException, SkillNotDeletedException, SkillNotFoundException, ForeignKeyConstraintException {
		Skill expected = new Skill(1, "TestSkill", new Category(1, "TestCat", null));
		Skill actual = skillService.deleteSkill("4");
		assertEquals(expected, actual);
	}
	
	@Test
	void test_deleteSkill_IDDoesntExist() throws EmptyParameterException, BadParameterException, SkillNotDeletedException, ForeignKeyConstraintException {
		try {
			skillService.deleteSkill("0");
			fail("SkillNotFoundException was not thrown");
		} catch (SkillNotFoundException e) {
			assertEquals(e.getMessage(), "The skill could not be deleted because it couldn't be found");
		}
	}
	
	@Test
	void test_deleteSkill_BadParameter() throws EmptyParameterException, SkillNotDeletedException, SkillNotFoundException, ForeignKeyConstraintException {
		try {
			skillService.deleteSkill("test");
			fail("BadParameterException was not thrown");
		} catch (BadParameterException e) {
			assertEquals(e.getMessage(), "The skill ID provided must be of type int");
		}
	}
	
	@Test
	void test_deleteSkill_emptyParameter() throws BadParameterException, SkillNotDeletedException, SkillNotFoundException, ForeignKeyConstraintException {
		try {
			skillService.deleteSkill("      ");
			fail("EmptyParameterException was not thrown");
		} catch (EmptyParameterException e) {
			assertEquals(e.getMessage(), "The skill ID was left blank");
		}
	}
	
	@Test
	void test_deleteSkill_skillNotDeleted() throws EmptyParameterException, BadParameterException, SkillNotFoundException, ForeignKeyConstraintException {
		try {
			skillService.deleteSkill("5");
			fail("SkillNotDeletedException was not thrown");
		} catch (SkillNotDeletedException e) {
			assertEquals(e.getMessage(), "The skill could not be deleted because of a database issue");
		}
	}
	
	
}
