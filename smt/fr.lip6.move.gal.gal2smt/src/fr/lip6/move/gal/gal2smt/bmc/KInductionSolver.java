package fr.lip6.move.gal.gal2smt.bmc;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.smtlib.IExpr;
import org.smtlib.ISort.IApplication;
import org.smtlib.IResponse;
import org.smtlib.IExpr.IDeclaration;
import org.smtlib.IExpr.ISymbol;
import org.smtlib.SMT.Configuration;
import org.smtlib.command.C_assert;
import org.smtlib.command.C_declare_fun;
import org.smtlib.command.C_define_fun;
import org.smtlib.impl.Script;
import org.smtlib.impl.Sort;

import fr.lip6.gal.gal2smt.tosmt.QualifiedExpressionTranslator;
import fr.lip6.move.gal.AssignType;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BinaryIntExpression;
import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.SafetyProp;
import fr.lip6.move.gal.gal2smt.Result;
import fr.lip6.move.gal.gal2smt.Solver;
import fr.lip6.move.gal.instantiate.Instantiator;
import fr.lip6.move.gal.semantics.Assign;
import fr.lip6.move.gal.semantics.IDeterministicNextBuilder;
import fr.lip6.move.gal.semantics.INext;
import fr.lip6.move.gal.semantics.LeafNextVisitor;
import fr.lip6.move.gal.semantics.Predicate;
import uniol.apt.analysis.invariants.InvariantCalculator;
import uniol.apt.analysis.invariants.InvariantCalculator.InvariantAlgorithm;

public class KInductionSolver extends NextBMCSolver {

	private static final String TRANS = "PARIKH";
	private static final String INVAR = "invariants";
	private static final String FLOW = "flow";
	private static final String PINVAR = "Pinvariants";
	private int invariantOccurrence = 0;

	public KInductionSolver(Configuration smtConfig, Solver engine, boolean withAllDiff) {
		super(smtConfig, engine, withAllDiff);
		//setShowSatState(true);
	}

	private boolean isPresburger = true;
	// To represent the flow matrix, if we can build it. We use a sparse representation.
	// Map variable index -> Transition index -> update to variable (a relative integer)
	private FlowMatrix flow = new FlowMatrix();
	protected int nbTransition=0;
	private BitSet positiveVars;
	private List<List<Integer>> invariants;
	private List<BitSet> invSupports;

	@Override
	public void init(IDeterministicNextBuilder nextb) {
		super.init(nextb);

		
		ISymbol state = efactory.symbol("state");
		List<IExpr> inv = new ArrayList<>();

		long timestamp = System.currentTimeMillis();

		
		if (isPresburger) {
			//declareFlowProperties();
			System.out.println("Presburger conditions satisfied. Using coverability to approximate state space in K-Induction.");
			computeAndDeclareInvariants();
			
		} else {
			flow = null;
			System.out.println("Presburger conditions not satisfied.");
		}
		// assert relation from s[0] -> s[1]
		// new C_assert(efactory.fcn(efactory.symbol(NEXT),efactory.numeral(0))).execute(solver);
		
		
		positiveVars = new BitSet();
		int vindex =0;
		List<Integer> init = nb.getInitial();
		for (int val : init) {
			if (val >= 0) {
				solver.push(1);
				
				
				assertCouldModifyNext(vindex, 0);
					
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
					positiveVars.set(vindex);
					// assert x >= 0 at step 0
					IExpr isPositive = efactory.fcn(efactory.symbol(">="), 
							efactory.fcn(efactory.symbol("select"),
									// state
									state, 
									// at correct var index 
									efactory.numeral(vindex)),
							// greater than 0
							efactory.numeral(0));
					// add it to detected invariants
					inv.add(isPositive);					
					IExpr expr = efactory.fcn(efactory.symbol(">="), 
							efactory.fcn(efactory.symbol("select"),
									// state
									accessStateAt(0), 
									// at correct var index 
									efactory.numeral(vindex)),
							// greater than 0
							efactory.numeral(0));
					// System.out.println(expr);
					// execute it, so that next variable invariant benefits from it
					IResponse err = solver.assertExpr(expr);
					if (err.isError()) {
						System.err.println("Error adding positive variable constraint "+ err);
					}
				} else {
					//System.out.println("could not prove variable is positive");
				}
			}
			vindex++;
		}

		// remove next state constraint
		//solver.pop(1);

		Logger.getLogger("fr.lip6.move.gal").info("Proved  "+ positiveVars.size() + " variables to be positive in " + (System.currentTimeMillis()-timestamp)+ " ms");

		

		// Enforce the positive invariants detected.		
		// build up the full boolean function for the invariants
		IExpr bodyExpr = efactory.fcn(efactory.symbol("and"), inv);
		if (inv.size() == 1) {
			bodyExpr = inv.get(0);
		} else if (inv.isEmpty()) {
			bodyExpr = efactory.symbol("true");
		}
		// integer sort
		IApplication ints = sortfactory.createSortExpression(efactory.symbol("Int"));
		// an array, indexed by integers, containing integers : (Array Int Int) 
		IApplication arraySort = sortfactory.createSortExpression(efactory.symbol("Array"), ints, ints);

		// enabling in a given state: enabledsrcXX ( int [] state ) : bool
		ISymbol invname = efactory.symbol(INVAR);
		C_define_fun invariantDecl = new org.smtlib.command.C_define_fun(
				invname,    // name
				Collections.singletonList(efactory.declaration(state, arraySort)), // param (int [] state) 
				Sort.Bool(), // return type
				bodyExpr); // actions : assertions over S[step] and S[step+1]
		
		solver.define_fun(invariantDecl);
		// NB: hence depth is 1 for 0-inductive problem
		//incrementDepth();		
				
		//addKnownInvariants(1);

	}
	
	protected BitSet getPositive() {
		return positiveVars;
	}

	private void assertCouldModifyNext(int vindex, int step) {
		List<IExpr> relevant = new ArrayList<IExpr>();
		for (int i = 0 ; i < nb.getDeterministicNext().size() ; i++) {
			if (nb.getDeterministicDependencyMatrix().getWrite(i).get(vindex)) {
				relevant.add(efactory.fcn(efactory.symbol(TRANSNAME+ i), efactory.numeral(step)));
			}
		}
		IExpr toass = efactory.fcn(efactory.symbol("or"), relevant);
		if (relevant.size() == 0) {
			toass = efactory.symbol("false");
		} else if (relevant.size() == 1) {
			toass = relevant.get(0);
		}
		IResponse resp = solver.assertExpr(toass);
		if (resp.isError()) {
			throw new RuntimeException("SMT solver raised an exception :"+ resp);
		}
	}

	protected List<List<Integer>> getInvariants() {
		return invariants;
	}
	
	protected List<BitSet> getInvariantSupport() {
		return invSupports;
	}
	
	private void computeAndDeclareInvariants() {
		long timestamp2 = System.currentTimeMillis();
		invariants = new ArrayList<>(InvariantCalculator.calcSInvariants(flow, InvariantAlgorithm.PIPE,false));
		
		invSupports = new ArrayList<>();
		for (List<Integer> rv : invariants) {
			StringBuilder sb = new StringBuilder();
			boolean first = true;
			int sum =0;
			BitSet b = new BitSet();
			for (int i =0; i < rv.size(); i++) {
				if (rv.get(i) != 0) {
					b.set(i);
					if (first) {
						first  = false;
					} else {
						sb.append(" + ");						
					}
					if (rv.get(i) != 1) {
						sb.append(rv.get(i)+ "'"+ nb.getVariableNames().get(i));
					} else {
						sb.append(nb.getVariableNames().get(i));
					}
					sum += nb.getInitial().get(i);
				}
			}
			System.out.println("invariant :" + sb.toString() +" = " + sum);
			invSupports.add(b);
		}
		
		Logger.getLogger("fr.lip6.move.gal").info("Computed "+invariants.size()+" place invariants in "+ (System.currentTimeMillis()-timestamp2) +" ms");
		
		
		// declare the transition : Pinvar (int [] src) : bool
		ISymbol fsrcname = efactory.symbol(PINVAR);
		ISymbol state = efactory.symbol("src");		
				
		// to hold the body (conjuncts) of the invariants
		List<IExpr> conds = new ArrayList<>();

		for (List<Integer> rv : invariants) {
			IExpr invar = convertInvariantToSMT(rv, state);			
			conds.add(invar);
		}

		// integer sort
		IApplication ints = sortfactory.createSortExpression(efactory.symbol("Int"));
		// an array, indexed by integers, containing integers : (Array Int Int) 
		IApplication arraySort = sortfactory.createSortExpression(efactory.symbol("Array"), ints, ints);

		List<IDeclaration> args = new ArrayList<>();
		args.add(efactory.declaration(state, arraySort));
		
		// build up the full boolean function for the flow equation
		IExpr bodyExpr = efactory.fcn(efactory.symbol("and"), conds);
		if (conds.size() == 1) {
			bodyExpr = conds.get(0);
		} else if (conds.isEmpty()) {
			bodyExpr = efactory.symbol("true");
		}
		
		C_define_fun flowfcn = new org.smtlib.command.C_define_fun(
				fsrcname,    // name
				args, // param (int [] src, int [] dst) 
				Sort.Bool(), // return type
				bodyExpr); // actions : assertions over S[step] and S[step+1]
		IResponse res = solver.define_fun(flowfcn);
		// System.out.println("Invariant function :\n" + flowfcn);
		if (res.isError()) {
			throw new RuntimeException("SMT solver raised an error :" + res.toString());
		}
	}

	protected IExpr convertInvariantToSMT(List<Integer> invariant, ISymbol state) {
		int sum = 0;
		// assert : cte = m0 * x0 + ... + m_n*x_n
		// build sum up
		List<IExpr> toadd = new ArrayList<>();
		List<IExpr> torem = new ArrayList<>();
		for (int v = 0 ; v < invariant.size() ; v++) {
			if (invariant.get(v) != 0) {				
				IExpr ss = efactory.fcn(efactory.symbol("select"),
						// state at step 0
						state, 
						// at correct var index 
						efactory.numeral(v));
				// yices does not deal well with multiplication, despite it being constants
				if (engine == Solver.YICES2) {
					for (int i=0; i < Math.abs(invariant.get(v)) ; i++) {
						if (invariant.get(v) > 0) 
							toadd.add(ss);
						else
							torem.add(ss);
					}
				} else {
					if (invariant.get(v) != 1) {
						ss = efactory.fcn(efactory.symbol("*"), efactory.numeral( Math.abs(invariant.get(v))), ss );
					}
					if (invariant.get(v) > 0) 
						toadd.add(ss);
					else
						torem.add(ss);
				}
				sum += nb.getInitial().get(v) * invariant.get(v);
			}
		}
		IExpr sumE ;
		if (toadd.isEmpty()) {
			sumE = efactory.numeral(0);
		} else if (toadd.size() == 1) {
			sumE = toadd.get(0);
		} else {
			sumE = efactory.fcn(efactory.symbol("+"), toadd);
		}
		
		IExpr sumR  = efactory.numeral(sum);
		if (! torem.isEmpty()) {
			torem.add(sumR);
			sumR = efactory.fcn(efactory.symbol("+"), torem);
		}
		IExpr invar = efactory.fcn(efactory.symbol("="), sumR, sumE);			
		
		return invar;
	}

	/** Declares a flow based state equation for variables that are in Presburger condition (only incremented or decremented by a constant).
	 * flow(int  [] state, int [] tr) : bool
	 * The state is a target state, the int [] tr is an otherwise unconstrained array of integers.
	 * Typically, simply declare an array T before asserting this function.
	 */
	private void declareFlowProperties() {
		if (! isPresburger)
			return;

		// declare the transition : flow (int [] src, int [] tr) : bool
		ISymbol fsrcname = efactory.symbol(FLOW);
		ISymbol src = efactory.symbol("src");
		ISymbol tr = efactory.symbol("tr");
				
		// declare the transition parikh vector
		// integer sort
		IApplication ints = sortfactory.createSortExpression(efactory.symbol("Int"));
		// an array, indexed by integers, containing integers : (Array Int Int) 
		IApplication arraySort = sortfactory.createSortExpression(efactory.symbol("Array"), ints, ints);

		// to hold the body (conjuncts) of the flow function
		List<IExpr> conds = new ArrayList<>();
		
		// assert positive on the transition occurrence variables
		// for all tindex : tr[tindex] >= 0
		for (int t = 0 ; t < nbTransition ; t++) {
			conds.add(efactory.fcn(efactory.symbol(">="), 
							efactory.fcn(efactory.symbol("select"),
									// transition
									tr, 
									// at correct tindex 
									efactory.numeral(t)),
							// greater than 0
							efactory.numeral(0)));
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
						efactory.fcn(efactory.symbol("select"), tr, efactory.numeral(teffect.getKey())),
						nbtok));
			}

			conds.add(efactory.fcn(efactory.symbol("="), 
					efactory.fcn(efactory.symbol("select"),
							// state at step 0
							src, 
							// at correct var index 
							efactory.numeral(vi)),
					// = m0.x + X0*C(t0,x) + ...+ XN*C(Tn,x)
					efactory.fcn(efactory.symbol("+"), exprs)));
		}

		List<IDeclaration> args = new ArrayList<>();
		args.add(efactory.declaration(src, arraySort));
		args.add(efactory.declaration(tr, arraySort));
		
		// build up the full boolean function for the flow equation
		IExpr bodyExpr = efactory.fcn(efactory.symbol("and"), conds);
		if (conds.size() == 1) {
			bodyExpr = conds.get(0);
		} else if (conds.isEmpty()) {
			bodyExpr = efactory.symbol("true");
		}
		
		C_define_fun flowfcn = new org.smtlib.command.C_define_fun(
				fsrcname,    // name
				args, // param (int [] src, int [] dst) 
				Sort.Bool(), // return type
				bodyExpr); // actions : assertions over S[step] and S[step+1]
		IResponse res = solver.define_fun(flowfcn);
		if (res.isError()) {
			throw new RuntimeException("SMT solver raised an error :" + res.toString());
		}
		
	}

	@Override
	public void incrementDepth() {
		addKnownInvariants(getDepth());
		super.incrementDepth();
	}

	protected void addKnownInvariants(IExpr state) {
		IResponse res;
		// first assert the invariant
		// System.out.println("Asserting invariants at "+state);
		res = solver.assertExpr(efactory.fcn(efactory.symbol(INVAR), state));
		if (res.isError()) {
			throw new RuntimeException("SMT solver raised an error on invariants :" + res.toString());
		}
		
		if (isPresburger) {
			//System.out.println("Adding invariants at "+state);
			
			res = solver.assertExpr(efactory.fcn(efactory.symbol(PINVAR), state));
			if (res.isError()) {
				throw new RuntimeException("SMT solver raised an error on P invariants :" + res.toString());
			}
			
			// callFlowConstraintOnStep(state);
		}
		
	}

	private void callFlowConstraintOnStep(IExpr state) {
		IResponse res;
		// create an array to hold the transition occurrences
		// integer sort
		IApplication ints = sortfactory.createSortExpression(efactory.symbol("Int"));
		// an array, indexed by integers, containing integers : (Array Int Int) 
		IApplication arraySort = sortfactory.createSortExpression(efactory.symbol("Array"), ints, ints);
		
		// build a new variable each time
		ISymbol trocc = efactory.symbol(TRANS + invariantOccurrence++);
		res = solver.declare_fun(new C_declare_fun( trocc, Collections.emptyList(), arraySort));
		if (res.isError()) {
			throw new RuntimeException("SMT solver raised an error on flow relation invariants (transition vector declaration) :" + res.toString());
		}
		
		// assert flow(state,trocc)
		res = solver.assertExpr(efactory.fcn(efactory.symbol(FLOW), state, trocc));
		if (res.isError()) {
			throw new RuntimeException("SMT solver raised an error (linear constraint declaration) :" + res.toString());
		}
	}
		
	protected void addKnownInvariants(int step) {
		addKnownInvariants(accessStateAt(step));

//		IResponse err = script.execute(solver);
//		if (err.isError()) {
//			throw new RuntimeException("Error when declaring Parikh based flow equations."+conf.defaultPrinter.toString(err));
//		}
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
		flow.addEffect(tindex,vindex,val);
	}

	@Override
	protected void visitTransition(List<INext> seq, int tindex) {
		nbTransition = Math.max(nbTransition, tindex+1);
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
	}


	private int nbcall = 0;
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

			ISymbol fname = efactory.symbol("pred"+(nbcall++));
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

//			for (ICommand c : script.commands()) {
//				System.out.println(c);
//			}

			// the actual induction problem
			solver.push(1);
			IResponse resScript = script.execute(solver);
			if (resScript.isError()) {
				throw new RuntimeException("Error when declaring property to solver "+ resScript);
			}
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
