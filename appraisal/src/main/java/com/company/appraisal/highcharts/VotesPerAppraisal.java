package com.company.appraisal.highcharts;

import com.company.appraisal.entities.AppraisalPk;

public class VotesPerAppraisal {

	private AppraisalPk appraisalPk;
	private long votes;

	public VotesPerAppraisal() {
		super();
	}

	public VotesPerAppraisal(AppraisalPk appraisalPk, long votes) {
		super();
		this.appraisalPk = appraisalPk;
		this.votes = votes;
	}

	public AppraisalPk getAppraisalPk() {
		return appraisalPk;
	}

	public void setAppraisalPk(AppraisalPk appraisalPk) {
		this.appraisalPk = appraisalPk;
	}

	public long getVotes() {
		return votes;
	}

	public void setVotes(long votes) {
		this.votes = votes;
	}
}
