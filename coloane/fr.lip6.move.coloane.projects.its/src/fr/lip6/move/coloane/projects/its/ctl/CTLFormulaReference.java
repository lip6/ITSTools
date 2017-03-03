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
 * A reference to an existing formula, syntax : @form.
 * @author Yann
 *
 */
public final class CTLFormulaReference implements CTLFormula {

	private String formulaName;
	private String formulaDescription;

	/**
	 * Referenced formula name.
	 * @param formulaName the name of the referenced formula.
	 */
	public CTLFormulaReference(String formulaName) {
		this.formulaName = formulaName;
	}

	/**
	 * The name of the referenced formula.
	 * @return The name of the referenced formula.
	 */
	public String getFormulaName() {
		return formulaName;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getOperator() {
		return CTLFormula.REFERENCE;
	}

	/**
	 * Position subformula body text, i.e. contents of subformula.
	 * @param subformula string of subformula.
	 */
	public void setFormulaDescription(String subformula) {
		this.formulaDescription = subformula.replace(";", "");
	}
	
	/**
	 * Returns the subformula body between braces.
	 * @return a nicely formatted subformula or true if no formula description found.
	 */
	@Override
	public String toString() {
		if (formulaDescription != null) {
			return "(" + formulaDescription + ")";
		}
		return "TRUE";
	}

}
