//==============================================================================
//	
//	Copyright (c) 2014-
//	Authors:
//	* Joachim Klein <klein@tcs.inf.tu-dresden.de>
//	* David Mueller <david.mueller@tcs.inf.tu-dresden.de>
//	
//------------------------------------------------------------------------------
//	
//	This file is part of the jhoafparser library, http://automata.tools/hoa/jhoafparser/
//
//	The jhoafparser library is free software; you can redistribute it and/or
//	modify it under the terms of the GNU Lesser General Public
//	License as published by the Free Software Foundation; either
//	version 2.1 of the License, or (at your option) any later version.
//	
//	The jhoafparser library is distributed in the hope that it will be useful,
//	but WITHOUT ANY WARRANTY; without even the implied warranty of
//	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//	Lesser General Public License for more details.
//	
//	You should have received a copy of the GNU Lesser General Public
//	License along with this library; if not, write to the Free Software
//	Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
//	
//==============================================================================

package jhoafparser.ast;

/** 
 * Atom of an acceptance condition, either Fin(accSet), Fin(!accSet), Inf(accSet) or Inf(!accSet)
 */
public class AtomAcceptance implements Atom {
	/** The acceptance condition type of this atom */
	public enum Type {
		/** Fin(.) atom */
		TEMPORAL_FIN,
		/** Inf(.) atom */
		TEMPORAL_INF;
	
		/** Returns the dual of the given acceptance condition type */
		Type dual() {
			return (this == TEMPORAL_FIN) ? TEMPORAL_INF : TEMPORAL_FIN;
		}
	};

	/** The type of this atom (Inf/Fin) */
	private Type type;
	/** The index of the acceptance set for this atom */
	private Integer accSet = null;
	/** True if the acceptance set is negated inside the Inf/Fin operator */
	private boolean negated = false;

	/**
	 * Constructor for an acceptance condition atom.
	 * 
	 * @param type the type (TEMPORAL_FIN, TEMPORAL_INF)
	 * @param accSet the acceptance set index 
	 * @param negated is the acceptance set negated, e.g., Fin(!2)?
	 * */
	public AtomAcceptance(Type type, Integer accSet, boolean negated) {
		this.type = type;
		this.accSet = accSet;
		this.negated = negated;
	}
	
	/** Static constructor for a Fin(accSet) atom */
	public static AtomAcceptance Fin(int accSet) {
		return new AtomAcceptance(Type.TEMPORAL_FIN, accSet, false);
	}

	/** Static constructor for a Fin(!accSet) atom */
	public static AtomAcceptance FinNot(int accSet) {
		return new AtomAcceptance(Type.TEMPORAL_FIN, accSet, true);
	}

	/** Static constructor for an Inf(accSet) atom */
	public static AtomAcceptance Inf(int accSet) {
		return new AtomAcceptance(Type.TEMPORAL_INF, accSet, false);
	}
	
	/** Static constructor for an Inf(!accSet) atom */
	public static AtomAcceptance InfNot(int accSet) {
		return new AtomAcceptance(Type.TEMPORAL_INF, accSet, true);
	}
	
	/** Returns a copy of this atom. */
	public Atom clone()
	{
		return new AtomAcceptance(type, accSet, negated);
	}

	/** Returns the type of this atom. */
	public Type getType()
	{
		return type;
	}
	
	/** Returns the acceptance set index of this atom. */
	public int getAcceptanceSet() {
		return accSet;
	}

	/** Returns true if the acceptance set is negated for this atom. */
	public boolean isNegated() {
		return negated;
	}

	/** Returns the dual (negation) for this atom. */
	public AtomAcceptance dual() {
		return new AtomAcceptance(type.dual(), accSet, !negated);
	}

	@Override
	public String toString() {
		return  (type == Type.TEMPORAL_FIN ? "Fin" : "Inf")+"("+(negated ? "!" : "") +accSet+")";
	}


	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accSet == null) ? 0 : accSet.hashCode());
		result = prime * result + (negated ? 1231 : 1237);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AtomAcceptance))
			return false;
		AtomAcceptance other = (AtomAcceptance) obj;
		if (type != other.type)
			return false;
		if (accSet == null) {
			if (other.accSet != null)
				return false;
		} else if (!accSet.equals(other.accSet))
			return false;
		if (negated != other.negated)
			return false;
		return true;
	}
}
