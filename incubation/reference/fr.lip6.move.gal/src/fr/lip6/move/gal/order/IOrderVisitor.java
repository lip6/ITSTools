package fr.lip6.move.gal.order;

public interface IOrderVisitor<T> {

	T visitComposite (CompositeGalOrder o);

	T visitVars(VarOrder varOrder);
}
