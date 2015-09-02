package org.semki.dutchtreat.mvc.dto;

import org.semki.dutchtreat.entity.Participant;

public class ParticipantDTO {
	
	public Long id;
	
	public String name;
	
	public Participant convertToEntity() {
		Participant entity = new Participant();
		entity.setId(id);
		entity.setName(name);
		return entity;
	}
}
