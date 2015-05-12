package fr.lip6.move.gal.cegar.interfaces;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.support.Support;

public interface IPropertySupport {
	public Support adaptTo(Specification spec);	
	public void refine(Support toAdd);
	public int getIncreasedDepth();
}
