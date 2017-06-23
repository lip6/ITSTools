package Interpreter;

import fr.lip6.move.gal.InvariantProp;
import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.itscl.modele.ItsInterpreter;

public class LTSminInterpreter extends ItsInterpreter {

	public LTSminInterpreter() {
		super(4096);
	}

	public Boolean call() throws Exception {
		boolean result;
		String output = getPout().toString();

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
		System.out.println(
				"FORMULA " + prop.getName() + " " + ress + " TECHNIQUES PARTIAL_ORDER EXPLICIT LTSMIN SAT_SMT");
		doneProps.add(prop.getName());
		return null;
	}

}
