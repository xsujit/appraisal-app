package com.masteknet.appraisal.domain.models;

import com.masteknet.appraisal.entities.AppraisalPk;


public class Result {

	private AppraisalPk appraisalPk;
	private long votes;

	public Result() {
		super();
	}

	public Result(AppraisalPk appraisalPk, long votes) {
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
