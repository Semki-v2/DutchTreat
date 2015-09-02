package org.semki.dutchtreat.mvc.dto;

import java.util.List;

import org.semki.dutchtreat.entity.Evento;

public class EventDTO {
	public Long id;
	
	public String name;
	
	public List<ParticipantDTO> participants;
	
	public EventDTO()
	{
	}

	public EventDTO(Evento e) {
		this.id = e.getId();
		this.name = e.getName();
	}
	
	public Evento convertToEntity() {
		Evento e = new Evento();
		e.setId(id);
		e.setName(name);
		return e;
	}
}
