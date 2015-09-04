package org.semki.dutchtreat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="purchase_consumer")
public class PurchaseConsumer implements PersistentEntity {
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "purchase_id", nullable = false)
	private Purchase purchase;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "consumer_id", nullable = false)
	private Participant consumer;
	
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
	public Purchase getPurchase() {
		return purchase;
	}
	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}
	public Participant getConsumer() {
		return consumer;
	}
	public void setConsumer(Participant consumer) {
		this.consumer = consumer;
	}
}
