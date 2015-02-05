package fr.lip6.move.gal.instantiate;

import java.util.Comparator;

import fr.lip6.move.gal.InstanceDecl;
import fr.lip6.move.gal.Specification;

public class InstanceComparator implements Comparator<InstanceDecl> {

	private final Specification spec;

	public InstanceComparator(Specification spec) {
		this.spec=spec;
	}

	@Override
	public int compare(InstanceDecl ai1, InstanceDecl ai2) {
		return TypeFuser.computeInstanceTypeString(ai1).compareTo(TypeFuser.computeInstanceTypeString(ai2));
	}

}
