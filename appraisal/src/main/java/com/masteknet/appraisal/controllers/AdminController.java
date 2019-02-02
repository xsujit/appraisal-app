package com.masteknet.appraisal.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.masteknet.appraisal.domain.models.EmployeeWrapper;
import com.masteknet.appraisal.entities.Employee;
import com.masteknet.appraisal.entities.Project;

@Controller
public class AdminController extends AppraisalBase {

	@GetMapping("/admin")
	@Secured("ROLE_FACILITATOR")
	public String getUsersInProject(Model model) {
		
		EmployeeWrapper pendingEmployeeWrapper = new EmployeeWrapper();
		Project project = getLoggedInEmployee().getUser().getProject();
		Iterable<Employee> employeeIter = employeeService.getPendingEmployees(project);
		employeeIter.forEach(pendingEmployeeWrapper.getEmployees()::add);
		model.addAttribute("pendingEmployeeWrapper", pendingEmployeeWrapper);
		model.addAttribute("approvedEmployees", employeeService.getApprovedEmployees(project));
		return "admin";
	}
	
	@PostMapping("/admin")
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
		return "redirect:/admin?success=Employee+approved+successfully";
	}
	
}
