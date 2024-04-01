package fr.lip6.move.gal.structural.smt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.IExpr.IDeclaration;
import org.smtlib.IExpr.IFactory;
import org.smtlib.IExpr.IFcnExpr;
import org.smtlib.IExpr.ISymbol;
import org.smtlib.IResponse;
import org.smtlib.ISolver;
import org.smtlib.ISort;
import org.smtlib.SMT;
import org.smtlib.SMT.Configuration;
import org.smtlib.command.C_assert;
import org.smtlib.command.C_check_sat;
import org.smtlib.command.C_declare_fun;
import org.smtlib.command.C_declare_sort;
import org.smtlib.command.C_define_fun;
import org.smtlib.command.C_minmax;
import org.smtlib.ext.C_get_model;
import org.smtlib.impl.Script;
import org.smtlib.sexpr.ISexpr;
import org.smtlib.sexpr.ISexpr.ISeq;

import android.util.SparseIntArray;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.InvariantCalculator;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.expr.AtomicProp;
import fr.lip6.move.gal.structural.expr.AtomicPropRef;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.expr.VarRef;
import fr.lip6.move.gal.util.IntMatrixCol;

import static fr.lip6.move.gal.structural.smt.SMTUtils.* ;

public class DeadlockTester {

	static final int DEBUG = 0;	
	/**
	 * Unsat answer means no deadlocks, SAT means nothing, as we are working with an overapprox.
	 * @param sr
	 * @return
	 */
	public static SparseIntArray testDeadlocksWithSMT(StructuralReduction sr, List<Integer> representative, SparseIntArray por) {
		
		IntMatrixCol sumMatrix = InvariantCalculator.computeReducedFlow(sr, representative);

		Set<SparseIntArray> invar = InvariantCalculator.computePInvariants(sumMatrix);		
		//InvariantCalculator.printInvariant(invar, sr.getPnames(), sr.getMarks());
		Set<SparseIntArray> invarT = null; //computeTinvariants(sr, sumMatrix, tnames);
		
		try {
			boolean solveWithReals = true;
			SparseIntArray parikh = new SparseIntArray();
			String reply = areDeadlocksPossible(sr, sumMatrix, invar, invarT, solveWithReals, parikh, por , representative );
			if ("real".equals(reply)) {
				reply = areDeadlocksPossible(sr, sumMatrix, invar, invarT, false, parikh, por , representative);
			}

			if (! "unsat".equals(reply)) {
				return parikh;
			} else {
				return null;
			}
		} catch (RuntimeException re) {
			Logger.getLogger("fr.lip6.move.gal").warning("SMT solver failed with error :" + re + " while checking Deadlocks.");
			return new SparseIntArray();
		}
	}
	

	/**
	 * Test which polarity of the atoms are possible *in a deadlock* state.
	 * @param sr the net
	 * @param atoms the APs to investigate
	 * @param isSafe 
	 * @return two booleans per AP : can it be true in a deadlock ? can it be false in a deadlock ? In doubt we set to true.
	 */
	public static boolean[] testAPInDeadlocksWithSMT(ISparsePetriNet sr, List<AtomicProp> atoms) {
		List<Integer> tnames = new ArrayList<>();
		List<Integer> representative = new ArrayList<>();

		boolean [] results = new boolean[2*atoms.size()];
		Arrays.fill(results, true);
		
		// using reals currently
		boolean solveWithReals = false;
		org.smtlib.SMT smt = new SMT();

		try {
		
		ISolver solver = initSolver(smt, solveWithReals,4000,6000);
		{
			// STEP 1 : declare variables
			Script varScript = declareVariables(sr.getPnames().size(), "s", sr.isSafe(), smt,solveWithReals);
			execAndCheckResult(varScript, solver);			
		}

		{
			// STEP 2 : declare and assert we are dead
			Script dead = assertNetIsDead(sr);
			execAndCheckResult(dead, solver);
		}
		
		String rsat = checkSat(solver);
		if (! "sat".equals(checkSat(solver))) {
			// we cannot die ! there can be only one ?
			// this is abnormal, we should only query this function if we know there are deadlocks.
			System.out.println("Could not satisfy deadlock states : solver returned "+rsat);
			return results;
		}
		
		for (int i=0,ie=2*atoms.size() ; i < ie ; i++) {
			if (results[i]) {
				Expression tocheck;
				// ap not yet disproved
				if (i % 2 == 0) {
					// positive atom
					tocheck = atoms.get(i/2).getExpression();
				} else {
					// negative atom
					tocheck = Expression.not(atoms.get(i/2).getExpression());
				}
				IExpr smtexpr = tocheck.accept(new ExprTranslator());
				Script property = new Script();
				property.add(new C_assert(smtexpr));
				
				IResponse res = solver.push(1);
				if (res.isError()) {
					break;
				}
				
				execAndCheckResult(property, solver);
				
				String issat = checkSat(solver);
				if ("unsat".equals(issat)) {
					results[i] = false;
				} else if ("unknown".equals(issat)) {
					// by default, assume it is possible.
					results[i] = true;
					System.out.println("Solver returned unknown.");
				}
				
				res = solver.pop(1);
				if (res.isError()) {
					break;
				}
			}
		}
		
		// Do we want to refine the test more ?
		// add invariants, traps, state equation...
		// IntMatrixCol sumMatrix = computeReducedFlow(sr, tnames, representative);		
		// Set<SparseIntArray> invar = InvariantCalculator.computePInvariants(sumMatrix, sr.getPnames());		
		// String textReply = assertInvariants(invar, sr, solver, smt,false, solveWithReals);

		
		} catch (RuntimeException re) {
			Logger.getLogger("fr.lip6.move.gal").warning("SMT solver failed with error :" + re + " while checking Deadlocks.");
		}
		return results;
	}

	
	public static SparseIntArray findPositiveTsemiflow(IntMatrixCol sumMatrix, int positivePlace) {
		org.smtlib.SMT smt = new SMT();
		
		ISolver solver = initSolver(smt, false, 4000, 6000);
		
		Script script;
		// t must be positive, it's a T semi flow
		script = declareVariables(sumMatrix.getColumnCount(), "t", false, true, smt, false);
		execAndCheckResult(script, solver);
//		// p must be positive, it's a positive solution
//		script = declareVariables(sumMatrix.getRowCount(), "s", false, false, smt, false);
//		execAndCheckResult(script, solver);
		
		script = new Script();
		IFactory ef = smt.smtConfig.exprFactory;
		
		// At least one strictly positive selected transition
		List<IExpr> onePos = new ArrayList<>();
		for (int i=0; i < sumMatrix.getColumnCount() ; i++) {
			onePos.add(ef.symbol("t"+i));
		}
		org.smtlib.ISort.IApplication ints2 = smt.smtConfig.sortFactory.createSortExpression(ef.symbol("Int"));
		ISymbol si = ef.symbol("Total");
		script.add(new org.smtlib.command.C_define_fun(
					si,
					Collections.emptyList(),
					ints2,
					ef.fcn(ef.symbol("+"), onePos)
					));
		
		script.add(new C_assert(ef.fcn(ef.symbol(">="), ef.symbol("Total"),ef.numeral(1))));
		

		// assert >= 0 effects of transitions on each place
		// we work with one constraint for each place => use transposed
		IntMatrixCol mat = sumMatrix.transpose();
		
		SparseIntArray total = new SparseIntArray();
		for (int varindex = 0 ; varindex < mat.getColumnCount() ; varindex++) {

			SparseIntArray line = mat.getColumn(varindex);
			IExpr constraint = buildRowConstraint(line, ef);
			
			total = SparseIntArray.sumProd(1, total, 1, line);
			int min =0;
			if (positivePlace == varindex) {
				min = 1;
			}			
			script.add(
					new C_assert(
							ef.fcn(ef.symbol("<="), 
									ef.numeral(min),
									// = m0.x + X0*C(t0,x) + ...+ XN*C(Tn,x)
									constraint)));
		}
		
		if (positivePlace < 0) {
			// must be positive solution		
			script.add(new C_assert(
					ef.fcn(ef.symbol(">="), 
							buildRowConstraint(total, ef),
							ef.numeral(1)						
							)));
		}
		
		
		execAndCheckResult(script, solver);
		
		String result = checkSat(solver);
		if ("sat".equals(result)) {
			System.out.println("Found an invariant !");
			
			SparseIntArray state = new SparseIntArray();
			SparseIntArray parikh = new SparseIntArray();
			SparseIntArray order = new SparseIntArray();
			if (queryVariables(state, parikh, order, solver)) {
				System.out.println("SMT solver failed to extract model.");
				return null;
			}
			System.out.println("This invariant on transitions " + parikh);
			System.out.println("Produces a positive solution :" + state);
			
			{
				long ttime = System.currentTimeMillis();
				System.out.println("Attempting to minimize the solution found.");				
				IResponse response = solver.minimize(ef.symbol("Total"));
				
				System.out.println("Minimization OK="+ response +"took " + (System.currentTimeMillis() - ttime) + " ms.");				
			}
			result = checkSat(solver);
			SparseIntArray oldparikh = parikh;
			parikh = new SparseIntArray();
			
			if (queryVariables(state, parikh, order, solver)) {
				System.out.println("SMT solver failed to minimize model, returning non optimized solution.");
				return oldparikh;
			}

			System.out.println("This minimized invariant on transitions " + parikh);
			System.out.println("Produces a positive solution");
			
			
			solver.exit();
			return parikh;
		} else {
			solver.exit();
			System.out.println("When looking for a positive semi flow solution, solver replied " + result);
			
			if ("unsat".equals(result)) {
				return new SparseIntArray();
			} else {
				return null;
			}
		}
		
	}


	public static IExpr buildRowConstraint(SparseIntArray line, IFactory ef) {
		// assert : x = X0*C(t0,x) + ...+ XN*C(Tn,x)
		List<IExpr> toadd = new ArrayList<>();
		List<IExpr> torem = new ArrayList<>();
		
		// Ignore initial marking

		//  Xi*C(ti,x)
		for (int i = 0 ; i < line.size() ; i++) {
			int trindex = line.keyAt(i);
			int val = line.valueAt(i);
			IExpr ss = ef.symbol("t"+trindex);				
			if (val != 1 && val != -1) {
				ss = ef.fcn(ef.symbol("*"), ef.numeral( Math.abs(val)), ss );
			}
			if (val > 0) 
				toadd.add(ss);
			else
				torem.add(ss);
		}
		
		IExpr sumE ;
		if (toadd.isEmpty()) {
			sumE = ef.numeral(0);
		} else if (toadd.size() == 1) {
			sumE = toadd.get(0);
		} else {
			sumE = ef.fcn(ef.symbol("+"), toadd);
		}

		IExpr sumR;
		if (torem.isEmpty()) {
			sumR = ef.numeral(0);
		} else if (torem.size() == 1) {
			sumR = torem.get(0);
		} else {
			sumR = ef.fcn(ef.symbol("+"), torem);
		}
		
		IExpr constraint = ef.fcn(ef.symbol("-"), sumE, sumR);
		return constraint;
	}
	
	public static List<SparseIntArray> testUnreachableWithSMT(List<Expression> tocheck, ISparsePetriNet sr, List<Integer> representative,
			 int timeout, boolean withWitness) {

		return testUnreachableWithSMT(tocheck, sr, representative, timeout, withWitness, new ArrayList<>());
	}
	
	public static List<SparseIntArray> testUnreachableWithSMT(List<Expression> tocheck, ISparsePetriNet sr,
			List<Integer> representative, int timeout, boolean withWitness, List<SparseIntArray> orders) {
		if (tocheck.isEmpty()) { return new ArrayList<>(); }
		System.out.println("Running SMT prover for "+tocheck.size()+" properties.");
		if (true || tocheck.size() >= 20 || sr.getPlaceCount() + sr.getTransitionCount() >= 8000) {
			return testUnreachableWithSMTIncremental(tocheck, sr, representative, timeout, withWitness, orders);
		}
		
		List<SparseIntArray> verdicts = new ArrayList<>();
		
		IntMatrixCol sumMatrix = InvariantCalculator.computeReducedFlow(sr, representative);

		Set<SparseIntArray> invar = InvariantCalculator.computePInvariants(sumMatrix);		
		//InvariantCalculator.printInvariant(invar, sr.getPnames(), sr.getMarks());
		Set<SparseIntArray> invarT = null ; // computeTinvariants(sr, sumMatrix, tnames);
		
		ReadFeedCache rfc = new ReadFeedCache();
		for (int i=0, e=tocheck.size() ; i < e ; i++) {			
			try {
				SparseIntArray parikh = null;
				if (withWitness)
					parikh = new SparseIntArray();
				boolean solveWithReals = true;
				IExpr smtexpr = tocheck.get(i).accept(new ExprTranslator());
				Script property = new Script();
				property.add(new C_assert(smtexpr));
				SparseIntArray por = null;
				if (withWitness)
					por = new SparseIntArray();
				
				String reply = verifyPossible(sr, property, sumMatrix, invar, invarT, solveWithReals, parikh, por, representative, rfc, 3000,timeout, null);
				if ("real".equals(reply)) {
					reply = verifyPossible(sr, property, sumMatrix, invar, invarT, false, parikh, por, representative, rfc, 3000,timeout, null);
				}

				if (! "unsat".equals(reply)) {
					if (withWitness)
						verdicts.add(parikh);
					else
						verdicts.add(new SparseIntArray());
					
					if (orders != null) orders.add(por);
					
					if (DEBUG>=2 && invarT != null) {
						for (SparseIntArray invt: invarT) {
							if (SparseIntArray.greaterOrEqual(parikh, invt)) {
								System.out.println("reducible !");
							}
						}
					}
				} else {
					verdicts.add(null);
					if (orders != null) orders.add(null);
				}
			} catch (RuntimeException re) {
				Logger.getLogger("fr.lip6.move.gal").warning("SMT solver failed with error :" + re + " while checking expression at index " + i);
				verdicts.add(new SparseIntArray());
				if (orders != null) orders.add(new SparseIntArray());
			}
		}
		
		return verdicts ;
	}
	
	
	public static List<SparseIntArray> testUnreachableWithSMTIncremental(List<Expression> tocheck, ISparsePetriNet sr,
			List<Integer> representative, int timeout, boolean withWitness, List<SparseIntArray> pors) {
		
		List<Script> properties = new ArrayList<>(tocheck.size());
		List<Script> propertiesWithSE = new ArrayList<>(tocheck.size());
		IntMatrixCol sumMatrix = InvariantCalculator.computeReducedFlow(sr, representative);
				
		
		for (int i=0, e=tocheck.size() ; i < e ; i++) {			
			IExpr smtexpr = tocheck.get(i).accept(new ExprTranslator());
			Script property = new Script();
			ICommand propAssert = new C_assert(smtexpr);
			property.add(propAssert );
			properties.add(property);
			
			// compute predecessor constraint
			Script s=new Script();
			s.add(propAssert);
			try {
				// let's not go overboard, we haven't even started the solver yet.
				if (sumMatrix.getColumnCount() < 20000) {
					s = computePredConstraint(tocheck.get(i),sumMatrix,representative,sr);
					s.add(new C_assert(smtexpr));
				}
			} catch (OutOfMemoryError err) {
				System.out.println("Skipping predecessor constraint due to memory overflow.");
			} finally {
				propertiesWithSE.add(s);
			}
			
			
			SparseIntArray por = null;
			if (withWitness)
				por = new SparseIntArray();
			pors.add(por);
			
		}

		Set<SparseIntArray> invar = InvariantCalculator.computePInvariants(sumMatrix);		
		//InvariantCalculator.printInvariant(invar, sr.getPnames(), sr.getMarks());
		Set<SparseIntArray> invarT = null ; //computeTinvariants(sr, sumMatrix, tnames);
		timeout *= 5;
		boolean [] done = new boolean [tocheck.size()];
		List<SparseIntArray> parikhs = new ArrayList<>(tocheck.size());
		for (int i=0, e=tocheck.size() ; i < e ; i++) {			
			SparseIntArray parikh = new SparseIntArray();
			parikhs.add(parikh);
		}
		
		ReadFeedCache rfc = new ReadFeedCache();
		long time = System.currentTimeMillis();
		boolean solveWithReal = true;
		List<String> replies = new ArrayList<>();
		try {				
			// Step 1 : go for solveWithReals = true;
			
			replies = verifyPossible(sr, properties, propertiesWithSE, sr.isSafe(), sumMatrix, invar, invarT, solveWithReal, parikhs, pors, representative,rfc, 9000, timeout, null, done, withWitness);
			reportReplies(replies,true,"all constraints",time);
			if (replies.contains("real")) {
				for (int i=0; i < tocheck.size() ; i++) {
					if (! "unsat".equals(replies.get(i))) {
						done [i] = false;
					}
				}
				// Step 2 : go for integer domain
				time = System.currentTimeMillis();
				solveWithReal = false;
				replies = verifyPossible(sr, properties, propertiesWithSE, sr.isSafe(), sumMatrix, invar, invarT, solveWithReal, parikhs, pors, representative,rfc, 9000, timeout, null, done, withWitness);
				reportReplies(replies,false,"all constraints",time);
			}
		} catch (RuntimeException re) {
			re.printStackTrace();
			Logger.getLogger("fr.lip6.move.gal").warning("SMT solver failed with error :" + re.getMessage().substring(0, Math.min(50, re.getMessage().length())) + "... while checking expressions."); 
			
			reportReplies(replies, solveWithReal, "all constraints", time);
			// Result is:"+parikhs);	
			//re.printStackTrace();
		}
		return parikhs;
	}


	public static void reportReplies(List<String> replies, boolean solveWithReal, String cause, long time) {
		long real = replies.stream().filter(s->"real".equals(s)).count();
		Logger.getLogger("fr.lip6.move.gal").info("After "+(System.currentTimeMillis()-time) +"ms SMT Verify possible using "+ cause +" in " + (solveWithReal ? "real" :"natural" )+ " domain returned "
				+"unsat :" + replies.stream().filter(s->"unsat".equals(s)).count()
				+ " sat :" + replies.stream().filter(s->"sat".equals(s)).count()
				+ (real>0 ? (" real:" + real) : "")
				);
	}

	private static Script computePredConstraint(Expression ap, IntMatrixCol sumMatrix,
			List<Integer> representative, ISparsePetriNet sr) {
		
		// we know that s satisfies ap
		// we want to force existence of an immediate predecessor of s satisfying !ap
		
		// there must exist a transition t such that
		// * the predecessor by t of s satisfies !ap; this depends only on the effect of t, not it's precise definition
		// * t was feasibly the last fired transition, i.e. there is a transition t' that t represents such that s >= post(t')
		// * t was selected in the Parikh solution to reach s; |t|>0
		
		// a map from index in reduced flow to set of transitions with this effect
		Map<Integer, List<Integer>> revMap = computeImages(representative); 
		
		SparseIntArray supp = computeSupport(ap);
		
		
		IFactory ef = new SMT().smtConfig.exprFactory;
		
		List<IExpr> allPotentialPred = new ArrayList<>();
		
		int selected = 0;
		// scan transition *effects* in sumMatrix
		for (int tid=0, tide=sumMatrix.getColumnCount() ; tid < tide ; tid++) {
			SparseIntArray t = sumMatrix.getColumn(tid);
			if (! SparseIntArray.keysIntersect(supp, t)) {
				// guaranteed to stutter : this is not a candidate
				continue;				
			} else {
				if (selected++ > 1000) {
					// whatever...
					return new Script();
				}
				// more subtle we do touch the target AP
				// compute if firing t would go from !ap to ap
				// * the predecessor by t of s satisfies !ap; this depends only on the effect of t, not it's precise definition
				IExpr apFalseBeforeT = rewriteAfterEffect(Expression.not(ap),t,true).accept(new ExprTranslator());
				
				// * t was selected in the Parikh solution to reach s; |t|>0
				IExpr tselected = ef.fcn(ef.symbol(">="), ef.symbol("t"+tid), ef.numeral(1));
				
				// * t was feasibly the last fired transition, i.e. there is a transition t' that t represents such that s >= post(t')
				List<IExpr> alternatives = new ArrayList<>();
				for (Integer ti : revMap.get(tid)) {
					alternatives.add(buildFeasible(ef, sr.getFlowTP().getColumn(ti)));					
				}
				
				// combine
				List<IExpr> toAnd = new ArrayList<>();
				toAnd.add(apFalseBeforeT);
				toAnd.add(tselected);
				toAnd.add(SMTUtils.makeOr(alternatives));
				allPotentialPred.add(SMTUtils.makeAnd(toAnd));
			}			
		}
		
		Script script = new Script();
		
		// assert an OR of one of the candidates 
		script.add(new C_assert(SMTUtils.makeOr(allPotentialPred)));
				
		return script;
	}


	private static IExpr buildFeasible(IFactory ef, SparseIntArray tp) {
		List<IExpr> tojoin = new ArrayList<>();
		for (int i=0,ie=tp.size();i<ie;i++) {
			int pid = tp.keyAt(i);
			int val = tp.valueAt(i);
			
			// must be that s is greater than tp
			tojoin.add(ef.fcn(ef.symbol(">="), ef.symbol("s"+pid), ef.numeral(val)));
		}
		
		return SMTUtils.makeAnd(tojoin);
	}


	public static boolean testEGap(Expression ap, ISparsePetriNet sr, int timeout) {

		try {
			List<Integer> representative = new ArrayList<>();
			IntMatrixCol sumMatrix = InvariantCalculator.computeReducedFlow(sr, representative);

			// a map from index in reduced flow to set of transitions with this effect
			Map<Integer, List<Integer>> revMap = computeImages(representative);

			SparseIntArray supp = computeSupport(ap);

			Script cantStutter = new Script();

			// assert ap
			cantStutter.add(new C_assert(ap.accept(new ExprTranslator())));
			cantStutter.add(new C_check_sat());

			IFactory ef = new SMT().smtConfig.exprFactory;
			List<IExpr> globalEnable = new ArrayList<>();
			// assert that all transitions that stutter (because of support of effects not
			// intersecting AP) are disabled
			for (int tid = 0, tide = sumMatrix.getColumnCount(); tid < tide; tid++) {
				SparseIntArray t = sumMatrix.getColumn(tid);
				if (!SparseIntArray.keysIntersect(supp, t)) {
					// guaranteed to stutter
					for (Integer ti : revMap.get(tid)) {
						IExpr disabled = buildDisabled(ef, sr.getFlowPT().getColumn(ti));
						cantStutter.add(new C_assert(disabled));
						cantStutter.add(new C_check_sat());
					}
				} else {
					// more subtle we do touch the target AP
					// if firing t would go from ap to !ap => add to enabling
					// if firing t leaves ap => must be disabled

					// afterT is true if after t ap is still true (we stutter)
					IExpr apTrueAfterT = rewriteAfterEffect(ap, t, false).accept(new ExprTranslator());
					IExpr apFalseAfterT = ef.fcn(ef.symbol("not"), apTrueAfterT);

					List<IExpr> toDisable = new ArrayList<>();

					for (Integer ti : revMap.get(tid)) {
						IExpr disabled = buildDisabled(ef, sr.getFlowPT().getColumn(ti));
						toDisable.add(disabled);

						globalEnable.add(ef.fcn(ef.symbol("and"), apFalseAfterT, ef.fcn(ef.symbol("not"), disabled)));
					}

					// collect disabled : ap and X ap => AND_repr t disabled
					if (!toDisable.isEmpty()) {
						IExpr stuttImplyDisabled = ef.fcn(ef.symbol("=>"), apTrueAfterT, SMTUtils.makeAnd(toDisable));
						cantStutter.add(new C_assert(stuttImplyDisabled));
						cantStutter.add(new C_check_sat());
					}
				}
			}
			// assert an OR of one modifying transition enabled
			cantStutter.add(new C_assert(SMTUtils.makeOr(globalEnable)));
			cantStutter.add(new C_check_sat());
			// ok let's go
			try {
				Set<SparseIntArray> invar = InvariantCalculator.computePInvariants(sumMatrix);
				ReadFeedCache rfc = new ReadFeedCache();
				String reply = verifyPossible(sr, cantStutter, sumMatrix, invar, null, true, null, null,
						representative, rfc, 3000, timeout, null);
				if ("real".equals(reply)) {
					reply = verifyPossible(sr, cantStutter, sumMatrix, invar, null, false, null, null,
							representative, rfc, 3000, timeout, null);
				}
				if ("unsat".equals(reply)) {
					return true;
				}
			} catch (RuntimeException re) {
				re.printStackTrace();
				Logger.getLogger("fr.lip6.move.gal")
						.warning("SMT solver failed with error :" + re + " while checking expression EG " + ap);
			}

		} catch (OutOfMemoryError e) {
			Logger.getLogger("fr.lip6.move.gal").warning("OOM error produced while checking expression EG " + ap);
		}
		return false;
	}


	private static SparseIntArray computeSupport(Expression ap) {
		// compute support of e
		SparseIntArray supp;
		{
			BitSet suppbs = new BitSet();
			SparsePetriNet.addSupport(ap,suppbs);
			supp=new SparseIntArray(suppbs);
		}
		return supp;
	}
	
	private static Expression rewriteAfterEffect (Expression expr, SparseIntArray t, boolean before) {
		if (expr == null) {
			return null;
		} else if (expr instanceof VarRef) {
			VarRef vref = (VarRef) expr;
			int delta=t.get(vref.getValue());
			if (delta != 0) {
				if (before) {
					delta = -delta;
				}
				return Expression.nop(Op.ADD, expr, Expression.constant(delta));
			} else {
				return expr;
			}
		} else if (expr instanceof AtomicPropRef) {
			AtomicPropRef apr = (AtomicPropRef) expr;
			return rewriteAfterEffect(apr.getAp().getExpression(),t,before) ;			
		} else {
			List<Expression> resc = new ArrayList<>();
			boolean changed = false;
			for (int i=0,ie=expr.nbChildren(); i < ie; i++) {
				Expression child = expr.childAt(i);
				Expression nc = rewriteAfterEffect(child, t,before);
				resc.add(nc);
				if (nc != child) {
					changed = true;
				}
			}
			if (!changed) {
				return expr;
			}
			return Expression.nop(expr.getOp(), resc);			
		} 
	}
	
	
	private static List<String> verifyPossible(ISparsePetriNet sr, List<Script> properties, List<Script> propertiesWithSE,
			boolean isSafe, IntMatrixCol sumMatrix, Set<SparseIntArray> invar,
			Set<SparseIntArray> invarT, boolean solveWithReals, List<SparseIntArray> parikhs, List<SparseIntArray> pors,
			List<Integer> representative, ReadFeedCache readFeedCache, int timeoutQ, int timeoutT, ICommand minmax, boolean[] done, boolean withWitness) {
		long time = System.currentTimeMillis();		
		lastState = null;
		lastParikh = null;
		lastOrder = null;
		org.smtlib.SMT smt = new SMT();
		List<String> verdicts = new ArrayList<>();
		for (int i=0; i < properties.size() ; i++) {
			if (done[i]) {
				verdicts.add("unsat");
			} else {
				verdicts.add("sat");
			}
		}
		ISolver solver = initSolver(smt, solveWithReals,2*timeoutQ,2*timeoutT);	
		try {
		
			
		{
			// STEP 1 : declare variables, assert net is dead.
			time = System.currentTimeMillis();
			Script varScript = declareVariables(sr.getPnames().size(), "s", isSafe, smt,solveWithReals);
			execAndCheckResult(varScript, solver);
			// add the script's constraints
			if (checkResults(properties, done, parikhs, pors, verdicts,solver,withWitness)) {
				solver.exit();
				return verdicts;
			}
		}

		// STEP 2 : declare and assert invariants 
		String textReply = assertInvariants(invar, sr, solver, smt,true, solveWithReals);

		// are we finished ?
		if (checkResults(properties, done, parikhs, pors, verdicts,solver,withWitness)) {
			solver.exit();
			return verdicts;
		}		

		// STEP 3 : go heavy, use the state equation to refine our solution
		time = System.currentTimeMillis();
		Script script = declareStateEquation(sumMatrix, sr.getMarks(), smt,solveWithReals,invarT);
		execAndCheckResult(script, solver);
		// are we finished ?
		if (checkResults(propertiesWithSE, done, parikhs, pors, verdicts,solver,withWitness)) {
			solver.exit();
			return verdicts;
		}	
		reportReplies(verdicts, solveWithReals, "state equation", time);
					
		// add read => feed constraints
		{
			Script readFeed = readFeedCache.getScript(sr, sumMatrix, representative);
			if (!readFeed.commands().isEmpty()) {
				time = System.currentTimeMillis();
				execAndCheckResult(readFeed, solver);
				
				if (checkResults(propertiesWithSE, done, parikhs, pors, verdicts,solver,withWitness)) {
					reportReplies(verdicts, solveWithReals, readFeed.commands().size() +" Read/Feed constraints", time);
					solver.exit();
					return verdicts;
				}
				reportReplies(verdicts, solveWithReals, readFeed.commands().size() +" Read/Feed constraints", time);

			}
		}
		
		
		// are we finished ?
		if (refineResultsWithTraps(sr, smt, propertiesWithSE, done, parikhs, pors, verdicts, solver,withWitness)) {
			reportReplies(verdicts, solveWithReals,"trap constraints", time);
				solver.exit();
				return verdicts;
		}
		reportReplies(verdicts, solveWithReals,"trap constraints", time);
		
//				
//		String rep = refineWithTraps(sr, tnames, solver, smt, solverPath);
//		if (! "none".equals(rep)) {
//			textReply = rep;				
//			textReply = realityCheck(tnames, solveWithReals, solver, textReply);
//		}
//		
//		if (textReply.equals("sat")) {
//			textReply = refineWithCausalOrder(sr, solver, sumMatrix, solveWithReals, representative, smt, tnames, solverPath);
//			textReply = realityCheck(tnames, solveWithReals, solver, textReply);
//		}

		// currently disabled due to high cost and low rewards
		boolean minimizeTraces = true;
		if (minimizeTraces){
			long ttime = System.currentTimeMillis();
			if (minmax == null) {
				System.out.println("Attempting to minimize the solution found.");				
				List<IExpr> tosum = new ArrayList<>(sumMatrix.getColumnCount());
				IFactory ef = smt.smtConfig.exprFactory;
				for (int trindex=0; trindex < sumMatrix.getColumnCount(); trindex++) {
					IExpr ss = ef.symbol("t"+trindex);
					tosum.add(ss);
				}
				solver.minimize(ef.fcn(ef.symbol("+"), tosum));
			} else {
				IResponse r = minmax.execute(solver);
				if (r.isError()) {
					System.err.println("Maximisation of solution failed !");
				}
			}
			checkResults(propertiesWithSE, done, parikhs, pors, verdicts, solver, withWitness);
			System.out.println("Minimization took " + (System.currentTimeMillis() - ttime) + " ms.");				
		} else {
			checkResults(propertiesWithSE, done, parikhs, pors, verdicts, solver, withWitness);			
		}
		
		} catch (RuntimeException re) {
			System.out.println("SMT process timed out, exceeded limit of "+timeoutT);
			reportReplies(verdicts, solveWithReals,"After timeout", time);
		} 		
		solver.exit();
		
		return verdicts;
	}




	private static boolean refineResultsWithTraps(ISparsePetriNet sr, SMT smt,
			List<Script> properties, boolean[] done, List<SparseIntArray> parikhs, List<SparseIntArray> pors,
			List<String> verdicts, ISolver solver, boolean withWitness) {
		
		int nbdone = 0;
		
		boolean hasTraps = true;
		for (int iter=0; hasTraps; iter++) {
			hasTraps = false;
			System.out.println("TRAPS : Iteration " + iter);
		for (int pid = 0, pide = properties.size() ; pid < pide ; pid++) {
			if (done[pid]) {
				nbdone++;
				continue;
			} else {
				
				IResponse res = solver.push(1);
				if (res.isError()) {
					break;
				}
				Script traps = new Script();
				
				Script tocheck = properties.get(pid);				
				execAndCheckResult(tocheck, solver);
				String textReply = checkSat(solver);

				// are we finished ?
				if (textReply.equals("unsat")||textReply.equals("unknown")) {
					done[pid] = true;
					nbdone++;
					verdicts.set(pid, textReply);
					if (textReply.equals("unsat"))
						parikhs.set(pid, null);
				}

				if (textReply.equals("sat")) {
					
					textReply = refineWithTraps(sr, solver, smt, traps);
					if (textReply.equals("unsat")||textReply.equals("unknown")) {
						done[pid] = true;
						nbdone++;
						verdicts.set(pid, textReply);
						if (textReply.equals("unsat"))
							parikhs.set(pid, null);
					} else {
					
						SparseIntArray state = new SparseIntArray();
						SparseIntArray parikh = new SparseIntArray();
						SparseIntArray por = new SparseIntArray();
						boolean hasreals = queryVariables(state, parikh, por, solver);
						if (hasreals)
						{
							// Logger.getLogger("fr.lip6.move.gal").info("Solution in real domain found non-integer solution.");
							textReply = "real";
							done[pid] = true;
							nbdone++;
							verdicts.set(pid, textReply);
						} else {
							parikhs.set(pid, parikh);
							pors.set(pid, por);
						}
					}
				}

				res = solver.pop(1);
				if (res.isError()) {
					break;
				}
				
				if (! traps.commands().isEmpty()) {
					execAndCheckResult(traps, solver);
					traps = new Script();
					hasTraps = true;
				}
				
			}
		}
		}

		// true if no more props to check
		return nbdone == properties.size();
	}


	private static boolean checkResults(List<Script> properties, boolean[] done, List<SparseIntArray> parikhs,
			List<SparseIntArray> pors, List<String> verdicts, ISolver solver, boolean withWitness) {

		int nbdone = 0;
		for (int pid = 0, pide = properties.size() ; pid < pide ; pid++) {
			if (done[pid]) {
				nbdone++;
				continue;
			} else {
				
				IResponse res = solver.push(1);
				if (res.isError()) {
					break;
				}
				
				Script tocheck = properties.get(pid);				
				execAndCheckResult(tocheck, solver);
				String textReply = checkSat(solver);

				// are we finished ?
				if (textReply.equals("unsat")||textReply.equals("unknown")) {
					done[pid] = true;
					nbdone++;
					verdicts.set(pid, textReply);
					if (textReply.equals("unsat"))
						parikhs.set(pid, null);
				}

				if (textReply.equals("sat") && withWitness) {
					SparseIntArray state = new SparseIntArray();
					SparseIntArray parikh = new SparseIntArray();
					SparseIntArray por = new SparseIntArray();
					boolean hasreals = queryVariables(state, parikh, por, solver);
					if (hasreals)
					{
						// Logger.getLogger("fr.lip6.move.gal").info("Solution in real domain found non-integer solution.");
						textReply = "real";
						done[pid] = true;
						nbdone++;
						verdicts.set(pid, textReply);
					} else {
						parikhs.set(pid, parikh);
						pors.set(pid, por);
					}
				}

				res = solver.pop(1);
				if (res.isError()) {
					break;
				}

			}
		}

		// true if no more props to check
		return nbdone == properties.size();				
	}
	



	private static class ReadFeedCache {
		boolean isInit = false;
		Script readFeed ;
		
		Script getScript(ISparsePetriNet sr, IntMatrixCol sumMatrix, List<Integer> representative) {
			if (! isInit) {
				isInit = true;
				readFeed = addReadFeedConstraints(sr, sumMatrix, representative);
			}
			return readFeed;
		}
	}
	
	private static String areDeadlocksPossible(ISparsePetriNet sr, IntMatrixCol sumMatrix, 
			Set<SparseIntArray> invar, Set<SparseIntArray> invarT, boolean solveWithReals, SparseIntArray parikh, SparseIntArray por, List<Integer> representative) {
		Script scriptAssertDead = assertNetIsDead(sr);
		return verifyPossible(sr, scriptAssertDead, sumMatrix, invar, invarT, solveWithReals, parikh, por, representative, new ReadFeedCache(), 3000, 300, null);
	}
		
	static final Configuration smtConf = new SMT().smtConfig;
	private static String verifyPossible(ISparsePetriNet sr, Script tocheck, IntMatrixCol sumMatrix,
			Set<SparseIntArray> invar, Set<SparseIntArray> invarT, boolean solveWithReals, SparseIntArray parikh, SparseIntArray por, List<Integer> representative, ReadFeedCache readFeedCache, int timeoutQ, int timeoutT, ICommand minmax) {
		long time;		
		lastState = null;
		lastParikh = null;
		lastOrder = null;
		org.smtlib.SMT smt = new SMT();
		ISolver solver = initSolver(smt, solveWithReals,timeoutQ,timeoutT);		
		{
			// STEP 1 : declare variables, assert net is dead.
			time = System.currentTimeMillis();
			Script varScript = declareVariables(sr.getPnames().size(), "s", sr.isSafe(), smt,solveWithReals);
			execAndCheckResult(varScript, solver);
			// add the script's constraints
			execAndCheckResult(tocheck, solver);
			String textReply = checkSat(solver);
			// are we finished ?
			if (textReply.equals("unsat")||textReply.equals("unknown")) {
				solver.exit();
				return textReply;
			}
		}

		// STEP 2 : declare and assert invariants 
		String textReply = assertInvariants(invar, sr, solver, smt,true, solveWithReals);

		// are we finished ?
		if (textReply.equals("unsat")||textReply.equals("unknown")) {
			solver.exit();
			return textReply;
		}

		// STEP 3 : go heavy, use the state equation to refine our solution
		time = System.currentTimeMillis();
		// Logger.getLogger("fr.lip6.move.gal").info((solveWithReals ? "[Real]":"[Nat]")+"Adding state equation constraints to refine reachable states.");
		Script script = declareStateEquation(sumMatrix, sr.getMarks(), smt,solveWithReals, invarT);
		
		execAndCheckResult(script, solver);
		textReply = checkSat(solver,  false);
		Logger.getLogger("fr.lip6.move.gal").info((solveWithReals ? "[Real]":"[Nat]")+"Absence check using state equation in "+ (System.currentTimeMillis()-time) +" ms returned " + textReply);
				
		textReply = realityCheck(solveWithReals, solver, textReply);
		if (textReply.equals("sat")) {
			Script readFeed = readFeedCache.getScript(sr, sumMatrix, representative);
			if (!readFeed.commands().isEmpty()) {
				time = System.currentTimeMillis();
				execAndCheckResult(readFeed, solver);
				textReply = checkSat(solver,  true);
				Logger.getLogger("fr.lip6.move.gal").info((solveWithReals ? "[Real]":"[Nat]")+"Added " + readFeed.commands().size() +" Read/Feed constraints in "+ (System.currentTimeMillis()-time) +" ms returned " + textReply);							
				textReply = realityCheck(solveWithReals, solver, textReply);
			}
		}
		
		if (textReply.equals("sat")) {
			String rep = refineWithTraps(sr, solver, smt, null);
			if (! "none".equals(rep)) {
				textReply = rep;				
				textReply = realityCheck(solveWithReals, solver, textReply);
			}
		}
		if (textReply.equals("sat")) {
			textReply = refineWithCausalOrder(sr, solver, sumMatrix, solveWithReals, representative, smt);
			textReply = realityCheck(solveWithReals, solver, textReply);
		}
//		if (textReply.equals("sat")) {
//			String rep = refineWithTraps(sr, tnames, solver, smt, solverPath);
//			if (! "none".equals(rep)) {
//				textReply = rep;				
//				textReply = realityCheck(tnames, solveWithReals, solver, textReply);
//			}
//		}
		// currently disabled due to high cost and low rewards
		boolean minimizeTraces = false;

		if (textReply.equals("sat") && parikh != null) {
			if (minmax != null || (minimizeTraces && sumMatrix.getColumnCount() < 3000)) {
				long ttime = System.currentTimeMillis();
				if (minmax == null) {
					System.out.println("Attempting to minimize the solution found.");				
					List<IExpr> tosum = new ArrayList<>(sumMatrix.getColumnCount());
					IFactory ef = smt.smtConfig.exprFactory;
					for (int trindex=0; trindex < sumMatrix.getColumnCount(); trindex++) {
						IExpr ss = ef.symbol("t"+trindex);
						tosum.add(ss);
					}
					solver.minimize(ef.fcn(ef.symbol("+"), tosum));
				} else {
					IResponse r = minmax.execute(solver);
					if (r.isError()) {
						System.err.println("Maximisation of solution failed !");
					}
				}
				textReply = checkSat(solver,  false);
				System.out.println("Minimization took " + (System.currentTimeMillis() - ttime) + " ms.");				
			}
			
			if (textReply.equals("sat") && parikh != null) {
				SparseIntArray state = new SparseIntArray();
				boolean hasreals = queryVariables(state, parikh, por==null ? new SparseIntArray():por, solver);
				if (hasreals)
					{
					Logger.getLogger("fr.lip6.move.gal").info("Solution in real domain found non-integer solution.");
					textReply = "real";
					}
				
				//			System.out.println("SAT in Deadlock state : ");
				//			for (int i=0 ; i < state.size() ; i++) {
				//				System.out.print(sr.getPnames().get(state.keyAt(i))+"="+ state.valueAt(i)+", ");
				//			}
				//			System.out.println();
			}
		} else if ("unknown".equals(textReply) && lastParikh != null && parikh != null) {
			parikh.move(lastParikh);
			if (por != null) {
				por.move(lastOrder);
			}
		}
		
		solver.exit();
		return textReply;
	}

	public static String realityCheck(boolean solveWithReals, ISolver solver, String textReply) {
		if (textReply.equals("sat") && solveWithReals) {			
			if (queryVariables(new SparseIntArray(), new SparseIntArray(), new SparseIntArray(), solver)) {
				Logger.getLogger("fr.lip6.move.gal").info("Solution in real domain found non-integer solution.");
				textReply = "real";
			}
		}
		return textReply;
	}

	private static String refineWithCausalOrder(ISparsePetriNet sr, ISolver solver, IntMatrixCol sumMatrix,
			boolean solveWithReals, List<Integer> representative, SMT smt) {
		long time = System.currentTimeMillis();
		Map<Integer,List<Integer>> images = computeImages(representative);
		IFactory ef = smt.smtConfig.exprFactory;
		// order of the transitions
		if (useAbstractDataType == POType.Plunge) {
			Script decl = declareVariables(sumMatrix.getColumnCount(), "o", false, false, smt, solveWithReals);
			execAndCheckResult(decl, solver);
		} else if (useAbstractDataType == POType.Forall){
			Script decl = new Script();
			// an abstract datatype A
			org.smtlib.ISort.IApplication type = smt.smtConfig.sortFactory.createSortExpression(ef.symbol("A"));
			decl.add(new C_declare_sort(ef.symbol("A"), ef.numeral(0)));
			// a binary predicate < : A x A -> Bool
			List<ISort> sorts = new ArrayList<>();
			sorts.add(type);
			sorts.add(type);
			decl.add(new C_declare_fun(ef.symbol("<"), sorts, smt.smtConfig.sortFactory.createSortExpression(ef.symbol("Bool"))));
			// irreflexive ! x < x
			IDeclaration xinA = ef.declaration(ef.symbol("x"), type);
			decl.add(new C_assert(ef.forall(Collections.singletonList(xinA), 
						ef.fcn(ef.symbol("not"), ef.fcn(ef.symbol("<"), ef.symbol("x"), ef.symbol("x"))))));
			// anti symmetric : x < y  => ! y < x
			IDeclaration yinA = ef.declaration(ef.symbol("y"), type);
			List<IDeclaration> args = new ArrayList<>();
			args.add(xinA);
			args.add(yinA);
			decl.add(new C_assert(ef.forall(args, 
					ef.fcn(ef.symbol("=>"),
							ef.fcn(ef.symbol("<"), ef.symbol("x"), ef.symbol("y")),
							ef.fcn(ef.symbol("not"),ef.fcn(ef.symbol("<"), ef.symbol("y"), ef.symbol("x")))))));
			// transitive : x < y && y < z => x < z
			IDeclaration zinA = ef.declaration(ef.symbol("z"), type);
			args.add(zinA);
			decl.add(new C_assert(ef.forall(args, 
					ef.fcn(ef.symbol("=>"),
							ef.fcn(ef.symbol("and"), 
									ef.fcn(ef.symbol("<"), ef.symbol("x"), ef.symbol("y")),
									ef.fcn(ef.symbol("<"), ef.symbol("y"), ef.symbol("z"))),
							ef.fcn(ef.symbol("<"), ef.symbol("x"), ef.symbol("z"))))));						
			execAndCheckResult(decl, solver);
			// declare actual variables
			decl = declareVariables(sumMatrix.getColumnCount(), "o", false, false, smt, "A");
			execAndCheckResult(decl, solver);
		} else if (useAbstractDataType == POType.Partial) {
			Script decl = new Script();
			// an abstract datatype A
			org.smtlib.ISort.IApplication type = smt.smtConfig.sortFactory.createSortExpression(ef.symbol("A"));
			decl.add(new C_declare_sort(ef.symbol("A"), ef.numeral(0)));
			List<IDeclaration> sorts = new ArrayList<>();
			sorts.add(ef.declaration(ef.symbol("x"), type));
			sorts.add(ef.declaration(ef.symbol("y"), type));
			IExpr body = ef.fcn(ef.symbol("and"), 
					ef.fcn(ef.symbol("not"), ef.fcn(ef.symbol("="), ef.symbol("x"),ef.symbol("y"))), // irreflexive				
					ef.fcn(ef.fcn(ef.symbol("_"), ef.symbol("partial-order"), ef.numeral(0)), ef.symbol("x"), ef.symbol("y")))
					;
			decl.add(new C_define_fun(ef.symbol("<"), sorts, smt.smtConfig.sortFactory.createSortExpression(ef.symbol("Bool")), body));
			execAndCheckResult(decl, solver);
			// declare actual variables
			decl = declareVariables(sumMatrix.getColumnCount(), "o", false, false, smt, "A");
			execAndCheckResult(decl, solver);
		}
				
		IntMatrixCol tsum = sumMatrix.transpose();
		int nbadded = 0;
		int nbalts = 0;
		int nbrep = 0;
		int nbskipped = 0;
		// a list of asserts, or null if empty or already used.
		List<C_assert> perTransition = new ArrayList<>();
		for (int tid=0; tid < sumMatrix.getColumnCount() ; tid++) {
			List<IExpr> perImage = new ArrayList<>();
			int localadded = 0;
			int localalts = 0;
			for (int img : images.get(tid)) {
				SparseIntArray pt = sr.getFlowPT().getColumn(img);
				
				// constraints on places that we consume from
				List<IExpr> prePlace = new ArrayList<>();
				for (int i = 0; i < pt.size() ; i++) {
					int p = pt.keyAt(i);
					int v = pt.valueAt(i);
					if (v > sr.getMarks().get(p)) {
						List<IExpr> couldFeed = new ArrayList<>();
						// find a feeder for p
						SparseIntArray feeders = tsum.getColumn(p);
						for (int j=0; j < feeders.size() ; j++) {
							int t2 = feeders.keyAt(j);
							int v2 = feeders.valueAt(j);
							if (t2 != tid && v2 > 0) {
								localalts++;
								// true feed effect
								couldFeed.add(
										ef.fcn(ef.symbol("and"), 
												ef.fcn(ef.symbol(">"), ef.symbol("t"+t2), ef.numeral(0)), 
												ef.fcn(ef.symbol("<"), ef.symbol("o"+t2), ef.symbol("o"+tid)))); // the ordering constraint
							}
						}
						prePlace.add(SMTUtils.makeOr(couldFeed));						
					}					
				}
				if (!prePlace.isEmpty()) {
					perImage.add(SMTUtils.makeAnd(prePlace));
					localadded++;
				} else {
					// found an image that is initially fireable => true => clear constraint.
					perImage.clear();
					localadded = 0;
					localalts = 0;
					break;
				}
			}
			if (localalts < 10) {
				nbadded += localadded;
				nbalts += localalts;
				if (!perImage.isEmpty()) {
					IExpr causal = ef.fcn(ef.symbol("=>"), ef.fcn(ef.symbol(">"), ef.symbol("t"+tid), ef.numeral(0)), SMTUtils.makeOr(perImage)); 
					perTransition.add (new C_assert(causal));
					//				if (tid % 10 == 0) {
					//					tocheck.add(new C_check_sat());
					//				}
					nbrep++;
				} else {
					perTransition.add(null);
				}
			} else {
				perTransition.add(null);
				nbskipped++;
			}

		}
		Logger.getLogger("fr.lip6.move.gal").info("Computed and/alt/rep : "+nbadded + "/"+ nbalts +"/"+ nbrep +" causal constraints (skipped "+ nbskipped +" transitions) in "+ (System.currentTimeMillis()-time) +" ms.");
		
		// these usually just produce "unknown" and a timeout
		if (nbalts >= 20000) {
			return "sat";
		}
		// ok so now progressively feed constraints
		int total = 0;
		int iter = 0;
		String textReply = checkSat(solver);
		boolean timeout = false;
		long ttime = time;
		
		if (useAbstractDataType == POType.Partial) {
			Script todo = new Script();
			for (C_assert constraint : perTransition) {
				if (constraint != null) {
					todo.add(constraint);
					if (++total % 5 == 0) {
						execAndCheckResult(todo, solver);
						todo.commands().clear();
						checkSat(solver);
					}
				}
			}
			if (! todo.commands().isEmpty()) {
				execAndCheckResult(todo, solver);			
			}
		} else {
			while ("sat".equals(textReply)) {
				SparseIntArray state = new SparseIntArray();
				SparseIntArray parikh = new SparseIntArray();
				boolean hasR = queryVariables(state,parikh,new SparseIntArray(),solver);
				if (hasR) {
					return "sat";
				}
				int effective = 0;
				Script tocheck = new Script();
				for (int i=0; i < parikh.size() ; i++) {
					int tid = parikh.keyAt(i);
					if (perTransition.get(tid)!=null) {
						tocheck.add(perTransition.get(tid));
						perTransition.set(tid, null);
						effective++;
						if (tocheck.commands().size() >= 5) {
							break;
						}
					}
				}
				if (effective == 0) {
					String rep = refineWithTraps(sr, solver, smt, null);
					if (! "none".equals(rep)) {
						continue;
					} else {
						break;
					}
				}
				total +=effective;
				iter++;
				execAndCheckResult(tocheck, solver);
				textReply = checkSat(solver);
				if ("sat".equals(textReply) && System.currentTimeMillis()-ttime  > 1000) {
					ttime=System.currentTimeMillis();
					state = new SparseIntArray();
					parikh = new SparseIntArray();
					queryVariables(state, parikh, new SparseIntArray(), solver);
				}
				if (System.currentTimeMillis()-time  > 20000) {
					timeout = true;
					break;
				}
			}
		}
		String res = checkSat(solver);
		Logger.getLogger("fr.lip6.move.gal").info("Added : "+total + " causal constraints over "+ iter +" iterations in "+ (System.currentTimeMillis()-time) +" ms."+ (timeout?"(timeout)":"") + " Result :"+res);
		return res;
	}

	private static Map<Integer, List<Integer>> computeImages(List<Integer> representative) {
		Map<Integer, List<Integer>> images = new HashMap<>();
		for (int i=0; i < representative.size() ; i++) {
			images.computeIfAbsent(representative.get(i), k -> new ArrayList<>()).add(i);
		}
		return images;
	}

	private static String refineWithTraps(ISparsePetriNet sr, ISolver solver,
			org.smtlib.SMT smt, Script traps) {
		long time = System.currentTimeMillis();		
		List<Integer> trap ;
		String textReply = "none";
		IFactory ef = smt.smtConfig.exprFactory;
		int added =0;
		int tested =0;
		
		int lasttrapsize = -1;
		int repeatTraps = 0;
		do {
			SparseIntArray state = new SparseIntArray();
			SparseIntArray pk = new SparseIntArray();
			if (queryVariables(state, pk, new SparseIntArray(),solver)) {
				return "sat";
			}
			trap = testTrapWithSMT(sr, state);
			if (DEBUG >=1)
				confirmTrap(sr,trap, state);
			tested++;
			if (!trap.isEmpty()) {
				
				
				if (trap.size() == lasttrapsize) {
					repeatTraps++;
				} else {
					lasttrapsize = trap.size();
					repeatTraps = 0;
				}
				if (repeatTraps >= 10) {
					// this is not looking good, might have a combinatorial number of traps
					Logger.getLogger("fr.lip6.move.gal").info("Trap strengthening procedure interrupted after too many repetitions " + (System.currentTimeMillis() -time) + " ms");
					break;
				}
				
				added++;
				// add a constraint
				List<IExpr> vars = trap.stream().map(n -> ef.symbol("s"+n)).collect(Collectors.toList());
				IExpr sum = buildSum(vars);
				Script s = new Script();
				ICommand constraint = new C_assert(ef.fcn(ef.symbol(">"), sum , ef.numeral(0)));
				s.add(constraint);
				if (traps != null) {
					traps.add(constraint);
				}
				execAndCheckResult(s, solver);
				textReply = checkSat(solver,  false);
				if (textReply.equals("unsat")) {
					Logger.getLogger("fr.lip6.move.gal").info("Trap strengthening procedure managed to obtain unsat after adding "+added+ " trap constraints in " + (System.currentTimeMillis() -time) + " ms");
					return textReply;
				} else if (textReply.equals("unknown")) {
					break;
				}
			}				
		} while (!trap.isEmpty());
		if (tested > 1 || added > 0) {
			Logger.getLogger("fr.lip6.move.gal").info("Trap strengthening (SAT) tested/added "+tested +"/" + added+ " trap constraints in " + (System.currentTimeMillis() -time) + " ms");
		}
		return textReply;
	}
	private static void confirmTrap(ISparsePetriNet sr, List<Integer> trap, SparseIntArray state) {
		if (trap.isEmpty())
			return;
		Set<Integer> targets = new HashSet<>(trap);
		for (int t = 0, e=sr.getTnames().size() ; t < e ;  t++) {
			SparseIntArray colpt = sr.getFlowPT().getColumn(t);
			boolean eats = false;
			for (int i=0 ; i < colpt.size() ; i++) {
				if (targets.contains(colpt.keyAt(i))) {
					eats = true;
					break;
				}
			}
			if (eats) {
				SparseIntArray coltp = sr.getFlowTP().getColumn(t);
				boolean feeds = false;
				for (int i=0 ; i < coltp.size() ; i++) {
					if (targets.contains(coltp.keyAt(i))) {
						feeds = true;
						break;
					}
				}
				if (!feeds) {
					System.err.println("This is NOT a trap !");
					return;
				}
			}
		}
		// make sure the trap is empty in target state
		for (int i : trap) {
			if (state.get(i) > 0) {
				System.err.println("This trap is already marked !");
				return;
			}
		}
		System.err.println("Trap OK");
	}

	// note the List should be large enough
	private static void queryBoolVariables (List<Boolean> res, ISolver solver) {
		IResponse r = new C_get_model().execute(solver);
		if (r instanceof ISeq) {
			ISeq seq = (ISeq) r;
			for (ISexpr v : seq.sexprs()) {
				if (v instanceof ISeq) {
					ISeq vseq = (ISeq) v;
					if (vseq.sexprs().get(1).toString().startsWith("s")) {
						int tid = Integer.parseInt( vseq.sexprs().get(1).toString().substring(1) );
						boolean value = Boolean.parseBoolean( vseq.sexprs().get(vseq.sexprs().size()-1).toString());
						
						if (value) {							
							res.set(tid, value);					
						}
					}
				}
			}
		}
	}
	static SparseIntArray lastState = null;
	static SparseIntArray lastOrder = null;
	static SparseIntArray lastParikh = null;
	private static boolean queryVariables(SparseIntArray state, SparseIntArray parikh, SparseIntArray order, ISolver solver) {
		boolean hasReals = false;
		IResponse r = new C_get_model().execute(solver);
		if (r.isError()) {
			return true;
		}
		if (r instanceof ISeq) {
			ISeq seq = (ISeq) r;
			for (ISexpr v : seq.sexprs()) {
				if (v instanceof ISeq) {
					ISeq vseq = (ISeq) v;
					if (vseq.sexprs().get(1).toString().startsWith("t")) {
						int tid = Integer.parseInt( vseq.sexprs().get(1).toString().substring(1) );
						int value ;
						try { value = (int) Float.parseFloat( vseq.sexprs().get(vseq.sexprs().size()-1).toString() ); }
						catch (NumberFormatException e) { 
							hasReals = true;
							value = 1; 
						}
						if (value != 0) 
							parikh.put(tid, value);
					} else if (vseq.sexprs().get(1).toString().startsWith("s")) {
						int tid = Integer.parseInt( vseq.sexprs().get(1).toString().substring(1) );
						int value;
						try { value = (int) Float.parseFloat( vseq.sexprs().get(vseq.sexprs().size()-1).toString() ); }
						catch (NumberFormatException e) { 
							hasReals = true;
							value = 1; 
						}
						if (value != 0) 
							state.put(tid, value);							
					} else if (vseq.sexprs().get(1).toString().startsWith("o")) {
						int tid = Integer.parseInt( vseq.sexprs().get(1).toString().substring(1) );
						int value;
						try {
							String s = vseq.sexprs().get(vseq.sexprs().size()-1).toString();
							s = s.replaceAll("[() ]", "");
							value = (int) Float.parseFloat( s ); 
						}
						catch (NumberFormatException e) {
							//System.out.println( vseq.sexprs().get(vseq.sexprs().size()-1).toString());
							value = 1; 
						}
						if (value != 0) 
							order.put(tid, value);							
					}
				}
			}
		}
		if (DEBUG >= 1) {
			System.out.println("Read State : " + state);
			System.out.println("Read Parikh : " + parikh);
			System.out.println("Read Order : " + order);
		}
		lastParikh = parikh.clone();
		lastState = state.clone();
		lastOrder = order.clone();
		return hasReals;
	}

	public static List<Integer> testDeadTransitionWithSMT(StructuralReduction sr) {
		List<Integer> deadTrans =new ArrayList<>();
		List<Integer> repr = new ArrayList<>();
		long time = System.currentTimeMillis();
		long orioritime = time;
		ISolver solver = null;
		org.smtlib.SMT smt = new SMT();
		IFactory ef = smt.smtConfig.exprFactory;

		try {
			IntMatrixCol sumMatrix = InvariantCalculator.computeReducedFlow(sr, repr);
			Set<SparseIntArray> invar = InvariantCalculator.computePInvariants(sumMatrix);		

			// using reals currently
			boolean solveWithReals = true;
			solver = initSolver(smt, solveWithReals,40,60);
			{
				// STEP 1 : declare variables
				time = System.currentTimeMillis();
				Script varScript = declareVariables(sr.getPnames().size(), "s", sr.isSafe(), smt,solveWithReals);
				execAndCheckResult(varScript, solver);			
			}

			// STEP 2 : declare and assert invariants 
			String textReply = assertInvariants(invar, sr, solver, smt,false, solveWithReals);

			// are we finished ?
			if (false && "sat".equals(textReply) && sr.getTnames().size() <= 3000) {
				// STEP 3 : go heavy, use the state equation to refine our solution
				time = System.currentTimeMillis();
				Script script = declareStateEquation(sumMatrix, sr.getMarks(), smt,solveWithReals, new HashSet<>());

				execAndCheckResult(script, solver);
				textReply = checkSat(solver,  false);
				if (textReply.equals("sat")) {
					Script readfeed = addReadFeedConstraints(sr, sumMatrix, repr);
					execAndCheckResult(readfeed, solver);
					textReply = checkSat(solver,  false);
				}
				//Logger.getLogger("fr.lip6.move.gal").info("Implicit Places using state equation in "+ (System.currentTimeMillis()-time) +" ms returned " + textReply);
			}

			time = System.currentTimeMillis();
			long oritime = time;
			for (int tid =0, sz=sr.getTnames().size() ; tid < sz ; tid++ ) {
				// assert t enabled
				Script pimplicit = new Script();
				SparseIntArray pre = sr.getFlowPT().getColumn(tid);
				List<IExpr> cond = new ArrayList<>();
				for (int i=0 ; i < pre.size() ; i++ ) {
					cond.add(ef.fcn(ef.symbol(">="), ef.symbol("s"+pre.keyAt(i)), ef.numeral(pre.valueAt(i))));
				}
				if (cond.isEmpty()) {
					continue;
				}
				IExpr disabled = SMTUtils.makeAnd(cond);
				pimplicit.add(new C_assert(disabled));
				IResponse res = solver.push(1);
				if (res.isError()) {
					break;
				}
				execAndCheckResult(pimplicit, solver);

				textReply = checkSat(solver,  false);

				// are we finished ?
				if (textReply.equals("unsat")) {
					Logger.getLogger("fr.lip6.move.gal").fine("Transition "+sr.getTnames().get(tid) + " with index "+tid+ " is dead.");
					deadTrans.add(tid);
				}

				res = solver.pop(1);
				if (res.isError()) {
					break;
				}
				Logger.getLogger("fr.lip6.move.gal").fine("Test for dead trans "+sr.getTnames().get(tid) + " with index "+tid+ " gave us " + textReply + " in " + (System.currentTimeMillis()-time) +" ms");
				long deltat = System.currentTimeMillis() - time;
				if (deltat >= 30000) {
					time = System.currentTimeMillis();
					Logger.getLogger("fr.lip6.move.gal").info("Performed "+tid +"/"+ sz + " 'is it Dead' test of which " + deadTrans.size() + " returned DEAD in " + (time -oritime)/1000 + " seconds." );				
					if (time - oritime > 120000 && deadTrans.isEmpty()) {
						Logger.getLogger("fr.lip6.move.gal").info("Timeout of Dead Transitions test with SMT after "+ (time -oritime)/1000 + " seconds.");
						break;
					}
				}
			}
			Logger.getLogger("fr.lip6.move.gal").info("Dead Transitions using invariants and state equation in "+ (System.currentTimeMillis()-orioritime) +" ms found " + deadTrans.size() + " transitions.");


		} catch (Exception e) {
			Logger.getLogger("fr.lip6.move.gal").info("Dead Transitions with SMT raised an exception" + e.getMessage() + " after "+ (System.currentTimeMillis()-orioritime) +" ms ");			
		} finally {
			if (solver != null) 
				solver.exit();
		}

		return deadTrans;
	}

	public static List<Integer> testImplicitTransitionWithSMT(StructuralReduction sr) {
		long time = System.currentTimeMillis();		
		org.smtlib.SMT smt = new SMT();
		List<Integer> redundantTrans =new ArrayList<>();
		if (sr.getTnames().size() >= 10000) {
			// refuse a 10^8 complexity scan
			return redundantTrans;
		}
		ISolver solver = null;
		try {
			// using integers currently
			boolean solveWithReals = false;
			solver = initSolver(smt, solveWithReals,25,200);

			time = System.currentTimeMillis();		
			// now for each transition
			for (int tid = 0, ntrans = sr.getTnames().size(); tid < ntrans; tid++) {
				SparseIntArray tiPT = sr.getFlowPT().getColumn(tid);
				SparseIntArray tiTP = sr.getFlowTP().getColumn(tid);
				List<Integer> candidates = new ArrayList<>();
				// for each transition t_j whose support is a subset of t_i's support			
				for (int tjd=0; tjd < ntrans ; tjd++) {
					// i==j case : skip
					if (tid == tjd)
						continue;
					SparseIntArray tjPT = sr.getFlowPT().getColumn(tjd);
					SparseIntArray tjTP = sr.getFlowTP().getColumn(tjd);
					if (SparseIntArray.greaterOrEqual(tiPT, tjPT) && SparseIntArray.greaterOrEqual(tiTP, tjTP)) {
						candidates.add(tjd);
					}
				}

				if (candidates.size() > 1) {
					IResponse res = solver.push(1);
					if (res.isError()) {
						break;
					}
					// declare an alpha_j
					Script varScript = declareVariables(candidates.size(), "t", false, smt,solveWithReals);
					execAndCheckResult(varScript, solver);

					Script script = new Script();
					Set<Integer> support = new TreeSet<>();
					for (int i=0; i < tiPT.size() ; i++) {
						support.add(tiPT.keyAt(i));
					}
					for (int i=0; i < tiTP.size() ; i++) {
						support.add(tiTP.keyAt(i));
					}

					//				System.out.println("Testing transition "+sr.getTnames().get(tid) + " against " + candidates.stream().map(n -> sr.getTnames().get(n)).collect(Collectors.toList()));
					//				System.out.println(sr.getTnames().get(tid) + " :" + sr.getFlowPT().getColumn(tid) + " -> " + sr.getFlowTP().getColumn(tid));
					//				System.out.println(" vs . ");
					//				for (int ttid : candidates) {
					//					System.out.println(sr.getTnames().get(ttid) + " :" + sr.getFlowPT().getColumn(ttid) + " -> " + sr.getFlowTP().getColumn(ttid));
					//					
					//				}

					IFactory ef = smt.smtConfig.exprFactory;
					for (int p : support) {
						// assert equality of effects
						// - pre (p,ti) + post(p,ti) = Sum_j alpha_j * ( - pre(p,tj) + post(p,tj) )
						int prei = tiPT.get(p);
						int vali = - prei + tiTP.get(p);
						List<IExpr> toadd = new ArrayList<>();
						List<IExpr> torem = new ArrayList<>();

						List<IExpr> prePT = new ArrayList<>();

						for (int cand =0 ; cand < candidates.size() ; cand++) {
							int tjd = candidates.get(cand);
							SparseIntArray tjPT = sr.getFlowPT().getColumn(tjd);
							SparseIntArray tjTP = sr.getFlowTP().getColumn(tjd);
							int prej = tjPT.get(p);
							int valj = - prej + tjTP.get(p);
							if (valj != 0) {
								IExpr ss = ef.symbol("t"+cand);
								if (valj != 1 && valj != -1) {
									ss = ef.fcn(ef.symbol("*"), ef.numeral( Math.abs(valj)), ss );
								} 
								if (valj > 0) 
									toadd.add(ss);
								else
									torem.add(ss);
							}
							if (prej != 0) {
								IExpr ss = ef.symbol("t"+cand);
								if (prej != 1) {
									ss = ef.fcn(ef.symbol("*"), ef.numeral( Math.abs(prej)), ss );
								} 
								prePT.add(ss);
							}
						}
						// effect of ti = effect of ponderated sum of tj
						// vali + torem = toadd
						if (vali < 0) {
							toadd.add(ef.numeral(-vali));
						} else {
							torem.add(ef.numeral(vali));
						}
						IExpr lhs = buildSum(torem);
						IExpr rhs = buildSum(toadd);

						script.add(new C_assert(ef.fcn(ef.symbol("="), lhs, rhs)));


						script.add(new C_assert(ef.fcn(ef.symbol(">="), ef.numeral(prei), buildSum(prePT))));
					}
					execAndCheckResult(script, solver);
					String textReply = checkSat(solver,  false);


					// are we finished ?
					if (textReply.equals("sat")) {
						Logger.getLogger("fr.lip6.move.gal").fine("Transition "+sr.getTnames().get(tid) + " with index "+tid+ " is redundant.");
						redundantTrans.add(tid);
					}

					res = solver.pop(1);
					if (res.isError()) {
						break;
					}
					Logger.getLogger("fr.lip6.move.gal").fine("Trans "+sr.getTnames().get(tid) + " with index "+tid+ " gave us " + textReply + " in " + (System.currentTimeMillis()-time) +" ms");
				}
			}

			Logger.getLogger("fr.lip6.move.gal").info("Redundant transitions in "+ (System.currentTimeMillis()-time) +" ms returned " + redundantTrans);

		} catch (Exception e) {
			Logger.getLogger("fr.lip6.move.gal").warning("SMT solver raised an exception "+ e.getMessage() + " returning currently detected " + redundantTrans.size() + " redundant transitions ");			
		} finally {
			if (solver != null) 
				solver.exit();
		}
		
		return redundantTrans;
	}

	// computes a list of integers corresponding to a subset of places, of which at least one should be marked, and that contradicts the solution provided
	// the empty set => traps cannot contradict the solution.
	public static List<Integer> testTrapWithSMT(ISparsePetriNet srori, SparseIntArray solution) {
		long time = System.currentTimeMillis();
		// step 0 : make sure there are finally empty places that were initially marked
		boolean feasible = false;
		for (int p=0, e= srori.getPnames().size(); p < e ; p++) {
			if (srori.getMarks().get(p) > 0 && solution.get(p) == 0) {
				feasible = true;
				break;
			}
		}
		if (!feasible) {
			return new ArrayList<>();
		}
		
		StructuralReduction sr = new StructuralReduction(srori);			
		
		// step 1 : reduce net by removing finally marked places entirely from the picture
		{
			List<Integer> todrop = new ArrayList<>(solution.size());

			for (int i=solution.size()-1 ; i >= 0 ; i --) {
				todrop.add(solution.keyAt(i));
			}
			sr.dropPlaces(todrop, false, false,"");
		}
		// iterate reduction of unfeasible parts
		{
			int doneIter =0;
			do {
				doneIter =0;
				Set<Integer> todropP = new TreeSet<>();
				Set<Integer> todropT = new TreeSet<>();

				for (int tid=sr.getTnames().size()-1 ; tid >= 0 ; tid --) {
					if (sr.getFlowPT().getColumn(tid).size()==0) {
						// discard this transition, it cannot induce any additional constraints
						todropT.add(tid);
						doneIter++;
					} else if (sr.getFlowTP().getColumn(tid).size()==0) {
						SparseIntArray pt = sr.getFlowPT().getColumn(tid);
						// discard the transition, but also it's whole pre set
						for (int i=0, e = pt.size() ; i < e ; i++) {
							todropP.add(pt.keyAt(i));							
						}
						doneIter++;
						todropT.add(tid);
					}
				}
				if (!todropT.isEmpty()) {
					sr.dropTransitions(new ArrayList<>(todropT), false,"");
				}
				if (!todropP.isEmpty()) {
					sr.dropPlaces(new ArrayList<>(todropP), false, false,"");
				}
			} while (doneIter >0);
		}
		
		if (sr.getPnames().isEmpty()) {
			// fail
			return new ArrayList<>();
		}
		{
			boolean ok = false;
			for (int i=0; i < sr.getPnames().size() ; i++) {
				if (sr.getMarks().get(i) >0 && solution.get(i)==0) {
					ok=true;
					break;
				}
			}
			if (!ok)
				return new ArrayList<>();
		}
		if (DEBUG >=1)
			Logger.getLogger("fr.lip6.move.gal").info("Computed a system of "+sr.getPnames().size()+"/"+ srori.getPnames().size() + " places and "+sr.getTnames().size()+"/"+ srori.getTnames().size() + " transitions for Trap test. " + (System.currentTimeMillis()-time) +" ms");
		
		if (! sr.getPnames().isEmpty()) {
			// okay so we have some candidate places that could form a trap here
			
			// init a solver
			SMT smt = new SMT();
			IFactory ef = smt.smtConfig.exprFactory;
			ISolver solver = initSolver(smt, "QF_UF", 50, 120);
			Script script = declareBoolVariables(sr.getPnames().size(), "s", smt);
			execAndCheckResult(script, solver);
			
			// now feed constraints in
			
			// solution should be a non empty set, containing at least one initially marked place that is now empty
			{
				List<IExpr> oring = new ArrayList<>();
				for (int i=0; i < sr.getPnames().size() ; i++) {
					if (sr.getMarks().get(i) >0 && solution.get(i)==0)
						oring.add(ef.symbol("s"+i));
				}
				
				if (oring.isEmpty()) {
					// failed
					solver.exit();
					return new ArrayList<>();
				}
				IExpr or = SMTUtils.makeOr(oring);
				Script s = new Script();
				s.add(new C_assert(or));
				execAndCheckResult(s, solver);
			}
			
			// transition constraints now
			IntMatrixCol tflowPT = sr.getFlowPT().transpose();
			// for each place p
			for (int  pid = 0 ; pid < sr.getPnames().size() ; pid++)  {
				
			
				//   for each transition t feeding from p
				SparseIntArray tpt = tflowPT.getColumn(pid);
				
				Script sc = new Script();

				for (int i=0, e=tpt.size(); i < e ; i++ ) {
					//        one place fed by t is in the set
					int tid = tpt.keyAt(i);
					SparseIntArray outs = sr.getFlowTP().getColumn(tid);
					List<IExpr> toass = new ArrayList<>();
					
					if (outs.size() == 0) {
						// this is bad : this place cannot be in any trap
						// just break out
						sc.commands().clear();
						IExpr constraint = ef.fcn(ef.symbol("not"), ef.symbol("s"+pid));
						sc.add(new C_assert(constraint));
						break;
					} else if (outs.get(pid) > 0) {
						// transition feeds back into p, this constraint is trivially satisfied, just remove it
						continue;
					} else {
						for (int j=0, ee=outs.size() ; j < ee ; j++) {
							// one of these places must be in the trap
							int ppid = outs.keyAt(j);
							toass.add(ef.symbol("s"+ ppid));						
						}
					}
					IExpr or = SMTUtils.makeOr(toass); 
					
					// assert the constraint for this transition
					IExpr constraint = ef.fcn(ef.symbol("=>"), ef.symbol("s"+pid), or);
//					if (toass.isEmpty()) {
//						// this can happen if only transitions take and put in the place
//						constraint = ef.fcn(ef.symbol("not"), ef.symbol("s"+pid));
//					}
					sc.add(new C_assert(constraint));
				}

				execAndCheckResult(sc, solver);
				String res = checkSat(solver);
				if ("unsat".equals(res)) {
					// meh, we (already) cannot build a trap
					solver.exit();
					return new ArrayList<>();
				}
			}
			// looks real good, we have not obtained UNSAT yet
			
			// try to minimize the trap
			long ttime = System.currentTimeMillis();
			List<IExpr> tosum = new ArrayList<>(sr.getPnames().size());
			for (int i=0, e=sr.getPnames().size(); i < e; i++ ) {
				IExpr ss = ef.symbol("s"+i);
				tosum.add(ss);
			}
			solver.minimize(ef.fcn(ef.symbol("+"), tosum));
			checkSat(solver,  false);
			long minitime = (System.currentTimeMillis() - ttime);	
			
			List<Boolean> trap = new ArrayList<>(sr.getPnames().size());
			for (int i=0, e=sr.getPnames().size(); i < e; i++ ) {
				trap.add(false);
			}
			queryBoolVariables(trap,solver);
			List<Integer> res = new ArrayList<>();
			int tsz = 0;
			for (int i=0 ; i < trap.size() ; i++) {
				if (trap.get(i)) {
					res.add(srori.getPnames().indexOf(sr.getPnames().get(i)));
					tsz++;
				}
			}
			solver.exit();
			Logger.getLogger("fr.lip6.move.gal").info("Deduced a trap "+ (DEBUG>=1 ? res : "")+"composed of "+tsz+" places in "+ (System.currentTimeMillis()-time) +" ms of which "+minitime+" ms to minimize.");
			return res;
		}
		
		return new ArrayList<>();
	}
		
	public static List<Integer> testImplicitWithSMT(StructuralReduction sr, boolean withStateEquation) {
		List<Integer> implicitPlaces =new ArrayList<>();
		List<Integer> repr = new ArrayList<>();
		IntMatrixCol tFlowPT = null;
		long time = System.currentTimeMillis();
		long orioritime = time;
		ISolver solver = null;
		try {
			IntMatrixCol sumMatrix = InvariantCalculator.computeReducedFlow(sr, repr);
			Set<SparseIntArray> invar = InvariantCalculator.computePInvariants(sumMatrix);		
			org.smtlib.SMT smt = new SMT();

			// using reals currently
			boolean solveWithReals = true;
			solver = initSolver(smt, solveWithReals,40,160);
			{
				// STEP 1 : declare variables
				time = System.currentTimeMillis();
				Script varScript = declareVariables(sr.getPnames().size(), "s", sr.isSafe(), smt,solveWithReals);
				execAndCheckResult(varScript, solver);			
			}

			// STEP 2 : declare and assert invariants 
			String textReply = assertInvariants(invar, sr, solver, smt,false,solveWithReals);

			// are we finished ?
			if ("sat".equals(textReply) && withStateEquation) {
				// STEP 3 : go heavy, use the state equation to refine our solution
				time = System.currentTimeMillis();
				Script script = declareStateEquation(sumMatrix, sr.getMarks(), smt,solveWithReals, new HashSet<>());

				execAndCheckResult(script, solver);
				textReply = checkSat(solver,  false);
				if (textReply.equals("sat")) {
					Script readfeed = addReadFeedConstraints(sr, sumMatrix, repr);
					execAndCheckResult(readfeed, solver);
					textReply = checkSat(solver,  false);
				}
				//Logger.getLogger("fr.lip6.move.gal").info("Implicit Places using state equation in "+ (System.currentTimeMillis()-time) +" ms returned " + textReply);
			}

			time = System.currentTimeMillis();
			long oritime = time;
			tFlowPT = sr.getFlowPT().transpose();

			for (int placeid = 0, sz = sr.getPnames().size(); placeid < sz; placeid++) {
				if (sr.computeSupport().get(placeid)) {
					continue;
				}
				
				// assert implicit
				Script pimplicit = assertPimplict (placeid,tFlowPT,sr,smt);
				if (pimplicit.commands().isEmpty()) {
					continue;
				}
				IResponse res = solver.push(1);
				if (res.isError()) {
					break;
				}
				execAndCheckResult(pimplicit, solver);

				textReply = checkSat(solver,  false);				

				res = solver.pop(1);
				if (res.isError()) {
					break;
				}
				// are we finished ?
				if (textReply.equals("unsat")) {
					Logger.getLogger("fr.lip6.move.gal").fine("Place "+sr.getPnames().get(placeid) + " with index "+placeid+ " seems implicit.");
					
					// make sure all transitions are indeed fireable
					boolean isTrueImplicit = true;
					IFactory ef = smt.smtConfig.exprFactory;
					// for each transition that takes from P				
					SparseIntArray eatP = tFlowPT.getColumn(placeid);
					List<IExpr> orConds = new ArrayList<>();
					for (int i=0; i < eatP.size() ; i++) {
						
						int tid = eatP.keyAt(i);
						
						Script s = buildTenabledExcept(sr, placeid, tid, ef);
						res = solver.push(1);
						if (res.isError()) {
							break;
						}
						execAndCheckResult(s, solver);

						textReply = checkSat(solver,  false);				

						res = solver.pop(1);
						if (res.isError()) {
							break;
						}
						if ("unsat".equals(textReply)) {
							isTrueImplicit = false;
							break;
						}						
					}
					if (isTrueImplicit)
						implicitPlaces.add(placeid);
					else 
						Logger.getLogger("fr.lip6.move.gal").fine("Place "+sr.getPnames().get(placeid) + " with index "+placeid+ " was not truly implicit " + (System.currentTimeMillis()-time) +" ms");
							
				}
				
				Logger.getLogger("fr.lip6.move.gal").fine("Place "+sr.getPnames().get(placeid) + " with index "+placeid+ " gave us " + textReply + " in " + (System.currentTimeMillis()-time) +" ms");
				long deltat = System.currentTimeMillis() - time;
				if (deltat >= 30000) {
					time = System.currentTimeMillis();
					Logger.getLogger("fr.lip6.move.gal").info("Performed "+placeid +"/"+ sz + " implicitness test of which " + implicitPlaces.size() + " returned IMPLICIT in " + (time -oritime)/1000 + " seconds." );				
					if (time - oritime > 120000 && implicitPlaces.isEmpty()) {
						Logger.getLogger("fr.lip6.move.gal").info("Timeout of Implicit test with SMT after "+ (time -oritime)/1000 + " seconds.");
						break;
					}
				}
			}
			Logger.getLogger("fr.lip6.move.gal").info("Implicit Places using invariants "+ (withStateEquation?"and state equation ":"")+ "in "+ (System.currentTimeMillis()-orioritime) +" ms returned " + implicitPlaces);

			
		} catch (Exception e) {
			Logger.getLogger("fr.lip6.move.gal").info("Implicit Places with SMT raised an exception" + e.getMessage() + " after "+ (System.currentTimeMillis()-orioritime) +" ms ");			
		} finally {
			if (solver != null) 
				solver.exit();
		}
		
		if (implicitPlaces.isEmpty()) {
			return Collections.emptyList();
		}
		
		if (tFlowPT == null) {
			tFlowPT = sr.getFlowPT().transpose();
		}
		
		// Debug code : pretty print net and all it's implicit places.
//		if (true) {
//			BitSet old = sr.getUntouchable();
//			BitSet b = new BitSet();
//			for (int i : implicitPlaces)
//				b.set(i);
//			sr.setProtected(b);
//			FlowPrinter.drawNet(sr);
//			sr.setProtected(old);
//		}
		
		List<Integer> realImplicit = new ArrayList<Integer>();
		//Collections.sort(implicitPlaces, (a,b) -> -sr.getPnames().get(a).compareTo(sr.getPnames().get(b)));
		// so that this variable is effectively final for lambda capture
		IntMatrixCol tfPT = tFlowPT;
		Collections.sort(implicitPlaces, (a,b) -> -Integer.compare(tfPT.getColumn(a).size(),tfPT.getColumn(b).size()));
		for (int i=0; i < implicitPlaces.size() ; i++) {
			int pi = implicitPlaces.get(i);
			SparseIntArray piPT = tFlowPT.getColumn(pi);
			boolean isOk = true;
			// make sure that the outputs of this place will not lose more than one condition
			for (int j=0; j < piPT.size() ; j++) {
				int tid = piPT.keyAt(j);
				SparseIntArray pret = sr.getFlowPT().getColumn(tid);
				for (int k=0; k < pret.size() ; k++) {
					int pp = pret.keyAt(k);
					if (pp!=pi && realImplicit.contains(pp)) {
						isOk = false;
						break;
					}
				}
			}
			if (isOk) {
				realImplicit.add(pi);
			}
//			// make sure that the outputs of this place will still have at least one condition
//			for (int j=0; j < piPT.size() ; j++) {
//				int tid = piPT.keyAt(j);
//				SparseIntArray pret = sr.getFlowPT().getColumn(tid);
//				boolean otherImplicit = false;
//				for (int k=0; k < pret.size() ; k++) {
//					int pp = pret.keyAt(k);
//					if (realImplicit.contains(pp)) {
//						otherImplicit = true;
//						break;
//					}
//				}
//				if (otherImplicit) {
//					isOk = false;
//					break;
//				}
//			}
//			if (isOk) {
//				realImplicit.add(pi);
//			}
		}
		if (realImplicit.size() < implicitPlaces.size()) {
			Logger.getLogger("fr.lip6.move.gal").info("Actually due to overlaps returned " + realImplicit);
		}
		Collections.sort(realImplicit);
		return realImplicit;
	}


	private static Script buildTenabledExcept(StructuralReduction sr, int placeid, int tid, IFactory ef) {
		Script tenabled = new Script();
		
		// assert that "t is enabled, disregarding the fact it needs P marked with >= value"
		SparseIntArray preT = sr.getFlowPT().getColumn(tid);
		List<IExpr> conds = new ArrayList<>();
		for (int j=0; j < preT.size() ; j++) {
			int pfrom = preT.keyAt(j);
			int pval = preT.valueAt(j);
			if (pfrom == placeid) {
				continue;
			}
			// M(pfrom) >= pval
			conds.add(
					ef.fcn(ef.symbol(">="), 
							ef.symbol("s"+pfrom),
							// >= pval
							ef.numeral(pval)));
		}
		
		if (conds.isEmpty()) {
			// p controls this output fully, it is *not* implicit
			throw new RuntimeException("Should not happen, transition should have had other inputs.");
		}						
		// build up the full AND of constraints
		IExpr tenab = SMTUtils.makeAnd(conds);
		
		tenabled.add(new C_assert(tenab));
		return tenabled;
	}

	private static Script assertPimplict(int placeid, IntMatrixCol tFlowPT, StructuralReduction sr, SMT smt) {
		
		IFactory ef = smt.smtConfig.exprFactory;
		// for each transition that takes from P				
		SparseIntArray eatP = tFlowPT.getColumn(placeid);
		List<IExpr> orConds = new ArrayList<>();
		for (int i=0; i < eatP.size() ; i++) {
			int tid = eatP.keyAt(i);
			int value = eatP.valueAt(i);
			
			// assert that "t is enabled, disregarding the fact it needs P marked with >= value"
			SparseIntArray preT = sr.getFlowPT().getColumn(tid);
			List<IExpr> conds = new ArrayList<>();
			for (int j=0; j < preT.size() ; j++) {
				int pfrom = preT.keyAt(j);
				int pval = preT.valueAt(j);
				if (pfrom == placeid) {
					continue;
				}
				// M(pfrom) >= pval
				conds.add(
						ef.fcn(ef.symbol(">="), 
								ef.symbol("s"+pfrom),
								// >= pval
								ef.numeral(pval)));
			}
			
			if (conds.isEmpty()) {
				// p controls this output fully, it is *not* implicit
				return new Script();
			}
			// P is not marked enough to enable T
			IExpr notMarked = ef.fcn(ef.symbol("<"), 
					ef.symbol("s"+placeid),
					// < value
					ef.numeral(value));
			
			conds.add(notMarked);
			// build up the full AND of constraints
			IExpr tenab = SMTUtils.makeAnd(conds);
			orConds.add(tenab);
		}
		
		Script script = new Script();
		if (orConds.isEmpty()) {
			return script;
		} 
		// t is enabled without P => P lacks tokens
		// If this assertion is sat, P is not implicit
		// if we get unsat, P is implicit w.r.t. this transition, it passes one implicitness test.
		script.add(new C_assert(SMTUtils.makeOr(orConds)));

		return script;
	}



	/** Create a script that constrains state variables to satisfy the Petri net state equation.
	 * 
	 * In practice we do not use all transitions, only significant representatives. 
	 * We add a variable for each transition giving it's firing count.
	 * We add an assertion for each place forcing it to satisfy the state equation from M0.
	 * 
	 * @param sumMatrix reduced combined flow matrix as computed in computeReducedFlow()
	 * @param initial TODO
	 * @param smt for solver factories
	 * @param representative 
	 * @param invarT 
	 * @return a Script that contains appropriate declarations and assertions implementing the state equation.
	 */
	private static Script declareStateEquation(IntMatrixCol sumMatrix, List<Integer> initialMarking, org.smtlib.SMT smt, boolean solveWithReals, Set<SparseIntArray> invarT) {
		// declare a set of variables for holding Parikh count of the transition
		Script script = declareVariables(sumMatrix.getColumnCount(), "t", false, smt,solveWithReals);

		IFactory ef = smt.smtConfig.exprFactory;
		
//		if (false)
//		{
//			// add Initially Enabled Constraint
//			Set<Integer> mustSee = new HashSet<>();
//			SparseIntArray initState = new SparseIntArray(sr.getMarks());
//			for (int tid=0; tid < sr.getTnames().size() ; tid++) {
//				if (SparseIntArray.greaterOrEqual(initState, sr.getFlowPT().getColumn(tid))) {
//					mustSee.add(representative.get(tid));
//				}
//			}
//			// The parikh includes at least one initially fireable transition
//			List<IExpr> initEn = new ArrayList<>();
//			for (Integer t : mustSee) {
//				initEn.add(ef.fcn(ef.symbol(">"), ef.symbol("t"+t), ef.numeral(0)));
//			}
//			IExpr initEnpred = SMTUtils.makeOr(initEn);
//			
//			// the Parikh vector is empty 
//			List<IExpr> all0 = new ArrayList<>();
//			for (int t=0 ; t < sumMatrix.getColumnCount() ; t++) {
//				all0.add(ef.fcn(ef.symbol("="), ef.symbol("t"+t), ef.numeral(0)));
//			}
//			IExpr all0pred = SMTUtils.makeAnd(all0);
//			
//			script.add(new C_assert(ef.fcn(ef.symbol("or"), initEnpred, all0pred)));
//		}
		
		if (invarT != null) {
			// t invariant constraints
			for (SparseIntArray invt : invarT) {
				List<IExpr> perT = new ArrayList<>();
				for (int i=0,ie=invt.size();i<ie;i++) {
					perT.add(ef.fcn(ef.symbol(">="), ef.symbol("t"+invt.keyAt(i)), ef.numeral(invt.valueAt(i))));
				}
				script.add(new C_assert(ef.fcn(ef.symbol("not"), SMTUtils.makeAnd(perT))));			
			}
			if (! invarT.isEmpty()) {
				script.add(new C_check_sat());
				Logger.getLogger("fr.lip6.move.gal").info("Added " + invarT.size() + " T-invariant constraints");
			}
		}
		
		addStateEquation(sumMatrix, initialMarking, script, ef);
				
		return script;
	
	}


	public static void addStateEquation(IntMatrixCol sumMatrix, List<Integer> initialMarking, Script script,
			IFactory ef) {
		// we work with one constraint for each place => use transposed
		IntMatrixCol mat = sumMatrix.transpose();
		for (int varindex = 0 ; varindex < mat.getColumnCount() ; varindex++) {

			SparseIntArray line = mat.getColumn(varindex);
			
			IExpr constraint = ef.fcn(ef.symbol("+"), ef.numeral(initialMarking.get(varindex)), buildRowConstraint(line, ef));
						
			script.add(
					new C_assert(
							ef.fcn(ef.symbol("="), 
									ef.symbol("s"+varindex),
									// = m0.x + X0*C(t0,x) + ...+ XN*C(Tn,x)
									constraint)));
			if (varindex % 3 == 0) {
				script.add(new C_check_sat());
			}
		}
	}


	public static Script addReadFeedConstraints(ISparsePetriNet sr, IntMatrixCol sumMatrix, List<Integer> representative) {
		Script script = new Script();
		IFactory ef = new SMT().smtConfig.exprFactory;				 
		int readConstraints = 0;
		IntMatrixCol tsum = sumMatrix.transpose();
		Map<Integer,List<Integer>> images = computeImages(representative);
		
		// now add read constraint : any transition reading from an initially unmarked place => p must be fed at some point			
		for (int tid=0; tid < sumMatrix.getColumnCount() ; tid++) {
			List<IExpr> perImage = new ArrayList<>();
			for (int img : images.get(tid)) {
				SparseIntArray pt = sr.getFlowPT().getColumn(img);
				SparseIntArray tp = sr.getFlowTP().getColumn(img);
				
				// constraints on places that we consume from
				List<IExpr> prePlace = new ArrayList<>();
				for (int i = 0; i < pt.size() ; i++) {
					int p = pt.keyAt(i);
					int v = pt.valueAt(i);
					if (v > sr.getMarks().get(p) && tp.get(p) > 0) {
						// we need at least delta more tokens in p
						int delta = v - sr.getMarks().get(p);
						List<IExpr> couldFeed = new ArrayList<>();
						// find a feeder for p
						SparseIntArray feeders = tsum.getColumn(p);
						for (int j=0; j < feeders.size() ; j++) {
							int t2 = feeders.keyAt(j);
							int v2 = feeders.valueAt(j);
							if ( v2 > 0 
									&& (t2 != tid || images.get(tid).stream().anyMatch(t -> sr.getFlowPT().getColumn(t).get(p)<v)) 
								) {								
								// true feed effect
								
								// basic >0 readFeed constraint from PN20
								// couldFeed.add(ef.fcn(ef.symbol(">"), ef.symbol("t"+t2), ef.numeral(0)));

								// new refined test counting tokens
								// count how many tokens are contributed per firing
								if (v2 != 1) {
									couldFeed.add(ef.fcn(ef.symbol("*"), ef.symbol("t"+t2), ef.numeral(v2)));
								} else {
									couldFeed.add(ef.symbol("t"+t2));
								}
							}
						}
						// sum it 
						prePlace.add(ef.fcn(ef.symbol(">="), SMTUtils.buildSum(couldFeed), ef.numeral(delta)));						
					}					
				}
				if (!prePlace.isEmpty()) {
					perImage.add(SMTUtils.makeAnd(prePlace));
				} else {
					// pure transition with no read behavior exists for this transition
					perImage.clear();
					break;
				}
			}
			if (!perImage.isEmpty()) {
				IExpr causal = ef.fcn(ef.symbol("=>"), ef.fcn(ef.symbol(">="), ef.symbol("t"+tid), ef.numeral(1)), SMTUtils.makeOr(perImage)); 
				script.add(new C_assert(causal));
				readConstraints ++;
			}
		}

		if (readConstraints > 0)
			Logger.getLogger("fr.lip6.move.gal").info("State equation strengthened by "+ readConstraints + " read => feed constraints.");
		
		return script;
	}


	/**
	 * Feeds the invariants to the solver as a set of constraints over the state variables.
	 * The feeding is done in two steps, first only semi flows, then the full generalized flows.
	 * 
	 * The goal is to get "unsat" result, so more constraints means more likely to be unsat, 
	 * but it's not clear that we need all constraints to be unsat (the contradiction could come early).
	 * We declare invariants in parts, if we get "unsat" return it immediately, otherwise add more invariants,
	 * check sat again and return the solver's response whether "sat" or "unsat". 
	 * 
	 * @param invar the invariants to declare, as obtained from the InvariantCalculator module.
	 * @param sr the Petri net
	 * @param solver we expect the solver to already know about variables
	 * @param smt access to smt factories
	 * @param solveWithReals 
	 * @return "unsat" is what we hope for, could also return "sat" and maybe "unknown". 
	 */
	private static String assertInvariants(Set<SparseIntArray> invar, ISparsePetriNet sr, ISolver solver,
			org.smtlib.SMT smt, boolean verbose, boolean solveWithReals) {

		long time = System.currentTimeMillis();
		Script invpos = new Script();
		Script invneg = new Script();
		int poscount = declareInvariants(invar,sr,invpos,invneg,smt);

		String textReply = "sat";
		// add the positive only for now
		if (!invpos.commands().isEmpty()) {
			execAndCheckResult(invpos, solver);		
			textReply = checkSat(solver,  false);
			if (verbose) Logger.getLogger("fr.lip6.move.gal").info((solveWithReals ? "[Real]":"[Nat]")+ "Absence check using  "+poscount+" positive place invariants in "+ (System.currentTimeMillis()-time) +" ms returned " + textReply);
		}

		if (textReply.equals("sat") && ! invneg.commands().isEmpty()) {
			time = System.currentTimeMillis();
			execAndCheckResult(invneg, solver);
			textReply = checkSat(solver,  true);
			if (verbose)  Logger.getLogger("fr.lip6.move.gal").info((solveWithReals ? "[Real]":"[Nat]")+"Absence check using  "+poscount+" positive and " + (invar.size() - poscount) +" generalized place invariants in "+ (System.currentTimeMillis()-time) +" ms returned " + textReply);
		}
		return textReply;
	}

	/**
	 * Declares the invariants represented by invar, in two scripts according to whether they are pure positive 
	 * (semi flows) or general flows.
	 * @param invar the invariants we need to build constraints for
	 * @param sr the Petri net
	 * @param invpos positive invariants asserted here
	 * @param invneg general invariants asserted here
	 * @param smt solver access
	 * @return number of positive flows
	 */
	private static int declareInvariants(Set<SparseIntArray> invar, ISparsePetriNet sr, Script invpos,
			Script invneg, SMT smt) {
		int posinv = 0;
		// splitting posneg from pure positive
		IFactory efactory = smt.smtConfig.exprFactory;
		for (SparseIntArray invariant : invar) {
			boolean hasNeg = false;
			for (int i=0; i < invariant.size() ; i++) {
				if (invariant.valueAt(i) < 0) {
					hasNeg = true;
					break;
				}
			}			
			if (! hasNeg) {
				posinv ++;
				addInvariant(sr, efactory, invpos, invariant);
				if (invpos.commands().size() %5 == 0) {
					invpos.add(new C_check_sat());
				}
			} else {
				addInvariant(sr, efactory, invneg, invariant);
				if (invneg.commands().size() %5 == 0) {
					invneg.add(new C_check_sat());
				}
			}
		}
		return posinv;
	}


	/**
	 * Builds a script that tests for deadlocks.
	 * i.e. that no transition is enabled.
	 * Algorithm consists in 
	 * AND over all transitions t 
	 *   OR of any input place not being marked enough to fire t.
	 *   
	 * We avoid having duplicate conditions asserted, but there is no implication test currently.
	 * @param sr the net we work with
	 * @param smt solver access
	 * @return a script which asserts that the system is deadlocked.
	 */
	private static Script assertNetIsDead(ISparsePetriNet sr) {
		Script scriptAssertDead = new Script();
		// deliberate block to help gc.
		{			
			IFactory ef = new SMT().smtConfig.exprFactory;
			Set<SparseIntArray> preconds = new HashSet<>();
			for (int i = 0; i < sr.getFlowPT().getColumnCount() ; i++)
				preconds.add(sr.getFlowPT().getColumn(i));
			for (SparseIntArray arr : preconds) {
				IExpr res = buildDisabled(ef, arr);
				// add that t is not fireable
				scriptAssertDead.add(new C_assert(res));
				if (scriptAssertDead.commands().size() % 10 == 0) {
					scriptAssertDead.add(new C_check_sat());
				}
			}			
		}
		return scriptAssertDead;
	}


	public static IExpr buildDisabled(IFactory ef, SparseIntArray flowPT) {
		List<IExpr> conds = new ArrayList<>();
		// one of the preconditions of the transition is not marked enough to fire
		for (int  i=0; i < flowPT.size() ; i++) {
			// use these bounds "<= (arc value - 1)" that are more precise than "< arc value" for Reals
			conds.add( ef.fcn(ef.symbol("<="), ef.symbol("s"+flowPT.keyAt(i)), ef.numeral(flowPT.valueAt(i)-1)));
		}
		// any of these is true => t is not fireable								
		IExpr res = SMTUtils.makeOr(conds);
		return res;
	}


	private static void addInvariant(ISparsePetriNet sr, IFactory efactory, Script script,
			SparseIntArray invariant) {
		long sum = 0;
		// assert : cte = m0 * x0 + ... + m_n*x_n
		// build sum up
		List<IExpr> toadd = new ArrayList<>();
		List<IExpr> torem = new ArrayList<>();
		try {
			for (int i = 0 ; i < invariant.size() ; i++) {
				int v = invariant.keyAt(i);
				int val = invariant.valueAt(i);
				IExpr ss = efactory.symbol("s"+v);
				if (val != 1 && val != -1) {
					ss = efactory.fcn(efactory.symbol("*"), efactory.numeral( Math.abs(val)), ss );
				}
				if (val > 0) 
					toadd.add(ss);
				else
					torem.add(ss);
				sum  = Math.addExact(sum, Math.multiplyExact((long)val, sr.getMarks().get(v))) ; 
			}
		} catch (ArithmeticException e) {
			System.err.println("Invariant declaration overflow for the constant !");
			return;
		}
		if (sum < 0) {
			toadd.add(efactory.numeral(-sum));
		} else if (sum >0) {
			torem.add(efactory.numeral(sum));
		}
		IExpr sumE ;
		if (toadd.isEmpty()) {
			sumE = efactory.numeral(0);
		} else if (toadd.size() == 1) {
			sumE = toadd.get(0);
		} else {
			sumE = efactory.fcn(efactory.symbol("+"), toadd);
		}

		IExpr sumR  ; 
		if (torem.isEmpty()) {
			sumR = efactory.numeral(0);
		} else if (torem.size() == 1) {
			sumR = torem.get(0);
		} else {
			sumR = efactory.fcn(efactory.symbol("+"), torem);
		}
		
		IExpr invarexpr = efactory.fcn(efactory.symbol("="), sumR, sumE);
		script.add(new C_assert(invarexpr));
	}
		
	public static void testOneSafeWithSMT(List<Expression> toCheck, ISparsePetriNet sr, Set<SparseIntArray> invar, DoneProperties doneProps, int timeout) {
		boolean isSafe = sr.isSafe();
		
		
		
		boolean solveWithReals = true;
		int timeoutQ = 100;
		int timeoutT = 100;
				
		org.smtlib.SMT smt = new SMT();
		IFactory ef = smt.smtConfig.exprFactory;
		ISolver solver = initSolver(smt, solveWithReals,timeoutQ,timeoutT);		
		{
			// STEP 1 : declare variables, assert net is dead.
			Script varScript = declareVariables(sr.getPnames().size(), "s", isSafe, smt,solveWithReals);
			execAndCheckResult(varScript, solver);
			String textReply = checkSat(solver);
			// are we finished ?
			if (textReply.equals("unsat")||textReply.equals("unknown")) {
				solver.exit();
				return ;
			}
		}
		
		// STEP 2 : declare and assert invariants 
		long time = System.currentTimeMillis();
		Script invpos = new Script();
		Script invneg = new Script();
		int poscount = declareInvariants(invar,sr,invpos,invneg,smt);

		String textReply = "sat";
		// add the positive only for now
		if (!invpos.commands().isEmpty()) {
			execAndCheckResult(invpos, solver);		
			textReply = checkSat(solver,  false);
		}
		
		int d = clearDone(toCheck, doneProps , ef, solver);
		Logger.getLogger("fr.lip6.move.gal").info((solveWithReals ? "[Real]":"[Nat]")+ "Absence check using  "+poscount+" positive place invariants in "+ (System.currentTimeMillis()-time) +" ms proved " + d + " places are One-Safe.");

		if (toCheck.isEmpty()) {
			solver.exit();
			return;
		}
		
		
		if (textReply.equals("sat") && ! invneg.commands().isEmpty()) {
			time = System.currentTimeMillis();
			execAndCheckResult(invneg, solver);
			textReply = checkSat(solver,  true);
			
		}
		
		d= clearDone(toCheck, doneProps , ef, solver);
		Logger.getLogger("fr.lip6.move.gal").info((solveWithReals ? "[Real]":"[Nat]")+"Absence check using  "+poscount+" positive and " + (invar.size() - poscount) +" generalized place invariants in "+ (System.currentTimeMillis()-time) +" ms proved " + d + " places are One-Safe.");
		
		if (toCheck.isEmpty()) {
			solver.exit();
			return;
		}
				
		
		// STEP 3 : go heavy, use the state equation to refine our solution
		time = System.currentTimeMillis();
		IntMatrixCol sumMatrix = InvariantCalculator.computeReducedFlow(sr, new ArrayList<Integer>());
		Script script = declareStateEquation(sumMatrix, sr.getMarks(), smt,solveWithReals, null);

		execAndCheckResult(script, solver);
		
		d=clearDone(toCheck, doneProps , ef, solver);
		Logger.getLogger("fr.lip6.move.gal").info((solveWithReals ? "[Real]":"[Nat]")+" State equation constraints in "+ (System.currentTimeMillis()-time) +" ms proved " + d + " places are One-Safe.");
		
		solver.exit();
		return;
	}


	private static int clearDone(List<Expression> toCheck, DoneProperties doneProps, IFactory ef, ISolver solver) {
		int done = 0;
		for (int i=toCheck.size()-1; i >= 0 ; i-- ) {
			int pid = toCheck.get(i).getValue();
			
			solver.push(1);
			
			IFcnExpr test = ef.fcn(ef.symbol(">"), ef.symbol("s"+pid), ef.numeral(1));
			solver.assertExpr(test);
			
			String res = checkSat(solver);
			
			if ("unsat".equals(res)) {
				toCheck.remove(i);
				done++;
				doneProps.put("place_"+pid, true, "SAT_SMT STRUCTURAL");
			}			
			solver.pop(1);			
		}
		return done;
	}
	
	public static List<SparseIntArray> findStructuralMaxWithSMT(List<Expression> tocheck, List<Integer> maxSeen,
			List<Integer> maxStruct, ISparsePetriNet sr, List<Integer> representative, List<SparseIntArray> orders,
			int timeout,
			boolean withWitness) {
		List<SparseIntArray> verdicts = new ArrayList<>();
		
		IntMatrixCol sumMatrix = InvariantCalculator.computeReducedFlow(sr, representative);

		Set<SparseIntArray> invar = InvariantCalculator.computePInvariants(sumMatrix);		
		//InvariantCalculator.printInvariant(invar, sr.getPnames(), sr.getMarks());
		Set<SparseIntArray> invarT = null; //computeTinvariants(sr, sumMatrix, tnames);
		
		ReadFeedCache rfc = new ReadFeedCache();
		for (int i=0, e=tocheck.size() ; i < e ; i++) {			
			try {
				SparseIntArray parikh = null;
				SparseIntArray por = null;
				if (withWitness) {
					parikh = new SparseIntArray();
					por = new SparseIntArray();
				}
				boolean solveWithReals = true;
				IExpr smtexpr = Expression.op(Op.GEQ, tocheck.get(i), Expression.constant(maxSeen.get(i)+1)).accept(new ExprTranslator());
				Script property = new Script();
				property.add(new C_assert(smtexpr));
				// Add a requirement on solver to please max the value of the expression
				ICommand minmax = new C_minmax(tocheck.get(i).accept(new ExprTranslator()),true);				
				String reply = verifyPossible(sr, property, sumMatrix, invar, invarT, solveWithReals, parikh, por, representative, rfc, 3000,timeout, minmax);
				if ("real".equals(reply)) {
					reply = verifyPossible(sr, property, sumMatrix, invar, invarT, false, parikh, por, representative, rfc, 3000,timeout, minmax);
				}

				if (! "unsat".equals(reply)) {
					if (withWitness) {
						verdicts.add(parikh);
						orders.add(por);
					} else {
						verdicts.add(new SparseIntArray());
						orders.add(new SparseIntArray());
					}
					
					if (DEBUG>=2 && invarT != null) {
						for (SparseIntArray invt: invarT) {
							if (SparseIntArray.greaterOrEqual(parikh, invt)) {
								System.out.println("reducible !");
							}
						}
					}
				} else {
					verdicts.add(null);
					orders.add(null);
					maxStruct.set(i, maxSeen.get(i));
				}
			} catch (RuntimeException re) {
				Logger.getLogger("fr.lip6.move.gal").warning("SMT solver failed with error :" + re + " while checking expression at index " + i);
				re.printStackTrace();
				verdicts.add(new SparseIntArray());
				orders.add(new SparseIntArray());
			}
		}
		
		return verdicts ;
	}





	
}
