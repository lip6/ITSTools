package fr.lip6.move.gal.gal2smt;

import java.util.ArrayList;
import java.util.List;

import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.IExpr.IFactory;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.Variable;

public class SMTBuilder {

	
	// name of functions/transitions of gal
	List<String> transitions = new ArrayList<String>();

	// list of all variables
	List<IExpr> vars = new ArrayList<IExpr>();

	private String logic = "AUFLIA";

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
		addHeader(logic, commands);
		addSemantics(commands);
		addProperty(prop, depth, commands);
		addFooter(commands);
	}
	
	/** 
	 * Add a header necessary for SMTlib.
	 * @param logic the logic to be used, or defaults to AUFLIA if unset.
	 */
	public void addHeader (String logic, List<ICommand> commands) {
		commands.add(new org.smtlib.command.C_set_option(efactory .keyword(":produce-models"), efactory.symbol("true")));
		commands.add(new org.smtlib.command.C_set_logic(efactory.symbol(logic)));
	}

	public void addSemantics (List<ICommand> commands) {
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
	}
	
	public void translateSemantics (GALTypeDeclaration gal, List<ICommand> commands) {
		/* VARIABLES */				
		for (Variable var : gal.getVariables()) {
			commands.add(VariableSMT.variableToSmt(var));
			commands.add(VariableSMT.initVariable(var));
			
			/* Ajout a la liste de variable */
			vars.add(efactory.symbol((var.getName())));
		}
		/* ARRAYS */
		for (ArrayPrefix array : gal.getArrays()) {				
			commands.add(ArraySMT.arrayToSmt(array));
			commands.add(ArraySMT.initArray(array));
			
			vars.add(efactory.symbol((array.getName())));
		}
		
		//TODO: creer et ajouter le hashset des vars a TransitionSMT
		CallsSMT.fillTransitionMap(gal.getTransitions());
		
		for (Transition transit : gal.getTransitions()) {
			ICommand transitCommand = TransitionSMT.transitionToSmt(transit, vars, gal);
			commands.add(transitCommand);
				
			/* Ajout a la liste de transition */
			transitions.add(transit.getName());
					
		}		

	}
	
	public void unrollTransitionRelation (int depth, List<ICommand> commands) {
		/* DEROULEMENTS */
		commands.addAll(TransitionSMT.deroulementTransition(transitions, depth));
	}
	
	public void addProperty (Property prop, int depth, List<ICommand> commands) {
		/* On place la property juste avant le check sat */
		PropertySMT.addProperty(prop, depth, commands);		
	}
	
	public void addFooter (List<ICommand> commands) {
		commands.add(new org.smtlib.command.C_check_sat());		
		commands.add(new org.smtlib.command.C_get_value(vars));		
		commands.add(new org.smtlib.command.C_exit());	
	}
	public void buildMaxDepthReachedProblem(int depth, List<ICommand> commands) {
		addHeader(logic, commands);
		addSemantics(commands);
		addDepthK(depth, commands);
		addFooter(commands);		
	}
	private void addDepthK(int depth, List<ICommand> commands) {
		
	}
}
