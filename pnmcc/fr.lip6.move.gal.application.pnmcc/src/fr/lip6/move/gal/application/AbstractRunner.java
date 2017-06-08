package fr.lip6.move.gal.application;

import java.io.IOException;
import java.util.Set;

import fr.lip6.move.gal.Specification;

public abstract class AbstractRunner implements IRunner  {

	protected Specification spec;
	protected Set<String> doneProps;
	protected Thread runnerThread;
	public AbstractRunner() {
		super();
	}

	@Override
	public void interrupt() {
		if (runnerThread != null) {
			runnerThread.interrupt();
		}
	}

	@Override
	public void join() throws InterruptedException {
		if (runnerThread != null) {
			runnerThread.join();
		}		
	}

	@Override
	public void configure(Specification z3Spec, Set<String> doneProps) throws IOException {
		this.spec = z3Spec ;
		this.doneProps = doneProps;
	}

}