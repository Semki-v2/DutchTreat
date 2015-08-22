package org.semki.dutchtreat.domain.entries;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class DutchTreatMachine {
	
	public static DutchTreatMachine Prepare()
	{
		DutchTreatMachine dtm = new DutchTreatMachine();
		dtm.Configure();
		dtm.Load();
		return dtm;
		
	}
	
	private EntryMachine entryMachine = new EntryMachine();
	
	public static final String PAYMENT_ACCOUNT = "PAYMENT_ACCOUNT";
	public static final String GOODS_ACCOUNT = "GOODS_ACCOUNT";
	
	private void Load()
	{
		// load all purchases
		// load all transfers
		
	}
	private void Configure()
	{
		entryMachine.AddAccountByName(PAYMENT_ACCOUNT);
		entryMachine.AddAccountByName(GOODS_ACCOUNT);
		
		
	}
	
	public void AddPurchase(Purchase purchase){
		
		// add payment
		AddPaymentEntry(purchase.PurchaseSum, purchase.Description, purchase, purchase.Evento, purchase.PurhasedOn, purchase.Buyer);
		
		// add expenses
		BigDecimal avgExpSum = purchase.PurchaseSum.divide(new BigDecimal(purchase.Consumers.size()));
		for(Participant consumer : purchase.Consumers)
		{
			AddGoodsEntry(avgExpSum, purchase.Description, purchase, purchase.Evento, purchase.PurhasedOn, consumer);
			
		}
		
		
	}
	
	
	
	private void AddPaymentEntry(BigDecimal sum, String description, Source source, 
			Evento evento, Date date, Participant participant)
	{
		
		AnalytcsSet set = new AnalytcsSet(participant, evento, source);
		System.out.println("add payment entry "+set.toString());
		entryMachine.AddEntry(PAYMENT_ACCOUNT, sum, set, description);
		
		
	}
	
	private void AddGoodsEntry(BigDecimal sum, String description, Source source, 
			Evento evento, Date date, Participant participant)
	{
		
		AnalytcsSet set = new AnalytcsSet(participant, evento, source);
		System.out.println("add goods entry "+set.toString());
		entryMachine.AddEntry(GOODS_ACCOUNT, sum, set, description);
		
		
	}

	public void AddTransfer(Transfer transfer)
	{
		// sum: from(+sum) -> to (-sum)
		AddPaymentEntry(transfer.TransferSum, transfer.Description, transfer, transfer.Evento, transfer.TransferDate, transfer.From);
		
		AddPaymentEntry(transfer.TransferSum.negate(), transfer.Description, transfer, transfer.Evento, transfer.TransferDate, transfer.To);
		
		
		
	}
	
	public BigDecimal GetBalance(Participant participant, Evento evento) 
	{
		AnalytcsSet set = new AnalytcsSet(participant, evento, null);
		
		return entryMachine.GetBalanceBetweenAccounts(PAYMENT_ACCOUNT, GOODS_ACCOUNT, set);
	}
	
	public ArrayList<String> GetEventoDebtsList(Evento evento)
	{
		ArrayList<String> list = new ArrayList<String>();
		
		for (Participant participant : evento.Participants)
		{
			AnalytcsSet analytics = new AnalytcsSet(participant, evento, null);
			BigDecimal partBalance = entryMachine.GetBalanceBetweenAccounts(PAYMENT_ACCOUNT, GOODS_ACCOUNT, analytics);
			list.add(participant.Name + " : "+partBalance.toString());
		}
		
		return list;
		
	}
	public void Print() {
		entryMachine.Print();
		
	}
	
	
	
	
}
