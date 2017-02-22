package fr.lip6.move.gal.gal2smt.bmc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.ISort.IApplication;
import org.smtlib.IResponse;
import org.smtlib.IExpr.ISymbol;
import org.smtlib.SMT.Configuration;
import org.smtlib.command.C_assert;
import org.smtlib.command.C_define_fun;
import org.smtlib.impl.Script;
import org.smtlib.impl.Sort;

import fr.lip6.move.gal.AssignType;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.SafetyProp;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.gal2smt.Result;
import fr.lip6.move.gal.gal2smt.Solver;
import fr.lip6.move.gal.instantiate.Instantiator;
import fr.lip6.move.gal.semantics.Assign;
import fr.lip6.move.gal.semantics.INext;
import fr.lip6.move.gal.semantics.LeafNextVisitor;
import fr.lip6.move.gal.semantics.Predicate;

public class KInductionSolver extends NextBMCSolver {

	private static final String TRANS = "PARIKH";

	public KInductionSolver(Configuration smtConfig, Solver engine, boolean withAllDiff) {
		super(smtConfig, engine, withAllDiff);
		//setShowSatState(true);
	}
	
	protected boolean isPresburger = true;
	// To represent the flow matrix, if we can build it. We use a sparse representation.
	// Map variable index -> Transition index -> update to variable (a relative integer)
	private Map<Integer, Map<Integer,Integer>> flow = new TreeMap<>();
	protected int nbTransition=0;

	@Override
	public void init(Specification spec) {
		super.init(spec);
		
		Set<Integer> positive = new HashSet<>();
		int vindex =0;
		List<Integer> init = nb.getInitial();
		for (int val : init) {
			if (val >= 0) {
				solver.push(1);
				// assert relation from s[0] -> s[1]
				new C_assert(efactory.fcn(efactory.symbol(NEXT),efactory.numeral(0))).execute(solver);
				// assert x >= 0 at step 0
				new C_assert(efactory.fcn(efactory.symbol(">="), 
						efactory.fcn(efactory.symbol("select"),
								// state at step 0
								accessStateAt(0), 
								// at correct var index 
								efactory.numeral(vindex)),
						// greater than 0
						efactory.numeral(0))).execute(solver);
				// assert x < 0 at step 1
				new C_assert(efactory.fcn(efactory.symbol("<"), 
						efactory.fcn(efactory.symbol("select"),
								// state at step 1
								accessStateAt(1), 
								// at correct var index 
								efactory.numeral(vindex)),
						// strictly less 0 = negative
						efactory.numeral(0))).execute(solver);
							
				Result res = checkSat();
				solver.pop(1);
				if (res == Result.UNSAT) {
					//System.out.println("positive var detected");
					positive.add(vindex);
					// assert x >= 0 at step 0
					C_assert xpos = new C_assert(efactory.fcn(efactory.symbol(">="), 
							efactory.fcn(efactory.symbol("select"),
									// state at step 0
									accessStateAt(0), 
									// at correct var index 
									efactory.numeral(vindex)),
							// greater than 0
							efactory.numeral(0)));
					System.out.println(xpos);
					IResponse err = xpos.execute(solver);
					if (err.isError()) {
						System.err.println("Error adding positive variable constraint "+ err);
					}
				} else {
					System.out.println("could not prove variable is positive");
				}
			}
			vindex++;
		}
		
		if (isPresburger) {
			System.out.println("Presburger conditions satisfied. Using coverability to approximate state space in K-Induction.");
		} else {
			flow = null;
			System.out.println("Presburger conditions not satisfied.");
		}
		
//		TypeDeclaration td = spec.getMain();
//		if (td instanceof GALTypeDeclaration) {
//			GALTypeDeclaration gal = (GALTypeDeclaration) td;
//			FlowMatrix fm = new FlowMatrix(conf,vh);			
//			boolean isInit = fm.init(gal);
//			
//			if (isInit) {
//				int step = 0;
//				fm.addFlowConstraintsAtStep(step,script,gal);
//
//				// check sat
//				IResponse res = script.execute(solver);
//				if (res.isError()) {
//					throw new RuntimeException("Could not initialize marking equation.");
//				}
//			} else {				
//				// add non negative constraint
//				for (IExpr access : vh.getAllAccess()) {
//					solver.assertExpr(efactory.fcn(efactory.symbol(">="), access, efactory.numeral(0)));
//				}
//			}
//		}
//		// add constraint from S[0] to S[1]

		incrementDepth();
		// NB: hence depth is 1 for 0-inductive problem
		
	}
	
	@Override
	public void incrementDepth() {
		if (isPresburger) {
			addFlowConstraints(getDepth());
		}
		super.incrementDepth();
	}
	
	protected void addFlowConstraints(int step) {
		Script script = new Script();
		// declare the transition parikh vector
		// integer sort
		IApplication ints = sortfactory.createSortExpression(efactory.symbol("Int"));
		// an array, indexed by integers, containing integers : (Array Int Int) 
		IApplication arraySort = sortfactory.createSortExpression(efactory.symbol("Array"), ints, ints);
				
		// declare transition variable : a big array of integer
		script.add(
				new org.smtlib.command.C_declare_fun(
						efactory.symbol(TRANS+step),
						Collections.emptyList(),
						arraySort								
						)
				);
		// assert positive on these variables
		for (int t = 0 ; t < nbTransition ; t++) {
			script.add(new C_assert(
					efactory.fcn(efactory.symbol(">="), 
							efactory.fcn(efactory.symbol("select"),
									// state at step 0
									efactory.symbol(TRANS+step), 
									// at correct var index 
									efactory.numeral(t)),
							// greater than 0
							efactory.numeral(0))));
		}			
		
		for (Entry<Integer, Map<Integer, Integer>> ent : flow.entrySet()) {
			int vi = ent.getKey();
			Map<Integer, Integer> line = ent.getValue();
			// assert : x = m0.x + X0*C(t0,x) + ...+ XN*C(Tn,x)
			List<IExpr> exprs = new ArrayList<IExpr>();
			
			// m0.x
			exprs.add(efactory.numeral(nb.getInitial().get(vi)));
			
			//  Xi*C(ti,x)
			for (Entry<Integer, Integer> teffect : line.entrySet()) {
				
				IExpr nbtok ;
				if (teffect.getValue() > 0) 
					nbtok = efactory.numeral(teffect.getValue());
				else if (teffect.getValue() < 0)
					nbtok = efactory.fcn(efactory.symbol("-"), efactory.numeral(-teffect.getValue()));
				else 
					continue;
				exprs.add(efactory.fcn(efactory.symbol("*"), 
						efactory.fcn(efactory.symbol("select"), efactory.symbol(TRANS+step), efactory.numeral(ent.getKey())),
						nbtok));
			}
			
			script.add(new C_assert(efactory.fcn(efactory.symbol("="), 
					efactory.fcn(efactory.symbol("select"),
							// state at step 0
							accessStateAt(step), 
							// at correct var index 
							efactory.numeral(vi)),
					// = m0.x + X0*C(t0,x) + ...+ XN*C(Tn,x)
					efactory.fcn(efactory.symbol("+"), exprs))));
		}
		IResponse err = script.execute(solver);
		if (err.isError()) {
			throw new RuntimeException("Error when declaring Parikh based flow equations."+conf.defaultPrinter.toString(err));
		}
		
	}

	class PresburgerChecker implements LeafNextVisitor<Boolean> {
		private final int tindex;
		
		public PresburgerChecker(int tindex) {
			this.tindex = tindex;
		}

		@Override
		public Boolean visit(Assign assn) {
			Assignment ass = assn.getAssignment();

			// index of the target variable
			int vindex = assn.getIndexer().getIndex(ass.getLeft().getRef().getName());
			// make sure lhs is a constant index access
			if ( ass.getLeft().getIndex() != null) {
				try {
					int ind = Instantiator.evalConst(ass.getLeft().getIndex());
					vindex += ind;
				} catch (ArrayIndexOutOfBoundsException e) {
					return false;
				}
			}
			
			// value added to target variable
			int val =0;			
			if (ass.getType() == AssignType.INCR) {
				try {
					int v = Instantiator.evalConst(ass.getRight());
					val = v;
				} catch (ArrayIndexOutOfBoundsException e) {
					return false;
				}				
			} else if (ass.getType() == AssignType.DECR) {
				try {
					int v = Instantiator.evalConst(ass.getRight());
					val = -v;
				} catch (ArrayIndexOutOfBoundsException e) {
					return false;
				}				
			} else if (ass.getType() == AssignType.ASSIGN) {
				if (ass.getRight() instanceof BinaryIntExpression) {
					BinaryIntExpression bin = (BinaryIntExpression) ass.getRight();
					if (EcoreUtil.equals(ass.getLeft(), bin.getLeft())) {
						// x = x + k ?
						try {
							int  k = Instantiator.evalConst(bin.getRight());
							if (bin.getOp().equals("+")) {
								val = k;
							} else if (bin.getOp().equals("-")) {
								val = -k;
							}
						} catch (ArrayIndexOutOfBoundsException e) {
							return false;
						}
					} else if (EcoreUtil.equals(ass.getLeft(), bin.getRight())) {
						// x = k + x ?
						try {
							int  k = Instantiator.evalConst(bin.getLeft());
							if (bin.getOp().equals("+")) {
								val = k;
							} else if (bin.getOp().equals("-")) {
								return false;
							}
						} catch (ArrayIndexOutOfBoundsException e) {
							return false;
						}
					} else {
						// rhs is not k+x or x+k
						return false;
					}
				} else {
					return false;
				}
			}

			addEffect(tindex, vindex, val);
			return true;
		}

		@Override
		public Boolean visit(Predicate pred) {
			return true;
		}
	}
	
	void addEffect(int tindex, int vindex, int val) {
		Map<Integer, Integer> line = flow.get(vindex);
		if (line == null) {
			line  = new TreeMap<>();
			flow.put(vindex, line);
		}
		Integer cur = line.get(tindex);		
		if (cur==null) {
			cur=0;
		}
		cur+=val;
		line.put(tindex, cur);
	}
	
	@Override
	protected void visitTransition(List<INext> seq, int tindex) {
		if (! isPresburger) {
			return;
		}
		// Use this opportunity to compute flow matrix
		PresburgerChecker pc = new PresburgerChecker(tindex);
		for (INext n : seq) {
			Boolean b = n.accept(pc);
			if (! b) {
				isPresburger = false;
				return ;
			}
		}
		nbTransition = Math.max(nbTransition, tindex+1);
	}
	

	@Override
	public Result verify(Property prop) {
		
		if (prop.getBody() instanceof SafetyProp) {
			SafetyProp sbody = (SafetyProp) prop.getBody();

			// we want unsat iff. there is no trace leading to a desirable state
			// desirable as in : can be exhibited		
			boolean isNeg = false;
			if (sbody instanceof ReachableProp) {
				isNeg = true;
			} else if (sbody instanceof NeverProp) {
				isNeg = true;
				// NOT : assert ! bprop up to k, and bprop in k+1
			} else {
				// NOP assert bprop up to k, and not bprop in k+1
			}

			QualifiedExpressionTranslator qet = new QualifiedExpressionTranslator(conf);
			qet.setNb(nb);
			
			ISymbol fname = efactory.symbol("pred");
			ISymbol sstep = efactory.symbol("step");			
			IApplication ints = sortfactory.createSortExpression(efactory.symbol("Int"));
			
			// state at step "step"
			IExpr state = accessStateAt(sstep);
			// build property at state
			IExpr eprop = qet.translateBool(sbody.getPredicate(), state);

			
			Script script = new Script();
			C_define_fun deftr = new org.smtlib.command.C_define_fun(
					fname,    // name
					Collections.singletonList(efactory.declaration(sstep, ints)), // param (int step) 
					Sort.Bool(), // return type
					eprop); // actions : assertions over S[step] 
			script.add(deftr);
			
			// and property up to depth (exclusive)
			for (int i=0 ; i <= getDepth(); i++) {
				// build prop at depth
				IExpr pred = efactory.fcn(fname, efactory.numeral(i));
				// assert negation of prop at depth
				if (i==getDepth()) {
					isNeg = ! isNeg;
				}
				// handle negation as necessary
				if (isNeg) {
					script.add(new C_assert( efactory.fcn(efactory.symbol("not"), pred)));
				} else {
					script.add(new C_assert(pred));					
				}
			}

			for (ICommand c : script.commands()) {
				System.out.println(c);
			}
			
			// the actual induction problem
			solver.push(1);
			script.execute(solver);
			Result res = checkSat();
			if (res == Result.SAT) {
				onSat(solver);
			}
			solver.pop(1);
			
			return res;
		} else {
			Logger.getLogger("fr.lip6.move.gal").warning("Only safety properties are handled in SMT solution currently. Cannot handle " + prop.getName());
			return Result.UNKNOWN;
		}
	}
	
}
