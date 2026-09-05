package fr.lip6.move.gal.application.solver.abstraction;

import java.util.BitSet;
import java.util.List;

import fr.lip6.move.gal.application.mcc.MccTranslator;
import fr.lip6.move.gal.application.solver.ReachabilitySolver;
import android.util.SparseIntArray;
import fr.lip6.move.gal.application.solver.ExhaustiveEngines;
import fr.lip6.move.gal.mcc.properties.ConcurrentHashDoneProperties;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.GlobalPropertySolvedException;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.util.IntMatrixCol;
import fr.lip6.move.petrispot.runner.PetriSpotRunner;
import fr.lip6.move.petrispot.runner.PetriSpotWalker;

/**
 * Upper bounds by place projection (ABSTRACTION.md): for an open bound with
 * maxSeen below the structural bound, prove the invariant "body <= maxSeen"
 * on projections of the net, refining with the places a spurious abstract
 * witness needed; a real abstract witness raises maxSeen instead.
 */
public class ProjectionBoundsSolver {

	private static final int DEBUG = 1;

	/**
	 * Try every open property; closes those proved (bound = maxSeen) and
	 * updates maxSeen when a real witness is found.
	 *
	 * @return the number of properties closed
	 */
	public static int solve(MccTranslator reader, List<Expression> tocheck, List<Integer> maxSeen,
			List<Integer> maxStruct, DoneProperties doneProps, int budgetSeconds) {
		SparsePetriNet spn = reader.getSPN();
		if (spn.getProperties().isEmpty()) return 0;
		long t0 = System.currentTimeMillis();
		IntMatrixCol semiflows = PetriSpotRunner.computeInvariants(spn, PetriSpotRunner.InvariantMode.PSEMIFLOWS, 60);
		SemiflowCover cover = new SemiflowCover(spn, semiflows);
		if (DEBUG >= 1) System.out.println("Place projection: " + cover.size() + " semi-flows over " + spn.getPlaceCount()
				+ " places, " + tocheck.size() + " open bounds.");
		int closed = 0;
		int perProperty = Math.max(5, budgetSeconds / Math.max(1, tocheck.size()));
		for (int v = tocheck.size() - 1; v >= 0; v--) {
			if (System.currentTimeMillis() - t0 > 1000L * budgetSeconds) break;
			Expression body = tocheck.get(v);
			BitSet targets = PlaceProjection.support(body);
			ProjectionCegar cegar = new ProjectionCegar(spn, cover);
			boolean done = false;
			long propDeadline = System.currentTimeMillis() + 1000L * perProperty;
			while (!done && System.currentTimeMillis() < propDeadline) {
				final int bound = maxSeen.get(v);
				if (maxStruct.get(v) >= 0 && bound >= maxStruct.get(v)) break; // already tight, handled elsewhere
				ProjectionCegar.AbstractChecker checker = (proj, budget) -> checkInvariant(reader, proj, body, bound, budget);
				int left = (int) Math.max(1, (propDeadline - System.currentTimeMillis()) / 1000);
				ProjectionCegar.Outcome out = cegar.run(targets, checker, left);
				switch (out.status) {
				case PROVED: {
					Property prop = spn.getProperties().get(v);
					doneProps.put(prop.getName(), bound, "TOPOLOGICAL PLACE_PROJECTION EXHAUSTIVE");
					System.out.println("Place projection proved bound " + bound + " for " + prop.getName() + " after "
							+ out.refinements + " refinements.");
					tocheck.remove(v);
					spn.getProperties().remove(v);
					maxSeen.remove(v);
					maxStruct.remove(v);
					closed++;
					done = true;
					break;
				}
				case REFUTED: {
					int value = body.eval(out.concreteWitness);
					if (value > bound) {
						System.out.println("Place projection found a real marking with value " + value + " (was " + bound + ").");
						maxSeen.set(v, value);
					} else {
						done = true; // inconsistent replay, do not loop
					}
					break;
				}
				default:
					done = true;
				}
			}
		}
		if (DEBUG >= 1) System.out.println("Place projection closed " + closed + " bounds in " + (System.currentTimeMillis() - t0) + " ms.");
		return closed;
	}

	/** Exhaustive check of "AG body <= bound" on the projection, then a walk for a witness when it fails. */
	private static ProjectionCegar.Answer checkInvariant(MccTranslator reader, PlaceProjection proj, Expression body,
			int bound, int budgetSeconds) {
		Expression abody = proj.translate(body);
		SparsePetriNet abs = proj.getNet();
		abs.getProperties().clear();
		abs.getProperties().add(new Property(Expression.nop(Op.AG, Expression.nop(Op.LEQ, abody, Expression.constant(bound))),
				PropertyType.INVARIANT, "prop0"));
		MccTranslator sub = reader.copy();
		sub.setSpn(abs, false);
		DoneProperties local = new ConcurrentHashDoneProperties();
		int budget = Math.max(2, budgetSeconds / 2);
		if (sub.isDoITS()) {
			ExhaustiveEngines.verifyWithSDD(sub, local, "ReachabilityCardinality", budget);
		} else {
			try {
				ReachabilitySolver.applyReductions(sub, local, budget);
			} catch (GlobalPropertySolvedException e) {
				// nothing global to conclude here
			}
		}
		Boolean verdict = local.getValue("prop0");
		if (verdict == null) return ProjectionCegar.Answer.UNKNOWN;
		if (verdict) return ProjectionCegar.Answer.PROVED;
		// refuted on the projection: look for an abstract witness with a trace
		int[] witness = null;
		PetriSpotWalker.Verdicts psv = PetriSpotWalker.runReachability(proj.getNet(),
				List.of(Expression.nop(Op.GT, abody, Expression.constant(bound))), null, 1000000, 1, Math.max(2, budget / 2), true);
		if (psv != null && psv.found[0] != 0) witness = psv.traces[0];
		return new ProjectionCegar.Answer(ProjectionCegar.Status.REFUTED, witness);
	}
}
