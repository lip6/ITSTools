package fr.lip6.move.gal.cegar.checkers;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

import fr.lip6.move.gal.InvariantProp;
import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.cegar.interfaces.IAbstractor;
import fr.lip6.move.gal.cegar.interfaces.IPropertyChecker;
import fr.lip6.move.gal.cegar.interfaces.IResult;
import fr.lip6.move.gal.cegar.interfaces.ITraceChecker;

public class CEGARChecker implements IPropertyChecker {

	private final ITraceChecker traceChecker;
	private final IPropertyChecker propChecker;
	private IAbstractor abstractor;

	private final Logger log = Logger.getLogger("fr.lip6.move.gal");
	
	public CEGARChecker(IAbstractor abstractor, ITraceChecker traceChecker, IPropertyChecker propChecker) {
		this.traceChecker = traceChecker;
		this.propChecker = propChecker;
		this.abstractor = abstractor;
	}

	@Override
	public IResult check(Specification gal, Property property) throws IOException {
		log.finer("Abstracting original GAL...");

		Specification abstraction = abstractor.abstractGal();
		log.finer("Abstraction complete");

		boolean inaccurate = true;
		int cycles = 0;

		while (inaccurate) {
			log.info("Checking abstraction for property " + property.getName() + "(refined " + cycles + " times)...");

			/******************		its-reach -reachable	******************/
			log.info("Running its-reach -reachable on the abstraction...");
			IResult result = propChecker.check(abstraction, property);

			if (! result.hasTrace()) {
				// we don't have a trace to replay so we cannot deduce a transition
				//	but the system is an abstraction, more permissive than original system
				// therefore no trace exists in full system
				log.info("Abstraction procedure found a larger abstraction that does not exhibit the behavior " + property.getName());
				// diagnose
				if (property.getBody() instanceof ReachableProp) {
					// could not find trace to state satisfying
					return new CheckResult(false, false, null);
				} else if (property.getBody() instanceof NeverProp) {
					// could not find trace to state satisfying
					return new CheckResult(true, false, null);					
				} else if (property.getBody() instanceof InvariantProp) {
					// could not find counter example trace
					return new CheckResult(true, false, null);										
				}
			}
			log.info("Found trace: " + Arrays.toString(result.getTrace()));


			/****************	its-reach -trace	**************************/
			log.info("Running its-reach -trace " + Arrays.toString(result.getTrace()) + " on the original GAL...");
			IResult traceResult = traceChecker.check(gal, result.getTrace());

			if ( ! traceResult.hasTrace()) {
				log.info("A valid concrete trace has been found by model-checking the abstraction after " + cycles + " refinement steps.");
				if (property.getBody() instanceof ReachableProp) {
					// found trace to state satisfying
					return new CheckResult(true, true, result.getTrace());
				} else if (property.getBody() instanceof NeverProp) {
					// found trace to state satisfying negation
					return new CheckResult(false, true, result.getTrace());
				} else if (property.getBody() instanceof InvariantProp) {
					// found counter example trace
					return new CheckResult(false, true, result.getTrace());
				}
			} else {
				final String failedTransition = traceResult.getTrace()[0];
				log.info("Transition " + failedTransition + " couldn't be replayed on the original GAL");
				abstractor.refine(failedTransition);
				abstraction = abstractor.abstractGal();

				log.finer("Abstraction refined");
			}

			cycles++;
		}

		// for trace and debug
		// SerializationUtil.systemToFile(abstraction, WorkFoldersRepository.getInstance().getWorkFolder(gal) + "cegarResult.gal");
		
		// should not ever reach this position, we return from cases in loop
		return null;
	}
}
