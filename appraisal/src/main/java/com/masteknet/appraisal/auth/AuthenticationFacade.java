package com.masteknet.appraisal.auth;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

	@Override
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	@Override
	public AppraisalUserPrincipal getPrincipal() {
		Authentication authentication = getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			return (AppraisalUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		return null;
	}
}
