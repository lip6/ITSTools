package fr.lip6.move.gal.gal2smt.bmc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.smtlib.ICommand;
import org.smtlib.IExpr.IDeclaration;
import org.smtlib.IExpr.ISymbol;
import org.smtlib.IExpr;
import org.smtlib.IResponse;
import org.smtlib.ISort;
import org.smtlib.SMT.Configuration;
import org.smtlib.command.C_assert;
import org.smtlib.command.C_define_fun;
import org.smtlib.impl.Script;
import org.smtlib.impl.Sort;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.AssignType;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.InvariantProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.SafetyProp;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Statement;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.gal2smt.Result;
import fr.lip6.move.gal.gal2smt.Solver;
import fr.lip6.move.gal.gal2smt.smt.IBMCSolver;
import fr.lip6.move.gal.gal2smt.smt.IVariableHandler;
import fr.lip6.move.gal.gal2smt.smt.SMTSolver;
import fr.lip6.move.gal.instantiate.Instantiator;

public class BMCSolver extends SMTSolver implements IBMCSolver {


	private static final String NEXT = "_Next__";
	private static final String DIFF = "_Diff__";
	private int depth = 0;
	private boolean withAllDiff;

	public BMCSolver(Configuration smtConfig, Solver engine, boolean withAllDiff) {
		this(smtConfig,engine,new VariableHandler(smtConfig),withAllDiff);
	}
	
	public BMCSolver(Configuration smtConfig, Solver engine, IVariableHandler vh, boolean withAllDiff) {
		super(engine,smtConfig,vh);
		this.withAllDiff = withAllDiff;
	}

	/**
	 * {@inheritDoc}
	 * @param spec
	 * @param withInitialState
	 */
	@Override
	public void init(Specification spec) {
		super.init(spec);

		// declare logic + headers
		Script script = new Script();

		if (spec.getMain() instanceof GALTypeDeclaration) {
			GALTypeDeclaration gal = (GALTypeDeclaration) spec.getMain();

			/* TRANS */
			// define a boolean function with single parameter (step) for each transition
			ISymbol sstep = efactory.symbol("step");
			// a list of invocation of transitions of the form : ti(step)
			List<IExpr> trs = new ArrayList<IExpr>();

			for (Transition tr : gal.getTransitions()) {
				if (tr.getLabel() == null || "".equals(tr.getLabel().getName())) {
					//translate local private transitions
					addTransitionDeclaration(tr,gal,trs, script, sstep);
				} // else skip labeled transitions
			}

			ISort ints = sortfactory.createSortExpression(efactory.symbol("Int"));
			
			C_define_fun deftr = new org.smtlib.command.C_define_fun(
					efactory.symbol(NEXT),    // name
					Collections.singletonList(efactory.declaration(sstep, ints)), // param (int step) 
					Sort.Bool(), // return type
					efactory.fcn(efactory.symbol("or"), trs)); // actions : assertions over S[step] and S[step+1]
			
			script.commands().add(deftr);
		//	Logger.getLogger("fr.lip6.move.gal").info(script.commands().toString());
			
			if (withAllDiff) {
				addStateDiffer(gal,script.commands());
			}
			
			IResponse res = script.execute(solver);
			if (res.isError()) {
				throw new RuntimeException("Specification could not be read correctly with by SMT Solver");				
			}
		} else {
			throw new RuntimeException("Expected ITS Specification to have a GAL as main type for SMT solution !");
		}
	}

	private void addStateDiffer(GALTypeDeclaration gal, List<ICommand> commands) {
		List<IExpr> diffs = new ArrayList<IExpr>();
		IExpr indexi = efactory.symbol("i");
		IExpr indexj = efactory.symbol("j");
		
		for (Variable var : gal.getVariables()) {					
			IExpr varx = vh.accessVar(var, indexi);
			IExpr varxn = vh.accessVar(var, indexj);
			
			IExpr expr = efactory.fcn(efactory.symbol("not"), 
									  efactory.fcn(efactory.symbol("="),varx, varxn)
									  );	
						
			diffs.add(expr);
		}
		
		// add arrays 
		for (ArrayPrefix arr : gal.getArrays()) {
			for (int i=0 ; i < ((Constant) arr.getSize()).getValue() ; i++) {
				IExpr varx = vh.accessArray(arr, efactory.numeral(i), indexi);
				IExpr varxn = vh.accessArray(arr, efactory.numeral(i), indexj);
				
				IExpr expr = efactory.fcn(efactory.symbol("not"), 
										  efactory.fcn(efactory.symbol("="),varx, varxn)
										  );	
							
				diffs.add(expr);
			}
		}
		
		IExpr oneDiff = efactory.fcn(efactory.symbol("or"), diffs);
		if (diffs.size() == 1) {
			oneDiff = diffs.get(0);
		}
		
		IExpr.ISymbol symbol = efactory.symbol(DIFF);
		
		ISort bool = Sort.Bool();
		ISort Int = sortfactory .createSortExpression(efactory.symbol("Int"));
		
		IDeclaration iDeclaration = efactory.declaration(efactory.symbol("i"), Int);
		IDeclaration jDeclaration = efactory.declaration(efactory.symbol("j"), Int);
		
		List<IDeclaration> declarations = new ArrayList<IDeclaration>();
		declarations.add(iDeclaration);
		declarations.add(jDeclaration);
		
		
		commands.add(new org.smtlib.command.C_define_fun(symbol, declarations, bool, oneDiff));		

	}
	
	/**
	 * Add assertion on S[0] corresponding to initial conditions
	 * @param spec
	 */
	public void assertInitialState (Specification spec) {
		if (spec.getMain() instanceof GALTypeDeclaration) {
			GALTypeDeclaration gal = (GALTypeDeclaration) spec.getMain();
			Script script = new Script();
			vh.addInitialConstraint(script, gal);
			script.execute(solver);
		}
	}

	private void addTransitionDeclaration(Transition tr,
			GALTypeDeclaration gal, List<IExpr> trs, Script script, ISymbol sstep) {
		List<IExpr> conds = new ArrayList<IExpr>();
		IExpr snext = efactory.fcn(efactory.symbol("+"),sstep,efactory.numeral("1"));

		
		if (! (tr.getGuard() instanceof True)) {
			conds.add(et.translateBool(tr.getGuard(), sstep));
		}
				
		// to keep track of modified vars and array cells
		Set<Variable> vars = new HashSet<Variable>();
		Map<ArrayPrefix, Set<Integer>> arrays = new HashMap<ArrayPrefix, Set<Integer>>();

		// handle statements
		for (Statement st : tr.getActions()) {						
			if (st instanceof Assignment) {
				Assignment ass = (Assignment) st;

				IExpr lhs ;
				if (ass.getLeft().getIndex() == null) {
					lhs = et.translate(ass.getLeft(), snext);
				} else {
					// index is read at current step
					IExpr index = et.translate(ass.getLeft().getIndex(), sstep);
					// assignment is done on next variables
					lhs = et.accessArray((ArrayPrefix) ass.getLeft().getRef(), index, snext);
				}
				IExpr rhs = et.translate(ass.getRight(), sstep);							
				if (ass.getType() == AssignType.INCR ) {
					rhs = efactory.fcn(efactory.symbol("+"), 
							et.translate(ass.getLeft(), sstep),
							rhs
							);							
				} else if (ass.getType() == AssignType.DECR ) {
					rhs = efactory.fcn(efactory.symbol("-"), 										
							et.translate(ass.getLeft(), sstep),
							rhs
							);							
				}
				conds.add(efactory.fcn(efactory.symbol("="), lhs, rhs));

				// we have modifications we need to note
				if (ass.getLeft().getIndex() == null) {
					if (! vars.add((Variable) ass.getLeft().getRef())) {
						throw new RuntimeException("Multiple assignments to a single variable are not supported in SMT translation currently. Was translating transition " + tr.getName());
					}
				} else {
					Set<Integer> hit = arrays.get(ass.getLeft().getRef());
					if (hit == null) { 
						hit = new HashSet<Integer>(); 
						arrays.put((ArrayPrefix) ass.getLeft().getRef(), hit);
					}
					if (ass.getLeft().getIndex() instanceof Constant) {
						Constant index = (Constant) ass.getLeft().getIndex();
						if (! hit.add(index.getValue())) {
							throw new RuntimeException("Multiple assignments to a single variable are not supported in SMT translation currently. Was translating transition " + tr.getName());
						}						
					} else {
						for (int i =0 ; i < Instantiator.evalConst(((ArrayPrefix) ass.getLeft().getRef()).getSize()); i++) {
							if (! hit.add(i)) {
								throw new RuntimeException("Multiple assignments to a single variable are not supported in SMT translation currently. Was translating transition " + tr.getName());
							}	
						}
					}
				}
			} else {
				//							if (st instanceof SelfCall) {
				throw new RuntimeException("Only assignments are supported for GAL currently. Was translating transition " + tr.getName());
			}						
		}



		// add constraints on unmodified variables
		for (Variable  var : gal.getVariables()) {
			if (! vars.contains(var)) {
				conds.add(efactory.fcn(efactory.symbol("="), 
						vh.accessVar(var, sstep),
						vh.accessVar(var, snext)));
			}
		}

		/* Array cell case */
		for (ArrayPrefix array : gal.getArrays()) {
			Set<Integer> indexes = arrays.get(array);
			int size = ((Constant) array.getSize()).getValue();
			/* On ajoute tous les index */
			for (int i = 0; i < size; i++) {
				if(indexes == null || !indexes.contains(i)){
					conds.add(efactory.fcn(efactory.symbol("="), 
							vh.accessArray(array,efactory.numeral(i), sstep),
							vh.accessArray(array,efactory.numeral(i), snext)
							));
				}
			}
		}
		IExpr bodyExpr = efactory.fcn(efactory.symbol("and"), conds);
		if (conds.size() == 1) {
			bodyExpr = conds.get(0);
		}
		// 
		ISort ints = sortfactory.createSortExpression(efactory.symbol("Int"));
		ISymbol fname = efactory.symbol(tr.getName());
		C_define_fun deftr = new org.smtlib.command.C_define_fun(
				fname,    // name
				Collections.singletonList(efactory.declaration(sstep, ints)), // param (int step) 
				Sort.Bool(), // return type
				bodyExpr); // actions : assertions over S[step] and S[step+1]
		script.commands().add(deftr);
		//
		trs.add(efactory.fcn(fname, sstep));
	}


	@Override
	public int getDepth() {
		return depth ;
	}

	@Override
	public void incrementDepth() {
		new C_assert(efactory.fcn(efactory.symbol(NEXT),efactory.numeral(depth))).execute(solver);
		depth++;
		
		if (withAllDiff) {
			for (int i = 0 ; i < depth ; i++ ) {
				solver.assertExpr(efactory.fcn(efactory.symbol(DIFF), efactory.numeral(i), efactory.numeral(depth)));
			}
		}
		
		
	}

	/**
	 * {@inheritDoc}
	 * 	@return SAT if property is reachable at current depth S[depth] |= prop (trace exists), UNSAT else, or UNKNWOWN in case of timeout
	 */
	@Override
	public Result verify(Property prop) {
		if (prop.getBody() instanceof SafetyProp) {
			SafetyProp sbody = (SafetyProp) prop.getBody();

			IExpr sprop = et.translateBool(sbody.getPredicate(), efactory.numeral(depth));
			if (sbody instanceof InvariantProp) {
				sprop = efactory.fcn(efactory.symbol("not"), sprop);
			}
			return super.verifyAssertion(sprop);

		}else {
			Logger.getLogger("fr.lip6.move.gal").warning("Only safety properties are handled in SMT solution currently. Cannot handle " + prop.getName());
			return Result.UNKNOWN;
		}
	}
	



}


//// label oriented code :
///**
// * Compute into todo an ordering of labels of the GAL that respects precedence order "callee before caller"
// * @param gal the system def
// * @param todo an (initially empty) set of labels, respecting the precedes partial order
// * @param labmap a map of labels to sets of transitions bearing that label
// * @param lab the currently examined label
// */
//private void handlePrecedence(GALTypeDeclaration gal, Set<String> todo,
//		Map<String, List<Transition>> labmap, String lab) {
//
//	if (todo.contains(lab)) {
//		return;
//	}
//	for (Transition tr :  labmap.get(lab)) {
//		// find calls
//		for (TreeIterator<EObject> it = tr.eAllContents() ; it.hasNext() ; /*NOP*/) {
//			EObject obj = it.next();
//
//			if (obj instanceof SelfCall) {
//				SelfCall self = (SelfCall) obj;
//				handlePrecedence(gal, todo, labmap, self.getLabel().getName());
//			}
//			if (obj instanceof Assignment || obj instanceof LabelCall || obj instanceof BooleanExpression) {
//				it.prune();
//			}
//		}
//	}
//	todo.add(lab);
//}
//// collect labels
//Map<String, List<Transition>> labmap = new HashMap<String, List<Transition>>();
//for (Transition tr : gal.getTransitions()) {
//	String lname;
//	if (tr.getLabel() == null)
//		lname = "";
//	else 
//		lname = tr.getLabel().getName();
//	List<Transition> list = labmap.get(lname );
//	if (list == null) {
//		list = new ArrayList<Transition>();
//		labmap.put(lname, list);
//	}
//	list.add(tr);
//}
//
//
////			// Following code irrelevant until encoding scheme for calls is found
////			// compute an order on labels respecting call order (callees before callers) 
////			Set<String> todo = new LinkedHashSet<String>();
////			handlePrecedence(gal,todo,labmap,"");
////			
////			// iterate over labels and output transitions
////			for (String lab : todo) {
