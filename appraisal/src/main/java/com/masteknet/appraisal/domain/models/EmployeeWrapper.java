package com.masteknet.appraisal.domain.models;

import java.util.ArrayList;
import java.util.List;
import com.masteknet.appraisal.entities.Employee;

public class EmployeeWrapper {
	
	private List<Employee> employees = new ArrayList<Employee>();

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

}
