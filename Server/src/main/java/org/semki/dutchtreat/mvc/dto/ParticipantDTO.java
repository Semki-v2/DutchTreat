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

	public static ParticipantDTO convertToDTO(Participant participant) {
		ParticipantDTO dto = new ParticipantDTO();
		dto.id = participant.getId();
		dto.name = participant.getName();
		return dto;
	}
}
