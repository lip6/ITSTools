package fr.lip6.move.gal.simplify;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.logic.*;
import fr.lip6.move.gal.support.ISupportVariable;
import fr.lip6.move.gal.support.Support;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.VarDecl;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.instantiate.Instantiator;
import fr.lip6.move.gal.instantiate.Simplifier;

public class LogicSimplifier {

	public static void simplify(Properties props) {

		for (PropertyDesc prop : props.getProps()) {
			for (EObject o : prop.eContents()) {
				rewrite(o);				
			}
		}

		rewriteWithInitialState(props);

		ConstantSimplifier.simplifyAllBools(props);

	}

	private static void rewriteWithInitialState(Properties props) {
		GALTypeDeclaration s = props.getSystem();
		for (PropertyDesc prop : props.getProps()) {
			prop.setComment(null);
			if (prop.getProp() instanceof ReachProp) {
				ReachProp p = (ReachProp) prop.getProp();
				boolean b = evalInInitialState(s, p.getFormula());
				if (!b && p.getInvariant().equals("I")) {
					java.lang.System.err.println("Invariant property "
							+ prop.getName()
							+ " is trivially false in initial state.");
					// rewrite to Invariant False
					p.setFormula(LogicFactory.eINSTANCE.createFalse());
				} else if (b && p.getInvariant().equals("N")) {
					java.lang.System.err.println("Never property "
							+ prop.getName()
							+ " is trivially true in initial state.");
					// rewrite to Never True.
					p.setFormula(LogicFactory.eINSTANCE.createTrue());
				} else if (b & p.getInvariant().equals("P")) {
					java.lang.System.err.println("Possibly property "
							+ prop.getName()
							+ " is trivially true in initial state.");
					// rewrite to Possibly True.
					p.setFormula(LogicFactory.eINSTANCE.createTrue());
				}
			} else if (prop.getProp() instanceof CtlProp) {
				BooleanExpression form = prop.getProp().getFormula();
				simplifyCTLInitial(s, form, prop.getName());
			}
		}

	}

	private static void simplifyCTLInitial(GALTypeDeclaration s,
			BooleanExpression form, String pname) {
		if (form instanceof SingleCtl || form instanceof Au || form instanceof Eu) {

			BooleanExpression subformula = null;
			BooleanExpression untilLeft = null;



			if (form instanceof SingleCtl) {
				SingleCtl ef = (SingleCtl) form;
				subformula = ef.getForm();
			} else {
				// EU or AU
				if (form instanceof Au) {
					Au au = (Au) form;
					subformula = au.getRight();
					untilLeft = au.getLeft();
				} else if (form instanceof Eu) {
					Eu eu = (Eu) form;
					subformula = eu.getRight();
					untilLeft = eu.getLeft();
				}
			}
			BooleanExpression sform = ConstantSimplifier.simplify(subformula);
			EcoreUtil.replace(subformula,sform);
			subformula = sform;
			if (untilLeft != null) {
				BooleanExpression suntil = ConstantSimplifier.simplify(untilLeft);
				EcoreUtil.replace(untilLeft,suntil);
				untilLeft = suntil;
			}
			if (isPureBoolean(subformula)) {
				boolean b = evalInInitialState(s, subformula);
				if (b && form instanceof Ef) {
					java.lang.System.err.println("EF property " + pname
							+ " is trivially true in initial state.");
					EcoreUtil
					.replace(form, LogicFactory.eINSTANCE.createTrue());
				} else if (!b && form instanceof Eg) {
					java.lang.System.err.println("EG property " + pname
							+ " is trivially false in initial state.");
					EcoreUtil.replace(form,
							LogicFactory.eINSTANCE.createFalse());
				} else if (b && form instanceof Af) {
					java.lang.System.err.println("AF property " + pname
							+ " is trivially true in initial state.");
					EcoreUtil
					.replace(form, LogicFactory.eINSTANCE.createTrue());
				} else if (!b && form instanceof Ag) {
					java.lang.System.err.println("AG property " + pname
							+ " is trivially false in initial state.");
					EcoreUtil.replace(form,
							LogicFactory.eINSTANCE.createFalse());
				} else if (form instanceof Au || form instanceof Eu) {
					if (b) {
						java.lang.System.err
						.println("(A p U q) or (E p U q) property "
								+ pname
								+ " is trivially true in initial state (q holds in initial state).");
						EcoreUtil.replace(form,
								LogicFactory.eINSTANCE.createTrue());
					} else {
						if (subformula instanceof False) {
							java.lang.System.err
							.println("(A p U q) or (E p U q) property "
									+ pname
									+ " is trivially false in initial state (q is a tautology for False).");
							EcoreUtil.replace(form,
									LogicFactory.eINSTANCE.createFalse());
						}

						if (isPureBoolean(untilLeft)) {
							boolean bb = evalInInitialState(s, untilLeft);
							if (!bb) {
								java.lang.System.err
								.println("(A p U q) or (E p U q) property "
										+ pname
										+ " is trivially false in initial state (!p and !q hold in initial state).");
								EcoreUtil.replace(form,
										LogicFactory.eINSTANCE.createFalse());
							}
						}
					}
				}
			} 


		} else if (form instanceof fr.lip6.move.gal.logic.And) {
			fr.lip6.move.gal.logic.And and = (fr.lip6.move.gal.logic.And) form;
			simplifyCTLInitial(s, and.getLeft(), pname);
			simplifyCTLInitial(s, and.getRight(), pname);
			EcoreUtil.replace(and, ConstantSimplifier.simplify(and));
		} else if (form instanceof fr.lip6.move.gal.logic.Or) {
			fr.lip6.move.gal.logic.Or or = (fr.lip6.move.gal.logic.Or) form;
			simplifyCTLInitial(s, or.getLeft(), pname);
			simplifyCTLInitial(s, or.getRight(), pname);
			EcoreUtil.replace(or, ConstantSimplifier.simplify(or));
		} else if (form instanceof fr.lip6.move.gal.logic.Not) {
			fr.lip6.move.gal.logic.Not not = (fr.lip6.move.gal.logic.Not) form;
			simplifyCTLInitial(s, not.getValue(), pname);
			EcoreUtil.replace(not, ConstantSimplifier.simplify(not));
		} else if (form instanceof True || form instanceof False) {
			// do nothing
		} else {
			java.lang.System.err
			.println("Unexpected Ctl formula operator "+form.getClass().getName().replace("Impl", "") + " encountered.");
		}
	}

	private static boolean isPureBoolean(EObject form) {
		if (form instanceof SingleCtl || form instanceof Au || form instanceof Eu) {
			return false;
		}

		for (EObject child : form.eContents()) {
			if (! isPureBoolean(child))
				return false;
		}
		return true;
	}

	private static boolean evalInInitialState(GALTypeDeclaration s,
			BooleanExpression e) {
		if (e instanceof fr.lip6.move.gal.logic.True) {
			return true;
		} else if (e instanceof fr.lip6.move.gal.logic.False) {
			return false;
		} else if (e instanceof fr.lip6.move.gal.logic.And) {
			fr.lip6.move.gal.logic.And and = (fr.lip6.move.gal.logic.And) e;
			return evalInInitialState(s, and.getLeft())
					&& evalInInitialState(s, and.getRight());
		} else if (e instanceof fr.lip6.move.gal.logic.Or) {
			fr.lip6.move.gal.logic.Or or = (fr.lip6.move.gal.logic.Or) e;
			return evalInInitialState(s, or.getLeft())
					|| evalInInitialState(s, or.getRight());
		} else if (e instanceof fr.lip6.move.gal.logic.Not) {
			fr.lip6.move.gal.logic.Not not = (fr.lip6.move.gal.logic.Not) e;
			return evalInInitialState(s, not.getValue());
		} else if (e instanceof fr.lip6.move.gal.logic.Comparison) {
			fr.lip6.move.gal.logic.Comparison cmp = (fr.lip6.move.gal.logic.Comparison) e;
			int l = evalInInitialState(s, cmp.getLeft());
			int r = evalInInitialState(s, cmp.getRight());
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
		} else if (e instanceof Deadlock) {
			for (Transition t : s.getTransitions()) {
				java.util.List<Transition> inst = Instantiator
						.instantiateParameters(t);
				for (Transition tr : inst) {
					fr.lip6.move.gal.BooleanExpression g = tr.getGuard();
					if (evalInInitialState(s, toLogic(g))) {
						return false;
					}
				}
			}
			return true;
		} else {
			java.lang.System.err
			.println("Unexpected boolean logic operator in evalInInitialState "
					+ e.getClass().getName());
		}

		return false;
	}

	private static int evalInInitialState(GALTypeDeclaration s, IntExpression e) {
		if (e instanceof fr.lip6.move.gal.logic.Constant) {
			fr.lip6.move.gal.logic.Constant cte = (fr.lip6.move.gal.logic.Constant) e;
			return cte.getValue();
		} else if (e instanceof fr.lip6.move.gal.logic.VariableRef) {
			fr.lip6.move.gal.logic.VariableRef vr = (fr.lip6.move.gal.logic.VariableRef) e;
			// hopefully all type parameters are already instantiated.
			return ((fr.lip6.move.gal.Constant) vr.getReferencedVar().getValue()).getValue();
		} else if (e instanceof fr.lip6.move.gal.logic.ArrayVarAccess) {
			fr.lip6.move.gal.logic.ArrayVarAccess av = (fr.lip6.move.gal.logic.ArrayVarAccess) e;
			int index = ((fr.lip6.move.gal.logic.Constant) av.getIndex())
					.getValue();
			int val = ((fr.lip6.move.gal.Constant) av.getPrefix().getValues().get(index))
					.getValue();
			return val;
		} else if (e instanceof fr.lip6.move.gal.logic.BinaryIntExpression) {
			fr.lip6.move.gal.logic.BinaryIntExpression bin = (fr.lip6.move.gal.logic.BinaryIntExpression) e;
			int l = evalInInitialState(s, bin.getLeft());
			int r = evalInInitialState(s, bin.getRight());
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
				java.lang.System.err
				.println("Unexpected operator in simplify procedure:"
						+ bin.getOp());
			}
			return res;
		} else {
			java.lang.System.err
			.println("Unexpected IntExpression in simplify procedure:"
					+ e.getClass().getName());
		}

		return 0;
	}

	private static void rewrite(EObject obj) {
		for (EObject child : obj.eContents()) {
			rewrite(child);
		}
		if (obj instanceof Enabling) {
			Enabling enab = (Enabling) obj;
			fr.lip6.move.gal.BooleanExpression b = GalFactory.eINSTANCE.createFalse();
			for (Transition tr : enab.getTrans()) {
				java.util.List<Transition> inst = Instantiator
						.instantiateParameters(tr);
				for (Transition t : inst) {
					fr.lip6.move.gal.BooleanExpression g = t.getGuard();

					b = GF2.or(b,EcoreUtil.copy(g));
				}
				// just a dirty trick to ensure we can get the result of simplify we need a context.
				fr.lip6.move.gal.Not not = GalFactory.eINSTANCE.createNot();
				not.setValue(b);
				Simplifier.simplify(b);
				b = not.getValue();
			}
			BooleanExpression bctl = toLogic(b);
			EcoreUtil.replace(obj, bctl);
		} else if (obj instanceof CardMarking) {
			CardMarking cm = (CardMarking) obj;
			fr.lip6.move.gal.logic.IntExpression sum = null;
			for (VarDecl place : cm.getPlaces()) {
				fr.lip6.move.gal.logic.IntExpression cur=null;
				if (place instanceof Variable) {
					Variable pl = (Variable) place;
					fr.lip6.move.gal.logic.VariableRef vl = LogicFactory.eINSTANCE
							.createVariableRef();
					vl.setReferencedVar(pl);
					cur = vl;
				} else if (place instanceof ArrayPrefix) {
					ArrayPrefix ap = (ArrayPrefix) place;
					cur = createSumOfArray(ap);
				}
				if (sum == null) {
					sum = cur;
				} else {
					BinaryIntExpression add = LogicFactory.eINSTANCE.createBinaryIntExpression();
					add.setLeft(sum);
					add.setOp("+");
					add.setRight(cur);
					sum = add;
				}
			}
			EcoreUtil.replace(obj, sum);			
		} else if (obj instanceof fr.lip6.move.gal.logic.Comparison) {
			fr.lip6.move.gal.logic.Comparison cmp = (fr.lip6.move.gal.logic.Comparison) obj;
			if (cmp.getLeft() instanceof MarkingRef
					&& cmp.getRight() instanceof MarkingRef) {
				MarkingRef left = (MarkingRef) cmp.getLeft();
				MarkingRef right = (MarkingRef) cmp.getRight();
				if (left.getPlace() instanceof Variable
						&& right.getPlace() instanceof Variable) {
					fr.lip6.move.gal.logic.VariableRef vl = LogicFactory.eINSTANCE
							.createVariableRef();
					vl.setReferencedVar((Variable) left.getPlace());
					cmp.setLeft(vl);
					fr.lip6.move.gal.logic.VariableRef vr = LogicFactory.eINSTANCE
							.createVariableRef();
					vr.setReferencedVar((Variable) right.getPlace());
					cmp.setRight(vr);
				} else if (left.getPlace() instanceof ArrayPrefix
						&& right.getPlace() instanceof ArrayPrefix
						&& ((ArrayPrefix) left.getPlace()).getSize() == ((ArrayPrefix) right
								.getPlace()).getSize()) {
					ArrayPrefix l = (ArrayPrefix) left.getPlace();
					ArrayPrefix r = (ArrayPrefix) right.getPlace();
					int size = l.getSize();
					assert size >= 1;
					fr.lip6.move.gal.logic.BooleanExpression combineComp = createArrayComparisons(
							l, cmp.getOperator(), r);
					EcoreUtil.replace(obj, combineComp);
				}

			}
		} else if (obj instanceof Imply) {
			Imply imp = (Imply) obj;
			fr.lip6.move.gal.logic.Not not = LogicFactory.eINSTANCE.createNot();
			not.setValue(EcoreUtil.copy(imp.getRight()));

			fr.lip6.move.gal.logic.Or or = LogicFactory.eINSTANCE.createOr();
			or.setOp("||");
			or.setLeft(not);
			or.setRight(EcoreUtil.copy(imp.getLeft()));

			EcoreUtil.replace(obj, or);
		} else if (obj instanceof Equiv) {
			Equiv imp = (Equiv) obj;
			fr.lip6.move.gal.logic.Not not1 = LogicFactory.eINSTANCE
					.createNot();
			not1.setValue(EcoreUtil.copy(imp.getLeft()));

			fr.lip6.move.gal.logic.Not not2 = LogicFactory.eINSTANCE
					.createNot();
			not2.setValue(EcoreUtil.copy(imp.getRight()));

			fr.lip6.move.gal.logic.And and1 = LogicFactory.eINSTANCE
					.createAnd();
			and1.setOp("&&");
			and1.setLeft(not1);
			and1.setRight(not2);

			fr.lip6.move.gal.logic.And and2 = LogicFactory.eINSTANCE
					.createAnd();
			and2.setOp("&&");
			and2.setLeft(EcoreUtil.copy(imp.getLeft()));
			and2.setRight(EcoreUtil.copy(imp.getRight()));

			fr.lip6.move.gal.logic.Or or = LogicFactory.eINSTANCE.createOr();
			or.setOp("||");
			or.setLeft(and1);
			or.setRight(and2);

			EcoreUtil.replace(obj, or);
		} else if (obj instanceof XOr) {
			XOr imp = (XOr) obj;
			// a xor b    rewrites to :  !a&b  || a&!b
			fr.lip6.move.gal.logic.Not nota = LogicFactory.eINSTANCE
					.createNot();
			nota.setValue(EcoreUtil.copy(imp.getLeft()));

			fr.lip6.move.gal.logic.Not notb = LogicFactory.eINSTANCE
					.createNot();
			notb.setValue(EcoreUtil.copy(imp.getRight()));

			fr.lip6.move.gal.logic.And and1 = LogicFactory.eINSTANCE
					.createAnd();
			and1.setOp("&&");
			and1.setLeft(nota);
			and1.setRight(EcoreUtil.copy(imp.getRight()));

			fr.lip6.move.gal.logic.And and2 = LogicFactory.eINSTANCE
					.createAnd();
			and2.setOp("&&");
			and2.setLeft(EcoreUtil.copy(imp.getLeft()));
			and2.setRight(notb);

			fr.lip6.move.gal.logic.Or or = LogicFactory.eINSTANCE.createOr();
			or.setOp("||");
			or.setLeft(and1);
			or.setRight(and2);

			EcoreUtil.replace(obj, or);
		} else if (obj instanceof fr.lip6.move.gal.logic.And) {
			((fr.lip6.move.gal.logic.And) obj).setOp("&&");
		} else if (obj instanceof fr.lip6.move.gal.logic.Or) {
			((fr.lip6.move.gal.logic.Or) obj).setOp("||");
		}
	}

	private static BooleanExpression createArrayComparisons(ArrayPrefix l,
			ComparisonOperators operator, ArrayPrefix r) {
		ComparisonOperators op = operator;
		if (operator == ComparisonOperators.NE) {
			op = ComparisonOperators.EQ;
		}
		int size = l.getSize();

		BooleanExpression toret = null;
		for (int i = 0; i < size; i++) {
			fr.lip6.move.gal.logic.Comparison cmp = createComparison(l, op, r,
					i);

			if (toret == null) {
				toret = cmp;
			} else {
				fr.lip6.move.gal.logic.And and = LogicFactory.eINSTANCE
						.createAnd();
				and.setOp("&&");
				and.setLeft(toret);
				and.setRight(cmp);
				toret = and;
			}
		}

		if (operator == ComparisonOperators.NE) {
			fr.lip6.move.gal.logic.Not not = LogicFactory.eINSTANCE.createNot();
			not.setValue(toret);
			toret = not;
		}

		return toret;
	}

	private static IntExpression createSumOfArray(ArrayPrefix l) {

		IntExpression sum = null;
		for (int i = 0; i < l.getSize(); i++) {
			fr.lip6.move.gal.logic.ArrayVarAccess av = LogicFactory.eINSTANCE
					.createArrayVarAccess();
			av.setPrefix(l);
			av.setIndex(constant(i));

			if (sum == null) {
				sum = av;
			} else {
				fr.lip6.move.gal.logic.BinaryIntExpression bin = LogicFactory.eINSTANCE
						.createBinaryIntExpression();
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
		fr.lip6.move.gal.logic.ArrayVarAccess left = LogicFactory.eINSTANCE
				.createArrayVarAccess();
		left.setPrefix(l);
		left.setIndex(constant(i));

		fr.lip6.move.gal.logic.ArrayVarAccess right = LogicFactory.eINSTANCE
				.createArrayVarAccess();
		right.setPrefix(r);
		right.setIndex(constant(i));

		fr.lip6.move.gal.logic.Comparison cmp = LogicFactory.eINSTANCE
				.createComparison();
		cmp.setOperator(operator);
		cmp.setLeft(left);
		cmp.setRight(right);

		return cmp;
	}

	private static IntExpression constant(int i) {
		fr.lip6.move.gal.logic.Constant c = LogicFactory.eINSTANCE
				.createConstant();
		c.setValue(i);
		return c;
	}
	

	

	private static BooleanExpression toLogic(
			fr.lip6.move.gal.BooleanExpression b) {
		if (b instanceof fr.lip6.move.gal.And) {
			fr.lip6.move.gal.And and = (fr.lip6.move.gal.And) b;
			fr.lip6.move.gal.logic.And and2 = LogicFactory.eINSTANCE
					.createAnd();
			and2.setOp("&&");
			and2.setLeft(toLogic(and.getLeft()));
			and2.setRight(toLogic(and.getRight()));
			return and2;
		} else if (b instanceof fr.lip6.move.gal.Or) {
			fr.lip6.move.gal.Or and = (fr.lip6.move.gal.Or) b;
			Or and2 = LogicFactory.eINSTANCE.createOr();
			and2.setOp("||");
			and2.setLeft(toLogic(and.getLeft()));
			and2.setRight(toLogic(and.getRight()));
			return and2;
		} else if (b instanceof fr.lip6.move.gal.Not) {
			fr.lip6.move.gal.Not and = (fr.lip6.move.gal.Not) b;
			Not and2 = LogicFactory.eINSTANCE
					.createNot();
			and2.setValue(toLogic(and.getValue()));
			return and2;
		} else if (b instanceof fr.lip6.move.gal.Comparison) {
			fr.lip6.move.gal.Comparison and = (fr.lip6.move.gal.Comparison) b;
			Comparison and2 = LogicFactory.eINSTANCE
					.createComparison();
			and2.setLeft(toLogic(and.getLeft()));
			and2.setRight(toLogic(and.getRight()));
			and2.setOperator(toLogic(and.getOperator()));
			return and2;
		} else if (b instanceof fr.lip6.move.gal.True) {
			return LogicFactory.eINSTANCE.createTrue();
		} else if (b instanceof fr.lip6.move.gal.False) {
			return LogicFactory.eINSTANCE.createFalse();
		} else {
			java.lang.System.err
			.println("Unknown predicate type in boolean expression "
					+ b.getClass().getName());
		}
		return LogicFactory.eINSTANCE.createTrue();
	}

	private static ComparisonOperators toLogic(
			fr.lip6.move.gal.ComparisonOperators operator) {
		switch (operator) {
		case EQ:
			return ComparisonOperators.EQ;
		case NE:
			return ComparisonOperators.NE;
		case GT:
			return ComparisonOperators.GT;
		case GE:
			return ComparisonOperators.GE;
		case LT:
			return ComparisonOperators.LT;
		case LE:
			return ComparisonOperators.LE;
		default:
			java.lang.System.err.println("Unknown operator in comparison !");
			return ComparisonOperators.EQ;
		}
	}

	
	private static IntExpression toLogic(fr.lip6.move.gal.IntExpression e) {
		if (e instanceof fr.lip6.move.gal.BinaryIntExpression) {
			fr.lip6.move.gal.BinaryIntExpression bin = (fr.lip6.move.gal.BinaryIntExpression) e;
			BinaryIntExpression and2 = LogicFactory.eINSTANCE
					.createBinaryIntExpression();
			and2.setOp(bin.getOp());
			and2.setLeft(toLogic(bin.getLeft()));
			and2.setRight(toLogic(bin.getRight()));
			return and2;
		} else if (e instanceof fr.lip6.move.gal.VariableReference) {
			fr.lip6.move.gal.VariableReference vr = (fr.lip6.move.gal.VariableReference) e;
			if (vr.getIndex() == null) {
			VariableRef vr2 = LogicFactory.eINSTANCE
					.createVariableRef();
			vr2.setReferencedVar((Variable)vr.getRef());
			return vr2;
			} else {
				ArrayVarAccess a2 = LogicFactory.eINSTANCE
						.createArrayVarAccess();
				a2.setPrefix((ArrayPrefix)vr.getRef());
				a2.setIndex(toLogic(vr.getIndex()));
				return a2;
			}
		} else if (e instanceof fr.lip6.move.gal.Constant) {
			fr.lip6.move.gal.Constant c = (fr.lip6.move.gal.Constant) e;
			Constant c2 = LogicFactory.eINSTANCE
					.createConstant();
			c2.setValue(c.getValue());
			return c2;
		} else if (e instanceof fr.lip6.move.gal.UnaryMinus) {
			fr.lip6.move.gal.UnaryMinus um = (fr.lip6.move.gal.UnaryMinus) e;
			if (um.getValue() instanceof fr.lip6.move.gal.Constant) {
				fr.lip6.move.gal.Constant cte = (fr.lip6.move.gal.Constant) um.getValue();
				Constant c2 = LogicFactory.eINSTANCE
						.createConstant();
				c2.setValue(- cte.getValue());
				return c2;				
			} else {
				java.lang.System.err.println("Unexpected unary minus not followed by constant "
						+ e.getClass().getName());				
			}
		} else {
			java.lang.System.err.println("Unknown type in integer toLogic expression "
					+ e.getClass().getName());
		}

		Constant cte = LogicFactory.eINSTANCE.createConstant();
		cte.setValue(0);
		return cte;
	}


	public static void simplifyConstants(Properties props, Support constants) {
		Set<String> constVars = new HashSet<String>();
		for (ISupportVariable sv : constants) {			
			constVars.add(sv.toString());
		}

		// Substitute constants in guards and assignments
		for (TreeIterator<EObject> it = props.eAllContents() ; it.hasNext() ; ) {
			EObject obj = it.next();
			if (obj instanceof VariableRef) {
				VariableRef va = (VariableRef) obj;
				if (constVars.contains(va.getReferencedVar().getName())) {
					EcoreUtil.replace(va, toLogic(EcoreUtil.copy(va.getReferencedVar().getValue())));
				}
			} else if (obj instanceof ArrayVarAccess) {
				ArrayVarAccess av = (ArrayVarAccess) obj;

				if ( av.getIndex() instanceof Constant ) {
					int index = ((Constant) av.getIndex()).getValue();
					if (constVars.contains(av.getPrefix().getName()+"["+index+"]")){
						EcoreUtil.replace(av, toLogic(EcoreUtil.copy(av.getPrefix().getValues().get(index))));						
					}
				}
			}
		}
	}

}
