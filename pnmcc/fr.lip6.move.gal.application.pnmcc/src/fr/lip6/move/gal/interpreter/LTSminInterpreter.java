package fr.lip6.move.gal.interpreter;

import java.util.concurrent.Semaphore;

import org.eclipse.core.runtime.IStatus;

import fr.lip6.move.gal.InvariantProp;
import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.application.LTSminRunner;
import fr.lip6.move.gal.itscl.interprete.InterpreteBytArray;

public class LTSminInterpreter implements Runnable {

	private IStatus status;
	private boolean isLTL;
	private boolean isdeadlock;
	private InterpreteBytArray bufferWIO;
	private Property prop;
	private LTSminRunner ltsRunner;
	private boolean completeProp;

	private int nbProps = 0;
	private final Semaphore waitRunner = new Semaphore(0);
	private final Semaphore waitInterpreter = new Semaphore(1);

	public LTSminInterpreter(LTSminRunner ltSminRunner, InterpreteBytArray bufferWIO) {
		this.ltsRunner = ltSminRunner;
		this.nbProps = ltSminRunner.getSpec().getProperties().size();
		this.bufferWIO = bufferWIO;
	}

	private void waitRunner() throws InterruptedException {
		waitRunner.acquire();
	}

	public void waitInterpreter() throws InterruptedException {
		waitInterpreter.acquire();
	}

	private void notifyRunner() {
		waitInterpreter.release();
	}

	public void notifyInterpreter(boolean isdeadlock, boolean isLTL, IStatus status, Property prop) {
		this.isdeadlock = isdeadlock;
		this.isLTL = isLTL;
		this.status = status;
		this.prop = prop;
		this.completeProp = true;

		waitRunner.release();
	}

	public void notifyInterpreter(boolean isdeadlock, boolean isLTL) {

		this.isdeadlock = isdeadlock;
		this.isLTL = isLTL;
		this.completeProp = false;

		waitRunner.release();
	}

	public void run() {
		int i = 0;
		while (i++ < nbProps) {
			boolean result = false;
			try {

				waitRunner();

				String output = bufferWIO.getWrittenData();

				if (isdeadlock) {
					result = output.contains("Deadlock found") || output.contains("deadlock () found");
				} else if (isLTL) {
					// accepting cycle = counter example to
					// formula
					if (!completeProp)
						System.out.println("Runner was interrupted before completing the treatment. ");
					result = !(status.getCode() == 1); // output.toLowerCase().contains("accepting
														// cycle found")
														// ;
				} else {
					boolean hasViol = output.contains("Invariant violation");

					if (hasViol) {
						System.out.println("Found Violation");
						if (prop.getBody() instanceof ReachableProp) {
							result = true;
						} else if (prop.getBody() instanceof NeverProp) {
							result = false;
						} else if (prop.getBody() instanceof InvariantProp) {
							result = false;
						} else {
							throw new RuntimeException("Unexpected property type " + prop);
						}
					} else {
						System.out.println("Invariant validated");
						if (prop.getBody() instanceof ReachableProp) {
							result = false;

						} else if (prop.getBody() instanceof NeverProp) {
							result = true;

						} else if (prop.getBody() instanceof InvariantProp) {
							result = true;

						} else {
							throw new RuntimeException("Unexpected property type " + prop);
						}
					}
				}
				ltsRunner.addToDoneProps(prop.getName());
				notifyRunner();
				String ress = (result + "").toUpperCase();
				System.out.println(
						"FORMULA " + prop.getName() + " " + ress + " TECHNIQUES PARTIAL_ORDER EXPLICIT LTSMIN SAT_SMT");

				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
