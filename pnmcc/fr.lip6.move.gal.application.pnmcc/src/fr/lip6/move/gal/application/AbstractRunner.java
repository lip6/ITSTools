package fr.lip6.move.gal.application;

import java.io.IOException;

import java.util.Set;

import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.itscl.modele.IListener;
import fr.lip6.move.gal.itscl.modele.IRunner;

public abstract class AbstractRunner implements IRunner {

	protected Specification spec;
	protected Set<String> doneProps;
	protected IListener listener;
	
	public AbstractRunner() {
		super();
	}
	
	public void setListener(IListener l){
		this.listener=l;
	}

	public void configure(Specification z3Spec, Set<String> doneProps) throws IOException {
		this.spec = z3Spec;
		this.doneProps = doneProps;
	}

	public abstract void solve();

	public Specification getSpec() {
		return spec;
	}

	public Set<String> getDoneProps() {
		return doneProps;
	}

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