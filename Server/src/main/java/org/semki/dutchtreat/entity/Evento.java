package org.semki.dutchtreat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
}
