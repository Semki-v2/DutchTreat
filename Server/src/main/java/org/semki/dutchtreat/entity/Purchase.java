package org.semki.dutchtreat.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="purchases")
public class Purchase implements PersistentEntity  {

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@Column(name="description")
	private String description;
	
	@Column(name="amount", scale = 2)
	private BigDecimal amount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "evento_id", nullable = false)
	private Evento evento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id", nullable = false)
	private Participant buyer;
	
	@OneToMany(mappedBy="purchase")
	private List<PurchaseConsumer> consumers = new ArrayList<>();
	
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
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Evento getEvento() {
		return evento;
	}
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	public Participant getBuyer() {
		return buyer;
	}
	public void setBuyer(Participant buyer) {
		this.buyer = buyer;
	}
	public List<PurchaseConsumer> getConsumers() {
		return consumers;
	}
	public void setConsumers(List<PurchaseConsumer> consumers) {
		this.consumers = consumers;
	}

}
