package org.semki.dutchtreat.mvc.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.semki.dutchtreat.DAO.EventoDAO;
import org.semki.dutchtreat.DAO.ParticipantDAO;
import org.semki.dutchtreat.entity.Evento;
import org.semki.dutchtreat.entity.Participant;
import org.semki.dutchtreat.mvc.dto.EventDTO;
import org.semki.dutchtreat.mvc.dto.ParticipantDTO;
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
	EventoDAO dao;
	
	@Autowired
	ParticipantDAO paricipantDAO;
	
	@RequestMapping("/eventos")
	@Transactional
	public List<EventDTO> getEvents() {
		List<Evento> list = dao.list();
		List<EventDTO> listDTO = new ArrayList<EventDTO>();
		for (Evento en : list) {
			listDTO.add(new EventDTO(en));
		}
		
		return listDTO;
	}
	
	@RequestMapping(value = "/eventos/{eventId}", method = RequestMethod.GET)
	@Transactional
	public EventDTO getEventById(@PathVariable Long eventId) {
		return new EventDTO(dao.get(eventId));
	}
	
	@RequestMapping(value = "/eventos/{eventId}", method = RequestMethod.DELETE)
	@Transactional
	public void deleteById(@PathVariable Long eventId) {
		dao.delete(eventId);
	}
	
	@RequestMapping(value = "/eventos",method = RequestMethod.POST)
	@Transactional
	public void create(@RequestBody EventDTO eDTO)
	{
		Evento entity = eDTO.convertToEntity();
		dao.save(entity);
		
		for (ParticipantDTO participantDTO : eDTO.participants) {
			Participant participant = participantDTO.convertToEntity();
			paricipantDAO.save(participant);
		}
	}
	
	@RequestMapping(value = "/eventos/{eventId}",method = RequestMethod.POST)
	@Transactional
	public void update(@RequestBody EventDTO eDTO, @PathVariable Long eventId)
	{
		Evento entity = dao.get(eventId);
		entity.setName(eDTO.name);
		dao.update(entity);
	}
}
