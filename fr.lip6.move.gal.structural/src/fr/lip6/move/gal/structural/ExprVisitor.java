package fr.lip6.move.gal.structural;

public interface ExprVisitor<T> {

	T visit(VarRef varRef);

	T visit(Constant constant);

	T visit(BinOp binOp);
	
}
