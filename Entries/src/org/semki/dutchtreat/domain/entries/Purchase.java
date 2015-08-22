package org.semki.dutchtreat.domain.entries;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class Purchase extends Source {

	public Participant Buyer;
	public ArrayList<Participant> Consumers;
	public BigDecimal PurchaseSum;
	public Date PurhasedOn;
	public String Description;
	public Evento Evento;
	
	public Purchase(Participant buyer, ArrayList<Participant> consumers,
			BigDecimal purchaseSum, Date purhasedOn, String description, Evento evento) {
		super();
		Buyer = buyer;
		Consumers = consumers;
		PurchaseSum = purchaseSum;
		PurhasedOn = purhasedOn;
		Description = description;
		Evento = evento;
	}
	public String toString()
	{
		return "Закупка на сумму "+PurchaseSum.toString();
	}
	
	
}
