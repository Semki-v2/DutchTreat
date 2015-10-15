package org.semki.dutchtreat.mvc.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semki.dutchtreat.DAO.AccountDAO;
import org.semki.dutchtreat.DAO.RoleDAO;
import org.semki.dutchtreat.core.enums.CRUDMODE;
import org.semki.dutchtreat.core.enums.Roles;
import org.semki.dutchtreat.core.exceptions.AccountValidationException;
import org.semki.dutchtreat.entity.Account;
import org.semki.dutchtreat.entity.Role;
import org.semki.dutchtreat.mvc.dto.AccountDTO;
import org.semki.dutchtreat.mvc.dto.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class AccountModel {

	@Autowired
	private AccountDAO accountDAO;
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private UserDetailsService userDetails;

	@Autowired
	private BCryptPasswordEncoder passEncoder;

	public void createAccount(AccountDTO dto) throws AccountValidationException {
		validateAccount(dto,CRUDMODE.CREATE);

		Account acc = new Account();

		acc.setEmail(dto.email);
		acc.setName(dto.user_login);
		acc.setActive(true);
		acc.setPassword(passEncoder.encode(dto.user_password));
		Set<Role> rolesSet = new HashSet<Role>();
		
		
		if (dto.roles != null)
		{
			for (RoleDTO roleDTO : dto.roles) {
				rolesSet.add(roleDAO.getRoleByName(roleDTO.name));
			}
			
			acc.setRoles(rolesSet);
		}
		
		accountDAO.save(acc);
	}

	public void validateAccount(AccountDTO dto,CRUDMODE mode)
			throws AccountValidationException {
		if (dto.user_login.equals("")) {
			throw new AccountValidationException(String.format(
					"Имя пользователя обязательно", dto.user_login));
		}

		if ((accountDAO.getAccountByName(dto.user_login) != null)&&(mode == CRUDMODE.CREATE)) {
			throw new AccountValidationException(String.format(
					"Имя пользователя (%s) занято", dto.user_login));
		}
		
		Account emailAccount = accountDAO.getAccountByEmail(dto.email);

		if ((emailAccount != null)&&(!emailAccount.getName().equals(dto.user_login))) {
			throw new AccountValidationException(String.format(
					"Данный Email (%s) уже использован", dto.user_login));
		}

		if (!dto.user_password.equals(dto.password_confirmation)) {
			throw new AccountValidationException(String.format(
					"Подтверждение не совпадает с паролем", dto.user_login));
		}
	}

	public void createRole(String name) {
		Role role = new Role();
		role.setName(name);
		roleDAO.save(role);
	}
	
	public Account getCurrentUserByUsername(String username)
	{
		return accountDAO.getAccountByName(username);
	}
	
	public String getCurrentUsername()
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	     return auth.getName();
	}
	
	public Account getCurrentUser() {
		return getCurrentUserByUsername(getCurrentUsername());
	}

	public Account updateAccount(AccountDTO accDTO) {
		validateAccount(accDTO,CRUDMODE.UPDATE);
		
		Account acc = accountDAO.get(accDTO.id);
		
		acc.setEmail(accDTO.email);
		acc.setActive(accDTO.active);
		acc.setPassword(passEncoder.encode(accDTO.user_password));
		Set<Role> rolesSet = new HashSet<Role>();
		
		for(RoleDTO roleDTO : accDTO.roles) {
			rolesSet.add(roleDAO.getRoleByName(roleDTO.name));
		}
		acc.setRoles(rolesSet);
		accountDAO.save(acc);
		
		return accountDAO.getAccountByName(acc.getName());
	}

	public Account getAccountById(Long id) {
		return accountDAO.get(id);
	}
	
	public boolean accountHasRole(String user_name,Roles roleName)
	{
		Account acc = accountDAO.getAccountByName(user_name);
		
		boolean result = false;
		
		for (Role role : acc.getRoles()) {
			
			if (role.getName().equals(roleName.toString()))
			{
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	public boolean currentUserHasRole(Roles role)
	{
		return accountHasRole(getCurrentUsername(), role);
	}

	public List<AccountDTO> getAccountList() {
		List<AccountDTO> accDtoList = new ArrayList<AccountDTO>();
		
		List<Account> accList = accountDAO.list();
		
		for (Account account : accList) {
			accDtoList.add(AccountDTO.convertToTransport(account));
		}
		
		return accDtoList;
	}

	public Account getAccountByEmail(String email) {
		return accountDAO.getAccountByEmail(email);
	}
}
