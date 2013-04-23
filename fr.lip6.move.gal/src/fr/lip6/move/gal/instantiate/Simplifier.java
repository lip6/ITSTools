package fr.lip6.move.gal.instantiate;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.Actions;
import fr.lip6.move.gal.And;
import fr.lip6.move.gal.ArrayPrefix;
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
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableRef;

public class Simplifier {

	public static System simplify(System s) {
		for (Variable var : s.getVariables()) {
			var.setValue(simplify(var.getValue()));
		}
		for (ArrayPrefix var : s.getArrays()) {
			for (IntExpression val : var.getValues().getValues()) {
				EcoreUtil.replace(val,simplify(val));
			}
		}


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

		// simplify syntactically false guard transitions
		int i =0;
		for (Transition t : todel) {
			EcoreUtil.delete(t);
			i++;
		}
		java.lang.System.err.println("Removed "+i + " false transitions.");

		simplifyPetriStyleAssignments(s);

		return s;
	}

	public static void simplifyPetriStyleAssignments(System s) {


		//simplify redundant assignments :
		// suppose we have both : x = x + 1; and  x = x-1; without reading or writing to x in between.
		// typically produced by test arc style petri nets
		for (Transition tr : s.getTransitions()) {


			if (isPetriStyle(tr)) {
				// do it quadratic, maps don't work well with eObject
				EList<Actions> actions = tr.getActions();
				for (int i = 0; i < actions.size(); i++) {
					Assignment ass = (Assignment) actions.get(i);

					for (int j = i+1 ; j < actions.size() ; j++) {
						Assignment ass2 = (Assignment) actions.get(j);
						if (EcoreUtil.equals(ass2.getLeft(),ass.getLeft())) {
							BinaryIntExpression bin = (BinaryIntExpression) ass.getRight();
							BinaryIntExpression bin2 = (BinaryIntExpression) ass2.getRight();

							Constant c = (Constant) bin.getRight();
							Constant c2 = (Constant) bin2.getRight();

							int val = c.getValue();
							if (bin.getOp().equals("-")) {
								val = -val;
							}
							int val2 = c2.getValue();
							if (bin2.getOp().equals("-")) {
								val2 = -val2;
							}
							int valtot = val + val2;

							if (valtot==0) {
								EcoreUtil.delete(ass);
							} else if (valtot > 0) {
								bin.setOp("+");
								c.setValue(valtot);
							} else {
								bin.setOp("-");
								c.setValue(-valtot);
							}
							EcoreUtil.delete(ass2);
							break;
						} // if same lhs
					}  // for j
				}  // for i
			} // if petri style tr
		} // for tr
	}

	private static boolean isPetriStyle(Transition tr) {

		for (Actions a : tr.getActions()) {
			if (a instanceof Assignment
					&& ( 
							( 		/// tab[cte] case
									((Assignment) a).getLeft() instanceof ArrayVarAccess 
									&& ( ((ArrayVarAccess) ((Assignment) a).getLeft()).getIndex() instanceof Constant  )
									)
									||  
									(       /// plain old variable
											((Assignment) a).getLeft() instanceof VariableRef
											)
							)
							&& ((Assignment) a).getRight() instanceof BinaryIntExpression					
							&& EcoreUtil.equals(((BinaryIntExpression) ((Assignment) a).getRight()).getLeft(), ((Assignment) a).getLeft() )
							&& ((BinaryIntExpression) ((Assignment) a).getRight()).getRight() instanceof Constant )
			{
			}
			else
			{
				return false;
			}
		}
		return true;
	}

	public static BooleanExpression simplify (BooleanExpression be) {
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

	public static IntExpression simplify(IntExpression expr) {
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
