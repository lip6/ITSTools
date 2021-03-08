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

package jhoafparser.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Helper class for filtering properties when changing aspects of the automaton */
public class PropertyFilter
{
	// static members
	/** Set of properties that are preserved as long as there is no change of language */
	private static Set<String> v1LanguageProperties = getV1LanguageProperties();
	/** Set of properties that are preserved as long as there is no change of syntax */
	private static Set<String> v1SyntaxProperties = getV1SyntaxProperties();
	/** Set of properties that are preserved as long as there is no change of the branching structure */
	private static Set<String> v1BranchingProperties = getV1BranchingProperties();
	/** Set of properties that are preserved as long as there is no change of the structure */
	private static Set<String> v1StructuralProperties = getV1StructuralProperties();
	
	// non-static members
	/** Set of allowed properties */
	private Set<String> allowedProperties = new HashSet<String>();

	/**
	 * Constructor.
	 * @param preservesLanguage if false, filter out properties that are only true if the language is unchanged
	 * @param preservesSyntax if false, filter out properties that relate to the syntax of the automaton (transition-/state-based, ...)
	 * @param preservesBranching if false, filter out properties that relate to the branching structure (deterministic, universal, non-deterministic, ...)
	 * @param preservesStructure if false, filter out properties that relate to the SCC structure (weak, ...)
	 */
	public PropertyFilter(boolean preservesLanguage, boolean preservesSyntax, boolean preservesBranching, boolean preservesStructure) {
		if (preservesLanguage) allowedProperties.addAll(v1LanguageProperties);
		if (preservesSyntax) allowedProperties.addAll(v1SyntaxProperties);
		if (preservesBranching) allowedProperties.addAll(v1BranchingProperties);
		if (preservesStructure) allowedProperties.addAll(v1StructuralProperties);
	}

	/** Add property to the set of allowed properties */
	public void allowProperty(String property) {
		allowedProperties.add(property);
	}
	
	/** Remove property from the set of allowed properties */
	public void disallowProperty(String property) {
		allowedProperties.remove(property);
	}

	/** Check whether the property is filtered, i.e., should not be passed on the output */
	public boolean isFiltered(String property) {
		return !allowedProperties.contains(property);
	}

	/** Filter the given properties according to the allowed properties */
	public List<String> filter(Collection<String> properties) {
		ArrayList<String> result = new ArrayList<String>();
		for (String property : properties) {
			if (!isFiltered(property)) {
				result.add(property);
			}
		}
		return result;
	}
	
	/** Get the properties dealing with language in HOAF v1 */
	public static Set<String> getV1LanguageProperties() {
		HashSet<String> properties = new HashSet<String>();

		properties.add("stutter-invariant"); // hints that the automaton describes a stutter-invariant property

		return properties;
	}

	/** Get the properties dealing with syntax in HOAF v1 */
	public static Set<String> getV1SyntaxProperties() {
		HashSet<String> properties = new HashSet<String>();

		properties.add("state-labels"); // hints that the automaton uses only state labels
		properties.add("trans-labels"); // hints that the automaton uses only transition labels
		properties.add("implicit-labels"); // hints that the automaton uses only implicit transitions labels
		properties.add("explicit-labels"); // hints that the automaton uses only explicit transitions labels
		properties.add("state-acc"); // hints that the automaton uses only state-based acceptance specifications
		properties.add("trans-acc"); // hints that the automaaton uses only transition-based acceptance specifications

		return properties;
	}

	/** Get the properties dealing with the branching structure in HOAF v1 */
	public static Set<String> getV1BranchingProperties() {
		HashSet<String> properties = new HashSet<String>();

		properties.add("univ-branch"); // hints that the automaton uses universal branching for at least one transition or for the initial state
		properties.add("no-univ-branch"); // hints that the automaton does not uses universal branching
		properties.add("deterministic"); // hints that the automaton is deterministic, i.e., it has at most one initial state, and the outgoing transitions of each state have disjoint labels (note that this also applies in the presence of universal branching)
		properties.add("complete"); // hints that the automaton is complete, i.e., it has at least one state, and the transition function is total

		return properties;
	}

	/** Get the properties dealing with the structure (SCC, etc) of the automaton in HOAF v1 */
	public static Set<String> getV1StructuralProperties() {
		HashSet<String> properties = new HashSet<String>();

		properties.add("unambiguous"); // hints that the automaton is unambiguous, i.e., for each word there is at most one accepting run of the automaton (this also applies in the presence of universal branching)
		properties.add("weak"); // hints that in each strongly connected component (in alternating automata, SCC can be defined in standard way if we see each universal branching transition as a set of non-branching transitions), all transitions (or all states) belong to the same accepting sets
		properties.add("very-weak"); // hints that the automaton is weak and every SCC has exactly one state
		properties.add("inherently-weak"); // hints that the automaton does not mix accepting cycles and non-accepting cycles in the same SCC
		properties.add("terminal"); // hints that the automaton is weak, that no non-accepting cycle can be reached from any accepting cycle, and that each SCC containing an accepting cycle is complete, i.e., the transition function is definied for each state of the SCC and each letter
		properties.add("tight"); //  hints that the automaton is tight

		return properties;
	}
	
	
	
}
