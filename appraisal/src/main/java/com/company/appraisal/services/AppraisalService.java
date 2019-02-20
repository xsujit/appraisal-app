package com.company.appraisal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import com.company.appraisal.entities.Appraisal;
import com.company.appraisal.entities.AppraisalCategory;
import com.company.appraisal.entities.AppraisalPk;
import com.company.appraisal.entities.Employee;
import com.company.appraisal.repositories.AppraisalRepository;

@Service
public class AppraisalService {
	
	private final AppraisalRepository appraisalRepository;

	@Autowired
	public AppraisalService(AppraisalRepository appraisalRepository) {
		super();
		this.appraisalRepository = appraisalRepository;
	}
	
	private Appraisal getAppraisal(AppraisalPk appraisalPk) { // private
		return appraisalRepository.findByAppraisalPk(appraisalPk);
	}
		
	@PostAuthorize("returnObject == null OR authentication.principal.projectId == returnObject.project.id")
	public Appraisal getAppraisal(Employee employee, AppraisalCategory appraisalCategory) {
		return getAppraisal(new AppraisalPk(employee, appraisalCategory));
	}

	public void save(Appraisal appraisal, AppraisalCategory appraisalCategory, Employee employee) {
		
		appraisal.setAppraisalPk(new AppraisalPk(employee, appraisalCategory));
		Appraisal currentAppraisal = getAppraisal(appraisal.getAppraisalPk());
		if(currentAppraisal != null && !currentAppraisal.isSignedOff()) { // appraisal edit flow
			currentAppraisal.setDescription(appraisal.getDescription());
			appraisalRepository.save(currentAppraisal);
		} else { // new appraisal flow
			appraisal.setProject(employee.getUser().getProject());
			appraisalRepository.save(appraisal);
		}
	}
	
	public Iterable<Appraisal> getSignedOffAppraisals() {
		return appraisalRepository.findBySignedOff(true);
	}

	public void signOff(AppraisalCategory appraisalCategory, Employee employee) {
		Appraisal appraisal = getAppraisal(employee, appraisalCategory);
		if(appraisal != null && !appraisal.isSignedOff()) {
			appraisal.setSignedOff(true);
			appraisalRepository.save(appraisal);
		}
	}
}
