package fr.lip6.move.gal.application;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import fr.lip6.move.gal.structural.DeadlockFound;
import fr.lip6.move.gal.structural.NoDeadlockExists;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;

public class GlobalPropertySolver {

	private String solverPath;
	
	private SparsePetriNet spn;

	public GlobalPropertySolver(String solverPath) {
		this.solverPath = solverPath;
	}

	//oneSafe
	void buildOneSafeProprety() {
		
		for(int pid = 0; pid < spn.getPlaceCount(); pid++) {
			Expression pInfOne = Expression.op(Op.LEQ, Expression.var(pid), Expression.constant(1));
			// unary op ignore right
			Expression ag = Expression.op(Op.AG, pInfOne, null);
			Property oneSafeProperty =  new Property(ag ,PropertyType.INVARIANT,"place_"+pid);
			spn.getProperties().add(oneSafeProperty);
		}
		
	}
	
	public boolean solveProperty(String examination, MccTranslator reader) {

		// initialize a shared container to detect help detect termination in portfolio case
		Map<String,Boolean> doneProps = new ConcurrentHashMap<>();

		boolean isSafe = false;
		// load "known" stuff about the model
		if (reader.isSafeNet()) {
			// NUPN implies one safe
			isSafe = true;
		}
		
		
		// build properties
		spn = reader.getSPN();
		
//	for (int tid=0; tid < spn.getTransitionCount() ; tid++) {
//		Expression prop = Expression.nop(Op.ENABLED, Collections.singletonList(Expression.trans(tid)));
//			Expression ag = Expression.op(Op.AG, prop, null);
//			Property p = new Property(ag ,PropertyType.INVARIANT,"enabled"+tid);
//			spn.getProperties().add(p );
//		}
//		System.out.println(spn);
//		
//		
//	Expression stable = Expression.op(Op.EQ, Expression.var(0), Expression.constant(spn.getMarks().get(0)));	
	
	
		//oneSafe
		buildOneSafeProprety();
		
		spn.simplifyLogic();
		spn.toPredicates();			
		spn.testInInitial();
		spn.removeConstantPlaces();
		spn.removeRedundantTransitions(false);
		spn.removeConstantPlaces();
		spn.simplifyLogic();
		if (isSafe) {
			spn.assumeOneSafe();
		}
		
		// vire les prop triviales, utile ?
		ReachabilitySolver.checkInInitial(reader, doneProps);
		if (!spn.getProperties().isEmpty()) {
			try {
				ReachabilitySolver.applyReductions(reader, doneProps, solverPath, isSafe);
			} catch (NoDeadlockExists e) {
				e.printStackTrace();
			} catch (DeadlockFound e) {
				e.printStackTrace();
			}
		}
		boolean isOneSafe = true;
		for(Entry<String, Boolean> e : doneProps.entrySet()) {
			if(e.getValue() == true) {
				System.out.println("FORMULA ONESAFE FALSE");
				isOneSafe = false;
				System.out.println("Property is false " + e.getKey());
				break;
			}
		}
		if(isOneSafe) System.out.println("FORMULA ONESAFE TRUE");
		return false;
	}

	
	
}
