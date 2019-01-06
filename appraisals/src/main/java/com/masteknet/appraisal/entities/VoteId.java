package com.masteknet.appraisal.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Embeddable
public class VoteId implements Serializable {
	
	private static final long serialVersionUID = 1910664083296540504L;
	
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name = "VOTER_EMPLOYEE_ID", referencedColumnName = "ID")
	private Employee voter;
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	private Appraisal appraisal;
	
	public VoteId() {
		super();
	}
	
	public VoteId(Employee employee, Appraisal appraisal) {
		super();
		this.voter = employee;
		this.appraisal = appraisal;
	}

	public Employee getVoter() {
		return voter;
	}

	public Appraisal getAppraisal() {
		return appraisal;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
        if (!(obj instanceof VoteId)) return false;
        VoteId that = (VoteId) obj;
        return Objects.equals(getVoter(), that.getVoter()) && Objects.equals(getAppraisal(), that.getAppraisal());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getVoter(), getAppraisal());
	}
	
}
