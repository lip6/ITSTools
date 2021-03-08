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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import jhoafparser.analysis.AcceptanceSimplify;
import jhoafparser.ast.*;
import jhoafparser.consumer.HOAConsumerException;
import jhoafparser.storage.StoredAutomaton;
import jhoafparser.storage.StoredAutomatonManipulator;
import jhoafparser.storage.StoredEdgeWithLabel;
import jhoafparser.storage.StoredHeader;
import jhoafparser.storage.StoredHeader.NameAndExtra;
import jhoafparser.storage.StoredState;
import jhoafparser.util.AcceptanceRepositoryStandard;

/**
 * Convert (nondeterministic) automaton to generalized-Buchi acceptance.
 * <p>
 * This transformation applies on the syntactical level and can
 * be therefore quite inefficient, as no additional knowledge
 * about the structure of the automaton is taken into account.
 * <p>
 * The acceptance condition is converted to DNF and,
 * for each conjunctive clause, a copy of the automaton is
 * constructed that enforces the acceptance atoms by
 * removing any Fin-states and accepting for the Inf-states.
 * An initial copy of the automaton non-deterministically guesses
 * the moment from which on a particular clause is satisfied,
 * jumping to the corresponding copy.
 **/
public class ToNondetBuchi implements StoredAutomatonManipulator
{
	/** The source automaton */
	private StoredAutomaton source;
	/** The target automaton */
	private StoredAutomaton target;
	/** The acceptance condition in DNF */
	private List<BooleanExpression<AtomAcceptance>> accDNF;
	/** The number of copies necessary */
	private int copies;
	/** The number of states in the source automaton */
	private int numberOfStates;
	/** The number of acceptance sets needed in the target automaton s*/
	private int numberOfBuchi;
	/** The acceptance condition of the source automaton */
	private BooleanExpression<AtomAcceptance> accExpr;

	/** Default constructor */
	public ToNondetBuchi() {
	}

	/** Constructor, apply the transformation to the given stored automaton. */
	private ToNondetBuchi(StoredAutomaton aut) throws HOAConsumerException {
		source = aut;
		
		if (source.hasUniversalBranching()) {
			throw new HOAConsumerException("Automaton has branching, can only convert (non-)deterministic automata to non-deterministic Buchi");
		}
		
		if (source.hasEdgesImplicit()) {
			// TODO: convert to explicit edges
			throw new HOAConsumerException("Converting to Buchi currently not supported for automata with implicit edges");
		}
		
		acceptanceToDNF();
		calcNumberOfBuchi();
		
		numberOfStates = Math.max(source.getNumberOfStates(), source.getHighestStateIndex() + 1);

		target = new StoredAutomaton();
		StoredHeader header = source.getStoredHeader().clone();
		target.setStoredHeader(header);
		header.setNumberOfStates(numberOfStates*(copies+1));
		header.getAcceptanceNames().clear();
		header.provideAcceptanceName("generalized-Buchi", Collections.singletonList((Object)numberOfBuchi));
		header.setAcceptanceCondition(numberOfBuchi, AcceptanceRepositoryStandard.forGenBuchi(Collections.singletonList((Object)numberOfBuchi)));

		handleProperties();

		handleOriginal();
		for (int copy = 0; copy < copies; copy++) {
			handleCopy(copy);
		}
	}

	/** Calculate the number of acceptance sets that are necessary in the copies */
	private void calcNumberOfBuchi() {
		numberOfBuchi = 0;
		for (BooleanExpression<AtomAcceptance> clause : accDNF) {
			numberOfBuchi = Math.max(numberOfBuchi, countInf(clause));
		}
		if (numberOfBuchi == 0) {
			// no Inf, three cases:
			if (accExpr.isTRUE()) {
				numberOfBuchi = 1;
			} else if (accExpr.isFALSE()) {
				copies = 0;
			} else {
				// There are only Fin() atoms -> generate 1 set
				numberOfBuchi = 1;
			}
		}
	}

	/** Count the number of Inf atoms in the acceptance condition */
	private int countInf(BooleanExpression<AtomAcceptance> acc) {
		switch (acc.getType()) {
		case EXP_AND:
		case EXP_OR:			
			return countInf(acc.getLeft()) + countInf(acc.getRight());
		case EXP_ATOM:
			switch (acc.getAtom().getType()) {
			case TEMPORAL_FIN:
				return 0;
			case TEMPORAL_INF:
				return 1;
			}
			break;
		case EXP_FALSE:
		case EXP_TRUE:
			return 0;
		default:
			break;
		}
		throw new UnsupportedOperationException("Unsupported operator in acceptance condition: "+acc);
	}

	/** Return the index in the target automaton corresponding to original state {@code t} in the initial copy */
	private Integer stateInOriginal(int t) {
		return t;
	}

	/** Return the index in the target automaton corresponding to original state {@code t} in copy {@code copy} */
	private Integer stateInCopy(int t, int copy) {
		return t + (copy+1)*numberOfStates;
	}

	/** Returns a singleton list consisting of state {@code t}*/
	private List<Integer> gotoState(Integer t) {
		return Collections.singletonList(t);
	}

	/**
	 * Checks whether the {@accSignature} is consistent with
	 * the given acceptance condition clause.
	 */
	private boolean consistentWithAcceptance(List<Integer> accSignature, BooleanExpression<AtomAcceptance> clause) {
		switch (clause.getType()) {
		case EXP_AND:
			return consistentWithAcceptance(accSignature, clause.getLeft()) && consistentWithAcceptance(accSignature, clause.getRight());
		case EXP_TRUE:
			return true;
		case EXP_FALSE:
			return false;
		case EXP_ATOM:
			if (clause.getAtom().getType() == AtomAcceptance.Type.TEMPORAL_FIN) {
				int index = clause.getAtom().getAcceptanceSet();

				if (clause.getAtom().isNegated()) {
					// Fin(!index) => 
					//  consistent if index does appear
					return accSignature.contains(index);
				} else {
					// Fin(index) => 
					//  consistent if index does not appear
					return !accSignature.contains(index);
				}
			} else {
				return true;
			}
		case EXP_OR:
			throw new UnsupportedOperationException("Acceptance condition has to be in conjunctive form:" + clause);
		default:
			break;
		}
		throw new UnsupportedOperationException("Unsupported operator in acceptance condition:" + clause);
	}

	/**
	 * Translate an acceptance signature from the source automaton
	 * to an acceptance signature in the target automaton,
	 * where the last {@code unused} acceptance sets in the target automaton
	 * are not used for this clause.
	 */
	private List<Integer> translateAcceptance(List<Integer> acceptanceSignature, BooleanExpression<AtomAcceptance> acc, int unused) {
		List<Integer> result = new ArrayList<Integer>();
		translateAcceptance(acceptanceSignature, acc, 0, result);

		// fill unused Inf
		for (int i=numberOfBuchi-unused; i<numberOfBuchi; i++) {
			result.add(i);
		}
		return result;
	}

	/**
	 * Translate an acceptance signature from the source automaton
	 * to an acceptance signature in the target automaton,
	 * providing the {@code current} smallest unused acceptance index. 
	 */
	private int translateAcceptance(List<Integer> acceptanceSignature, BooleanExpression<AtomAcceptance> acc, int current, List<Integer> transformedSignature) {
		switch (acc.getType()) {
		case EXP_AND:
			current = translateAcceptance(acceptanceSignature, acc.getLeft(), current, transformedSignature);
			current = translateAcceptance(acceptanceSignature, acc.getRight(), current, transformedSignature);
			return current;
		case EXP_ATOM:
			if (acc.getAtom().getType() == AtomAcceptance.Type.TEMPORAL_INF) {
				boolean negated = acc.getAtom().isNegated();
				if (acceptanceSignature.contains(acc.getAtom().getAcceptanceSet()) ^ negated) {
					// this is good, add to acceptance
					transformedSignature.add(current);
				}
				return current+1;
			} else {
				// nothing to do
				return current;
			}
		case EXP_FALSE:
		case EXP_TRUE:
		default:
			break;
		
		}
		throw new UnsupportedOperationException("Unsupported operator in acceptance condition:" + acc);
	}

	/** Do the transformation concerning the initial copy */
	private void handleOriginal() {
		for (int s = 0; s < numberOfStates; s++) {
			StoredState original = source.getStoredState(s);
			String name = Integer.toString(s);
			if (original == null) {
				// terminal state
				target.addState(new StoredState(s, name, null, null));
			} else {
				// original state, but no acceptance
				target.addState(new StoredState(s, name, original.getLabelExpr(), null));
			}

			// handle edges
			if (source.hasEdgesWithLabel(s)) {
				for (StoredEdgeWithLabel edge : source.getEdgesWithLabel(s)) {
					assert(edge.getConjSuccessors().size() == 1);   // ensured by check !isUniversalBranching above
					int t = edge.getConjSuccessors().get(0);

					// edge into original, no acceptance
					target.addEdgeWithLabel(s, new StoredEdgeWithLabel(edge.getLabelExpr(), gotoState(stateInOriginal(t)), null));
					
					for (int copy = 0; copy < copies; copy++) {
						// edge into copy, no acceptance
						target.addEdgeWithLabel(s, new StoredEdgeWithLabel(edge.getLabelExpr(), gotoState(stateInCopy(t, copy)), null));
					}
				}
			}
		}
	}

	/** Do the transformation concerning copy/clause {@copy} */
	 private void handleCopy(int copy) throws HOAConsumerException {
		BooleanExpression<AtomAcceptance> acc = accDNF.get(copy);
		int unusedBuchi = numberOfBuchi - countInf(acc);
		assert(unusedBuchi >= 0);

		for (int s = 0; s < numberOfStates; s++) {
			StoredState original = source.getStoredState(s);
			String name = s+"_"+copy;

			// the state index in the copy
			Integer s_ = stateInCopy(s, copy);
			
			if (original == null) {
				original = new StoredState(s_, name, null, null);
			}

			boolean makeTerminal = false;
			if (original.getAccSignature() != null) {
				if (!consistentWithAcceptance(original.getAccSignature(), acc)) {
					makeTerminal = true;
				}

				List<Integer> transformedAcceptance = translateAcceptance(original.getAccSignature(), acc, unusedBuchi);
				target.addState(new StoredState(s_, name, original.getLabelExpr(), transformedAcceptance));
			} else {
				// no acceptance signature: keep it that way, acceptance is handled via the edges
				target.addState(new StoredState(s_, name, original.getLabelExpr(), null));
			}
			
			if (makeTerminal) {
				continue;
			}

			// handle edges
			if (source.hasEdgesWithLabel(s)) {
				for (StoredEdgeWithLabel edge : source.getEdgesWithLabel(s)) {
					assert(edge.getConjSuccessors().size() == 1);   // ensured by check !isUniversalBranching above
					int t = edge.getConjSuccessors().get(0);

					if (original.getAccSignature() != null) {
						// the state has acceptance signature, so we don't need to deal with acceptance here
						if (edge.getAccSignature() != null) {
							throw new HOAConsumerException("Mixing state and edge acceptance signatures not supported");
						}
						// edge into same copy, no acceptance
						target.addEdgeWithLabel(s_, new StoredEdgeWithLabel(edge.getLabelExpr(), gotoState(stateInCopy(t, copy)), null));
						continue;
					}

					List<Integer> edgeAccSig = (edge.getAccSignature() != null ? edge.getAccSignature() : new ArrayList<Integer>(0));
					if (!consistentWithAcceptance(edgeAccSig, acc)) {
						// drop this edge
						continue;
					}

					List<Integer> transformedAcceptance = translateAcceptance(edgeAccSig, acc, unusedBuchi);
					// edge into same copy, transformed acceptance
					target.addEdgeWithLabel(s_, new StoredEdgeWithLabel(edge.getLabelExpr(), gotoState(stateInCopy(t, copy)), transformedAcceptance));
				}
			}
		}
	}

	/**
	 * Handle properties that are preserved under the transformation.
	 */
	private void handleProperties() throws HOAConsumerException {
		Iterator<String> it = target.getStoredHeader().getProperties().iterator();
		while (it.hasNext()) {
			String property = it.next();
			switch (property) {
			case "state-labels":
				// preserved
				break;
			case "trans-labels":
				// preserved
				break;
			case "implicit-labels":
				// remove, as the automaton is converted to explicit-labels
				it.remove();
				break;
			case "explicit-labels":
				// preserved
				break;
			case "state-acc":
				// preserved
				break;
			case "trans-acc":
				// preserved
				break;
			case "univ-branch":
				throw new HOAConsumerException("Automaton is marked as having universal branching, but conversion to non-det Buchi is not supported");
			case "no-univ-branch":
				// preserved
				break;
			case "deterministic":
				// not preserved, we introduce non-determinism
				it.remove();
				break;
			case "complete":
				// not preserved, we may introduce terminal states
				it.remove();
				break;
			case "unambiguous":
				// not preserved, we may introduce ambiguity
				it.remove();
				break;
			case "stutter-invariant":
				// preserved
				break;

			
			case "weak":
			case "very-weak":
			case "inherently-weak":
			case "terminal":
			case "tight":
				// probably not preserved, remove
				it.remove();
				break;
			default:
				// unknown, remove
				it.remove();
				break;
			}
		}
	}

	/** Convert the acceptance of the source automaton to DNF */
	private void acceptanceToDNF() {
		accExpr = source.getStoredHeader().getAcceptanceCondition();
		accExpr = AcceptanceSimplify.simplify(accExpr);
		accDNF = AcceptanceSimplify.toDNF(accExpr);

		copies = accDNF.size();
	}

	@Override
	public StoredAutomaton manipulate(StoredAutomaton aut) throws HOAConsumerException
	{
		for (NameAndExtra<List<Object>> accname : aut.getStoredHeader().getAcceptanceNames()) {
			if (accname.name.equals("Buchi") ||
			    accname.name.equals("generalized-Buchi")) {
				// nothing to do
				return aut;
			}
		}

		ToNondetBuchi transform = new ToNondetBuchi(aut);
		return transform.target;
	}

}
