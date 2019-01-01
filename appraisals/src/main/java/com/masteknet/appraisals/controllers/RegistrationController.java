package com.masteknet.appraisals.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.masteknet.appraisals.models.RegistrationForm;
import com.masteknet.appraisals.services.ProjectService;
import com.masteknet.appraisals.services.UserDetailsServiceImpl;

@Controller
public class RegistrationController extends AppraisalBase {
		
	@Autowired
	private ProjectService projectService;
	@Autowired
	private UserDetailsServiceImpl userService;

	@GetMapping("/register")
    public String getRegistrationForm(Model model) {
		
		model.addAttribute("projects", projectService.createProjectMap());
		model.addAttribute(new RegistrationForm());	
        return "register";
    }	
	
	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute RegistrationForm registrationForm, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		
		model.addAttribute("projects", projectService.createProjectMap());
		if(employeeService.employeeIdExists(Long.parseLong(registrationForm.getEmployeeId()))) {
			result.rejectValue("employeeId", "error.registrationForm", "Employee Id already exists.");
		}
		if(userService.emailExists(registrationForm.getUsername())) {
			result.rejectValue("username", "error.registrationForm", "An account already exists for this email.");
		}
		if (result.hasErrors()) {
			return "register";
		}
		employeeService.createBasicUser(registrationForm, projectService.getProject(registrationForm.getProjectId()));
		return "redirect:/login";
	}
}
