package fr.lip6.move.gal.semantics;

public interface NextVisitor<T> {	
	T visit (Assign ass);
	T visit (Predicate pred);
	T visit (Alternative alt);
	T visit (Sequence seq);
	T visitAbort ();
	T visitIdentity ();
}
