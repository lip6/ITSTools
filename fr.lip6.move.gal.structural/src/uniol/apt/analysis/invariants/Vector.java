/*-
 * APT - Analysis of Petri Nets and labeled Transition systems
 * Copyright (C) 2012-2013  Members of the project group APT
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package uniol.apt.analysis.invariants;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Representing a vector used to give a covering vector for t- or s-invariants. Just a overriden toString-method.
 * @author Manuel Gieseking
 */
public class Vector extends ArrayList<Integer> {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for a new Vector.
	 */
	public Vector() {
	}

	/**
	 * Constructor with a given list of integers.
	 * @param c - the integers to put to this vector.
	 */
	public Vector(Collection<? extends Integer> c) {
		super(c);
	}

	@Override
	public String toString() {
		if (size() == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder("[");
		sb.append(get(0));
		for (int i = 1; i < size(); ++i) {
			sb.append("; ").append(get(i));
		}
		sb.append("]");
		return sb.toString();
	}
}
