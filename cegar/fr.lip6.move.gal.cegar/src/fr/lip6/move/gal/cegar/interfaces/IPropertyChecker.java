package fr.lip6.move.gal.cegar.interfaces;

import java.io.IOException;

import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;

public interface IPropertyChecker {
	public IResult check(Specification gal, Property property) throws IOException ;
}
