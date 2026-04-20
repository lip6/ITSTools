package fr.lip6.move.gal.application.solver.ltl.knowledge;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import fr.lip6.ltl.tgba.AcceptedRunFoundException;
import fr.lip6.ltl.tgba.EmptyProductException;
import fr.lip6.ltl.tgba.LTLException;
import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.move.gal.application.runner.spot.SpotRunner;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;

public class KnowledgeReducer {
	
	
	public static TGBA integrateKnowledge(SparsePetriNet spn, TGBA tgba, List<Expression> knowledge, List<Expression> falseKnowledge, Property propPN,
			SpotRunner spot) throws TimeoutException, EmptyProductException, AcceptedRunFoundException {
		
		long time = System.currentTimeMillis();
		int oriAlphabetSize = tgba.getAPs().size();
		int oriNbStates = tgba.getEdges().size();
		int oriNbEdge = tgba.getEdges().stream().mapToInt(List::size).sum();
		boolean wasStutter = tgba.isStutterInvariant();
		
		TGBA tgbared = tgba;
		try {
			tgbared = knowledgeLoop(tgba, propPN.getBody(), knowledge, falseKnowledge, spot);

			if (tgbared.isEmptyLanguage()) {
				throw new EmptyProductException("KNOWLEDGE");
			} else if (tgbared.isUniversalLanguage()) {
				throw new AcceptedRunFoundException("KNOWLEDGE");
			}
			
			System.out.println("Knowledge based reduction with " + knowledge.size() + " factoid took "
					+ (System.currentTimeMillis() - time) + " ms. Reduced automaton from " + oriNbStates + " states, "
					+ oriNbEdge + " edges and " + oriAlphabetSize + " AP (stutter "+ (wasStutter?"insensitive":"sensitive") +") to " + tgbared.getEdges().size() + " states, "
					+ tgbared.getEdges().stream().mapToInt(List::size).sum() + " edges and " + tgbared.getAPs().size() + " AP (stutter " + (tgbared.isStutterInvariant()?"insensitive":"sensitive")+").");		
		} catch (LTLException e) {
			System.out.println("Knowledge based reduction with " + knowledge.size() + " factoid took "
					+ (System.currentTimeMillis() - time) + " ms. Proved property starting from automaton with " + oriNbStates + " states, "
					+ oriNbEdge + " edges and " + oriAlphabetSize + " AP (stutter "+ (wasStutter?"insensitive":"sensitive"));
			throw e;
		} catch (TimeoutException e) {
			System.out.println("Timeout when exploiting knowledge after " + (System.currentTimeMillis() - time) + " ms. Continuing without knowledge-based reduction.");
		}
		
		return tgbared;
	}
	
    /**
     * Practical knowledge integration following the benchmark-validated decision tree.
     * 
     * This function implements the four-step procedure exactly as decided from measurements:
     * 1. Test emptiness of (phi AND K+_QE) using satisfiability (phi is 
     * 2. Test emptiness of (NOT phi AND K+_QE) using satisfiability.
     * 3. Isolated negative-knowledge satisfiability test (counter example only).
     * 4. Fallback to precise conjunction with AUTO_SI vs AUTO_SMALL.
     * 
     * If any step decides the property, the corresponding exception is thrown.
     * Otherwise the best reduced TGBA is returned.
     * 
     * The precise conjunction is built only when needed (SpotRunner.givenThat accepts lists directly).
     * Filtering and existential quantification are currently a no-op placeholder.
     */
	public static TGBA knowledgeLoop(TGBA tgba, Expression phi, List<Expression> positiveKnowledge, List<Expression> falseKnowledge, SpotRunner spot) 
            throws EmptyProductException, AcceptedRunFoundException, TimeoutException {

        if (positiveKnowledge.isEmpty() && falseKnowledge.isEmpty()) {
            return tgba;
        }
        
		if (tgba.isEmptyLanguage()) {
			throw new EmptyProductException("KNOWLEDGE");
		} else if (tgba.isUniversalLanguage()) {
			throw new AcceptedRunFoundException("KNOWLEDGE");
		}

        // Filter + existential quantification applied individually to each fact.
        // Currently a no-op placeholder (QE/filtering not active).
        // List<Expression> kPlusQEList = filterAndQuantifyExistentially(positiveKnowledge, phi);

        // === Step 1: build conjunction of facts as an expression ===
        Expression kPlus ;
        if (positiveKnowledge.isEmpty()) {
			kPlus = Expression.constant(true);
		} else if (positiveKnowledge.size() == 1) {
			kPlus = positiveKnowledge.get(0);
		} else {
			kPlus = Expression.nop(Op.AND, positiveKnowledge);
		}
        
        // === Step 2 : direct implications of knowledge with respect to phi ===
        try {
        	// a) Test satisfiability of (!phi AND K+) => 
        	// * if UNSAT,  min(!phi) construction is empty. phi is true (implied by K+).
        	if (! spot.isSatisfiable(Expression.op(Op.AND, Expression.not(phi), kPlus))) {
        		System.out.println("Property proved to be true by K+ with min(!phi)");
        		throw new EmptyProductException("KNOWLEDGE");
        	}

        	// b) Test satisfiability of (phi AND K+) =>
        	// * if UNSAT, min(phi) construction is empty. this is equivalent to universality of  max(!phi)
        	// any run of the system is a counter-example to phi, so phi is false.
        	if (! spot.isSatisfiable(Expression.op(Op.AND, phi, kPlus))) {
        		System.out.println("Property proved to be false by K+ with min(phi)");
        		throw new AcceptedRunFoundException("KNOWLEDGE");
        	}
        } catch (IOException e) {
        	System.out.println("IOException or timeout when running Spot to compute min construction : " + e);
        }
        
        // === Step 3 : negative knowledge tests ===
        // Can only prove existence of a counter-example. 
		/* K- is false ; so !K- contains a run of S.*/
		/* This run must also be part of K+, since K+ wraps S.  */
		/* So, consider (!K- AND K+) : it must contain a system run.*/
        /* It also must be non empty since K- false means at least one run, and all runs are in K+. */
		/* So if it is included in !phi, !phi contains a system run, that is a counterexample to phi. */

		/* To avoid complementations and full inclusions, we leverage a "satisfiable" LTL test */
		/* !K- AND K+ AND phi unsatisfiable => all of (!K- AND K+) included in !phi.*/
		/* hence we have a counterexample to phi if  : !K- AND K+ AND phi is UNSAT*/
        tryNegativeKnowledge(phi, kPlus, falseKnowledge, spot);
        
        
        // === Step 4 : Knowledge we have cannot prove or disprove entirely phi: build smaller automata ===
        // We leverage the constructions BM (bounded by Minato) and "Stutter Relax" "Stutter Restrict".
        // See Petri Nets 2025 paper.
        // We build several (its cheap), and pick the best (smallest, stutter-invariant if possible).
        // Currently : Spot has auto-small and auto-si that fixpoint to get small automata.
        TGBA autoSI    = spot.givenThat(tgba, positiveKnowledge, SpotRunner.GivenStrategy.AUTO_SI);   
        TGBA autoSmall = spot.givenThat(tgba, positiveKnowledge, SpotRunner.GivenStrategy.AUTO_SMALL);

        return pickBestAutomaton(autoSI, autoSmall);
    }

	
    /**
     * Isolated negative-knowledge test, callable independently.
     * For each false fact K- we test satisfiability of (NOT K-) AND (phi AND K+_QE).
     * If any test is UNSAT, we have a counter-example to phi.
     * @throws AcceptedRunFoundException 
     */
    public static void tryNegativeKnowledge(Expression phi,
                                             List<Expression> knowledge,
                                             List<Expression> falseKnowledge,
                                             SpotRunner spot) throws AcceptedRunFoundException {
    	tryNegativeKnowledge(phi, Expression.nop(Op.AND,knowledge), falseKnowledge, spot);
    }
	
    /**
     * Isolated negative-knowledge test, callable independently.
     * For each false fact K- we test satisfiability of (NOT K-) AND (phi AND K+_QE).
     * If any test is UNSAT, we have a counter-example to phi.
     */
    public static void tryNegativeKnowledge(Expression phi,
                                             Expression kPlus,
                                             List<Expression> falseKnowledge,
                                             SpotRunner spot)
            throws AcceptedRunFoundException {

        if (falseKnowledge.isEmpty()) {
            return;
        }

        Expression kPlusAndPhi = Expression.op(Op.AND, phi, kPlus);
        
        boolean first = true;

        for (Expression fk : falseKnowledge) {
        	Expression testForm = Expression.op(Op.AND, Expression.not(fk), kPlusAndPhi);
        	try {
        		if (!spot.isSatisfiable(testForm)) {
        			System.out.println("Negative knowledge disproves property using: " + fk);
        			throw new AcceptedRunFoundException("NEGATIVE_KNOWLEDGE");   // counter-example found
        		}
        	} catch (IOException e) {
        		if (first) {
        			System.out.println("IOException or timeout when running Spot to compute negative knowledge test : " + e + "Not reporting more errors.");
        			first = false;
        		}
        	}
        }
    }

    /**
     * Placeholder for support-based filtering + syntactic existential quantification.
     * Applied individually to each positive knowledge fact.
     * Currently returns the input list unchanged (QE/filtering not active).
     * 
     * Later implementation can:
     *   - keep only facts whose support intersects phi's support
     *   - call Simplifier.existentialQuantification on extra APs
     */
    private static List<Expression> filterAndQuantifyExistentially(List<Expression> positiveKnowledge,
                                                                   Expression targetFormula) {
        return new ArrayList<>(positiveKnowledge);   // no-op for now
    }

    private static TGBA pickBestAutomaton(TGBA a, TGBA b) {
        if (a == null) return b;
        if (b == null) return a;

        boolean aIsSI = a.isStutterInvariant();
        boolean bIsSI = b.isStutterInvariant();

        if (aIsSI && !bIsSI) return a;
        if (bIsSI && !aIsSI) return b;
        
        // TODO : refine tie breaker with edges, acceptance...
        return (a.nbStates() <= b.nbStates()) ? a : b;
    }
	
	
	

//	public static TGBA applyKnowledgeBasedReductions(SparsePetriNet spn, TGBA tgba, SpotRunner spot, Property propPN) throws LTLException, TimeoutException {
//		
//		// cheap knowledge 
//		List<Expression> knowledge = new ArrayList<>(); 
//		List<Expression> falseKnowledge = new ArrayList<>(); 
//		
//		KnowledgeFacts.addInitialStateKnowledge(knowledge, spn, tgba);
//	
//		KnowledgeFacts.addNextStateKnowledge(knowledge, falseKnowledge, spn, tgba);
//		
//		KnowledgeFacts.addConvergenceKnowledge(knowledge, spn, tgba);
//		
//		System.out.println("Knowledge obtained : " + knowledge);
//		System.out.println("False Knowledge obtained : " + falseKnowledge);
//		
//		// try to reduce the tgba using this knowledge
//		long time = System.currentTimeMillis();
//		
//		
//		System.out.println("Knowledge based reduction with " + knowledge.size() + " factoid took "
//				+ (System.currentTimeMillis() - time) + " ms. Reduced automaton from " + oriNbStates + " states, "
//				+ oriNbEdge + " edges and " + oriAlphabetSize + " AP (stutter "+ (wasStutter?"insensitive":"sensitive") +") to " + tgba.getEdges().size() + " states, "
//				+ tgba.getEdges().stream().mapToInt(List::size).sum() + " edges and " + tgba.getAPs().size() + " AP (stutter " + (tgba.isStutterInvariant()?"insensitive":"sensitive")+").");	
//		
//		if (false)
//			tgba = manuallyIntegrateKnowledge(spn, tgba, knowledge, propPN, spot);
//		else
//			tgba = spotIntegrateKnowledge(spn, tgba, knowledge, falseKnowledge, propPN, spot);
//	
//		if (tgba.isEmptyLanguage() || tgba.isUniversalLanguage()) {
//			return tgba;
//		} else {
//			KnowledgeFacts.addInvarianceKnowledge(knowledge, falseKnowledge, spn, tgba);
//	
//			System.out.println("Knowledge obtained : " + knowledge);
//			System.out.println("False Knowledge obtained : " + falseKnowledge);
//	
//			tgba = spotIntegrateKnowledge(spn, tgba, knowledge, falseKnowledge, propPN, spot);						
//		}
//		
//		if (tgba.isEmptyLanguage() || tgba.isUniversalLanguage()) {
//			return tgba;
//		} else {
//			spot.computeInfStutter(tgba);
//			List<Expression> ff = KnowledgeFacts.computeEGknowledge(spn, tgba);
//			if (!ff.isEmpty()) {
//				falseKnowledge.addAll(ff);
//				System.out.println("Knowledge obtained : " + knowledge);
//				System.out.println("False Knowledge obtained : " + falseKnowledge);
//	
//				tgba = spotIntegrateKnowledge(spn, tgba, knowledge, falseKnowledge, propPN, spot);						
//			}
//		}
//	
//		return tgba;
//	}
//
//	public static TGBA manuallyIntegrateKnowledge(SparsePetriNet spn, TGBA tgba, List<Expression> knowledge, Property propPN,
//				SpotRunner spot) throws AcceptedRunFoundException, EmptyProductException, TimeoutException {
//			boolean needRebuild = true;
//			boolean wasAdopted = false;
//			for (Expression factoid : knowledge) {
//				String ltl = SpotRunner.printLTLProperty(factoid);
//	
//				try {
//					// need to complement tgba				
//	
//					File comp = Files.createTempFile("comp", ".hoa").toFile();
//					if (needRebuild) {
//						if (! spot.buildComplement(tgba, comp)) {
//							// failure of Spot ?
//							continue;
//						}				
//					}
//					// test inclusion : Knowledge dominates the formula
//					// i.e. A is a subset of K
//					// therefore !K*A = 0
//					//				if (sr.isProductEmpty(comp,"!(" +ltl + ")")) {
//					//					// property is true, negation is empty
//					//					System.out.println("Property (complement) proved to be true thanks to knowledge :" + factoid);
//					//					return TGBA.makeFalse(); 
//					//				}
//	
//					// test disjoint : A * K is empty
//					// therefore, A does not cover K => does not cover S
//					// we have empty product with !A.
//					if (spot.isProductEmpty(comp,ltl)) {
//						System.out.println("Property (complement) proved to be false thanks to knowledge :" + factoid);
//						throw new AcceptedRunFoundException("KNOWLEDGE");
//						//return TGBA.makeTrue();
//					}
//				} catch (IOException e) {
//					// skip
//					System.out.println("IOexception raised when running Spot : " + e);
//				}
//	
//				TGBA prod = spot.computeProduct(tgba, ltl);
//				if (prod.getEdges().get(prod.getInitial()).size() == 0) {
//					// this is just false !
//					System.out.println("Property proved to be true thanks to knowledge :" + factoid);
//					throw new EmptyProductException("KNOWLEDGE");
//	//				return TGBA.makeFalse();
//				} else if (prod.getProperties().contains("stutter-invariant") && ! tgba.getProperties().contains("stutter-invariant")) {
//					System.out.println("Adopting stutter invariant property thanks to knowledge :" + factoid);
//					tgba = prod;
//					propPN.setBody(Expression.op(Op.OR, propPN.getBody(), Expression.not(Expression.resolveAP(factoid))));
//					needRebuild = true;
//					wasAdopted = true;
//				} else if (prod.getAPs().size() < tgba.getAPs().size()) {
//					System.out.println("Adopting property with smaller alphabet thanks to knowledge :" + factoid);
//					tgba = prod;
//					propPN.setBody(Expression.op(Op.OR, propPN.getBody(), Expression.not(Expression.resolveAP(factoid))));
//					needRebuild = true;
//					wasAdopted = true;
//				}			
//			}						
//	
//			if (wasAdopted) {
//				spot.computeInfStutter(tgba);
//				spot.runLTLSimplifications(spn);
//			}
//			return tgba;
//		}
//
//	private static TGBA spotIntegrateKnowledge(SparsePetriNet spn, TGBA tgba, List<Expression> knowledge, List<Expression> falseKnowledge, Property propPN,
//			SpotRunner spot) throws TimeoutException, EmptyProductException, AcceptedRunFoundException {
//		
//		long time = System.currentTimeMillis();
//		int oriAlphabetSize = tgba.getAPs().size();
//		int oriNbStates = tgba.getEdges().size();
//		int oriNbEdge = tgba.getEdges().stream().mapToInt(List::size).sum();
//		boolean wasStutter = tgba.isStutterInvariant();
//		
//		tgba = knowledgeLoop(tgba, knowledge, falseKnowledge, spot);
//				
//		System.out.println("Knowledge based reduction with " + knowledge.size() + " factoid took "
//				+ (System.currentTimeMillis() - time) + " ms. Reduced automaton from " + oriNbStates + " states, "
//				+ oriNbEdge + " edges and " + oriAlphabetSize + " AP (stutter "+ (wasStutter?"insensitive":"sensitive") +") to " + tgba.getEdges().size() + " states, "
//				+ tgba.getEdges().stream().mapToInt(List::size).sum() + " edges and " + tgba.getAPs().size() + " AP (stutter " + (tgba.isStutterInvariant()?"insensitive":"sensitive")+").");		
//	
//		if (tgba.isEmptyLanguage()) {
//			throw new EmptyProductException("KNOWLEDGE");
//		} else if (tgba.isUniversalLanguage()) {
//			throw new AcceptedRunFoundException("KNOWLEDGE");
//		}
//				
//		spot.computeInfStutter(tgba);
//		spot.runLTLSimplifications(spn);
//		
//		return tgba;
//	}
//
//	public static TGBA knowledgeLoop(TGBA tgba, List<Expression> knowledge, List<Expression> falseKnowledge, SpotRunner spot) {
//		
//		
//		
//		
//		if (true) {
//			// Spot 2.11+
//			TGBA tgbarelax = tgba;
//			TGBA res = null;
//			res = spot.givenThat(tgba, knowledge, SpotRunner.GivenStrategy.MINATO);
//			if (res != null) tgbarelax = res;
//			
//			if (tgbarelax.isEmptyLanguage()) {
//				System.out.println("Property proved to be true thanks to knowledge (Minato strategy)");
//				return tgbarelax;
//			} else if (tgbarelax.isUniversalLanguage()) {
//				System.out.println("Property proved to be false thanks to knowledge (Minato strategy)");
//				return tgbarelax;
//			}
//			
//			// more aggressive : AND the knowledge
//			{
//				Expression allFacts = Expression.nop(Op.AND, knowledge);
//				res = spot.givenThat(tgbarelax, allFacts, SpotRunner.GivenStrategy.MINATO);
//				if (res != null) tgbarelax = res;
//				res = spot.givenThat(tgbarelax, allFacts, SpotRunner.GivenStrategy.STUTTER_RELAX);
//				if (res != null) tgbarelax = res;
//				res = spot.givenThat(tgbarelax, allFacts, SpotRunner.GivenStrategy.MINATO);
//				if (res != null) tgbarelax = res;
//				res = spot.givenThat(tgbarelax, allFacts, SpotRunner.GivenStrategy.STUTTER_RELAX);
//				if (res != null) tgbarelax = res;
//				res = spot.givenThat(tgbarelax, allFacts, SpotRunner.GivenStrategy.ALL);
//				if (res != null) tgbarelax = res;
//			}
//	
//			if (tgbarelax.isEmptyLanguage()) {
//				System.out.println("Property proved to be true thanks to conjunction of knowledge (Minato strategy)");
//				return tgbarelax;
//			} else if (tgbarelax.isUniversalLanguage()) {
//				System.out.println("Property proved to be false thanks to conjunction of knowledge (Minato strategy)");
//				return tgbarelax;
//			}
//	
//			
//			if (! tgba.isStutterInvariant() && tgbarelax.isStutterInvariant()) {
//				System.out.println("Knowledge sufficient to adopt a stutter insensitive property.");
//			}
//	
//			
//			// test inclusion
//			// autfilt --included-in=AnotPhi.hoa Kmoins.hoa
//			{
//				Expression allFacts = Expression.nop(Op.AND, knowledge);
//				for (Expression factoid : falseKnowledge) {				
//					Expression negFact = Expression.op(Op.AND,allFacts,factoid);
//					if (spot.isIncludedIn(negFact,tgbarelax)) {
//						System.out.println("Property proved to be false thanks to negative knowledge :" + factoid);
//						return TGBA.makeTrue();				
//					}
//				}
//			}
//			return tgbarelax;
//		} else {
//		
//			// OLD STYLE : Spot 2.10.4.dev "manual" knowledge loop
//			// counter-examples ?
//			{
//				TGBA tgbarelax = tgba;
//				for (Expression factoid : knowledge) {
//					tgbarelax = spot.givenThat(tgbarelax, factoid, SpotRunner.GivenStrategy.RELAX);
//					if (tgba.isEmptyLanguage()) {
//						System.out.println("Property proved to be true thanks to knowledge :" + factoid);
//						return tgbarelax;
//					} else if (tgba.isUniversalLanguage()) {
//						System.out.println("Property proved to be false thanks to knowledge :" + factoid);
//						return tgbarelax;
//					}
//				}
//				// test inclusion
//				// autfilt --included-in=AnotPhi.hoa Kmoins.hoa
//				for (Expression factoid : falseKnowledge) {
//					if (spot.isIncludedIn(factoid,tgbarelax)) {
//						System.out.println("Property proved to be false thanks to negative knowledge :" + factoid);
//						return TGBA.makeTrue();				
//					}
//				}
//			}
//	
//	
//			for (Expression factoid : knowledge) {
//				tgba = spot.givenThat(tgba, factoid, SpotRunner.GivenStrategy.RESTRICT);
//				if (tgba.isEmptyLanguage()) {
//					System.out.println("Property proved to be true thanks to knowledge :" + factoid);
//					return tgba;
//				}
//			}
//	
//			if (!tgba.isStutterInvariant()) {
//				Expression allFacts = Expression.nop(Op.AND, knowledge);
//				tgba = spot.givenThat(tgba, allFacts, SpotRunner.GivenStrategy.STUTTER_RELAX);
//				if (tgba.isStutterInvariant()) {
//					System.out.println("Knowledge sufficient to adopt a stutter insensitive property.");
//				}
//			}
//	
//			for (Expression factoid : knowledge) {
//				tgba = spot.givenThat(tgba, factoid, SpotRunner.GivenStrategy.RELAX);
//				if (tgba.isEmptyLanguage()) {
//					System.out.println("Property proved to be true thanks to knowledge :" + factoid);
//					return tgba;
//				} else if (tgba.isUniversalLanguage()) {
//					System.out.println("Property proved to be false thanks to knowledge :" + factoid);
//					return tgba;
//				}
//			}
//		}
//		return tgba;
//	}

}
