package com.company.appraisal.services;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.appraisal.entities.AppraisalCategory;
import com.company.appraisal.entities.AppraisalType;
import com.company.appraisal.entities.AppraisalYear;
import com.company.appraisal.repositories.AppraisalTypeRepository;
import com.company.appraisal.repositories.AppraisalYearRepository;

@Service
public class AppraisalCategoryService {
	
	private final AppraisalYearRepository appraisalYearRepository;
	private final AppraisalTypeRepository appraisalTypeRepository;
	
	@Autowired
	public AppraisalCategoryService(AppraisalYearRepository appraisalYearRepository,
			AppraisalTypeRepository appraisalTypeRepository) {
		super();
		this.appraisalYearRepository = appraisalYearRepository;
		this.appraisalTypeRepository = appraisalTypeRepository;
	}

	public AppraisalYear getAppraisalYear() {
		return appraisalYearRepository.findByActive(true);
	}
	
	public AppraisalYear getAppraisalYear(LocalDate year) {
		return appraisalYearRepository.findByYear(year);
	}
	
	public AppraisalType getAppraisalType() {
		return appraisalTypeRepository.findByActive(true);
	}
	
	public AppraisalType getAppraisalType(byte type) {
		return appraisalTypeRepository.findByType(type);
	}

	public AppraisalCategory getAppraisalCategory() {
		return new AppraisalCategory(getAppraisalType(), getAppraisalYear());
	}
	
	public AppraisalCategory getAppraisalCategory(byte type, LocalDate year) {
		return new AppraisalCategory(getAppraisalType(type), getAppraisalYear(year));
	}
	
	public AppraisalCategory getAppraisalCategory(byte type) {
		return new AppraisalCategory(getAppraisalType(type), getAppraisalYear());
	}

}
