package fr.lip6.move.gal.structural.smt;

import static fr.lip6.move.gal.structural.smt.SMTUtils.buildSum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.SMT;
import org.smtlib.IExpr.IFactory;
import org.smtlib.command.C_assert;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.util.IntMatrixCol;

public class TrapAlongPathRefiner extends TrapRefiner {

	private IntMatrixCol effects;
	private Map<Integer, List<Integer>> revMap;

	public TrapAlongPathRefiner(ISparsePetriNet net, IntMatrixCol effects, List<Integer> repr) {
		super(net,"Known Traps Along Path");
		this.effects = effects;
		this.revMap = SMTUtils.computeImages(repr);		
	}

    /**
     * Look for a trap that contradicts the given state, create and add a constraint to the known traps if found.
     * @param net the net
     * @param state the solution on SAT of the problem
     * @param known the known traps to add to
     * @param solver the solver state
     * @return true if a new trap was found
     */
	@Override
	protected boolean findTrap(ISparsePetriNet net, CandidateSolution solution, StaticRefiner known, SolverState solver) {
        boolean trapFound = false;
        
        SparseIntArray parikh = solution.getParikh();
		if (parikh == null || parikh.size() == 0) {
			return false;
		}
        
		SparseIntArray state = new SparseIntArray();
		SparseIntArray max = new SparseIntArray();
		
		Set<Integer> maxSeen = new HashSet<>();
		for (int i = 0, ie = parikh.size(); i < ie; i++) {
			SparseIntArray tFired = effects.getColumn(parikh.keyAt(i));
			state = SparseIntArray.sumProd(1, state, parikh.valueAt(i), tFired);
			// max simply adds positive values, never negative.
			for (int j = 0, je = tFired.size(); j < je; j++) {
				int p = tFired.keyAt(j);
				int v = tFired.valueAt(j);
				if (v > 0) {					
					maxSeen.add(p);
				}
			}
		}
		for (int p : maxSeen) {
			max.put(p, 1);
		}

		
        List<Integer> trap = SMTTrapUtils.testTrapAlongPathWithSMT(net, parikh, state, max, solver.getDeclaredVars().getVars().get("s"));
        
        if (!trap.isEmpty()) {
                      
            SparseIntArray sparseTrap = new SparseIntArray(trap.size());
			for (int i = 0, ie = trap.size(); i < ie; i++) {
				sparseTrap.append(trap.get(i), 1);
			}
            
            List<Integer> feeders = new ArrayList<>();
            // compute feeders of the trap
            // unfortunately a bit imprecise when we have many representatives since it uses reduced flow matrix, so read arcs are skipped
			for (int t = 0, te = effects.getColumnCount(); t < te; t++) {
				List<Integer> represents = revMap.get(t);
				
				// case 1 : single representative
				if (represents.size() == 1) {
					int truet = represents.get(0);
					SparseIntArray tPost = net.getFlowTP().getColumn(truet);
					if (SparseIntArray.keysIntersect(tPost, sparseTrap)) {
						feeders.add(t);
					}
				} else {
					// case 2 : multiple representatives
					// overapproximate using the effects matrix
					SparseIntArray tFired = effects.getColumn(t);
					for (int i = 0, ie = tFired.size(); i < ie; i++) {
						int p = tFired.keyAt(i);
						if (tFired.valueAt(i) > 0 && sparseTrap.get(p) > 0) {
							// a feeder
							feeders.add(t);
							break;
						}
					}
				}
			}
			
			// if we have feeders, add the feed => trap constraint
			if (!feeders.isEmpty()) {
				// asserts one place is non empty
				IExpr trapPredicate = buildTrapExpression(trap);
				IExpr feedPredicate = buildFeedExpression(feeders);
				
				VarSet support = computeSupport(trap);
				for (Integer t : feeders) {
					support.addVar("t", t);
				}
				
				IExpr.IFactory ef = SMT.instance.smtConfig.exprFactory;
				ICommand constraint = new C_assert(ef.fcn(ef.symbol("=>"), feedPredicate,trapPredicate));            
		        				
		        known.addConstraint(new SMTConstraint(constraint, support));
		        trapFound = true;
			}
        }
        return trapFound;
    }
	
	private IExpr buildFeedExpression(Collection<Integer> feeders) {
		IFactory ef = SMT.instance.smtConfig.exprFactory;
		List<IExpr> vars = feeders.stream().map(n -> ef.symbol("t"+n)).collect(Collectors.toList());
		IExpr sum = buildSum(vars);
		IExpr trapPredicate = ef.fcn(ef.symbol(">="), sum , ef.numeral(1));
		return trapPredicate;
	}	

	@Override
	public void updateWitness(SolverState solver, Problem problem) {
		super.updateWitness(solver, problem);
		// we also need the Parikh witness
		problem.updateWitness(solver, "t");
	}
}
