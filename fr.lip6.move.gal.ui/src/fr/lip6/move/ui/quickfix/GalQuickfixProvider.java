
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
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.validation.GalJavaValidator;

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
//				else if(element instanceof List)
//				{
//					List l = (List) element ; 
//					l.setName(l.getName()+"_1");
//				}
				else if(element instanceof Transition)
				{
					Transition t = (Transition) element ; 
					t.setName(t.getName()+"_1");
				}
				else if(element instanceof Property)
				{
					Property p = (Property) element ; 
					p.setName(p.getName()+"_1");
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
				if(element instanceof ArrayPrefix) {
					ArrayPrefix array = (ArrayPrefix) element ;
					if (array.getSize() instanceof Constant) {
						Constant cte = (Constant) array.getSize();

						int nbElementsToAdd = cte.getValue() - array.getValues().size() ; 
						
						if(array.getValues() == null) {
							for(int i=0; i<nbElementsToAdd; i++) {
								array.getValues().add(GF2.constant(0));
							}
						} else {
							// Table already initialized, but not entirely
							if(nbElementsToAdd > 0) {
								for(int i=0; i<nbElementsToAdd; i++) {
									array.getValues().add(GF2.constant(0));
								}
							}
						}
					}
				} else {
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
						array.setSize(GF2.constant(0));
					}
					else
					{
						array.setSize(GF2.constant(array.getValues().size()));
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
	public void excess_RemoveItems(final Issue issue,
			IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, "Remove excess items",
				"Remove the excess last items", null,
				new ISemanticModification() {

					@Override
					public void apply(EObject element,
							IModificationContext context) throws Exception {
						if (element instanceof ArrayPrefix) {
							ArrayPrefix array = (ArrayPrefix) element;
							if (array.getSize() instanceof Constant) {
								Constant cte = (Constant) array.getSize();

								int nbElementsToRemove = array.getValues()
										.size() - cte.getValue();
								int taille;
								for (int i = 0; i < nbElementsToRemove; i++) {
									taille = array.getValues().size();
									array.getValues().remove(taille - 1);
								}
							}
						} else {
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
					int nbElements =  array.getValues().size() ;

					array.setSize(GF2.constant(nbElements));
				}
				else 
				{
					System.err.println("Not yet implemented");
					System.out.println(element.getClass().getName());
				}
			}

			
		});
	}
	
	@Fix(GalJavaValidator.GAL_ERROR_MISSING_MAIN)
	public void missing_AddMainDeclaration(final Issue issue, IssueResolutionAcceptor acceptor)
	{
		acceptor.accept(issue, "Add Main declaration", 
				"A main declaration is required if your specification includes more than one type.", 
				null, new ISemanticModification() {

			@Override
			public void apply(EObject element, IModificationContext context)
					throws Exception 
					{
				if(element instanceof Specification)
				{
					Specification spec = (Specification) element ; 
					spec.setMain(spec.getTypes().get(spec.getTypes().size()-1));				
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