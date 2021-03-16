package fr.lip6.move.gal.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.logicng.formulas.And;
import org.logicng.formulas.CFalse;
import org.logicng.formulas.CTrue;
import org.logicng.formulas.Formula;
import org.logicng.formulas.FormulaFactory;
import org.logicng.formulas.Literal;
import org.logicng.formulas.NAryOperator;
import org.logicng.formulas.Variable;
import org.logicng.transformations.dnf.DNFFactorization;
import org.logicng.transformations.qmc.QuineMcCluskeyAlgorithm;

import fr.lip6.move.gal.structural.expr.AtomicProp;
import fr.lip6.move.gal.structural.expr.AtomicPropRef;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;

public class ExpressionToLogicNG {
	FormulaFactory f = new FormulaFactory();
	private Map<String,AtomicProp> map = new HashMap<>();
	
	public Expression simplify (Expression be) {
		ExecutorService pool = Executors.newCachedThreadPool();
			
		// System.out.println("Before : "+ SerializationUtil.getText(be, true));
		FutureTask<Expression> task = new FutureTask<>(()-> {
			Formula ff = toFormula(be);
			Formula fs =  QuineMcCluskeyAlgorithm.compute(ff); //new DNFFactorization().apply(ff, false); 
			if (fs.numberOfNodes() >= 4 * ff.numberOfNodes()+1) {
				fs=ff;
			}
			Expression newbe = toExpression(fs);
			return newbe;
		});
		pool.execute(task);
		try {
			return task.get(500, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			task.cancel(true);
			return be;
		}
		//		QuineMcCluskeyAlgorithm.compute(ff);
		// System.out.println("After LogicNG : "+ SerializationUtil.getText(newbe, true));
	}
	
	private Expression toExpression(Formula f) {
		if (f instanceof Variable) {
			Variable v = (Variable) f;
			return Expression.apRef(map.get(v.name()));
		} else if (f instanceof NAryOperator) {
			NAryOperator or = (NAryOperator) f;
			List<Expression> children = new ArrayList<> (or.numberOfOperands());
			for (Formula elt : or) {
				children.add(toExpression(elt));
			}
			if (f instanceof And) {
				return Expression.nop(Op.AND,children);
			} else {
				return Expression.nop(Op.OR,children);				
			}
		} else if (f instanceof Literal) {
			Literal l = (Literal) f;
			return Expression.not(toExpression(l.variable()));
		} else if (f instanceof org.logicng.formulas.Not) {
			org.logicng.formulas.Not l = (org.logicng.formulas.Not) f;
			return Expression.not(toExpression(l.operand()));
		} else if (f instanceof CFalse) {
			return  Expression.constant(false);
		} else if (f instanceof CTrue) {
			return  Expression.constant(true);
		} else {
			System.out.println("error");
		}
		return null;
	}

	private Formula toFormula (Expression be) {
		if (be.getOp() == Op.AND || be.getOp() == Op.OR) {
			List<Formula> children = new ArrayList<> (be.nbChildren());
			for (int cid=0,cide=be.nbChildren() ; cid < cide ; cid++) {
				children.add(toFormula(be.childAt(cid)));
			}
			if (be.getOp() == Op.AND) {
				return f.and(children);
			} else {
				// or
				return f.or(children);
			}			
		} else if (be.getOp() == Op.NOT) {
			return f.not(toFormula(be.childAt(0)));
		} else if (be.getOp() == Op.BOOLCONST) {
			if (be.getValue() ==0) {
				return f.constant(false);
			} else {
				return f.constant(true);
			}
		} else if (be.getOp() == Op.APREF){
			AtomicPropRef apr = (AtomicPropRef) be;
			String srep = apr.getAp().getName();
			map.putIfAbsent(srep, apr.getAp());
			return f.variable(srep);
		} else {
			throw new IllegalArgumentException("Expression should use only AtomicProp references " + be );
		}
	}

}
