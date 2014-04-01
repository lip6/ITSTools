package fr.lip6.move.gal.instantiate;

import fr.lip6.move.gal.ArrayPrefix;

public class SupportConstArrayAccess implements ISupportVariable {

	private int value;
	private ArrayPrefix prefix;

	public SupportConstArrayAccess(ArrayPrefix prefix, int value) {
		this.prefix = prefix;
		this.value = value;
	}

	@Override
	public ArrayPrefix getVar() {
		return prefix;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SupportConstArrayAccess other = (SupportConstArrayAccess) obj;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return prefix.getName() + "["+value+"]";
	}

}
