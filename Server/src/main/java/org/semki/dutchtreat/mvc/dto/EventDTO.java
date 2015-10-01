package org.semki.dutchtreat.mvc.dto;

import java.util.ArrayList;
import java.util.List;

import org.semki.dutchtreat.entity.Account;
import org.semki.dutchtreat.entity.Evento;

public class EventDTO {
	public Long id;
	
	public String name;
	
	public List<ParticipantDTO> participants = new ArrayList<>();
	
	public List<AccountDTO> accessAccounts = new ArrayList<AccountDTO>();
	
	public EventDTO()
	{
	}

	public EventDTO(Evento e) {
		this.id = e.getId();
		this.name = e.getName();
		
		for (Account account : e.getAccessAccounts()) {
			this.accessAccounts.add(AccountDTO.convertToTransport(account));
		}
	}
	
	public Evento convertToEntity() {
		Evento e = new Evento();
		e.setId(id);
		e.setName(name);
		
		return e;
	}
}
