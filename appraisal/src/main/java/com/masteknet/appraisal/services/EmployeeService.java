package com.masteknet.appraisal.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.masteknet.appraisal.auth.IAuthenticationFacade;
import com.masteknet.appraisal.domain.models.RegistrationForm;
import com.masteknet.appraisal.domain.models.TeamStatistics;
import com.masteknet.appraisal.entities.AppraisalCategory;
import com.masteknet.appraisal.entities.AuthUserGroup;
import com.masteknet.appraisal.entities.Employee;
import com.masteknet.appraisal.entities.Project;
import com.masteknet.appraisal.entities.User;
import com.masteknet.appraisal.repositories.EmployeeRepository;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
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
	
	public Iterable<Employee> getPendingEmployees(Project project) {
		return employeeRepository.findByUserProjectAndUserEnabled(project, false);
	}
	
	public Iterable<Employee> getApprovedEmployees(Project project) {
		return employeeRepository.findByUserProjectAndUserEnabled(project, true);
	}
	
	public Employee getEmployee(long employeeId) {
		return employeeRepository.findById(employeeId);
	}
	
	public void save(Employee employee) {
		employeeRepository.save(employee);
	}
	
	public void saveAll(List<Employee> employees) {
		employeeRepository.saveAll(employees);
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
		save(employee);
	}
	
	public List<TeamStatistics> getTeamStats(AppraisalCategory category, Project project) {
		
		return employeeRepository.getTeamStats(category, project);
	}
	
}
