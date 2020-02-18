package fr.lip6.move.gal.instantiate;

import java.util.logging.Logger;

import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.And;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.BoolProp;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.ConstParameter;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.GF2;
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
import fr.lip6.move.gal.SafetyProp;
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
				if (p.getPredicate() instanceof True || p.getPredicate() instanceof False) {
					continue;
				}
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
				if (p.getPredicate() instanceof True || p.getPredicate() instanceof False) {
					continue;
				}
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
				if (p.getPredicate() instanceof True || p.getPredicate() instanceof False) {
					continue;
				}
				boolean b = evalInInitialState(p.getPredicate());
				if (!b) {
					getLog().info("Invariant property "
							+ prop.getName()
							+ " is trivially false : it is not verified in initial state.");
					// rewrite to Possibly True.
//					System.out.println("FORMULA "+prop.getName() + " FALSE TECHNIQUES TOPOLOGICAL");
					p.setPredicate(GalFactory.eINSTANCE.createFalse());;					
				}
			} else if (prop.getBody() instanceof BoolProp) {
				BoolProp bprop = (BoolProp) prop.getBody();
				replaceWithInitial(bprop.getPredicate());
				bprop.setPredicate(Simplifier.simplify(bprop.getPredicate()));
			}
//			} else if (prop.getProp() instanceof CtlProp) {
//				BooleanExpression form = prop.getProp().getFormula();
//				simplifyCTLInitial(s, form, prop.getName());
//			}
			
			
		}

	}

	public static void replaceWithInitial (BooleanExpression e) {
		// basically, as long as we don't find temporal connectives, just evaluate in initial state
		if (e instanceof And) {
			And and = (And) e;
			replaceWithInitial(and.getLeft());
			replaceWithInitial(and.getRight());
		} else if (e instanceof Or) {
			Or and = (Or) e;
			replaceWithInitial(and.getLeft());
			replaceWithInitial(and.getRight());
		} else if (e instanceof Not) {
			Not not = (Not) e;
			replaceWithInitial(not.getValue());
		} else if (e instanceof Comparison) {
			if (evalInInitialState(e)) {
				EcoreUtil.replace(e, GalFactory.eINSTANCE.createTrue());
			} else {
				EcoreUtil.replace(e, GalFactory.eINSTANCE.createFalse());
			}
		} else {
			return;
		}		
	}
	
	public static boolean evalInInitialState(BooleanExpression e) {
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

	public static BooleanExpression assumeOneBounded(Comparison cmp) {
		// normalize
		ComparisonOperators op = cmp.getOperator();
		IntExpression l = cmp.getLeft();
		IntExpression r = cmp.getRight();
		switch (op) {
		case GE :
			l = cmp.getRight();
			r = cmp.getLeft();
			op = ComparisonOperators.LE;
			break;
		case GT :
			l = cmp.getRight();
			r = cmp.getLeft();
			op = ComparisonOperators.LT;
			break;
		}
		BooleanExpression res;
		// break into cases
		switch (op) {
		case EQ :
			// both 0 or both 1
			res = GF2.and(
					GF2.createComparison(EcoreUtil.copy(l), ComparisonOperators.EQ, GF2.constant(0)),
					GF2.createComparison(EcoreUtil.copy(r), ComparisonOperators.EQ, GF2.constant(0)));
			res = GF2.or( res , GF2.and(
					GF2.createComparison(EcoreUtil.copy(l), ComparisonOperators.EQ, GF2.constant(1)),
					GF2.createComparison(EcoreUtil.copy(r), ComparisonOperators.EQ, GF2.constant(1))));
			break;
		case NE :
			// 01 or 10
			res = GF2.and(
					GF2.createComparison(EcoreUtil.copy(l), ComparisonOperators.EQ, GF2.constant(0)),
					GF2.createComparison(EcoreUtil.copy(r), ComparisonOperators.EQ, GF2.constant(1)));
			res = GF2.or( res , GF2.and(
					GF2.createComparison(EcoreUtil.copy(l), ComparisonOperators.EQ, GF2.constant(1)),
					GF2.createComparison(EcoreUtil.copy(r), ComparisonOperators.EQ, GF2.constant(0))));
			break;
		case LT :
			// 01
			res = GF2.and(
					GF2.createComparison(EcoreUtil.copy(l), ComparisonOperators.EQ, GF2.constant(0)),
					GF2.createComparison(EcoreUtil.copy(r), ComparisonOperators.EQ, GF2.constant(1)));
			break;
		case LE :
			// 0* or 11 => r is 1 or l is 0 => 0* or *1
			res = GF2.or( 
					GF2.createComparison(EcoreUtil.copy(l), ComparisonOperators.EQ, GF2.constant(0)),
					GF2.createComparison(EcoreUtil.copy(r), ComparisonOperators.EQ, GF2.constant(1))
					);
			break;	
		default :
			throw new RuntimeException("Unexpected comparison operator in conversion "+ cmp);
		}
		return res;
	}

	
	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}

	public static void pushNegation(Specification spec) {
		for (Property prop : spec.getProperties()) {
			if (prop.getBody() instanceof SafetyProp) {
				BoolProp bp = (BoolProp) prop.getBody();
				pushNegation(bp.getPredicate(),false);
			}
		}
	}

	private static void pushNegation(BooleanExpression expr, boolean isNegated) {
		BooleanExpression e2 = pushNeg(expr, isNegated);
		EcoreUtil.replace(expr, e2);
	}
	
	private static BooleanExpression pushNeg(BooleanExpression expr, boolean isNegated) {
		if (expr instanceof And) {
			And and = (And) expr;
			BooleanExpression l = pushNeg(and.getLeft(),isNegated);
			BooleanExpression r = pushNeg(and.getRight(),isNegated);
			if (isNegated) {
				Or or = GalFactory.eINSTANCE.createOr();
				or.setLeft(l);
				or.setRight(r);
				return or;
			} else {
				And or = GalFactory.eINSTANCE.createAnd();
				or.setLeft(l);
				or.setRight(r);
				return or;
			}
		} else if (expr instanceof Or) {
			Or or = (Or) expr;
			BooleanExpression l = pushNeg(or.getLeft(),isNegated);
			BooleanExpression r = pushNeg(or.getRight(),isNegated);
			if (isNegated) {
				And and = GalFactory.eINSTANCE.createAnd();
				and.setLeft(l);
				and.setRight(r);
				return and;
			} else {
				Or and = GalFactory.eINSTANCE.createOr();
				and.setLeft(l);
				and.setRight(r);
				return and;
			}
		} else if (expr instanceof Not) {
			Not not = (Not) expr;
			BooleanExpression r = pushNeg(not.getValue(),!isNegated);
			return r;
		} else if (expr instanceof Comparison) {
			Comparison comp = (Comparison) expr;
			if (isNegated) {
				switch (comp.getOperator()) {
				case EQ : comp.setOperator(ComparisonOperators.NE); break;
				case NE : comp.setOperator(ComparisonOperators.EQ); break;
				case GE : comp.setOperator(ComparisonOperators.LT); break;
				case GT : comp.setOperator(ComparisonOperators.LE); break;
				case LT : comp.setOperator(ComparisonOperators.GE); break;
				case LE : comp.setOperator(ComparisonOperators.GT); break;
				}
			}
			return comp;
		} else if (expr instanceof True) {
			if (isNegated) {
				return GalFactory.eINSTANCE.createFalse();
			} else {
				return GalFactory.eINSTANCE.createTrue();
			}
		} else if (expr instanceof False) {
			if (isNegated) {
				return GalFactory.eINSTANCE.createTrue();
			} else {
				return GalFactory.eINSTANCE.createFalse();
			}
		} else {
			getLog().warning("Unexpected IntExpression in PushNegation procedure:"	+ expr);
			return null;
		}		
	}

	
}
