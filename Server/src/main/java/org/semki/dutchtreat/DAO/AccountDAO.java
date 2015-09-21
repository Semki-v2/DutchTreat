package org.semki.dutchtreat.DAO;

import org.semki.dutchtreat.entity.Account;

public interface AccountDAO extends BaseDAO<Account>  {
	
	public Account getAccountByName(String name);
	
	public Account getAccountByEmail(String email);

}
