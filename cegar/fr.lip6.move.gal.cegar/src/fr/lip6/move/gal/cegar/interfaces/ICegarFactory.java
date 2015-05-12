package fr.lip6.move.gal.cegar.interfaces;

import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.cegar.factory.ABSTRACTION_STRAT;
import fr.lip6.move.gal.cegar.factory.CEGARFactoryException;

/**
 * The Factory for Cegar operations.
 * Provides constructions methods for different Objects necessary in the CEGAR process. Depending on the method, the Factory may have to be configured.
 * @author Anonymous
 *
 */
public interface ICegarFactory {

	/**
	 * The singleton instance for the factory.
	 */
	public static ICegarFactory getSingleton = fr.lip6.move.gal.cegar.factory.CegarFactory.getSingleton();
	
	public IPropertyChecker createCegarChecker(IAbstractor abstractor, ITraceChecker traceChecker, IPropertyChecker propChecker) throws CEGARFactoryException;
	public IPropertyChecker createCegarChecker(IAbstractor abstractor) throws CEGARFactoryException;
	public IPropertyChecker createITSProprertyCheckerAdapter() throws CEGARFactoryException;
	public ITraceChecker createITSTraceChecker() throws CEGARFactoryException;
	public IAbstractor createAbstractor(Specification specification, Property property) throws CEGARFactoryException;
	
	/**
	 * Configure the Factory for the specified strategy.
	 * 
	 * @param strategy
	 */
	public void configure(ABSTRACTION_STRAT strategy);
	
	/**
	 * Configure the Factory for the specified folder and project.
	 * 
	 * @param modelff
	 */
	public void configure(String folder);
	
	/**
	 * Configure the Factory for the specified strategy, folder and project.
	 * 
	 * @param strategy
	 * @param folder
	 */
	public void configure(ABSTRACTION_STRAT strategy, String folder);
	
}
