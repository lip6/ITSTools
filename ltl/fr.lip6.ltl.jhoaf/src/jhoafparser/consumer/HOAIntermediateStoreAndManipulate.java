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

import java.util.ArrayList;
import java.util.List;

import jhoafparser.storage.StoredAutomaton;
import jhoafparser.storage.StoredAutomatonManipulator;

/**
 * A {@code HOAIntermediate} that stores the automaton, runs a number of
 * {@code StoredAutomatonManipulator} and (optionally) feeds the 
 * manipulated automaton to the next {@code HOAConsumer}/{@code HOAIntermediate}.
 * <p>>
 * Provides access to the stored automaton via getStoredAutomaton (inherited from HOAConsumerStore). 
 */
public class HOAIntermediateStoreAndManipulate extends HOAConsumerStore
{
	/** The list (ordered) of manipulators to call */
	private List<StoredAutomatonManipulator> manipulators = new ArrayList<StoredAutomatonManipulator>();
	
	/** The next consumer */
	private HOAConsumer next;

	/** Constructor.
	 * @param manipulators variable-length StoredAutomatonManipulator[] of the manipulators
	 */
	public HOAIntermediateStoreAndManipulate(StoredAutomatonManipulator... manipulators) {
		this(null, manipulators);
	}

	/** Constructor.
	 * @param next the next consumer
	 * @param manipulators variable-length StoredAutomatonManipulator[] of the manipulators
	 */
	public HOAIntermediateStoreAndManipulate(HOAConsumer next, StoredAutomatonManipulator... manipulators)
	{
		this.next = next;
		for (StoredAutomatonManipulator manipulator : manipulators) {
			this.manipulators.add(manipulator);
		}
	}

	/** Get a factory. */
	public static HOAConsumerFactory getFactory(final HOAConsumerFactory next, final StoredAutomatonManipulator... manipulators) {
		return new HOAConsumerFactory() {
			@Override
			public HOAConsumer getNewHOAConsumer()
			{
				return new HOAIntermediateStoreAndManipulate(next.getNewHOAConsumer(), manipulators);
			}
			
		};
	}

	/** Add another manipulator to the end of the manipulators. */
	public void addManipulator(StoredAutomatonManipulator manipulator) {
		manipulators.add(manipulator);
	}

	@Override
	public void notifyEnd() throws HOAConsumerException {
		super.notifyEnd();
		StoredAutomaton aut = this.getStoredAutomaton();

		// Run manipulators, store pack the automaton
		for (StoredAutomatonManipulator manipulator : manipulators) {
			aut = manipulator.manipulate(aut);
			this.setStoredAutomaton(aut);
		}

		if (next != null) {
			aut.feedToConsumer(next);
		}
	}
}
