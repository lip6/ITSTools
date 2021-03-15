package fr.lip6.ltl.tgba;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
		
		for (int i=0; i < nbSteps ; i++) {
			
			if (cur.canStutter) {
				if (product.getTgba().getInfStutter().get(cur.getTGBAState()).eval(cur.getPNState())==1) {
					System.out.println("Stuttering criterion allowed to conclude after "+i+" steps with "+reset+" reset in "+(System.currentTimeMillis()-time)+ " ms.");

					throw new AcceptedRunFoundException();
				}
				if (cur.isDead) {
					reset ++;
					cur = product.getInitial();
				}
			}
			
			List<ProductState> succs = product.computeSuccessors(cur);
			
			if (succs.isEmpty()) {
				if (cur.getPNState().equals(wu.getInitial())) {
					throw new EmptyProductException();					
				} else {
					reset ++;
					cur = product.getInitial();
				}
			} else if (succs.stream().anyMatch(ps -> acceptAll[ps.getTGBAState()])) {
				throw new AcceptedRunFoundException();
			} else {
				// random choice of a successor
				int choice = rand.nextInt(succs.size()); 
				cur = succs.get(choice);

				if (i % 10 == 0) {
					long curtime = System.currentTimeMillis();
					if (curtime - time > 1000*timeout) {
						System.out.println("Product exploration timeout after "+i+" steps with "+reset+" reset in "+(curtime-time)+ " ms.");
					}
				}			
			}
		}
		
		System.out.println("Product exploration explored " + nbSteps +" steps with "+reset+" reset in "+(System.currentTimeMillis()-time)+ " ms.");
		
	}
}
