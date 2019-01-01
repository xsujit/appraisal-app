package com.masteknet.appraisals.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
public class AppraisalCategory implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	private AppraisalType appraisalType;
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	private AppraisalYear appraisalYear;
	
	public AppraisalCategory() {
		super();
	}
	
	public AppraisalCategory(AppraisalType appraisalType, AppraisalYear appraisalYear) {
		super();
		this.appraisalType = appraisalType;
		this.appraisalYear = appraisalYear;
	}
	
	public AppraisalType getAppraisalType() {
		return appraisalType;
	}
	
	public void setAppraisalType(AppraisalType appraisalType) {
		this.appraisalType = appraisalType;
	}
	
	public AppraisalYear getAppraisalYear() {
		return appraisalYear;
	}
	
	public void setAppraisalYear(AppraisalYear appraisalYear) {
		this.appraisalYear = appraisalYear;
	}
	
}
