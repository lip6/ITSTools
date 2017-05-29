package fr.lip6.move.gal.itstools.launch.devTools;

import java.util.HashSet;
import java.util.Set;

public class BooleanAttributes implements Attribute<Boolean> {

	private Boolean defautValue;

	
	@Override
	public Set<Boolean> getAttributes() {
		Set<Boolean> s = new HashSet<Boolean>();
		s.add(true); s.add(false);
		return s;
	}

	@Override
	public void setDefaultAttribute(Boolean a) {
		defautValue = a;
	}

}
