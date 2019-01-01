package com.masteknet.appraisals.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import com.masteknet.appraisals.entities.Appraisal;
import com.masteknet.appraisals.entities.AppraisalCategory;
import com.masteknet.appraisals.entities.AppraisalPk;
import com.masteknet.appraisals.entities.Employee;
import com.masteknet.appraisals.repositories.AppraisalRepository;

@Service
public class AppraisalService {
	
	private AppraisalRepository appraisalRepository;

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

	public DataAccessException save(Appraisal appraisal, AppraisalCategory appraisalCategory, Employee employee) {
		
		appraisal.setAppraisalPk(new AppraisalPk(employee, appraisalCategory));
		appraisal.setProject(employee.getUser().getProject());
		try {
			appraisalRepository.save(appraisal);
		} catch (DataAccessException dae) {
			System.out.println(dae.getMessage());
			return dae; 
		}
		appraisalRepository.save(appraisal);
		return null;
	}
	
	public DataAccessException update(Appraisal appraisal, AppraisalCategory appraisalCategory, Employee employee) {

		appraisal.setAppraisalPk(new AppraisalPk(employee, appraisalCategory));
		Appraisal currentAppraisal = getAppraisal(appraisal.getAppraisalPk());
		if(currentAppraisal != null && !currentAppraisal.isSignedOff()) {
			currentAppraisal.setDescription(appraisal.getDescription());
		}
		try {
			appraisalRepository.save(currentAppraisal);
		} catch (DataAccessException dae) {
			System.out.println(dae.getMessage());
			return dae;
		}
		return null;
	}
		
	public Iterable<Appraisal> getSignedOffAppraisals() {
		return appraisalRepository.findBySignedOff(true);
	}

	public DataAccessException signOff(AppraisalCategory appraisalCategory, Employee employee) {
		Appraisal appraisal = getAppraisal(employee, appraisalCategory);
		if(appraisal != null && !appraisal.isSignedOff()) {
			appraisal.setSignedOff(true);
			try {
				appraisalRepository.save(appraisal);
			} catch (DataAccessException dae) {
				System.out.println(dae.getMessage());
				return dae; 
			}	
		}
		return null;
	}
}
