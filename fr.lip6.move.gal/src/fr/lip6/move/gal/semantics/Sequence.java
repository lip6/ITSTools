package fr.lip6.move.gal.semantics;

import java.util.ArrayList;
import java.util.List;

import fr.lip6.move.gal.False;
import fr.lip6.move.gal.UniqueTable;

/**
 * A semantic composition of behaviors.
 * @author ythierry
 *
 */
public class Sequence implements INext {

	private List<INext> acts;

	private Sequence(List<INext> acts) {
		this.acts = acts;
	}

	// private static UniqueTable<Sequence> unique = new UniqueTable<>();
	public static INext seq(List<INext> acts) {
		List<INext> flat = new ArrayList<INext>(acts.size());
		for (INext n : acts) {
			if (n instanceof Sequence) {
				Sequence seq = (Sequence) n;
				flat.addAll(seq.getActions());
			} else if (n instanceof Predicate && ((Predicate) n).getGuard() instanceof False) {
				return n;
			} else {
				flat.add(n);
			}
		}
		if (flat.size() == 1) {
			return acts.get(0);
		} else {
			// return unique.canonical(new Sequence(flat));
			return new Sequence(flat);
		}
	}

	@Override
	public String toString() {
		boolean first = true;
		StringBuilder sb = new StringBuilder();
		if (acts.isEmpty()) {
			sb.append("/*NOP*/");
		}
		for (INext act : acts) {
			if (first)
				first = false;
			else
				sb.append("; ");
			sb.append(act.toString());
		}
		sb.append('\n');
		return sb.toString();
	}

	public List<INext> getActions() {
		return acts;
	}

	@Override
	public <T> T accept(NextVisitor<T> vis) {
		return vis.visit(this);
	}

	@Override
	public int hashCode() {
		final int prime = 269;
		int result = 1;
		result = prime * result + ((acts == null) ? 0 : acts.hashCode());
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
		Sequence other = (Sequence) obj;
		if (acts == null) {
			if (other.acts != null)
				return false;
		} else if (!acts.equals(other.acts))
			return false;
		return true;
	}

}
