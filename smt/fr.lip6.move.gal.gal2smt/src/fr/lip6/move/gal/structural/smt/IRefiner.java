package fr.lip6.move.gal.structural.smt;


public interface IRefiner {
	public static enum RefinementMode {
		INCLUDED_ONLY,
		OVERLAPS,
		ALL
	}
	
	int refine(SolverState solver, ProblemSet problems, RefinementMode mode, VarSet current, long timeout);
	boolean isDone();
	void reset();
}
