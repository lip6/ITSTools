package fr.lip6.move.gal.instantiate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
import fr.lip6.move.gal.ConstParameter;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.ParamRef;
import fr.lip6.move.gal.Parameter;
import fr.lip6.move.gal.ParameterList;
import fr.lip6.move.gal.System;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.True;

public class Instantiator {

	// to count number of skipped transitions
	private static int nbskipped=0;

	public static System instantiateParameters(System s) throws Exception {

		s.setName(s.getName()+"_flat");
		instantiateTypeParameters(s);

		nbskipped = 0;
		List<Transition> todel = new ArrayList<Transition>();
		List<Transition> done = new ArrayList<Transition>();
		for (Transition t : s.getTransitions()) {
			List<Transition> list = instantiateParameters(t);
			todel.add(t);
			done.addAll(list);
		}
		s.getTransitions().clear();
		s.getTransitions().addAll(done);



		java.lang.System.err.println("On-the-fly reduction of False transitions avoided exploring " + nbskipped + " instantiations of transitions. Total transitions built is " + done.size());

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
		for (Transition t : s.getTransitions()) {
			for (Actions a : t.getActions()) {
				if (a instanceof Call) {
					Call call = (Call) a;
					if (call.getLabel() == null) {
						String targetname = call.getLabel().getName();
					}
					String targetname = call.getLabel().getName();

					Label target =map.get(targetname);
					if (target == null) {
						java.lang.System.err.println("Could not find appropriate target for call to "+targetname);
					}
					call.setLabel(target);
				}
			}
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
			Parameter p = t.getParams().getParamList().get(0);
			int min = -1;
			IntExpression smin = Simplifier.simplify(p.getType().getMin());
			if (smin instanceof Constant) {
				Constant cte = (Constant) smin;
				min = cte.getValue();
			}
			int max = - 1;
			IntExpression smax = Simplifier.simplify(p.getType().getMax());
			if (smax instanceof Constant) {
				Constant cte = (Constant) smax;
				max = cte.getValue();
			}
			if (min == -1 || max == -1) {
				throw new ArrayIndexOutOfBoundsException("Expected constant as both min and max bounds of type def "+p.getType().getName());
			}
			for(int i = min; i <= max; i++){
				BooleanExpression guard = EcoreUtil.copy(t.getGuard());
				instantiateParameter(guard, t.getParams().getParamList().get(0), i);
				guard = Simplifier.simplify(guard);
				// avoid producing copies for False transitions.
				if (guard instanceof False) {
					nbskipped++;
					continue;
				}

				Transition tcopy = EcoreUtil.copy(t);
				Parameter param = tcopy.getParams().getParamList().get(0);
				instantiate(tcopy.getLabel(), param, i);
				instantiateParameter(tcopy,param, i);
				EcoreUtil.delete(param);				
				tcopy.setGuard(Simplifier.simplify(tcopy.getGuard()));
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
		return t.getParams()!=null && t.getParams().getParamList()!=null 
				&& ! t.getParams().getParamList().isEmpty();
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
	public static System separateParameters(System system) {

		List<Transition> toadd = new ArrayList<Transition>();

		if (Simplifier.simplifyPetriStyleAssignments(system)) {
			for (Transition t : system.getTransitions()) {
				if (hasParam(t) && t.getParams().getParamList().size() > 1) {
					Map<BooleanExpression,List<Parameter>> guardedges= new HashMap<BooleanExpression, List<Parameter>>();
					Map<Actions,List<Parameter>> actionedges= new LinkedHashMap<Actions, List<Parameter>>();

					if (addGuardTerms(t.getGuard(),guardedges)) {

						for (Actions a : t.getActions()) {
							List<Parameter> targets = grabParamRefs(a);
							actionedges.put(a, targets);
						}

						// So we now have a hypergraph, with edges relating parameters that are linked through an action or guard condition

						// build a reverse map, with just simple edges to reason on the underlying graph.
						Map<Parameter, Set<Parameter>> neighbors = new HashMap<Parameter, Set<Parameter>>();
						for (Parameter p : t.getParams().getParamList()) {
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
									if (nbnear==1) {
										java.lang.System.err.println("Found a free parameter : " + param.getName());
									} else {
										java.lang.System.err.println("Found a separable parameter : " + param.getName());
										java.lang.System.err.println("It is related to : " + entry.getValue());
									}

									Transition sep = GalFactory.eINSTANCE.createTransition();
									sep.setName(t.getName()+param.getName().replace("$", ""));
									ParameterList pl = GalFactory.eINSTANCE.createParameterList();
									Map<Parameter,Parameter> paramMap = new HashMap<Parameter,Parameter>();
									for (Parameter p : entry.getValue()) {
										Parameter copy = EcoreUtil.copy(p);
										paramMap.put(p, copy);
										pl.getParamList().add(copy);
									}
									sep.setParams(pl);

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
									t.getParams().getParamList().remove(param);


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
										Parameter other = null;
										for (Parameter pother : entry.getValue()) {
											if (pother!=param)
												other = pother;
										}
										used.add(other);	

										lab.setName(sep.getName() + "_" + other.getName());
									}
									sep.setLabel(lab);
									toadd.add(sep);
									Call call = GalFactory.eINSTANCE.createCall();
									call.setLabel(lab);
									t.getActions().add(call);

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

		normalizeCalls(system);
		return system;
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

		s.setName(s.getName()+"_nocolor");
		instantiateTypeParameters(s);

		for (TreeIterator<EObject> it = s.eAllContents(); it.hasNext();) {
			EObject obj = it.next();

			if (obj instanceof ArrayPrefix) {
				ArrayPrefix ap = (ArrayPrefix) obj;
				ap.setSize(1);
				int sum =0;
				for (IntExpression e : ap.getValues().getValues()) {
					IntExpression eprime = Simplifier.simplify(e);
					if (eprime instanceof Constant) {
						Constant cte = (Constant) eprime;
						sum += cte.getValue();
					}
				}
				ap.getValues().getValues().clear();
				ap.getValues().getValues().add(constant(sum));

			} else if (obj instanceof ArrayVarAccess) {
				ArrayVarAccess av = (ArrayVarAccess) obj;
				av.setIndex(constant(0));
			}
		}

		for (Transition t : s.getTransitions()) {
			t.getParams().getParamList().clear();
		}

		return s;
	}

	private static IntExpression constant(int val) {
		Constant toret = GalFactory.eINSTANCE.createConstant();
		toret.setValue(val);
		return toret;
	}



}