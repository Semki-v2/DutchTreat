package org.semki.dutchtreat.domain.entries;

import java.util.ArrayList;

public class Evento extends Analytic{

		public String Name;
		public ArrayList<Participant> Participants;
		
		public String toString()
		{
			return "Событие "+ Name;
		}

	
}
