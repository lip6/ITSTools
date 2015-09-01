package fr.lip6.move.gal.application;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import fr.lip6.move.gal.False;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.InvariantProp;
import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.True;
import fr.lip6.move.gal.cegar.frontend.CegarFrontEnd;
import fr.lip6.move.gal.cegar.interfaces.IResult;
import fr.lip6.move.gal.gal2smt.Gal2SMTFrontEnd;
import fr.lip6.move.gal.gal2smt.ISMTObserver;
import fr.lip6.move.gal.gal2smt.Result;
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
	private static final String SMT = "-smt";
	private static final String ITS = "-its";
	private static final String CEGAR = "-cegar";
	
	private ByteArrayOutputStream errorOutput;

	private ByteArrayOutputStream stdOutput;
	private Thread cegarRunner;
	private Thread z3Runner;
	private Thread itsRunner;
	
	
	public synchronized void killAll () {
		if (cegarRunner != null)
			cegarRunner.interrupt();
		if (z3Runner != null)
			z3Runner.interrupt();
		if (itsRunner != null) 
			itsRunner.interrupt();
		System.exit(0);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	public Object start(IApplicationContext context) throws Exception {
		
		String [] args = (String[]) context.getArguments().get(APPARGS);
		
		String pwd = null;
		String examination = null;
		String z3path = null;
		
		boolean doITS = false;
		boolean doSMT = false;
		boolean doCegar = false;
		boolean doAll = true;
		
		
		for (int i=0; i < args.length ; i+=2) {
			if (PNFOLDER.equals(args[i])) {
				pwd = args[i+1];
			} else if (EXAMINATION.equals(args[i])) {
				examination = args[i+1]; 
			} else if (Z3PATH.equals(args[i])) {
				z3path = args[i+1]; 
			} else if (SMT.equals(args[i])) {
				doAll = false;
				doSMT = true;
			} else if (CEGAR.equals(args[i])) {
				doAll = false;
				doCegar = true;
			} else if (ITS.equals(args[i])) {
				doAll = false;
				doITS = true;
			} 
		}
		
		try {
			transformPNML(pwd);
		} catch (IOException e) {
			System.err.println("Incorrect file or folder " + pwd + "\n Error :" + e.getMessage());
			if (e.getCause() != null) {
				e.getCause().printStackTrace();
			} else {
				e.printStackTrace();
			}
			return null;
			
		}
		
		String outpath ;

		// for debug and control
		if (pwd.contains("COL")) {
			outpath =  pwd + "/model.pnml.img.gal";
			SerializationUtil.systemToFile(spec, outpath);
		}
		

		CommandLine cl =null;
		boolean withStructure = order != null; 
		
		if (examination.equals("StateSpace")) {
			outpath =  pwd + "/model.pnml.gal";

			if (!applyOrder()) {
				GALRewriter.flatten(spec, true);
			}
//			Simplifier.simplify(spec);
//
//			// compute constants
			
			SerializationUtil.systemToFile(spec, outpath);

			cl = buildCommandLine(outpath);
			cl.addArg("--stats");
		} else if (examination.startsWith("Reachability")) {

			//			Properties props = fr.lip6.move.gal.logic.util.SerializationUtil.fileToProperties(file.getLocationURI().getPath().toString());
			// TODO : is the copy really useful ?
			String propff = pwd +"/" +  examination + ".xml";
			Properties props = PropertyParser.fileToProperties(propff , spec);
			
			spec = ToGalTransformer.toGal(props);

			GALRewriter.flatten(spec, true);
			
			// get rid of trivial properties in spec
			checkInInitial(spec);
			
			// cegar does not support hierarchy currently, time to start it, the spec won't get any better
			if (z3path != null && (doAll || doSMT) ) {
				Specification z3Spec = EcoreUtil.copy(spec);
				// run on a fresh copy to avoid any interference with other threads.
				runSMT(pwd, z3path, z3Spec);
			}
			
			// run on a fresh copy to avoid any interference with other threads.
			if (doAll || doCegar)
				runCegar(EcoreUtil.copy(spec),  pwd);


			if (doAll || doITS) {
				// decompose + simplify as needed
				applyOrder();

				ArrayList<Property> properties = new ArrayList<Property>(spec.getProperties());

				if (! properties.isEmpty()) {

					outpath = pwd +"/" + examination + ".gal" ;
					spec.getProperties().clear();
					fr.lip6.move.serialization.SerializationUtil.systemToFile(spec, outpath);
					cl = buildCommandLine(outpath);


					// We will put properties in a file
					String propPath =pwd + "/" + examination + ".prop";

					// create file
					SerializationUtil.serializePropertiesForITSTools(outpath,	properties, propPath);

					// property file arguments
					cl.addArg("-reachable-file");
					cl.addArg(new File(propPath).getName());

					cl.addArg("--nowitness");

				} else {
					// no more properties !
					return null;
				}
			}
			
			
		}
		if (cl != null) {
			cl.setWorkingDir(new File(pwd));
		}
				
		
		if (doAll || doITS) 
			runITStool(cl,examination,withStructure);
		
		if (cegarRunner != null)
			cegarRunner.join();
		if (z3Runner != null)
			z3Runner.join();
		if (itsRunner != null)
			itsRunner.join();
		
		return IApplication.EXIT_OK;
	}



	private void runITStool(final CommandLine cl, final String examination, final boolean withStructure) {
		itsRunner = new Thread (new Runnable() {
			@Override
			public void run() {
				try {		
					runTool(3500, cl);
				} catch (TimeOutException e) {
					System.out.println("COULD_NOT_COMPUTE");
					return;
					//					return new Status(IStatus.ERROR, ID,
					//							"Check Service process did not finish in a timely way."
					//									+ errorOutput.toString());
				} catch (IOException e) {
					System.out.println("COULD_NOT_COMPUTE");
					return;
					//					return new Status(IStatus.ERROR, ID,
					//							"Unexpected exception executing service."
					//									+ errorOutput.toString(), e);
				}		

				for (String line : stdOutput.toString().split("\\r?\\n")) {
					if ( line.matches("Max variable value.*")) {
						if (examination.equals("StateSpace")) {
							System.out.println( "STATE_SPACE MAX_TOKEN_IN_PLACE " + line.split(":")[1] + " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL " + (withStructure?"USE_NUPN":"") );
						}
					}
					if ( line.matches("Maximum sum along a path.*")) {
						if (examination.equals("StateSpace")) {
							System.out.println( "STATE_SPACE MAX_TOKEN_PER_MARKING " + line.split(":")[1] + " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL " + (withStructure?"USE_NUPN":"") );
						}
					}
					if ( line.matches("\\s*Total reachable state count.*")) {
						if (examination.equals("StateSpace")) {
							System.out.println( "STATE_SPACE STATES " + line.split(":")[1] + " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL " + (withStructure?"USE_NUPN":"") );
						}
					}
					
					if ( line.matches(".*-"+examination+"-\\d+.*")) {
						System.out.println(line);
						String res;
						if (line.matches(".*property.*")) {
							String pname = line.split(" ")[2];
							if (line.contains("does not hold")) {
								res = "FALSE";
							} else if (line.contains("No reachable states")) {
								res = "FALSE";
								pname = line.split(":")[1];
							} else {
								res = "TRUE";
							}
							System.out.println("FORMULA "+pname+ " "+ res + " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL COLLATERAL_PROCESSING " + (withStructure?"USE_NUPN":""));
						}
					}
				}
				getLog().info(stdOutput.toString());
//				getLog().warning(errorOutput.toString());
				killAll();
			}
		});
		itsRunner.start();
	}

	public synchronized void runSMT(final String pwd, final String z3path,
			final Specification z3Spec) {
		z3Runner = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Gal2SMTFrontEnd gsf = new Gal2SMTFrontEnd(z3path);
				gsf.addObserver(new ISMTObserver() {
					@Override
					public void notifyResult(Property prop, Result res, int depth) {
						if (res == Result.SAT) {
							if (prop.getBody() instanceof ReachableProp) {
								// a trace to state P
								System.out.println("FORMULA " + prop.getName() + " TRUE " + "TECHNIQUES SAT_SMT COLLATERAL_PROCESSING" );
							} else {
								// a c-e trace 
								System.out.println("FORMULA " + prop.getName() + " FALSE " + "TECHNIQUES SAT_SMT COLLATERAL_PROCESSING" );						
							}
						}
					}
				});
				try {
					Map<String, Result> satresult = gsf.checkProperties(z3Spec, pwd);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// test for and handle properties		
				if (z3Spec.getProperties().isEmpty()) {
					System.out.println("Topological + SAT/SMT solved all properties. Skipping subsequent analysis.");
					killAll();
				}
				List<Property> todel = new ArrayList<Property>();
				//						for (Property prop : z3Spec.getProperties()) {
				//							if (satresult.get(prop.getName()) == Result.SAT) {
				//								todel.add(prop);
				//							}
				//						}
				//						specWithProps.getProperties().removeAll(todel);
				//					}
			}
		}
				);
		z3Runner.start();

		
	}



	/**
	 * Structural analysis and reduction : test in initial state.
	 * @param specWithProps spec which will be modified : trivial properties will be removed
	 */
	private void checkInInitial(Specification specWithProps) {
		List<Property> props = new ArrayList<Property>(specWithProps.getProperties());
				
		// iterate down so indexes are consistent
		for (int i = props.size()-1; i >= 0 ; i--) {
			Property prop = props.get(i);

			// discard property
			if (prop.getBody().getPredicate() instanceof True || prop.getBody().getPredicate() instanceof False) {
				specWithProps.getProperties().remove(i);
			}
			// output verdict
			if (prop.getBody() instanceof ReachableProp || prop.getBody() instanceof InvariantProp) {
				
				if (prop.getBody().getPredicate() instanceof True) {
					// positive forms : EF True , AG True <=>True
					System.out.println("FORMULA "+prop.getName() + " TRUE TECHNIQUES TOPOLOGICAL");
				} else if (prop.getBody().getPredicate() instanceof False) {
					// positive forms : EF False , AG False <=> False
					System.out.println("FORMULA "+prop.getName() + " FALSE TECHNIQUES TOPOLOGICAL");
				}
			} else if (prop.getBody() instanceof NeverProp) {
				if (prop.getBody().getPredicate() instanceof True) {
					// negative form : ! EF P = AG ! P, so ! EF True <=> False
					System.out.println("FORMULA "+prop.getName() + " FALSE TECHNIQUES TOPOLOGICAL");
				} else if (prop.getBody().getPredicate() instanceof False) {
					// negative form : ! EF P = AG ! P, so ! EF False <=> True
					System.out.println("FORMULA "+prop.getName() + " TRUE TECHNIQUES TOPOLOGICAL");
				}
			}
		}
	}




	private synchronized void runCegar(final Specification specNoProp, final String pwd) {

		cegarRunner = new Thread(new Runnable() {

			@Override
			public void run() {
				// current implem cannot deal with arrays
				// degeneralize, should be ok for Petri nets at least
				GALRewriter.flatten(specNoProp, true);
				CompositeBuilder cb = CompositeBuilder.getInstance();
				cb.rewriteArraysAsVariables(specNoProp);
				Simplifier.simplify(specNoProp);

				final List<Property> properties = new ArrayList<Property>(specNoProp.getProperties());
				for (Property prop : properties) {
					specNoProp.getProperties().clear();
					specNoProp.getProperties().add(prop);
					try {
						IResult res = CegarFrontEnd.processGal(specNoProp, pwd);
						String ress = "FALSE";
						if (res.isPropertyTrue()) {
							ress = "TRUE";
						}

						System.out.println("FORMULA "+prop.getName()+ " "+ ress + " TECHNIQUES DECISION_DIAGRAMS COLLATERAL_PROCESSING TOPOLOGICAL CEGAR ");

					} catch (IOException e) {
						e.printStackTrace();
						getLog().warning("Aborting CEGAR due to an exception");
						return;
					} catch (RuntimeException re) {
						re.printStackTrace();
						getLog().warning("Aborting CEGAR check of property " + prop.getName() + " due to an exception when running procedure.");
					}
				}
				killAll();
				
			}
		});
		cegarRunner.start();
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
	
	
	public IStatus runTool(int timeout, CommandLine cl) throws IOException, TimeOutException {

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
	 * Sets the spec and order attributes, spec is set to result of PNML tranlsation and order is set to null if no nupn/computed order is available.
	 * @param folder input folder absolute path, containing a model.pnml file
	 * @throws IOException if file can't be found
	 */
	private void transformPNML(String folder) throws IOException {
		File ff = new File(folder+ "/"+ "model.pnml");
		if (ff != null && ff.exists()) {
			getLog().info("Parsing pnml file : " + ff.getAbsolutePath());

			PnmlToGalTransformer trans = new PnmlToGalTransformer();
			spec = trans.transform(ff.toURI());
			order = trans.getOrder();
			// SerializationUtil.systemToFile(spec, ff.getPath() + ".gal");
			if (spec.getMain() == null) {
				spec.setMain(spec.getTypes().get(spec.getTypes().size()-1));
			}
		} else {
			throw new IOException("Cannot open file "+ff.getAbsolutePath());
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
