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
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Call;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.ConstParameter;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.For;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.Not;
import fr.lip6.move.gal.ParamRef;
import fr.lip6.move.gal.Parameter;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.TypedefDeclaration;
import fr.lip6.move.gal.VarAccess;
import fr.lip6.move.gal.VarDecl;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableRef;

public class Instantiator {

	private static final int HOTBIT_THRESHOLD = 8;
	// to count number of skipped transitions
	private static int nbskipped=0;

	public static void instantiateParameters(Specification spec)  {

		instantiateTypeParameters(spec);

		//		instantiateHotBit(spec);

		nbskipped = 0;

		for (TypeDeclaration td : spec.getTypes()) {
			if (td instanceof GALTypeDeclaration) {
				GALTypeDeclaration s = (GALTypeDeclaration) td;


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
				// We should no longer need these typedefs.
				// s.getTypes().clear();
			}
		}

		// We should no longer need these typedefs.
		// spec.getTypedefs().clear();

		normalizeCalls(spec);
	}


	public static void instantiateHotBit(Specification spec) {

		List<Variable> todel = new ArrayList<Variable>();
		for (TypeDeclaration td : spec.getTypes()) {
			if (td instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) td;
				for (Variable var : gal.getVariables()) {
					if (var.isHotbit()) {
						TypedefDeclaration type = var.getHottype();
						Bounds b = computeBounds(type); 

						Label labresets = null;

						ArrayPrefix ap = GalFactory.eINSTANCE.createArrayPrefix();
						ap.setName(var.getName());
						int size = b.max - b.min + 1;
						ap.setSize(size);
						int pos = evalConst(var.getValue());
						for (int i = 0; i < size ; i++ ) {
							if (i != pos) {
								ap.getValues().add(constant(0));
							} else {
								ap.getValues().add(constant(1));								
							}
						}

						gal.getArrays().add(ap);
						// call if write before any read occurs
						if (labresets == null) {
							labresets = generateResets (ap, gal, type);
						}


						for (Transition tr : gal.getTransitions()) {
							Parameter param = GalFactory.eINSTANCE.createParameter();
							param.setName("$" + var.getName());
							param.setType(type);

							ParamRef pref = GalFactory.eINSTANCE.createParamRef();
							pref.setRefParam(param);

							ArrayVarAccess av = GalFactory.eINSTANCE.createArrayVarAccess();
							av.setPrefix(ap);
							av.setIndex(pref);

							//							boolean hasRead = false;
							//							List<EObject> readwrites = new ArrayList<EObject>();

							// iterate in order
							int k = replaceAllVarRefs(tr.getGuard(), var, pref);

							boolean waswritten = false;
							Actions addbefore=null;
							Actions toadd=null;
							for (Actions a : tr.getActions()) {
								if (!waswritten) {
									if (a instanceof Assignment) {
										Assignment ass = (Assignment) a;
										if (ass.getLeft() instanceof VariableRef) {
											VariableRef va = (VariableRef) ass.getLeft();
											if (va.getReferencedVar() == var) {
												k += replaceAllVarRefs(ass.getRight(), var, pref);

												ArrayVarAccess av2 = EcoreUtil.copy(av);
												av2.setIndex(ass.getRight());
												ass.setRight(constant(1));
												ass.setLeft(av2);

												if (k >0) {
													// read before write													
													toadd = createAssignment(EcoreUtil.copy(av),constant(0));
												} else {
													Call call = GalFactory.eINSTANCE.createCall();
													call.setLabel(labresets);
													toadd = call;
												}
												addbefore = ass;
												waswritten = true;
												continue;
											}
										}
									}
									k += replaceAllVarRefs(a, var, pref);
								} else {
									int kprime =  replaceAllVarRefs(a, var, pref);
									if (kprime >0) {
										throw new ArrayIndexOutOfBoundsException("Read after write on hotbit variable is not allowed.");
									}
								}
							}

							// write test : add outside loop to avoid concurrentModifEx
							if (waswritten) {
								tr.getActions().add(tr.getActions().indexOf(addbefore), toadd);
							}


							// if k>0, variable is read at least once
							if (k > 0) {
								tr.getParams().add(param);


								tr.setGuard(and(tr.getGuard(), 
										createComparison(EcoreUtil.copy(av), ComparisonOperators.EQ, constant(1))));								
							}														
						}
						todel .add(var);
					}

				}
				gal.getVariables().removeAll(todel);

				List<ArrayPrefix> toaddArrays = new ArrayList<ArrayPrefix>();
				List<ArrayPrefix> todelArrays = new ArrayList<ArrayPrefix>();


				for (ArrayPrefix array : gal.getArrays()) {
					if (array.isHotbit()) {
						TypedefDeclaration type = array.getHottype();
						Bounds b = computeBounds(type); 


						ArrayPrefix ap = GalFactory.eINSTANCE.createArrayPrefix();
						ap.setName("hot"+array.getName());
						int size = b.max - b.min + 1;
						ap.setSize(size*array.getSize());

						for (IntExpression value : array.getValues()) {
							int pos = evalConst(value);
							for (int i = 0; i < size ; i++ ) {
								if (i != pos) {
									ap.getValues().add(constant(0));
								} else {
									ap.getValues().add(constant(1));								
								}
							}
						}
						toaddArrays.add(ap);
						todelArrays.add(array);


						for (Transition tr : gal.getTransitions()) {

							// first collect all references to the target array
							List<ArrayVarAccess> accesses = new ArrayList<ArrayVarAccess>();
							// one list per unique expression, references indexes of accesses to this expression
							List<List<Integer>> accessPerExpr = new ArrayList<List<Integer>>();

							// Explore children of transition
							// identify duplicates 
							collectAccesses(array, tr, accesses, accessPerExpr);

							int currentIndex = 0;
							//for each unique array access expression tab[i]
							// create a parameter
							// 	map various accesses onto their appropriate parameter

							// with a list of all accesses properly sorted,
							// i.e. rhs before lhs in case of assignment

							// reads and writes are evaluated per unique array access expression

							for (List<Integer> accList : accessPerExpr) {

								boolean isRead = false;
								boolean isWritten = false;
								ParamRef pref = null;

								for (Integer accIndex : accList) {
									ArrayVarAccess access = accesses.get(accIndex);
									boolean isWriteAccess = ( access.eContainer() instanceof Assignment 
											&& ((Assignment) access.eContainer()).getLeft() == access);

									if (isWritten) {
										throw new UnsupportedOperationException("Cannot read or write a hotbit variable after it is assigned.");
									}

									if (!isWriteAccess && !isRead) {										
										// first read 
										// + for first read add to guard : && tab[i * size + $ptabi]==1 + set isRead=true

										// create a parameter with hotbit range
										Parameter param = GalFactory.eINSTANCE.createParameter();
										param.setName("$" + array.getName() + currentIndex);
										param.setType(type);

										tr.getParams().add(param);

										// create an expression tab[i*|r| + ptabi]
										pref = GalFactory.eINSTANCE.createParamRef();
										pref.setRefParam(param);

										ArrayVarAccess av = GalFactory.eINSTANCE.createArrayVarAccess();
										av.setPrefix(ap);
										// build expression : i*|r| + ptabi
										BinaryIntExpression mult = GalFactory.eINSTANCE.createBinaryIntExpression();
										mult.setLeft(EcoreUtil.copy(access.getIndex()));
										mult.setOp("*");
										mult.setRight(constant(size));

										BinaryIntExpression plus = GalFactory.eINSTANCE.createBinaryIntExpression();										
										plus.setLeft(mult);
										plus.setOp("+");
										plus.setRight(EcoreUtil.copy(pref));
										av.setIndex(plus);

										// Add guard constraint
										tr.setGuard(and(tr.getGuard(), 
												createComparison(EcoreUtil.copy(av), ComparisonOperators.EQ, constant(1))));

									}
									if (!isWriteAccess) {

										// any read : replace access by parameter
										// for each read before a write, replace 
										// tab[i] -> $ptabi   

										EcoreUtil.replace(access, EcoreUtil.copy(pref));
										isRead = true;
									} else {

										if (! isRead) {
											// for each write before read, replace
											// tab[i]=e -> "reset(i)" = for ($it : type) { tab[i*size + $it]=0; } ; tab[i*size+e] = 1;

											// reset all bits : use a for loop
											Parameter it = GalFactory.eINSTANCE.createParameter();
											it.setName("$" +"it" + array.getName() + currentIndex);
											it.setType(type);

											ParamRef pitref = GalFactory.eINSTANCE.createParamRef();
											pitref.setRefParam(it);

											ArrayVarAccess ava = GalFactory.eINSTANCE.createArrayVarAccess();
											ava.setPrefix(ap);
											// build expression : i*|r| + ptabi
											BinaryIntExpression mult = GalFactory.eINSTANCE.createBinaryIntExpression();
											mult.setLeft(EcoreUtil.copy(access.getIndex()));
											mult.setOp("*");
											mult.setRight(constant(size));
											BinaryIntExpression plus = GalFactory.eINSTANCE.createBinaryIntExpression();										
											plus.setLeft(mult);
											plus.setOp("+");
											plus.setRight(pitref);
											ava.setIndex(plus);

											Assignment ass = GalFactory.eINSTANCE.createAssignment();
											ass.setLeft(ava);
											ass.setRight(constant(0));

											For forloop = GalFactory.eINSTANCE.createFor();
											forloop.setParam(it);
											forloop.getActions().add(ass);

											// insert before assignment
											Assignment parent = (Assignment) access.eContainer();
											@SuppressWarnings("unchecked")
											EList<Actions> statementList = (EList<Actions>) parent.eContainer().eGet(parent.eContainmentFeature());
											int index = statementList.indexOf(parent);
											statementList.add(index, forloop);
										} else {
											// for each write after a read replace,
											// tab[i] = e -> tab[i * size + $ptabi]=0 ; tab[ i*size + e ] = 1;


											// Was read; just reset tab[i*|r|+ ptabi]
											// variable was read before and is now updated
											Assignment resetCur = GalFactory.eINSTANCE.createAssignment();
											ArrayVarAccess av = GalFactory.eINSTANCE.createArrayVarAccess();
											av.setPrefix(ap);
											// build expression : i*|r| + ptabi
											BinaryIntExpression mult = GalFactory.eINSTANCE.createBinaryIntExpression();
											mult.setLeft(EcoreUtil.copy(access.getIndex()));
											mult.setOp("*");
											mult.setRight(constant(size));
											BinaryIntExpression plus = GalFactory.eINSTANCE.createBinaryIntExpression();										
											plus.setLeft(mult);
											plus.setOp("+");
											plus.setRight(EcoreUtil.copy(pref));
											av.setIndex(plus);

											resetCur.setLeft(av);
											resetCur.setRight(constant(0));

											// insert before assignment
											Assignment parent = (Assignment) access.eContainer();
											@SuppressWarnings("unchecked")
											EList<Actions> statementList = (EList<Actions>) parent.eContainer().eGet(parent.eContainmentFeature());
											int index = statementList.indexOf(parent);
											statementList.add(index, resetCur);

										}
										// in any case update the assignment
										Assignment parent = (Assignment) access.eContainer();
										access.setPrefix(ap);
										// build expression : i*|r| + rhs
										BinaryIntExpression mult2 = GalFactory.eINSTANCE.createBinaryIntExpression();
										mult2.setLeft(EcoreUtil.copy(access.getIndex()));
										mult2.setOp("*");
										mult2.setRight(constant(size));
										BinaryIntExpression plus2 = GalFactory.eINSTANCE.createBinaryIntExpression();										
										plus2.setLeft(mult2);
										plus2.setOp("+");
										plus2.setRight(parent.getRight());
										access.setIndex(plus2);

										parent.setRight(constant(1));
										isWritten = true;											
									}
								}

								// next list of expressions
								currentIndex++;
							}



							// for each read after write replace
							// tab[i] by (updated) rhs e of previous write (and pray it hasnt been modified since ??)							
							// write after read after write unsupported within transition body.
							// Do we really need this ?


						}


					}

				}

				for (ArrayPrefix ap : todelArrays){
					gal.getArrays().remove(ap);
				}
				for (ArrayPrefix ap : toaddArrays) {
					gal.getArrays().add(ap);
				}
			}
		}
	}

	/**
	 * Collect references to a given array, in order in accesses, ensuring rhs is explored before lhs in assignments.
	 * Fuses identical expressions into accessesPerExpr.
	 * @param targetArray
	 * @param parent
	 * @param accesses
	 * @param accessPerExpr
	 */
	private static void collectAccesses(ArrayPrefix targetArray,
			EObject parent, List<ArrayVarAccess> accesses,
			List<List<Integer>> accessPerExpr) {

		if (parent instanceof Assignment) {
			Assignment ass = (Assignment) parent;
			//explore rhs before lhs
			collectAccesses(targetArray, ass.getRight(), accesses, accessPerExpr);
			collectAccesses(targetArray, ass.getLeft(), accesses, accessPerExpr);
			// skip children they are already explored
		} else if (parent instanceof Transition) {
			Transition tr = (Transition) parent;
			//explore guard before statement
			collectAccesses(targetArray, tr.getGuard(), accesses, accessPerExpr);
			for (Actions a : tr.getActions()) {
				collectAccesses(targetArray, a, accesses, accessPerExpr);
			}
		} else if (parent instanceof ArrayVarAccess) {
			ArrayVarAccess potav = (ArrayVarAccess) parent;
			if (potav.getPrefix() == targetArray) {
				accesses.add(potav);

				boolean found = false;
				for (List<Integer> listAcess : accessPerExpr) {
					if (EcoreUtil.equals(accesses.get(listAcess.get(0)), potav)) {
						listAcess.add(accesses.size()-1);
						found = true;
						break;
					}
				}
				if (!found) {
					ArrayList<Integer> al = new ArrayList<Integer>();
					al.add(accesses.size()-1);
					accessPerExpr.add(al);
				}

			}
		} else {
			for (EObject obj : parent.eContents() ) {
				collectAccesses(targetArray, obj, accesses, accessPerExpr);				
			}				
		}

	}


	/**
	 * Just a trick to get foreach loops on Eobjects.
	 * Deep iteration into all contents.
	 * @param parent a parent of a subtree to explore
	 * @return an iterable appropriate for use in foreach loop
	 */
	static Iterable<EObject> getAllChildren(final EObject parent) {
		return new Iterable<EObject>() {
			@Override
			public Iterator<EObject> iterator() {
				return parent.eAllContents();
			}
		};
	}


	private static Comparison createComparison(ArrayVarAccess l, ComparisonOperators op, IntExpression r) {
		Comparison cmp = GalFactory.eINSTANCE.createComparison();
		cmp.setLeft(l);
		cmp.setOperator(op);
		cmp.setRight(r);
		return cmp;
	}


	private static Actions createAssignment(VarAccess lhs, IntExpression rhs) {
		Assignment ass = GalFactory.eINSTANCE.createAssignment();
		ass.setLeft(lhs);
		ass.setRight(rhs);
		return ass;
	}


	private static Label generateResets(ArrayPrefix hotbit, GALTypeDeclaration gal,
			TypedefDeclaration hottype) {
		Transition tr  = GalFactory.eINSTANCE.createTransition();

		tr.setName("treset"+hotbit.getName());

		Label lab = GalFactory.eINSTANCE.createLabel();
		lab.setName("reset"+hotbit.getName());
		tr.setLabel(lab);

		Parameter param = GalFactory.eINSTANCE.createParameter();
		param.setName("$"+hotbit.getName()+"id");
		param.setType(hottype);
		tr.getParams().add(param);

		ParamRef pref = GalFactory.eINSTANCE.createParamRef();
		pref.setRefParam(param);

		ArrayVarAccess av = GalFactory.eINSTANCE.createArrayVarAccess();
		av.setPrefix(hotbit);
		av.setIndex(pref);

		Comparison comp = GalFactory.eINSTANCE.createComparison();
		comp.setOperator(ComparisonOperators.EQ);

		comp.setLeft(EcoreUtil.copy(av));
		comp.setRight(constant(1));

		tr.setGuard(comp);

		Assignment ass = GalFactory.eINSTANCE.createAssignment();
		ass.setLeft(EcoreUtil.copy(av));
		ass.setRight(constant(0));


		tr.getActions().add(ass);

		gal.getTransitions().add(tr);
		return lab;
	}



	/**
	 * Replace all occurrences of Variablerefs that refer to var into copies of the expression av.
	 * @param elt an element in which to replace all occurrences
	 * @param var a target variable (hotbit)
	 * @param av the new expression to use 
	 * @return the number of replacements done
	 */
	private static int replaceAllVarRefs(EObject elt, 	Variable var, IntExpression newvar) {
		int nb=0;

		for (TreeIterator<EObject> it = elt.eAllContents(); it.hasNext() ; ) {
			EObject obj = it.next();
			if (obj instanceof VariableRef) {
				VariableRef va = (VariableRef) obj;
				if (va.getReferencedVar() == var) {
					nb++;
					EcoreUtil.replace(va, EcoreUtil.copy(newvar));
				}
			}
		}							
		return nb;
	}


	public static void normalizeCalls(GALTypeDeclaration s) { 
		Map<String,Label> map = new HashMap<String, Label>();
		for (Transition t : s.getTransitions()) {
			if (t.getLabel() != null && ! map.containsKey(t.getLabel().getName()) ) {
				map.put(t.getLabel().getName(), t.getLabel());
			}
		}
		List<Transition> todel = new ArrayList<Transition>();
		for (Transition t : s.getTransitions()) {
			for (TreeIterator<EObject> it = t.eAllContents() ; it.hasNext() ; ) {
				EObject a = it.next();

				if (a instanceof Call) {
					Call call = (Call) a;
					String targetname = call.getLabel().getName();

					Label target = map.get(targetname);
					if (target == null) {
						java.lang.System.err.println("Could not find appropriate target for call to "+targetname+ " . Assuming it was false/destroyed and killing "+ t.getName());

						// TODO : this delete stuff is shaky due to nested statements, we should perhaps abort rather.
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

	public static void normalizeCalls(Specification spec) { 
		for (TypeDeclaration td : spec.getTypes()) {
			if (td instanceof GALTypeDeclaration) {
				normalizeCalls((GALTypeDeclaration)td);
			}
		}
	}

	private static void instantiateTypeParameters(Specification s) {
		List<ConstParameter> params = new ArrayList<ConstParameter>();
		List<TypedefDeclaration> typedefs= new ArrayList<TypedefDeclaration>();
		for (TreeIterator<EObject> it = s.eAllContents() ; it.hasNext() ; ) {
			EObject obj = it.next();
			if (obj instanceof ParamRef) {
				ParamRef pr = (ParamRef) obj;
				if (pr.getRefParam() instanceof ConstParameter) {
					EcoreUtil.replace(obj, constant(((ConstParameter) pr.getRefParam()).getValue()));
				}
			} else if (obj instanceof ConstParameter) {
				params.add((ConstParameter) obj);
			} else if (obj instanceof TypedefDeclaration) {
				typedefs.add((TypedefDeclaration)obj);
			}
		}
		instantiateForLoops(s);
		for (ConstParameter cp : params) {
			EcoreUtil.delete(cp);
		}
	}

	private static void instantiateForLoops(Specification s) {
		List<For> forinstr = new ArrayList<For>();
		for (TreeIterator<EObject> it = s.eAllContents() ; it.hasNext() ; ) {
			EObject obj = it.next();
			if (obj instanceof For) {
				forinstr.add((For) obj);
			}
		}
		// treat deepest first
		Collections.reverse(forinstr);
		for (For pr : forinstr ) {
			Parameter p = pr.getParam();
			Bounds b= computeBounds(p.getType());

			// ok so we have min and max, we'll create max-min copies of the body statements
			// in each one we replace the param by its value
			// we cumulate into a temporary container
			List<Actions> bodies = new ArrayList<Actions>();
			for(int i = b.min; i <= b.max; i++){
				for (Actions asrc : pr.getActions()) {
					Actions adest = EcoreUtil.copy(asrc);
					instantiateParameter(adest, p, i);

					// add adest at end of bodies
					bodies.add(adest);
				}
			}

			// then, we want to substitute to the For instruction the sequence "bodies"
			// Because we do not currently have a nested "sequence" for a plain nested body Actions class,
			// this means deleting the For from its containing Elist (a sequene of actions) and inserting all instructions in bodies
			// Tricky part, identify where to insert the result
			Object oacts = pr.eContainer().eGet(pr.eContainingFeature());
			if (oacts instanceof EList<?>) {
				@SuppressWarnings("unchecked")
				EList<EObject> acts = (EList<EObject>) oacts;					
				int pos = acts.indexOf(pr);
				if (pos != -1) {
					acts.remove(pos);
					acts.addAll(pos, bodies);
				}
			}

		}


	}

	private static Bounds computeBounds(TypedefDeclaration type) {

		int min = evalConst(type.getMin());
		int max = evalConst(type.getMax());;
		if (min == -1 || max == -1) {
			throw new ArrayIndexOutOfBoundsException("Expected constant as both min and max bounds of type def "+type.getName());
		}
		return new Bounds(min, max);
	}

	private static int evalConst (IntExpression expr) {
		Simplifier.simplify(expr);
		if (expr instanceof Constant) {
			Constant cte = (Constant) expr;
			return cte.getValue();
		} else {
			throw new ArrayIndexOutOfBoundsException("Expected expression to resolve to a constant " + expr);
		}
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
				instantiateLabel(tcopy.getLabel(), param, i);
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
		instantiateParameterNoRec(param, value, src);
		for (TreeIterator<EObject> it = src.eAllContents(); it.hasNext();) {
			EObject obj = it.next();

			instantiateParameterNoRec(param, value, obj);
		}
	}


	private static void instantiateParameterNoRec(AbstractParameter param,	int value, EObject obj) {
		if (obj instanceof ParamRef) {
			ParamRef pr = (ParamRef) obj;
			if (pr.getRefParam() == param) {
				EcoreUtil.replace(obj, constant(value));
			}
		} else if (obj instanceof Call) {
			Call call = (Call) obj;
			Label target = GalFactory.eINSTANCE.createLabel();
			target.setName(call.getLabel().getName());
			instantiateLabel(target, param, value);
			call.setLabel(target);
		}
	}

	private static void instantiateLabel(Label label, AbstractParameter param, int i) { 
		String paramStr = param.getName();
		if (label != null) {
			label.setName( label.getName().replace(paramStr, paramStr.replace("$", "")+ Integer.toString(i)));
		}
	}

	public static void fuseIsomorphicEffects (Specification spec) {
		for (TypeDeclaration td : spec.getTypes()) {
			if (td instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) td;
				fuseIsomorphicEffects(gal);
			}
		}
	}

	public static GALTypeDeclaration fuseIsomorphicEffects (GALTypeDeclaration system) {
		sortParameters(system);

		Map<String,List<Integer>> labmap = new HashMap<String,List<Integer>>();

		// pre scan all transitions to reduce number of comparisons necessary
		for (int i=0; i < system.getTransitions().size() ; ++i ) {
			Transition tr = system.getTransitions().get(i);
			String key = "";
			if (tr.getLabel() != null) {
				key = tr.getLabel().getName();
			}
			List<Integer> list = labmap.get(key);
			if (list == null) {
				list = new ArrayList<Integer>();
				labmap.put(key, list);
			}
			list.add(i);			
		}

		// collect indexes of transitions with unique label
		List<Integer> uniqueLabel = new ArrayList<Integer>();		
		for (Entry<String, List<Integer>> e: labmap.entrySet() ) {
			if (e.getValue().size()==1) {
				uniqueLabel.addAll(e.getValue());
			}
		}

		// fuse two transitions with unique label iff : they are identical up to renaming of parameters and label.

		// remap the label of the destroyed transitions to a transition with similar effect
		Map<Label,Label> labelMap = new HashMap<Label, Label>();

		// Destruction is performed at the end to avoid shifting transition indexes
		int nbremoved = 0;
		List<Integer> todrop = new ArrayList<Integer>();

		// test all pairs
		for (int i=0; i < uniqueLabel.size() ; ++i ) {
			for (int j=i+1; j < uniqueLabel.size() ; ++j ) {
				Transition t1 = system.getTransitions().get(uniqueLabel.get(i));
				Transition t2 = system.getTransitions().get(uniqueLabel.get(j));

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

					//					BooleanExpression guard = t2copy.getGuard();
					//					boolean sameActs = true;
					//					for (int index=0; index < t2copy.getActions().size(); index++) {
					//						if (! EcoreUtil.equals(t1.getActions().get(index), t2copy.getActions().get(index))) {
					//							sameActs=false;
					//							break;
					//						}
					//					}
					//					if (! sameActs) {
					//						continue;
					//					}
					//					if (EcoreUtil.equals(t1.getGuard(), t2copy.getGuard())) {
					//						
					//					}
					// t2copy.setGuard(EcoreUtil.copy(t1.getGuard());
					// test for identity : this test should be true if the two transitions actually have the same body
					if (EcoreUtil.equals(t1, t2copy)) {
						// So test is successful : we can happily discard t2, provided we update calls
						todrop.add(uniqueLabel.get(j));
						uniqueLabel.remove(j);
						labelMap.put(t2.getLabel(), t1.getLabel());
						// to ensure correct position in t1/t2 loop
						j--;

						nbremoved ++;
					}


				}

			}
		}

//		// Now look for two transitions with same label, same parameters up to renaming, same statements, and that differ at most through their guard.
//		for (Entry<String, List<Integer>> e: labmap.entrySet() ) {
//			if (e.getValue().size()>1) {
//				List<Integer> trlist = e.getValue();
//
//				for (int i=0; i < trlist.size() ; ++i ) {
//					for (int j=i+1; j < trlist.size() ; ++j ) {
//						Transition t1 = system.getTransitions().get(trlist.get(i));
//						Transition t2 = system.getTransitions().get(trlist.get(j));
//
//						if (	t1.getActions().size() == t2.getActions().size()
//								&& t1.getParams() !=null && t2.getParams() != null
//								&& t1.getParams().size() == t2.getParams().size() ) {
//							EList<Parameter> pl1 = t1.getParams();
//							EList<Parameter> pl2 = t2.getParams();
//
//							int size = pl1.size();
//							boolean areCompat = true;
//							for (int k = 0 ; k < size ; k++) {
//								if (pl1.get(k).getType() != pl2.get(k).getType()) {
//									areCompat = false;
//									break;
//								}
//							}
//							if (!areCompat)
//								break;
//
//							// looks good, labeled transitions, same number of parameters, with pair wise type match, same number of actions
//							Transition t2copy = EcoreUtil.copy(t2);
//							// Attempt a rename 					
//							t2copy.setName(t1.getName());
//							// rename parameters
//							pl2 = t2copy.getParams();
//							for (int k = 0 ; k < size ; k++) {
//								pl2.get(k).setName(pl1.get(k).getName());
//							}
//							BooleanExpression g1 = t1.getGuard();
//							BooleanExpression g2 = t2copy.getGuard();
//							t1.setGuard(GalFactory.eINSTANCE.createTrue());
//							t2copy.setGuard(GalFactory.eINSTANCE.createTrue());
//							
//							// test for identity : this test should be true if the two transitions actually have the same body
//							if (EcoreUtil.equals(t1, t2copy)) {
//								// So test is successful : we can happily discard t2, provided we update t1 guard correctly
//								//								for (TreeIterator<EObject> it = t2.getGuard().eAllContents() ; it.hasNext() ;  ) {
//								//									EObject obj = it.next();
//								//									if (obj instanceof Call) {
//								//										
//								//										
//								//										
//								//									}
//								//								}
//								for (EObject obj : getAllChildren(g2)) {
//									if (obj instanceof ParamRef) {
//										ParamRef pref = (ParamRef) obj;
//										pref.setRefParam(t1.getParams().get(t2copy.getParams().indexOf(pref.getRefParam())));
//									}
//								}
//								t1.setGuard(or(g1, g2));
//								
//								todrop.add(trlist.get(j));
//								System.err.println("Setting up transition " + t2.getName() + " for fusion into " + t1.getName());
//								trlist.remove(j);
//								labelMap.put(t2.getLabel(), t1.getLabel());
//								// to ensure correct position in t1/t2 loop
//								j--;
//
//								nbremoved ++;
//							} else {
//								t1.setGuard(g1);
//							}
//
//
//
//							//
//
//							//							BooleanExpression guard = t2copy.getGuard();
//							//							boolean sameActs = true;
//							//							for (int index=0; index < t2copy.getActions().size(); index++) {
//							//								if (! EcoreUtil.equals(t1.getActions().get(index), t2copy.getActions().get(index))) {
//							//									sameActs=false;
//							//									break;
//							//								}
//							//							}
//							//							if (! sameActs) {
//							//								continue;
//							//							}
//							//							if (EcoreUtil.equals(t1.getGuard(), t2copy.getGuard())) {
//							//								
//							//							}
//							// t2copy.setGuard(EcoreUtil.copy(t1.getGuard());
//
//
//						}
//
//					}
//
//				}
//			}
//		}


		Collections.sort(todrop, Collections.reverseOrder());
		for (Integer trindex : todrop) {
			System.err.println("Dropping transition " + system.getTransitions().get(trindex).getName());
			system.getTransitions().remove(trindex.intValue());
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

	public static void separateParameters(Specification spec) {

		for (TypeDeclaration td : spec.getTypes()) {
			if (td instanceof GALTypeDeclaration) {
				GALTypeDeclaration system = (GALTypeDeclaration) td;


				// sortParameters(system);


				List<Transition> toadd = new ArrayList<Transition>();

				//		if (Simplifier.simplifyPetriStyleAssignments(system)) {
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
											java.lang.System.err.println("Found a free parameter : " + param.getName() +" in transition " + t.getName());
											// a single parameter
											if (t.getParams().size() == 1) {
												// all actions use it
												boolean isAll = true;
												// is every action for param ?
												for (Entry<Actions, List<Parameter>> ae : actionedges.entrySet()) {
													if (ae.getValue().size() != 1 || ae.getValue().get(0) != param) {
														isAll = false;
														break;
													}
												}
												if (isAll) {
													// is every term of guard for param ?
													for (Entry<BooleanExpression, List<Parameter>> ae : guardedges.entrySet()) {
														if (ae.getValue().size() != 1 || ae.getValue().get(0) != param) {
															isAll = false;
															break;
														}
													}
													if (isAll) {
														java.lang.System.err.println("Free parameter : " + param.getName() + " is isolated.");

														// we'll just create an empty caller shell if we go ahead
														break;
													}
												}
											}
											if (t.getLabel() != null && t.getLabel().getName().contains(param.getName())) {
												java.lang.System.err.println("Free parameter : " + param.getName() + " is used in label and cannot be separated.");

												// we'll mess with calls if we go ahead
												break;
											}
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
													guard = and(guard, elt);
												}
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
									guard = and(guard, be);
								}
							}
							t.setGuard(guard);

						}
					}
					//					}
				}

				system.getTransitions().addAll(toadd);

				fuseIsomorphicEffects(system);

			}
		}

		normalizeCalls(spec);
		
	}


	private static BooleanExpression and(BooleanExpression l, BooleanExpression r) {
		And and = GalFactory.eINSTANCE.createAnd();
		and.setLeft(l);
		and.setRight(r);
		return and;
	}


	private static void sortParameters(GALTypeDeclaration system) {
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
		} else if (guard instanceof True) {
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

	public static void instantiateParametersWithAbstractColors(Specification s) {


		instantiateTypeParameters(s);


		List<Parameter> params = new ArrayList<Parameter>();
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
			} else if (obj instanceof Parameter) {
				params.add((Parameter) obj);
			}
		}

		for (Parameter p : params) {
			EcoreUtil.delete(p);
		}

	}

	public static IntExpression constant(int val) {
		return Simplifier.constant(val);
	}


	public static void clearTypedefs(Specification spec) {
		for (TypeDeclaration td : spec.getTypes()) {
			if (td instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) td;
				gal.getTypes().clear();	
			}						
		}
		spec.getTypedefs().clear();
	}

	public static void tagHotbitVariables(Specification s) {
		for (TypeDeclaration type : s.getTypes()) {
			if (type instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) type;
				tagHotbitVariables(gal);
			}
		}
	}

	public static void tagHotbitVariables(GALTypeDeclaration s) {

		Map<VarDecl,Integer> maxvars = new HashMap<VarDecl, Integer>();
		Map<VarDecl,Integer> minvars = new HashMap<VarDecl, Integer>();
		Set<VarDecl> hotvars = new HashSet<VarDecl>(s.getVariables());
		hotvars.addAll(s.getArrays());
		int totalVars = s.getVariables().size();
		
		for (Variable var : s.getVariables()) {
			IntExpression val = var.getValue();
			if (val instanceof Constant && ! var.isHotbit()) {
				Constant cte = (Constant) val;
				maxvars.put(var, cte.getValue());
				minvars.put(var, cte.getValue());
			} else {
				maxvars.put(var, 0);
				minvars.put(var, 0);
				hotvars.remove(var);
			}
		}
		
		for (ArrayPrefix ap : s.getArrays()) {
			maxvars.put(ap, 0);
			minvars.put(ap, 0);
			totalVars += ap.getSize();			
			for (IntExpression val : ap.getValues()) {
				if (val instanceof Constant && ! ap.isHotbit()) {
					Constant cte = (Constant) val;
					maxvars.put(ap, Math.max(cte.getValue(), maxvars.get(ap)));
					minvars.put(ap, Math.min(cte.getValue(), minvars.get(ap)));	
				} else {
					maxvars.put(ap, 0);
					minvars.put(ap, 0);
					hotvars.remove(ap);
					break;
				}
			}
		}
		
		// compute hotbit candidates and their range
		for (TreeIterator<EObject> it = s.eAllContents() ; it.hasNext() ; ) {
			EObject obj = it.next();
			if (obj instanceof Assignment) {
				Assignment ass = (Assignment) obj;
				VarAccess lhs = ass.getLeft();
				IntExpression rhs = ass.getRight();
				VarDecl vd = null ;
				if (lhs instanceof VariableRef) {
					vd = ((VariableRef) lhs).getReferencedVar();
				} else if (lhs instanceof ArrayVarAccess) {
					ArrayVarAccess av = (ArrayVarAccess) lhs;
					vd = av.getPrefix();
				} 
				if (vd != null) {
					if (rhs instanceof Constant) {
						maxvars.put(vd, Math.max(maxvars.get(vd), ((Constant) rhs).getValue()));
						minvars.put(vd, Math.min(minvars.get(vd), ((Constant) rhs).getValue()));
					} else {
						hotvars.remove(vd);
					}
				} 
			} 
		}
		
		
		StringBuilder sb = new StringBuilder();
		int sum = hotvars.size();
		for (VarDecl var : hotvars) {
			sb.append( minvars.get(var)  +"<="+ var.getName()+ (var instanceof ArrayPrefix ? "[*]" :"") +"<=" + maxvars.get(var) + ",");
		}
		if (sum != 0) {
			java.lang.System.err.println("Found a total of " + sum + " hotbit candidates (out of "+ totalVars +" variables) \n "+sb.toString() );
		} else {
			return;
		}
		
		for (VarDecl var : hotvars) {
			int min = minvars.get(var);
			int max = maxvars.get(var);
			if (min==0 && max - min >= HOTBIT_THRESHOLD) {
				System.err.println("Variable " + var.getName() + " is above HOTBIT_THRESHOLD=" + HOTBIT_THRESHOLD + ". Tagging as hotbit.");
				TypedefDeclaration r = GalFactory.eINSTANCE.createTypedefDeclaration();
				r.setName(var.getName()+"_t");
				r.setMin(constant(min));
				r.setMax(constant(max));
				s.getTypes().add(r);
				
				var.setHotbit(true);
				var.setHottype(r);
			}
		}
		
	}

}


class Bounds {
	int min;
	int max;
	public Bounds(int min, int max) {
		this.min = min;
		this.max = max;
	}

}