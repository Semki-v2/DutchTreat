package org.semki.dutchtreat.mvc.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.semki.dutchtreat.DAO.EventoDAO;
import org.semki.dutchtreat.DAO.ParticipantDAO;
import org.semki.dutchtreat.DAO.PurchaseConsumerDAO;
import org.semki.dutchtreat.DAO.PurchasesDAO;
import org.semki.dutchtreat.entity.Evento;
import org.semki.dutchtreat.entity.Purchase;
import org.semki.dutchtreat.entity.PurchaseConsumer;
import org.semki.dutchtreat.mvc.dto.ParticipantDTO;
import org.semki.dutchtreat.mvc.dto.PurchaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/api")
public class PurchasesController {
	
	@Autowired
	PurchasesDAO dao;
	
	@Autowired
	EventoDAO eventoDAO;
	
	@Autowired
	ParticipantDAO participantDAO;
	
	@Autowired
	PurchaseConsumerDAO consumersDAO;
	
	@RequestMapping("/purchases")
	@Transactional
	public List<PurchaseDTO> getListByEvent(@RequestParam(name="event_id", required=true) Long eventId) {
		Evento evento = eventoDAO.get(eventId);
		List<PurchaseDTO> result = new ArrayList<>();
		List<Purchase> purchases = dao.getByEvent(evento);
		for (Purchase purchase : purchases) {
			result.add(PurchaseDTO.ConvertToDTO(purchase));
		}
		return result;
	}
	
	@RequestMapping(value = "/purchases/{purchaseId}", method = RequestMethod.GET)
	@Transactional
	public PurchaseDTO getById(@PathVariable Long purchaseId) {
		Purchase entity = dao.get(purchaseId);
		PurchaseDTO dto = PurchaseDTO.ConvertToDTO(entity); 
		return dto;
	}
	
	@RequestMapping(value = "/purchases",method = RequestMethod.POST)
	@Transactional
	public void create(@RequestBody PurchaseDTO dto)
	{
		Purchase entity = new Purchase();
		entity.setAmount(dto.amount);
		entity.setDescription(dto.description);
		entity.setEvento(eventoDAO.get(dto.event_id));
		entity.setBuyer(participantDAO.get(dto.buyer.id));
		dao.save(entity);
		
		for (ParticipantDTO consumerDTO : dto.consumers) {
			PurchaseConsumer consumer = new PurchaseConsumer();
			consumer.setConsumer(participantDAO.get(consumerDTO.id));
			consumer.setPurchase(entity);
			consumersDAO.save(consumer);
		}
	}
	
	@RequestMapping(value = "/purchases/{purchaseId}",method = RequestMethod.POST)
	@Transactional
	public void update(@RequestBody PurchaseDTO dto, @PathVariable Long purchaseId)
	{
		Purchase entity = dao.get(purchaseId);
		entity.setAmount(dto.amount);
		entity.setDescription(dto.description);
		entity.setBuyer(participantDAO.get(dto.buyer.id));
		dao.update(entity);
		
		for (PurchaseConsumer consumer : consumersDAO.getByPurchase(entity)) {
			consumersDAO.delete(consumer.getId());
		}
		
		for (ParticipantDTO consumerDTO : dto.consumers) {
			PurchaseConsumer consumer = new PurchaseConsumer();
			consumer.setConsumer(participantDAO.get(consumerDTO.id));
			consumer.setPurchase(entity);
			consumersDAO.save(consumer);
		}
	}
	
	@RequestMapping(value = "/purchases/{purchaseId}", method = RequestMethod.DELETE)
	@Transactional
	public void deleteById(@PathVariable Long purchaseId) {
		Purchase purchase = dao.get(purchaseId);
		for (PurchaseConsumer consumer : consumersDAO.getByPurchase(purchase)) {
			consumersDAO.delete(consumer.getId());
		}
		dao.delete(purchaseId);
	}
}
