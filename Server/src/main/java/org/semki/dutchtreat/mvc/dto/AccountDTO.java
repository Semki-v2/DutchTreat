package org.semki.dutchtreat.mvc.dto;

import java.util.ArrayList;
import java.util.List;

import org.semki.dutchtreat.entity.Account;
import org.semki.dutchtreat.entity.Role;

public class AccountDTO {

	public Long id;

	public String user_login;

	public String user_password;

	public String password_confirmation;

	public String email;
	
	public List<RoleDTO> roles;

	public boolean active;
	
	public static AccountDTO convertToTransport(Account account)
	{
		AccountDTO dto = new AccountDTO();
		
		dto.id = account.getId();
		dto.user_login = account.getName();
		dto.email = account.getEmail();
		dto.roles = new ArrayList<RoleDTO>();
		dto.active = account.isActive();
		
		for (Role role : account.getRoles()) {
			dto.roles.add(new RoleDTO(role));
		}
		
		return dto;
	}

}
