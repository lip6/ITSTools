package fr.lip6.move.gal.cegar.checkers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.util.EcoreUtil;

import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.instantiate.GALRewriter;
import fr.lip6.move.gal.itstools.CommandLine;
import fr.lip6.move.gal.itstools.CommandLineBuilder;
import fr.lip6.move.gal.itstools.ProcessController;
import fr.lip6.move.gal.itstools.BinaryToolsPlugin.Tool;
import fr.lip6.move.gal.itstools.ProcessController.TimeOutException;
import fr.lip6.move.serialization.SerializationUtil;

public class ITSLauncher {

	private static final int DEFAULT_TIMEOUT = 60;
	private static final String ID = "fr.lip6.move.gal";
	private String modelff;
	

	private ByteArrayOutputStream errorOutput;
	private ByteArrayOutputStream stdOutput;
	private String cegarProp = "";
	private String trace = "";
	private Specification spec;

	
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

	public void run() {
		run(DEFAULT_TIMEOUT);
	}
	
	
	public IStatus run(int timeout) {

		long debut = System.currentTimeMillis();
		errorOutput = new ByteArrayOutputStream();
		stdOutput = new ByteArrayOutputStream();
			
		try {
			CommandLineBuilder clb = new CommandLineBuilder(Tool.reach);

			// remove properties from file
			List<Property> props = new ArrayList<Property> (spec.getProperties());
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

			final ProcessController controller = new ProcessController(timeout * 1000, cl.getArgs(), null,cl.getWorkingDir());
			controller.forwardErrorOutput(errorOutput);
			controller.forwardOutput(stdOutput);
			int exitCode = controller.execute();

			getLog().info("Run of its-reach took " + (System.currentTimeMillis() -debut) + " ms");
//			getLog().info("trace of its reach "+stdOutput.toString());
//			getLog().warning("error trace of its reach "+errorOutput.toString());

			if (exitCode != 0) {
				if (errorOutput.size() > 0) {
					getLog().warning("its reach execution raised an exception "+errorOutput.toString());
					return new Status(IStatus.WARNING, ID,errorOutput.toString());
				}
			}
			return Status.OK_STATUS;
		} catch (TimeOutException e) {
			return new Status(IStatus.ERROR, ID,
					"Check Service process did not finish in a timely way."
							+ errorOutput.toString());
		} catch (IOException e) {
			return new Status(IStatus.ERROR, ID,
					"Unexpected exception executing service."
							+ errorOutput.toString(), e);
		}		
	}



	public void setModel(Specification gal) {
		this.spec = EcoreUtil.copy(gal);
	}
	
	public ByteArrayOutputStream getStdOutput() {
		return stdOutput;
	}
	
	public ByteArrayOutputStream getErrorOutput() {
		return errorOutput;
	}

	public Reader getResult() throws IOException {
		return new StringReader(stdOutput.toString());
	}
}
