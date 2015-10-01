package org.semki.dutchtreat.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "eventos")
public class Evento implements PersistentEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	private String name;
	
//	private Date startDate;
//	
//	private Date finishDate;
	
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
	        name = "account_event_access",
	        joinColumns = @JoinColumn(name = "evento_id"),
	        inverseJoinColumns = @JoinColumn(name = "account_id")
	)
	private Set<Account> accessAccounts = new HashSet<Account>();
	
//	@Column(name="start_date22")
//	public Date getStartDate() {
//		return startDate;
//	}
//	public void setStartDate(Date startDate) {
//		this.startDate = startDate;
//	}
//	
//	@Column(name="finish_date22")
//	public Date getFinishDate() {
//		return finishDate;
//	}
//	public void setFinishDate(Date finishDate) {
//		this.finishDate = finishDate;
//	}
	
	@Override
	public Long getId() {
		return id;
	}
	@Override
	public void setId(Long id) {
		if (id != null) {
			this.id = id;
		}
	}
	public Set<Account> getAccessAccounts() {
		return accessAccounts;
	}
	public void setAccessAccounts(Set<Account> accessAccounts) {
		this.accessAccounts = accessAccounts;
	}
	
}
