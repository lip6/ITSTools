package fr.lip6.move.gal.application.runner;

import java.util.ArrayList;
import java.util.List;

import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.gal.structural.expr.Op;
import fr.lip6.move.hsc.runner.HscRunner;

/**
 * The libHSC engine as a runner of the portfolio: the reduced net and its
 * open reachability, deadlock and bound properties go to hsc-pn (symbolic,
 * saturation), every verdict is a proof and lands in DoneProperties as it is
 * printed. CTL and LTL properties are left to the other engines.
 */
public class HscSolverRunner extends AbstractRunner {

	private static final String TECHNIQUES = "DECISION_DIAGRAMS SATURATION HSC";
	private final SparsePetriNet net;
	private final int timeout;
	private final HscRunner.Shape shape;
	private final boolean force;

	public HscSolverRunner(SparsePetriNet net, int timeout) {
		this(net, timeout, HscRunner.Shape.LOUVAIN, true);
	}

	public HscSolverRunner(SparsePetriNet net, int timeout, HscRunner.Shape shape, boolean force) {
		this.net = net;
		this.timeout = timeout;
		this.shape = shape;
		this.force = force;
	}

	@Override
	public void solve(Ender ender) {
		runnerThread = new Thread(this::run, "hsc-runner");
		runnerThread.start();
		this.ender = ender;
	}

	private Ender ender;

	private void run() {
		List<Property> reach = new ArrayList<>();
		List<Expression> predicates = new ArrayList<>();
		List<Property> bounds = new ArrayList<>();
		Property deadlock = null;
		for (Property p : net.getProperties()) {
			if (doneProps.containsKey(p.getName())) continue;
			if (p.getBody().getOp() == Op.BOOLCONST) {
				// settled by the simplifications on the initial marking (testInInitial)
				doneProps.put(p.getName(), p.getBody().getValue() == 1, "TOPOLOGICAL INITIAL_STATE");
				continue;
			}
			switch (p.getType()) {
			case INVARIANT: {
				Expression body = p.getBody();
				if (body.getOp() == Op.EF) {
					predicates.add(body.childAt(0));
				} else if (body.getOp() == Op.AG) {
					predicates.add(Expression.not(body.childAt(0)));
				} else {
					continue;
				}
				reach.add(p);
				break;
			}
			case DEADLOCK:
				deadlock = p;
				break;
			case BOUNDS:
				bounds.add(p);
				break;
			default:
				break;
			}
		}
		try {
			if (!reach.isEmpty()) {
				HscRunner.runReachability(net, predicates, timeout, shape, force, (index, value, techniques) -> {
					Property p = reach.get(index);
					// the predicate is what EF looks for; AG holds iff it is unreachable
					boolean reached = "TRUE".equals(value);
					doneProps.put(p.getName(), (p.getBody().getOp() == Op.EF) == reached, TECHNIQUES);
				});
			}
			if (deadlock != null && !doneProps.containsKey(deadlock.getName())) {
				Boolean dead = HscRunner.runDeadlock(net, timeout, shape, force);
				if (dead != null) doneProps.put(deadlock.getName(), dead, TECHNIQUES);
			}
			if (!bounds.isEmpty()) {
				List<Expression> forms = new ArrayList<>(bounds.size());
				for (Property p : bounds) forms.add(p.getBody());
				long[] max = HscRunner.runBounds(net, forms, timeout, shape, force);
				for (int i = 0; i < bounds.size(); i++) {
					if (max[i] >= 0) doneProps.put(bounds.get(i).getName(), (int) max[i], TECHNIQUES);
				}
			}
		} catch (RuntimeException e) {
			System.out.println("HSC runner aborted: " + e.getMessage());
		}
		if (doneProps.isFinished() && ender != null) {
			ender.killAll();
		}
	}
}
