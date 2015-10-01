package org.semki.dutchtreat.mvc.models;

import java.util.ArrayList;
import java.util.List;

import org.semki.dutchtreat.DAO.AccountDAO;
import org.semki.dutchtreat.DAO.EventoDAO;
import org.semki.dutchtreat.DAO.ParticipantDAO;
import org.semki.dutchtreat.entity.Account;
import org.semki.dutchtreat.entity.Evento;
import org.semki.dutchtreat.entity.Participant;
import org.semki.dutchtreat.mvc.dto.AccountDTO;
import org.semki.dutchtreat.mvc.dto.EventDTO;
import org.semki.dutchtreat.mvc.dto.ParticipantDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EventModel {

	@Autowired
	EventoDAO eventDAO;
	
	@Autowired
	AccountDAO accDAO;
	
	@Autowired
	ParticipantDAO participantDAO;
	
	public void createEvent(EventDTO eDTO)
	{
		Evento entity = eDTO.convertToEntity();
		eventDAO.save(entity);
		
		for (ParticipantDTO participantDTO : eDTO.participants) {
			Participant participant = participantDTO.convertToEntity();
			participant.setEvento(entity);
			participantDAO.save(participant);
		}
		
		for (AccountDTO accDTO : eDTO.accessAccounts) {
			
		}
	}
	
	
	
	public List<EventDTO> getRestrictedEventos(Account acc) {
		
		List<Evento> list = eventDAO.getRestrictedEventos(acc.getId());
		List<EventDTO> listDTO = convertToDTOList(list);
		
		return listDTO;
	}
	
	public List<EventDTO> getAllEventos() {
		
		List<Evento> list = eventDAO.list();
		List<EventDTO> listDTO = convertToDTOList(list);
		
		return listDTO;
	} 



	private List<EventDTO> convertToDTOList(List<Evento> list) {
		List<EventDTO> listDTO = new ArrayList<EventDTO>();
		
		for (Evento en : list) {
			EventDTO eventDTO = new EventDTO(en);
			List<Participant> participants = participantDAO.getByEvent(en);
			for (Participant participant : participants) {
				eventDTO.participants.add(ParticipantDTO.convertToDTO(participant));
			}
			listDTO.add(eventDTO);
		}
		return listDTO;
	}
}
