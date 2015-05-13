package fr.lip6.move.gal.application;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.cegar.frontend.CegarFrontEnd;
import fr.lip6.move.gal.cegar.interfaces.IResult;
import fr.lip6.move.gal.gal2smt.Gal2SMTFrontEnd;
import fr.lip6.move.gal.instantiate.CompositeBuilder;
import fr.lip6.move.gal.instantiate.GALRewriter;
import fr.lip6.move.gal.instantiate.Instantiator;
import fr.lip6.move.gal.instantiate.Simplifier;
import fr.lip6.move.gal.itstools.CommandLine;
import fr.lip6.move.gal.itstools.CommandLineBuilder;
import fr.lip6.move.gal.itstools.ProcessController;
import fr.lip6.move.gal.itstools.BinaryToolsPlugin.Tool;
import fr.lip6.move.gal.itstools.ProcessController.TimeOutException;
import fr.lip6.move.gal.logic.Properties;
import fr.lip6.move.gal.logic.saxparse.PropertyParser;
import fr.lip6.move.gal.logic.togal.ToGalTransformer;
import fr.lip6.move.gal.order.IOrder;
import fr.lip6.move.gal.pnml.togal.PnmlToGalTransformer;
import fr.lip6.move.gal.support.Support;
import fr.lip6.move.serialization.SerializationUtil;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

	
	private static final String ID = "fr.lip6.move.gal";
	private static final String APPARGS = "application.args";
	
	private static final String PNFOLDER = "-pnfolder";

	private static final String EXAMINATION = "-examination";
	private static final String Z3PATH = "-z3path";
	
	private ByteArrayOutputStream errorOutput;

	private ByteArrayOutputStream stdOutput;;
	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	public Object start(IApplicationContext context) throws Exception {
		
		String [] args = (String[]) context.getArguments().get(APPARGS);
		
		String pwd = null;
		String examination = null;
		String z3path = null;
		for (int i=0; i < args.length ; i+=2) {
			if (PNFOLDER.equals(args[i])) {
				pwd = args[i+1];
			} else if (EXAMINATION.equals(args[i])) {
				examination = args[i+1]; 
			} else if (Z3PATH.equals(args[i])) {
				z3path = args[i+1]; 
			} 
		}
		
		transformPNML(pwd);
		
		
		
		String outpath ;
		if (spec.getMain() == null) {
			spec.setMain(spec.getTypes().get(spec.getTypes().size()-1));
		}

		CommandLine cl =null;
		boolean withStructure = order != null; 
		
		if (examination.equals("StateSpace")) {
			outpath =  pwd + "/model.pnml.gal";

			applyOrder();
			Simplifier.simplify(spec);

			// compute constants
			Support constants = GALRewriter.flatten(spec, true);

			
			SerializationUtil.systemToFile(spec, outpath);

			cl = buildCommandLine(outpath);
		} else if (examination.startsWith("Reachability")) {

			//			Properties props = fr.lip6.move.gal.logic.util.SerializationUtil.fileToProperties(file.getLocationURI().getPath().toString());
			// TODO : is the copy really useful ?
			String propff = pwd +"/" +  examination + ".xml";
			Properties props = PropertyParser.fileToProperties(propff , EcoreUtil.copy(spec));
			
			Specification specWithProps = ToGalTransformer.toGal(props);

//			runCegar(EcoreUtil.copy(specWithProps),  pwd);
//			if (true)
//				return null;
			if (z3path != null) {
				Gal2SMTFrontEnd gsf = new Gal2SMTFrontEnd(z3path);
				gsf.checkProperties(EcoreUtil.copy(specWithProps), pwd);
				return null;
			}
			
			if (order != null) {
				CompositeBuilder.getInstance().decomposeWithOrder((GALTypeDeclaration) specWithProps.getTypes().get(0), order.clone());
			}
			// compute constants
			Support constants = GALRewriter.flatten(specWithProps, true);
			ArrayList<Property> properties = new ArrayList<Property>(specWithProps.getProperties());

			outpath = pwd +"/" + examination + ".gal" ;
			specWithProps.getProperties().clear();
			fr.lip6.move.serialization.SerializationUtil.systemToFile(specWithProps, outpath);
			cl = buildCommandLine(outpath);
			
			// test for and handle properties		
			if (! properties.isEmpty()) {

				// We will put properties in a file
				String propPath =pwd + "/" + examination + ".prop";

				// create file
				SerializationUtil.serializePropertiesForITSTools(outpath,	properties, propPath);

				// property file arguments
				cl.addArg("-reachable-file");
				cl.addArg(new File(propPath).getName());

				cl.addArg("--nowitness");
				
			}
			
//			applyOrder();
//			Simplifier.simplify(spec);
			
			
		}
		cl.setWorkingDir(new File(pwd));
				
		
		

		

		try {		
			run(600, cl);
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
				System.out.println( "STATE_SPACE STATES " + line.split(":")[1] + " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL " + (withStructure?"USE_NUPN":"") );
			}
			if ( line.matches(".*"+examination+".*")) {
				System.out.println(line);
				String res;
				String pname = line.split(" ")[2];
				if (line.contains("does not hold")) {
					res = "FALSE";
				} else if (line.contains("No reachable states")) {
					res = "FALSE";
					pname = line.split(":")[1];
				} else {
					res = "TRUE";
				}
				System.out.println("FORMULA "+pname+ " "+ res + " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL " + (withStructure?"USE_NUPN":""));
			}
		}
		
		getLog().info(stdOutput.toString());
		getLog().warning(errorOutput.toString());
		
		return IApplication.EXIT_OK;
	}




	private void runCegar(final Specification specNoProp, final String pwd) {

		 Simplifier.simplify(specNoProp);

		 final List<Property> properties = new ArrayList<Property>(specNoProp.getProperties());
//		Executor exec = Executors.newSingleThreadExecutor();
//		exec.execute(new Runnable() {
//			@Override
//			public void run() {
				for (Property prop : properties) {
					specNoProp.getProperties().clear();
					specNoProp.getProperties().add(prop);
					try {
						IResult res = CegarFrontEnd.processGal(specNoProp, pwd);
						String ress = "FALSE";
						if (res.isPropertyTrue()) {
							ress = "TRUE";
						}
		
						System.out.println("FORMULA "+prop.getName()+ " "+ ress + " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL CEGAR ");
						
					} catch (IOException e) {
						e.printStackTrace();
						getLog().warning("Aborting CEGAR due to an exception");
						return;
					}
				}
//			}
//		});
	}


	private boolean applyOrder() {
		if (order != null) {
			getLog().info("Applying decomposition ");
			getLog().fine(order.toString());
			CompositeBuilder.getInstance().decomposeWithOrder((GALTypeDeclaration) spec.getTypes().get(0), order);
			return true;
		}
		return false;
	}


	private CommandLine buildCommandLine(String modelff) throws IOException {
		CommandLineBuilder cl = new CommandLineBuilder(Tool.reach);
		cl.setModelFile(modelff);
		cl.setModelType("CGAL");
		return cl.getCommandLine();
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
	
	
	private Specification spec;
	private IOrder order;

	
	/**
	 * Sets the spec and order attributes.
	 * @param folder
	 * @throws Exception
	 */
	private void transformPNML(String folder) throws Exception {
		File ff = new File(folder+ "/"+ "model.pnml");
		if (ff != null && ff.exists()) {
			getLog().info("Parsing pnml file : " + ff.getAbsolutePath());

			PnmlToGalTransformer trans = new PnmlToGalTransformer();
			spec = trans.transform(ff.toURI());
			order = trans.getOrder();
			// SerializationUtil.systemToFile(spec, ff.getPath() + ".gal");
		}
	}
	
	private void buildProperty (File file) throws IOException {
		if (file.getName().endsWith(".xml") && file.getName().contains("Reachability") ) {
			
			// normal case
			{
//				Properties props = fr.lip6.move.gal.logic.util.SerializationUtil.fileToProperties(file.getLocationURI().getPath().toString());
				// TODO : is the copy really useful ?
				Properties props = PropertyParser.fileToProperties(file.getPath().toString(), EcoreUtil.copy(spec));
				
				Specification specWithProps = ToGalTransformer.toGal(props);

				if (order != null) {
					CompositeBuilder.getInstance().decomposeWithOrder((GALTypeDeclaration) specWithProps.getTypes().get(0), order.clone());
				}
				// compute constants
				Support constants = GALRewriter.flatten(specWithProps, true);

				File galout = new File( file.getParent() +"/" + file.getName().replace(".xml", ".gal"));
				fr.lip6.move.serialization.SerializationUtil.systemToFile(specWithProps, galout.getAbsolutePath());
			} 
			// Abstraction case 
			if (file.getParent().contains("-COL-")) {
				ToGalTransformer.setWithAbstractColors(true);
				Properties props = fr.lip6.move.gal.logic.util.SerializationUtil.fileToProperties(file.getAbsolutePath());

				Specification specnocol = ToGalTransformer.toGal(props);
				Instantiator.instantiateParametersWithAbstractColors(specnocol);
				GALRewriter.flatten(specnocol, true);

				File galout = new File( file.getParent() +"/" + file.getName().replace(".xml", ".nocol.gal"));
				fr.lip6.move.serialization.SerializationUtil.systemToFile(specnocol, galout.getAbsolutePath());

				ToGalTransformer.setWithAbstractColors(false);
			}

		}		
	}

	
	
	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		// nothing to do
	}
}
