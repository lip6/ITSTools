package fr.lip6.move.gal.semantics;

import fr.lip6.move.gal.BooleanExpression;

public class Predicate implements INext {

	private BooleanExpression be;
	private VariableIndexer indexer;

	public Predicate (BooleanExpression be, VariableIndexer index) {
		this.be = be;
		this.indexer = index;
	}
	
	public BooleanExpression getGuard() {
		return be;
	}
	
	@Override
	public String toString() {
		return "{ " + ExpressionPrinter.print(be, "s",indexer) +" }";
	}
	
	public VariableIndexer getIndexer() {
		return indexer;
	}

	@Override
	public <T> T accept(NextVisitor<T> vis) {
		return vis.visit(this);
	}

}
