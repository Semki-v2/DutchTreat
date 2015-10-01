package org.semki.dutchtreat.mvc.dto;

import org.semki.dutchtreat.entity.Role;

public class RoleDTO {
	
	public RoleDTO() {

	}

	public RoleDTO(String roleName) {
		this.name = roleName;
	}
	
	public RoleDTO(Role role) {
		this.name = role.getName();
		this.id = role.getId();
	}

	public Long id;
	
	public String name;
}
