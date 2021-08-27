package com.revature.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.revature.app.dao.UserRepo;
import com.revature.app.dto.UserDTO;
import com.revature.app.model.User;



@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceUnitTest {
	@Mock
	private UserRepo uDao;
	
	@InjectMocks
	private UserService uServ;
	
	@Test
	void testResgisterUser_positive() {
		UserService uNoArg = new UserService();
		User user = new User("first","last","email","pass");
		UserDTO userDTO = new UserDTO("first","last","email","pass");
		
		User notUser = new User("first","last","email","pass1");
		
		notUser = new User("email","pass1");
		notUser = new User(1);
		notUser.setEmail("email");
		notUser.setFirst(null);
		notUser.setLast(null);
		notUser.setId(0);
		notUser.setPass(null);
		
		when(uDao.save(user)).thenReturn(user);
		
		User actual = uServ.registerUser(userDTO);
		System.out.println(actual);
		assertEquals(actual.getEmail(), user.getEmail());
		
	}
	
	@Test
	void testResgisterUser_negative() {
		User user = new User("first","last","email","pass");
		UserDTO userDTO = new UserDTO("first","last","email","pass");
		UserDTO userDTOs = new UserDTO();
		User notUser = new User("first","last","email","pass1");
		notUser = new User("email","pass1");
		notUser = new User(1);
		
		when(uDao.save(null)).thenReturn(null);
		
		User actual = uServ.registerUser(userDTO);
		
		
		
	}
	
	@Test
	void testResgisterUser_negative_exception() throws Exception {
		User user = new User("first","last","email","pass");
		UserDTO userDTO = new UserDTO("first","last","email","pass");
		
		User notUser = new User("first","last","email","pass1");
		notUser = new User("email","pass1");
		notUser = new User(1);
		Exception e = new Exception();
		when(uDao.save(null)).thenThrow();
		
		User actual = uServ.registerUser(userDTO);
		
		
		
	}
	
	@Test
	void testLoginUser_positive() {
		User user = new User("first","last","email","pass");
		User notUser = new User("first","last","email","pass1");
		
		when(uDao.findByEmail("email")).thenReturn(user);
		
		User actual = uServ.loginUser(user.getEmail(), user.getPass());
		
		assertEquals(actual.getEmail(), user.getEmail());
		
	}
	@Test
	void testLoginUser_negative() {
		User user = new User("first","last","email","pass");
		User notUser = new User("first","last","email","pass1");
		
		when(uDao.findByEmail("email")).thenReturn(null);
		
		User actual = uServ.loginUser(user.getEmail(), user.getPass());
		
		
		
	}
	@Test
	void testLoginUser_wrong_password() {
		User user = new User("first","last","email","pass");
		User notUser = new User("first","last","email","pass1");
		
		when(uDao.findByEmail("email")).thenReturn(user);
		
		User actual = uServ.loginUser(notUser.getEmail(), notUser.getPass());
		
		
		
	}
	
	@Test
	void testDisplay_positive() {
		User user = new User("first","last","email","pass");
		User notUser = new User("first","last","email","pass1");
		
		when(uDao.findByEmail("email")).thenReturn(user);
		
		User actual = uServ.displayUser(user.getEmail());
		
		assertEquals(actual.getEmail(), user.getEmail());
		
	}
	
	@Test
	void testDisplay_negative() {
		User user = new User("first","last","email","pass");
		User notUser = new User("first","last","email","pass1");
		
		when(uDao.findByEmail("email")).thenReturn(null);
		
		User actual = uServ.displayUser(user.getEmail());
		
	}
	
	@Test
	void testGetUserByID_positive() {
		User user = new User("first","last","email","pass");
		User notUser = new User("first","last","email","pass1");
		
		when(uDao.findById(1)).thenReturn(user);
		
		User actual = uServ.getUserById(1);
		
		assertEquals(actual.getEmail(), user.getEmail());
		
	}
	
	
	
	
	
	
	
	
}
