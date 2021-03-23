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

package jhoafparser.analysis;

import java.util.ArrayList;
import java.util.List;

import jhoafparser.consumer.HOAConsumerException;
import jhoafparser.storage.StoredAutomaton;
import jhoafparser.storage.StoredAutomatonManipulator;
import jhoafparser.storage.StoredEdgeImplicit;
import jhoafparser.storage.StoredEdgeWithLabel;
import jhoafparser.storage.StoredState;

/** Deduce syntactic properties and add to the stored automaton. */
public class SyntacticProperties implements StoredAutomatonManipulator
{
	@Override
		public StoredAutomaton manipulate(StoredAutomaton aut) throws HOAConsumerException
	{
		aut.getStoredHeader().addProperties(deduceProperties(aut));
		return aut;
	}

	/** Deduce syntactic properties */
	public static List<String> deduceProperties(StoredAutomaton aut) {
		List<String> deducedProperties = new ArrayList<String>();

		boolean state_labels = true;     // hints that the automaton uses only state labels
		boolean trans_labels = true;     // hints that the automaton uses only transition labels
		boolean implicit_labels = true;  // hints that the automaton uses only implicit transitions labels
		boolean explicit_labels = true;  // hints that the automaton uses only explicit transitions labels
		boolean state_acc = true;        // hints that the automaton uses only state-based acceptance specifications
		boolean trans_acc = true;        // hints that the automaton uses only transition-based acceptance specifications
		boolean univ_branch = false;     // hints that the automaton uses universal branching for at least one transition or for the initial state
		                                 // ^ complement of no-univ-branch: hints that the automaton does not use universal branching
		boolean deterministic = true;    // hints that the automaton is deterministic, i.e., it has at most one initial state, and the outgoing transitions of each state have disjoint labels (note that this also applies in the presence of universal branching)
		boolean complete = false;         // hints that the automaton is complete, i.e., it has at least one state, and the transition function is total

		boolean has_labels = false;
		boolean has_acceptance = false;
		boolean has_one_state = (aut.getNumberOfStates() > 0);
		
		if (aut.getStoredHeader().getStartStates().size() > 1) {
			deterministic = false;
		}
		
		for (List<Integer> start : aut.getStoredHeader().getStartStates()) {
			if (start.size() > 1) {
				deterministic = false;
				univ_branch = true;
			}
		}

		for (StoredState s : aut.getStoredStates()) {
			if (s.getLabelExpr() != null) {
				trans_labels = false;
				has_labels = true;
			}

			if (s.getAccSignature() != null) {
				trans_acc = false;
				has_acceptance = true;
			}

			if (aut.hasEdgesImplicit(s.getStateId())) {
				// todo: check completeness
				for (StoredEdgeImplicit edge : aut.getEdgesImplicit(s.getStateId())) {
					has_labels = true;
					state_labels = false;
					explicit_labels = false;

					if (edge.getAccSignature() != null) {
						has_acceptance = true;
						state_acc = false;
					}

					if (edge.getConjSuccessors().size() > 1) {
						univ_branch = true;
					}
				}
			} else if (aut.hasEdgesWithLabel(s.getStateId())) {
				implicit_labels = false;

				for (StoredEdgeWithLabel edge : aut.getEdgesWithLabel(s.getStateId())) {
					if (edge.getLabelExpr() != null) {
						has_labels = true;
						state_labels = false;
						deterministic = false; // we don't know without checking whether the expressions overlap
						complete = false; // we don't know without checking whether the expressions overlap
					}

					if (edge.getAccSignature() != null) {
						has_acceptance = true;
						state_acc = false;
					}

					if (edge.getConjSuccessors().size() > 1) {
						univ_branch = true;
					}
				}
			}
		}

		if (has_one_state) {
			// the properties only make sense if there is at least one state

			if (deterministic) deducedProperties.add("deterministic");
			if (univ_branch)
				deducedProperties.add("univ-branch");
			else
				deducedProperties.add("no-univ-branch");

			if (has_labels) {
				if (state_labels) deducedProperties.add("state-labels");
				if (trans_labels) deducedProperties.add("trans-labels");
				if (implicit_labels) deducedProperties.add("implicit-labels");
				if (explicit_labels) deducedProperties.add("explicit-labels");
				if (complete) deducedProperties.add("complete");
			}

			if (has_acceptance) {
				if (state_acc) deducedProperties.add("state-acc");
				if (trans_acc) deducedProperties.add("trans-acc");
			}
		}

		return deducedProperties;
	}
}
