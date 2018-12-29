package com.masteknet.appraisals.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.masteknet.appraisals.entities.AuthUserGroup;
import com.masteknet.appraisals.entities.Employee;
import com.masteknet.appraisals.entities.Project;
import com.masteknet.appraisals.entities.User;
import com.masteknet.appraisals.models.RegistrationForm;
import com.masteknet.appraisals.repositories.EmployeeRepository;

@Service
public class EmployeeService {

	// repositories
	private EmployeeRepository employeeRepository;
	
	// service
	private UserDetailsServiceImpl userService;
	
	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	public UserDetailsServiceImpl getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserDetailsServiceImpl userService) {
		this.userService = userService;
	}

	private User getLoggedInUser() { // private
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			return userService.findByEmail(authentication.getName());	
		}
		return null;
	}
	
	public boolean emailExists(String email) { // before authentication
		if(userService.findByEmail(email) != null) {
			return true;
		}
		return false;
	}
	
	public boolean employeeIdExists(long employeeId) { // before authentication
		if(getEmployee(employeeId) != null) {
			return true;
		}
		return false;
	}
	
	public Employee getLoggedInEmployee() {
		User user = getLoggedInUser();
		if(user != null) {
			return employeeRepository.findByUserId(user.getId());	
		}
		return null;
	}
	
	public Iterable<Employee> getEmployees() {
		return employeeRepository.findAll();
	}
	
	public Iterable<Employee> getEmployees(Project project) {
		return employeeRepository.findByProject(project);
	}
	
	public Employee getEmployee(long employeeId) {
		return employeeRepository.findById(employeeId);
	}

	public void createBasicUser(RegistrationForm registrationForm, Project project) { // try model mapper
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);
		User user = new User(encoder.encode(registrationForm.getPassword()), registrationForm.getUsername());
		AuthUserGroup userGroup = new AuthUserGroup("USER", user);
		Employee employee = new Employee(Long.parseLong(registrationForm.getEmployeeId()), user, registrationForm.getFirstName(), 
				registrationForm.getLastName(), project, registrationForm.getLocation());
		List<AuthUserGroup> userGroups = new ArrayList<>();
		userGroups.add(userGroup);
		user.setUsergroups(userGroups);
		employeeRepository.save(employee);
	}
	
}
