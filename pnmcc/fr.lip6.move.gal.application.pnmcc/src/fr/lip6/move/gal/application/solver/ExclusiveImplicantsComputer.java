package fr.lip6.move.gal.application.solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.command.C_assert;
import org.smtlib.impl.Script;

import android.util.SparseIntArray;
import fr.lip6.move.gal.application.mcc.MccTranslator;
import fr.lip6.move.gal.structural.InvariantCalculator;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.expr.AtomicPropRef;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.expr.VarRef;
import fr.lip6.move.gal.structural.smt.DeadlockTester;
import fr.lip6.move.gal.structural.smt.ExprTranslator;
import fr.lip6.move.gal.util.IntMatrixCol;

public class ExclusiveImplicantsComputer {

	private static final String B_IMPLIES_A = "BimpliesA";
	private static final String A_IMPLIES_B = "AimpliesB";
	private static final String A_EXCLUSIVE_B = "AExclusiveB";

	/**
	 * Captures a drain problem : are all transitions in "setT" drain transitions of
	 * "setA" ?
	 */
	private static class DrainProblem {
		private List<SparseIntArray> setsA = new ArrayList<>();
		private List<SparseIntArray> setsT = new ArrayList<>();
		String name;
		Optional<Boolean> result = Optional.empty();

		public DrainProblem(SparseIntArray setA, SparseIntArray setT, String name) {
			this.setsA.add(setA);
			this.setsT.add(setT);
			this.name = name;
		}

		public DrainProblem(String name) {
			this.name = name;
		}

		public void addProblem(SparseIntArray setA, SparseIntArray setT) {
			setsA.add(setA);
			setsT.add(setT);
		}

		public Expression computeDrainProblemCondition(SparsePetriNet spn) {
			// A is not unmarked in M' and in M
			List<Expression> toOr = new ArrayList<>();
			for (int i = 0; i < setsA.size(); i++) {
				SparseIntArray setA = setsA.get(i);
				SparseIntArray setT = setsT.get(i);
					
				Expression totest = computeConstraintForOneSet(spn, setA, setT);
				toOr.add(totest);
			}

			return Expression.nop(Op.OR, toOr);
		}

		public Expression computeConstraintForOneSet(SparsePetriNet spn, SparseIntArray setA, SparseIntArray setT) {
			Expression MpANonEmpty;
			{
				List<Expression> vars = new ArrayList<>();
				for (int i = 0, ie = setA.size(); i < ie; i++) {
					vars.add(Expression.var(setA.keyAt(i)));
				}
				MpANonEmpty = Expression.op(Op.GEQ, Expression.nop(Op.ADD, vars), Expression.constant(1));
			}

			// Now add all the transitions to test
			// If any of them is SAT, we cannot prove our invariant.
			List<Expression> drainExpressions = new ArrayList<>();
			for (int i = 0, ie = setT.size(); i < ie; i++) {
				// for each transition in the set; t must be a drain.
				int tid = setT.keyAt(i);
				// TODO : WIP
				//
				SparseIntArray teffect = SparseIntArray.sumProd(-1, spn.getFlowPT().getColumn(tid), 1,
						spn.getFlowTP().getColumn(tid));
				// false means *predecessor* must satisfy expression
				Expression beforeT = rewriteAfterEffect(MpANonEmpty, teffect, true);

				// T enabling conditions
				Expression tEnabled = Expression.nop(Op.ENABLED, Expression.trans(tid));
				// replace with variable comparisons
				tEnabled = spn.replacePredicates(tEnabled);
				// take a step back : they hold in M
				Expression tEnabledBefore = rewriteAfterEffect(tEnabled, teffect, true);

				// T was feasibly fired last
				Expression tFeasiblyLast;
				{
					List<Expression> conditions = new ArrayList<>();
					SparseIntArray tp = spn.getFlowTP().getColumn(tid);
					for (int j = 0, je = tp.size(); j < je; j++) {
						int p = tp.keyAt(j);
						int v = tp.valueAt(j);

						// M(p) >= post(t)
						conditions.add(Expression.nop(Op.GEQ, Expression.var(p), Expression.constant(v)));
					}
					tFeasiblyLast = Expression.nop(Op.AND, conditions);
				}

				drainExpressions.add(Expression.nop(Op.AND, MpANonEmpty, beforeT, tEnabledBefore, tFeasiblyLast));
			}

			// OR: if we can SAT this problem using any transition in setT, we cannot prove
			// our constraint.
			Expression totest = Expression.nop(Op.OR, drainExpressions);
			return totest;
		}

		@Override
		public String toString() {
			return "DrainProblem [" + ", setsA=" + setsA + ", setsT=" + setsT + ", name=" + name
					+ ", result=" + result + "]";
		}
	}

	public static void studyImplicants(MccTranslator reader) {

		SparsePetriNet spn = reader.getSPN();

		int nbp = spn.getPlaceCount();
		for (int i = 0; i < nbp; i++) {
			for (int j = i + 1; j < nbp; j++) {
				SparseIntArray a = new SparseIntArray();
				SparseIntArray b = new SparseIntArray();
				a.append(i, 1);
				b.append(j, 1);
				testInvariants(a, b, reader);
			}
		}

	}

	private static void testInvariants(SparseIntArray a, SparseIntArray b, MccTranslator reader) {

		SparsePetriNet spn = reader.getSPN();

		boolean matchExclusive = true;
		boolean matchAimpliesB = true;
		boolean matchBimpliesA = true;

		SparseIntArray initial = new SparseIntArray(spn.getMarks());
		{
			// initial conditions.

			// Exclusive : M0(A) <> M0(B) must hold initially
			// Implicant : M0(A) => M0(B) must hold initially

			boolean amarked = SparseIntArray.keysIntersect(a, initial);
			boolean bmarked = SparseIntArray.keysIntersect(b, initial);
			if (amarked && bmarked) {
				matchExclusive = false;
			} else if (amarked && !bmarked) {
				matchAimpliesB = false;
			} else if (!amarked && bmarked) {
				matchBimpliesA = false;
			} else {
				// both a and b are initially empty; no contradiction.
			}
		}

		List<DrainProblem> problems = new ArrayList<>();

		// compute relevant sets
		{
			// .A transitions feeding A
			SparseIntArray feedA = new SparseIntArray();
			// A. transitions consuming in A
			SparseIntArray consA = new SparseIntArray();
			computeFeedCons(spn, a, feedA, consA);

			// .B
			SparseIntArray feedB = new SparseIntArray();
			// B.
			SparseIntArray consB = new SparseIntArray();
			computeFeedCons(spn, b, feedB, consB);

			if (matchExclusive) {
				// .A \ A.
				SparseIntArray feedNotConsA = SparseIntArray.removeAll(feedA, consA);
				// .B \ B.
				SparseIntArray feedNotConsB = SparseIntArray.removeAll(feedB, consB);

				// Test empty intersection : .A \ A. inter .B \ B. = empty set
				if (SparseIntArray.keysIntersect(feedNotConsA, feedNotConsB)) {
					matchExclusive = false;
				} else {

					DrainProblem dp = new DrainProblem(A_EXCLUSIVE_B);
					// add drain problems
					if (feedNotConsB.size() > 0) {
						dp.addProblem(a, feedNotConsB);
					}
					if (feedNotConsA.size() > 0) {
						dp.addProblem(b, feedNotConsA);
					}
					problems.add(dp);
				}
			}

			if (matchAimpliesB) {
				// .A \ A.
				SparseIntArray feedNotConsA = SparseIntArray.removeAll(feedA, consA);

				// .A \ A. must be a subset of .B
				if (!SparseIntArray.containsAllKeys(feedB, feedNotConsA)) {
					matchAimpliesB = false;
				} else {
					// B. \ .B
					SparseIntArray consNotFeedB = SparseIntArray.removeAll(consB, feedB);
					if (consNotFeedB.size() > 0) {
						// must be drain transitions of A
						problems.add(new DrainProblem(a, consNotFeedB, A_IMPLIES_B));
					}
				}
			}

			if (matchBimpliesA) {
				// .B \ B.
				SparseIntArray feedNotConsB = SparseIntArray.removeAll(feedB, consB);

				// .B \ B. must be a subset of .A
				if (!SparseIntArray.containsAllKeys(feedA, feedNotConsB)) {
					matchBimpliesA = false;
				} else {
					// A. \ .A
					SparseIntArray consNotFeedA = SparseIntArray.removeAll(consA, feedA);
					if (consNotFeedA.size() > 0) {
						// must be drain transitions of B
						problems.add(new DrainProblem(b, consNotFeedA, B_IMPLIES_A));
					}
				}
			}
		}

		// This invocation prepares and then runs the SMT solver on the problems
		// result is for each problem either a "witness" state if problem is SAT/unknown/timeout...,
		// or null if the problem is UNSAT.
		List<SparseIntArray> verdicts = solveDrainProblems(spn, problems);

		for (int id = 0, ide = problems.size(); id < ide; id++) {
			DrainProblem dp = problems.get(id);
			if (verdicts.get(id) == null) {
				// "null" reflects an UNSAT result for this problem.
				System.out.println("Problem " + dp.name + " is true. A="+printPnames(a, spn.getPnames())+ " B=" + printPnames(b, spn.getPnames()) + " T=" + printPnames(dp.setsT.get(0), spn.getTnames()) );
			} else {
				// any other reply is a SAT or unknown answer
				System.out.println("Could not prove "+dp.name+ " with A="+printPnames(a, spn.getPnames())+ " B=" + printPnames(b, spn.getPnames()));
				switch (dp.name) {
				case A_EXCLUSIVE_B : matchExclusive = false; break;
				case A_IMPLIES_B : matchAimpliesB = false; break;
				case B_IMPLIES_A : matchBimpliesA = false; break;
				default : throw new IllegalArgumentException("Unknown problem name " + dp.name);
				}				
			}
		}

		if (matchAimpliesB) {
			System.out.println("Proved that : " + printPnames(a, spn.getPnames()) + " => " + printPnames(b, spn.getPnames()));			
		}
		if (matchBimpliesA) {
			System.out.println("Proved that : " + printPnames(b, spn.getPnames()) + " => " + printPnames(a, spn.getPnames()));			
		}			
		if (matchExclusive) {
			System.out.println("Proved that : " + printPnames(b, spn.getPnames()) + " <> " + printPnames(a, spn.getPnames()));
		}
		
	}

	private static List<SparseIntArray> solveDrainProblems(SparsePetriNet spn, List<DrainProblem> problems) {

		int tcsize = problems.size();
		if (problems.size() == 0)
			return Collections.emptyList();
		List<Script> properties = new ArrayList<>();

		for (int probid = 0; probid < tcsize; probid++) {
			DrainProblem dp = problems.get(probid);
			Expression totest = dp.computeDrainProblemCondition(spn);

			IExpr smtexpr = totest.accept(new ExprTranslator());
			Script property = new Script();
			ICommand propAssert = new C_assert(smtexpr);
			property.add(propAssert);
			properties.add(property);
		}

		List<Integer> representative = new ArrayList<>();
		IntMatrixCol sumMatrix = InvariantCalculator.computeReducedFlow(spn, representative);

		List<SparseIntArray> pors = new ArrayList<>();
		int timeout = 30; // in seconds
		return DeadlockTester.escalateRealToInt(spn, properties, timeout,
				false /* no witness needed */, representative, pors, sumMatrix);

	}

	private static String printPnames(SparseIntArray a, List<String> pnames) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		sb.append("{");
		for (int i = 0, ie = a.size(); i < ie; i++) {
			if (first)
				first = false;
			else
				sb.append(", ");			
			sb.append(pnames.get(a.keyAt(i)));
		}
		sb.append("}");
		return sb.toString();
	}

	public static Expression rewriteAfterEffect(Expression expr, SparseIntArray t, boolean before) {
		if (expr == null) {
			return null;
		} else if (expr instanceof VarRef) {
			VarRef vref = (VarRef) expr;
			int delta = t.get(vref.getValue());
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
			return rewriteAfterEffect(apr.getAp().getExpression(), t, before);
		} else {
			List<Expression> resc = new ArrayList<>();
			boolean changed = false;
			for (int i = 0, ie = expr.nbChildren(); i < ie; i++) {
				Expression child = expr.childAt(i);
				Expression nc = rewriteAfterEffect(child, t, before);
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

	public static void computeFeedCons(SparsePetriNet spn, SparseIntArray A, SparseIntArray feedA,
			SparseIntArray consA) {
		for (int tid = 0, tide = spn.getTransitionCount(); tid < tide; tid++) {
			if (SparseIntArray.keysIntersect(spn.getFlowTP().getColumn(tid), A)) {
				feedA.append(tid, 1);
			}
			if (SparseIntArray.keysIntersect(spn.getFlowPT().getColumn(tid), A)) {
				consA.append(tid, 1);
			}
		}
	}
}
