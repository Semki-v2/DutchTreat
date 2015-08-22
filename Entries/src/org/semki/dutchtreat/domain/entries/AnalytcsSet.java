package org.semki.dutchtreat.domain.entries;

public class AnalytcsSet {
	
	public Participant Participant;
	public Evento Evento;
	public Source Source;
	
	
	public AnalytcsSet(Participant participant, Evento evento2, Source source) {
		super();
		Participant = participant;
		Evento = evento2;
		Source = source;
		
	}


	public String toString()
	{
		String result = "";
		if (Participant!= null)
			result = result + Participant.toString();
		if(Evento!= null)
			result += Evento.toString();
		if(Source != null)
			result += Source.toString();
		
		
		return result;
		
	}








	public boolean Contains(AnalytcsSet filterAnalytics) {
		if(filterAnalytics.Participant != null)
		{
			/*if (!filterAnalytics.Participant.equals(Participant))
			{
				return false;
			}*/
			if(filterAnalytics.Participant.Name != Participant.Name)
				return false;
			
		}
		
		if(filterAnalytics.Evento != null)
		{
		/*
			if (!filterAnalytics.Evento.equals(Evento))
			{
				return false;
			}*/
			if(filterAnalytics.Evento.Name != Evento.Name)
			{
				
				return false;
			}
			
		}
		
		if(filterAnalytics.Source != null)
		{
			//System.out.println("filterAnalytics.Source != null");
			
			/*if (!filterAnalytics.Source.equals(Source))
			{
				return false;
			}*/
			
		}
		return true;
	}
	

}
