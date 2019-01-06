package com.masteknet.appraisal.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.masteknet.appraisal.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	
	User findById(long id);
	User findByEmail(String email);
}
