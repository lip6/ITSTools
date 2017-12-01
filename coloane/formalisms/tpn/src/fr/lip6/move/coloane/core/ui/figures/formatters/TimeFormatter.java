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
package fr.lip6.move.coloane.core.ui.figures.formatters;

import fr.lip6.move.coloane.interfaces.model.IAttributeFormatter;
import fr.lip6.move.coloane.interfaces.model.IElement;

import java.text.DecimalFormatSymbols;

/**
 * A formatter to make pretty display of Eft and Lft including infinity symbol.
 *
 * @author Yann
 *
 */
public final class TimeFormatter implements IAttributeFormatter {

	/**
	 * {@inheritDoc}
	 */
	public String applyFormat(String value, IElement parentElement) {
		String earlyValue = parentElement.getAttribute("earliestFiringTime")
				.getValue();
		String latestValue =
			parentElement.getAttribute("latestFiringTime").getValue();
		String paren = "]";
		if (latestValue.equalsIgnoreCase("inf")) {
			if ("0".equalsIgnoreCase(earlyValue)) {
				// Don't display [0,inf[
				return null;
			}
			latestValue = new DecimalFormatSymbols().getInfinity();
			paren = "[";
		}

		if (! "".equals(earlyValue) && ! "".equals(latestValue)) {
			return "[" + earlyValue + "," + latestValue + paren;
		}
		return null;
	}

}
