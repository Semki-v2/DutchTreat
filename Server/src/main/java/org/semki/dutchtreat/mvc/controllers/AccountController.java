package org.semki.dutchtreat.mvc.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.semki.dutchtreat.core.exceptions.AccountValidationException;
import org.semki.dutchtreat.entity.Account;
import org.semki.dutchtreat.mvc.dto.AccountDTO;
import org.semki.dutchtreat.mvc.dto.RESTFaultDTO;
import org.semki.dutchtreat.mvc.models.AccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("app/auth")
public class AccountController {
	
	@Autowired
	private AccountModel accModel;
	private Exception exception;
	
	@RequestMapping(value = "/registration",method = RequestMethod.POST)
	@Transactional
	public void createAccount(@RequestBody AccountDTO dto)
	{
		accModel.createAccount(dto);
	}
	
	@RequestMapping(value = "/logout",method = RequestMethod.GET)
	@Transactional
	public void logout()
	{
		
	}
	
	@RequestMapping(value = "/current",method = RequestMethod.GET)
	@Transactional
	public @ResponseBody AccountDTO getCurrentUser()
	{	      
	    Account acc = accModel.getCurrentUser();
		
		return AccountDTO.convertToTransport(acc);
	}
	
	@RequestMapping(value = "/account/edit",method = RequestMethod.POST)
	@Transactional
	public @ResponseBody AccountDTO editAccount(@RequestBody AccountDTO accDTO)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	      String current_username = auth.getName();
		
	      if (!accDTO.user_login.equals(current_username))
	      {
			//TODO заменить на permission exception 
	    	throw new AccountValidationException(String.format(
					"Пользователь %l не может редактировать данный аккаунт", accDTO.user_login));
	      }
	      
		Account acc = accModel.updateAccount(accDTO);
		
		return AccountDTO.convertToTransport(acc);
	}
	
	@RequestMapping(value = "/account/{id}",method = RequestMethod.GET)
	@Transactional
	public @ResponseBody AccountDTO getAccount(@PathVariable Long id)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	      String current_username = auth.getName();
		
		Account acc = accModel.getAccountById(id);
		
		return AccountDTO.convertToTransport(acc);
	}
	
	@RequestMapping(value = "/account",method = RequestMethod.GET)
	@Transactional
	public @ResponseBody List<AccountDTO> getAccountList()
	{	
		return  accModel.getAccountList(); 
	}
	
	@RequestMapping(value = "/checkuser",method = RequestMethod.GET,params = {"username"})
	@Transactional
	public @ResponseBody boolean checkUser(@RequestParam("username") String username)
	{
		Account account = accModel.getCurrentUserByUsername(username);
		return  (account==null);
	}
	
	@RequestMapping(value = "/checkemail",method = RequestMethod.GET,params = {"email"})
	@Transactional
	public @ResponseBody boolean checkEmail(@RequestParam("email") String email)
	{
		Account account = accModel.getAccountByEmail(email);
		return  (account==null);
	}
	
	
	@ExceptionHandler(AccountValidationException.class)
	public @ResponseBody RESTFaultDTO handleInvalidAccException(HttpServletResponse response, AccountValidationException exception)
	{
		RESTFaultDTO dto = new RESTFaultDTO();
		
		dto.Message = exception.getMessage();
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		
		return dto;
	}
}
