package fr.lip6.move.petrispot.runner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import fr.lip6.move.gal.process.CommandLine;
import fr.lip6.move.gal.process.Runner;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.util.IntMatrixCol;
import fr.lip6.petrispot.binaries.BinaryToolsPlugin;

/**
 * Invokes the PetriSpot binary (petri64) to compute Petri net invariants/flows.
 *
 * <p>Workflow:
 * <ol>
 *   <li>Build the incidence matrix from the net's pre/post flow matrices.</li>
 *   <li>Write it to a temporary KERS file.</li>
 *   <li>Invoke petri64 with the appropriate mode flag.</li>
 *   <li>Read the output KERS file and return the result matrix.</li>
 *   <li>Clean up temporary files (unless DEBUG &gt;= 1).</li>
 * </ol>
 */
public class PetriSpotRunner {

	/** Set to 1 to keep intermediate files for inspection; 2 for extra verbose output. */
	private static final int DEBUG = 0;

	/** Default timeout in seconds for the PetriSpot binary. */
	private static final long DEFAULT_TIMEOUT_S = 120;

	/**
	 * The four computation modes supported by PetriSpot.
	 */
	public enum InvariantMode {
		/** P-flows (also called place-flows, integer flows on places) */
		PFLOWS,
		/** T-flows (transition flows) */
		TFLOWS,
		/** P-semiflows (non-negative P-flows, i.e. place invariants) */
		PSEMIFLOWS,
		/** T-semiflows (non-negative T-flows, i.e. transition invariants) */
		TSEMIFLOWS;
	}

	/**
	 * Compute invariants/flows for the given Petri net using PetriSpot.
	 *
	 * @param spn     the Petri net whose incidence matrix will be analysed
	 * @param mode    which class of invariants to compute
	 * @param timeout timeout in seconds for the external binary
	 * @return the result matrix (columns = invariants/flows), or {@code null} on failure
	 */
	public static IntMatrixCol computeInvariants(IntMatrixCol incidence, InvariantMode mode, long timeout) {
		if (mode == InvariantMode.PFLOWS) {
			IntMatrixCol cached = checkCache(incidence);
			if (cached != null) {
				return cached;
			}
		}
		List<File> todel = new ArrayList<>();
		// Default: empty matrix — safe for all callers (means "no invariants found")
		IntMatrixCol result = new IntMatrixCol(0, 0);
		try {
			

			// Step 2 – write incidence matrix to a temp KERS file
			File inputKers  = Files.createTempFile("petrispot-input-",  ".kers").toFile();
			File outputKers = Files.createTempFile("petrispot-output-", ".kers").toFile();
			todel.add(inputKers);
			todel.add(outputKers);

			KERSFormatIO.write(incidence, inputKers.toPath());

			// Step 3 – build command line
			String binaryPath = BinaryToolsPlugin.getPetriURI().getPath();
			CommandLine cl = new CommandLine();
			cl.addArg(binaryPath);
			cl.addArg("--loadKERS=" + inputKers.getCanonicalPath());
			cl.addArg(modeFlag(mode));
			cl.addArg("--basisKERS=" + outputKers.getCanonicalPath());

			System.out.println("Running PetriSpot : " + cl);
			long t0 = System.currentTimeMillis();

			// Step 4 – invoke and monitor
			int exitCode = Runner.runTool(timeout, cl);

			if (exitCode != 0) {
				System.out.println("PetriSpot run failed in " + (System.currentTimeMillis() - t0)
						+ " ms. Status: " + exitCode);
			} else if (!outputKers.exists() || outputKers.length() == 0) {
				System.out.println("PetriSpot produced no output file for mode " + mode);
			} else {
				// Step 5 – parse result
				if (DEBUG >= 1) System.out.println("Successful run of PetriSpot took "
						+ (System.currentTimeMillis() - t0) + " ms. Input: "
						+ inputKers.getCanonicalPath() + " Output: " + outputKers.getCanonicalPath());
				result = KERSFormatIO.read(outputKers.toPath());
				if (DEBUG >= 2) System.out.println("PetriSpot result matrix: " + result);
			}
		} catch (TimeoutException e) {
			System.out.println("PetriSpot timed out after " + timeout + " s (" + mode + ")");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			System.out.println("PetriSpot invocation interrupted (" + mode + ")");
		} catch (IOException e) {
			System.out.println("PetriSpot I/O error (" + mode + "): " + e.getMessage());
		} finally {
			if (DEBUG == 0)
				for (File f : todel)
					f.delete();
		}
		cache(incidence, result);
		return result;
	}

	/**
	 * Convenience overload using the default timeout.
	 */
	public static IntMatrixCol computeInvariants(SparsePetriNet spn, InvariantMode mode) {
		return computeInvariants(spn, mode, DEFAULT_TIMEOUT_S);
	}
	
	public static IntMatrixCol computeInvariants(SparsePetriNet spn, InvariantMode mode, long timeout) {
		// Step 1 – build incidence matrix: effect = post - pre = -1*flowPT + 1*flowTP
		IntMatrixCol incidence = IntMatrixCol.sumProd(-1, spn.getFlowPT(), 1, spn.getFlowTP());
		return computeInvariants(incidence, mode, timeout);
	}

	// ---- private helpers ----

	public static IntMatrixCol computeInvariants(IntMatrixCol incidence, InvariantMode mode) {
		return computeInvariants(incidence, mode, DEFAULT_TIMEOUT_S);
	}

	private static final Object lock = new Object();
	private static IntMatrixCol last = null;
	private static IntMatrixCol lastInv = null;
	private static void cache(IntMatrixCol pn, IntMatrixCol inv) {
		synchronized (lock) {
			last = new IntMatrixCol(pn);
			lastInv = new IntMatrixCol(inv);
		}
	}

	private static IntMatrixCol checkCache(IntMatrixCol pn) {
		synchronized (lock) {
			if (pn.equals(last)) {
				Logger.getLogger("fr.lip6.move.gal").info("Invariant cache hit.");
				return lastInv;
			} else {
				return null;
			}
		}
	}
	
	/**
	 * Returns the PetriSpot command-line flag for the requested computation mode.
	 * TODO: replace placeholders with actual flags once confirmed.
	 */
	private static String modeFlag(InvariantMode mode) {
		switch (mode) {
		case PFLOWS:      return "--Pflows";
		case TFLOWS:      return "--Tflows";
		case PSEMIFLOWS:  return "--Psemiflows";
		case TSEMIFLOWS:  return "--Tsemiflows";
		default: throw new IllegalArgumentException("Unknown mode: " + mode);
		}
	}
}
