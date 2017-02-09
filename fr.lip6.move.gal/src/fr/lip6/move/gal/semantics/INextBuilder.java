package fr.lip6.move.gal.semantics;

import java.util.List;

import fr.lip6.move.gal.CompositeTypeDeclaration;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Specification;

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
	 * The number of variables that this builder defines.
	 * @return the total number of variables needed to hold the state vector.
	 */
	int size();

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

}