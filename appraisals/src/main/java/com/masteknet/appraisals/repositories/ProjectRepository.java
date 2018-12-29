package com.masteknet.appraisals.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.masteknet.appraisals.entities.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long>{
	Project findById(long id);
}
