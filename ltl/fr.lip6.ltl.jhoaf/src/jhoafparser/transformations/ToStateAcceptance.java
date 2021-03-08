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

package jhoafparser.transformations;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import jhoafparser.consumer.HOAConsumerException;
import jhoafparser.storage.StoredAutomaton;
import jhoafparser.storage.StoredAutomatonManipulator;
import jhoafparser.storage.StoredEdgeImplicit;
import jhoafparser.storage.StoredEdgeWithLabel;
import jhoafparser.storage.StoredState;
import jhoafparser.storage.UniqueTable;

/**
 * Convert automaton to state-based acceptance.
 * 
 * This conversion relies on storing the acceptance signatures of the
 * incoming transitions in the states, i.e., duplicating states depending
 * on the acceptance signature that was seen when reaching the state.
 */
public class ToStateAcceptance implements StoredAutomatonManipulator
{
	/** A (state, acceptanceSingature) pair. */
	private static class StateWithAcceptance {
		/** The state id */
		public Integer originalStateId;
		/** The acceptance signature */
		public BitSet acceptanceSignature;

		/** Constructor. */
		public StateWithAcceptance(Integer originalStateId, BitSet acceptanceSignature) {
			this.originalStateId = originalStateId;
			this.acceptanceSignature = acceptanceSignature;
		}

		@Override
		public String toString()
		{
			return originalStateId + " " + acceptanceSignature;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((acceptanceSignature == null) ? 0 : acceptanceSignature.hashCode());
			result = prime * result + ((originalStateId == null) ? 0 : originalStateId.hashCode());
			return result;
		}
		
		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof StateWithAcceptance))
				return false;
			StateWithAcceptance other = (StateWithAcceptance) obj;
			if (acceptanceSignature == null) {
				if (other.acceptanceSignature != null)
					return false;
			} else if (!acceptanceSignature.equals(other.acceptanceSignature))
				return false;
			if (originalStateId == null) {
				if (other.originalStateId != null)
					return false;
			} else if (!originalStateId.equals(other.originalStateId))
				return false;
			return true;
		}
	}

	/**
	 * If true, check if the source automaton is already state-acc and
	 * skip the transformation...
	 */
	private boolean skipIfAlreadyStateBased = true;

	/** The source automaton */
	private StoredAutomaton source;
	/** The target automaton */
	private StoredAutomaton target;

	/** A unique table of bit set based acceptance signatures (for efficient storage) */
	private UniqueTable<BitSet> acceptanceSignatures = new UniqueTable<BitSet>();
	/** Mapping the state+acc tuples to integers in the transformed automaton */
	private HashMap<StateWithAcceptance, Integer> transformedStates = new HashMap<StateWithAcceptance, Integer>();
	/** Storage for the states that have yet to be explored */
	private TreeMap<Integer, StateWithAcceptance> statesForOutput = new TreeMap<Integer, StateWithAcceptance>();

	/** Should we print debug information? */
	private boolean debug = false;

	/** Default constructor. */
	public ToStateAcceptance()
	{
	}

	/** Constructor with source automaton */
	private ToStateAcceptance(StoredAutomaton source) {
		this.source = source;
		target = new StoredAutomaton();
	}

	/** Handle the transformation of the header */
	private void handleHeader() {
		target.setStoredHeader(source.getStoredHeader().clone());
		handleProperties();

		// don't know about misc headers
		target.getStoredHeader().getMiscHeaders().clear();
	}

	/** Handle the transformation of the properties */
	private void handleProperties()
	{
		for (Iterator<String> it = target.getStoredHeader().getProperties().iterator(); it.hasNext();) {
			String property = it.next();
			switch (property) {
			case "trans-acc":
				it.remove();
				break;
			case "state-acc":
				break;

			// transition-structue properties, safe to pass on
			case "state-labels":
			case "trans-labels":
			case "implicit-labels":
			case "explicit-labels":
			case "univ-branch":
			case "no-univ-branch":
			case "deterministic":
			case "complete":

			// language-based properties, safe to pass on
			case "unambiguous":
			case "stutter-invariant":
			case "weak":
			case "very-weak":
			case "inherently-weak":
			case "terminal":
			case "tight":
				break;

			default:
				// unknown property, has to be removed
				it.remove();
			}
		}

		target.getStoredHeader().getProperties().add("state-acc");
	}

	/** Handle the initial states, used as starting point for the later exploration phase */
	private void handleStartStates() {
		target.getStoredHeader().getStartStates().clear();
		for (List<Integer> start : source.getStoredHeader().getStartStates()) {
			List<Integer> transformed = new ArrayList<Integer>();

			for (Integer s : start) {
				// empty acceptance signature for the start states
				transformed.add(handleState(s, new BitSet()));
			}

			target.getStoredHeader().addStartStates(transformed);
		}
	}

	/**
	 * Transform the transition structure.
	 **/
	private void transformTransitionStructure() throws HOAConsumerException {
		// Do DFS of the reachable fragment...
		while (!statesForOutput.isEmpty()) {
			Entry<Integer, StateWithAcceptance> e = statesForOutput.firstEntry();
			statesForOutput.remove(e.getKey());

			Integer stateId = e.getKey();
			StateWithAcceptance sTransformed = e.getValue();

			StoredState s = source.getStoredState(sTransformed.originalStateId);
			String info = null;
			if (debug) {
				info = sTransformed.toString();
			} else {
				info = s.getInfo();
			}
			target.addState(new StoredState(stateId, info, s.getLabelExpr(), bitSetToAccSignature(sTransformed.acceptanceSignature)));

			List<Integer> accSignatureState = source.getStoredState(sTransformed.originalStateId).getAccSignature();

			if (source.hasEdgesImplicit(sTransformed.originalStateId)) {
				if (source.hasEdgesWithLabel(sTransformed.originalStateId)) {
					throw new HOAConsumerException("Mixed explicit and implicit edges");
				}

				for (StoredEdgeImplicit edge : source.getEdgesImplicit(sTransformed.originalStateId)) {
					List<Integer> conjSuccessors = transformSuccessors(edge.getConjSuccessors(), accSignatureState, edge.getAccSignature());
					target.addEdgeImplicit(stateId, new StoredEdgeImplicit(conjSuccessors, null));
				}
			} else if (source.hasEdgesWithLabel(sTransformed.originalStateId)) {
				for (StoredEdgeWithLabel edge : source.getEdgesWithLabel(sTransformed.originalStateId)) {
					List<Integer> conjSuccessors = transformSuccessors(edge.getConjSuccessors(), accSignatureState, edge.getAccSignature());
					target.addEdgeWithLabel(stateId, new StoredEdgeWithLabel(edge.getLabelExpr(), conjSuccessors, null));
				}
			} else {
				throw new UnsupportedOperationException("State without outgoing edges");
			}
		}
	}

	/**
	 * Transform the successors, using the acceptance signatures from the state and the edges
	 **/
	private List<Integer> transformSuccessors(List<Integer> conjSuccessors, List<Integer> accSignatureState, List<Integer> accSignatureEdge) throws HOAConsumerException {
		List<Integer> accSignature;

		if (accSignatureState != null && accSignatureEdge != null) {
			accSignature = new ArrayList<Integer>(accSignatureState);
			accSignature.addAll(accSignatureEdge);
		} else {
			accSignature = accSignatureEdge != null ? accSignatureEdge : accSignatureState;
		}

		BitSet acceptanceSignature = accSignatureToBitSet(accSignature);

		List<Integer> transformed = new ArrayList<Integer>(conjSuccessors.size());
		for (Integer successor : conjSuccessors) {
			transformed.add(handleState(successor, acceptanceSignature));
		}

		return transformed;
	}

	/**
	 * Try to find state with given stateID and acceptance signature. If it does not
	 * already exists, create and mark for later exploration
	 **/
	private Integer handleState(Integer stateId, BitSet accSignature) {
		StateWithAcceptance sTransformed = new StateWithAcceptance(stateId, accSignature);
		Integer id = transformedStates.get(sTransformed);
		if (id == null) {
			id = transformedStates.size();
			transformedStates.put(sTransformed, id);
			statesForOutput.put(id, sTransformed);
		}

		return id;
	}

	/** Acceptance signature: Translate from integer list representation representation to BitSet */
	private BitSet accSignatureToBitSet(List<Integer> accSignature) {
		BitSet result = new BitSet();
		if (accSignature != null) {
			for (Integer i : accSignature) {
				result.set(i);
			}
		}
		return acceptanceSignatures.findOrAdd(result);
	}

	/** Acceptance signature: Translate from BitSet representation to integer list representation */
	private List<Integer> bitSetToAccSignature(BitSet accSignature) {
		List<Integer> list = new ArrayList<Integer>();
		for (Integer i = accSignature.nextSetBit(0); i>=0; i = accSignature.nextSetBit(i+1)) {
			list.add(i);
		}
		return list;
	}

	/** Checks whether the source automaton has any transition-based acceptance */
	private boolean hasTransitionAcceptance() {
		int numStates = source.getNumberOfStates();
		for (int state = 0; state <= numStates; state++) {
			if (source.hasEdgesImplicit(state)) {
				for (StoredEdgeImplicit edge : source.getEdgesImplicit(state)) {
					if (edge.getAccSignature() != null && !edge.getAccSignature().isEmpty()) {
						return true;
					}
				}
			}
			if (source.hasEdgesWithLabel(state)) {
				for (StoredEdgeWithLabel edge : source.getEdgesWithLabel(state)) {
					if (edge.getAccSignature() != null && !edge.getAccSignature().isEmpty()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/** Transform the automaton, return the result. */
	private StoredAutomaton transform() throws HOAConsumerException {
		if (skipIfAlreadyStateBased && !hasTransitionAcceptance()) {
			source.getStoredHeader().getProperties().remove("trans-acc");
			source.getStoredHeader().getProperties().add("state-acc");
			return source;
		}

		handleHeader();
		handleStartStates();

		transformTransitionStructure();
		target.getStoredHeader().setNumberOfStates(target.getNumberOfStates());

		return target;
	}

	@Override
	public StoredAutomaton manipulate(StoredAutomaton aut) throws HOAConsumerException
	{
		ToStateAcceptance transform = new ToStateAcceptance(aut);
		return transform.transform();
	}
}
