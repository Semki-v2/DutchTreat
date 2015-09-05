package org.semki.dutchtreat.mvc.dto;

import java.math.BigDecimal;
import java.util.List;

public class ParticipantBalanceDTO {
	
	public BigDecimal totalBalance;
	
	public List<CalculationRowDTO> records;
}
