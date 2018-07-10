package com.schedular.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schedular.app.entities.Schedule;
import com.schedular.app.entities.User;
import com.schedular.app.repos.ScheduleRepo;

@Service
public class ScheduleService {

	@Autowired
	ScheduleRepo scheduleRepo;
	
	public List<Schedule> getSchedules(){
		return (List<Schedule>) scheduleRepo.findAll();
	}
	public Schedule getSchedule(Long id){
		return scheduleRepo.findScheduleById(id);
	}
	public Schedule getUserSchedule(Long id,User user){
		return scheduleRepo.findScheduleByIdAndUser(id, user);
	}
	public List<Schedule> getUserSchedules(User user){
		return scheduleRepo.findByUser(user);
	}
	public Schedule saveSchdule(Schedule schedule){
		 return scheduleRepo.save(schedule);
	}
	public Long deleteSchdule(Long id,User user){
		 return scheduleRepo.deleteByIdAndUser(id,user);
	}

}
