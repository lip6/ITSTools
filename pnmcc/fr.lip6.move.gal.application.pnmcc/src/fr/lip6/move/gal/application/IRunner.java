package fr.lip6.move.gal.application;

import java.io.IOException;
import java.util.Set;

import fr.lip6.move.gal.Specification;

public interface IRunner {

	void interrupt();

	void join() throws InterruptedException;

	void configure(Specification z3Spec, Set<String> doneProps) throws IOException;

	void solve(Ender application);
	
	
}
