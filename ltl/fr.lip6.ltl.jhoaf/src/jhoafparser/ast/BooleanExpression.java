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


package jhoafparser.ast;

import java.util.BitSet;

/**
 * This represents (a node of) an abstract syntax tree
 * of a boolean expression, parametrized with the type
 * of leaf nodes (atoms).
 * <p>
 * The nodes are designed to be immutable, which allows
 * sharing of subexpression in a safe way between multiple
 * trees.
 * <p>
 * For unary operator (NOT), the child is stored as the
 * left child of the not.
 * <p>
 * With {@code AtomLabel}, this represents a label expression
 * over atomic propositions, with {@code AtomAcceptance} an
 * expression of Fin/Inf acceptance conditions.
 * 
 * @param <Atoms> The atoms (leaf nodes) in the abstract syntax tree.
 */
public class BooleanExpression<Atoms extends Atom> {
	/** The possible node types (boolean operator, true, false, atom) */
	public enum Type {
		/** boolean AND */
		EXP_AND,
		/** boolean OR */
		EXP_OR,
		/** boolean NOT */
		EXP_NOT,
		/** boolean TRUE */
		EXP_TRUE,
		/** boolean FALSE */
		EXP_FALSE,
		/** atom (leaf node) */
		EXP_ATOM;
	};

	/** The type of this node */
	private Type type;
	/** The left child of this node (may be {@code null}) */
	private BooleanExpression<Atoms> left = null;
	/** The right child of this node (may be {@code null}) */
	private BooleanExpression<Atoms> right = null;
	/** Stores the actual atom for {@code type == EXP_ATOM} */
	private Atoms atom = null;


	/** Constructor for a boolean operator node, with given type and children */
	public BooleanExpression(Type type, BooleanExpression<Atoms> left, BooleanExpression<Atoms> right) {
		this.type=type;
		this.left=left;
		this.right=right;
		this.atom=null;
	}
	
	/** Constructor for a boolean leaf node (true / false) with the given value */
	public BooleanExpression(boolean value) {
		this.type = (value ? Type.EXP_TRUE : Type.EXP_FALSE);
		this.left = null;
		this.right = null;
		this.atom = null;
	}
	
	/** Constructor for an atom leaf node */
	public BooleanExpression(Atoms atom) {
		type = Type.EXP_ATOM;
		this.left = null;
		this.right = null;
		this.atom = atom;
	}

	/** Return the type of this node */
	public Type getType() {return type;}
	/** Get the left child */
	public BooleanExpression<Atoms> getLeft() {return left;}
	/** Get the right child */
	public BooleanExpression<Atoms> getRight() {return right;}
	/** Get the atom */
	public Atoms getAtom() {return atom;}

	/** Return a deep-copy of this expression */
	@SuppressWarnings("unchecked")
	public BooleanExpression<Atoms> clone() {
		if (isAtom()) {
			return new BooleanExpression<Atoms>((Atoms) atom.clone());
		}

		BooleanExpression<Atoms> newLeft  = (left  != null ?  left.clone() : null);
		BooleanExpression<Atoms> newRight = (right != null ? right.clone() : null);

		return new BooleanExpression<Atoms>(type, newLeft, newRight);
	}

	/** Return true if this node is a boolean AND operator */
	public boolean isAND() {return type==Type.EXP_AND;}
	/** Return true if this node is a boolean OR operator */
	public boolean isOR() {return type==Type.EXP_OR;}
	/** Return true if this node is a boolean NOT operator */
	public boolean isNOT() {return type==Type.EXP_NOT;}
	/** Return true if this node is a boolean true leaf */
	public boolean isTRUE() {return type==Type.EXP_TRUE;}
	/** Return true if this node is a boolean false leaf */
	public boolean isFALSE() {return type==Type.EXP_FALSE;}
	/** Return true if this node is an atom */
	public boolean isAtom() {return type==Type.EXP_ATOM;}

	/** Returns an expression of {@code this} AND {@code other} */
	public BooleanExpression<Atoms> and(BooleanExpression<Atoms> other) {
		return new BooleanExpression<Atoms>(Type.EXP_AND, this, other);
	}

	/** Returns an expression of {@code this} OR {@code other} */
	public BooleanExpression<Atoms> or(BooleanExpression<Atoms> other) {
		return new BooleanExpression<Atoms>(Type.EXP_OR, this, other);
	}

	/** Returns an expression of NOT {@code this} */
	public BooleanExpression<Atoms> not() {
		return new BooleanExpression<Atoms>(Type.EXP_NOT, this, null);
	}

	/** 
	 * Static helper function, calculating the bits that are set for
	 * a given implicit edge index, i.e., the binary representation
	 * of the index.
	 */
	public static BitSet fromImplicit(long implicitIndex) {
		BitSet bs = new BitSet();
		
		int bit = 0;
		while (implicitIndex != 0) {
			bs.set(bit, (implicitIndex & 0x01)==1);
			implicitIndex = implicitIndex >> 2;
			bit++;
		}
		
		return bs;
	}
	
	/**
	 * Returns a boolean expression with {@code AtomLabel} atoms for the
	 * given implicit edge index and number of atomic propositions.
	 */
	public static BooleanExpression<AtomLabel> fromImplicit(long implicitIndex, int apSize) {
		BooleanExpression<AtomLabel> result = null;
		BitSet bs = fromImplicit(implicitIndex);
		
		for (int i = 0; i < apSize; i++) {
			BooleanExpression<AtomLabel> label = new BooleanExpression<AtomLabel>(AtomLabel.createAPIndex(new Integer(i)));
			if (bs.get(i) == false) {
				label = label.not();
			}
			
			if (result != null)
				result = result.and(label);
			else
				result = label;
		}
		
		return result;
	}

	/**
	 * Returns true if the two boolean expression are syntactically equal, i.e.,
	 * if the tree structure matches and the atoms are equal.
	 */ 
	public static boolean areSyntacticallyEqual(BooleanExpression<?> expr1, BooleanExpression<?> expr2)
	{
		if (expr1 == null || expr2 == null) return false;
		if (expr1.getType() != expr2.getType()) return false;
		
		switch (expr1.getType()) {
		case EXP_TRUE:
		case EXP_FALSE:
			return true;
		case EXP_AND:
		case EXP_OR:
			if (!areSyntacticallyEqual(expr1.getLeft(), expr2.getLeft())) return false;
			if (!areSyntacticallyEqual(expr1.getRight(), expr2.getRight())) return false;
			return true;
		case EXP_NOT:
			if (!areSyntacticallyEqual(expr1.getLeft(), expr2.getLeft())) return false;
			return true;
		case EXP_ATOM:
			return expr1.getAtom().equals(expr2.getAtom());
		}
		throw new UnsupportedOperationException("Unknown operator in expression: "+expr1);
	}

	/**
	 * Returns true if the node needs to be parenthesized
	 * if the parent node has the given {@code enclosingType}
	 * to preserve operator precedence. 
	 */
	public boolean needsParentheses(Type enclosingType) {
		switch (type) {
		case EXP_ATOM:
		case EXP_TRUE:
		case EXP_FALSE:
			return false;
		case EXP_AND:
			if (enclosingType==Type.EXP_NOT) return true;
			if (enclosingType==Type.EXP_AND) return false;
			if (enclosingType==Type.EXP_OR) return false;
			break;
		case EXP_OR:
			if (enclosingType==Type.EXP_NOT) return true;
			if (enclosingType==Type.EXP_AND) return true;
			if (enclosingType==Type.EXP_OR) return false;
		case EXP_NOT:
			return false;
		}
		throw new RuntimeException("Unhandled type");
	}

	/**
	 * Returns an infix representation of the boolean
	 * expression.
	 */
	public String toStringInfix() {
		StringBuilder result = new StringBuilder();
		switch (type) {
		case EXP_AND: {
			boolean paren = left.needsParentheses(Type.EXP_AND);
			if (paren) result.append("(");
			result.append(left.toStringInfix());
			if (paren) result.append(")");
			result.append(" & ");
			
			paren = right.needsParentheses(Type.EXP_AND);
			if (paren) result.append("(");
			result.append(right.toStringInfix());
			if (paren) result.append(")");
			return result.toString();
		}
		case EXP_OR: {
			boolean paren = left.needsParentheses(Type.EXP_OR);
			if (paren) result.append("(");
			result.append(left.toStringInfix());
			if (paren) result.append(")");
			result.append(" | ");
			
			paren = right.needsParentheses(Type.EXP_OR);
			if (paren) result.append("(");
			result.append(right.toStringInfix());
			if (paren) result.append(")");
			return result.toString();
		}
		case EXP_NOT: {
			boolean paren = left.needsParentheses(Type.EXP_NOT);
			result.append("!");
			if (paren) result.append("(");
			result.append(left.toStringInfix());
			if (paren) result.append(")");
			return result.toString();
		}
		case EXP_TRUE: return "t";
		case EXP_FALSE: return "f";
		case EXP_ATOM: return getAtom().toString();
		}
		throw new RuntimeException("Unhandled BooleanExpression type: "+type);
	}

	@Override
	public String toString() {
		return toStringInfix();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((atom == null) ? 0 : atom.hashCode());
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BooleanExpression))
			return false;
		@SuppressWarnings("rawtypes")
		BooleanExpression other = (BooleanExpression) obj;
		if (type != other.type)
			return false;
		if (atom == null) {
			if (other.atom != null)
				return false;
		} else if (!atom.equals(other.atom))
			return false;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		return true;
	}
}
