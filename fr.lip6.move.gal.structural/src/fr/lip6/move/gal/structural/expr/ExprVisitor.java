package fr.lip6.move.gal.structural.expr;

public interface ExprVisitor<T> {

	T visit(VarRef varRef);

	T visit(Constant constant);

	T visit(BinOp binOp);

	T visitBool(BoolConstant boolConstant);

	T visit(ParamRef paramRef);

	T visit(ArrayVarRef arrayVarRef);

	T visit(TransRef transRef);

	T visit(NaryOp naryOp);
	
	T visit(AtomicPropRef apRef);
}
