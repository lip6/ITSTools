package fr.lip6.move.gal.semantics;

import java.util.List;

public class Alternative implements INext {

	private List<INext> alts;
	
	private Alternative(List<INext> alts) {
		this.alts = alts;
	}
	public static INext alt (List<INext> alts) {
		if (alts.isEmpty()) {
			return INext.EMPTY;
		} else if (alts.size() == 1) {
			return alts.get(0);
		} else {
			return new Alternative(alts);
		}
	}
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("ALT(");		
		boolean first = true;
		for (INext act : alts) {
			if (first) first = false;
			else sb.append("\n||\n");
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

