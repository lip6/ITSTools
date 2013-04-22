package fr.lip6.move.gal.simplify;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;


import fr.lip6.move.gal.And;
import fr.lip6.move.gal.ArrayVarAccess;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.Not;
import fr.lip6.move.gal.Or;
import fr.lip6.move.gal.System;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.VariableRef;
import fr.lip6.move.gal.logic.BooleanExpression;
import fr.lip6.move.gal.logic.CardMarking;
import fr.lip6.move.gal.logic.Enabling;
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
					fr.lip6.move.gal.BooleanExpression b = tr.getGuard();
					BooleanExpression bctl = toLogic(b);
					EcoreUtil.replace(obj, bctl);
				}
			}
		}
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
