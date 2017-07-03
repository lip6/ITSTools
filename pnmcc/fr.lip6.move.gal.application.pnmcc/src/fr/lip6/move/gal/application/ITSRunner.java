package fr.lip6.move.gal.application;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.interpreter.ITSInterpreter;
import fr.lip6.move.gal.itscl.interpreter.FileStreamInterprete;
import fr.lip6.move.gal.itstools.CommandLine;
import fr.lip6.move.gal.itstools.CommandLineBuilder;
import fr.lip6.move.gal.itstools.Runner;
import fr.lip6.move.gal.itstools.BinaryToolsPlugin.Tool;
import fr.lip6.move.gal.itstools.ProcessController.TimeOutException;
import fr.lip6.move.serialization.SerializationUtil;

public class ITSRunner extends AbstractRunner {

	private String examination;
	private MccTranslator reader;
	private CommandLine cl;
	private boolean doITS;
	private boolean onlyGal;
	private String folder;
	private Set<String> todoProps;

	private boolean done = false;
	private ITSInterpreter interp;

	public ITSRunner(String examination, MccTranslator reader, boolean doITS, boolean onlyGal, String workFolder) {
		this.examination = examination;
		this.reader = reader;
		this.doITS = doITS;
		this.onlyGal = onlyGal;
		this.folder = workFolder;
	}

	public void configure(Specification spec, Set<String> doneProps) throws IOException {
		super.configure(spec, doneProps);
		if (examination.equals("StateSpace")) {
			String outpath = outputGalFile();
			cl = buildCommandLine(outpath);
			cl.addArg("--stats");
		} else if (examination.equals("ReachabilityDeadlock")) {
			if (doITS || onlyGal) {
				String outpath = outputGalFile();
				cl = buildCommandLine(outpath, Tool.ctl);
				cl.addArg("-ctl");
				cl.addArg("DEADLOCK");
			}
		} else if (examination.startsWith("CTL")) {
			reader.removeAdditionProperties();

			if (doITS || onlyGal) {
				String outpath = outputGalFile();

				String ctlpath = outputPropertyFile();

				cl = buildCommandLine(outpath, Tool.ctl);

				cl.addArg("-ctl");
				cl.addArg(ctlpath);

				// cl.addArg("--backward");
			}
		} else if (examination.startsWith("LTL")) {

			if (doITS || onlyGal) {
				String outpath = outputGalFile();
				String ltlpath = outputPropertyFile();

				cl = buildCommandLine(outpath, Tool.ltl);
				cl.addArg("-LTL");
				cl.addArg(ltlpath);

				cl.addArg("-c");
				// cl.addArg("-SSLAP-FSA");

				cl.addArg("-stutter-deadlock");
			}

		} else if (examination.startsWith("Reachability") || examination.contains("Bounds")) {
			if (doITS || onlyGal) {
				// decompose + simplify as needed
				String outpath = outputGalFile();

				cl = buildCommandLine(outpath);

				// We will put properties in a file
				String propPath = outputPropertyFile();

				// property file arguments
				cl.addArg("-reachable-file");
				cl.addArg(new File(propPath).getName());

				cl.addArg("--nowitness");
			}
		}
		if (cl != null) {
			cl.setWorkingDir(new File(folder));
		}
	}

	class ITSRealRunner implements Runnable {
		private OutputStream pout;
		private CommandLine cl;

		public ITSRealRunner(OutputStream pout, CommandLine cl) {
			this.pout = pout;
			this.cl = cl;
		}

		public void run() {

			try {
				Runner.runTool(3500, cl, pout, false);

			} catch (TimeOutException e) {
				System.out.println("Detected timeout of ITS tools.");
				return;
				// return new Status(IStatus.ERROR, ID,
				// "Check Service process did not finish in a timely way."
				// + errorOutput.toString());
			} catch (IOException e) {
				System.out.println("Failure when invoking ITS tools.");
				return;
				// return new Status(IStatus.ERROR, ID,
				// "Unexpected exception executing service."
				// + errorOutput.toString(), e);
			} finally {
				try {
					pout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private CommandLine buildCommandLine(String modelff) throws IOException {
		return buildCommandLine(modelff, Tool.reach);
	}

	private CommandLine buildCommandLine(String modelff, Tool tool) throws IOException {
		CommandLineBuilder cl = new CommandLineBuilder(tool);
		cl.setModelFile(modelff);
		cl.setModelType("CGAL");
		return cl.getCommandLine();
	}

	public String outputPropertyFile() throws IOException {
		String proppath = folder + "/" + examination;
		if (examination.contains("CTL")) {
			proppath += ".ctl";
			SerializationUtil.serializePropertiesForITSCTLTools(getOutputPath(), spec.getProperties(), proppath);
		} else if (examination.contains("LTL")) {
			proppath += ".ltl";
			SerializationUtil.serializePropertiesForITSLTLTools(getOutputPath(), spec.getProperties(), proppath);
		} else {
			// Reachability
			proppath += ".prop";
			SerializationUtil.serializePropertiesForITSTools(getOutputPath(), spec.getProperties(), proppath);
		}

		return proppath;
	}

	// private void buildProperty (File file) throws IOException {
	// if (file.getName().endsWith(".xml") &&
	// file.getName().contains("Reachability") ) {
	//
	// // normal case
	// {
	//// Properties props =
	// fr.lip6.move.gal.logic.util.SerializationUtil.fileToProperties(file.getLocationURI().getPath().toString());
	// // TODO : is the copy really useful ?
	// Properties props =
	// PropertyParser.fileToProperties(file.getPath().toString(),
	// EcoreUtil.copy(spec));
	//
	// Specification specWithProps = ToGalTransformer.toGal(props);
	//
	// if (order != null) {
	// CompositeBuilder.getInstance().decomposeWithOrder((GALTypeDeclaration)
	// specWithProps.getTypes().get(0), order.clone());
	// }
	// // compute constants
	// Support constants = GALRewriter.flatten(specWithProps, true);
	//
	// File galout = new File( file.getParent() +"/" +
	// file.getName().replace(".xml", ".gal"));
	// fr.lip6.move.serialization.SerializationUtil.systemToFile(specWithProps,
	// galout.getAbsolutePath());
	// }
	// // Abstraction case
	// if (file.getParent().contains("-COL-")) {
	// ToGalTransformer.setWithAbstractColors(true);
	// Properties props =
	// PropertyParser.fileToProperties(file.getPath().toString(),
	// EcoreUtil.copy(spec));
	//
	// Specification specnocol = ToGalTransformer.toGal(props);
	// Instantiator.instantiateParametersWithAbstractColors(specnocol);
	// GALRewriter.flatten(specnocol, true);
	//
	// File galout = new File( file.getParent() +"/" +
	// file.getName().replace(".xml", ".nocol.gal"));
	// fr.lip6.move.serialization.SerializationUtil.systemToFile(specnocol,
	// galout.getAbsolutePath());
	//
	// ToGalTransformer.setWithAbstractColors(false);
	// }
	//
	// }
	// }

	private String getOutputPath() {
		return folder + "/" + examination + ".pnml.gal";
	}

	public String outputGalFile() throws IOException {
		String outpath = getOutputPath();
		if (!spec.getProperties().isEmpty()) {
			List<Property> props = new ArrayList<Property>(spec.getProperties());
			spec.getProperties().clear();
			SerializationUtil.systemToFile(spec, outpath);
			spec.getProperties().addAll(props);
		} else {
			SerializationUtil.systemToFile(spec, outpath);
		}
		return outpath;
	}

	
	
	public void setDone() {
		done = !done;
	}

	public Boolean taskDone() {
		interp.acquireResult();
		return done;
	}

	/**
	 * Generate ITS interpreter
	 * 
	 * Run both solver and interpreter and wait till termination of both threads
	 */
	public void solve() {
		FileStreamInterprete bufferWIO = new FileStreamInterprete();
		
		todoProps = reader.getSpec().getProperties().stream().map(p -> p.getName()).collect(Collectors.toSet());
		
		Thread runnerThread = new Thread(new ITSRealRunner(bufferWIO.getPout(), cl),"ITSRealRunner");
		interp = new ITSInterpreter(examination, reader.hasStructure(), reader, doneProps, todoProps,
				this);
		interp.setInput(bufferWIO);
		Thread interpTh = new Thread(interp, "ITSInterpreter");

		inRunner.addThInterprete(interpTh);
		long time = System.currentTimeMillis();

		try {
			runnerThread.start();
			interpTh.start();
			runnerThread.join();
			interpTh.join();
		} catch (InterruptedException e) {
			try {
				runnerThread.interrupt();
			} catch (Exception ee) {
				System.out.println("ITSRealRunner didn't interrupte");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		System.out.println("ITS complete. Time  : " + (System.currentTimeMillis() - time));
	}

}