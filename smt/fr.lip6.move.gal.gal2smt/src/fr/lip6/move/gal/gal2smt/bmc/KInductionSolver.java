package fr.lip6.move.gal.gal2smt.bmc;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

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

import android.util.SparseIntArray;
import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.SafetyProp;
import fr.lip6.move.gal.gal2smt.Result;
import fr.lip6.move.gal.gal2smt.Solver;
import fr.lip6.move.gal.gal2smt.tosmt.QualifiedExpressionTranslator;
import fr.lip6.move.gal.semantics.IDeterministicNextBuilder;
import fr.lip6.move.gal.structural.InvariantCalculator;
import fr.lip6.move.gal.structural.MatrixBuilder;
import fr.lip6.move.gal.util.MatrixCol;

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

	// To represent the flow matrix, if we can build it. We use a sparse representation.
	// Map variable index -> Transition index -> update to variable (a relative integer)
	private MatrixBuilder flow;
	protected int nbTransition=0;
	private BitSet positiveVars;
	private List<List<Integer>> invariants;
	private List<BitSet> invSupports;

	@Override
	public void init(IDeterministicNextBuilder nextb) {
		super.init(nextb);

		nbTransition = nextb.getDeterministicNext().size();
		ISymbol state = efactory.symbol("state");
		List<IExpr> inv = new ArrayList<>();

		long timestamp = System.currentTimeMillis();
		flow = new MatrixBuilder(nextb);
		
		if (flow.isPresburger()) {
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
				IResponse res = solver.push(1);
				if (res.isError()) {
					throw new RuntimeException("SMT push produced unexpected response "+res);
				}
				
				assertCouldModifyNext(vindex, 0);
					
				// assert x >= 0 at step 0
				res = new C_assert(efactory.fcn(efactory.symbol(">="), 
						efactory.fcn(efactory.symbol("select"),
								// state at step 0
								accessStateAt(0), 
								// at correct var index 
								efactory.numeral(vindex)),
						// greater than 0
						efactory.numeral(0))).execute(solver);
				if (res.isError()) {
					throw new RuntimeException("SMT assertion produced unexpected response "+res);
				}
				// assert x < 0 at step 1
				res = new C_assert(efactory.fcn(efactory.symbol("<"), 
						efactory.fcn(efactory.symbol("select"),
								// state at step 1
								accessStateAt(1), 
								// at correct var index 
								efactory.numeral(vindex)),
						// strictly less 0 = negative
						efactory.numeral(0))).execute(solver);
				if (res.isError()) {
					throw new RuntimeException("SMT assertion produced unexpected response "+res);
				}
				Result result = checkSat();
				solver.pop(1);
				if (result == Result.UNSAT) {
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
						throw new RuntimeException("SMT assertion produced unexpected response "+res);						
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
		
		IResponse res = solver.define_fun(invariantDecl);
		if (res.isError()) {
			throw new RuntimeException("SMT function declaration produced unexpected response "+res);
		}
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
		invariants = new ArrayList<>(InvariantCalculator.computePInvariants(flow.getMatrix()));
		
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
		if (! flow.isPresburger())
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
		MatrixCol mat = flow.getMatrix().getSparseIncidenceMatrix().transpose();
		for (int varindex = 0 ; varindex < mat.getColumnCount() ; varindex++) {
			
			SparseIntArray line = mat.getColumn(varindex);
			// assert : x = m0.x + X0*C(t0,x) + ...+ XN*C(Tn,x)
			List<IExpr> exprs = new ArrayList<IExpr>();

			// m0.x
			exprs.add(efactory.numeral(nb.getInitial().get(varindex)));

			//  Xi*C(ti,x)
			for (int i = 0 ; i < line.size() ; i++) {
				int val = line.valueAt(i);
				int trindex = line.keyAt(i);
				IExpr nbtok ;
				if (val > 0) 
					nbtok = efactory.numeral(val);
				else if (val < 0)
					nbtok = efactory.fcn(efactory.symbol("-"), efactory.numeral(-val));
				else 
					continue;
				exprs.add(efactory.fcn(efactory.symbol("*"), 
						efactory.fcn(efactory.symbol("select"), tr, efactory.numeral(trindex)),
						nbtok));
			}

			conds.add(efactory.fcn(efactory.symbol("="), 
					efactory.fcn(efactory.symbol("select"),
							// state at step 0
							src, 
							// at correct var index 
							efactory.numeral(varindex)),
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
		
		if (flow.isPresburger()) {
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
			IResponse res = solver.push(1);			
			if (res.isError()) {
				throw new RuntimeException("SMT push produced unexpected response "+res);
			}
			res = script.execute(solver);
			if (res.isError()) {
				throw new RuntimeException("Error when declaring property to solver "+ res);
			}
			Result result = checkSat();
			if (result == Result.SAT) {
				onSat(solver);
			}
			res = solver.pop(1);
			if (res.isError()) {
				throw new RuntimeException("Error : SMT pop returned an unexpected result "+ res);
			}
			
			return result;
		} else {
			Logger.getLogger("fr.lip6.move.gal").warning("Only safety properties are handled in SMT solution currently. Cannot handle " + prop.getName());
			return Result.UNKNOWN;
		}
	}

}
