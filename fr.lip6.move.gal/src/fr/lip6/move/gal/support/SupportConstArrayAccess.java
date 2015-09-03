package fr.lip6.move.gal.support;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.IntExpression;

/**
 * A reference to a particular cell of an array, e.g. tab[3].
 * @author Yann
 *
 */
public class SupportConstArrayAccess implements ISupportVariable {

	private int index;
	private ArrayPrefix prefix;

	public SupportConstArrayAccess(ArrayPrefix prefix, int value) {
		this.prefix = prefix;
		this.index = value;
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
		result = prime * result + index;
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
		if (index != other.index)
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return prefix.getName() + "["+index+"]";
	}

	@Override
	public IntExpression getInitialValue() {
		return prefix.getValues().get(index);
	}

}
