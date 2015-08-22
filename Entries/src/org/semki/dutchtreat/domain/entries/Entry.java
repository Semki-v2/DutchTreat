package org.semki.dutchtreat.domain.entries;

import java.math.BigDecimal;

public class Entry {

		public Account Account;
		public BigDecimal Sum;
		public AnalytcsSet Analytics;
		public String description;
		

		public Entry(Account account, BigDecimal sum, AnalytcsSet analytics, String description)
		{
			this.Account = account;
			this.Sum = sum;
			this.Analytics = analytics;
			this.description = description;
		}
		
		public String toString()
		{
			return "Entry "+description+" sum :"+Sum.toString()+" счет "+Account.toString()+ " analytics" + Analytics.toString();
		}
		
	
}
