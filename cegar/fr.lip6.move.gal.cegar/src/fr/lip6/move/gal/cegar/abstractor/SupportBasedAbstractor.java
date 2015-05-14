package fr.lip6.move.gal.cegar.abstractor;

import java.util.logging.Logger;

import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.cegar.factory.ABSTRACTION_STRAT;
import fr.lip6.move.gal.cegar.interfaces.IAbstractor;
import fr.lip6.move.gal.cegar.support.InexistantTransitionException;
import fr.lip6.move.gal.cegar.support.SupportManager;
import fr.lip6.move.gal.instantiate.BoundsBuilder;
import fr.lip6.move.gal.support.Support;

public class SupportBasedAbstractor implements IAbstractor {

	private final Specification original;
	private final SupportManager supportManager;
	private final Property property;
	private final ABSTRACTION_STRAT strategy;
	private static Logger log = Logger.getLogger("fr.lip6.move.gal");

	public SupportBasedAbstractor(Specification specification, Property property, ABSTRACTION_STRAT strategy) {
		this.supportManager = new SupportManager(specification);
		this.original = specification;
		this.property = property;
		this.strategy = strategy;
	}

	@Override
	public Specification abstractGal() {
		Specification workingCopy = EcoreUtil.copy(this.original);		
		Support toKeep = supportManager.getPropertySupport(property).adaptTo(workingCopy);
		abstractUsingSupport(workingCopy, toKeep,supportManager);
		return workingCopy;
	}

	@Override
	public void refine(String failedTrans) {
		switch(this.strategy) {
			case TRACE:
				log.info("Using failed transition "+ failedTrans + " to refine the abstraction...");
				try {
					supportManager.refineSupport(property, failedTrans);
				} catch (InexistantTransitionException e) {
					e.printStackTrace();
					log.severe("Tried to access inexisting transition '" + e.getMessage() + "'");
				}		
				break;
				
			case DEPTH:
				log.info("Increasing the depth in order to refine the abstraction...");
				this.supportManager.refineSupport(property);
				break;
				
			case OTHER:
				// TODO ?
		}
	}
	
	private void abstractUsingSupport(Specification workingCopy, Support toKeep, SupportManager sm) {
		int bound = BoundComputer.compute(workingCopy);
		TransitionsAbstractor.abstractUsingSupport(workingCopy, toKeep,sm);
		VariablesAbstractor.abstractUsingSupport(workingCopy, toKeep);
		BoundsBuilder.boundVariable(workingCopy, Math.min(bound+1, 3));
		//BoundApplier.apply(workingCopy, bound);	
	}
}
