package org.semki.dutchtreat.core.initiaze;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.semki.dutchtreat.DAO.AccountDAO;
import org.semki.dutchtreat.DAO.RoleDAO;
import org.semki.dutchtreat.mvc.dto.AccountDTO;
import org.semki.dutchtreat.mvc.dto.RoleDTO;
import org.semki.dutchtreat.mvc.models.AccountModel;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@EnableTransactionManagement
@Transactional
@Service
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
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		initRoles();
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
			
			adminDTO.roles = new ArrayList<RoleDTO>();
			adminDTO.user_login =  name;
			adminDTO.user_password = password;
			adminDTO.password_confirmation = password;
			adminDTO.email = email;
			
			for (String roleName : rolesNames) {
				adminDTO.roles.add(new RoleDTO(roleName));
			}
		
			accModel.createAccount(adminDTO);
		}
	}
	
	@Transactional
	public void initRoles()
	{
		createOrUpdateRole(Roles.USER);
		createOrUpdateRole(Roles.ADMIN);
	}
	
	@Transactional
	public void createOrUpdateRole(Roles name)
	{
		if (roleDAO.getRoleByName(name.toString())==null)
		{
			accModel.createRole(name.toString());
		}
	}

}
