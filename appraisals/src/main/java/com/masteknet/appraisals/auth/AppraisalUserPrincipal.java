package com.masteknet.appraisals.auth;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.masteknet.appraisals.entities.AuthUserGroup;
import com.masteknet.appraisals.entities.User;

public class AppraisalUserPrincipal implements UserDetails {
	
	private static final long serialVersionUID = -2490487613804220734L;
	private User user;
	private List<AuthUserGroup> userGroups;
	
	public AppraisalUserPrincipal(User user, List<AuthUserGroup> userGroups) {
		super();
		this.user = user;
		this.userGroups = userGroups;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(userGroups == null) {
			return Collections.emptySet();
		}
		Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
		userGroups.forEach(group -> {
			grantedAuthorities.add(new SimpleGrantedAuthority(group.getAuthGroup()));
		});
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public long getProjectId() {
		return this.user.getProject().getId();
	}
	
	public long getUserId() {
		return this.user.getId();
	}
	
}
