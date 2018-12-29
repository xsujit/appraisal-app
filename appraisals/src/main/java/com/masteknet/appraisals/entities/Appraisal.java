package com.masteknet.appraisals.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Size;

@Entity
@Table(name="APPRAISAL")
public class Appraisal implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AppraisalPk appraisalPk;
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	@JoinColumn(name="PROJECT_ID")
	private Project project;
	@Column(name="SUBMIT_DATE", updatable=false)
	private LocalDateTime submitDate;
	@Column(name="LAST_UPDATE_DATE", insertable=false)
	private LocalDateTime lastUpdateDate;
	@Column(name="SIGNED_OFF")
	private boolean signedOff;
	@Size(min=2, max=5024, message="Key performance area must be between 2 and 5024 characters")
	@Column(name="DESCRIPTION", nullable=false, columnDefinition="text")
	private String description;
	@Version
	@Column(name="OPTLOCK")
	private Long version;

	public AppraisalPk getAppraisalPk() {
		return appraisalPk;
	}

	public void setAppraisalPk(AppraisalPk appraisalPk) {
		this.appraisalPk = appraisalPk;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public LocalDateTime getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(LocalDateTime submitDate) {
		this.submitDate = submitDate;
	}

	public LocalDateTime getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public boolean isSignedOff() {
		return signedOff;
	}

	public void setSignedOff(boolean signedOff) {
		this.signedOff = signedOff;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@PrePersist
	protected void onCreate() {
		submitDate = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		lastUpdateDate = LocalDateTime.now();
	}

}
