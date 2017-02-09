package fr.lip6.move.gal.semantics;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class NextStreamVisitor<T> implements NextVisitor<Stream<T>>{

	protected Stream<T> current;
	
	public NextStreamVisitor(Stream<T> current) {
		this.current = current;
	}

	@Override
	public Stream<T> visit(Alternative alt) {
		Stream<T> toret = null;
		List<T> stcopy = current.collect(Collectors.toList());
		for (INext act : alt.getAlternatives()) {
			if (toret == null) {
				current = stcopy.stream();
				toret = act.accept(this);
			} else {
				current = stcopy.stream();
				Stream<T> tocat = act.accept(this); 
				Stream<T> toret2 = Stream.concat(tocat, toret);
				toret = toret2;
			}
		}
		return toret;
	}

	@Override
	public Stream<T> visit(Sequence seq) {
		for (INext act : seq.getActions()) {
			current = act.accept(this);
		}
		return current;
	}
	
	@Override
	public Stream<T> visitAbort() {
		return Stream.empty();
	}

	@Override
	public Stream<T> visitIdentity() {
		return current;
	}
}
