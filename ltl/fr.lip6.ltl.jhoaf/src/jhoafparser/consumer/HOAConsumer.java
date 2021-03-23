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

import java.util.List;

import jhoafparser.ast.*;

/**
 * The {@code HOAConsumer} interface is the basic means of interacting with the parser and
 * the other infrastructure provided by jhoafparser.
 * <p>
 * Most of the methods in the interface correspond to the various events that happen
 * during parsing of a HOA input, e.g., marking the occurrence of the various header
 * elements, the start of the automaton body, the states and edges, etc.
 * In the documentation for each method, additional information is provided whether
 * the corresponding element in a HOA input is considered optional or mandatory and
 * whether it can occur once or multiple times. For error handling, a consumer is
 * supposed to throw {@link HOAConsumerException}.
 * <p>
 * Additionally, the consumer can indicate to the parser that aliases
 * have to be resolved before invoking any of the event methods in the consumer.
 * <p>
 * To chain HOAConsumers, see the {@link HOAIntermediate} interface.
 */
public interface HOAConsumer {
	/** 
	 * This function is called by the parser to query the consumer whether aliases should be
	 * resolved by the parser (return {@code true} or whether the consumer would like to
	 * see the aliases unresolved (return {@code false}). This function should always return
	 * a constant value.
	 **/
	public boolean parserResolvesAliases();
	
	/** Called by the parser for the "HOA: version" item [mandatory, once]. */
	public void notifyHeaderStart(String version) throws HOAConsumerException;
	
	/** Called by the parser for the "States: int(numberOfStates)" item [optional, once]. */
	public void setNumberOfStates(int numberOfStates) throws HOAConsumerException;
	
	/** 
	 * Called by the parser for each "Start: state-conj" item [optional, multiple]. 
	 * @param stateConjunction a list of state indizes, interpreted as a conjunction
	 **/
	public void addStartStates(List<Integer> stateConjunction) throws HOAConsumerException;
	
	/**
	 * Called by the parser for each "Alias: alias-def" item [optional, multiple].
	 * Will be called no matter the return value of {@code parserResolvesAliases()}.
	 * 
	 *  @param name the alias name (without @)
	 *  @param labelExpr a boolean expression over labels
	 **/
	public void addAlias(String name, BooleanExpression<AtomLabel> labelExpr) throws HOAConsumerException;
	
	/**
	 * Called by the parser for the "AP: ap-def" item [optional, once].
	 * @param aps the list of atomic propositions
	 */
	public void setAPs(List<String> aps) throws HOAConsumerException;
	
	/**
	 * Called by the parser for the "Acceptance: acceptance-def" item [mandatory, once].
	 * @param numberOfSets the number of acceptance sets used to tag state / transition acceptance
	 * @param accExpr a boolean expression over acceptance atoms
	 **/
	public void setAcceptanceCondition(int numberOfSets, BooleanExpression<AtomAcceptance> accExpr) throws HOAConsumerException;
	
	/** 
	 * Called by the parser for each "acc-name: ..." item [optional, multiple].
	 * @param name the provided name
	 * @param extraInfo the additional information for this item
	 * */
	public void provideAcceptanceName(String name, List<Object> extraInfo) throws HOAConsumerException;
	
	/**
	 * Called by the parser for the "name: ..." item [optional, once].
	 **/
	public void setName(String name) throws HOAConsumerException;
	
	/**
	 * Called by the parser for the "tool: ..." item [optional, once].
	 * @param name the tool name
	 * @param version the tool version ({@code null} if not provided)
	 **/
	 public void setTool(String name, String version) throws HOAConsumerException;
	
	/**
	 * Called by the parser for the "properties: ..." item [optional, multiple].
	 * @param properties a list of properties
	 */
	public void addProperties(List<String> properties) throws HOAConsumerException;
	
	/** 
	 * Called by the parser for each unknown header item [optional, multiple].
	 * @param name the name of the header (without ':')
	 * @param content a list of extra information provided by the header
	 */
	public void addMiscHeader(String name, List<Object> content) throws HOAConsumerException;
	
	/**
	 * Called by the parser to notify that the BODY of the automaton has started [mandatory, once].
	 */
	public void notifyBodyStart() throws HOAConsumerException;
	
	/** 
	 * Called by the parser for each "State: ..." item [multiple]. 
	 * @param id the identifier for this state
	 * @param info an optional String providing additional information about the state ({@code null} if not provided)
	 * @param labelExpr an optional boolean expression over labels (state-labeled) ({@code null} if not provided) 
	 * @param accSignature an optional list of acceptance set indizes (state-labeled acceptance) ({@code null} if not provided)
	 */
	public void addState(int id, String info, BooleanExpression<AtomLabel> labelExpr, List<Integer> accSignature) throws HOAConsumerException;
	
	/** 
	 * Called by the parser for each implicit edge definition [multiple], i.e.,
	 * where the edge label is deduced from the index of the edge.
	 * <br/>
	 * If the edges are provided in implicit form, after every {@code addState()} there should be 2^|AP| calls to
	 * {@code addEdgeImplicit}. The corresponding boolean expression over labels / BitSet 
	 * can be obtained by calling BooleanExpression.fromImplicit(i-1) for the i-th call of this function per state. 
	 * 
	 * @param stateId the index of the 'from' state
	 * @param conjSuccessors a list of successor state indizes, interpreted as a conjunction 
	 * @param accSignature an optional list of acceptance set indizes (transition-labeled acceptance) ({@code null} if not provided)
	 */
	
	public void addEdgeImplicit(int stateId, List<Integer> conjSuccessors, List<Integer> accSignature) throws HOAConsumerException;
	
	/**
	 * Called by the parser for each explicit edge definition [optional, multiple], i.e.,
	 * where the label is either specified for the edge or as a state-label.
	 * <br/>
	 * @param stateId the index of the 'from' state
	 * @param labelExpr a boolean expression over labels ({@code null} if none provided, only in case of state-labeled states)  
	 * @param conjSuccessors a list of successors state indizes, interpreted as a conjunction 
	 * @param accSignature an optional list of acceptance set indizes (transition-labeled acceptance) ({@code null} if none provided)
	 */
	public void addEdgeWithLabel(int stateId, BooleanExpression<AtomLabel> labelExpr, List<Integer> conjSuccessors, List<Integer> accSignature) throws HOAConsumerException;

	/**
	 * Called by the parser to notify the consumer that the definition for state {@code stateId}
	 * has ended [multiple].
	 */
	public void notifyEndOfState(int stateId) throws HOAConsumerException;
	
	/**
	 * Called by the parser to notify the consumer that the automata definition has ended [mandatory, once].
	 */
	public void notifyEnd() throws HOAConsumerException;
	
	/**
	 * Called by the parser to notify the consumer that an "ABORT" message has been encountered 
	 * (at any time, indicating error, the automaton should be discarded).
	 */
	public void notifyAbort();
	
	
	/**
	 * Is called whenever a condition is encountered that merits a (non-fatal) warning.
	 * The consumer is free to handle this situation as it wishes.
	 */
	public void notifyWarning(String warning) throws HOAConsumerException;
}
