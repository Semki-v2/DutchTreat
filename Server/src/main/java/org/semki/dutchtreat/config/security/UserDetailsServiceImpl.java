package org.semki.dutchtreat.config.security;

import org.semki.dutchtreat.DAO.AccountDAO;
import org.semki.dutchtreat.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private AccountDAO accountDAO;
	 
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		Account account = accountDAO.getUserByName(username);
		
		if (account==null)
		{
			throw new UsernameNotFoundException("no user found with " + username);
		}
		
		return new AccountUserDetails(account);
	}

}
