
package fr.lip6.move.ui.quickfix;


import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.model.edit.ISemanticModification;
import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.validation.Issue;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.List;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.validation.GalJavaValidator;
import fr.lip6.move.gal.InitValues;

public class GalQuickfixProvider extends DefaultQuickfixProvider {
	
		
	/**
	 * Corrects the existing name by adding back a number in front of.
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
					var.setName(var.getName()+"_1");
				}
				else if(element instanceof ArrayPrefix)
				{
					ArrayPrefix array = (ArrayPrefix) element ; 
					array.setName(array.getName()+"_1") ; 
				}
				else if(element instanceof List)
				{
					List l = (List) element ; 
					l.setName(l.getName()+"_1");
				}
				else if(element instanceof Transition)
				{
					Transition t = (Transition) element ; 
					t.setName(t.getName()+"_1");
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
	 * Complete the elements missing from an array
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
					int nbElementsToAdd = array.getSize() - array.getValues().getValues().size() ; 
					
					if(array.getValues() == null)
					{
						GalFactory factory = GalFactory.eINSTANCE;
						InitValues v = factory.createInitValues();
						
						array.setValues(v) ;
						
						for(int i=0; i<nbElementsToAdd; i++)
						{
							Constant zero = GalFactory.eINSTANCE.createConstant();
							zero.setValue(0);
							array.getValues().getValues().add(zero);
						}
					}
					else // Table already initialized, but not entirely
					{
						if(nbElementsToAdd > 0)
						{
							for(int i=0; i<nbElementsToAdd; i++)
							{
								Constant zero = GalFactory.eINSTANCE.createConstant();
								zero.setValue(0);
								array.getValues().getValues().add(zero);
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
	 * Change the size instead of adding the elements in Table...
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
	 * Remove the excess elements of an array
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
					int nbElementsToRemove =   array.getValues().getValues().size() - array.getSize() ; 
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
	 * In the case of a surplus, changes the size of the array, 
	 * instead of deleting the excess elements
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
					int nbElements =  array.getValues().getValues().size() ;

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