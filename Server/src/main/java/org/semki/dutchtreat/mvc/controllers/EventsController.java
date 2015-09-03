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
			EventDTO eventDTO = new EventDTO(en);
			List<Participant> participants = paricipantDAO.getByEvent(en);
			for (Participant participant : participants) {
				eventDTO.participants.add(ParticipantDTO.convertToDTO(participant));
			}
			listDTO.add(eventDTO);
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
			participant.setEvento(entity);
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
