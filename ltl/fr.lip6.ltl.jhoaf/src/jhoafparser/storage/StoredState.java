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

import jhoafparser.ast.*;

/**
 * A stored state of an HOA automaton
 */
public class StoredState
{
	/** The state id */
	private int stateId;

	/** (optional) information */
	private String info;
	
	/** (optional) a label expression */
	private BooleanExpression<AtomLabel> labelExpr;

	/** (optional) an acceptance signature */
	private List<Integer> accSignature;

	/** Constructor */
	public StoredState(int stateId, String info, BooleanExpression<AtomLabel> labelExpr, List<Integer> accSignature)
	{
		super();
		this.stateId = stateId;
		this.info = info;
		this.labelExpr = labelExpr;
		this.accSignature = accSignature;
	}

	/**
	 * @return the StateId
	 */
	public int getStateId()
	{
		return stateId;
	}

	/**
	 * @return the state info
	 */
	public String getInfo()
	{
		return info;
	}

	/**
	 * @return the label expression
	 */
	public BooleanExpression<AtomLabel> getLabelExpr()
	{
		return labelExpr;
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
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + ((labelExpr == null) ? 0 : labelExpr.hashCode());
		result = prime * result + stateId;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof StoredState))
			return false;
		StoredState other = (StoredState) obj;
		if (accSignature == null) {
			if (other.accSignature != null)
				return false;
		} else if (!accSignature.equals(other.accSignature))
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (labelExpr == null) {
			if (other.labelExpr != null)
				return false;
		} else if (!labelExpr.equals(other.labelExpr))
			return false;
		if (stateId != other.stateId)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return stateId +
				" " +
				(info == null ? "" : info+" ") +
				(labelExpr == null ? "" : labelExpr+" ") +
				(accSignature == null ? "" : accSignature);
	}
	
}
