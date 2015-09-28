package org.semki.dutchtreat.mvc.dto;

import java.util.List;

public class AccountDTO {

	public Long id;

	public String user_login;

	public String user_password;

	public String password_confirmation;

	public String email;
	
	public List<RoleDTO> roles;

}
