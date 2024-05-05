package fr.lip6.move.gal.structural.smt;

import java.util.ArrayList;
import java.util.List;

import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.InvariantCalculator;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.smt.IRefiner.RefinementMode;
import fr.lip6.move.gal.util.IntMatrixCol;

public class SMTBasedReachabilitySolver {

	public static ProblemSet prepareProblemSet(List<Property> props, DoneProperties doneProps) {
		ProblemSet problems = new ProblemSet(doneProps);
		for (fr.lip6.move.gal.structural.Property p : props) {
			if (doneProps.containsKey(p.getName())) {
				continue;
			}
			if (p.getType() != PropertyType.INVARIANT) {
				throw new IllegalArgumentException("Only invariants are supported for now.");
			}
			Expression pred = p.getBody().childAt(0);			
			
			Op op = p.getBody().getOp();
			if (op == Op.EF|| op == Op.AG) {
				problems.addProblem(new Problem(p.getName(), op == Op.EF, pred));
			} else {
				throw new IllegalArgumentException("Only EF and AG properties should be roots of an INVARIANT.");
			}
		}
		return problems;
	}
	
	public static int solveProblems(ProblemSet problems, ISparsePetriNet spn, int timeout, boolean withWitness, List<Integer> repr) {
		long time = System.currentTimeMillis();
		List<IRefiner> refiners = new ArrayList<>();
		IntMatrixCol effects = InvariantCalculator.computeReducedFlow(spn, repr );

		SolverState solver = new SolverState();

		int initial = problems.getSolved().size();

		// add places "s" variables
		solver.addVars("s", spn.getPlaceCount(), VarType.NUMERIC);	
		solver.setMinBounds("s", 0);


		if (spn.isSafe()) {
			refiners.add(DomainRefinerBuilder.enforceMaxBound("s", spn.getPlaceCount(), 1));			
		}

		refiners.addAll(InvariantRefinerBuilder.buildInvariantRefiners(effects, spn.getMarks()));

		// also adds "t" variables for transitions
		refiners.addAll(StateEquationRefinerBuilder.buildStateEquationRefiner(effects,spn.getMarks(), solver));

		{
			IRefiner ref = ReadFeedRefinerBuilder.buildReadFeedRefiner(spn, effects, repr, solver);
			if (ref != null) {
				refiners.add(ref);
			}
		}

		// a bit expensive, so do it last
		refiners.add(new PredecessorConstraintRefiner(spn, repr, effects, problems));


		refiners.add(new TrapRefiner(spn));


		try {
			solve (refiners, problems, solver, timeout, withWitness);
			if (!problems.isSolved()) {
				for (Problem p : problems.getUnsolved()) {
					p.dropRefinement();
					p.getSolution().setReply(SMTReply.SAT);
				}
				for (IRefiner ref : refiners) {
					ref.reset();
				}
				System.out.println("Escalating to Integer solving :"+ problems);
				solver.setNumericType(SolutionType.Int);
				solve (refiners, problems, solver, timeout, withWitness);
			}
			System.out.println("After SMT, in "+(System.currentTimeMillis()-time)+"ms problems are : "+problems);
		} catch (Exception e) {
			System.out.println("SMT process timed out in "+(System.currentTimeMillis()-time)+"ms, After SMT, problems are : "+problems);
		}
		return problems.getSolved().size() - initial;
	}

	private static void solve(List<IRefiner> refiners, ProblemSet problems, SolverState solver, int timeout, boolean withWitness) {
		
		
		long time = System.currentTimeMillis();
		solver.start(timeout);
		
		for (Problem p : problems.getUnsolved()) {
			p.declareProblem(solver);
		}
		// quick check
		problems.updateStatus(solver);
		int totalConstraints = 0;
		int iteration = 0;
		RefinementMode mode = RefinementMode.INCLUDED_ONLY;
		boolean realSolutions = false;
		int nbCheckPoint = 0;
		while (!problems.isSolved()) {			
			VarSet current = solver.getDeclaredVars().clone();
						
			int addedConstraints = 0;
			for (IRefiner ref : refiners) {
				addedConstraints += ref.refine(solver, problems, mode, current, timeout);
				if (addedConstraints > 0) {
					break;
				}
			}
			totalConstraints += addedConstraints;

			problems.updateStatus(solver);
			if (problems.isSolved()) {
				break;
			}
			if (problems.getUnsolved().stream().allMatch(p -> p.getSolution().getReply() == SMTReply.UNKNOWN)) {
				System.out.println("Solver is answering 'unknown', stopping.");
				break;
			}

			if (!realSolutions && iteration % 3 == 0 && solver.getNumericType() == SolutionType.Real) {
				for (Problem p : problems.getUnsolved()) {
					p.updateStatus(solver, problems.getDoneProps());
					p.updateWitness(solver, "s");
                }
				if (problems.getUnsolved().stream().allMatch(p -> p.getSolution().getReply() == SMTReply.REAL)) {
					System.out.println("All remaining problems are real, not stopping.");
					realSolutions = true;
					// break;
				}	
			}
			int addedVars = solver.getDeclaredVars().size() - current.size();

			System.out.println("At refinement iteration " + iteration + " (" + mode + ") " + addedVars + "/"
					+ solver.getDeclaredVars().size() + " variables, " + addedConstraints + "/" + totalConstraints
					+ " constraints. Problems are: " + problems);
			
			long passed = System.currentTimeMillis() - time;
			if (withWitness && passed > nbCheckPoint*5000) {
				nbCheckPoint++;
				// might soon timeout, grab traces
				for (Problem p : problems.getUnsolved()) {
					p.updateStatus(solver, problems.getDoneProps());
					if (withWitness)
						p.updateWitness(solver, "t");
		        }
			}

			if (addedConstraints + addedVars == 0 && mode == RefinementMode.OVERLAPS) {
				System.out.println("No progress, stopping.");
				break;
			} else if (addedConstraints + addedVars == 0) {
				mode = RefinementMode.OVERLAPS;
			} else {
				mode = RefinementMode.INCLUDED_ONLY;
			}
			iteration++;
		}

		for (Problem p : problems.getUnsolved()) {
			p.updateStatus(solver, problems.getDoneProps());
			if (withWitness && !realSolutions)
				p.updateWitness(solver, "t");
        }
		
		System.out.println("After SMT solving in domain " + solver.getNumericType() + " declared "
				+ solver.getDeclaredVars().size() + "/" + solver.getAllVars().size() + " variables, and "
				+ totalConstraints + " constraints, problems are : " + problems + " in "
				+ (System.currentTimeMillis() - time) +" ms.");
		System.out.println("Refiners :" +refiners);
		solver.stop();
	}
}
