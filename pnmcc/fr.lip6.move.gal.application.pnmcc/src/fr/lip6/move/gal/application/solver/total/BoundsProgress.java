package fr.lip6.move.gal.application.solver.total;

/**
 * A sink for the interval of a bound still open: the largest value seen on
 * a reachable marking and the structural bound, -1 or Integer.MAX_VALUE when
 * there is none. The bounds solver reports at each of its checkpoints.
 */
public interface BoundsProgress {
	void interval(String prop, int maxSeen, int maxStruct);
}
