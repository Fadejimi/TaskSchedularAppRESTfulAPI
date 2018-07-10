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
import com.schedular.app.entities.Task;
import com.schedular.app.entities.TasksMilestone;
import com.schedular.app.entities.User;
import com.schedular.app.exceptions.ResourceNotFoundException;
import com.schedular.app.services.ScheduleService;
import com.schedular.app.services.TaskService;
import com.schedular.app.services.UserService;

@RestController
@RequestMapping("/api/v1/")
public class TaskController {

	@Autowired
	ScheduleService scheduleService;
	@Autowired
	UserService userService;
	@Autowired
	TaskService taskService;
	
	@GetMapping("/schedules/{sch_id}/tasks")
	public Collection<Task> getTasks(@RequestHeader("Authorization") String token,@PathVariable Long sch_id){
		String reqToken[] = token.split("Bearer ");
		User user = userService.getUserToken(reqToken[1]);
		if(user == null) {
			throw new ResourceNotFoundException("Invalid or unauthorized user");
		}
		List<Task> tasks = taskService.findAllUserSchduleTasks(sch_id,user.getId());
		return tasks;
	}
	
	@GetMapping("/schedules/{sch_id}/tasks/{id}")
	public Resource<Task> getTask(@RequestHeader("Authorization") String token,@PathVariable Long sch_id,@PathVariable Long id){
		String reqToken[] = token.split("Bearer ");
		User user = userService.getUserToken(reqToken[1]);
		if(user == null) {
			throw new ResourceNotFoundException("Invalid or unauthorized user");
		}
		Task task = taskService.findUserSchduleTask(id, sch_id,user.getId());
		

		Resource<Task> resource = new Resource<Task>(task);
		ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(
				ControllerLinkBuilder.methodOn(this.getClass()).getTasks(token,sch_id)
		);
		resource.add(linkTo.withRel("all-schedule-tasks"));
		
		
		return resource;
	}
	
	@PostMapping("/schedules/{sch_id}/tasks")
	public ResponseEntity<Object> setTask(@RequestHeader("Authorization") String token,@PathVariable Long sch_id,@RequestBody Task task){
		String reqToken[] = token.split("Bearer ");
		User user = userService.getUserToken(reqToken[1]);
		if(user == null) {
			throw new ResourceNotFoundException("Invalid or unauthorized user");
		}
		//check if you has this task
		
		Schedule schedule = scheduleService.getSchedule(sch_id);
		schedule.setUser(user);
		
		task.addTasksMilestone(new TasksMilestone());
		task.setSchedule(schedule);
		
		Task t = taskService.saveTask(task);
		
		URI location = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(t.getId())
					.toUri();
		return ResponseEntity.created(location).build();
	
	}
	@PutMapping("/schedules/{sch_id}/tasks")
	public ResponseEntity<Task> updateTask(@RequestHeader("Authorization") String token,@PathVariable Long sch_id,@RequestBody Task task){
		String reqToken[] = token.split("Bearer ");
		User user = userService.getUserToken(reqToken[1]);
		if(user == null) {
			throw new ResourceNotFoundException("Invalid or unauthorized user");
		}
		Schedule schedule = scheduleService.getSchedule(sch_id);
		schedule.setUser(user);
		
		task.setSchedule(schedule);
		
		//add a check to determine if user has this task before you update
		Task t = taskService.saveTask(task);
		 return new ResponseEntity<Task>(t, HttpStatus.OK);
	}
	
	@DeleteMapping("/schedules/{sch_id}/tasks/{id}")
	public ResponseEntity<Task> deleteTask(@RequestHeader("Authorization") String token,@PathVariable Long sch_id, @PathVariable Long id){
		String reqToken[] = token.split("Bearer ");
		User user = userService.getUserToken(reqToken[1]);
		if(user == null) {
			throw new ResourceNotFoundException("Invalid or unauthorized user");
		}
		 taskService.deleteUserScheduleTask(id, sch_id,user.getId());
		 return new ResponseEntity<Task>(HttpStatus.NO_CONTENT);
	}
	
}
