package org.semki.dutchtreat.mvc.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.semki.dutchtreat.DAO.EventoDAO;
import org.semki.dutchtreat.DAO.ParticipantDAO;
import org.semki.dutchtreat.core.initiaze.Roles;
import org.semki.dutchtreat.entity.Evento;
import org.semki.dutchtreat.entity.Participant;
import org.semki.dutchtreat.mvc.dto.EventDTO;
import org.semki.dutchtreat.mvc.dto.ParticipantDTO;
import org.semki.dutchtreat.mvc.models.AccountModel;
import org.semki.dutchtreat.mvc.models.EventModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	@Autowired
	EventModel eventoModel;
	
	@Autowired
	AccountModel accountModel;
	
	@RequestMapping("/eventos")
	@Transactional
	public List<EventDTO> getEvents() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	      String username = auth.getName();
	      
	    List<EventDTO> listDTO;
	     
	    if (accountModel.accountHasRole(username, Roles.ADMIN))
	    {
	    	listDTO = eventoModel.getAllEventos();
	    }
	    else
	    {
	    	listDTO = eventoModel.getRestrictedEventos(accountModel.getCurrentUser(username));
	    } 
		
		return listDTO;
	}
	
	@RequestMapping(value = "/eventos/{eventId}", method = RequestMethod.GET)
	@Transactional
	public EventDTO getEventById(@PathVariable Long eventId) {
		Evento evento = dao.get(eventId);
		EventDTO eventDTO = new EventDTO(evento);
		List<Participant> participants = paricipantDAO.getByEvent(evento);
		for (Participant participant : participants) {
			eventDTO.participants.add(ParticipantDTO.convertToDTO(participant));
		}
		return eventDTO;
	}
	
	@RequestMapping(value = "/eventos/{eventId}", method = RequestMethod.DELETE)
	@Transactional
	public void deleteById(@PathVariable Long eventId) {
		Evento event = dao.get(eventId);
		paricipantDAO.deleteByEvent(event);
		dao.delete(eventId);
	}
	
	@RequestMapping(value = "/eventos",method = RequestMethod.POST)
	@Transactional
	public void create(@RequestBody EventDTO eDTO)
	{
		eventoModel.createEvent(eDTO);
	}
	
	@RequestMapping(value = "/eventos/{eventId}",method = RequestMethod.POST)
	@Transactional
	public void update(@RequestBody EventDTO eDTO, @PathVariable Long eventId)
	{
		Evento entity = dao.get(eventId);
		entity.setName(eDTO.name);
		dao.update(entity);
		
		for (ParticipantDTO participantDTO : eDTO.participants) {
			Participant participant;
			if (participantDTO.id != null) {
				participant = paricipantDAO.get(participantDTO.id);
				participant.setName(participantDTO.name);
			}
			else {
				participant = paricipantDAO.getByEventAndName(entity, participantDTO.name);
				if (participant == null) {
					participant = participantDTO.convertToEntity();
				}
				participant.setEvento(entity);
			}
			paricipantDAO.save(participant);
		}
	}
}
