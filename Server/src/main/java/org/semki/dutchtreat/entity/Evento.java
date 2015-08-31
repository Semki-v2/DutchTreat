package org.semki.dutchtreat.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

@Entity
@Table(name = "Evento")
public class Evento {
	
	private int Id;
	private String name;
	private Date startDate;
	private Date finishDate;
	
	@javax.persistence.Id
	@GeneratedValue
	@Column(name="id")
	public int getId() {
		return Id;
	}
	public void setId(Integer id) {
		
		if(id!= null)
		{
			this.Id = id;
		}
	}
	
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="startDate")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@Column(name="finishDate")
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	
}
