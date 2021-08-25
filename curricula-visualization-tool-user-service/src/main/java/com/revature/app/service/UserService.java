package com.revature.app.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.app.dao.UserRepo;
import com.revature.app.model.User;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;


@Service
@NoArgsConstructor
@AllArgsConstructor(onConstructor=@__(@Autowired))
public class UserService {
	
	private UserRepo uDao;
	
	public User registerUser(User u) {
		try {
			return uDao.save(u);
		} catch(Exception e) {
			return null;
		}
	}
	
public User loginUser(String email, String password) {
		//findByUsername will return null if the user does not exist
		User u = uDao.findByEmail(email);
		//If username does not exist return null
		if(u == null) {
			return null;
		}
		else {
			//If you user exists but password is wrong return null
			if(!u.getPass().equals(password)) {
				return null;
			}
			//Else return the logged in user
			else {
				return u;
			}
		}
	}

public User displayUser(String email) {
	User u = uDao.findByEmail(email);
		if(u == null) {
			return null;
		}
		else {
			return u;
		}
	}

	public User getUserById(int id) {
		return uDao.getById(id);
	}
}
