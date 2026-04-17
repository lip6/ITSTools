package fr.lip6.move.petrispot.runner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
 *   <li>Clean up temporary files.</li>
 * </ol>
 */
public class PetriSpotRunner {

	private static final Logger log = Logger.getLogger("fr.lip6.move.gal");

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
	public static IntMatrixCol computeInvariants(SparsePetriNet spn, InvariantMode mode, long timeout) {
		// Step 1 – build incidence matrix: effect = post - pre = -1*flowPT + 1*flowTP
		IntMatrixCol incidence = IntMatrixCol.sumProd(-1, spn.getFlowPT(), 1, spn.getFlowTP());

		Path workDir = null;
		try {
			// Step 2 – write incidence matrix to a temp KERS file
			workDir = Files.createTempDirectory("petrispot-");
			Path inputKers  = workDir.resolve("input.kers");
			Path outputKers = workDir.resolve("output.kers");

			KERSFormatIO.write(incidence, inputKers);

			// Step 3 – build command line
			String binaryPath = BinaryToolsPlugin.getPetriURI().getPath();
			CommandLine cl = new CommandLine();
			cl.addArg(binaryPath);
			cl.addArg("--loadKERS=" + inputKers.toAbsolutePath());
			cl.addArg(modeFlag(mode));
			cl.addArg("--basisKERS=" + outputKers.toAbsolutePath());
			cl.setWorkingDir(workDir.toFile());

			log.info("Invoking PetriSpot: " + cl);
			long t0 = System.currentTimeMillis();

			// Step 4 – invoke and monitor
			int exitCode = Runner.runTool(timeout, cl);
			log.info("PetriSpot finished in " + (System.currentTimeMillis() - t0) + " ms, exit=" + exitCode);

			if (exitCode != 0) {
				log.warning("PetriSpot returned non-zero exit code " + exitCode + " for mode " + mode);
				return null;
			}

			if (!Files.exists(outputKers)) {
				log.warning("PetriSpot produced no output file for mode " + mode);
				return null;
			}

			// Step 5 – parse result
			return KERSFormatIO.read(outputKers);

		} catch (TimeoutException e) {
			log.warning("PetriSpot timed out after " + timeout + " s (" + mode + ")");
			return null;
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			log.warning("PetriSpot invocation interrupted (" + mode + ")");
			return null;
		} catch (IOException e) {
			log.warning("PetriSpot I/O error (" + mode + "): " + e.getMessage());
			return null;
		} finally {
			// Step 6 – cleanup
			if (workDir != null) {
				deleteQuietly(workDir.resolve("input.kers"));
				deleteQuietly(workDir.resolve("output.kers"));
				deleteQuietly(workDir);
			}
		}
	}

	/**
	 * Convenience overload using the default timeout.
	 */
	public static IntMatrixCol computeInvariants(SparsePetriNet spn, InvariantMode mode) {
		return computeInvariants(spn, mode, DEFAULT_TIMEOUT_S);
	}

	// ---- private helpers ----

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

	private static void deleteQuietly(Path p) {
		try {
			Files.deleteIfExists(p);
		} catch (IOException e) {
			// best-effort cleanup
		}
	}
}
