package fr.lip6.move.gal.instantiate;

import fr.lip6.move.gal.Variable;

/**
 * A reference to a plain variable, e.g. x.
 * @author Yann
 *
 */
public class SupportVariable implements ISupportVariable {

	private Variable variable;
	
	public SupportVariable(Variable variable) {
		this.variable = variable;
	}

	@Override
	public Variable getVar() {
		return variable;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((variable == null) ? 0 : variable.hashCode());
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
		SupportVariable other = (SupportVariable) obj;
		if (variable == null) {
			if (other.variable != null)
				return false;
		} else if (!variable.equals(other.variable))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return variable.getName();
	}

}
