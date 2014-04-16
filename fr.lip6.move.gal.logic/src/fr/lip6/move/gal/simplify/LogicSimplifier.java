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
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.Not;
import fr.lip6.move.gal.Or;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableRef;
import fr.lip6.move.gal.instantiate.Instantiator;
import fr.lip6.move.gal.instantiate.Simplifier;
import fr.lip6.move.gal.logic.Af;
import fr.lip6.move.gal.logic.Ag;
import fr.lip6.move.gal.logic.BooleanExpression;
import fr.lip6.move.gal.logic.CardMarking;
import fr.lip6.move.gal.logic.ComparisonOperators;
import fr.lip6.move.gal.logic.Ctl;
import fr.lip6.move.gal.logic.CtlProp;
import fr.lip6.move.gal.logic.Deadlock;
import fr.lip6.move.gal.logic.Ef;
import fr.lip6.move.gal.logic.Eg;
import fr.lip6.move.gal.logic.Enabling;
import fr.lip6.move.gal.logic.Equiv;
import fr.lip6.move.gal.logic.Imply;
import fr.lip6.move.gal.logic.IntExpression;
import fr.lip6.move.gal.logic.LogicFactory;
import fr.lip6.move.gal.logic.MarkingRef;
import fr.lip6.move.gal.logic.Properties;
import fr.lip6.move.gal.logic.PropertyDesc;
import fr.lip6.move.gal.logic.ReachProp;
import fr.lip6.move.gal.logic.XOr;

public class LogicSimplifier {

	
	public static void simplify (Properties props) {
		
		for (PropertyDesc prop : props.getProps()) {
			for (EObject o : prop.eContents()) {
				rewrite(o);
			}
		}
		
		rewriteWithInitialState(props);
	}

	private static void rewriteWithInitialState(Properties props) {
		GALTypeDeclaration s = props.getSystem();
		for (PropertyDesc prop : props.getProps()) {
			if (prop.getProp() instanceof ReachProp) {
				ReachProp p = (ReachProp) prop.getProp();
				boolean b = evalInInitialState(s,p.getFormula());
				if (!b && p.getInvariant().equals("I")) {
					java.lang.System.err.println("Invariant property " + prop.getName() + " is trivially false in initial state.");
					// rewrite to Invariant False
					p.setFormula(LogicFactory.eINSTANCE.createFalse());
				} else if (b && p.getInvariant().equals("N")) {
					java.lang.System.err.println("Never property " + prop.getName() + " is trivially true in initial state.");
					// rewrite to Never True.
					p.setFormula(LogicFactory.eINSTANCE.createTrue());
				}
			} else if (prop instanceof CtlProp) {
				BooleanExpression form = prop.getProp().getFormula();
				simplifyCTLInitial(s,form, prop.getName());				
			}
		}
		
	}
	
	

	private static void simplifyCTLInitial(GALTypeDeclaration s, BooleanExpression form, String pname) {
		if (form instanceof Ctl) {
			Ctl ef = (Ctl) form;
			if (isPureBoolean(ef.getForm())) {
				boolean b = evalInInitialState(s,ef.getForm());
				if (b && ef instanceof Ef) {
					java.lang.System.err.println("EF property " + pname + " is trivially true in initial state.");
					EcoreUtil.replace(form, LogicFactory.eINSTANCE.createTrue());
				} else if (!b && ef instanceof Eg) {
					java.lang.System.err.println("EG property " +pname + " is trivially false in initial state.");
					EcoreUtil.replace(form, LogicFactory.eINSTANCE.createFalse());
				} else if (b && ef instanceof Af) {
					java.lang.System.err.println("AF property " +pname + " is trivially true in initial state.");
					EcoreUtil.replace(form, LogicFactory.eINSTANCE.createTrue());
				} else if (!b && ef instanceof Ag) {
					java.lang.System.err.println("AG property " + pname + " is trivially false in initial state.");
					EcoreUtil.replace(form, LogicFactory.eINSTANCE.createFalse());
				}
			}
		} else if (form instanceof fr.lip6.move.gal.logic.And) {
			fr.lip6.move.gal.logic.And and = (fr.lip6.move.gal.logic.And) form;
			simplifyCTLInitial(s, and.getLeft(), pname);
			simplifyCTLInitial(s, and.getRight(), pname);
			EcoreUtil.replace(and, ConstantSimplifier.simplify(and));
		} else if (form instanceof fr.lip6.move.gal.logic.Or) {
			fr.lip6.move.gal.logic.Or and = (fr.lip6.move.gal.logic.Or) form;
			simplifyCTLInitial(s, and.getLeft(), pname);
			simplifyCTLInitial(s, and.getRight(), pname);
			EcoreUtil.replace(and, ConstantSimplifier.simplify(and));
		} else if (form instanceof fr.lip6.move.gal.logic.Not) {
			fr.lip6.move.gal.logic.Not and = (fr.lip6.move.gal.logic.Not) form;
			simplifyCTLInitial(s, and.getValue(), pname);			
			EcoreUtil.replace(and, ConstantSimplifier.simplify(and));
		} else {
			java.lang.System.err.println("Unexpected Ctl formula operator encountered.");
		}
	}


	private static boolean isPureBoolean(BooleanExpression form) {
		for (TreeIterator<EObject> it = form.eAllContents() ; it.hasNext() ; ) {
			EObject obj = it.next();
			if (obj instanceof Ctl) {
				return false;
			}
		}
		return true;
	}

	private static boolean evalInInitialState(GALTypeDeclaration s, BooleanExpression e) {
		if (e instanceof fr.lip6.move.gal.logic.True) {
			return true;
		} else if (e instanceof fr.lip6.move.gal.logic.False) {
			return false;
		} else if (e instanceof fr.lip6.move.gal.logic.And) {
			fr.lip6.move.gal.logic.And and = (fr.lip6.move.gal.logic.And) e;
			return evalInInitialState(s, and.getLeft()) && evalInInitialState(s, and.getRight());
		} else if (e instanceof fr.lip6.move.gal.logic.Or) {
			fr.lip6.move.gal.logic.Or or = (fr.lip6.move.gal.logic.Or) e;
			return evalInInitialState(s, or.getLeft()) || evalInInitialState(s, or.getRight());			
		} else if (e instanceof fr.lip6.move.gal.logic.Not) {
			fr.lip6.move.gal.logic.Not not = (fr.lip6.move.gal.logic.Not) e;
			return evalInInitialState(s, not.getValue());
		} else if (e instanceof fr.lip6.move.gal.logic.Comparison) {
			fr.lip6.move.gal.logic.Comparison cmp = (fr.lip6.move.gal.logic.Comparison) e;
			int l = evalInInitialState(s, cmp.getLeft());
			int r = evalInInitialState(s, cmp.getRight());
			switch (cmp.getOperator()) {
			case EQ: return l==r;
			case NE: return l!=r;
			case GT: return l>r;
			case GE: return l>=r;
			case LT: return l<r;
			case LE: return l<=r;	
			default : 
				java.lang.System.err.println("Unknown operator in comparison !");
				return false;
			}			
		} else if (e instanceof Deadlock) {
			for (Transition t : s.getTransitions()) {
				java.util.List<Transition> inst  = Instantiator.instantiateParameters(t);				
				for (Transition tr : inst) {
					fr.lip6.move.gal.BooleanExpression g = tr.getGuard();
					if ( evalInInitialState(s, toLogic(g))) {
						return false;
					}
				}
			}
			return true;			
		} else {
			java.lang.System.err.println("Unexpected boolean logic operator in evalInInitialState "+e.getClass().getName());
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
			return ((Constant) vr.getReferencedVar().getValue()).getValue();
		} else if (e instanceof fr.lip6.move.gal.logic.ArrayVarAccess) {
			fr.lip6.move.gal.logic.ArrayVarAccess av = (fr.lip6.move.gal.logic.ArrayVarAccess) e;
			int index = ((fr.lip6.move.gal.logic.Constant) av.getIndex()).getValue();
			int val = ((Constant) av.getPrefix().getValues().get(index)).getValue();
			return val;
		} else if (e instanceof fr.lip6.move.gal.logic.BinaryIntExpression) {
			fr.lip6.move.gal.logic.BinaryIntExpression bin = (fr.lip6.move.gal.logic.BinaryIntExpression) e;
			int l = evalInInitialState(s, bin.getLeft());
			int r = evalInInitialState(s, bin.getRight());
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
			return res;			
		}  else {
			java.lang.System.err.println("Unexpected IntExpression in simplify procedure:" + e.getClass().getName());
		}
		
		return 0;
	}
	

	private static void rewrite(EObject obj) {
		for (EObject child : obj.eContents()) {
			rewrite(child);
		}
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
			Not not = GalFactory.eINSTANCE.createNot();
			not.setValue(b);
			Simplifier.simplify(b);
			b= not.getValue();
			
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
					fr.lip6.move.gal.logic.IntExpression suml = createSumOfArray(l);
					fr.lip6.move.gal.logic.IntExpression sumr = createSumOfArray(r);
					cmp.setLeft(suml);
					cmp.setRight(sumr);							
				}

			} else 	if (cmp.getLeft() instanceof MarkingRef && cmp.getRight() instanceof MarkingRef) {
				MarkingRef left =  (MarkingRef) cmp.getLeft();
				MarkingRef right = (MarkingRef) cmp.getRight();
				if (left.getPlace() instanceof Variable && right.getPlace() instanceof Variable) {
					fr.lip6.move.gal.logic.VariableRef vl = LogicFactory.eINSTANCE.createVariableRef();
					vl.setReferencedVar((Variable) left.getPlace());
					cmp.setLeft(vl);
					fr.lip6.move.gal.logic.VariableRef vr = LogicFactory.eINSTANCE.createVariableRef();
					vr.setReferencedVar((Variable) right.getPlace());
					cmp.setRight(vr);
				} else if (left.getPlace() instanceof ArrayPrefix && right.getPlace() instanceof ArrayPrefix
							&& ((ArrayPrefix) left.getPlace()).getSize()== ((ArrayPrefix) right.getPlace()).getSize()) {
					ArrayPrefix l = (ArrayPrefix) left.getPlace();
					ArrayPrefix r = (ArrayPrefix) right.getPlace();
					int size = l.getSize();
					assert size >= 1;
					fr.lip6.move.gal.logic.BooleanExpression combineComp = createArrayComparisons(l,cmp.getOperator(),r);
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
			fr.lip6.move.gal.logic.Not not1 = LogicFactory.eINSTANCE.createNot();
			not1.setValue(EcoreUtil.copy(imp.getLeft()));
			
			fr.lip6.move.gal.logic.Not not2 = LogicFactory.eINSTANCE.createNot();
			not2.setValue(EcoreUtil.copy(imp.getRight()));
			
			fr.lip6.move.gal.logic.And and1 = LogicFactory.eINSTANCE.createAnd();
			and1.setOp("&&");
			and1.setLeft(not1);
			and1.setRight(not2);
			
			fr.lip6.move.gal.logic.And and2 = LogicFactory.eINSTANCE.createAnd();
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
			fr.lip6.move.gal.logic.Not not1 = LogicFactory.eINSTANCE.createNot();
			not1.setValue(EcoreUtil.copy(imp.getLeft()));
			
			fr.lip6.move.gal.logic.Not not2 = LogicFactory.eINSTANCE.createNot();
			not2.setValue(EcoreUtil.copy(imp.getRight()));
			
			fr.lip6.move.gal.logic.And and1 = LogicFactory.eINSTANCE.createAnd();
			and1.setOp("&&");
			and1.setLeft(not1);
			and1.setRight(EcoreUtil.copy(imp.getRight()));
			
			fr.lip6.move.gal.logic.And and2 = LogicFactory.eINSTANCE.createAnd();
			and2.setOp("&&");
			and2.setLeft(EcoreUtil.copy(imp.getLeft()));
			and2.setRight(not2);
			
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
		

	private static BooleanExpression createArrayComparisons(ArrayPrefix l, ComparisonOperators operator, ArrayPrefix r) {
		ComparisonOperators op = operator;
		if (operator == ComparisonOperators.NE) {
			op = ComparisonOperators.EQ;
		}
		int size = l.getSize();
		
		BooleanExpression toret = null;		
		for (int i = 0; i <size ; i++) {
			fr.lip6.move.gal.logic.Comparison cmp = createComparison(l, op, r, i);
			
			if (toret == null) {
				toret = cmp;
			} else {
				fr.lip6.move.gal.logic.And and = LogicFactory.eINSTANCE.createAnd();
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
			and2.setOp("&&");
			and2.setLeft(toLogic(and.getLeft()));
			and2.setRight(toLogic(and.getRight()));
			return and2;
		} else if (b instanceof Or) {
			Or and = (Or) b;
			fr.lip6.move.gal.logic.Or and2 = LogicFactory.eINSTANCE.createOr();
			and2.setOp("||");
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
			and2.setOperator(toLogic(and.getOperator()));
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

	private static ComparisonOperators toLogic(
			fr.lip6.move.gal.ComparisonOperators operator) {
		switch (operator) {
		case EQ: return ComparisonOperators.EQ;
		case NE: return ComparisonOperators.NE;
		case GT: return ComparisonOperators.GT;
		case GE: return ComparisonOperators.GE;
		case LT: return ComparisonOperators.LT;
		case LE: return ComparisonOperators.LE;	
		default : 
			java.lang.System.err.println("Unknown operator in comparison !");
			return ComparisonOperators.EQ;
		}
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
