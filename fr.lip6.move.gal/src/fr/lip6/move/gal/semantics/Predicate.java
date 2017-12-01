package fr.lip6.move.gal.semantics;

import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.UniqueTable;

/**
 * A Predicate is a filter based on a Boolean expression, that only accepts states that satisfy it, similar to a GAL guard element.
 * The Predicate uses a variable indexer to deal with multiple instances within a composite.
 * @author ythierry
 *
 */
public class Predicate implements INext {

	private BooleanExpression be;
	private VariableIndexer indexer;

	private Predicate(BooleanExpression be, VariableIndexer index) {
		this.be = be;
		this.indexer = index;
	}
	
	private static UniqueTable<Predicate> unique = new UniqueTable<>();
	public static INext pred (BooleanExpression be, VariableIndexer index) {
		Predicate as = new Predicate(be, index);
		return unique.canonical(as);		
	}

	public BooleanExpression getGuard() {
		return be;
	}

	public VariableIndexer getIndexer() {
		return indexer;
	}

	@Override
	public <T> T accept(NextVisitor<T> vis) {
		return vis.visit(this);
	}

	
	/** String rep is cached so it can be used for hashcode and equals. */
	private String stringRep = null;
	@Override
	public String toString() {
		if (stringRep == null)
			stringRep = "{ " + ExpressionPrinter.print(be, "s", indexer) + " }";
		return stringRep;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (! (obj instanceof Assign) ) {
			return false;
		}
		return toString().equals(obj.toString());
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
}
