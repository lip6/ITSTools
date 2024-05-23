package fr.lip6.move.gal.structural.smt;

import static fr.lip6.move.gal.structural.smt.SMTUtils.buildSum;

import java.util.List;
import java.util.stream.Collectors;

import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.IExpr.IFactory;
import org.smtlib.SMT;
import org.smtlib.command.C_assert;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.ISparsePetriNet;

public class TrapRefiner implements IRefiner {
    private StaticRefiner knownTraps;
    private ISparsePetriNet net;

    public TrapRefiner(ISparsePetriNet net) {
        this(net,"Known Traps");
    }

    protected TrapRefiner(ISparsePetriNet net, String name) {
        this.net = net;
        this.knownTraps = new StaticRefiner(name);
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
        				updateWitness(solver, problem);
        			}
        			if (solver.getNumericType() ==SolutionType.Real && problem.getSolution().getReply() == SMTReply.REAL) {
        				continue;
        			}
                    boolean newTrapFound = findTrap(net, problem.getSolution(), knownTraps, solver);            
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
            if (knownTraps.unappliedCount() > 0) {
            	// let's not compute more traps, those we have are not in scope of declared vars
            	return 0;
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
        				updateWitness(solver, problem);
        			}
        			
        			boolean newTrapFound = findTrap(net, problem.getSolution(), knownTraps, solver);
        			if (newTrapFound) {
        				int added = knownTraps.refine(solver, problems, mode, current, timeout);
        				totalConstraints += added;
        				constraintsAdded += added;
        				if (added == 0)
        					break;
        			} else {
        				break;
        			}
					if (totalConstraints >= 20) {
						// let other refiners do some work
						return totalConstraints;
					}
        		}
            }
            problems.update();
        } // while (constraintsAdded > 0);
        }
        
        return totalConstraints;
    }

	public void updateWitness(SolverState solver, Problem problem) {
		//grab the reached state
		problem.updateWitness(solver, "s");
	}

    /**
     * Look for a trap that contradicts the given state, create and add a constraint to the known traps if found.
     * @param net the net
     * @param state the solution on SAT of the problem
     * @param known the known traps to add to
     * @param solver the solver state
     * @return true if a new trap was found
     */
    protected boolean findTrap(ISparsePetriNet net, CandidateSolution solution, StaticRefiner known, SolverState solver) {
        boolean trapFound = false;
        
        SparseIntArray state = solution.getState();
		if (state == null || state.size() == 0) {
			return false;
		}
        
		// SparseBoolArray all = new SparseBoolArray(net.getPlaceCount(),true);
        // List<Integer> trap = SMTTrapUtils.testTrapWithSMT(net, candidate.getState(), all ) ;
        List<Integer> trap = SMTTrapUtils.testTrapWithSMT(net, state, solver.getDeclaredVars().getVars().get("s"));
        
        if (!trap.isEmpty()) {
            IExpr trapPredicate = buildTrapExpression(trap);
            VarSet support = computeSupport(trap);
            ICommand constraint = new C_assert(trapPredicate);            
            known.addConstraint(new SMTConstraint(constraint, support));
            trapFound = true;
        }
        return trapFound;
    }

	public static IExpr buildTrapExpression(List<Integer> trap) {
		IFactory ef = SMT.instance.smtConfig.exprFactory;
		List<IExpr> vars = trap.stream().map(n -> ef.symbol("s"+n)).collect(Collectors.toList());
		IExpr sum = buildSum(vars);
		IExpr trapPredicate = ef.fcn(ef.symbol(">="), sum , ef.numeral(1));
		return trapPredicate;
	}

	public VarSet computeSupport(List<Integer> trap) {
		VarSet support = new VarSet();
		for (Integer p : trap) {
			support.addVar("s", p);
		}
		return support;
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
