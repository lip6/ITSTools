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
package fr.lip6.move.coloane.core.ui.figures.arcs;


import org.eclipse.draw2d.ColorConstants;

/**
 * Reset arc definition.<br>
 * This arc has a black diamond at its end.
 *
 * @author Yann Thierry-Mieg based on Jean-Baptiste Voron
 */
public class ResetArc extends DiamondArc {
	/**
	 * Constructor
	 */
	public ResetArc() {
		super(ColorConstants.black);
	}
}
