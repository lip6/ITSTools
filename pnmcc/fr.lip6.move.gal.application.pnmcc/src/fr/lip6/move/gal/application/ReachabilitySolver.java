package fr.lip6.move.gal.application;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import android.util.SparseIntArray;
import fr.lip6.move.gal.gal2smt.DeadlockTester;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.DeadlockFound;
import fr.lip6.move.gal.structural.NoDeadlockExists;
import fr.lip6.move.gal.structural.RandomExplorer;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.StructuralReduction.ReductionType;
import fr.lip6.move.gal.structural.expr.BinOp;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;

public class ReachabilitySolver {

	public static void checkInInitial(MccTranslator reader, DoneProperties doneProps) {
		for (fr.lip6.move.gal.structural.Property prop : new ArrayList<>(reader.getSPN().getProperties())) {
			if (prop.getBody().getOp() == Op.BOOLCONST) {
				doneProps.put(prop.getName(),prop.getBody().getValue()==1,"TOPOLOGICAL INITIAL_STATE");
				reader.getSPN().getProperties().remove(prop);
			}
		}
	}

	public static void applyReductions(MccTranslator reader, DoneProperties doneProps, String solverPath, boolean isSafe)
				throws NoDeadlockExists, DeadlockFound {
			int iter;
			int iterations =0;
			boolean doneAtoms = false;
			boolean doneSums = false;
			do {
				iter =0;
				SparsePetriNet spn = reader.getSPN();
				
				StructuralReduction sr = new StructuralReduction(spn);
	
				//  need to protect some variables
				List<Integer> tocheckIndexes = new ArrayList<>();
				List<Expression> tocheck = new ArrayList<>(spn.getProperties().size());
				computeToCheck(spn, tocheckIndexes, tocheck);
				RandomExplorer re = new RandomExplorer(sr);
				int steps = 1000000; // 1 million
				if (iterations == 0 && iter==0) {
					steps = 10000; // be more moderate on first run : 100k
				}
				if (randomCheckReachability(re, tocheck, spn, doneProps,steps) >0)
					iter++;
						
				if (reader.getSPN().getProperties().isEmpty())
					break;
				
				if (solverPath != null) {
					List<Integer> repr = new ArrayList<>();
					List<SparseIntArray> paths = DeadlockTester.testUnreachableWithSMT(tocheck, sr, solverPath, isSafe, repr, iterations==0 ? 5:45,true);
					
					iter += treatVerdicts(reader.getSPN(), doneProps, tocheck, tocheckIndexes, paths);
									
					for (int v = paths.size()-1 ; v >= 0 ; v--) {
						SparseIntArray parikh = paths.get(v);
						if (parikh != null) {
							// we have a candidate, try a Parikh satisfaction run. 
							int sz = 0;
							for (int i=0 ; i < parikh.size() ; i++) {
								sz += parikh.valueAt(i);
							}
							if (sz != 0) {
								if (Application.DEBUG >= 1) {
									System.out.println("SMT solver thinks a reachable witness state is likely to occur in "+sz +" steps.");
									SparseIntArray init = new SparseIntArray();	
									for (int i=0 ; i < parikh.size() ; i++) {
										System.out.print(sr.getTnames().get(parikh.keyAt(i))+"="+ parikh.valueAt(i)+", ");
										init = SparseIntArray.sumProd(1, init, - parikh.valueAt(i), sr.getFlowPT().getColumn(parikh.keyAt(i)));
										init = SparseIntArray.sumProd(1, init, + parikh.valueAt(i), sr.getFlowTP().getColumn(parikh.keyAt(i)));
									}
									System.out.println();
									{
										System.out.println("This Parikh overall has effect " + init);
										SparseIntArray is = new SparseIntArray(sr.getMarks());
										System.out.println("Initial state is " + is);
										System.out.println("Reached state is " + SparseIntArray.sumProd(1, is, 1, init));
									}
								}
	//							StringBuilder sb = new StringBuilder();
	//							for (int i=0 ; i < parikh.size() ; i++) {
	//								sb.append(sr.getTnames().get(parikh.keyAt(i))+"="+ parikh.valueAt(i)+", ");
	//							}
	//							sb.append(SerializationUtil.getText(reader.getSpec().getProperties().get(v).getBody(),false));
	//							//sb.append(tocheck.get(v));
	//							Set<Integer> toHL = new HashSet<>();
	//							for (int i=0;i <consumed.size() ; i++) {
	//								if (init.get(consumed.keyAt(i))==0 && sr.getMarks().get(consumed.keyAt(i)) ==0) {
	//									toHL.add(consumed.keyAt(i));
	//								}
	//							}
	//							FlowPrinter.drawNet(sr, "Parikh Test :" + sb.toString(),toHL,Collections.emptySet());
								int[] verdicts = re.runGuidedReachabilityDetection(100*sz, parikh, tocheck,repr,30);
								interpretVerdict(tocheck, spn, doneProps, verdicts, "PARIKH");
								if (tocheck.isEmpty()) {
									break;
								}
							}
						}
					}
					if (spn.getProperties().removeIf(p -> doneProps.containsKey(p.getName())))
						iter++;
					
				}
				
				if (spn.getProperties().removeIf(p -> doneProps.containsKey(p.getName())))
					iter++;
				if (spn.getProperties().isEmpty())
					break;
				
				
				BitSet support = spn.computeSupport();
				System.out.println("Support contains "+support.cardinality() + " out of " + sr.getPnames().size() + " places. Attempting structural reductions.");
				
				sr.setProtected(support);
				if (applyReductions(sr, reader, ReductionType.SAFETY, solverPath, isSafe,false,iterations==0)) {
					iter++;					
				} else if (iterations>0 && iter==0  /*&& doneSums*/ && applyReductions(sr, reader, ReductionType.SAFETY, solverPath, isSafe,true,false)) {
					iter++;
				}
				// FlowPrinter.drawNet(sr, "Final Model", 1000);
				spn.readFrom(sr);
				spn.testInInitial();
				spn.removeConstantPlaces();
				spn.simplifyLogic();			
				checkInInitial(reader, doneProps);
				
				if (reader.getSPN().getProperties().isEmpty()) {
					return;
				}
	/*			
				if ( (iter == 0 || iterations >=1) && !doneSums) {
					iter++;
					doneSums = true;
					if (reader.rewriteSums())
						reader.flattenSpec(false);
				}
	*/
				
				if (iter == 0 && !doneAtoms) {
	//					SerializationUtil.systemToFile(reader.getSpec(), "/tmp/before.gal");
					if (new AtomicReducerSR().strongReductions(solverPath, reader, isSafe, doneProps) > 0) {
						checkInInitial(reader, doneProps);
						iter++;
					}
					doneAtoms = true;
	//					reader.rewriteSums();
	//					SerializationUtil.systemToFile(reader.getSpec(), "/tmp/after.gal");
				}
				if (reader.getSPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName())))
					iter++;
	
							
				
				iterations++;
			} while ( (iterations<=1 || iter > 0) && ! reader.getSPN().getProperties().isEmpty());
			
			if (! reader.getSPN().getProperties().isEmpty()) {
				// try to disprove on an overapprox.
				StructuralReduction sr = new StructuralReduction(reader.getSPN());
				sr.abstractReads();
				sr.reduce(ReductionType.SAFETY);
				
				
				List<Integer> tocheckIndexes = new ArrayList<>();
				SparsePetriNet spn = new SparsePetriNet(reader.getSPN());
				spn.readFrom(sr);
				List<Expression> tocheck = new ArrayList<>(spn.getProperties().size());
				computeToCheck(spn, tocheckIndexes, tocheck);
				
				List<Integer> repr = new ArrayList<>();
				if (solverPath != null) {
					List<SparseIntArray> paths = DeadlockTester.testUnreachableWithSMT(tocheck, sr, solverPath, isSafe, repr, iterations==0 ? 5:45,true);
	
					iter += treatVerdicts(spn, doneProps, tocheck, tocheckIndexes, paths, "OVER_APPROXIMATION");
				}
			}
		}

	public static void computeToCheck(SparsePetriNet spn, List<Integer> tocheckIndexes, List<Expression> tocheck) {
		for (fr.lip6.move.gal.structural.Property p : spn.getProperties()) {
			if (p.getBody().getOp() == Op.EF) {
				tocheck.add(((BinOp)p.getBody()).left);
			} else if (p.getBody().getOp() == Op.AG) {
				tocheck.add(Expression.not(((BinOp)p.getBody()).left));
			}
		}			
		for (int j=0; j < spn.getProperties().size(); j++) { tocheckIndexes.add(j);}
	}

	static int treatVerdicts(SparsePetriNet sparsePetriNet, DoneProperties doneProps, List<Expression> tocheck,
			List<Integer> tocheckIndexes, List<SparseIntArray> paths) {
		return treatVerdicts(sparsePetriNet, doneProps, tocheck, tocheckIndexes, paths, "");
	}

	static int treatVerdicts(SparsePetriNet spn, DoneProperties doneProps, List<Expression> tocheck,
			List<Integer> tocheckIndexes, List<SparseIntArray> paths, String technique) {
		int iter = 0;
		for (int v = paths.size()-1 ; v >= 0 ; v--) {
			SparseIntArray parikh = paths.get(v);
			if (parikh == null) {
				fr.lip6.move.gal.structural.Property prop = spn.getProperties().get(tocheckIndexes.get(v));
				if (prop.getBody().getOp() == Op.EF) {
					doneProps.put(prop.getName(),false,"STRUCTURAL_REDUCTION TOPOLOGICAL SAT_SMT "+technique);
				} else {
					// AG
					doneProps.put(prop.getName(),true,"STRUCTURAL_REDUCTION TOPOLOGICAL SAT_SMT "+technique);
				}
				
				tocheck.remove(v);
				tocheckIndexes.remove(v);
				iter++;
			} 
		}
		if (spn.getProperties().removeIf(p -> doneProps.containsKey(p.getName())))
			iter++;
		return iter;
	}

	static int randomCheckReachability(RandomExplorer re, List<Expression> tocheck, SparsePetriNet spn,
			DoneProperties doneProps, int steps) {
		int[] verdicts = re.runRandomReachabilityDetection(steps,tocheck,30,-1);
		int seen = interpretVerdict(tocheck, spn, doneProps, verdicts,"RANDOM");
		for (int i=0 ; i < tocheck.size() ; i++) {			
			verdicts = re.runRandomReachabilityDetection(steps,tocheck,5,i);
			for  (int j =0; j <= i ; j++) {
				if (verdicts[j] != 0) 
					i--;
			}
			seen += interpretVerdict(tocheck, spn, doneProps, verdicts,"BESTFIRST");			
		}
		if (seen == 0) {
			RandomExplorer.WasExhaustive wex = new RandomExplorer.WasExhaustive();
			verdicts = re.runProbabilisticReachabilityDetection(steps*1000,tocheck,30,-1,false,wex);
			seen += interpretVerdict(tocheck, spn, doneProps, verdicts,"PROBABILISTIC");
			if (wex.wasExhaustive) {
				wex = new RandomExplorer.WasExhaustive();
				verdicts = re.runProbabilisticReachabilityDetection(steps*1000,tocheck,30,-1,true,wex);				
				seen += interpretVerdict(tocheck, spn, doneProps, verdicts,"EXHAUSTIVE",wex.wasExhaustive);
			}
		}
		return seen;
	}

	private static int interpretVerdict(List<Expression> tocheck, SparsePetriNet spn, DoneProperties doneProps,
			int[] verdicts, String walkType) {
		return interpretVerdict(tocheck, spn, doneProps, verdicts, walkType, false);
	}

	private static int interpretVerdict(List<Expression> tocheck, SparsePetriNet spn, DoneProperties doneProps,
			int[] verdicts, String walkType, boolean andNeg) {
		int seen = 0; 
		for (int v = verdicts.length-1 ; v >= 0 ; v--) {
			if (verdicts[v] != 0) {
				fr.lip6.move.gal.structural.Property prop = spn.getProperties().get(v);
				if (prop.getBody().getOp() == Op.EF) {
					doneProps.put(prop.getName(),true,"TOPOLOGICAL "+walkType+"_WALK");
				} else {
					doneProps.put(prop.getName(),false,"TOPOLOGICAL "+walkType+"_WALK");
				}				
				tocheck.remove(v);
				spn.getProperties().remove(v);
				seen++;
			} else if (andNeg) {
				fr.lip6.move.gal.structural.Property prop = spn.getProperties().get(v);
				if (prop.getBody().getOp() == Op.EF) {
					doneProps.put(prop.getName(),false,"TOPOLOGICAL "+walkType+"_WALK");
				} else {
					doneProps.put(prop.getName(),true,"TOPOLOGICAL "+walkType+"_WALK");
				}				
				tocheck.remove(v);
				spn.getProperties().remove(v);
				seen++;
			}
		}
		return seen;
	}

	static boolean applyReductions(StructuralReduction sr, MccTranslator reader, ReductionType rt, String solverPath, boolean isSafe, boolean withSMT, boolean isFirstTime)
			throws NoDeadlockExists, DeadlockFound {
		boolean cont = false;
		int it =0;
		int initp = sr.getPnames().size();
		int initt = sr.getTnames().size();
		int total = 0;
		do {
			System.out.println("Starting structural reductions, iteration "+ it + " : " + sr.getPnames().size() +"/" +initp+ " places, " + sr.getTnames().size()+"/"+initt + " transitions.");
			
			int reduced = 0; 
									
			// The big one ! apply our set of reduction rules, customized by ReductionType, until convergence.
			reduced += sr.reduce(rt);
			total+=reduced;
			cont = false;
			
			// Quick test for deadlocks, no more transitions.
			if (rt == ReductionType.DEADLOCKS && sr.getTnames().isEmpty()) {
				throw new DeadlockFound();
			}
			
			// Coming mostly from colored models with an arc <x>+<y> from a colored place
			// when the net is color safe (unfolded version is 1 safe) all bindings with
			// x=y become unfeasible. Removing them makes the net much simpler, no more arc weights !=1, less transitions...
			if (isFirstTime && it==0) {
				boolean hasReduced = arcValuesTriggerSMTDeadTransitions(sr, solverPath, isSafe);
				if (hasReduced) {
					cont=true;
					total++;
				}
			}
			
			// implicit and dead transitions test using SMT
			// We pass iteration counter and reduced counter to delay more costly versions with state equation.
			if (withSMT && solverPath != null) {
				boolean hasReduced = applySMTBasedReductionRules(sr, rt, it, solverPath, isSafe, reduced);
				if (hasReduced) {
					cont = true;
					total++;
				}
			}
			if (!cont && rt == ReductionType.SAFETY && withSMT) {
				cont = sr.ruleFreeAgglo(true) > 0;
			}
			it++;
		} while (cont);
		System.out.println("Finished structural reductions, in "+ it + " iterations. Remains : " + sr.getPnames().size() +"/" +initp+ " places, " + sr.getTnames().size()+"/"+initt + " transitions.");
		return total > 0;
	}

	private static boolean applySMTBasedReductionRules(StructuralReduction sr, ReductionType rt, int iteration,
			String solverPath, boolean isSafe, int reduced) throws NoDeadlockExists {
		boolean hasReduced = false;
		boolean useStateEq = false;
		if (reduced > 0 || iteration ==0) {
			long t = System.currentTimeMillis();
			// 	go for more reductions ?
			
			List<Integer> implicitPlaces = DeadlockTester.testImplicitWithSMT(sr, solverPath, isSafe, false);							
			if (!implicitPlaces.isEmpty()) {
				sr.dropPlaces(implicitPlaces,false,"Implicit Places With SMT (invariants only)");
				sr.ruleReduceTrans(rt);
				hasReduced = true;
			} else if (sr.getPnames().size() <= 10000 && sr.getTnames().size() < 10000){
				// limit to 20 k variables for SMT solver with parikh constraints
				useStateEq = true;
				// with state equation can we solve more ?
				implicitPlaces = DeadlockTester.testImplicitWithSMT(sr, solverPath, isSafe, true);
				if (!implicitPlaces.isEmpty()) {
					sr.dropPlaces(implicitPlaces,false,"Implicit Places With SMT (with state equation)");
					sr.ruleReduceTrans(rt);
					reduced += implicitPlaces.size();							
					hasReduced = true;
				}
			}							
			System.out.println("Implicit Place search using SMT "+ (useStateEq?"with State Equation":"only with invariants") +" took "+ (System.currentTimeMillis() -t) +" ms to find "+implicitPlaces.size()+ " implicit places.");
		}

		if (reduced == 0 || iteration==0) {
			List<Integer> tokill = DeadlockTester.testImplicitTransitionWithSMT(sr, solverPath);
			if (! tokill.isEmpty()) {
				System.out.println("Found "+tokill.size()+ " redundant transitions using SMT." );
			}
			sr.dropTransitions(tokill,"Redundant Transitions using SMT "+ (useStateEq?"with State Equation":"only with invariants") );
			if (!tokill.isEmpty()) {
				System.out.println("Redundant transitions reduction (with SMT) removed "+tokill.size()+" transitions :"+ tokill);								
				hasReduced = true;
			}
		}
		if (reduced == 0 || iteration==0) {
			List<Integer> tokill = DeadlockTester.testDeadTransitionWithSMT(sr, solverPath, isSafe);
			if (! tokill.isEmpty()) {
				System.out.println("Found "+tokill.size()+ " dead transitions using SMT." );
			}
			sr.dropTransitions(tokill,"Dead Transitions using SMT only with invariants");
			if (!tokill.isEmpty()) {
				System.out.println("Dead transitions reduction (with SMT) removed "+tokill.size()+" transitions :"+ tokill);								
				hasReduced = true;
			}
		}
		return hasReduced;
	}

	private static boolean arcValuesTriggerSMTDeadTransitions(StructuralReduction sr, String solverPath, boolean isSafe) {
		boolean hasGT1ArcValues = false;
		for (int t=0,te=sr.getTnames().size() ; t < te && !hasGT1ArcValues; t++) {
			SparseIntArray col = sr.getFlowPT().getColumn(t);
			for (int i=0,ie=col.size(); i < ie ; i++) {
				if (col.valueAt(i)>1) {
					hasGT1ArcValues = true;
					break;
				}
			}					
		}
		
		if (hasGT1ArcValues) {
			List<Integer> tokill = DeadlockTester.testDeadTransitionWithSMT(sr, solverPath, isSafe);
			if (! tokill.isEmpty()) {
				System.out.println("Found "+tokill.size()+ " dead transitions using SMT." );
			}
			sr.dropTransitions(tokill,"Dead Transitions using SMT only with invariants");
			if (!tokill.isEmpty()) {
				System.out.println("Dead transitions reduction (with SMT) triggered by suspicious arc values removed "+tokill.size()+" transitions :"+ tokill);								
				return true;						
			}
		}
		return false;
	}

}
