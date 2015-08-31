package org.semki.dutchtreat.mvc.dto;

import org.semki.dutchtreat.entity.Evento;

public class Event {
	public Integer id;
	
	public String name;
	
	public Event()
	{
		
	}

	public Event(Evento e) {
		this.id = e.getId();
		this.name = e.getName();
	}
	
	
}
