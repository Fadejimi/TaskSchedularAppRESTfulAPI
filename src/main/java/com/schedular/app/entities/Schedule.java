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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Lob 
	private String description;
	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	
	@JsonIgnore
	@OneToMany(
	        mappedBy = "schedule", 
	        cascade = CascadeType.ALL
	    )
	private List<Task> tasks;
	
	@JsonIgnore
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "user_id")
	private User user;
	
	@Transient
	private double scheduleMilestone;
	
	public Schedule() {
		this.createdAt = new Date();
		this.updatedAt = new Date();
		this.tasks = new ArrayList<>();
	}
	
	public Schedule(User user,String name, String description) {
		this.user = user;
		this.name = name;
		this.description = description;
		this.createdAt = new Date();
		this.updatedAt = new Date();
		this.tasks = new ArrayList<>();
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

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
		this.scheduleMilestone = this.getScheduleMilestone();
	}
	
	public void addTask(Task task) {
		this.tasks.add(task);
		task.setSchedule(this);
		this.scheduleMilestone = this.getScheduleMilestone();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public double getScheduleMilestone() {
		double sum = 0;
		int cnt = 0;
		for(Task t:this.tasks) {
			sum += t.computeMilestone();
			cnt++;
		}
		return (cnt>0)?sum/cnt:0;
	}

	private void setScheduleMilestone(double scheduleMilestone) {
		this.scheduleMilestone = scheduleMilestone;
	}
	
}
