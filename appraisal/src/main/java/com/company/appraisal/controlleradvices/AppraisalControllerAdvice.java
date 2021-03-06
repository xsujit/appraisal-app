package com.company.appraisal.controlleradvices;

import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.company.appraisal.controllers.AdminController;
import com.company.appraisal.controllers.AppraisalController;
import com.company.appraisal.controllers.IndexController;
import com.company.appraisal.controllers.ResultController;
import com.company.appraisal.controllers.TeamController;
import com.company.appraisal.entities.AppraisalCategory;
import com.company.appraisal.entities.Employee;

@ControllerAdvice(assignableTypes = {AppraisalController.class, TeamController.class, AdminController.class, 
		ResultController.class, IndexController.class})
public class AppraisalControllerAdvice {

	@ModelAttribute("loggedInEmployee")
	public Employee getLoggedInEmployee(HttpSession session) {
		return (Employee) session.getAttribute("loggedInEmployee");
	}
	
	@ModelAttribute("appraisalCategory")
	public AppraisalCategory getappraisalCategory(HttpSession session) {
		return (AppraisalCategory) session.getAttribute("appraisalCategory");
	}
}
