package fr.lip6.move.validation;
 

import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.validation.Check;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.GalPackage;
import fr.lip6.move.gal.List;
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
	public static final String GAL_ERROR_NAME_EXISTS = "101" ; 

	
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
					
					warning("Variable name already exists",       /* Error Message */ 
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
	
	//@Check // Bug Xtext: ne marche pas :-/
//	public void checkListUnicity(System system)
//	{
//		EList<List> listeList = system.getLists();
//		for(List var1 : listeList )
//		{
//			for(List var2 : listeList)
//			{
//				if(var1 != var2 /*&& var2.getName().equals(var1.getName())*/)
//				{
//					error("List name already exists",       /* Error Message */ 
//							var2,                               /* Object Source of Error */ 
//							GalPackage.Literals.ARRAY_PREFIX__NAME, /* wrong Feature */
//							GAL_ERROR_NAME_EXISTS               /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
//							);
//					return ; 
//				}
//					
//			}
//		}
//	}
	
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
		
		/*
		 * LL: lislList
		 * AP: ArrayPrefix
		 * VL: VariableList
		 */
		checkUnicityFromElist_LL(listlist);
		checkUnicityFromEList_AP_VL(arraylist, varlist);
		checkUnicityFromEList_AP_LL(arraylist, listlist);
		checkUnicityFromEList_LL_VL(listlist, varlist);
		
	}

	//@Bug de Xtext: La séparation en une méthode à part avec l'annotation @Check (pour la liste)
	// ne marche pas (chez moi) pour la vérification de l'unicité de la liste
	// Je l'ai donc fait en une simple méthode, appelée par la méthode de vérification globale
	/**
	 * Check unicity of list variables in the system
	 * @param listlist
	 */
	private void checkUnicityFromElist_LL(EList<List> listlist) 
	{
		if(listlist == null ) return ; 
		
		for(List l : listlist) {
			for(List l2 : listlist) {
				if(l != l2 && l.getName().equals(l2.getName()))
				{
					error("List name already exists",         /* Error Message */ 
							l2,                               /* Object Source of Error */ 
							GalPackage.Literals.LIST__NAME,   /* wrong Feature */
							GAL_ERROR_NAME_EXISTS             /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
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