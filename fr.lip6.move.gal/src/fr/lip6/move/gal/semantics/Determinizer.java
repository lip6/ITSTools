package fr.lip6.move.gal.semantics;

import java.util.List;
import java.util.stream.Stream;

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
