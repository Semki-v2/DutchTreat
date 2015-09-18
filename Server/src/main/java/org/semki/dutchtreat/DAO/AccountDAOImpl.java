package org.semki.dutchtreat.DAO;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.semki.dutchtreat.entity.Account;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class AccountDAOImpl extends BaseDAOImpl<Account> implements AccountDAO {

	public AccountDAOImpl() {
		super(Account.class);
	}

	@Override
	public Account getUserByName(String name) {
		List<Account> accList = createCriteria().add(Restrictions.eq("name", name)).list();
		
		Account result = null;
		
		if (accList.size()>0)
		{
			result = accList.get(0);
		}
		
		return result;
	}


}
