package org.semki.dutchtreat.mvc.controllers;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.semki.dutchtreat.core.exceptions.AccountValidationException;
import org.semki.dutchtreat.mvc.dto.AccountDTO;
import org.semki.dutchtreat.mvc.dto.RESTFaultDTO;
import org.semki.dutchtreat.mvc.models.AccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	
	@ExceptionHandler(AccountValidationException.class)
	public @ResponseBody RESTFaultDTO handleInvalidAccException(HttpServletResponse response, AccountValidationException exception)
	{
		RESTFaultDTO dto = new RESTFaultDTO();
		
		dto.Message = exception.getMessage();
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		
		return dto;
	}
}
