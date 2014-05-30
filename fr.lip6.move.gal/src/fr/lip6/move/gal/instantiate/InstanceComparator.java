package fr.lip6.move.gal.instantiate;

import java.util.Comparator;

import fr.lip6.move.gal.AbstractInstance;
import fr.lip6.move.gal.GalInstance;
import fr.lip6.move.gal.ItsInstance;
import fr.lip6.move.gal.OtherInstance;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.TemplateInstance;

public class InstanceComparator implements Comparator<AbstractInstance> {

	private final Specification spec;

	public InstanceComparator(Specification spec) {
		this.spec=spec;
	}

	private int getIndex(AbstractInstance ai) {
		if (ai instanceof GalInstance) {
			GalInstance gali = (GalInstance) ai;
			return spec.getTypes().indexOf(gali.getType());
		}
		if (ai instanceof ItsInstance) {
			ItsInstance isti = (ItsInstance) ai;
			return spec.getTypes().indexOf(isti.getType());
		}
		if (ai instanceof TemplateInstance) {
			return -1;
		}
		if (ai instanceof OtherInstance) {
			OtherInstance oi = (OtherInstance) ai;
			return spec.getTypes().indexOf(oi.getType());
		}
		return 0;
	}

	@Override
	public int compare(AbstractInstance ai1, AbstractInstance ai2) {
		return new Integer(getIndex(ai1)).compareTo(getIndex(ai2));
	}

}
