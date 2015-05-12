package fr.lip6.move.gal.cegar.interfaces;

import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.cegar.support.InexistantTransitionException;

public interface IPropertySupports {
	public IPropertySupport getPropertySupport(Property prop);
	public void refineSupport(Property prop, String failedTransition) throws InexistantTransitionException;
	public void refineSupport(Property prop);

}
