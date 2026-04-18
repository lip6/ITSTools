package fr.lip6.move.gal.application.solver.ltl;

import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

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
import fr.lip6.move.gal.application.solver.ltl.knowledge.KnowledgeFacts;
import fr.lip6.move.gal.application.solver.ltl.knowledge.KnowledgeReducer;
import fr.lip6.move.gal.gal2smt.Solver;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.FlowPrinter;
import fr.lip6.move.gal.structural.GlobalPropertySolvedException;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.StructuralReduction.ReductionType;
import fr.lip6.move.gal.structural.expr.AtomicProp;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.expr.Simplifier;

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
				
				if (KnowledgeFacts.testAFDead(skel) && skel.testInDeadlock()>0) {
					solved += ReachabilitySolver.checkInInitial(skel, doneProps);
				}
				skel.getProperties().removeIf(p -> ! Simplifier.allEnablingsAreNegated(p,skel));
				if (! skel.getProperties().isEmpty()) {
					System.out.println("Remains "+skel.getProperties().size()+ " properties that can be checked using skeleton over-approximation.");
					reader.setSpn(skel,true);
					solved += ReachabilitySolver.checkInInitial(reader.getSPN(), doneProps);
					if (KnowledgeFacts.testAFDead(skel) && skel.testInDeadlock()>0) {
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
		if (KnowledgeFacts.testAFDead(reader.getSPN()) && reader.getSPN().testInDeadlock()>0) {
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
				System.out.println("Running random walk in product with property : " + propPN.getName());
				if (DEBUG >= 2) { System.out.println(" automaton " + tgba); }
				RandomProductWalker pw = new RandomProductWalker(spnForProp,tgba);
				pw.runProduct(NBSTEPS, 10, false);
				if (doneProps.containsKey(propPN.getName())) 
					return;
				
				pw.runProduct(NBSTEPS, 10, true);
				if (doneProps.containsKey(propPN.getName())) 
					return;
				
			}
			
			TGBA tgbak = tgba;
			SparsePetriNet spnForPropWithK = spnForProp;
			if (! noKnowledgetest) {
				// so we couldn't find a counter example, let's reflect upon this fact.
				tgbak = KnowledgeReducer.applyKnowledgeBasedReductions(spnForProp,tgba, spot, propPN);				

				if (tgbak != tgba) {
					ReductionType rt = tgbak.isStutterInvariant() ? ReductionType.SI_LTL : ReductionType.LTL;

					spnForPropWithK = reduceForProperty(spnForProp, tgbak, rt,
							spnForProp.getProperties().isEmpty() ? propPN : spnForProp.getProperties().get(0));

					// try again on this reduced system
					tgbak = KnowledgeReducer.applyKnowledgeBasedReductions(spnForPropWithK, tgbak, spot, propPN);
				}
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
		LTSminRunner ltsminRunner = new LTSminRunner(false, false, timeout, spn.isSafe());
		
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
			ltsminRunner = new LTSminRunner(negProp.isStutterInvariant(), false, timeout, spn.isSafe());
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


}
