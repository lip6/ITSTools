package fr.lip6.move.gal.application;


import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.itscl.modele.IListener;
import fr.lip6.move.gal.itscl.modele.IRunner;
import fr.lip6.move.gal.itscl.modele.Problem;


public abstract class AbstractRunner extends Problem implements IRunner {
	
	protected IListener listener;
	
	public AbstractRunner() {
		super();
	}
	
	public void setListener(IListener l){
		this.listener=l;
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