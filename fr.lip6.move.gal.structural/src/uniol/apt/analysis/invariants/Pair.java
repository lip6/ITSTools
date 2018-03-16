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
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Pair of elements.
 * @author Raffaela Ferrari, vsp
 *
 * @param <X> class of the first element
 * @param <Y> class of the second element
 */
public class Pair<X, Y> {
	private final X q1;
	private final Y q2;

	/**
	 * Creates a new pair of two elements.
	 * @param q1 the first element.
	 * @param q2 the second element.
	 */
	public Pair(X q1, Y q2) {
		this.q1 = q1;
		this.q2 = q2;
	}

	/**
	 * Returns the first element of the pair
	 * @return the first element of the pair
	 */
	public X getFirst() {
		return q1;
	}

	/**
	 * Returns the second element of the pair
	 * @return the second element of the pair
	 */
	public Y getSecond() {
		return q2;
	}

	@Override
	public String toString() {
		return "(" + q1 + "," + q2 + ")";
	}

	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof Pair)) {
			return false;
		}
		if (other == this) {
			return true;
		}
		Pair<?, ?> otherPair = (Pair<?, ?>) other;
		return Objects.equals(this.q1, otherPair.q1) && Objects.equals(this.q2, otherPair.q2);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.q1, this.q2);
	}

	public static <X, Y> List<Pair<X, Y>> zip(Collection<X> col1, Collection<Y> col2) {
		if (col1.size() != col2.size())
			throw new IllegalArgumentException("Collection need equal sizes");

		List<Pair<X, Y>> ret = new ArrayList<>();
		Iterator<Y> it = col2.iterator();
		for (X ele1 : col1) {
			assert it.hasNext();
			Y ele2 = it.next();

			ret.add(new Pair<>(ele1, ele2));
		}

		return ret;
	}

	public static <X, Y> Pair<List<X>, List<Y>> unzip(Iterable<Pair<X, Y>> iterable) {
		List<X> ret1 = new ArrayList<>();
		List<Y> ret2 = new ArrayList<>();
		for (Pair<X, Y> ele : iterable) {
			ret1.add(ele.getFirst());
			ret2.add(ele.getSecond());
		}

		return new Pair<>(ret1, ret2);
	}
}
