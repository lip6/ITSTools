package fr.lip6.move.coloane.projects.its.variables;

import fr.lip6.move.gal.Variable;



public class GalVariable extends LeafModelVariable {
	Variable variable;

	public GalVariable(fr.lip6.move.gal.Variable variable) {
		super(variable.getName());
		setId(variable.getName());
		this.variable = variable;
	}

	public GalVariable(GalVariable other) {
		super(other);
		this.variable = other.variable;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public final String getDescription() {
		return "An integer" ;// representing a simple variable of a GAL System";
	}

	public GalVariable clone () {
		return new GalVariable(this);
	}
}