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
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.StructuralReduction.ReductionType;
import fr.lip6.move.gal.structural.expr.AtomicProp;
import fr.lip6.move.gal.structural.expr.Expression;

public class LTLLengthAwareSolver {

	private static final int DEBUG = 0;
	private String spotPath;
	private String solverPath;
	private String workDir;
	private LTLPropertySolver ltlsolve;
	
	public LTLLengthAwareSolver(String spotPath, String solverPath, String workDir, LTLPropertySolver ltlsolve) {
		this.spotPath = spotPath;
		this.solverPath = solverPath;
		this.workDir = workDir;
		this.ltlsolve = ltlsolve;
	}
	
	public int runSLCLLTLTest(MccTranslator reader, DoneProperties doneProps)
			throws TimeoutException, LTLException {		
		SpotRunner spot = new SpotRunner(spotPath, workDir, 10);

		int solved =0;

		for (fr.lip6.move.gal.structural.Property propPN : reader.getSPN().getProperties()) {
			if (doneProps.containsKey(propPN.getName())) 
				continue;
			long time = System.currentTimeMillis();
			if (DEBUG >= 1) System.out.println("Starting run for "+propPN.getName()+" :" + SpotRunner.printLTLProperty(propPN.getBody()));
			TGBA tgba = spot.transformToTGBA(propPN);
			try {
				spot.analyzeCLSL(tgba);
			} catch (IOException e) {
				System.out.println("Warning : SL/CL computation failed.");
			}
			
			if (! tgba.isStutterInvariant() && (tgba.isCLInvariant() || tgba.isSLInvariant())) {
				// semi decision approach
				
				System.out.println("Found a " + (tgba.isSLInvariant() ? "SL":"CL") + " insensitive property : " + propPN.getName());
				
				// annotate it with Infinite Stutter Accepted Formulas
				spot.computeInfStutter(tgba);
			
				// build a new copy of the model, with only this property				
				List<AtomicProp> aps = tgba.getAPs();
					
				SparsePetriNet spn = new SparsePetriNet(reader.getSPN());
				
				// get rid of these, go for tgba
				spn.getProperties().clear();
				spn.getProperties().add(propPN.copy());
				
				StructuralReduction sr = new StructuralReduction(spn);
				
				BitSet support = new BitSet();
				for (AtomicProp ap : aps) {
					SparsePetriNet.addSupport(ap.getExpression(),support);
				}
				
				System.out.println("Support contains "+support.cardinality() + " out of " + sr.getPnames().size() + " places. Attempting structural reductions.");

				sr.setProtected(support);

				try {
					ReductionType rt = ReductionType.LI_LTL ; 
					ReachabilitySolver.applyReductions(sr, rt, solverPath, true, true);			
				} catch (GlobalPropertySolvedException gse) {
					System.out.println("Unexpected exception when reducing for LTL :" +gse.getMessage());
					gse.printStackTrace();
				}
				
				// rebuild and reinterpret the reduced net
				// index of places may have changed, formula might be syntactically simpler 
				// recompute fresh tgba with correctly indexed AP					
				List<Expression> atoms = aps.stream().map(ap -> ap.getExpression()).collect(Collectors.toList());
				List<Expression> atoms2 = spn.readFrom(sr,atoms);
				// we can maybe simplify some predicates now : apply some basic tests
				spn.removeConstantPlaces(atoms2);
				
				for (int i =0,ie=atoms.size(); i<ie; i++) {
					aps.get(i).setExpression(atoms2.get(i));
				}
				
				if (doneProps.containsKey(propPN.getName())) 
					continue;
				
				DoneProperties tmpDoneProps = new ConcurrentHashDoneProperties();
				ltlsolve.checkLTLProperty(spn.getProperties().get(0), tgba, spn, reader, tmpDoneProps , spot, time);
				if (tmpDoneProps.containsKey(propPN.getName())) {
					boolean verdict = tmpDoneProps.getValue(propPN.getName());
					if (verdict && tgba.isCLInvariant()) {
						// true formula + CL = real proof
						doneProps.put(propPN.getName(), verdict, "CL_INSENSITIVE");
						solved++;
					} else if (!verdict && tgba.isSLInvariant()) {
						// false formula + SL = real proof
						doneProps.put(propPN.getName(), verdict, "SL_INSENSITIVE");
						solved++;
					} else {
						System.out.println("CL/SL decision was in the wrong direction : "+ (tgba.isSLInvariant() ? "SL":"CL") + " + " + verdict);					
					}
				}
			}

			
		}
		return solved;
	}

	
	
}
