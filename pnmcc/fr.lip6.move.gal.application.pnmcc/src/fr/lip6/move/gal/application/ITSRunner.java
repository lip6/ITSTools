package fr.lip6.move.gal.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import fr.lip6.move.gal.Constant;
import fr.lip6.move.gal.GALTypeDeclaration;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.Reference;
import fr.lip6.move.gal.Specification;
import fr.lip6.move.gal.itstools.CommandLineBuilder;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.itstools.BinaryToolsPlugin.Tool;
import fr.lip6.move.gal.process.CommandLine;
import fr.lip6.move.gal.process.Runner;
import fr.lip6.move.serialization.SerializationUtil;

public class ITSRunner extends AbstractRunner {

	private String examination;
	private MccTranslator reader;
	protected CommandLine cl;
	private boolean doITS;
	private boolean onlyGal;
	protected String workFolder;

	private Thread itsReader;
	private long timeout;
	private String orderff;



	public ITSRunner(String examination, MccTranslator reader, boolean doITS, boolean onlyGal,
			String workFolder, long timeout, String orderff) {
		this.examination = examination;
		this.reader = reader;
		this.doITS = doITS;
		this.onlyGal = onlyGal;
		this.workFolder = workFolder;
		this.timeout = timeout;
		this.orderff = orderff;
	}



	@Override
	public void configure(Specification spec, DoneProperties doneProps) throws IOException {
		super.configure(spec, doneProps);
		if (examination.equals("StateSpace")) {
			String outpath = outputGalFile();
			cl = buildCommandLine(outpath);
			cl.addArg("--stats");
		} else if (examination.equals("ReachabilityDeadlock")|| examination.equals("GlobalProperties")) {
			if (doITS || onlyGal) {
				String outpath = outputGalFile();
				cl = buildCommandLine(outpath, Tool.ctl);
				cl.addArg("-ctl");
				cl.addArg("DEADLOCK");
			}
		} else if (examination.startsWith("CTL")) {
			// reader.removeAdditionProperties();


			if (doITS || onlyGal) {								
				String outpath = outputGalFile(); 

				String ctlpath = outputPropertyFile(); 

				cl = buildCommandLine(outpath, Tool.ctl);

				cl.addArg("-ctl");
				cl.addArg(ctlpath);	

				//cl.addArg("--backward");
			}
		} else if (examination.startsWith("LTL")) {

			if (doITS || onlyGal) {
				String outpath = outputGalFile();
				String ltlpath = outputPropertyFile();


				cl = buildCommandLine(outpath, Tool.ltl);
				cl.addArg("-LTL");
				cl.addArg(ltlpath);	

				cl.addArg("-c");
				//cl.addArg("-SSLAP-FSA");

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
		if (orderff != null) {
			cl.addArg("--load-order");
			cl.addArg(orderff);
		}
		if (spec.getMain() instanceof GALTypeDeclaration) {
			cl.addArg("--gen-order");
			cl.addArg("FOLLOW");
		}
		if (cl != null) {
			cl.setWorkingDir(new File(workFolder));
		}
	}

	@Override
	public void interrupt() {
		super.interrupt();
		if (itsReader != null) {
			itsReader.interrupt();
		}
	}

	@Override
	public void join() throws InterruptedException {
		super.join();
		if (itsReader != null) {
			itsReader.join();
		}
	}

	class ITSInterpreter implements Runnable {

		private BufferedReader in;
		//private Map<String, List<Property>> boundProps;
		private String examination;
		private boolean withStructure;
		private MccTranslator reader;
		private DoneProperties seen;
		private Set<String> todoProps;
		private Ender ender;


		public ITSInterpreter(String examination, boolean withStructure, MccTranslator reader, DoneProperties doneProps, Set<String> todoProps, Ender ender) {			
			this.examination = examination;
			this.withStructure = withStructure;
			this.reader = reader;
			this.seen = doneProps;
			this.todoProps = todoProps; 
			this.ender = ender;
		}

		public void setInput(InputStream pin) {
			this.in = new BufferedReader(new InputStreamReader(pin));
		}

		@Override
		public void run() {
			int seenCounts = 0;

			try {
				for (String line = ""; line != null ; line=in.readLine() ) {
					System.out.println(line);
					//stdOutput.toString().split("\\r?\\n")) ;
					if ( line.matches("Max variable value.*")) {
						if (examination.equals("StateSpace")) {
							seenCounts++;							
							System.out.println( "STATE_SPACE MAX_TOKEN_IN_PLACE " + line.split(":")[1] + " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL " + (withStructure?"USE_NUPN":"") );
						}
					}
					if ( line.matches("Maximum sum along a path.*")) {
						if (examination.equals("StateSpace")) {
							seenCounts++;
							int nbtok = Integer.parseInt(line.split(":")[1].replaceAll("\\s", ""));
							nbtok += reader.countMissingTokens();
							System.out.println( "STATE_SPACE MAX_TOKEN_PER_MARKING " + nbtok + " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL " + (withStructure?"USE_NUPN":"") );
						}
					}
					if ( line.matches("Exact state count.*")) {
						if (examination.equals("StateSpace")) {
							seenCounts++;							
							System.out.println( "STATE_SPACE STATES " + line.split(":")[1] + " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL " + (withStructure?"USE_NUPN":"") );
						}
					}
					if ( line.matches("Total edges in reachability graph.*")) {
						if (examination.equals("StateSpace")) {
							seenCounts++;
							System.out.println( "STATE_SPACE UNIQUE_TRANSITIONS " + line.split(":")[1] + " TECHNIQUES DECISION_DIAGRAMS TOPOLOGICAL " + (withStructure?"USE_NUPN":"") );
						}
					}
					if ( line.matches("System contains.*deadlocks.*")) {
						if (examination.equals("ReachabilityDeadlock") || examination.equals("GlobalProperties")) {

							Property dead = reader.getSpec().getProperties().get(0);
							String pname = dead.getName();
							double nbdead = Double.parseDouble(line.split("\\s+")[2]);
							seen.put(pname,nbdead != 0,"DECISION_DIAGRAMS TOPOLOGICAL " + (withStructure?"USE_NUPN":""));
						}
					}
					if ( line.matches("Bounds property.*")) {
						if (examination.contains("Bounds") ) {
							String [] words = line.split(" ");
							String pname = words[2];
							String [] tab = line.split("<=");

							String sbound = tab[2].replaceAll("\\s", "");

							int bound = Integer.parseInt(sbound);
							Property target = null;
							for (Property prop : reader.getSpec().getProperties()) {
								if (prop.getName().equals(pname) ) {
									target = prop;
									break;
								}
							}
							int toadd=0;
							for (TreeIterator<EObject> it = target.eAllContents() ; it.hasNext() ; ) {
								EObject obj = it.next();
								if (obj instanceof Constant) {
									Constant cte = (Constant) obj;
									toadd += cte.getValue();
								} else if (obj instanceof Reference) {
									it.prune();
								}
							}
							seen.put(pname,(bound+toadd),"DECISION_DIAGRAMS TOPOLOGICAL "+ (withStructure?"USE_NUPN":""));
						}
					}
					if ( examination.startsWith("CTL")) {
						if (line.matches(".*formula \\d+,\\d+,.*")) {
							String [] tab = line.split(",");
							int formindex = Integer.parseInt(tab[0].split(" ")[1]);
							int verdict = Integer.parseInt(tab[1]);
							String pname = reader.getSpec().getProperties().get(formindex).getName();
							seen.put(pname,verdict != 0,"DECISION_DIAGRAMS TOPOLOGICAL "+ (withStructure?"USE_NUPN":""));
						}
					}
					if ( examination.startsWith("LTL")) {
						if (line.matches("Formula \\d+ is .*")) {
							String [] tab = line.split(" ");
							int formindex = Integer.parseInt(tab[1]);
							String res = tab[3];
							String pname = reader.getSpec().getProperties().get(formindex).getName();
							seen.put(pname, "TRUE".equals(res),"DECISION_DIAGRAMS TOPOLOGICAL "+ (withStructure?"USE_NUPN":""));
						}
					}

					if ( line.endsWith("does not hold.") || line.endsWith("is true.") ) {
						//System.out.println(line);
						String res;
						if (line.matches(".*property.*") && ! line.contains("Bounds")) {
							String[] words = line.split(" ");
							if (words.length < 3 || ! "property".equals(words[1]) ) {
								continue;
							}
							String pname = words[2];
							if (line.contains("does not hold")) {
								res = "FALSE";
							} else if (line.contains("No reachable states")) {
								res = "FALSE";
								pname = line.split(":")[1];
							} else {
								res = "TRUE";
							}
							pname = pname.replaceAll("\\s", "");
							if (!seen.containsKey(pname)) {
								seen.put(pname,"TRUE".equals(res),"DECISION_DIAGRAMS TOPOLOGICAL COLLATERAL_PROCESSING " + (withStructure?"USE_NUPN":""));
							}
						}
					}
					System.out.flush();
				}
				in.close();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (GlobalPropertySolverException e) {
				ender.killAll();
				return;
			}
			if (seen.keySet().containsAll(todoProps)) {
				if (examination.equals("StateSpace") && seenCounts < 3) {
					// NOP
				} else {
					ender.killAll();
				}
			}
		}

	}

	private CommandLine buildCommandLine(String modelff) throws IOException {
		return buildCommandLine(modelff,Tool.reach);
	}

	private CommandLine buildCommandLine(String modelff, Tool tool) throws IOException {
		CommandLineBuilder cl = new CommandLineBuilder(tool);
		cl.setModelFile(modelff);
		cl.setModelType("CGAL");
		return cl.getCommandLine();
	}


	public String outputPropertyFile() throws IOException {
		String proppath = workFolder +"/" + examination ;
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

	//	private void buildProperty (File file) throws IOException {
	//		if (file.getName().endsWith(".xml") && file.getName().contains("Reachability") ) {
	//			
	//			// normal case
	//			{
	////				Properties props = fr.lip6.move.gal.logic.util.SerializationUtil.fileToProperties(file.getLocationURI().getPath().toString());
	//				// TODO : is the copy really useful ?
	//				Properties props = PropertyParser.fileToProperties(file.getPath().toString(), EcoreUtil.copy(spec));
	//				
	//				Specification specWithProps = ToGalTransformer.toGal(props);
	//
	//				if (order != null) {
	//					CompositeBuilder.getInstance().decomposeWithOrder((GALTypeDeclaration) specWithProps.getTypes().get(0), order.clone());
	//				}
	//				// compute constants
	//				Support constants = GALRewriter.flatten(specWithProps, true);
	//
	//				File galout = new File( file.getParent() +"/" + file.getName().replace(".xml", ".gal"));
	//				fr.lip6.move.serialization.SerializationUtil.systemToFile(specWithProps, galout.getAbsolutePath());
	//			} 
	//			// Abstraction case 
	//			if (file.getParent().contains("-COL-")) {
	//				ToGalTransformer.setWithAbstractColors(true);
	//				Properties props = PropertyParser.fileToProperties(file.getPath().toString(), EcoreUtil.copy(spec));
	//
	//				Specification specnocol = ToGalTransformer.toGal(props);
	//				Instantiator.instantiateParametersWithAbstractColors(specnocol);
	//				GALRewriter.flatten(specnocol, true);
	//
	//				File galout = new File( file.getParent() +"/" + file.getName().replace(".xml", ".nocol.gal"));
	//				fr.lip6.move.serialization.SerializationUtil.systemToFile(specnocol, galout.getAbsolutePath());
	//
	//				ToGalTransformer.setWithAbstractColors(false);
	//			}
	//
	//		}		
	//	}


	private String getOutputPath() {
		return workFolder + "/"+ examination +".pnml.gal";
	}

	public String outputGalFile() throws IOException {
		String outpath =  getOutputPath();
		if (! spec.getProperties().isEmpty()) {
			List<Property> props = new ArrayList<Property>(spec.getProperties());
			spec.getProperties().clear();
			SerializationUtil.systemToFile(spec, outpath);
			spec.getProperties().addAll(props);
		} else {
			SerializationUtil.systemToFile(spec, outpath);
		}
		return outpath;
	}

	@Override
	public void solve(Ender ender) {

		ProcessBuilder pb = new ProcessBuilder(cl.getArgs());
		pb.directory(cl.getWorkingDir());
		pb.redirectErrorStream(true);

		System.out.println("Invoking ITS tools like this :" + cl );
		try {
			final Process process = pb.start();	


			Set<String> todoProps = reader.getSpec().getProperties().stream().map(p -> p.getName()).collect(Collectors.toSet());

			runnerThread = new Thread (() -> {
				try {
					int status = Runner.waitForOrTimeout(timeout, TimeUnit.SECONDS, cl, process);
					if (status != 0) {
						System.err.println("ITS-tools command line returned an error code "+status);
					}
				} catch (InterruptedException e) {
					System.out.println("ITS tools runner thread asked to quit. Dying gracefully.");
					return;
				} catch (TimeoutException e) {
					System.out.println("Detected timeout of ITS tools.");
					return;
				}
			});

			ITSInterpreter interp = new ITSInterpreter(examination, reader.hasStructure(), reader, doneProps, todoProps, ender);
			interp.setInput(process.getInputStream());
			itsReader = new Thread (interp);
			itsReader.start();
			runnerThread.start();
		} catch (IOException e) {
			System.out.println("Failure when invoking ITS tools."+e);
			return;
		}
	}

}
