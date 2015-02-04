package fr.lip6.move.gal.instantiate;

import java.util.Comparator;

import fr.lip6.move.gal.AbstractInstance;
import fr.lip6.move.gal.Specification;

public class InstanceComparator implements Comparator<AbstractInstance> {

	private final Specification spec;

	public InstanceComparator(Specification spec) {
		this.spec=spec;
	}

	@Override
	public int compare(AbstractInstance ai1, AbstractInstance ai2) {
		return TypeFuser.computeInstanceTypeString(ai1).compareTo(TypeFuser.computeInstanceTypeString(ai2));
	}

}
