package fr.lip6.move.gal.itstools.launch.devTools;

import java.util.Set;

public interface Attribute<T> {

	public void setDefaultAttribute(T a);
	public Set<T> getAttributes();
}
