package fr.lip6.move.gal.instantiate;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.Abort;
import fr.lip6.move.gal.Actions;
import fr.lip6.move.gal.And;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.ArrayVarAccess;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.BitComplement;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.GalFactory2;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Ite;
import fr.lip6.move.gal.Not;
import fr.lip6.move.gal.Or;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.VarAccess;
import fr.lip6.move.gal.VarDecl;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableRef;

public class Simplifier {

	public static void simplify(Specification spec) {
		for (TypeDeclaration td : spec.getTypes()) {
			if (td instanceof GALTypeDeclaration) {
				simplify((GALTypeDeclaration) td);
			}
		}
		
		Instantiator.fuseIsomorphicEffects(spec);
	}
	
	public static GALTypeDeclaration simplify(GALTypeDeclaration s) {
		simplifyAllExpressions(s);
		
		simplifyAbort(s);
		
		simplifyFalseTransitions(s);		
		
		simplifyPetriStyleAssignments(s);

		simplifyConstantVariables(s);
		
		simplifyAllExpressions(s);
		
		simplifyConstantIte(s);
		
		simplifyAbort(s);
		
		return s;
	}

	/**
	 * Replace any sequence that contains an abort by a single abort statement.
	 * Get rid of any transition that has abort in its body statements.
	 * @param s
	 */
	public static int simplifyAbort(GALTypeDeclaration s) {

		List<Abort> toclear = new ArrayList<Abort>();
		// first collect occurrences of abort
		for (TreeIterator<EObject> it = s.eAllContents() ; it.hasNext() ; ) {
			EObject obj = it.next();
			if (obj instanceof Abort) {
				toclear.add((Abort) obj);
			}
		}
		
		int nbrem = 0;
		for (Abort obj : toclear) {
			// a transition with abort in its body
			if (obj.eContainer() instanceof Transition) {
				nbrem ++;
				s.getTransitions().remove(obj.eContainer());
			} else {
				// some nested block of some kind, absorb other statements.
				@SuppressWarnings("unchecked")
				EList<Actions> statementList = (EList<Actions>) obj.eContainer().eGet(obj.eContainmentFeature());
				statementList.clear();
				statementList.add(obj);
			}
		}
		return nbrem;
	}

	/**
	 * Reduce:  if (c) { s1 } else { s2 }
	 * to {s1} if c is trivially true or {s2} if c is trivially false. 
	 * @param s
	 */
	private static void simplifyConstantIte(GALTypeDeclaration s) {
		True tru = GalFactory.eINSTANCE.createTrue();
		False fals = GalFactory.eINSTANCE.createFalse();
		List<Ite> toreplace = new ArrayList<Ite>();
		
		
		for (TreeIterator<EObject> it = s.eAllContents() ; it.hasNext() ; ) {
			EObject obj = it.next();
			if (obj instanceof Ite) {
				Ite ite = (Ite) obj; 
				if (EcoreUtil.equals(ite.getCond(),tru) || EcoreUtil.equals(ite.getCond(), fals)) {
					toreplace.add(ite);
				}
			}
		}
		
		for (Ite ite : toreplace) {
			// insert before assignment
			@SuppressWarnings("unchecked")
			EList<Actions> statementList = (EList<Actions>) ite.eContainer().eGet(ite.eContainmentFeature());
			int index = statementList.indexOf(ite);

			if (EcoreUtil.equals(ite.getCond(),tru)) {
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
	private static void simplifyAllExpressions(GALTypeDeclaration s) {
		List<EObject> todo = new ArrayList<EObject>();
		todo.add(s);
		while (!todo.isEmpty()) {
			EObject cur = todo.remove(0);
			if (cur instanceof IntExpression) {
				IntExpression expr = (IntExpression) cur;
				simplify(expr);
			} else if (cur instanceof BooleanExpression) {
				BooleanExpression expr = (BooleanExpression) cur;
				simplify(expr);
			} else {
				todo.addAll(cur.eContents());
			}
		}
	}


	/**
	 * Simplify syntactically false guard transitions
	 * @param s
	 */
	private static void simplifyFalseTransitions(GALTypeDeclaration s) {
		List<Transition> todel = new ArrayList<Transition>();
		for (Transition t : s.getTransitions()) {
			if (t.getGuard() instanceof False) {
				todel.add(t);
			}
		}
		s.getTransitions().removeAll(todel);

		if (! todel.isEmpty()) 
			java.lang.System.err.println("Removed "+ todel.size() + " false transitions.");
		
	}

	/** Identify and discard constant variables */
	private static void simplifyConstantVariables(GALTypeDeclaration s) {

		Set<Variable> constvars = new HashSet<Variable>(s.getVariables());
		Map<ArrayPrefix, Set<Integer>> constantArrs = new HashMap<ArrayPrefix, Set<Integer>>();
		int totalVars = s.getVariables().size();
		
		for (ArrayPrefix ap : s.getArrays()) {
			Set<Integer> vals = new HashSet<Integer>();
			for (int i = 0 ; i < ap.getSize(); i++) {
				vals.add(i);
			}
			totalVars += ap.getSize();
			constantArrs.put(ap, vals);
		}
		// compute constant vars
		Set<ArrayPrefix> dontremove = new HashSet<ArrayPrefix>();
		for (TreeIterator<EObject> it = s.eAllContents() ; it.hasNext() ; ) {
			EObject obj = it.next();
			if (obj instanceof Assignment) {
				Assignment ass = (Assignment) obj;
				VarAccess lhs = ass.getLeft();
				if (lhs instanceof VariableRef) {
					VariableRef va = (VariableRef) lhs;
					constvars.remove(va.getReferencedVar());
				} else if (lhs instanceof ArrayVarAccess) {
					ArrayVarAccess av = (ArrayVarAccess) lhs;
					if (av.getIndex() instanceof Constant) {
						Constant cte = (Constant) av.getIndex();
						constantArrs.get(av.getPrefix()).remove(cte.getValue());						
					} else {
						constantArrs.get(av.getPrefix()).clear();
					}
				}
			} else if (obj instanceof ArrayVarAccess) {
				ArrayVarAccess av = (ArrayVarAccess) obj;
				if (! (av.getIndex() instanceof Constant ) ) {
					dontremove.add(av.getPrefix());
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

		StringBuilder sb = new StringBuilder();
		int sum = constvars.size();
		for (Variable var : constvars) {
			sb.append(var.getName()+",");
		}
		for (Entry<ArrayPrefix, Set<Integer>> e : constantArrs.entrySet()) {
			sum += e.getValue().size();
			for (int val : e.getValue()) {
				sb.append(e.getKey().getName() + "[" + val + "],");
			}
		}
		
		
		if (sum != 0) {
			java.lang.System.err.println("Found a total of " + sum + " constant array cells/variables (out of "+ totalVars +" variables) \n "+sb.toString() );
		} else {
			return;
		}
		
		int totalexpr = 0;
		List<EObject> todel = new ArrayList<EObject>();
		// Substitute constants in guards and assignments
		for (TreeIterator<EObject> it = s.eAllContents() ; it.hasNext() ; ) {
			EObject obj = it.next();
			if (obj instanceof VariableRef) {
				VariableRef va = (VariableRef) obj;
				if (constvars.contains(va.getReferencedVar())) {
					if (va.eContainer() instanceof Assignment 
						&& va.eContainingFeature().getName().equals("left")) {
						todel.add(va.eContainer());
					} else {
						EcoreUtil.replace(va, EcoreUtil.copy(va.getReferencedVar().getValue()));
						totalexpr++;
					}
				} 
			} else if (obj instanceof ArrayVarAccess) {
				ArrayVarAccess av = (ArrayVarAccess) obj;
				
				if ( av.getIndex() instanceof Constant ) {
					int index = ((Constant) av.getIndex()).getValue();
					if (constantArrs.get(av.getPrefix()).contains(index) ) {
						EcoreUtil.replace(av, EcoreUtil.copy(av.getPrefix().getValues().get(index)));						
						totalexpr++;
					}
				}
			}
		}
		
		// get rid of assignments to constants
		for (EObject obj : todel) {
			EcoreUtil.delete(obj);
		}
		
		// Discard constants from state signature if possible
		for (Variable var : constvars) {
			EcoreUtil.delete(var);
		}
		for (Entry<ArrayPrefix, Set<Integer>> e : constantArrs.entrySet()) {
			if (e.getValue().size() == e.getKey().getSize() && (! dontremove.contains(e.getKey()))) {
				EcoreUtil.delete(e.getKey());
			}
		}
		if (totalexpr != 0) {
			java.lang.System.err.println(" Simplified "+ totalexpr + " expressions due to constant valuations.");
			simplifyAllExpressions(s);
		}
	}

	public static boolean simplifyPetriStyleAssignments(GALTypeDeclaration system) {

		boolean isPetriStyle = true;
		//simplify redundant assignments :
		// suppose we have both : x = x + 1; and  x = x-1; without reading or writing to x in between.
		// typically produced by test arc style petri nets
		for (Transition tr : system.getTransitions()) {


			if (isPetriStyle(tr)) {
				Map<String,Map<VarAccess,Integer>> arrAdd = new HashMap<String, Map<VarAccess,Integer>>();
				Map<Variable,Integer> varAdd = new HashMap<Variable, Integer>();				
				
				for (Actions a : tr.getActions()) {
					Assignment ass = (Assignment) a;
					BinaryIntExpression bin = (BinaryIntExpression) ass.getRight();
					Constant c = (Constant) bin.getRight();
					int val = c.getValue();
					if (bin.getOp().equals("-")) {
						val = -val;
					}
					
					if (ass.getLeft() instanceof ArrayVarAccess) {
						String aname = ((ArrayVarAccess)ass.getLeft()).getPrefix().getName();
						Map<VarAccess, Integer> indmap = arrAdd.get(aname);
						if (indmap == null) {
							indmap = new HashMap<VarAccess, Integer>();
							arrAdd.put(aname, indmap);
						}
						boolean found = false;
						for (Entry<VarAccess, Integer> entry : indmap.entrySet()) {
							if (EcoreUtil.equals(entry.getKey(),ass.getLeft())) {
								entry.setValue(entry.getValue() + val);
								found = true;
								break;
							}
						}
						if (!found) {
							indmap.put(ass.getLeft(), val);
						}
					} else if (ass.getLeft() instanceof VariableRef) {
						Variable vname = ((VariableRef)ass.getLeft()).getReferencedVar();
						Integer indmap = varAdd.get(vname);
						if (indmap == null) {
							varAdd.put(vname, val);
						} else {
							varAdd.put(vname, indmap + val);
						}
					}
				}
				List<Actions> newActs = new ArrayList<Actions> ();
				for (Entry<String, Map<VarAccess, Integer>> entry : arrAdd.entrySet()) {
					for (Entry<VarAccess, Integer> entry2 : entry.getValue().entrySet()) {
						VarAccess arr = entry2.getKey();
						Integer val = entry2.getValue();
						
						if (val != 0) {
							Assignment ass = GalFactory2.increment(arr, val); 
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
						VariableRef varRef = GalFactory2.createVariableRef(entry.getKey());
						Assignment ass = GalFactory2.increment(varRef , entry.getValue());
						newActs.add(ass);
					}
				}
				
				for (TreeIterator<EObject> it = tr.getGuard().eAllContents() ; it.hasNext() ; ) {
					EObject obj = it.next();
					if (obj instanceof Comparison) {
						Comparison cmp = (Comparison) obj;
						if (cmp.getOperator()==ComparisonOperators.GE && cmp.getLeft() instanceof VarAccess && cmp.getRight() instanceof Constant) {
							int curval = ((Constant) cmp.getRight()).getValue();
							int val = 0;
							if (cmp.getLeft() instanceof ArrayVarAccess) {
								ArrayVarAccess av = (ArrayVarAccess) cmp.getLeft();
								Map<VarAccess, Integer> map = arrAdd.get(av.getPrefix().getName());
								if (map != null) {
									for (Entry<VarAccess, Integer> entry : map.entrySet()) {
										if (EcoreUtil.equals(entry.getKey(),av)) {
											val = entry.getValue();
											break;
										}
									}
								}
							} else {
								VariableRef vr = (VariableRef) cmp.getLeft();
								Integer tmp = varAdd.get(vr.getReferencedVar()); 
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
				
				tr.getActions().clear();
				tr.getActions().addAll(newActs);
				

			}  else { // if petri style tr
				isPetriStyle = false;
			}
		} // for tr

		return isPetriStyle;
	}

	private static boolean isPetriStyle(Transition tr) {

		for (Actions a : tr.getActions()) {
			if (a instanceof Assignment
					&& ( 
							( 		/// tab[cte] case
									((Assignment) a).getLeft() instanceof ArrayVarAccess 
									//&& ( ((ArrayVarAccess) ((Assignment) a).getLeft()).getIndex() instanceof Constant  )
									)
									||  
									(       /// plain old variable
											((Assignment) a).getLeft() instanceof VariableRef
											)
							)
							&& ((Assignment) a).getRight() instanceof BinaryIntExpression					
							&& ( ( (BinaryIntExpression) ((Assignment) a).getRight()).getOp()=="+" ||
								   ((BinaryIntExpression) ((Assignment) a).getRight()).getOp()=="-"
								)
							&& EcoreUtil.equals(((BinaryIntExpression) ((Assignment) a).getRight()).getLeft(), ((Assignment) a).getLeft() )
							&& ((BinaryIntExpression) ((Assignment) a).getRight()).getRight() instanceof Constant )
			{
			}
			else
			{
				return false;
			}
		}
		return true;
	}

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
			} 
		} else if (be instanceof Or) {
			Or and = (Or) be;
			simplify(and.getLeft());
			simplify(and.getRight());
			BooleanExpression left = and.getLeft();
			BooleanExpression right = and.getRight();
			if (left instanceof False) {
				EcoreUtil.replace(be, right);
			} else if (right instanceof False) {
				EcoreUtil.replace(be, left);
			} else if (left instanceof True || right instanceof True) {
				EcoreUtil.replace(be,gf.createTrue());
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
			}
		}
	}

	public static void simplify(IntExpression expr) {
		if (expr instanceof BinaryIntExpression) {
			BinaryIntExpression bin = (BinaryIntExpression) expr;
			simplify(bin.getLeft());
			simplify(bin.getRight());
			
			IntExpression left =bin.getLeft();
			IntExpression right = bin.getRight();

			if (left instanceof Constant && right instanceof Constant) {
				int l = ((Constant) left).getValue();
				int r = ((Constant) right).getValue();
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
					java.lang.System.err.println("Unexpected operator in simplify procedure:" + bin.getOp());
				}
				EcoreUtil.replace(bin, GalFactory2.constant(res));
			} else if (left instanceof Constant) {
				int l = ((Constant) left).getValue();
				if (l==0 && "+".equals(bin.getOp())) {
					EcoreUtil.replace(bin, right);
				} else if (l==1 && "*".equals(bin.getOp())) {
					EcoreUtil.replace(bin, right);
				}
			} else if (right instanceof Constant) {
				int r = ((Constant) right).getValue();
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
				EcoreUtil.replace(minus, GalFactory2.constant(~ ((Constant) minus.getValue()).getValue()));
			}
		} else if (expr instanceof ArrayVarAccess) {
			ArrayVarAccess acc = (ArrayVarAccess) expr;
			simplify(acc.getIndex());
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
					if (!(cmp.getLeft() instanceof ArrayVarAccess || cmp.getRight() instanceof ArrayVarAccess)) {
						tguards.put(t, true);
						break;
					}
				}
			}
			for (Actions a : t.getActions()) {
				if (a instanceof Assignment) {
					Assignment ass = (Assignment) a;
					VarAccess lhs = ass.getLeft();
					BinaryIntExpression rhs = (BinaryIntExpression)ass.getRight();
					if (lhs instanceof ArrayVarAccess && rhs instanceof BinaryIntExpression && rhs.getRight() instanceof Constant ) {
						ArrayVarAccess av = (ArrayVarAccess) lhs ;
						int val = ((Constant) rhs.getRight()).getValue();
						if (val != 1) {
							java.lang.System.err.println("Problem with variable value not 1");
						}
						if (rhs.getOp().equals("+")) {
							addToSet (av.getPrefix(), t, av.getIndex(), presets);
						} else {
							addToSet (av.getPrefix(), t, av.getIndex(), postsets);							
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
							java.lang.System.err.println("This place :" + ap.getName() + " is candidate for agglomeration.");
							nbagglo++;
						}
					}
				}
			}
		}
		java.lang.System.err.println("Total places to agglo " + nbagglo);
		
		
		
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
