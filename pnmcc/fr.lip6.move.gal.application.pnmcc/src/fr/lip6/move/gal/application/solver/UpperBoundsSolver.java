package fr.lip6.move.gal.application.solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;


import android.util.SparseIntArray;
import fr.lip6.move.gal.application.Application;
import fr.lip6.move.gal.application.mcc.MccTranslator;
import fr.lip6.move.gal.mcc.properties.ConcurrentHashDoneProperties;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.CoverWalker;
import fr.lip6.move.gal.structural.FlowPrinter;
import fr.lip6.move.gal.structural.GlobalPropertySolvedException;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.InvariantCalculator;
import fr.lip6.move.gal.structural.PetriNet;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.RandomExplorer;
import fr.lip6.move.gal.structural.RandomExplorer.WalkStats;
import fr.lip6.move.gal.structural.RandomExplorer.WalkType;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.StructuralReduction.ReductionType;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.smt.DeadlockTester;
import fr.lip6.move.gal.util.IntMatrixCol;

public class UpperBoundsSolver {

	private static final int omega  = Integer.MAX_VALUE;
	
	public static void checkInInitial(SparsePetriNet spn, DoneProperties doneProps) {
		for (fr.lip6.move.gal.structural.Property prop : new ArrayList<>(spn.getProperties())) {
			if (prop.getBody().getOp() == Op.CONST) {
				doneProps.put(prop.getName(),prop.getBody().getValue(),"TOPOLOGICAL INITIAL_STATE");				
				spn.getProperties().remove(prop);
			}
		}
	}
	
	public static List<Integer> treatSkeleton(MccTranslator reader, DoneProperties doneProps) {
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
			invar = InvariantCalculator.computePInvariants(sumMatrix);
		}
		approximateStructuralBoundsUsingInvariants(sr, invar, tocheck, maxStruct);
		printBounds("after Invariants on skeleton", maxSeen, maxStruct);
		
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
		
			List<Integer> repr = new ArrayList<>();
			List<SparseIntArray> paths = DeadlockTester.findStructuralMaxWithSMT(tocheck, maxSeen, maxStruct, sr, repr, new ArrayList<>(), 5, true);
			
			//interpretVerdict(tocheck, spn, doneProps, new int[tocheck.size()], solverPath, maxSeen, maxStruct);
			printBounds("after SMT on skeleton", maxSeen, maxStruct);
			
			checkStatus(spn, tocheck, maxStruct, maxSeen, doneProps, "TOPOLOGICAL SAT_SMT CPN_APPROX INITIAL_STATE");
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
	

	public static List<Integer> applyReductions(MccTranslator reader, DoneProperties doneProps, List<Integer> initMaxStruct) {
			int iter;
			int iterations =0;

			SparsePetriNet spn = reader.getSPN();
			//  need to protect some variables
			List<Integer> tocheckIndexes = new ArrayList<>();
			spn.testAliasing(doneProps);
			List<Expression> tocheck = new ArrayList<>(spn.getProperties().size());
			computeToCheck(spn, tocheckIndexes, tocheck, doneProps);

			List<Integer> maxStruct = new ArrayList<>(tocheckIndexes.size());
			List<Integer> maxSeen = new ArrayList<>(tocheckIndexes.size());
			{
				SparseIntArray m0 = new SparseIntArray(spn.getMarks());
				if (spn.isSafe()) {
					SparseIntArray ones = new SparseIntArray(spn.getMarks().size());
					for (int i=0,ie=spn.getPlaceCount();i<ie; i++) {
						ones.append(i, 1);
					}
					for (Expression tc:tocheck) {
						maxSeen.add(tc.eval(m0));
						maxStruct.add(tc.eval(ones));
					}
					printBounds("Initiallly, because the net is safe", maxSeen, maxStruct);
				} else {
					for (Expression tc:tocheck) {
						maxSeen.add(tc.eval(m0));
						maxStruct.add(-1);
					}
					printBounds("Initially", maxSeen, maxStruct);
				}
			}
			if (initMaxStruct != null) {
				for (int i=0; i < maxStruct.size() ; i++) {
					int init=initMaxStruct.get(i); 
					if (init != -1 && (maxStruct.get(i) <0 || maxStruct.get(i) > init)) {
						maxStruct.set(i, init);
					}
				}
				printBounds("Adding known information on max bounds.", maxSeen, maxStruct);
			}
			
			checkStatus(spn, tocheck, maxStruct, maxSeen, doneProps, "TOPOLOGICAL INITIAL_STATE");
			
			List<Integer> lastMaxSeen = null;
			
			printBounds("Before main loop", maxSeen, maxStruct);
			
			boolean first = true;
			do {
				iter =0;
				spn.testAliasing(doneProps);
				if (! first)  {
					computeToCheck(spn, tocheckIndexes, tocheck, doneProps);			
				} else {
					first = false;
				}
				StructuralReduction sr = new StructuralReduction(spn);
				
				// the invariants themselves
				Set<SparseIntArray> invar ;
				{
					// effect matrix
					List<Integer> repr = new ArrayList<>();
					IntMatrixCol sumMatrix = InvariantCalculator.computeReducedFlow(spn, repr);
					invar = InvariantCalculator.computePInvariants(sumMatrix);
				}
				approximateStructuralBoundsUsingInvariants(sr, invar, tocheck, maxStruct);

				printBounds("after invariants", maxSeen, maxStruct);
				//	FlowPrinter.drawNet(sr, "After Invariants");
				checkStatus(spn, tocheck, maxStruct, maxSeen, doneProps, "TOPOLOGICAL INITIAL_STATE");
				
				lastMaxSeen = new ArrayList<>(maxSeen);
				RandomExplorer re = new RandomExplorer(sr);
				int steps = 1000000; // 1 million
				if (iterations == 0 && iter==0) {
					steps = 10000; // be more moderate on first run : 100k
				}
				if (randomCheckReachability(re, tocheck, spn, doneProps,steps,maxSeen,maxStruct) >0)
					iter++;
				
				checkStatus(spn, tocheck, maxStruct, maxSeen, doneProps, "TOPOLOGICAL RANDOM_WALK");
				printBounds("after WALK",maxSeen,maxStruct);
				
				
				Optional<Boolean> isBounded = Optional.empty();
				
				
				if (spn.getProperties().isEmpty())
					break;
				
					List<Integer> repr = new ArrayList<>();
					List<SparseIntArray> orders=new ArrayList<>();
					List<SparseIntArray> paths = DeadlockTester.findStructuralMaxWithSMT(tocheck, maxSeen, maxStruct, sr, repr, orders, iterations==0 ? 5:45, true);
					
					// interpretVerdict(tocheck, spn, doneProps, new int[tocheck.size()], "PARIKH", maxSeen, maxStruct);
					printBounds("after SMT", maxSeen, maxStruct);
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
					
					printBounds("After Parikh guided walk", maxSeen, maxStruct);
				
				if (spn.getProperties().removeIf(p -> doneProps.containsKey(p.getName())))
					iter++;
				if (spn.getProperties().isEmpty())
					break;
				
				
				BitSet support = spn.computeSupport();
				System.out.println("Support contains "+support.cardinality() + " out of " + sr.getPnames().size() + " places. Attempting structural reductions.");
				
				sr.setProtected(support);

//				boolean foundDead = false;
//				if (iterations >= 1 && !foundDead) {
//					List<Integer> deadPlaces = findDeadPlaces(reader);
//
//					if (!deadPlaces.isEmpty()) {
//						sr.dropSurroundingTransitions(deadPlaces, "Dead places");
//						iter++;
//						foundDead=true;
//					}
//				}
//				if (iterations >= 1 && !foundDead) {
//					List<Integer> deadTransitions = findDeadTransitions(reader);
//
//					if (!deadTransitions.isEmpty()) {
//						sr.dropTransitions(deadTransitions, true, "Dead transitions detected with 'AG(!fireable(t))'");						
//						iter++;
//					}
//				}
				
				
				// a single place, that is one bounded : kill it's consumers
				if (support.cardinality()==1 && maxStruct.get(0)==1) {
					int pid = support.nextSetBit(0);
					List<Integer> tfeed = new ArrayList<>();
					SparseIntArray fpt = sr.getFlowPT().transpose().getColumn(pid);
					for (int i = fpt.size() - 1 ; i >= 0 ; i--) {
						tfeed.add(fpt.keyAt(i));
					}					
					if (!tfeed.isEmpty()) 
						sr.dropTransitions(tfeed , true,"Removing consumers from one bounded place "+sr.getPnames().get(pid));
				}
				
				try {
					if (ReachabilitySolver.applyReductions(sr, ReductionType.REACHABILITY, false, iterations==0)) {
						iter++;					
					} else if (iterations>0 && iter==0  /*&& doneSums*/ && ReachabilitySolver.applyReductions(sr, ReductionType.REACHABILITY, true, false)) {
						iter++;
					}
				} catch (GlobalPropertySolvedException e) {
					System.out.println("Unexpected GlobalPropertySolved exception occurred while checking bounds.");
					e.printStackTrace();
				}
//				int reds= sr.ruleRedundantCompositionsBounds();
//				if (reds > 0) {					
//					iter++;
//				}
				
				// FlowPrinter.drawNet(sr, "Final Model", 1000);
				spn.readFrom(sr);
				spn.testInInitial();
				spn.removeConstantPlaces();
				spn.simplifyLogic();			
				checkInInitial(spn, doneProps);
				
				if (spn.getProperties().isEmpty()) {
					return Collections.emptyList();
				}
								
				if (spn.getProperties().removeIf(p -> doneProps.containsKey(p.getName())))
					iter++;
				
				
				
				if (!isBounded.isPresent()) {
					// check if we still have inf upper bounds
					int hasInf = -1;
					for (int index=0; index < maxStruct.size() ; index++) {
						if (maxStruct.get(index) == -1) {
							hasInf = index;
							break;
						}
					}
					
					// test if we are bounded using SMT
					if (hasInf >= 0) {
						// effect matrix
						List<Integer> repr2 = new ArrayList<>();
						IntMatrixCol sumMatrix = InvariantCalculator.computeReducedFlow(spn, repr2);
						
						// debatable : use the index of the structurally unbounded place ? 
//						SparseIntArray inv = DeadlockTester.findPositiveTsemiflow(sumMatrix, hasInf);
						SparseIntArray inv = DeadlockTester.findPositiveTsemiflow(sumMatrix, -1);
						if (inv.size()==0) {
							isBounded = Optional.of(true);
						} else {
							computeToCheck(spn, tocheckIndexes, tocheck, doneProps);
							CoverWalker cw = new CoverWalker(spn);
							SparseIntArray maxState = new SparseIntArray();
							int[] verdicts = cw.runRandomReachabilityDetection(10000, tocheck, 3000, -1, true,maxState);
							
							iter += interpretVerdict(tocheck, spn, doneProps, verdicts,"COVER",maxSeen,maxStruct);
							printBounds("after cover walk", maxSeen, maxStruct);

							// Experimental code : try to remove sources of infinity.
							if (false && ! spn.getProperties().isEmpty()) {
								System.out.println("Resetting from maxState :" + maxState);
								SparsePetriNet spnMax = new SparsePetriNet(spn);
								
								List<Integer> marks= new ArrayList<>(spnMax.getPlaceCount());
								for (int i=0,ie=spnMax.getPlaceCount();i<ie;i++) {
									marks.add(0);
								}
								List<Integer> omegas = new ArrayList<>();
								for (int i=0; i < maxState.size() ; i++) {
									if (maxState.valueAt(i) != Integer.MAX_VALUE) {
										marks.set(i,maxState.valueAt(i));
									} else {
										omegas.add(maxState.keyAt(i));
									}
								}
								//spnMax.setInitial(marks);
								
								System.out.println("Removing omega places with index :" + omegas);
								
								StructuralReduction srMax = new StructuralReduction(spnMax);
								srMax.dropPlaces(omegas, false, "CoverMax");
								spnMax.readFrom(srMax);
								
								spn = spnMax;
								
								{
									List<Integer> repr3 = new ArrayList<>();
									IntMatrixCol sumMatrix2 = InvariantCalculator.computeReducedFlow(spn, repr3);
									SparseIntArray inv2 = DeadlockTester.findPositiveTsemiflow(sumMatrix2,-1);
									if (inv2.size()==0) {
										isBounded = Optional.of(true);
										System.out.println("After discarding omega places, the net is now bounded.");
									} else {
										System.out.println("Even after discarding omega places, the net is still structurally unbounded.");
									}
								}
							}
						}
					}
					
				}
				
				iterations++;
			} while ( (iterations<=1 || iter > 0 || !lastMaxSeen.equals(maxSeen)) && ! spn.getProperties().isEmpty());
			
			
			reader.setSpn(spn,false);
			
			testWithReachability(reader,maxSeen,maxStruct,doneProps);
			
			return maxStruct;
		}
	
	
	private static List<Integer> findDeadTransitions (MccTranslator ori) {
		MccTranslator subproblem = ori.copy();
		SparsePetriNet spn = subproblem.getSPN();		
		spn.getProperties().clear();
		
		SparseIntArray initial = new SparseIntArray(spn.getMarks());
		for (int tid=0; tid < spn.getTransitionCount() ; tid++) {
			SparseIntArray pt = spn.getFlowPT().getColumn(tid);
			if (! SparseIntArray.greaterOrEqual(initial, pt)) {
				Property tisdead = new Property(
						Expression.nop(Op.AG, 
								Expression.nop(Op.NOT, Expression.nop(Op.ENABLED,Expression.trans(tid)))), PropertyType.INVARIANT, "TDEAD"+tid);
				spn.getProperties().add(tisdead);
			}
		}
		subproblem.setSpn(spn, true);
		subproblem.simplifySPN(true, true);
		
		long time = System.currentTimeMillis();
		System.out.println("Running "+spn.getProperties().size()+" sub problems to find dead transitions.");
		DoneProperties localDone = new ConcurrentHashDoneProperties();
		try {
			ReachabilitySolver.applyReductions(subproblem,localDone,100);
		} catch (GlobalPropertySolvedException e) {
			e.printStackTrace();
		}
		List<Integer> deadTrans = new ArrayList<Integer>();
		for (Entry<String, Boolean> ent : localDone.entrySet()) {
			if (ent.getValue()) {
				int tid = Integer.parseInt(ent.getKey().substring(5));
				deadTrans.add(tid);
			}
		}
		System.out.println("Search for dead transitions found "+deadTrans.size()+ " dead transitions in " + (System.currentTimeMillis()-time) + "ms");
		return deadTrans;
	}
	

	private static List<Integer> findDeadPlaces (MccTranslator ori) {
		MccTranslator subproblem = ori.copy();
		SparsePetriNet spn = subproblem.getSPN();		
		spn.getProperties().clear();
		
		for (int pid=0; pid < spn.getPlaceCount() ; pid++) {
			if (spn.getMarks().get(pid) == 0) {
				Property pisdead = new Property(
						Expression.nop(Op.AG, 
								Expression.nop(Op.EQ, Expression.var(pid), Expression.constant(0))), PropertyType.INVARIANT, "PDEAD"+pid);
				spn.getProperties().add(pisdead);
			}
		}
		
		long time = System.currentTimeMillis();
		System.out.println("Running "+spn.getProperties().size()+" sub problems to find dead places.");
		DoneProperties localDone = new ConcurrentHashDoneProperties();
		try {
			ReachabilitySolver.applyReductions(subproblem,localDone,100);
		} catch (GlobalPropertySolvedException e) {
			e.printStackTrace();
		}
		List<Integer> deadPlaces = new ArrayList<Integer>();
		for (Entry<String, Boolean> ent : localDone.entrySet()) {
			if (ent.getValue()) {
				int pid = Integer.parseInt(ent.getKey().substring(5));
				deadPlaces.add(pid);
			}
		}
		System.out.println("Search for dead places found "+deadPlaces.size()+ " dead places in " + (System.currentTimeMillis()-time) + "ms");
		return deadPlaces;
	}
	
	private static void testWithReachability(MccTranslator ori, List<Integer> maxSeen, List<Integer> maxStruct,
			DoneProperties doneProps) {

		SparsePetriNet spnori = ori.getSPN();
		MccTranslator subproblem = ori.copy();
		SparsePetriNet spn = subproblem.getSPN();		
		spn.getProperties().clear();
		
		
		IntMatrixCol tflowTP = subproblem.getSPN().getFlowTP().transpose();
		Map<Integer,String> propId = new HashMap<>();
		for (int id=0; id < spnori.getProperties().size() ; id++) {
			Property prop = spnori.getProperties().get(id);
			propId.put(id, prop.getName());
			if (maxStruct.get(id) > 0) {
				Property reachMax = new Property(
						Expression.nop(Op.EF, 
								Expression.nop(Op.EQ, prop.getBody(), Expression.constant(maxStruct.get(id)))), PropertyType.INVARIANT, "MAX"+id );
				spn.getProperties().add(reachMax);
			}
			Property seenIsBound = new Property(
					Expression.nop(Op.AG, 
							Expression.nop(Op.LEQ, prop.getBody(), Expression.constant(maxSeen.get(id)))), PropertyType.INVARIANT, "MIN"+id );
			spn.getProperties().add(seenIsBound);
		}
		subproblem.simplifySPN(true, true);
		DoneProperties localDone = new ConcurrentHashDoneProperties();
			
		try {
			ReachabilitySolver.applyReductions(subproblem,localDone,100);
		} catch (GlobalPropertySolvedException e) {
			e.printStackTrace();
		}
		int seen = 0;
		for (int id = spnori.getProperties().size() ; id >= 0 ; id--) {
			boolean done = false;
			Boolean b = localDone.getValue("MAX"+id);
			String pname = propId.get(id);
			if (b!=null && b) {
				// We *can* reach the structural max.
				doneProps.put(pname, maxStruct.get(id), "REACHABILITY_MAX");
				done = true;
			} else {
				b = localDone.getValue("MIN"+id);

				if (b!=null && b) {
				// We *cannot exceed* the seen value.
				doneProps.put(pname, maxSeen.get(id), "REACHABILITY_MIN");
				done = true;
				}
			}
			if (done) {
				ori.getSPN().getProperties().remove(id);
				maxSeen.remove(id);
				maxStruct.remove(id);
				seen++;
			}
		}
		printBounds("After reachability solving "+seen+" queries.", maxSeen, maxStruct);
	}

	private static void printBounds(String rule, List<Integer> maxSeen, List<Integer> maxStruct) {
		StringBuilder sb = new StringBuilder();
		sb.append("Max Seen:");		
		printOmegas(maxSeen, sb);
		sb.append(" Max Struct:");		
		printOmegas(maxStruct, sb);
		
		System.out.println("Current structural bounds on expressions ("+ rule + ") : " + sb.toString());
		for (int i=0; i< maxSeen.size() ; i++) {
			if (maxStruct.get(i) >= 0 && maxSeen.get(i) > maxStruct.get(i)) {
				System.out.println("Inconsistency detected : max struct is less than max seen for bound index "+i);
			}
		}
	}

	public static void printOmegas(List<Integer> bounds, StringBuilder sb) {
		sb.append("[");
		boolean first = true;
		for (Integer i : bounds) {
			if (!first)
				sb.append(", ");
			else
				first=false;
			sb.append(i==-1||i==omega ? "+inf" : i.toString());
		}
		sb.append("]");
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
		tocheckIndexes.clear();
		tocheck.clear();
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
			Integer value = maxSeen.get(v);
			maxSeen.set(v,Math.max(value, cur));
			
			if (value == Integer.MAX_VALUE ||  value.equals(maxStruct.get(v))) {
				fr.lip6.move.gal.structural.Property prop = spn.getProperties().get(v);
				doneProps.put(prop.getName(),value,"TOPOLOGICAL "+walkType+"_WALK");
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
		WalkStats ws = new WalkStats(WalkType.RANDOM);
		int[] verdicts = re.runRandomReachabilityDetection(steps,tocheck,30,-1,true, ws);
		System.out.println(ws);
		
		ws = new WalkStats(WalkType.BEST_FIRST);
		int seen = interpretVerdict(tocheck, spn, doneProps, verdicts,"RANDOM",maxSeen,maxStruct);
		for (int i=0 ; i < tocheck.size() ; i++) {			
			verdicts = re.runRandomReachabilityDetection(steps,tocheck,5,i,true, ws);
			
			seen += interpretVerdict(tocheck, spn, doneProps, verdicts,"BESTFIRST",maxSeen,maxStruct);			
		}
		System.out.println(ws);
		
		
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
