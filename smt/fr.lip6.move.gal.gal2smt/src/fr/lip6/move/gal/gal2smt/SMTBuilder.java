package fr.lip6.move.gal.gal2smt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.ISort;
import org.smtlib.IExpr.IFactory;
import org.smtlib.command.C_pop;
import org.smtlib.command.C_push;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.BooleanExpression;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.GF2;
import fr.lip6.move.gal.IntExpression;
import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.SelfCall;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.Variable;

public class SMTBuilder {

	
	// name of functions/transitions of gal
	List<String> transitions = new ArrayList<String>();

	// list of all variables
	List<IExpr> vars = new ArrayList<IExpr>();

	private String logic = "QF_AUFLIA";

	private Specification spec;

	private List<ICommand> semantics = null;

	private final static IFactory efactory = GalToSMT.getSMT().smtConfig.exprFactory;
	
	public SMTBuilder(Specification spec) {
		this.spec = spec;
	}
	/**
	 * Make sure to set a legal value for the solver.
	 * @param logic the logic for SMTlib
	 */
	public void setLogic(String logic) {
		this.logic = logic;
	}

	public void buildReachabilityProblem (Property prop, int depth, List<ICommand> commands) {
		addHeader(commands);
		addSemantics(commands,true);
		unrollTransitionRelation(depth, commands);
		addProperty(prop, depth+1, commands);
		addFooter(commands);
	}
	
	
	/** 
	 * Add a header necessary for SMTlib.
	 */
	public void addHeader (List<ICommand> commands) {
		addHeader(logic,commands);
	}
	
	/** 
	 * Add a header necessary for SMTlib.
	 * @param logic the logic to be used, or defaults to AUFLIA if unset.
	 */
	public void addHeader (String logic, List<ICommand> commands) {
		commands.add(new org.smtlib.command.C_set_option(efactory .keyword(":produce-models"), efactory.symbol("true")));
		commands.add(new org.smtlib.command.C_set_logic(efactory.symbol(logic)));
	}

	public void addSemantics (List<ICommand> commands, boolean withInitial) {
		if (semantics == null) {
			// cache miss
			semantics  = new ArrayList<ICommand>();			
			for (TypeDeclaration type : spec.getTypes()) {
				if (type instanceof GALTypeDeclaration) {
					GALTypeDeclaration gal = (GALTypeDeclaration) type;
					translateSemantics(gal, semantics);	
					
				}
			}
		}
		commands.addAll(semantics);
		if (withInitial) {
			for (TypeDeclaration type : spec.getTypes()) {
				if (type instanceof GALTypeDeclaration) {
					GALTypeDeclaration gal = (GALTypeDeclaration) type;
					addInitialConstraint(gal,commands);
				}
			}
		}
	}
	
	private void addInitialConstraint(GALTypeDeclaration gal, List<ICommand> commands) {
		for (Variable var : gal.getVariables()) {
			commands.add(VariableSMT.initVariable(var));
		}
		for (ArrayPrefix array : gal.getArrays()) {				
			commands.add(ArraySMT.initArray(array));
		}
	}
	
	public void translateSemantics (GALTypeDeclaration gal, List<ICommand> commands) {
		/* VARIABLES */				
		for (Variable var : gal.getVariables()) {
			commands.add(VariableSMT.variableToSmt(var));
			
			/* Ajout a la liste de variable */
			vars.add(efactory.symbol((var.getName())));
		}
		/* ARRAYS */
		for (ArrayPrefix array : gal.getArrays()) {				
			commands.add(ArraySMT.arrayToSmt(array));
			
			vars.add(efactory.symbol((array.getName())));
		}
		
		//TODO: creer et ajouter le hashset des vars a TransitionSMT
		Map<String, List<Transition>> labMap = CallsSMT.fillTransitionMap(gal.getTransitions());

		Set<Transition> done = new HashSet<Transition>();
		for (Transition transit : gal.getTransitions()) {			
			// find calls
			addTransition (done, transit, commands, gal, labMap);
		}		

	}
	
	private void addTransition(Set<Transition> done, Transition trans,
			List<ICommand> commands, GALTypeDeclaration gal, Map<String, List<Transition>> labMap) {
		
		if (! done.contains(trans)) {
			// find calls
			for (TreeIterator<EObject> it = trans.eAllContents() ; it.hasNext() ; /*NOP*/) {
				EObject obj = it.next();
				
				if (obj instanceof SelfCall) {
					SelfCall self = (SelfCall) obj;
					for (Transition t : labMap.get(self.getLabel().getName())) {
						// ensure callees are already serialized
						addTransition(done, t, commands, gal, labMap);
					}
				}
				if (obj instanceof BooleanExpression || obj instanceof IntExpression) {
					it.prune();
				}
			}
			
			ICommand transitCommand = TransitionSMT.transitionToSmt(trans, vars, gal);			
			commands.add(transitCommand);
			
			/* Ajout a la liste de transition */
			transitions.add(trans.getName());
		
			done.add(trans);
		}
	}
	public void unrollTransitionRelation (int depth, List<ICommand> commands) {
		/* DEROULEMENTS */
		commands.addAll(TransitionSMT.deroulementTransition(transitions, depth));
	}
	
	public void addProperty (Property prop, int depth, List<ICommand> commands) {
		// prepare later removal of property
		// only one assert in prop currently
//		ICommand push = new C_push(efactory.numeral(1));
//		commands.add(push);

		/* On place la property juste avant le check sat */		
		PropertySMT.addProperty(prop, depth, commands,true);		
	}
	
	public void removeProperty (List<ICommand> commands) {
		ICommand pop = new C_pop(efactory.numeral(commands.size()));
		commands.add(pop);
	}
	
	public void addFooter (List<ICommand> commands) {
//		commands.add(new org.smtlib.command.C_check_sat());		
//		commands.add(new org.smtlib.command.C_get_value(vars));		
//		commands.add(new org.smtlib.command.C_exit());	
	}
	public void buildMaxDepthReachedProblem(int depth, List<ICommand> commands) {
		addHeader(commands);
		addSemantics(commands,true);
		addDepthK(depth, commands);
		addFooter(commands);		
	}
	private void addDepthK(int depth, List<ICommand> commands) {
		// an all diff on pairs of states
	}
	public void buildInductionProblem(Property prop, int depth,
			List<ICommand> commands) {
		BooleanExpression prev = prop.getBody().getPredicate();
		// we want unsat iff. there is no trace leading to a desirable state
		// desirable as in : can be exhibited
		BooleanExpression bprop = prop.getBody().getPredicate();
		if (prop.getBody() instanceof ReachableProp) {
			bprop = GF2.not(bprop);
			// NOT : assert ! bprop up to k, and bprop in k+1
		} else if (prop.getBody() instanceof NeverProp) {
			bprop = GF2.not(bprop);
			// NOT : assert ! bprop up to k, and bprop in k+1
		} else {
			// NOP assert bprop up to k, and not bprop in k+1
		}
		
		
		addHeader(commands);
		addSemantics(commands,false);
		// unroll to k+1
		unrollTransitionRelation(depth+1, commands);
		// and property up to depth (inclusive)
		for (int i=0 ; i < depth+1; i++) {
			commands.add(new org.smtlib.command.C_assert(ExpressionTranslator.translateBool(bprop, efactory.numeral(i))));
		}
		//negate
		bprop = GF2.not(bprop);
		// assert ! prop at step depth+1		
		commands.add(new org.smtlib.command.C_assert(ExpressionTranslator.translateBool(bprop, efactory.numeral(depth+1))));

		prop.getBody().setPredicate(prev);
	}
	public void declarePositiveIntegerVariable(String name,	List<ICommand> commands) {
		IExpr.ISymbol p= efactory.symbol(name);		
		ISort ints = GalToSMT.getSMT().smtConfig.sortFactory.createSortExpression(efactory.symbol("Int"));
		
		ICommand command = new org.smtlib.command.C_declare_fun(p, Collections.EMPTY_LIST, ints );
		
		commands.add(command);
		
		// assert >= 0
		commands.add(new org.smtlib.command.C_assert(efactory.fcn(efactory.symbol(">="), p , efactory.numeral(0))));
	}
}
