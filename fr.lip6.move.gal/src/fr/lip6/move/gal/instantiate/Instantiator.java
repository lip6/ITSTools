package fr.lip6.move.gal.instantiate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.AbstractParameter;
import fr.lip6.move.gal.Actions;
import fr.lip6.move.gal.And;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.ArrayVarAccess;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Call;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.ConstParameter;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.Not;
import fr.lip6.move.gal.ParamRef;
import fr.lip6.move.gal.Parameter;
import fr.lip6.move.gal.System;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.True;

public class Instantiator {

	// to count number of skipped transitions
	private static int nbskipped=0;

	public static System instantiateParameters(System s) throws Exception {

		
		instantiateTypeParameters(s);

		nbskipped = 0;
		
		List<Transition> done = new ArrayList<Transition>();
		for (Transition t : s.getTransitions()) {
			List<Transition> list = instantiateParameters(t);
			done.addAll(list);
		}
		s.getTransitions().clear();
		s.getTransitions().addAll(done);

		java.lang.System.err.println("On-the-fly reduction of False transitions avoided exploring " + nbskipped + " instantiations of transitions. Total transitions built is " + done.size());

		if (nbskipped > 0) {
			List<Transition> todel = new ArrayList<Transition>();
			// we might have destroyed labeled transitions that were called.
			normalizeCalls(s);
			// propagate the destruction
			for (Transition t : s.getTransitions()) {
				for (Actions a : t.getActions()) {
					if (a instanceof Call) {
						Call call = (Call) a;
						if (call.getLabel().eContainer() == null ||
								call.getLabel().eContainer().eContainer() != s) {
							// Was probably destroyed
							todel.add(t);
							break;
						}
					}
				}
			}
			if (! todel.isEmpty()) {
				s.getTransitions().removeAll(todel);
				
				java.lang.System.err.println("False transitions propagation removed an additional " + todel.size() + " instantiations of transitions. total transiitons in result is "+ s.getTransitions().size());

			}
		
		}
		
		normalizeCalls(s);
		return s;
	}

	public static void normalizeCalls(System s) {
		Map<String,Label> map = new HashMap<String, Label>();
		for (Transition t : s.getTransitions()) {
			if (t.getLabel() != null && ! map.containsKey(t.getLabel().getName()) ) {
				map.put(t.getLabel().getName(), t.getLabel());
			}
		}
		List<Transition> todel = new ArrayList<Transition>();
		for (Transition t : s.getTransitions()) {
			for (Actions a : t.getActions()) {
				if (a instanceof Call) {
					Call call = (Call) a;
					String targetname = call.getLabel().getName();

					Label target =map.get(targetname);
					if (target == null) {
						java.lang.System.err.println("Could not find appropriate target for call to "+targetname+ " . Assuming it was false/destroyed and killing "+ t.getName());
						todel.add(t);
						continue;
					}
					call.setLabel(target);
				}
			}
		}
		if (! todel.isEmpty()) {
			java.lang.System.err.println("False transition propagation eliminated "+todel.size()+ " transitions.");
			s.getTransitions().removeAll(todel);
		}
	}

	private static void instantiateTypeParameters(System s) {
		if (!s.getParams().isEmpty()) {
			for (TreeIterator<EObject> it = s.eAllContents() ; it.hasNext() ; ) {
				EObject obj = it.next();
				if (obj instanceof ParamRef) {
					ParamRef pr = (ParamRef) obj;
					if (pr.getRefParam() instanceof ConstParameter) {
						Constant cte = GalFactory.eINSTANCE.createConstant();
						cte.setValue(((ConstParameter) pr.getRefParam()).getValue());
						EcoreUtil.replace(obj, cte);
					}
				}
			}
		}
		s.getParams().clear();
		s.getTypes().clear();
	}

	public static List<Transition> instantiateParameters(Transition toinst) {

		java.util.List<Transition> todo  = new ArrayList<Transition>();
		java.util.List<Transition> done  = new ArrayList<Transition>();
		if (hasParam(toinst)) {
			todo.add(toinst);
		} else {
			done.add(EcoreUtil.copy(toinst));
		}
		while (! todo.isEmpty()) {
			Transition t = todo.remove(0);
			Parameter p = t.getParams().get(0);
			int min = -1;
			Simplifier.simplify(p.getType().getMin());
			IntExpression smin = p.getType().getMin();
			if (smin instanceof Constant) {
				Constant cte = (Constant) smin;
				min = cte.getValue();
			}
			int max = - 1;
			Simplifier.simplify(p.getType().getMax());
			IntExpression smax = p.getType().getMax();
			if (smax instanceof Constant) {
				Constant cte = (Constant) smax;
				max = cte.getValue();
			}
			if (min == -1 || max == -1) {
				throw new ArrayIndexOutOfBoundsException("Expected constant as both min and max bounds of type def "+p.getType().getName());
			}
			for(int i = min; i <= max; i++){
				BooleanExpression guard = EcoreUtil.copy(t.getGuard());
				instantiateParameter(guard, t.getParams().get(0), i);
				
				Not not = GalFactory.eINSTANCE.createNot();
				not.setValue(guard);
				Simplifier.simplify(guard);
				guard = not.getValue();
				
				// avoid producing copies for False transitions.
				if (guard instanceof False) {
					nbskipped++;
					continue;
				}

				Transition tcopy = EcoreUtil.copy(t);
				Parameter param = tcopy.getParams().get(0);
				instantiate(tcopy.getLabel(), param, i);
				instantiateParameter(tcopy,param, i);
				EcoreUtil.delete(param);				
				Simplifier.simplify(tcopy.getGuard());
				tcopy.setName(tcopy.getName()+"_"+ i );
				if (hasParam(tcopy)) {
					todo.add(tcopy);
				} else {
					done.add(tcopy);
				}
			}
		}
		return done;
	}

	private static boolean hasParam(Transition t) {
		return t.getParams()!=null && ! t.getParams().isEmpty();
	}

	private static void instantiateParameter(EObject src, AbstractParameter param, int value) {
		for (TreeIterator<EObject> it = src.eAllContents(); it.hasNext();) {
			EObject obj = it.next();

			if (obj instanceof ParamRef) {
				ParamRef pr = (ParamRef) obj;
				if (pr.getRefParam().equals(param)) {
					EcoreUtil.replace(obj, constant(value));
				}
			} else if (obj instanceof Call) {
				Call call = (Call) obj;
				Label target = GalFactory.eINSTANCE.createLabel();
				target.setName(call.getLabel().getName());
				instantiate(target, param, value);
				call.setLabel(target);
			}
		}
	}

	private static void instantiate(Label label, AbstractParameter param, int i) { 
		String paramStr = param.getName();
		if (label != null) {
			label.setName( label.getName().replace(paramStr, Integer.toString(i)));
		}
	}
	
	public static System fuseIsomorphicEffects (System system) {
		sortParameters(system);
		
		Map<Label,Label> labelMap = new HashMap<Label, Label>();
		int nbremoved = 0;
		// test all pairs
		for (int i=0; i < system.getTransitions().size() ; ++i ) {
			for (int j=i+1; j < system.getTransitions().size() ; ++j ) {
				Transition t1 = system.getTransitions().get(i);
				Transition t2 = system.getTransitions().get(j);
				
				if (	t1.getLabel() != null && t2.getLabel() != null
						&& t1.getActions().size() == t2.getActions().size()
						&& t1.getParams() !=null && t2.getParams() != null
						&& t1.getParams().size() == t2.getParams().size() ) {
					EList<Parameter> pl1 = t1.getParams();
					EList<Parameter> pl2 = t2.getParams();
					
					int size = pl1.size();
					boolean areCompat = true;
					for (int k = 0 ; k < size ; k++) {
						if (pl1.get(k).getType() != pl2.get(k).getType()) {
							areCompat = false;
							break;
						}
					}
					if (!areCompat)
						break;
					
					// looks good, labeled transitions, same number of parameters, with pair wise type match, same number of actions
					Transition t2copy = EcoreUtil.copy(t2);
					// Attempt a rename + relabel.					
					t2copy.setLabel(EcoreUtil.copy(t1.getLabel()));
					t2copy.setName(t1.getName());
					// rename parameters
					pl2 = t2copy.getParams();
					for (int k = 0 ; k < size ; k++) {
						pl2.get(k).setName(pl1.get(k).getName());
					}
					// test for identity : this test should be true if the two transitions actually have the same body
					if (EcoreUtil.equals(t1, t2copy)) {
						// So test is successful : we can happily discard t2, provided we update calls
						EcoreUtil.remove(t2);
						labelMap.put(t2.getLabel(), t1.getLabel());
						// to ensure correct position in t1/t2 loop
						j--;
						
						nbremoved ++;
					}
					
				}
				
			}
		}
		
		
		if (nbremoved > 0) {
			java.lang.System.err.println("Removed a total of "+nbremoved + " redundant transitions.");
			for (TreeIterator<EObject> it = system.eAllContents() ; it.hasNext() ;  ) {
				EObject obj = it.next();
				if (obj instanceof Call) {
					Call call = (Call) obj;
					Label target = labelMap.get(call.getLabel()) ;
					if (target != null) {
						call.setLabel(target);
					}
				}
			}
		}
		return system;
	}
	
	public static System separateParameters(System system) {

		// sortParameters(system);
		
		
		List<Transition> toadd = new ArrayList<Transition>();

		if (Simplifier.simplifyPetriStyleAssignments(system)) {
			for (Transition t : system.getTransitions()) {
				if (hasParam(t) && t.getParams().size() >= 1) {
					Map<BooleanExpression,List<Parameter>> guardedges= new HashMap<BooleanExpression, List<Parameter>>();
					Map<Actions,List<Parameter>> actionedges= new LinkedHashMap<Actions, List<Parameter>>();

					if (addGuardTerms(t.getGuard(),guardedges)) {
						
						
						// We might have equality of two params in guard... refactor to only have one param
						List<BooleanExpression> todel =new ArrayList<BooleanExpression>();
						
						for (Entry<BooleanExpression, List<Parameter>> ent : guardedges.entrySet()) {
							BooleanExpression term = ent.getKey();
							if (term instanceof Comparison) {
								Comparison cmp = (Comparison) term;
								
								if (cmp.getOperator()== ComparisonOperators.EQ && cmp.getLeft() instanceof ParamRef && cmp.getRight() instanceof ParamRef) {
									AbstractParameter p1 = ((ParamRef)cmp.getLeft()).getRefParam();
									AbstractParameter p2 = ((ParamRef)cmp.getRight()).getRefParam();
									// set guard term to true
									todel.add(cmp);
									// map all refs to p2 to p1
									for (TreeIterator<EObject> it = t.eAllContents(); it.hasNext() ; ) {
										EObject obj = it.next();
										if (obj instanceof ParamRef) {
											ParamRef pr = (ParamRef) obj;
											if (pr.getRefParam() == p2) {
												pr.setRefParam(p1);
											}
										}
									}
									// drop p2
									t.getParams().remove(p2);
									java.lang.System.err.println("Fused parameters : " + p1.getName() +" and " + p2.getName());
								}
							}
						}
						
						if (!todel.isEmpty()) {
							for (BooleanExpression be : todel) {
								EcoreUtil.replace(be, GalFactory.eINSTANCE.createTrue());
							}
							todel.clear();
							guardedges.clear();
							addGuardTerms(t.getGuard(), guardedges);
						}
						
						
						for (Actions a : t.getActions()) {
							List<Parameter> targets = grabParamRefs(a);
							actionedges.put(a, targets);
						}

						// So we now have a hypergraph, with edges relating parameters that are linked through an action or guard condition

						// build a reverse map, with just simple edges to reason on the underlying graph.
						Map<Parameter, Set<Parameter>> neighbors = new LinkedHashMap<Parameter, Set<Parameter>>();
						for (Parameter p : t.getParams()) {
							neighbors.put(p, new HashSet<Parameter>());
						}
						for (List<Parameter> edge : guardedges.values()) {
							for (Parameter p1 : edge) {
								for (Parameter p2 : edge) {
									//if (p1 != p2)
									neighbors.get(p1).add(p2);
								}
							}

						}
						for (List<Parameter> edge : actionedges.values()) {
							for (Parameter p1 : edge) {
								for (Parameter p2 : edge) {
									//if (p1 != p2)
									neighbors.get(p1).add(p2);
								}
							}
						}

						// So neighbors now tells us who is connected and how strongly 
						Set<Parameter> used = new HashSet<Parameter>();
						for (Entry<Parameter, Set<Parameter>> entry : neighbors.entrySet()) {
							int nbnear = entry.getValue().size();
							Parameter param = entry.getKey();
							if (! used.contains(param)) {
								if (nbnear <= 2) {
									Parameter other = null;
									if (nbnear==1) {
										java.lang.System.err.println("Found a free parameter : " + param.getName());
									} else {
										for (Parameter pother : entry.getValue()) {
											if (pother!=param)
												other = pother;
										}
//										if (neighbors.get(other).size() == 2) {
//											java.lang.System.err.println("Skipping parameter : " + param.getName());
//											java.lang.System.err.println("It is in binary relation with  : " + other.getName());
//											continue;
//										}
										java.lang.System.err.println("Found a separable parameter : " + param.getName());
										java.lang.System.err.println("It is related to : " + other.getName());
									}

									Transition sep = GalFactory.eINSTANCE.createTransition();
									sep.setName(t.getName()+param.getName().replace("$", ""));
									Map<Parameter,Parameter> paramMap = new HashMap<Parameter,Parameter>();
									for (Parameter p : entry.getValue()) {
										Parameter copy = EcoreUtil.copy(p);
										paramMap.put(p, copy);
										sep.getParams().add(copy);
									}
									

									True tru =  GalFactory.eINSTANCE.createTrue();
									BooleanExpression guard =tru;
									List<BooleanExpression> todrop = new ArrayList<BooleanExpression>();
									for (Iterator<Entry<BooleanExpression, List<Parameter>>> it = guardedges.entrySet().iterator() ; it.hasNext() ;) {
										Entry<BooleanExpression, List<Parameter>> guardelt = it.next();
										if (guardelt.getValue().contains(param)) {
											BooleanExpression elt =EcoreUtil.copy(guardelt.getKey()) ;										
											todrop.add(guardelt.getKey());
											if (guard == tru) {
												guard = elt;
											} else {
												And and = GalFactory.eINSTANCE.createAnd();
												and.setLeft(guard);
												and.setRight(elt);
												guard = and;
											}
											//it.remove();
										}
									}
									for (BooleanExpression be : todrop) {
										guardedges.remove(be);
									}
									sep.setGuard(guard);

									List<Actions> toremove = new ArrayList<Actions>();
									for (Iterator<Entry<Actions, List<Parameter>>> it = actionedges.entrySet().iterator() ; it.hasNext() ;) {
										Entry<Actions, List<Parameter>> actelt = it.next();
										if (actelt.getValue().contains(param)) {
											Actions elt =EcoreUtil.copy(actelt.getKey()) ; 
											sep.getActions().add(elt);
											toremove.add(actelt.getKey());
											//it.remove();
										}
									}
									for (Actions a : toremove) {
										actionedges.remove(a);
										t.getActions().remove(a);
									}
									t.getParams().remove(param);


									// normalize refs
									for (TreeIterator<EObject> it = sep.eAllContents() ; it.hasNext() ; ) {
										EObject obj = it.next();
										if (obj instanceof ParamRef) {
											ParamRef pr = (ParamRef) obj;
											if (pr.getRefParam() instanceof Parameter) {
												Parameter pold = (Parameter) pr.getRefParam();
												pr.setRefParam(paramMap.get(pold));
											}
										}
									}

									Label lab = GalFactory.eINSTANCE.createLabel();

									if (nbnear==1) { 
										lab.setName(sep.getName());
										
									} else {
//										used.add(other);	
										neighbors.get(other).remove(param);
										lab.setName(sep.getName() + "_" + other.getName());
									}
									sep.setLabel(lab);
									toadd.add(sep);
									Call call = GalFactory.eINSTANCE.createCall();
									call.setLabel(lab);
									t.getActions().add(call);
									actionedges.put(call, Collections.singletonList(other));

								} else {
									java.lang.System.err.println("Found a deeply bound parameter : " + entry.getKey().getName());
								}
							}
						}

						// rebuild t guard
						True tru =  GalFactory.eINSTANCE.createTrue();
						BooleanExpression guard =tru;
						for (BooleanExpression be : guardedges.keySet()) {
							be = EcoreUtil.copy(be);
							if (guard == tru) {
								guard = be;
							} else {
								And and = GalFactory.eINSTANCE.createAnd();
								and.setLeft(guard);
								and.setRight(be);
								guard = and;
							}
						}
						t.setGuard(guard);

					}
				}
			}
		}

		system.getTransitions().addAll(toadd);
		
		fuseIsomorphicEffects(system);

		normalizeCalls(system);
		return system;
	}
	private static void sortParameters(System system) {
		// sorting parameters helps identify repeated structures.
		for (Transition t : system.getTransitions()) {
			if (t.getParams() != null) {
				List<Parameter> plist = new ArrayList<Parameter>(t.getParams());
				Collections.sort(plist, new Comparator<Parameter>() {

					@Override
					public int compare(Parameter p1, Parameter p2) {
						int tc= p1.getType().getName().compareTo(p2.getType().getName());
						if (tc != 0 )
							return tc;
						return p1.getName().compareTo(p2.getName());
					}
				});
				t.getParams().clear();
				t.getParams().addAll(plist);
			}
		}
	}

	/**
	 * Check that guard is a conjunction of conditions, and add the dependencies induced on parameters to them.
	 * @param guard
	 * @param guardedges
	 * @return
	 */
	private static boolean addGuardTerms(BooleanExpression guard,	Map<BooleanExpression, List<Parameter>> guardedges) {
		if (guard instanceof And) {
			And and = (And) guard;
			return addGuardTerms(and.getLeft(), guardedges) && addGuardTerms(and.getRight(), guardedges);				
		} else if (guard instanceof Comparison) {
			Comparison cmp = (Comparison) guard;

			List<Parameter> targets = grabParamRefs(cmp);

			guardedges.put(cmp, targets);
			return true;
		} 

		return false;
	}

	private static List<Parameter> grabParamRefs(EObject cmp) {
		List<Parameter> targets = new ArrayList<Parameter>();
		for (TreeIterator<EObject> it = cmp.eAllContents() ; it.hasNext() ; ) {
			EObject obj = it.next();
			if (obj instanceof ParamRef) {
				ParamRef pr = (ParamRef) obj;
				if (pr.getRefParam() instanceof Parameter) {
					if (!targets.contains(pr.getRefParam())) {
						targets.add((Parameter) pr.getRefParam());
					}
				}
			}
		}
		return targets;
	}

	public static System instantiateParametersWithAbstractColors(System s) {

		
		instantiateTypeParameters(s);

		for (TreeIterator<EObject> it = s.eAllContents(); it.hasNext();) {
			EObject obj = it.next();

			if (obj instanceof ArrayPrefix) {
				ArrayPrefix ap = (ArrayPrefix) obj;
				ap.setSize(1);
				int sum =0;
				for (IntExpression e : ap.getValues()) {
					Simplifier.simplify(e);
				}
				for (IntExpression e : ap.getValues()) {
					
					if (e instanceof Constant) {
						Constant cte = (Constant) e;
						sum += cte.getValue();
					}
				}
				ap.getValues().clear();
				ap.getValues().add(constant(sum));

			} else if (obj instanceof ArrayVarAccess) {
				ArrayVarAccess av = (ArrayVarAccess) obj;
				av.setIndex(constant(0));
			}
		}

		for (Transition t : s.getTransitions()) {
			t.getParams().clear();
		}

		return s;
	}

	private static IntExpression constant(int val) {
		Constant toret = GalFactory.eINSTANCE.createConstant();
		toret.setValue(val);
		return toret;
	}



}