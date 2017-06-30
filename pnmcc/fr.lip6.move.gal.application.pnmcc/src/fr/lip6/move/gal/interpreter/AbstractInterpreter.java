package fr.lip6.move.gal.interpreter;

import java.util.concurrent.Semaphore;

public abstract class AbstractInterpreter implements IInterpreter {
	private Semaphore hasComplete = new Semaphore(0);

	public void acquireResult() {
		try {
			hasComplete.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void releaseResult(){
		hasComplete.release();
	}

}
