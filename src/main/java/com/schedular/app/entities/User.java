package com.schedular.app.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.UniqueElements;


@ApiModel(description="All details about user")  //used by swagger for documentation
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min=2, message="Name should have atleat 2 characters")
	@ApiModelProperty(notes="Name should have atleat two character")
	private String name;

	@Column(unique=true)
	private String email;
	
	@JsonIgnore
	private String token;
	
	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateAt;
	
	@JsonIgnore
	@OneToMany(
	        mappedBy = "user", 
	        cascade = CascadeType.ALL
	    )
	private List<Schedule> schedules;
	
	public User() {
		this.createdAt = new Date();
		this.updateAt = new Date();
	}
	
	public User(String name, String email, String token) {
		this.name = name;
		this.email = email;
		this.token = token;
		this.createdAt = new Date();
		this.updateAt = new Date();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public List<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}
	public void addSchedule(Schedule schedule) {
		this.schedules.add(schedule);
		schedule.setUser(this);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", token=" + token + ", createdAt="
				+ createdAt + ", updateAt=" + updateAt + "]";
	}
	
	
	
	
}
