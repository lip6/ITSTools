package fr.lip6.move.gal.application.solver.ltl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import android.util.SparseIntArray;
import fr.lip6.ltl.tgba.AcceptedRunFoundException;
import fr.lip6.ltl.tgba.EmptyProductException;
import fr.lip6.ltl.tgba.LTLException;
import fr.lip6.ltl.tgba.RandomProductWalker;
import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.ltl.tgba.TGBAEdge;
import fr.lip6.move.gal.application.mcc.MccTranslator;
import fr.lip6.move.gal.application.runner.spot.SpotRunner;
import fr.lip6.move.gal.application.solver.GALSolver;
import fr.lip6.move.gal.application.solver.ReachabilitySolver;
import fr.lip6.move.gal.application.solver.global.GlobalPropertySolver;
import fr.lip6.move.gal.application.solver.logic.AtomicReducerSR;
import fr.lip6.move.gal.mcc.properties.ConcurrentHashDoneProperties;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.DeadlockFound;
import fr.lip6.move.gal.structural.FlowPrinter;
import fr.lip6.move.gal.structural.GlobalPropertySolvedException;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.StructuralReduction.ReductionType;
import fr.lip6.move.gal.structural.WalkUtils;
import fr.lip6.move.gal.structural.expr.AtomicProp;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.expr.Simplifier;
import fr.lip6.move.gal.structural.smt.DeadlockTester;

public class LTLPropertySolver {

	private static final int NBSTEPS = 100000;
	private static final int DEBUG = 0;
	private String spotPath;
	private String solverPath;
	private String workDir;
	private boolean exportLTL;
	public static boolean noSLCLtest=false;
	public static boolean noKnowledgetest=false;
	public static boolean noStutterTest=false;

	public LTLPropertySolver(String spotPath, String solverPath, String workDir, boolean exportLTL) {
		this.spotPath = spotPath;
		this.solverPath = solverPath;
		this.workDir = workDir;
		this.exportLTL = exportLTL;
	}

	public int runStructuralLTLCheck(MccTranslator reader, DoneProperties doneProps)
			throws IOException, TimeoutException, LTLException {
		int solved = preSolveForLogic(reader, doneProps, true);
		if (reader.getSPN().getProperties().isEmpty()) {
			return solved;
		}
		
		runStutteringLTLTest(reader, doneProps);

		reader.getSPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
		return solved;
	}

	public int preSolveForLogic(MccTranslator reader, DoneProperties doneProps, boolean isLTL)
			throws IOException, TimeoutException {
		int solved = 0;
		if (reader.getHLPN() != null) {
			if (isLTL && exportLTL) {					
				SpotRunner.exportLTLProperties(reader.getHLPN(),"col",workDir);
			}
			solved += ReachabilitySolver.checkInInitial(reader.getHLPN(),doneProps);
			if (isLTL) {
				SpotRunner sr = new SpotRunner(spotPath, workDir, 10);
				sr.runLTLSimplifications(reader.getHLPN());
			}
			if (isLTL && exportLTL) {					
				SpotRunner.exportLTLProperties(reader.getHLPN(),"colred",workDir);
			}
			SparsePetriNet skel = reader.getHLPN().skeleton();
			skel.getProperties().removeIf(p -> ! Simplifier.allEnablingsAreNegated(p.getBody()));
			reader.setSpn(skel,true);
			ReachabilitySolver.checkInInitial(reader.getSPN(), doneProps);
			new AtomicReducerSR().strongReductions(solverPath, reader, doneProps);
			reader.getSPN().simplifyLogic();
			ReachabilitySolver.checkInInitial(reader.getSPN(), doneProps);
			reader.rebuildSpecification(doneProps);
			GALSolver.checkInInitial(reader.getSpec(), doneProps, reader.getSPN().isSafe());
			reader.flattenSpec(false);
			GALSolver.checkInInitial(reader.getSpec(), doneProps, reader.getSPN().isSafe());
			ReachabilitySolver.checkInInitial(reader.getHLPN(), doneProps);
		}
		reader.createSPN();
		solved += ReachabilitySolver.checkInInitial(reader.getSPN(),doneProps);
		if (isLTL && exportLTL) {
			SpotRunner.exportLTLProperties(reader.getSPN(),"raw",workDir);
		}

		if (spotPath != null && isLTL) {
			SpotRunner sr = new SpotRunner(spotPath, workDir, 10);
			sr.runLTLSimplifications(reader.getSPN());
		}

		solved += ReachabilitySolver.checkInInitial(reader.getSPN(),doneProps);

		if (reader.getSPN().getProperties().isEmpty()) {
			System.out.println("All properties solved without resorting to model-checking.");
			return solved;
		}

		{
			// ok let's reduce the system for LTL with combined support 
			StructuralReduction sr = new StructuralReduction(reader.getSPN());
			BitSet support = reader.getSPN().computeSupport();
			System.out.println("Support contains "+support.cardinality() + " out of " + sr.getPnames().size() + " places. Attempting structural reductions.");
			sr.setProtected(support);
			try {
				ReachabilitySolver.applyReductions(sr, ReductionType.LTL, solverPath, true, true);				
				reader.getSPN().readFrom(sr);
			} catch (GlobalPropertySolvedException gse) {
				System.out.println("Unexpected exception when reducting for LTL :" +gse.getMessage());
				gse.printStackTrace();
			}
		}
		solved += ReachabilitySolver.checkInInitial(reader.getSPN(),doneProps);
		solved += GALSolver.runGALReductions(reader, doneProps);
		solved += ReachabilitySolver.checkInInitial(reader.getSPN(),doneProps);					
		solved += new AtomicReducerSR().strongReductions(solverPath, reader, doneProps);
		reader.getSPN().simplifyLogic();
		solved += reader.getSPN().testInInitial();
		solved += ReachabilitySolver.checkInInitial(reader.getSPN(),doneProps);
		
		
		reader.getSPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
		
		if (isLTL && spotPath != null) {
			SpotRunner sr = new SpotRunner(spotPath, workDir, 10);
			sr.runLTLSimplifications(reader.getSPN());
		}
		solved += ReachabilitySolver.checkInInitial(reader.getSPN(),doneProps);
		return solved;
	}
	
	public int runSLCLLTLTest(MccTranslator reader, DoneProperties doneProps)
			throws TimeoutException, LTLException {
		if (noSLCLtest) {
			return 0;
		}
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
				
				System.out.println("Found a " + (tgba.isSLInvariant() ? "SL":"CL") + " insensitive property : " + propPN.getName() + ":" + propPN.getBody());
				
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
					ReductionType rt = ReductionType.SLCL_LTL ; 
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
				checkLTLProperty(spn.getProperties().get(0), tgba, spn, reader, tmpDoneProps , spot, time);
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
	

	public void runStutteringLTLTest(MccTranslator reader, DoneProperties doneProps)
			throws TimeoutException, LTLException {
		
		
		SpotRunner spot = new SpotRunner(spotPath, workDir, 10);



		for (fr.lip6.move.gal.structural.Property propPN : reader.getSPN().getProperties()) {
			if (doneProps.containsKey(propPN.getName())) 
				continue;
			long time = System.currentTimeMillis();
			if (DEBUG >= 1) System.out.println("Starting run for "+propPN.getName()+" :" + SpotRunner.printLTLProperty(propPN.getBody()));
			TGBA tgba = spot.transformToTGBA(propPN);


			SparsePetriNet spnForProp = reduceForProperty(reader.getSPN(), tgba, propPN);

			// annotate it with Infinite Stutter Accepted Formulas
			spot.computeInfStutter(tgba);

			
			checkLTLProperty(spnForProp.getProperties().get(0), tgba, spnForProp, reader, doneProps, spot, time);
		}
	}

	private void checkLTLProperty(fr.lip6.move.gal.structural.Property propPN, TGBA tgba, SparsePetriNet spnForProp,
			MccTranslator reader, DoneProperties doneProps, SpotRunner spot, long time)
			throws LTLException, TimeoutException {
		try {
			if (DEBUG >= 2) FlowPrinter.drawNet(spnForProp,"For product with " + propPN.getName());
			// walk the product a bit
			if (doneProps.containsKey(propPN.getName())) 
				return;
			
			if (! noStutterTest) {
				System.out.println("Running random walk in product with property : " + propPN.getName() + " automaton " + tgba);
				RandomProductWalker pw = new RandomProductWalker(spnForProp,tgba);
				pw.runProduct(NBSTEPS, 10, false);
				if (doneProps.containsKey(propPN.getName())) 
					return;
				
				pw.runProduct(NBSTEPS, 10, true);
				if (doneProps.containsKey(propPN.getName())) 
					return;
				
			}
			// so we couldn't find a counter example, let's reflect upon this fact.
			TGBA tgbak = applyKnowledgeBasedReductions(spnForProp,tgba, spot, propPN);				
			
			SparsePetriNet spnForPropWithK;
			if (tgbak != tgba) {
				spnForPropWithK = reduceForProperty(spnForProp, tgbak,
						spnForProp.getProperties().isEmpty() ? propPN : spnForProp.getProperties().get(0));
			} else {
				spnForPropWithK = spnForProp;
			}
			
			if (doneProps.containsKey(propPN.getName())) 
				return;
			

			if (DEBUG >= 2) FlowPrinter.drawNet(spnForPropWithK,"For product with " + propPN.getName());
			// index of places may have changed, formula might be syntactically simpler 
			// annotate it with Infinite Stutter Acceped Formulas
			spot.computeInfStutter(tgbak);
			if (!noStutterTest) {
				RandomProductWalker pw = new RandomProductWalker(spnForPropWithK,tgbak);
				pw.runProduct(NBSTEPS, 10, false);
				pw.runProduct(NBSTEPS, 10, true);
			}
			
			if (! tgbak.isStutterInvariant()) {
				treatPartialPOR(tgbak, spnForPropWithK, spot);
			}
			
			
			if (doneProps.containsKey(propPN.getName())) 
				return;
			
			// Last step, try exhaustive methods
			
			MccTranslator reader2 = reader.copy();
			if (spnForPropWithK.getProperties().isEmpty()) {
				spnForProp.getProperties().add(propPN);
				// we killed it due to alphabet differences
				StructuralReduction sr = new StructuralReduction(spnForProp);
				
				BitSet support = spnForProp.computeSupport();					
				sr.setProtected(support);
				try {
					ReductionType rt = tgba.isStutterInvariant() ? ReductionType.SI_LTL : ReductionType.LTL; 
					ReachabilitySolver.applyReductions(sr, rt, solverPath, true, true);			
				} catch (GlobalPropertySolvedException gse) {
					System.out.println("Unexpected exception when reducing for LTL :" +gse.getMessage());
					gse.printStackTrace();
				}
				spnForProp.readFrom(sr);				
				reader2.setSpn(spnForProp, true);
			} else {
				reader2.setSpn(spnForPropWithK, true);
			}
			// 15 seconds timeout, just treat the fast ones.
			GlobalPropertySolver.verifyWithSDD(reader2, doneProps, "LTL", solverPath, 15);
			
		} catch (AcceptedRunFoundException a) {
			doneProps.put(propPN.getName(), false, "STUTTER_TEST");
		} catch (EmptyProductException e2) {
			doneProps.put(propPN.getName(), true, "STRUCTURAL INITIAL_STATE");
		}
		System.out.println("Treatment of property "+propPN.getName()+" finished in "+(System.currentTimeMillis()-time)+" ms.");
	}

	private void treatPartialPOR(TGBA tgbak, SparsePetriNet spnForPropWithK, SpotRunner spot) throws LTLException {
		RandomProductWalker pw;
		// go for PPOR
		try {
			TGBA tgbappor = spot.computeForwardClosedSI(tgbak);

			boolean canWork = false;
			boolean[] stm = tgbappor.getStutterMarkers();
			// check that there are stutter invariant states
			for (int q=0; q < tgbappor.nbStates() ; q++) {
				if (stm[q] && ! isFullAccept(tgbappor.getEdges().get(q),tgbappor.getNbAcceptance())) {
					canWork = true;
					break;
				}
			}
			
			if (canWork) {
				System.out.println("Applying partial POR strategy " + Arrays.toString(stm));
				spot.computeInfStutter(tgbappor);
				// build the reduced system and TGBA
				SparsePetriNet spnredSI = new SparsePetriNet(spnForPropWithK);
				spnredSI.getProperties().clear();

				{
					StructuralReduction sr = buildReduced(spnredSI, true, tgbappor.getAPs(),true);
					
					// rebuild and reinterpret the reduced net
					// index of places may have changed, formula might be syntactically simpler 
					// recompute fresh tgba with correctly indexed AP					
					List<Expression> atoms = tgbappor.getAPs().stream().map(ap -> ap.getExpression()).collect(Collectors.toList());
					List<Expression> atomsred = spnredSI.readFrom(sr,atoms);
					
					if (DEBUG >= 2) {
						Set<Integer> hl = new HashSet<>();
						BitSet bs = sr.getTokeepImages();
						
						for (int i = bs.nextSetBit(0); i >= 0; i = bs.nextSetBit(i+1)) {
							hl.add(i);
						}
						FlowPrinter.drawNet(sr, solverPath, hl, new HashSet<>());
					}
					
					pw = new RandomProductWalker(spnForPropWithK, sr, tgbappor, atomsred);
					
					pw.runProduct(NBSTEPS, 10, false);
					pw.runProduct(NBSTEPS, 10, true);
				}
				
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean isFullAccept(List<TGBAEdge> list, int nbacc) {
		for (TGBAEdge e : list) {
			if (e.getSrc() == e.getDest() && e.getAcceptance().size() == nbacc && e.getCondition().getOp() == Op.BOOLCONST && e.getCondition().getValue()==1) {
				return true;
			}
		}
		return false;
	}

	private SparsePetriNet reduceForProperty(SparsePetriNet orispn, TGBA tgba, Property propPN) {
		// build a new copy of the model, with only this property				
		List<AtomicProp> aps = tgba.getAPs();
		boolean isStutterInv = tgba.isStutterInvariant();
		
		SparsePetriNet spn = new SparsePetriNet(orispn);
		spn.getProperties().clear();
		if (propPN != null)
			spn.getProperties().add(propPN.copy());

		{
			StructuralReduction sr = buildReduced(spn, isStutterInv, aps, false);
			
			// rebuild and reinterpret the reduced net
			// index of places may have changed, formula might be syntactically simpler 
			// recompute fresh tgba with correctly indexed AP					
			List<Expression> atoms = aps.stream().map(ap -> ap.getExpression()).collect(Collectors.toList());
			List<Expression> atoms2 = spn.readFrom(sr,atoms);
			for (int i =0,ie=atoms.size(); i<ie; i++) {
				aps.get(i).setExpression(atoms2.get(i));
			}
		}
		// we can maybe simplify some predicates now : apply some basic tests
		spn.testInInitial();
		spn.removeConstantPlaces();
		spn.simplifyLogic();
		return spn;
	}

	private StructuralReduction buildReduced(SparsePetriNet spn, boolean isStutterInv, List<AtomicProp> aps, boolean keepImage) {
		// ok let's reduce the system for this property 
		StructuralReduction sr = new StructuralReduction(spn);
		// whether we want to build and store the image function, for dynamic product approaches
		sr.setKeepImage(keepImage);
		
		// compute the support deriving from the AP
		BitSet support = new BitSet();
		for (AtomicProp ap : aps) {
			SparsePetriNet.addSupport(ap.getExpression(),support);
		}
		System.out.println("Support contains "+support.cardinality() + " out of " + sr.getPnames().size() + " places. Attempting structural reductions.");
		
		if (! spn.getProperties().isEmpty()) {
			// check whether the LTL property support agrees with the AP support of the TGBA
			BitSet supportForProp = spn.computeSupport();
			if (! supportForProp.equals(support)) {
				System.out.println("Property had overlarge support with respect to TGBA, discarding it for now.");
				//spn.getProperties().clear();
				// TODO : patch this once we have TGBA support in tools
				support = supportForProp;
			}			
		}
		sr.setProtected(support);
		try {
			ReductionType rt = isStutterInv ? ReductionType.SI_LTL : ReductionType.LTL ; 
			ReachabilitySolver.applyReductions(sr, rt, solverPath, true, true);			
		} catch (GlobalPropertySolvedException gse) {
			System.out.println("Unexpected exception when reducing for LTL :" +gse.getMessage());
			gse.printStackTrace();
		}
		return sr;
	}

	private TGBA applyKnowledgeBasedReductions(SparsePetriNet spn, TGBA tgba, SpotRunner spot, Property propPN) throws LTLException, TimeoutException {

		if (noKnowledgetest) {
			return tgba;
		}
		
		// cheap knowledge 
		List<Expression> knowledge = new ArrayList<>(); 

		addConvergenceKnowledge(knowledge, spn, tgba, spn.isSafe());

		addInitialStateKnowledge(knowledge, spn, tgba);

		addNextStateKnowledge(knowledge, spn, tgba);
		
		System.out.println("Knowledge obtained : " + knowledge);

		// try to reduce the tgba using this knowledge
		if (false)
			tgba = manuallyIntegrateKnowledge(spn, tgba, knowledge, propPN, spot);
		else
			tgba = spotIntegrateKnowledge(spn, tgba, knowledge, propPN, spot);
			
		return tgba;

	}

	private TGBA spotIntegrateKnowledge(SparsePetriNet spn, TGBA tgba, List<Expression> knowledge, Property propPN,
			SpotRunner spot) throws TimeoutException, EmptyProductException {
		
		long time = System.currentTimeMillis();
		int oriAlphabetSize = tgba.getAPs().size();
		int oriNbStates = tgba.getEdges().size();
		int oriNbEdge = tgba.getEdges().stream().mapToInt(List::size).sum();
		TGBA tgbaOri = tgba;
		
		for (Expression factoid : knowledge) {
			tgba = spot.givenThat(tgba, factoid, SpotRunner.GivenStrategy.RESTRICT);
			if (tgba.getEdges().size() == 1 && tgba.getEdges().get(0).isEmpty()) {
				System.out.println("Property proved to be true thanks to knowledge :" + factoid);
				break;
			}
		}

		if (!(tgba.getEdges().size() == 1 && tgba.getEdges().get(0).isEmpty())) {
			for (Expression factoid : knowledge) {
				tgba = spot.givenThat(tgba, factoid, SpotRunner.GivenStrategy.RELAX);
				if (tgba.getEdges().size() == 1 && tgba.getEdges().get(0).isEmpty()) {
					System.out.println("Property proved to be true thanks to knowledge :" + factoid);
					break;
				}
			}
		}
		
		System.out.println("Knowledge based reduction with " + knowledge.size() + " factoid took "
				+ (System.currentTimeMillis() - time) + " ms. Reduced automaton from " + oriNbStates + " states, "
				+ oriNbEdge + " edges and " + oriAlphabetSize + " AP to " + tgba.getEdges().size() + " states, "
				+ tgba.getEdges().stream().mapToInt(List::size).sum() + " edges and " + tgba.getAPs().size() + " AP.");		

		if (tgba.getEdges().size() == 1 && tgba.getEdges().get(0).isEmpty()) {
			throw new EmptyProductException();
		}
		
		spot.computeInfStutter(tgba);
		spot.runLTLSimplifications(spn);
		
		return tgba;
	}

	public TGBA manuallyIntegrateKnowledge(SparsePetriNet spn, TGBA tgba, List<Expression> knowledge, Property propPN,
			SpotRunner spot) throws AcceptedRunFoundException, EmptyProductException, TimeoutException {
		boolean needRebuild = true;
		boolean wasAdopted = false;
		for (Expression factoid : knowledge) {
			String ltl = SpotRunner.printLTLProperty(factoid);

			try {
				// need to complement tgba				

				File comp = Files.createTempFile("comp", ".hoa").toFile();
				if (needRebuild) {
					if (! spot.buildComplement(tgba, comp)) {
						// failure of Spot ?
						continue;
					}				
				}
				// test inclusion : Knowledge dominates the formula
				// i.e. A is a subset of K
				// therefore !K*A = 0
				//				if (sr.isProductEmpty(comp,"!(" +ltl + ")")) {
				//					// property is true, negation is empty
				//					System.out.println("Property (complement) proved to be true thanks to knowledge :" + factoid);
				//					return TGBA.makeFalse(); 
				//				}

				// test disjoint : A * K is empty
				// therefore, A does not cover K => does not cover S
				// we have empty product with !A.
				if (spot.isProductEmpty(comp,ltl)) {
					System.out.println("Property (complement) proved to be false thanks to knowledge :" + factoid);
					throw new AcceptedRunFoundException();
					//return TGBA.makeTrue();
				}
			} catch (IOException e) {
				// skip
				System.out.println("IOexception raised when running Spot : " + e);
			}

			TGBA prod = spot.computeProduct(tgba, ltl);
			if (prod.getEdges().get(prod.getInitial()).size() == 0) {
				// this is just false !
				System.out.println("Property proved to be true thanks to knowledge :" + factoid);
				throw new EmptyProductException();
//				return TGBA.makeFalse();
			} else if (prod.getProperties().contains("stutter-invariant") && ! tgba.getProperties().contains("stutter-invariant")) {
				System.out.println("Adopting stutter invariant property thanks to knowledge :" + factoid);
				tgba = prod;
				propPN.setBody(Expression.op(Op.OR, propPN.getBody(), Expression.not(Expression.resolveAP(factoid))));
				needRebuild = true;
				wasAdopted = true;
			} else if (prod.getAPs().size() < tgba.getAPs().size()) {
				System.out.println("Adopting property with smaller alphabet thanks to knowledge :" + factoid);
				tgba = prod;
				propPN.setBody(Expression.op(Op.OR, propPN.getBody(), Expression.not(Expression.resolveAP(factoid))));
				needRebuild = true;
				wasAdopted = true;
			}			
		}						

		if (wasAdopted) {
			spot.computeInfStutter(tgba);
			spot.runLTLSimplifications(spn);
		}
		return tgba;
	}

	private void addNextStateKnowledge(List<Expression> knowledge, SparsePetriNet spn, TGBA tgba) {
		Set<Expression> condX = new HashSet<>();
		// check if there are true arc from initial state
		for (TGBAEdge edge : tgba.getEdges().get(tgba.getInitial())) {
			// edge is labeled by true
			// if (edge.getCondition().getOp()==Op.BOOLCONST && edge.getCondition().getValue()==1) {
				// not a self loop, that is F as front operator
				if (edge.getDest() != tgba.getInitial()) {
					int dest = edge.getDest();
					for (TGBAEdge edgeX : tgba.getEdges().get(dest)) {
						if (edgeX.getCondition().getOp() != Op.BOOLCONST) {
							// grab formulas labeling edges out of this node
							condX.add(edgeX.getCondition());
						}
					}
				}
			// }
		}
		if (condX.isEmpty())
			return;
		List<Expression> condXlist=new ArrayList<>(condX);
		boolean [] alltrue = new boolean[condXlist.size()];
		boolean [] allfalse = new boolean[condXlist.size()];
		Arrays.fill(alltrue,true);
		Arrays.fill(allfalse,true);
		
		// run a 1 step test
		WalkUtils wu = new WalkUtils(spn);
		SparseIntArray init = wu.getInitial();
		int[] enabled = wu.computeEnabled(init);
		for (int i=0 ; i < enabled[0] ; i++) {
			int ti = enabled[i+1];
			SparseIntArray dest = wu.fire(ti, init);
			for (int ei = 0; ei < condXlist.size() ; ei++) {
				if (!allfalse[ei] && !alltrue[ei]) {
					continue;
				}
				Expression cond=condXlist.get(ei);
				int res = cond.eval(dest);
				if (res==0) {
					alltrue[ei]=false;
				} else {
					allfalse[ei]=false;
				}
			}
		}
		
		// interpret results as LTL assertions (knowledge)
		for (int ei = 0; ei < condXlist.size() ; ei++) {
			if (alltrue[ei]) {
				knowledge.add(Expression.nop(Op.X,condXlist.get(ei)));
			} else if (allfalse[ei]) {
				knowledge.add(Expression.nop(Op.X,Expression.not(condXlist.get(ei))));				
			}
		}
		
	}

	private void addInitialStateKnowledge(List<Expression> knowledge, ISparsePetriNet spn, TGBA tgba) {
		SparseIntArray init = new SparseIntArray(spn.getMarks());
		for (AtomicProp ap : tgba.getAPs()) {
			if (ap.getExpression().eval(init) == 1) {
				knowledge.add(Expression.apRef(ap));
			} else {
				knowledge.add(Expression.not(Expression.apRef(ap)));
			}
		}
	}

	private void addConvergenceKnowledge(List<Expression> knowledge, ISparsePetriNet spn, TGBA tgba, boolean isSafe) {
		// we are SCC free hence structurally we will meet a deadlock in all traces
		// hence we must be accepted in one of these states, and they are by definition stuttering
		boolean allPathsAreDead = testAFDead (spn);

		if (allPathsAreDead) {
			System.out.println("Detected that all paths lead to deadlock. Applying this knowledge to assert that all AP eventually converge : F ( (Ga|G!a) & (Gb|G!b)...)");

			boolean [] results = DeadlockTester.testAPInDeadlocksWithSMT(spn, tgba.getAPs(), solverPath, isSafe);						

			// build expressions :  G p | G !p 
			// for each ap "p", but remove bad values eliminated through SMT
			for (int i=0,ie=tgba.getAPs().size() ; i < ie ; i++) {
				boolean posExist = results[2*i];
				boolean negExist = results[2*i+1];
				knowledge.add(
						Expression.op(Op.F, 
								Expression.op(Op.OR, 
										posExist ? Expression.op(Op.G, Expression.apRef(tgba.getAPs().get(i)), null): Expression.constant(false), 
												negExist ? Expression.op(Op.G, Expression.not(Expression.apRef(tgba.getAPs().get(i))),null): Expression.constant(false)),null));
				if (!posExist && ! negExist) {
					System.out.println("Strange error detected, AP can be neither true nor false in deadlock.");
				}
			}
		}
	}

	private boolean testAFDead(ISparsePetriNet spn) {
		try {
			Set<Integer> safe = StructuralReduction.findSCCSuffixes(spn, ReductionType.DEADLOCKS, new BitSet());			
		} catch (DeadlockFound e) {
			return true;
		}
		return false;
	}

}
