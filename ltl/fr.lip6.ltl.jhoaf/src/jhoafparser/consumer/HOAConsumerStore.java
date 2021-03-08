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
import jhoafparser.storage.StoredAutomaton;
import jhoafparser.storage.StoredEdgeImplicit;
import jhoafparser.storage.StoredEdgeWithLabel;
import jhoafparser.storage.StoredState;

/** A consumer that stores the automaton into a {@link StoredAutomaton}. */
public class HOAConsumerStore implements HOAConsumer
{
	/** The stored automaton. */
	private StoredAutomaton storedAutomaton;
	/** True if the end of parsing has been reached, i.e., the automaton is fully constructed. */
	private boolean done = false;

	/** Constructor */
	public HOAConsumerStore() {
		storedAutomaton = new StoredAutomaton();
	}

	/** Get the stored automaton. Can only be called after notifyEnd() has been invoked. */
	public StoredAutomaton getStoredAutomaton() {
		if (!done) {
			throw new UnsupportedOperationException("Automaton not fully constructed");
		}
		return storedAutomaton;
	}

	/** Replace the stored automaton with the argument. */
	public void setStoredAutomaton(StoredAutomaton aut) {
		this.storedAutomaton = aut;
	}

	@Override
	public boolean parserResolvesAliases()
	{
		return false;
	}

	@Override
	public void notifyHeaderStart(String version) throws HOAConsumerException
	{
		// NOP
	}

	@Override
	public void setNumberOfStates(int numberOfStates) throws HOAConsumerException
	{
		storedAutomaton.getStoredHeader().setNumberOfStates(numberOfStates);
	}

	@Override
	public void addStartStates(List<Integer> stateConjunction) throws HOAConsumerException
	{
		storedAutomaton.getStoredHeader().addStartStates(stateConjunction);
	}

	@Override
	public void addAlias(String name, BooleanExpression<AtomLabel> labelExpr) throws HOAConsumerException
	{
		storedAutomaton.getStoredHeader().addAlias(name, storedAutomaton.findOrAdd(labelExpr));
	}

	@Override
	public void setAPs(List<String> aps) throws HOAConsumerException
	{
		storedAutomaton.getStoredHeader().setAPs(aps);
	}

	@Override
	public void setAcceptanceCondition(int numberOfSets, BooleanExpression<AtomAcceptance> accExpr) throws HOAConsumerException
	{
		storedAutomaton.getStoredHeader().setAcceptanceCondition(numberOfSets, accExpr);
	}

	@Override
	public void provideAcceptanceName(String name, List<Object> extraInfo) throws HOAConsumerException
	{
		storedAutomaton.getStoredHeader().provideAcceptanceName(name, extraInfo);
	}

	@Override
	public void setName(String name) throws HOAConsumerException
	{
		storedAutomaton.getStoredHeader().setName(name);
	}

	@Override
	public void setTool(String name, String version) throws HOAConsumerException
	{
		storedAutomaton.getStoredHeader().setTool(name, version);
	}

	@Override
	public void addProperties(List<String> properties) throws HOAConsumerException
	{
		storedAutomaton.getStoredHeader().addProperties(properties);
	}

	@Override
	public void addMiscHeader(String name, List<Object> content) throws HOAConsumerException
	{
		storedAutomaton.getStoredHeader().addMiscHeader(name, content);

	}

	@Override
	public void notifyBodyStart() throws HOAConsumerException
	{
		// NOP
	}

	@Override
	public void addState(int id, String info, BooleanExpression<AtomLabel> labelExpr, List<Integer> accSignature) throws HOAConsumerException
	{
		storedAutomaton.addState(new StoredState(id, info, storedAutomaton.findOrAdd(labelExpr), storedAutomaton.findOrAdd(accSignature)));
	}

	@Override
	public void addEdgeImplicit(int stateId, List<Integer> conjSuccessors, List<Integer> accSignature) throws HOAConsumerException
	{
		storedAutomaton.addEdgeImplicit(stateId, new StoredEdgeImplicit(storedAutomaton.findOrAdd(conjSuccessors), storedAutomaton.findOrAdd(accSignature)));
	}

	@Override
	public void addEdgeWithLabel(int stateId, BooleanExpression<AtomLabel> labelExpr, List<Integer> conjSuccessors, List<Integer> accSignature)
			throws HOAConsumerException
	{
		storedAutomaton.addEdgeWithLabel(stateId, new StoredEdgeWithLabel(storedAutomaton.findOrAdd(labelExpr), storedAutomaton.findOrAdd(conjSuccessors), storedAutomaton.findOrAdd(accSignature)));
	}

	@Override
	public void notifyEndOfState(int stateId) throws HOAConsumerException
	{
		// NOP
	}

	@Override
	public void notifyEnd() throws HOAConsumerException
	{
		done = true;
	}

	@Override
	public void notifyAbort()
	{
		done = true;
		storedAutomaton = null;
	}

	@Override
	public void notifyWarning(String warning) throws HOAConsumerException
	{
		// NOP
	}

}
