package org.semki.dutchtreat.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Purchase implements PersistentEntity  {

	
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		
	}

}
