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

/**
 * A formatter to make selective display of tokens : only show if >= 10 or it is not a recognizable integer.
 *
 * @author Yann
 *
 */
public final class MarkingFormatter implements IAttributeFormatter {

	/**
	 * {@inheritDoc}
	 */
	public String applyFormat(String value, IElement parentElement) {
		String smark = parentElement.getAttribute("marking").getValue();
		try {
			int mark = Integer.parseInt(smark);
			if (mark > 0 && mark < 10) {
				// No display, the tokens are drawn by the place.
				return null;
			}
		} catch (NumberFormatException e) {
			// go on to return smark
		}
		return smark;		
	}

}
