package org.semki.dutchtreat.DAO;

import org.semki.dutchtreat.entity.Account;

public interface AccountDAO extends BaseDAO<Account>  {
	
	public Account getUserByName(String name);

}
