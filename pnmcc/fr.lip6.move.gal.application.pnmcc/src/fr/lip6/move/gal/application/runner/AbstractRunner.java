package fr.lip6.move.gal.application.runner;

import java.io.IOException;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.mcc.properties.DoneProperties;

public abstract class AbstractRunner implements IRunner  {

	protected Specification spec;
	protected DoneProperties doneProps;
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
	public void join(long millis) throws InterruptedException {
		if (runnerThread != null) {
			runnerThread.join(millis);
		}		
	}

	
	@Override
	public void configure(Specification z3Spec, DoneProperties doneProps) throws IOException {
		this.spec = z3Spec ;
		this.doneProps = doneProps;
	}

}