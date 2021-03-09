package fr.lip6.move.gal.application;

import java.util.Collections;
import java.util.Map.Entry;

import fr.lip6.move.gal.mcc.properties.DoneProperties;
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

	// oneSafe

	// TODO: javadoc
	
	void buildOneSafeProperty() {

		for (int pid = 0; pid < spn.getPlaceCount(); pid++) {
			Expression pInfOne = Expression.op(Op.LEQ, Expression.var(pid), Expression.constant(1));
			// unary op ignore right
			Expression ag = Expression.op(Op.AG, pInfOne, null);
			Property oneSafeProperty = new Property(ag, PropertyType.INVARIANT, "place_" + pid);
			spn.getProperties().add(oneSafeProperty);
		}

	}

	void buildStableMarkingProperty() {
		for (int pid = 0; pid < spn.getPlaceCount(); pid++) {
			Expression stable = Expression.op(Op.EQ, Expression.var(pid), Expression.constant(spn.getMarks().get(pid)));
			Expression ef = Expression.op(Op.AG, stable, null);
			Property stableMarkingProperty = new Property(ef, PropertyType.INVARIANT, "place_" + pid);
			spn.getProperties().add(stableMarkingProperty);
		}
	}

	void buildQuasiLivenessProperty() {
		for (int tid = 0; tid < spn.getTransitionCount(); tid++) {
			Expression quasiLive = Expression.nop(Op.ENABLED, Collections.singletonList(Expression.trans(tid)));
			Expression ef = Expression.op(Op.EF , quasiLive, null);
			Property quasiLivenessProperty = new Property(ef, PropertyType.INVARIANT, "transition_"+tid);
			spn.getProperties().add(quasiLivenessProperty);

		}

	}

	public boolean solveProperty(String examination, MccTranslator reader) {

		// initialize a shared container to detect help detect termination in portfolio case
		DoneProperties doneProps = new MccDonePropertyPrinter();

		boolean isSafe = false;
		// load "known" stuff about the model
		if (reader.isSafeNet()) {
			// NUPN implies one safe
			isSafe = true;
		}
		
		
		spn = reader.getSPN();



		// switching examination
		switch (examination) {

		case "StableMarking":
			buildStableMarkingProperty();
			break;

		case "OneSafe":
			buildOneSafeProperty();
			break;
		case "QuasiLiveness":
			buildQuasiLivenessProperty();
			break;
		}


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
	
		
		return false;
		}
		// TODO: change this
		// boolean isOneSafe = true;
		for (Entry<String, Boolean> e : doneProps.entrySet()) {
			if (!e.getValue()) {
				/*
				 * System.out.println("FORMULA ONESAFE FALSE"); isOneSafe = false;
				 * System.out.println("Property is false " + e.getKey()); break;
				 */

				return false;
			}
		}

		// if(isOneSafe) System.out.println("FORMULA ONESAFE TRUE");
		return true;
	}
}


