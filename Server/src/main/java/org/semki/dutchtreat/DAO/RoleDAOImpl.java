package org.semki.dutchtreat.DAO;

import org.hibernate.criterion.Restrictions;
import org.semki.dutchtreat.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RoleDAOImpl extends BaseDAOImpl<Role> implements RoleDAO {
	
	@Autowired
	private HibernateTransactionManager tman;

	public RoleDAOImpl() {
		super(Role.class);
	}

	@Override
	public Role getRoleByName(String name) {
		return first(createCriteria().add(Restrictions.eq("name", name)).list());
	}

}
