package fr.lip6.move.gal.struct2gal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import fr.lip6.move.gal.order.CompositeGalOrder;
import fr.lip6.move.gal.order.IOrder;
import fr.lip6.move.gal.order.VarOrder;
import fr.lip6.move.gal.structural.hlpn.HLPlace;
import fr.lip6.move.gal.structural.hlpn.SparseHLPetriNet;

public class HLPNOrderComputer {

	public static IOrder computeOrder (SparseHLPetriNet pn) {
		Map<String,List<HLPlace>> placeSort = new HashMap<>();
		for (HLPlace p : pn.getPlaces()) {
			placeSort.computeIfAbsent( SparseHLPetriNet.sortName(p.getSort()), v -> new ArrayList<>()).add(p);
		}
		List<IOrder> orders = new ArrayList<>();
		for (Entry<String, List<HLPlace>> ps : placeSort.entrySet()) {
			List<HLPlace> places = ps.getValue(); 
			if (! places.isEmpty())
			{
				//Sort psort = places.get(0).getType().getStructure();
				int sz = places.get(0).getInitial().length; 
	
				if (sz > 1) {
					
					for (int i=0 ; i < sz ; i++) {
						List<String> supp = new ArrayList<>();
						for (HLPlace p : places) {
							supp.add( p.getName() +"_" + i);
						}
						orders.add(new VarOrder(supp, ps.getKey()+i));
					}
				} else {
					// dot case mostly
					for (HLPlace p : places) {
						List<String> supp = new ArrayList<>();
						String pname = p.getName();
						supp.add(pname+"_0");
						orders.add(new VarOrder(supp, pname));
					}
				}
			}
		}
		IOrder order = new CompositeGalOrder(orders, "main");
		return order;
	}

}
