package fr.lip6.move.gal.semantics;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An awesome generic class to work with Streams of things and INext bricks.
 * This visitor traverses all the various paths through the semantics and presents them as a stream. 
 * @author ythierry
 *
 * @param <T> the stuff returned in the stream.
 */
public abstract class NextStreamVisitor<T> implements NextVisitor<Stream<T>> {

	protected Stream<T> current;

	/**
	 * Initialize this class with some stream you have built, e.g. from a list containing initial states.
	 * @param current used in the Visitor's recursions, the input Stream for the visit.
	 */
	public NextStreamVisitor(Stream<T> current) {
		this.current = current;
	}

	/**
	 * Alternatives will concatenate the effect of traveling down each of the possible paths (DFS traversal).
	 * Typically an alternative will push more elements into the output Stream than there were originally.
	 * Unfortunately we need to collect the input into a list here, since reading the input stream is destructive and we need to read it more than just once.
	 */
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

	/**
	 * Visiting a sequence is just chaining the treatments on top of one another, i.e. semantic composition of behaviors. 
	 */
	@Override
	public Stream<T> visit(Sequence seq) {
		for (INext act : seq.getActions()) {
			current = act.accept(this);
		}
		return current;
	}

}
