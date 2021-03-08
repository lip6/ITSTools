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
 * This {@code HOAConsumer} provides a "no-operation" end-point
 * for a chain of {@code HOAIntermediate}.
 */
public class HOAConsumerNull implements HOAConsumer {

	/** Return a factory for building HOAConsumerNull */
	public static HOAConsumerFactory getFactory()
	{
		return new HOAConsumerFactory() {
			@Override
			public HOAConsumer getNewHOAConsumer()
			{
				return new HOAConsumerNull();
			}
		};
	}

	@Override
	public boolean parserResolvesAliases() {
		return false;
	}

	@Override
	public void notifyHeaderStart(String version) {
	}

	@Override
	public void setNumberOfStates(int numberOfStates) {
	}

	@Override
	public void addStartStates(List<Integer> stateConjunction) {
	}

	@Override
	public void addAlias(String name, BooleanExpression<AtomLabel> labelExpr) {
	}

	@Override
	public void setAPs(List<String> aps) {
	}

	@Override
	public void setAcceptanceCondition(int numberOfSets,
			BooleanExpression<AtomAcceptance> accExpr) {
	}

	@Override
	public void provideAcceptanceName(String name, List<Object> extraInfo) {
	}

	@Override
	public void setName(String name) {
	}

	@Override
	public void setTool(String name, String version) {
	}

	@Override
	public void addProperties(List<String> properties) {
	}

	@Override
	public void addMiscHeader(String name, List<Object> content) {
	}

	@Override
	public void notifyBodyStart() {
	}

	@Override
	public void addState(int id, String info, BooleanExpression<AtomLabel> labelExpr,
			List<Integer> accSignature) {
	}

	@Override
	public void addEdgeImplicit(int stateId, List<Integer> conjSuccessors,
			List<Integer> accSignature) {
	}

	@Override
	public void addEdgeWithLabel(int stateId, BooleanExpression<AtomLabel> labelExpr,
			List<Integer> conjSuccessors, List<Integer> accSignature) {
	}

	@Override
	public void notifyEndOfState(int stateId) throws HOAConsumerException
	{
	}

	@Override
	public void notifyEnd() {
	}

	@Override
	public void notifyAbort() {
	}

	@Override
	public void notifyWarning(String warning) throws HOAConsumerException
	{
	}

}
