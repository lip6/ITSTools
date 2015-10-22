package fr.lip6.move.gal.gal2smt.bmc;

import java.util.ArrayList;
import java.util.List;

import org.smtlib.IExpr;
import org.smtlib.SMT.Configuration;

import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.gal2smt.ExpressionTranslator;
import fr.lip6.move.gal.gal2smt.Result;
import fr.lip6.move.gal.gal2smt.Solver;

public class KInductionSolver extends BMCSolver {

	public KInductionSolver(Configuration smtConfig, Solver engine, boolean withAllDiff) {
		super(smtConfig, engine, withAllDiff);
	}

	@Override
	public void init(Specification spec) {
		super.init(spec);
		// add constraint from S[0] to S[1]
		incrementDepth();
		// NB: hence depth is 1 for 0-inductive problem
	}
	
	@Override
	public Result verifyAtCurrentDepth(Property prop) {
		BooleanExpression prev = prop.getBody().getPredicate();
		// we want unsat iff. there is no trace leading to a desirable state
		// desirable as in : can be exhibited
		BooleanExpression bprop = prop.getBody().getPredicate();
		if (prop.getBody() instanceof ReachableProp) {
			bprop = GF2.not(bprop);
			// NOT : assert ! bprop up to k, and bprop in k+1
		} else if (prop.getBody() instanceof NeverProp) {
			bprop = GF2.not(bprop);
			// NOT : assert ! bprop up to k, and bprop in k+1
		} else {
			// NOP assert bprop up to k, and not bprop in k+1
		}
	

		List<IExpr> asserts = new ArrayList<IExpr>();
		// and property up to depth (exclusive)
		for (int i=0 ; i < getDepth(); i++) {
			asserts.add(ExpressionTranslator.translateBool(bprop, efactory.numeral(i)));
		}
		//negate
		bprop = GF2.not(bprop);
		// assert ! prop at step depth		
		asserts.add(ExpressionTranslator.translateBool(bprop, efactory.numeral(getDepth())));

		solver.push(1);
		
		// the actual induction problem
		IExpr sprop = efactory.fcn(efactory.symbol("and"), asserts);
		solver.assertExpr(sprop);
				
		Result res = checkSat();
		
		// cleanup
		solver.pop(1);
		prop.getBody().setPredicate(prev);
		return res;
	}
	
}
