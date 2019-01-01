package com.masteknet.appraisals.services;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.masteknet.appraisals.auth.AppraisalUserPrincipal;
import com.masteknet.appraisals.entities.AuthUserGroup;
import com.masteknet.appraisals.entities.Employee;
import com.masteknet.appraisals.entities.User;
import com.masteknet.appraisals.repositories.AuthUserGroupRepository;
import com.masteknet.appraisals.repositories.EmployeeRepository;
import com.masteknet.appraisals.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	private final UserRepository userRepository;
	private final AuthUserGroupRepository authUserGroupRepository;
	private final EmployeeRepository employeeRepository;

	public UserDetailsServiceImpl(UserRepository userRepository, AuthUserGroupRepository authUserGroupRepository, EmployeeRepository employeeRepository) {
		super();
		this.userRepository = userRepository;
		this.authUserGroupRepository = authUserGroupRepository;
		this.employeeRepository = employeeRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException("Sorry, this " + email + " does not exists");
		}
		List<AuthUserGroup> authGroups = this.authUserGroupRepository.findByUserId(user.getId());
		Employee employee = employeeRepository.findByUserId(user.getId());
		return new AppraisalUserPrincipal(user, authGroups, employee);
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
