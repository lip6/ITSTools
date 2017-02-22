package fr.lip6.move.gal.semantics;

import java.util.ArrayList;
import java.util.List;

import fr.lip6.move.gal.False;
import fr.lip6.move.gal.GalFactory;

/**
 * A successor brick that corresponds to non determinism, such as induced by calls or if-then-else.
 * @author ythierry
 *
 */
public class Alternative implements INext {

	private List<INext> alts;

	private Alternative(List<INext> alts) {
		this.alts = alts;
	}

	/**
	 * Factory operation to build an alternative.
	 * @param alts a list of possibilities
	 * @return a INext representing the alternative
	 */
	public static INext alt(List<INext> alts) {
		List<INext> flat = new ArrayList<INext>(alts.size());
		for (INext n : alts) {
			if (n instanceof Alternative) {
				Alternative alt = (Alternative) n;
				flat.addAll(alt.getAlternatives());
			} else if (n instanceof Predicate && ((Predicate) n).getGuard() instanceof False) {					
				// continue
			} else {
				flat.add(n);
			}
		}
		if (flat.isEmpty()) {
			return new Predicate(GalFactory.eINSTANCE.createFalse(), null);
		} else if (flat.size() == 1) {
			return flat.get(0);
		} else {
			return new Alternative(flat);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("ALT(");
		boolean first = true;
		for (INext act : alts) {
			if (first)
				first = false;
			else
				sb.append("\n||\n");
			sb.append(act.toString());
		}
		return sb.toString();
	}

	public List<INext> getAlternatives() {
		return alts;
	}

	@Override
	public <T> T accept(NextVisitor<T> vis) {
		return vis.visit(this);
	}
}
