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

import java.util.List;

import jhoafparser.analysis.AcceptanceSimplify;
import jhoafparser.ast.*;
import jhoafparser.consumer.HOAConsumer;
import jhoafparser.consumer.HOAConsumerException;
import jhoafparser.consumer.HOAConsumerFactory;
import jhoafparser.consumer.HOAIntermediate;

/** Convert acceptance to DNF */
public class DNFAcceptance extends HOAIntermediate
{
	/** Constructor, next consumer */
	public DNFAcceptance(HOAConsumer next)
	{
		super(next);
	}

	/**
	 * Return a factory for building DNFAcceptance.
	 * using the provided HOAConsumerFactory to construct the next HOAConsumer.
	 **/
	public static HOAConsumerFactory getFactory(final HOAConsumerFactory next)
	{
		return new HOAConsumerFactory() {
			@Override
			public HOAConsumer getNewHOAConsumer()
			{
				return new DNFAcceptance(next.getNewHOAConsumer());
			}
		};
	}

	@Override
	public void setAcceptanceCondition(int numberOfSets, BooleanExpression<AtomAcceptance> accExpr) throws HOAConsumerException
	{
		next.setAcceptanceCondition(numberOfSets, AcceptanceSimplify.toDNFCondition(accExpr));
	}

	@Override
	public void provideAcceptanceName(String name, List<Object> extraInfo) throws HOAConsumerException {
		// we have to filter acc-name, because we have perhaps modified the Acceptance-header
		// do nothing
	}

}
