package fr.lip6.move.gal.flatten.popup.actions;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.Actions;
import fr.lip6.move.gal.And;
import fr.lip6.move.gal.ArrayVarAccess;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Not;
import fr.lip6.move.gal.Or;
import fr.lip6.move.gal.System;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.VarAccess;

public class Simplifier {

	public static System simplify(System s) {
		List<Transition> todel = new ArrayList<Transition>();
		for (Transition t : s.getTransitions()) {
			BooleanExpression newg = simplify(t.getGuard());
			t.setGuard(newg);
			if (newg instanceof False) {
				todel.add(t);
				continue;
			}
			for (Actions a : t.getActions()) {
				if (a instanceof Assignment) {
					Assignment ass = (Assignment) a;
					ass.setLeft((VarAccess) simplify(ass.getLeft()));
					ass.setRight(simplify(ass.getRight()));
				}
			}
		}
		int i =0;
		for (Transition t : todel) {
			EcoreUtil.delete(t);
			i++;
		}
		java.lang.System.err.println("Removed "+i + " false transitions.");
		return s;
	}
	
	private static BooleanExpression simplify (BooleanExpression be) {
		GalFactory gf = GalFactory.eINSTANCE;
		if (be instanceof And) {
			And and = (And) be;
			BooleanExpression left = simplify(and.getLeft());
			BooleanExpression right = simplify(and.getRight());
			if (left instanceof True) {
				return right;
			} else if (right instanceof True) {
				return left;
			} else if (left instanceof False || right instanceof False) {
				return left;
			} else {
				and.setLeft(left);
				and.setRight(right);
				return and;
			}
		} else if (be instanceof Or) {
			Or and = (Or) be;
			BooleanExpression left = simplify(and.getLeft());
			BooleanExpression right = simplify(and.getRight());
			if (left instanceof False) {
				return right;
			} else if (right instanceof False) {
				return left;
			} else if (left instanceof True || right instanceof True) {
				return left;
			} else {
				and.setLeft(left);
				and.setRight(right);
				return and;
			}
		} else if (be instanceof Not) {
			Not not = (Not) be;
			BooleanExpression left = simplify(not.getValue());
			if (left instanceof Not) {
				return ((Not)left).getValue();
			} else if (left instanceof False) {
				True tru = gf.createTrue();
				return tru;
			} else if (left instanceof True) {
				False tru = gf.createFalse();
				return tru;
			} else {
				Not nott = gf.createNot();
				nott.setValue(left);
				return not;
			}
		} else if (be instanceof Comparison) {
			Comparison comp = (Comparison) be;
			IntExpression left = simplify(comp.getLeft());
			IntExpression right = simplify(comp.getRight());
			if (left instanceof Constant && right instanceof Constant) {
				boolean res = false;
				int l = ((Constant) left).getValue();
				int r = ((Constant) right).getValue();
				switch (comp.getOperator()) {
				case EQ : res = (l==r); break;
				case NE : res = (l!=r); break;
				case GE : res = (l>=r); break;
				case GT : res = (l>r); break;
				case LT : res = (l<r); break;
				case LE : res = (l<=r); break;
				}
				if (res) {
					True tru = gf.createTrue();
					return tru;
				} else {
					False tru = gf.createFalse();
					return tru;
				}
			}
			comp.setLeft(left);
			comp.setRight(right);
			return comp;
		}
 		return be;
	}

	private static IntExpression simplify(IntExpression expr) {
		GalFactory gf = GalFactory.eINSTANCE;
		if (expr instanceof BinaryIntExpression) {
			BinaryIntExpression bin = (BinaryIntExpression) expr;
			IntExpression left = simplify(bin.getLeft());
			IntExpression right = simplify(bin.getRight());
			
			if (left instanceof Constant && right instanceof Constant) {
				int l = ((Constant) left).getValue();
				int r = ((Constant) right).getValue();
				int res=0;
				if ("+".equals(bin.getOp())) {
					res = l + r;
				} else if ("-".equals(bin.getOp())) {
					res = l - r;
				} else if ("/".equals(bin.getOp())) {
					res = l / r;
				} else if ("*".equals(bin.getOp())) {
					res = l * r;
				} else if ("**".equals(bin.getOp())) {
					res = (int) Math.pow(l , r);
				} else if ("%".equals(bin.getOp())) {
					res = l % r;
				} else {
					java.lang.System.err.println("Unexpected operator in simplify procedure:" + bin.getOp());
				}
				Constant cst = gf.createConstant();
				cst.setValue(res);
				return cst;
			} else if (left instanceof Constant) {
				int l = ((Constant) left).getValue();
				if (l==0 && "+".equals(bin.getOp())) {
					return right;
				} else if (l==1 && "*".equals(bin.getOp())) {
					return right;
				}
			} else if (right instanceof Constant) {
				int r = ((Constant) right).getValue();
				if (r==0 && "+".equals(bin.getOp())) {
					return left;
				} else if (r==1 && "*".equals(bin.getOp())) {
					return left;
				}
			}
			bin.setLeft(left);
			bin.setRight(right);
			return bin;
			
		} else if (expr instanceof ArrayVarAccess) {
			ArrayVarAccess acc = (ArrayVarAccess) expr;
			acc.setIndex(simplify(acc.getIndex()));
			return acc;
		}
		return expr;
	} 

}
