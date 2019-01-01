package com.masteknet.appraisals.auth;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {

	Authentication getAuthentication();
	AppraisalUserPrincipal getPrincipal();
}
