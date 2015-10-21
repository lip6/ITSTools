package fr.lip6.move.gal.gal2smt.bmc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.smtlib.IExpr.ISymbol;
import org.smtlib.IExpr;
import org.smtlib.IResponse;
import org.smtlib.ISolver;
import org.smtlib.ISort;
import org.smtlib.IExpr.IFactory;
import org.smtlib.ISort.IApplication;
import org.smtlib.SMT.Configuration;
import org.smtlib.command.C_define_fun;
import org.smtlib.impl.Script;
import org.smtlib.impl.Sort;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.AssignType;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.LabelCall;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.SelfCall;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Statement;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.gal2smt.ArraySMT;
import fr.lip6.move.gal.gal2smt.ExpressionTranslator;
import fr.lip6.move.gal.gal2smt.GalToSMT;
import fr.lip6.move.gal.gal2smt.Result;
import fr.lip6.move.gal.gal2smt.Solver;
import fr.lip6.move.gal.gal2smt.VariableSMT;

public class BMCSolver implements IBMCSolver {


	private final Solver engine;
	private final Configuration conf;
	private ISolver solver;
	private final IFactory efactory;
	private final ISort.IFactory sortfactory ;

	public BMCSolver(Configuration smtConfig, Solver engine) {
		this.engine = engine;
		this.conf = smtConfig;
		this.efactory = smtConfig.exprFactory;
		this.sortfactory = smtConfig.sortFactory;
	}

	/**
	 * {@inheritDoc}
	 * @param spec
	 * @param withInitialState
	 */
	@Override
	public void init(Specification spec, boolean withInitialState) {
		
		solver = engine.getSolver(conf);
		// start the solver
		IResponse err = solver.start();
		if (err.isError()) {
			throw new RuntimeException("Could not start solver "+ engine+" from path "+ conf.executable);
		}
		
		// declare logic + headers
		Script script = new Script();
		
		
		// Logic + options
		script.commands().add(new org.smtlib.command.C_set_option(efactory .keyword(":produce-models"), efactory.symbol("true")));
		script.commands().add(new org.smtlib.command.C_set_logic(efactory.symbol("QF_AUFLIA")));

		if (spec.getMain() instanceof GALTypeDeclaration) {
			GALTypeDeclaration gal = (GALTypeDeclaration) spec.getMain();
			
			// integer sort
			ISort ints = sortfactory.createSortExpression(efactory.symbol("Int"));
			// an array, indexed by integers, containing integers : (Array Int Int) 
			IApplication arraySort = sortfactory.createSortExpression(efactory.symbol("Array"), ints, ints);
			// an array, indexed by ints of such arrays : (Array Int (Array Int Int)) 
			IApplication arrayArraySort = sortfactory.createSortExpression(efactory.symbol("Array"), ints, arraySort);
			
			// Declare variables
			/* VARIABLES */				
			for (Variable var : gal.getVariables()) {
				// a new variable with this type
				script.commands().add(
						new org.smtlib.command.C_declare_fun(
								efactory.symbol(var.getName()),
								Collections.EMPTY_LIST,
								arraySort								
								)
						
						);
			}
			/* ARRAYS */
			for (ArrayPrefix array : gal.getArrays()) {
				// a new variable with this type
				script.commands().add(
						new org.smtlib.command.C_declare_fun(
								efactory.symbol(array.getName()),
								Collections.EMPTY_LIST,
								arrayArraySort								
								)
						
						);
			}
			
			/* TRANS */
			// collect labels
			Map<String, List<Transition>> labmap = new HashMap<String, List<Transition>>();
			for (Transition tr : gal.getTransitions()) {
				String lname;
				if (tr.getLabel() == null)
					lname = "";
				else 
					lname = tr.getLabel().getName();
				List<Transition> list = labmap.get(lname );
				if (list == null) {
					list = new ArrayList<Transition>();
					labmap.put(lname, list);
				}
				list.add(tr);
			}
			// compute an order on labels respecting call order (callees before callers) 
			Set<String> todo = new LinkedHashSet<String>();
			handlePrecedence(gal,todo,labmap,"");
			
			// iterate over labels and output transitions
			for (String lab : todo) {
				// define a boolean function with single parameter (step) for each transition
				ISymbol sstep = efactory.symbol("step");
				IExpr snext = efactory.fcn(efactory.symbol("+"),sstep,efactory.numeral("1"));
				for (Transition tr : labmap.get(lab)) {
					List<IExpr> conds = new ArrayList<IExpr>();
					
					if (! (tr.getGuard() instanceof True)) {
						conds.add(ExpressionTranslator.translateBool(tr.getGuard(), sstep));
					}
					// to keep track of modified vars and array cells
					Set<Variable> vars = new HashSet<Variable>();
					Map<ArrayPrefix, Set<Integer>> arrays = new HashMap<ArrayPrefix, Set<Integer>>();

					// handle statements
					for (Statement st : tr.getActions()) {
						
						if (st instanceof Assignment) {
							Assignment ass = (Assignment) st;

							IExpr lhs = ExpressionTranslator.translate(ass.getLeft(), snext);
							IExpr rhs = ExpressionTranslator.translate(ass.getRight(), sstep);							
							if (ass.getType() == AssignType.INCR ) {
								rhs = efactory.fcn(efactory.symbol("+"), 
										ExpressionTranslator.translate(ass.getLeft(), sstep),
										rhs
										);							
							} else if (ass.getType() == AssignType.DECR ) {
								rhs = efactory.fcn(efactory.symbol("-"), 										
										ExpressionTranslator.translate(ass.getLeft(), sstep),
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
								if (! hit.add(((Constant) ass.getLeft().getIndex()).getValue())) {
									throw new RuntimeException("Multiple assignments to a single variable are not supported in SMT translation currently. Was translating transition " + tr.getName());
								}
							}
						} else if (st instanceof SelfCall) {
							SelfCall scall = (SelfCall) st;
							
						}
						
						
					}
					
					
					
					// add constraints on unmodified variables
					for (Variable  var : gal.getVariables()) {
						if (! vars.contains(var)) {
							conds.add(efactory.fcn(efactory.symbol("="), 
									accessVar(var, sstep),
									accessVar(var, snext)));
						}
					}
					
					/* Array cell case */
					for (ArrayPrefix array : gal.getArrays()) {
						Set<Integer> indexes = arrays.get(array);
						int size = ((Constant) array.getSize()).getValue();
						/* On ajoute tout les index */
						for (int i = 0; i < size; i++) {
							if(indexes == null || !indexes.contains(i)){
								conds.add(efactory.fcn(efactory.symbol("="), 
										accessArray(array, i, sstep),
										accessArray(array, i, sstep)
										));
							}
						}
					}
					// 
					C_define_fun deftr = new org.smtlib.command.C_define_fun(
							efactory.symbol(tr.getName()),    // name
							Collections.singletonList(efactory.declaration(sstep, ints), // param (int step) 
							Sort.Bool(), // return type
							bodyExpr); // actions : assertions over S[step] and S[step+1]
				}
				//
			}
			
			
		} else {
			throw new RuntimeException("Expected ITS Specification to have a GAL as main type for SMT solution !");
		}
		
	}
	
	/**
	 * Access array[index] at time "step"
	 * @param array array to access
	 * @param index cell to access 
	 * @param step time step to access
	 * @return a select expression to find the appropriate variable
	 */
	private IExpr accessArray (ArrayPrefix array, int index, IExpr step) {
		ISymbol select = efactory.symbol("select");
		
		return efactory.fcn(select,
				efactory.fcn(select,
						efactory.symbol(array.getName()), efactory.numeral(index)), 
				step);
		
	}

	public IExpr accessVar (Variable vr, IExpr step) {
		return efactory.fcn(efactory.symbol("select"), efactory.symbol(vr.getName()), step);
	}

	/**
	 * Compute into todo an ordering of labels of the GAL that respects precedence order "callee before caller"
	 * @param gal the system def
	 * @param todo an (initially empty) set of labels, respecting the precedes partial order
	 * @param labmap a map of labels to sets of transitions bearing that label
	 * @param lab the currently examined label
	 */
	private void handlePrecedence(GALTypeDeclaration gal, Set<String> todo,
			Map<String, List<Transition>> labmap, String lab) {

		if (todo.contains(lab)) {
			return;
		}
		for (Transition tr :  labmap.get(lab)) {
			// find calls
			for (TreeIterator<EObject> it = tr.eAllContents() ; it.hasNext() ; /*NOP*/) {
				EObject obj = it.next();
				
				if (obj instanceof SelfCall) {
					SelfCall self = (SelfCall) obj;
					handlePrecedence(gal, todo, labmap, self.getLabel().getName());
				}
				if (obj instanceof Assignment || obj instanceof LabelCall || obj instanceof BooleanExpression) {
					it.prune();
				}
			}
		}
		todo.add(lab);
	}

	@Override
	public int getDepth() {
		return 0;
	}

	@Override
	public void incrementDepth() {

	}

	@Override
	public Result checkProperty(Property prop) {
		return null;
	}

	@Override
	public void exit() {

	}

}
