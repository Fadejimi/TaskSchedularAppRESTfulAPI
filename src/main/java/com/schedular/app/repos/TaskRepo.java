package com.schedular.app.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.schedular.app.entities.Schedule;
import com.schedular.app.entities.Task;

@Repository
public interface TaskRepo extends CrudRepository<Task, Long> {
	
	List<Task> findBySchedule(Schedule schedule);
	Task findTaskById(Long id);
	Task findTaskByIdAndSchedule(Long id,Schedule schedule);
	
	@Transactional
	Long deleteByIdAndSchedule(Long id,Schedule schedule);
	
	//@Query("delete from Task t where t.id = :id and t.schedule.id = :schId and t.schedule.user.id = :userId")
	//void deleteUserScheduleTask(@Param("id") Long id, @Param("schId") Long schId,@Param("userId") Long userId);
	
	 @Query("select distinct t from Task t where t.schedule.id = :schId and t.schedule.user.id = :userId")
	 List<Task> findAllUserSchduleTasks(@Param("schId") Long schId,@Param("userId") Long userId);
	 
	 @Query("select distinct t from Task t where t.id = :id and t.schedule.id = :schId and t.schedule.user.id = :userId")
	 Task findUserSchduleTask(@Param("id") Long id, @Param("schId") Long schId,@Param("userId") Long userId);
	
	
	
}
