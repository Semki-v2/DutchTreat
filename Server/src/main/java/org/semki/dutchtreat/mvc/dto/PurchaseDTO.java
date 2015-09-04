package org.semki.dutchtreat.mvc.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.semki.dutchtreat.entity.Purchase;
import org.semki.dutchtreat.entity.PurchaseConsumer;

public class PurchaseDTO {
	
public Long id;
	
	public Long event_id;
	
	public BigDecimal amount;
	
	public String description;
	
	public ParticipantDTO buyer;
	
	public List<ParticipantDTO> consumers;
	
	public static PurchaseDTO ConvertToDTO(Purchase p) {
		PurchaseDTO dto = new PurchaseDTO();
		dto.id = p.getId();
		dto.amount = p.getAmount();
		dto.description = p.getDescription();
		dto.event_id = p.getEvento().getId();
		dto.buyer = ParticipantDTO.convertToDTO(p.getBuyer());
		dto.consumers = new ArrayList<>();
		for (PurchaseConsumer consumer : p.getConsumers()) {
			dto.consumers.add(ParticipantDTO.convertToDTO(consumer.getConsumer()));
		}
		return dto;
	}
}
