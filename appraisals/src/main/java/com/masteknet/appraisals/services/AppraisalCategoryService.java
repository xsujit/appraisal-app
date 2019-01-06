package com.masteknet.appraisals.services;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.masteknet.appraisals.entities.AppraisalCategory;
import com.masteknet.appraisals.entities.AppraisalType;
import com.masteknet.appraisals.entities.AppraisalYear;
import com.masteknet.appraisals.repositories.AppraisalTypeRepository;
import com.masteknet.appraisals.repositories.AppraisalYearRepository;

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
