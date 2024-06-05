package fr.lip6.move.gal.structural.smt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.smtlib.IExpr;
import org.smtlib.SMT;
import org.smtlib.IExpr.IFactory;
import org.smtlib.command.C_assert;
import org.smtlib.impl.Script;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.util.IntMatrixCol;

public class PredecessorConstraintRefiner implements IRefiner {
	
	private Set<String> doneProblems = new HashSet<>();
	private Map<String, IExpr> definitions = new HashMap<>();
	private Map<String, VarSet> support = new HashMap<>();
	
	public PredecessorConstraintRefiner(ISparsePetriNet spn, List<Integer> repr, IntMatrixCol effects,
			ProblemSet problems) {
		if (effects.getColumnCount() == 0) {
			// no transitions, no constraints
			return;
		}
		for (Problem p : problems.getUnsolved()) {
			Expression ap = p.getPredicate();
			if (ap == null) {
				doneProblems.add(p.getName());
			} else {
				try {
					IExpr pred = computePredExpr(ap, effects, repr, spn);
					if (pred != null) {
						definitions.put(p.getName(), pred);
						VarSet s = SMTUtils.computeSupport(pred);
						support.put(p.getName(), s);
						doneProblems.add(p.getName());
					}
				} catch (OutOfMemoryError e) {
					System.out.println("Out of memory, skipping this problem.");
				}
			}
		}
	}



	@Override
	public int refine(SolverState solver, ProblemSet problems, RefinementMode mode, VarSet current, long timeout) {
		int total = 0;
		
		for (Problem p : problems.getUnsolved()) {
			if (! doneProblems.contains(p.getName())) {
				IExpr pred = definitions.get(p.getName());
				if (pred == null) {
					doneProblems.add(p.getName());
					continue;
				}				
				if (canApplyConstraint(p.getName(), current, mode)) {
					solver.declareVars(support.get(p.getName()));
					p.refineDefinition(pred, solver);
					doneProblems.add(p.getName());
					total++;
				}
			}
		}
				
		return total;
	}


	
    private boolean canApplyConstraint(String constraint, VarSet current, RefinementMode mode) {    	
        switch (mode) {
            case INCLUDED_ONLY:
                return current.containsAll(support.get(constraint));
            case OVERLAPS:
                return VarSet.intersects(current, support.get(constraint));
            case ALL:
                return true;
            default:
                return false;
        }
    }

	@Override
	public void reset() {
		doneProblems.clear();
	}

	public static IExpr computePredExpr(Expression ap, IntMatrixCol sumMatrix, List<Integer> representative,
			ISparsePetriNet sr) {
		// we know that s satisfies ap
		// we want to force existence of an immediate predecessor of s satisfying !ap

		// there must exist a transition t such that
		// * the predecessor by t of s satisfies !ap; this depends only on the effect of t, not it's precise definition
		// * t was feasibly the last fired transition, i.e. there is a transition t' that t represents such that s >= post(t')
		// * t was selected in the Parikh solution to reach s; |t|>0

		// a map from index in reduced flow to set of transitions with this effect
		Map<Integer, List<Integer>> revMap = SMTUtils.computeImages(representative); 

		SparseIntArray supp = SMTUtils.computeSupport(ap);


		IFactory ef = SMT.instance.smtConfig.exprFactory;

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
					return null;
				}
				// more subtle we do touch the target AP
				// compute if firing t would go from !ap to ap
				// * the predecessor by t of s satisfies !ap; this depends only on the effect of t, not it's precise definition
				IExpr apFalseBeforeT = SMTUtils.rewriteAfterEffect(Expression.not(ap),t,true).accept(new ExprTranslator());

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
		// assert an OR of one of the candidates 		
		IExpr predicate = SMTUtils.makeOr(allPotentialPred);
		return predicate;
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
	
	
	/**
	 * Historical API, building a Script.
	 * @param ap
	 * @param sumMatrix
	 * @param representative
	 * @param sr
	 * @return
	 */
	public static Script computePredConstraint(Expression ap, IntMatrixCol sumMatrix,
			List<Integer> representative, ISparsePetriNet sr) {

		IExpr predicate = computePredExpr(ap, sumMatrix, representative, sr);
		Script script = new Script();		
		if (predicate != null) {
			script.add(new C_assert(predicate));
		}
		return script;
	}

	
	@Override
    public String toString() {
        return "PredecessorRefiner: " + doneProblems.size() + "/" + definitions.size() + " constraints";
    }

}
