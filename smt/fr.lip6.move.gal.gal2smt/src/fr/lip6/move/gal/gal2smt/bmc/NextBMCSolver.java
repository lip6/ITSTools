package fr.lip6.move.gal.gal2smt.bmc;

import org.smtlib.IExpr;
import org.smtlib.IExpr.IFactory;
import org.smtlib.IExpr.ISymbol;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.smtlib.IPrinter;
import org.smtlib.IResponse;
import org.smtlib.ISolver;
import org.smtlib.ISort;
import org.smtlib.ISort.IApplication;
import org.smtlib.Utils;
import org.smtlib.impl.Script;
import org.smtlib.impl.Sort;
import org.smtlib.SMT.Configuration;
import org.smtlib.command.C_assert;
import org.smtlib.command.C_define_fun;
import fr.lip6.move.gal.InvariantProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.SafetyProp;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.gal2smt.Result;
import fr.lip6.move.gal.gal2smt.Solver;
import fr.lip6.move.gal.gal2smt.smt.IBMCSolver;
import fr.lip6.move.gal.semantics.Alternative;
import fr.lip6.move.gal.semantics.Determinizer;
import fr.lip6.move.gal.semantics.INext;
import fr.lip6.move.gal.semantics.INextBuilder;

public class NextBMCSolver implements IBMCSolver {

	private static final String STATE = "s";
	protected static final String NEXT = "_Next__";
	protected final Solver engine;
	protected final Configuration conf;
	protected ISolver solver;
	protected final IFactory efactory;
	protected final ISort.IFactory sortfactory ;
	private int depth = 0;
	
	protected INextBuilder nb;
	private boolean withAllDiff;

	public NextBMCSolver(Configuration smtConfig, Solver engine, boolean withAllDiff) {
		this.conf = smtConfig;
		this.efactory = smtConfig.exprFactory;
		this.sortfactory = smtConfig.sortFactory;
		this.engine = engine;
		this.withAllDiff = withAllDiff;
	}
	@Override
	public void init(Specification spec) {
		solver = engine.getSolver(conf);
		// start the solver
		IResponse err = solver.start();
		if (err.isError()) {
			throw new RuntimeException("Could not start solver "+ engine+" from path "+ conf.executable);
		}
		
		// Logic + options
		err = solver.set_option(efactory.keyword(Utils.PRODUCE_MODELS), efactory.symbol("true"));
		if (err.isError()) {
			throw new RuntimeException("Could not set :produce-models option");
		}
		err = solver.set_logic("QF_AUFLIA", null);
		if (err.isError()) {
			throw new RuntimeException("Could not set logic");
		}

		
		this.nb = INextBuilder.build(spec);
		
		
		// integer sort
		IApplication ints = sortfactory.createSortExpression(efactory.symbol("Int"));
		// an array, indexed by integers, containing integers : (Array Int Int) 
		IApplication arraySort = sortfactory.createSortExpression(efactory.symbol("Array"), ints, ints);
		// an array, indexed by ints of such arrays : (Array Int (Array Int Int)) 
		IApplication arrayArraySort = sortfactory.createSortExpression(efactory.symbol("Array"), ints, arraySort);
		
		Script script = new Script() ;
		// declare state variable : a big array of integer
		script.add(
				new org.smtlib.command.C_declare_fun(
						efactory.symbol(STATE),
						Collections.emptyList(),
						arrayArraySort								
						)
				);
		
		List<INext> nextRel = nb.getNextForLabel("");
		INext allTrans = Alternative.alt(nextRel);
		
		List<INext> bootstrap = new ArrayList<>();
		Determinizer det = new Determinizer(Collections.singleton(bootstrap).stream());
		Stream<List<INext>> nextStream = allTrans.accept(det);
		
		// define a boolean function with single parameter (step) for each transition
		ISymbol sstep = efactory.symbol("step");
		// a list of invocation of transitions of the form : ti(step)
		final List<IExpr> trs = new ArrayList<IExpr>();		

		// unique index for each independent sequence of transition relation
		int tindex = 0;
		
		final GalExpressionTranslator et = new GalExpressionTranslator(conf);

		// manual iteration over the results of determinize
		for (Iterator<List<INext>> seqit = nextStream.iterator() ; seqit.hasNext() ; /*NOP*/ ) {
			List<INext> seq = seqit.next();

			// To hold all constraints corresponding to this transition
			List<IExpr> conds = new ArrayList<IExpr>();

			// The current state : state[step]
			IExpr cur = accessStateAt(sstep);
			
			// do the translation as a Visit of INext
			NextTranslator translator = new NextTranslator(cur, et);
			
			for (INext statement : seq) {
				IExpr cond = statement.accept(translator);
				// the visit returns a new condition (Predicate case) or updates its state to reflect assignment
				if (cond != null)
					conds.add(cond);
			}

			// Enforce update of state a step+1
			IExpr snext = efactory.fcn(efactory.symbol("+"),sstep,efactory.numeral("1"));
			// The current state : state[step]
			IExpr next = accessStateAt(snext);
			// finish the update by asserting that the store result is equal to state at step+1
			conds.add( efactory.fcn(efactory.symbol("="), translator.getState(), next));
			
			// build up the full boolean function for the transition
			IExpr bodyExpr = efactory.fcn(efactory.symbol("and"), conds);
			if (conds.size() == 1) {
				bodyExpr = conds.get(0);
			}

			// declare the transition
			ISymbol fname = efactory.symbol("tr"+ tindex++);
			C_define_fun deftr = new org.smtlib.command.C_define_fun(
					fname,    // name
					Collections.singletonList(efactory.declaration(sstep, ints)), // param (int step) 
					Sort.Bool(), // return type
					bodyExpr); // actions : assertions over S[step] and S[step+1]
			script.commands().add(deftr);
			// add it to the components of NEXT
			trs.add(efactory.fcn(fname, sstep));
		}

		// One function to hold them all, and in the darkness bind them 
		C_define_fun nextR = new org.smtlib.command.C_define_fun(
				efactory.symbol(NEXT),    // name
				Collections.singletonList(efactory.declaration(sstep, ints)), // param (int step) 
				Sort.Bool(), // return type
				efactory.fcn(efactory.symbol("or"), trs)); // actions : OR of all transitions declared
		script.commands().add(nextR);
		
//		for (ICommand c : script.commands()) {
//			System.out.println(c);
//		}
		err = script.execute(solver);
		if (err.isError()) {
			throw new RuntimeException("Error when declaring system variables to SMT solver."+conf.defaultPrinter.toString(err));
		}
	}
	
	

	@Override
	public void exit() {
		IResponse res = solver.exit();
		if (res.isError()) {
			Logger.getLogger("fr.lip6.move.gal").info("SMT solver already quit.");
		}
	}

	@Override
	public Result checkSat() {
		IResponse res = solver.check_sat();
		if (res.isError()) {
			throw new RuntimeException("SMT solver raised an exception or timeout.");
		}
		IPrinter printer = conf.defaultPrinter;
	//	System.out.println(printer.toString(script));
		String textReply = printer.toString(res);
	//	System.out.println(printer.toString(res));
		if ("sat".equals(textReply)) {
			return Result.SAT;
		} else if ("unsat".equals(textReply)) {
			return Result.UNSAT;
		} else {
			throw new RuntimeException("SMT solver raised an error :" + textReply);
		}
	}

	@Override
	public void setShowSatState(boolean shouldShow) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getDepth() {
		return depth;
	}

	@Override
	public void incrementDepth() {
		C_assert nexti = new C_assert(efactory.fcn(efactory.symbol(NEXT),efactory.numeral(depth)));
		//System.out.println(nexti);
		nexti.execute(solver);
		
		depth++;
		
		if (withAllDiff) {
			IExpr cur = accessStateAt(depth);
			for (int i = 0 ; i < depth ; i++ ) {
				solver.assertExpr(
						efactory.fcn( efactory.symbol("not"), 
								efactory.fcn( efactory.symbol("="),
										cur, 
										accessStateAt(i))));
			}
		}
	}

	@Override
	public Result verify(Property prop) {
		if (prop.getBody() instanceof SafetyProp) {
			SafetyProp sbody = (SafetyProp) prop.getBody();

			QualifiedExpressionTranslator qet = new QualifiedExpressionTranslator(conf);
			qet.setNb(nb);
			IExpr state = accessStateAt(depth);
			IExpr sprop = qet.translateBool(sbody.getPredicate(), state);
			if (sbody instanceof InvariantProp) {
				sprop = efactory.fcn(efactory.symbol("not"), sprop);
			}
			return verifyAssertion(sprop);
		} else {
			Logger.getLogger("fr.lip6.move.gal").warning("Only safety properties are handled in SMT solution currently. Cannot handle " + prop.getName());
			return Result.UNKNOWN;
		}
	}

	
	public Result verifyAssertion(IExpr sprop) {
		solver.push(1);
		solver.assertExpr(sprop);
		Result res = checkSat();
		if (res == Result.SAT) {
			onSat(solver);
		}
		solver.pop(1);
		return res;
	}

	protected void onSat(ISolver solver) {
		// TODO
	}
	
	protected IExpr accessStateAt (int step) {
		return accessStateAt(efactory.numeral(step));
	}
	
	protected IExpr accessStateAt (IExpr step) {
		ISymbol select = efactory.symbol("select");		
		return efactory.fcn(select, efactory.symbol(STATE), step); 
	}
	
	@Override
	public void assertInitialState() {
		Script script = new Script();
		ISymbol select = efactory.symbol("select");
		
		int index = 0;		
		IExpr initial = accessStateAt(0); 
		for (int val : nb.getInitial()) {
			script.commands().add(
					new C_assert(
							efactory.fcn(efactory.symbol("="), 					
									// access initial [index]
									efactory.fcn(select, initial, efactory.numeral(index)),
									// it has value val
									efactory.numeral(val)
							)
					));
			index++;
		}
		script.execute(solver);
	}

}
