package com.company.appraisal.wrappers;

import java.util.ArrayList;
import java.util.List;

import com.company.appraisal.entities.Employee;

public class EmployeeWrapper {
	
	private List<Employee> employees = new ArrayList<Employee>();

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

}
