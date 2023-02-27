package fr.lip6.move.gal.application.runner.ltsmin;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import fr.lip6.ltl.tgba.TGBA;
import fr.lip6.move.gal.LTLProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.application.runner.AbstractRunner;
import fr.lip6.move.gal.application.runner.Ender;
import fr.lip6.move.gal.application.runner.IRunner;
import fr.lip6.move.gal.gal2pins.Gal2PinsTransformerNext;
import fr.lip6.move.gal.gal2smt.Gal2SMTFrontEnd;
import fr.lip6.move.gal.gal2smt.Solver;
import fr.lip6.move.gal.ltsmin.BinaryToolsPlugin;
import fr.lip6.move.gal.ltsmin.BinaryToolsPlugin.Tool;
import fr.lip6.move.gal.mcc.properties.DoneProperties;
import fr.lip6.move.gal.mcc.properties.MCCExporter;
import fr.lip6.move.gal.mcc.properties.PropertyPrinter;
import fr.lip6.move.gal.pn2pins.PetriNet2PinsTransformer;
import fr.lip6.move.gal.process.CommandLine;
import fr.lip6.move.gal.process.Runner;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.StructuralToPNML;
import fr.lip6.move.gal.structural.expr.CExpressionPrinter;
import fr.lip6.move.gal.structural.expr.Op;

public class LTSminPNMLRunner extends AbstractRunner implements ILTSminRunner {

	private static final int DEBUG = 0;
	private boolean doPOR;
	private File workFolder;
	private long timeout;
	private boolean isSafe;
	private SparsePetriNet spn;
	private TGBA tgba;
	private String stateBasedHOA;
	private String pnmlPath;

	public LTSminPNMLRunner(boolean doPOR, long timeout, boolean isSafe) {
		this.doPOR = doPOR;
		try {
			this.workFolder = Files.createTempDirectory("ltsmin").toFile();
			workFolder.deleteOnExit();
		} catch (IOException e) {
			System.out.println("Unable to create temporary folder.");
		}
		this.timeout = timeout;
		this.isSafe = isSafe;
	}

	
	@Override
	public void solve(Ender ender) {
		runnerThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println("Building PNML and property files in : \n" + new File(workFolder + "/"));
					
					// step 1 : build PNML
					File pnmlpathff = Files.createTempFile("model", ".pnml").toFile();
					pnmlpathff.deleteOnExit();
					pnmlPath = pnmlpathff.getCanonicalPath();
					StructuralToPNML.transform(spn, pnmlPath);
					
					// step 2 : export properties
					if (tgba != null) {
											
					} else {
						
					}
					
					if (tgba == null) {
						checkProperties(timeout,doneProps);
					} else {
						checkProperty(tgba.getName(), stateBasedHOA, timeout, false, PropertyType.LTL);
					}
					
					/*
					List<String> todo;
					if (spn == null) {
						todo = spec.getProperties().stream().map(p -> p.getName())
								.collect(Collectors.toList());
					} else {
						todo = spn.getProperties().stream().map(p -> p.getName())
								.collect(Collectors.toList());
					}
					todo.removeAll(doneProps.keySet());
					
					if (tgba == null) {
						checkProperties(g2p, p2p, timeout,doneProps);
					} else {
						checkProperty(tgba.getName(), stateBasedHOA, timeout, false, PropertyType.LTL);
					}
					todo.removeAll(doneProps.keySet());
					if (! todo.isEmpty()) {
						System.out.println("Retrying LTSmin with larger timeout "+(8*timeout)+ " s");
						checkProperties(g2p, p2p, 8 * timeout, doneProps);
					}
					todo.removeAll(doneProps.keySet());
					if ( todo.isEmpty()) {
						ender.killAll();
					}*/
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					System.out.println("WARNING : LTS min runner thread was asked to interrupt. Dying gracefully.");
				} catch (RuntimeException e) {
					System.out.println("WARNING : LTS min runner thread failed on error :" + e);
					e.printStackTrace();
				}
			}

			public void checkProperties(long time, DoneProperties doneProps)
					throws IOException, InterruptedException {
				boolean negateResult;

				for (fr.lip6.move.gal.structural.Property prop : new ArrayList<>(spn.getProperties())) {
					if (doneProps.containsKey(prop.getName())) {
						continue;
					}
					String pbody = null;
					if (prop.getType() == PropertyType.LTL) {
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						PrintWriter pw = new PrintWriter(baos);
						{
							CExpressionPrinter printer = new CExpressionPrinter(new PrintWriter(baos), "src");
							prop.getBody().accept(printer);
							printer.close();
						}
						pbody = baos.toString();
					}

					if (prop.getBody().getOp() == Op.EF) {
						negateResult = true;
					} else {
						negateResult = false;
					}

					checkProperty(prop.getName(),pbody,time,negateResult, prop.getType());
				}
			}




		});
		runnerThread.start();
	}
	
	private void checkProperty(String pname, String pbody, long timeout, boolean negateResult, PropertyType propertyType) throws IOException, InterruptedException {
		if (doneProps.containsKey(pname)) {
			return;
		}
		CommandLine ltsmin = new CommandLine();
		ltsmin.setWorkingDir(workFolder);
		ltsmin.addArg(BinaryToolsPlugin.getProgramURI(Tool.pnmlmc).getPath().toString());
		ltsmin.addArg(pnmlPath);

		ltsmin.addArg("--procs=8");
		boolean withPOR = false;
		if (doPOR && isStutterInvariant(pbody)) {
			ltsmin.addArg("-p");
			withPOR = true;
		}
		ltsmin.addArg("--when");
		boolean isdeadlock = false;
		boolean isLTL = false;
		if (propertyType == PropertyType.DEADLOCK) {
			ltsmin.addArg("-d");
			isdeadlock = true;
		} else if (propertyType == PropertyType.LTL) {
			if (pbody.endsWith(".hoa")) {
				ltsmin.addArg("--hoa");
			} else {
				ltsmin.addArg("--ltl");
			}
			ltsmin.addArg(pbody);
			// ltsmin.addArg("--strategy=renault");
			ltsmin.addArg("--buchi-type=spotba");

			// ltsmin.addArg("--ltl-semantics");
			// ltsmin.addArg("spin");

			isLTL = true;
		} else { // INVARIANT			
			ltsmin.addArg("-i");
			ltsmin.addArg(pbody);
//			ltsmin.addArg(pname.replaceAll("-", "") + "==true");
		}
		System.out.println(ltsmin);
		
		try {
			File outputff = Files.createTempFile("ltsrun", ".out").toFile();
			outputff.deleteOnExit();
			long time = System.currentTimeMillis();
			System.out.println("Running LTSmin : " + ltsmin);
			int status = Runner.runTool(timeout, ltsmin, outputff, true);
			if (status == 137) {
				System.err.println("LTSmin failed to check property "+ pname + " due to out of memory issue (code 137).");
				return;
			}
			if (status != 0 && status != 1) {
				Files.lines(outputff.toPath()).forEach(l -> System.err.println(l));
				throw new RuntimeException("Unexpected exception when executing ltsmin :" + ltsmin + "\n" + status);				
			}
			System.out.println("LTSmin run took "+ (System.currentTimeMillis() -time) +" ms.");
			System.out.flush();
			boolean result;
			

			
			if (Files.lines(outputff.toPath()).anyMatch(output -> output.contains("Error: tree leafs table full! Change -s/--ratio"))) {
				// this is a real issue : need to bail out, result is not correct
				System.err.println("LTSmin failed to check property "+ pname + " due to out of memory issue.");
				return;
			}
			if (isdeadlock) {
				result = Files.lines(outputff.toPath()).anyMatch(line -> line.contains("Deadlock found") || line.contains("deadlock () found")); 
			} else if (isLTL) {
				// accepting cycle = counter example to
				// formula
				result = ! (status == 1) ; // output.toLowerCase().contains("accepting cycle found") ;
			} else {
				boolean hasViol = Files.lines(outputff.toPath()).anyMatch(output -> output.contains("Invariant violation"));

				if (hasViol) {
					System.out.println("Found Violation");
					if (negateResult) {
						result = true;
					} else  {
						result = false;
					} 
				} else {
					System.out.println("Invariant validated");
					if (negateResult) {
						result = false;
					} else {
						result = true;
					} 
				}
			}
			String ress = (result + "").toUpperCase();
			doneProps.put(pname,"TRUE".equals(ress),(withPOR ? "PARTIAL_ORDER ":"") + "EXPLICIT LTSMIN SAT_SMT");
			System.out.flush();
		} catch (TimeoutException to) {
			System.out.println("WARNING : LTSmin timed out (>"+timeout+" s) on command " + ltsmin);
			return ;
		}
	}
	
	private boolean isStutterInvariant(String pbody) {		
		return pbody==null || pbody.contains(".hoa") || ! pbody.contains("X");
	}

	private void compilePINS(int timeout) throws IOException, TimeoutException, InterruptedException {
		// compile
		long time = System.currentTimeMillis();
		CommandLine clgcc = new CommandLine();
		clgcc.setWorkingDir(workFolder);
		clgcc.addArg(BinaryToolsPlugin.getProgramURI(Tool.limit_time).getPath().toString());		
		clgcc.addArg(Long.toString(timeout));
		clgcc.addArg("gcc");
		clgcc.addArg("-c");
		clgcc.addArg("-I" + BinaryToolsPlugin.getIncludeFolderURI().getPath().toString());
		clgcc.addArg("-I.");
		clgcc.addArg("-std=c99");
		clgcc.addArg("-fPIC");
		// try no opt to limit timeout
		clgcc.addArg("-O0");
		// clgcc.addArg("-O2");
		clgcc.addArg("model.c");

		System.out.println("Running compilation step : " + clgcc);
		File outputff = Files.createTempFile("gccrun", ".out").toFile();
		outputff.deleteOnExit();
		new File(workFolder+"/model.o").deleteOnExit();
		int status = Runner.runTool(timeout, clgcc, outputff, true);
		if (status != 0) {
			Files.lines(outputff.toPath()).forEach(l -> System.err.println(l));
			throw new RuntimeException("Could not compile executable ." + clgcc);
		}
		System.out.println("Compilation finished in "+ (System.currentTimeMillis() -time) +" ms.");
		System.out.flush();
	}

	private void linkPINS(long timeLimit) throws IOException, TimeoutException, InterruptedException {
		// link
		long time = System.currentTimeMillis();
		CommandLine clgcc = new CommandLine();
		File cwd = workFolder;
		clgcc.setWorkingDir(cwd);
		clgcc.addArg("gcc");
		clgcc.addArg("-shared");
		clgcc.addArg("-o");
		clgcc.addArg("gal.so");
		clgcc.addArg("model.o");
		System.out.println("Running link step : " + clgcc);
		File outputff = Files.createTempFile("linkrun", ".out").toFile();
		outputff.deleteOnExit();
		new File(workFolder+"/gal.so").deleteOnExit();
		int status = Runner.runTool(timeout, clgcc, outputff, true);
		if (status != 0) {
			Files.lines(outputff.toPath()).forEach(l -> System.err.println(l));
			throw new RuntimeException("Could not link executable ." + clgcc);
		}
		System.out.println("Link finished in "+ (System.currentTimeMillis() -time) +" ms.");
		System.out.flush();
	}

	public void setNet(SparsePetriNet spn) {
		this.spn = spn;
	}


	public void setTGBA(TGBA negProp, String stateBasedHOA) {
		this.tgba = negProp;
		this.stateBasedHOA = stateBasedHOA;
	}
}
