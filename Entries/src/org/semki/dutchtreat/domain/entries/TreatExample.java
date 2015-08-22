package org.semki.dutchtreat.domain.entries;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class TreatExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TreatExample a = new TreatExample();
		a.test1();
	}
	
	private DutchTreatMachine test1()
	{
		DutchTreatMachine dm = DutchTreatMachine.Prepare();
		
		Participant dk = new Participant("DK");
		Participant vova = new Participant("Vova");
		Participant andrey = new Participant("Andrey");
		Participant denis = new Participant("Денис");
		Participant TT = new Participant("TT");
		
		Evento buhaton = new Evento();
		buhaton.Name = "BUHATTTON!!!";
		buhaton.Participants = new ArrayList<Participant>();
		buhaton.Participants.add(denis);
		buhaton.Participants.add(dk);
		buhaton.Participants.add(vova);
		buhaton.Participants.add(andrey);
		buhaton.Participants.add(TT);
		
		ArrayList<Participant> meatconsumers = new ArrayList<Participant>();
		meatconsumers.add(denis);
		meatconsumers.add(andrey);
		meatconsumers.add(vova);
		meatconsumers.add(dk);
		
		Purchase meat = new Purchase(denis, meatconsumers, new BigDecimal(4000), new Date(), "шашлычное мясо!!", buhaton);
		
		Purchase tekila = new Purchase(vova, buhaton.Participants, new BigDecimal(3000), new Date(), "tekilazzz!!", buhaton);
		
		Purchase vegpizza = new Purchase(TT, buhaton.Participants, new BigDecimal(1200), new Date(), "tekilazzz!!", buhaton);
		
		dm.AddPurchase(meat);
		dm.AddPurchase(tekila);
		dm.AddPurchase(vegpizza);
		
		ArrayList<String> list = dm.GetEventoDebtsList(buhaton);
		for (String s : list)
		{
			System.out.println(s);
		}
		
		dm.Print();
		
		Transfer tr1 = new Transfer(dk, denis, new BigDecimal(1000), buhaton, "от дк");
		dm.AddTransfer(tr1);
		dm.AddTransfer(new Transfer(dk, vova, new BigDecimal(1000), buhaton, "от дк 2"));
		
		dm.AddTransfer(new Transfer(andrey, denis, new BigDecimal(1160), buhaton, "от андрея "));
		dm.AddTransfer(new Transfer(andrey, TT, new BigDecimal(360), buhaton, "от андрея "));

		dm.AddTransfer(new Transfer(andrey, dk, new BigDecimal(160), buhaton, "от андрея "));
		dm.AddTransfer(new Transfer(andrey, vova, new BigDecimal(160), buhaton, "от андрея "));
		
		ArrayList<String> l2 = dm.GetEventoDebtsList(buhaton);
		for (String s : l2)
		{
			System.out.println(s);
		}
		
		//dm.Print();
		return dm;
		
	}

}
