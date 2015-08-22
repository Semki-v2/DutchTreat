package org.semki.dutchtreat.domain.entries;

import java.math.BigDecimal;
import java.util.Date;

public class Transfer extends Source {

	public Participant From;
	public Participant To;
	public BigDecimal TransferSum;
	public String Description;
	public Evento Evento;
	public Date TransferDate;
	public Transfer(Participant from, Participant to, BigDecimal transferSum, Evento evento, String description) {
		super();
		From = from;
		To = to;
		TransferSum = transferSum;
		Description = description;
		Evento = evento;
	}
	
	public String toString()
	{
		return "Перевод от "+From.toString()+ " к "+To.toString() + " на сумму " + TransferSum;
	}

	
}
