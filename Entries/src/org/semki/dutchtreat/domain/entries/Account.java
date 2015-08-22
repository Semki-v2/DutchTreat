package org.semki.dutchtreat.domain.entries;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Account {


	public String Name;
	
	ArrayList<Entry> entries = new ArrayList<Entry>();
	
	
	
	public Account(String name) {
		this.Name = name;
	}
	public void AddEntry(Entry entry)
	{
		//System.out.println("added entry "+entry.toString());
		entries.add(entry);
	}
	public BigDecimal GetSum(AnalytcsSet filterAnalytics)
	{
		BigDecimal total = new BigDecimal(0);
		ArrayList<Entry> entriesForCalcSum = GetFilteredEntries(filterAnalytics);
		for (Entry entry : entriesForCalcSum)
		{
			
			total = total.add(entry.Sum);
			//System.out.println("entry sum"+entry.Sum+ "total "+ total.toString());
		}
		
		//System.out.println("filtered count " +entriesForCalcSum.size() +"Сумма "+total+" для "+filterAnalytics+" для счета "+toString());
		return total;
		
	}
	
	public ArrayList<Entry> GetFilteredEntries(AnalytcsSet filterAnalytics)
	{
		
		ArrayList<Entry> filteredEntries = new ArrayList<Entry>();
		for(Entry entry : entries)
		{	
			//System.out.println("entry analytis "+ entry.Analytics.toString());
			
			if (entry.Analytics.Contains(filterAnalytics))
			{
				filteredEntries.add(entry);
				//System.out.println("matched" + entry.toString());
			}
			
		}
		return filteredEntries;
	}
	public static Account Create(String name) {
		return new Account(name);
	}
	
	
	@Override
	public String toString() {
		return "Account [Name=" + Name + "]";
	}

	public void PrintEntries()
	{
		for (Entry entry : entries)
		{
			System.out.println(entry.toString());
		}
	}
}
