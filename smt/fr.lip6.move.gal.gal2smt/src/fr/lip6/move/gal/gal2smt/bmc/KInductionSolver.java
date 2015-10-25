package fr.lip6.move.gal.gal2smt.bmc;

import java.util.ArrayList;
import java.util.List;

import org.smtlib.IExpr;
import org.smtlib.IResponse;
import org.smtlib.SMT.Configuration;
import org.smtlib.impl.Script;

import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.gal2smt.Result;
import fr.lip6.move.gal.gal2smt.Solver;
import fr.lip6.move.gal.gal2smt.cover.FlowMatrix;

public class KInductionSolver extends BMCSolver {

	public KInductionSolver(Configuration smtConfig, Solver engine, boolean withAllDiff) {
		super(smtConfig, engine, withAllDiff);
		//setShowSatState(true);
	}

	@Override
	public void init(Specification spec) {
		super.init(spec);
		Script script = new Script();
		TypeDeclaration td = spec.getMain();
		if (td instanceof GALTypeDeclaration) {
			GALTypeDeclaration gal = (GALTypeDeclaration) td;
			FlowMatrix fm = new FlowMatrix(conf,vh);			
			boolean isInit = fm.init(gal);
			
			if (isInit) {
				int step = 0;
				fm.addFlowConstraintsAtStep(step,script,gal);

				// check sat
				IResponse res = script.execute(solver);
				if (res.isError()) {
					throw new RuntimeException("Could not initialize marking equation.");
				}
			} else {				
				// add non negative constraint
				for (IExpr access : vh.getAllAccess()) {
					solver.assertExpr(efactory.fcn(efactory.symbol(">="), access, efactory.numeral(0)));
				}
			}
		}
		// add constraint from S[0] to S[1]
		incrementDepth();
		// NB: hence depth is 1 for 0-inductive problem
		
	}
	
	@Override
	public Result verify(Property prop) {
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
			asserts.add(et.translateBool(bprop, efactory.numeral(i)));
		}
		//negate
		bprop = GF2.not(bprop);
		// assert ! prop at step depth		
		asserts.add(et.translateBool(bprop, efactory.numeral(getDepth())));
		// the actual induction problem
		IExpr sprop = efactory.fcn(efactory.symbol("and"), asserts);

		Result res = verifyAssertion(sprop);
		// cleanup
		prop.getBody().setPredicate(prev);
		return res;
	}
	
}
