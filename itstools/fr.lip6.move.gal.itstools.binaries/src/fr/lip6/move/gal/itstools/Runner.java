package fr.lip6.move.gal.itstools;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import fr.lip6.move.gal.itstools.CommandLine;

public class Runner {
	private static final String ID = "fr.lip6.move.gal";

	/**
	 * Runs a process and wait for its termination. Outputs and errors are not captured.
	 * Returns the process exit value or throws if timeout is reached.
	 * @param timeout a timeout in seconds
	 * @param cl the command line to run, carries working folder and command line arguments.
	 * @return the exit value of the process when it finished normally
	 * @throws IOException in case of problems executing the command itself (starting a process)
	 * @throws TimeoutException in case timeout is exceeded
	 * @throws InterruptedException in case the thread calling this method is interrupted (e.g. some outside thread decided this run was no longer useful).
	 */
	public static int runTool(long timeout, CommandLine cl) throws IOException, TimeoutException, InterruptedException {
		ProcessBuilder pb = new ProcessBuilder(cl.getArgs());
		pb.directory(cl.getWorkingDir());
		pb.redirectError(Redirect.INHERIT);
		pb.redirectOutput(Redirect.INHERIT);
		Process process = pb.start();
		return waitForOrTimeout(timeout, TimeUnit.SECONDS, cl, process);
	}

	/**
	 * Wait for a process to end ; if it doesn't end by timeout, kill it.
	 * If we are interrupted, kill it.
	 * @param timeout timeout expressed in givien time unit
	 * @param tu the time unit
	 * @param cl command line being run by the process, used to label exceptions with readable messages.
	 * @param process the process we are monitoring
	 * @return the exit code of the process if it finished normally
	 * @throws TimeoutException if timeout was reached. We kill the process if this happens.
	 * @throws InterruptedException if somebody interrupted the thread. We kill the process when that happens.
	 */
	public static int waitForOrTimeout(long timeout, TimeUnit tu, CommandLine cl, Process process)
			throws TimeoutException, InterruptedException {
		try {
			if (process.waitFor(timeout, tu)) {
				// normal timely termination
				return process.exitValue();
			} else {
				// timeout
				killProcess(process);
				throw new TimeoutException("Subprocess running "+cl+" killed by timeout after "+timeout +" " + TimeUnit.SECONDS);
			}
		} catch (InterruptedException e) {
			// forcefully asked to die			
			killProcess(process);
			throw e;
		}
	}

	/**
	 * Try to play nice and ask the process to die, but kill -9 if it does not respond within 100 ms.
	 * @param process a process to kill
	 * @throws InterruptedException if we were interrupted within the critical 100 ms between soft and hard kill.
	 */
	private static void killProcess(Process process) throws InterruptedException {
		process.destroy();
		if (! process.waitFor(100, TimeUnit.MILLISECONDS)) {
			process.destroyForcibly();
		}
	}
	
	
	/**
	 * Runs a process and wait for its termination. Outputs and errors are captured (appended) into the provided file.
	 * If errToOut is set to false, errors are set to INHERIT, i.e. will go to stderr of current process.
	 * Returns the process exit value or throws if timeout is reached.
	 * @param timeout a timeout in seconds
	 * @param cl the command line to run, carries working folder and command line arguments.
	 * @param stdout a writeable File for the tool to append its output
	 * @param errToOut if true, merges stderr into the output file, if false INHERIT stderr.
	 * @return the exit value of the process when it finished normally
	 * @throws IOException in case of problems executing the command itself (starting a process), writing to the requested file
	 * @throws TimeoutException in case timeout is exceeded
	 * @throws InterruptedException in case the thread calling this method is interrupted (e.g. some outside thread decided this run was no longer useful).
	 */
	public static int runTool(long timeout, CommandLine cl, File stdout, boolean errToOut) throws IOException, TimeoutException, InterruptedException {
		ProcessBuilder pb = new ProcessBuilder(cl.getArgs());
		pb.directory(cl.getWorkingDir());
		if (errToOut) {
			pb.redirectErrorStream(true);
		} else {
			pb.redirectError(Redirect.INHERIT);
		}
		pb.redirectOutput(stdout);
		Process process = pb.start();		
		return waitForOrTimeout(timeout, TimeUnit.SECONDS, cl, process);
	}
}
