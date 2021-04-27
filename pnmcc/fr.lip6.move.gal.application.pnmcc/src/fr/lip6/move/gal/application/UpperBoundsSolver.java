package fr.lip6.move.gal.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Set;


import android.util.SparseIntArray;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.DeadlockFound;
import fr.lip6.move.gal.structural.GlobalPropertySolvedException;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.InvariantCalculator;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.RandomExplorer;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.StructuralReduction.ReductionType;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.smt.DeadlockTester;
import fr.lip6.move.gal.util.IntMatrixCol;

public class UpperBoundsSolver {

	public static void checkInInitial(SparsePetriNet spn, DoneProperties doneProps) {
		for (fr.lip6.move.gal.structural.Property prop : new ArrayList<>(spn.getProperties())) {
			if (prop.getBody().getOp() == Op.CONST) {
				doneProps.put(prop.getName(),prop.getBody().getValue(),"TOPOLOGICAL INITIAL_STATE");				
				spn.getProperties().remove(prop);
			}
		}
	}
	
	public static List<Integer> treatSkeleton(MccTranslator reader, DoneProperties doneProps, String solverPath) {
		SparsePetriNet spn = reader.getHLPN().skeleton();
		spn.simplifyLogic();
		spn.toPredicates();			
		spn.testInInitial();
		spn.removeConstantPlaces();
		spn.removeRedundantTransitions(false);
		spn.removeConstantPlaces();
		spn.simplifyLogic();
		
		
	//  need to protect some variables
		List<Integer> tocheckIndexes = new ArrayList<>();
		List<Expression> tocheck = new ArrayList<>(spn.getProperties().size());
		computeToCheck(spn, tocheckIndexes, tocheck, doneProps);
		List<Integer> maxStruct = new ArrayList<>(tocheckIndexes.size());
		List<Integer> maxSeen = new ArrayList<>(tocheckIndexes.size());
		{
			SparseIntArray m0 = new SparseIntArray(spn.getMarks());
			for (Expression tc:tocheck) {
				maxSeen.add(tc.eval(m0));
				maxStruct.add(-1);				
			}
		}
		
		StructuralReduction sr = new StructuralReduction(spn);
		Set<SparseIntArray> invar;
		{
			// effect matrix
			IntMatrixCol sumMatrix = IntMatrixCol.sumProd(-1, spn.getFlowPT(), 1, spn.getFlowTP());
			invar = InvariantCalculator.computePInvariants(sumMatrix, spn.getPnames());
		}
		approximateStructuralBoundsUsingInvariants(sr, invar, tocheck, maxStruct);
		
		checkStatus(spn, tocheck, maxStruct, maxSeen, doneProps, "TOPOLOGICAL CPN_APPROX INITIAL_STATE");
		
		{
			List<SparseIntArray> paths = new ArrayList<>(tocheck.size());
			List<SparseIntArray> orders = new ArrayList<>(tocheck.size());
			for (int i=0; i < tocheck.size(); i++) {
				paths.add(null);
				orders.add(null);
			}
			treatVerdicts(reader.getSPN(), doneProps, tocheck, tocheckIndexes, paths , maxSeen, maxStruct, orders);
		}
		
		if (solverPath != null) {
			List<Integer> repr = new ArrayList<>();
			List<SparseIntArray> paths = DeadlockTester.findStructuralMaxWithSMT(tocheck, maxSeen, maxStruct, sr, solverPath, repr, new ArrayList<>(), 5,true);
			
			//interpretVerdict(tocheck, spn, doneProps, new int[tocheck.size()], solverPath, maxSeen, maxStruct);
			System.out.println("Current structural bounds on expressions (after SMT) : " + maxStruct);

			checkStatus(spn, tocheck, maxStruct, maxSeen, doneProps, "TOPOLOGICAL SAT_SMT CPN_APPROX INITIAL_STATE");
		}
		return maxStruct;
	}

	private static void checkStatus(SparsePetriNet spn, List<Expression> tocheck, List<Integer> maxStruct,
			List<Integer> maxSeen, DoneProperties doneProps, String tech) {
		for (int v = tocheck.size()-1 ; v >= 0 ; v--) {
			if (maxSeen.get(v).equals(maxStruct.get(v))) {
				fr.lip6.move.gal.structural.Property prop = spn.getProperties().get(v);
				doneProps.put(prop.getName(),maxSeen.get(v),tech);
				tocheck.remove(v);
				spn.getProperties().remove(v);
				maxSeen.remove(v);
				maxStruct.remove(v);
			}
		}
	}
	

	public static void applyReductions(SparsePetriNet spn, DoneProperties doneProps, String solverPath, List<Integer> initMaxStruct) {
			int iter;
			int iterations =0;

			//  need to protect some variables
			List<Integer> tocheckIndexes = new ArrayList<>();
			List<Expression> tocheck = new ArrayList<>(spn.getProperties().size());
			computeToCheck(spn, tocheckIndexes, tocheck, doneProps);

			List<Integer> maxStruct = new ArrayList<>(tocheckIndexes.size());
			List<Integer> maxSeen = new ArrayList<>(tocheckIndexes.size());
			{
				SparseIntArray m0 = new SparseIntArray(spn.getMarks());
				for (Expression tc:tocheck) {
					maxSeen.add(tc.eval(m0));
					if (spn.isSafe() && tc.getOp() == Op.PLACEREF) {
						maxStruct.add(1);
					} else {
						maxStruct.add(-1);
					}
				}
			}
			boolean hasSkel = false;
			if (initMaxStruct != null) {
				for (int i=0; i < maxStruct.size() ; i++) {
					maxStruct.set(i, Math.min(maxStruct.get(i)==-1?Integer.MAX_VALUE:maxStruct.get(i), 
							             	  initMaxStruct.get(i)==-1?Integer.MAX_VALUE:initMaxStruct.get(i)));
				}
				hasSkel = true;
			}
			
			checkStatus(spn, tocheck, maxStruct, maxSeen, doneProps, "TOPOLOGICAL INITIAL_STATE");
			
			List<Integer> lastMaxSeen = null;
			
			boolean first = true;
			do {
				iter =0;
				if (! first)  {
					tocheck = new ArrayList<>(spn.getProperties().size());
					computeToCheck(spn, tocheckIndexes, tocheck, doneProps);			
				} else {
					first = false;
				}
				StructuralReduction sr = new StructuralReduction(spn);
				
				if (!hasSkel) {
					// the invariants themselves
					Set<SparseIntArray> invar ;
					{
						// effect matrix
						IntMatrixCol sumMatrix = IntMatrixCol.sumProd(-1, spn.getFlowPT(), 1, spn.getFlowTP());
						invar = InvariantCalculator.computePInvariants(sumMatrix, spn.getPnames());
					}
					approximateStructuralBoundsUsingInvariants(sr, invar, tocheck, maxStruct);
					checkStatus(spn, tocheck, maxStruct, maxSeen, doneProps, "TOPOLOGICAL INITIAL_STATE");
				} else {
					hasSkel = false;
				}
				
				lastMaxSeen = new ArrayList<>(maxSeen);
				RandomExplorer re = new RandomExplorer(sr);
				int steps = 1000000; // 1 million
				if (iterations == 0 && iter==0) {
					steps = 10000; // be more moderate on first run : 100k
				}
				if (randomCheckReachability(re, tocheck, spn, doneProps,steps,maxSeen,maxStruct) >0)
					iter++;
				
				checkStatus(spn, tocheck, maxStruct, maxSeen, doneProps, "TOPOLOGICAL RANDOM_WALK");
				
				if (spn.getProperties().isEmpty())
					break;
				
				if (solverPath != null) {
					List<Integer> repr = new ArrayList<>();
					List<SparseIntArray> orders=new ArrayList<>();
					List<SparseIntArray> paths = DeadlockTester.findStructuralMaxWithSMT(tocheck, maxSeen, maxStruct, sr, solverPath, repr, orders, iterations==0 ? 5:45,true);
					
					//interpretVerdict(tocheck, spn, doneProps, new int[tocheck.size()], solverPath, maxSeen, maxStruct);
					System.out.println("Current structural bounds on expressions (after SMT) : " + maxStruct+ " Max seen :" + maxSeen);

					iter += treatVerdicts(spn, doneProps, tocheck, tocheckIndexes, paths, maxSeen, maxStruct,orders);
									
					
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
								int[] verdicts = re.runGuidedReachabilityDetection(100*sz, parikh, orders.get(v) ,tocheck,repr,30,true);
								iter += interpretVerdict(tocheck, spn, doneProps, verdicts,"PARIKH",maxSeen,maxStruct);
								//iter += treatVerdicts(reader.getSPN(), doneProps, tocheck, tocheckIndexes, paths, maxSeen, maxStruct);
								
								//interpretVerdict(tocheck, spn, doneProps, verdicts, "PARIKH",maxSeen,maxStruct);
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
				
				if (spn.isSafe() && tocheck.size() == 1 && support.cardinality()==1) {
					int pid = support.nextSetBit(0);
					List<Integer> tfeed = new ArrayList<>(sr.getFlowPT().getColumn(pid).size());
					SparseIntArray fpt = sr.getFlowPT().transpose().getColumn(pid);
					for (int i = fpt.size() - 1 ; i >= 0 ; i--) {
						tfeed.add(fpt.keyAt(i));
					}					
					if (!tfeed.isEmpty()) 
						sr.dropTransitions(tfeed , true,"Remove Feeders");
				}
				
				try {
					if (ReachabilitySolver.applyReductions(sr, ReductionType.SAFETY, solverPath, false,iterations==0)) {
						iter++;					
					} else if (iterations>0 && iter==0  /*&& doneSums*/ && ReachabilitySolver.applyReductions(sr, ReductionType.SAFETY, solverPath, true,false)) {
						iter++;
					}
				} catch (GlobalPropertySolvedException e) {
					System.out.println("Unexpected GlobalPropertySolved exception occurred while checking bounds.");
					e.printStackTrace();
				}
				int reds= sr.ruleRedundantCompositionsBounds();
				if (reds > 0) {					
					iter++;
				}
				
				// FlowPrinter.drawNet(sr, "Final Model", 1000);
				spn.readFrom(sr);
				spn.testInInitial();
				spn.removeConstantPlaces();
				spn.simplifyLogic();			
				checkInInitial(spn, doneProps);
				
				if (spn.getProperties().isEmpty()) {
					return;
				}
								
				if (spn.getProperties().removeIf(p -> doneProps.containsKey(p.getName())))
					iter++;
							
				
				iterations++;
			} while ( (iterations<=1 || iter > 0 || !lastMaxSeen.equals(maxSeen)) && ! spn.getProperties().isEmpty());
						
		}

	public static void approximateStructuralBoundsUsingInvariants(ISparsePetriNet sr, Set<SparseIntArray> invar, List<Expression> tocheck,
			List<Integer> maxStruct) {
		{
			// try to set a max bound on variables using invariants
			
			// structural bounds determined on all variables/places
			int [] limits = new int [sr.getPnames().size()];
			// -1 = no structural bound found
			Arrays.fill(limits, -1);
			// split invariants in positive semi-flows and generalized flows (with neg coeffs) 
			List<SparseIntArray> posinv = new ArrayList<SparseIntArray>();
			List<SparseIntArray> geninv = new ArrayList<SparseIntArray>();
			
			// do the split
			for (SparseIntArray invariant : invar) {
				boolean hasNeg = false;
				for (int i=0; i < invariant.size() ; i++) {
					if (invariant.valueAt(i) < 0) {
						hasNeg = true;
						break;
					}
				}			
				if (! hasNeg) {
					posinv.add(invariant);
				} else {
					geninv.add(invariant);
				}
			}
			
			
			// start with positive semi flows
			for (SparseIntArray invariant : posinv) {

				// ok so we have an invariant : a0 * p0 + a1 * p1 ...= K
				// at best all variables are zero except one, in that case  pi = K / ai (rounded down)
				// e.g.  2*p0 + p1 = 4 =>  p0 <= 2 ; p1 <= 4
				// e.g.  2*p0 + p1 = 5 =>  p0 <= 2 ; p1 <= 5
				
				// compute K
				int sum = 0;
				for (int i = 0 ; i < invariant.size() ; i++) {
					int v = invariant.keyAt(i);
					int val = invariant.valueAt(i);
					sum += sr.getMarks().get(v) * val;
				}
				// iterate elements and set bound
				for (int i = 0 ; i < invariant.size() ; i++) {
					int v = invariant.keyAt(i);
					int val = invariant.valueAt(i);
					int bound = sum / val ;
					limits[v] = Math.min(limits[v]==-1?Integer.MAX_VALUE:limits[v], bound);
				}
			}
			
			if (Application.DEBUG >= 2) {
				System.out.println("(Positive flows) Managed to find structural bounds :" + Arrays.toString(limits));						
				System.out.println("Current structural bounds on expressions : " + maxStruct);
			}
			
			// now use these bounds to have a (pessimistic) opinion on generalized flows
			for (int repeat=0; repeat < 2 ; repeat++) {
				for (SparseIntArray invariant : geninv) {

					// ok so we have an invariant : a0 * p0 - a1 * p1 + ...= K
					// Now if all negative coefficient elements are actually bounded, we can conservatively use this bound
					// e.g.  p0 - p1 = K  knowing that p1 <= K1 =>  p0 - K1 <= K => p0 <= K + K1
					// in other words, move all negative elements to the right, update K pessimistically
					// Similarly, if the positive elements are all bounded we can deduce
					// p0 - p1 = K knowing that p0 <= K0 => p0 - K = p1 => p1 <= K0 - K

					// true if 
					boolean boundPosExists = true;
					boolean boundNegExists = true;

					// compute K and whether we have bounds on one term 
					int tsum = 0;
					int pbound = 0;
					int nbound = 0;
					for (int i = 0 ; i < invariant.size() ; i++) {
						int v = invariant.keyAt(i);
						int val = invariant.valueAt(i);
						tsum += sr.getMarks().get(v) * val;
						if (val < 0 && limits[v]==-1) {
							boundNegExists = false;
						} else if (val > 0 && limits[v]==-1) {
							boundPosExists = false;
						} else if (val < 0) {
							nbound -= val * limits[v];
						} else if (val > 0) {
							pbound += val * limits[v];
						}
					}

					// apply any bounds found
					if (boundPosExists || boundNegExists) {
						// p0 - p1 = K knowing that p0 <= K0 => p0 - K = p1 => p1 <= K0 - K
						int psum = pbound - tsum;
						// e.g.  p0 - p1 = K  knowing that p1 <= K1 =>  p0 - K1 <= K => p0 <= K + K1
						int nsum = nbound + tsum ; 

						// iterate elements and set bound
						for (int i = 0 ; i < invariant.size() ; i++) {
							int v = invariant.keyAt(i);
							int val = invariant.valueAt(i);
							if (val < 0 && boundPosExists) {
								int bound = -psum / val ;
								limits[v] = Math.min(limits[v]==-1?Integer.MAX_VALUE:limits[v], bound);
							} else if (val > 0 && boundNegExists){
								int bound = nsum / val ;
								limits[v] = Math.min(limits[v]==-1?Integer.MAX_VALUE:limits[v], bound);																		
							}
						}							
					}						
				}
				if (Application.DEBUG >= 2) {
					System.out.println("Repeat="+repeat+" : Managed to find structural bounds :" + Arrays.toString(limits));						
					System.out.println("Current structural bounds on expressions : " + maxStruct );
				}
				for (int propid = 0 ; propid < tocheck.size() ; propid++) {
					Expression tc = tocheck.get(propid);
					if (tc.getOp() == Op.PLACEREF) {
						if (limits[tc.getValue()]!=-1) {
							maxStruct.set(propid, Math.min(maxStruct.get(propid)==-1?Integer.MAX_VALUE:maxStruct.get(propid), limits[tc.getValue()]));
						}
					} else if (tc.getOp() == Op.CONST){
						// trivial !
						maxStruct.set(propid, tc.getValue());							
					} else if (tc.getOp() == Op.ADD){
						int bound = 0;
						boolean hasBound = true;
						// check all children have a bound
						for (int ci=0, cie =tc.nbChildren() ; ci < cie ; ci++) {
							Expression child = tc.childAt(ci);
							if (child.getOp() == Op.PLACEREF) {
								if (limits[child.getValue()] == -1) {
									hasBound = false;
									break;
								} else {
									bound += limits[child.getValue()];
								}
							} else if (child.getOp() == Op.CONST) {
								bound += child.getValue();
							} else {
								System.out.println("Strange operator met in bounds query.");
							}
						}
						if (hasBound) {
							maxStruct.set(propid, Math.min(maxStruct.get(propid)==-1?Integer.MAX_VALUE:maxStruct.get(propid), bound));								
						}
					}						
				}
				if (Application.DEBUG >= 1) {
					System.out.println("Current structural bounds on expressions : " + maxStruct);				
				}
			}
		}
	}

	public static void computeToCheck(SparsePetriNet spn, List<Integer> tocheckIndexes, List<Expression> tocheck, DoneProperties doneProps) {
		int j=0;
		for (fr.lip6.move.gal.structural.Property p : spn.getProperties()) {
			if (! doneProps.containsKey(p.getName()) && p.getType() == PropertyType.BOUNDS) {
				tocheck.add(p.getBody());
				tocheckIndexes.add(j);
			}
			j++;
		}			
	}	
	
	static int treatVerdicts(SparsePetriNet sparsePetriNet, DoneProperties doneProps, List<Expression> tocheck,
			List<Integer> tocheckIndexes, List<SparseIntArray> paths, List<Integer> maxSeen, List<Integer> maxStruct, List<SparseIntArray> orders) {
		return treatVerdicts(sparsePetriNet, doneProps, tocheck, tocheckIndexes, paths, "",maxSeen,maxStruct,orders);
	}

	static int treatVerdicts(SparsePetriNet spn, DoneProperties doneProps, List<Expression> tocheck,
			List<Integer> tocheckIndexes, List<SparseIntArray> paths, String technique, List<Integer> maxSeen, List<Integer> maxStruct, List<SparseIntArray> orders) {
		int seen = 0; 
		for (int v = paths.size()-1 ; v >= 0 ; v--) {
			if (maxSeen.get(v).equals(maxStruct.get(v))) {
				fr.lip6.move.gal.structural.Property prop = spn.getProperties().get(v);
				doneProps.put(prop.getName(),maxSeen.get(v),"TOPOLOGICAL SAT_SMT RANDOM_WALK");
				tocheck.remove(v);
				spn.getProperties().remove(v);
				maxSeen.remove(v);
				maxStruct.remove(v);
				paths.remove(v);
				orders.remove(v);
				seen++;
			}
		}
		return seen;

	}

	private static int interpretVerdict(List<Expression> tocheck, SparsePetriNet spn, DoneProperties doneProps,
			int[] verdicts, String walkType, List<Integer> maxSeen, List<Integer> maxStruct) {
		int seen = 0; 
		for (int v = verdicts.length-1 ; v >= 0 ; v--) {
			int cur = verdicts[v];
			maxSeen.set(v,Math.max(maxSeen.get(v), cur));
			if (maxSeen.get(v).equals(maxStruct.get(v))) {
				fr.lip6.move.gal.structural.Property prop = spn.getProperties().get(v);
				doneProps.put(prop.getName(),maxSeen.get(v),"TOPOLOGICAL "+walkType+"_WALK");
				tocheck.remove(v);
				spn.getProperties().remove(v);
				maxSeen.remove(v);
				maxStruct.remove(v);
				seen++;
			}
		}
		return seen;
	}
	
	
	static int randomCheckReachability(RandomExplorer re, List<Expression> tocheck, SparsePetriNet spn,
			DoneProperties doneProps, int steps, List<Integer> maxSeen, List<Integer> maxStruct) {
		int[] verdicts = re.runRandomReachabilityDetection(steps,tocheck,30,-1,true);
		
		int seen = interpretVerdict(tocheck, spn, doneProps, verdicts,"RANDOM",maxSeen,maxStruct);
		for (int i=0 ; i < tocheck.size() ; i++) {			
			verdicts = re.runRandomReachabilityDetection(steps,tocheck,5,i,true);
			
			seen += interpretVerdict(tocheck, spn, doneProps, verdicts,"BESTFIRST",maxSeen,maxStruct);			
		}

		
		
//		if (seen == 0) {
//			RandomExplorer.WasExhaustive wex = new RandomExplorer.WasExhaustive();
//			verdicts = re.runProbabilisticReachabilityDetection(steps*1000,tocheck,30,-1,false,wex);
//			seen += interpretVerdict(tocheck, spn, doneProps, verdicts,"PROBABILISTIC");
//			if (wex.wasExhaustive) {
//				wex = new RandomExplorer.WasExhaustive();
//				verdicts = re.runProbabilisticReachabilityDetection(steps*1000,tocheck,30,-1,true,wex);				
//				seen += interpretVerdict(tocheck, spn, doneProps, verdicts,"EXHAUSTIVE",wex.wasExhaustive);
//			}
//		}
		return seen;
	}
	
}
