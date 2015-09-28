package org.semki.dutchtreat.core.initiaze;

import org.apache.log4j.Logger;
import org.semki.dutchtreat.DAO.AccountDAO;
import org.semki.dutchtreat.DAO.RoleDAO;
import org.semki.dutchtreat.entity.Role;
import org.semki.dutchtreat.mvc.dto.AccountDTO;
import org.semki.dutchtreat.mvc.models.AccountModel;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@EnableTransactionManagement
@Transactional
public class SecurityInit implements InitializingBean{
	
	@Autowired
	private AccountModel accModel;
	
	@Autowired
	private AccountDAO accDAO;
	
	@Autowired
	private RoleDAO roleDAO;
	
	static Logger log = Logger.getLogger(SecurityInit.class);
	

	@Override
	@Transactional
	public void afterPropertiesSet() throws Exception {
		//initRoles();
		initUsers();
	}
	
	@Transactional
	public void initUsers()
	{
		createOrUpdateUser("admin", "aDm1541", "admin.de1mos.net",new String[]{"ADMIN","USER"});
	}
	
	@Transactional
	public void createOrUpdateUser(String name,String password,String email,String[] rolesNames)
	{	
		if (accDAO.getAccountByName(name)==null)
		{
			AccountDTO adminDTO = new AccountDTO();
		
			adminDTO.user_login =  name;
			adminDTO.user_password = password;
			adminDTO.password_confirmation = password;
			adminDTO.email = email;
			
//			for (String roleName : rolesNames) {
//				adminDTO.roles.add(new RoleDTO(roleName));
//			}
		
			accModel.createAccount(adminDTO);
		}
	}
	
	@Transactional
	public void initRoles()
	{
		createOrUpdateRole("USER");
		createOrUpdateRole("ADMIN");
	}
	
	@Transactional
	public void createOrUpdateRole(String name)
	{
		if (roleDAO.getRoleByName(name)==null)
		{
			Role role = new Role();
			role.setName(name);
			roleDAO.save(role);
		}
	}

}
