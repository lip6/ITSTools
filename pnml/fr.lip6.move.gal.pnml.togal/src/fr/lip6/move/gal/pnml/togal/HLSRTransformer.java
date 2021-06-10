package fr.lip6.move.gal.pnml.togal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import fr.lip6.move.gal.order.IOrder;
import fr.lip6.move.gal.pnml.togal.utils.EqualityHelperUpToPerm;
import fr.lip6.move.gal.pnml.togal.utils.HLUtils;
import fr.lip6.move.gal.pnml.togal.utils.Utils;
import fr.lip6.move.gal.structural.SparseHLPetriNet;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.expr.Param;
import fr.lip6.move.gal.util.Pair;
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
import fr.lip6.move.pnml.symmetricnet.finiteIntRanges.FiniteIntRangeConstant;
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
import fr.lip6.move.pnml.symmetricnet.partitions.PartitionElement;
import fr.lip6.move.pnml.symmetricnet.terms.ProductSort;
import fr.lip6.move.pnml.symmetricnet.terms.Sort;
import fr.lip6.move.pnml.symmetricnet.terms.SortDecl;
import fr.lip6.move.pnml.symmetricnet.terms.Term;
import fr.lip6.move.pnml.symmetricnet.terms.Tuple;
import fr.lip6.move.pnml.symmetricnet.terms.UserOperator;
import fr.lip6.move.pnml.symmetricnet.terms.UserSort;
import fr.lip6.move.pnml.symmetricnet.terms.VariableDecl;

public class HLSRTransformer {

	private static final int DEBUG = 0;

	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}

	private IOrder order = null;
	private static boolean firstError = true;

	public IOrder getOrder() {
		return order;
	}
	
	public SparseHLPetriNet transform(PetriNet pn) {
		SparseHLPetriNet res = new SparseHLPetriNet();
		res.setName(Utils.normalizeName(pn.getName().getText()));
		for (Page p : pn.getPages()) {
			handlePage(p, res);
		}
		return res;
	}


	private void handlePage(Page page, SparseHLPetriNet res) {
		long time = System.currentTimeMillis();
		Map<Place,Integer> placeMap = new HashMap<>();
		Map<String,List<Place>> placeSort = new HashMap<>();
		
		boolean isOneSafe = true;
		try {
			for (PnObject n : page.getObjects()) {
				if (n instanceof Place) {
					Place p = (Place) n;

					Sort psort = p.getType().getStructure();
					String sname = getSortName(psort);
					placeSort.computeIfAbsent(sname, v -> new ArrayList<>()).add(p);

					int[] value = interpretMarking(p.getHlinitialMarking(),psort);
					if (Arrays.stream(value).sum()>1) {
						isOneSafe = false;
					}
					int index = res.addPlace(Utils.normalizeName(p.getId()), value, Utils.normalizeName(sname));
					placeMap.put(p, index);
				}
			}
		} catch (NegativeArraySizeException nase) {
			if (! isOneSafe) {
				throw new OverlargeMarkingException();
			} else {
				throw nase;
			}
		}

		
		if (DEBUG >= 1) {
			StringBuilder sb = new StringBuilder();
			for (Entry<String, List<Place>> r : placeSort.entrySet()) {
				sb.append(r.getKey() +"->");
				for (Place p : r.getValue()) {
					sb.append(p.getId() +",");
				}
				sb.append("\n");
			}
			getLog().info("sort/places :\n" +sb.toString());
		}

		Set<Integer> constPlaces = new HashSet<>();
		for (int i=0; i < res.getPlaces().size() ; i++) {
			constPlaces.add(i);
		}
		double totalt = 0;
		for (PnObject pnobj : page.getObjects()) {
			if (pnobj instanceof Transition) {
				Transition t = (Transition) pnobj;
				
				Set<VariableDecl> vars= new HashSet<>(); 
				grabChildVariables(t,vars);
				for (Arc a : t.getInArcs()) {
					grabChildVariables(a, vars);
				}
				for (Arc a : t.getOutArcs()) {
					grabChildVariables(a, vars);
				}
				
				double tbind = 1;
				List<Param> params = new ArrayList<>();
				Map<VariableDecl,Param> varMap = new HashMap<>();
				for (VariableDecl var : vars) {
					Param param = createParameter(var);
					varMap.put(var,param);
					params.add(param);
					tbind *= param.size();
				}
				totalt += tbind;
				
				Expression guard = Expression.constant(true);
				Condition cond = t.getCondition();
				if (cond != null ) {
					Term g = cond.getStructure();
					guard = Expression.op(Op.AND, guard, convertToBoolean(g,varMap));
				}

				Expression constraint = detectBindingSymmetry (varMap, t); 
				guard = Expression.op(Op.AND, constraint, guard);
							
				int tind = res.addTransition(Utils.normalizeName(t.getId()), params, guard);
				
				Set<Integer> effective = new HashSet<>();
				Map<Integer,String> seenIn = new HashMap<>(); 
				for (Arc arc : t.getInArcs()) {
					Place pl = (Place) arc.getSource();					
					int pind = placeMap.get(pl);
					seenIn.put(pind,arc.getHlinscription().getText());
					effective.add(pind);
					List<Pair<Expression,Integer>> funcs = buildRefsFromArc(arc.getHlinscription().getStructure(), pl.getType().getStructure(), varMap );
					for (Pair<Expression, Integer> tok : funcs) {
						res.addPreArc(pind, tind, tok.getFirst(), tok.getSecond());						
					}
				}
				for (Arc arc : t.getOutArcs()) {
					Place pl = (Place) arc.getTarget();
					int pind = placeMap.get(pl);
					if (arc.getHlinscription().getText().equals(seenIn.get(pind))) {
						effective.remove(pind);
					} else {
						effective.add(pind);
					}
					List<Pair<Expression,Integer>> funcs = buildRefsFromArc(arc.getHlinscription().getStructure(), pl.getType().getStructure(), varMap );
					for (Pair<Expression, Integer> tok : funcs) {
						res.addPostArc(pind, tind, tok.getFirst(), tok.getSecond());						
					}
				}
				constPlaces.removeAll(effective);
			}
		}		
		long flatp=0;
		for (Integer ind : constPlaces) {
			res.getPlaces().get(ind).setConstant(true);
			flatp += res.getPlaces().get(ind).getInitial().length ;
		}
		if (!constPlaces.isEmpty()) {
			getLog().info("Detected "+ constPlaces.size() + " constant HL places corresponding to " + flatp + " PT places.");
		}
		getLog().info("Imported "+ res.getPlaces().size() + " HL places and " + res.getTransitions().size()+ " HL transitions for a total of " 
				+ res.getPlaceCount() + " PT places and " + totalt + " transition bindings in " + (System.currentTimeMillis() - time) + " ms.");
	}





	/** Detect binding symmetries and exploit them to reduce the number of bindings to be considered.
	 * e.g. <p1>+<p2>  => we can enforce $p1 <= $p2
	 * @param varMap the variables of the transition and their image parameter
	 * @param t the transition for which bindings must be symmetric
	 * @return a constraint that can be added to the guard to limit bindings of symmetric paramters
	 */
	private Expression detectBindingSymmetry(Map<VariableDecl, Param> varMap, Transition t) {
		
		List<VariableDecl> keys = new ArrayList<VariableDecl>(varMap.keySet());
		
		Expression constraint = Expression.constant(true);
		for (int i=0 ; i < keys.size() ; i++) {
			for (int j=i+1 ; j < keys.size() ; j++) {
				VariableDecl var1 = keys.get(i);
				VariableDecl var2 = keys.get(j);
				Param p1 = varMap.get(var1 );
				Param p2 = varMap.get(var2);
				if (p1.size() == p2.size()) {
					if (areSymmetric(t, var1, var2)) {
						getLog().info(var1.getName()+" symmetric to "+var2.getName()+ " in transition "+ t.getId());
						constraint = Expression.op(Op.AND, constraint, Expression.op(Op.GEQ ,Expression.paramRef(p1), Expression.paramRef(p2)));
						keys.set(i, var2);
						keys.remove(j);
						//we've shifted the list
						j--;
					}
					
				}
			}
		}
		return constraint;
	}
	/**
	 * Returns true if the two variables var1 and var2, assumed to be compatible, are symmetric, 
	 * i.e. any token that mentions var1 has a corresponding matched token using var2.
	 * TODO : this is not as sophisticated as algorithms of Snow, our CAMI to GreatSPN engine, by a wide margin. We could do more refined symmetry analysis.
	 * @param t a HL transition
	 * @param var1 variable declaration
	 * @param var2
	 * @return true iff. var1 and var2 could be permuted (we could switch their names if you like) without affecting transition semantics.
	 */
	public boolean areSymmetric(Transition t, VariableDecl var1,
			VariableDecl var2) {
		Set<VariableDecl> guardvars = new HashSet<VariableDecl>();
		grabChildVariables(t.getCondition(), guardvars );
		if (guardvars.contains(var1) || guardvars.contains(var2)) {
			return false;
		}
		// iterate over all color functions of connected arcs 
		for (Arc arc : Utils.concat(t.getInArcs(),t.getOutArcs()) ) {
			
			Term cfunc = arc.getHlinscription().getStructure();

			// we store tokens in these lists that concern v1 or v2
			// e.g. p1term = 1'<p,v1>  p2term = 1'<p,v2>
			List<Term> p1term = new ArrayList<Term>();
			List<Term> p2term = new ArrayList<Term>();
							
			if (cfunc instanceof Add) {
				// descend into sums of tokens
				Add add = (Add) cfunc;							
				for (Term tok : add.getSubterm()) {
					findVarRefsInTokens(tok, p1term, var1, p2term, var2);
				}
			} else if (cfunc instanceof NumberOf) {
				// single token case normally
				findVarRefsInTokens(cfunc, p1term, var1, p2term, var2);
			} else {
				// ?? maybe a subtract or smething
				if (firstError ) {
					getLog().warning("Unknown color function,"+ cfunc.eClass().getName() + " skipping symmetry detection on parameters for transition "+t.getId());
					firstError = false;
				}
				return  false;
			}
			
			// cant hope to pair them off...
			if (p1term.size() != p2term.size()) {
				return  false;
			}

			// we now have two sets of tokens, we hope they can be paired off
			for (Term p1t : p1term) {
				boolean foundmatch = false;
				for (Term p2t : p2term) {
					if (equalsUpToPerm(p1t,p2t,var1,var2)) {
						p2term.remove(p2t);
						foundmatch = true;
						break;
					}
				}
				if (! foundmatch) {
					return false;
				}
			}
			if (! p2term.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	private boolean equalsUpToPerm(Term p1t, Term p2t, VariableDecl var1, VariableDecl var2) {
	    EqualityHelperUpToPerm equalityHelper = new EqualityHelperUpToPerm(var1,var2);
	    return equalityHelper.equals(p1t, p2t);
	}

	private void findVarRefsInTokens(Term tok, List<Term> p1term,
			VariableDecl var1, List<Term> p2term, VariableDecl var2) {
		// grab tokens that refer to p1 or p2
		for (TreeIterator<EObject> it = tok.eAllContents(); it.hasNext();) {
			EObject obj = it.next();
			if (obj instanceof fr.lip6.move.pnml.symmetricnet.terms.Variable) {
				fr.lip6.move.pnml.symmetricnet.terms.Variable var = (fr.lip6.move.pnml.symmetricnet.terms.Variable) obj;
				if (var.getVariableDecl() == var1) {
					p1term.add(tok);
					return;
				} else if (var.getVariableDecl() == var2) {
					p2term.add(tok);
					return;
				}
			}
		}
	}

	private Expression convertToInt (Term g, Map<VariableDecl, Param> varMap) {
		if (g instanceof fr.lip6.move.pnml.symmetricnet.terms.Variable) {
			fr.lip6.move.pnml.symmetricnet.terms.Variable var = (fr.lip6.move.pnml.symmetricnet.terms.Variable) g;

			return Expression.paramRef(varMap.get(var.getVariableDecl()));
		} else if (g instanceof IntegerOperator) {
			IntegerOperator io = (IntegerOperator) g;
			
			return Expression.op(toOp(io),
						convertToInt(io.getSubterm().get(0), varMap), 						 
						convertToInt(io.getSubterm().get(1), varMap));
		} else if (g instanceof NumberConstant) {
			NumberConstant nc = (NumberConstant) g;
			return Expression.constant(Math.toIntExact(nc.getValue()));
		} else if (g instanceof UserOperator) {
			UserOperator uo = (UserOperator) g;
			return Expression.constant(HLUtils.getConstantIndex(uo));
		} else if (g instanceof Predecessor) {
			Predecessor uo = (Predecessor) g;
			Expression left = convertToInt(uo.getSubterm().get(0), varMap);
			return Expression.op(Op.MINUS, left, Expression.constant(1));
		} else if (g instanceof Successor) {
			Successor uo = (Successor) g;
			Expression left = convertToInt(uo.getSubterm().get(0), varMap);
			return Expression.op(Op.ADD, left, Expression.constant(1));
		} else if (g instanceof FiniteIntRangeConstant) {
			FiniteIntRangeConstant firc = (FiniteIntRangeConstant) g;			
			return Expression.constant(Math.toIntExact(firc.getValue() - firc.getRange().getStart()));
		} else {
			getLog().warning("Unknown arithmetic term or operator :" + g.getClass().getName());			
		}
		return Expression.constant(0);
	}

	private Op toOp(IntegerOperator io) {
		if (io instanceof Addition) {
			return Op.ADD;
		} else if (io instanceof Division) {
			return Op.DIV;
		} else if (io instanceof Modulo) {
			return Op.MOD;
		} else if (io instanceof Multiplication	) {
			return Op.MULT;
		} else if (io instanceof Subtraction) {
			return Op.MINUS;
		} else {
			getLog().warning("Unexpected operator type in arithmetic : " + io.getClass().getName());
		}
		return null;
	}

	private Expression convertToBoolean(Term g,	Map<VariableDecl, Param> varMap) {
		
		if (g instanceof fr.lip6.move.pnml.symmetricnet.booleans.And) {
			fr.lip6.move.pnml.symmetricnet.booleans.And and = (fr.lip6.move.pnml.symmetricnet.booleans.And) g;
			if (and.getSubterm().size() == 2)
				return Expression.op(Op.AND, convertToBoolean(and.getSubterm().get(0), varMap), convertToBoolean(and.getSubterm().get(1), varMap));
			else if (and.getSubterm().size() == 1) {
				getLog().warning("AND operator with single subterm is malformed PNML.");
				return convertToBoolean(and.getSubterm().get(0), varMap);
			} else {
				List<Expression> children = and.getSubterm().stream().map(st-> convertToBoolean(st, varMap)).collect(Collectors.toList());
				return Expression.nop(Op.AND,children);
			}
		} else if (g instanceof Or) {
			Or or = (Or) g;
			if (or.getSubterm().size() == 2)
				return Expression.op(Op.OR, convertToBoolean(or.getSubterm().get(0), varMap), convertToBoolean(or.getSubterm().get(1), varMap));
			else if (or.getSubterm().size() == 1){
				getLog().warning("OR operator with single subterm is malformed PNML.");
				return convertToBoolean(or.getSubterm().get(0), varMap);
			} else {
				List<Expression> children = or.getSubterm().stream().map(st-> convertToBoolean(st, varMap)).collect(Collectors.toList());
				return Expression.nop(Op.OR,children);
			}
		} else if (g instanceof Not) {
			Not not = (Not) g;
			return Expression.not(convertToBoolean(not.getSubterm().get(0), varMap));
		} else if (g instanceof Equality) {
			Equality equ = (Equality) g;
			return Expression.op(Op.EQ,
						convertToInt(equ.getSubterm().get(0), varMap), 
						convertToInt(equ.getSubterm().get(1), varMap));
		} else if (g instanceof Inequality) {
			Inequality equ = (Inequality) g;
			return Expression.op(Op.NEQ,
					convertToInt(equ.getSubterm().get(0), varMap), 
					convertToInt(equ.getSubterm().get(1), varMap));
		} else if (g instanceof LessThanOrEqual) {
			LessThanOrEqual equ = (LessThanOrEqual) g;
			return Expression.op(Op.LEQ,
					convertToInt(equ.getSubterm().get(0), varMap), 					 
					convertToInt(equ.getSubterm().get(1), varMap));
		} else if (g instanceof LessThan) {
			LessThan equ = (LessThan)g;
			return Expression.op(Op.LT,
					convertToInt(equ.getSubterm().get(0), varMap), 					
					convertToInt(equ.getSubterm().get(1), varMap));
		} else if (g instanceof GreaterThanOrEqual) {
			GreaterThanOrEqual equ = (GreaterThanOrEqual) g;
			return Expression.op(Op.GEQ,
					convertToInt(equ.getSubterm().get(0), varMap), 
					convertToInt(equ.getSubterm().get(1), varMap));
		} else if (g instanceof GreaterThan) {
			GreaterThan equ = (GreaterThan)g;
			return Expression.op(Op.GT,
					convertToInt(equ.getSubterm().get(0), varMap), 
					convertToInt(equ.getSubterm().get(1), varMap));
		} else {
			getLog().warning("Unknown boolean operator encountered " + g.getClass().getName());
		}
		return Expression.constant(true);
	}

	private void grabChildVariables(EObject t, Set<VariableDecl> vars) {
		if (t == null) {
			return;
		}
		for (TreeIterator<EObject> it = t.eAllContents(); it.hasNext();) {
			EObject obj = it.next();
			if (obj instanceof fr.lip6.move.pnml.symmetricnet.terms.Variable) {
				fr.lip6.move.pnml.symmetricnet.terms.Variable var = (fr.lip6.move.pnml.symmetricnet.terms.Variable) obj;
				vars.add(var.getVariableDecl());
			}

		}
	}

	private Param createParameter(VariableDecl var) {
		Sort sort = var.getSort();
		if (sort instanceof UserSort) {
			return new Param(var.getName(), computeSortCardinality(sort));
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

	private List<Pair<Expression, Integer>> buildRefsFromArc(Term term, Sort psort, Map<VariableDecl, Param> varMap) {
		List<Pair<Expression, Integer>> toret = new ArrayList<>();

		if (term instanceof All) {
			// All all = (All) term;
			int size = computeSortCardinality(psort);
			for (int i = 0; i < size; i++) {
				toret.add(new Pair<Expression, Integer>(Expression.constant(i), 1));
			}
		} else if (term instanceof NumberOf) {
			NumberOf no = (NumberOf) term;
			int card = HLUtils.getCardinality(no);

			List<Pair<Expression, Integer>> token = buildRefsFromArc(no.getSubterm().get(1), psort, varMap);
			
			for (Pair<Expression, Integer> it : token) {
				toret.add( new Pair<>(it.getFirst(), it.getSecond()*card));
			}
		} else if (term instanceof UserOperator) {
			// Probably designating a constant of the type
			UserOperator uo = (UserOperator) term;
			int index = HLUtils.getConstantIndex(uo);

			toret.add(new Pair<>(Expression.constant(index), 1));
		} else if (term instanceof DotConstant) {
			toret.add(new Pair<>(Expression.constant(0),1));
		} else if (term instanceof fr.lip6.move.pnml.symmetricnet.terms.Variable) {
			// Probably designating a constant of the type
			fr.lip6.move.pnml.symmetricnet.terms.Variable var = (fr.lip6.move.pnml.symmetricnet.terms.Variable) term;
			Param param = varMap.get(var.getVariableDecl());
			Expression pr = Expression.paramRef(param);
			toret.add(new Pair<>(pr,1));
		} else if (term instanceof Tuple) {
			Tuple tuple = (Tuple) term;
			// hopefully, only constants in the tuple
			int tot = 1;
			Expression zero = Expression.constant(0);
			List<Expression> targets = new ArrayList<>(); 
			targets.add(zero);
			
			for (int i = tuple.getSubterm().size() -1 ; i >= 0 ; i--) {
				Term elem = tuple.getSubterm().get(i);
				Expression value =null;
				Sort elemSort = null;
				if (elem instanceof UserOperator) {
					UserOperator uo = (UserOperator) elem;
					int cst = HLUtils.getConstantIndex(uo);
					FEConstant fec = (FEConstant) uo.getDeclaration();
					elemSort = fec.getSort();

					value = Expression.constant(cst);
				} else if (elem instanceof fr.lip6.move.pnml.symmetricnet.terms.Variable) {
					// Probably designating a constant of the type
					fr.lip6.move.pnml.symmetricnet.terms.Variable var = (fr.lip6.move.pnml.symmetricnet.terms.Variable) elem;
					elemSort = var.getVariableDecl().getSort();
					Param param = varMap.get(var.getVariableDecl());
					value = Expression.paramRef(param);
				} else if (elem instanceof Predecessor) {
					Predecessor pred = (Predecessor) elem;
					// Probably designating a constant of the type
					fr.lip6.move.pnml.symmetricnet.terms.Variable var = (fr.lip6.move.pnml.symmetricnet.terms.Variable) pred.getSubterm().get(0);
					Param param = varMap.get(var.getVariableDecl());
					Expression pr = Expression.paramRef(param);

					Expression bin = Expression.op(Op.MINUS,pr, Expression.constant(1));
										
					elemSort = var.getVariableDecl().getSort();
					Expression max = Expression.constant(computeSortCardinality(elemSort));
					
					// wrap function for negative integer i : 
					// ( (i % max) + max ) % max )
					
					Expression mod = Expression.op(Op.MOD,bin , max);
					Expression sum = Expression.op(Op.ADD,mod, max);
					
					value = Expression.op(Op.MOD, sum, max);
				} else if (elem instanceof Successor) {
					Successor pred = (Successor) elem;
					// Probably designating a constant of the type
					fr.lip6.move.pnml.symmetricnet.terms.Variable var = (fr.lip6.move.pnml.symmetricnet.terms.Variable) pred.getSubterm().get(0);
					Param param = varMap.get(var.getVariableDecl());
					Expression pr = Expression.paramRef(param);

					Expression bin = Expression.op(Op.ADD, pr, Expression.constant(1));
					
					elemSort = var.getVariableDecl().getSort();
					
					Expression mod = Expression.op(Op.MOD, bin, Expression.constant(computeSortCardinality(elemSort)));
					
					value = mod;					
				} else if (elem instanceof FiniteIntRangeConstant) {
					FiniteIntRangeConstant fc = (FiniteIntRangeConstant) elem;

					if (psort instanceof UserSort) {
						UserSort usort = (UserSort) psort;
						if (usort.getDeclaration() instanceof NamedSort) {
							NamedSort nsort = (NamedSort) usort.getDeclaration();
							if (nsort.getSortdef() instanceof ProductSort) {
								ProductSort prod = (ProductSort) nsort.getSortdef();
								elemSort = prod.getElementSort().get(i);
							}
						}
					}
					if (elemSort == null) {
						throw new UnsupportedOperationException();
					}
					boolean ok = false;
					if (elemSort instanceof UserSort) {
						UserSort usort = (UserSort) elemSort;
						if (usort.getDeclaration() instanceof NamedSort) {
							NamedSort nsort = (NamedSort) usort.getDeclaration();

							if (nsort.getSortdef() instanceof FiniteIntRange) {
								FiniteIntRange fir = (FiniteIntRange) nsort.getSortdef();
								value = Expression.constant(fc.getValue() - Math.toIntExact(fir.getStart()));
								ok = true;
							}
						}
					}
					if (!ok) {
						throw new UnsupportedOperationException();
					}
					
				} else {
					getLog().warning("unrecognized term type " + elem.getClass().getName());
					value = Expression.constant(1);
					throw new UnsupportedOperationException();
				}

				Expression pres ;
				if (tot != 1) {
					pres = Expression.op(Op.MULT, value, Expression.constant(tot));
				} else {
					pres = value;
				}
				
				for (int j=0; j < targets.size() ; j++) {
					Expression target = targets.get(j);
					if (target != zero) {
						targets.set(j, Expression.op(Op.ADD, pres, target));
					} else {
						targets.set(j, pres);
					}
				}

				tot *= computeSortCardinality(elemSort);
			}
			for (Expression target : targets) {
				toret.add(new Pair<>(target,1));
			}
		} else if (term instanceof Add) {
			Add add = (Add) term;
			for (Term t : add.getSubterm()) {
				List<Pair<Expression, Integer>> toadd = buildRefsFromArc(t, psort, varMap);
				toret.addAll(toadd);
			}
		} else if (term instanceof Subtract) {
			Subtract add = (Subtract) term;
			int nbterm = 0;
			for (Term t : add.getSubterm()) {
				List<Pair<Expression, Integer>> toadd = buildRefsFromArc(t, psort, varMap);
				for (Pair<Expression, Integer> it : toadd) {
					if (nbterm == 0) {
						// the first term minus the next ones
						toret.add(it); 
					} else {
						toret.add(new Pair<>(it.getFirst(), -it.getSecond()));
					}
				}
				nbterm++;
			}
//			for (Integer val : toret.values()) {
//				if (val < 0) {
//					throw new UnsupportedOperationException("Negative term built in arc inscription.");
//				}
//			}
		} else if (term instanceof Predecessor) {
			Predecessor pred = (Predecessor) term;
			// Probably designating a constant of the type
			fr.lip6.move.pnml.symmetricnet.terms.Variable var = (fr.lip6.move.pnml.symmetricnet.terms.Variable) pred.getSubterm().get(0);
			Param param = varMap.get(var.getVariableDecl());
			Expression pr = Expression.paramRef(param);

			Expression bin = Expression.op(Op.MINUS, pr, Expression.constant(1));
								
			Expression max = Expression.constant(computeSortCardinality(var.getVariableDecl().getSort()));
			
			// wrap function for negative integer i : 
			// ( (i % max) + max ) % max )
			
			Expression mod = Expression.op(Op.MOD, bin, max); 
			
			Expression sum = Expression.op(Op.ADD, mod, max);
			
			Expression mod2 = Expression.op(Op.MOD, sum, max);
			
			toret.add(new Pair<>(mod2,1));
		} else if (term instanceof Successor) {
			Successor pred = (Successor) term;
			// Probably designating a constant of the type
			fr.lip6.move.pnml.symmetricnet.terms.Variable var = (fr.lip6.move.pnml.symmetricnet.terms.Variable) pred.getSubterm().get(0);
			Param param = varMap.get(var.getVariableDecl());
			Expression pr = Expression.paramRef(param);

			Expression bin = Expression.op(Op.ADD, pr, Expression.constant(1));

			Expression max = Expression.constant(computeSortCardinality(var.getVariableDecl().getSort()));

			Expression mod = Expression.op(Op.MOD,bin, max);
			
			toret.add(new Pair<>(mod,1));
		} else {
			getLog().warning("Encountered unknown term in arc inscription " + term.getClass().getName());
		}
		return toret;
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
			List<Integer> targets = new ArrayList<>();
			targets.add(0);
			
			for (int i = tuple.getSubterm().size() -1 ; i >= 0 ; i--) {
				Term elem = tuple.getSubterm().get(i);
				Sort elemSort = null; 					
				if (psort instanceof UserSort) {
					UserSort usort = (UserSort) psort;
					if (usort.getDeclaration() instanceof NamedSort) {
						NamedSort nsort = (NamedSort) usort.getDeclaration();
						if (nsort.getSortdef() instanceof ProductSort) {
							ProductSort prod = (ProductSort) nsort.getSortdef();
							elemSort = prod.getElementSort().get(i);
						} else {
							elemSort = nsort.getSortdef();
						}
					}
				}
				if (elemSort == null) {
					throw new UnsupportedOperationException();
				}
				
				if (elem instanceof UserOperator) {
					targets = interpretSubTerm(tot, targets, elem);
				} else if (elem instanceof Add) {
					Add add = (Add) elem;
					List<Integer> newtargets = new ArrayList<>();
					addTargets(add, targets, tot, newtargets);
					targets = newtargets;
				} else if (elem instanceof All) {
					List<Integer> newtargets = new ArrayList<>();
					for (int target : targets) {
						for (int ii = 0, iie = computeSortCardinality(elemSort) ; ii < iie ; ii++) {
							newtargets.add(target + tot * ii);
						}
					}
					targets = newtargets;
				} else if (elem instanceof FiniteIntRangeConstant) {
					FiniteIntRangeConstant firc = (FiniteIntRangeConstant) elem;
					int pos = firc.getValue() - Math.toIntExact(firc.getRange().getStart());
					for (int ii=0; ii < targets.size() ; ii++) {
						targets.set(ii, targets.get(ii) + tot * pos);
					}					
				} else {
					throw new UnsupportedOperationException();
				}
				tot *= computeSortCardinality(elemSort);
			}
			for (Integer target : targets) {
				toret[target] = 1;
			}
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

	public void addTargets(Add add, List<Integer> targets, int tot, List<Integer> newtargets) {
		for (Term e : add.getSubterm()) {
			if (e instanceof Add) {
				Add added = (Add) e;
				for (Term expr : added.getSubterm()) {
					if (expr instanceof Add) {
						addTargets((Add) expr, targets, tot, newtargets);
					} else {
						List<Integer> subtargets = interpretSubTerm(tot, targets, expr);
						newtargets.addAll(subtargets);
					}
				}
			} else {
				List<Integer> subtargets = interpretSubTerm(tot, targets, e);
				newtargets.addAll(subtargets);
			}
		}
	}

	public List<Integer> interpretSubTerm(int tot, List<Integer> targets, Term elem) {
		UserOperator uo = (UserOperator) elem;
		if (uo.getDeclaration() instanceof FEConstant) {
			int cte = HLUtils.getConstantIndex(uo);
			for (int ii=0; ii < targets.size() ; ii++) {
				targets.set(ii, targets.get(ii) + tot * cte);
			}
		//	tot *= computeSortCardinality( ((FEConstant)uo.getDeclaration()).getSort());
		} else if (uo.getDeclaration() instanceof PartitionElement) {
			PartitionElement pe = (PartitionElement) uo.getDeclaration();
			List<Integer> ctes = HLUtils.getConstantIndexes(pe);
			List<Integer> newtargets = new ArrayList<>();
			for (Integer cte : ctes) {
				for (int ii=0; ii < targets.size() ; ii++) {
					newtargets.add(targets.get(ii) + tot * cte);
				}
			}
			targets = newtargets;
		//	tot *= computeSortCardinality(  ((FEConstant)((UserOperator) ((PartitionElement)uo.getDeclaration()).getPartitionelementconstants().get(0)).getDeclaration()).getSort());
		} else {
			throw new UnsupportedOperationException();
		}
		return targets;
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
			// ranges are inclusive : [1,2] -> values 1 and 2 legal.
			return Math.toIntExact(fir.getEnd()) - Math.toIntExact(fir.getStart()) + 1;
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
}
