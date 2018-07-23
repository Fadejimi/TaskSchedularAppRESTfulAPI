package com.schedular.app.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.schedular.app.entities.Schedule;
import com.schedular.app.entities.Task;
import com.schedular.app.entities.TasksMilestone;

@Repository
public interface TaskMilestoneRepo extends CrudRepository<TasksMilestone, Long>{
	List<TasksMilestone> findByTask(Task task);
	TasksMilestone findTasksMilestoneById(Long id);
	TasksMilestone findTasksMilestoneByIdAndTask(Long id,Task task);
	
	@Transactional
	Long deleteByIdAndTask(Long id,Task task);
	
	//@Query("delete from Task t where t.id = :id and t.schedule.id = :schId and t.schedule.user.id = :userId")
	//void deleteUserScheduleTask(@Param("id") Long id, @Param("schId") Long schId,@Param("userId") Long userId);
	
	 @Query("select distinct t from TasksMilestone t where t.task.id = :tId and t.task.schedule.user.id = :userId")
	 List<TasksMilestone> findAllUserTaskTasksMilestone(@Param("tId") Long tId,@Param("userId") Long userId);
	 
	 @Query("select distinct t from TasksMilestone t where t.id = :id and t.task.id = :tId and t.task.schedule.user.id = :userId")
	 TasksMilestone findUserTaskTasksMilestone(@Param("id") Long id, @Param("tId") Long schId,@Param("userId") Long userId);
	
}
