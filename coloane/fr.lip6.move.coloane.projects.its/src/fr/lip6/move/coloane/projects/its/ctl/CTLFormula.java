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
package fr.lip6.move.coloane.projects.its.ctl;

/**
 * Interface for all CTL formulae syntactic elements.
 * @author Yann
 *
 */
public interface  CTLFormula {

	String CONSTANT = "Constant";
	String PREDICATE = "Predicate";
	String REFERENCE = "Reference";
	String AF = "AF";
	String AG = "AG";
	String AU = "AU";
	String AX = "AX";
	String EF = "EF";
	String EG = "EG";
	String EU = "EU";
	String EX = "EX";
	String NOT = "!";
	String AND = "*";
	String OR = "+";
	String XOR = "^";
	String EQUIV = "<->";
	String IMPLY = "->";
	
	/**
	 * Gives the code for this syntactic element.
	 * Constants defined in interface CTLFormula.
	 * @return a value from the set in CTLFormula interface.
	 */
	String getOperator();
	
	// Singleton constant instances
	CTLFormula TRUE = new CTLConstant(CTLConstant.TRUE);
	CTLFormula FALSE = new CTLConstant(CTLConstant.FALSE);
	CTLFormula DEAD = new CTLConstant(CTLConstant.DEAD);
	
}
