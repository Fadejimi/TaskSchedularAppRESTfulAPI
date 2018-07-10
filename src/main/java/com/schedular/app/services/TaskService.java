package com.schedular.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schedular.app.entities.Schedule;
import com.schedular.app.entities.Task;
import com.schedular.app.repos.TaskRepo;

@Service
public class TaskService {

	@Autowired
	TaskRepo taskRepo;
	
	public List<Task> getSchedules(){
		return (List<Task>) taskRepo.findAll();
	}
	public Task getTask(Long id){
		return taskRepo.findTaskById(id);
	}
	public Task getScheduleTask(Long id,Schedule schdedule){
		return taskRepo.findTaskByIdAndSchedule(id, schdedule);
	}
	public List<Task> getcheduleTasks(Schedule schdedule){
		return taskRepo.findBySchedule(schdedule);
	}
	public Task saveTask(Task task){
		 return taskRepo.save(task);
	}
	
	public void deleteUserScheduleTask(Long id, Long schId, Long userId){
		 // taskRepo.deleteUserScheduleTask(id,schId,userId);
		  Task task  = taskRepo.findUserSchduleTask(id, schId, userId);
		  taskRepo.delete(task);
	}
	public List<Task> findAllUserSchduleTasks(Long schId, Long userId){
		 return taskRepo.findAllUserSchduleTasks(schId, userId);
	}
	public Task findUserSchduleTask(Long id,Long schId, Long userId){
		 return taskRepo.findUserSchduleTask(id, schId, userId);
	}
	

}
