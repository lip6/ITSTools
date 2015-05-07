package fr.lip6.move.gal.gal2smt;


import static fr.lip6.move.gal.gal2smt.ExpressionTranslator.translate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.smtlib.IExpr;

import fr.lip6.move.gal.ArrayPrefix;
import fr.lip6.move.gal.Assignment;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.SelfCall;
import fr.lip6.move.gal.Statement;
import fr.lip6.move.gal.Variable;

public class GalToSmtFunctions{
	private static IExpr.IFactory efactory = GalToSMT.getSMT().smtConfig.exprFactory;
	
	
	public static List<IExpr> translateActions(IExpr indexNow, IExpr indexNext,
			EList<Statement> acts, GALTypeDeclaration gal) {
		List<IExpr> actions = new ArrayList<IExpr>();
		Set<Variable> vars = new HashSet<Variable>();
		Map<ArrayPrefix, HashSet<Integer>> arrays = new HashMap<ArrayPrefix, HashSet<Integer>>();		
		
		for (Statement action : acts) {
			if (action instanceof Assignment) {
				Assignment ass = (Assignment) action;
				
				IExpr expr = ExpressionTranslator.sucrerie(ass, indexNow);				
				
				if (ass.getLeft().getRef() instanceof Variable) {
					Variable var = (Variable) ass.getLeft().getRef();
					vars.add(var);
					
					actions.add(efactory.fcn(efactory.symbol("="), 
							translate(ass.getLeft(), indexNext), 
							expr));
					
				}else if (ass.getLeft().getRef() instanceof ArrayPrefix) {
					ArrayPrefix array = (ArrayPrefix) ass.getLeft().getRef();
					Constant indexArray =(Constant) ass.getLeft().getIndex();
					
					if(!arrays.containsKey(array)){
						arrays.put(array, new HashSet<Integer>());
					}					
					arrays.get(array).add(indexArray.getValue());
										
					IExpr left = ArraySMT.getIndex(array, indexArray.getValue());				
					
					actions.add(efactory.fcn(efactory.symbol("="), 
							efactory.fcn(efactory.symbol("select"),left,indexNext),
							expr));
				} 
				
			}else if(action instanceof SelfCall){
				
				SelfCall call = (SelfCall) action;			
				actions.add(CallsSMT.generateSelfCalls(call.getLabel(), indexNow));
			}	
		}
		/* Variable non modifié */
		for (Variable  var : gal.getVariables()) {
			if (! vars.contains(var)) {
				actions.add(efactory.fcn(efactory.symbol("="), 
						translate(var, indexNext),
						translate(var, indexNow)));
			}
		}
		
		/* Array non modifié */
		for (ArrayPrefix array : gal.getArrays()) {
			Set<Integer> indexes = arrays.get(array);
			int size = ((Constant) array.getSize()).getValue();
			/* On ajoute tout les index */
			for (int i = 0; i < size; i++) {
				if(indexes ==null || !indexes.contains(i)){
					/* On ajoute juste les index manquants */
					IExpr a = ArraySMT.getIndex(array, i);
					
					IExpr next = efactory.fcn(efactory.symbol("select"),a,indexNext);
					IExpr now = efactory.fcn(efactory.symbol("select"),a,indexNow);				
					actions.add(efactory.fcn(efactory.symbol("="),next,now));
				}
			}
		}
		return actions;
	}


}
