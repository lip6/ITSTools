package fr.lip6.move.gal.application.solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.command.C_assert;
import org.smtlib.impl.Script;

import android.util.SparseIntArray;
import fr.lip6.move.gal.application.mcc.MccTranslator;
import fr.lip6.move.gal.mcc.properties.ConcurrentHashDoneProperties;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.FlowPrinter;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.expr.AtomicPropRef;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.expr.VarRef;
import fr.lip6.move.gal.structural.smt.Problem;
import fr.lip6.move.gal.structural.smt.ProblemSet;
import fr.lip6.move.gal.structural.smt.SMTBasedReachabilitySolver;

public class ExclusiveImplicantsComputer {

	private static final int DEBUG = 0;

	public enum ProblemType {
		A_IMPLIES_B, A_EXCLUSIVE_B
	}

	/**
	 * A constraint, concerning two sets a and b. Constraints can be of three types
	 * : A => B; B => A ; A <> B.
	 */
	public static class Constraint {
		private final ProblemType type;
		private final SparseIntArray a;
		private final SparseIntArray b;

		public Constraint(ProblemType type, SparseIntArray a, SparseIntArray b) {
			this.type = type;
			this.a = a;
			this.b = b;
		}

		public SparseIntArray getA() {
			return a;
		}

		public SparseIntArray getB() {
			return b;
		}

		public ProblemType getType() {
			return type;
		}

		public String printConstraint(SparsePetriNet spn) {
			StringBuilder sb = new StringBuilder();
			sb.append(printPnames(a, spn.getPnames()));
			switch (type) {
			case A_IMPLIES_B:
				sb.append(" => ");
				break;
			case A_EXCLUSIVE_B:
				sb.append(" <> ");
				break;
			}
			sb.append(printPnames(b, spn.getPnames()));
			return sb.toString();
		}
	}

	/**
	 * Captures a drain problem : are all transitions in "setT" drain transitions of
	 * "setA" ?
	 */
	private static class DrainProblem {
		// these lists are several queries in one : for every index i, are all
		// transitions of setsT[i] drain wrt setsA[i].
		// All queries must be satisfied to deduce the constraint is true.
		private List<SparseIntArray> setsA = new ArrayList<>();
		private List<SparseIntArray> setsT = new ArrayList<>();
		/**
		 * Resulting constraint is True if all transitions in setT are drain with
		 * respect to setA
		 */
		private final Constraint constraint;

		/**
		 * Mostly for Implicants, where we only have one setsT/setsA.
		 * 
		 * @param setA set of places
		 * @param setT set of transitions that hopefully are drains of setA
		 * @param type type of the resulting constraint
		 * @param a    set of places
		 * @param b    set of places
		 */
		public DrainProblem(SparseIntArray setA, SparseIntArray setT, ProblemType type, SparseIntArray a,
				SparseIntArray b) {
			constraint = new Constraint(type, a, b);
			this.setsA.add(setA);
			this.setsT.add(setT);
		}

		/**
		 * Simpler version, simply initializing the constraint, used in exclusive case
		 * where we might have up to two problems to solve.
		 * 
		 * @param type type of the resulting constraint
		 * @param a    set of places
		 * @param b    set of places
		 */
		public DrainProblem(ProblemType type, SparseIntArray a, SparseIntArray b) {
			constraint = new Constraint(type, a, b);
		}

		/**
		 * Adds an additional problem to solve (mostly exclusive case).
		 * 
		 * @param setA
		 * @param setT
		 */
		public void addProblem(SparseIntArray setA, SparseIntArray setT) {
			setsA.add(setA);
			setsT.add(setT);
		}

		/**
		 * Compute and combine elements to obtain a single Boolean expression that can
		 * be asked to the SMT solver.
		 * 
		 * @param spn the net
		 * @return an expression that if untrue in any reachable state means the
		 *         constraint is true.
		 */
		public Expression computeDrainProblemCondition(SparsePetriNet spn) {
			// A is not unmarked in M' and in M
			List<Expression> toOr = new ArrayList<>();

			// there might be several problems in one
			for (int i = 0; i < setsA.size(); i++) {
				SparseIntArray setA = setsA.get(i);
				SparseIntArray setT = setsT.get(i);

				Expression totest = computeConstraintForOneSet(spn, setA, setT);
				toOr.add(totest);
			}

			// if any of them is sat, we are sat (cannot prove constraint).
			return Expression.nop(Op.OR, toOr);
		}

		/**
		 * Focus on one set of places setA, and one set of transitions setT.
		 * 
		 * @param spn  the net
		 * @param setA the places we consider
		 * @param setT a set of transitions, that should be drain w.r.t. setA
		 * @return an expression that if untrue in any reachable state indicates that
		 *         setT are indeed drains wr.t. setA.
		 */
		public Expression computeConstraintForOneSet(SparsePetriNet spn, SparseIntArray setA, SparseIntArray setT) {

			// Compute this expression once, it is used several times.
			// It expresses that setA contains at least one token.
			Expression MpANonEmpty;
			{
				List<Expression> vars = new ArrayList<>();
				for (int i = 0, ie = setA.size(); i < ie; i++) {
					vars.add(Expression.var(setA.keyAt(i)));
				}
				// sum of markings of places of A >= 1
				MpANonEmpty = Expression.op(Op.GEQ, Expression.nop(Op.ADD, vars), Expression.constant(1));
			}

			// Now add all the transitions to test
			// If any of them is SAT, we cannot prove our invariant.
			// We build one Expression per transition and add to this list.
			List<Expression> drainExpressions = new ArrayList<>();

			for (int i = 0, ie = setT.size(); i < ie; i++) {
				// for each transition in the set; t must be a drain.
				int tid = setT.keyAt(i);

				// effects of t; discards e.g. read arcs.
				SparseIntArray teffect = SparseIntArray.sumProd(-1, spn.getFlowPT().getColumn(tid), 1,
						spn.getFlowTP().getColumn(tid));

				// Returns an expression that says that setA was marked *before* we fired t.
				Expression MAnotEmptyBefore = rewriteAfterEffect(MpANonEmpty, teffect, true);

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


				// T was enabled conditions
				// This constraint is not useful; because by definition of tFeasiblyLast we are >= Post, and we apply -Post+Pre
				// it must be the case that we are already superior to pre.

				// All these constraints must simultaneously hold so that we have a
				// counter-example for t being drain for setA.
				drainExpressions.add(Expression.nop(Op.AND, MpANonEmpty, MAnotEmptyBefore, tFeasiblyLast));
			}

			// OR: if we can SAT this problem using any transition in setT, we cannot prove
			// our constraint.
			Expression totest = Expression.nop(Op.OR, drainExpressions);
			return totest;
		}

		@Override
		public String toString() {
			return "DrainProblem [" + ", setsA=" + setsA + ", setsT=" + setsT + ", name=" + constraint.getType() + "]";
		}

		public Constraint getConstraint() {
			return constraint;
		}
	}

	public static List<Constraint> studyImplicants(MccTranslator reader) {

		long time = System.currentTimeMillis();
		SparsePetriNet spn = reader.getSPN();

		// problems we need to check with SMT
		List<DrainProblem> problems = new ArrayList<>();
		// Constraints we have managed to prove
		List<Constraint> constraints = new ArrayList<>();

		// Very basic : all pairs of places.
		int nbp = spn.getPlaceCount();
		for (int i = 0; i < nbp; i++) {
			for (int j = i + 1; j < nbp; j++) {
				SparseIntArray a = new SparseIntArray();
				SparseIntArray b = new SparseIntArray();
				a.append(i, 1);
				b.append(j, 1);

				// this tests conditions for all three constraint between a and b.
				// if trivial conditions are unmet, the constraint is discarded
				// if trivial conditions hold, it adds a constraint to constraints
				// else when we are unsure, it adds a problem to "problems" for the SMT step.
				testInvariants(a, b, reader, problems, constraints);
			}
		}

		if (constraints.size() > 0) {
			System.out.println("Proved "+constraints.size()+" constraints trivially:");
			if (DEBUG >= 1)
				for (Constraint c : constraints) {
					System.out.println(c.printConstraint(spn));
				}
		}

		// This invocation prepares and then runs the SMT solver on the problems
		// result is for each problem either "unsolved" if problem is
		// SAT/unknown/timeout...,
		// or "solved" if the problem is UNSAT.
		List<Constraint> solved = solveDrainProblems(spn, problems);


		// Print the constraints we managed to prove
		if (solved.size() > 0) {
			System.out.println("Proved "+constraints.size()+" constraints with SMT :");
			if (DEBUG >= 1)
				for (Constraint c : solved) {
					System.out.println(c.printConstraint(spn));
				}
		}

		// reader.getSPN().getProperties().clear();
		if (DEBUG >= 0)
			FlowPrinter.drawNet(reader.getSPN(), reader.getSPN().getName());

		System.out.println("In total, drain approach introduced " + constraints.size() + " trivial constraints and "+ solved.size() + " constraints requiring SMT in "
				+ (System.currentTimeMillis() - time) + " ms.");
		constraints.addAll(solved);
		return constraints;
	}

	private static void testInvariants(SparseIntArray a, SparseIntArray b, MccTranslator reader,
			List<DrainProblem> problems, List<Constraint> constraints) {

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
			} else if (bmarked && !amarked) {
				matchBimpliesA = false;
			} else {
				// both a and b are initially empty; no contradiction.
			}
		}

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

			// .A \ A.
			SparseIntArray feedNotConsA = SparseIntArray.removeAll(feedA, consA);
			// .B \ B.
			SparseIntArray feedNotConsB = SparseIntArray.removeAll(feedB, consB);

			if (matchExclusive) {

				// Test empty intersection : .A \ A. inter .B \ B. = empty set
				if (SparseIntArray.keysIntersect(feedNotConsA, feedNotConsB)) {
					matchExclusive = false;
				} else {

					DrainProblem dp = new DrainProblem(ProblemType.A_EXCLUSIVE_B, a, b);
					// add drain problems
					if (feedNotConsB.size() > 0) {
						dp.addProblem(a, feedNotConsB);
					}
					if (feedNotConsA.size() > 0) {
						dp.addProblem(b, feedNotConsA);
					}
					if (feedNotConsB.size() > 0 || feedNotConsA.size() > 0) {
						problems.add(dp);
					} else {
						// Ok, we proved it without SMT.
						constraints.add(new Constraint(ProblemType.A_EXCLUSIVE_B, a, b));
					}
				}
			}

			if (matchAimpliesB) {

				// .A \ A. must be a subset of .B
				if (!SparseIntArray.containsAllKeys(feedB, feedNotConsA)) {
					matchAimpliesB = false;
				} else {
					// B. \ .B
					SparseIntArray consNotFeedB = SparseIntArray.removeAll(consB, feedB);
					if (consNotFeedB.size() > 0) {
						// must be drain transitions of A
						problems.add(new DrainProblem(a, consNotFeedB, ProblemType.A_IMPLIES_B, a, b));
					} else {
						// Ok, we proved it without SMT.
						constraints.add(new Constraint(ProblemType.A_IMPLIES_B, a, b));
					}
				}
			}

			if (matchBimpliesA) {

				// .B \ B. must be a subset of .A
				if (!SparseIntArray.containsAllKeys(feedA, feedNotConsB)) {
					matchBimpliesA = false;
				} else {
					// A. \ .A
					SparseIntArray consNotFeedA = SparseIntArray.removeAll(consA, feedA);
					if (consNotFeedA.size() > 0) {
						// must be drain transitions of B
						problems.add(new DrainProblem(b, consNotFeedA, ProblemType.A_IMPLIES_B, b, a));
					} else {
						// Ok, we proved it without SMT.
						constraints.add(new Constraint(ProblemType.A_IMPLIES_B, b, a));
					}
				}
			}
		}

	}

	private static List<Constraint> solveDrainProblems(SparsePetriNet spn, List<DrainProblem> problems) {

		int tcsize = problems.size();
		if (problems.size() == 0)
			return Collections.emptyList();
		
		DoneProperties done = new ConcurrentHashDoneProperties();
		ProblemSet ps = new ProblemSet(done);
		int i = 0;
		for (DrainProblem p : problems) {
			Problem pp = new Problem("p"+(i++),true,p.computeDrainProblemCondition(spn));
			ps.addProblem(pp);
		}
		
		List<Integer> repr = new ArrayList<>();
		int timeout = 30; // in seconds
		int solved = SMTBasedReachabilitySolver.solveProblems(ps, spn, timeout, false /* don't negate*/, repr);

		System.out.println("Solved "+solved+" problems out of "+tcsize + " drain problems.");
		
		
		List<Constraint> constraints = new ArrayList<>();
		for (Problem p : ps.getSolved()) {
			int id = Integer.parseInt(p.getName().substring(1));
			DrainProblem dp = problems.get(id);
			constraints .add(dp.getConstraint());
		}

		if (DEBUG >= 1) {
			for (Problem p : ps.getSolved()) {
				int id = Integer.parseInt(p.getName().substring(1));
				DrainProblem dp = problems.get(id);
				System.out.println("Could not prove " + dp.getConstraint().printConstraint(spn));
			}
		}
		
		return constraints;
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
