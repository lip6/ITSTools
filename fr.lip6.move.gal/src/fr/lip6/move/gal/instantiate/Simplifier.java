package fr.lip6.move.gal.instantiate;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.Abort;
import fr.lip6.move.gal.AliasDeclaration;
import fr.lip6.move.gal.AssignType;
import fr.lip6.move.gal.CompositeTypeDeclaration;
import fr.lip6.move.gal.InstanceCall;
import fr.lip6.move.gal.InstanceDecl;
import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.LabelCall;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.QualifiedReference;
import fr.lip6.move.gal.Statement;
import fr.lip6.move.gal.And;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.BitComplement;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.Event;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Ite;
import fr.lip6.move.gal.NamedDeclaration;
import fr.lip6.move.gal.Not;
import fr.lip6.move.gal.Or;
import fr.lip6.move.gal.SelfCall;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Synchronization;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.UnaryMinus;
import fr.lip6.move.gal.VarDecl;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.support.Support;

public class Simplifier {

	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}

	public static Support simplify(Specification spec) {
		long debut = System.currentTimeMillis();

		
		replaceAlias(spec);
		
		Set <GALTypeDeclaration> torem = new HashSet<GALTypeDeclaration>();
		Map<GALTypeDeclaration, Set<String>> trueLabs = new HashMap<GALTypeDeclaration,Set<String>>();
		Support toret = new Support();
		for (TypeDeclaration td : spec.getTypes()) {
			if (td instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) td;
				toret.addAll(simplify(gal));
				if (gal.getVariables().isEmpty() && gal.getArrays().isEmpty()){
					torem.add(gal);
					for (Transition tr : gal.getTransitions()) {
						if (tr.getLabel() != null) {
							if (tr.getGuard() instanceof True) {
								Set<String> trueLab = trueLabs.get(gal) ;
								if (trueLab == null) {
									trueLab = new HashSet<String>();
									trueLabs.put(gal, trueLab);
								}
								trueLab.add(tr.getLabel().getName());
							} else if (tr.getGuard() instanceof False) {
								// ok its an abort
							} else {
								getLog().warning("expected an empty type to only have true or false guards in transitions. Treating :"+gal.getName() +":"+ tr.getName() );
							}
						}
					}
				}
			}
		}
		removeAll(spec.getTypes(), torem);
		if (! torem.isEmpty()) {

			for (TypeDeclaration td : spec.getTypes()) {

				if (td instanceof CompositeTypeDeclaration) {
					CompositeTypeDeclaration ctd = (CompositeTypeDeclaration) td;
					Set<InstanceDecl> todel = new HashSet<InstanceDecl>();
					for (InstanceDecl inst : ctd.getInstances()) {
						if (torem.contains(inst.getType())) {
							todel.add(inst);
						}
					}
					if (! todel.isEmpty()) {
						removeAll(ctd.getInstances(), todel);
						List<Statement> todel2 = new ArrayList<Statement>();
						// all calls to labels should resolve as either false => abort or true
						// abort already taken into account.
						for (Synchronization sync : ctd.getSynchronizations()) {
							for (TreeIterator<EObject> it = sync.eAllContents() ; it.hasNext() ; ) {
								EObject obj = it.next();
								if (obj instanceof InstanceCall) {
									InstanceCall call = (InstanceCall) obj;
									Set<String> trueLab = trueLabs.get(((InstanceDecl) call.getInstance().getRef()).getType());
									if (trueLab != null && trueLab.contains(call.getLabel().getName())) {
										todel2.add(call);
									}
								} else if (obj instanceof BooleanExpression || obj instanceof IntExpression) {
									it.prune();
								}
							}
						}
						for (Statement statement : todel2) {
							EcoreUtil.remove(statement);
						}
					}
				}
			}
			StringBuilder sb = new StringBuilder("Removed "+ torem.size() + " GAL types that were empty due to variable simplifications.\n");
			for (GALTypeDeclaration gal : torem) {
				sb.append(gal.getName());
				sb.append(",");
			}
			getLog().info(sb.toString());
		}

		Instantiator.fuseIsomorphicEffects(spec);

		for (Property p : spec.getProperties()) {
			for (EObject e : p.getBody().eContents()) {
				if (e instanceof BooleanExpression) {
					BooleanExpression be = (BooleanExpression) e;
					simplify(be);
				}
			}			
		}
		PropertySimplifier.rewriteWithInitialState(spec);
		CTLSimplifier.simplifyTemporal(spec);
		getLog().fine("Simplify gal took : " + (System.currentTimeMillis() - debut) + " ms"); //$NON-NLS-1$ //$NON-NLS-2$
		return toret;
	}

	/**
	 * Efficient O (n) operation to removeAll from an aggregation.
	 * @param container a container for a set of elements (no duplicates), some of which we want to get rid of
	 * @param todel some elements to remove, typically stored in a HashSet.
	 */
	public static <T> void removeAll ( List<T> container, Set<? extends T> todel ) {
		if (todel.isEmpty())
			return;
		List<T> contents = new ArrayList<T>(container);
		container.clear();
		// since container contains no duplicates ensure |B| max contains() operations
		int torem = todel.size();
		for (T elt : contents) {
			if ( torem==0 || ! todel.contains(elt) ) {
				container.add(elt);
			} else {
				torem--;
			}
		}
	}

	/**
	 * Efficient O (n) operation to retainAll from an aggregation.
	 * @param container a container for a set of elements (no duplicates), some of which we want to get rid of
	 * @param tokeep some elements to remove, typically stored in a HashSet.
	 */
	public static <T> void retainAll ( List<T> container, Set<? extends T> tokeep ) {
		if (tokeep.isEmpty()) {
			container.clear();
			return ;
		}
		List<T> contents = new ArrayList<T>(container);
		container.clear();
		for (T elt : contents) {
			if ( tokeep.contains(elt) ) {
				container.add(elt);
			}
		}
	}

	private static int replaceAlias(Specification spec) {
		boolean doit = false;
		for (TypeDeclaration td : spec.getTypes()) {
			if (td instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) td;
				if (! gal.getAlias().isEmpty()) {
					doit = true;
					break;
				}
			}			
		}
		if (!doit) return 0;
		
		int nbsub = 0;
		for (TreeIterator<EObject> it = spec.eAllContents() ; it.hasNext() ; ) {
			EObject obj = it.next();
			if (obj instanceof VariableReference) {
				VariableReference ref = (VariableReference) obj;
				if (ref.getRef() instanceof AliasDeclaration) {
					AliasDeclaration alias = (AliasDeclaration) ref.getRef();
					EObject par = obj.eContainer(); 
					QualifiedReference qref = null;
					while (par instanceof QualifiedReference) {
						QualifiedReference parent = (QualifiedReference) par;
						QualifiedReference tmp = GalFactory.eINSTANCE.createQualifiedReference();
						tmp.setQualifier(EcoreUtil.copy(parent.getQualifier()));
						tmp.setNext(qref);
						qref = tmp;
						par = par.eContainer();
					}
					IntExpression expr = EcoreUtil.copy(alias.getExpr());
					
					if (qref != null) {
						// now qualify all variables in the alias.
						for (TreeIterator<EObject> jt = expr.eAllContents() ; jt.hasNext() ; ) {
							EObject o = jt.next();
							if (o instanceof VariableReference) {
								VariableReference vref = (VariableReference) o;
								QualifiedReference q = EcoreUtil.copy(qref);
								QualifiedReference qq = q;
								while (qq.getNext() != null) {
									qq = (QualifiedReference) qq.getNext();
								}
								qq.setNext(EcoreUtil.copy(vref));
								EcoreUtil.replace(o, q);
								jt.prune();
							}
						}
						EObject  torep = obj.eContainer();
						while (torep.eContainer() instanceof QualifiedReference) {
							torep = torep.eContainer();
						}
						EcoreUtil.replace(torep, expr);
					} else {
						EcoreUtil.replace(obj, expr);
					}
					
					
										
					it.prune();
					nbsub++;
				}
			}
		}
		for (TypeDeclaration td : spec.getTypes()) {
			if (td instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) td;
				gal.getAlias().clear();
			}			
		}
		return nbsub;
	}

	public static void removeUncalledTransitions(Specification spec) {
		Map<TypeDeclaration, Set<String>> tokeep = new HashMap< TypeDeclaration,  Set<String> > ();

		for (TypeDeclaration type : spec.getTypes()) {

			// special handling of empty label = private
			{
				Set<String> seen = tokeep.get(type);
				if (seen == null) {
					seen = new HashSet<String>();
					tokeep.put(type, seen);
				}
				seen.add("");
			}

			if (type instanceof CompositeTypeDeclaration) {
				CompositeTypeDeclaration ctd = (CompositeTypeDeclaration) type;

				for (Synchronization sync : ctd.getSynchronizations()) {

					for (TreeIterator<EObject> it = sync.eAllContents() ; it.hasNext() ; ) {
						EObject obj = it.next();

						TypeDeclaration calledType = null;
						Label lab = null;
						if (obj instanceof InstanceCall) {
							InstanceCall call = (InstanceCall) obj;

							calledType = ((InstanceDecl) call.getInstance().getRef()).getType();
							lab = call.getLabel();
						} else if (obj instanceof SelfCall) {
							SelfCall call = (SelfCall) obj;

							calledType = ctd;
							lab = call.getLabel();
						}

						if (calledType != null) {
							Set<String> seen = tokeep.get(calledType);
							if (seen == null) {
								seen = new HashSet<String>();
								tokeep.put(calledType, seen);
							}
							seen.add(lab.getName());							
						} 					

					}										
				}
			} else if (type instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) type;

				for (Transition trans : gal.getTransitions()) {

					for (TreeIterator<EObject> it = trans.eAllContents() ; it.hasNext() ; ) {
						EObject obj = it.next();

						if (obj instanceof IntExpression || obj instanceof BooleanExpression) {
							it.prune();
						}
						if (obj instanceof SelfCall) {
							SelfCall call = (SelfCall) obj;
							Set<String> seen = tokeep.get(gal);
							if (seen == null) {
								seen = new HashSet<String>();
								tokeep.put(gal, seen);
							}
							seen.add(call.getLabel().getName());							
						} 					

					}										
				}


			}			
		}

		// special handling of elapse at top level
		Set<String> l = tokeep.get(spec.getMain());
		if (l ==null) {
			l = new HashSet<>();
			tokeep.put(spec.getMain(), l);
		}
		l.add("elapse");

		for (TypeDeclaration type : spec.getTypes()) {
			if (type instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) type;
				Set<String> seen = tokeep.get(gal);

				Set<Transition> todel = new HashSet<Transition>();
				for (Transition tr : gal.getTransitions()) {
					Label lab = tr.getLabel() ;
					if (lab != null) {
						if (seen == null || !seen.contains(lab.getName())) {
							// kill it !
							todel.add(tr);
						}
					}				 
				}
				if (!todel.isEmpty()) {
					getLog().info("Removed "+ todel.size() +" uncalled transitions from type "+gal.getName());
				}

				// efficient gal.getTrans().removeAll(todel)
				removeAll(gal.getTransitions(), todel);

			} else if (type instanceof CompositeTypeDeclaration) {
				CompositeTypeDeclaration ctd = (CompositeTypeDeclaration) type;

				Set<String> seen = tokeep.get(ctd);
				Set<Synchronization> todel = new HashSet<Synchronization>();

				for (Synchronization tr : ctd.getSynchronizations()) {
					Label lab = tr.getLabel() ;
					if (lab != null && ! "".equals(lab.getName()) ) {

						if (seen == null || !seen.contains(lab.getName())) {
							// kill it !
							todel.add(tr);
						}
					}				 
				}
				if (!todel.isEmpty()) {
					getLog().info("Removed "+ todel.size() +" uncalled synchronizations from type "+ctd.getName());
				}

				removeAll(ctd.getSynchronizations(), todel);

			}


		}



	}

	/**
	 * Returns the set of variables that are constants, they may have been removed from s.
	 * @param s the GAL to simplify
	 * @return a set of constants that have been simplified
	 */
	public static Support simplify(GALTypeDeclaration s) {
		simplifyAllExpressions(s);

		simplifyAbort(s.getTransitions());

		simplifyFalseTransitions(s);		

		simplifyPetriStyleAssignments(s);

		Support toret = simplifyConstantVariables(s);

		simplifyAllExpressions(s);

		simplifyConstantIte(s.getTransitions());

		simplifyAbort(s.getTransitions());

		simplifyFalseTransitions(s);		

		return toret;
	}

	/**
	 * Replace any sequence that contains an abort by a single abort statement.
	 * Get rid of any transition that has abort in its body statements.
	 * @param s
	 */
	public static <T extends Event> int simplifyAbort(List<T> events) {

		List<Abort> toclear = new ArrayList<Abort>();
		// first collect occurrences of abort
		for (Event t : events) {
			for (TreeIterator<EObject> it = t.eAllContents() ; it.hasNext() ; ) {
				EObject obj = it.next();
				if (obj instanceof Abort) {
					toclear.add((Abort) obj);
				} else if (obj instanceof LabelCall || obj instanceof SelfCall || obj instanceof Assignment) {
					it.prune();
				}
			}
		}

		int nbrem = 0;
		Set<T> todel = new HashSet<>();
		for (Abort obj : toclear) {
			// a transition with abort in its body
			if (obj.eContainer() instanceof Event) {
				nbrem ++;
				todel.add((T) obj.eContainer());
			} else {
				// some nested block of some kind, absorb other statements.
				@SuppressWarnings("unchecked")
				EList<Statement> statementList = (EList<Statement>) obj.eContainer().eGet(obj.eContainmentFeature());
				statementList.clear();
				statementList.add(obj);
			}
		}
		removeAll(events, todel);
		return nbrem;
	}

	/**
	 * Reduce:  if (c) { s1 } else { s2 }
	 * to {s1} if c is trivially true or {s2} if c is trivially false. 
	 * @param s
	 */
	static void simplifyConstantIte(List<? extends Event> events) {
		List<Ite> toreplace = new ArrayList<Ite>();


		for (Event evt : events) {
			for (TreeIterator<EObject> it = evt.eAllContents() ; it.hasNext() ; ) {
				EObject obj = it.next();
				if (obj instanceof Ite) {
					Ite ite = (Ite) obj; 
					if (ite.getCond() instanceof True || ite.getCond() instanceof False) {
						toreplace.add(ite);
					}
				} else if (obj instanceof Assignment  || obj instanceof SelfCall || obj instanceof BooleanExpression || obj instanceof IntExpression) {
					it.prune();
				}
			}
		}

		for (Ite ite : toreplace) {
			// insert before assignment
			@SuppressWarnings("unchecked")
			EList<Statement> statementList = (EList<Statement>) ite.eContainer().eGet(ite.eContainmentFeature());
			int index = statementList.indexOf(ite);

			if (ite.getCond() instanceof True) {
				if (! ite.getIfTrue().isEmpty())
					statementList.addAll(index, ite.getIfTrue());
			} else {
				if (! ite.getIfFalse().isEmpty())
					statementList.addAll(index, ite.getIfFalse());				
			}
			index = statementList.indexOf(ite);
			statementList.remove(index);
		}
	}

	/**
	 * Navigate recursively into the EObject and invoke simplify on
	 *  each encountered IntExpression or BoolExpression. We don't recurse on
	 *  these expressions obviously, so we only hit top level Expression occurrences.
	 */
	public static void simplifyAllExpressions(EObject s) {
		if (s == null)
			return;
		for (TreeIterator<EObject> it = s.eAllContents() ; it.hasNext() ;  ) {
			EObject cur = it.next();
			if (cur instanceof IntExpression) {
				IntExpression expr = (IntExpression) cur;
				simplify(expr);
				it.prune();
			} else if (cur instanceof BooleanExpression) {
				BooleanExpression expr = (BooleanExpression) cur;
				simplify(expr);
				it.prune();
			} 
		}
	}


	/**
	 * Simplify syntactically false guard transitions
	 * @param s
	 */
	private static void simplifyFalseTransitions(GALTypeDeclaration s) {
		Set<Transition> todel = new HashSet<Transition>();
		for (Transition t : s.getTransitions()) {
			if (t.getGuard() instanceof False) {
				todel.add(t);
			}
		}
		removeAll(s.getTransitions(), todel);

		if (! todel.isEmpty()) 
			getLog().info("Removed "+ todel.size() + " false transitions.");

	}

	/** Identify and discard constant variables 
	 * @return */
	private static Support simplifyConstantVariables(GALTypeDeclaration s) {

		Set<Variable> constvars = new HashSet<Variable>(s.getVariables());
		Map<ArrayPrefix, Set<Integer>> constantArrs = new HashMap<ArrayPrefix, Set<Integer>>();
		Set<NamedDeclaration> dontremove = new HashSet<NamedDeclaration>();
		Support toret = new Support();
		int totalVars = computeConstants(s, constvars, constantArrs, dontremove, toret);


		int sum = printConstantVars(s, constvars, constantArrs, totalVars); 

		if (sum == 0) {
			return toret;
		}

		int totalexpr = 0;
		List<EObject> todel = new ArrayList<EObject>();
		// Substitute constants in guards and assignments
		for (Transition t : s.getTransitions()) {
			totalexpr += replaceConstantRefs(constvars, constantArrs, todel, t);
		}
		if (s.eContainer() != null && s.eContainer() instanceof Specification) {
			Specification spec = (Specification)s.eContainer();
			for (Property prop : spec.getProperties()) {
				int changeexpr = replaceConstantRefs(constvars, constantArrs, todel, prop);
				if (changeexpr != 0) {
					simplifyAllExpressions(prop);
					totalexpr += changeexpr;
				}
			}
		}


		// get rid of assignments to constants
		for (EObject obj : todel) {
			EcoreUtil.remove(obj);
		}

		StringBuilder stb = new StringBuilder();
		// Discard constants from state signature if possible
		for (Variable var : constvars) {
			stb.append(var.getName()+"="+ getConstantValue(var.getValue()) + ", ");
			EcoreUtil.remove(var);
		}
		if (!constvars.isEmpty()) {
			getLog().info("Removed " + constvars.size() + " constant variables :" + stb.substring(0, stb.length() -2) ); 
		}

		for (Entry<ArrayPrefix, Set<Integer>> e : constantArrs.entrySet()) {
			if (e.getValue().size() == ((Constant) e.getKey().getSize()).getValue() && (! dontremove.contains(e.getKey()))) {
				getLog().info("Removed constant array :" + e.getKey().getName() + "[]" ); 				
				EcoreUtil.remove(e.getKey());				
			}
		}
		if (totalexpr != 0) {
			getLog().info(" Simplified "+ totalexpr + " expressions due to constant valuations.");
			simplifyAllExpressions(s);
		}
		return toret;
	}

	/**
	 * @param s
	 * @param constvars
	 * @param constantArrs
	 * @param totalVars
	 * @return
	 */
	static int printConstantVars(GALTypeDeclaration s, Set<Variable> constvars,
			Map<ArrayPrefix, Set<Integer>> constantArrs, int totalVars) {
		int sum = constvars.size();
		StringBuilder sb = new StringBuilder();
		for (Variable var : constvars) {
			sb.append(var.getName()+",");
		}

		for (Entry<ArrayPrefix, Set<Integer>> e : constantArrs.entrySet()) {
			if (e.getValue().isEmpty()) {
				continue;
			}
			sum += e.getValue().size();
			List<Integer> indexes = new ArrayList<>(e.getValue());
			Collections.sort(indexes);
			List<List<Integer>> ranges = computeRanges(indexes);
			sb.append(e.getKey().getName()+"[");
			for ( Iterator<List<Integer>> it = ranges.iterator() ; it.hasNext() ; ) {
				List<Integer> startEnd = it.next();
				if (startEnd.get(0) != startEnd.get(startEnd.size()-1)) {
					sb.append( startEnd.get(0) +"-"+ startEnd.get(startEnd.size()-1));
				} else {
					sb.append( startEnd.get(0));
				}
				if (it.hasNext()) {
					sb.append(",");
				}
			}
			sb.append("], ");
		}


		if (sum != 0) {
			getLog().info("Found a total of " + sum + " constant array cells/variables (out of "+ totalVars +" variables) in type "+ s.getName());
			getLog().info(sb.toString() );
		}
		return sum;
	}

	/**
	 * Compute constant variables and array cells in a GAL.
	 * Uses basic "no write" to variable + some domain propagation
	 * @param s the spec to simplify
	 * @param constvars a set of copnstant variables (will be updated)
	 * @param constantArrs a set of constant array cells, coded into a Map. Will be updated.
	 * @param dontremove a set of constant but not to be removed array variables
	 * @param simplified the support representing variables simplified away
	 * @return
	 */
	public static int computeConstants(GALTypeDeclaration s, Set<Variable> constvars,
			Map<ArrayPrefix, Set<Integer>> constantArrs, Set<NamedDeclaration> dontremove, Support simplified) {
		int totalVars = s.getVariables().size();

		for (ArrayPrefix ap : s.getArrays()) {
			Set<Integer> vals = new HashSet<Integer>();
			int size = ((Constant) ap.getSize()).getValue();
			for (int i = 0 ; i < size ; i++) {
				vals.add(i);
			}
			totalVars += size;
			constantArrs.put(ap, vals);
		}
		// compute constant vars		
		for (Transition t: s.getTransitions()) {
			for (TreeIterator<EObject> it = t.eAllContents() ; it.hasNext() ; ) {
				EObject obj = it.next();
				if (obj instanceof Assignment) {
					Assignment ass = (Assignment) obj;
					VariableReference lhs = ass.getLeft();
					if (lhs.getIndex() == null) {
						constvars.remove(lhs.getRef());
					} else {
						if (lhs.getIndex() instanceof Constant) {
							Constant cte = (Constant) lhs.getIndex();
							constantArrs.get(lhs.getRef()).remove(cte.getValue());						
						} else {
							constantArrs.get(lhs.getRef()).clear();
						}
					}
				} else if (obj instanceof VariableReference) {
					VariableReference av = (VariableReference) obj;
					if (av.getIndex()!=null && ! (av.getIndex() instanceof Constant ) ) {
						dontremove.add(av.getRef());
					}
				}
			}
		}
		Map<VarDecl, Set<Integer>> domains = DomainAnalyzer.computeVariableDomains(s);
		for (Entry<VarDecl, Set<Integer>> entry : domains.entrySet()) {
			if (entry.getValue().size()==1) {
				if (entry.getKey() instanceof Variable) {
					constvars.add((Variable) entry.getKey());
				}
			}
		}

		for (Variable var : constvars) {
			simplified.add(var);
		}
		for (Entry<ArrayPrefix, Set<Integer>> e : constantArrs.entrySet()) {
			for (int val : e.getValue()) {
				simplified.add(e.getKey(), val);
			}
		}
		return totalVars;
	}

	private static List<List<Integer>> computeRanges(List<Integer> list) {
		List<List<Integer>>lList=new ArrayList<List<Integer>>(); //list of list of integer
		if (list.isEmpty()) {
			return lList;
		} else if (list.size() == 1) {
			lList.add(Arrays.asList(list.get(0)));
			return lList;
		}
		int i=0;
		int start=0;
		List<Integer> sList=new ArrayList<Integer>(2);
		for(  i = 1; i <list.size();i++){
			if( list.get(i - 1) + 1 != list.get(i)){
				sList.add(list.get(start));
				sList.add(list.get(i-1));
				lList.add(sList);
				sList=new ArrayList<Integer>(2);
				start=i;
			}
		}
		sList.add(list.get(start));        // for last range
		sList.add(list.get(list.size()-1));
		lList.add(sList);
		return lList;
	}

	static int replaceConstantRefs(Set<Variable> constvars,
			Map<ArrayPrefix, Set<Integer>> constantArrs, 
			List<EObject> todel, EObject t) {
		int totalexpr =0;
		for (TreeIterator<EObject> it = t.eAllContents() ; it.hasNext() ; ) {
			EObject obj = it.next();

			if (obj instanceof Assignment) {
				Assignment ass = (Assignment) obj;
				// kill assignments to constants
				if ( isAssignToConstant(ass, constvars, constantArrs) ) {
					todel.add(ass);
					it.prune();
				}					
			}
			VariableReference va = null;
			if (obj instanceof VariableReference) {
				va = (VariableReference) obj;
			} else if (obj instanceof QualifiedReference) {
				QualifiedReference qref = (QualifiedReference) obj;
				do {
					if (qref.getNext() instanceof VariableReference) {
						va = (VariableReference) qref.getNext();
					} else {
						qref = (QualifiedReference) qref.getNext();
					}
				} while (va==null);
			}
			if (va != null) {
				if (va.getIndex() == null) {
					if (constvars.contains(va.getRef())) {
						EcoreUtil.replace(obj, EcoreUtil.copy(((Variable)va.getRef()).getValue()));
						totalexpr++;
					}
					it.prune();
				} else if ( va.getIndex() instanceof Constant ) {
					int index = ((Constant) va.getIndex()).getValue();
					Set<Integer> cstIndexes = constantArrs.get(va.getRef());
					if (cstIndexes != null && cstIndexes.contains(index) ) {
						EcoreUtil.replace(obj, EcoreUtil.copy(((ArrayPrefix) va.getRef()).getValues().get(index)));						
						totalexpr++;
					}
					it.prune();
				}
			}
		}
		return totalexpr;
	}

	private static boolean isAssignToConstant(Assignment ass, Set<Variable> constvars, Map<ArrayPrefix, Set<Integer>> constantArrs) {
		if (ass.getLeft().getIndex() == null && constvars.contains(ass.getLeft().getRef()) ) {
			return true;
		} else if ( ass.getLeft().getIndex() != null && ass.getLeft().getIndex() instanceof Constant ) {
			// assign to a specific array cell
			Set<Integer> cstsIndexes = constantArrs.get(ass.getLeft().getRef());
			if (cstsIndexes != null && cstsIndexes.contains(((Constant) ass.getLeft().getIndex()).getValue())) {
				return true;
			}
		}
		return false;
	}

	public static boolean simplifyPetriStyleAssignments(GALTypeDeclaration system) {

		boolean isPetriStyle = true;
		//simplify redundant assignments :
		// suppose we have both : x = x + 1; and  x = x-1; without reading or writing to x in between.
		// typically produced by test arc style petri nets
		for (Transition tr : system.getTransitions()) {


			if (isPetriStyle(tr)) {
				Map<String,Map<VariableReference,Integer>> arrAdd = new HashMap<String, Map<VariableReference,Integer>>();
				Map<Variable,Integer> varAdd = new HashMap<Variable, Integer>();				
				boolean dobreak=false;
				for (Statement a : tr.getActions()) {
					if (a instanceof SelfCall) {
						dobreak = true;
						break;
					}
				}
				if (dobreak) 
					break;
				for (Statement a : tr.getActions()) {
					Assignment ass = (Assignment) a;
					int val;
					if (ass.getType() == AssignType.ASSIGN) {
						BinaryIntExpression bin = (BinaryIntExpression) ass.getRight();
						Constant c = (Constant) bin.getRight();
						val = c.getValue();
						if (bin.getOp().equals("-")) {
							val = -val;
						}
					} else {
						Constant c = (Constant) ass.getRight();
						val = c.getValue();
						if (ass.getType() == AssignType.DECR) {
							val = -val;
						}
					}

					if (ass.getLeft().getIndex() != null) {
						String aname = ass.getLeft().getRef().getName();
						Map<VariableReference, Integer> indmap = arrAdd.get(aname);
						if (indmap == null) {
							indmap = new HashMap<VariableReference, Integer>();
							arrAdd.put(aname, indmap);
						}
						boolean found = false;
						for (Entry<VariableReference, Integer> entry : indmap.entrySet()) {
							if (EcoreUtil.equals(entry.getKey(),ass.getLeft())) {
								entry.setValue(entry.getValue() + val);
								found = true;
								break;
							}
						}
						if (!found) {
							indmap.put(ass.getLeft(), val);
						}
					} else {
						Variable vname = (Variable) ass.getLeft().getRef();
						Integer indmap = varAdd.get(vname);
						if (indmap == null) {
							varAdd.put(vname, val);
						} else {
							varAdd.put(vname, indmap + val);
						}
					}
				}
				List<Statement> newActs = new ArrayList<Statement> ();
				for (Entry<String, Map<VariableReference, Integer>> entry : arrAdd.entrySet()) {
					for (Entry<VariableReference, Integer> entry2 : entry.getValue().entrySet()) {
						VariableReference arr = entry2.getKey();
						Integer val = entry2.getValue();

						if (val != 0) {
							Statement ass = GF2.createIncrement(arr, val); 
							newActs.add(ass);
							//							if (val < 0) {
							//								//ensure guard protects adequately vs negative marking values
							//								// should clear useless assignments
							//								And and = GalFactory.eINSTANCE.createAnd();
							//								and.setLeft(tr.getGuard());
							//								Comparison cmp = GalFactory.eINSTANCE.createComparison();
							//								cmp.setOperator(ComparisonOperators.GE);
							//								cmp.setLeft(EcoreUtil.copy(arr));
							//								cmp.setRight(constant(-val));
							//								and.setRight(cmp);
							//								tr.setGuard(and);
							//							}
						}
					}
				}
				for (Entry<Variable, Integer> entry : varAdd.entrySet()) {
					if (entry.getValue() != 0) {
						VariableReference varRef = GF2.createVariableRef(entry.getKey());
						Statement ass = GF2.createIncrement(varRef , entry.getValue());
						newActs.add(ass);
					}
				}

				for (TreeIterator<EObject> it = tr.getGuard().eAllContents() ; it.hasNext() ; ) {
					EObject obj = it.next();
					if (obj instanceof Comparison) {
						Comparison cmp = (Comparison) obj;
						if (cmp.getOperator()==ComparisonOperators.GE && cmp.getLeft() instanceof VariableReference && cmp.getRight() instanceof Constant) {
							int curval = ((Constant) cmp.getRight()).getValue();
							int val = 0;
							VariableReference av = (VariableReference) cmp.getLeft();
							if (av.getIndex() != null) {
								Map<VariableReference, Integer> map = arrAdd.get(av.getRef().getName());
								if (map != null) {
									for (Entry<VariableReference, Integer> entry : map.entrySet()) {
										if (EcoreUtil.equals(entry.getKey(),av)) {
											val = entry.getValue();
											break;
										}
									}
								}
							} else {
								Integer tmp = varAdd.get(av.getRef()); 
								if (tmp != null) {
									val = tmp; 							
								}
							}
							if (val < 0 && -val > curval) {
								((Constant) cmp.getRight()).setValue(-val);
							}

						}
					}

				}
				simplify(tr.getGuard());

				tr.getActions().clear();
				tr.getActions().addAll(newActs);


			}  else { // if petri style tr
				isPetriStyle = false;
			}
		} // for tr

		return isPetriStyle;
	}

	public static boolean isPetriStyle(Transition tr) {

		for (Statement a : tr.getActions()) {
			if (a instanceof Assignment
					&& ((Assignment) a).getRight() instanceof BinaryIntExpression
					&& ( (  ((BinaryIntExpression) ((Assignment) a).getRight()).getOp().equals("+") 
							||	((BinaryIntExpression) ((Assignment) a).getRight()).getOp().equals("-") )
							&& EcoreUtil.equals(((BinaryIntExpression) ((Assignment) a).getRight()).getLeft(), ((Assignment) a).getLeft() )
							&& ((BinaryIntExpression) ((Assignment) a).getRight()).getRight() instanceof Constant )) {
				// NOP
			} else if (a instanceof Assignment 
					&& ((Assignment) a).getType() != AssignType.ASSIGN 
					&&  ((Assignment) a).getRight() instanceof Constant ) {
				// NOP
			} else if (a instanceof SelfCall) {
				// NOP
			} else 	{
				return false;
			}
		}
		return true;
	}
	static boolean deepEquals = true;
	public static void simplify (BooleanExpression be) {
		GalFactory gf = GalFactory.eINSTANCE;
		if (be instanceof And) {
			And and = (And) be;
			simplify(and.getLeft());
			simplify(and.getRight());
			BooleanExpression left = and.getLeft();
			BooleanExpression right = and.getRight();
			if (left instanceof True) {
				EcoreUtil.replace(be, right);
			} else if (right instanceof True) {
				EcoreUtil.replace(be, left);
			} else if (left instanceof False || right instanceof False) {
				EcoreUtil.replace(be,gf.createFalse());
			} else if (EcoreUtil.equals(left, right)) {
				EcoreUtil.replace(be,EcoreUtil.copy(left));
			}
		} else if (be instanceof Or) {
			Or or = (Or) be;
			simplify(or.getLeft());
			simplify(or.getRight());
			BooleanExpression left = or.getLeft();
			BooleanExpression right = or.getRight();
			if (left instanceof False) {
				EcoreUtil.replace(be, right);
			} else if (right instanceof False) {
				EcoreUtil.replace(be, left);
			} else if (left instanceof True || right instanceof True) {
				EcoreUtil.replace(be,gf.createTrue());
			} else if (EcoreUtil.equals(left, right)) {
				EcoreUtil.replace(be,EcoreUtil.copy(left));
			}
		} else if (be instanceof Not) {
			Not not = (Not) be;
			simplify(not.getValue());
			BooleanExpression left = not.getValue();
			if (left instanceof Not) {
				EcoreUtil.replace(be, ((Not)left).getValue());
			} else if (left instanceof False) {
				EcoreUtil.replace(be, gf.createTrue());
			} else if (left instanceof True) {
				EcoreUtil.replace(be, gf.createFalse());
			} else if (left instanceof Or) {
				Or or = (Or) left;
				// ! (a | b) => !a & !b
				BooleanExpression and = GF2.and(GF2.not(or.getLeft()), GF2.not(or.getRight()));
				EcoreUtil.replace(be, and);
				simplify(and);
			} else if (left instanceof And) {
				And or = (And) left;
				// ! (a & b) => !a | !b
				BooleanExpression and = GF2.or(GF2.not(or.getLeft()), GF2.not(or.getRight()));
				EcoreUtil.replace(be, and);
				simplify(and);
			} else if (left instanceof Comparison) {
				Comparison comp = (Comparison) left;
				simplify(comp);
				switch (comp.getOperator()) {
				case EQ : comp.setOperator(ComparisonOperators.NE); break;
				case NE : comp.setOperator(ComparisonOperators.EQ); break;
				case GE : comp.setOperator(ComparisonOperators.LT); break;
				case GT : comp.setOperator(ComparisonOperators.LE); break;
				case LT : comp.setOperator(ComparisonOperators.GE); break;
				case LE : comp.setOperator(ComparisonOperators.GT); break;
				}
				EcoreUtil.replace(be, comp);
			}
		} else if (be instanceof Comparison) {
			Comparison comp = (Comparison) be;
			simplify(comp.getLeft());
			simplify(comp.getRight());
			IntExpression left = comp.getLeft();
			IntExpression right = comp.getRight();
			if (left instanceof Constant && right instanceof Constant) {
				boolean res = false;
				int l = ((Constant) left).getValue();
				int r = ((Constant) right).getValue();
				switch (comp.getOperator()) {
				case EQ : res = (l==r); break;
				case NE : res = (l!=r); break;
				case GE : res = (l>=r); break;
				case GT : res = (l>r); break;
				case LT : res = (l<r); break;
				case LE : res = (l<=r); break;
				}
				if (res) {
					EcoreUtil.replace(be, gf.createTrue());
				} else {
					EcoreUtil.replace(be, gf.createFalse());
				}
			} else if (deepEquals && EcoreUtil.equals(left, right)) {
				switch (comp.getOperator()) {
				case NE : EcoreUtil.replace(be, GalFactory.eINSTANCE.createFalse()); break;
				case EQ : 
				case GE : 
				case LE : EcoreUtil.replace(be, GalFactory.eINSTANCE.createTrue()); break;
				case GT : break;
				case LT : break;
				}
			}
		} else {
			for (EObject child : be.eContents()) {
				if (child instanceof BooleanExpression) {
					BooleanExpression bec = (BooleanExpression) child;
					simplify(bec);					
				} else if (child instanceof IntExpression) {
					IntExpression iec = (IntExpression) child;
					simplify(iec);
				}
			}
		}
	}

	public static void simplify(IntExpression expr) {
		if (expr instanceof BinaryIntExpression) {
			BinaryIntExpression bin = (BinaryIntExpression) expr;
			simplify(bin.getLeft());
			simplify(bin.getRight());

			IntExpression left = bin.getLeft();
			IntExpression right = bin.getRight();

			if (isConstant(left) && isConstant(right)) {
				int l = getConstantValue(left);
				int r = getConstantValue(right);
				int res=0;
				if ("+".equals(bin.getOp())) {
					res = l + r;
				} else if ("-".equals(bin.getOp())) {
					res = l - r;
				} else if ("/".equals(bin.getOp())) {
					res = l / r;
				} else if ("*".equals(bin.getOp())) {
					res = l * r;
				} else if ("**".equals(bin.getOp())) {
					res = (int) Math.pow(l , r);
				} else if ("%".equals(bin.getOp())) {
					res = l % r;
				} else if ("<<".equals(bin.getOp())) {
					res = l << r;
				} else if (">>".equals(bin.getOp())) {
					res = l >> r;
				} else if ("|".equals(bin.getOp())) {
					res = l | r;
				} else if ("&".equals(bin.getOp())) {
					res = l & r;
				} else if ("^".equals(bin.getOp())) {
					res = l ^ r;
				} else {
					getLog().warning("Unexpected operator in simplify procedure:" + bin.getOp());
				}
				EcoreUtil.replace(bin, GF2.constant(res));
			} else if (isConstant(left)) {
				int l = getConstantValue(left);
				if (l==0 && "+".equals(bin.getOp())) {
					EcoreUtil.replace(bin, right);
				} else if (l==1 && "*".equals(bin.getOp())) {
					EcoreUtil.replace(bin, right);
				}
			} else if (isConstant(right)) {
				int r = getConstantValue(right);
				if (r==0 && "+".equals(bin.getOp())) {
					EcoreUtil.replace(bin, left);
				} else if (r==1 && "*".equals(bin.getOp())) {
					EcoreUtil.replace(bin, left);
				}
			}
			//		} else if (expr instanceof UnaryMinus) {
			//			UnaryMinus minus = (UnaryMinus) expr;
			//			if (minus.getValue() instanceof Constant) {
			//				EcoreUtil.replace(minus, constant(- ((Constant) minus.getValue()).getValue()));
			//			}
		} else if (expr instanceof BitComplement) {
			BitComplement minus = (BitComplement) expr;
			if (minus.getValue() instanceof Constant) {
				EcoreUtil.replace(minus, GF2.constant(~ ((Constant) minus.getValue()).getValue()));
			}
		} else if (expr instanceof VariableReference) {
			VariableReference acc = (VariableReference) expr;
			if (acc.getIndex() != null)
				simplify(acc.getIndex());
		}
	} 


	private static boolean isConstant(IntExpression expr) {
		return (expr instanceof Constant) || (expr instanceof UnaryMinus && ((UnaryMinus) expr).getValue() instanceof Constant);		
	}

	/** Better make sure you call above isConstant before this or you'll get ClassCastEx.*/
	private static int getConstantValue(IntExpression expr) {
		if (expr instanceof Constant) {
			Constant cte = (Constant) expr;
			return cte.getValue();
		} else {
			return -((Constant)((UnaryMinus) expr).getValue()).getValue();
		}
	}

	public static void simplifyImplicitVariables (GALTypeDeclaration system) {

		Map<Transition,Boolean> tguards= new HashMap<Transition, Boolean>();

		Map<ArrayPrefix, Map<Transition, List<IntExpression>>> presets = new HashMap<ArrayPrefix, Map<Transition,List<IntExpression>>> ();
		Map<ArrayPrefix, Map<Transition, List<IntExpression>>> postsets = new HashMap<ArrayPrefix, Map<Transition,List<IntExpression>>> ();

		for (Transition t : system.getTransitions()) {
			tguards.put(t, false);
			for (TreeIterator<EObject> it = t.getGuard().eAllContents(); it.hasNext();) {
				EObject obj = it.next();
				if (obj instanceof Comparison) {
					Comparison cmp = (Comparison) obj;
					if (! (
							(cmp.getLeft() instanceof VariableReference  && ((VariableReference) cmp.getLeft()).getIndex() != null)
							|| (cmp.getRight() instanceof VariableReference  && ((VariableReference) cmp.getRight()).getIndex() != null)
							)) {
						tguards.put(t, true);
						break;
					}
				}
			}
			for (Statement a : t.getActions()) {
				if (a instanceof Assignment) {
					Assignment ass = (Assignment) a;
					VariableReference lhs = ass.getLeft();
					BinaryIntExpression rhs = (BinaryIntExpression)ass.getRight();
					if (lhs.getIndex() != null && rhs instanceof BinaryIntExpression && rhs.getRight() instanceof Constant ) {
						int val = ((Constant) rhs.getRight()).getValue();
						if (val != 1) {
							getLog().warning("Problem with variable value not 1");
						}
						if (rhs.getOp().equals("+")) {
							addToSet ((ArrayPrefix) lhs.getRef(), t, lhs.getIndex(), presets);
						} else {
							addToSet ((ArrayPrefix) lhs.getRef(), t, lhs.getIndex(), postsets);							
						}
					}
				}
			}
		}

		// basically we now have the colored PT structure loaded up: it is accessible through places + guards
		// build a reverse index from t to p
		Map<Transition, List<ArrayPrefix>> tpreset = new HashMap<Transition, List<ArrayPrefix>>();
		Map<Transition, List<ArrayPrefix>> tpostet = new HashMap<Transition, List<ArrayPrefix>>();

		for (Entry<ArrayPrefix, Map<Transition, List<IntExpression>>> entry : presets.entrySet()) {
			for (Transition tr : entry.getValue().keySet()) {
				List<ArrayPrefix> list = tpostet.get(tr);
				if (list == null) {
					list = new ArrayList<ArrayPrefix>();
					tpostet.put(tr, list);
				}
				list.add(entry.getKey());
			}
		}
		for (Entry<ArrayPrefix, Map<Transition, List<IntExpression>>> entry : postsets.entrySet()) {
			for (Transition tr : entry.getValue().keySet()) {
				List<ArrayPrefix> list = tpreset.get(tr);
				if (list == null) {
					list = new ArrayList<ArrayPrefix>();
					tpreset.put(tr, list);
				}
				list.add(entry.getKey());
			}
		}

		int nbagglo= 0;
		// look for places with a single preset and post set
		for (ArrayPrefix ap : system.getArrays()) {
			Map<Transition, List<IntExpression>> pre = presets.get(ap);
			Map<Transition, List<IntExpression>> post = postsets.get(ap);
			if (pre != null && post != null && pre.size() == 1 && post.size() == 1) {
				Entry<Transition, List<IntExpression>> pret = pre.entrySet().iterator().next();
				Entry<Transition, List<IntExpression>> postt = post.entrySet().iterator().next();
				if (! tguards.get(pret.getKey()) && ! tguards.get(postt.getKey()) ) {
					// so we have a single unguarded transitions on both sides
					// check that we have a single token flow
					if ( pret.getValue().size() == 1 && postt.getValue().size() == 1) {

						// check that F (postset) is not enabled by other than current p
						if ( tpreset.get(postt.getKey()).size() == 1) {
							getLog().info("This place :" + ap.getName() + " is candidate for agglomeration.");
							nbagglo++;
						}
					}
				}
			}
		}
		getLog().info("Total places to agglo " + nbagglo);



	}

	private static void addToSet(ArrayPrefix prefix, Transition t, IntExpression index, Map<ArrayPrefix, Map<Transition,List<IntExpression> >> presets) {
		Map<Transition, List<IntExpression>> transMap = presets.get(prefix);
		if (transMap == null) {
			transMap = new HashMap<Transition, List<IntExpression>> ();
			presets.put(prefix, transMap);
		}
		List<IntExpression> list = transMap.get(t);
		if (list == null) {
			list = new ArrayList<IntExpression>();
			transMap.put(t, list);
		}
		list.add(index);
	}

}
