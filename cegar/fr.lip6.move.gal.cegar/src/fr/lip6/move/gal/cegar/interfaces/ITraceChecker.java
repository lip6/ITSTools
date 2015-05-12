package fr.lip6.move.gal.cegar.interfaces;

import java.io.IOException;

import fr.lip6.move.gal.Specification;

public interface ITraceChecker {

	public IResult check(Specification gal, String trace[]) throws IOException ;
}
