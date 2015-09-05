package org.semki.dutchtreat.mvc.models;

import java.math.BigDecimal;

import org.semki.dutchtreat.entity.Participant;

public class CalcultionRow {
	
	public Participant initiator;
	
	public BigDecimal amount;
	
	public String description;

	public CalcultionRow(Participant initiator, BigDecimal amount, String description) {
		super();
		this.initiator = initiator;
		this.amount = amount;
		this.description = description;
	}
}
