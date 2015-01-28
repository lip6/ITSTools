package fr.lip6.move.gal.instantiate;

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
	
	// implementors should provide the following standard methods 
	@Override
	public String toString();
	@Override
	public int hashCode() ;
	@Override
	public boolean equals (Object o);
}
