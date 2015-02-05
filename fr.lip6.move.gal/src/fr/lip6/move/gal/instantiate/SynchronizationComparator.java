package fr.lip6.move.gal.instantiate;

import java.util.Comparator;

import fr.lip6.move.gal.CompositeTypeDeclaration;
import fr.lip6.move.gal.InstanceCall;
import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.SelfCall;
import fr.lip6.move.gal.Statement;
import fr.lip6.move.gal.Synchronization;

public class SynchronizationComparator implements
		Comparator<Synchronization> {

	@Override
	public int compare(Synchronization s1, Synchronization s2) {
		// Integer.compare unavailable java <= 1.6
		int cmp = new Integer(s1.getActions().size()).compareTo(s2.getActions().size());
		if (cmp != 0)
			return cmp;
		for (int i = 0; i < s1.getActions().size() ; i++) {
			Statement a1 = s1.getActions().get(i);
			Statement a2 = s2.getActions().get(i);
			if (a1 instanceof SelfCall) {
				SelfCall self = (SelfCall) a1;
				if (a2 instanceof SelfCall) {
					SelfCall self2 = (SelfCall) a2;
					return ((Label)self.getLabel()).getName().compareTo(((Label)self2.getLabel()).getName());
				} else {
					return 1;
				}
			} else if (a1 instanceof InstanceCall) {
				InstanceCall icall = (InstanceCall) a1;
				if (a2 instanceof InstanceCall) {
					InstanceCall icall2 = (InstanceCall) a2;
					cmp = new Integer(((CompositeTypeDeclaration)icall.getInstance().eContainer()).getInstances().indexOf(icall.getInstance()))
						.compareTo(((CompositeTypeDeclaration)icall2.getInstance().eContainer()).getInstances().indexOf(icall2.getInstance()));
					if (cmp != 0)
						return cmp;
					return ((Label)icall.getLabel()).getName().compareTo(((Label)icall2.getLabel()).getName());
				} else {
					return -1;
				}
			}
		}
		return 0;
	}

}
