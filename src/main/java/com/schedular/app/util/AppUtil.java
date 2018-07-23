package com.schedular.app.util;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.schedular.app.entities.User;
import com.schedular.app.services.UserService;

@Component
public class AppUtil {
	@Autowired
	static UserService userService;
	
	public static boolean authenticate(String token) {
		String reqToken[] = token.split("Bearer ");
		User user = userService.getUserToken(reqToken[1]);
		
		if (user == null) {
			return false;
		}
		return true;
	}
	
	public static User getUser(String token) {
		String reqToken[] = token.split("Bearer ");
		User user = userService.getUserToken(reqToken[1]);
		
		return user;
	}
	public static ResponseEntity<Object> getResponseEntity(long id) {
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(id)
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
}
