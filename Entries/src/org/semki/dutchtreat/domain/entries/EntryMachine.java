package org.semki.dutchtreat.domain.entries;

import java.math.BigDecimal;
import java.util.ArrayList;

public class EntryMachine {
	private ArrayList<Account> Accounts = new ArrayList<Account>();
	

	
	private Account GetAccountByName(String name)
	{
		for(Account acc : Accounts )
		{
			if(acc.Name == name)
			{
				return acc;
			}
		}
		return null;
		
	}
	
	private Account GetOrCreateAccountByName(String name)
	{
		Account acc = GetAccountByName(name);
		if(acc == null)
		{
			acc = Account.Create(name);
			Accounts.add(acc);
		}
		return acc;
	}
	

	/// configuration
	
	public void AddAccountByName(String name)
	{
		GetOrCreateAccountByName(name);
		
	}
	
	/// fill
	public void AddEntry(String accountName, BigDecimal sum, AnalytcsSet analytics, String description)
	{
		Account acc = GetOrCreateAccountByName(accountName);
		Entry entry = new Entry(acc, sum, analytics, description);
		acc.AddEntry(entry);
	}
	/// report
	public BigDecimal GetBalanceBetweenAccounts(String firstAccountName, String secondAccountName, AnalytcsSet analytics)
	{
		///System.out.println("подсчет баланса для фильтра "+analytics.toString());
		
		Account firstAccount = GetAccountByName(firstAccountName);
		Account secondAccount = GetAccountByName(secondAccountName);
		
		BigDecimal firstSum = firstAccount.GetSum(analytics);
		BigDecimal secondSum = secondAccount.GetSum(analytics);
		
		//System.out.println("first is " + firstAccount+ "second "+ secondAccount);
		
		//System.out.println("first sum is "+firstSum+ "second sum "+ secondSum);
		
		return firstSum.subtract(secondSum);
	}

	public void Print() {
		// TODO Auto-generated method stub
		for (Account acc : Accounts)
		{
			acc.PrintEntries();
		}
		
	}

}
