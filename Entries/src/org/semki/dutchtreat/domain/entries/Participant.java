package org.semki.dutchtreat.domain.entries;

public class Participant extends Analytic {

	public String Name;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((Name == null) ? 0 : Name.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Participant other = (Participant) obj;
		if (Name == null) {
			if (other.Name != null)
				return false;
		} else if (Name != other.Name)
			return false;
		return true;
	}


	public Participant(String name) {
		super();
		Name = name;
	}
	

	public String toString()
	{
		return " Участник "+ Name;
	}


}
