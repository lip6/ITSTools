package fr.lip6.move.gal.application.solver;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.util.SparseIntArray;
import fr.lip6.move.gal.application.Application;
import fr.lip6.move.gal.application.mcc.MccTranslator;
import fr.lip6.move.gal.application.solver.global.GlobalPropertySolver;
import fr.lip6.move.gal.application.solver.logic.AtomicReducerSR;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.DeadlockFound;
import fr.lip6.move.gal.structural.GlobalPropertySolvedException;
import fr.lip6.move.gal.structural.PetriNet;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.RandomExplorer;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.StructuralReduction.ReductionType;
import fr.lip6.move.gal.structural.expr.BinOp;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.smt.DeadlockTester;

public class ReachabilitySolver {

	public static int checkInInitial(PetriNet pn, DoneProperties doneProps) {
		int done = 0;
		for (fr.lip6.move.gal.structural.Property prop : new ArrayList<>(pn.getProperties())) {
			if (prop.getBody().getOp() == Op.BOOLCONST) {
				doneProps.put(prop.getName(),prop.getBody().getValue()==1,"TOPOLOGICAL INITIAL_STATE");
				done++;
			} else if (prop.getType() == PropertyType.BOUNDS && prop.getBody().getOp()== Op.CONST) {
				doneProps.put(prop.getName(),prop.getBody().getValue(),"TOPOLOGICAL INITIAL_STATE");
				done++;
			}
		}
		pn.getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
		return done;
	}

	public static void applyReductions(MccTranslator reader, DoneProperties doneProps, String solverPath, int timeout)
				throws GlobalPropertySolvedException {
			int iter;
			int iterations =0;
			long initial=System.currentTimeMillis();
			boolean doneAtoms = false;
			boolean doneSums = false;
			boolean doneAtomsSR = false;
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
				if ((iterations == 0 && iter==0) || timeout != -1) {
					steps = 10000; // be more moderate on first run : 100k
				}
				if (randomCheckReachability(re, tocheck, spn, doneProps,steps) >0)
					iter++;
						
				if (reader.getSPN().getProperties().isEmpty() || doneProps.isFinished())
					break;
				
				if (solverPath != null) {
					List<Integer> repr = new ArrayList<>();
					List<SparseIntArray> orders = new ArrayList<>();
					int smttime = iterations==0 ? 5:45;
					if (timeout != -1) {
						smttime = 5;
					}
					List<SparseIntArray> paths = DeadlockTester.testUnreachableWithSMT(tocheck, sr, solverPath, repr, smttime,true,orders);
					
					iter += treatSMTVerdicts(reader.getSPN(), doneProps, tocheck, tocheckIndexes, paths);
					long time = System.currentTimeMillis();
					
					Map<SparseIntArray,List<Integer>> indexMap = new LinkedHashMap<>();
			        for (int i=0 ; i < paths.size() ; i++) {
			          SparseIntArray val = paths.get(i);
			          if (val != null)
			        	  indexMap.computeIfAbsent(val,k->new ArrayList<>()).add(i);
			        }
			        if (indexMap.size() < paths.size()) {
			        	System.out.println("Fused "+paths.size()+" Parikh solutions to " + indexMap.size() +" different solutions.");
			        }
					int maxTime = 100;
					if (indexMap.size() >= 20 || timeout != -1) {
						maxTime = 30;
					}
			        for (Entry<SparseIntArray, List<Integer>> ent:indexMap.entrySet()) {
			        	int v = ent.getValue().get(0);
						if (System.currentTimeMillis() - time >= maxTime * 1000) {
							break;
						}
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
								int maxt = 30;
								int maxsteps = 100*sz;
								if (tocheck.size() >= 200) {
									maxt = 1;
									maxsteps = sz;
								} else if (tocheck.size() >= 100 || timeout != -1) {
									maxt = 2;
									maxsteps = 2*sz;
								} else if (tocheck.size() >= 16) {
									maxt = 5;
									maxsteps = 10*sz;
								}
								int [] verdicts = re.runGuidedReachabilityDetection(maxsteps, parikh, orders.get(v), tocheck,repr,maxt,false);
								interpretWalkerVerdict(tocheck, spn, doneProps, verdicts, "PARIKH");
								if (tocheck.isEmpty()) {
									break;
								}
							}
						}
					}
					if (spn.getProperties().removeIf(p -> doneProps.containsKey(p.getName())))
						iter++;
					
				}
				ReachabilitySolver.checkInInitial(spn, doneProps);
				if (spn.getProperties().removeIf(p -> doneProps.containsKey(p.getName())))
					iter++;
				if (spn.getProperties().isEmpty())
					break;
				
				
				BitSet support = spn.computeSupport();
				System.out.println("Support contains "+support.cardinality() + " out of " + sr.getPnames().size() + " places. Attempting structural reductions.");
				
				sr.setProtected(support);
				if (applyReductions(sr, ReductionType.SAFETY, solverPath, false,iterations==0)) {
					iter++;					
				} else if (iterations>0 && iter==0  /*&& doneSums*/ && applyReductions(sr, ReductionType.SAFETY, solverPath, true,false)) {
					iter++;
				}
				// FlowPrinter.drawNet(sr, "Final Model", 1000);
				spn.readFrom(sr);
				spn.testInInitial();
				spn.removeConstantPlaces();
				spn.simplifyLogic();			
				if (checkInInitial(spn, doneProps) >0) {
					iter++;
				}
				
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
				
				if (timeout!=-1 && System.currentTimeMillis() - initial >= timeout *1000)
					return;
				
				if (iter == 0 && !doneAtoms && timeout == -1) {
	//					SerializationUtil.systemToFile(reader.getSpec(), "/tmp/before.gal");
					new AtomicReducerSR().strongReductions(solverPath, reader.getSPN(), doneProps, null, false);
					checkInInitial(reader.getSPN(), doneProps);
					iter++;
					
					doneAtoms = true;
	//					reader.rewriteSums();
	//					SerializationUtil.systemToFile(reader.getSpec(), "/tmp/after.gal");
				}
				if (reader.getSPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName())))
					iter++;
	
				int timeSDD = Math.max(5*(iterations+1),15);
				// GlobalPropertySolver.verifyWithSDD(reader, doneProps, "ReachabilityCardinality", solverPath, timeSDD);			
				
				if (reader.getSPN().getProperties().removeIf(p -> doneProps.containsKey(p.getName())))
					iter++;
				iterations++;
				
				if (timeout!=-1 && System.currentTimeMillis() - initial >= timeout *1000)
					return;
				
				if (iterations > 1 && iter==0 && !doneAtomsSR && timeout==-1) {
					int n = new AtomicReducerSR().checkAtomicPropositions(reader.getSPN(), doneProps, solverPath, true);
					n += checkInInitial(reader.getSPN(), doneProps);
					iter +=n;
					doneAtomsSR = true;
				}
			} while ( (iterations<=1 || iter > 0) && ! reader.getSPN().getProperties().isEmpty() && !doneProps.isFinished());
			
			if (! reader.getSPN().getProperties().isEmpty() && !doneProps.isFinished()) {
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
					List<SparseIntArray> paths = DeadlockTester.testUnreachableWithSMT(tocheck, sr, solverPath, repr, iterations==0 ? 5:45,true);
	
					iter += treatSMTVerdicts(spn, doneProps, tocheck, tocheckIndexes, paths, "OVER_APPROXIMATION");
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

	static int treatSMTVerdicts(SparsePetriNet sparsePetriNet, DoneProperties doneProps, List<Expression> tocheck,
			List<Integer> tocheckIndexes, List<SparseIntArray> paths) {
		return treatSMTVerdicts(sparsePetriNet, doneProps, tocheck, tocheckIndexes, paths, "");
	}

	static int treatSMTVerdicts(SparsePetriNet spn, DoneProperties doneProps, List<Expression> tocheck,
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
		int seen = interpretWalkerVerdict(tocheck, spn, doneProps, verdicts,"RANDOM");
		if (tocheck.size() >= 15 && tocheck.size() < 100) {
			steps /= 10;
		}
		if (tocheck.size() >= 100 && tocheck.size() < 500) {
			steps /= 100;
		}
		if (tocheck.size() >= 500) {
			steps /= 1000;
		}
		if (steps <= 30) {
			steps = 30;
		}
		int seen100 = 0;
		for (int i=0 ; i < tocheck.size() && i-seen100 < 50; i++) {			
			verdicts = re.runRandomReachabilityDetection(steps,tocheck,5,i);
			for  (int j =0; j <= i ; j++) {
				if (verdicts[j] != 0) 
					i--;
			}
			int seen1 = interpretWalkerVerdict(tocheck, spn, doneProps, verdicts,"BESTFIRST");
			seen+=seen1;
			if (seen1 != 0) seen100 = i;
		}
		if (seen == 0 || seen <= tocheck.size() / 10) {
			RandomExplorer.WasExhaustive wex = new RandomExplorer.WasExhaustive();
			verdicts = re.runProbabilisticReachabilityDetection(steps*1000,tocheck,30,-1,false,wex);
			seen += interpretWalkerVerdict(tocheck, spn, doneProps, verdicts,"PROBABILISTIC");
			if (wex.wasExhaustive) {
				wex = new RandomExplorer.WasExhaustive();
				verdicts = re.runProbabilisticReachabilityDetection(steps*1000,tocheck,30,-1,true,wex);				
				seen += interpretWalkerVerdict(tocheck, spn, doneProps, verdicts,"EXHAUSTIVE",wex.wasExhaustive);
			}
		}
		return seen;
	}

	private static int interpretWalkerVerdict(List<Expression> tocheck, SparsePetriNet spn, DoneProperties doneProps,
			int[] verdicts, String walkType) {
		return interpretWalkerVerdict(tocheck, spn, doneProps, verdicts, walkType, false);
	}

	private static int interpretWalkerVerdict(List<Expression> tocheck, SparsePetriNet spn, DoneProperties doneProps,
			int[] verdicts, String walkType, boolean wasExhaustiveWalk) {
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
			} else if (wasExhaustiveWalk) {
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

	public static boolean applyReductions(StructuralReduction sr, ReductionType rt, String solverPath, boolean withSMT, boolean isFirstTime)
			throws GlobalPropertySolvedException {
		boolean cont = false;
		int it =0;
		int initp = sr.getPnames().size();
		int initt = sr.getTnames().size();
		int total = 0;
		
		int lastReduction = -1;
		do {
			System.out.println("Starting structural reductions in "+ rt +" mode, iteration "+ it + " : " + sr.getPnames().size() +"/" +initp+ " places, " + sr.getTnames().size()+"/"+initt + " transitions.");
			
			int reduced = 0; 
									
			// The big one ! apply our set of reduction rules, customized by ReductionType, until convergence.
			if (lastReduction == 0) {
				break;				
			} else {
				reduced += sr.reduce(rt);
			}
			total+=reduced;
			
			if (reduced > 0) {
				lastReduction = 0;
			}
			cont = false;
			
			// Quick test for deadlocks, no more transitions.
			if (rt == ReductionType.DEADLOCKS && sr.getTnames().isEmpty()) {
				throw new DeadlockFound();
			}
			
			// Coming mostly from colored models with an arc <x>+<y> from a colored place
			// when the net is color safe (unfolded version is 1 safe) all bindings with
			// x=y become unfeasible. Removing them makes the net much simpler, no more arc weights !=1, less transitions...
			if (lastReduction == 1) {
				break;
			} else if (isFirstTime && it==0) {
				boolean hasReduced = arcValuesTriggerSMTDeadTransitions(sr, solverPath, rt);
				if (hasReduced) {
					cont=true;
					total++;
					lastReduction=1;
				}
			}
			
			// implicit and dead transitions test using SMT
			// We pass iteration counter and reduced counter to delay more costly versions with state equation.
			if (withSMT && solverPath != null) {
				int hasReduced = applySMTBasedReductionRules(sr, rt, it, solverPath, reduced, lastReduction);
				if (hasReduced != -1) {
					lastReduction=hasReduced;
					cont = true;
					total++;
				}
			}
			if (lastReduction == 5) {
				break;
			} else if (!cont && rt == ReductionType.SAFETY && withSMT) {
				if (sr.ruleFreeAgglo(true) > 0) {
					cont=true;
					lastReduction = 5;
				}
			}
			it++;
		} while (cont);
		System.out.println("Finished structural reductions, in "+ it + " iterations. Remains : " + sr.getPnames().size() +"/" +initp+ " places, " + sr.getTnames().size()+"/"+initt + " transitions.");
		return total > 0;
	}

	private static int applySMTBasedReductionRules(StructuralReduction sr, ReductionType rt, int iteration,
			String solverPath, int reduced, int lastReduction) throws GlobalPropertySolvedException {
		boolean hasReduced = false;
		boolean useStateEq = false;
		// we'd love to do this but it messes up some metrics on token counts.
		if (lastReduction != 2) {

			if (rt != ReductionType.STATESPACE &&  (reduced > 0 || iteration ==0) && !sr.isKeepImage()) {
				long t = System.currentTimeMillis();
				// 	go for more reductions ?

				List<Integer> implicitPlaces = DeadlockTester.testImplicitWithSMT(sr, solverPath, false);							
				if (!implicitPlaces.isEmpty()) {
					sr.dropPlaces(implicitPlaces,false,"Implicit Places With SMT (invariants only)");
					sr.ruleReduceTrans(rt);
					hasReduced = true;
					lastReduction=2;
				} else if (sr.getPnames().size() <= 10000 && sr.getTnames().size() < 10000){
					// limit to 20 k variables for SMT solver with parikh constraints
					useStateEq = true;
					// with state equation can we solve more ?
					implicitPlaces = DeadlockTester.testImplicitWithSMT(sr, solverPath, true);
					if (!implicitPlaces.isEmpty()) {
						sr.dropPlaces(implicitPlaces,false,"Implicit Places With SMT (with state equation)");
						sr.ruleReduceTrans(rt);
						reduced += implicitPlaces.size();							
						hasReduced = true;
						lastReduction=2;
					}
				}							
				System.out.println("Implicit Place search using SMT "+ (useStateEq?"with State Equation":"only with invariants") +" took "+ (System.currentTimeMillis() -t) +" ms to find "+implicitPlaces.size()+ " implicit places.");
			}
		} else {
			return -1;
		}
		// ok we got some implicit places, let the normal rules have another try.
		if (hasReduced)
			return lastReduction;
		if (lastReduction != 3) {
			if (rt != ReductionType.LIVENESS && rt != ReductionType.LTL && rt != ReductionType.SLCL_LTL &&(reduced == 0 || iteration==0)) {
				List<Integer> tokill = DeadlockTester.testImplicitTransitionWithSMT(sr, solverPath);
				if (! tokill.isEmpty()) {
					System.out.println("Found "+tokill.size()+ " redundant transitions using SMT." );
				}
				sr.dropTransitions(tokill,"Redundant Transitions using SMT "+ (useStateEq?"with State Equation":"only with invariants") );
				if (!tokill.isEmpty()) {
					System.out.println("Redundant transitions reduction (with SMT) removed "+tokill.size()+" transitions." /*+ tokill*/);								
					hasReduced = true;
					lastReduction=3;
				}
			}
		} else {
			return -1;
		}
		if (lastReduction != 4) {
			if (lastReduction != 1 && (reduced == 0 || iteration==0)) {
				List<Integer> tokill = DeadlockTester.testDeadTransitionWithSMT(sr, solverPath);
				if (! tokill.isEmpty()) {
					System.out.println("Found "+tokill.size()+ " dead transitions using SMT." );
					if (rt == ReductionType.LIVENESS) {
						throw new DeadlockFound();
					}
				}
				sr.dropTransitions(tokill,"Dead Transitions using SMT only with invariants");
				if (!tokill.isEmpty()) {
					System.out.println("Dead transitions reduction (with SMT) removed "+tokill.size()+" transitions" /*+ tokill*/);								
					hasReduced = true;
					lastReduction=4;
				}
			}
		} else {
			return -1;
		}
		return lastReduction;
	}

	private static boolean arcValuesTriggerSMTDeadTransitions(StructuralReduction sr, String solverPath, ReductionType rt) throws DeadlockFound {
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
			List<Integer> tokill = DeadlockTester.testDeadTransitionWithSMT(sr, solverPath);
			if (! tokill.isEmpty()) {
				System.out.println("Found "+tokill.size()+ " dead transitions using SMT." );
			}
			sr.dropTransitions(tokill,"Dead Transitions using SMT only with invariants");
			if (!tokill.isEmpty()) {
				System.out.println("Dead transitions reduction (with SMT) triggered by suspicious arc values removed "+tokill.size()+" transitions." /*+ tokill*/);								
				if (rt == ReductionType.LIVENESS) {
					throw new DeadlockFound();
				}
				return true;						
			}
		}
		return false;
	}
	
}
