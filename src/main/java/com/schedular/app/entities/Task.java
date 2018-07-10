package com.schedular.app.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Lob
	private String description;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	
	@Future
	@ApiModelProperty(notes="endDate date should be in the future")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	
	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_id")
	private Schedule schedule;
	
	//@JsonIgnore
	@OneToMany(
	        mappedBy = "task", 
	        cascade = CascadeType.ALL
	    )
	private List<TasksMilestone> tasksMilestones;
	
	public Task() {
		this.createdAt = new Date();
		this.updatedAt = new Date();
		this.tasksMilestones = new ArrayList<>();
	}
	
	public Task(Schedule schedule, String name, String description, Date startDate, Date endDate) {
		this.schedule =schedule;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.createdAt = new Date();
		this.updatedAt = new Date();
		this.tasksMilestones = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public List<TasksMilestone> getTasksMilestones() {
		return tasksMilestones;
	}

	public void setTasksMilestones(List<TasksMilestone> tasksMilestones) {
		this.tasksMilestones = tasksMilestones;
	}
	public void addTasksMilestone(TasksMilestone tasksMilestone) {
		this.tasksMilestones.add(tasksMilestone);
		tasksMilestone.setTask(this);
	}
	
	public double computeMilestone() {
		double sum  = 0;
		for(TasksMilestone mt:this.tasksMilestones) {
			sum +=mt.getPercentage();
		}
		return sum;
	}
	
	
}
