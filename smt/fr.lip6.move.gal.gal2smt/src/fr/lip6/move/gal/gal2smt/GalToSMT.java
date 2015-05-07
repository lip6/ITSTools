package fr.lip6.move.gal.gal2smt;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.smtlib.ICommand;
import org.smtlib.IExpr;
import org.smtlib.SMT;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.Transition;
import fr.lip6.move.gal.TypeDeclaration;
import fr.lip6.move.gal.Variable;
import fr.lip6.move.gal.gal2smt.popup.IGalToSMTAction;

public class GalToSMT {
	public static final int PORTEE = 10;
	private static final String LOGIC = "AUFLIRA";
	
	private static SMT smt;
	public static SMT getSMT() {
		if (smt == null) {
			smt = new SMT();
		}
		return smt;
	}
	private static IExpr.IFactory efactory = GalToSMT.getSMT().smtConfig.exprFactory;
	
	public static List<ICommand> transformeGal(Specification s, IGalToSMTAction action) {
		List<ICommand> commands = new ArrayList<ICommand>();
		
		List<IExpr> vars = new ArrayList<IExpr>();
		List<String> transitions = new ArrayList<String>();
		
		
		commands.addAll(CorpsSMT.smtHeaders(LOGIC));
		
		for (TypeDeclaration type : s.getTypes()) {
			if (type instanceof GALTypeDeclaration) {
				GALTypeDeclaration gal = (GALTypeDeclaration) type;
				
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
		}
		/* DEROULEMENTS */
		commands.addAll(TransitionSMT.deroulementTransition(transitions, PORTEE));
		
		/* PROPERTIES */
		action.setPropertiesList(s.getProperties());
		
		commands.addAll(CorpsSMT.smtFooter(vars));	
		
		return commands;
	}

	public static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal.gal2smt");
	}

}
