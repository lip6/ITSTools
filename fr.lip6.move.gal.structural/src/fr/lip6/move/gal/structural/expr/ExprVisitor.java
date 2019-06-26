package fr.lip6.move.gal.structural.expr;

public interface ExprVisitor<T> {

	T visit(VarRef varRef);

	T visit(Constant constant);

	T visit(BinOp binOp);
	
}
