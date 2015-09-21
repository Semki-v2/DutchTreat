package org.semki.dutchtreat.mvc.models;

import org.semki.dutchtreat.DAO.AccountDAO;
import org.semki.dutchtreat.core.exceptions.AccountValidationException;
import org.semki.dutchtreat.entity.Account;
import org.semki.dutchtreat.mvc.dto.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class AccountModel {
	
	@Autowired
	private AccountDAO accountDAO;
	
	@Autowired
	private ReflectionSaltSource saltSource;
	
	@Autowired
	private Md5PasswordEncoder passEncoder;
	
	public void createAccount(AccountDTO dto) throws AccountValidationException
	{
		validateAccount(dto);
		
		Account acc = new Account();
		
		acc.setEmail(dto.email);
		acc.setName(dto.user_login);
		acc.setActive(true);
		acc.setPassword(passEncoder.encodePassword(dto.user_password, saltSource));
		
		accountDAO.save(acc);
	}
	
	public void validateAccount(AccountDTO dto) throws AccountValidationException
	{
		if (dto.user_login.equals(""))
		{
			throw new AccountValidationException(String.format("Имя пользователя обязательно", dto.user_login));
		}
		
		if (accountDAO.getAccountByName(dto.user_login)!=null)
		{
			throw new AccountValidationException(String.format("Имя пользователя (&1) занято", dto.user_login));
		}
		
		if (accountDAO.getAccountByEmail(dto.email)!=null)
		{
			throw new AccountValidationException(String.format("Данный Email (&1) уже использован", dto.user_login));
		}
		
		if (!dto.user_password.equals(dto.password_confirmation))
		{
			throw new AccountValidationException(String.format("Подтверждение не совпадает с паролем", dto.user_login));
		}
	}

}
