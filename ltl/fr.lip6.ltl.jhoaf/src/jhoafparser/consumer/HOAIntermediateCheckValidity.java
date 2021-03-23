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

import java.util.BitSet;
import java.util.HashSet;
import java.util.List;

import jhoafparser.ast.*;
import jhoafparser.util.AcceptanceRepository;
import jhoafparser.util.AcceptanceRepositoryStandard;
import jhoafparser.util.ImplicitEdgeHelper;

/**
 * HOAIntermediate that checks that the parsed HOA automaton is well-formed.<p>
 * 
 * Among others, checks for
 * <ul>
 * <li>Conformance of stated properties with the automaton structure.
 * <li>Conformance of Acceptance and acc-name headers.
 * <li>Definedness of aliases.
 * <li>Well-formedness of label expressions (only using atomic proposition indizes that are defined).
 * <li>Well-formedness of acceptance (only using acceptance set indizes that are defined).
 * </ul>
 */
public class HOAIntermediateCheckValidity extends HOAIntermediate
{
	/** Flag: reject semantic misc headers that are not supported? */
	protected boolean flagRejectSemanticMiscHeaders = false;

	/** A list of supported misc headers */
	protected HashSet<String> supportedMiscHeaders = null;
	
	/** The header names that have occurred so far in the automaton definition */
	protected HashSet<String> usedHeaders = new HashSet<String>();
	
	/** The number of states that have been specified in the header (optional) */
	protected Integer numberOfStates = null;
	
	/** The number of acceptance sets (mandatory) */
	protected Integer numberOfAcceptanceSets = null;

	/** The set of states for which addState has been called */
	protected BitSet statesWithDefinition = new BitSet();

	/** The set of states that occur as target states of some transition */
	protected BitSet targetStatesOfTransitions = new BitSet();
	
	/** The set of states that are start states */
	protected BitSet startStates = new BitSet();

	/** The defined alias names */
	protected HashSet<String> aliases = new HashSet<String>();
	/** Atomic proposition indizes that are defined in alias definitions */
	protected BitSet apsInAliases = new BitSet();

	/** The number of atomic propositions */
	protected Integer numberOfAPs = null;

	/** The acc-name */
	protected String accName = null;
	/** The extra info of the acc-name */
	protected List<Object> accExtraInfo = null;
	
	/** The acceptance condition */
	protected BooleanExpression<AtomAcceptance> acceptance = null;
	
	/** The index of the current state */
	protected int currentState = 0;
	/** Flag: current state has state label */
	protected boolean currentStateHasStateLabel = false;
	/** Flag: current state has transition label */
	protected boolean currentStateHasTransitionLabel = false;
	/** Flag: current state has implicit edge */
	protected boolean currentStateHasImplicitEdge = false;
	/** Flag: current state has explicit edge */
	protected boolean currentStateHasExplicitEdge = false;
	/** Flag: current state has state acceptance */
	protected boolean currentStateHasStateAcceptance = false;
	/** Flag: current state has transitions acceptance */
	protected boolean currentStateHasTransitionAcceptance = false;
	/** Flag: current state is colored */
	protected boolean currentStateIsColored;


	/** Helper for keeping track of the implicit edges */
	protected ImplicitEdgeHelper implicitEdgeHelper = null;
	
	/** Automaton asserts that the automaton uses only state labels */
	protected boolean property_state_labels = false;
	/** Automaton asserts that the automaton uses only transition labels */
	protected boolean property_trans_labels = false;
	/** Automaton asserts that the automaton uses only implicit transitions labels */
	protected boolean property_implicit_labels = false;
	/** Automaton asserts that the automaton uses only explicit transitions labels */
	protected boolean property_explicit_labels = false;
	/** Automaton asserts that the automaton uses only state-based acceptance specifications */
	protected boolean property_state_acc = false;
	/** Automaton asserts that the automaton uses only transition-based acceptance specifications */
	protected boolean property_trans_acc = false;
	/** Automaton asserts that the automaton uses universal branching for at least one transition or for the initial state */
	protected boolean property_univ_branch = false;
	/** Automaton asserts that the automaton does not use universal branching */
	protected boolean property_no_univ_branch = false;
	/** Automaton asserts that the automaton is deterministic, i.e., it has at most one initial state, and the outgoing transitions of each state have disjoint labels (note that this also applies in the presence of universal branching) */
	protected boolean property_deterministic = false;
	/** Automaton asserts that the automaton is complete, i.e., it has at least one state, and the transition function is total */
	protected boolean property_complete = false;
	/** Automaton asserts that each transition (or each state, for state-based acceptance) of the automaton belongs to exactly one acceptance set; this is typically the case in parity automata */
	protected boolean property_colored = false;


	/** Number of start headers (non-deterministic choice of start state) */
	protected int numberOfStartHeaders = 0;
	/** Has this automaton universal branching? */
	protected boolean hasUniversalBranching = false;

	
	/** Constructor, giving next consumer */
	public HOAIntermediateCheckValidity(HOAConsumer next)
	{
		super(next);
	}

	/**
	 * Return a factory for building HOAIntermediateCheckValidity,
	 * using the provided HOAConsumerFactory to construct the next HOAConsumer.
	 **/
	public static HOAConsumerFactory getFactory(final HOAConsumerFactory next)
	{
		return new HOAConsumerFactory() {
			@Override
			public HOAConsumer getNewHOAConsumer()
			{
				return new HOAIntermediateCheckValidity(next.getNewHOAConsumer());
			}
		};
	}

	/** Set Flag: reject semantic (Uppercase) misc headers? */
	public void setFlagRejectSemanticMiscHeaders(boolean value)
	{
		flagRejectSemanticMiscHeaders = value;
	}

	/** Provide a set of misc headers that are supported by the next consumer */
	public void setSupportedMiscHeaders(HashSet<String> supportedMiscHeaders)
	{
		this.supportedMiscHeaders = supportedMiscHeaders;
	}

	/** Add a misc header that is supported by the next consumer */
	public void addSupportedMiscHeader(String supportedMiscHeader)
	{
		if (supportedMiscHeaders == null)
			supportedMiscHeaders = new HashSet<String>();
		
		supportedMiscHeaders.add(supportedMiscHeader); 
	}
	
	@Override
	public boolean parserResolvesAliases()
	{
		return next.parserResolvesAliases();
	}

	@Override
	public void notifyHeaderStart(String version) throws HOAConsumerException
	{
		if (!version.equals("v1")) {
			throw new HOAConsumerException("Can only parse HOA format v1");
		}
		next.notifyHeaderStart(version);
	}

	@Override
	public void setNumberOfStates(int numberOfStates) throws HOAConsumerException
	{
		headerAtMostOnce("States");
		this.numberOfStates = numberOfStates;
		next.setNumberOfStates(numberOfStates);
	}

	@Override
	public void addStartStates(List<Integer> stateConjunction) throws HOAConsumerException
	{
		numberOfStartHeaders++;
		if (stateConjunction.size()>1) {
			hasUniversalBranching = true;
		}

		for (Integer state : stateConjunction) {
			checkStateIndex(state);
			startStates.set(state);
		}
		next.addStartStates(stateConjunction);
	}

	@Override
	public void addAlias(String name, BooleanExpression<AtomLabel> labelExpr) throws HOAConsumerException
	{
		usedHeaders.add("Alias");

		checkAliasesAreDefined(labelExpr);
		aliases.add(name);

		gatherLabels(labelExpr, apsInAliases);

		next.addAlias(name, labelExpr);
	}

	@Override
	public void setAPs(List<String> aps) throws HOAConsumerException
	{
		headerAtMostOnce("AP");

		numberOfAPs = aps.size();

		HashSet<String> apSet = new HashSet<String>();
		for (String ap : aps) {
			if (apSet.add(ap) == false) {
				throw new HOAConsumerException("Atomic proposition "+ap+" appears more than once in AP-header");
			}
		}

		next.setAPs(aps);
	}

	@Override
	public void setAcceptanceCondition(int numberOfSets, BooleanExpression<AtomAcceptance> accExpr) throws HOAConsumerException
	{
		headerAtMostOnce("Acceptance");
		numberOfAcceptanceSets = numberOfSets;

		checkAcceptanceCondition(accExpr);
		acceptance = accExpr.clone();

		next.setAcceptanceCondition(numberOfSets, accExpr);
	}

	@Override
	public void provideAcceptanceName(String name, List<Object> extraInfo) throws HOAConsumerException
	{
		headerAtMostOnce("acc-name");
		accName = name;
		
		// TODO: clone
		accExtraInfo = extraInfo;
		next.provideAcceptanceName(name,  extraInfo);
	}

	@Override
	public void setName(String name) throws HOAConsumerException
	{
		headerAtMostOnce("name");
		next.setName(name);
	}

	@Override
	public void setTool(String name, String version) throws HOAConsumerException
	{
		headerAtMostOnce("tool");
		next.setTool(name, version);
	}

	@Override
	public void addProperties(List<String> properties) throws HOAConsumerException
	{
		usedHeaders.add("properties");

		for (String property : properties) {
			switch (property) {
			case "state_labels": property_state_labels = true; break;
			case "trans_labels": property_trans_labels = true; break;
			case "implicit_labels": property_implicit_labels = true; break;
			case "explicit_labels": property_explicit_labels = true; break;
			case "state_acc": property_state_acc = true; break;
			case "trans_acc": property_trans_acc = true; break;
			case "univ_branch": property_univ_branch = true; break;
			case "no_univ_branch": property_no_univ_branch = true; break;
			case "deterministic": property_deterministic = true; break;
			case "complete": property_complete = true; break;
			case "colored": property_colored = true; break;
			default:
				// do nothing
			}
		}

		next.addProperties(properties);
	}

	@Override
	public void addMiscHeader(String name, List<Object> content) throws HOAConsumerException
	{
		usedHeaders.add(name);
		char firstCharacter = name.charAt(0);
		if (firstCharacter >= 'A' && firstCharacter <= 'Z') {
			// first character is upper case -> if we don't know what to do with this header,
			// raise exception as this may change the semantics of the automaton
			if (flagRejectSemanticMiscHeaders && !(supportedMiscHeaders != null && supportedMiscHeaders.contains(name))) {
				throw new HOAConsumerException("Header "+name+" potentially has semantic relevance, but is not supported");
			}
		}
		next.addMiscHeader(name, content);
	}

	@Override
	public void notifyBodyStart() throws HOAConsumerException
	{
		// check for existence of mandatory headers
		headerIsMandatory("Acceptance");

		// check that all AP indizes in aliases are valid
		if (numberOfAPs == null) {
			// there was no AP-header, equivalent to AP: 0
			numberOfAPs = 0;
		}
		int highestAPIndex = apsInAliases.length() - 1;
		if (highestAPIndex >= 0 && highestAPIndex >= numberOfAPs) {
			throw new HOAConsumerException("AP index "+highestAPIndex+" in some alias definition is out of range"
					+ (numberOfAPs == 0 ? "(there are no APs)" : "(0 - "+(numberOfAPs-1)+")"));
		}

		if (accName != null) {
			checkAccName();
		}

		// check whether the start states violate properties
		if (property_no_univ_branch && hasUniversalBranching) {
			throw new HOAConsumerException("Property 'no_univ_branching' is violated by the start states");
		}
		if (property_deterministic && numberOfStartHeaders != 1) {
			throw new HOAConsumerException("Property 'deterministic' is violated by having "+numberOfStartHeaders+" Start-headers");
		}

		implicitEdgeHelper = new ImplicitEdgeHelper(numberOfAPs);
		
		next.notifyBodyStart();
	}

	@Override
	public void addState(int id, String info, BooleanExpression<AtomLabel> labelExpr, List<Integer> accSignature) throws HOAConsumerException
	{
		checkStateIndex(id);
		if (statesWithDefinition.get(id)) {
			throw new HOAConsumerException("State "+id+" is defined multiple times");
		}
		statesWithDefinition.set(id);
		currentState = id;
		
		if (accSignature != null) {
			checkAcceptanceSignature(accSignature, false);
			currentStateIsColored = (accSignature.size() == 1);
		} else {
			currentStateIsColored = false;
		}

		if (property_colored && numberOfAcceptanceSets>0 && !currentStateIsColored) {
			if (property_state_acc) {
				// we already know that the automaton is in violation...
				throw new HOAConsumerException("State "+id+" is not colored");
			}
		}
		
		if (labelExpr != null) {
			checkLabelExpression(labelExpr);
		}
		
		// reset flags
		currentStateHasStateLabel = (labelExpr != null);
		currentStateHasTransitionLabel = false;
		currentStateHasImplicitEdge = false;
		currentStateHasExplicitEdge = false;
		currentStateHasStateAcceptance = (accSignature != null);
		currentStateHasTransitionAcceptance = false;

		implicitEdgeHelper.startOfState(id);

		next.addState(id, info, labelExpr, accSignature);
	}

	@Override
	public void addEdgeImplicit(int stateId, List<Integer> conjSuccessors, List<Integer> accSignature) throws HOAConsumerException
	{
		assert(stateId == currentState);

		for (Integer succ : conjSuccessors) {
			checkStateIndexTarget(succ);
		}

		if (conjSuccessors.size() > 1) {
			hasUniversalBranching = true;
		}

		boolean edgeIsColored = false;
		if (accSignature != null) {
			checkAcceptanceSignature(accSignature, true);
			edgeIsColored = (accSignature.size() == 1);
		}

		if (property_colored && numberOfAcceptanceSets > 0) {
			if (!currentStateIsColored && !edgeIsColored) {
				throw new HOAConsumerException("In state "+stateId+", there is a transition that is not colored...");
			} else if (currentStateIsColored && edgeIsColored) {
				throw new HOAConsumerException("In state "+stateId+", there is a transition that is colored even though the state is colored already...");
			}
		}

		if (currentStateHasExplicitEdge) {
			throw new HOAConsumerException("Can not mix explicit and implicit edge definitions (state "+stateId+")");
		}
		currentStateHasImplicitEdge = true;
		
		currentStateHasTransitionLabel = true;
		if (currentStateHasStateLabel) {
			throw new HOAConsumerException("Can not mix state labels and implicit edge definitions (state "+stateId+")");
		}

		implicitEdgeHelper.nextImplicitEdge();
		next.addEdgeImplicit(stateId, conjSuccessors, accSignature);
	}

	@Override
	public void addEdgeWithLabel(int stateId, BooleanExpression<AtomLabel> labelExpr, List<Integer> conjSuccessors, List<Integer> accSignature)
			throws HOAConsumerException
	{
		assert(stateId == currentState);

		for (Integer succ : conjSuccessors) {
			checkStateIndexTarget(succ);
		}

		if (conjSuccessors.size() > 1) {
			hasUniversalBranching = true;
		}

		boolean edgeIsColored = false;
		if (accSignature != null) {
			checkAcceptanceSignature(accSignature, true);
			edgeIsColored = (accSignature.size() == 1);
		}

		if (property_colored && numberOfAcceptanceSets > 0) {
			if (!currentStateIsColored && !edgeIsColored) {
				throw new HOAConsumerException("In state "+stateId+", there is a transition that is not colored...");
			} else if (currentStateIsColored && edgeIsColored) {
				throw new HOAConsumerException("In state "+stateId+", there is a transition that is colored even though the state is colored already...");
			}
		}

		if (labelExpr != null) {
			checkLabelExpression(labelExpr);
		}
		
		if (labelExpr != null) {
			currentStateHasTransitionLabel = true;
			if (currentStateHasStateLabel) {
				throw new HOAConsumerException("Can not mix state and transition labeling (state "+stateId+")");
			}
		}

		if (currentStateHasImplicitEdge) {
			throw new HOAConsumerException("Can not mix explicit and implicit edge definitions (state "+stateId+")");
		}
		currentStateHasExplicitEdge = true;

		next.addEdgeWithLabel(stateId, labelExpr, conjSuccessors, accSignature);
	}
	
	@Override
	public void notifyEndOfState(int stateId) throws HOAConsumerException
	{
		implicitEdgeHelper.endOfState();

		// check for property violations
		if (property_state_labels && currentStateHasTransitionLabel) {
			throw new HOAConsumerException("Property 'state-labels' is violated by having transition labels in state "+stateId);
		}
		if (property_trans_labels && currentStateHasStateLabel) {
			throw new HOAConsumerException("Property 'trans-labels' is violated by having a state label in state "+stateId);

		}
		if (property_implicit_labels && currentStateHasExplicitEdge) {
			throw new HOAConsumerException("Property 'implicit-label' is violated by having a label expression on a transition in state "+stateId);
		}
		if (property_explicit_labels && currentStateHasImplicitEdge) {
			throw new HOAConsumerException("Property 'explicit-label' is violated by having implicit transition(s) in state "+stateId);
		}
		if (property_state_acc && currentStateHasTransitionAcceptance) {
			throw new HOAConsumerException("Property 'state-acc' is violated by having transition acceptance in state "+stateId);
		}
		if (property_trans_acc && currentStateHasStateAcceptance) {
			throw new HOAConsumerException("Property 'trans-acc' is violated by having state acceptance in state "+stateId);
		}
		if (property_no_univ_branch && hasUniversalBranching) {
			throw new HOAConsumerException("Property 'no-univ-branch' is violated by having universal branching in state "+stateId);
		}

		// TODO: deterministic
		//  for implicit edges, this is done via the ImplicitEdgeHelper
		//  for explicit edges, check would need to keep track of overlapping label expressions

		// TODO: complete
		//  for implicit edges, this is done via the ImplicitEdgeHelper
		//  for explicit edges, check would need to keep track of overlapping label expressions

		next.notifyEndOfState(stateId);
	}

	@Override
	public void notifyEnd() throws HOAConsumerException
	{
		// check sanity of state definitions and target states
		checkStates();

		if (property_univ_branch && !hasUniversalBranching) {
			throw new HOAConsumerException("Property 'univ-branch' is violated by not having universal branching in the automaton");
		}

		next.notifyEnd();
	}

	@Override
	public void notifyAbort()
	{
		next.notifyAbort();
	}

	// ----------------------------------------------------------------------------
	
	/** Generate a warning for the next consumer. */
	private void doWarning(String warning) throws HOAConsumerException
	{
		next.notifyWarning(warning);
	}

	
	// ----------------------------------------------------------------------------
	
	/** Check whether the given mandatory header has been seen, throw {@code HOAConsumerException} otherwise. */
	private void headerIsMandatory(String name) throws HOAConsumerException
	{
		if (!usedHeaders.contains(name)) {
			throw new HOAConsumerException("Mandatory header "+name+" is missing");
		}
	}

	/** Check whether the given header has been seen at most once, throw {@code HOAConsumerException} otherwise. */
	private void headerAtMostOnce(String headerName) throws HOAConsumerException
	{
		if (usedHeaders.contains(headerName)) {
			throw new HOAConsumerException("Header "+headerName+" occurs multiple times, but is allowed only once.");
		}
		usedHeaders.add(headerName);
	}

	/** Check that the given state index is well-formed, throw {@code HOAConsumerException} otherwise. */
	private void checkStateIndex(int index) throws HOAConsumerException
	{
		if (index < 0) {
			throw new HOAConsumerException("State index "+index+" is negative");
		}
		if (numberOfStates != null) {
			if (index >= numberOfStates) {
				throw new HOAConsumerException("State index "+index+" is out of range (0 - "+(numberOfStates-1)+")");
			}
		}
	}

	/** Checks that the target state index of a transition is valid, throw {@code HOAConsumerException} otherwise. */
	private void checkStateIndexTarget(int index) throws HOAConsumerException
	{
		if (index < 0) {
			throw new HOAConsumerException("State index "+index+" is negative");
		}
		if (numberOfStates != null) {
			if (index >= numberOfStates) {
				throw new HOAConsumerException("State index "+index+" is out of range (0 - "+(numberOfStates-1)+")");
			}
		}
		targetStatesOfTransitions.set(index);
	}

	/** Check that the state indizes are valid, throw {@code HOAConsumerException} otherwise. */
	private void checkStates() throws HOAConsumerException
	{
		boolean haveComplainedAboutMissingStates = false;
		
		// if numberOfStates is set, check that all states are in range
		if (numberOfStates != null) {
			// the states with a definition
			int highestStateIndex = statesWithDefinition.length() - 1;
			if (highestStateIndex >= numberOfStates) {
				throw new HOAConsumerException("State index "+highestStateIndex+" is out of range (0 - "+(numberOfStates-1)+")");
			}

			// the states occuring as targets
			highestStateIndex = targetStatesOfTransitions.length() - 1;
			if (highestStateIndex >= numberOfStates) {
				throw new HOAConsumerException("State index "+highestStateIndex+" (target in a transition) is out of range (0 - "+(numberOfStates-1)+")");
			}
			
			// the start states
			highestStateIndex = startStates.length() - 1;
			if (highestStateIndex >= numberOfStates) {
				throw new HOAConsumerException("State index "+highestStateIndex+" (start state) is out of range (0 - "+(numberOfStates-1)+")");
			}

			if (statesWithDefinition.cardinality() != numberOfStates) {
				int missing = numberOfStates - statesWithDefinition.cardinality();
				doWarning("There are "+missing+" states without definition");
				haveComplainedAboutMissingStates = true;
			}
		}

		BitSet targetsButNoDefinition = (BitSet) targetStatesOfTransitions.clone();
		targetsButNoDefinition.andNot(statesWithDefinition);
		if (!targetsButNoDefinition.isEmpty() && !haveComplainedAboutMissingStates) {
			doWarning("There are "+targetsButNoDefinition.cardinality()+" states that are targets of transitions but that have no definition");
			haveComplainedAboutMissingStates = true;
		}

		BitSet startStatesButNoDefinition = (BitSet) startStates.clone();
		startStatesButNoDefinition.andNot(statesWithDefinition);
		if (!startStatesButNoDefinition.isEmpty() && !haveComplainedAboutMissingStates) {
			doWarning("There are "+startStatesButNoDefinition.cardinality()+" states that are start states but that have no definition");
			haveComplainedAboutMissingStates = true;
		}
		
		if (haveComplainedAboutMissingStates && property_colored && numberOfAcceptanceSets > 0) {
			// states without definition are not colored
			throw new HOAConsumerException("An automaton with property 'colored' can not have states missing a definition");
		}
	}

	/** Check that the acceptance condition is well-formed, referencing only existing acceptance sets.
	 * Throws {@code HOAConsumerException} otherwise. */
	private void checkAcceptanceCondition(BooleanExpression<AtomAcceptance> accExpr) throws HOAConsumerException
	{
		assert (numberOfAcceptanceSets != null);

		switch (accExpr.getType()) {
		case EXP_TRUE:
		case EXP_FALSE:
			return;
		case EXP_AND:
		case EXP_OR:
			checkAcceptanceCondition(accExpr.getLeft());
			checkAcceptanceCondition(accExpr.getRight());
			return;
		case EXP_NOT:
			throw new HOAConsumerException("Acceptance condition contains boolean negation, not allowed");
		case EXP_ATOM:
			int acceptanceSet = accExpr.getAtom().getAcceptanceSet();
			if (acceptanceSet < 0 || acceptanceSet >= numberOfAcceptanceSets) {
				throw new HOAConsumerException("Acceptance condition contains acceptance set with index "+acceptanceSet+", valid range is 0 - "+(numberOfAcceptanceSets-1));
			}
			return;
		}
		throw new UnsupportedOperationException("Unknown operator in acceptance condition: "+accExpr);
	}
	
	/** Check that the acceptance signature is well-formed, referencing only existing acceptance sets.
	 * Throws {@code HOAConsumerException} otherwise. */
	private void checkAcceptanceSignature(List<Integer> accSignature, boolean inTransition) throws HOAConsumerException
	{
		for (Integer acceptanceSet : accSignature) {
			if (acceptanceSet < 0 || acceptanceSet >= numberOfAcceptanceSets) {
				throw new HOAConsumerException("Acceptance signature "
						+ (inTransition ? "(in transition) " : "")
						+ "for state index "+currentState+" contains acceptance set with index "+acceptanceSet
						+ ((numberOfAcceptanceSets==0)
						    ? ", but there are not acceptance sets"
						    : ", valid range is 0 - "+(numberOfAcceptanceSets-1)));
			}

		}
	}

	/** Check that all aliases in the label expression are defined, throws {@code HOAConsumerException} otherwise. */
	private void checkAliasesAreDefined(BooleanExpression<AtomLabel> expr) throws HOAConsumerException
	{
		switch (expr.getType()) {
		case EXP_TRUE:
		case EXP_FALSE:
			return;
		case EXP_AND:
		case EXP_OR:
			checkAliasesAreDefined(expr.getLeft());
			checkAliasesAreDefined(expr.getRight());
			return;
		case EXP_NOT:
			checkAliasesAreDefined(expr.getLeft());
			return;
		case EXP_ATOM:
			if (expr.getAtom().isAlias()) {
				if (!aliases.contains(expr.getAtom().getAliasName())) {
					throw new HOAConsumerException("Alias @"+expr.getAtom().getAliasName()+" is not defined");
				}
			}
			return;
		}
		throw new UnsupportedOperationException("Unknown operator in label expression: "+expr);
	}

	/**
	 * Traverse the expression and, for every label encountered, set the corresponding bit in the
	 * {@code result} BitSet.
	 * @param expr the expression
	 * @param result a BitSet where the additional bits corresponding to APs are set
	 */
	private void gatherLabels(BooleanExpression<AtomLabel> expr, BitSet result) {
		switch (expr.getType()) {
		case EXP_TRUE:
		case EXP_FALSE:
			return;
		case EXP_AND:
		case EXP_OR:
			gatherLabels(expr.getLeft(), result);
			gatherLabels(expr.getRight(), result);
			return;
		case EXP_NOT:
			gatherLabels(expr.getLeft(), result);
			return;
		case EXP_ATOM:
			if (!expr.getAtom().isAlias()) {
				result.set(expr.getAtom().getAPIndex());
			}
			return;
		}
		throw new UnsupportedOperationException("Unknown operator in label expression: "+expr);
	}
	
	/** Check that all aliases and AP indizes in the label expression are defined, throws {@code HOAConsumerException} otherwise. */
	private void checkLabelExpression(BooleanExpression<AtomLabel> expr) throws HOAConsumerException
	{
		switch (expr.getType()) {
		case EXP_TRUE:
		case EXP_FALSE:
			return;
		case EXP_AND:
		case EXP_OR:
			checkLabelExpression(expr.getLeft());
			checkLabelExpression(expr.getRight());
			return;
		case EXP_NOT:
			checkLabelExpression(expr.getLeft());
			return;
		case EXP_ATOM:
			if (expr.getAtom().isAlias()) {
				if (!aliases.contains(expr.getAtom().getAliasName())) {
					throw new HOAConsumerException("Alias @"+expr.getAtom().getAliasName()+" is not defined");
				}
			} else {
				assert(numberOfAPs != null);
				int apIndex = expr.getAtom().getAPIndex();
				if (apIndex >= numberOfAPs) {
					if (numberOfAPs == 0) {
						throw new HOAConsumerException("AP index "+apIndex+" in expression is out of range (no APs): "+expr);	
					} else {
						throw new HOAConsumerException("AP index "+apIndex+" in expression is out of range (from 0 to "+(numberOfAPs-1)+"): "+expr);
					}
				}
			}
			return;
		}
		throw new UnsupportedOperationException("Unknown operator in label expression: "+expr);
	}

	// ----------------------------------------------------------------------------
	
	/** Check that the Acceptance header matches the canonical condition defined by the acc-name header, throws {@code HOAConsumerException} otherwise. */
	private void checkAccName() throws HOAConsumerException {
		AcceptanceRepository repository = new AcceptanceRepositoryStandard();
		
		try {
			BooleanExpression<AtomAcceptance> canonical = repository.getCanonicalAcceptanceExpression(accName, accExtraInfo);
			if (canonical == null) {
				// acceptance name is not known
				return;
			}

			//System.out.println("Canonical: "+canonical);
			//System.out.println("Acceptance: "+acceptance);
			
			assert (acceptance != null);
			if (!BooleanExpression.areSyntacticallyEqual(acceptance, canonical)) {
				throw new HOAConsumerException("The acceptance given by the Acceptance and by the acc-name headers do not match syntactically:"
						+ "\nFrom Acceptance-header: "+acceptance
						+ "\nCanonical expression for acc-name-header: "+canonical);
			}
		} catch (IllegalArgumentException e) {
			throw new HOAConsumerException(e.getMessage());
		}
	}
}
