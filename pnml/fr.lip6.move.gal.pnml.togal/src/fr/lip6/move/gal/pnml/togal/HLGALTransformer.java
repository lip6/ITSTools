package fr.lip6.move.gal.pnml.togal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.AssignType;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Statement;
import fr.lip6.move.gal.And;
import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.ComparisonOperators;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.GalFactory;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.Ite;
import fr.lip6.move.gal.ParamRef;
import fr.lip6.move.gal.Parameter;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.TypedefDeclaration;
import fr.lip6.move.gal.VariableReference;
import fr.lip6.move.gal.order.CompositeGalOrder;
import fr.lip6.move.gal.order.IOrder;
import fr.lip6.move.gal.order.OrderFactory;
import fr.lip6.move.gal.order.VarOrder;
import fr.lip6.move.gal.pnml.togal.utils.HLUtils;
import fr.lip6.move.gal.pnml.togal.utils.Utils;
import fr.lip6.move.gal.support.Support;
import fr.lip6.move.pnml.symmetricnet.terms.NamedSort;
import fr.lip6.move.pnml.symmetricnet.booleans.Bool;
import fr.lip6.move.pnml.symmetricnet.booleans.Equality;
import fr.lip6.move.pnml.symmetricnet.booleans.Inequality;
import fr.lip6.move.pnml.symmetricnet.booleans.Not;
import fr.lip6.move.pnml.symmetricnet.booleans.Or;
import fr.lip6.move.pnml.symmetricnet.cyclicEnumerations.Predecessor;
import fr.lip6.move.pnml.symmetricnet.cyclicEnumerations.Successor;
import fr.lip6.move.pnml.symmetricnet.dots.Dot;
import fr.lip6.move.pnml.symmetricnet.dots.DotConstant;
import fr.lip6.move.pnml.symmetricnet.finiteEnumerations.FEConstant;
import fr.lip6.move.pnml.symmetricnet.finiteEnumerations.FiniteEnumeration;
import fr.lip6.move.pnml.symmetricnet.finiteIntRanges.FiniteIntRange;
import fr.lip6.move.pnml.symmetricnet.finiteIntRanges.GreaterThan;
import fr.lip6.move.pnml.symmetricnet.finiteIntRanges.GreaterThanOrEqual;
import fr.lip6.move.pnml.symmetricnet.finiteIntRanges.LessThan;
import fr.lip6.move.pnml.symmetricnet.finiteIntRanges.LessThanOrEqual;
import fr.lip6.move.pnml.symmetricnet.hlcorestructure.Arc;
import fr.lip6.move.pnml.symmetricnet.hlcorestructure.Condition;
import fr.lip6.move.pnml.symmetricnet.hlcorestructure.HLMarking;
import fr.lip6.move.pnml.symmetricnet.hlcorestructure.Page;
import fr.lip6.move.pnml.symmetricnet.hlcorestructure.PetriNet;
import fr.lip6.move.pnml.symmetricnet.hlcorestructure.Place;
import fr.lip6.move.pnml.symmetricnet.hlcorestructure.PnObject;
import fr.lip6.move.pnml.symmetricnet.hlcorestructure.Transition;
import fr.lip6.move.pnml.symmetricnet.integers.Addition;
import fr.lip6.move.pnml.symmetricnet.integers.Division;
import fr.lip6.move.pnml.symmetricnet.integers.IntegerOperator;
import fr.lip6.move.pnml.symmetricnet.integers.Modulo;
import fr.lip6.move.pnml.symmetricnet.integers.Multiplication;
import fr.lip6.move.pnml.symmetricnet.integers.NumberConstant;
import fr.lip6.move.pnml.symmetricnet.integers.Subtraction;
import fr.lip6.move.pnml.symmetricnet.multisets.Add;
import fr.lip6.move.pnml.symmetricnet.multisets.All;
import fr.lip6.move.pnml.symmetricnet.multisets.NumberOf;
import fr.lip6.move.pnml.symmetricnet.multisets.Subtract;
import fr.lip6.move.pnml.symmetricnet.terms.OperatorDecl;
import fr.lip6.move.pnml.symmetricnet.terms.ProductSort;
import fr.lip6.move.pnml.symmetricnet.terms.Sort;
import fr.lip6.move.pnml.symmetricnet.terms.SortDecl;
import fr.lip6.move.pnml.symmetricnet.terms.Term;
import fr.lip6.move.pnml.symmetricnet.terms.Tuple;
import fr.lip6.move.pnml.symmetricnet.terms.UserOperator;
import fr.lip6.move.pnml.symmetricnet.terms.UserSort;
import fr.lip6.move.pnml.symmetricnet.terms.VariableDecl;

public class HLGALTransformer {

	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}

	private IOrder order = null;

	public IOrder getOrder() {
		return order;
	}
	
	public GALTypeDeclaration transform(PetriNet pn, Specification spec) {


		GALTypeDeclaration gal = GalFactory.eINSTANCE.createGALTypeDeclaration();
		gal.setName(Utils.normalizeName(pn.getName().getText()));
		spec.getTypes().add(gal);
		for (Page p : pn.getPages()) {
			handlePage(p, gal);
		}
		
		
		clear();
		return gal;
	}


	private void clear() {
		typedefs = new HashMap<NamedSort, TypedefDeclaration>();
	}

	private void handlePage(Page page, GALTypeDeclaration gal) {

		Map<String,List<Place>> placeSort = new HashMap<String, List<Place>>();
		Map<Place,ArrayPrefix> placeMap = new HashMap<Place, ArrayPrefix>();
		for (PnObject n : page.getObjects()) {
			if (n instanceof Place) {
				Place p = (Place) n;

				Sort psort = p.getType().getStructure();
				String sname = getSortName(psort);
				List<Place> pls = placeSort.get(sname);
				if (pls == null) {
					pls = new ArrayList<Place>();
					placeSort.put(sname, pls);
				}
				pls.add(p);
				
				int[] value = interpretMarking(p.getHlinitialMarking(),psort);
				ArrayPrefix ap = GalFactory.eINSTANCE.createArrayPrefix();
				ap.setSize(GF2.constant(value.length));
				if (p.getName() != null)
					ap.setName(Utils.normalizeName(p.getName().getText()));
				else 
					ap.setName(Utils.normalizeName(p.getId()));
				for (int val : value) {
					ap.getValues().add(constant(val));
				}
				ap.setComment("/** Place " + p.getName().getText() + " dom :" + psort.getContainerType().getText() + "*/");
				gal.getArrays().add(ap);
				placeMap.put(p, ap);
			}
		}

		getLog().info("Transformed "+ placeMap.size() + " places.");

		IOrder ord = OrderFactory.readOrder(gal, gal.getName());
		getLog().info("read order :" + ord);
		StringBuilder sb = new StringBuilder();
		for (Entry<String, List<Place>> r : placeSort.entrySet()) {
			sb.append(r.getKey() +"->");
			for (Place p : r.getValue()) {
				sb.append(p.getId() +",");
			}
			sb.append("\n");
		}
		getLog().info("sort/places :" +sb.toString());
		List<IOrder> orders = new ArrayList<IOrder>();
		for (Entry<String, List<Place>> ps : placeSort.entrySet()) {
			List<Place> places = ps.getValue(); 
			if (! places.isEmpty())
			{
				Sort psort = places.get(0).getType().getStructure();
				int sz = computeSortCardinality(psort); 

				if (sz > 1) {
					
					for (int i=0 ; i < sz ; i++) {
						Support supp = new Support();
						for (Place p : places) {
							supp.add(placeMap.get(p), i);
						}
						orders.add(new VarOrder(supp, Utils.normalizeName(ps.getKey()+i)));
					}
				} else {
					// dot case mostly
					for (Place p : places) {
						Support supp = new Support();
						supp.add(placeMap.get(p), 0);
						orders.add(new VarOrder(supp, placeMap.get(p).getName()));
					}
					
				}
			}
		}
		order = new CompositeGalOrder(orders, "main");

		
		for (PnObject pnobj : page.getObjects()) {
			if (pnobj instanceof Transition) {
				Transition t = (Transition) pnobj;
				fr.lip6.move.gal.Transition tr = GalFactory.eINSTANCE.createTransition();
				if (t.getName() != null)
					tr.setName(Utils.normalizeName(t.getName().getText()));
				else 
					tr.setName(Utils.normalizeName(t.getId()));

				Set<VariableDecl> vars= new HashSet<VariableDecl>(); 
				grabChildVariables(t,vars);
				for (Arc a : t.getInArcs()) {
					grabChildVariables(a, vars);
				}
				for (Arc a : t.getOutArcs()) {
					grabChildVariables(a, vars);
				}


				
				Map<VariableDecl,Parameter> varMap = new HashMap<VariableDecl, Parameter>();
				for (VariableDecl var : vars) {
					Parameter param = createParameter(var,gal);
					varMap.put(var,param);
					tr.getParams().add(param);
				}

				Condition cond = t.getCondition();
				BooleanExpression guard ;
				True tru = GalFactory.eINSTANCE.createTrue();
				if (cond != null ) {
					Term g = cond.getStructure();
					guard = convertToBoolean(g,varMap);
				} else {				
					guard = tru;
				}

				for (Arc arc : t.getInArcs()) {
					Place pl = (Place) arc.getSource();
					
					Map<VariableReference, Integer> refPl = buildRefsFromArc(arc.getHlinscription().getStructure(), pl.getType().getStructure(), placeMap.get(pl) ,varMap );

//					List<IntExpression> indexes = new ArrayList<IntExpression>();
					for (Entry<VariableReference, Integer> it : refPl.entrySet()) {
						BooleanExpression comp = GF2.createComparison(it.getKey(), ComparisonOperators.GE, GF2.constant(it.getValue()));
//						if (it.getKey() instanceof ArrayVarAccess) {
//							ArrayVarAccess ava = (ArrayVarAccess) it.getKey();
//							indexes.add(ava.getIndex());
//						}
						guard = GF2.and(guard, comp);						
					}
//					for (int i = 0; i < indexes.size(); i++) {
//						for (int j = i+1; j < indexes.size(); j++) {
//							// test if indexes are NOT equal
//							BooleanExpression neq = GF2.not(GF2.createComparison(EcoreUtil.copy(indexes.get(i)), ComparisonOperators.EQ, EcoreUtil.copy(indexes.get(j))));
//							// OR current marking greater than sum of taken tokens
//							ArrayVarAccess currentCell = GF2.createArrayVarAccess(placeMap.get(pl), EcoreUtil.copy(indexes.get(i)));
//							IntExpression maxval = GF2.createBinaryIntExpression(GF2.constant(refPl.get(indexes.get(i).eContainer())), "+", GF2.constant(refPl.get(indexes.get(j).eContainer())));
//							GF2.createComparison(currentCell, ComparisonOperators.GE, maxval);
//							
//						}
//					}
					
					
				}
				tr.setGuard(guard);
				for (Arc arc : t.getInArcs()) {
					Place pl = (Place) arc.getSource();

					boolean isTestArc = false;
					for (Arc abis : t.getOutArcs()) {
						if (abis.getTarget().equals(pl) && abis.getHlinscription().getText().equals(arc.getHlinscription().getText())) {
							isTestArc = true;
							break;
						}
					}
					if (isTestArc) {
						// skip test arc effects
						continue;
					}

					Map<VariableReference, Integer> refPl = buildRefsFromArc(arc.getHlinscription().getStructure(), pl.getType().getStructure(), placeMap.get(pl) ,varMap);

					for (Entry<VariableReference, Integer> it : refPl.entrySet()) {
						Statement ass = GF2.createIncrement(it.getKey(), - it.getValue()) ;
						if (refPl.size() > 1) {
							BooleanExpression condition = GF2.createComparison(EcoreUtil.copy(it.getKey()), ComparisonOperators.GE, constant(it.getValue()));						
							Ite ite = GalFactory.eINSTANCE.createIte();
							ite.setCond(condition);
							ite.getIfTrue().add(ass);
							ite.getIfFalse().add(GalFactory.eINSTANCE.createAbort());
							tr.getActions().add(ite);
						} else {
							tr.getActions().add(ass);
						}
					}
				}
				for (Arc arc : t.getOutArcs()) {
					Place pl = (Place) arc.getTarget();

					boolean isTestArc = false;
					for (Arc abis : t.getInArcs()) {
						if (abis.getSource().equals(pl) && abis.getHlinscription().getText().equals(arc.getHlinscription().getText())) {
							isTestArc = true;
							break;
						}
					}
					if (isTestArc) {
						// skip test arc effects
						continue;
					}

					Map<VariableReference, Integer> refPl = buildRefsFromArc(arc.getHlinscription().getStructure(), pl.getType().getStructure(), placeMap.get(pl) ,varMap);

					for (Entry<VariableReference, Integer> it : refPl.entrySet()) {
						Statement ass = GF2.createIncrement(it.getKey(), it.getValue());

						boolean wasRedundant = false;
						for (Statement act : tr.getActions()) {
							if (act instanceof Assignment) {
								Assignment ass2 = (Assignment) act;
								if (EcoreUtil.equals(ass2.getLeft(),it.getKey())) {
									if (ass2.getType() != AssignType.ASSIGN && ass2.getRight() instanceof Constant) {
										
										Constant c2 = (Constant) ass2.getRight();

										wasRedundant = true;

										int val2 = c2.getValue();
										
										int valtot ;
										if (ass2.getType() == AssignType.INCR) {
											valtot = it.getValue() + val2;
										} else {
											valtot = it.getValue() - val2;
										}
										if (valtot==0) {
											EcoreUtil.delete(ass2);
										} else if (valtot > 0) {
											ass2.setType(AssignType.INCR);;
											c2.setValue(valtot);
										} else {
											ass2.setType(AssignType.DECR);;
											c2.setValue(-valtot);
										}
									}
									break;
								}
							}
						}
						if (!wasRedundant) {
							tr.getActions().add(ass);
						}
					}
				}
				gal.getTransitions().add(tr);
			}
		}
		getLog().info("Transformed " + gal.getTransitions().size() + " transitions.");

		
	}






	private IntExpression convertToInt (Term g, Map<VariableDecl, Parameter> varMap) {
		if (g instanceof fr.lip6.move.pnml.symmetricnet.terms.Variable) {
			fr.lip6.move.pnml.symmetricnet.terms.Variable var = (fr.lip6.move.pnml.symmetricnet.terms.Variable) g;

			return GF2.createParamRef(varMap.get(var.getVariableDecl()));
		} else if (g instanceof IntegerOperator) {
			IntegerOperator io = (IntegerOperator) g;
			
			return GF2.createBinaryIntExpression(
						convertToInt(io.getSubterm().get(0), varMap), 
						toStringOp(io), 
						convertToInt(io.getSubterm().get(1), varMap));
		} else if (g instanceof NumberConstant) {
			NumberConstant nc = (NumberConstant) g;
			return GF2.constant(nc.getValue());
		} else if (g instanceof UserOperator) {
			UserOperator uo = (UserOperator) g;
			return GF2.constant(HLUtils.getConstantIndex(uo));
		} else if (g instanceof Predecessor) {
			Predecessor uo = (Predecessor) g;
			IntExpression left = convertToInt(uo.getSubterm().get(0), varMap);
			return GF2.createBinaryIntExpression(left, "-", GF2.constant(1));
		} else if (g instanceof Successor) {
			Successor uo = (Successor) g;
			IntExpression left = convertToInt(uo.getSubterm().get(0), varMap);
			return GF2.createBinaryIntExpression(left, "+", GF2.constant(1));
		} else {
			getLog().warning("Unknown arithmetic term or operator :" + g.getClass().getName());			
		}
		return constant(0);
	}

	private String toStringOp(IntegerOperator io) {
		if (io instanceof Addition) {
			return "+";
		} else if (io instanceof Division) {
			return "/";
		} else if (io instanceof Modulo) {
			return "%";
		} else if (io instanceof Multiplication	) {
			return "*";
		} else if (io instanceof Subtraction) {
			return "-";
		} else {
			getLog().warning("Unexpected operator type in arithmetic : " + io.getClass().getName());
		}
		return null;
	}

	private BooleanExpression convertToBoolean(Term g,	Map<VariableDecl, Parameter> varMap) {
		
		if (g instanceof fr.lip6.move.pnml.symmetricnet.booleans.And) {
			fr.lip6.move.pnml.symmetricnet.booleans.And and = (fr.lip6.move.pnml.symmetricnet.booleans.And) g;

			return GF2.and(convertToBoolean(and.getSubterm().get(0), varMap), convertToBoolean(and.getSubterm().get(1), varMap));
		} else if (g instanceof Or) {
			Or or = (Or) g;

			return GF2.or(convertToBoolean(or.getSubterm().get(0), varMap), convertToBoolean(or.getSubterm().get(1), varMap));
		} else if (g instanceof Not) {
			Not not = (Not) g;
			return GF2.not(convertToBoolean(not.getSubterm().get(0), varMap));
		} else if (g instanceof Equality) {
			Equality equ = (Equality) g;
			return GF2.createComparison(
						convertToInt(equ.getSubterm().get(0), varMap), 
						ComparisonOperators.EQ, 
						convertToInt(equ.getSubterm().get(1), varMap));
		} else if (g instanceof Inequality) {
			Inequality equ = (Inequality) g;
			return GF2.createComparison(
					convertToInt(equ.getSubterm().get(0), varMap), 
					ComparisonOperators.NE, 
					convertToInt(equ.getSubterm().get(1), varMap));
		} else if (g instanceof LessThanOrEqual) {
			LessThanOrEqual equ = (LessThanOrEqual) g;
			return GF2.createComparison(
					convertToInt(equ.getSubterm().get(0), varMap), 
					ComparisonOperators.LE, 
					convertToInt(equ.getSubterm().get(1), varMap));
		} else if (g instanceof LessThan) {
			LessThan equ = (LessThan)g;
			return GF2.createComparison(
					convertToInt(equ.getSubterm().get(0), varMap), 
					ComparisonOperators.LT, 
					convertToInt(equ.getSubterm().get(1), varMap));
		} else if (g instanceof GreaterThanOrEqual) {
			GreaterThanOrEqual equ = (GreaterThanOrEqual) g;
			return GF2.createComparison(
					convertToInt(equ.getSubterm().get(0), varMap), 
					ComparisonOperators.GE, 
					convertToInt(equ.getSubterm().get(1), varMap));
		} else if (g instanceof GreaterThan) {
			GreaterThan equ = (GreaterThan)g;
			return GF2.createComparison(
					convertToInt(equ.getSubterm().get(0), varMap), 
					ComparisonOperators.GT, 
					convertToInt(equ.getSubterm().get(1), varMap));
		} else {
			getLog().warning("Unknown boolean operator encountered " + g.getClass().getName());
		}
		return GalFactory.eINSTANCE.createTrue();
	}

	private void grabChildVariables(EObject t, Set<VariableDecl> vars) {
		for (TreeIterator<EObject> it = t.eAllContents(); it.hasNext();) {
			EObject obj = it.next();
			if (obj instanceof fr.lip6.move.pnml.symmetricnet.terms.Variable) {
				fr.lip6.move.pnml.symmetricnet.terms.Variable var = (fr.lip6.move.pnml.symmetricnet.terms.Variable) obj;
				vars.add(var.getVariableDecl());
			}

		}
	}

	private Parameter createParameter(VariableDecl var, GALTypeDeclaration gal) {
		return GF2.createParameter("$"+var.getName(), findOrCreateTypeDef(gal, var.getSort()));
	}

	Map<NamedSort, TypedefDeclaration> typedefs = new HashMap<NamedSort, TypedefDeclaration>();
	private TypedefDeclaration findOrCreateTypeDef(GALTypeDeclaration gal,	Sort sort) {
		if (sort instanceof UserSort) {
			NamedSort ns = (NamedSort) ((UserSort) sort).getDeclaration();
			TypedefDeclaration toret = typedefs.get(ns);
			if (toret == null) {
				toret = GF2.createTypeDef(ns.getName(), 0, computeSortCardinality(sort)-1);
				gal.getTypedefs().add(toret);
				typedefs.put(ns, toret);			
			}
			return toret;
		} else {
			getLog().warning("problem finding type for variable");
			return null;
		}
	}

	private int[] interpretMarking(HLMarking hlinitialMarking, Sort psort) {
		if (hlinitialMarking == null) {
			return new int[computeSortCardinality(psort)];
		}
		return interpretMarkingTerm(hlinitialMarking.getStructure(), psort);
	}

	private Map<VariableReference, Integer> buildRefsFromArc(Term term, Sort psort, ArrayPrefix place, Map<VariableDecl, Parameter> varMap) {
		Map<VariableReference,Integer> toret = new HashMap<VariableReference, Integer>();

		if (term instanceof All) {
			// All all = (All) term;
			int size = computeSortCardinality(psort);
			for (int i = 0; i < size; i++) {
				VariableReference va = GF2.createArrayVarAccess(place, constant(i));
				add(toret,va,1);
			}
		} else if (term instanceof NumberOf) {
			NumberOf no = (NumberOf) term;
			int card = HLUtils.getCardinality(no);

			Map<VariableReference, Integer> token = buildRefsFromArc(no.getSubterm().get(1), psort, place, varMap);

			for (Entry<VariableReference, Integer> it : token.entrySet()) {
				add( toret, it.getKey(), it.getValue()*card);
			}
		} else if (term instanceof UserOperator) {
			// Probably designating a constant of the type
			UserOperator uo = (UserOperator) term;
			int index = HLUtils.getConstantIndex(uo);

			VariableReference va = GF2.createArrayVarAccess(place, constant(index));
			add(toret,va,1);
		} else if (term instanceof DotConstant) {
			VariableReference va = GF2.createArrayVarAccess(place, constant(0));
			add(toret,va,1);				
		} else if (term instanceof fr.lip6.move.pnml.symmetricnet.terms.Variable) {
			// Probably designating a constant of the type
			fr.lip6.move.pnml.symmetricnet.terms.Variable var = (fr.lip6.move.pnml.symmetricnet.terms.Variable) term;
			Parameter param = varMap.get(var.getVariableDecl());
			ParamRef pr = GF2.createParamRef(param);

			VariableReference va = GF2.createArrayVarAccess(place, pr);
			add(toret,va,1);

		} else if (term instanceof Tuple) {
			Tuple tuple = (Tuple) term;
			// hopefully, only constants in the tuple
			int tot = 1;
			IntExpression zero = GF2.constant(0);
			IntExpression target= zero;
			
			for (int i = tuple.getSubterm().size() -1 ; i >= 0 ; i--) {
				Term elem = tuple.getSubterm().get(i);
				IntExpression value =null;
				Sort elemSort = null;
				if (elem instanceof UserOperator) {
					UserOperator uo = (UserOperator) elem;
					int cst = HLUtils.getConstantIndex(uo);
					FEConstant fec = (FEConstant) uo.getDeclaration();
					elemSort = fec.getSort();

					value = GF2.constant(cst);
				} else if (elem instanceof fr.lip6.move.pnml.symmetricnet.terms.Variable) {
					// Probably designating a constant of the type
					fr.lip6.move.pnml.symmetricnet.terms.Variable var = (fr.lip6.move.pnml.symmetricnet.terms.Variable) elem;
					elemSort = var.getVariableDecl().getSort();
					Parameter param = varMap.get(var.getVariableDecl());
					value = GF2.createParamRef(param);
				} else if (elem instanceof Predecessor) {
					Predecessor pred = (Predecessor) elem;
					// Probably designating a constant of the type
					fr.lip6.move.pnml.symmetricnet.terms.Variable var = (fr.lip6.move.pnml.symmetricnet.terms.Variable) pred.getSubterm().get(0);
					Parameter param = varMap.get(var.getVariableDecl());
					ParamRef pr = GF2.createParamRef(param);

					IntExpression bin = GF2.createBinaryIntExpression(pr, "-", GF2.constant(1));
										
					elemSort = var.getVariableDecl().getSort();
					IntExpression max = GF2.constant(computeSortCardinality(elemSort));
					
					// wrap function for negative integer i : 
					// ( (i % max) + max ) % max )
					
					IntExpression mod = GF2.createBinaryIntExpression(bin, "%", EcoreUtil.copy(max));
					
					IntExpression sum = GF2.createBinaryIntExpression(mod, "+", EcoreUtil.copy(max));
					
					value = GF2.createBinaryIntExpression(sum, "%", max);
										
				} else if (elem instanceof Successor) {
					Successor pred = (Successor) elem;
					// Probably designating a constant of the type
					fr.lip6.move.pnml.symmetricnet.terms.Variable var = (fr.lip6.move.pnml.symmetricnet.terms.Variable) pred.getSubterm().get(0);
					Parameter param = varMap.get(var.getVariableDecl());
					ParamRef pr = GF2.createParamRef(param);

					IntExpression bin = GF2.createBinaryIntExpression(pr, "+", GF2.constant(1));
					
					elemSort = var.getVariableDecl().getSort();
					
					IntExpression mod = GF2.createBinaryIntExpression(bin, "%", GF2.constant(computeSortCardinality(elemSort)));
					
					value = mod;
					
				} else {
					getLog().warning("unrecognized term type " + elem.getClass().getName());
					value = GF2.constant(1);
				}

				IntExpression pres ;
				if (tot != 1) {
					pres = GF2.createBinaryIntExpression(value, "*", GF2.constant(tot));
				} else {
					pres = value;
				}

				if (target != zero) {
					target = GF2.createBinaryIntExpression(pres, "+", target);
				} else {
					target = pres;
				}

				tot *= computeSortCardinality(elemSort);
			}
			VariableReference va = GF2.createArrayVarAccess(place, target);
			add(toret,va,1);

		} else if (term instanceof Add) {
			Add add = (Add) term;
			for (Term t : add.getSubterm()) {
				Map<VariableReference, Integer> toadd = buildRefsFromArc(t, psort, place, varMap);
				for (Entry<VariableReference, Integer> it : toadd.entrySet()) {
					add( toret, it.getKey(), it.getValue());
				}
			}
		} else if (term instanceof Subtract) {
			Subtract add = (Subtract) term;
			int nbterm = 0;
			for (Term t : add.getSubterm()) {
				Map<VariableReference, Integer> toadd = buildRefsFromArc(t, psort, place, varMap);
				for (Entry<VariableReference, Integer> it : toadd.entrySet()) {
					if (nbterm == 0) {
						// the first term minus the next ones
						add( toret, it.getKey(), + it.getValue());
					} else {
						add( toret, it.getKey(), - it.getValue());						
					}
				}
				nbterm++;
			}
		} else if (term instanceof Predecessor) {
			Predecessor pred = (Predecessor) term;
			// Probably designating a constant of the type
			fr.lip6.move.pnml.symmetricnet.terms.Variable var = (fr.lip6.move.pnml.symmetricnet.terms.Variable) pred.getSubterm().get(0);
			Parameter param = varMap.get(var.getVariableDecl());
			ParamRef pr = GF2.createParamRef(param);

			IntExpression bin = GF2.createBinaryIntExpression(pr, "-", GF2.constant(1));
								
			IntExpression max = GF2.constant(computeSortCardinality(var.getVariableDecl().getSort()));
			
			// wrap function for negative integer i : 
			// ( (i % max) + max ) % max )
			
			IntExpression mod = GF2.createBinaryIntExpression(bin, "%", EcoreUtil.copy(max)); 
			
			IntExpression sum = GF2.createBinaryIntExpression(mod, "+", EcoreUtil.copy(max));
			
			IntExpression mod2 = GF2.createBinaryIntExpression(sum, "%", max);
			
			VariableReference va = GF2.createArrayVarAccess(place, mod2);
			add(toret,va,1);
			
		} else if (term instanceof Successor) {
			Successor pred = (Successor) term;
			// Probably designating a constant of the type
			fr.lip6.move.pnml.symmetricnet.terms.Variable var = (fr.lip6.move.pnml.symmetricnet.terms.Variable) pred.getSubterm().get(0);
			Parameter param = varMap.get(var.getVariableDecl());
			ParamRef pr = GF2.createParamRef(param);

			IntExpression bin = GF2.createBinaryIntExpression(pr, "+", GF2.constant(1));

			IntExpression max = GF2.constant(computeSortCardinality(var.getVariableDecl().getSort()));

			IntExpression mod = GF2.createBinaryIntExpression(bin, "%", max);
			
			VariableReference va = GF2.createArrayVarAccess(place, mod);
			add(toret,va,1);
				
		} else {
			getLog().warning("Encountered unknown term in arc inscription " + term.getClass().getName());
		}


		return toret;
	}

	private void add(Map<VariableReference, Integer> toret, VariableReference va, int i) {
		Integer old = toret.get(va);
		if (old==null) {
			toret.put(va, i);
		} else {
			toret.put(va, i+old);
		}
	}

	private int[] interpretMarkingTerm(Term term, Sort psort) {
		int [] toret = new int[computeSortCardinality(psort)];

		if (term instanceof All) {
			// All all = (All) term;
			for (int i = 0; i < toret.length; i++) {
				toret[i]++;
			}
		} else if (term instanceof NumberOf) {
			NumberOf no = (NumberOf) term;
			int card = HLUtils.getCardinality(no);
			int tokenindex = 1;
			if (no.getSubterm().size() == 1) {
				// this could happen if the pnml input is malformed and numberOf has no cardinality.
				tokenindex = 0;
			}
			int [] token = interpretMarkingTerm(no.getSubterm().get(tokenindex),psort);
			for (int i =0 ; i < token.length ; ++i ) {
				toret [i] = card * token[i];
			}
		} else if (term instanceof UserOperator) {
			// Probably designating a constant of the type
			UserOperator uo = (UserOperator) term;
			int index = HLUtils.getConstantIndex(uo);
			toret[index]++;

		} else if (term instanceof Tuple) {
			Tuple tuple = (Tuple) term;
			// hopefully, only constants in the tuple
			int tot = 1;
			int target=0;
			for (int i = tuple.getSubterm().size() -1 ; i >= 0 ; i--) {
				Term elem = tuple.getSubterm().get(i);
				if (elem instanceof UserOperator) {
					UserOperator uo = (UserOperator) elem;
					target += tot * HLUtils.getConstantIndex(uo);
					tot *= computeSortCardinality( ((FEConstant)uo.getDeclaration()).getSort());
				}
			}
			toret[target] = 1;
		} else if (term instanceof Add) {
			Add add = (Add) term;
			for (Term t : add.getSubterm()) {
				int [] toadd = interpretMarkingTerm(t,psort);
				for (int i=0; i < toadd.length ; ++i) {
					toret [i] += toadd[i];
				}
			}
		} else if (term instanceof Subtract) {
			Subtract add = (Subtract) term;
			assert (((Subtract) term).getSubterm().size()==2);
			int [] toadd = interpretMarkingTerm(add.getSubterm().get(0),psort);
			int [] torem = interpretMarkingTerm(add.getSubterm().get(1),psort);
			
			for (int i=0; i < toadd.length ; ++i) {
					toret [i] += toadd[i];
					toret [i] -= torem[i];
 			}
			
		} else if (term instanceof DotConstant) {
			toret[0]=1;
		} else {
			getLog().warning("Encountered unknown type in marking term :" +term.getClass());
		}


		return toret;
	}



	private HashMap<Sort, Integer> cache = new HashMap<Sort, Integer>();
	private int computeSortCardinality(Sort psort) {
		Integer val = cache.get(psort); 
		if (val==null) {
			val = _computeSortCardinality(psort);
			cache.put(psort,val);
		}
		return val;
	}

	private String getSortName (Sort psort) {
		if (psort instanceof Bool) 	{
			return "bool";
		} else if (psort instanceof Dot) {
			return "dot";
		} else if (psort instanceof FiniteEnumeration) {
//			FiniteEnumeration fe = (FiniteEnumeration) psort;
			return "enum";
		} else if (psort instanceof FiniteIntRange) {
//			FiniteIntRange fir = (FiniteIntRange) psort;
			return "range";
		} else if (psort instanceof ProductSort) {
			ProductSort ps = (ProductSort) psort;
			String name = ""; 
			for (Sort e : ps.getElementSort()) {
				name += getSortName(e);
			}
			return name;
		} else if (psort instanceof UserSort) {
			UserSort us = (UserSort) psort;
			SortDecl sdecl = us.getDeclaration();
			if (sdecl instanceof NamedSort) {
				NamedSort names = (NamedSort) sdecl;
				return names.getName();
			}

			//		} else if (psort instanceof CyclicEnumeration) {
			//			CyclicEnumeration ce = (CyclicEnumeration) psort;
			//			
		} else {
			getLog().warning("Unknown Sort element encountered in input file : " + psort);
		}
		return "";
		
	}

	private Integer _computeSortCardinality(Sort psort) {
		if (psort instanceof Bool) 	{
			return 2;
		} else if (psort instanceof Dot) {
			return 1;
		} else if (psort instanceof FiniteEnumeration) {
			FiniteEnumeration fe = (FiniteEnumeration) psort;
			return fe.getElements().size();
		} else if (psort instanceof FiniteIntRange) {
			FiniteIntRange fir = (FiniteIntRange) psort;
			return fir.getEnd() - fir.getStart();
		} else if (psort instanceof ProductSort) {
			ProductSort ps = (ProductSort) psort;
			int sum = 1; 
			for (Sort e : ps.getElementSort()) {
				sum *= computeSortCardinality(e);
			}
			return sum;
		} else if (psort instanceof UserSort) {
			UserSort us = (UserSort) psort;
			SortDecl sdecl = us.getDeclaration();
			if (sdecl instanceof NamedSort) {
				NamedSort names = (NamedSort) sdecl;
				Sort trueSort = names.getSortdef();
				return computeSortCardinality(trueSort);
			}

			//		} else if (psort instanceof CyclicEnumeration) {
			//			CyclicEnumeration ce = (CyclicEnumeration) psort;
			//			
		} else {
			getLog().warning("Unknown Sort element encountered in input file : " + psort);
		}
		return 0;

	}

	
	private static IntExpression constant(int val) {
		return GF2.constant(val);
	}
}
