package com.masteknet.appraisal.wrappers;

import com.masteknet.appraisal.entities.Appraisal;
import com.masteknet.appraisal.entities.Employee;

public class TeamStatistics {
	
	private Employee employee;
	private Appraisal appraisal;
	private long votes;
	
	public TeamStatistics() {
		super();
	}
	public TeamStatistics(Employee employee, Appraisal appraisal, long votes) {
		super();
		this.employee = employee;
		this.appraisal = appraisal;
		this.votes = votes;
	}
	
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Appraisal getAppraisal() {
		return appraisal;
	}
	public void setAppraisal(Appraisal appraisal) {
		this.appraisal = appraisal;
	}
	public long getVotes() {
		return votes;
	}
	public void setVotes(long votes) {
		this.votes = votes;
	}
	

}
