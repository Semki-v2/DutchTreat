package org.semki.dutchtreat.mvc.dto;

import java.math.BigDecimal;

import org.semki.dutchtreat.entity.Participant;

public class BalanceSummaryRowDTO {

	public ParticipantDTO participant;
	
	public BigDecimal amount;
	
	public static BalanceSummaryRowDTO convertToDTO(Participant pariticipant, BigDecimal amount) {
		BalanceSummaryRowDTO dto = new BalanceSummaryRowDTO();
		dto.amount = amount;
		dto.participant = ParticipantDTO.convertToDTO(pariticipant);
		return dto;
	}
}
