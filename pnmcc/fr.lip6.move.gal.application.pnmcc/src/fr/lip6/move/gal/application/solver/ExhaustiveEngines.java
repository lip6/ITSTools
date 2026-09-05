package fr.lip6.move.gal.application.solver;

import java.io.IOException;

import fr.lip6.move.gal.application.mcc.MccTranslator;
import fr.lip6.move.gal.application.runner.Ender;
import fr.lip6.move.gal.application.runner.IRunner;
import fr.lip6.move.gal.application.runner.its.ITSRunner;
import fr.lip6.move.gal.application.runner.ltsmin.LTSminRunner;
import fr.lip6.move.gal.mcc.properties.DoneProperties;

/**
 * The exhaustive engines behind every examination: the decision diagrams
 * of ITSRunner, then LTSmin, on the properties still open in a
 * DoneProperties, with a PetriSpot walk beside them for the given budget.
 */
public class ExhaustiveEngines {

	private ExhaustiveEngines() {
	}

	/**
	 * Hand the model to the exhaustive engines, with a PetriSpot walk on the
	 * cores they leave idle: both are single threaded, and the walk publishes
	 * into the same DoneProperties, so either may end the attempt.
	 */
	public static void verifyWithSDD(MccTranslator reader, DoneProperties doneProps, String examinationForITS,
			int timeout) {
		ParallelWalk walk = ParallelWalk.start(reader.getSPN(), doneProps, timeout);
		try {
			runExhaustiveEngines(reader, doneProps, examinationForITS, timeout);
		} finally {
			ParallelWalk.stop(walk);
		}
	}

	/** The decision diagrams, then LTSmin, on whatever properties remain open. */
	private static void runExhaustiveEngines(MccTranslator reader, DoneProperties doneProps,
			String examinationForITS, int timeout) {
		long time = System.currentTimeMillis();
		boolean wasInterrupted = false;
		if (reader.isDoITS())
		try {
			for (int i=0; i < 2 ; i++) {
				reader.rebuildSpecification(doneProps);
				reader.getSpec().getProperties().removeIf(p -> doneProps.containsKey(p.getName()));
				
				if (i==0) {
					reader.setLouvain(false);
					reader.setOrder(null);				
					reader.flattenSpec(false);
				} else {
					reader.setLouvain(true);
					reader.setOrder(null);
					reader.flattenSpec(true);
				}
				final IRunner itsRunner = new ITSRunner(examinationForITS, reader, true, false, reader.getFolder(), timeout,
						null);
				try {
					// decompose + simplify as needed
					itsRunner.configure(reader.getSpec(), doneProps);
					itsRunner.solve(new Ender() {
						public void killAll() {
							itsRunner.interrupt();
						}
					});
					itsRunner.join();
				} catch (InterruptedException e) {
					System.out.println("ITS runner timed out or was interrupted.");					
					wasInterrupted = true;
				} catch (IOException e) {
					System.out.println("ITS runner failed with exception " + e.getMessage());
					e.printStackTrace();
					wasInterrupted = true;
				} finally {
					if (itsRunner != null) {
						itsRunner.interrupt();
						try {
							itsRunner.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				reader.getSPN().getProperties().removeIf(p->doneProps.containsKey(p.getName()));
				if (reader.getSPN().getProperties().isEmpty() || doneProps.isFinished()) {
					break;
				}
			}
		} catch (OutOfMemoryError e) {
			reader.setSpec(null);
			System.out.println("ITSRunner failed with out of memory error.");
		}
		
		if (doneProps.isFinished() || wasInterrupted || reader.getSPN().getProperties().isEmpty()) {
			return;
		}
		timeout -= (System.currentTimeMillis() - time) / 1000;
		if (timeout <= 0) return;
		//CTL is not for LTSmin
		if (reader.isDoLTSMin())
		if (! reader.getSPN().getProperties().isEmpty() && !examinationForITS.startsWith("CTL")) {
			LTSminRunner ltsminRunner = new LTSminRunner(false, false, timeout, reader.getSPN().isSafe());
			try {
				ltsminRunner.configure(null, doneProps);
				ltsminRunner.setNet(reader.getSPN());
				// ltsminRunner.setShouldRetry(false);
				
				ltsminRunner.solve(new Ender() {
					public void killAll() {
						ltsminRunner.interrupt();
					}
				});
				
				ltsminRunner.join(timeout*1000);
				ltsminRunner.interrupt();
				ltsminRunner.join();
			} catch (IOException | InterruptedException e) {
				System.out.println("LTSmin runner failed with exception " + e.getMessage());
				wasInterrupted = true;								
				
				e.printStackTrace();				
			} finally {
				ltsminRunner.interrupt();
				try {
					ltsminRunner.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}		
		reader.getSPN().getProperties().removeIf(p->doneProps.containsKey(p.getName()));
	}

}
