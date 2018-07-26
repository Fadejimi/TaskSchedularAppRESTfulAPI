package com.schedular.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schedular.app.entities.User;
import com.schedular.app.repos.UserRepo;

@Service
public class UserService {

	@Autowired
	UserRepo userRepo;
	
	public List<User> getUsers(){
		return userRepo.findAll();
	}
	public User getUser(Long id){
		return userRepo.findById(id).get();
	}
	public User getUserToken(String token){
		return userRepo.findByToken(token);
	}
	public User saveUser(User user){
		 return userRepo.save(user);
	}

	public User getUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}

}
