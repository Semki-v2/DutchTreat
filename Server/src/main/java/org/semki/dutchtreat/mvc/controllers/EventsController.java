package org.semki.dutchtreat.mvc.controllers;

import java.io.Console;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.SessionFactory;
import org.semki.dutchtreat.DAO.EventoDAOImpl;
import org.semki.dutchtreat.entity.Evento;
import org.semki.dutchtreat.mvc.dto.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/api")
public class EventsController {
	
	@Autowired
	SessionFactory session;
	
	static List<Event> events = new ArrayList<>();
	static {
		Event e1 = new Event();
		e1.id = 1;
		e1.name = "Байкал";
		events.add(e1);
		Event e2 = new Event();
		e2.id = 2;
		e2.name = "Бухатон";
		events.add(e2);
	}
	
	@RequestMapping("/eventos")
	public List<Event> getEvents() {
		
		EventoDAOImpl dao = new EventoDAOImpl(session);
		
		List<Evento> list = dao.list();
		List<Event> listDTO = new ArrayList<Event>();
		for (Evento en : list) {
			Event dto = new Event();
			
			dto.name = en.getName();
			dto.id = en.getId();
			
			listDTO.add(dto);
		}
		
		return listDTO;
	}
	
	@RequestMapping("/eventos/{eventId}")
	public Event getEventById(@PathVariable Integer eventId) {
		Event result = null;
		for (Event e : events) {
			if (e.id == eventId) {
				result = e;
			}
		}
		
		return result;
	}
	
	@RequestMapping(value = "/eventosnew",method = RequestMethod.POST)
	public void createNew(@RequestBody Event eDTO)
	{
		final Logger logger = LoggerFactory.getLogger(EventsController.class);
		EventoDAOImpl dao = new EventoDAOImpl(session);
		
		Evento entity = new Evento();
		
		entity.setName(eDTO.name);
		entity.setId(eDTO.id);
		
		logger.debug("create new "+eDTO.name);
		
		dao.saveOrUpdate(entity);
	}
}
