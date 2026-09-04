package fr.lip6.move.gal.application.solver.abstraction;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.SparseIntArray;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.expr.BinOp;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.NaryOp;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.gal.structural.expr.VarRef;
import fr.lip6.move.gal.util.IntMatrixCol;

/**
 * Projection of a P/T net onto a set of kept places (ABSTRACTION.md): the
 * other places disappear with their arcs, transitions left without arcs are
 * dropped, transitions that became identical are merged. The result
 * over-approximates the reachable markings of the kept places. The map from
 * abstract transitions to their concrete members serves to replay abstract
 * traces on the concrete net.
 */
public class PlaceProjection {

	private final ISparsePetriNet concrete;
	private final BitSet kept;
	private final int[] concreteToAbstract; // place index, or -1 when dropped
	private final List<Integer> abstractToConcretePlace = new ArrayList<>();
	private final List<List<Integer>> abstractToConcreteTransitions = new ArrayList<>();
	private final SparsePetriNet net;

	public PlaceProjection(ISparsePetriNet concrete, BitSet kept) {
		this.concrete = concrete;
		this.kept = (BitSet) kept.clone();
		concreteToAbstract = new int[concrete.getPlaceCount()];
		net = new SparsePetriNet();
		for (int p = 0; p < concrete.getPlaceCount(); p++) {
			if (kept.get(p)) {
				concreteToAbstract[p] = net.addPlace(concrete.getPnames().get(p), concrete.getMarks().get(p));
				abstractToConcretePlace.add(p);
			} else {
				concreteToAbstract[p] = -1;
			}
		}
		Map<String, Integer> merged = new HashMap<>();
		IntMatrixCol pt = concrete.getFlowPT();
		IntMatrixCol tp = concrete.getFlowTP();
		for (int t = 0; t < concrete.getTransitionCount(); t++) {
			SparseIntArray pre = project(pt.getColumn(t));
			SparseIntArray post = project(tp.getColumn(t));
			if (pre.size() == 0 && post.size() == 0) continue;
			String key = pre.toString() + "->" + post.toString();
			Integer at = merged.get(key);
			if (at == null) {
				at = net.addTransition(concrete.getTnames().get(t));
				for (int i = 0; i < pre.size(); i++) net.addPreArc(pre.keyAt(i), at, pre.valueAt(i));
				for (int i = 0; i < post.size(); i++) net.addPostArc(post.keyAt(i), at, post.valueAt(i));
				merged.put(key, at);
				abstractToConcreteTransitions.add(new ArrayList<>());
			}
			abstractToConcreteTransitions.get(at).add(t);
		}
	}

	private SparseIntArray project(SparseIntArray col) {
		SparseIntArray out = new SparseIntArray();
		for (int i = 0; i < col.size(); i++) {
			int a = concreteToAbstract[col.keyAt(i)];
			if (a >= 0) out.append(a, col.valueAt(i));
		}
		return out;
	}

	/** The projected net: kept places renumbered, merged transitions. */
	public SparsePetriNet getNet() {
		return net;
	}

	public BitSet getKept() {
		return kept;
	}

	public int abstractPlace(int concretePlace) {
		return concreteToAbstract[concretePlace];
	}

	public int concretePlace(int abstractPlace) {
		return abstractToConcretePlace.get(abstractPlace);
	}

	public List<Integer> concreteTransitions(int abstractTransition) {
		return abstractToConcreteTransitions.get(abstractTransition);
	}

	/** Rewrite an expression over concrete places into one over abstract places; every place must be kept. */
	public Expression translate(Expression e) {
		if (e == null) return null;
		if (e instanceof VarRef) {
			int a = concreteToAbstract[e.getValue()];
			if (a < 0) throw new IllegalArgumentException("Expression mentions a dropped place " + e.getValue());
			return Expression.var(a);
		} else if (e instanceof BinOp b) {
			return Expression.op(b.getOp(), translate(b.left), translate(b.right));
		} else if (e instanceof NaryOp n) {
			List<Expression> kids = new ArrayList<>();
			for (int i = 0; i < n.nbChildren(); i++) kids.add(translate(n.childAt(i)));
			return Expression.nop(n.getOp(), kids);
		}
		// constants and the like: no place inside
		return e;
	}

	/** Places of an expression, as concrete indices. */
	public static BitSet support(Expression e) {
		BitSet s = new BitSet();
		collect(e, s);
		return s;
	}

	private static void collect(Expression e, BitSet s) {
		if (e == null) return;
		if (e.getOp() == Op.PLACEREF) {
			s.set(e.getValue());
			return;
		}
		for (int i = 0; i < e.nbChildren(); i++) collect(e.childAt(i), s);
	}

	/**
	 * Replay an abstract trace on the concrete net, choosing for each abstract
	 * transition any enabled concrete member.
	 *
	 * @return the concrete marking reached when the whole trace is fireable
	 *         (a real behaviour), or null when it is blocked; then
	 *         blockedPlaces receives the dropped places that were missing at
	 *         the blocking step
	 */
	public SparseIntArray replay(int[] abstractTrace, BitSet blockedPlaces) {
		SparseIntArray state = new SparseIntArray(concrete.getMarks());
		IntMatrixCol pt = concrete.getFlowPT();
		IntMatrixCol tp = concrete.getFlowTP();
		for (int at : abstractTrace) {
			int fired = -1;
			for (int t : abstractToConcreteTransitions.get(at)) {
				if (enabled(state, pt.getColumn(t))) {
					fired = t;
					break;
				}
			}
			if (fired < 0) {
				for (int t : abstractToConcreteTransitions.get(at)) {
					SparseIntArray pre = pt.getColumn(t);
					for (int i = 0; i < pre.size(); i++) {
						int p = pre.keyAt(i);
						if (!kept.get(p) && state.get(p) < pre.valueAt(i)) blockedPlaces.set(p);
					}
				}
				return null;
			}
			state = SparseIntArray.sumProd(1, state, -1, pt.getColumn(fired));
			state = SparseIntArray.sumProd(1, state, 1, tp.getColumn(fired));
		}
		return state;
	}

	private static boolean enabled(SparseIntArray state, SparseIntArray pre) {
		for (int i = 0; i < pre.size(); i++) {
			if (state.get(pre.keyAt(i)) < pre.valueAt(i)) return false;
		}
		return true;
	}
}
