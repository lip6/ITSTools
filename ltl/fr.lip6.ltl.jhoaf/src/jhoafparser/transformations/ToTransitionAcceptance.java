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
import java.util.List;

import jhoafparser.ast.*;
import jhoafparser.consumer.HOAConsumer;
import jhoafparser.consumer.HOAConsumerException;
import jhoafparser.consumer.HOAConsumerStore;
import jhoafparser.consumer.HOAIntermediate;
import jhoafparser.storage.StoredAutomaton;
import jhoafparser.storage.StoredAutomatonManipulator;

/**
 * Convert automaton to transition-based acceptance (on-the-fly).
 * The procedure is quite simple: Whenever there is state-based acceptance
 * we have to push it to all outgoing edges instead.
 **/
public class ToTransitionAcceptance extends HOAIntermediate
{
	/** Temporary storage of the acceptance signature of the current state */
	private List<Integer> currentStateSignature;

	/** Constructor with next consumer */
	public ToTransitionAcceptance(HOAConsumer next)
	{
		super(next);
	}

	/** Constructor a stored automaton manipulator from this consumer */
	public static StoredAutomatonManipulator getStoredAutomatonManipulator() {
		return new StoredAutomatonManipulator() {
			@Override
			public StoredAutomaton manipulate(StoredAutomaton aut) throws HOAConsumerException
			{
				HOAConsumerStore storeTransformed = new HOAConsumerStore();
				HOAConsumer transform = new ToTransitionAcceptance(storeTransformed);

				aut.feedToConsumer(transform);

				return storeTransformed.getStoredAutomaton();
			}
		};
	}

	@Override
	public void addProperties(List<String> properties) throws HOAConsumerException
	{
		List<String> filtered = new ArrayList<String>();
		for (String property : properties) {
			if (property.equals("state-acc") ||
			    property.equals("trans-acc")) {
				// don't add to filtered
			} else {
				filtered.add(property);
			}
		}

		next.addProperties(filtered);
	}
	
	@Override
	public void notifyBodyStart() throws HOAConsumerException
	{
		List<String> property = new ArrayList<String>(1);
		property.add("trans-acc");
		next.addProperties(property);
	}

	@Override
	public void addState(int id, String info, BooleanExpression<AtomLabel> labelExpr, List<Integer> accSignature) throws HOAConsumerException
	{
		currentStateSignature = accSignature;

		// don't pass on acceptance signature
		next.addState(id, info, labelExpr, null);
	}

	@Override
	public void addEdgeImplicit(int stateId, List<Integer> conjSuccessors, List<Integer> accSignature) throws HOAConsumerException
	{
		List<Integer> transAccSignature;

		if (accSignature != null && currentStateSignature != null) {
			transAccSignature = new ArrayList<Integer>(accSignature);
			transAccSignature.addAll(accSignature);
		} else {
			transAccSignature = (accSignature != null ? accSignature : currentStateSignature);
		}

		next.addEdgeImplicit(stateId, conjSuccessors, transAccSignature);
	}

	@Override
	public void addEdgeWithLabel(int stateId, BooleanExpression<AtomLabel> labelExpr, List<Integer> conjSuccessors, List<Integer> accSignature)
			throws HOAConsumerException
	{
		List<Integer> transAccSignature;

		if (accSignature != null && currentStateSignature != null) {
			transAccSignature = new ArrayList<Integer>(accSignature);
			transAccSignature.addAll(accSignature);
		} else {
			transAccSignature = (accSignature != null ? accSignature : currentStateSignature);
		}

		next.addEdgeWithLabel(stateId, labelExpr, conjSuccessors, transAccSignature);
	}

}
