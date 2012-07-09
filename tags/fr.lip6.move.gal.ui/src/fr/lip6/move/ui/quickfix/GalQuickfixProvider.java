
package fr.lip6.move.ui.quickfix;

import java.util.HashMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.model.edit.ISemanticModification;
import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.validation.Issue;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.List;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.validation.GalJavaValidator;
import fr.lip6.move.gal.InitValues;




/**
 * Quick Fixer
 * @author steph
 *
 */
public class GalQuickfixProvider extends DefaultQuickfixProvider {

	
	// Cette table de hashage associera un "nom" à un "compteur", qui sera incrementé
	// a chaque conflit de nom
	private HashMap<String, Integer> compteurMap = new HashMap<String, Integer>();
	
	
	
	/**
	 * Renvoie un nouveau nom à partir de l'ancien. Le nom est généralement suivi d'un chiffre
	 * et est unique dans le système
	 */
	private String generateNewNameFromOld(String oldName)
	{
		String retour = "";
		int cpt = 1 ;
		
		// Si c'est la première fois qu'on fais un quickFix
		if(! compteurMap.containsKey(oldName))
		{
			compteurMap.put(oldName, 1);
			return oldName + 1 ; // Nom de la variable + 1
		}
		 
		// Sinon : boucler jusqu'a tomber sur un nom inexistant
		while(true)
		{
			cpt = cpt + compteurMap.get(oldName) ; 
			// Si ce nouveau nom existe
			if(GalJavaValidator.galElementsName.containsKey(oldName + cpt))
			{
				cpt ++ ; 
				continue ; 
			}
			// Ici le nom n'est pas encore utilisé.
			retour = oldName + cpt  ;
			compteurMap.put(oldName, cpt);
			return retour ; 
		}
	}
	
	
	
	
	
	
	
	/**
	 * Corrige le nom existant en lui rajoutant un chiffre devant.
	 */
	@Fix(GalJavaValidator.GAL_ERROR_NAME_EXISTS)
	public void suggestOtherVariableName(final Issue issue, IssueResolutionAcceptor acceptor)
	{
		acceptor.accept(issue, "Change name", "Choose other name", null, new ISemanticModification() {
			
			@Override
			public void apply(EObject element, IModificationContext context)
					throws Exception 
			{
				if(element instanceof Variable) 
				{
					Variable var = (Variable) element ; 
					var.setName(generateNewNameFromOld(var.getName()));
				}
				else if(element instanceof ArrayPrefix)
				{
					ArrayPrefix array = (ArrayPrefix) element ; 
					array.setName(generateNewNameFromOld(array.getName())) ; 
				}
				else if(element instanceof List)
				{
					List l = (List) element ; 
					l.setName(generateNewNameFromOld(l.getName()));
				}
				else if(element instanceof Transition)
				{
					Transition t = (Transition) element ; 
					t.setName(generateNewNameFromOld(t.getName()));
				}
				else 
				{
					System.err.println("Not yet implemented");
					System.out.println(element.getClass().getName());
				}
			}

			
		});
	}
	
	
	
	/**
	 * Complète les elements manquant d'un tableau
	 */
	@Fix(GalJavaValidator.GAL_ERROR_MISSING_ELEMENTS)
	public void missing_CompleteElements(final Issue issue, IssueResolutionAcceptor acceptor)
	{
		acceptor.accept(issue, "Complete missing elements", "Complete missing elements with zeros", null, new ISemanticModification() {
			
			@Override
			public void apply(EObject element, IModificationContext context)
					throws Exception 
			{
				if(element instanceof ArrayPrefix)
				{
					ArrayPrefix array = (ArrayPrefix) element ; 
					int nbElementsToAdd = GalJavaValidator.arrayMissingValues.get(array.getName()) ; 
					
					// Si null 
					if(array.getValues() == null)
					{
						GalFactory factory = GalFactory.eINSTANCE;
						InitValues v = factory.createInitValues();
						
						array.setValues(v) ;
						
						for(int i=0; i<nbElementsToAdd; i++)
						{
							array.getValues().getValues().add(0);
						}
					}
					else // tableau deja initialisé, mais pas en entier
					{
						if(nbElementsToAdd > 0)
						{
							for(int i=0; i<nbElementsToAdd; i++)
							{
								array.getValues().getValues().add(0);
							}
						}
					}
				}
				else 
				{
					System.err.println("Not yet implemented");
					System.out.println(element.getClass().getName());
				}
			}

			
		});
	}
	
	
	
	
	/**
	 * Change la taille au lieu de rajouter les elements au tableauu
	 */
	@Fix(GalJavaValidator.GAL_ERROR_MISSING_ELEMENTS)
	public void missing_ChangeArraySize(final Issue issue, IssueResolutionAcceptor acceptor)
	{
		acceptor.accept(issue, "Change array size", "Change array size", null, new ISemanticModification() {
			
			@Override
			public void apply(EObject element, IModificationContext context)
					throws Exception 
			{
				if(element instanceof ArrayPrefix)
				{
					ArrayPrefix array = (ArrayPrefix) element ; 
					
					// Si null 
					if(array.getValues() == null)
					{
						array.setSize(0);
					}
					else
					{
						array.setSize(array.getValues().getValues().size());
					}
				}
				else 
				{
					System.err.println("Not yet implemented");
					System.out.println(element.getClass().getName());
				}
			}
		});
	}
	
	
	
	
	
	
	
	/**
	 * Supprime les elements en trop d'un tableau
	 */
	@Fix(GalJavaValidator.GAL_ERROR_EXCESS_ITEMS)
	public void excess_RemoveItems(final Issue issue, IssueResolutionAcceptor acceptor)
	{
		acceptor.accept(issue, "Remove excess items", "Remove the excess last items", null, new ISemanticModification() {
			
			@Override
			public void apply(EObject element, IModificationContext context)
					throws Exception 
			{
				if(element instanceof ArrayPrefix)
				{
					ArrayPrefix array = (ArrayPrefix) element ; 
					int nbElementsToRemove =  -GalJavaValidator.arrayMissingValues.get(array.getName()) ; 
					int taille ; 
					for(int i=0; i<nbElementsToRemove; i++)
					{
						taille = array.getValues().getValues().size();
						array.getValues().getValues().remove(taille-1);
					}
				}
				else 
				{
					System.err.println("Not yet implemented");
					System.out.println(element.getClass().getName());
				}
			}
		});
	}

	
	
	
	/**
	 * Dans le cas d'un surplus, change la taille du tableau, au lieu de 
	 * supprimer les elements en trop
	 */
	@Fix(GalJavaValidator.GAL_ERROR_EXCESS_ITEMS)
	public void excess_ChangeArraySize(final Issue issue, IssueResolutionAcceptor acceptor)
	{
		acceptor.accept(issue, "Change array size", 
				               "Change array size, to keep the excess initialized items", 
				                null, new ISemanticModification() {
			
			@Override
			public void apply(EObject element, IModificationContext context)
					throws Exception 
			{
				if(element instanceof ArrayPrefix)
				{
					ArrayPrefix array = (ArrayPrefix) element ; 
					int nbElements =  array.getSize() - GalJavaValidator.arrayMissingValues.get(array.getName()) ;

					array.setSize(nbElements);
				}
				else 
				{
					System.err.println("Not yet implemented");
					System.out.println(element.getClass().getName());
				}
			}

			
		});
	}
}