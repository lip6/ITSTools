package fr.lip6.move.gal.application.solver;

import java.util.ArrayList;
import java.util.List;

import fr.lip6.move.gal.application.solver.global.GlobalPropertySolverException;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.structural.Property;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralReduction;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.move.petrispot.runner.PetriSpotWalker;
import fr.lip6.move.hsc.runner.HscRunner;

/**
 * A PetriSpot walk running beside a decision diagram attempt.
 *
 * The diagram engines are single threaded, so a run that has handed its model
 * to them leaves the other cores idle for as long as they take. On the MCC 2026
 * campaign that idle tail is half of a failed Liveness run and 41 % of a failed
 * QuasiLiveness one. This walks on those cores, on the same net and the same
 * open properties, and publishes what it finds into the shared DoneProperties,
 * so the diagram attempt may end early for the right reason.
 *
 * The walk is stopped as soon as the attempt it accompanies is over; the
 * verdicts it printed before that are kept.
 *
 * <p>CTL properties (the attempts of the CTL examinations) go to PetriSpot's
 * explicit CTL checker instead of the reachability walk: it answers a formula
 * when a witness or a counter-example of bounded size exists, and the
 * verdict is the formula's truth.
 */
public class ParallelWalk {

	/** Whether a walk accompanies every decision diagram attempt. */
	public static final boolean ENABLED = true;

	/**
	 * Whether libHSC's symbolic CTL checker (hsc-pn) also runs beside a CTL
	 * attempt, on the same reduced net: set by the -hsc flag.
	 */
	public static boolean HSC_CTL = false;

	/** Cores left to the walk. The diagram engine uses one of the four. */
	private static final int THREADS = 3;

	/** Steps of a single walk before the driver escalates. */
	private static final long STEPS = 1000000;

	private final Thread thread;
	private final PetriSpotWalker.Cancel cancel;

	private ParallelWalk(Thread thread, PetriSpotWalker.Cancel cancel) {
		this.thread = thread;
		this.cancel = cancel;
	}

	/**
	 * Walk the properties of spn that are still open, for at most seconds.
	 *
	 * @return the walk, or null when there is nothing to walk on, no way to
	 *         state it as a target, or the feature is off
	 */
	public static ParallelWalk start(SparsePetriNet spn, DoneProperties doneProps, int seconds) {
		if (!ENABLED || !PetriSpotWalker.USE_PETRISPOT || seconds <= 0 || spn == null) {
			return null;
		}
		List<Property> props = new ArrayList<>(spn.getProperties());
		props.removeIf(p -> doneProps.containsKey(p.getName()));
		if (props.isEmpty()) {
			return null;
		}
		if (props.stream().allMatch(p -> p.getType() == PropertyType.CTL)) {
			return startCtl(spn, props, doneProps, seconds);
		}
		List<Integer> indexes = new ArrayList<>();
		List<Expression> tocheck = new ArrayList<>();
		ReachabilitySolver.computeToCheck(props, indexes, tocheck);
		if (tocheck.size() != props.size()) {
			// a property we cannot hand to a walker; leave the cores idle rather
			// than answer the wrong question
			return null;
		}
		StructuralReduction sr = new StructuralReduction(spn);
		PetriSpotWalker.Cancel cancel = new PetriSpotWalker.Cancel();
		Thread thread = new Thread(() -> {
			try {
				PetriSpotWalker.Verdicts v = PetriSpotWalker.runBeside(sr, tocheck, STEPS, seconds, THREADS, cancel,
						ReachabilitySolver.streamTo(props, doneProps));
				if (v != null) {
					int seen = ReachabilitySolver.interpretWalkerVerdict(tocheck, props, doneProps, v.found,
							v.techniques);
					if (seen > 0) {
						System.out.println("Walk beside the decision diagrams solved " + seen + " properties.");
					}
				}
			} catch (RuntimeException e) {
				System.out.println("Walk beside the decision diagrams failed : " + e.getMessage());
			}
		}, "petrispot-beside-dd");
		thread.setDaemon(true);
		thread.start();
		return new ParallelWalk(thread, cancel);
	}

	/** The explicit CTL checker beside a CTL attempt; verdicts published as they arrive. */
	private static ParallelWalk startCtl(SparsePetriNet spn, List<Property> props, DoneProperties doneProps,
			int seconds) {
		List<Expression> formulas = new ArrayList<>();
		for (Property p : props) {
			formulas.add(p.getBody());
		}
		StructuralReduction sr = new StructuralReduction(spn);
		PetriSpotWalker.Cancel cancel = new PetriSpotWalker.Cancel();
		PetriSpotWalker.Listener listener = new PetriSpotWalker.Listener() {
			@Override
			public void formula(int index, String value, String techniques) {
				try {
					doneProps.put(props.get(index).getName(), "TRUE".equals(value),
							ReachabilitySolver.walkTechniques(techniques));
				} catch (GlobalPropertySolverException e) {
					// the verdict decided the whole examination (Liveness...): the printer has said so, nothing to add
				}
			}
		};
		Thread thread = new Thread(() -> {
			try {
				PetriSpotWalker.Verdicts v = PetriSpotWalker.runCtl(sr, formulas, seconds, THREADS, cancel, listener);
				if (v != null) {
					int seen = 0;
					for (int i = 0; i < v.found.length; i++) {
						if (v.found[i] != 0) {
							doneProps.put(props.get(i).getName(), v.found[i] == PetriSpotWalker.Verdicts.WITNESS,
									ReachabilitySolver.walkTechniques(v.techniques[i]));
							seen++;
						}
					}
					if (seen > 0) {
						System.out.println("CTL check beside the decision diagrams solved " + seen + " properties.");
					}
				}
			} catch (GlobalPropertySolverException e) {
				System.out.println("CTL check beside the decision diagrams decided the examination : " + e.getMessage());
			} catch (RuntimeException e) {
				System.out.println("CTL check beside the decision diagrams failed : " + e.getMessage());
			}
		}, "petrispot-ctl-beside-dd");
		thread.setDaemon(true);
		thread.start();
		if (HSC_CTL) {
			startHscCtl(sr, props, formulas, doneProps, seconds);
		}
		return new ParallelWalk(thread, cancel);
	}

	/**
	 * libHSC's symbolic CTL checker beside a CTL attempt (-hsc): the same
	 * reduced net and formulas go to hsc-pn, whose verdicts are proofs and
	 * land in doneProps as they stream. Bounded by seconds like the walk; not
	 * cancelled with it, the process ends with its budget.
	 */
	private static void startHscCtl(StructuralReduction sr, List<Property> props, List<Expression> formulas,
			DoneProperties doneProps, int seconds) {
		HscRunner.Listener listener = (index, value, techniques) -> {
			try {
				doneProps.put(props.get(index).getName(), "TRUE".equals(value), "DECISION_DIAGRAMS SATURATION HSC");
			} catch (GlobalPropertySolverException e) {
				// the verdict decided the whole examination: the printer has said so
			}
		};
		Thread thread = new Thread(() -> {
			try {
				HscRunner.Verdicts v = HscRunner.runCtl(sr, formulas, seconds, HscRunner.Shape.LOUVAIN, true, listener);
				if (v != null && v.solved() > 0) {
					System.out.println("libHSC CTL check beside the decision diagrams solved " + v.solved() + " properties.");
				}
			} catch (RuntimeException e) {
				System.out.println("libHSC CTL check beside the decision diagrams failed : " + e.getMessage());
			}
		}, "hsc-ctl-beside-dd");
		thread.setDaemon(true);
		thread.start();
	}

	/** Stop the walk and wait for its verdicts to be published. */
	public void stop() {
		cancel.cancel();
		try {
			thread.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/** Stop a walk that may not have been started. */
	public static void stop(ParallelWalk walk) {
		if (walk != null) {
			walk.stop();
		}
	}
}
