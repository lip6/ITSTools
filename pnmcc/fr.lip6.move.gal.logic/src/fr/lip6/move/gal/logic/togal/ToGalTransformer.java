package fr.lip6.move.gal.logic.togal;

import java.util.logging.Logger;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.LogicProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.VarDecl;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.instantiate.Instantiator;
import fr.lip6.move.gal.instantiate.Simplifier;
import fr.lip6.move.gal.logic.*;

public class ToGalTransformer {

	public static Specification toGal (Properties props) {
		Specification spec = (Specification) props.getSystem().eContainer();
		for (PropertyDesc prop : props.getProps()) {
			spec.getProperties().add(toGal(prop));
		}		
		return spec;
	}

	public static Property toGal (PropertyDesc pdesc) {
		Property prop =GalFactory.eINSTANCE.createProperty();

		prop.setName(pdesc.getName());

		LogicProp lprop ;
		// reachability ?
		if (pdesc.getProp().getFormula() instanceof Ef  && hasNoTemporal(((Ef)pdesc.getProp().getFormula()).getForm())) {
			Ef ef = (Ef) pdesc.getProp().getFormula();
			lprop = GalFactory.eINSTANCE.createReachableProp();
			lprop.setPredicate(toGal(ef.getForm()));
		} else if (pdesc.getProp().getFormula() instanceof Ag  && hasNoTemporal(((Ag)pdesc.getProp().getFormula()).getForm())) {
			Ag ag = (Ag) pdesc.getProp().getFormula();
			lprop = GalFactory.eINSTANCE.createInvariantProp();
			lprop.setPredicate(toGal(ag.getForm()));
		} else if (pdesc.getProp().getFormula() instanceof True ) {
			lprop = GalFactory.eINSTANCE.createReachableProp();
			lprop.setPredicate(GalFactory.eINSTANCE.createTrue());
		} else if (pdesc.getProp().getFormula() instanceof False ) {
			lprop = GalFactory.eINSTANCE.createReachableProp();
			lprop.setPredicate(GalFactory.eINSTANCE.createFalse());
		} else {
			getLog().warning("Could not translate property :" + pdesc.getName());
			lprop = GalFactory.eINSTANCE.createReachableProp();
			lprop.setPredicate(GalFactory.eINSTANCE.createTrue());
		}
		prop.setBody(lprop );
		return prop;
	}

	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}

	private static boolean hasNoTemporal(BooleanExpression form) {
		for (TreeIterator<EObject> it = form.eAllContents() ; it.hasNext(); ) {
			EObject obj = it.next();
			if (obj instanceof Ef || obj instanceof Eg 
					|| obj instanceof Af || obj instanceof Ag 
					|| obj instanceof Ax || obj instanceof Ex 
					|| obj instanceof Eu || obj instanceof Au) {
				return false;
			}
		}
		return true;
	}

	private static fr.lip6.move.gal.BooleanExpression toGal(
			BooleanExpression obj) {
		if (obj instanceof And) {
			And and = (And) obj;
			return GF2.and(toGal(and.getLeft()), toGal(and.getRight()));
		} else if (obj instanceof Or) {
			Or or = (Or) obj;
			return GF2.or(toGal(or.getLeft()), toGal(or.getRight()));
		} else if (obj instanceof Not) {
			Not not = (Not) obj;
			return GF2.not(toGal(not.getValue()));
		} else if (obj instanceof Comparison) {
			Comparison cmp = (Comparison) obj;
			return GF2.createComparison(toGal(cmp.getLeft()), toGal(cmp.getOperator()), toGal(cmp.getRight()));
		} else if (obj instanceof True) {
			return GalFactory.eINSTANCE.createTrue();
		} else if (obj instanceof False) {
			return GalFactory.eINSTANCE.createFalse();
		} else if (obj instanceof Enabling) {
			Enabling enab = (Enabling) obj;
			fr.lip6.move.gal.BooleanExpression res = GalFactory.eINSTANCE.createFalse();
			for (Transition tr : enab.getTrans()) {
				if (withAbstractColors) {
					Transition tcopy = EcoreUtil.copy(tr);
					Instantiator.abstractArraystoSingleCell(tcopy);
					res = tcopy.getGuard();
				} else {				
					java.util.List<Transition> inst = Instantiator
							.instantiateParameters(tr);
					for (Transition t : inst) {
						fr.lip6.move.gal.BooleanExpression g = t.getGuard();

						res = GF2.or(res,EcoreUtil.copy(g));
					}
				}
			}
			// just a dirty trick to ensure we can get the result of simplify we need a context.
			fr.lip6.move.gal.Not not = GalFactory.eINSTANCE.createNot();
			not.setValue(res);
			Simplifier.simplify(res);
			res = not.getValue();
			return res ;
		} else {
			getLog().warning("Unknown predicate type in boolean expression "
					+ obj.getClass().getName());
		}
		return GalFactory.eINSTANCE.createTrue();
	}

	private static fr.lip6.move.gal.ComparisonOperators toGal(
			ComparisonOperators operator) {
		switch (operator) {
		case EQ:
			return fr.lip6.move.gal.ComparisonOperators.EQ;
		case NE:
			return fr.lip6.move.gal.ComparisonOperators.NE;
		case GT:
			return fr.lip6.move.gal.ComparisonOperators.GT;
		case GE:
			return fr.lip6.move.gal.ComparisonOperators.GE;
		case LT:
			return fr.lip6.move.gal.ComparisonOperators.LT;
		case LE:
			return fr.lip6.move.gal.ComparisonOperators.LE;
		default:
			getLog().warning("Unknown operator in comparison !");
			return fr.lip6.move.gal.ComparisonOperators.EQ;
		}
	}



	private static fr.lip6.move.gal.IntExpression toGal(IntExpression e) {
		if (e instanceof BinaryIntExpression) {
			BinaryIntExpression bin = (BinaryIntExpression) e;
			return GF2.createBinaryIntExpression(toGal(bin.getLeft()), bin.getOp(), toGal(bin.getRight()));
		} else if (e instanceof VariableRef) {
			VariableRef vr = (VariableRef) e;
			return GF2.createVariableRef(vr.getReferencedVar());
		} else if (e instanceof ArrayVarAccess) {
			ArrayVarAccess ava = (ArrayVarAccess) e;
			return GF2.createArrayVarAccess(ava.getPrefix(), toGal(ava.getIndex()));
		} else if (e instanceof Constant) {
			Constant c = (Constant) e;
			return GF2.constant(c.getValue());
		} else if (e instanceof CardMarking) {
			CardMarking cm = (CardMarking) e;
			fr.lip6.move.gal.IntExpression sum = null;
			for (VarDecl place : cm.getPlaces()) {
				fr.lip6.move.gal.IntExpression cur=null;
				if (place instanceof Variable) {
					Variable pl = (Variable) place;
					VariableReference vl = GF2.createVariableRef(pl);
					cur = vl;
				} else if (place instanceof ArrayPrefix) {
					ArrayPrefix ap = (ArrayPrefix) place;
					cur = createSumOfArray(ap);
				}
				if (sum == null) {
					sum = cur;
				} else {
					fr.lip6.move.gal.IntExpression add = GF2.createBinaryIntExpression(sum, "+", cur);
					sum = add;
				}
			}
			return sum;			
		} else {
			getLog().warning("Unknown type in integer toGal for expression "
					+ e.getClass().getName());
		}

		return GF2.constant(0);
	}

	private static boolean withAbstractColors = false;
	public static void setWithAbstractColors(boolean withAbstractColors) {
		ToGalTransformer.withAbstractColors = withAbstractColors;
	}
	private static fr.lip6.move.gal.IntExpression createSumOfArray(ArrayPrefix l) {
		if (withAbstractColors) {
			return  GF2.createArrayVarAccess(l, GF2.constant(0));
		} else {
			fr.lip6.move.gal.IntExpression sum = null;
			for (int i = 0; i < ((fr.lip6.move.gal.Constant) l.getSize()).getValue(); i++) {
				VariableReference av = GF2.createArrayVarAccess(l, GF2.constant(i));
				if (sum == null) {
					sum = av;
				} else {
					fr.lip6.move.gal.IntExpression bin = GF2.createBinaryIntExpression(sum, "+", av);
					sum = bin;
				}
			}
			return sum;
		}
	}
}

