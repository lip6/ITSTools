package fr.lip6.move.gal.structural.smt;

import static fr.lip6.move.gal.structural.smt.SMTUtils.buildSum;

import java.util.List;
import java.util.stream.Collectors;

import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.IExpr.IFactory;
import org.smtlib.command.C_assert;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.ISparsePetriNet;

public class TrapRefiner implements IRefiner {
    private StaticRefiner knownTraps;
    private ISparsePetriNet net;

    public TrapRefiner(ISparsePetriNet net) {
        this.net = net;
        this.knownTraps = new StaticRefiner("Known Traps");
    }

    @Override
    public int refine(SolverState solver, ProblemSet problems, RefinementMode mode, VarSet current, long timeout) {
        int totalConstraints =0;
        
        int constraintsAdded = 0;
        
        boolean old=false;
		if (old) {
            do {
            	constraintsAdded = 0;
                // we need witness to find traps
                problems.updateStatus(solver);
                for (Problem problem : problems.getUnsolved()) {
                	if (problem.getSolution().getReply() != SMTReply.SAT) {
                		continue;
                	}
                	problem.updateStatus(solver, problems.getDoneProps());  
        			if (problem.getSolution().getReply() != SMTReply.SAT) {
        				continue;
        			} else {
        				problem.updateWitness(solver, "s");
        			}
        			if (solver.getNumericType() ==SolutionType.Real && problem.getSolution().getReply() == SMTReply.REAL) {
        				continue;
        			}
                    CandidateSolution candidate = problem.getSolution();
                    boolean newTrapFound = findTrap(net, candidate.getState(), knownTraps, solver);            
                }
                constraintsAdded += knownTraps.refine(solver, problems, mode, current, timeout);
                totalConstraints += constraintsAdded;        	        	
            } while (constraintsAdded > 0);
        } else {
        
        
        /*do*/ {
        	constraintsAdded = 0;
            constraintsAdded += knownTraps.refine(solver, problems, mode, current, timeout);
            if (constraintsAdded > 0) {
            	totalConstraints += constraintsAdded;
            	return totalConstraints;
            }
        	for (Problem problem : problems.getUnsolved()) {
        		while (true) {
        			// we need witness to find traps
        			if (problem.getSolution().getReply() != SMTReply.SAT) {
        				break;
        			}
        			problem.updateStatus(solver, problems.getDoneProps());  
        			if (problem.getSolution().getReply() != SMTReply.SAT) {
        				break;
        			} else {
        				problem.updateWitness(solver, "s");
        			}
        			
        			CandidateSolution candidate = problem.getSolution();
        			boolean newTrapFound = findTrap(net, candidate.getState(), knownTraps, solver);
        			if (newTrapFound) {
        				int added = knownTraps.refine(solver, problems, mode, current, timeout);
        				totalConstraints += added;
        				constraintsAdded += added;
        			} else {
        				break;
        			}
        		}
            }
            problems.update();
        } // while (constraintsAdded > 0);
        }
        
        return totalConstraints;
    }

    private boolean findTrap(ISparsePetriNet net, SparseIntArray state, StaticRefiner known, SolverState solver) {
        boolean trapFound = false;
        
		if (state == null || state.size() == 0) {
			return false;
		}
        
		// SparseBoolArray all = new SparseBoolArray(net.getPlaceCount(),true);
        // List<Integer> trap = SMTTrapUtils.testTrapWithSMT(net, candidate.getState(), all ) ;
        List<Integer> trap = SMTTrapUtils.testTrapWithSMT(net, state, solver.getDeclaredVars().getVars().get("s"));
        
        if (!trap.isEmpty()) {
            IFactory ef = solver.getSMT().smtConfig.exprFactory;
            List<IExpr> vars = trap.stream().map(n -> ef.symbol("s"+n)).collect(Collectors.toList());
            IExpr sum = buildSum(vars);
            ICommand constraint = new C_assert(ef.fcn(ef.symbol(">="), sum , ef.numeral(1)));
            VarSet support = new VarSet();
            for (Integer p : trap) {
            	support.addVar("s", p);
            }
            known.addConstraint(new SMTConstraint(constraint, support));
            trapFound = true;
        }
        return trapFound;
    }


    @Override
    public void reset() {
        knownTraps.reset();
    }
    
    @Override
    public String toString() {
    	return knownTraps.toString();
    }
}
