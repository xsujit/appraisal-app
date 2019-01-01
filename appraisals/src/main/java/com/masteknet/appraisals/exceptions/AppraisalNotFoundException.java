package com.masteknet.appraisals.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Requested appraisal not found.")
public class AppraisalNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8790211652911971729L;

	public AppraisalNotFoundException(String appraisalId) {
		super(appraisalId + " not found");
	}
}
