package com.schedular.app.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.schedular.app.entities.Schedule;
import com.schedular.app.entities.User;

@Repository
public interface ScheduleRepo extends CrudRepository<Schedule, Long> {
	
	List<Schedule> findByUser(User user);
	Schedule findScheduleById(Long id);
	Schedule findScheduleByIdAndUser(Long id,User user);
	
	 @Transactional
	Long deleteByIdAndUser(Long id,User user);

}
