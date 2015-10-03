package org.semki.dutchtreat.DAO;

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
	public Account getAccountByName(String name) {
		return this.first(createCriteria().add(Restrictions.eq("name", name))
				.list());
	}

	@Override
	public Account getAccountByEmail(String email) {
		return this.first(createCriteria().add(Restrictions.eq("email", email))
				.list());
	}

}
