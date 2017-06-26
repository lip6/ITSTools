package fr.lip6.move.gal.application;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.itscl.modele.IRunner;
import fr.lip6.move.gal.itscl.modele.InterpreteObservable;
import fr.lip6.move.gal.itscl.modele.ItsInterpreter;

public abstract class AbstractRunner implements IRunner {

	protected Specification spec;
	protected Set<String> doneProps;
	protected InterpreteObservable inRunner;
	protected ItsInterpreter bufferWIO = new ItsInterpreter();
	
	public Specification getSpec() {
		return spec;
	}

	public void setInterprete(InterpreteObservable ob){
		this.inRunner=ob;
	}
	
	
	public Set<String> getDoneProps() {
		return doneProps;
	}
	
	public void setDoneProps(Set<String> d){
		doneProps=d;
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

	public Boolean taskDone() {
		for (Property prop : getSpec().getProperties()) {
			if (!doneProps.contains(prop.getName())) {
				// still some work to do
				return false;
			}
		}
		return true;
	}

}