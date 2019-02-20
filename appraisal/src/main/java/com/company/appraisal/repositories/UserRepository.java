package com.company.appraisal.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.company.appraisal.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	
	User findById(long id);
	User findByEmail(String email);
}
