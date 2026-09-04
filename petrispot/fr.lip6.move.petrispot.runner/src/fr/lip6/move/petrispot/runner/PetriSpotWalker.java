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
	 * Verdicts of one request: found is 1 when a witness was found (or the
	 * known bound was reached), with the MCC technique words; for bound
	 * requests, max is the largest value of each expression seen.
	 */
	public static class Verdicts {
		public final int[] found;
		public final String[] techniques;
		public final long[] max;

		Verdicts(int n) {
			found = new int[n];
			techniques = new String[n];
			max = new long[n];
			java.util.Arrays.fill(max, Long.MIN_VALUE);
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
		return run(net, forms, args, totalSeconds);
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
		Verdicts v = run(net, forms, args, totalSeconds);
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
		List<String> args = new ArrayList<>();
		args.add("--walkSteps=" + steps);
		args.add("-t");
		args.add(Integer.toString(timeoutSeconds));
		Verdicts v = run(net, List.of(SexprPropertyPrinter.deadlock("prop0")), args, timeoutSeconds);
		if (v == null) {
			return null;
		}
		return v.found[0] != 0;
	}

	private static Verdicts run(ISparsePetriNet net, List<String> forms, List<String> args, int budgetSeconds) {
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
			cl.addArg("--threads=" + THREADS);
			cl.addArg("-q");
			for (String a : args) {
				cl.addArg(a);
			}
			if (DEBUG >= 1) System.out.println("Running PetriSpot walker: " + cl);

			ProcessBuilder pb = new ProcessBuilder(cl.getArgs());
			pb.redirectError(Redirect.INHERIT);
			Process process = pb.start();
			Thread reader = new Thread(() -> readVerdicts(process, verdicts), "petrispot-stdout");
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

	/** Consume stdout to its end, recording every FORMULA and BOUND line as it arrives. */
	private static void readVerdicts(Process process, Verdicts verdicts) {
		try (BufferedReader in = new BufferedReader(
				new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
			String line;
			while ((line = in.readLine()) != null) {
				if (DEBUG >= 2) System.out.println("[petrispot] " + line);
				boolean formula = line.startsWith("FORMULA prop");
				if (!formula && !line.startsWith("BOUND prop")) continue;
				// FORMULA prop<i> TRUE|FALSE|<k> TECHNIQUES <words>   or   BOUND prop<i> <max>
				String[] words = line.split(" ", 5);
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
				} else {
					try {
						verdicts.max[index] = Math.max(verdicts.max[index], Long.parseLong(words[2]));
					} catch (NumberFormatException e) {
						// malformed line: ignore
					}
				}
			}
		} catch (IOException e) {
			// stream closed by a kill: keep what was read
		}
	}
}
