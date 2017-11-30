package fr.lip6.move.gal.semantics;

/**
 * A visitor for INext semantic bricks, knows about the four basic bricks : Alt, Seq, Assign, Predicate
 * @author ythierry
 */
public interface NextVisitor<T> {
	T visit(Assign ass);

	T visit(Predicate pred);

	T visit(Alternative alt);

	T visit(Sequence seq);

}
