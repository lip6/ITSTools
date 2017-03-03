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

import fr.lip6.move.coloane.projects.its.obs.SimpleObservable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * A class holding the values of a tool's parameters.
 * Parameters can currently be either String or Boolean type.
 * Observable so GUIs can be notified of changes.
 * @author Yann
 *
 */
public class ParameterList extends SimpleObservable {

	private Map<String, String> parameters = new LinkedHashMap<String, String>();
	private Map<String, String> eParameters = new HashMap<String, String>();
	private Map<String, String> helpers = new HashMap<String, String>();
	private Map<String, Boolean> bParameters = new HashMap<String, Boolean>();
	private Map<String, String []> eParamSets = new HashMap<String, String[]>();

	/**
	 * Copy ctor, used to run a tool again, same settings.
	 * @param other to be copied into this
	 */
	public ParameterList(ParameterList other) {
		parameters = new HashMap<String, String>(other.parameters);
		bParameters = new HashMap<String, Boolean>(other.bParameters);
	}

	/**
	 * Default empty parameters constructor.
	 */
	public ParameterList() {
	}

	/**
	 * Returns the names of all String type parameters.
	 * @return the legal String parameters (keys) which are defined.
	 */
	public Set<String> getParameters() {
		return parameters.keySet();
	}

	/**
	 * Returns the names of all Bool type parameters.
	 * @return the legal Bool parameters (keys) which are defined.
	 */
	public Set<String> getBoolParameters() {
		return bParameters.keySet();
	}

	/**
	 * Returns the names of all enumerated type parameters.
	 * @return the legal enum parameters (keys) which are defined.
	 */
	public Set<String> getEnumParameters() {
		return eParameters.keySet();
	}

	/**
	 * Return the boolean value currently associated to a parameter.
	 * @param bParam the parameter name.
	 * @return the current value
	 */
	public Boolean getBoolParameterValue(String bParam) {
		return bParameters.get(bParam);
	}

	/**
	 * Return the String value currently associated to a parameter.
	 * @param param the parameter name.
	 * @return the current value
	 */
	public String getParameterValue(String param) {
		return parameters.get(param);
	}

	/**
	 * Return the String value currently associated to an enumerated parameter.
	 * @param param the parameter name.
	 * @return the current value
	 */
	public String getEnumParameterValue(String param) {
		return eParameters.get(param);
	}
	
	/**
	 * Return the String value currently associated to a parameter.
	 * @param param the parameter name.
	 * @return the potential values
	 */
	public String [] getEnumParameterPotentialValue(String param) {
		return eParamSets.get(param);
	}
	
	
	
	/**
	 * Add a String type parameter, with default initial value init.
	 * @param paramName the name of the parameter.
	 * @param initial default initial value
	 * @param help some tooltip to guide the user
	 */
	public void addParameter(String paramName, String initial, String help) {
		parameters.put(paramName, initial);
		helpers.put(paramName,help);
	}

	/**
	 * Add an enumeration type parameter, with default initial value init.
	 * @param paramName the name of the parameter.
	 * @param initial default initial value
	 * @param help some tooltip to guide the user
	 * @param potential list of potential values
	 */
	public void addEnumParameter(String paramName, String initial, String help, String [] potentials) {
		eParameters.put(paramName, initial);
		eParamSets .put(paramName,potentials);
		helpers.put(paramName,help);
		
	}
	
	/**
	 * Position the value of a String parameter.
	 * Also notifies observers on successful change of value.
	 * @param param the String parameter to modify
	 * @param value the new value
	 * @return true if change done, false if some problem.
	 */
	public boolean setParameterValue(String param, String value) {
		if (parameters.containsKey(param)) {
			if (!parameters.get(param).equals(value)) {
				parameters.put(param, value);
				notifyObservers();
			}
			return true;
		}
		return false;
	}

	
	/**
	 * Position the value of a Bool parameter.
	 * Also notifies observers on successful change of value.
	 * @param param the String parameter to modify
	 * @param value the new value
	 * @return true if change done, false if some problem.
	 */
	public boolean setBoolParameterValue(String param, boolean value) {
		if (bParameters.containsKey(param)) {
			if (!bParameters.get(param).equals(value)) {
				bParameters.put(param, value);
				notifyObservers();
			}
			return true;
		}
		return false;
	}

	/**
	 * Position the value of an Enum parameter.
	 * Also notifies observers on successful change of value.
	 * @param param the String parameter to modify
	 * @param value the new value
	 * @return true if change done, false if some problem.
	 */
	public boolean setEnumParameterValue(String param, String value) {
		if (eParameters.containsKey(param)) {
			if (!eParameters.get(param).equals(value)) {
				if (!checkLegal(param,value)) {
					return false;
				}
				eParameters.put(param, value);
				notifyObservers();
			}
			return true;
		}
		return false;
	}

	
	private boolean checkLegal(String param, String value) {
		for (String v : eParamSets.get(param)) {
			if (v.equals(value))
				return true;
		}
		return false;
	}

	/**
	 * Add a boolean parameter with the specified default value.
	 * @param bparam name of the new parameter
	 * @param initial default initial value
	 * @param help the help tooltip
	 */
	public void addBooleanParameter(String bparam, boolean initial, String help) {
		bParameters.put(bparam, initial);
		helpers.put(bparam,help);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return parameters.toString() + bParameters.toString() + eParameters.toString(); 
	}

	public String getToolTip(String param) {
		String ret = helpers.get(param);
		if (ret != null) {
			return ret;
		}
		return "";
	}

	
}
