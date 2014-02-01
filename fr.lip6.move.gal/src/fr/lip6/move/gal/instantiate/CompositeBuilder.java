package fr.lip6.move.gal.instantiate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.Actions;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.ArrayVarAccess;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Parameter;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.VarAccess;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableRef;

public class CompositeBuilder {



	public static Specification buildComposite (GALTypeDeclaration galori) {

		GALTypeDeclaration gal = EcoreUtil.copy(galori);
		
		for (EObject obj : Instantiator.getAllChildren(gal)) {
			for (ArrayPrefix ap : gal.getArrays()) {
				Set<Integer> vals = new HashSet<Integer>();
				for (int i = 0 ; i < ap.getSize(); i++) {
					vals.add(i);
				}
			}			
		}
		
		// build hypergraph of transition to variable dependency
		Map<Transition, Set<Variable>> varEdges = new HashMap<Transition, Set<Variable>>();
		Map<Transition, Map<ArrayPrefix,Set<Integer>>> arrEdges = new HashMap<Transition, Map<ArrayPrefix,Set<Integer>>>();



		Map<ArrayPrefix, Set<Integer>> constantArrs = new HashMap<ArrayPrefix, Set<Integer>>();

//		Map<ArrayPrefix,Boolean> hasComplexAccess = new HashMap<ArrayPrefix,Boolean>();
		
//		int totalVars = gal.getVariables().size();		
		for (ArrayPrefix ap : gal.getArrays()) {
			Set<Integer> vals = new HashSet<Integer>();
			for (int i = 0 ; i < ap.getSize(); i++) {
				vals.add(i);
			}
			constantArrs.put(ap, vals);
//			totalVars += ap.getSize();
//			hasComplexAccess.put(ap, false);
		}

		
		// compute hypergraph into Edges
		Transition owner = null;
		for (TreeIterator<EObject> it = gal.eAllContents() ; it.hasNext() ; ) {
			EObject obj = it.next();
			if (obj instanceof Transition) {
				owner = (Transition) obj;				
			} else if (obj instanceof VariableRef) {
				VariableRef va = (VariableRef) obj;
				addVarRefToTrans(varEdges, owner, va);
			} else if (obj instanceof ArrayVarAccess) {
				ArrayVarAccess av = (ArrayVarAccess) obj;
				addArrayAccessToTrans(arrEdges, owner, av);
			}
		}		
		

		// now deduce underlying connectivity graph between variables
		// collect components : two lists with matching indexes.
		List<Set<Variable>> components = new ArrayList<Set<Variable>>();
		List<Map<ArrayPrefix,Set<Integer>>> arrComponents = new ArrayList<Map<ArrayPrefix,Set<Integer>>>();
		
		// we iterate thru all variables of GAL; these todo are emptied as the algorithm processes transitions
		// initially all vars and array cells need to be treated
		Set<Variable> todo = new HashSet<Variable>(gal.getVariables());
		Map<ArrayPrefix, Set<Integer>> todoArrs = new HashMap<ArrayPrefix, Set<Integer>>(constantArrs);
		
		while (! todo.isEmpty() || ! todoArrs.isEmpty()) {
			// this will be the new component 
			Set<Variable> component = new HashSet<Variable>();
			Map<ArrayPrefix, Set<Integer>> arrcomponent = new HashMap<ArrayPrefix,Set<Integer>>();
			
			// pop any variable from todo, and add it to the component
			if (!todo.isEmpty()) {
				Variable seed = pop(todo);
				component.add(seed);
			} else {
				Entry<ArrayPrefix, Set<Integer>> arrtarget = todoArrs.entrySet().iterator().next();
				ArrayPrefix target = arrtarget.getKey();
				Set<Integer> tvalues = arrtarget.getValue();
				Integer tindex = pop(tvalues);
				if (tvalues.isEmpty()) {
					todoArrs.remove(target);
				}
				
				Set<Integer> valueSet = new HashSet<Integer>();
				valueSet.add(tindex);
				arrcomponent.put(target, valueSet);
			}
						
			// iterate thru transitions : add all related variables of seed (transitively) to component.
			for (Transition t : gal.getTransitions()) {
				boolean domerge = false;
				Set<Variable> vars = varEdges.get(t);
				if (vars != null) {
					Set<Variable> varscopy = new HashSet<Variable>(vars);
					varscopy.retainAll(component);
					if (! varscopy.isEmpty()) {
						domerge = true;
					}
				} else {
					vars = Collections.emptySet();
				}
				
				Map<ArrayPrefix, Set<Integer>> arrs = arrEdges.get(t);
				if (arrs != null) {
					for (Entry<ArrayPrefix, Set<Integer>> entry : arrs.entrySet()) {
						Set<Integer> incomp = arrcomponent.get(entry.getKey());
						if (incomp != null) {
							Set<Integer> targetcopy = new HashSet<Integer>(entry.getValue()) ;
							targetcopy.retainAll(incomp);
							if (! targetcopy.isEmpty()) {
								domerge = true;
								break;
							}
						}
					}
				}
				
				if (domerge){
					Map<ArrayPrefix, Set<Integer>> toadd = arrEdges.get(t);
					if (toadd != null) {
						for (Entry<ArrayPrefix, Set<Integer>> entry : toadd.entrySet()) {
							ArrayPrefix target = entry.getKey();
							Set<Integer> incomp = arrcomponent.get(target);
							if (incomp == null) {
								incomp = new HashSet<Integer>(entry.getValue());
								arrcomponent.put(entry.getKey(), incomp);
							} else {
								incomp.addAll(entry.getValue());
							}
							
							Set<Integer> set = todoArrs.get(target);
							if (set != null) {
								set.removeAll(incomp);
								if (set.isEmpty()) 
									todoArrs.remove(target);
							}
						}
					}
					
					component.addAll(vars);						
					todo.removeAll(vars);
				}
			}
			components.add(component);
			arrComponents.add(arrcomponent);
			
		}
		
		Specification spec = null;
		
		if (components.size() > 1 ) {
			System.err.println("Found separable sub components !");
			spec = GalFactory.eINSTANCE.createSpecification();
			
			
			for (int i = 0; i < components.size(); i++) {
				System.err.println("\nComponent "+i);
				GALTypeDeclaration subgal = GalFactory.eINSTANCE.createGALTypeDeclaration();
				subgal.setName("Sub"+i);
				spec.getTypes().add(subgal);
				Map<Variable,Variable> mapvars = new HashMap<Variable, Variable>();
				for (Variable var :components.get(i)) {
					Variable varimg = EcoreUtil.copy(var);
					subgal.getVariables().add(varimg );
					mapvars.put(var, varimg);
					System.err.println(var.getName());
				}
				for (Entry<ArrayPrefix, Set<Integer>> entry : arrComponents.get(i).entrySet()) {
					System.err.println(entry.getKey().getName() + entry.getValue());					
				}
			}
		}
		
		
		
		
		return spec;
	}

	private static<T> T pop(Set<T> todo) {
		T seed = todo.iterator().next();
		todo.remove(seed);
		return seed;
	}

	private static void addArrayAccessToTrans(
			Map<Transition, Map<ArrayPrefix, Set<Integer>>> arrEdges,
			Transition owner,
			ArrayVarAccess av) {
		Map<ArrayPrefix, Set<Integer>> arrmap = arrEdges.get(owner);
		if (arrmap == null) {
			arrmap = new HashMap<ArrayPrefix,Set<Integer>> ();
			arrEdges.put(owner, arrmap);
		}
		ArrayPrefix ap = av.getPrefix();
		Set<Integer> list = arrmap.get(ap);
		if (list == null) {
			list = new TreeSet<Integer>();
			arrmap.put(av.getPrefix(), list);
		}
		if (av.getIndex() instanceof Constant) {
			Constant cte = (Constant) av.getIndex();
			list.add(cte.getValue());						
		} else {
//					hasComplexAccess.put(av.getPrefix(), true);Set<Integer> vals = new HashSet<Integer>();
			for (int i = 0 ; i < ap.getSize(); i++) {
				list.add(i);
			}
		}
	}

	private static void addVarRefToTrans(
			Map<Transition, Set<Variable>> varEdges, Transition owner,
			VariableRef va) {
		Set<Variable> refs = varEdges.get(owner);
		if (refs == null) {
			refs = new HashSet<Variable>();
			varEdges.put(owner, refs);
		}
		refs.add(va.getReferencedVar());
	}
}
