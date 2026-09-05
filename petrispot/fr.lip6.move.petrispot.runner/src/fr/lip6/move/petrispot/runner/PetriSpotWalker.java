package fr.lip6.move.petrispot.runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.util.SparseIntArray;
import fr.lip6.move.gal.process.CommandLine;
import fr.lip6.move.gal.process.Runner;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.expr.Expression;
import fr.lip6.petrispot.binaries.BinaryToolsPlugin;

/**
 * Explicit reachability walks delegated to the PetriSpot binary, the
 * replacement for the Java {@code RandomExplorer} (PetriSpot INTEROP.md).
 *
 * <p>One request is one process: the net is written as PNET, the predicates
 * as s-expressions, the binary runs with the given budgets, and its stdout
 * is read as it comes so that verdicts already printed survive a kill on
 * timeout. Every method returns {@code null} when PetriSpot could not be used
 * (binary missing, predicate outside the supported fragment, I/O failure),
 * and the caller falls back to the Java walker.
 */
public class PetriSpotWalker {

	/**
	 * Compile-time switch: true routes the explicit walks of the pnmcc solvers
	 * to PetriSpot, false keeps the Java RandomExplorer everywhere.
	 */
	public static final boolean USE_PETRISPOT = true;

	/** Walkers run in parallel by the binary (the MCC core count). */
	public static final int THREADS = 4;

	/** 1 keeps the exchanged files, 2 also echoes the binary's output. */
	private static final int DEBUG = 0;

	/** Seconds added to the binary's own budget before it is killed. */
	private static final int GRACE_SECONDS = 5;

	/**
	 * A walk in flight, so a caller that no longer needs it can stop it. The
	 * verdicts printed before the kill are kept: stdout is parsed as it
	 * arrives.
	 */
	public static final class Cancel {
		private volatile Process process;
		private volatile boolean cancelled;

		public void cancel() {
			cancelled = true;
			Process p = process;
			if (p != null) {
				p.destroyForcibly();
			}
		}

		private void attach(Process p) {
			process = p;
			if (cancelled) {
				p.destroyForcibly();
			}
		}
	}

	/**
	 * Verdicts of one request: found is 1 when a witness was found (or the
	 * known bound was reached), with the MCC technique words; for bound
	 * requests, max is the largest value of each expression seen.
	 */
	public static class Verdicts {
		public final int[] found;
		public final String[] techniques;
		public final long[] max;
		/** With traces requested: the transitions fired from the initial marking to the witness, per property (null when none). */
		public final int[][] traces;

		Verdicts(int n) {
			found = new int[n];
			techniques = new String[n];
			max = new long[n];
			traces = new int[n][];
			java.util.Arrays.fill(max, Long.MIN_VALUE);
		}
	}

	/**
	 * Receives each verdict line the moment PetriSpot prints it, on the reader
	 * thread, so a caller can publish it before the walk is over. The Verdicts
	 * returned at the end carry the same information; a listener that throws
	 * is dropped and the caller reads the Verdicts as usual.
	 */
	public interface Listener {
		/** FORMULA prop&lt;index&gt; value TECHNIQUES words : a witness, or a known bound reached (value is then the bound). */
		default void formula(int index, String value, String techniques) {
		}

		/** BOUND prop&lt;index&gt; max : the largest value seen so far for a bound request. */
		default void bound(int index, long max) {
		}
	}

	/**
	 * Look for a witness of each predicate: a random sweep over all of them for
	 * up to sweepSeconds, then focused heuristic walks until totalSeconds.
	 * Each walk fires at most steps transitions.
	 *
	 * @return one verdict per predicate, or null if PetriSpot could not run
	 */
	public static Verdicts runReachability(ISparsePetriNet net, List<Expression> predicates, long steps,
			int sweepSeconds, int totalSeconds) {
		return runReachability(net, predicates, null, steps, sweepSeconds, totalSeconds);
	}

	/** Same, publishing each verdict to the listener as it arrives. */
	public static Verdicts runReachability(ISparsePetriNet net, List<Expression> predicates, long steps,
			int sweepSeconds, int totalSeconds, Listener listener) {
		return runReachability(net, predicates, null, steps, sweepSeconds, totalSeconds, false, listener);
	}

	/**
	 * Same, with an optional Parikh vector per predicate (null entries allowed,
	 * or a null list): the hinted predicates are walked with the parikh
	 * strategy in their focused rounds.
	 */
	public static Verdicts runReachability(ISparsePetriNet net, List<Expression> predicates,
			List<SparseIntArray> parikhs, long steps, int sweepSeconds, int totalSeconds) {
		return runReachability(net, predicates, parikhs, steps, sweepSeconds, totalSeconds, false);
	}

	/** Same; withTrace asks for the witness traces (Verdicts.traces), off the fast path otherwise. */
	public static Verdicts runReachability(ISparsePetriNet net, List<Expression> predicates,
			List<SparseIntArray> parikhs, long steps, int sweepSeconds, int totalSeconds, boolean withTrace) {
		return runReachability(net, predicates, parikhs, steps, sweepSeconds, totalSeconds, withTrace, null);
	}

	/** Same, publishing each verdict to the listener as it arrives. */
	public static Verdicts runReachability(ISparsePetriNet net, List<Expression> predicates,
			List<SparseIntArray> parikhs, long steps, int sweepSeconds, int totalSeconds, boolean withTrace,
			Listener listener) {
		if (predicates.isEmpty()) {
			return new Verdicts(0);
		}
		List<String> forms = new ArrayList<>(predicates.size());
		try {
			for (int i = 0; i < predicates.size(); i++) {
				forms.add(SexprPropertyPrinter.reach("prop" + i, predicates.get(i)));
			}
		} catch (UnsupportedOperationException e) {
			System.out.println("PetriSpot walker skipped: " + e.getMessage());
			return null;
		}
		List<String> args = new ArrayList<>();
		args.add("--walkSteps=" + steps);
		args.add("--sweepTime=" + sweepSeconds);
		args.add("--totalTime=" + totalSeconds);
		// A round that ends on its step budget having found nothing stops the
		// driver otherwise, handing back the seconds it was given.
		args.add("--escalate");
		if (withTrace) args.add("--trace");
		return run(net, forms, hintForms(parikhs), args, totalSeconds, THREADS, null, listener);
	}

	/**
	 * A walk meant to run beside another solver: it takes the number of threads
	 * it may use and a handle the caller stops it with, and it escalates its
	 * step budget rather than conceding, since nothing else will use the time.
	 *
	 * @return one verdict per predicate, or null if PetriSpot could not run
	 */
	public static Verdicts runBeside(ISparsePetriNet net, List<Expression> predicates, long steps, int totalSeconds,
			int threads, Cancel cancel) {
		return runBeside(net, predicates, steps, totalSeconds, threads, cancel, null);
	}

	/** Same, publishing each verdict to the listener as it arrives. */
	public static Verdicts runBeside(ISparsePetriNet net, List<Expression> predicates, long steps, int totalSeconds,
			int threads, Cancel cancel, Listener listener) {
		if (predicates.isEmpty()) {
			return new Verdicts(0);
		}
		List<String> forms = new ArrayList<>(predicates.size());
		try {
			for (int i = 0; i < predicates.size(); i++) {
				forms.add(SexprPropertyPrinter.reach("prop" + i, predicates.get(i)));
			}
		} catch (UnsupportedOperationException e) {
			System.out.println("PetriSpot walker skipped: " + e.getMessage());
			return null;
		}
		List<String> args = new ArrayList<>();
		args.add("--walkSteps=" + steps);
		args.add("--sweepTime=" + Math.min(10, totalSeconds));
		args.add("--totalTime=" + totalSeconds);
		args.add("--escalate");
		return run(net, forms, null, args, totalSeconds, threads, cancel, listener);
	}

	/** The (parikh prop<i> ...) forms of a list of vectors, or null when there is none. */
	private static List<String> hintForms(List<SparseIntArray> parikhs) {
		if (parikhs == null) {
			return null;
		}
		List<String> hints = new ArrayList<>();
		for (int i = 0; i < parikhs.size(); i++) {
			SparseIntArray p = parikhs.get(i);
			if (p == null) continue;
			String form = SexprPropertyPrinter.parikh("prop" + i, p);
			if (form != null) hints.add(form);
		}
		return hints.isEmpty() ? null : hints;
	}

	/**
	 * Maximise each expression (a weighted sum of places): a random sweep over
	 * all of them for up to sweepSeconds, then focused best-first climbs until
	 * totalSeconds, each walk firing at most steps transitions. knownBounds[i]
	 * is a structural upper bound of expression i, or -1 when unknown; reaching
	 * it ends that expression early.
	 *
	 * @return the largest values seen (Verdicts.max), or null if PetriSpot could not run
	 */
	public static Verdicts runBounds(ISparsePetriNet net, List<Expression> expressions, List<Integer> knownBounds,
			long steps, int sweepSeconds, int totalSeconds) {
		return runBounds(net, expressions, knownBounds, null, steps, sweepSeconds, totalSeconds);
	}

	/** Same, with an optional Parikh vector per expression (see runReachability). */
	public static Verdicts runBounds(ISparsePetriNet net, List<Expression> expressions, List<Integer> knownBounds,
			List<SparseIntArray> parikhs, long steps, int sweepSeconds, int totalSeconds) {
		return runBounds(net, expressions, knownBounds, parikhs, steps, sweepSeconds, totalSeconds, null);
	}

	/** Same, publishing each value to the listener as it arrives. */
	public static Verdicts runBounds(ISparsePetriNet net, List<Expression> expressions, List<Integer> knownBounds,
			List<SparseIntArray> parikhs, long steps, int sweepSeconds, int totalSeconds, Listener listener) {
		if (expressions.isEmpty()) {
			return new Verdicts(0);
		}
		List<String> forms = new ArrayList<>(expressions.size());
		try {
			for (int i = 0; i < expressions.size(); i++) {
				int k = knownBounds.get(i);
				forms.add(SexprPropertyPrinter.bound("prop" + i, expressions.get(i), k >= 0 ? k : -1));
			}
		} catch (UnsupportedOperationException e) {
			System.out.println("PetriSpot walker skipped: " + e.getMessage());
			return null;
		}
		List<String> args = new ArrayList<>();
		args.add("--walkSteps=" + steps);
		args.add("--sweepTime=" + sweepSeconds);
		args.add("--totalTime=" + totalSeconds);
		// a bound round that ends on its step budget buys steps, not a shorter run
		args.add("--escalate");
		Verdicts v = run(net, forms, hintForms(parikhs), args, totalSeconds, THREADS, null, listener);
		if (v != null) {
			for (int i = 0; i < v.max.length; i++) {
				if (v.max[i] == Long.MIN_VALUE) {
					System.out.println("PetriSpot walker reported no value for bound " + i);
					return null;
				}
			}
		}
		return v;
	}

	/**
	 * Look for a deadlock with random walks of at most steps transitions each,
	 * for up to timeoutSeconds.
	 *
	 * @return TRUE if a deadlock was reached, FALSE if none was, null if PetriSpot could not run
	 */
	public static Boolean runDeadlock(ISparsePetriNet net, long steps, int timeoutSeconds) {
		return runDeadlock(net, null, steps, timeoutSeconds);
	}

	/** Same, guided by a Parikh vector when one is given. */
	public static Boolean runDeadlock(ISparsePetriNet net, SparseIntArray parikh, long steps, int timeoutSeconds) {
		List<String> args = new ArrayList<>();
		args.add("--walkSteps=" + steps);
		args.add("-t");
		args.add(Integer.toString(timeoutSeconds));
		// Without a total budget the driver walks one round and concludes; with
		// --escalate a round that ended on its step budget buys more steps
		// rather than giving the remaining seconds back.
		args.add("--totalTime=" + timeoutSeconds);
		args.add("--escalate");
		List<String> hints = parikh == null ? null : hintForms(List.of(parikh));
		Verdicts v = run(net, List.of(SexprPropertyPrinter.deadlock("prop0")), hints, args, timeoutSeconds);
		if (v == null) {
			return null;
		}
		return v.found[0] != 0;
	}

	private static Verdicts run(ISparsePetriNet net, List<String> forms, List<String> hints, List<String> args,
			int budgetSeconds) {
		return run(net, forms, hints, args, budgetSeconds, THREADS, null, null);
	}

	/** Same, on a chosen number of threads, with a handle to stop it early and a listener fed as lines arrive. */
	static Verdicts run(ISparsePetriNet net, List<String> forms, List<String> hints, List<String> args,
			int budgetSeconds, int threads, Cancel cancel, Listener listener) {
		long t0 = System.currentTimeMillis();
		Verdicts verdicts = new Verdicts(forms.size());
		List<File> todel = new ArrayList<>();
		try {
			File netFile = Files.createTempFile("petrispot-net-", ".pnet").toFile();
			File propFile = Files.createTempFile("petrispot-props-", ".sexpr").toFile();
			todel.add(netFile);
			todel.add(propFile);
			PNETFormatIO.write(net, netFile.toPath());
			Files.write(propFile.toPath(), forms, StandardCharsets.UTF_8);

			CommandLine cl = new CommandLine();
			cl.addArg(binaryPath());
			cl.addArg("--net=" + netFile.getCanonicalPath());
			cl.addArg("--props=" + propFile.getCanonicalPath());
			if (hints != null) {
				File hintFile = Files.createTempFile("petrispot-hints-", ".sexpr").toFile();
				todel.add(hintFile);
				Files.write(hintFile.toPath(), hints, StandardCharsets.UTF_8);
				cl.addArg("--hints=" + hintFile.getCanonicalPath());
			}
			cl.addArg("--threads=" + threads);
			cl.addArg("-q");
			for (String a : args) {
				cl.addArg(a);
			}
			if (DEBUG >= 1) System.out.println("Running PetriSpot walker: " + cl);

			ProcessBuilder pb = new ProcessBuilder(cl.getArgs());
			pb.redirectError(Redirect.INHERIT);
			Process process = pb.start();
			if (cancel != null) {
				cancel.attach(process);
			}
			Thread reader = new Thread(() -> readVerdicts(process, verdicts, listener), "petrispot-stdout");
			reader.start();
			int exitCode = -1;
			try {
				exitCode = Runner.waitForOrTimeout(budgetSeconds + GRACE_SECONDS, TimeUnit.SECONDS, cl, process);
			} catch (TimeoutException e) {
				System.out.println("PetriSpot walker killed after " + (budgetSeconds + GRACE_SECONDS) + " s.");
			}
			reader.join();
			int seen = 0;
			for (int f : verdicts.found) seen += f;
			int bounds = 0;
			for (long m : verdicts.max) if (m != Long.MIN_VALUE) bounds++;
			System.out.println("PetriSpot walker: " + seen + "/" + forms.size() + " properties solved"
					+ (bounds > 0 ? ", " + bounds + " bounds reported" : "") + " in "
					+ (System.currentTimeMillis() - t0) + " ms (exit " + exitCode + ").");
			if (exitCode != 0 && seen == 0 && bounds == 0) {
				return null;
			}
			return verdicts;
		} catch (IOException e) {
			System.out.println("PetriSpot walker I/O error: " + e.getMessage());
			return null;
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			System.out.println("PetriSpot walker interrupted.");
			return null;
		} finally {
			if (DEBUG == 0)
				for (File f : todel)
					f.delete();
		}
	}

	/** The bundled binary, or the one named by the system property petrispot.bin (tests outside OSGi). */
	private static String binaryPath() throws IOException {
		String override = System.getProperty("petrispot.bin");
		if (override != null && !override.isEmpty()) {
			return override;
		}
		return BinaryToolsPlugin.getPetriURI().getPath();
	}

	/**
	 * Consume stdout to its end, recording every FORMULA and BOUND line as it
	 * arrives and handing it to the listener, if any. A listener that throws
	 * is not called again: the caller reads the Verdicts when the run ends.
	 */
	private static void readVerdicts(Process process, Verdicts verdicts, Listener listener) {
		try (BufferedReader in = new BufferedReader(
				new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
			String line;
			while ((line = in.readLine()) != null) {
				if (DEBUG >= 2) System.out.println("[petrispot] " + line);
				boolean formula = line.startsWith("FORMULA prop");
				boolean witness = line.startsWith("WITNESS prop");
				if (!formula && !witness && !line.startsWith("BOUND prop")) continue;
				// FORMULA prop<i> TRUE|FALSE|<k> TECHNIQUES <words>  |  BOUND prop<i> <max>  |  WITNESS prop<i> <k> t3 t9 ...
				String[] words = line.split(" ", witness ? 4 : 5);
				if (words.length < 3) continue;
				int index;
				try {
					index = Integer.parseInt(words[1].substring(4));
				} catch (NumberFormatException e) {
					continue;
				}
				if (index < 0 || index >= verdicts.found.length) continue;
				if (formula) {
					// every request asks for a witness, so a verdict means one was found
					verdicts.found[index] = 1;
					verdicts.techniques[index] = words.length == 5 ? words[4] : "EXPLICIT";
					if (listener != null) {
						try {
							listener.formula(index, words[2], verdicts.techniques[index]);
						} catch (RuntimeException e) {
							System.out.println("PetriSpot walker listener dropped: " + e.getMessage());
							listener = null;
						}
					}
				} else if (witness) {
					// transitions are named t<i> on the PNET path
					String[] ts = words.length == 4 ? words[3].trim().split(" ") : new String[0];
					int[] trace = new int[ts.length];
					int n = 0;
					for (String t : ts) {
						if (t.length() > 1 && t.charAt(0) == 't') {
							try {
								trace[n++] = Integer.parseInt(t.substring(1));
							} catch (NumberFormatException e) {
								// not an index: unusable trace
								n = -1;
								break;
							}
						}
					}
					if (n >= 0) verdicts.traces[index] = java.util.Arrays.copyOf(trace, n);
				} else {
					try {
						verdicts.max[index] = Math.max(verdicts.max[index], Long.parseLong(words[2]));
					} catch (NumberFormatException e) {
						// malformed line: ignore
						continue;
					}
					if (listener != null) {
						try {
							listener.bound(index, verdicts.max[index]);
						} catch (RuntimeException e) {
							System.out.println("PetriSpot walker listener dropped: " + e.getMessage());
							listener = null;
						}
					}
				}
			}
		} catch (IOException e) {
			// stream closed by a kill: keep what was read
		}
	}
}
