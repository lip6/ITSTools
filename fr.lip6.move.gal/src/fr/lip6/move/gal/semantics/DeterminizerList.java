package fr.lip6.move.gal.semantics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Computes from a semantic definition, an equivalent semantics, but as a list of alternatives, each one being choice free.
 * In other words, this corresponds to a conjunctive normal form, one big Alternative with only Sequences (i.e. a List<INext>) of Assign and Predicate in each of the alternatives. 
 * 
 * @author ythierry
 *
 */
public class DeterminizerList implements NextVisitor<List<List<INext>>> {

	@Override
	public List<List<INext>> visit(Assign ass) {
		return Collections.singletonList(Collections.singletonList(ass));
	}

	@Override
	public List<List<INext>> visit(Predicate pred) {
		return Collections.singletonList(Collections.singletonList(pred));
	}

	
	@Override
	public List<List<INext>> visit(Alternative alt) {
		List<List<INext>> toret = new ArrayList<>();
		alt.getAlternatives().stream().forEach(a -> toret.addAll(a.accept(this)));
		return toret;
	}

	@Override
	public List<List<INext>> visit(Sequence seq) {
		List<List<INext>> toret = new ArrayList<>();
		toret.add(new ArrayList<>());
		for (INext act : seq.getActions()) {
			List<List<INext>> sub = act.accept(this);
			List<List<INext>> tmp = new ArrayList<>(toret.size()*sub.size());
			for (List<INext> prefix  : toret) {
				for (List<INext> cont : sub) {
					List<INext> toadd = new ArrayList<>(prefix.size()+cont.size());
					toadd.addAll(prefix);
					toadd.addAll(cont);
					tmp.add(toadd);
				}
			}
			toret = tmp;
		}
		return toret;
	}
	
}
