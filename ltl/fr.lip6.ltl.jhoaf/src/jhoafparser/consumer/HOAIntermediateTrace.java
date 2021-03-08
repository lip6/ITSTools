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

import java.io.PrintStream;
import java.util.List;

import jhoafparser.ast.*;

/**
 * Traces the function calls to HOAConsumer, prints function name and arguments to stream.
 */
public class HOAIntermediateTrace extends HOAIntermediate
{
	/** The logging stream */
	private PrintStream log;

	/** Trace to standard output */
	public HOAIntermediateTrace(HOAConsumer next)
	{
		this(next, System.out);
	}

	/** Trace to log */
	public HOAIntermediateTrace(HOAConsumer next, PrintStream log)
	{
		super(next);
		this.log = log;
	}

	/**
	 * Return a factory for building HOAIntermediateTrace (tracing to standard out)
	 * using the provided HOAConsumerFactory to construct the next HOAConsumer.
	 **/
	public static HOAConsumerFactory getFactory(final HOAConsumerFactory next)
	{
		return new HOAConsumerFactory() {
			@Override
			public HOAConsumer getNewHOAConsumer()
			{
				return new HOAIntermediateTrace(next.getNewHOAConsumer());
			}
		};
	}

	/**
	 * Return a factory for building HOAIntermediateTrace (tracing to log),
	 * using the provided HOAConsumerFactory to construct the next HOAConsumer.
	 **/
	public static HOAConsumerFactory getFactory(final HOAConsumerFactory next, final PrintStream log)
	{
		return new HOAConsumerFactory() {
			@Override
			public HOAConsumer getNewHOAConsumer()
			{
				return new HOAIntermediateTrace(next.getNewHOAConsumer(), log);
			}
		};
	}

	/** Trace function call */
	private void trace(String function, Object... namesAndArguments)
	{
		log.println("=> " + function);
		for (int i = 0; i < namesAndArguments.length; ) {
			log.print("      ");
			log.print(namesAndArguments[i++]);
			log.print(" = ");
			Object argument = namesAndArguments[i++];

			if (argument == null) {
				log.print("null");
			} else if (argument instanceof String) {
				log.print("\"" + argument + "\"");
			} else {
				log.print(argument.toString());
			}
			log.println();
		}
	}

	@Override
	public boolean parserResolvesAliases()
	{
		return next.parserResolvesAliases();
	}

	@Override
	public void notifyHeaderStart(String version) throws HOAConsumerException
	{
		trace("notifyHeaderStart",
		      "version", version);

		next.notifyHeaderStart(version);
	}

	@Override
	public void setNumberOfStates(int numberOfStates) throws HOAConsumerException
	{
		trace("setNumberOfStates",
		      "numberOfStates", numberOfStates);

		next.setNumberOfStates(numberOfStates);
	}

	@Override
	public void addStartStates(List<Integer> stateConjunction) throws HOAConsumerException
	{
		trace("addStartStates",
		      "stateConjunction", stateConjunction);

		next.addStartStates(stateConjunction);
	}

	@Override
	public void addAlias(String name, BooleanExpression<AtomLabel> labelExpr) throws HOAConsumerException
	{
		trace("addAlias",
		      "name", name,
		      "labelExpr", labelExpr);

		next.addAlias(name, labelExpr);
	}

	@Override
	public void setAPs(List<String> aps) throws HOAConsumerException
	{
		trace("setAPs",
		      "aps", aps);

		next.setAPs(aps);
	}

	@Override
	public void setAcceptanceCondition(int numberOfSets, BooleanExpression<AtomAcceptance> accExpr) throws HOAConsumerException
	{
		trace("setAcceptanceCondition",
		      "numberOfSets", numberOfSets,
		      "accExpr", accExpr);

		next.setAcceptanceCondition(numberOfSets, accExpr);
	}

	@Override
	public void provideAcceptanceName(String name, List<Object> extraInfo) throws HOAConsumerException
	{
		trace("provideAcceptanceName",
		      "name", name,
		      "extraInfo", extraInfo);

		next.provideAcceptanceName(name,  extraInfo);
	}

	@Override
	public void setName(String name) throws HOAConsumerException
	{
		trace("setName",
		      "name", name);

		next.setName(name);

	}

	@Override
	public void setTool(String name, String version) throws HOAConsumerException
	{
		trace("setTool",
		       "name", name,
		       "version", version);

		next.setTool(name, version);
	}

	@Override
	public void addProperties(List<String> properties) throws HOAConsumerException
	{
		trace("addProperties",
		      "properties", properties);

		next.addProperties(properties);
	}

	@Override
	public void addMiscHeader(String name, List<Object> content) throws HOAConsumerException
	{
		trace("addMiscHeader",
		      "name", name,
		      "content", content);

		next.addMiscHeader(name, content);

	}

	@Override
	public void notifyBodyStart() throws HOAConsumerException
	{
		trace("notifyBodyStart");

		next.notifyBodyStart();
	}

	@Override
	public void addState(int id, String info, BooleanExpression<AtomLabel> labelExpr, List<Integer> accSignature) throws HOAConsumerException
	{
		trace("addState",
		      "id", id,
		      "info", info,
		      "labelExpr", labelExpr,
		      "accSignature", accSignature);

		next.addState(id, info, labelExpr, accSignature);
	}

	@Override
	public void addEdgeImplicit(int stateId, List<Integer> conjSuccessors, List<Integer> accSignature) throws HOAConsumerException
	{
		trace("addEdgeImplicit",
		      "stateId", stateId,
		      "conjSuccessors", conjSuccessors,
		      "accSignature", accSignature);

		next.addEdgeImplicit(stateId, conjSuccessors, accSignature);
	}

	@Override
	public void addEdgeWithLabel(int stateId, BooleanExpression<AtomLabel> labelExpr, List<Integer> conjSuccessors, List<Integer> accSignature)
			throws HOAConsumerException
	{
		trace("addEdgeWithLabel",
		      "stateId", stateId,
		      "labelExpr", labelExpr,
		      "conjSuccessors", conjSuccessors,
		      "accSignature", accSignature);

		next.addEdgeWithLabel(stateId, labelExpr, conjSuccessors, accSignature);
	}

	@Override
	public void notifyEndOfState(int stateId) throws HOAConsumerException
	{
		trace("notifyEndOfState",
		      "stateId", stateId);

		next.notifyEndOfState(stateId);
	}

	@Override
	public void notifyEnd() throws HOAConsumerException
	{
		trace("notifyEnd");

		next.notifyEnd();
	}

	@Override
	public void notifyAbort()
	{
		trace("notifyAbort");

		next.notifyAbort();
	}
	
	@Override
	public void notifyWarning(String warning) throws HOAConsumerException
	{
		trace("notifyWarning",
		      "warning", warning);

		next.notifyWarning(warning);
	}

}
