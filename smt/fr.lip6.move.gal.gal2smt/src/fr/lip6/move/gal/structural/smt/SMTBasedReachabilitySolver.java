package fr.lip6.move.gal.structural.smt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.InvariantCalculator;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.smt.IRefiner.RefinementMode;
import fr.lip6.move.gal.util.IntMatrixCol;

public class SMTBasedReachabilitySolver {

	public static ProblemSet prepareProblemSet(List<Property> props) {
		ProblemSet problems = new ProblemSet();
		int index = 0;
		for (fr.lip6.move.gal.structural.Property p : props) {
			if (p.getType() != PropertyType.INVARIANT) {
				throw new IllegalArgumentException("Only invariants are supported for now.");
			}
			Expression pred = p.getBody().childAt(0);			
			
			if (p.getBody().getOp() == Op.EF) {
				problems.addProblem(new Problem(p.getName(), index, pred));
			} else if (p.getBody().getOp() == Op.AG) {
				problems.addProblem(new Problem(p.getName(), index, Expression.not(pred)));
			} else {
				throw new IllegalArgumentException("Only EF and AG properties should be roots of an INVARIANT.");
			}
			index++;
		}
		return problems;
	}
	
	public static void solveProblems(ProblemSet problems, ISparsePetriNet spn, int timeout, boolean withWitness) {
		List<IRefiner> refiners = new ArrayList<>();
		List<Integer> repr = new ArrayList<>();
		IntMatrixCol effects = InvariantCalculator.computeReducedFlow(spn, repr );

		SolverState solver = new SolverState();

		// add places "s" variables
		solver.addVars("s", spn.getPlaceCount(), VarType.NUMERIC);
		
		refiners.add(DomainRefinerBuilder.enforceMinBound("s", spn.getPlaceCount(), 0));
		
		refiners.addAll(InvariantRefinerBuilder.buildInvariantRefiners(effects, spn.getMarks()));
		
		// also adds "t" variables for transitions
		refiners.addAll(StateEquationRefinerBuilder.buildStateEquationRefiner(effects,spn.getMarks(), solver));

		refiners.add(new TrapRefiner(spn));

		// a bit expensive, so do it last
		refiners.add(new PredecessorConstraintRefiner(spn, repr, effects, problems));

		
		solve (refiners, problems, solver, timeout, withWitness);
		if (!problems.isSolved() && (problems.hasType(SMTReply.REAL) || problems.hasType(SMTReply.UNKNOWN))) {
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
		System.out.println("After SMT, problems are : "+problems);
	}

	private static void solve(List<IRefiner> refiners, ProblemSet problems, SolverState solver, int timeout, boolean withWitness) {
		
		
		long time = System.currentTimeMillis();
		solver.start(timeout);
		
		for (Problem p : problems.getUnsolved()) {
			p.declareProblem(solver);
		}
		// quick check
		problems.updateStatus(solver, false);
		int totalConstraints = 0;
		int iteration = 0;
		while (!problems.isSolved()) {
			RefinementMode mode = (iteration % 2 == 0) ? RefinementMode.INCLUDED_ONLY : RefinementMode.OVERLAPS;
			VarSet current = solver.getDeclaredVars().clone();
			boolean anyRefinement = false; // Track if any refinement occurs
			int addedConstraints = 0;
			for (IRefiner ref : refiners) {
				addedConstraints += ref.refine(solver, problems, mode, current, timeout);
				anyRefinement |= (addedConstraints > 0);
			}

			problems.updateStatus(solver, withWitness);
			if (problems.isSolved()) {
				break;
			}
			if (problems.getUnsolved().stream().allMatch(p -> p.getSolution().getReply() == SMTReply.UNKNOWN)) {
				System.out.println("Solver is answering 'unknown', stopping.");
				break;
			}

			if (iteration >= 3 && solver.getNumericType() == SolutionType.Real &&  problems.getUnsolved().stream().allMatch(p -> p.getSolution().getReply() == SMTReply.REAL)) {
				System.out.println("All remaining problems are real, stopping.");
				break;
			}
			int addedVars = solver.getDeclaredVars().size() - current.size();
			anyRefinement |= (addedVars > 0);
			totalConstraints += addedConstraints;
			System.out.println("At refinement iteration " + iteration + " (" + mode + ") " + addedVars + "/"
					+ solver.getDeclaredVars().size() + " variables, " + addedConstraints + "/" + totalConstraints
					+ " constraints. Problems are: " + problems);

			if (!anyRefinement) {
				System.out.println("No progress, stopping.");
				break;
			}
			iteration++;
		}

		
		System.out.println("After SMT solving in domain " + solver.getNumericType() + " declared "
				+ solver.getDeclaredVars().size() + "/" + solver.getAllVars().size() + " variables, " + " and "
				+ totalConstraints + " constraints, problems are : " + problems + " in "
				+ (System.currentTimeMillis() - time) +" ms.");
		System.out.println("Refiners :" +refiners);
		solver.stop();
	}
}