package org.semki.dutchtreat.config.security;

import java.util.ArrayList;
import java.util.Collection;

import org.semki.dutchtreat.entity.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class AccountUserDetails implements UserDetails {

	private final Account user;
	
	final static Logger logger = LoggerFactory.getLogger(AccountUserDetails.class);
	
	public AccountUserDetails(Account user) {
		super();
		this.user = user;
	}

	@Override
	public String getUsername() {
		return user.getName();
	}
	
	

	@Override
	public boolean isAccountNonExpired() {
		// TODO Account expiared
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
		return user.isActive();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		GrantedAuthority authority = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "USER";
            }
        };

        ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(authority);
        return authorities;
	}

	public String getEmail() {
		return user.getEmail();
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

}
