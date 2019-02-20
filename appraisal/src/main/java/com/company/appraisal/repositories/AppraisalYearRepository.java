package com.company.appraisal.repositories;

import java.time.LocalDate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.company.appraisal.entities.AppraisalYear;

@Repository
public interface AppraisalYearRepository extends CrudRepository<AppraisalYear, LocalDate> {
	
	AppraisalYear findByActive(boolean activeYear);
	AppraisalYear findByYear(LocalDate date);
}
