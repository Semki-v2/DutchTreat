package org.semki.dutchtreat.mvc.models;

import java.math.BigDecimal;

import org.semki.dutchtreat.entity.Participant;

public class CalculationRow {
	
	public Participant initiator;
	
	public BigDecimal amount;
	
	public String description;

	public CalculationRow(Participant initiator, BigDecimal amount, String description) {
		super();
		this.initiator = initiator;
		this.amount = amount;
		this.description = description;
	}
}
