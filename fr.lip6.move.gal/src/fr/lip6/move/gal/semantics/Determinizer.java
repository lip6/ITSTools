package fr.lip6.move.gal.semantics;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Computes from a semantic definition, an equivalent semantics, but as a list of alternatives, each one being choice free.
 * In other words, this corresponds to a conjunctive normal form, one big Alternative with only Sequences of Assign and Predicate in each of the alternatives. 
 * 
 * @author ythierry
 *
 */
public class Determinizer extends NextStreamVisitor<List<INext>> {

	public Determinizer(Stream<List<INext>> current) {
		super(current);
	}

	@Override
	public Stream<List<INext>> visit(Assign ass) {
		return current.peek(actions -> actions.add(ass));
	}

	@Override
	public Stream<List<INext>> visit(Predicate pred) {
		return current.peek(actions -> actions.add(pred));
	}

	
	/**
	 * Alternatives will concatenate the effect of traveling down each of the possible paths (DFS traversal).
	 * Typically an alternative will push more elements into the output Stream than there were originally.
	 * Unfortunately we need to collect the input into a list here, since reading the input stream is destructive and we need to read it more than just once.
	 */
	@Override
	public Stream<List<INext>> visit(Alternative alt) {
		Stream<List<INext>> toret = null;
		List<List<INext>> stcopy = current.collect(Collectors.toList());
		for (INext act : alt.getAlternatives()) {
			List<List<INext>> stcopy2 = new ArrayList<List<INext>>();
			stcopy.forEach(x -> stcopy2.add(new ArrayList<>(x)));
			Stream<List<INext>> tocat = act.accept(new Determinizer(stcopy2.stream()));
			if (toret == null) {				
				toret = tocat;
			} else {
				Stream<List<INext>> toret2 = Stream.concat(tocat, toret);
				toret = toret2;
			}
		}
		return toret;
	}
	
}
