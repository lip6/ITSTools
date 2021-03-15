package fr.lip6.ltl.tgba;

import java.util.ArrayList;
import java.util.List;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.WalkUtils;

public class Product {

	private WalkUtils wu;
	private TGBA tgba;

	public Product(WalkUtils wu, TGBA tgba) {
		this.wu = wu;
		this.tgba = tgba;
	}
	
	public ProductState getInitial() {
		return new ProductState(tgba.getInitial(), wu.getInitial());
	}
	
	public TGBA getTgba() {
		return tgba;
	}
	
	
	public List<ProductState> computeSuccessors(ProductState source) {
		List<ProductState> succs = new ArrayList<>();
		
		SparseIntArray srcPN = source.getPNState();
		int [] list = wu.computeEnabled(srcPN);
		
		if (list[0] == 0){
			//System.out.println("Dead end with self loop(s) found at step " + i);				
			List<TGBAEdge> arcs = tgba.getEdges().get(source.getTGBAState());
			for (TGBAEdge arc:arcs) {
				if (arc.getCondition().eval(srcPN) == 1) {
					succs.add(new ProductState(arc.getDest(),srcPN));
				}
			}
			for (ProductState ps : succs) {
				ps.setCanStutter(true);
				ps.setDead(true);
			}
			return succs;
		}

		List<TGBAEdge> arcs = tgba.getEdges().get(source.getTGBAState());

		List<TGBAEdge> canFire = new ArrayList<> ();
		for (TGBAEdge arc:arcs) {
			if (arc.getCondition().eval(srcPN) == 1) {
				canFire.add(arc);
			}
		}
		
		if (! canFire.isEmpty()) {
			List<SparseIntArray> pnSuccs = new ArrayList<> (list[0]);
			boolean canStutter = false;
			for (int ti = 1 ; ti-1 < list[0] ; ti++) {
				SparseIntArray succ = wu.fire(list[ti],srcPN);
				if (wu.getCombFlow().getColumn(list[ti]).size() == 0) {
					canStutter = true;
				} else {
					pnSuccs.add(succ);
				}
			}

			for (TGBAEdge arc:canFire) {
				if (canStutter) {
					ProductState ps = new ProductState(arc.getDest(), srcPN);
					ps.setCanStutter(true);
					succs.add(ps);
				}
			}
			
			for (TGBAEdge arc:canFire) {
				for (SparseIntArray st : pnSuccs) {
					succs.add(new ProductState(arc.getDest(), st));					
				}
			}
		}
		return succs;
	}
	
}
