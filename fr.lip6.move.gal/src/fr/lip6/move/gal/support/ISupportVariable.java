package fr.lip6.move.gal.support;

import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.VarDecl;

/**
 * An interface to store Variable references in a Support.
 * @author Yann
 *
 */
public interface ISupportVariable {

	/**
	 * Obtain the relevant variable 
	 * @return the variable of this SupportVariable.
	 */
	public VarDecl getVar();
	
	/**
	 * Obtain the initial value for this variable 
	 * @return the initialization for this variable.
	 */	
	public IntExpression getInitialValue();
	
	// implementors should provide the following standard methods 
	@Override
	public String toString();
	@Override
	public int hashCode() ;
	@Override
	public boolean equals (Object o);
}
