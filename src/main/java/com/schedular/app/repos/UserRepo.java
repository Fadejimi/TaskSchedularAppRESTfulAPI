package com.schedular.app.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.schedular.app.entities.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
	List<User> findAll();
	User findByToken(String token);
}
