package fr.lip6.move.gal.semantics;

import java.util.List;
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

}
