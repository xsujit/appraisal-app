package com.masteknet.appraisal.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import com.masteknet.appraisal.entities.AppraisalCategory;
import com.masteknet.appraisal.entities.Employee;
import com.masteknet.appraisal.services.AppraisalCategoryService;
import com.masteknet.appraisal.services.EmployeeService;

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
