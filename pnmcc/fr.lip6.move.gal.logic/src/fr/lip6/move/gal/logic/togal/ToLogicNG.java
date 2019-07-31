package fr.lip6.move.gal.logic.togal;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.logicng.formulas.CFalse;
import org.logicng.formulas.CTrue;
import org.logicng.formulas.Formula;
import org.logicng.formulas.FormulaFactory;
import org.logicng.formulas.Literal;
import org.logicng.formulas.Variable;

import fr.lip6.move.gal.logic.Ag;
import fr.lip6.move.gal.logic.And;
import fr.lip6.move.gal.logic.BooleanExpression;
import fr.lip6.move.gal.logic.CardMarking;
import fr.lip6.move.gal.logic.Comparison;
import fr.lip6.move.gal.logic.Constant;
import fr.lip6.move.gal.logic.Ef;
import fr.lip6.move.gal.logic.Enabling;
import fr.lip6.move.gal.logic.False;
import fr.lip6.move.gal.logic.IntExpression;
import fr.lip6.move.gal.logic.LogicFactory;
import fr.lip6.move.gal.logic.LogicProp;
import fr.lip6.move.gal.logic.Not;
import fr.lip6.move.gal.logic.Or;
import fr.lip6.move.gal.logic.Properties;
import fr.lip6.move.gal.logic.Property;
import fr.lip6.move.gal.logic.PropertyDesc;
import fr.lip6.move.gal.logic.True;

public class ToLogicNG {
	FormulaFactory f = new FormulaFactory();
	private Map<String,BooleanExpression> map = new HashMap<>();
	public void simplify (Properties props) {		
		
		for (PropertyDesc p : props.getProps()) {
			Property prop = p.getProp();
			if (prop instanceof LogicProp) {
				LogicProp rp = (LogicProp) prop;
				BooleanExpression be = rp.getFormula();
				
				if (be instanceof Ag) {
					Ag ag = (Ag) be;
					Formula ff = toFormula(ag.getForm());					
					ag.setForm(toLogic(ff));
				} else if (be instanceof Ef) {
					Ef ag = (Ef) be;
					Formula ff = toFormula(ag.getForm());
					ag.setForm(toLogic(ff));
				}					
			}			
		}		
	}
	
	private BooleanExpression toLogic(Formula f) {
		if (f instanceof Variable) {
			Variable v = (Variable) f;
			return EcoreUtil.copy(map.get(v.name()));
		} else if (f instanceof org.logicng.formulas.Or) {
			org.logicng.formulas.Or or = (org.logicng.formulas.Or) f;
			Or oc = LogicFactory.eINSTANCE.createOr();
			oc.setLeft(LogicFactory.eINSTANCE.createFalse());
			for (Formula elt : or) {
				oc.setRight(toLogic(elt));
				Or oc2 = LogicFactory.eINSTANCE.createOr();
				oc2.setLeft(oc);
				oc = oc2;
			}
			return oc.getLeft();
		} else if (f instanceof org.logicng.formulas.And) {
			org.logicng.formulas.And or = (org.logicng.formulas.And) f;
			And oc = LogicFactory.eINSTANCE.createAnd();
			oc.setLeft(LogicFactory.eINSTANCE.createTrue());
			for (Formula elt : or) {
				oc.setRight(toLogic(elt));
				And oc2 = LogicFactory.eINSTANCE.createAnd();
				oc2.setLeft(oc);
				oc = oc2;
			}
			return oc.getLeft();
		} else if (f instanceof Literal) {
			Literal l = (Literal) f;
			Not n = LogicFactory.eINSTANCE.createNot();
			n.setValue(toLogic(l.variable()));
			return n;
		} else if (f instanceof CFalse) {
			return  LogicFactory.eINSTANCE.createFalse();
		} else if (f instanceof CTrue) {
			return  LogicFactory.eINSTANCE.createTrue();			
		} else {
			System.out.println("error");
		}
		return null;
	}

	private Formula toFormula (BooleanExpression obj) {
		if (obj instanceof And) {
			And and = (And) obj;
			return f.and( toFormula(and.getLeft()), toFormula(and.getRight()));
		} else if (obj instanceof Or) {
			Or and = (Or) obj;
			return f.or( toFormula(and.getLeft()), toFormula(and.getRight()));
		} else if (obj instanceof Not) {
			Not not = (Not) obj;
			return f.not(toFormula(not.getValue()));
		} else if (obj instanceof True) {
			return f.constant(true);
		} else if (obj instanceof False) {
			return f.constant(false);
		} else {
			return f.variable(toString(obj));
		}		
	}

	private String toString(BooleanExpression obj) {
		String k = null;
		if (obj instanceof Enabling) {
			Enabling en = (Enabling) obj;
			k =  en.getTrans().stream().map(t -> t.getName()).collect(Collectors.toList()).toString();			
		} else if (obj instanceof Comparison) {
			Comparison cmp = (Comparison) obj;
			k = toString(cmp.getLeft()) + cmp.getOperator().toString() + toString(cmp.getRight());
		}
		map.putIfAbsent(k, obj);		
		return k;
	}

	private String toString(IntExpression e) {
		if (e instanceof CardMarking) {
			CardMarking cm = (CardMarking) e;
			return cm.getPlaces().stream().map(t -> t.getName()).collect(Collectors.toList()).toString();
		} else if (e instanceof Constant) {
			return Integer.toString(((Constant) e).getValue()) ;
		}
		return "";
	}
}
