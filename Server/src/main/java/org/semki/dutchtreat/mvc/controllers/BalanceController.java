package org.semki.dutchtreat.mvc.controllers;

import javax.transaction.Transactional;

import org.semki.dutchtreat.DAO.EventoDAO;
import org.semki.dutchtreat.DAO.ParticipantDAO;
import org.semki.dutchtreat.entity.Evento;
import org.semki.dutchtreat.entity.Participant;
import org.semki.dutchtreat.mvc.dto.ParticipantBalanceDTO;
import org.semki.dutchtreat.mvc.dto.EventSummaryBalanceDTO;
import org.semki.dutchtreat.mvc.models.BalanceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/api")
public class BalanceController {

	@Autowired
	private ParticipantDAO participantDAO;
	
	@Autowired
	private EventoDAO eventoDAO;

	@RequestMapping(value = "/balance/by-participant/{participantId}", method = RequestMethod.GET)
	@Transactional
	public ParticipantBalanceDTO getBalanceByParticipant(
			@PathVariable Long participantId) {
		Participant participant = participantDAO.get(participantId);
		BalanceCalculator calculator = BalanceCalculator.CreateByEvent(participant.getEvento());
		return calculator.ToParticipantBalanceDTO(participant);
	}
	
	@RequestMapping(value = "/balance/summary-by-event/{eventId}", method = RequestMethod.GET)
	@Transactional
	public EventSummaryBalanceDTO getSummaryBalanceByEvent(@PathVariable Long eventId) {
		Evento event = eventoDAO.get(eventId);
		BalanceCalculator calculator = BalanceCalculator.CreateByEvent(event);
		return calculator.ToSummaryBalanceDTO();
	}
}
