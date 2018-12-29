package com.masteknet.appraisals.controllers.advices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.masteknet.appraisals.controllers.AppraisalController;
import com.masteknet.appraisals.controllers.IndexController;
import com.masteknet.appraisals.controllers.TeamController;
import com.masteknet.appraisals.services.AppraisalCategoryService;
import com.masteknet.appraisals.services.EmployeeService;

@ControllerAdvice(assignableTypes = {AppraisalController.class, IndexController.class, TeamController.class})
public class AppraisalControllerAdvice {

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private AppraisalCategoryService appraisalCategoryService;
	
	@ModelAttribute
    public void globalAttributes(Model model) {
		model.addAttribute("employee", employeeService.getLoggedInEmployee());
		model.addAttribute("appraisalCategory", appraisalCategoryService.getAppraisalCategory());
    }
	
	//	@PreAuthorize("hasRole('ROLE_ADMIN')")
}
