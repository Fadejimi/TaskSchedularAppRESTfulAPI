package com.schedular.app.restcontroller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
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

import com.schedular.app.entities.Schedule;
import com.schedular.app.entities.Task;
import com.schedular.app.entities.TasksMilestone;
import com.schedular.app.entities.User;
import com.schedular.app.exceptions.ResourceNotFoundException;
import com.schedular.app.services.TaskMilestoneService;
import com.schedular.app.services.TaskService;
import com.schedular.app.services.UserService;
import com.schedular.app.util.AppUtil;

@RestController
@RequestMapping("/api/v1/")
public class TaskMilestoneController {
	@Autowired
	TaskMilestoneService taskMilestoneService;
	@Autowired
	UserService userService;
	@Autowired
	TaskService taskService;
	
	@GetMapping("/tasks/{id}/task_milestones") 
	public List<TasksMilestone> getTaskMilestones(@RequestHeader("Authorization") String token, 
			@PathVariable("id") Long id) {
		String reqToken[] = token.split("Bearer ");
		User user = userService.getUserToken(reqToken[1]);
		if(user == null) {
			throw new ResourceNotFoundException("Invalid or unauthorized user");
		}
		return taskMilestoneService.findAllScheduleTasksMilestone(id, user.getId());
	}
	
	@PostMapping("/tasks/{id}/task_milestones") 
	public ResponseEntity<Object> createTaskMilestone(@RequestHeader("Authorization") String token, 
			@PathVariable("id") long id, 
			@Valid @RequestBody TasksMilestone tasksMilestone) {
		String reqToken[] = token.split("Bearer ");
		User user = userService.getUserToken(reqToken[1]);
		if(user == null) {
			throw new ResourceNotFoundException("Invalid or unauthorized user");
		}
		
		Task task = taskService.getTask(id);
		task.addTasksMilestone(tasksMilestone);
		
		tasksMilestone.setTask(task);
		
		TasksMilestone t = taskMilestoneService.createTaskMilestone(tasksMilestone);
		return AppUtil.getResponseEntity(t.getId());
	}
	
	@PutMapping("/tasks/{id}/task_milestones")
	public ResponseEntity<TasksMilestone> updateTask(@RequestHeader("Authorization") String token,
			@PathVariable Long id,
			@RequestBody TasksMilestone tasksMilestone){
		String reqToken[] = token.split("Bearer ");
		User user = userService.getUserToken(reqToken[1]);
		if(user == null) {
			throw new ResourceNotFoundException("Invalid or unauthorized user");
		}
		Task task = taskService.getTask(id);
		tasksMilestone.setTask(task);
		
		//add a check to determine if user has this task before you update
		TasksMilestone t = taskMilestoneService.createTaskMilestone(tasksMilestone);
		
		return new ResponseEntity<TasksMilestone>(t, HttpStatus.OK);
	}
	
	@DeleteMapping("/tasks/{task_id}/tasks/{id}")
	public ResponseEntity<Task> deleteTask(@RequestHeader("Authorization") String token,
			@PathVariable Long task_id, @PathVariable Long id){
		String reqToken[] = token.split("Bearer ");
		User user = userService.getUserToken(reqToken[1]);
		if(user == null) {
			throw new ResourceNotFoundException("Invalid or unauthorized user");
		}
		
		taskMilestoneService.deleteTaskMilestone(id);
		return new ResponseEntity<Task>(HttpStatus.NO_CONTENT);
	}
}
