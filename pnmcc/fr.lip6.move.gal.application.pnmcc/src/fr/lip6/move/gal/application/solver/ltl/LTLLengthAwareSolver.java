package fr.lip6.move.gal.application.solver.ltl;

import java.io.IOException;
import java.util.BitSet;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import fr.lip6.ltl.tgba.LTLException;
import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.move.gal.application.mcc.MccTranslator;
import fr.lip6.move.gal.application.runner.spot.SpotRunner;
import fr.lip6.move.gal.application.solver.ReachabilitySolver;
import fr.lip6.move.gal.mcc.properties.ConcurrentHashDoneProperties;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.GlobalPropertySolvedException;
import fr.lip6.move.gal.structural.PetriNet;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.StructuralReduction.ReductionType;
import fr.lip6.move.gal.structural.expr.AtomicProp;
import fr.lip6.move.gal.structural.expr.Expression;

/**
 * A solver realizing the FORTE'22 strategy to verify shortening or lengthening insensitive formulas (which are not both) using a reduction of the net.
 * Using Spot it computes the sensitivity, if appropriate it then reduces the model with mode LI_LTL and analyzes the resulting model.
 * When the polarity of the result is in the right direction, we can conclude. Otherwise we still need to look at full model.
 * Based on :
 * Emmanuel Paviot-Adet, Denis Poitrenaud, Etienne Renault, Yann Thierry-Mieg:
 * LTL Under Reductions with Weaker Conditions Than Stutter Invariance. FORTE 2022: 170-187
 * @author ythierry
 *
 */
public class LTLLengthAwareSolver {

	private static final int DEBUG = 0;
	private LTLPropertySolver ltlsolve;

	/**
	 * Provide the context, and the LTLSolver instance used to try to solve properties on the reduced model.
	 * @param ltlsolve the solver we delegate the reduced model solution to.
	 * @param spotPath to create a SpotRunner
	 * @param workDir where we are working
	 */
	public LTLLengthAwareSolver(LTLPropertySolver ltlsolve) {
		this.ltlsolve = ltlsolve;
	}

	/**
	 * Try to solve with FORTE'22 strategy.
	 * @param reader
	 * @param doneProps
	 * @return
	 * @throws TimeoutException
	 * @throws LTLException
	 */
	public int runSLCLLTLTest(MccTranslator reader, DoneProperties doneProps)
			throws TimeoutException, LTLException {
		SpotRunner spot = new SpotRunner(10);

		int solved =0;

		// run on each property
		for (fr.lip6.move.gal.structural.Property propPN : reader.getSPN().getProperties()) {

			// some strategy running in parallel might have solved this one
			if (doneProps.containsKey(propPN.getName()))
				continue;

			long time = System.currentTimeMillis();
			if (DEBUG >= 1) System.out.println("Starting run for "+propPN.getName()+" :" + SpotRunner.printLTLProperty(propPN.getBody()));

			// analyze sensitivity using spot + get a TGBA
			TGBA tgba = spot.transformToTGBA(propPN);
			try {
				spot.analyzeCLSL(tgba);
			} catch (IOException e) {
				System.out.println("Warning : SL/CL computation failed.");
			}

			if (! tgba.isStutterInvariant() && (tgba.isCLInvariant() || tgba.isSLInvariant())) {
				// trigger semi decision approach

				System.out.println("Found a " + (tgba.isSLInvariant() ? "Lengthening":"Shortening") + " insensitive property : " + propPN.getName());

				
				// annotate it with Infinite Stutter Accepted Formulas
				spot.computeInfStutter(tgba);

				// build a new copy of the model
				SparsePetriNet spn = ltlsolve.reduceForProperty(reader.getSPN(), tgba, ReductionType.LI_LTL, propPN);

				// we might have solved the property syntactically thanks to reductions.
				if (doneProps.containsKey(propPN.getName()))
					continue;

				// run our usual decision procedures on the reduced model
				// capture results in tmp rather than printing results immediately
				DoneProperties tmpDoneProps = new ConcurrentHashDoneProperties();
				ltlsolve.checkLTLProperty(spn.getProperties().get(0), tgba, spn, reader, tmpDoneProps , spot, time);

				// could we solve the reduced model ?
				if (tmpDoneProps.containsKey(propPN.getName())) {
					boolean verdict = tmpDoneProps.getValue(propPN.getName());

					// is it in the right polarity to be trusted ?
					if (verdict && tgba.isCLInvariant()) {
						// true formula + CL = real proof
						doneProps.put(propPN.getName(), verdict, "SHORTENING_INSENSITIVE");
						solved++;
					} else if (!verdict && tgba.isSLInvariant()) {
						// false formula + SL = real proof
						doneProps.put(propPN.getName(), verdict, "LENGTHENING_INSENSITIVE");
						solved++;
					} else {
						System.out.println("Length sensitive decision was in the wrong direction : "+ (tgba.isSLInvariant() ? "Lengthening":"Shortening") + " insensitive + " + verdict);
					}
				}
			}
		} // foreach property

		return solved;
	}



}
