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

package jhoafparser.storage;

import java.util.List;

/**
 * An stored edge of a HOA automaton with implicit label
 */
public class StoredEdgeImplicit
{
	/** The list of successors */
	private List<Integer> conjSuccessors;
	/** The acceptance signature */
	private List<Integer> accSignature;

	/** Constructor. accSignature may be {@code null} */
	public StoredEdgeImplicit(List<Integer> conjSuccessors, List<Integer> accSignature)
	{
		super();
		this.conjSuccessors = conjSuccessors;
		this.accSignature = accSignature;
	}

	/**
	 * @return the successors
	 */
	public List<Integer> getConjSuccessors()
	{
		return conjSuccessors;
	}

	/**
	 * @return the acceptance signature
	 */
	public List<Integer> getAccSignature()
	{
		return accSignature;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accSignature == null) ? 0 : accSignature.hashCode());
		result = prime * result + ((conjSuccessors == null) ? 0 : conjSuccessors.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof StoredEdgeImplicit))
			return false;
		StoredEdgeImplicit other = (StoredEdgeImplicit) obj;
		if (accSignature == null) {
			if (other.accSignature != null)
				return false;
		} else if (!accSignature.equals(other.accSignature))
			return false;
		if (conjSuccessors == null) {
			if (other.conjSuccessors != null)
				return false;
		} else if (!conjSuccessors.equals(other.conjSuccessors))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return (conjSuccessors == null ? "null" : conjSuccessors) +
				" " +
				(accSignature == null ? "null" : accSignature);
	}
}
