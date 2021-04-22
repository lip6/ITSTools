package fr.lip6.ltl.tgba;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.WalkUtils;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;

public class RandomProductWalker {

	private static final int DEBUG = 0;
	private List<WalkUtils> wus = new ArrayList<>();
	TGBA tgba;
	int initialTGBA ;
	List<List<Expression>> apsPerState = new ArrayList<>();
	private List<Expression> image;
	boolean hasMulti = false;
	
	public RandomProductWalker(ISparsePetriNet spn, TGBA tgba) {
		WalkUtils wu = new WalkUtils(spn);
		List<Expression> atoms = tgba.getAPs().stream().map(ap -> ap.getExpression()).collect(Collectors.toList());
		for (int i=0; i < tgba.nbStates() ; i++) {
			apsPerState.add(atoms);
			wus.add(wu);
		}
		initialTGBA = tgba.getInitial();
		this.tgba = tgba;
	}
	public RandomProductWalker(ISparsePetriNet spn, StructuralReduction spnred, TGBA tgba, List<Expression> apred) {
		WalkUtils wu = new WalkUtils(spn);
		WalkUtils wured = new WalkUtils(spnred);
		
		List<Expression> atoms = tgba.getAPs().stream().map(ap -> ap.getExpression()).collect(Collectors.toList());
		for (int i=0; i < tgba.nbStates() ; i++) {
			if (tgba.getStutterMarkers()[i]) {
				apsPerState.add(apred);
				wus.add(wured);				
			} else {
				apsPerState.add(atoms);
				wus.add(wu);
			}
		}
		initialTGBA = tgba.getInitial();
		image = spnred.getImage();
		this.tgba = tgba;
		hasMulti = true;
	}
	
	private void setAPinterpretation (int q) {
		if (! hasMulti) 
			return;
		List<Expression> atoms = apsPerState.get(q);
		for (int i =0,ie=atoms.size(); i<ie; i++) {
			tgba.getAPs().get(i).setExpression(atoms.get(i));
		}
	}
	
	public void runProduct (int nbSteps, int timeout, boolean withStack) throws LTLException {
				
		boolean[] acceptAll = computeFullyAcceptingStates(tgba);
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		long time = System.currentTimeMillis();
		int reset = 0;
		ProductState initial = new ProductState(initialTGBA, getWU(initialTGBA).getInitial());
		ProductState cur = initial;
		int [] enabled = getWU(initialTGBA).computeEnabled(cur.getPNState());
		int [] initEnabled = enabled.clone();
		
		setAPinterpretation(initialTGBA);
		
		WeakHashMap<ProductState, int[]> stack = new WeakHashMap<>();

		int[] curAcc = new int [tgba.getNbAcceptance()];
		initialiseStack(withStack, initial, stack, curAcc);
		int stackdepth = 0;
		
		for (int i=0; i < nbSteps ; i++) {
			List<Integer> tgbaArcs = computeSuccTGBAEdges(cur,tgba); 					
			
			if (DEBUG >= 1) {
				System.out.println("In product state :" + cur);
			}
			
			int tgbaState = cur.getTGBAState();
			if (tgbaArcs.isEmpty()) {
				if (cur.getPNState().equals(getWU(initialTGBA).getInitial()) && tgbaState == initialTGBA ) {
					System.out.println("Initial state of product has no viable successors after "+i+" steps with "+reset+" reset in "+(System.currentTimeMillis()-time)+ " ms.");
					
					throw new EmptyProductException();					
				} else {
					reset ++;
					cur = initial;
					enabled = initEnabled.clone();
					setAPinterpretation(initialTGBA);
					initialiseStack(withStack, initial, stack, curAcc);
					stackdepth = 0;
					continue;
				}
			} else if (tgbaArcs.stream().anyMatch(arcid -> acceptAll[tgba.getEdges().get(tgbaState).get(arcid).getDest()])) {
				System.out.println("Entered a terminal (fully accepting) state of product in "+i+" steps with "+reset+" reset in "+(System.currentTimeMillis()-time)+ " ms.");

				throw new AcceptedRunFoundException();
			} else {
				
				if (enabled[0]==0 || getWU(tgbaState).canStutter(enabled)) {
					if (tgba.getInfStutter().get(tgbaState).eval(cur.getPNState())==1) {
						System.out.println("Stuttering criterion allowed to conclude after "+i+" steps with "+reset+" reset in "+(System.currentTimeMillis()-time)+ " ms.");
						throw new AcceptedRunFoundException();
					}
					if (enabled[0]==0) {
						// deadlock in KS
						reset ++;
						cur = initial;
						enabled = initEnabled.clone();
						setAPinterpretation(initialTGBA);
						initialiseStack(withStack, initial, stack, curAcc);
						stackdepth = 0;
						continue;
					}				
				}
				
				// random choice of a successor
				int r = rand.nextInt(enabled[0])+1;
				int tfired = enabled[r];			

				int rq = rand.nextInt(tgbaArcs.size());
				TGBAEdge chosenEdge = tgba.getEdges().get(tgbaState).get(rq);
				int newq = chosenEdge.getDest();
				
				if (DEBUG >= 1) System.out.println("Chosen edge :"+chosenEdge);					
				
				if (withStack) {
					for (int j = 0, je = chosenEdge.getAcceptance().size(); j < je; j++) {
						int acc = chosenEdge.getAcceptance().keyAt(j);
						++curAcc[acc];
					}
				}
				
				if (DEBUG >= 1) System.out.println("Chosen transition "+tfired+" :"+ getWU(tgbaState).getNet().getTnames().get(tfired) +": pre="+getWU(tgbaState).getFlowPT().getColumn(tfired) + " post="+getWU(tgbaState).getFlowTP().getColumn(tfired));
				
				SparseIntArray newstate = getWU(tgbaState).fire(tfired, cur.getPNState());
				if (getWU(newq) == getWU(tgbaState)) {
					getWU(newq).updateEnabled(newstate, enabled, tfired, false);
				} else {
					newstate = image(tgbaState, newq, newstate);
					enabled = getWU(newq).computeEnabled(newstate);
					setAPinterpretation(newq);
					stack.clear();
				}
				cur = new ProductState(newq, newstate);
				
				if (withStack) {
					int[] oldAccs = stack.get(cur);
					stackdepth++;
					if (oldAccs == null) {
						stack.put(cur, curAcc.clone());
					} else {
						// already met
						boolean isAccepting = true;
						for (int acci=0; acci < tgba.getNbAcceptance() ; acci++) {
							if (curAcc[acci] == oldAccs[acci]) {
								isAccepting = false;
								break;
							}
						}
						if (isAccepting) {
							if (DEBUG >= 1) {
								System.out.println("Reached state on stack "+ cur + " with counters " + Arrays.toString(curAcc) + " old accs =" + Arrays.toString(oldAccs));
							}
							// Great we found an accepted run !
							System.out.println("Stack based approach found an accepted trace after " + i
									+ " steps with " + reset + " reset with depth " + stackdepth + " and stack size "
									+ stack.size() + " in " + (System.currentTimeMillis() - time) + " ms.");
							throw new AcceptedRunFoundException();
						}
					}
				}
				
				if (i % 10 == 0) {
					long curtime = System.currentTimeMillis();
					if (curtime - time > 1000*timeout) {
						System.out.println("Product exploration timeout after "+i+" steps with "+reset+" reset in "+(curtime-time)+ " ms.");
						return ;
					}
				}			
			}
		}
		
		System.out.println("Product exploration explored " + nbSteps +" steps with "+reset+" reset in "+(System.currentTimeMillis()-time)+ " ms.");
		
	}
	private void initialiseStack(boolean withStack, ProductState initial, WeakHashMap<ProductState, int[]> stack, int [] curAcc) {
		if (withStack) {
			stack.clear();	
			Arrays.fill(curAcc, 0);
			stack.put(initial, curAcc.clone());
		}
	}

	private SparseIntArray image(int tgbaState, int newq, SparseIntArray newstate) {		
		if (!hasMulti)
			return newstate;
		if (! tgba.getStutterMarkers()[tgbaState] && tgba.getStutterMarkers()[newq]) {
			List<Integer> res = image.stream().map(e -> e.eval(newstate)).collect(Collectors.toList());
			return new SparseIntArray(res);
		} else {
			return newstate;
		}
	}

	private WalkUtils getWU(int newq) {
		return wus.get(newq);
	}

	public static List<Integer> computeSuccTGBAEdges (ProductState source, TGBA tgba) {
		List<TGBAEdge> arcs = tgba.getEdges().get(source.getTGBAState());
		SparseIntArray srcPN = source.getPNState();
		
		List<Integer> canFire = new ArrayList<>();
		for (int arcid = 0, arcide = arcs.size(); arcid < arcide; arcid++) {
			TGBAEdge arc = arcs.get(arcid);
			if (arc.getCondition().eval(srcPN) != 0) {
				canFire.add(arcid);
			}
		}
		return canFire;
	}

	
	private boolean[] computeFullyAcceptingStates(TGBA tgba) {
		boolean [] acceptAll = new boolean[tgba.nbStates()];
		{
			for (int eid=0, eide=tgba.nbStates() ; eid < eide ; eid++) {
				for (TGBAEdge e : tgba.getEdges().get(eid)) {
					if (e.getSrc() == e.getDest() && e.getAcceptance().size() == tgba.getNbAcceptance() && e.getCondition().getOp() == Op.BOOLCONST && e.getCondition().getValue()==1) {
						acceptAll [eid] = true;
						break;
					}
				}
			}
		}
		return acceptAll;
	}
}
