package fr.lip6.move.gal.instantiate;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.Actions;
import fr.lip6.move.gal.And;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.ArrayVarAccess;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.False;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Not;
import fr.lip6.move.gal.Or;
import fr.lip6.move.gal.System;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.VarAccess;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.VariableRef;

public class Simplifier {

	public static System simplify(System s) {
		simplifyTypeParameters(s);

		simplifyConstantOperations(s);

		simplifyPetriStyleAssignments(s);

		simplifyConstantVariables(s);
		return s;
	}

	private static void simplifyTypeParameters(System s) {
		for (Variable var : s.getVariables()) {
			simplify(var.getValue());
		}
		for (ArrayPrefix var : s.getArrays()) {
			for (IntExpression val : var.getValues().getValues()) {
				simplify(val);
			}
		}
	}

	private static void simplifyConstantOperations(System s) {
		List<Transition> todel = new ArrayList<Transition>();
		for (Transition t : s.getTransitions()) {
			simplify(t.getGuard());
			BooleanExpression newg = t.getGuard();
			if (newg instanceof False) {
				todel.add(t);
				continue;
			}
			for (Actions a : t.getActions()) {
				if (a instanceof Assignment) {
					Assignment ass = (Assignment) a;
					simplify(ass.getLeft());
					simplify(ass.getRight());
				}
			}
		}

		// simplify syntactically false guard transitions
		int i =0;
		for (Transition t : todel) {
			EcoreUtil.delete(t);
			i++;
		}
		java.lang.System.err.println("Removed "+i + " false transitions.");
		
	}

	private static void simplifyConstantVariables(System s) {

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
					constantArrs.get(av.getPrefix()).remove(((Constant) av.getIndex()).getValue());
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
		// Substitute constants in guards and assignments
		for (TreeIterator<EObject> it = s.eAllContents() ; it.hasNext() ; ) {
			EObject obj = it.next();
			if (obj instanceof VariableRef) {
				VariableRef va = (VariableRef) obj;
				if (constvars.contains(va.getReferencedVar())) {
					EcoreUtil.replace(va, EcoreUtil.copy(va.getReferencedVar().getValue()));
					totalexpr++;
				} 
			} else if (obj instanceof ArrayVarAccess) {
				ArrayVarAccess av = (ArrayVarAccess) obj;
				
				if ( av.getIndex() instanceof Constant && constantArrs.get(av.getPrefix()).contains(((Constant) av.getIndex()).getValue()) ) {
					EcoreUtil.replace(av, EcoreUtil.copy(av.getPrefix().getValues().getValues().get(((Constant) av.getIndex()).getValue())));						
					totalexpr++;
				}
			}
		}
		if (totalexpr != 0) {
			java.lang.System.err.println(" Simplified "+ totalexpr + " expressions due to constant valuations.");
			simplifyConstantOperations(s);
		}
	}

	public static boolean simplifyPetriStyleAssignments(System s) {

		boolean isPetriStyle = true;
		//simplify redundant assignments :
		// suppose we have both : x = x + 1; and  x = x-1; without reading or writing to x in between.
		// typically produced by test arc style petri nets
		for (Transition tr : s.getTransitions()) {


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
							Assignment ass = increment(arr, val); 
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
						VariableRef varRef = GalFactory.eINSTANCE.createVariableRef();
						varRef.setReferencedVar(entry.getKey());
						Assignment ass = increment(varRef , entry.getValue());
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
				
//				// do it quadratic, maps don't work well with eObject
//				EList<Actions> actions = tr.getActions();
//				for (int i = 0; i < actions.size(); i++) {
//					Assignment ass = (Assignment) actions.get(i);
//					
//					for (int j = i+1 ; j < actions.size() ; j++) {
//						Assignment ass2 = (Assignment) actions.get(j);
//						if (EcoreUtil.equals(ass2.getLeft(),ass.getLeft())) {
//							BinaryIntExpression bin = (BinaryIntExpression) ass.getRight();
//							BinaryIntExpression bin2 = (BinaryIntExpression) ass2.getRight();
//
//							Constant c = (Constant) bin.getRight();
//							Constant c2 = (Constant) bin2.getRight();
//
//							int val = c.getValue();
//							if (bin.getOp().equals("-")) {
//								val = -val;
//							}
//							int val2 = c2.getValue();
//							if (bin2.getOp().equals("-")) {
//								val2 = -val2;
//							}
//							int valtot = val + val2;
//
//							if (valtot==0) {
//								EcoreUtil.delete(ass);
//							} else if (valtot > 0) {
//								bin.setOp("+");
//								c.setValue(valtot);
//							} else {
//								bin.setOp("-");
//								c.setValue(-valtot);
//							}
//							EcoreUtil.delete(ass2);
//							break;
//						} // if same lhs
//					}  // for j
//				}  // for i
			}  else { // if petri style tr
				isPetriStyle = false;
			}
		} // for tr

		return isPetriStyle;
	}

	private static Assignment increment(VarAccess var, Integer value) {
		Assignment ass = GalFactory.eINSTANCE.createAssignment();
		ass.setLeft(EcoreUtil.copy(var));
		
		BinaryIntExpression op = GalFactory.eINSTANCE.createBinaryIntExpression();		
		op.setLeft(EcoreUtil.copy(var));
		
		if (value >= 0) {
			op.setOp("+");
			op.setRight(constant(value));
		} else {
			op.setOp("-");
			op.setRight(constant(- value));
		}
		
		ass.setRight(op);
		return ass;
	}
	
	private static Constant constant(int val) {
		Constant tmp = GalFactory.eINSTANCE.createConstant();
		tmp.setValue(val);
		return tmp;
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
		GalFactory gf = GalFactory.eINSTANCE;
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
				} else {
					java.lang.System.err.println("Unexpected operator in simplify procedure:" + bin.getOp());
				}
				Constant cst = gf.createConstant();
				cst.setValue(res);
				EcoreUtil.replace(bin, cst);
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

		} else if (expr instanceof ArrayVarAccess) {
			ArrayVarAccess acc = (ArrayVarAccess) expr;
			simplify(acc.getIndex());
		}
	} 

	
	public static void simplifyImplicitVariables (System system) {
		
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
