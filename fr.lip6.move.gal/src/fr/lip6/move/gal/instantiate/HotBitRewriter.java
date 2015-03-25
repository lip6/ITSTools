package fr.lip6.move.gal.instantiate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.AssignType;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.SelfCall;
import fr.lip6.move.gal.Statement;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.For;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Label;
import fr.lip6.move.gal.ParamRef;
import fr.lip6.move.gal.Parameter;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.TypedefDeclaration;
import fr.lip6.move.gal.VarDecl;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableReference;

public class HotBitRewriter {

	private static final int HOTBIT_THRESHOLD = 8;


	public static void tagHotbitVariables(Specification s) {
		for (TypeDeclaration type : s.getTypes()) {
			if (type instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) type;
				tagHotbitVariables(gal);
			}
		}
	}

	public static void tagHotbitVariables(GALTypeDeclaration s) {

		Map<VarDecl, Set<Integer>> seenvars = DomainAnalyzer.computeConstAccessVariableDomains(s);

		for (Entry<VarDecl, Set<Integer>> entry : seenvars.entrySet()) {
			VarDecl var = entry.getKey();
			Set<Integer> domain = entry.getValue();
			if (! isContinuous(domain) || domain.size() <  HOTBIT_THRESHOLD) {
				continue;
			}


			int min = domain.iterator().next();			
			int max = ((TreeSet<Integer>) domain).descendingIterator().next();
			if (min==0) {
				System.err.println("Variable " + var.getName() + " is above HOTBIT_THRESHOLD=" + HOTBIT_THRESHOLD + ". Tagging as hotbit.");
				TypedefDeclaration r = GalFactory.eINSTANCE.createTypedefDeclaration();
				r.setName(var.getName()+"_t");
				r.setMin(GF2.constant(min));
				r.setMax(GF2.constant(max));
				s.getTypedefs().add(r);

				var.setHotbit(true);
				var.setHottype(r);
			}
		}

	}


	public static boolean isContinuous(Set<Integer> set) {
		int i = 0;
		for (Integer elt : set) {
			if (elt != i++) {
				return false;
			}
		}
		return true;
	}

	public static void instantiateHotBit(Specification spec) {

		List<Variable> todel = new ArrayList<Variable>();
		for (TypeDeclaration td : spec.getTypes()) {
			if (td instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) td;
				for (Variable var : gal.getVariables()) {
					if (var.isHotbit()) {
						var.setName(var.getName().replaceAll("\\.", "_"));
						TypedefDeclaration type = var.getHottype();
						Bounds b = Instantiator.computeBounds(type); 

						Label labresets = null;

						ArrayPrefix ap = GalFactory.eINSTANCE.createArrayPrefix();
						ap.setName(var.getName());
						int size = b.max - b.min + 1;
						ap.setSize(GF2.constant(size));
						int pos = Instantiator.evalConst(var.getValue());
						for (int i = 0; i < size ; i++ ) {
							if (i != pos) {
								ap.getValues().add(GF2.constant(0));
							} else {
								ap.getValues().add(GF2.constant(1));								
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

							ParamRef pref = GF2.createParamRef(param);

							VariableReference av = GF2.createArrayVarAccess(ap,pref);

							//							boolean hasRead = false;
							//							List<EObject> readwrites = new ArrayList<EObject>();

							// iterate in order
							int k = replaceAllVarRefs(tr.getGuard(), var, pref);

							boolean waswritten = false;
							Statement addbefore=null;
							Statement toadd=null;
							for (Statement a : tr.getActions()) {
								if (!waswritten) {
									if (a instanceof Assignment) {
										Assignment ass = (Assignment) a;
										VariableReference va =  ass.getLeft();
										if (va.getRef() == var) {
											k += replaceAllVarRefs(ass.getRight(), var, pref);

											VariableReference av2 = EcoreUtil.copy(av);
											av2.setIndex(ass.getRight());
											ass.setRight(GF2.constant(1));
											ass.setLeft(av2);

											if (k >0) {
												// read before write													
												toadd = GF2.createAssignment(EcoreUtil.copy(av),GF2.constant(0));
											} else {
												SelfCall call = GalFactory.eINSTANCE.createSelfCall();
												call.setLabel(labresets);
												toadd = call;
											}
											addbefore = ass;
											waswritten = true;
											continue;
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


								tr.setGuard(GF2.and(tr.getGuard(), 
										GF2.createComparison(EcoreUtil.copy(av), ComparisonOperators.EQ, GF2.constant(1))));								
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
						Bounds b = Instantiator.computeBounds(type); 


						ArrayPrefix ap = GalFactory.eINSTANCE.createArrayPrefix();
						ap.setName("hot"+array.getName());
						int size = b.max - b.min + 1;
						
						ap.setSize( GF2.constant( size * ((Constant) array.getSize()).getValue()));

						for (IntExpression value : array.getValues()) {
							int pos = Instantiator.evalConst(value);
							for (int i = 0; i < size ; i++ ) {
								if (i != pos) {
									ap.getValues().add(GF2.constant(0));
								} else {
									ap.getValues().add(GF2.constant(1));								
								}
							}
						}
						toaddArrays.add(ap);
						todelArrays.add(array);


						for (Transition tr : gal.getTransitions()) {

							// first collect all references to the target array
							List<VariableReference> accesses = new ArrayList<VariableReference>();
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
									VariableReference access = accesses.get(accIndex);
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
										pref = GF2.createParamRef(param);

										// build expression : i*|r| + ptabi
										IntExpression mult = GF2.createBinaryIntExpression(
												EcoreUtil.copy(access.getIndex()),
												"*", 
												GF2.constant(size));

										IntExpression plus = GF2.createBinaryIntExpression(mult, "+", EcoreUtil.copy(pref));

										VariableReference av = GF2.createArrayVarAccess(ap, plus);

										// Add guard constraint
										tr.setGuard(GF2.and(tr.getGuard(), 
												GF2.createComparison(EcoreUtil.copy(av), ComparisonOperators.EQ, GF2.constant(1))));

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

											ParamRef pitref = GF2.createParamRef(it);

											// build expression : i*|r| + ptabi
											IntExpression mult = GF2.createBinaryIntExpression(
													EcoreUtil.copy(access.getIndex()), 
													"*",
													GF2.constant(size));
											IntExpression plus = GF2.createBinaryIntExpression(mult, "+", pitref);

											VariableReference ava = GF2.createArrayVarAccess(ap,plus);

											Statement ass = GF2.createAssignment(ava,GF2.constant(0));

											For forloop = GalFactory.eINSTANCE.createFor();
											forloop.setParam(it);
											forloop.getActions().add(ass);

											// insert before assignment
											Assignment parent = (Assignment) access.eContainer();
											@SuppressWarnings("unchecked")
											EList<Statement> statementList = (EList<Statement>) parent.eContainer().eGet(parent.eContainmentFeature());
											int index = statementList.indexOf(parent);
											statementList.add(index, forloop);
										} else {
											// for each write after a read replace,
											// tab[i] = e -> tab[i * size + $ptabi]=0 ; tab[ i*size + e ] = 1;


											// Was read; just reset tab[i*|r|+ ptabi]
											// variable was read before and is now updated
											// build expression : i*|r| + ptabi
											IntExpression mult = GF2.createBinaryIntExpression(
													EcoreUtil.copy(access.getIndex()),
													"*",
													GF2.constant(size));
											IntExpression plus = GF2.createBinaryIntExpression(mult,"+",EcoreUtil.copy(pref));
											VariableReference av = GF2.createArrayVarAccess(ap,plus);

											Statement resetCur = GF2.createAssignment(av,GF2.constant(0));

											// insert before assignment
											Assignment parent = (Assignment) access.eContainer();
											@SuppressWarnings("unchecked")
											EList<Statement> statementList = (EList<Statement>) parent.eContainer().eGet(parent.eContainmentFeature());
											int index = statementList.indexOf(parent);
											statementList.add(index, resetCur);

										}
										// in any case update the assignment
										Assignment parent = (Assignment) access.eContainer();
										access.setRef(ap);
										// build expression : i*|r| + rhs
										BinaryIntExpression mult2 = GalFactory.eINSTANCE.createBinaryIntExpression();
										mult2.setLeft(EcoreUtil.copy(access.getIndex()));
										mult2.setOp("*");
										mult2.setRight(GF2.constant(size));
										BinaryIntExpression plus2 = GalFactory.eINSTANCE.createBinaryIntExpression();										
										plus2.setLeft(mult2);
										plus2.setOp("+");
										plus2.setRight(parent.getRight());
										access.setIndex(plus2);

										parent.setRight(GF2.constant(1));
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



	private static Label generateResets(ArrayPrefix hotbit, GALTypeDeclaration gal,
			TypedefDeclaration hottype) {
		Transition tr  = GalFactory.eINSTANCE.createTransition();

		tr.setName("treset"+hotbit.getName());

		Label lab = GF2.createLabel("reset"+hotbit.getName());
		tr.setLabel(lab);

		Parameter param = GalFactory.eINSTANCE.createParameter();
		param.setName("$"+hotbit.getName()+"id");
		param.setType(hottype);
		tr.getParams().add(param);

		ParamRef pref = GF2.createParamRef(param);

		VariableReference av = GF2.createArrayVarAccess(hotbit,pref);


		tr.setGuard(GF2.createComparison(EcoreUtil.copy(av), 
				ComparisonOperators.EQ, 
				GF2.constant(1)));

		Statement ass = GF2.createAssignment(EcoreUtil.copy(av),GF2.constant(0));

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
			if (obj instanceof VariableReference) {
				VariableReference va = (VariableReference) obj;
				if (va.getRef() == var) {
					nb++;
					EcoreUtil.replace(va, EcoreUtil.copy(newvar));
				}
			}
		}							
		return nb;
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
			EObject parent, List<VariableReference> accesses,
			List<List<Integer>> accessPerExpr) {

		if (parent instanceof Assignment) {
			Assignment ass = (Assignment) parent;
			if (ass.getType() == AssignType.ASSIGN) {
				//explore rhs before lhs
				collectAccesses(targetArray, ass.getRight(), accesses, accessPerExpr);
				collectAccesses(targetArray, ass.getLeft(), accesses, accessPerExpr);
			} else {
				// variable is read to increment it => lhs before rhs ?
				collectAccesses(targetArray, ass.getLeft(), accesses, accessPerExpr);				
				collectAccesses(targetArray, ass.getRight(), accesses, accessPerExpr);
			}
			// skip children they are already explored
		} else if (parent instanceof Transition) {
			Transition tr = (Transition) parent;
			//explore guard before statement
			collectAccesses(targetArray, tr.getGuard(), accesses, accessPerExpr);
			for (Statement a : tr.getActions()) {
				collectAccesses(targetArray, a, accesses, accessPerExpr);
			}
		} else if (parent instanceof VariableReference) {
			VariableReference potav = (VariableReference) parent;
			if (potav.getRef() == targetArray) {
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
}
