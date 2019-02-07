package com.masteknet.appraisal.repositories;

import java.time.LocalDate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.masteknet.appraisal.entities.AppraisalYear;

@Repository
public interface AppraisalYearRepository extends CrudRepository<AppraisalYear, LocalDate> {
	
	AppraisalYear findByActive(boolean activeYear);
	AppraisalYear findByYear(LocalDate date);
}
