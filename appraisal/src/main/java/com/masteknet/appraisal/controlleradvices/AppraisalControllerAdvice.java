package com.masteknet.appraisal.controlleradvices;

import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.masteknet.appraisal.controllers.AdminController;
import com.masteknet.appraisal.controllers.AppraisalController;
import com.masteknet.appraisal.controllers.ResultController;
import com.masteknet.appraisal.controllers.TeamController;
import com.masteknet.appraisal.entities.AppraisalCategory;
import com.masteknet.appraisal.entities.Employee;

@ControllerAdvice(assignableTypes = {AppraisalController.class, TeamController.class, AdminController.class, ResultController.class})
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
