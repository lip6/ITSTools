package Interpreter;

import java.util.Set;

import org.eclipse.core.runtime.IStatus;

import fr.lip6.move.gal.InvariantProp;
import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.application.LTSminRunner;
import fr.lip6.move.gal.itscl.modele.IListener;
import fr.lip6.move.gal.itscl.modele.ItsInterpreter;

public class LTSminInterpreter implements IListener{

	private IStatus status;
	private boolean isLTL;
	private boolean isdeadlock;
	private ItsInterpreter bufferWIO;
	private Property prop;
	private Set<String> doneProps;
	private LTSminRunner ltsRunner;

	public LTSminInterpreter(LTSminRunner ltSminRunner) {
		this.ltsRunner = ltSminRunner;
		this.doneProps=ltsRunner.getDoneProps();
	}

	public void configure(boolean isdeadlock, boolean isLTL, IStatus status, ItsInterpreter bufferWIO, Property prop) {
		this.isdeadlock = isdeadlock;
		this.isLTL = isLTL;
		this.status = status;
		this.bufferWIO = bufferWIO;
		this.prop = prop;
	}

	public Object call() {

		try {
			while (true) {
				boolean result;
				String output = bufferWIO.getPout().toString();
				for (String line = ""; line != null; line = bufferWIO.readLine()) {
					if (isdeadlock) {
						result = output.contains("Deadlock found") || output.contains("deadlock () found");
					} else if (isLTL) {
						// accepting cycle = counter example to
						// formula
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
					String ress = (result + "").toUpperCase();
					System.out.println("FORMULA " + prop.getName() + " " + ress
							+ " TECHNIQUES PARTIAL_ORDER EXPLICIT LTSMIN SAT_SMT");
					doneProps.add(prop.getName());
				}
			}
		} catch (Exception e) {
			System.out.println("im her in catcher ");
		} finally {
			ltsRunner.setDoneProps(doneProps);
		}
		return null;
	}

}
