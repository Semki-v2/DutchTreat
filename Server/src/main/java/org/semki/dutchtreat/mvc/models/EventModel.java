package org.semki.dutchtreat.mvc.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semki.dutchtreat.DAO.AccountDAO;
import org.semki.dutchtreat.DAO.EventoDAO;
import org.semki.dutchtreat.DAO.ParticipantDAO;
import org.semki.dutchtreat.core.enums.Roles;
import org.semki.dutchtreat.core.exceptions.PermissionExeption;
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

	@Autowired
	AccountModel accModel;

	public void createEvent(EventDTO eDTO) {
		Evento entity = convertDTOToEntity(eDTO);

		entity.setAccessAccounts(getAccessHashSet(eDTO));

		eventDAO.save(entity);

		for (ParticipantDTO participantDTO : eDTO.participants) {
			Participant participant = participantDTO.convertToEntity();
			participant.setEvento(entity);
			participantDAO.save(participant);
		}
	}

	public void updateEvent(EventDTO eDTO, Long eventId) {
		Evento entity = eventDAO.get(eventId);
		entity.setName(eDTO.name);
		Set<Account> accessHashSet = getAccessHashSet(eDTO);
		entity.setAccessAccounts(accessHashSet);
		eventDAO.update(entity);

		for (ParticipantDTO participantDTO : eDTO.participants) {
			Participant participant;
			if (participantDTO.id != null) {
				participant = participantDAO.get(participantDTO.id);
				participant.setName(participantDTO.name);
			} else {
				participant = participantDAO.getByEventAndName(entity,
						participantDTO.name);
				if (participant == null) {
					participant = participantDTO.convertToEntity();
				}
				participant.setEvento(entity);
			}
			participantDAO.save(participant);
		}
	}

	private Evento convertDTOToEntity(EventDTO eDTO) {
		Evento entity = eDTO.convertToEntity();

		getAccessHashSet(eDTO);
		return entity;
	}

	private Set<Account> getAccessHashSet(EventDTO eDTO) {
		Set<Account> accounts = new HashSet<Account>();

		for (AccountDTO accDTO : eDTO.accessAccounts) {
			Account acc = accDAO.get(accDTO.id);

			accounts.add(acc);
		}

		return accounts;
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
			listDTO.add(convertEventToTransport(en));
		}
		return listDTO;
	}

	private EventDTO convertEventToTransport(Evento en) {
		EventDTO eventDTO = new EventDTO(en);
		List<Participant> participants = participantDAO.getByEvent(en);
		for (Participant participant : participants) {
			eventDTO.participants.add(ParticipantDTO.convertToDTO(participant));
		}
		return eventDTO;
	}

	public EventDTO GetById(Long eventId) throws PermissionExeption {

		Evento event = eventDAO.get(eventId);

		if (!CurrentUserHasAccess(event)) {
			throw new PermissionExeption();
		}

		return convertEventToTransport(event);
	}

	public boolean CurrentUserHasAccess(Evento e) {
		Account user = accModel.getCurrentUser();

		if (accModel.accountHasRole(user.getName(), Roles.ADMIN)) {
			return true;
		}

		return e.getAccessAccounts().contains(user);
	}

	public EventDTO createInvate(Long eventId) {
		Evento event = eventDAO.get(eventId);

		event.setInvate(java.util.UUID.randomUUID().toString());

		eventDAO.update(event);

		event = eventDAO.get(eventId);

		return convertEventToTransport(event);
	}

	public void addUser(Long eventId, String invateHash) throws PermissionExeption {
		Evento event = eventDAO.get(eventId);
		
		Account currentUser = accModel.getCurrentUser();
		
		if (event.getInvate().equals(invateHash))
		{
			Set<Account> accounts = event.getAccessAccounts();
			
			if (!accounts.contains(currentUser))
			{
				accounts.add(currentUser);
				
				event.setAccessAccounts(accounts);
				
				eventDAO.update(event);
			}
		}
		else
		{
			throw new PermissionExeption();
		}
	}
}
