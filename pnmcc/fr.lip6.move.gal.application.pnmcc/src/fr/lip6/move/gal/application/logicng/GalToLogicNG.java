package fr.lip6.move.gal.application.logicng;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.logicng.formulas.CFalse;
import org.logicng.formulas.CTrue;
import org.logicng.formulas.Formula;
import org.logicng.formulas.FormulaFactory;
import org.logicng.formulas.Literal;
import org.logicng.formulas.Variable;
import org.logicng.transformations.dnf.DNFFactorization;
import org.logicng.transformations.qmc.QuineMcCluskeyAlgorithm;

import fr.lip6.move.gal.And;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.Not;
import fr.lip6.move.gal.Or;
import fr.lip6.move.gal.True;
import fr.lip6.move.serialization.SerializationUtil;
import fr.lip6.move.gal.False;

public class GalToLogicNG {
	FormulaFactory f = new FormulaFactory();
	private Map<String,BooleanExpression> map = new HashMap<>();
	
	public void simplify (List<BooleanExpression> props) {
		ExecutorService pool = Executors.newCachedThreadPool();
		for (BooleanExpression be : props) {
			
			// System.out.println("Before : "+ SerializationUtil.getText(be, true));
			FutureTask<BooleanExpression> task = new FutureTask<>(()-> {
				Formula ff = toFormula(be);
				Formula fs =  new DNFFactorization().apply(ff, false); 
				if (fs.numberOfNodes() >= 4 * ff.numberOfNodes()+1) {
					fs=ff;
				}
				BooleanExpression newbe = toGal(fs);
				return newbe;
			} );
			pool.execute(task);
			try {
				EcoreUtil.replace(be, task.get(500, TimeUnit.MILLISECONDS));
			} catch (Exception e) {
				task.cancel(true);
			}
			//		QuineMcCluskeyAlgorithm.compute(ff);
			// System.out.println("After LogicNG : "+ SerializationUtil.getText(newbe, true));
		}				
	}
	
	private BooleanExpression toGal(Formula f) {
		if (f instanceof Variable) {
			Variable v = (Variable) f;
			return EcoreUtil.copy(map.get(v.name()));
		} else if (f instanceof org.logicng.formulas.Or) {
			org.logicng.formulas.Or or = (org.logicng.formulas.Or) f;
			BooleanExpression res = GalFactory.eINSTANCE.createFalse();
			for (Formula elt : or) {
				res = GF2.or(res, toGal(elt));				
			}
			return res;
		} else if (f instanceof org.logicng.formulas.And) {
			org.logicng.formulas.And and = (org.logicng.formulas.And) f;
			BooleanExpression res = GalFactory.eINSTANCE.createTrue();
			for (Formula elt : and) {
				res = GF2.and(res, toGal(elt));				
			}
			return res;			
		} else if (f instanceof Literal) {
			Literal l = (Literal) f;
			return GF2.not(toGal(l.variable()));
		} else if (f instanceof org.logicng.formulas.Not) {
			org.logicng.formulas.Not l = (org.logicng.formulas.Not) f;
			return GF2.not(toGal(l.operand()));
		} else if (f instanceof CFalse) {
			return  GalFactory.eINSTANCE.createFalse();
		} else if (f instanceof CTrue) {
			return  GalFactory.eINSTANCE.createTrue();			
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
			String srep = SerializationUtil.getText(obj,true);
			map.putIfAbsent(srep, obj);
			return f.variable(srep);
		}		
	}

}
