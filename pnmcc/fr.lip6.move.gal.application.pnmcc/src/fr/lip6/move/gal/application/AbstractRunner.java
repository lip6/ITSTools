package fr.lip6.move.gal.application;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.itscl.interpreter.IInterpreteObservable;
import fr.lip6.move.gal.itscl.modele.IRunner;

public abstract class AbstractRunner implements IRunner {

	protected Specification spec;
	protected Set<String> doneProps;
	protected IInterpreteObservable inRunner;

	public Specification getSpec() {
		return spec;
	}

	public void configureInterpreter(IInterpreteObservable ob) {
		this.inRunner = ob;
	}

	public void configure(Specification z3Spec, Set<String> doneProps) throws IOException {
		this.spec = z3Spec;
		this.doneProps = doneProps;
	}

	public void configure(Specification spec) {
		try {
			configure(spec, ConcurrentHashMap.newKeySet());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract void solve();

	public abstract Boolean taskDone();

}