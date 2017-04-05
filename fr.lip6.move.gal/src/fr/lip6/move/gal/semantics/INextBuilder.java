package fr.lip6.move.gal.semantics;

import java.util.List;

import fr.lip6.move.gal.CompositeTypeDeclaration;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Reference;

/**
 * This interface represents a provider of INext semantics, it basically reifies the ITS type contract.
 * @author ythierry
 *
 */
public interface INextBuilder {

	/**
	 * The successor relationship for a given label.
	 * @param lab the target label, could be the empty string "" to get the local behaviors (tau).
	 * @return a list of alternatives
	 */
	List<INext> getNextForLabel(String lab);

	/**
	 * The initial state of the system.
	 * @return a vector of integers giving the initial values of all variables
	 */
	List<Integer> getInitial();

	/**
	 * The names of the variables of the system
	 */
	List<String> getVariableNames();
	
	/**
	 * The number of variables that this builder defines.
	 * @return the total number of variables needed to hold the state vector.
	 */
	int size();

	/**
	 * Resolve the index of a given (qualified) variable reference, with only constant array indices used. 
	 * @param vref the reference to resolve
	 * @return an index that gives access to this (qualified) variable
	 */
	public int getIndex(Reference vref);
	
	/**
	 * Factory method to obtain a INextBuilder.
	 * @param spec the specification we want semantics for
	 * @return a semantic builder correctly initialized an set up.
	 */
	static INextBuilder build(Specification spec) {
		if (spec.getMain() instanceof GALTypeDeclaration) {
			GALTypeDeclaration gal = (GALTypeDeclaration) spec.getMain();
			return new GalNextBuilder(gal, 0);
		} else if (spec.getMain() instanceof CompositeTypeDeclaration) {
			CompositeTypeDeclaration ctd = (CompositeTypeDeclaration) spec.getMain();
			return new CompositeNextBuilder(ctd, 0);
		}
		throw new UnsupportedOperationException("Unrecognized type for main instance.");
	}
	
	/**
	 * Computes the determinisitc transition relation and stores/caches  it.
	 * @return a list of sequences (stored as List) of Predicates and Assignments only. no Alt, no nestesd Seq.
	 */
	public List<List<INext>> getDeterministicNext ();

	/**
	 * Computes the deterministic transition raltion if not already available and computes read write supports of transitions. 
	 * @return a dependency matrix, indexed on getDeterministicNext().
	 */
	public DependencyMatrix getDeterministicDependencyMatrix();
}