package com.masteknet.appraisal.controlleradvices;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.masteknet.appraisal.controllers.AdminController;
import com.masteknet.appraisal.controllers.AppraisalController;
import com.masteknet.appraisal.controllers.TeamController;
import com.masteknet.appraisal.entities.AppraisalCategory;
import com.masteknet.appraisal.entities.Employee;

@ControllerAdvice(assignableTypes = {AppraisalController.class, TeamController.class, AdminController.class})
public class AppraisalControllerAdvice {
	
	@ModelAttribute
    public void globalAttributes(Model model, HttpSession session) {

		model.addAttribute("employee", (Employee) session.getAttribute("loggedInEmployee"));
		model.addAttribute("appraisalCategory", (AppraisalCategory) session.getAttribute("appraisalCategory"));
    }
	
}
