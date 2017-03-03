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
import fr.lip6.move.coloane.interfaces.objects.result.ISubResult;
import fr.lip6.move.coloane.interfaces.objects.result.ITip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An adapter for a syntax error.
 * @author Yann
 *
 */
public final class SubResult implements ISubResult {

	private List<String> textualResults = new ArrayList<String>();
	private Map<Integer, List<String>> attributesOutline = new HashMap<Integer, List<String>>();
	private List<ISubResult> children = new ArrayList<ISubResult>();
	private String name = "";
	private List<Integer> objectsDesignation = new ArrayList<Integer>();
	private List<Integer> objectsOutline = new ArrayList<Integer>();
	private int type;

	/**
	 * {@inheritDoc}
	 */
	public Map<Integer, List<String>> getAttributesOutline() {
		return attributesOutline;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ISubResult> getChildren() {
		return children;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter for name
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * {@inheritDoc}
	 */
	public List<Integer> getObjectsDesignation() {
		return objectsDesignation;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Integer> getObjectsOutline() {
		return objectsOutline;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<List<String>> getTextualResults() {
		return Collections.singletonList(textualResults);
	}

	/**
	 * {@inheritDoc}
	 */
	public int getType() {
		return type;
	}

	/**
	 * add an object attribute to the list of target objects
	 * @param id id of the owning element
	 * @param string attribute that is targeted
	 */
	public void addAttributeOutline(int id, String string) {
		if (!attributesOutline.containsKey(id)) {
			attributesOutline.put(id, new ArrayList<String>());
			objectsDesignation.add(id);
		}
		attributesOutline.get(id).add(string);
	}

	/**
	 * add an object to the list of target objects
	 * @param id id of the owning element
	 */
	public void addObjectOutline(int id) {
		objectsOutline.add(id);
	}

	/**
	 * Add a textual result.
	 * @param string the text to add
	 */
	public void addTextualResults(String string) {
		textualResults.add(string);
	}

	@Override
	public void addTip(ITip tip) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTip(IElement object, String name, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTextualResult(String... result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addAttributeOutline(Integer objectId, String attributeName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addAttributeOutline(IElement object, String attributeName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addObjectDesignation(Integer objectId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addObjectDesignation(IElement object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addObjectOutline(Integer objectId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addObjectOutline(IElement object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSubResult(ISubResult subResult) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSubResultName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInformation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ISubResult> getSubResults() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, List<ITip>> getTips() {
		// TODO Auto-generated method stub
		return null;
	}

}
