package fr.lip6.ltl.tgba;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.WalkUtils;
import fr.lip6.move.gal.structural.expr.Expression;

public class RandomProductWalker {

	private List<WalkUtils> wus = new ArrayList<>();

	public RandomProductWalker(ISparsePetriNet spn) {
		this.wus.add(new WalkUtils(spn));
	}
	
	int initialTGBA ;
	List<TGBA> tgbas = new ArrayList<>();
	public void setTGBA (TGBA tgba) {
		tgbas.clear();
		WalkUtils wu = wus.get(0);
		wus.clear();
		for (int i=0; i < tgba.getEdges().size() ; i++) {
			tgbas.add(tgba);
			wus.add(wu);
		}
		initialTGBA = tgba.getInitial();		
	}
	
	public void runProduct (int nbSteps, int timeout) throws LTLException {
				
		boolean[] acceptAll = computeFullyAcceptingStates(tgbas.get(initialTGBA));
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		long time = System.currentTimeMillis();
		int reset = 0;
		ProductState initial = new ProductState(initialTGBA, getWU(initialTGBA).getInitial());
		ProductState cur = initial;
		int [] enabled = getWU(initialTGBA).computeEnabled(cur.getPNState());
		
		
		for (int i=0; i < nbSteps ; i++) {
			List<Integer> tgbaArcs = computeSuccTGBAEdges(cur,tgbas.get(cur.getTGBAState()));					
									
			int tgbaState = cur.getTGBAState();
			if (tgbaArcs.isEmpty()) {
				if (cur.getPNState().equals(getWU(initialTGBA).getInitial()) && tgbaState == initialTGBA ) {
					System.out.println("Initial state of product has no viable successors after "+i+" steps with "+reset+" reset in "+(System.currentTimeMillis()-time)+ " ms.");
					
					throw new EmptyProductException();					
				} else {
					reset ++;
					cur = initial;
					enabled = getWU(initialTGBA).computeEnabled(cur.getPNState());
					continue;
				}
			} else if (tgbaArcs.stream().anyMatch(ps -> acceptAll[ps])) {
				System.out.println("Entered a fully accepting state of product in "+i+" steps with "+reset+" reset in "+(System.currentTimeMillis()-time)+ " ms.");

				throw new AcceptedRunFoundException();
			} else {
				
				if (enabled[0]==0 || getWU(tgbaState).canStutter(enabled)) {
					if (tgbas.get(tgbaState).getInfStutter().get(tgbaState).eval(cur.getPNState())==1) {
						System.out.println("Stuttering criterion allowed to conclude after "+i+" steps with "+reset+" reset in "+(System.currentTimeMillis()-time)+ " ms.");
						throw new AcceptedRunFoundException();
					}
					if (enabled[0]==0) {
						// deadlock in KS
						reset ++;
						cur = initial;
						enabled = getWU(initialTGBA).computeEnabled(cur.getPNState());
						continue;
					}				
				}
				
				// random choice of a successor
				int r = rand.nextInt(enabled[0])+1;
				int tfired = enabled[r];			

				int rq = rand.nextInt(tgbaArcs.size());
				int newq = tgbaArcs.get(rq);
				
				SparseIntArray newstate = getWU(tgbaState).fire(tfired, cur.getPNState());

				if (getWU(newq) == getWU(tgbaState)) {
					getWU(newq).updateEnabled(newstate, enabled, tfired, false);
				} else {
					newstate = image(tgbaState, newq, newstate);
					enabled = getWU(newq).computeEnabled(newstate);
				}
				cur = new ProductState(newq, newstate);
				
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

	private SparseIntArray image(int tgbaState, int newq, SparseIntArray newstate) {		
		return newstate;
	}

	private WalkUtils getWU(int newq) {
		return wus.get(newq);
	}

	public static List<Integer> computeSuccTGBAEdges (ProductState source, TGBA tgba) {
		List<TGBAEdge> arcs = tgba.getEdges().get(source.getTGBAState());
		SparseIntArray srcPN = source.getPNState();
		
		List<Integer> canFire = new ArrayList<> ();
		for (TGBAEdge arc:arcs) {
			if (arc.getCondition().eval(srcPN) == 1) {
				canFire.add(arc.getDest());
			}
		}
		return canFire;
	}

	
	private boolean[] computeFullyAcceptingStates(TGBA tgba) {
		boolean [] acceptAll = new boolean[tgba.getEdges().size()];
		{
			Expression tt = Expression.constant(true);
			for (int eid=0, eide=tgba.getEdges().size() ; eid < eide ; eid++) {
				for (TGBAEdge e : tgba.getEdges().get(eid)) {
					if (e.getSrc() == e.getDest() && e.getAcceptance().size() == tgba.getNbAcceptance() && e.getCondition().equals(tt)) {
						acceptAll [eid] = true;
						break;
					}
				}
			}
		}
		return acceptAll;
	}
}
