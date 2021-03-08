package fr.lip6.move.gal.pn2pins;

import java.util.logging.Logger;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.util.IntMatrixCol;

public class NecessaryEnablingsolver {

	private int sat = 0;
	private int unsat = 0;
	private long timestamp = 0;

	public NecessaryEnablingsolver(boolean isSafe) {
		// super(smtConfig, engine, false, isSafe);
	}

	private void clearStats() {
		timestamp = System.currentTimeMillis();
		sat = 0;
		unsat = 0;
	}

	private long lastPrint = 0;
	private SparsePetriNet net;
	private IntMatrixCol combFlow;

	private void printStats(boolean force, String message) {
		// unless force will only report every 3000 ms
		long time = System.currentTimeMillis();
		long duration = time - timestamp;
		if (!force) {
			if (time - lastPrint < 3000) {
				return;
			}
		}
		Logger.getLogger("fr.lip6.move.gal").info("Computation of " + message + " took " + duration
				+ " ms. Total solver calls (SAT/UNSAT): " + (sat + unsat) + "(" + sat + "/" + unsat + ")");
		lastPrint = time;
	}

	public void init(SparsePetriNet net) {
		this.net = net;
//		addKnownInvariants(0);
		combFlow = new IntMatrixCol(net.getPnames().size(), 0);
		for (int i = 0; i < net.getFlowPT().getColumnCount(); i++) {
			SparseIntArray col = SparseIntArray.sumProd(-1, net.getFlowPT().getColumn(i), 1,
					net.getFlowTP().getColumn(i));
			combFlow.appendColumn(col);
		}
	}

	public IntMatrixCol computeAblingMatrix(boolean isEnabler) {
		clearStats();
		int nbTransition = net.getTransitionCount();
		IntMatrixCol matrix = new IntMatrixCol(nbTransition, 0);
		Logger.getLogger("fr.lip6.move.gal").info("Computing symmetric may " + (isEnabler ? "enable" : "disable")
				+ " matrix : " + nbTransition + " transitions.");

		// *true* feeders/consumers for each place
		IntMatrixCol tflowTP = combFlow.transpose();
		// for each transition t
		for (int tindex = 0; tindex < nbTransition; tindex++) {
			SparseIntArray col = new SparseIntArray();
			// for each pre place of t
			SparseIntArray pre = net.getFlowPT().getColumn(tindex);
			for (int i = 0, ie = pre.size(); i < ie; i++) {
				int pid = pre.keyAt(i);
				SparseIntArray feeders = tflowTP.getColumn(pid);
				for (int j = 0, je = feeders.size(); j < je; j++) {
					int fid = feeders.keyAt(j);
					int val = feeders.valueAt(j);
					if (isEnabler && val > 0) {
						col.put(fid, 1);
					} else if (!isEnabler && val < 0) {
						col.put(fid, 1);
					}
				}
			}
			matrix.appendColumn(col);
		}
		printStats(true, "Complete " + (isEnabler ? "enable" : "disable") + " matrix.");

		return matrix;
	}

//	private ISolver buildSolver() {
//		ISolver solver = engine.getSolver(conf);
//		// start the solver
//		IResponse err = solver.start();
//		if (err.isError()) {
//			throw new RuntimeException("Could not start solver "+ engine+" from path "+ conf.executable + " raised error :"+err);
//		}
//		err = solver.set_logic("QF_AUFLIRA", null);
//		if (err.isError()) {
//			throw new RuntimeException("Could not set logic :"+err);
//		}
//		return solver;
//	}

//	/**
//	 * Answers true if there are potential states that allow both t1 and t2 to fire.
//	 * If (t1==t2), this is changed to "are there states where the sequence t1.t1 is possible ?"
//	 * @param t1 an index for t1
//	 * @param t2 an index for t2
//	 * @return
//	 */
//	public boolean canBeCoenabled (int t1,int t2) {
//		// push a context
//		solver.push(1);
//
//		if (t1 != t2) {
//			// assert t1 enabled in initial
//			solver.assertExpr(
//					efactory.fcn(efactory.symbol(ENABLED+t1), efactory.numeral(0)));
//
//			// assert t2 enabled in initial
//			solver.assertExpr(
//					efactory.fcn(efactory.symbol(ENABLED+t2), efactory.numeral(0)));
//		} else {
//			// assert t1 fired from initial state[0]
//			solver.assertExpr(
//					efactory.fcn(efactory.symbol(TRANSNAME+t1), efactory.numeral(0)));
//
//			// assert t1 enabled in s[1]
//			solver.assertExpr(
//					efactory.fcn(efactory.symbol(ENABLED+t1), efactory.numeral(1)));
//		}
//
//		Result res = checkSat();
//		//Logger.getLogger("fr.lip6.move.gal").info("Checking co enabling of "+t1 + " and " + t2 + " : " + res);
//		solver.pop(1);
//		if (res == Result.SAT) {
//			sat++;
//			return true;
//		} else if (res == Result.UNSAT){
//			unsat++;
//			return false;
//		} else {
//			throw new RuntimeException("SMT solver raised an error in enabler solving :"+res);
//		}
//	}

//	public MatrixCol computeCoEnablingMatrix(DependencyMatrix dm) {
//		MatrixCol coEnabled = new MatrixCol(nbTransition,nbTransition);
//		Logger.getLogger("fr.lip6.move.gal").info("Computing symmetric co enabling matrix : " + nbTransition + " transitions.");
//		clearStats();
//
//		// Build one Solver per line		
//		for (int t1 = 0 ; t1 < nbTransition ; t1++) {
//
//			if (isSolverTired ) {
//				for (int t2 = t1 ; t2 < nbTransition ; t2++) {
//					coEnabled.getColumn(t1).put(t2,1);
//					coEnabled.getColumn(t2).put(t1, 1);
//				}
//				continue;
//			}
//			Script scriptInit = new Script();
//
//			ISolver solver = buildSolver();
//
//			ISymbol s0 = efactory.symbol("s0");
//
//			Script invarScript = new Script();
//			// total support
//			BitSet total = (BitSet) dm.getControl(t1).clone();
//			// add relevant invariants
//			int maxCoeff = 0;
//			for (int inv= 0 ; inv < getInvariants().size() ; inv++) {
//				if (dm.getControl(t1).intersects(getInvariantSupport().get(inv))) {
//					IExpr exprInv = convertInvariantToSMT(getInvariants().get(inv), s0);
//					SparseIntArray col = getInvariants().get(inv);
//					for (int i = 0 ; i < col.size() ; i++) {
//						int coef = col.valueAt(i);
//						maxCoeff = Math.max(maxCoeff, Math.abs(coef));
//					}
//					invarScript.add(new C_assert(exprInv));
//					total.or(getInvariantSupport().get(inv));
//				}			
//			}
//
//
//			IApplication ints = sortfactory.createSortExpression(efactory.symbol("Int"));
//			IApplication reals = sortfactory.createSortExpression(efactory.symbol("Real"));
//			if (maxCoeff < 1000) {
//				reals = ints;
//			}
//			// an array, indexed by integers, containing integers : (Array Int Int) 
//			// ACTUALLY : solve with Reals to avoid "unknown" diagnosis, and runs much much faster.
//			// being an overapproxiamtion should be ok for this matrix + real solutions are usually pretty close to integer ones.
//			IApplication arraySort = sortfactory.createSortExpression(efactory.symbol("Array"), ints, reals);
//
//
//
//
//			// declare one states : an array of Int
//			scriptInit.add(
//					new org.smtlib.command.C_declare_fun(
//							s0,
//							Collections.emptyList(),
//							arraySort								
//							)
//					);
//
//			scriptInit.commands().addAll(invarScript.commands());
//
//			// a translator to map them to SMT syntax
//			final GalExpressionTranslator et = new GalExpressionTranslator(conf);
//
//			enabledInState(t1, s0, scriptInit, et);
//
//
//			for (int i = total.nextSetBit(0); i >= 0; i = total.nextSetBit(i+1)) {				
//				IExpr isPositive = efactory.fcn(efactory.symbol(">="), 
//						efactory.fcn(efactory.symbol("select"),
//								// state
//								s0, 
//								// at correct var index 
//								efactory.numeral(i)),
//						// greater than 0
//						efactory.numeral(0));
//
//				scriptInit.add(new C_assert(isPositive));
//			}
//
//			IResponse res = scriptInit.execute(solver);
//			if (res.isError()) {
//				System.err.println(scriptInit.commands());
//				throw new RuntimeException("SMT solver raised an exception or timeout when executing script :\n"+scriptInit);
//			}
//
//			for (int t2 = t1 ; t2 < nbTransition ; t2++) {
//				if (t1 == t2) {
//					coEnabled.getColumn(t1).put(t2, 1);
//				} else {
//					if (! total.intersects(dm.getControl(t2)) || isSolverTired) {
//						coEnabled.getColumn(t1).put(t2, 1);
//						coEnabled.getColumn(t2).put(t1, 1);
//						continue;
//					}
//
//
//					res = solver.push(1);
//					if (res.isError()) {
//						throw new RuntimeException("SMT solver raised an exception on push().");
//					}
//					Script s = new Script();
//					enabledInState(t2, s0, s, et);
//
//					//					System.err.println(scriptInit.commands());
//					//					System.err.println(s.commands());
//
//					res = s.execute(solver);
//					if (res.isError()) {
//						System.err.println(s.commands());
//						throw new RuntimeException("SMT solver raised an exception.");
//					}
//					res = solver.check_sat();
//
//					if (res.isError()) {
//						throw new RuntimeException("SMT solver raised an exception or timeout.");
//					}
//
//					IPrinter printer = conf.defaultPrinter;
//					String textReply = printer.toString(res);
//					if ("unknown".equals(textReply)) {
//						// sometimes we can just try again...
//						System.err.println("SMT solver raised 'unknown', retrying with same input.");
//						res = solver.check_sat();
//						if (res.isError()) {
//							throw new RuntimeException("SMT solver raised an error :" + textReply);	
//						} else {
//							textReply = printer.toString(res);
//							if ("unknown".equals(textReply)) {
//								System.err.println("SMT solver raised 'unknown' twice, overapproximating result to 1.");
//								isSolverTired = true;
//								textReply = "sat";
//							}
//						}
//					}
//
//					if ("sat".equals(textReply)) {
//						coEnabled.getColumn(t1).put(t2, 1);
//						coEnabled.getColumn(t2).put(t1, 1);			
//						sat++;
//					} else if ("unsat".equals(textReply)) {
//						// let it stay 0
////						coEnabled.get(t1)[t2]=0;
////						coEnabled.get(t2)[t1]=0;						
//						unsat++;
//					} else {
//						System.err.println(scriptInit.commands());
//						System.err.println(s.commands());												
//						throw new RuntimeException("SMT solver raised an error :" + textReply);						
//					}
//
//					if (solver.pop(1).isError()) {
//						throw new RuntimeException("SMT solver raised an exception or timeout.");
//					}
//				}
//			}
//
//			solver.exit();
//			printStats(false,"co-enabling matrix(" + t1 + "/" + nbTransition +")");
//		}
//		printStats(true,"Finished co-enabling matrix.");
//		return coEnabled;
//	}
//
//	private void enabledInState(int target, ISymbol state, Script script, final GalExpressionTranslator et) {
//		// the relevant transitions in manipulable form
//		List<INext> ttarget = nb.getDeterministicNext().get(target);
//		// do the translation as a Visit of INext : building assertions over SRC
//		NextTranslator translator = new NextTranslator(state, et);
//
//		// To hold all constraints corresponding to this transition
//		List<IExpr> conds = new ArrayList<IExpr>();			
//
//		for (INext statement : ttarget) {
//			IExpr cond = statement.accept(translator);
//			// the visit returns a new condition (Predicate case) or updates its state to reflect assignment
//			if (cond != null)
//				conds.add(cond);
//		}
//
//		// build up the full boolean function for the transition
//		IExpr bodyExpr = efactory.fcn(efactory.symbol("and"), conds);
//		if (conds.size() == 1) {
//			bodyExpr = conds.get(0);
//		} else if (conds.isEmpty()) {
//			bodyExpr = efactory.symbol("true");
//		}
//
//		script.add(new C_assert(bodyExpr));
//	}

	public IntMatrixCol computeDoNotAccord (IntMatrixCol mayEnable) {
		int nbTransition = net.getTransitionCount();
		IntMatrixCol dnaMatrix = new IntMatrixCol(nbTransition,nbTransition);
		Logger.getLogger("fr.lip6.move.gal").info("Computing Do-Not-Accords matrix : " + nbTransition + " transitions.");
		clearStats();
		for (int t1 = 0 ; t1 < nbTransition ; t1++) {
			for (int t2 = t1+1 ; t2 < nbTransition ; t2++) {
				if (mayEnable.getColumn(t1).get(t2)==1 || mayEnable.getColumn(t2).get(t1)==1) {
					// mutual disabling criterion not met
					// t1 and t2 do not accord
					dnaMatrix.getColumn(t1).put(t2, 1);
					dnaMatrix.getColumn(t2).put(t1, 1);
					continue;
				}
				// otherwise : we are fine, Petri net transitions commute naturally.
			}
		}
		
		printStats(true,"Completed DNA matrix.");
		return dnaMatrix;
	}

	boolean[] computeLoHiEdges(Expression expr, SparseIntArray state) {
		boolean[] ret = new boolean[2];
		switch (expr.getOp()) {
		case AND:
		case OR: {
			for (int i = 0, ie = expr.nbChildren(); i < ie; i++) {
				boolean[] cur = computeLoHiEdges(expr.childAt(i), state);
				ret[0] |= cur[0];
				ret[1] |= cur[1];
			}
			break;
		}
		case NOT: {
			boolean[] cur = computeLoHiEdges(expr.childAt(0), state);
			ret[0] = cur[1];
			ret[1] = cur[0];
			break;
		}
		case GT:
		case GEQ:
		case EQ:
		case NEQ:
		case LEQ:
		case LT: {
			SparseIntArray empty = new SparseIntArray();
			int kleft = expr.childAt(0).eval(empty);
			int kright = expr.childAt(1).eval(empty);
			int ldelta = expr.childAt(0).eval(state) - kleft;
			int rdelta = expr.childAt(1).eval(state) - kright;

			if (ldelta == rdelta) {
				ret[0] = false;
				ret[1] = false;
			} else if (expr.getOp() == Op.EQ || expr.getOp() == Op.NEQ) {
				ret[0] = true;
				ret[1] = true;
			} else if (ldelta < rdelta && (expr.getOp() == Op.LT || expr.getOp() == Op.LEQ)
					|| ldelta > rdelta && (expr.getOp() == Op.GT || expr.getOp() == Op.GEQ)) {
				ret[0] = false;
				ret[1] = true;
			} else {
				ret[0] = true;
				ret[1] = false;
			}
			break;
		}

		}
		return ret;
	}

	public SparseIntArray[] computeAblingsForPredicate(Expression be) {
		SparseIntArray[] toret = new SparseIntArray[2];
		toret[0] = new SparseIntArray();
		toret[1] = new SparseIntArray();

		// for each transition t
		for (int tindex = 0; tindex < net.getTransitionCount(); tindex++) {
			SparseIntArray effect = combFlow.getColumn(tindex);
			boolean[] lohi = computeLoHiEdges(be, effect);
			if (lohi[0])
				toret[0].append(tindex, 1);
			if (lohi[1])
				toret[1].append(tindex, 1);
		}
		// printStats(true, "Complete enable+disable matrix for expression.");
		return toret;
	}

	public IntMatrixCol computeCoEnablingMatrix() {
		// placeholder for now
		IntMatrixCol toret = new IntMatrixCol(net.getTransitionCount(), net.getTransitionCount());
		for (int i = 0; i < net.getTransitionCount(); i++) {
			for (int j = 0; j < net.getTransitionCount(); j++) {
				toret.getColumn(i).append(j, 1);
			}
		}
		return toret;
	}

}
