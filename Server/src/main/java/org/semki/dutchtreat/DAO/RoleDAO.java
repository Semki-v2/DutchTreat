package org.semki.dutchtreat.DAO;

import org.semki.dutchtreat.entity.Role;

public interface RoleDAO extends BaseDAO<Role> {

	public Role getRoleByName(String name);
	
	
	
}
