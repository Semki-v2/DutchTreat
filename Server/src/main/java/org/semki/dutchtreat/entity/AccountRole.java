package org.semki.dutchtreat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="account_role")
public class AccountRole implements PersistentEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "acc_role_id", nullable = false)
	private AccRole accRole;
	
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
	
	public Account getUser() {
		return account;
	}
	public void setUser(Account user) {
		this.account = user;
	}
	public AccRole getRole() {
		return accRole;
	}
	public void setRole(AccRole role) {
		this.accRole = role;
	}
	
}
