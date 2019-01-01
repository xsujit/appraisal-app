package com.masteknet.appraisals.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.masteknet.appraisals.auth.IAuthenticationFacade;
import com.masteknet.appraisals.entities.AuthUserGroup;
import com.masteknet.appraisals.entities.Employee;
import com.masteknet.appraisals.entities.Project;
import com.masteknet.appraisals.entities.User;
import com.masteknet.appraisals.models.RegistrationForm;
import com.masteknet.appraisals.repositories.EmployeeRepository;

@Service
public class EmployeeService {

	private EmployeeRepository employeeRepository;
	private IAuthenticationFacade authenticationFacade;
	
	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository, IAuthenticationFacade authenticationFacade) {
		super();
		this.employeeRepository = employeeRepository;
		this.authenticationFacade = authenticationFacade;
	}

	public boolean employeeIdExists(long employeeId) { // before authentication
		if(getEmployee(employeeId) != null) {
			return true;
		}
		return false;
	}
	
	public Employee getLoggedInEmployee() {
		if(authenticationFacade.getPrincipal() != null) {
			return employeeRepository.findByUserId(authenticationFacade.getPrincipal().getUserId());	
		}
		return null;
	}
	
	public Iterable<Employee> getEmployees() {
		return employeeRepository.findAll();
	}
	
	public Iterable<Employee> getEmployees(Project project) {
		return employeeRepository.findByUserProject(project);
	}
	
	public Employee getEmployee(long employeeId) {
		return employeeRepository.findById(employeeId);
	}

	public void createBasicUser(RegistrationForm registrationForm, Project project) { // try model mapper
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);
		User user = new User(encoder.encode(registrationForm.getPassword()), registrationForm.getUsername(), project);
		AuthUserGroup userGroup = new AuthUserGroup("USER", user);
		Employee employee = new Employee(Long.parseLong(registrationForm.getEmployeeId()), user, registrationForm.getFirstName(), 
				registrationForm.getLastName(), registrationForm.getLocation());
		List<AuthUserGroup> userGroups = new ArrayList<>();
		userGroups.add(userGroup);
		user.setUsergroups(userGroups);
		employeeRepository.save(employee);
	}
	
}
