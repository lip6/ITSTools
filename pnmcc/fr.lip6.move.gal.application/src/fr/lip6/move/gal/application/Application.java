package fr.lip6.move.gal.application;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.instantiate.CompositeBuilder;
import fr.lip6.move.gal.itstools.BinaryToolsPlugin;
import fr.lip6.move.gal.itstools.BinaryToolsPlugin.Tool;
import fr.lip6.move.gal.itstools.launch.CommandLine;
import fr.lip6.move.gal.itstools.launch.ProcessController;
import fr.lip6.move.gal.itstools.launch.ProcessController.TimeOutException;
import fr.lip6.move.gal.pnml.togal.PnmlToGalTransformer;
import fr.lip6.move.serialization.SerializationUtil;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

	
	private static final String ID = "fr.lip6.move.gal";
	private static final String APPARGS = "application.args";
	
	private static final String PNFOLDER = "-pnfolder";

	private static final String EXAMINATION = "-examination";
	
	private enum ExamType { STATESPACE, REACH, CTL, LTL }

	private ByteArrayOutputStream errorOutput;

	private ByteArrayOutputStream stdOutput;;
	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	public Object start(IApplicationContext context) throws Exception {
		
		String [] args = (String[]) context.getArguments().get(APPARGS);
		
		String pwd = null;
		ExamType examination = null;
		
		for (int i=0; i < args.length ; i+=2) {
			if (PNFOLDER.equals(args[i])) {
				pwd = args[i+1];
			} else if (EXAMINATION.equals(args[i])) {
				if ( "StateSpace".equals(args[i+1])) {
					examination = ExamType.STATESPACE;
				}
			}
		}
		
				
		String pnmlff = pwd + "/model.pnml"; 		
		URI fileuri = new java.net.URI("file://" +pnmlff.replace('\\', '/'));
		
		getLog().info("Handling file : " + fileuri.getPath());
		
		PnmlToGalTransformer trans = new PnmlToGalTransformer();
		Specification spec = trans.transform(fileuri);
		if (trans.getOrder() != null) {
			getLog().info("Applying decomposition : " + trans.getOrder());
			CompositeBuilder.getInstance().decomposeWithOrder((GALTypeDeclaration) spec.getTypes().get(0), trans.getOrder());
		}
		String outpath = fileuri.getPath() + ".gal";

		if (spec.getMain() == null) {
			spec.setMain(spec.getTypes().get(spec.getTypes().size()-1));
		}
		SerializationUtil.systemToFile(spec, outpath);
		
		CommandLine cl = buildCommandLine(outpath);

		try {		
			run(120, cl);
		} catch (TimeOutException e) {
			System.out.println("COULD_NOT_COMPUTE");
			return new Status(IStatus.ERROR, ID,
					"Check Service process did not finish in a timely way."
							+ errorOutput.toString());
		} catch (IOException e) {
			System.out.println("COULD_NOT_COMPUTE");
			return new Status(IStatus.ERROR, ID,
					"Unexpected exception executing service."
							+ errorOutput.toString(), e);
		}		
		
		for (String line : stdOutput.toString().split("\\n")) {
			if ( line.matches("\\s*Total reachable state count.*")) {
				System.out.println( "STATE_SPACE STATES " + line.split(":")[1] + " TECHNIQUES DECISION_DIAGRAMS USE_NUPN TOPOLOGICAL");
			}
		}
		
	//	getLog().info(stdOutput.toString());
	//	getLog().warning(errorOutput.toString());
		
		return IApplication.EXIT_OK;
	}


	private CommandLine buildCommandLine(String modelff) throws IOException {
		CommandLine cl = new CommandLine();
		
		// Path to ITS-reach exe				
		String itsReachPath = BinaryToolsPlugin.getProgramURI(Tool.reach).getPath().toString();
		cl.addArg(itsReachPath);
		
		cl.addArg("-i");
		cl.addArg(modelff);
		
		cl.addArg("-t");
		cl.addArg("CGAL");
		
		cl.addArg("--quiet");
		return cl;
	}
	
	
	public IStatus run(int timeout, CommandLine cl) throws IOException, TimeOutException {

		errorOutput = new ByteArrayOutputStream();
		stdOutput = new ByteArrayOutputStream();
		

			
			final ProcessController controller = new ProcessController(timeout * 1000, cl.getArgs(), null,cl.getWorkingDir());
			controller.forwardErrorOutput(errorOutput);
			controller.forwardOutput(stdOutput);
			int exitCode = controller.execute();
			if (exitCode != 0) {
				if (errorOutput.size() > 0) {
					return new Status(IStatus.WARNING, ID,errorOutput.toString());
				}
			}
			getLog().fine("trace of its reach "+stdOutput.toString());
			return Status.OK_STATUS;
	}

	private static Logger getLog() {
		return Logger.getLogger("fr.lip6.move.gal");
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		// nothing to do
	}
}
