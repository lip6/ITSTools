package fr.lip6.ltl.tgba;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.WalkUtils;
import fr.lip6.move.gal.structural.expr.Expression;

public class RandomProductWalker {

	private WalkUtils wu;

	public RandomProductWalker(SparsePetriNet spn) {
		this.wu = new WalkUtils(spn);
	}

	public void runProduct (TGBA tgba, int nbSteps, int timeout) throws LTLException {
		
		Product product = new Product(wu,tgba);
		
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
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		long time = System.currentTimeMillis();
		int reset = 0;
		ProductState cur = product.getInitial();
		int [] enabled = wu.computeEnabled(cur.getPNState());
		
		
		for (int i=0; i < nbSteps ; i++) {
			List<Integer> tgbaArcs = product.computeSuccTGBAEdges(cur);
			
			if (enabled[0]==0 || wu.canStutter(enabled)) {
				if (product.getTgba().getInfStutter().get(cur.getTGBAState()).eval(cur.getPNState())==1) {
					System.out.println("Stuttering criterion allowed to conclude after "+i+" steps with "+reset+" reset in "+(System.currentTimeMillis()-time)+ " ms.");
					throw new AcceptedRunFoundException();
				}
				if (enabled[0]==0) {
					// deadlock in KS
					reset ++;
					cur = product.getInitial();
					enabled = wu.computeEnabled(cur.getPNState());
				}				
			}
									
			if (tgbaArcs.isEmpty()) {
				if (cur.getPNState().equals(wu.getInitial()) && cur.getTGBAState() == tgba.getInitial() ) {
					System.out.println("Initial state of product has no viable successors after "+i+" steps with "+reset+" reset in "+(System.currentTimeMillis()-time)+ " ms.");
					
					throw new EmptyProductException();					
				} else {
					reset ++;
					cur = product.getInitial();
					enabled = wu.computeEnabled(cur.getPNState());
				}
			} else if (tgbaArcs.stream().anyMatch(ps -> acceptAll[ps])) {
				System.out.println("Entered a fully accepting state of product in "+i+" steps with "+reset+" reset in "+(System.currentTimeMillis()-time)+ " ms.");

				throw new AcceptedRunFoundException();
			} else {
				// random choice of a successor
				int r = rand.nextInt(enabled[0])+1;
				int tfired = enabled[r];			

				SparseIntArray newstate = wu.fire ( tfired, cur.getPNState());				
				// NB : does not discard empty events
				wu.updateEnabled(newstate, enabled, tfired,false);

				int rq = rand.nextInt(tgbaArcs.size());
				
				cur = new ProductState(tgbaArcs.get(rq), newstate);

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
}
