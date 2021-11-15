package fr.lip6.move.gal.application.runner;

import java.io.IOException;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.mcc.properties.DoneProperties;

public interface IRunner {

	void interrupt();

	void join() throws InterruptedException;

	void configure(Specification z3Spec, DoneProperties doneProps) throws IOException;

	void solve(Ender application);

	void join(long millis) throws InterruptedException;
	
	
}
