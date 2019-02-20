package com.company.appraisal.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.company.appraisal.auth.AppraisalUserPrincipal;
import com.company.appraisal.entities.AppraisalCategory;
import com.company.appraisal.entities.AuthUserGroup;
import com.company.appraisal.entities.Employee;
import com.company.appraisal.entities.Project;
import com.company.appraisal.entities.User;
import com.company.appraisal.repositories.EmployeeRepository;
import com.company.appraisal.wrappers.RegistrationForm;
import com.company.appraisal.wrappers.TeamStatistics;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	
	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	public boolean employeeIdExists(long employeeId) { // before authentication
		if(getEmployee(employeeId) != null) {
			return true;
		}
		return false;
	}
	
	public Employee getLoggedInEmployee() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			AppraisalUserPrincipal principal = (AppraisalUserPrincipal) authentication.getPrincipal();
			return employeeRepository.findByUserId(principal.getUserId());
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
