package fr.lip6.move.gal.semantics;

import java.util.List;

public interface IDeterministicNextBuilder extends INextBuilder {
	
	/**
	 * Computes the determinisitc transition relation and stores/caches  it.
	 * @return a list of sequences (stored as List) of Predicates and Assignments only. no Alt, no nestesd Seq.
	 */
	public List<List<INext>> getDeterministicNext ();

	/**
	 * Computes the deterministic transition relation if not already available and computes read write supports of transitions. 
	 * @return a dependency matrix, indexed on getDeterministicNext().
	 */
	public DependencyMatrix getDeterministicDependencyMatrix();
	
	/**
	 * Factory method to obtain a IDeterministicNextBuilder.
	 * @param nb the builder we want to determinize
	 * @return a semantic builder supporting deterministic questions.
	 */
	static IDeterministicNextBuilder build(INextBuilder nb) {
		return new DeterministicNextBuilder(nb);
	}
}
