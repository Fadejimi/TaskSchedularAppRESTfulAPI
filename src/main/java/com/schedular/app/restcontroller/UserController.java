package com.schedular.app.restcontroller;

import java.net.URI;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.schedular.app.entities.User;
import com.schedular.app.services.UserService;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("/users")
	public Collection<User> getUsers(){
		return userService.getUsers();
	}
	@GetMapping("/users/{id}")
	public Resource<User> getAUsers(@PathVariable Long id){
		
		User user = userService.getUser(id);
		Resource<User> resource = new Resource<User>(user);
		ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(
				ControllerLinkBuilder.methodOn(this.getClass()).getUsers()
		);
		resource.add(linkTo.withRel("all-users"));
		
		return resource;
	}
	
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
		User savedUser = userService.saveUser(user);
		URI location = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(savedUser.getId())
					.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
}
