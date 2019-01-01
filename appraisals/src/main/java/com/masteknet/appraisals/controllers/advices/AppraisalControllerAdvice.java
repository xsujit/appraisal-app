package com.masteknet.appraisals.controllers.advices;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.masteknet.appraisals.controllers.AppraisalBase;
import com.masteknet.appraisals.controllers.AppraisalController;
import com.masteknet.appraisals.controllers.TeamController;

@ControllerAdvice(assignableTypes = {AppraisalController.class, TeamController.class})
public class AppraisalControllerAdvice extends AppraisalBase {
	
	@ModelAttribute
    public void globalAttributes(Model model) {

		model.addAttribute("employee", getLoggedInEmployee());
		model.addAttribute("appraisalCategory", getAppraisalCategory());
    }
}
