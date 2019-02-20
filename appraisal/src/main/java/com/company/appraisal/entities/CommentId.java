package com.company.appraisal.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class CommentId implements Serializable  {

	private static final long serialVersionUID = -8008743456482674894L;
	
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name = "COMMENTER_EMPLOYEE_ID", referencedColumnName = "ID")
	private Employee commenter;
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	private Appraisal appraisal;
	
	public CommentId() {
		super();
	}

	public CommentId(Employee commenter, Appraisal appraisal) {
		super();
		this.commenter = commenter;
		this.appraisal = appraisal;
	}
	
	public Employee getCommenter() {
		return commenter;
	}
	public Appraisal getAppraisal() {
		return appraisal;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
        if (!(obj instanceof CommentId)) return false;
        CommentId that = (CommentId) obj;
        return Objects.equals(getCommenter(), that.getCommenter()) && Objects.equals(getAppraisal(), that.getAppraisal());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCommenter(), getAppraisal());
	}
	
}
