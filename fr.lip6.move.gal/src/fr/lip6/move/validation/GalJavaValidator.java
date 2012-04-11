package fr.lip6.move.validation;

import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.validation.Check;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.GalPackage;
import fr.lip6.move.gal.System;
import fr.lip6.move.gal.Variable;
 

/**
 * Classe de validation des éléments de la grammaire
 * @author steph 
 *
 */
public class GalJavaValidator extends AbstractGalJavaValidator {


	/*
	 * Champs statiques accessibles depuis les classes de test
	 */
	public static final int GAL_ERROR_NAME_EXISTS = 101 ; 
	
	@Check
	/**
	 * Checks if the declared size of the array is equal to the number of 
	 * items of the declared integer list 
	 */
	public void checkArrayNumber(ArrayPrefix array)
	{
		int size = array.getSize() ; 
		int nbDeclared = array.getValues().getValues().size() ; 
		int diff = size - nbDeclared ; 
		
		if(diff == 0) return ; 
		
		if(diff>0)
		{
			error("You need to add "+diff+" more values at initialization",
					GalPackage.Literals.ARRAY_PREFIX__NAME);
			
		}
		if(diff<0){
			error("You need to remove "+(-diff)+" values at initialization",
					GalPackage.Literals.ARRAY_PREFIX__NAME);
		}
	}
	
	/**
	 * Checks if the variable name is unique ! 
	 */
	@Check
	public void checkVariableUnicity(System system)
	{
		EList<Variable> listeVariables = system.getVariables(); 
		for(Variable var1 : listeVariables )
		{
			for(Variable var2 : listeVariables)
			{
				if(var1 != var2 && var2.getName().equals(var1.getName()))
				{
					//error("Exist always", GalPackage.Literals.VARIABLE__NAME, GAL_ERROR_NAME_EXISTS);
					
					error("Variable name already exists",       /* Error Message */ 
							var2,                               /* Object Source of Error */ 
							GalPackage.Literals.VARIABLE__NAME, /* wrong Feature */
							GAL_ERROR_NAME_EXISTS               /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
							);
					 
					return ; 
				}
					
			}
		}
	}

	@Check
	/**
	 * Check array name Unicity
	 */
	public void checkArrayUnicity(System system)
	{
		EList<ArrayPrefix> listeArrays = system.getArrays();
		for(ArrayPrefix var1 : listeArrays )
		{
			for(ArrayPrefix var2 : listeArrays)
			{
				if(var1 != var2 && var2.getName().equals(var1.getName()))
				{
					error("Array name already exists",       /* Error Message */ 
							var2,                               /* Object Source of Error */ 
							GalPackage.Literals.ARRAY_PREFIX__NAME, /* wrong Feature */
							GAL_ERROR_NAME_EXISTS               /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
							);
					return ; 
				}
					
			}
		}
	}
}