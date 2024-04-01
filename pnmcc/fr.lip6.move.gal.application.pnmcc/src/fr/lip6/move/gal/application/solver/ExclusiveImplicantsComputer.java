package fr.lip6.move.gal.application.solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.command.C_assert;
import org.smtlib.impl.Script;

import android.util.SparseIntArray;
import fr.lip6.move.gal.application.mcc.MccTranslator;
import fr.lip6.move.gal.structural.ISparsePetriNet;
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

	/**
	 * Captures a drain problem : are all transitions in "setT" drain transitions of "setA" ?
	 */
	private static class DrainProblem {
		SparseIntArray setA;
		SparseIntArray setT;
		String name;
		Optional<Boolean> result = Optional.empty();
		public DrainProblem(SparseIntArray setA, SparseIntArray setT, String name) {
			this.setA = setA;
			this.setT = setT;
			this.name = name;
		}
		
	}
	
	public static void studyImplicants(MccTranslator reader) {
		
		SparseIntArray a = new SparseIntArray();
		SparseIntArray b = new SparseIntArray();
		
		a.append(0, 1);
		b.append(1, 1);
		
		testInvariants(a,b,reader);
		
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
			// .A   transitions feeding A
			SparseIntArray feedA = new SparseIntArray();
			// A.   transitions consuming in A
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
				}
				
				// add drain problems
				problems.add(new DrainProblem(a, feedNotConsB, "FeedNotConsBsubsetsDrainA"));
				problems.add(new DrainProblem(b, feedNotConsA, "FeedNotConsAsubsetsDrainB"));				
			}
			
			if (matchAimpliesB) {
				// .A \ A.
				SparseIntArray feedNotConsA = SparseIntArray.removeAll(feedA, consA);
				
				// .A \ A. must be a subset of .B
				if (! SparseIntArray.containsAllKeys(feedB, feedNotConsA)) {
					matchAimpliesB = false;
				} else {
					// B. \ .B
					SparseIntArray consNotFeedB = SparseIntArray.removeAll(consB, feedB);
					// must be drain transitions of A
					problems.add(new DrainProblem(a, consNotFeedB, "ConsNotFeedBsubsetsDrainA"));
				}				
			}
			
			if (matchBimpliesA) {
				// .B \ B.
				SparseIntArray feedNotConsB = SparseIntArray.removeAll(feedB, consB);
				
				// .B \ B. must be a subset of .A
				if (! SparseIntArray.containsAllKeys(feedA, feedNotConsB)) {
					matchBimpliesA = false;
				} else {
					// A. \ .A
					SparseIntArray consNotFeedA = SparseIntArray.removeAll(consA, feedA);
					// must be drain transitions of B
					problems.add(new DrainProblem(b, consNotFeedA, "ConsNotFeedAsubsetsDrainB"));
				}				
			}
		}
		
		solveDrainProblems(spn,problems);
		
	}

	private static void solveDrainProblems(SparsePetriNet spn, List<DrainProblem> problems) {
		
		
		int tcsize = problems.size();
		List<Script> properties = new ArrayList<>();

		
		for (int probid=0 ; probid < tcsize ; probid++) {
			DrainProblem dp = problems.get(probid);
			// A is unmarked in M' or in M
			
			SparseIntArray setA = dp.setA;

			Expression MpANonEmpty;
			{
				List<Expression> vars = new ArrayList<>();
				for (int i=0,ie=setA.size(); i <ie; i++) {
					vars.add(Expression.var(setA.keyAt(i)));
				}
				MpANonEmpty = Expression.op(Op.GEQ, 
						Expression.nop(Op.ADD,vars),
						Expression.constant(1));				
			}
			
			List<Expression> drainExpressions = new ArrayList<>();		
			SparseIntArray setT = dp.setT;
			for (int i=0,ie=setT.size(); i<ie;i++) {
				// for each transition in the set; t must be a drain.
				int tid = setT.keyAt(i);
				// TODO : WIP
				// 
				SparseIntArray teffect = SparseIntArray.sumProd(-1, spn.getFlowPT().getColumn(tid), 1, spn.getFlowTP().getColumn(tid));
				// false means *predecessor* must satisfy expression
				Expression beforeT = rewriteAfterEffect(MpANonEmpty, teffect, false);
				
				drainExpressions.add(Expression.nop(Op.AND,MpANonEmpty,beforeT));
			}

			// OR: if we can SAT this problem using any transition in setT, we cannot prove our constraint.
			Expression totest = Expression.nop(Op.OR,drainExpressions);
			
			IExpr smtexpr = totest.accept(new ExprTranslator());
			Script property = new Script();
			ICommand propAssert = new C_assert(smtexpr);
			property.add(propAssert );
			properties.add(property);			
		}

		
		
		List<Integer> representative = new ArrayList<>();
		IntMatrixCol sumMatrix = InvariantCalculator.computeReducedFlow(spn, representative);
		
		List<SparseIntArray> pors = new ArrayList<>();
		int timeout = 30; // in seconds
		List<SparseIntArray> result = DeadlockTester.escalateRealToInt(spn, properties, timeout, false /*no witness needed*/, representative, pors, sumMatrix);

		
	}
	
	public static Expression rewriteAfterEffect (Expression expr, SparseIntArray t, boolean before) {
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

	public static void computeFeedCons(SparsePetriNet spn, SparseIntArray A, SparseIntArray feedA,
			SparseIntArray consA) {
		for (int tid=0,tide=spn.getTransitionCount();tid<tide;tid++) {
			if (SparseIntArray.keysIntersect(spn.getFlowTP().getColumn(tid), A)) {
				feedA.append(tid, 1);
			}
			if (SparseIntArray.keysIntersect(spn.getFlowPT().getColumn(tid), A)) {
				consA.append(tid,1);
			}
		}
	}
}
