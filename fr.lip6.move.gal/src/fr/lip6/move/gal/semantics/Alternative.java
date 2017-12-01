package fr.lip6.move.gal.semantics;

import java.util.ArrayList;
import java.util.List;

import fr.lip6.move.gal.False;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.UniqueTable;

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

	
	private static UniqueTable<Alternative> unique = new UniqueTable<>();
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
			return Predicate.pred(GalFactory.eINSTANCE.createFalse(), null);
		} else if (flat.size() == 1) {
			return flat.get(0);
		} else {
			return unique.canonical(new Alternative(flat));
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

	@Override
	public int hashCode() {
		final int prime = 13043;
		int result = 1;
		result = prime * result + ((alts == null) ? 0 : alts.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alternative other = (Alternative) obj;
		if (alts == null) {
			if (other.alts != null)
				return false;
		} else if (!alts.equals(other.alts))
			return false;
		return true;
	}
	
}
