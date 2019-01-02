package com.masteknet.appraisals.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import com.masteknet.appraisals.entities.AppraisalCategory;
import com.masteknet.appraisals.entities.Employee;
import com.masteknet.appraisals.services.AppraisalCategoryService;
import com.masteknet.appraisals.services.EmployeeService;

public abstract class AppraisalBase {
	
	protected static final String ERROR_MESSAGE = "Unable to persist data. Please contact support.";
	
	@Autowired
	protected AppraisalCategoryService appraisalCategoryService;
	@Autowired
	protected EmployeeService employeeService;
	
	protected AppraisalCategory getAppraisalCategory() {
		return appraisalCategoryService.getAppraisalCategory();
	}
	
	protected Employee getLoggedInEmployee() {
		return employeeService.getLoggedInEmployee();
	}
}
