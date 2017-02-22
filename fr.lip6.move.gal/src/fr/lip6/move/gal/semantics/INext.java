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

}