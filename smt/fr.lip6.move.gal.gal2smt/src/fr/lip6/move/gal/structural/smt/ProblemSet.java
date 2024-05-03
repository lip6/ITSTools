package fr.lip6.move.gal.structural.smt;

import java.util.ArrayList;
import java.util.List;

import fr.lip6.move.gal.mcc.properties.DoneProperties;

public class ProblemSet {
	private List<Problem> solved = new ArrayList<>();
	private List<Problem> unsolved = new ArrayList<>();
	private DoneProperties doneProps ;
	
	public ProblemSet(DoneProperties doneProps) {
		this.doneProps = doneProps;
	}

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
	
	public void updateStatus(SolverState solver) {
		for (Problem p : unsolved) {
			if (p.getSolution().getReply() == SMTReply.UNKNOWN) {
				continue;
			}
			p.updateStatus(solver, doneProps);
		}
		update();
	}
	
	
	public boolean isSolved() {
        return unsolved.isEmpty();
	}
	
	public List<Problem> getUnsolved() {
		return unsolved;
	}
	
	public List<Problem> getSolved() {
		return solved;
	}

	public boolean hasType(SMTReply type) {
		for (Problem p : unsolved) {
			if (p.getSolution().getReply() == type) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Problem set: " + solved.size() + " solved, " + unsolved.size() + " unsolved");
		// sb.append(unsolved);
		return sb.toString();
	}
	
	public DoneProperties getDoneProps() {
		return doneProps;
	}
}
