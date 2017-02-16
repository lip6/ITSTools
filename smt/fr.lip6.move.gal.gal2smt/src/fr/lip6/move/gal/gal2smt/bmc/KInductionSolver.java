package fr.lip6.move.gal.gal2smt.bmc;

import java.util.Collections;
import java.util.logging.Logger;

import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.ISort.IApplication;
import org.smtlib.IResponse;
import org.smtlib.IExpr.ISymbol;
import org.smtlib.SMT.Configuration;
import org.smtlib.command.C_assert;
import org.smtlib.command.C_define_fun;
import org.smtlib.impl.Script;
import org.smtlib.impl.Sort;

import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.SafetyProp;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.gal2smt.Result;
import fr.lip6.move.gal.gal2smt.Solver;

public class KInductionSolver extends NextBMCSolver {

	public KInductionSolver(Configuration smtConfig, Solver engine, boolean withAllDiff) {
		super(smtConfig, engine, withAllDiff);
		//setShowSatState(true);
	}

	@Override
	public void init(Specification spec) {
		super.init(spec);
		
		int vindex =0;
		for (int val : nb.getInitial()) {
			if (val >= 0) {
				solver.push(1);
				// assert relation from s[0] -> s[1]
				new C_assert(efactory.fcn(efactory.symbol(NEXT),efactory.numeral(0))).execute(solver);
				// assert x >= 0 at step 0
				new C_assert(efactory.fcn(efactory.symbol(">="), 
						efactory.fcn(efactory.symbol("select"),
								// state at step 0
								accessStateAt(0), 
								// at correct var index 
								efactory.numeral(vindex)),
						// greater than 0
						efactory.numeral(0))).execute(solver);
				// assert x < 0 at step 1
				new C_assert(efactory.fcn(efactory.symbol("<"), 
						efactory.fcn(efactory.symbol("select"),
								// state at step 1
								accessStateAt(1), 
								// at correct var index 
								efactory.numeral(vindex)),
						// strictly less 0 = negative
						efactory.numeral(0))).execute(solver);
							
				Result res = checkSat();
				solver.pop(1);
				if (res == Result.UNSAT) {
					//System.out.println("positive var detected");
					// assert x >= 0 at step 0
					C_assert xpos = new C_assert(efactory.fcn(efactory.symbol(">="), 
							efactory.fcn(efactory.symbol("select"),
									// state at step 0
									accessStateAt(0), 
									// at correct var index 
									efactory.numeral(vindex)),
							// greater than 0
							efactory.numeral(0)));
					System.out.println(xpos);
					IResponse err = xpos.execute(solver);
					if (err.isError()) {
						System.err.println("Error adding positive variable constraint "+ err);
					}
				} else {
					System.out.println("could not prove variable is positive");
				}
			}
			vindex++;
		}
		
//		TypeDeclaration td = spec.getMain();
//		if (td instanceof GALTypeDeclaration) {
//			GALTypeDeclaration gal = (GALTypeDeclaration) td;
//			FlowMatrix fm = new FlowMatrix(conf,vh);			
//			boolean isInit = fm.init(gal);
//			
//			if (isInit) {
//				int step = 0;
//				fm.addFlowConstraintsAtStep(step,script,gal);
//
//				// check sat
//				IResponse res = script.execute(solver);
//				if (res.isError()) {
//					throw new RuntimeException("Could not initialize marking equation.");
//				}
//			} else {				
//				// add non negative constraint
//				for (IExpr access : vh.getAllAccess()) {
//					solver.assertExpr(efactory.fcn(efactory.symbol(">="), access, efactory.numeral(0)));
//				}
//			}
//		}
//		// add constraint from S[0] to S[1]

		incrementDepth();
		// NB: hence depth is 1 for 0-inductive problem
		
	}
	
	@Override
	public Result verify(Property prop) {
		
		if (prop.getBody() instanceof SafetyProp) {
			SafetyProp sbody = (SafetyProp) prop.getBody();

			// we want unsat iff. there is no trace leading to a desirable state
			// desirable as in : can be exhibited		
			boolean isNeg = false;
			if (sbody instanceof ReachableProp) {
				isNeg = true;
			} else if (sbody instanceof NeverProp) {
				isNeg = true;
				// NOT : assert ! bprop up to k, and bprop in k+1
			} else {
				// NOP assert bprop up to k, and not bprop in k+1
			}

			QualifiedExpressionTranslator qet = new QualifiedExpressionTranslator(conf);
			qet.setNb(nb);
			
			ISymbol fname = efactory.symbol("pred");
			ISymbol sstep = efactory.symbol("step");			
			IApplication ints = sortfactory.createSortExpression(efactory.symbol("Int"));
			
			// state at step "step"
			IExpr state = accessStateAt(sstep);
			// build property at state
			IExpr eprop = qet.translateBool(sbody.getPredicate(), state);

			
			Script script = new Script();
			C_define_fun deftr = new org.smtlib.command.C_define_fun(
					fname,    // name
					Collections.singletonList(efactory.declaration(sstep, ints)), // param (int step) 
					Sort.Bool(), // return type
					eprop); // actions : assertions over S[step] 
			script.add(deftr);
			
			// and property up to depth (exclusive)
			for (int i=0 ; i <= getDepth(); i++) {
				// build prop at depth
				IExpr pred = efactory.fcn(fname, efactory.numeral(i));
				// assert negation of prop at depth
				if (i==getDepth()) {
					isNeg = ! isNeg;
				}
				// handle negation as necessary
				if (isNeg) {
					script.add(new C_assert( efactory.fcn(efactory.symbol("not"), pred)));
				} else {
					script.add(new C_assert(pred));					
				}
			}

			for (ICommand c : script.commands()) {
				System.out.println(c);
			}
			
			// the actual induction problem
			solver.push(1);
			script.execute(solver);
			Result res = checkSat();
			if (res == Result.SAT) {
				onSat(solver);
			}
			solver.pop(1);
			
			return res;
		} else {
			Logger.getLogger("fr.lip6.move.gal").warning("Only safety properties are handled in SMT solution currently. Cannot handle " + prop.getName());
			return Result.UNKNOWN;
		}
	}
	
}
