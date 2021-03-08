//==============================================================================
//	
//	Copyright (c) 2014-
//	Authors:
//	* Joachim Klein <klein@tcs.inf.tu-dresden.de>
//	* David Mueller <david.mueller@tcs.inf.tu-dresden.de>
//	
//------------------------------------------------------------------------------
//	
//	This file is part of the jhoafparser library, http://automata.tools/hoa/jhoafparser/
//
//	The jhoafparser library is free software; you can redistribute it and/or
//	modify it under the terms of the GNU Lesser General Public
//	License as published by the Free Software Foundation; either
//	version 2.1 of the License, or (at your option) any later version.
//	
//	The jhoafparser library is distributed in the hope that it will be useful,
//	but WITHOUT ANY WARRANTY; without even the implied warranty of
//	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//	Lesser General Public License for more details.
//	
//	You should have received a copy of the GNU Lesser General Public
//	License along with this library; if not, write to the Free Software
//	Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
//	
//==============================================================================

package jhoafparser.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import jhoafparser.ast.*;
import jhoafparser.consumer.HOAConsumer;
import jhoafparser.consumer.HOAConsumerException;

/** Stored header information for a HOA automaton */
public class StoredHeader
{
	/** A name and extra information */
	public static class NameAndExtra<T> {
		/** The name */
		public String name;
		/** The extra information */
		public T extra;

		/** Constructor */
		NameAndExtra(String name, T extraInfo) {
			this.name = name;
			this.extra = extraInfo;
		}
	}

	/** The number of states (States-header) */
	private Integer numberOfStates = null;
	/** The start states (disjunction of conjunction of state indizes) */
	private List<List<Integer>> startStates = new ArrayList<List<Integer>>();
	/** The atomic propositions (AP header) */
	private List<String> aps = new ArrayList<String>();

	/** The number of acceptance sets (Acceptance-header) */
	private int numberOfAcceptanceSets;
	/** The acceptance condition */
	private BooleanExpression<AtomAcceptance> accExpr;
	/** The acc-name headers, consisting of the name of the condition and optionally additional information */
	private List<NameAndExtra<List<Object>>> accNames = new ArrayList<NameAndExtra<List<Object>>>();
	/** The misc. headers, consisting of the name of the header and additional information */
	private List<NameAndExtra<List<Object>>> miscHeaders = new ArrayList<NameAndExtra<List<Object>>>();

	/** The name of the automaton (name-header) */
	private String name;
	/** The tool-header */
	private String tool;
	/** The tool version (tool-header) */
	private String toolVersion;

	/** The properties */
	private SortedSet<String> properties = new TreeSet<String>();
	/** The alias definitions */
	private List<NameAndExtra<BooleanExpression<AtomLabel>>> aliases = new ArrayList<NameAndExtra<BooleanExpression<AtomLabel>>>();
	/** The set of names of defined aliases, for fast lookup */
	private HashSet<String> aliasNames = new HashSet<String>();

	@Override
	/** Construct a deep copy of this header */
	public StoredHeader clone() {
		StoredHeader result = new StoredHeader();

		result.numberOfStates = numberOfStates;
		for (List<Integer> start : startStates) {
			result.addStartStates(new ArrayList<Integer>(start));
		}
		result.setAPs(new ArrayList<String>(aps));
		result.setAcceptanceCondition(numberOfAcceptanceSets, accExpr.clone());
		for (NameAndExtra<List<Object>> accName : accNames) {
			result.provideAcceptanceName(accName.name, accName.extra);
		}
		for (NameAndExtra<List<Object>> miscHeader : miscHeaders) {
			result.provideAcceptanceName(miscHeader.name, miscHeader.extra);
		}
		for (NameAndExtra<BooleanExpression<AtomLabel>> alias : aliases) {
			result.addAlias(alias.name, alias.extra.clone());
		}
		if (name != null) {
			result.setTool(tool, toolVersion);
		}
		if (name != null) {
			result.setName(name);
		}
		result.addProperties(properties);

		return result;
	}

	/** Set the number of states, {@code null} to signify "not provided" */
	public void setNumberOfStates(Integer numberOfStates) {
		this.numberOfStates = numberOfStates;
	}

	/** Returns the stored number of states (may be {@code null} if none was stored) */
	public Integer getNumberOfStates() {
		return numberOfStates;
	}

	/** Add a conjunction of start states */
	public void addStartStates(List<Integer> stateConjunction) {
		startStates.add(stateConjunction);
	}
	
	/** Get the disjunction of conjunctions of start states */
	public List<List<Integer>> getStartStates() {
		return startStates;
	}

	/** Add the alias definition for {@code name} */
	public void addAlias(String name, BooleanExpression<AtomLabel> labelExpr) {
		if (hasAlias(name)) {
			throw new UnsupportedOperationException("Can not store alias "+name+" multiple times");
		}
		aliases.add(new NameAndExtra<BooleanExpression<AtomLabel>>(name, labelExpr));
		aliasNames.add(name);
	}

	/** Get the list of alias definitions */
	public List<NameAndExtra<BooleanExpression<AtomLabel>>> getAliases() {
		return aliases;
	}

	/** Returns true if there is an alias definition for {@code name} */
	public boolean hasAlias(String name)
	{
		return aliasNames.contains(name);
	}

	/** Set the list of atomic propositions (with the given order) */
	public void setAPs(List<String> aps) {
		this.aps = aps;
	}

	/** Get the list of atomic propositions */
	public List<String> getAPs() {
		return aps;
	}

	/** Set the acceptance condition (number of acceptance sets and expression) */
	public void setAcceptanceCondition(int numberOfSets, BooleanExpression<AtomAcceptance> accExpr) {
		numberOfAcceptanceSets = numberOfSets;
		this.accExpr = accExpr;
	}

	/** Get the number of acceptance sets */
	public int getNumberOfAcceptanceSets() {
		return numberOfAcceptanceSets;
	}

	/** Get the acceptance condition expression */
	public BooleanExpression<AtomAcceptance> getAcceptanceCondition() {
		return accExpr;
	}

	/** Store the information of a acc-name header */
	public void provideAcceptanceName(String name, List<Object> extraInfo) {
		accNames.add(new NameAndExtra<List<Object>>(name, extraInfo));
	}

	/** Remove all information concerning acc-name headers */
	public void removeAcceptanceNames() {
		accNames.clear();
	}

	/** Get the acc-name headers */
	public List<NameAndExtra<List<Object>>> getAcceptanceNames() {
		return accNames;
	}

	/** Set the name of the automaton */
	public void setName(String name) {
		this.name = name;
	}

	/** Get the name of the automaton, maybe {@code null} */
	public String getName() {
		return name;
	}

	/** Set the tool, maybe {@code null} */
	public void setTool(String name, String version) {
		this.tool = name;
		this.toolVersion = version;
	}

	/** Get the tool, maybe {@code null} */
	public String getTool() {
		return tool;
	}

	/** Get the tool version, maybe {@code null} */
	public String getToolVersion() {
		return toolVersion;
	}

	/** Add a property */
	public void addProperty(String property) {
		this.properties.add(property);
	}

	/** Add multiple properties */
	public void addProperties(Collection<String> properties) {
		this.properties.addAll(properties);
	}

	/** Get the set of properties */
	public Set<String> getProperties() {
		return properties;
	}

	/** Add a misc header */
	public void addMiscHeader(String name, List<Object> content) {
		miscHeaders.add(new NameAndExtra<List<Object>>(name, content));
	}

	/** Get the list of misc headers */
	public List<NameAndExtra<List<Object>>> getMiscHeaders() {
		return miscHeaders;
	}

	/**
	 * Use the information stored in this {@code StoredAutomaton}
	 * and feed them back to the consumer
	 */
	public void feedToConsumer(HOAConsumer c) throws HOAConsumerException {
		if (!properties.isEmpty()) {
			c.addProperties(new ArrayList<String>(properties));
		}

		if (getNumberOfStates() != null)
			c.setNumberOfStates(getNumberOfStates());

		for (List<Integer> start : getStartStates()) {
			c.addStartStates(start);
		}

		c.setAPs(getAPs());

		for (NameAndExtra<BooleanExpression<AtomLabel>> alias : getAliases()) {
			c.addAlias(alias.name, alias.extra);
		}

		if (getAcceptanceCondition() != null) {
			c.setAcceptanceCondition(getNumberOfAcceptanceSets(), getAcceptanceCondition());
		}

		for (NameAndExtra<List<Object>> acc_name : getAcceptanceNames()) {
			c.provideAcceptanceName(acc_name.name, acc_name.extra);
		}

		if (getName() != null) {
			c.setName(getName());
		}
		
		if (getTool() != null) {
			c.setTool(getTool(), getToolVersion());
		}

	}
}
