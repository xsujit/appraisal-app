package com.company.appraisal.services;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.company.appraisal.auth.AppraisalUserPrincipal;
import com.company.appraisal.entities.AuthUserGroup;
import com.company.appraisal.entities.User;
import com.company.appraisal.repositories.AuthUserGroupRepository;
import com.company.appraisal.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	private final UserRepository userRepository;
	private final AuthUserGroupRepository authUserGroupRepository;

	public UserDetailsServiceImpl(UserRepository userRepository, AuthUserGroupRepository authUserGroupRepository) {
		super();
		this.userRepository = userRepository;
		this.authUserGroupRepository = authUserGroupRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException("Sorry, this " + email + " does not exists");
		}
		List<AuthUserGroup> authGroups = this.authUserGroupRepository.findByUserId(user.getId());
		return new AppraisalUserPrincipal(user, authGroups);
	}
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public boolean emailExists(String email) { // before authentication
		if(findByEmail(email) != null) {
			return true;
		}
		return false;
	}
}
