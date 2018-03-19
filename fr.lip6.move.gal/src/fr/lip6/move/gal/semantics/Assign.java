package fr.lip6.move.gal.semantics;

import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.UniqueTable;

/**
 * A GAL assignment, wrapped into an INext instance.
 * This class carries a VariableIndexer, to allow for several instances of a type or within composite definitions.
 * @author ythierry
 *
 */
public class Assign implements INext {

	private Assignment ass;
	private VariableIndexer indexer;

	
	private static UniqueTable<Assign> unique = new UniqueTable<>();
	public static INext ass (Assignment ass, VariableIndexer index) {
		Assign as = new Assign(ass, index);
		return unique.canonical(as);		
	}
	
	private Assign(Assignment ass, VariableIndexer index) {
		this.ass = ass;
		this.indexer = index;
	}

	public VariableIndexer getIndexer() {
		return indexer;
	}
	
	public Assignment getAssignment() {
		return ass;
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
			stringRep = ExpressionPrinter.print(ass, "s", indexer).replaceAll("[\\s\\n;]*", "");
		return stringRep;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this) 
			return true;
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
