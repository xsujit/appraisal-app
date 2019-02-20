package com.company.appraisal.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.appraisal.entities.AuthUserGroup;

@Repository
public interface AuthUserGroupRepository extends JpaRepository<AuthUserGroup, Long>{

	List<AuthUserGroup> findByUserId(long userId);
}
