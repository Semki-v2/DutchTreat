package org.semki.dutchtreat.mvc.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.semki.dutchtreat.DAO.EventoDAO;
import org.semki.dutchtreat.DAO.ParticipantDAO;
import org.semki.dutchtreat.DAO.TransferDAO;
import org.semki.dutchtreat.entity.Evento;
import org.semki.dutchtreat.entity.Transfer;
import org.semki.dutchtreat.mvc.dto.TransferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/api")
public class TransfersController {

	@Autowired
	TransferDAO dao;
	
	@Autowired
	EventoDAO eventoDAO;
	
	@Autowired
	ParticipantDAO participantDAO;
	
	@RequestMapping("/transfers")
	@Transactional
	public List<TransferDTO> getListByEvent(@RequestParam(name="event_id", required=true) Long eventId) {
		Evento evento = eventoDAO.get(eventId);
		List<TransferDTO> result = new ArrayList<>();
		List<Transfer> transfers = dao.getByEvent(evento);
		for (Transfer transfer : transfers) {
			result.add(TransferDTO.ConvertToDTO(transfer));
		}
		return result;
	}
	
	@RequestMapping(value = "/transfers/{transferId}", method = RequestMethod.GET)
	@Transactional
	public TransferDTO getById(@PathVariable Long transferId) {
		Transfer transfer = dao.get(transferId);
		TransferDTO dto = TransferDTO.ConvertToDTO(transfer); 
		return dto;
	}
	
	@RequestMapping(value = "/transfers",method = RequestMethod.POST)
	@Transactional
	public void create(@RequestBody TransferDTO dto)
	{
		Transfer entity = new Transfer();
		entity.setAmount(dto.amount);
		entity.setDescription(dto.description);
		entity.setEvento(eventoDAO.get(dto.event_id));
		entity.setSender(participantDAO.get(dto.sender.id));
		entity.setReceiver(participantDAO.get(dto.receiver.id));
		dao.save(entity);
	}
	
	@RequestMapping(value = "/transfers/{transferId}",method = RequestMethod.POST)
	@Transactional
	public void update(@RequestBody TransferDTO dto, @PathVariable Long transferId)
	{
		Transfer entity = dao.get(transferId);
		entity.setAmount(dto.amount);
		entity.setDescription(dto.description);
		entity.setSender(participantDAO.get(dto.sender.id));
		entity.setReceiver(participantDAO.get(dto.receiver.id));
		dao.update(entity);
	}
	
	@RequestMapping(value = "/transfers/{transferId}", method = RequestMethod.DELETE)
	@Transactional
	public void deleteById(@PathVariable Long transferId) {
		dao.delete(transferId);
	}
}
