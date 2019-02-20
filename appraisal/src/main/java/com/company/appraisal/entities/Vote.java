package com.company.appraisal.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name="VOTE")
public class Vote {

	@EmbeddedId
    private VoteId id;
	
	@Column(name="SUBMIT_DATE", updatable=false, nullable=false)
	private LocalDateTime submitDate;
	
	public VoteId getId() {
		return id;
	}

	public void setId(VoteId id) {
		this.id = id;
	}

	public LocalDateTime getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(LocalDateTime submitDate) {
		this.submitDate = submitDate;
	}

	@PrePersist
	protected void onCreate() {
		submitDate = LocalDateTime.now();
	}
}
