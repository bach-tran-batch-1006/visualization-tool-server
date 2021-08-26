package com.revature.app.dao;

import com.revature.app.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
	
	public List<User> findAll();
	public User findByEmail(String email);
	public User findById(int id);

}
