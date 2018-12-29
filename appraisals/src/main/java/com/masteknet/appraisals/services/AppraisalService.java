package com.masteknet.appraisals.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
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
	
	public Appraisal getAppraisal(AppraisalPk appraisalPk) {
		return appraisalRepository.findByAppraisalPk(appraisalPk);
	}
	
	public Appraisal getAppraisal(AppraisalCategory appraisalCategory, Employee employee) {
		return getAppraisal(new AppraisalPk(employee, appraisalCategory));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public boolean isAdmin() {
		return true;
	}
		
	public DataAccessException save(Appraisal appraisal) {
		
		try {
			appraisalRepository.save(appraisal);
		} catch (DataAccessException dae) {
			System.out.println(dae.getMessage());
			return dae; 
		}
		return null;
	}
	
	public DataAccessException update(Appraisal appraisal) {

		Appraisal existingAppraisal = getAppraisal(appraisal.getAppraisalPk());
		if(existingAppraisal != null) {
			existingAppraisal.setDescription(appraisal.getDescription());
		}
		try {
			appraisalRepository.save(existingAppraisal);
		} catch (DataAccessException dae) {
			System.out.println(dae.getMessage());
			return dae; 
		}
		return null;
	}
		
	public Iterable<Appraisal> getSignedOffAppraisals() {
		return appraisalRepository.findBySignedOff(true);
	}
	/*
	public Iterable<Appraisal> getSignedOffActiveAppraisals() {
		return appraisalRepository.findBySignedOffAndAppraisalPkAppraisalCategory(true, appraisalCategoryService.getAppraisalCategory());
	}
	
	public Iterable<Appraisal> getSignedOffTeamAppraisals(Project project){
		return appraisalRepository.findBySignedOffAndAppraisalPkAppraisalCategoryAndProject(true, appraisalCategoryService.getAppraisalCategory(), project);
	}
	*/
	public DataAccessException signOff(AppraisalCategory appraisalCategory, Employee employee) {
		Appraisal appraisal = getAppraisal(appraisalCategory, employee);
		if(appraisal != null) {
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
