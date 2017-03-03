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
package fr.lip6.move.coloane.projects.its.checks;

import fr.lip6.move.coloane.projects.its.obs.ISimpleObserver;
import fr.lip6.move.coloane.projects.its.obs.SimpleObservable;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CTLFormulaDescription extends SimpleObservable implements
		IServiceResultProvider,ISimpleObserver {

	private String name = "formula";
	private String comments = "New formula";
	private ParsedCTLFormula ctlFormula;
	private CTLCheckService parent;

	
	public CTLFormulaDescription(String name, String formula, String comments,
			CTLCheckService parent) {
		this.name = name;
		this.comments = comments;
		this.parent = parent;
		this.ctlFormula = new ParsedCTLFormula(formula,parent.getParent());
		ctlFormula.addObserver(this);
	}

	public void setComments(String comments) {
		if (!this.comments.equals(comments)) {
			this.comments = comments;
			notifyObservers();
		}
	}

	public void setFormula(String formula) {
		if (!formula.equals(ctlFormula.getFormulaString())) {
			ctlFormula.setFormulaString(formula);
			notifyObservers();
		}
	}

	@Override
	public String toString() {
		return ctlFormula.getParsedFormula().toString();
	}

	public void setName(String name) {
		if (!this.name.equals(name)) {
			this.name = name;
			notifyObservers();
		}
	}

	public String getComments() {
		return comments;
	}

	public ParsedCTLFormula getCtlFormula() {
		return ctlFormula;
	}

	public String getName() {
		return name;
	}

	public CTLCheckService getParent() {
		return parent;
	}

	private List<ServiceResult> results = new LinkedList<ServiceResult>();

	public void addResult(ServiceResult serviceResult) {
		results.add(serviceResult);
		notifyObservers();
	}

	public Iterator<ServiceResult> iterator() {
		return results.iterator();
	}

	public void update() {
		notifyObservers();
	}

}
