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

package jhoafparser.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jhoafparser.ast.*;
import jhoafparser.storage.UniqueTable;

/** Helper class for simplifying a generic acceptance condition */
public class AcceptanceSimplify
{
	/** Returns a Boolean if the expression is TRUE or FALSE, {@code null} otherwise. */
	private static Boolean getSimpleTruth(BooleanExpression<AtomAcceptance> acc) {
		switch (acc.getType()) {
		case EXP_FALSE:
			return new Boolean(false);
		case EXP_TRUE:
			return new Boolean(true);
		default:
			return null;
		}
	}

	/**
	 * Simplify expression (remove unneeded TRUE / FALSE).
	 * @param acc the expression
	 * @return the simplified expression
	 */
	public static BooleanExpression<AtomAcceptance> simplify(BooleanExpression<AtomAcceptance> acc) {
		switch (acc.getType()) {
		case EXP_TRUE:
		case EXP_FALSE:
			return acc;
		case EXP_ATOM:
			return acc;
		case EXP_AND: {
			Boolean left = getSimpleTruth(acc.getLeft());
			Boolean right = getSimpleTruth(acc.getRight());
			if (left != null && right != null) {
				return new BooleanExpression<AtomAcceptance>(left && right);
			} else if (left != null) {
				return (left ? acc.getRight() : new BooleanExpression<AtomAcceptance>(false));
			} else if (right != null) {
				return (right ? acc.getLeft() : new BooleanExpression<AtomAcceptance>(false));
			} else {
				return acc;
			}
		}
		case EXP_OR: {
			Boolean left = getSimpleTruth(acc.getLeft());
			Boolean right = getSimpleTruth(acc.getRight());
			if (left != null && right != null) {
				return new BooleanExpression<AtomAcceptance>(left || right);
			} else if (left != null) {
				return (left ? new BooleanExpression<AtomAcceptance>(true) : acc.getRight());
			} else if (right != null) {
				return (right ? new BooleanExpression<AtomAcceptance>(true) : acc.getLeft());
			} else {
				return acc;
			}
		}
		default:
			break;
		}

		throw new UnsupportedOperationException("Unsupported operator in acceptance condition "+acc);
	}

	/** Returns true if the only boolean operator in the given acceptance condition is AND */
	public static boolean isConjunctive(BooleanExpression<AtomAcceptance> acc) {
		switch (acc.getType()) {
		case EXP_AND:
			return isConjunctive(acc.getLeft()) && isConjunctive(acc.getRight());
		case EXP_ATOM:
		case EXP_FALSE:
		case EXP_TRUE:
			return true;
		case EXP_OR:
			return false;
		default:
			break;
		}
		throw new UnsupportedOperationException("Unsupported operator in acceptance condition "+acc);
	}

	/**
	 * Converts the given acceptance condition into disjunctive normal form (DNF).
	 * <p>
	 * Uses a unique table to allow sharing of subformulas.
	 * 
	 * @param acc the acceptance condition that should be transformed
	 * @param uniqueTable a unique table over acceptance expressions
	 * @returns an equivalent acceptance condition in DNF
	 **/
	private static List<BooleanExpression<AtomAcceptance>> toDNF(BooleanExpression<AtomAcceptance> acc, UniqueTable<BooleanExpression<AtomAcceptance>> uniqueTable) {
		if (isConjunctive(acc)) {
			return Collections.singletonList(uniqueTable.findOrAdd(acc));
		}

		switch (acc.getType()) {
		case EXP_OR: {
			// simply recurse
			List<BooleanExpression<AtomAcceptance>> left = toDNF(acc.getLeft(), uniqueTable);
			List<BooleanExpression<AtomAcceptance>> right = toDNF(acc.getRight(), uniqueTable);
			left.addAll(right);
			return left;
		}
		case EXP_AND: {
			// recurse
			List<BooleanExpression<AtomAcceptance>> left = toDNF(acc.getLeft(), uniqueTable);
			List<BooleanExpression<AtomAcceptance>> right = toDNF(acc.getRight(), uniqueTable);

			// combine
			List<BooleanExpression<AtomAcceptance>> result = new ArrayList<BooleanExpression<AtomAcceptance>>();
			for (BooleanExpression<AtomAcceptance> l : left) {
				for (BooleanExpression<AtomAcceptance> r : right) {
					result.add(uniqueTable.findOrAdd(l.and(r)));
				}
			}
			return result;
		}
		default:
			// should never arrive here
			throw new UnsupportedOperationException("Unsupported operator in acceptance condition: "+acc);
		}
	}

	/** Convert acceptance condition into disjunctive normal form (list of conjunctive clauses) */
	public static List<BooleanExpression<AtomAcceptance>> toDNF(BooleanExpression<AtomAcceptance> acc) {
		UniqueTable<BooleanExpression<AtomAcceptance>> uniqueTable = new UniqueTable<BooleanExpression<AtomAcceptance>>();

		return toDNF(acc, uniqueTable);
	}

	/** Convert acceptance condition into disjunctive normal form */
	public static BooleanExpression<AtomAcceptance> toDNFCondition(BooleanExpression<AtomAcceptance> acc) {
		List<BooleanExpression<AtomAcceptance>> list = toDNF(acc);
		if (list.size() == 0) {
			return new BooleanExpression<AtomAcceptance>(false);
		}

		BooleanExpression<AtomAcceptance> result = list.remove(0);
		for (BooleanExpression<AtomAcceptance> clause : list) {
			result = result.or(clause);
		}

		return result;
	}

}
