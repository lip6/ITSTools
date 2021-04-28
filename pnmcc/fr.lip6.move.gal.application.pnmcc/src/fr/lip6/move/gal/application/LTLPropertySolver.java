package fr.lip6.move.gal.application;

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
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.DeadlockFound;
import fr.lip6.move.gal.structural.FlowPrinter;
import fr.lip6.move.gal.structural.GlobalPropertySolvedException;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.StructuralReduction.ReductionType;
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

			
			try {
				System.out.println("Running random walk in product with property : " + propPN.getName() + " automaton " + tgba);
				if (DEBUG >= 2) FlowPrinter.drawNet(spnForProp,"For product with " + propPN.getName());
				// walk the product a bit
				RandomProductWalker pw = new RandomProductWalker(spnForProp,tgba);
				pw.runProduct(NBSTEPS, 10, false);
				pw.runProduct(NBSTEPS, 10, true);
				
				// so we couldn't find a counter example, let's reflect upon this fact.
				TGBA tgbak = applyKnowledgeBasedReductions(spnForProp,tgba, spot, propPN);				
				
				SparsePetriNet spnForPropWithK;
				if (tgbak != tgba) {
					spnForPropWithK = reduceForProperty(spnForProp, tgbak,
							spnForProp.getProperties().isEmpty() ? null : spnForProp.getProperties().get(0));
				} else {
					spnForPropWithK = spnForProp;
				}

				if (DEBUG >= 2) FlowPrinter.drawNet(spnForPropWithK,"For product with " + propPN.getName());
				// index of places may have changed, formula might be syntactically simpler 
				// annotate it with Infinite Stutter Acceped Formulas
				spot.computeInfStutter(tgbak);
				pw = new RandomProductWalker(spnForPropWithK,tgbak);
				pw.runProduct(NBSTEPS, 10, false);
				pw.runProduct(NBSTEPS, 10, true);
				
				if (! tgbak.isStutterInvariant()) {
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
				
				
				// Last step, try exhaustive methods
				
				MccTranslator reader2 = reader.copy();
				if (spnForPropWithK.getProperties().isEmpty()) {
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
		sr.setKeepImage(keepImage);
		BitSet support = new BitSet();
		for (AtomicProp ap : aps) {
			SparsePetriNet.addSupport(ap.getExpression(),support);
		}
		System.out.println("Support contains "+support.cardinality() + " out of " + sr.getPnames().size() + " places. Attempting structural reductions.");
		BitSet supportForProp = spn.computeSupport();
		if (! supportForProp.equals(support)) {
			System.out.println("Property had overlarge support with respect to TGBA, discarding it for now.");
			spn.getProperties().clear();
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

		// cheap knowledge 
		List<Expression> knowledge = new ArrayList<>(); 

		addConvergenceKnowledge(knowledge, spn, tgba, spn.isSafe());

		addInitialStateKnowledge(knowledge, spn, tgba);

		System.out.println("Knowledge obtained : " + knowledge);

		// try to reduce the tgba using this knowledge
		SpotRunner sr = new SpotRunner(spotPath, workDir, 10);

		boolean needRebuild = true;
		boolean wasAdopted = false;
		for (Expression factoid : knowledge) {
			String ltl = SpotRunner.printLTLProperty(factoid);

			try {
				// need to complement tgba				

				File comp = Files.createTempFile("comp", ".hoa").toFile();
				if (needRebuild) {
					if (! sr.buildComplement(tgba, comp)) {
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
				if (sr.isProductEmpty(comp,ltl)) {
					System.out.println("Property (complement) proved to be false thanks to knowledge :" + factoid);
					throw new AcceptedRunFoundException();
					//return TGBA.makeTrue();
				}
			} catch (IOException e) {
				// skip
				System.out.println("IOexception raised when running Spot : " + e);
			}

			TGBA prod = sr.computeProduct(tgba, ltl);
			if (prod.getEdges().get(prod.getInitial()).size() == 0) {
				// this is just false !
				System.out.println("Property proved to be true thanks to knowledge :" + factoid);
				throw new EmptyProductException();
//				return TGBA.makeFalse();
			} else if (prod.getProperties().contains("stutter-invariant") && ! tgba.getProperties().contains("stutter-invariant")) {
				System.out.println("Adopting stutter invariant property thanks to knowledge :" + factoid);
				tgba = prod;
				propPN.setBody(Expression.op(Op.AND, propPN.getBody(), factoid));
				needRebuild = true;
				wasAdopted = true;
			} else if (prod.getAPs().size() < tgba.getAPs().size()) {
				System.out.println("Adopting property with smaller alphabet thanks to knowledge :" + factoid);
				tgba = prod;
				propPN.setBody(Expression.op(Op.AND, propPN.getBody(), factoid));
				needRebuild = true;
				wasAdopted = true;
			}			
		}						

		if (wasAdopted) {
			spot.computeInfStutter(tgba);
			sr.runLTLSimplifications(spn);
		}

		return tgba;

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
