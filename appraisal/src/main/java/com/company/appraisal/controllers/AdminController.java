package com.company.appraisal.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.company.appraisal.entities.AppraisalCategory;
import com.company.appraisal.entities.Employee;
import com.company.appraisal.entities.Project;
import com.company.appraisal.services.EmployeeService;
import com.company.appraisal.wrappers.EmployeeWrapper;
import com.company.appraisal.wrappers.TeamStatistics;

@Controller
public class AdminController {
	
	@Autowired
	protected EmployeeService employeeService;

	@GetMapping("/admin/user")
	@Secured("ROLE_FACILITATOR")
	public String getUsersInProject(Model model, @ModelAttribute("loggedInEmployee") Employee me) {
		
		EmployeeWrapper pendingEmployeeWrapper = new EmployeeWrapper();
		Project project = me.getUser().getProject();
		Iterable<Employee> employeeIter = employeeService.getPendingEmployees(project);
		employeeIter.forEach(pendingEmployeeWrapper.getEmployees()::add);
		model.addAttribute("pendingEmployeeWrapper", pendingEmployeeWrapper);
		model.addAttribute("approvedEmployees", employeeService.getApprovedEmployees(project));
		return "admin-user";
	}
	
	@PostMapping("/admin/user")
	@Secured("ROLE_FACILITATOR")
	public String approveUsers(@ModelAttribute("pendingEmployeeWrapper") EmployeeWrapper pendingEmployeeWrapper) {

		List<Employee> employees = new ArrayList<>();
		for(Employee pendingEmployee : pendingEmployeeWrapper.getEmployees()) {
			if(pendingEmployee.getUser().isEnabled()) {
				Employee employee = employeeService.getEmployee(pendingEmployee.getId());
				employee.getUser().setEnabled(true);
				employees.add(employee);
			}
		}
		employeeService.saveAll(employees);
		return "redirect:/admin/user?success=Employee+approved+successfully";
	}
	
	@GetMapping("/admin")
	public String getAdminHome() {
		return "admin";
	}
	
	@GetMapping("/admin/stats")
	@Secured("ROLE_FACILITATOR")
	public String getTeamStats(Model model, @ModelAttribute("loggedInEmployee") Employee me, @ModelAttribute("appraisalCategory") AppraisalCategory category) {
		
		List<TeamStatistics> teamStats = employeeService.getTeamStats(category, me.getUser().getProject());
		model.addAttribute("teamStats", teamStats);
		return "admin-team-stats";
	}
	
}
