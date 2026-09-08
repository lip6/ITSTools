package fr.lip6.move.hsc.runner;

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

import fr.lip6.hsc.binaries.BinaryToolsPlugin;
import fr.lip6.move.gal.interop.PNETFormatIO;
import fr.lip6.move.gal.interop.SexprPropertyPrinter;
import fr.lip6.move.gal.process.CommandLine;
import fr.lip6.move.gal.process.Runner;
import fr.lip6.move.gal.structural.ISparsePetriNet;
import fr.lip6.move.gal.structural.expr.Expression;

/**
 * Runs hsc-pn on a net and a list of properties written as s-expression forms
 * (INTEROP.md sections 3 to 5), and reads the FORMULA lines back as they are
 * printed. Every verdict is a proof: TRUE and FALSE are both final, a bound is
 * exact. A property still open when the budget ends stays UNKNOWN.
 */
public class HscRunner {

	/** 0 none, 1 keep the exchanged files and print the command, 2 also echo the binary's output. */
	public static int DEBUG = 0;
	/** Seconds granted beyond the budget before the process is killed. */
	private static final int GRACE_SECONDS = 5;

	/** The hierarchy hsc-pn builds over the net: the PNET path has no unit tree. */
	public enum Shape {
		FLAT("flat"), LOUVAIN("louvain");
		final String flag;
		Shape(String flag) { this.flag = flag; }
	}

	public static class Verdicts {
		/** Per property: null while unknown, "TRUE", "FALSE", or the bound value. */
		public final String[] value;
		public final String[] techniques;
		Verdicts(int n) {
			value = new String[n];
			techniques = new String[n];
		}
		public int solved() {
			int n = 0;
			for (String v : value) if (v != null) n++;
			return n;
		}
	}

	/** Receives each verdict the moment hsc-pn prints it, on the reader thread. */
	public interface Listener {
		void formula(int index, String value, String techniques);
	}

	/**
	 * Reachability of each predicate (EF): forms (reach prop&lt;i&gt; pred); the
	 * value is TRUE when some reachable marking satisfies it, FALSE otherwise.
	 */
	public static Verdicts runReachability(ISparsePetriNet net, List<Expression> predicates, int totalSeconds,
			Shape shape, boolean force, Listener listener) {
		List<String> forms = new ArrayList<>(predicates.size());
		for (int i = 0; i < predicates.size(); i++) {
			forms.add(SexprPropertyPrinter.reach("prop" + i, predicates.get(i)));
		}
		return run(net, forms, totalSeconds, shape, force, listener);
	}

	/** Deadlock: one form (deadlock prop0); TRUE when a dead marking is reachable. */
	public static Boolean runDeadlock(ISparsePetriNet net, int totalSeconds, Shape shape, boolean force) {
		Verdicts v = run(net, List.of(SexprPropertyPrinter.deadlock("prop0")), totalSeconds, shape, force, null);
		if (v == null || v.value[0] == null) return null;
		return "TRUE".equals(v.value[0]);
	}

	/** Exact maxima of the expressions over the reachable markings; -1 where unknown. */
	public static long[] runBounds(ISparsePetriNet net, List<Expression> expressions, int totalSeconds, Shape shape,
			boolean force) {
		List<String> forms = new ArrayList<>(expressions.size());
		for (int i = 0; i < expressions.size(); i++) {
			forms.add(SexprPropertyPrinter.bound("prop" + i, expressions.get(i), -1));
		}
		Verdicts v = run(net, forms, totalSeconds, shape, force, null);
		long[] res = new long[expressions.size()];
		java.util.Arrays.fill(res, -1);
		if (v == null) return res;
		for (int i = 0; i < res.length; i++) {
			if (v.value[i] != null) {
				try {
					res[i] = Long.parseLong(v.value[i]);
				} catch (NumberFormatException e) {
					// not a bound line: leave unknown
				}
			}
		}
		return res;
	}

	/**
	 * Write the net and the forms, run the binary under the budget, read the
	 * stream. Returns null when the binary could not run at all (missing,
	 * I/O error, no verdict and a non-zero exit), so a caller can fall back.
	 */
	public static Verdicts run(ISparsePetriNet net, List<String> forms, int totalSeconds, Shape shape, boolean force,
			Listener listener) {
		long t0 = System.currentTimeMillis();
		Verdicts verdicts = new Verdicts(forms.size());
		List<File> todel = new ArrayList<>();
		try {
			File netFile = Files.createTempFile("hsc-net-", ".pnet").toFile();
			File propFile = Files.createTempFile("hsc-props-", ".sexpr").toFile();
			todel.add(netFile);
			todel.add(propFile);
			PNETFormatIO.write(net, netFile.toPath());
			Files.write(propFile.toPath(), forms, StandardCharsets.UTF_8);
			CommandLine cl = new CommandLine();
			cl.addArg(binaryPath());
			cl.addArg("--net");
			cl.addArg(netFile.getCanonicalPath());
			cl.addArg("--props");
			cl.addArg(propFile.getCanonicalPath());
			cl.addArg("--shape");
			cl.addArg((shape == null ? Shape.LOUVAIN : shape).flag);
			if (force) cl.addArg("--force");
			if (totalSeconds > 0) {
				cl.addArg("--totalTime");
				cl.addArg(Integer.toString(totalSeconds));
			}
			cl.addArg("-q");
			if (DEBUG >= 1) System.out.println("Running hsc-pn: " + cl);
			ProcessBuilder pb = new ProcessBuilder(cl.getArgs());
			pb.redirectError(Redirect.INHERIT);
			Process process = pb.start();
			Thread reader = new Thread(() -> readVerdicts(process, verdicts, listener), "hsc-pn-stdout");
			reader.start();
			int exitCode = -1;
			try {
				exitCode = Runner.waitForOrTimeout(totalSeconds + GRACE_SECONDS, TimeUnit.SECONDS, cl, process);
			} catch (TimeoutException e) {
				System.out.println("hsc-pn killed after " + (totalSeconds + GRACE_SECONDS) + " s.");
			}
			reader.join();
			int seen = verdicts.solved();
			System.out.println("hsc-pn: " + seen + "/" + forms.size() + " properties solved in "
					+ (System.currentTimeMillis() - t0) + " ms (exit " + exitCode + ").");
			if (exitCode != 0 && seen == 0) {
				return null;
			}
			return verdicts;
		} catch (IOException e) {
			System.out.println("hsc-pn I/O error: " + e.getMessage());
			return null;
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			System.out.println("hsc-pn interrupted.");
			return null;
		} finally {
			if (DEBUG == 0)
				for (File f : todel)
					f.delete();
		}
	}

	/** The bundled binary, or the one named by the system property hsc.bin (tests outside OSGi). */
	private static String binaryPath() throws IOException {
		String override = System.getProperty("hsc.bin");
		if (override != null && !override.isEmpty()) {
			return override;
		}
		return BinaryToolsPlugin.getHscURI().getPath();
	}

	/** Consume stdout to its end: FORMULA prop&lt;i&gt; value TECHNIQUES words. */
	private static void readVerdicts(Process process, Verdicts verdicts, Listener listener) {
		try (BufferedReader in = new BufferedReader(
				new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
			String line;
			while ((line = in.readLine()) != null) {
				if (DEBUG >= 2) System.out.println("[hsc-pn] " + line);
				if (!line.startsWith("FORMULA prop")) continue;
				String[] words = line.split(" ", 5);
				if (words.length < 3) continue;
				int index;
				try {
					index = Integer.parseInt(words[1].substring(4));
				} catch (NumberFormatException e) {
					continue;
				}
				if (index < 0 || index >= verdicts.value.length) continue;
				verdicts.value[index] = words[2];
				verdicts.techniques[index] = words.length == 5 ? words[4] : "DECISION_DIAGRAMS";
				if (listener != null) {
					try {
						listener.formula(index, words[2], verdicts.techniques[index]);
					} catch (RuntimeException e) {
						System.out.println("hsc-pn listener dropped: " + e.getMessage());
						listener = null;
					}
				}
			}
		} catch (IOException e) {
			// stream closed by a kill: keep what was read
		}
	}
}
