package fr.lip6.move.gal.instantiate;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import fr.lip6.move.gal.AssignType;
import fr.lip6.move.gal.CompositeTypeDeclaration;
import fr.lip6.move.gal.InstanceCall;
import fr.lip6.move.gal.InstanceDecl;
import fr.lip6.move.gal.Property;
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

		List <GALTypeDeclaration> torem = new ArrayList<GALTypeDeclaration>();
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
								Set<String> trueLab = trueLabs.get(tr.getLabel()) ;
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
		spec.getTypes().removeAll(torem);
		if (! torem.isEmpty()) {
			
			for (TypeDeclaration td : spec.getTypes()) {
				
				if (td instanceof CompositeTypeDeclaration) {
					CompositeTypeDeclaration ctd = (CompositeTypeDeclaration) td;
					List<InstanceDecl> todel = new ArrayList<InstanceDecl>();
					for (InstanceDecl inst : ctd.getInstances()) {
						if (torem.contains(inst.getType())) {
							todel.add(inst);
						}
					}
					if (! todel.isEmpty()) {
						ctd.getInstances().removeAll(todel);
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
							EcoreUtil.delete(statement);
						}
					}
				}
			}
			getLog().info("Removed "+ torem.size() + " GAL types that were empty due to variable simplifications.");
		}
		
		Instantiator.fuseIsomorphicEffects(spec);
		
		PropertySimplifier.rewriteWithInitialState(spec);
		getLog().info("Simplify gal took : " + (System.currentTimeMillis() - debut) + " ms"); //$NON-NLS-1$ //$NON-NLS-2$
		return toret;
	}

	/**
	 * Returns the set of variables that are constants, they may have been removed from s.
	 * @param s the GAL to simplify
	 * @return a set of constants that have been simplified
	 */
	public static Support simplify(GALTypeDeclaration s) {
		simplifyAllExpressions(s);

		simplifyAbort(s);

		simplifyFalseTransitions(s);		

		simplifyPetriStyleAssignments(s);

		Support toret = simplifyConstantVariables(s);

		simplifyAllExpressions(s);

		simplifyConstantIte(s);

		simplifyAbort(s);

		return toret;
	}

	/**
	 * Replace any sequence that contains an abort by a single abort statement.
	 * Get rid of any transition that has abort in its body statements.
	 * @param s
	 */
	public static int simplifyAbort(GALTypeDeclaration s) {

		List<Abort> toclear = new ArrayList<Abort>();
		// first collect occurrences of abort
		for (Transition t : s.getTransitions()) {
			for (TreeIterator<EObject> it = t.eAllContents() ; it.hasNext() ; ) {
				EObject obj = it.next();
				if (obj instanceof Abort) {
					toclear.add((Abort) obj);
				} else if (obj instanceof SelfCall || obj instanceof Assignment) {
					it.prune();
				}
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
				EList<Statement> statementList = (EList<Statement>) obj.eContainer().eGet(obj.eContainmentFeature());
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
		List<Ite> toreplace = new ArrayList<Ite>();


		for (Transition t : s.getTransitions()) {
			for (TreeIterator<EObject> it = t.eAllContents() ; it.hasNext() ; ) {
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
		List<Transition> todel = new ArrayList<Transition>();
		for (Transition t : s.getTransitions()) {
			if (t.getGuard() instanceof False) {
				todel.add(t);
			}
		}
		s.getTransitions().removeAll(todel);

		if (! todel.isEmpty()) 
			getLog().info("Removed "+ todel.size() + " false transitions.");

	}

	/** Identify and discard constant variables 
	 * @return */
	private static Support simplifyConstantVariables(GALTypeDeclaration s) {

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
		Set<NamedDeclaration> dontremove = new HashSet<NamedDeclaration>();
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
		Support toret = new Support();
		for (Variable var : constvars) {
			toret.add(var);
		}
		for (Entry<ArrayPrefix, Set<Integer>> e : constantArrs.entrySet()) {
			for (int val : e.getValue()) {
				toret.add(e.getKey(), val);
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
			getLog().info("Found a total of " + sum + " constant array cells/variables (out of "+ totalVars +" variables) \n "+sb.toString() );
		} else {
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
				totalexpr += replaceConstantRefs(constvars, constantArrs, todel, prop);
			}
		}

		
		// get rid of assignments to constants
		for (EObject obj : todel) {
			EcoreUtil.delete(obj);
		}

		StringBuilder stb = new StringBuilder();
		// Discard constants from state signature if possible
		for (Variable var : constvars) {
			stb.append(var.getName()+ ", ");
			EcoreUtil.delete(var);
		}
		if (!constvars.isEmpty()) {
			getLog().info("Removed " + constvars.size() + " constant variables :" + stb.substring(0, stb.length() -2) ); 
		}
		
		for (Entry<ArrayPrefix, Set<Integer>> e : constantArrs.entrySet()) {
			if (e.getValue().size() == e.getKey().getSize() && (! dontremove.contains(e.getKey()))) {
				EcoreUtil.delete(e.getKey());
			}
		}
		if (totalexpr != 0) {
			getLog().info(" Simplified "+ totalexpr + " expressions due to constant valuations.");
			simplifyAllExpressions(s);
		}
		return toret;
	}

	private static int replaceConstantRefs(Set<Variable> constvars,
			Map<ArrayPrefix, Set<Integer>> constantArrs, 
			List<EObject> todel, EObject t) {
		int totalexpr =0;
		for (TreeIterator<EObject> it = t.eAllContents() ; it.hasNext() ; ) {
			EObject obj = it.next();

			if (obj instanceof Assignment) {
				Assignment ass = (Assignment) obj;
				// kill assignments to constants
				if ( ass.getLeft().getIndex() == null && constvars.contains(ass.getLeft().getRef())
						|| ass.getLeft().getIndex() != null && ass.getLeft().getIndex() instanceof Constant && constantArrs.get(ass.getLeft().getRef()).contains(((Constant) ass.getLeft().getIndex()).getValue()) ) {
					todel.add(ass);
					it.prune();
				}					
			}
			if (obj instanceof VariableReference) {
				VariableReference va = (VariableReference) obj;

				if (va.getIndex() == null) {
					if (constvars.contains(va.getRef())) {
							EcoreUtil.replace(va, EcoreUtil.copy(((Variable)va.getRef()).getValue()));
							totalexpr++;
					}
					it.prune();
				} else if ( va.getIndex() instanceof Constant ) {
					int index = ((Constant) va.getIndex()).getValue();
					if (constantArrs.get(va.getRef()).contains(index) ) {
						EcoreUtil.replace(va, EcoreUtil.copy(((ArrayPrefix) va.getRef()).getValues().get(index)));						
						totalexpr++;
					}
					it.prune();
				}
			}
		}
		return totalexpr;
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

	private static boolean isPetriStyle(Transition tr) {

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
