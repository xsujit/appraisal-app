package com.masteknet.appraisal.highcharts;

import com.masteknet.appraisal.entities.AppraisalPk;
import com.masteknet.appraisal.entities.Employee;

public class VotedAppraisal {
	
	private AppraisalPk appraisalPk;
	private Employee voter;
	
	public VotedAppraisal() {
		super();
	}
	public VotedAppraisal(AppraisalPk appraisalPk, Employee voter) {
		super();
		this.appraisalPk = appraisalPk;
		this.voter = voter;
	}
	
	public AppraisalPk getAppraisalPk() {
		return appraisalPk;
	}
	public void setAppraisalPk(AppraisalPk appraisalPk) {
		this.appraisalPk = appraisalPk;
	}
	public Employee getVoter() {
		return voter;
	}
	public void setVoter(Employee voter) {
		this.voter = voter;
	}

}