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

package jhoafparser.util;

import jhoafparser.ast.*;
import jhoafparser.consumer.HOAConsumerException;
import jhoafparser.storage.UniqueTable;

/**
 * Helper for keeping track of implicit edges.
 * <br>
 * In notifyBodyStart(), call:<br>
 *   {@code helper = new ImplicitEdgeHelper(numberOfAPs);}<br><br>
 * In addState(), call:<br>
 *   {@code helper.startOfState(stateId);}<br><br>
 * In addEdgeImplicit(), call:<br>
 *   {@code edgeIndex = helper.nextImplicitEdge();}<br><br>
 * In notifyEndOfState(), call:<br>
 *   {@code helper.endOfState();}<br><br>
 */
public class ImplicitEdgeHelper
{
	/** The number of atomic propositions */
	private int numberOfAPs;
	
	/** The index of the current edge */
	private long edgeIndex = 0;
	
	/** The required number of edges per state */
	private long edgesPerState = 0;

	/** Are we currently in a state definition? */
	private boolean inState = false;
	
	/** The current state index (for error message) */
	private Integer stateId = null;
	
	/** Constructor with number of atomic propositions */
	public ImplicitEdgeHelper(int numberOfAPs)
	{
		this.numberOfAPs = numberOfAPs;
		edgesPerState = 1L << numberOfAPs;
	}

	/** Return the expected number of edges per state, i.e., 2^|AP|. */
	public long getEdgesPerState()
	{
		return edgesPerState;
	}

	/** Notify the ImplicitEdgeHelper that a new state definition has been encountered.
	 * @param stateId the state index (for error message)
	 * @throws UnsupportedOperationException if {@code startOfState} is called before another state is finished with a call to endOfState() 
	 */
	public void startOfState(int stateId) throws UnsupportedOperationException
	{
		if (inState) {
			throw new UnsupportedOperationException("ImplicitEdgeHelper: startOfState("+stateId+") without previous call to endOfState()");
		}

		edgeIndex = 0;
		this.stateId = stateId;
		inState = true;
	}

	/** Notify the ImplicitEdgeHelper that the next implicit edge for the current state has been encountered.
	 * Return the edge index.
	 * @return the index of the current edge
	 * @throws HOAConsumerException if the number of edges is more than 2^numberOfAPs
	 * @throws UnsupportedOperationException if this function is called without a previous call to startOfState()
	 *  */
	public long nextImplicitEdge() throws HOAConsumerException, UnsupportedOperationException
	{
		if (!inState) {
			throw new UnsupportedOperationException("ImplicitEdgeHelper: nextImplicitEdge() without previous call to startOfState()");
		}
		
		long currentEdgeIndex = edgeIndex;
		edgeIndex++;

		if (currentEdgeIndex >= edgesPerState) {
			throw new HOAConsumerException("For state "+stateId+", more edges than allowed for "+numberOfAPs+" atomic propositions");
		}

		return currentEdgeIndex;
	}

	/**
	 * Construct a boolean expression corresponding to the given implicit edge index.
	 * @param implicitIndex the edge index (in range (0, 2^|AP|-1)
	 */
	public BooleanExpression<AtomLabel> toExplicitLabel(long implicitIndex, UniqueTable<BooleanExpression<AtomLabel>> uniqueTable) {
		if (numberOfAPs == 0)
			return uniqueTable.findOrAdd(new BooleanExpression<AtomLabel>(true));

		BooleanExpression<AtomLabel> result = null;

		for (int i=0; i < numberOfAPs; i++) {
			BooleanExpression<AtomLabel> literal = uniqueTable.findOrAdd(new BooleanExpression<AtomLabel>(AtomLabel.createAPIndex(i)));
			if (implicitIndex % 2 == 0) {
				literal = uniqueTable.findOrAdd(literal.not());
			}
			implicitIndex = implicitIndex / 2;
			if (result == null) {
				result = literal;
			} else {
				result = uniqueTable.findOrAdd(result.and(literal));
			}
		}

		return result;
	}

	/** Notify the ImplicitEdgeHelper that the current state definition is finished.
	 * Checks the number of encountered implicit edges against the expected number.
	 * If there are no implicit edges for the current state, that's fine as well. 
	 * @throws HOAConsumerException if there are more then zero and less then 2^numberOfAPs edges
	 * @throws UnsupportedOperationException if endOfState() is called without previous call to startOfState
	 */
	public void endOfState() throws HOAConsumerException
	{
		if (!inState) {
			throw new UnsupportedOperationException("ImplicitEdgeHelper: endOfState() without previous call to startOfState()");
		}

		inState = false;

		if (edgeIndex >0 && edgeIndex != edgesPerState) {
			throw new HOAConsumerException("For state "+stateId+", less edges than required for "+numberOfAPs+" atomic propositions");
		}
	}
}

