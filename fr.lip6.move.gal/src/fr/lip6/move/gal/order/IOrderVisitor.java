package fr.lip6.move.gal.order;

public interface IOrderVisitor<T> {

	T visitComposite (CompositeGalOrder o);

	void visitVars(VarOrder varOrder);
}
