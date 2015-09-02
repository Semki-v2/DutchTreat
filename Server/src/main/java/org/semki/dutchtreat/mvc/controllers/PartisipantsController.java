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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/api")
public class PartisipantsController {
	
	@Autowired
	EventoDAO eventDAO;
	
	@Autowired
	ParticipantDAO participantDAO;
	
	@RequestMapping(value = "/participants/{eventId}", method = RequestMethod.GET)
	@Transactional
	public List<ParticipantDTO> getEventById(@PathVariable Long eventId) {
		Evento evento = eventDAO.get(eventId);
		List<Participant> participants = participantDAO.getByEvent(evento);
		List<ParticipantDTO> result = new ArrayList<ParticipantDTO>();
		for (Participant participant : participants) {
			result.add(ParticipantDTO.convertToDTO(participant));
		}
		return result;
	}

}
