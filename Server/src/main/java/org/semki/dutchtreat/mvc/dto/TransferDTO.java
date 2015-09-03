package org.semki.dutchtreat.mvc.dto;

import java.math.BigDecimal;

import org.semki.dutchtreat.entity.Transfer;

public class TransferDTO {

	public Long id;
	
	public Long event_id;
	
	public BigDecimal amount;
	
	public String description;
	
	public ParticipantDTO sender;
	
	public ParticipantDTO receiver;
	
	public static TransferDTO ConvertToDTO(Transfer t) {
		TransferDTO dto = new TransferDTO();
		dto.id = t.getId();
		dto.amount = t.getAmount();
		dto.description = t.getDescription();
		dto.event_id = t.getEvento().getId();
		dto.sender = ParticipantDTO.convertToDTO(t.getSender());
		dto.receiver = ParticipantDTO.convertToDTO(t.getReceiver());
		return dto;
	}
}
