package fr.lip6.move.gal.cegar.checkers;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.instantiate.GALRewriter;
import fr.lip6.move.gal.itstools.CommandLineBuilder;
import fr.lip6.move.gal.process.CommandLine;
import fr.lip6.move.gal.process.Runner;
import fr.lip6.move.gal.itstools.BinaryToolsPlugin.Tool;
import fr.lip6.move.serialization.SerializationUtil;

public class ITSLauncher {

	private static final int DEFAULT_TIMEOUT = 3500;
	private static final String ID = "fr.lip6.move.gal";
	private String modelff;
	

	private String cegarProp = "";
	private String trace = "";
	private Specification spec;
	private File outputff;
	
	public ITSLauncher(String modelff) {
		this.modelff = modelff;
	}
	
	public void setProperty (String prop) {
		this.cegarProp  = prop;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}

	private Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
	}

	public IStatus run() {
		return run(DEFAULT_TIMEOUT);
	}
	
	public IStatus run(int timeout) {

		long debut = System.currentTimeMillis();
		
			
		try {
			outputff = Files.createTempFile("itsrun", ".out").toFile();
			
			CommandLineBuilder clb = new CommandLineBuilder(Tool.reach);

			// remove properties from file
			spec.getProperties().clear();

			if (spec.getMain() == null) {
				spec.setMain(spec.getTypes().get(0));
			}

			// flatten it
			GALRewriter.flatten(spec, true);

			// produce the model file
			SerializationUtil.systemToFile(spec, modelff);

			clb.setModelFile(modelff);
			clb.setModelType("CGAL");


			if ("".equals(trace)) {
				// reachability case
				clb.setReachableProp(cegarProp);
			} else {
				// trace case
				clb.setTrace(trace);
			}

			CommandLine cl = clb.getCommandLine();
			int exitCode = Runner.runTool(timeout, cl, outputff, true);

			getLog().info("Run of its-reach took " + (System.currentTimeMillis() -debut) + " ms");
//			getLog().info("trace of its reach "+stdOutput.toString());
//			getLog().warning("error trace of its reach "+errorOutput.toString());

			if (exitCode != 0) {
				getLog().warning("its reach execution raised an exception ");
				getLog().warning("STDOUT : "+Files.readAllLines(outputff.toPath()));
				return new Status(IStatus.WARNING, ID, "Command returned non zero exit value, probably out of memory.");
			}
			return Status.OK_STATUS;
		} catch (TimeoutException e) {			
			return new Status(IStatus.ERROR, ID,
					"Check Service process did not finish in a timely way. Timeout was " + timeout + " seconds. " );
		} catch (IOException|InterruptedException e) {
			return new Status(IStatus.ERROR, ID,
					"Unexpected exception executing service.", e);
		}		
	}



	public void setModel(Specification gal) {
		this.spec = EcoreUtil.copy(gal);
	}		

	public Reader getResult() throws IOException {
		return new FileReader(outputff);
	}
}
