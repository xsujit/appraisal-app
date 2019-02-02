package com.masteknet.appraisal.controlleradvices;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.masteknet.appraisal.controllers.AdminController;
import com.masteknet.appraisal.controllers.AppraisalBase;
import com.masteknet.appraisal.controllers.AppraisalController;
import com.masteknet.appraisal.controllers.TeamController;

@ControllerAdvice(assignableTypes = {AppraisalController.class, TeamController.class, AdminController.class})
public class AppraisalControllerAdvice extends AppraisalBase {
	
	@ModelAttribute
    public void globalAttributes(Model model) {

		model.addAttribute("employee", getLoggedInEmployee());
		model.addAttribute("appraisalCategory", getAppraisalCategory());
    }
	
}
