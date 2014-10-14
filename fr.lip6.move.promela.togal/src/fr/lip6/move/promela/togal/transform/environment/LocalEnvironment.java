package fr.lip6.move.promela.togal.transform.environment;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.promela.promela.ChanVariable;
import fr.lip6.move.promela.promela.MemVariable;
import fr.lip6.move.promela.togal.transform.representations.ChannelRepresentation;

public class LocalEnvironment implements PromelaEnvironment {

	private Map<MemVariable, ArrayPrefix> arrayMap;

	private Map<MemVariable, Variable> atomicMap;

	private Map<ChanVariable, ChannelRepresentation> chanMap;

	public LocalEnvironment() {
		arrayMap = new HashMap<MemVariable, ArrayPrefix>();
		atomicMap = new HashMap<MemVariable, Variable>();
		chanMap = new HashMap<ChanVariable, ChannelRepresentation>();
	}

	public ArrayPrefix getArray(MemVariable m) {
		return arrayMap.get(m);
	}
	
	public Variable getAtomic(MemVariable m) {
		return atomicMap.get(m);
	}

	public ChannelRepresentation getChannel(ChanVariable m) {
		return chanMap.get(m);
	}

	// bolean?
	public void putAtomic(MemVariable mv, Variable ap) {
		atomicMap.put(mv, ap);
	}

	public void putArray(MemVariable mv, ArrayPrefix ap) {
		arrayMap.put(mv, ap);
	}

	public void putChannel(ChanVariable cv, ChannelRepresentation cr) {
		chanMap.put(cv, cr);
	}

	public void addToGal(GALTypeDeclaration gal) {

		EList<Variable> gVar = gal.getVariables();
		EList<ArrayPrefix> gAr = gal.getArrays();
		for (Variable v : atomicMap.values())
			gVar.add(v);

		for (ArrayPrefix ap : arrayMap.values())
			gAr.add(ap);

		for (ChannelRepresentation cr : chanMap.values()) {
			gAr.add(cr.getgChanArray());
			gVar.add(cr.getgChanAvail());
		}
		// channel
	}
}
