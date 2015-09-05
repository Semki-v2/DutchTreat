package org.semki.dutchtreat.mvc.dto;

import java.math.BigDecimal;

import org.semki.dutchtreat.mvc.models.CalcultionRow;

public class CalculationRowDTO {
	
	public BigDecimal amount;
	
	public String description;
	
	public ParticipantDTO initiator;
	
	public static CalculationRowDTO convertToDTO(CalcultionRow row) {
		CalculationRowDTO dto = new CalculationRowDTO();
		dto.amount = row.amount;
		dto.description = row.description;
		dto.initiator = ParticipantDTO.convertToDTO(row.initiator);
		return dto;
	}
}
