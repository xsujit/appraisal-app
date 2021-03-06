package com.company.appraisal.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.company.appraisal.entities.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long>{
	Project findById(long id);
}
