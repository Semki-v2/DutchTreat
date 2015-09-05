package org.semki.dutchtreat.mvc.controllers;

import javax.transaction.Transactional;

import org.semki.dutchtreat.DAO.ParticipantDAO;
import org.semki.dutchtreat.entity.Participant;
import org.semki.dutchtreat.mvc.dto.ParticipantBalanceDTO;
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

	@RequestMapping(value = "/balance/by-participant/{participantId}", method = RequestMethod.GET)
	@Transactional
	public ParticipantBalanceDTO getBalanceByParticipant(
			@PathVariable Long participantId) {
		Participant participant = participantDAO.get(participantId);
		BalanceCalculator calculator = BalanceCalculator.CreateByParticipant(participant);
		calculator.calculate();
		return calculator.ToParticipantBalanceDTO();
	}
}
