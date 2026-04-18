package fr.lip6.move.gal.application.solver.ltl.knowledge;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import android.util.SparseIntArray;
import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.ltl.tgba.TGBAEdge;
import fr.lip6.move.gal.application.mcc.MccTranslator;
import fr.lip6.move.gal.application.runner.spot.SpotRunner;
import fr.lip6.move.gal.application.solver.ReachabilitySolver;
import fr.lip6.move.gal.mcc.properties.ConcurrentHashDoneProperties;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.DeadlockFound;
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

public class KnowledgeFacts {
	public static final int DEBUG = 0;

	public static void addConvergenceKnowledge(List<Expression> knowledge, ISparsePetriNet spn, TGBA tgba) {
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

	public static void addInitialStateKnowledge(List<Expression> knowledge, ISparsePetriNet spn, TGBA tgba) {
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

	public static void addInvarianceKnowledge(List<Expression> knowledge, List<Expression> falseKnowledge, SparsePetriNet spn, TGBA tgba) {
		
		SparsePetriNet spnred = new SparsePetriNet(spn);
		spnred.getProperties().clear();
		
		
		Set<Expression> apSet = new HashSet<>();
		{
			Set<Expression> seen = new HashSet<>();
			for (int s=0,se=tgba.nbStates() ; s < se ; s++) {
				for (TGBAEdge e : tgba.getEdges().get(s)) {
					addCondition(e.getCondition(), seen, apSet);
				}
			}
		}
		// unify
		List<Expression> apForm = new ArrayList<>(apSet);
		
		if (DEBUG >=1) {
			System.out.println("Running invariance knowledge with AP :" + tgba.getAPs());
		}
		
		// build a list of invariants to test with SMT/random
		// for each of them test value in initial state
		SparseIntArray istate = new SparseIntArray(spnred.getMarks());
		{
			
			for (int index = 0; index < apForm.size() ; index++) {
	   				Expression cmp = apForm.get(index);
					int val = cmp.eval(istate);
					if (val == 0) {
						// initially false, reverse the expression so it is initially true
						cmp = Simplifier.pushNegation(Expression.not(cmp));
						
						// update
						apForm.set(index, cmp);						
					}
					
					// our new formula
					String pname = "apf" + index;
					// cleanup any AP refs in actual predicate
					cmp = AtomicPropManager.rewriteWithoutAP(cmp);
					// assert that the AP formula is invariant
					Property p = new Property(Expression.nop(Op.AG, cmp), PropertyType.INVARIANT, pname);
					spnred.getProperties().add(p);						
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
			if (res) {
				// cool we've proven an invariant, we can substitute							
				if (DEBUG >= 1) System.out.println("Successfully proved AP formula is invariant; concluding " + pname +"="+ c + " is always true.");						
	
				knowledge.add(Expression.nop(Op.G, c));
				nsolved ++;
			} else {
				// so we have proved that EF !p 
				Expression FnotP = Expression.nop(Op.F, Expression.not(c));						
				falseKnowledge.add(FnotP);
			}
		}
		if (nsolved > 0) {
			
			if (DEBUG >=1) {
				System.out.println("Finished invariance knowledge with AP :" + tgba.getAPs());
			}
			
			System.out.println("Found "+nsolved+" invariant AP formulas.");
		}		
		
	}

	public static List<Expression> computeEGknowledge(SparsePetriNet spn, TGBA tgba) {
			List<Expression> falseKnowledge = new ArrayList<>();
			SparseIntArray init = new SparseIntArray(spn.getMarks());
			List<Expression> infS = tgba.getInfStutter();
			if (infS == null) {
				SpotRunner sr = new SpotRunner(10);
				sr.computeInfStutter(tgba);			
			}
			Expression ap = tgba.getInfStutter().get(tgba.getInitial());
			boolean isInitiallyTrue = ap.eval(init)!=0;
			if (isInitiallyTrue) {
				if (ap.getOp() == Op.OR) {
					for (int i=0;i<ap.nbChildren();i++) {
						Expression ap2 = ap.childAt(i);
						// make sure this term is initially true
						if (ap2.eval(init)!=0) {
							if (DeadlockTester.testEGap(ap2,spn, 15)) {
								System.out.println("Proved EG "+ap2 + " asserting that LTL formula F !"+ap2+" is false.");
								falseKnowledge.add(Expression.nop(Op.F,Expression.not(ap2)));
							} else {
								System.out.println("Could not prove EG "+ap2);
							}
						}
					}
				} else {
					if (DeadlockTester.testEGap(ap,spn, 15)) {
						System.out.println("Proved EG "+ap +" asserting that LTL formula F !"+ap+" is false.");
						falseKnowledge.add(Expression.nop(Op.F, Expression.not(ap)));
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

	public static void addNextStateKnowledge(List<Expression> knowledge, List<Expression> falseKnowledge, SparsePetriNet spn, TGBA tgba) {
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
						Expression condition = edgeX.getCondition();
						addCondition(condition, seen, condX); 
						for (TGBAEdge edgeXX : tgba.getEdges().get(edgeX.getDest())) {
							Expression conditionX = edgeXX.getCondition();
							addCondition(conditionX, seenX, condXX);
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

	public static boolean testAFDead(ISparsePetriNet spn) {
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

	public static void addCondition(Expression condition, Set<Expression> seen, Set<Expression> condX) {
		if (condition.getOp() != Op.BOOLCONST) {
			if (seen.add(condition) && seen.add(Expression.not(condition))) {
				// grab formulas labeling edges out of this node
				condX.add(condition);
			}
			// now also extract pure AP
			Set<Expression> aps = new HashSet<>();
			extractAP(condition,aps);
			for (Expression ap : aps) {
				if (seen.add(ap) && seen.add(Expression.not(ap))) {
					condX.add(ap);
				}
			}
		}
	}

	
	private static void extractAP(Expression condition, Set<Expression> aps) {
		if (condition == null) {
			return;
		} else if (condition.getOp() == Op.APREF) {
			aps.add(condition);
		} else {
			for (int i=0, ie = condition.nbChildren() ; i < ie ; i++) {
				extractAP(condition.childAt(i), aps);
			}
		}
	}

}
