package fr.lip6.move.gal.cegar.factory;

import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.cegar.abstractor.SupportBasedAbstractor;
import fr.lip6.move.gal.cegar.checkers.CEGARChecker;
import fr.lip6.move.gal.cegar.checkers.ITSLauncher;
import fr.lip6.move.gal.cegar.checkers.ITSPropertyCheckerAdapter;
import fr.lip6.move.gal.cegar.checkers.ITSTraceChecker;
import fr.lip6.move.gal.cegar.interfaces.IAbstractor;
import fr.lip6.move.gal.cegar.interfaces.ICegarFactory;
import fr.lip6.move.gal.cegar.interfaces.IPropertyChecker;
import fr.lip6.move.gal.cegar.interfaces.ITraceChecker;

public class CegarFactory implements ICegarFactory {
	
	private static ICegarFactory singleton;
	private ABSTRACTION_STRAT strategy = null;
	private ITSLauncher launcher = null;
	
	public static ICegarFactory getSingleton() {
		if (singleton == null)
			singleton = new CegarFactory();
		
		return singleton;
	}
	
	private CegarFactory() {}


	@Override
	public IPropertyChecker createCegarChecker(IAbstractor abstractor) throws CEGARFactoryException {
		ITraceChecker traceChecker = createITSTraceChecker();
		IPropertyChecker propertyChecker = createITSProprertyCheckerAdapter();
		return new CEGARChecker(abstractor, traceChecker, propertyChecker);
	}
	
	@Override
	public IPropertyChecker createCegarChecker(IAbstractor abstractor, ITraceChecker traceChecker, IPropertyChecker propChecker) throws CEGARFactoryException {		
		return new CEGARChecker(abstractor, traceChecker, propChecker);
	}

	@Override
	public IPropertyChecker createITSProprertyCheckerAdapter() throws CEGARFactoryException {
		checkLauncher();
		
		return new ITSPropertyCheckerAdapter(this.launcher);
	}

	@Override
	public ITraceChecker createITSTraceChecker() throws CEGARFactoryException {
		checkLauncher();
		
		return new ITSTraceChecker(this.launcher);
	}

	@Override
	public IAbstractor createAbstractor(Specification specification, Property property) throws CEGARFactoryException {
		checkStrategy();
		
		return new SupportBasedAbstractor(specification, property, strategy);
	}

	@Override
	public void configure(ABSTRACTION_STRAT strategy) {
		this.strategy = strategy;
	}

	@Override
	public void configure(String folder) {
		this.launcher = new ITSLauncher(folder);
	}

	@Override
	public void configure(ABSTRACTION_STRAT strategy, String folder) {
		configure(strategy);
		configure(folder);
	}
		
	private void checkStrategy() throws CEGARFactoryException {
		if(this.strategy == null)
			throw new CEGARFactoryException("Factory not configured. Set the strategy.");
	}
	
	private void checkLauncher() throws CEGARFactoryException {
		if(this.launcher == null)
			throw new CEGARFactoryException("Factory not configured. Set the launcher.");
	}
}
