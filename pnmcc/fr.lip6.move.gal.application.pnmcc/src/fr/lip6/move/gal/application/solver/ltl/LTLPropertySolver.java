package fr.lip6.move.gal.application.solver.ltl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import android.util.SparseIntArray;
import fr.lip6.ltl.tgba.AcceptedRunFoundException;
import fr.lip6.ltl.tgba.EmptyProductException;
import fr.lip6.ltl.tgba.LTLException;
import fr.lip6.ltl.tgba.RandomProductWalker;
import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.ltl.tgba.TGBA.ExportMode;
import fr.lip6.ltl.tgba.TGBAEdge;
import fr.lip6.move.gal.application.mcc.MccTranslator;
import fr.lip6.move.gal.application.runner.Ender;
import fr.lip6.move.gal.application.runner.ltsmin.ILTSminRunner;
import fr.lip6.move.gal.application.runner.ltsmin.LTSminPNMLRunner;
import fr.lip6.move.gal.application.runner.ltsmin.LTSminRunner;
import fr.lip6.move.gal.application.runner.spot.SpotRunner;
import fr.lip6.move.gal.application.solver.GALSolver;
import fr.lip6.move.gal.application.solver.ReachabilitySolver;
import fr.lip6.move.gal.application.solver.global.GlobalPropertySolver;
import fr.lip6.move.gal.application.solver.logic.AtomicReducerSR;
import fr.lip6.move.gal.gal2smt.Solver;
import fr.lip6.move.gal.mcc.properties.ConcurrentHashDoneProperties;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.DeadlockFound;
import fr.lip6.move.gal.structural.FlowPrinter;
import fr.lip6.move.gal.structural.GlobalPropertySolvedException;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.StructuralReduction.ReductionType;
import fr.lip6.move.gal.structural.WalkUtils;
import fr.lip6.move.gal.structural.expr.AtomicProp;
import fr.lip6.move.gal.structural.expr.AtomicPropManager;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.expr.Simplifier;
import fr.lip6.move.gal.structural.smt.DeadlockTester;

public class LTLPropertySolver {

	private static final int NBSTEPS = 100000;
	private static final int DEBUG = 0;
	private String workDir;
	private boolean exportLTL;
	public static boolean noKnowledgetest=false;
	public static boolean noStutterTest=false;

	public LTLPropertySolver(String workDir, boolean exportLTL) {
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
				SpotRunner sr = new SpotRunner(10);
				sr.runLTLSimplifications(reader.getHLPN());
			}
			if (isLTL && exportLTL) {					
				SpotRunner.exportLTLProperties(reader.getHLPN(),"colred",workDir);
			}
			SparsePetriNet skel = reader.getHLPN().skeleton();
			if (skel.testInInitial()>0) {
				solved += ReachabilitySolver.checkInInitial(skel, doneProps);
			}
			if (! skel.getProperties().isEmpty()) {
				
				if (testAFDead(skel) && skel.testInDeadlock()>0) {
					solved += ReachabilitySolver.checkInInitial(skel, doneProps);
				}
				skel.getProperties().removeIf(p -> ! Simplifier.allEnablingsAreNegated(p,skel));
				if (! skel.getProperties().isEmpty()) {
					System.out.println("Remains "+skel.getProperties().size()+ " properties that can be checked using skeleton over-approximation.");
					reader.setSpn(skel,true);
					solved += ReachabilitySolver.checkInInitial(reader.getSPN(), doneProps);
					if (testAFDead(skel) && skel.testInDeadlock()>0) {
						solved +=ReachabilitySolver.checkInInitial(skel, doneProps);
					}
					new AtomicReducerSR().strongReductions(reader.getSPN(), doneProps, new SpotRunner(10), true);
					reader.getSPN().simplifyLogic();
					solved +=ReachabilitySolver.checkInInitial(reader.getSPN(), doneProps);
					reader.rebuildSpecification(doneProps);
					GALSolver.checkInInitial(reader.getSpec(), doneProps, reader.getSPN().isSafe());
					reader.flattenSpec(false);
					solved +=GALSolver.checkInInitial(reader.getSpec(), doneProps, reader.getSPN().isSafe());
					solved +=ReachabilitySolver.checkInInitial(reader.getHLPN(), doneProps);
				} else {
					System.out.println("All "+reader.getHLPN().getProperties().size()+ " properties of the HLPN use transition enablings in a way that makes the skeleton too coarse.");
				}
			}
			solved +=ReachabilitySolver.checkInInitial(reader.getHLPN(),doneProps);
			if (reader.getHLPN().getProperties().isEmpty()) {
				reader.setSpn(new SparsePetriNet(), false);
				System.out.println("All properties of the HLPN proved before fully unfolding the net.");
				return solved;
			}
		}
		reader.createSPN();
		reader.getSPN().simplifyLogic();
		solved += reader.getSPN().testInInitial();
		solved += ReachabilitySolver.checkInInitial(reader.getSPN(),doneProps);
		if (isLTL && exportLTL) {
			SpotRunner.exportLTLProperties(reader.getSPN(),"raw",workDir);
		}

		if (isLTL) {
			SpotRunner sr = new SpotRunner(10);
			sr.runLTLSimplifications(reader.getSPN());
		}

		reader.getSPN().simplifyLogic();
		solved += reader.getSPN().testInInitial();
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
				ReachabilitySolver.applyReductions(sr, ReductionType.LTL, true, true);				
				reader.getSPN().readFrom(sr);
				reader.getSPN().removeConstantPlaces();
			} catch (GlobalPropertySolvedException gse) {
				System.out.println("Unexpected exception when reducting for LTL :" +gse.getMessage());
				gse.printStackTrace();
			}
			support = reader.getSPN().computeSupport();
			System.out.println("Support contains "+support.cardinality() + " out of " + sr.getPnames().size() + " places after structural reductions.");
		}
		solved += ReachabilitySolver.checkInInitial(reader.getSPN(),doneProps);
		solved += GALSolver.runGALReductions(reader, doneProps);
		solved += ReachabilitySolver.checkInInitial(reader.getSPN(),doneProps);					
		solved += new AtomicReducerSR().strongReductions(reader.getSPN(), doneProps, new SpotRunner(10), false);
		reader.getSPN().simplifyLogic();
		solved += reader.getSPN().testInInitial();
		solved += ReachabilitySolver.checkInInitial(reader.getSPN(),doneProps);
		
		if (reader.getSPN().getProperties().stream().anyMatch(p->p.getType()==PropertyType.CTL)) {
			solved += GALSolver.runGALReductions(reader, doneProps);			
		}
		reader.getSPN().simplifyLogic();
		solved += ReachabilitySolver.checkInInitial(reader.getSPN(),doneProps);	
		//verifyWithLTSmin (reader.getSPN(),doneProps,15);
		
		reader.getSPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
		if (testAFDead(reader.getSPN()) && reader.getSPN().testInDeadlock()>0) {
			ReachabilitySolver.checkInInitial(reader.getSPN(), doneProps);
		}
		
		
		if (isLTL) {
			SpotRunner sr = new SpotRunner(10);
			sr.runLTLSimplifications(reader.getSPN());
		}
		solved += ReachabilitySolver.checkInInitial(reader.getSPN(),doneProps);
		return solved;
	}
	

	public void runStutteringLTLTest(MccTranslator reader, DoneProperties doneProps)
			throws TimeoutException, LTLException {
		
		
		SpotRunner spot = new SpotRunner(10);



		for (fr.lip6.move.gal.structural.Property propPN : reader.getSPN().getProperties()) {
			if (doneProps.containsKey(propPN.getName())) 
				continue;
			long time = System.currentTimeMillis();
			if (DEBUG >= 1) System.out.println("Starting run for "+propPN.getName()+" :" + SpotRunner.printLTLProperty(propPN.getBody()));
			TGBA tgba = spot.transformToTGBA(propPN);

			ReductionType rt = tgba.isStutterInvariant() ? ReductionType.SI_LTL : ReductionType.LTL;
			SparsePetriNet spnForProp = reduceForProperty(reader.getSPN(), tgba, rt,propPN);

			// annotate it with Infinite Stutter Accepted Formulas
			spot.computeInfStutter(tgba);

			
			checkLTLProperty(spnForProp.getProperties().get(0), tgba, spnForProp, reader, doneProps, spot, time);
		}
	}

	void checkLTLProperty(fr.lip6.move.gal.structural.Property propPN, TGBA tgba, SparsePetriNet spnForProp,
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
				ReductionType rt = tgbak.isStutterInvariant() ? ReductionType.SI_LTL : ReductionType.LTL;

				spnForPropWithK = reduceForProperty(spnForProp, tgbak, rt,
						spnForProp.getProperties().isEmpty() ? propPN : spnForProp.getProperties().get(0));
				
				// try again on this reduced system
				tgbak = applyKnowledgeBasedReductions(spnForPropWithK, tgbak, spot, propPN);
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
			
			if (true) {
				// using HOA
				tgbak.setName(propPN.getName());
				if (reader.isDoLTSMin())
					verifyWithLTSmin(spnForPropWithK, tgbak, doneProps, 15, spot);
				if (doneProps.containsKey(propPN.getName())) 
					return;
				
				ReductionType rt = tgbak.isStutterInvariant() ? ReductionType.SI_LTL : ReductionType.LTL;
				SparsePetriNet spnHOA = reduceForProperty(spnForPropWithK, tgbak, rt, null);
				if (reader.isDoLTSMin())
					verifyWithLTSmin(spnHOA, tgbak, doneProps, 15, spot);
				if (doneProps.containsKey(propPN.getName())) 
					return;
				
				MccTranslator reader2 = reader.copy();
				reader2.setSpn(spnHOA, false);
				reader2.setTgba(tgbak);
				GlobalPropertySolver.verifyWithSDD(reader2, doneProps, "LTL", 15);
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
					
					// Danger here if TGBA became stutter inv, but property was not.
					// rt = ReductionType.LTL;
					
					ReachabilitySolver.applyReductions(sr, rt, true, true);			
				} catch (GlobalPropertySolvedException gse) {
					System.out.println("Unexpected exception when reducing for LTL :" +gse.getMessage());
					gse.printStackTrace();
				}
				spnForProp.readFrom(sr);				
				reader2.setSpn(spnForProp, true);
			} else {
				reader2.setSpn(spnForPropWithK, true);
			}
			if (doneProps.containsKey(propPN.getName())) 
				return;
			
			// 15 seconds timeout, just treat the fast ones.
			GlobalPropertySolver.verifyWithSDD(reader2, doneProps, "LTL", 15);
			
		} catch (AcceptedRunFoundException a) {
			doneProps.put(propPN.getName(), false, a.getTechniques());
		} catch (EmptyProductException e2) {
			doneProps.put(propPN.getName(), true, e2.getTechniques());
		} finally {
			System.out.println("Treatment of property "+propPN.getName()+" finished in "+(System.currentTimeMillis()-time)+" ms.");
		}
	}
	
	private void verifyWithLTSmin (SparsePetriNet spn, DoneProperties doneProps, int timeout) {
		LTSminRunner ltsminRunner = new LTSminRunner(Solver.Z3, false, false, timeout, spn.isSafe());
		
		try {
			ltsminRunner.configure(null, doneProps);
			ltsminRunner.setNet(spn);

			ltsminRunner.solve(new Ender() {
				public void killAll() {
					ltsminRunner.interrupt();
				}
			});

			ltsminRunner.join(timeout*1000);
			ltsminRunner.interrupt();
			ltsminRunner.join();
		} catch (IOException | InterruptedException e) {
			System.out.println("LTSmin runner failed with exception " + e.getMessage());
			e.printStackTrace();
		}

		spn.getProperties().removeIf(p->doneProps.containsKey(p.getName()));
	}
	
	private void verifyWithLTSmin (SparsePetriNet spn, TGBA negProp, DoneProperties doneProps, int timeout, SpotRunner spot) {
		ExportMode mode = ExportMode.LTSMINAP;
		
		ILTSminRunner ltsminRunner ;
		if (mode == ExportMode.LTSMINAP) {
			ltsminRunner = new LTSminRunner(Solver.Z3, negProp.isStutterInvariant(), false, timeout, spn.isSafe());
		} else {
			ltsminRunner = new LTSminPNMLRunner(negProp.isStutterInvariant(), timeout, spn.isSafe());
		}
		
		try {
			ltsminRunner.configure(null, doneProps);
			ltsminRunner.setNet(spn);
			
			// force state based acceptance
			String stateBasedHOA = spot.toBuchi(negProp,mode);
			
			ltsminRunner.setTGBA(negProp, stateBasedHOA);

			ltsminRunner.solve(new Ender() {
				public void killAll() {
					ltsminRunner.interrupt();
				}
			});

			ltsminRunner.join(timeout*1000);
			ltsminRunner.interrupt();
			ltsminRunner.join();
		} catch (IOException | InterruptedException e) {
			System.out.println("LTSmin runner failed with exception " + e.getMessage());
			e.printStackTrace();
		}

		spn.getProperties().removeIf(p->doneProps.containsKey(p.getName()));
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
					StructuralReduction sr = buildReduced(spnredSI, ReductionType.SI_LTL, tgbappor.getAPs(),true);
					
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
						FlowPrinter.drawNet(sr, "PPOR", hl, new HashSet<>());
					}
					
					pw = new RandomProductWalker(spnForPropWithK, sr, tgbappor, atomsred);
					
		//			pw.runProduct(NBSTEPS, 10, false);
		//			pw.runProduct(NBSTEPS, 10, true);
					
					// restore AP to original state
					pw.setAPinterpretation(tgbappor.getInitial());
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

	SparsePetriNet reduceForProperty(SparsePetriNet orispn, TGBA tgba, ReductionType rt, Property propPN) {
		// build a new copy of the model, with only this property				
		List<AtomicProp> aps = tgba.getAPs();
		
		SparsePetriNet spn = new SparsePetriNet(orispn);
		spn.getProperties().clear();
		if (propPN != null)
			spn.getProperties().add(propPN.copy());

		{
			StructuralReduction sr = buildReduced(spn, rt, aps, false);
			
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

	private StructuralReduction buildReduced(SparsePetriNet spn, ReductionType rt, List<AtomicProp> aps, boolean keepImage) {
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
			ReachabilitySolver.applyReductions(sr, rt, true, true);			
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
		List<Expression> falseKnowledge = new ArrayList<>(); 
		
		addInitialStateKnowledge(knowledge, spn, tgba);

		addNextStateKnowledge(knowledge, falseKnowledge, spn, tgba);
		
		addConvergenceKnowledge(knowledge, spn, tgba);
		
		System.out.println("Knowledge obtained : " + knowledge);
		System.out.println("False Knowledge obtained : " + falseKnowledge);
		
		// try to reduce the tgba using this knowledge
		if (false)
			tgba = manuallyIntegrateKnowledge(spn, tgba, knowledge, propPN, spot);
		else
			tgba = spotIntegrateKnowledge(spn, tgba, knowledge, falseKnowledge, propPN, spot);

		if (tgba.isEmptyLanguage() || tgba.isUniversalLanguage()) {
			return tgba;
		} else {
			addInvarianceKnowledge(knowledge, falseKnowledge, spn, tgba);

			System.out.println("Knowledge obtained : " + knowledge);
			System.out.println("False Knowledge obtained : " + falseKnowledge);

			tgba = spotIntegrateKnowledge(spn, tgba, knowledge, falseKnowledge, propPN, spot);						
		}
		
		if (tgba.isEmptyLanguage() || tgba.isUniversalLanguage()) {
			return tgba;
		} else {
			spot.computeInfStutter(tgba);
			List<Expression> ff = computeEGknowledge(spn, tgba);
			if (!ff.isEmpty()) {
				falseKnowledge.addAll(ff);
				System.out.println("Knowledge obtained : " + knowledge);
				System.out.println("False Knowledge obtained : " + falseKnowledge);

				tgba = spotIntegrateKnowledge(spn, tgba, knowledge, falseKnowledge, propPN, spot);						
			}
		}

		return tgba;
	}

	private List<Expression> computeEGknowledge(SparsePetriNet spn, TGBA tgba) {
		List<Expression> falseKnowledge = new ArrayList<>();
		SparseIntArray init = new SparseIntArray(spn.getMarks());
		
		Expression ap = tgba.getInfStutter().get(tgba.getInitial());
		boolean isInitiallyTrue = ap.eval(init)!=0;
		if (isInitiallyTrue) {
			if (ap.getOp() == Op.OR) {
				for (int i=0;i<ap.nbChildren();i++) {
					Expression ap2 = ap.childAt(i);
					// make sure this term is initially true
					if (ap2.eval(init)!=0) {
						if (DeadlockTester.testEGap(ap2,spn, 15)) {
							System.out.println("Proved EG "+ap2);
							falseKnowledge.add(Expression.nop(Op.G,ap2));
						} else {
							System.out.println("Could not prove EG "+ap2);
						}
					}
				}
			} else {
				if (DeadlockTester.testEGap(ap,spn, 15)) {
					System.out.println("Proved EG "+ap);
					falseKnowledge.add(Expression.nop(Op.G,ap));
				} else {
					System.out.println("Could not prove EG "+ap);
				}
			}
		}
		
//		else {
//				if (DeadlockTester.testEGap(Expression.not(ap.getExpression()),spn, solverPath, 15)) {
//					System.out.println("Proved EG !"+ap.getName());
//					falseKnowledge.add(Expression.nop(Op.G,Expression.not(Expression.apRef(ap))));
//				} else {
//					System.out.println("Could not prove EG !"+ap.getName());
//				}
//			}
//		}
		return falseKnowledge;
	}

	private void addInvarianceKnowledge(List<Expression> knowledge, List<Expression> falseKnowledge, SparsePetriNet spn, TGBA tgba) {
		
		SparsePetriNet spnred = new SparsePetriNet(spn);
		spnred.getProperties().clear();
		
		
		List<Expression> apForm = new ArrayList<>();
		{
			Set<Expression> seen = new HashSet<>();
			for (int s=0,se=tgba.nbStates() ; s < se ; s++) {
				for (TGBAEdge e : tgba.getEdges().get(s)) {
					if (e.getCondition().getOp() != Op.BOOLCONST) {
						Expression cmp = e.getCondition();
						if (seen.add(cmp) && seen.add(Expression.not(cmp))) {
							apForm.add(e.getCondition());
						}
					}
				}
			}
		}
		// unify
		apForm = new ArrayList<>(new LinkedHashSet<>(apForm));
		
		// build a list of invariants to test with SMT/random
		// for each of them test value in initial state
		SparseIntArray istate = new SparseIntArray(spnred.getMarks());
		
		{
			
			int index = 0;
			for (Expression cmp: apForm) {
					int val = cmp.eval(istate);
					String pname = "apf" + index++;
					if (val == 1) {
						// UNSAT => it never becomes false
						Property p = new Property(Expression.nop(Op.EF, Expression.not(cmp)), PropertyType.INVARIANT, pname);
						spnred.getProperties().add(p);

					} else {
						// UNSAT => it never becomes true
						Property p = new Property(Expression.nop(Op.EF,cmp), PropertyType.INVARIANT, pname);
						spnred.getProperties().add(p);				
					}
			}
			for (Property prop : spnred.getProperties()) {
				Expression nbody = AtomicPropManager.rewriteWithoutAP(prop.getBody());
				if (prop.getBody() != nbody) {
					prop.setBody(nbody);
				}				
			}
		}
		
		String wd = "/tmp";
		try {
			File workFolder = Files.createTempDirectory("redAtoms").toFile();
			workFolder.deleteOnExit();
			wd = workFolder.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		MccTranslator reader = new MccTranslator(wd, false);
		reader.setSpn(spnred, true);
		
		DoneProperties todoProps = new ConcurrentHashDoneProperties();
		try {
			ReachabilitySolver.applyReductions(reader, todoProps , 100);
		} catch (GlobalPropertySolvedException e) {
			e.printStackTrace();
		}
		
		int nsolved = 0;
		for (Entry<String, Boolean> ent : todoProps.entrySet()) {
			Boolean res = ent.getValue();
			String pname = ent.getKey();
			int pindex = Integer.parseInt(pname.replace("apf", ""));
			Expression c = apForm.get(pindex);
			boolean val = c.eval(istate) == 1;
			if (! res) {
				// cool we've proven an invariant, we can substitute							
				if (DEBUG >= 1) System.out.println("SMT proved AP formula is invariant; concluding " + c + " is always " + val);						

				if (val) {
					knowledge.add(Expression.nop(Op.G, c));
				} else {
					knowledge.add(Expression.nop(Op.G, Expression.not(c)));
				}
				nsolved ++;
			} else {
				// so we have proved that EF !p if p is initially true
				
				Expression FnotP = null;
				if (val) {
					FnotP = Expression.nop(Op.F, Expression.not(c));						
				} else {
					FnotP = Expression.nop(Op.F, c);
				}
				
				falseKnowledge.add(FnotP);
			}
		}
		if (nsolved > 0) {
			System.out.println("Found "+nsolved+" invariant AP formulas.");
		}		
		
	}

	private TGBA spotIntegrateKnowledge(SparsePetriNet spn, TGBA tgba, List<Expression> knowledge, List<Expression> falseKnowledge, Property propPN,
			SpotRunner spot) throws TimeoutException, EmptyProductException, AcceptedRunFoundException {
		
		long time = System.currentTimeMillis();
		int oriAlphabetSize = tgba.getAPs().size();
		int oriNbStates = tgba.getEdges().size();
		int oriNbEdge = tgba.getEdges().stream().mapToInt(List::size).sum();
		boolean wasStutter = tgba.isStutterInvariant();
		
		tgba = knowledgeLoop(tgba, knowledge, falseKnowledge, spot);
				
		System.out.println("Knowledge based reduction with " + knowledge.size() + " factoid took "
				+ (System.currentTimeMillis() - time) + " ms. Reduced automaton from " + oriNbStates + " states, "
				+ oriNbEdge + " edges and " + oriAlphabetSize + " AP (stutter "+ (wasStutter?"insensitive":"sensitive") +") to " + tgba.getEdges().size() + " states, "
				+ tgba.getEdges().stream().mapToInt(List::size).sum() + " edges and " + tgba.getAPs().size() + " AP (stutter " + (tgba.isStutterInvariant()?"insensitive":"sensitive")+").");		

		if (tgba.isEmptyLanguage()) {
			throw new EmptyProductException("KNOWLEDGE");
		} else if (tgba.isUniversalLanguage()) {
			throw new AcceptedRunFoundException("KNOWLEDGE");
		}
				
		spot.computeInfStutter(tgba);
		spot.runLTLSimplifications(spn);
		
		return tgba;
	}

	public TGBA knowledgeLoop(TGBA tgba, List<Expression> knowledge, List<Expression> falseKnowledge, SpotRunner spot) {
		
		
		
		
		if (true) {
			// Spot 2.11+
			TGBA tgbarelax = tgba;
			tgbarelax = spot.givenThat(tgba, knowledge, SpotRunner.GivenStrategy.ALL);

			if (tgbarelax.isEmptyLanguage()) {
				System.out.println("Property proved to be true thanks to knowledge (Minato strategy)");
				return tgbarelax;
			} else if (tgbarelax.isUniversalLanguage()) {
				System.out.println("Property proved to be false thanks to knowledge (Minato strategy)");
				return tgbarelax;
			}
			
			// more aggressive : AND the knowledge
			{
				Expression allFacts = Expression.nop(Op.AND, knowledge);
				tgbarelax = spot.givenThat(tgbarelax, allFacts, SpotRunner.GivenStrategy.ALL);
			}

			if (tgbarelax.isEmptyLanguage()) {
				System.out.println("Property proved to be true thanks to conjunction of knowledge (Minato strategy)");
				return tgbarelax;
			} else if (tgbarelax.isUniversalLanguage()) {
				System.out.println("Property proved to be false thanks to conjunction of knowledge (Minato strategy)");
				return tgbarelax;
			}

			
			if (tgba.isStutterInvariant()) {
				System.out.println("Knowledge sufficient to adopt a stutter insensitive property.");
			}

			
			// test inclusion
			// autfilt --included-in=AnotPhi.hoa Kmoins.hoa
			for (Expression factoid : falseKnowledge) {
				if (spot.isIncludedIn(factoid,tgbarelax)) {
					System.out.println("Property proved to be false thanks to negative knowledge :" + factoid);
					return TGBA.makeTrue();				
				}
			}

			return tgbarelax;
		} else {
		
			// OLD STYLE : Spot 2.10.4.dev "manual" knowledge loop
			// counter-examples ?
			{
				TGBA tgbarelax = tgba;
				for (Expression factoid : knowledge) {
					tgbarelax = spot.givenThat(tgbarelax, factoid, SpotRunner.GivenStrategy.RELAX);
					if (tgba.isEmptyLanguage()) {
						System.out.println("Property proved to be true thanks to knowledge :" + factoid);
						return tgbarelax;
					} else if (tgba.isUniversalLanguage()) {
						System.out.println("Property proved to be false thanks to knowledge :" + factoid);
						return tgbarelax;
					}
				}
				// test inclusion
				// autfilt --included-in=AnotPhi.hoa Kmoins.hoa
				for (Expression factoid : falseKnowledge) {
					if (spot.isIncludedIn(factoid,tgbarelax)) {
						System.out.println("Property proved to be false thanks to negative knowledge :" + factoid);
						return TGBA.makeTrue();				
					}
				}
			}


			for (Expression factoid : knowledge) {
				tgba = spot.givenThat(tgba, factoid, SpotRunner.GivenStrategy.RESTRICT);
				if (tgba.isEmptyLanguage()) {
					System.out.println("Property proved to be true thanks to knowledge :" + factoid);
					return tgba;
				}
			}

			if (!tgba.isStutterInvariant()) {
				Expression allFacts = Expression.nop(Op.AND, knowledge);
				tgba = spot.givenThat(tgba, allFacts, SpotRunner.GivenStrategy.STUTTER);
				if (tgba.isStutterInvariant()) {
					System.out.println("Knowledge sufficient to adopt a stutter insensitive property.");
				}
			}

			for (Expression factoid : knowledge) {
				tgba = spot.givenThat(tgba, factoid, SpotRunner.GivenStrategy.RELAX);
				if (tgba.isEmptyLanguage()) {
					System.out.println("Property proved to be true thanks to knowledge :" + factoid);
					return tgba;
				} else if (tgba.isUniversalLanguage()) {
					System.out.println("Property proved to be false thanks to knowledge :" + factoid);
					return tgba;
				}
			}
		}
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
					throw new AcceptedRunFoundException("KNOWLEDGE");
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
				throw new EmptyProductException("KNOWLEDGE");
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

	private void addNextStateKnowledge(List<Expression> knowledge, List<Expression> falseKnowledge, SparsePetriNet spn, TGBA tgba) {
		Set<Expression> condX = new HashSet<>();
		Set<Expression> condXX = new HashSet<>();
		{
			Set<Expression> seen = new HashSet<>();
			Set<Expression> seenX = new HashSet<>();
			// check if there are true arc from initial state
			for (TGBAEdge edge : tgba.getEdges().get(tgba.getInitial())) {
				// not a self loop, that is F as front operator
				if (edge.getDest() != tgba.getInitial() || edge.getCondition().getOp() != Op.BOOLCONST) {
					int dest = edge.getDest();
					for (TGBAEdge edgeX : tgba.getEdges().get(dest)) {
						if (edgeX.getCondition().getOp() != Op.BOOLCONST) {
							if (seen.add(edgeX.getCondition()) && seen.add(Expression.not(edgeX.getCondition()))) {
								// grab formulas labeling edges out of this node
								condX.add(edgeX.getCondition());
							}
						} 
						for (TGBAEdge edgeXX : tgba.getEdges().get(edgeX.getDest())) {
							if (seenX.add(edgeXX.getCondition()) && seenX.add(Expression.not(edgeXX.getCondition()))) {
								// grab formulas labeling edges out of this node
								condXX.add(edgeXX.getCondition());
							}
						}
						
					}
				}
			}
		}
		if (condX.isEmpty() && condXX.isEmpty())
			return;
		List<Expression> condXlist=new ArrayList<>(condX);
		int lastCondX = condXlist.size();
		condXlist.addAll(condXX);
		boolean [] alltrue = new boolean[condXlist.size()];
		boolean [] allfalse = new boolean[condXlist.size()];
		Arrays.fill(alltrue,true);
		Arrays.fill(allfalse,true);
		boolean doXX = true;
		
		// run a 1 step test
		WalkUtils wu = new WalkUtils(spn);
		SparseIntArray init = wu.getInitial();
		int[] enabled = wu.getInitialEnabling().clone();
		
		// we are starting from a deadlock ?
		if (enabled[0]==0) {
			for (int ei = 0; ei < condXlist.size() ; ei++) {
				if (!allfalse[ei] && !alltrue[ei]) {
					continue;
				}
				Expression cond=condXlist.get(ei);
				int res = cond.eval(init);
				if (res==0) {
					alltrue[ei]=false;
				} else {
					allfalse[ei]=false;
				}
			}			
		}
		
		for (int i=0 ; i < enabled[0] ; i++) {
			int ti = enabled[i+1];
			SparseIntArray dest = wu.fire(ti, init);
			for (int ei = 0; ei < lastCondX ; ei++) {
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
			if (! condXX.isEmpty() && enabled[0] < 2000) {
				int [] enableX = Arrays.copyOf(enabled, enabled.length);
				wu.updateEnabled(dest, enableX, ti);
				if (enableX[0] > 0) {
					for (int ii=0 ; ii < enableX[0] ; ii++) {
						int tti = enableX[ii+1];
						SparseIntArray destX = wu.fire(tti, dest);
						for (int ei = lastCondX; ei < condXlist.size() ; ei++) {
							if (!allfalse[ei] && !alltrue[ei]) {
								continue;
							}
							Expression cond=condXlist.get(ei);
							int res = cond.eval(destX);
							if (res==0) {
								alltrue[ei]=false;
							} else {
								allfalse[ei]=false;
							}
						}
					}
				} else {
					// successor state is a deadlock
					for (int ei = lastCondX; ei < condXlist.size() ; ei++) {
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
			} else {
				doXX=false;
			}
		}
		
		// interpret results as LTL assertions (knowledge)
		for (int ei = 0; ei < lastCondX ; ei++) {
			if (alltrue[ei]) {
				knowledge.add(Expression.nop(Op.X,condXlist.get(ei)));
			} else if (allfalse[ei]) {
				knowledge.add(Expression.nop(Op.X,Expression.not(condXlist.get(ei))));				
			} else {
				falseKnowledge.add(Expression.nop(Op.X,condXlist.get(ei)));
				falseKnowledge.add(Expression.nop(Op.X,Expression.not(condXlist.get(ei))));
			}
		}
		if (doXX) {
			for (int ei = lastCondX; ei < condXlist.size() ; ei++) {
				if (alltrue[ei]) {
					knowledge.add(Expression.nop(Op.X,Expression.nop(Op.X,condXlist.get(ei))));
				} else if (allfalse[ei]) {
					knowledge.add(Expression.nop(Op.X,Expression.nop(Op.X,Expression.not(condXlist.get(ei)))));				
				} else {
					falseKnowledge.add(Expression.nop(Op.X,Expression.nop(Op.X,condXlist.get(ei))));
					falseKnowledge.add(Expression.nop(Op.X,Expression.nop(Op.X,Expression.not(condXlist.get(ei)))));
				}
			}
		}
	}

	private void addInitialStateKnowledge(List<Expression> knowledge, ISparsePetriNet spn, TGBA tgba) {
		SparseIntArray init = new SparseIntArray(spn.getMarks());
		List<Expression> kis = new ArrayList<>();
		for (AtomicProp ap : tgba.getAPs()) {
			if (ap.getExpression().eval(init) == 1) {
				kis.add(Expression.apRef(ap));
			} else {
				kis.add(Expression.not(Expression.apRef(ap)));
			}
		}
		knowledge.add(Expression.nop(Op.AND,kis));
	}

	private void addConvergenceKnowledge(List<Expression> knowledge, ISparsePetriNet spn, TGBA tgba) {
		// we are SCC free hence structurally we will meet a deadlock in all traces
		// hence we must be accepted in one of these states, and they are by definition stuttering
		boolean allPathsAreDead = testAFDead (spn);

		if (allPathsAreDead) {
			System.out.println("Detected that all paths lead to deadlock. Applying this knowledge to assert that all AP eventually converge : F ( (Ga|G!a) & (Gb|G!b)...)");

			boolean [] results = DeadlockTester.testAPInDeadlocksWithSMT(spn, tgba.getAPs());						

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
		} else {
			// recompute stable though testAFDead did it, but it's basically a graph traversal no big deal
			Set<Integer> stablePlaces = new HashSet<>();
			Set<Integer> stableTrans = new HashSet<>();
			StructuralReduction.computeStabilizing(spn, stablePlaces,stableTrans);
			if (!stablePlaces.isEmpty()) {
				int nbstable=0;
				for (int apid=0,ie=tgba.getAPs().size() ; apid < ie ; apid++) {
					AtomicProp ap = tgba.getAPs().get(apid);
					BitSet supp = new BitSet(); 
					SparsePetriNet.addSupport(ap.getExpression(), supp);
					
					boolean covered = true;
					for (int i = supp.nextSetBit(0); i >= 0; i = supp.nextSetBit(i+1)) {
						// operate on index i here
						if (!stablePlaces.contains(i)) {
							covered=false;
							break;
						}
						if (i == Integer.MAX_VALUE) {
							break; // or (i+1) would overflow
						}
					}
					if (covered) {
						nbstable++;
						knowledge.add(
								Expression.op(Op.F, 
										Expression.op(Op.OR, 
												Expression.op(Op.G, Expression.apRef(tgba.getAPs().get(apid)), null), 
												Expression.op(Op.G, Expression.not(Expression.apRef(tgba.getAPs().get(apid))),null)),null));						
					}
				}
				if (nbstable >0) {
					System.out.println("Detected a total of "+stablePlaces.size()+"/"+ spn.getPlaceCount()+ " stabilizing places and "+stableTrans.size()+"/"+ spn.getTransitionCount()+ " transitions leading to convergence knowledge of the form 'F(Gp|G!p)' for "+nbstable+"/"+ tgba.getAPs().size()+" atomic propositions.");
				}
			}
		}
	}

	private boolean testAFDead(ISparsePetriNet spn) {
		try {
			if (spn.getFlowPT().getColumns().stream().allMatch(c -> c.size() > 0)) {
				StructuralReduction.findSCCSuffixes(spn, ReductionType.DEADLOCK, new BitSet());
			}
		} catch (DeadlockFound e) {
			// AF dead is true
			System.out.println("Detected that all paths lead to deadlock. Applying this knowledge to assert that all AP eventually converge (and all enablings converge to false).");

			return true;
		}
		return false;
	}

}
