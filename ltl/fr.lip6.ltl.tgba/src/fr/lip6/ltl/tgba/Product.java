package fr.lip6.ltl.tgba;

import java.util.ArrayList;
import java.util.List;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.WalkUtils;

public class Product {

	private WalkUtils wu;
	private TGBA tgba;

	public Product(ISparsePetriNet spn, TGBA tgba) {
		this.wu = new WalkUtils(spn);
		this.tgba = tgba;
	}
	
	public ProductState getInitial() {
		return new ProductState(tgba.getInitial(), wu.getInitial());
	}
	
	public List<ProductState> computeSuccessors(ProductState source) {
		List<ProductState> succs = new ArrayList<>();
		
		int [] list = wu.computeEnabled(source.getPNState());
		
		if (list[0] == 0){
			//System.out.println("Dead end with self loop(s) found at step " + i);				
			//TODO 
			return succs;
		}
		
		List<SparseIntArray> pnSuccs = null; // delay computation
		
		SparseIntArray srcPN = source.getPNState();
		
		
		List<TGBAEdge> arcs = tgba.getEdges().get(source.getTGBAState());
		for (TGBAEdge arc:arcs) {
			if (arc.getCondition().eval(srcPN) == 1) {
				if (pnSuccs == null) {
					pnSuccs = new ArrayList<> (list[0]);
					for (int ti = 1 ; ti-1 < list[0] ; ti++) {
						SparseIntArray succ = wu.fire(list[ti],srcPN);
						pnSuccs.add(succ);
					}
				}
				for (SparseIntArray st : pnSuccs) {
					succs.add(new ProductState(arc.getDest(), st));
				}
			}
		}
		
		return succs;
	}
	
}
