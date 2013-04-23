package fr.lip6.move.gal.simplify;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;


import fr.lip6.move.gal.And;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.ArrayVarAccess;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.Not;
import fr.lip6.move.gal.Or;
import fr.lip6.move.gal.System;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableRef;
import fr.lip6.move.gal.instantiate.Instantiator;
import fr.lip6.move.gal.instantiate.Simplifier;
import fr.lip6.move.gal.logic.BooleanExpression;
import fr.lip6.move.gal.logic.CardMarking;
import fr.lip6.move.gal.logic.ComparisonOperators;
import fr.lip6.move.gal.logic.Enabling;
import fr.lip6.move.gal.logic.Imply;
import fr.lip6.move.gal.logic.IntExpression;
import fr.lip6.move.gal.logic.LogicFactory;
import fr.lip6.move.gal.logic.MarkingRef;
import fr.lip6.move.gal.logic.Properties;
import fr.lip6.move.gal.logic.Property;

public class LogicSimplifier {

	
	public static void simplify (Properties props) {
		System s = props.getSystem();
		for (Property prop : props.getProps()) {
			for (TreeIterator<EObject> it = prop.eAllContents(); it.hasNext() ; ) {
				EObject obj = it.next();
				if (obj instanceof Enabling) {
					Enabling enab = (Enabling) obj;
					Transition tr = enab.getTrans();
					java.util.List<Transition> inst  = Instantiator.instantiateParameters(tr);
					fr.lip6.move.gal.BooleanExpression b = null;
					for (Transition t : inst) {
						fr.lip6.move.gal.BooleanExpression g = t.getGuard();
						
						if (b != null) {
							Or or = GalFactory.eINSTANCE.createOr();
							or.setLeft(b);
							or.setRight(g);
							b = or;
						} else {
							b = EcoreUtil.copy(g);
						}
					}
					b = Simplifier.simplify(b);
					BooleanExpression bctl = toLogic(b);
					EcoreUtil.replace(obj, bctl);
				} else if (obj instanceof fr.lip6.move.gal.logic.Comparison) {
					fr.lip6.move.gal.logic.Comparison cmp = (fr.lip6.move.gal.logic.Comparison) obj;
					if (cmp.getLeft() instanceof CardMarking && cmp.getRight() instanceof CardMarking) {
						CardMarking left = (CardMarking) cmp.getLeft();
						CardMarking right = (CardMarking) cmp.getRight();
						if (left.getMarking().getPlace() instanceof Variable && right.getMarking().getPlace() instanceof Variable) {
							fr.lip6.move.gal.logic.VariableRef vl = LogicFactory.eINSTANCE.createVariableRef();
							vl.setReferencedVar((Variable) left.getMarking().getPlace());
							cmp.setLeft(vl);
							fr.lip6.move.gal.logic.VariableRef vr = LogicFactory.eINSTANCE.createVariableRef();
							vr.setReferencedVar((Variable) right.getMarking().getPlace());
							cmp.setRight(vr);
						} else if (left.getMarking().getPlace() instanceof ArrayPrefix && right.getMarking().getPlace() instanceof ArrayPrefix
										&& ((ArrayPrefix) left.getMarking().getPlace()).getSize()== ((ArrayPrefix) right.getMarking().getPlace()).getSize()) {
							ArrayPrefix l = (ArrayPrefix) left.getMarking().getPlace();
							ArrayPrefix r = (ArrayPrefix) right.getMarking().getPlace();
							int size = l.getSize();
							assert size >= 1;
							int i=0;
							fr.lip6.move.gal.logic.IntExpression suml = createSumOfArray(l);
							fr.lip6.move.gal.logic.IntExpression sumr = createSumOfArray(r);
							cmp.setLeft(suml);
							cmp.setRight(sumr);							
						}

					} else 	if (cmp.getLeft() instanceof MarkingRef && cmp.getRight() instanceof MarkingRef) {
//						fr.lip6.move.gal.logic.Comparison comp = createComparison(l,cmp.getOperator(),r,i);
//						for (i++ ; i < size ; i++) {
//							fr.lip6.move.gal.logic.And and = LogicFactory.eINSTANCE.createAnd();
//							
//						}

					}
				} else if (obj instanceof Imply) {
					Imply imp = (Imply) obj;
					fr.lip6.move.gal.logic.Not not = LogicFactory.eINSTANCE.createNot();
					not.setValue(imp.getRight());
					
					fr.lip6.move.gal.logic.Or or = LogicFactory.eINSTANCE.createOr();
					or.setLeft(not);
					or.setRight(imp.getLeft());
					
					EcoreUtil.replace(obj, or);
				}
			} 
		}
	}

	private static IntExpression createSumOfArray(ArrayPrefix l) {
		
		IntExpression sum = null;
		for (int i=0; i < l.getSize() ; i++) {
			fr.lip6.move.gal.logic.ArrayVarAccess av = LogicFactory.eINSTANCE.createArrayVarAccess();
			av.setPrefix(l);
			av.setIndex(constant(i));
			
			if (sum == null) {
				sum = av;
			} else {
				fr.lip6.move.gal.logic.BinaryIntExpression bin = LogicFactory.eINSTANCE.createBinaryIntExpression();
				bin.setOp("+");
				bin.setLeft(sum);
				bin.setRight(av);
				sum = bin;
			}
		}
		
		return sum;
	}

	private static fr.lip6.move.gal.logic.Comparison createComparison(
			ArrayPrefix l, ComparisonOperators operator, ArrayPrefix r, int i) {
		fr.lip6.move.gal.logic.ArrayVarAccess left = LogicFactory.eINSTANCE.createArrayVarAccess();
		left.setPrefix(l);
		left.setIndex(constant(i));
		
		fr.lip6.move.gal.logic.ArrayVarAccess right = LogicFactory.eINSTANCE.createArrayVarAccess();
		right.setPrefix(r);
		right.setIndex(constant(i));
		
		fr.lip6.move.gal.logic.Comparison cmp = LogicFactory.eINSTANCE.createComparison();
		cmp.setOperator(operator);
		cmp.setLeft(left);
		cmp.setRight(right);
		
		return cmp;
	}

	private static IntExpression constant(int i) {
		fr.lip6.move.gal.logic.Constant c = LogicFactory.eINSTANCE.createConstant();
		c.setValue(i);
		return c;
	}

	private static BooleanExpression toLogic(fr.lip6.move.gal.BooleanExpression b) {
		if (b instanceof And) {
			And and = (And) b;
			fr.lip6.move.gal.logic.And and2 = LogicFactory.eINSTANCE.createAnd();
			and2.setLeft(toLogic(and.getLeft()));
			and2.setRight(toLogic(and.getRight()));
			return and2;
		} else if (b instanceof Or) {
			Or and = (Or) b;
			fr.lip6.move.gal.logic.Or and2 = LogicFactory.eINSTANCE.createOr();
			and2.setLeft(toLogic(and.getLeft()));
			and2.setRight(toLogic(and.getRight()));
			return and2;
		} else if (b instanceof Not	) {
			Not and = (Not) b;
			fr.lip6.move.gal.logic.Not and2 = LogicFactory.eINSTANCE.createNot();
			and2.setValue(toLogic(and.getValue()));
			return and2;
		} else 	if (b instanceof Comparison) {
			Comparison and = (Comparison) b;
			fr.lip6.move.gal.logic.Comparison and2 = LogicFactory.eINSTANCE.createComparison();
			and2.setLeft(toLogic(and.getLeft()));
			and2.setRight(toLogic(and.getRight()));
			return and2;
		} else if (b instanceof True) {
			return LogicFactory.eINSTANCE.createTrue();
		} else if (b instanceof False) {
			return LogicFactory.eINSTANCE.createFalse();
		} else {
			java.lang.System.err.println("Unknown predicate type in boolean expression "+ b.getClass().getName());
		}
		return LogicFactory.eINSTANCE.createTrue();
	}

	private static IntExpression toLogic(fr.lip6.move.gal.IntExpression e) {
		if (e instanceof BinaryIntExpression) {
			BinaryIntExpression and = (BinaryIntExpression) e;
			fr.lip6.move.gal.logic.BinaryIntExpression and2 = LogicFactory.eINSTANCE.createBinaryIntExpression();
			and2.setOp(and.getOp());
			and2.setLeft(toLogic(and.getLeft()));
			and2.setRight(toLogic(and.getRight()));
			return and2;
		} else if (e instanceof ArrayVarAccess) {
			ArrayVarAccess a = (ArrayVarAccess) e;
			fr.lip6.move.gal.logic.ArrayVarAccess a2 = LogicFactory.eINSTANCE.createArrayVarAccess();
			a2.setPrefix(a.getPrefix());
			a2.setIndex(toLogic(a.getIndex()));
			return a2;
		} else if (e instanceof VariableRef) {
			VariableRef vr = (VariableRef) e;
			fr.lip6.move.gal.logic.VariableRef vr2 = LogicFactory.eINSTANCE.createVariableRef();
			vr2.setReferencedVar(vr.getReferencedVar());
			return vr2;
		} else if (e instanceof Constant) {
			Constant c = (Constant) e;
			fr.lip6.move.gal.logic.Constant c2 = LogicFactory.eINSTANCE.createConstant();
			c2.setValue(c.getValue());
			return c2;
		} else {
			java.lang.System.err.println("Unknown type in integer expression " + e.getClass().getName());			
		}
		
		fr.lip6.move.gal.logic.Constant cte =  LogicFactory.eINSTANCE.createConstant();
		cte.setValue(0);
		return cte;
	}

}
