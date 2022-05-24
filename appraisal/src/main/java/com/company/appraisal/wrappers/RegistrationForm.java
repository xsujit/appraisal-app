package com.company.appraisal.wrappers;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegistrationForm {
	
	private long userId;
	@NotBlank(message="Employee Id is required")
	@Pattern(regexp = "^[1-9]\\d*$", message="Employee Id must be greater than zero")
	private String employeeId;
	@Email(message="Email should be well formed")
	@NotBlank(message="Email is required")
	private String username;
	@NotBlank(message="Password is required")
	@Size(min=6, max=14, message="Password must be between 6 and 14 characters")
	private String password;
	@NotBlank(message="First name is required")
	@Pattern(regexp = "[a-zA-Z ]+", message="Only characters and space allowed")
	private String firstName;
	@NotBlank(message="Last name is required")
	@Pattern(regexp = "[a-zA-Z ]+", message="Only characters and space allowed")
	private String lastName;
	@Min(value=1, message="Please select a project")
	private long projectId;
	@NotBlank(message="Location is required")
	@Pattern(regexp = "[a-zA-Z ]+", message="Only characters and space allowed")
	private String location;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

}
