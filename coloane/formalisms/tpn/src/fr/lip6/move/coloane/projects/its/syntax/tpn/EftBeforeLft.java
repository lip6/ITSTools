/**
 * Copyright (c) 2006-2010 MoVe - Laboratoire d'Informatique de Paris 6 (LIP6).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jean-Baptiste VORON (LIP6) - Project Head / Initial contributor
 *   Clément DÉMOULINS (LIP6) - Project Manager
 *   Yann THIERRY-MIEG (LIP6)
 *
 * Official contacts:
 *   coloane@lip6.fr
 *   http://coloane.lip6.fr
 */
package fr.lip6.move.coloane.projects.its.syntax.tpn;

import fr.lip6.move.coloane.core.formalisms.checkers.CheckerResult;
import fr.lip6.move.coloane.core.model.factory.FormalismManager;
import fr.lip6.move.coloane.interfaces.formalism.ICheckerResult;
import fr.lip6.move.coloane.interfaces.formalism.IElementFormalism;
import fr.lip6.move.coloane.interfaces.formalism.IGraphFormalism;
import fr.lip6.move.coloane.interfaces.formalism.INodeChecker;
import fr.lip6.move.coloane.interfaces.model.IElement;
import fr.lip6.move.coloane.interfaces.model.INode;

import java.util.ArrayList;
import java.util.List;

/**
 * A syntax rule to check the EFT is before LFT.
 *
 * @author Yann
 *
 */
public final class EftBeforeLft implements INodeChecker {

	private List<IElementFormalism> ruleTypes;

	/**
	 * Ctor.
	 */
	public EftBeforeLft() {
		ruleTypes = new ArrayList<IElementFormalism>();
		IGraphFormalism tpn = FormalismManager.getInstance()
				.getFormalismByName("Time Petri Net").getRootGraph();
		ruleTypes.add(tpn.getElementFormalism("transition"));
	}

	/**
	 * {@inheritDoc}
	 */
	public ICheckerResult check(IElement elt) {
		String eft = elt.getAttribute("earliestFiringTime").getValue();
		String lft = elt.getAttribute("latestFiringTime").getValue();
		int eftv;
		try {
			eftv = Integer.parseInt(eft);
		} catch (NumberFormatException e) {

			// We should just bail out, it might be an arbitrary int expression with variables etc...
			return new CheckerResult(false, "");
			
			//String msg = "The attribute earliest firing time \"eft\" is not an integer.\n"
			//		+ "It is set to value \"" + eft + "\".";

			// SubResult sr = new SubResult();
			// sr.addAttributeOutline(elt.getId(), "earliestFiringTime");
			// sr.addObjectOutline(elt.getId());
			// sr.addTextualResults(msg);
			// result.addChild(sr);

			// return new CheckerResult(true, msg);
		}
		if (lft.equals("inf")) {
			return new CheckerResult(false, "");
		}
		int lftv;
		try {
			lftv = Integer.parseInt(lft);
		} catch (NumberFormatException e) {
			// SubResult sr = new SubResult();
			// sr.addAttributeOutline(elt.getId(), "earliestFiringTime");
			// sr.addAttributeOutline(elt.getId(), "latestFiringTime");
			// sr.addObjectOutline(elt.getId());
			// result.addChild(sr);

			// We should just bail out, it might be an arbitrary int expression
			return new CheckerResult(false, "");
			
//			String msg = "Latest firing time for transition should be an integer";
//			return new CheckerResult(true, msg);
		}
		if (eftv > lftv) {
			// SubResult sr = new SubResult();
			// sr.addAttributeOutline(elt.getId(), "earliestFiringTime");
			// sr.addAttributeOutline(elt.getId(), "latestFiringTime");
			// sr.addObjectOutline(elt.getId());
			// result.addChild(sr);

			String msg = "Earliest firing time should be lesser or equal to Latest firing time.";
			return new CheckerResult(true, msg);
		}

		return new CheckerResult(false, "");
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return "Earliest firing time before Latest firing time.";
	}

	/**
	 * {@inheritDoc}
	 */
	public Iterable<IElementFormalism> getRuleTypes() {
		return ruleTypes;
	}

	/**
	 * {@inheritDoc}
	 */
	public ICheckerResult performCheck(INode node) {
		return check(node);
	}

}
