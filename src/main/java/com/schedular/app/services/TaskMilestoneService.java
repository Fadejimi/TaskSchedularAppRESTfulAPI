package com.schedular.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schedular.app.entities.Task;
import com.schedular.app.entities.TasksMilestone;
import com.schedular.app.exceptions.ResourceNotFoundException;
import com.schedular.app.repos.TaskMilestoneRepo;
import com.schedular.app.repos.TaskRepo;

@Service
public class TaskMilestoneService {
	@Autowired 
	TaskMilestoneRepo taskMilestoneRepo;
	
	@Autowired
	TaskRepo taskRepo;
	
	public List<TasksMilestone> getTaskMilestones(Task task) {
		return taskMilestoneRepo.findByTask(task);
	}
	
	public TasksMilestone createTaskMilestone(TasksMilestone taskMilestone) {
		return taskMilestoneRepo.save(taskMilestone);
	}
	
	public TasksMilestone getTaskMilestone(long id) {
		return taskMilestoneRepo.findTasksMilestoneById(id);
	}
	
	public void deleteTaskMilestone(long id) {
		TasksMilestone task = this.getTaskMilestone(id);
		taskMilestoneRepo.delete(task);
	}
	
	public List<TasksMilestone> findAllScheduleTasksMilestone(long tId, long user_id) {
		return taskMilestoneRepo.findAllUserTaskTasksMilestone(tId, user_id);
	}
}
