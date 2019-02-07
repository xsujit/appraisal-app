package com.masteknet.appraisal.wrappers;

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
