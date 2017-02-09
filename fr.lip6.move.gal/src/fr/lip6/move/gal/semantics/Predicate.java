package fr.lip6.move.gal.semantics;

import fr.lip6.move.gal.BooleanExpression;

/**
 * A Predicate is a filter based on a Boolean expression, that only accepts states that satisfy it, similar to a GAL guard element.
 * The Predicate uses a variable indexer to deal with multiple instances within a composite.
 * @author ythierry
 *
 */
public class Predicate implements INext {

	private BooleanExpression be;
	private VariableIndexer indexer;

	public Predicate(BooleanExpression be, VariableIndexer index) {
		this.be = be;
		this.indexer = index;
	}

	public BooleanExpression getGuard() {
		return be;
	}

	@Override
	public String toString() {
		return "{ " + ExpressionPrinter.print(be, "s", indexer) + " }";
	}

	public VariableIndexer getIndexer() {
		return indexer;
	}

	@Override
	public <T> T accept(NextVisitor<T> vis) {
		return vis.visit(this);
	}

}
