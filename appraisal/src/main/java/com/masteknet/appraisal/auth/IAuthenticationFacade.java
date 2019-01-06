package com.masteknet.appraisal.auth;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {

	Authentication getAuthentication();
	AppraisalUserPrincipal getPrincipal();
}
