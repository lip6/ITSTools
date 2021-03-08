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
import java.util.BitSet;
import java.util.List;

import jhoafparser.ast.*;
import jhoafparser.consumer.HOAConsumer;
import jhoafparser.consumer.HOAConsumerException;
import jhoafparser.consumer.HOAConsumerFactory;
import jhoafparser.consumer.HOAIntermediateBatchProcessState;
import jhoafparser.storage.StoredEdgeImplicit;
import jhoafparser.storage.StoredEdgeWithLabel;
import jhoafparser.storage.StoredState;
import jhoafparser.util.PropertyFilter;

/**
 * Analysis class for deducing an acc-name for a given acceptance condition.
 * <p>
 * Having deduced an acc-name, will transform the acceptance sets in states
 * and edges to conform to the canonical form for this acc-name.
 */
public class DeduceAccName extends HOAIntermediateBatchProcessState
{
	/** Store information about a transformed acceptance condition*/
	private class Transformed {
		/** The transformed expression */
		private BooleanExpression<AtomAcceptance> expression;

		/**
		 * Information for constructing the acceptance sets of the transformed
		 * condition.<br>
		 * 
		 * Boolean:    create TRUE/FALSE acceptance set
		 * Integer(i): if (i>0) take from set i-1, if (i<0) negate set (-i-1).
		 * */
		private List<Object> transformedSets = new ArrayList<Object>();

		/** Get the transformed expression */
		public BooleanExpression<AtomAcceptance> getExpression() {
			return expression;
		}

		/** Set the transformed expression */
		public void setExpression(BooleanExpression<AtomAcceptance> expression) {
			this.expression = expression;
		}

		/**
		 * Create a new acceptance set for the transformed condition,
		 * construct from set index (optionally negated).
		 * @return the index in the transformed condition
		 */
		public int createAccSet(Integer index, boolean negated) {
			int result = transformedSets.size();
			transformedSets.add((index + 1) * (negated ? -1 : 1));
			return result;
		}

		/**
		 * Create a new acceptance set for the transformed condition,
		 * construct from TRUE/FALSE.
		 * @return the index in the transformed condition
		 */
		public int createAccSet(boolean value) {
			int result = transformedSets.size();
			transformedSets.add(new Boolean(value));
			return result;
		}

		/** Returns the number of acceptance sets in the transformed condition */
		public int getNumberOfAccSets() {
			return transformedSets.size();
		}

		/**
		 * Returns {@code true} if the acceptance set in the
		 * transformed condition is a boolean set.
		 **/
		public boolean isAccSetBoolean(int transformedAccSet) {
			return transformedSets.get(transformedAccSet) instanceof Boolean;
		}

		/**
		 * Returns the boolean value used to construct the given
		 * acceptance set in the transformed condition.<br>
		 * Can only be called if {@code isAccSetBoolean(transformedAccSet) == true}.
		 */
		public boolean getAccSetBoolean(int transformedAccSet) {
			return (boolean) transformedSets.get(transformedAccSet);
		}

		/**
		 * Returns the index of the original acceptance set used to
		 * construct the given acceptance set in the transformed condition.<br>
		 * Can only be called if {@code isAccSetBoolean(transformedAccSet) == false}.
		 **/
		public int getOriginalAccSet(int transformedAccSet) {
			if (isAccSetBoolean(transformedAccSet)) {
				throw new UnsupportedOperationException("Acceptance set is boolean");
			}
			int i = (int) transformedSets.get(transformedAccSet);
			if (i<0) i = -i;
			return i-1;
		}

		/**
		 * Returns true if original acceptance used to
		 * construct the given acceptance set in the transformed condition
		 * should be negated.
		 * Can only be called if {@code isAccSetBoolean(transformedAccSet) == false}.
		 **/
		public boolean isAccSetNegated(int transformedAccSet) {
			if (isAccSetBoolean(transformedAccSet)) {
				throw new UnsupportedOperationException("Acceptance set is boolean");
			}
			return ((int)transformedSets.get(transformedAccSet) < 0);
		}
		
	}

	/** The number of acceptance sets in the original automaton */
	private int numberOfAccSets;
	/** The original acceptance condition */
	private BooleanExpression<AtomAcceptance> accExpr;

	/**
	 * An ordered list of allowed acceptance conditions (acc-names).
	 * Conditions appearing earlier are preferred.
	 **/
	private List<String> allowedAcceptance = new ArrayList<String>();

	/** The chosen transformation to be applied */
	private Transformed chosenTransformation = null;

	/** Default value: should acceptance be placed on states if there are no other constrainsts? */
	private boolean defaultPlaceAcceptanceOnState = false;
	
	/** Property filter to filter out properties that may become invalid */
	private PropertyFilter propertyFilter = new PropertyFilter(true,  // preserves language
	                                                           true,  // does preserve the syntax 
	                                                           true,  // preserves branching
	                                                           true   // preserves structure
	                                                           );

	/** Is "state-acc" set in the properties of the original automaton ? */
	private boolean propertyStateAcc = false;
	/** Is "trans-acc" set in the properties of the original automaton ? */
	private boolean propertyTransAcc = false;

	/** Constructor, allow all supported acceptance conditions. */
	public DeduceAccName(HOAConsumer next)
	{
		super(next);
		allowedAcceptance.add("all");
		allowedAcceptance.add("none");
		allowedAcceptance.add("Buchi");
		allowedAcceptance.add("co-Buchi");
		allowedAcceptance.add("generalized-Buchi");
		allowedAcceptance.add("generalized-co-Buchi");
		allowedAcceptance.add("Rabin");
		allowedAcceptance.add("Streett");
	}

	/**
	 * Constructor, provide (ordered) list of allowed acceptance conditions 
	 * (names valid in the acc-name header).
	 * Conditions appearing earlier are preferred.
	 * */
	public DeduceAccName(HOAConsumer next, List<String> allowedAcceptance) {
		super(next);
		this.allowedAcceptance.addAll(allowedAcceptance);
	}

	@Override
	public void setAcceptanceCondition(int numberOfSets, BooleanExpression<AtomAcceptance> accExpr) throws HOAConsumerException {
		numberOfAccSets = numberOfSets;
		this.accExpr = accExpr;

		// do not pass forward, yet
	}

	@Override
	public void provideAcceptanceName(String name, List<Object> extraInfo) throws HOAConsumerException {
		// no not pass forward, yet
	}

	@Override
	public void addProperties(List<String> properties) throws HOAConsumerException {
		for (String property : properties) {
			if (property.equals("state-acc")) propertyStateAcc = true;
			if (property.equals("trans-acc")) propertyTransAcc = true;
		}

		next.addProperties(propertyFilter.filter(properties));
	}

	@Override
	public void notifyBodyStart() throws HOAConsumerException {
		if (accExpr == null) {
			throw new HOAConsumerException("Missing acceptance condition!");
		}

		// determine where to place acceptance by default
		if (propertyStateAcc) {
			defaultPlaceAcceptanceOnState = true;
		} else if (propertyTransAcc) {
			defaultPlaceAcceptanceOnState = false;
		} else {
			// keep global default
		}

		// simplify the expression: remove redundant TRUE / FALSE
		BooleanExpression<AtomAcceptance> simplified = AcceptanceSimplify.simplify(accExpr);

		boolean deducedAccName = false;
		for (String allowed : allowedAcceptance) {
			// try each allowed acceptance condition

			// construct new Transformed object for storing
			// the transformed condition
			Transformed transformed = new Transformed();

			String name = null;
			List<Object> extra = new ArrayList<Object>();

			switch (allowed) {
			case "all":
				if (toAll(simplified, transformed)) {
					name = "all";
				}
				break;
			case "none":
				if (toNone(simplified, transformed)) {
					name = "none";
				}
				break;
			case "Buchi":
				if (toBuchi(simplified, transformed)) {
					name = "Buchi";
				}
				break;
			case "co-Buchi":
				if (toCoBuchi(simplified, transformed)) {
					name = "co-Buchi";
				}
				break;
			case "generalized-Buchi":
				if (toGeneralizedBuchi(simplified, transformed)) {
					extra.add(transformed.getNumberOfAccSets());
					name = "generalized-Buchi";
				}
				break;
			case "generalized-co-Buchi":
				if (toGeneralizedCoBuchi(simplified, transformed)) {
					extra.add(transformed.getNumberOfAccSets());
					name = "generalized-co-Buchi";
				}
				break;
			case "Rabin":
				if (toRabin(simplified, transformed)) {
					extra.add(transformed.getNumberOfAccSets() / 2);
					name = "Rabin";
				}
				break;
			case "Streett":
				if (toStreett(simplified, transformed)) {
					extra.add(transformed.getNumberOfAccSets() / 2);
					name = "Streett";
				}
				break;
			default:
				// Unknown acceptance, ignore
				break;
			}
			
			if (name != null) {
				// we have a new condition!
				next.setAcceptanceCondition(transformed.getNumberOfAccSets(), transformed.getExpression());
				next.provideAcceptanceName(name, extra);
				chosenTransformation = transformed;
				deducedAccName = true;
				break;  // stop loop
			}
		}

		if (!deducedAccName) {
			// no match, just keep old acceptance
			next.setAcceptanceCondition(numberOfAccSets, accExpr);
		}

		// notify the next consumer that the body is about to start
		next.notifyBodyStart();
	}

	@Override
	public boolean processState(StoredState state, List<StoredEdgeImplicit> edgesImplicit, List<StoredEdgeWithLabel> edgesWithLabel)
			throws HOAConsumerException
	{
		if (chosenTransformation == null) {
			// we don't actually transform anything, let parent handle feeding to the next consumer
			return true;
		}
		
		// go ahead, apply transformation
	
		// determine where to place acceptance
		boolean placeAcceptanceOnState;
		boolean hasStateAcceptance = hasStateAcceptance(state);
		if (hasStateAcceptance) {
			if (!hasTransitionAcceptance(edgesImplicit, edgesWithLabel)) {
				placeAcceptanceOnState = true;
			} else {
				// mixed state- and transition-acceptance
				placeAcceptanceOnState = false;
			}
		} else if (hasTransitionAcceptance(edgesImplicit, edgesWithLabel)) {
			placeAcceptanceOnState = false;
		} else {
			// no acceptance in original, but we might introduce new acceptance (true/false sets, ...)
			// place on default location
			placeAcceptanceOnState = defaultPlaceAcceptanceOnState;
		}
	
		if (placeAcceptanceOnState) {
			next.addState(state.getStateId(), state.getInfo(), state.getLabelExpr(), transformAcceptance(state.getAccSignature()));
		} else {
			next.addState(state.getStateId(), state.getInfo(), state.getLabelExpr(), null);
		}
	
		if (edgesImplicit != null) {
			for (StoredEdgeImplicit edge : edgesImplicit) {
				List<Integer> accEdge = edge.getAccSignature();
				if (accEdge != null && hasStateAcceptance) {
					// merge both acceptance
					accEdge = new ArrayList<Integer>(accEdge);
					accEdge.addAll(state.getAccSignature());
				}
				next.addEdgeImplicit(state.getStateId(), edge.getConjSuccessors(), transformAcceptance(accEdge));
			}
		} else if (edgesWithLabel != null) {
			for (StoredEdgeWithLabel edge : edgesWithLabel) {
				List<Integer> accEdge = edge.getAccSignature();
				if (accEdge != null && hasStateAcceptance) {
					// merge both acceptance
					accEdge = new ArrayList<Integer>(accEdge);
					accEdge.addAll(state.getAccSignature());
				}
				next.addEdgeWithLabel(state.getStateId(), edge.getLabelExpr(), edge.getConjSuccessors(), transformAcceptance(accEdge));
			}
		} else {
			// no edge, terminal => acceptance is irrelevant
		}
	
		return false;
	}

	/**
	 * Attempt to transform acceptance expression to 'all'.
	 * Returns true on success, with the result stored in {@code transformed}.
	 * Expects the expression to be simplified (unnecessary TRUE/FALSE removed).
	 **/
	private boolean toAll(BooleanExpression<AtomAcceptance> acc, Transformed transformed) {
		switch (acc.getType()) {
		case EXP_TRUE:
			transformed.setExpression(new BooleanExpression<AtomAcceptance>(true));
			return true;
		default:
			return false;
		}
	}

	/**
	 * Attempt to transform acceptance expression to 'none'.
	 * Returns true on success, with the result stored in {@code transformed}.
	 * Expects the expression to be simplified (unnecessary TRUE/FALSE removed).
	 **/
	private boolean toNone(BooleanExpression<AtomAcceptance> acc, Transformed transformed) {
		switch (acc.getType()) {
		case EXP_FALSE:
			transformed.setExpression(new BooleanExpression<AtomAcceptance>(false));
			return true;
		default:
			return false;
		}
	}

	/**
	 * Attempt to transform acceptance expression to Buchi.
	 * Returns true on success, with the result stored in {@code transformed}.
	 * Expects the expression to be simplified (unnecessary TRUE/FALSE removed).
	 **/
	private boolean toBuchi(BooleanExpression<AtomAcceptance> acc, Transformed transformed) {
		int inf;
		switch (acc.getType()) {
		case EXP_TRUE:
			inf = transformed.createAccSet(true);
			break;
		case EXP_FALSE:
			inf = transformed.createAccSet(false);
			break;
		case EXP_ATOM: {
			AtomAcceptance atom = acc.getAtom();
			
			if (atom.getType() == AtomAcceptance.Type.TEMPORAL_FIN) {
				return false;
			}
			
			inf = transformed.createAccSet(atom.getAcceptanceSet(), atom.isNegated());
			break;
		}
		default:
			return false;
		}

		BooleanExpression<AtomAcceptance> Inf = new BooleanExpression<AtomAcceptance>(AtomAcceptance.Inf(inf));

		// if there already exists an expression, do AND (for when called in generalized-Buchi)
		if (transformed.getExpression() != null) {
			transformed.setExpression(transformed.getExpression().and(Inf));
		} else {
			transformed.setExpression(Inf);
		}
		return true;
	}

	/**
	 * Attempt to transform acceptance expression to CoBuchi.
	 * Returns true on success, with the result stored in {@code transformed}.
	 * Expects the expression to be simplified (unnecessary TRUE/FALSE removed).
	 **/
	private boolean toCoBuchi(BooleanExpression<AtomAcceptance> acc, Transformed transformed) {
		int fin;
		switch (acc.getType()) {
		case EXP_TRUE:
			fin = transformed.createAccSet(false);
			break;
		case EXP_FALSE:
			fin = transformed.createAccSet(true);
			break;
		case EXP_ATOM: {
			AtomAcceptance atom = acc.getAtom();
			
			if (atom.getType() == AtomAcceptance.Type.TEMPORAL_INF) {
				return false;
			}

			fin = transformed.createAccSet(atom.getAcceptanceSet(), atom.isNegated());
			break;
		}
		default:
			return false;
		}

		BooleanExpression<AtomAcceptance> Fin = new BooleanExpression<AtomAcceptance>(AtomAcceptance.Fin(fin));

		// if there already exists an expression, do AND (for when called in generalized-Buchi)
		if (transformed.getExpression() != null) {
			transformed.setExpression(transformed.getExpression().and(Fin));
		} else {
			transformed.setExpression(Fin);
		}
		return true;
	}

	/**
	 * Attempt to transform acceptance expression to generalized-Buchi.
	 * Returns true on success, with the result stored in {@code transformed}.
	 * Expects the expression to be simplified (unnecessary TRUE/FALSE removed).
	 **/
	private boolean toGeneralizedBuchi(BooleanExpression<AtomAcceptance> acc, Transformed transformed) {
		switch (acc.getType()) {
		case EXP_TRUE:
			return toBuchi(acc, transformed);
		case EXP_FALSE:
			// no pair, expression = false
			transformed.setExpression(new BooleanExpression<AtomAcceptance>(false));
			return true;
		case EXP_ATOM:
			return toBuchi(acc, transformed);
		case EXP_AND:
			if (!toGeneralizedBuchi(acc.getLeft(), transformed)) return false;
			if (!toGeneralizedBuchi(acc.getRight(), transformed)) return false;
			return true;
		default:
			return false;
		}
	}

	/**
	 * Attempt to transform acceptance expression to generalized-co-Buchi.
	 * Returns true on success, with the result stored in {@code transformed}.
	 * Expects the expression to be simplified (unnecessary TRUE/FALSE removed).
	 **/
	private boolean toGeneralizedCoBuchi(BooleanExpression<AtomAcceptance> acc, Transformed transformed) {
		switch (acc.getType()) {
		case EXP_TRUE:
			// no pair, expression = false
			transformed.setExpression(new BooleanExpression<AtomAcceptance>(true));
			return true;
		case EXP_FALSE:
			return toCoBuchi(acc, transformed);
		case EXP_ATOM:
			return toCoBuchi(acc, transformed);
		case EXP_AND:
			if (!toGeneralizedCoBuchi(acc.getLeft(), transformed)) return false;
			if (!toGeneralizedCoBuchi(acc.getRight(), transformed)) return false;
			return true;
		default:
			return false;
		}
	}

	/**
	 * Attempt to transform acceptance expression to Rabin.
	 * Returns true on success, with the result stored in {@code transformed}.
	 * Expects the expression to be simplified (unnecessary TRUE/FALSE removed).
	 **/
	private boolean toRabin(BooleanExpression<AtomAcceptance> acc, Transformed transformed) {
		switch (acc.getType()) {
		case EXP_OR:
			if (toRabin(acc.getLeft(), transformed))
				return toRabin(acc.getRight(), transformed);
			else
				return false;
		case EXP_AND:
		case EXP_ATOM:
		case EXP_TRUE:
			return toRabinPair(acc, transformed);
		case EXP_FALSE:
			transformed.setExpression(new BooleanExpression<AtomAcceptance>(false));
			return true;
		default:
			return false;
		}
	}

	/**
	 * Attempt to transform acceptance expression to a Rabin pair (Fin(S_1) & Inf(S_2)).
	 * Returns true on success, with {@code transformed} updated.
	 * Expects the expression to be simplified (unnecessary TRUE/FALSE removed).
	 **/
	private boolean toRabinPair(BooleanExpression<AtomAcceptance> acc, Transformed transformed) {
		int fin;
		int inf;

		switch (acc.getType()) {
		case EXP_FALSE:
			// simply no pair
			return true;
		case EXP_TRUE:
			fin = transformed.createAccSet(false);
			inf = transformed.createAccSet(true);
			break;
		case EXP_ATOM: {
			AtomAcceptance atom = acc.getAtom();
			
			if (atom.getType() == AtomAcceptance.Type.TEMPORAL_FIN) {
				fin = transformed.createAccSet(atom.getAcceptanceSet(), atom.isNegated());
				inf = transformed.createAccSet(true);
			} else { // TEMPORAL_INF
				fin = transformed.createAccSet(false);
				inf = transformed.createAccSet(atom.getAcceptanceSet(), atom.isNegated());
			}
			break;
		}
		case EXP_AND: {
			BooleanExpression<AtomAcceptance> left = acc.getLeft();
			BooleanExpression<AtomAcceptance> right = acc.getRight();

			if (left.isAtom() && left.getAtom().getType() == AtomAcceptance.Type.TEMPORAL_INF) {
				// swap
				left = acc.getRight();
				right = acc.getLeft();
			} else if (right.isAtom() && right.getAtom().getType() == AtomAcceptance.Type.TEMPORAL_FIN) {
				// swap
				left = acc.getRight();
				right = acc.getLeft();
			}

			if (!(left.isAtom() && left.getAtom().getType() == AtomAcceptance.Type.TEMPORAL_FIN)) {
				return false;
			}
			if (!(right.isAtom() && right.getAtom().getType() == AtomAcceptance.Type.TEMPORAL_INF)) {
				return false;
			}

			fin = transformed.createAccSet(left.getAtom().getAcceptanceSet(), left.getAtom().isNegated());
			inf = transformed.createAccSet(right.getAtom().getAcceptanceSet(), right.getAtom().isNegated());
			break;
		}
		default:
			return false;
		}

		BooleanExpression<AtomAcceptance> pair = new BooleanExpression<AtomAcceptance>(AtomAcceptance.Fin(fin));
		pair = pair.and(new BooleanExpression<AtomAcceptance>(AtomAcceptance.Inf(inf)));

		if (transformed.getExpression() == null)
			transformed.setExpression(pair);
		else
			transformed.setExpression(transformed.getExpression().or(pair));

		return true;
	}

	/**
	 * Attempt to transform acceptance expression to Streett.
	 * Returns true on success, with the result stored in {@code transformed}.
	 * Expects the expression to be simplified (unnecessary TRUE/FALSE removed).
	 **/
	private boolean toStreett(BooleanExpression<AtomAcceptance> acc, Transformed transformed) {
		switch (acc.getType()) {
		case EXP_AND:
			if (toStreett(acc.getLeft(), transformed))
				return toStreett(acc.getRight(), transformed);
			else
				return false;
		case EXP_OR:
		case EXP_ATOM:
		case EXP_FALSE:
			return toStreettPair(acc, transformed);
		case EXP_TRUE:
			transformed.setExpression(new BooleanExpression<AtomAcceptance>(true));
			return true;
		default:
			return false;
		}
	}

	/**
	 * Attempt to transform acceptance expression to a Streett pair (Fin(S_1) | Inf(S_2)).
	 * Returns true on success, with {@code transformed} updated.
	 * Expects the expression to be simplified (unnecessary TRUE/FALSE removed).
	 **/
	private boolean toStreettPair(BooleanExpression<AtomAcceptance> acc, Transformed transformed) {
		int fin;
		int inf;

		switch (acc.getType()) {
		case EXP_TRUE:
			// simply no pair
			return true;
		case EXP_FALSE:
			fin = transformed.createAccSet(true);
			inf = transformed.createAccSet(false);
			break;
		case EXP_ATOM: {
			AtomAcceptance atom = acc.getAtom();
			
			if (atom.getType() == AtomAcceptance.Type.TEMPORAL_FIN) {
				fin = transformed.createAccSet(atom.getAcceptanceSet(), atom.isNegated());
				inf = transformed.createAccSet(true);
			} else { // TEMPORAL_INF
				fin = transformed.createAccSet(false);
				inf = transformed.createAccSet(atom.getAcceptanceSet(), atom.isNegated());
			}
			break;
		}
		case EXP_OR: {
			BooleanExpression<AtomAcceptance> left = acc.getLeft();
			BooleanExpression<AtomAcceptance> right = acc.getRight();

			if (left.isAtom() && left.getAtom().getType() == AtomAcceptance.Type.TEMPORAL_INF) {
				// swap
				left = acc.getRight();
				right = acc.getLeft();
			} else if (right.isAtom() && right.getAtom().getType() == AtomAcceptance.Type.TEMPORAL_FIN) {
				// swap
				left = acc.getRight();
				right = acc.getLeft();
			}

			if (!(left.isAtom() && left.getAtom().getType() == AtomAcceptance.Type.TEMPORAL_FIN)) {
				return false;
			}
			if (!(right.isAtom() && right.getAtom().getType() == AtomAcceptance.Type.TEMPORAL_INF)) {
				return false;
			}

			fin = transformed.createAccSet(left.getAtom().getAcceptanceSet(), left.getAtom().isNegated());
			inf = transformed.createAccSet(right.getAtom().getAcceptanceSet(), right.getAtom().isNegated());
			break;
		}
		default:
			return false;
		}

		BooleanExpression<AtomAcceptance> pair = new BooleanExpression<AtomAcceptance>(AtomAcceptance.Fin(fin));
		pair = pair.or(new BooleanExpression<AtomAcceptance>(AtomAcceptance.Inf(inf)));

		if (transformed.getExpression() == null)
			transformed.setExpression(pair);
		else
			transformed.setExpression(transformed.getExpression().and(pair));

		return true;
	}

	/** Helper: returns true if the stored state has state acceptance */
	private boolean hasStateAcceptance(StoredState state) {
		return state.getAccSignature() != null;
	}

	/** Helper: returns true if one of the edges has transition acceptance */
	private boolean hasTransitionAcceptance(List<StoredEdgeImplicit> edgesImplicit, List<StoredEdgeWithLabel> edgesWithLabel) {
		if (edgesImplicit != null) {
			for (StoredEdgeImplicit edge : edgesImplicit) {
				if (edge.getAccSignature() != null) return true;
			}
		} else if (edgesWithLabel != null) {
			for (StoredEdgeWithLabel edge : edgesWithLabel) {
				if (edge.getAccSignature() != null) return true;
			}
		}
		return false;
	}

	/**
	 * Construct the transformed acceptance signature
	 * according to chosenTransformation and the original acceptance 
	 */
	private List<Integer> transformAcceptance(List<Integer> acceptance) {
		// create bit set from the original acceptance
		BitSet acc = new BitSet();
		if (acceptance != null) {
			for (Integer a : acceptance) acc.set(a);
		}

		List<Integer> result = new ArrayList<Integer>();

		int n = chosenTransformation.getNumberOfAccSets();
		for (int accSetTransformed = 0; accSetTransformed < n; accSetTransformed++) {
			if (chosenTransformation.isAccSetBoolean(accSetTransformed)) {
				if (chosenTransformation.getAccSetBoolean(accSetTransformed)) {
					// TRUE, always add
					result.add(accSetTransformed);
				} else {
					// FALSE, do not add
				}
			} else {
				boolean negated = chosenTransformation.isAccSetNegated(accSetTransformed);
				int setIndex = chosenTransformation.getOriginalAccSet(accSetTransformed);
				if (acc.get(setIndex) ^ negated) {
					result.add(accSetTransformed);
				}
			}
		}

		if (result.isEmpty() && acceptance==null) {
			// if we got a missing acceptance and we did not change it,
			// return null acceptance
			result = null;
		}

		return result;
	}

	/**
	 * Return a factory for building DeduceAccName (allowing all acceptance conditions),
	 * using the provided HOAConsumerFactory to construct the next HOAConsumer.
	 **/
	public static HOAConsumerFactory getFactory(final HOAConsumerFactory next)
	{
		return new HOAConsumerFactory() {
			@Override
			public HOAConsumer getNewHOAConsumer()
			{
				return new DeduceAccName(next.getNewHOAConsumer());
			}
		};
	}

	/**
	 * Return a factory for building DeduceAccName (with allowed acceptance conditions),
	 * using the provided HOAConsumerFactory to construct the next HOAConsumer.
	 **/
	public static HOAConsumerFactory getFactory(final HOAConsumerFactory next, final List<String> allowedAcceptance)
	{
		return new HOAConsumerFactory() {
			@Override
			public HOAConsumer getNewHOAConsumer()
			{
				return new DeduceAccName(next.getNewHOAConsumer(), allowedAcceptance);
			}
		};
	}

}
