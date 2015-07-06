package fr.lip6.move.gal.instantiate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.AssignType;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.VarDecl;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableReference;

public class DomainAnalyzer {

	
	public static Map<VarDecl, Set<Integer>> computeConstAccessVariableDomains(GALTypeDeclaration s) {
		Map<VarDecl,Set<Integer>> domains = new HashMap<VarDecl, Set<Integer>>();
		Set<VarDecl> hotvars = new HashSet<VarDecl>();
		
		// Grab initial values
		collectInitialValues(s, domains, hotvars);
		
		// To avoid too many traversals we store a graph of which variable depends on which
		// this is for propagation of variable domains : such a variable is only assigned constants and other variables
		// if all the other variables domains are resolved, this variable will also get a domain eventually.
		// y  -> { x, z } means that x and z are not yet resolved but that  
		// both x = y and x = z assignment instructions occur 
		Map<VarDecl, Set<VarDecl>> dependUpon = new HashMap<VarDecl, Set<VarDecl>>();
		
		// Traverse spec, and build variable dependency graph
		collectVarAcesses(s, domains, hotvars, dependUpon);
		
		// at this stage, dependUpon contains as keys only variables that maybe can be resolved : they are only assigned
		// other vars and constants
		// hotvars contains all "fixed" variables and all "maybe" variables.
		
		// We want only vars with static accesses here.
		hotvars.removeAll(dependUpon.keySet());
		
		// remove vars with unknown domain 
		cleanupDomains(domains, hotvars);
		
		return domains;
	}
	
	public static Map<VarDecl, Set<Integer>> computeVariableDomains(GALTypeDeclaration s) {
		Map<VarDecl,Set<Integer>> domains = new HashMap<VarDecl, Set<Integer>>();
		Set<VarDecl> hotvars = new HashSet<VarDecl>();
		
		// Grab initial values
		collectInitialValues(s, domains, hotvars);
		
		// To avoid too many traversals we store a graph of which variable depends on which
		// this is for propagation of variable domains : such a variable is only assigned constants and other variables
		// if all the other variables domains are resolved, this variable will also get a domain eventually.
		// y  -> { x, z } means that x and z are not yet resolved but that  
		// both x = y and x = z assignment instructions occur 
		Map<VarDecl, Set<VarDecl>> dependUpon = new HashMap<VarDecl, Set<VarDecl>>();
		
		// Traverse spec, and build variable dependency graph
		collectVarAcesses(s, domains, hotvars, dependUpon);
		
		// at this stage, dependUpon contains as keys only variables that maybe can be resolved : they are only assigned
		// other vars and constants
		// hotvars contains all "fixed" variables and all "maybe" variables.
		// vars in hotvars but not in dependUpon are Fixed variables because they were only assigned constants in the whole spec.
		
		resolveVariableDependencyGraph(domains, hotvars, dependUpon);
		
		
		// This choice is debatable : technically, we could still find SCC of dependency graph
		// and assign one domain for each as the union of the domains currently found for vars in the SCC
		while (! dependUpon.isEmpty()) {
			
			Entry<VarDecl, Set<VarDecl>> entry = dependUpon.entrySet().iterator().next();
			Set<VarDecl> scc = new HashSet<VarDecl>();
			List<VarDecl> tosee = new ArrayList<VarDecl>();
			tosee.add(entry.getKey());
			scc.add(entry.getKey());
			
			while (! tosee.isEmpty()) {
				VarDecl var = tosee.remove(0);
				Set<VarDecl> tdeps = dependUpon.get(var);
				if (tdeps != null) {
					for (VarDecl v2 : tdeps) {
						if (scc.add(v2)) {
							tosee.add(v2);
						}
					}
				}
			}
			Set<Integer> resDom = new HashSet<Integer>();
			for (VarDecl vd : scc) {
				resDom.addAll(domains.get(vd));
			}
			for (VarDecl vd : scc) {
				domains.get(vd).addAll(resDom);
				dependUpon.remove(vd);
			}
		}
		
		// remove vars with unknown domain 
		cleanupDomains(domains, hotvars);
		
		// some traces 
		StringBuilder sb = new StringBuilder();
		int sum = hotvars.size();
		for (VarDecl var : domains.keySet()) {
			sb.append( var.getName() + " in "+ domains.get(var) + ",");
			
			String newCom = var.getComment();
			if (var.getComment() != null) {
				newCom = newCom.replace("*/", "\n");
				newCom = newCom.replaceAll ("\\s+\\n",""); 
				newCom = newCom.replaceAll("Dom:\\[.*\\]", "");
			} else {
				newCom = "/** ";
			}
			var.setComment(newCom + " Dom:"+domains.get(var) + " */");
		}
		if (sum != 0) {
			int totalVars = s.getVariables().size();
			if (totalVars - sum > 0) {
				sb.append("\nUnknown domain : ");
				for (VarDecl vd : s.getVariables()) {			
					if (!domains.containsKey(vd)) {
						sb.append(vd.getName() +", ");
					}
				}
				sb.append("\n");
			}

			for (ArrayPrefix ap : s.getArrays()) {
				totalVars += ((Constant) ap.getSize()).getValue();			
			}
			Logger.getLogger("fr.lip6.move.gal").info("Found a total of " + sum + " fixed domain variables (out of "+ totalVars +" variables) in GAL type "+ s.getName());
			Logger.getLogger("fr.lip6.move.gal").fine(sb.toString());
		}
		
		return domains;
	}

	private static void cleanupDomains(Map<VarDecl, Set<Integer>> domains,
			Set<VarDecl> hotvars) {
		// cleanup the domains data structure, get rid of empty domains a.k.a. unknown domain vars
		List<VarDecl> torem = new ArrayList<VarDecl>();
		// first pass : collect
		for (VarDecl vd : domains.keySet()) {
			if (! hotvars.contains(vd)) {
				torem.add(vd);
			}
		}
		// destroy
		for (VarDecl vd : torem) {
			domains.remove(vd);
		}
	}

	private static void resolveVariableDependencyGraph(
			Map<VarDecl, Set<Integer>> domains, Set<VarDecl> hotvars,
			Map<VarDecl, Set<VarDecl>> dependUpon) {
		// iterate until no variables in maybes can be moved to fixed.
		while (true) {
			List<VarDecl> todel = new ArrayList<VarDecl>();
			// for each  "maybe" variable
			for (Entry<VarDecl, Set<VarDecl>> entry : dependUpon.entrySet()) {
				VarDecl vd = entry.getKey();
				// for resolved vars
				List<VarDecl> resolved = new ArrayList<VarDecl>();
				// scan its dependencies
				for (VarDecl referencedVar : entry.getValue()) {
					// if the variable we depend upon is fixed
					if (hotvars.contains(referencedVar)) { 
						if (!dependUpon.containsKey(referencedVar)) {
						// We propagate the domain definition of referencedVar to vd
						domains.get(vd).addAll(domains.get(referencedVar));
						// we have propagated the info, get rid of edge in dependency graph
						resolved.add(referencedVar);
						}
					} else {
						// This variable depends on unsolvable variable, get rid of it.
						todel.add(vd);
						hotvars.remove(vd);
					}
				}
				// get rid of resolved dependencies
				entry.getValue().removeAll(resolved);
	
				// cool, all variables that vd depended upon have been resolved
				if (entry.getValue().isEmpty()) {
					// We move vd from maybeHot to hotvars
					todel.add(vd);
				}
			}
			// end of propagations, we have done one round through the graph and no variables have been resolved
			if (todel.isEmpty()) {
				break;
			}
			// mark these vars from maybe known to surely known
			for (VarDecl vd : todel) {
				dependUpon.remove(vd);
			}
		}
	}

	private static void collectVarAcesses(GALTypeDeclaration s,
			Map<VarDecl, Set<Integer>> domains, Set<VarDecl> hotvars,
			Map<VarDecl, Set<VarDecl>> dependUpon) {
		// compute variable ranges
		for (TreeIterator<EObject> it = s.eAllContents() ; it.hasNext() ; ) {
			EObject obj = it.next();
			if (obj instanceof Assignment) {
				Assignment ass = (Assignment) obj;
				VariableReference lhs = ass.getLeft();
				IntExpression rhs = ass.getRight();
				VarDecl vd = (VarDecl) lhs.getRef();

				// vd is now lhs of assignment
				if (hotvars.contains(vd)) {
					if (rhs instanceof Constant && ass.getType() == AssignType.ASSIGN ) {
						// If the assignment is of the form (k is a constant)
						// x = k ; tab[x] = k : k is added to domain of x
						// simple case : add rhs constant k to domain of lhs
						domains.get(vd).add(((Constant) rhs).getValue());
					} else if (rhs instanceof VariableReference && ass.getType() == AssignType.ASSIGN) {
						// Well, this still might get resolved, it's a plain copy of var to another
						// form  : x = y
						// store the fact that x depends on y
						Set<VarDecl> deps = dependUpon.get(vd);
						if (deps == null) {
							deps = new HashSet<VarDecl>();
							dependUpon.put(vd, deps);
						}
						VariableReference vref = (VariableReference) rhs;
						deps.add((VarDecl) vref.getRef());
					} else {
						// otherwise : we have some sort of arithmetic or other computation as rhs
						// just let lhs domain be unconstrained.
						domains.get(vd).clear();
						hotvars.remove(vd);
						// drop this variable from perhaps known vars
						dependUpon.remove(vd);
					}
				} 
			}
		}
	}

	private static void collectInitialValues(GALTypeDeclaration s,
			Map<VarDecl, Set<Integer>> domains, Set<VarDecl> hotvars) {
		hotvars.addAll(s.getVariables());
		hotvars.addAll(s.getArrays());
		
		// build up a list of all variables
		for (Variable var : s.getVariables()) {
			IntExpression val = var.getValue();
			if (val instanceof Constant && ! var.isHotbit()) {
				Constant cte = (Constant) val;
				Set<Integer> seen = new TreeSet<Integer>();
				seen.add(cte.getValue());
				// add initial values to domains
				domains.put(var, seen);				
			} else {
				// looks like this variable is already causing problems. Forget about it.
				domains.put(var, Collections.<Integer>emptySet());
				hotvars.remove(var);
			}
		}
		// also collect arrays : only one domain for whole array
		for (ArrayPrefix ap : s.getArrays()) {
			Set<Integer> seen = new TreeSet<Integer>();
			domains.put(ap, seen);
						
			for (IntExpression val : ap.getValues()) {
				if (val instanceof Constant && ! ap.isHotbit()) {
					Constant cte = (Constant) val;
					// plain constant initial value : add to domain
					seen.add(cte.getValue());
				} else {
					// some kind of expression, drop array from examined variables.
					seen.clear();
					hotvars.remove(ap);
					break;
				}
			}
		}
	}

	public static void computeVariableDomains(Specification s) {
		for (TypeDeclaration td : s.getTypes()) {
			if (td instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) td;
				computeVariableDomains(gal);
			}
		}
	}

}
