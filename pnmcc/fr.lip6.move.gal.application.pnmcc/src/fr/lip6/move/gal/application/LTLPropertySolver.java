package fr.lip6.move.gal.application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.BitSet;
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
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.DeadlockFound;
import fr.lip6.move.gal.structural.FlowPrinter;
import fr.lip6.move.gal.structural.GlobalPropertySolvedException;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.StructuralReduction.ReductionType;
import fr.lip6.move.gal.structural.expr.AtomicProp;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.smt.DeadlockTester;

public class LTLPropertySolver {

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
		runStutteringLTLTest(reader, doneProps, isSafe);

		reader.getSPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
		return solved;
	}

	public void runStutteringLTLTest(MccTranslator reader, DoneProperties doneProps, boolean isSafe)
			throws TimeoutException, LTLException {
		SpotRunner spot = new SpotRunner(spotPath, workDir, 10);



		for (fr.lip6.move.gal.structural.Property propPN : reader.getSPN().getProperties()) {
			if (doneProps.containsKey(propPN.getName())) 
				continue;

			TGBA tgba = spot.transformToTGBA(propPN);


			SparsePetriNet spn = reduceForProperty(reader.getSPN(), tgba);

			// annotate it with Infinite Stutter Acceped Formulas
			spot.computeInfStutter(tgba);

			// walk the product a bit
			RandomProductWalker pw = new RandomProductWalker(spn);

			try {
				System.out.println("Running random walk in product with property : " + propPN.getName() + " automaton " + tgba);
				if (DEBUG >= 2) FlowPrinter.drawNet(spn,"For product with " + propPN.getName());
				pw.runProduct(tgba , 10000, 10);

				// so we couldn't find a counter example, let's reflect upon this fact.
				TGBA tgbak = applyKnowledgeBasedReductions(spn,tgba, isSafe,spot);
				
				
				SparsePetriNet spnmore = reduceForProperty(spn, tgbak);

				if (DEBUG >= 2) FlowPrinter.drawNet(spn,"For product with " + propPN.getName());
				// index of places may have changed, formula might be syntactically simpler 
				// annotate it with Infinite Stutter Acceped Formulas
				spot.computeInfStutter(tgbak);
				pw = new RandomProductWalker(spnmore);
				pw.runProduct(tgbak , 10000, 10);

			} catch (AcceptedRunFoundException a) {
				doneProps.put(propPN.getName(), false, "STUTTER_TEST");
			} catch (EmptyProductException e2) {
				doneProps.put(propPN.getName(), true, "STRUCTURAL INITIAL_STATE");
			}





		}
	}

	private SparsePetriNet reduceForProperty(SparsePetriNet orispn, TGBA tgba) {
		// build a new copy of the model, with only this property				
		SparsePetriNet spn = new SparsePetriNet(orispn);
		spn.getProperties().clear();

		{
			// ok let's reduce the system for this property 
			StructuralReduction sr = new StructuralReduction(spn);
			BitSet support = new BitSet();
			for (AtomicProp ap : tgba.getAPs()) {
				SparsePetriNet.addSupport(ap.getExpression(),support);
			}
			System.out.println("Support contains "+support.cardinality() + " out of " + sr.getPnames().size() + " places. Attempting structural reductions.");
			sr.setProtected(support);
			try {
				if (tgba.getProperties().contains("stutter-invariant")) {
					sr.reduce(ReductionType.SI_LTL);
				} else {
					sr.reduce(ReductionType.LTL);
				}
			} catch (GlobalPropertySolvedException gse) {
				System.out.println("Unexpected exception when reducting for LTL :" +gse.getMessage());
				gse.printStackTrace();
			}
			
			// rebuild and reinterpret the reduced net
			// index of places may have changed, formula might be syntactically simpler 
			// recompute fresh tgba with correctly indexed AP					
			List<Expression> atoms = tgba.getAPs().stream().map(ap -> ap.getExpression()).collect(Collectors.toList());
			List<Expression> atoms2 = spn.readFrom(sr,atoms);
			for (int i =0,ie=atoms.size(); i<ie; i++) {
				tgba.getAPs().get(i).setExpression(atoms2.get(i));
			}
		}
		// we can maybe simplify some predicates now : apply some basic tests
		spn.testInInitial();
		spn.removeConstantPlaces();
		spn.simplifyLogic();
		return spn;
	}

	private TGBA applyKnowledgeBasedReductions(ISparsePetriNet spn, TGBA tgba, boolean isSafe, SpotRunner spot) {

		// cheap knowledge 
		List<Expression> knowledge = new ArrayList<>(); 

		addConvergenceKnowledge(knowledge, spn, tgba, isSafe);

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
					return TGBA.makeTrue();
				}
			} catch (IOException e) {
				// skip
				System.out.println("IOexception raised when running Spot : " + e);
			}

			TGBA prod = sr.computeProduct(tgba, ltl);
			if (prod.getEdges().get(prod.getInitial()).size() == 0) {
				// this is just false !
				System.out.println("Property proved to be true thanks to knowledge :" + factoid);
				return TGBA.makeFalse();
			} else if (prod.getProperties().contains("stutter-invariant") && ! tgba.getProperties().contains("stutter-invariant")) {
				System.out.println("Adopting stutter invariant property thanks to knowledge :" + factoid);
				tgba = prod;
				needRebuild = true;
				wasAdopted = true;
			} else if (prod.getAPs().size() < tgba.getAPs().size()) {
				System.out.println("Adopting property with smaller alphabet thanks to knowledge :" + factoid);
				tgba = prod;
				needRebuild = true;
				wasAdopted = true;
			}			
		}						

		if (wasAdopted) {
			spot.computeInfStutter(tgba);
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
