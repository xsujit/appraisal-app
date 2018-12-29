package com.masteknet.appraisals.repositories;

import java.time.LocalDate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.masteknet.appraisals.entities.AppraisalYear;

@Repository
public interface AppraisalYearRepository extends CrudRepository<AppraisalYear, LocalDate> {
	AppraisalYear findByActive(boolean activeYear);
	AppraisalYear findByYear(LocalDate date);

}
