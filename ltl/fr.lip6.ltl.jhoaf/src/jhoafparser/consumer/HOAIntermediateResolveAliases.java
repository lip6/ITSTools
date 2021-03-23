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

import java.util.HashMap;
import java.util.List;

import jhoafparser.ast.*;

/**
 * A {@code HOAIntermediate} that resolves aliases on-the-fly.
 * <p>
 * Stores the definition of aliases from the header and resolves
 * any aliases in label expressions before passing the events to
 * the next consumer.
 * <p>
 */
public class HOAIntermediateResolveAliases extends HOAIntermediate
{
	/** The map storing alias definitions */
	HashMap<String, BooleanExpression<AtomLabel>> aliases = new HashMap<String, BooleanExpression<AtomLabel>>();

	/** Constructor, with next consumer */
	public HOAIntermediateResolveAliases(HOAConsumer next)
	{
		super(next);
	}

	/**
	 * Return a factory for building HOAIntermediateResolveAliases,
	 * using the provided HOAConsumerFactory to construct the next HOAConsumer.
	 **/
	public static HOAConsumerFactory getFactory(final HOAConsumerFactory next)
	{
		return new HOAConsumerFactory() {
			@Override
			public HOAConsumer getNewHOAConsumer()
			{
				return new HOAIntermediateResolveAliases(next.getNewHOAConsumer());
			}
		};
	}

	/** Returns true if {@code labelExpr} contains any unresolved aliases */
	private boolean containsAliases(BooleanExpression<AtomLabel> labelExpr) throws HOAConsumerException {
		switch (labelExpr.getType()) {
		case EXP_FALSE:
		case EXP_TRUE:
			return false;
		case EXP_AND:
		case EXP_OR:
			if (containsAliases(labelExpr.getLeft())) return true;
			if (containsAliases(labelExpr.getRight())) return true;
			return false;
		case EXP_NOT:
			if (containsAliases(labelExpr.getLeft())) return true;
			return false;
		case EXP_ATOM:
			return labelExpr.getAtom().isAlias();
		}
		throw new HOAConsumerException("Unhandled boolean expression type");
	}

	/**
	 * Check that all aliases used in {@code labelExpr} are defined,
	 * otherwise throw {@code HOAConsumerException}
	 **/
	private void checkAliasDefinedness(BooleanExpression<AtomLabel> labelExpr) throws HOAConsumerException {
		switch (labelExpr.getType()) {
		case EXP_FALSE:
		case EXP_TRUE:
			return;
		case EXP_AND:
		case EXP_OR:
			checkAliasDefinedness(labelExpr.getLeft());
			checkAliasDefinedness(labelExpr.getRight());
			return;
		case EXP_NOT:
			checkAliasDefinedness(labelExpr.getLeft());
			return;
		case EXP_ATOM:
			if (labelExpr.getAtom().isAlias()) {
				String aliasName = labelExpr.getAtom().getAliasName();
				if (!aliases.containsKey(aliasName)) {
					throw new HOAConsumerException("Expression "+labelExpr+" uses undefined alias @"+aliasName);
				}
			}
			return;
		}
		throw new HOAConsumerException("Unhandled boolean expression type");
	}

	/**
	 * Return a label expression that is obtained by replacing all aliases from the argument expression.
	 **/
	private BooleanExpression<AtomLabel> resolveAliases(BooleanExpression<AtomLabel> labelExpr) throws HOAConsumerException
	{
		switch (labelExpr.getType()) {
		case EXP_TRUE:
		case EXP_FALSE:
			return labelExpr;
		case EXP_AND:
		case EXP_OR:
			return new BooleanExpression<AtomLabel>(labelExpr.getType(),
			                                        resolveAliases(labelExpr.getLeft()),
			                                        resolveAliases(labelExpr.getRight()));
		case EXP_NOT:
			return new BooleanExpression<AtomLabel>(labelExpr.getType(),
                                                    resolveAliases(labelExpr.getLeft()),
                                                    null);
		case EXP_ATOM:
			if (!labelExpr.getAtom().isAlias()) {
				return labelExpr;
			} else {
				BooleanExpression<AtomLabel> resolved = aliases.get(labelExpr.getAtom().getAliasName());
				if (resolved == null) {
					throw new HOAConsumerException("Can not resolve alias @"+labelExpr.getAtom().getAliasName());
				}
				if (containsAliases(resolved)) {
					resolved = resolveAliases(resolved);
				}
				return resolved;
			}
		}

		throw new HOAConsumerException("Unhandled boolean expression type");
	}

	@Override
	public void addAlias(String name, BooleanExpression<AtomLabel> labelExpr) throws HOAConsumerException
	{
		if (aliases.containsKey(name)) {
			throw new HOAConsumerException("Alias "+name+" is defined multiple times!");
		}

		if (containsAliases(labelExpr)) {
			// check that all the aliases in the expression are already defined 
			checkAliasDefinedness(labelExpr);

			// resolve aliases in the expression
			labelExpr = resolveAliases(labelExpr);
		}

		aliases.put(name, labelExpr);
	}

	@Override
	public void addState(int id, String info, BooleanExpression<AtomLabel> labelExpr, List<Integer> accSignature) throws HOAConsumerException
	{
		if (labelExpr != null && containsAliases(labelExpr)) {
			labelExpr = resolveAliases(labelExpr);
		}
		next.addState(id, info, labelExpr, accSignature);
	}

	@Override
	public void addEdgeWithLabel(int stateId, BooleanExpression<AtomLabel> labelExpr, List<Integer> conjSuccessors, List<Integer> accSignature)
			throws HOAConsumerException
	{
		if (labelExpr != null && containsAliases(labelExpr)) {
			labelExpr = resolveAliases(labelExpr);
		}

		next.addEdgeWithLabel(stateId, labelExpr, conjSuccessors, accSignature);
	}
}
