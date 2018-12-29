package com.masteknet.appraisals.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.masteknet.appraisals.entities.AppraisalType;

@Repository
public interface AppraisalTypeRepository extends CrudRepository<AppraisalType, Byte> {

	AppraisalType findByActive(boolean active);
	AppraisalType findByType(byte type);

}
