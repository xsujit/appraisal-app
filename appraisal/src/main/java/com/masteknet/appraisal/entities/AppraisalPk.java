package com.masteknet.appraisal.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class AppraisalPk implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	@JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID")
	private Employee employee;
	@Embedded
	@AssociationOverrides(value = {
			@AssociationOverride(name = "appraisalType", joinColumns =  @JoinColumn(name = "APPRAISAL_TYPE", referencedColumnName = "TYPE")),
			@AssociationOverride(name = "appraisalYear", joinColumns =  @JoinColumn(name = "APPRAISAL_YEAR", referencedColumnName = "YEAR"))
	})
	private AppraisalCategory appraisalCategory;
	
	public AppraisalPk() {
		super();
	}

	public AppraisalPk(Employee employee, AppraisalCategory appraisalCategory) {
		super();
		this.employee = employee;
		this.appraisalCategory = appraisalCategory;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public AppraisalCategory getAppraisalCategory() {
		return appraisalCategory;
	}

	public void setAppraisalCategory(AppraisalCategory appraisalCategory) {
		this.appraisalCategory = appraisalCategory;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
        if (!(obj instanceof AppraisalPk)) return false;
        AppraisalPk that = (AppraisalPk) obj;
        return Objects.equals(getEmployee(), that.getEmployee()) && Objects.equals(getAppraisalCategory(), that.getAppraisalCategory());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getEmployee(), getAppraisalCategory());
	}
}
