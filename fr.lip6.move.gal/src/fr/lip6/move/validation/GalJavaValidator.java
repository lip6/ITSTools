package fr.lip6.move.validation;
 

import java.util.HashMap;

import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.validation.Check;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.GalPackage;
import fr.lip6.move.gal.List;
import fr.lip6.move.gal.System;
import fr.lip6.move.gal.Transition;
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
	public static final String GAL_ERROR_NAME_EXISTS = "101" ; 
	public static final String GAL_ERROR_MISSING_ELEMENTS = "102";
	public static final String GAL_ERROR_TOO_MUCH_ELEMENTS= "103" ;
	public static final HashMap<String, Integer> arrayMissingValues = new HashMap<String, Integer>();

	
	@Check
	/**
	 * Checks if the declared size of the array is equal to the number of 
	 * items of the declared integer list 
	 */
	public void checkArrayNumber(ArrayPrefix array)
	{
		int size = array.getSize() ; 
	
		if(array.getValues() == null && size != 0)
		{
			String plurielElements = size > 1 ? " elements" : " element" ; 
			
			error("You must initialize array with " + array.getSize() + plurielElements,
					GalPackage.Literals.ARRAY_PREFIX__NAME /* wrong Feature */
					);
			return ; 
		}
		int nbDeclared = array.getValues().getValues().size() ; 
		int diff = size - nbDeclared ; 
		
		if(diff == 0) return ; 
		
		if(diff>0)
		{
			// Ajout dans la liste d'éléments manquants, pour faciliter le 
			// QuickFix
			arrayMissingValues.put(array.getName(), diff);
			
			error("You need to add "+diff+" more values at initialization",
					array,                               /* Object Source of Error */ 
					GalPackage.Literals.ARRAY_PREFIX__NAME, /* wrong Feature */
					GAL_ERROR_MISSING_ELEMENTS               /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
					);
			
			
		}
		else if(diff<0){
			arrayMissingValues.put(array.getName(), diff);
			
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
	
	
	@Check 
	public void checkListUnicity(System system)
	{
		EList<List> listeList = system.getLists();
		for(List var1 : listeList )
		{
			for(List var2 : listeList)
			{
				if(var1 != var2 /*&& var2.getName().equals(var1.getName())*/)
				{
					error("List name already exists",       /* Error Message */ 
							var2,                               /* Object Source of Error */ 
							GalPackage.Literals.LIST__NAME, /* wrong Feature */
							GAL_ERROR_NAME_EXISTS               /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
							);
					return ; 
				}
					
			}
		}
	}
	
	@Check 
	public void checkTransitionUnicity(System system)
	{
		EList<Transition> transList = system.getTransitions();
		for(Transition var1 : transList )
		{
			for(Transition var2 : transList)
			{
				if(var1 != var2 && var2.getName().equals(var1.getName()))
				{
					error("Transition name already exists",       /* Error Message */ 
							var2,                               /* Object Source of Error */ 
							GalPackage.Literals.TRANSITION__NAME, /* wrong Feature */
							GAL_ERROR_NAME_EXISTS               /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
							);
					return ; 
				}
					
			}
		}
	}
	
	/**
	 * Check unicity between all others variables in the system
	 * @param system
	 */
	@Check
	public void checkOtherTypesUnicity(System system)
	{
		EList<ArrayPrefix> arraylist = system.getArrays() ;
		EList<Variable>    varlist   = system.getVariables();
		EList<List>        listlist  = system.getLists() ; 
		EList<Transition>  transList = system.getTransitions();
		
		/*
		 * LL: lislList
		 * AP: ArrayPrefix
		 * VL: VariableList
		 */
		// With ArrayPrefix
		checkUnicityFromEList_AP_VL(arraylist, varlist);
		checkUnicityFromEList_AP_LL(arraylist, listlist);
		checkUnicityFromEList_AP_TR(arraylist, transList);
		
		// With ListList
		
		checkUnicityFromEList_LL_VL(listlist, varlist);
		checkUnicityFromEList_LL_TR(listlist, transList);
		
		// with VL
		checkUnicityFromEList_VL_TR(varlist, transList);
		
	}



	private void checkUnicityFromEList_VL_TR(EList<Variable> varlist,
			EList<Transition> transList) {
		if(varlist == null || transList == null)	return ;

		for(Variable v : varlist)
		{
			for(Transition t : transList)
			{
				if(t.getName().equals(v.getName()))
				{
					error("This name is already used",       /* Error Message */ 
							t,                               /* Object Source of Error */ 
							GalPackage.Literals.TRANSITION__NAME, /* wrong Feature */
							GAL_ERROR_NAME_EXISTS               /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
							);
					return ; 
				}
			}
		}
	}

	private void checkUnicityFromEList_LL_TR(EList<List> listlist,
			EList<Transition> transList) {

		if(listlist == null || transList == null)	return ;

		for(List l : listlist)
		{
			for(Transition t : transList)
			{
				if(t.getName().equals(l.getName()))
				{
					error("This name is already used",       /* Error Message */ 
							t,                               /* Object Source of Error */ 
							GalPackage.Literals.TRANSITION__NAME, /* wrong Feature */
							GAL_ERROR_NAME_EXISTS               /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
							);
					return ; 
				}
			}
		}
	}

	private void checkUnicityFromEList_AP_TR(EList<ArrayPrefix> arraylist,
			EList<Transition> transList) {

		if(arraylist == null || transList == null)	return ;

		for(ArrayPrefix ap : arraylist)
		{
			for(Transition t : transList)
			{
				if(t.getName().equals(ap.getName()))
				{
					error("This name is already used",       /* Error Message */ 
							t,                               /* Object Source of Error */ 
							GalPackage.Literals.TRANSITION__NAME, /* wrong Feature */
							GAL_ERROR_NAME_EXISTS               /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
							);
					return ; 
				}
			}
		}
	}

	/**
	 * Check unicity between names of lists and variables
	 * @param listlist
	 * @param varlist
	 */
	private void checkUnicityFromEList_LL_VL(EList<List> listlist,EList<Variable> varlist) 
	{
		if(listlist == null || varlist == null)	return ;

		for(List ap : listlist)
		{
			for(Variable v : varlist)
			{
				if(v.getName().equals(ap.getName()))
				{
					error("This name is already used",       /* Error Message */ 
							v,                               /* Object Source of Error */ 
							GalPackage.Literals.VARIABLE__NAME, /* wrong Feature */
							GAL_ERROR_NAME_EXISTS               /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
							);
					return ; 
				}
			}
		}
	}

	/**
	 * Check unicity between Arrays and Lists
	 * @param arraylist
	 * @param listlist
	 */
	private void checkUnicityFromEList_AP_LL(EList<ArrayPrefix> arraylist,
			EList<List> listlist) 
	{
		
		if(arraylist == null || listlist == null) return ;
		for(ArrayPrefix ap : arraylist)
		{
			for(List v : listlist)
			{
				if(v.getName().equals(ap.getName()))
				{
					error("This name is already used",       /* Error Message */ 
							v,                               /* Object Source of Error */ 
							GalPackage.Literals.VARIABLE__NAME, /* wrong Feature */
							GAL_ERROR_NAME_EXISTS               /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
							);
					return ; 
				}
			}
		}
	}

	/**
	 * Unicity between Arrays and Variables
	 * @param apList
	 * @param vList
	 */
	private void checkUnicityFromEList_AP_VL(EList<ArrayPrefix> apList,
			EList<Variable> vList) 
	{
		if(apList == null || vList == null)	return ;
		
		for(ArrayPrefix ap : apList)
		{
			for(Variable v : vList)
			{
				if(v.getName().equals(ap.getName()))
				{
					error("This name is already used",       /* Error Message */ 
							v,                               /* Object Source of Error */ 
							GalPackage.Literals.VARIABLE__NAME, /* wrong Feature */
							GAL_ERROR_NAME_EXISTS               /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
							);
					return ; 
				}
			}
		}
	}
}