package fr.lip6.move.gal.structural.smt;

import java.util.ArrayList;
import java.util.List;

public class ProblemSet {
	private List<Problem> solved = new ArrayList<>();
	private List<Problem> unsolved = new ArrayList<>();
	
	public void addProblem(Problem p) {
        unsolved.add(p);
    }
	
	public void update() {
		for (int i = unsolved.size() - 1; i >= 0; i--) {
			Problem p = unsolved.get(i);
			if (p.isSolved()) {
				unsolved.remove(i);
				solved.add(p);
			}
		}
	}
	
	public void updateStatus(SolverState solver, boolean withWitness) {
		for (Problem p : unsolved) {
			if (solver.getNumericType() == SolutionType.Real && p.getSolution().getReply() == SMTReply.REAL) {
				continue;
            }
			p.updateStatus(solver, withWitness);
		}
		update();
	}
	
	
	public boolean isSolved() {
        return unsolved.isEmpty();
	}
	
	public List<Problem> getUnsolved() {
		return unsolved;
	}

	public boolean hasReal() {
		for (Problem p : unsolved) {
			if (p.getSolution().getReply() == SMTReply.REAL) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Problem set: " + solved.size() + " solved, " + unsolved.size() + " unsolved\n");
		return sb.toString();
	}
}
