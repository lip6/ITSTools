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
package fr.lip6.move.coloane.projects.its.syntax;

import fr.lip6.move.coloane.interfaces.model.IElement;
import fr.lip6.move.coloane.interfaces.model.IGraph;
import fr.lip6.move.coloane.interfaces.model.requests.IRequest;
import fr.lip6.move.coloane.interfaces.objects.result.IResult;
import fr.lip6.move.coloane.interfaces.objects.result.ISubResult;
import fr.lip6.move.coloane.interfaces.objects.result.ITip;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An adapter for a syntax error result.
 * @author Yann
 */
public final class Result implements IResult {

    private List<ISubResult> subResults = new ArrayList<ISubResult>();

	/**
	 * {@inheritDoc}
	 */
	public IGraph getNewGraph() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getRootName() {
		return "tpn";
	}

	/**
	 * {@inheritDoc}
	 */
	public String getServiceName() {
		return "tpn.syntax";
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ISubResult> getSubResults() {
		return subResults;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ITip> getTipsList() {
		return new ArrayList<ITip>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addChild(SubResult sr) {
		subResults.add(sr);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addSubResult(ISubResult) {
	}

	/**
	 * {@inheritDoc}
	 */
	public void addTip(ITip) {
	}

	/**
	 * {@inheritDoc}
	 */
	public void addTip(IElement , String , String ) {
	}

	/**
	 * {@inheritDoc}
	 */
	public void addDeltaRequest(IRequest ) {
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void addDeltaRequests(List<IRequest> requests) {
	}
	/**
	 * {@inheritDoc}
	 */
	public void setNewGraph(IGraph newGraph) {
	}
	/**
	 * {@inheritDoc}
	 */
	public void addNewGraphDeltaRequest(List<IRequest> requests) {
	}
	/**
	 * {@inheritDoc}
	 */
	public String getResultName() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<IRequest> getDeltaRequestsList() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<IRequest> getNewComputedGraphDeltaRequestsList() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public IGraph getNewComputedGraph() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<List<String>> getTextualResults() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<Integer, List<ITip>> getTips() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean shouldBeDisplayed() {
		return false;
	}

}
