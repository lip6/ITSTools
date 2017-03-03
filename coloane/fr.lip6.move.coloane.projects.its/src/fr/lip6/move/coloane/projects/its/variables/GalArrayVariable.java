package fr.lip6.move.coloane.projects.its.variables;

import fr.lip6.move.gal.ArrayPrefix;


public class GalArrayVariable extends LeafModelVariable {
	ArrayPrefix array;
	int index;
	
	public GalArrayVariable(ArrayPrefix array, int index) {
		super(buildId(array, index));
		this.index = index;
		this.array = array;
		setId(getName());		
	}

	private static String buildId (ArrayPrefix array, int index) {
		return array.getName() + "[" + index + "]";
	}
	
	public GalArrayVariable(GalArrayVariable other) {
		super(other);
		this.array = other.array;
		this.index = other.index;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public final String getDescription() {
		return "An array element" ;// representing a simple variable of a GAL System";
	}

	public GalArrayVariable clone () {
		return new GalArrayVariable(this);
	}
}