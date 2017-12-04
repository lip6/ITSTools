package fr.lip6.move.gal.itstools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import fr.lip6.move.gal.itstools.CommandLine;
import fr.lip6.move.gal.itstools.ProcessController;
import fr.lip6.move.gal.itstools.ProcessController.TimeOutException;

public class Runner {
	private static final String ID = "fr.lip6.move.gal";

	public static IStatus runTool(long timeout, CommandLine cl) throws IOException, TimeOutException {
		return runTool(timeout, cl, false);
	}
	
	public static IStatus runTool(long timeout, CommandLine cl, boolean errToOut) throws IOException, TimeOutException {
		ByteArrayOutputStream stdOutput = new ByteArrayOutputStream();

		return runTool(timeout, cl, stdOutput, errToOut);
	}

	public static IStatus runTool(long timeout, CommandLine cl, OutputStream stdout, boolean errToOut) throws IOException, TimeOutException {
		ByteArrayOutputStream errorOutput = new ByteArrayOutputStream();

		final ProcessController controller = new ProcessController(timeout * 1000, cl.getArgs(), null,cl.getWorkingDir());
		controller.forwardErrorOutput(errorOutput);
		controller.forwardOutput(stdout);
		int exitCode = controller.execute();
		if (errorOutput.size() > 0) {
			if (errToOut) {
				stdout.write(errorOutput.toByteArray());
			}
			Logger.getLogger(ID).info("Standard error output from running tool "+cl + "\n" + errorOutput.toString());
		}
		System.out.println("Exit code :"+exitCode);
		if (exitCode != 0) {
			if (errorOutput.size() > 0) {
				return new Status(IStatus.WARNING, ID, exitCode, errorOutput.toString(),null);
			}
		}
		stdout.close();
		return Status.OK_STATUS;
	}
}
