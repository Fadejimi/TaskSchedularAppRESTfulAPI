package com.schedular.app.restcontroller;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.schedular.app.entities.Schedule;
import com.schedular.app.entities.User;
import com.schedular.app.exceptions.ResourceNotFoundException;
import com.schedular.app.services.ScheduleService;
import com.schedular.app.services.UserService;

@RestController
@RequestMapping("/api/v1/")
public class ScheduleController {

	@Autowired
	ScheduleService scheduleService;
	@Autowired
	UserService userService;
	
	@GetMapping("/schedules")
	public Collection<Schedule> getSchedules(@RequestHeader("Authorization") String token){
		String reqToken[] = token.split("Bearer ");
		User user = userService.getUserToken(reqToken[1]);
		if(user == null) {
			throw new ResourceNotFoundException("Invalid or unauthorized user");
		}
		List<Schedule> sch = scheduleService.getUserSchedules(user);
		
		return sch;
		
	}
	
	@GetMapping("/schedules/{id}")
	public Resource<Schedule> getSchedule(@RequestHeader("Authorization") String token, @PathVariable Long id){
		String reqToken[] = token.split("Bearer ");
		User user = userService.getUserToken(reqToken[1]);
		if(user == null) {
			throw new ResourceNotFoundException("Invalid or unauthorized user");
		}
		
		Schedule sch = scheduleService.getUserSchedule(id,user);

		Resource<Schedule> resource = new Resource<Schedule>(sch);
		ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(
				ControllerLinkBuilder.methodOn(this.getClass()).getSchedules(token)
		);
		resource.add(linkTo.withRel("all-users-schedules"));
		
		
		return resource;
	}
	
	@PostMapping("/schedules")
	public ResponseEntity<Object> setSchedule(@RequestHeader("Authorization") String token, @RequestBody Schedule schedule){
		String reqToken[] = token.split("Bearer ");
		User user = userService.getUserToken(reqToken[1]);
		if(user == null) {
			throw new ResourceNotFoundException("Invalid or unauthorized user");
		}
		schedule.setUser(user);
		Schedule s = scheduleService.saveSchdule(schedule);
		
		URI location = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(s.getId())
					.toUri();
		return ResponseEntity.created(location).build();
		
		
	}
	@PutMapping("/schedules")
	public ResponseEntity<Schedule> updateSchedule(@RequestHeader("Authorization") String token,@RequestBody Schedule schedule){
		String reqToken[] = token.split("Bearer ");
		User user = userService.getUserToken(reqToken[1]);
		if(user == null) {
			throw new ResourceNotFoundException("Invalid or unauthorized user");
		}
		schedule.setUser(user);
		Schedule s = scheduleService.saveSchdule(schedule);
		
		 return new ResponseEntity<Schedule>(s, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/schedules/{id}")
	public ResponseEntity<Schedule> deleteSchedule(@RequestHeader("Authorization") String token,@PathVariable Long id){
		String reqToken[] = token.split("Bearer ");
		User user = userService.getUserToken(reqToken[0]);
		if(user == null) {
			throw new ResourceNotFoundException("Invalid or unauthorized user");
		}
		Long delId = scheduleService.deleteSchdule(id,user);
		return new ResponseEntity<Schedule>(HttpStatus.NO_CONTENT);
	}
	
}
