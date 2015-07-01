package fr.lip6.move.validation;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.validation.Check;

import fr.lip6.move.gal.AbstractParameter;
import fr.lip6.move.gal.ArrayInstanceDeclaration;
import fr.lip6.move.gal.CompositeTypeDeclaration;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.InstanceCall;
import fr.lip6.move.gal.InstanceDecl;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.ConstParameter;
import fr.lip6.move.gal.For;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GalPackage;
import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.ParamRef;
import fr.lip6.move.gal.Parameter;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.QualifiedReference;
import fr.lip6.move.gal.SelfCall;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Synchronization;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.scoping.GalScopeProvider;


/** 
 * Validation class for grammar elements
 */
public class GalJavaValidator extends AbstractGalJavaValidator {


	/*
	 * Static fields used in quickfix class in user interface
	 */
	// Error codes
	public static final String GAL_ERROR_NAME_EXISTS      = "101" ; 
	public static final String GAL_ERROR_MISSING_ELEMENTS = "102";
	public static final String GAL_ERROR_EXCESS_ITEMS     = "103";
	public static final String GAL_ERROR_CIRCULAR_CALLS     = "104";
	public static final String GAL_ERROR_PARAM_SCOPE     = "105";
	public static final String GAL_ERROR_UNUSED = "106";
	public static final String GAL_ERROR_MISSING_MAIN = "107";
	private static final String GAL_ERROR_ARRAY_TYPE = "108";
	private static final String GAL_ERROR_ARRAY_NOINDEX = "109";
	private static final String GAL_ERROR_INSTANCE_NOQUAL = "110";
	private static final String GAL_ERROR_BAD_PARAM_CALL = "111";
	private static final String GAL_WARN_LABELNAME = "112";
	private static final String GAL_ERROR_PARAM_ON_EMPTY_LABEL = "113"	;


	@Check
	public void checkArrayIndex(VariableReference pr) {
		if (pr.getIndex() == null) {
			return;
		}
		if (pr.getIndex() instanceof Constant){
			int index  = ((Constant) pr.getIndex()).getValue();
			if (index < 0) {
				error("Array index out of bounds (negative value).", /* Error Message */ 
						pr.getIndex(),             /* Object Source of Error */ 
						GalPackage.Literals.VARIABLE_REFERENCE__INDEX,                /* wrong Feature */
						GAL_ERROR_ARRAY_TYPE /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
						);					
				return;
			}
			if (pr.getRef() instanceof ArrayPrefix) {
				ArrayPrefix ap = (ArrayPrefix) pr.getRef();
				if (ap.getSize() instanceof Constant) {
					Constant cte = (Constant) ap.getSize();
					if (cte.getValue() <= index) {
						error("Array index out of bounds.", /* Error Message */ 
								pr,             /* Object Source of Error */ 
								GalPackage.Literals.VARIABLE_REFERENCE__INDEX,                /* wrong Feature */
								GAL_ERROR_ARRAY_TYPE /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
								);					
					}
				}
			} else if (pr.getRef() instanceof ArrayInstanceDeclaration) {
				ArrayInstanceDeclaration ap = (ArrayInstanceDeclaration) pr.getRef();
				if (ap.getSize() instanceof Constant) {
					Constant cte = (Constant) ap.getSize();
					if (cte.getValue() <= index) {
						error("Array index out of bounds.", /* Error Message */ 
								pr,             /* Object Source of Error */ 
								GalPackage.Literals.VARIABLE_REFERENCE__INDEX,                /* wrong Feature */
								GAL_ERROR_ARRAY_TYPE /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
								);					
					}
				}
			}

		}
	}



	@Check
	public void checkArrayType(VariableReference pr) {
		if (pr.getIndex() != null) {
			if (!(pr.getRef() instanceof ArrayPrefix  || pr.getRef() instanceof ArrayInstanceDeclaration)) {
				error("Variable "+ pr.getRef().getName() +" is not an array.", /* Error Message */ 
						pr.getRef(),             /* Object Source of Error */ 
						GalPackage.Literals.VARIABLE_REFERENCE__REF,                /* wrong Feature */
						GAL_ERROR_ARRAY_TYPE /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
						);

			}
		}
	}

	@Check
	public void checkVariableRefType(VariableReference ref) {
		if (ref.getRef() instanceof ArrayPrefix || ref.getRef() instanceof ArrayInstanceDeclaration) {
			// make sure we are in context of ArrayReference.array, the only legal place such refs can occur.
			if ( ref.getIndex() != null) {
				return;
			}
			error("Cannot make raw reference to the array "+ ref.getRef().getName() +", please specify an index, e.g. "+ref.getRef().getName() + "[0].", /* Error Message */ 
					ref,             /* Object Source of Error */ 
					GalPackage.Literals.VARIABLE_REFERENCE__REF,                /* wrong Feature */
					GAL_ERROR_ARRAY_NOINDEX /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
					);
		} else if (ref.getRef() instanceof InstanceDecl) {
			// make sure we are in context of QualifiedRef.qualifier, the only legal place such refs can occur.
			if ( ref.eContainer() instanceof QualifiedReference && ref.eContainingFeature().getName().equals("qualifier")
					|| ref.eContainer() instanceof InstanceCall && ref.eContainingFeature().getName().equals("instance") ) {
				return;
			}
			error("Cannot make raw reference to the instance "+ ref.getRef().getName() +", please specify the variable to access, e.g. "+ref.getRef().getName() + ":nestedVar." , /* Error Message */ 
					ref,             /* Object Source of Error */ 
					GalPackage.Literals.VARIABLE_REFERENCE__REF,                /* wrong Feature */
					GAL_ERROR_INSTANCE_NOQUAL /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
					);

		}



	}

	@Check
	/**
	 * Check uniqueness between all Gal element name
	 */
	public void checkNameUnicity(Property ap)
	{
		Specification spec = (Specification) ap.eContainer();
		
		for (Property p2 : spec.getProperties()) {
			if (p2!=ap && ap.getName().equals(p2.getName())) {
				error("This name is already used for another property", /* Error Message */ 
						ap,             /* Object Source of Error */ 
						GalPackage.Literals.PROPERTY__NAME,                /* wrong Feature */
						GAL_ERROR_NAME_EXISTS      /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
						);
				
			}
		}
	}

	

	@Check
	/**
	 * Check uniqueness between all Gal element name
	 */
	public void checkNameUnicity(ArrayPrefix ap)
	{
		GALTypeDeclaration s = GalScopeProvider.getSystem(ap);
		for (Variable var : s.getVariables()) {
			if (ap.getName().equals(var.getName())) {
				error("This name is already used for another variable", /* Error Message */ 
						ap,             /* Object Source of Error */ 
						GalPackage.Literals.NAMED_DECLARATION__NAME,                /* wrong Feature */
						GAL_ERROR_NAME_EXISTS      /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
						);

			}
		}
		for (ArrayPrefix ap2 : s.getArrays()) {
			if (ap != ap2 && ap.getName().equals(ap2.getName())) {
				error("This name is already used for an array", /* Error Message */ 
						ap,             /* Object Source of Error */ 
						GalPackage.Literals.NAMED_DECLARATION__NAME,                /* wrong Feature */
						GAL_ERROR_NAME_EXISTS      /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
						);

			}
		}

	}

	@Check
	/**
	 * Check uniqueness between all Gal element name
	 */
	public void checkNameUnicity(Transition t)
	{
		GALTypeDeclaration s = GalScopeProvider.getSystem(t);
		for (Transition var : s.getTransitions()) {
			if (t != var && t.getName().equals(var.getName())) {
				error("This name is already used for another transition", /* Error Message */ 
						t,             /* Object Source of Error */ 
						GalPackage.Literals.EVENT__NAME,                /* wrong Feature */
						GAL_ERROR_NAME_EXISTS      /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
						);

			}
		}
	}

	@Check
	/**
	 * Check uniqueness between all Gal element name
	 */
	public void checkNameUnicity(Synchronization t)
	{
		if (t.eContainer() instanceof CompositeTypeDeclaration) {
			CompositeTypeDeclaration ctd = (CompositeTypeDeclaration) t.eContainer();

			for (Synchronization s : ctd.getSynchronizations()) {
				if (s != t && s.getName().equals(t.getName())) {
					error("This name is already used for another synchronization", /* Error Message */ 
							t,             /* Object Source of Error */ 
							GalPackage.Literals.EVENT__NAME,                /* wrong Feature */
							GAL_ERROR_NAME_EXISTS      /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
							);					
				}
			}

		}
	}


	@Check
	/**
	 * Check uniqueness between all Gal element name
	 */
	public void checkNameUnicity(Variable v)
	{
		GALTypeDeclaration s = GalScopeProvider.getSystem(v);
		for (Variable var : s.getVariables()) {
			if (v != var && v.getName().equals(var.getName())) {
				error("This name is already used for another variable", /* Error Message */ 
						v,             /* Object Source of Error */ 
						GalPackage.Literals.NAMED_DECLARATION__NAME,                /* wrong Feature */
						GAL_ERROR_NAME_EXISTS      /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
						);

			}
		}
		for (ArrayPrefix ap : s.getArrays()) {
			if (ap.getName().equals(v.getName())) {
				error("This name is already used for an array", /* Error Message */ 
						v,             /* Object Source of Error */ 
						GalPackage.Literals.NAMED_DECLARATION__NAME,                /* wrong Feature */
						GAL_ERROR_NAME_EXISTS      /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
						);

			}
		}
	}

	@Check
	public void checkParamNames (AbstractParameter p) {

		// Skip a level, since the container is a For that we don't need to test.
		EObject parent = p.eContainer();

		// We should break out of here with the return on parent is a System case
		while (parent != null) {
			if (parent instanceof For) {
				if (((For) parent).getParam() != p && ((For) parent).getParam().getName().equals(p.getName())) {
					error("This name is already used to designate another  parameter.", /* Error Message */ 
							p,             /* Object Source of Error */ 
							GalPackage.Literals.ABSTRACT_PARAMETER__NAME,                /* wrong Feature */
							GAL_ERROR_NAME_EXISTS      /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
							);
				}
			} else if (parent instanceof Transition) {
				Transition t = (Transition) parent;
				for (Parameter p2 : t.getParams()) {
					if (p2 != p  && p2.getName().equals(p.getName())) {
						error("This name is already used to a transition parameter.", /* Error Message */ 
								p,             /* Object Source of Error */ 
								GalPackage.Literals.ABSTRACT_PARAMETER__NAME,                /* wrong Feature */
								GAL_ERROR_NAME_EXISTS      /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
								);				
					}
				}
			} else if (parent instanceof Synchronization) {
				Synchronization t = (Synchronization) parent;
				for (Parameter p2 : t.getParams()) {
					if (p2 != p  && p2.getName().equals(p.getName())) {
						error("This name is already used to a transition parameter.", /* Error Message */ 
								p,             /* Object Source of Error */ 
								GalPackage.Literals.ABSTRACT_PARAMETER__NAME,                /* wrong Feature */
								GAL_ERROR_NAME_EXISTS      /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
								);				
					}
				}
			} else if (parent instanceof GALTypeDeclaration) {
				GALTypeDeclaration system = (GALTypeDeclaration) parent;
				for (ConstParameter cp : system.getParams()) {
					if (cp != p && cp.getName().equals(p.getName())) {
						error("This name is already used to designate a type parameter.", /* Error Message */ 
								p,             /* Object Source of Error */ 
								GalPackage.Literals.ABSTRACT_PARAMETER__NAME,                /* wrong Feature */
								GAL_ERROR_NAME_EXISTS      /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
								);								
					}
				}
			} else if (parent instanceof CompositeTypeDeclaration) {
				CompositeTypeDeclaration system = (CompositeTypeDeclaration) parent;
				for (ConstParameter cp : system.getParams()) {
					if (cp != p && cp.getName().equals(p.getName())) {
						error("This name is already used to designate a type parameter.", /* Error Message */ 
								p,             /* Object Source of Error */ 
								GalPackage.Literals.ABSTRACT_PARAMETER__NAME,                /* wrong Feature */
								GAL_ERROR_NAME_EXISTS      /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
								);								
					}
				}
			} else if (parent instanceof Specification) {
				Specification spec = (Specification) parent;
				for (ConstParameter cp : spec.getParams()) {
					if (cp != p && cp.getName().equals(p.getName())) {
						error("This name is already used to designate a type parameter.", /* Error Message */ 
								p,             /* Object Source of Error */ 
								GalPackage.Literals.ABSTRACT_PARAMETER__NAME,                /* wrong Feature */
								GAL_ERROR_NAME_EXISTS      /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
								);								
					}
				}
				return;
			}
			parent = parent.eContainer();
		}
	}




	@Check
	public void checkParamUSage (Parameter p) {
		for (TreeIterator<EObject> it= p.eContainer().eAllContents(); it.hasNext(); ) {
			EObject obj = it.next();
			if (obj instanceof ParamRef) {
				ParamRef pr = (ParamRef) obj;
				if (pr.getRefParam() == p) {
					return;
				}
			} else if (obj instanceof SelfCall) {
				if (((SelfCall) obj).getLabel().getName().contains(p.getName())) {
					return;
				}
			} else if (obj instanceof InstanceCall) {
				if (((InstanceCall) obj).getLabel().getName().contains(p.getName())) {
					return;
				}
			} else if (obj instanceof Label) {
				if (((Label) obj).getName().contains(p.getName())) {
					return;
				}
			}
		}

		warning("This parameter is never used !", /* Error Message */ 
				p,             /* Object Source of Error */ 
				GalPackage.Literals.ABSTRACT_PARAMETER__NAME,                /* wrong Feature */
				GAL_ERROR_UNUSED      /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
				);				

	}


	@Check
	/**
	 * Verify that there are no circular references to labels, i.e. call graphs form a strict DAG.
	 * @param s the full system
	 */
	public void checkNoCircularCalls (GALTypeDeclaration s) {

		// First scan transitions to build a map "label" to set of transitions bearing it.
		Map<String,Set<Transition>> labMap = buildLabMap(s);

		// Now search for call instances in the system
		for (Transition t : s.getTransitions()) {
			for (Iterator<EObject> it = t.eAllContents() ; it.hasNext() ; /*NOP*/) {
				EObject cont = it.next();
				if (cont instanceof SelfCall) {
					SelfCall call = (SelfCall) cont;
					Set<Transition> tosee = new HashSet<Transition>();
					getDependencies(labMap, call.getLabel().getName(), tosee);
					if (tosee.contains(t)) {
						error("There are circular calls between actions of your system", /* Error Message */ 
								call,             /* Object Source of Error */ 
								GalPackage.Literals.SELF_CALL__LABEL,                /* wrong Feature */
								GAL_ERROR_CIRCULAR_CALLS      /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
								);
					}
				}
			}
		}		

	}

	private Map<String, Set<Transition>> buildLabMap(GALTypeDeclaration s) {
		Map<String,Set<Transition>> labMap = new HashMap<String, Set<Transition>>();
		for (Transition t : s.getTransitions()) {
			if (t.getLabel()!=null) {
				String lab =t.getLabel().getName();
				Set<Transition> toadd = labMap.get(lab);
				if (toadd == null) {
					toadd = new HashSet<Transition>();
				}
				toadd.add(t);
				labMap.put(lab, toadd);
			}
		}
		return labMap;
	}

	/**
	 * Constructs (adds to) in "seen" the list of transitions that are necessary to compute "label"'s effect.
	 * @param labMap maps labels to the set of transitions that bear this label
	 * @param label the current target label
	 * @param seen all transitions we know we already know we depend upon
	 */
	public void getDependencies (Map<String,Set<Transition>> labMap, String label, Set<Transition> seen) {

		for (Transition t : labMap.get(label)) {
			if (! seen.contains(t)) {
				// time to add it !
				seen.add(t);
				// compute its own transitive dependencies
				for (Iterator<EObject> it = t.eAllContents() ; it.hasNext() ; /*NOP*/) {
					EObject cont = it.next();
					if (cont instanceof SelfCall) {
						SelfCall call = (SelfCall) cont;
						// recurse
						getDependencies(labMap, call.getLabel().getName(), seen);
					}
				}
			}
		}

	}

	@Check
	public void checkNumberOfParams (SelfCall call) {
		if (call.getParams().size() != call.getLabel().getParams().size()) {
			error("Label "+call.getLabel().getName() +" is defined with " + call.getLabel().getParams().size() + " parameters.", /* Error Message */ 
					call,             /* Object Source of Error */ 
					GalPackage.Literals.SELF_CALL__PARAMS,                /* wrong Feature */
					GAL_ERROR_BAD_PARAM_CALL      /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
					);
			
		}
	}

	@Check
	public void checkNumberOfParams (InstanceCall call) {
		if (call.getParams().size() != call.getLabel().getParams().size()) {
			error("Label "+call.getLabel().getName() +" is defined with " + call.getLabel().getParams().size() + " parameters.", /* Error Message */ 
					call,             /* Object Source of Error */ 
					GalPackage.Literals.INSTANCE_CALL__PARAMS,                /* wrong Feature */
					GAL_ERROR_BAD_PARAM_CALL      /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
					);
			
		}
	}
	
	@Check
	public void checkNumberOfParams (Label lab) {
		if (lab.getName().equals("") && lab.getParams().size() > 0 ) {
			error("Empty or private label should not be defined with parameters.", /* Error Message */ 
					lab,             /* Object Source of Error */ 
					GalPackage.Literals.LABEL__PARAMS,                /* wrong Feature */
					GAL_ERROR_PARAM_ON_EMPTY_LABEL      /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
					);
			
		}
	}


	@Check
	public void checkLabelNameNotEndWithUnderscore (Label lab) {
		if (lab.getName().matches(".*_\\d+$")) {
			warning("Using underscore and numbers at end of labels is reserved for parameter instantiation mechanism.", /* Error Message */ 
					lab,             /* Object Source of Error */ 
					GalPackage.Literals.LABEL__NAME,                /* wrong Feature */
					GAL_WARN_LABELNAME      /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
					);
		}

	}
	
	@Check
	public void checkMainIsPresentIfMoreThanOneType (Specification spec) {
		if (spec.getTypes().size() > 1) {
			if (spec.getMain() == null) {
				error("When your specification includes more than one type, there should be a 'main' declaration.", /* Error Message */ 
						spec.getTypes().get(spec.getTypes().size()-1),             /* Object Source of Error */ 
						GalPackage.Literals.TYPE_DECLARATION__NAME,                /* wrong Feature */
						GAL_ERROR_MISSING_MAIN      /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
						);
			}
		}

	}

	@Check
	/**
	 * Checks if the declared size of the array is equal to the number of 
	 * items of the declared integer list 
	 */
	public void checkArrayNumber(ArrayPrefix array)
	{
		if (! array.getValues().isEmpty()) {

			if (array.getSize() instanceof Constant) {
				Constant cte = (Constant) array.getSize();
				int size = cte.getValue() ; 


				if(array.getValues() == null && size != 0)
				{
					String plurielElements = size > 1 ? " elements" : " element" ; 

					error("This array should be initialized (with " + array.getSize() + plurielElements + ")",
							array,					
							GalPackage.Literals.NAMED_DECLARATION__NAME /* wrong Feature */,
							GAL_ERROR_MISSING_ELEMENTS

							);
					return ; 
				}

				int nbDeclared = array.getValues().size() ; 
				int diff = size - nbDeclared ; 

				if(diff == 0) return ; 

				if(diff>0)
				{
					// Add in the missing element list to help quickfix 
					error("You need to add "+diff+" more values at initialization",
							array,                               /* Object Source of Error */ 
							GalPackage.Literals.ARRAY_PREFIX__VALUES, /* wrong Feature */
							GAL_ERROR_MISSING_ELEMENTS               /* Error Code. @see GalJavaValidator.GAL_ERROR_*  */
							);


				}
				else if(diff<0)
				{
					error("There are too much items. You need to remove "+(-diff)+" values at initialization",
							array,
							GalPackage.Literals.ARRAY_PREFIX__VALUES /* wrong Feature */,
							GAL_ERROR_EXCESS_ITEMS); 
				}
			} else {
				error("When array size is not constant, default initialization is required. Please remove initial values declaration.",
						array,
						GalPackage.Literals.ARRAY_PREFIX__VALUES /* wrong Feature */,
						GAL_ERROR_EXCESS_ITEMS); 
				
			}
		}
	}
}