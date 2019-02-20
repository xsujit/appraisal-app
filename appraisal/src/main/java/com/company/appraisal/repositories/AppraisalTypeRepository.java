package com.company.appraisal.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.company.appraisal.entities.AppraisalType;

@Repository
public interface AppraisalTypeRepository extends CrudRepository<AppraisalType, Byte> {

	AppraisalType findByActive(boolean active);
	AppraisalType findByType(byte type);
}
