package fr.lip6.move.gal.application;

import java.io.IOException;
import java.util.BitSet;
import java.util.concurrent.TimeoutException;

import fr.lip6.ltl.tgba.AcceptedRunFoundException;
import fr.lip6.ltl.tgba.EmptyProductException;
import fr.lip6.ltl.tgba.LTLException;
import fr.lip6.ltl.tgba.RandomProductWalker;
import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.GlobalPropertySolvedException;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.StructuralReduction.ReductionType;

public class LTLPropertySolver {

	private String spotPath;
	private String solverPath;
	private String workDir;
	private boolean exportLTL;

	public LTLPropertySolver(String spotPath, String solverPath, String workDir, boolean exportLTL) {
		this.spotPath = spotPath;
		this.solverPath = solverPath;
		this.workDir = workDir;
		this.exportLTL = exportLTL;
	}

	public int runStructuralLTLCheck(MccTranslator reader, boolean isSafe, DoneProperties doneProps)
			throws IOException, TimeoutException, LTLException {
		int solved =0;
		if (reader.getHLPN() != null) {
			if (exportLTL) {					
				SpotRunner.exportLTLProperties(reader.getHLPN(),"col",workDir);
			}
			solved += ReachabilitySolver.checkInInitial(reader.getHLPN(),doneProps);
			SpotRunner sr = new SpotRunner(spotPath, workDir, 10);
			sr.runLTLSimplifications(reader.getHLPN());
			if (exportLTL) {					
				SpotRunner.exportLTLProperties(reader.getHLPN(),"colred",workDir);
			}
		}
		reader.createSPN();
		solved += ReachabilitySolver.checkInInitial(reader.getSPN(),doneProps);
		if (exportLTL) {
			SpotRunner.exportLTLProperties(reader.getSPN(),"raw",workDir);
		}
	
		if (spotPath != null) {
			SpotRunner sr = new SpotRunner(spotPath, workDir, 10);
			sr.runLTLSimplifications(reader.getSPN());
		}
		
		solved += ReachabilitySolver.checkInInitial(reader.getSPN(),doneProps);
		
		if (reader.getSPN().getProperties().isEmpty()) {
			System.out.println("All properties solved without resorting to model-checking.");
			return solved;
		}
		
		solved += new AtomicReducerSR().strongReductions(solverPath, reader, isSafe, doneProps);
		solved += ReachabilitySolver.checkInInitial(reader.getSPN(),doneProps);
	
		solved += GALSolver.runGALReductions(reader, isSafe, doneProps);
		
		reader.getSPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
		if (spotPath != null) {
			SpotRunner sr = new SpotRunner(spotPath, workDir, 10);
			sr.runLTLSimplifications(reader.getSPN());
		}
		runStutteringLTLTest(reader, doneProps, workDir, spotPath);
			
		reader.getSPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
		return solved;
	}

	public void runStutteringLTLTest(MccTranslator reader, DoneProperties doneProps, String pwd, String spotPath)
			throws TimeoutException, LTLException {
		SpotRunner spot = new SpotRunner(spotPath, pwd, 10);
		
		
		
		for (fr.lip6.move.gal.structural.Property propPN : reader.getSPN().getProperties()) {
			if (doneProps.containsKey(propPN.getName())) 
				continue;
			
			TGBA tgba = spot.transformToTGBA(propPN);
			if (tgba.getProperties().contains("stutter-invariant")) {
				
				SparsePetriNet spn = new SparsePetriNet(reader.getSPN());
				spn.getProperties().clear();
				spn.getProperties().add(propPN.copy());
				// ok let's reduce the system for this property 
				StructuralReduction sr = new StructuralReduction(spn);
				BitSet support = spn.computeSupport();
				System.out.println("Support contains "+support.cardinality() + " out of " + sr.getPnames().size() + " places. Attempting structural reductions.");
				
				sr.setProtected(support);
				try {
					sr.reduce(ReductionType.SI_LTL);
					spn.readFrom(sr);
					spn.testInInitial();
					spn.removeConstantPlaces();
					spn.simplifyLogic();
					
					tgba = spot.transformToTGBA(spn.getProperties().get(0));
					spot.computeInfStutter(tgba);
					
					RandomProductWalker pw = new RandomProductWalker(spn);
					
					try {
						System.out.println("Running random walk in product with property : " + propPN.getName() + " automaton " + tgba);
						pw.runProduct(tgba , 10000, 10);
					} catch (AcceptedRunFoundException a) {
						doneProps.put(propPN.getName(), false, "STUTTER_TEST");
					} catch (EmptyProductException e2) {
						doneProps.put(propPN.getName(), true, "STRUCTURAL INITIAL_STATE");
					}
	
				} catch (GlobalPropertySolvedException gse) {
					System.out.println("Unexpected exception when reducting for LTL :" +gse.getMessage());
				}
			} else {
				spot.computeInfStutter(tgba);
				RandomProductWalker pw = new RandomProductWalker(reader.getSPN());
				
				try {
					System.out.println("Running random walk in product with property : " + propPN.getName() + " automaton " + tgba);
					pw.runProduct(tgba , 10000, 10);
				} catch (AcceptedRunFoundException a) {
					doneProps.put(propPN.getName(), false, "STUTTER_TEST");
				} catch (EmptyProductException e2) {
					doneProps.put(propPN.getName(), true, "STRUCTURAL INITIAL_STATE");
				}
			}
			
		}
		
		
		
	
	}

}
