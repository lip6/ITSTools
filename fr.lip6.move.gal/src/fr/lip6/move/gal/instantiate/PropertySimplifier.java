package fr.lip6.move.gal.instantiate;

import java.util.logging.Logger;

import fr.lip6.move.gal.And;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ConstParameter;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.InvariantProp;
import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Not;
import fr.lip6.move.gal.Or;
import fr.lip6.move.gal.ParamRef;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.QualifiedReference;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.UnaryMinus;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableReference;

/**
 * A class to simplify trivial queries, based on initial state values.
 * @author Yann
 *
 */
public class PropertySimplifier {

	
	public static void rewriteWithInitialState(Specification spec) {
		for (Property prop : spec.getProperties()) {
			// prop.setComment(null);
			if (prop.getBody() instanceof ReachableProp) {
				ReachableProp p = (ReachableProp) prop.getBody();
				boolean b = evalInInitialState(p.getPredicate());
				if (b) {
					getLog().info("Reachable property "
							+ prop.getName()
							+ " is trivially true : it is verified in initial state.");
					// rewrite to Possibly True.
//					System.out.println("FORMULA "+prop.getName() + " TRUE TECHNIQUES TOPOLOGICAL");
					p.setPredicate(GalFactory.eINSTANCE.createTrue());;					
				}
			} else if (prop.getBody() instanceof NeverProp) {
				NeverProp p = (NeverProp) prop.getBody();
				boolean b = evalInInitialState(p.getPredicate());
				if (b) {
					getLog().info("Never property "
							+ prop.getName()
							+ " is trivially false : it is verified in initial state.");
					// rewrite to Possibly True.
//					System.out.println("FORMULA "+prop.getName() + " FALSE TECHNIQUES TOPOLOGICAL");
					p.setPredicate(GalFactory.eINSTANCE.createTrue());;					
				}
			} else if (prop.getBody() instanceof InvariantProp) {
				InvariantProp p = (InvariantProp) prop.getBody();
				boolean b = evalInInitialState(p.getPredicate());
				if (!b) {
					getLog().info("Invariant property "
							+ prop.getName()
							+ " is trivially false : it is not verified in initial state.");
					// rewrite to Possibly True.
//					System.out.println("FORMULA "+prop.getName() + " FALSE TECHNIQUES TOPOLOGICAL");
					p.setPredicate(GalFactory.eINSTANCE.createFalse());;					
				}
			} 
//			} else if (prop.getProp() instanceof CtlProp) {
//				BooleanExpression form = prop.getProp().getFormula();
//				simplifyCTLInitial(s, form, prop.getName());
//			}
		}

	}

	private static boolean evalInInitialState(BooleanExpression e) {
		if (e instanceof True) {
			return true;
		} else if (e instanceof False) {
			return false;
		} else if (e instanceof And) {
			And and = (And) e;
			return evalInInitialState(and.getLeft())
					&& evalInInitialState(and.getRight());
		} else if (e instanceof Or) {
			Or or = (Or) e;
			return evalInInitialState(or.getLeft())
					|| evalInInitialState(or.getRight());
		} else if (e instanceof Not) {
			Not not = (Not) e;
			return ! evalInInitialState(not.getValue());
		} else if (e instanceof Comparison) {
			Comparison cmp = (Comparison) e;
			int l = evalInInitialState(cmp.getLeft());
			int r = evalInInitialState(cmp.getRight());
			switch (cmp.getOperator()) {
			case EQ:
				return l == r;
			case NE:
				return l != r;
			case GT:
				return l > r;
			case GE:
				return l >= r;
			case LT:
				return l < r;
			case LE:
				return l <= r;
			default:
				java.lang.System.err
				.println("Unknown operator in comparison !");
				return false;
			}
//		} else if (e instanceof Deadlock) {
//			for (Transition t : s.getTransitions()) {
//				java.util.List<Transition> inst = Instantiator
//						.instantiateParameters(t);
//				for (Transition tr : inst) {
//					fr.lip6.move.gal.BooleanExpression g = tr.getGuard();
//					if (evalInInitialState(s, toLogic(g))) {
//						return false;
//					}
//				}
//			}
//			return true;
		} else {
			getLog().warning("Unexpected boolean logic operator in evalInInitialState "
					+ e.getClass().getName());
		}

		return false;
	}

	
	private static int evalInInitialState(IntExpression e) {
		if (e instanceof Constant) {
			Constant cte = (Constant) e;
			return cte.getValue();
		} else if (e instanceof VariableReference) {
			VariableReference vr = (VariableReference) e;
			// hopefully all type parameters are already instantiated.
			try {
			if (vr.getIndex() == null) {
				return 	evalInInitialState( ((Variable) vr.getRef()).getValue()) ;
			} else {
				return ((Constant) ((ArrayPrefix) vr.getRef()).getValues().get( ((Constant)vr.getIndex()).getValue()  )).getValue();				
			}
			} catch (ClassCastException ce) {
				getLog().warning("Expected only constant variable access in simplify in initial state procedure.");
				throw ce;
			}
		} else if (e instanceof BinaryIntExpression) {
			BinaryIntExpression bin = (BinaryIntExpression) e;
			int l = evalInInitialState(bin.getLeft());
			int r = evalInInitialState(bin.getRight());
			int res = 0;
			if ("+".equals(bin.getOp())) {
				res = l + r;
			} else if ("-".equals(bin.getOp())) {
				res = l - r;
			} else if ("/".equals(bin.getOp())) {
				res = l / r;
			} else if ("*".equals(bin.getOp())) {
				res = l * r;
			} else if ("**".equals(bin.getOp())) {
				res = (int) Math.pow(l, r);
			} else if ("%".equals(bin.getOp())) {
				res = l % r;
			} else {
				getLog().warning("Unexpected operator in simplify procedure:"
						+ bin.getOp());
			}
			return res;
		} else if (e instanceof QualifiedReference) {
			QualifiedReference qref = (QualifiedReference) e;
			return evalInInitialState(qref.getNext());
		} else if (e instanceof UnaryMinus) {
			UnaryMinus um = (UnaryMinus) e;
			return - evalInInitialState(um.getValue());
		} else if (e instanceof ParamRef) {
			ParamRef pref = (ParamRef) e;
			if (pref.getRefParam() instanceof ConstParameter) {
				return ((ConstParameter) pref.getRefParam()).getValue();
			}
		} else {
			getLog().warning("Unexpected IntExpression in simplify procedure:"
					+ e.getClass().getName());
		}

		return 0;
	}

	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}

	
}
