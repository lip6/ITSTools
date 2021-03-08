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

package jhoafparser.consumer;

import java.util.ArrayList;
import java.util.List;

import jhoafparser.ast.*;
import jhoafparser.storage.StoredAutomaton;
import jhoafparser.storage.StoredEdgeImplicit;
import jhoafparser.storage.StoredEdgeWithLabel;
import jhoafparser.storage.StoredState;

/**
 * Abstract helper class that simplifies the handling of states and edges in an HOAIntermediate.
 * This class stores all information from a single sequence of<br>
 *   {@code addState(...)}<br>
 *   {@code addEdgeImplicit(...)}<br>
 *   {@code addEdgeWithLabels(...)}<br>
 *   {@code notifyEndOfState(...)}<br>
 * calls and calls {@code processState(...)}, implemented in the sub-class,
 * with the gathered information in the
 * {@code notifyEndOfState()} method. <br>
 *
 */
public abstract class HOAIntermediateBatchProcessState extends HOAIntermediate
{
	/** The stored state */
	private StoredState state;
	/** The stored implicit edges */
	private List<StoredEdgeImplicit> edgesImplicit;
	/** The stored explicit edges */
	private List<StoredEdgeWithLabel> edgesWithLabel;

	/** Constructor */
	public HOAIntermediateBatchProcessState(HOAConsumer next)
	{
		super(next);
	}

	@Override
	final public void addState(int id, String info, BooleanExpression<AtomLabel> labelExpr, List<Integer> accSignature) {
		// store state
		state = new StoredState(id, info, labelExpr, accSignature);
	}

	@Override
	final public void addEdgeImplicit(int stateId, List<Integer> conjSuccessors, List<Integer> accSignature) throws HOAConsumerException
	{
		// store edge
		if (edgesImplicit == null) edgesImplicit = new ArrayList<StoredEdgeImplicit>();
		if (edgesWithLabel != null) throw new HOAConsumerException("Can not mix implicit and explicit edges (state "+stateId+")");

		edgesImplicit.add(new StoredEdgeImplicit(conjSuccessors, accSignature));
	}

	@Override
	final public void addEdgeWithLabel(int stateId, BooleanExpression<AtomLabel> labelExpr, List<Integer> conjSuccessors, List<Integer> accSignature) throws HOAConsumerException {
		// store edge

		if (edgesWithLabel == null) edgesWithLabel = new ArrayList<StoredEdgeWithLabel>();
		if (edgesImplicit != null) throw new HOAConsumerException("Can not mix implicit and explicit edges (state "+stateId+")");

		edgesWithLabel.add(new StoredEdgeWithLabel(labelExpr, conjSuccessors, accSignature));
	}

	@Override
	public void notifyEndOfState(int stateId) throws HOAConsumerException {
		// call processState in sub-class
		boolean rv = processState(state, edgesImplicit, edgesWithLabel);

		if (rv) {
			// the sub-class wants us to feed the information to the next consumer
			next.addState(state.getStateId(), state.getInfo(), state.getLabelExpr(), state.getAccSignature());

			StoredAutomaton.feedEdgesToConsumer(next, state.getStateId(), edgesImplicit, edgesWithLabel);

			next.notifyEndOfState(state.getStateId());
		}

		// clear stored information
		state = null;
		edgesImplicit = null;
		edgesWithLabel = null;
	}

	/**
	 * Batch-process the given state and edges. This method will be called for each notifyEndOfState.
	 * This method may modify the stored state as well as the edge lists.
	 * <p>
	 * If this method returns {@code true}, the stored state and edge information will be fed into
	 * the next consumer.
	 * <p>
	 * Only one of {@code edgesImplicit} and {@code edgesWithLabel} will be non-{@code null}.
	 * @param state the current state information
	 * @param edgesImplicit the list of implicit edges
	 * @param edgesWithLabel the list of explicit edges
	 * @return Should the stored information be fed to the next consumer?
	 */
	public abstract boolean processState(StoredState state, List<StoredEdgeImplicit> edgesImplicit, List<StoredEdgeWithLabel> edgesWithLabel) throws HOAConsumerException;

}
