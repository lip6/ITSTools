package fr.lip6.move.gal.application;

import java.io.IOException;
import java.util.Map;

import fr.lip6.move.gal.Specification;

public abstract class AbstractRunner implements IRunner  {

	protected Specification spec;
	protected Map<String, Boolean> doneProps;
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
	public void configure(Specification z3Spec, Map<String,Boolean> doneProps) throws IOException {
		this.spec = z3Spec ;
		this.doneProps = doneProps;
	}

}