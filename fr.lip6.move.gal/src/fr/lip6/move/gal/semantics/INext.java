package fr.lip6.move.gal.semantics;

/**
 * A semantic brick to describe successor relationships : Alternative, Sequence, Assign, Predicate and the basic Identity and Abort.
 * The interface is almost empty; see the visitor API for concrete definition of possible INext elements. 
 * @author ythierry
 *
 */
public interface INext {

	/**
	 * Visitor API.
	 * @param vis a visitor
	 * @return whatever the visitor is computing 
	 */
	<T> T accept(NextVisitor<T> vis);

	/**
	 * Singleton for the Abort INext element.
	 */
	INext EMPTY = new Abort();

	/**
	 * Singleton for the Identity relation.
	 */
	INext ID = new Identity();
}

/** Kills any sequence it is part of, empty set of successors by Abort.
 *  Neutral element for Alternative.
 */
class Abort implements INext {

	@Override
	public <T> T accept(NextVisitor<T> vis) {
		return vis.visitAbort();
	}
}

/** Leaves any state it is applied to unchanged.
 * Neutral element for composition.
 *  
 */
class Identity implements INext {

	@Override
	public <T> T accept(NextVisitor<T> vis) {
		return vis.visitIdentity();
	}
}
