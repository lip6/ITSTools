package fr.lip6.move.gal.semantics;

public interface LeafNextVisitor<T> extends NextVisitor<T> {

	@Override
	default T visit(Alternative alt) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default T visit(Sequence seq) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default T visitIdentity() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default T visitAbort() {
		throw new UnsupportedOperationException();
	}
}
