package com.masteknet.appraisals.models;

import com.masteknet.appraisals.entities.Appraisal;
import com.masteknet.appraisals.entities.Employee;
import com.masteknet.appraisals.entities.Vote;

public class Team {
	
	private Employee employee;
	private Appraisal appraisal;
	private Vote vote;
	
	public Team() {
		super();
	}
	public Team(Employee employee, Appraisal appraisal, Vote vote) {
		super();
		this.employee = employee;
		this.appraisal = appraisal;
		this.vote = vote;
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
	public Vote getVote() {
		return vote;
	}
	public void setVote(Vote vote) {
		this.vote = vote;
	}
	
}
