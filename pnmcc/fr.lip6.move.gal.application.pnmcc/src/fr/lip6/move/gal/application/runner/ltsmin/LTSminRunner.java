package fr.lip6.move.gal.application.runner.ltsmin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

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
import fr.lip6.move.gal.pn2pins.PetriNet2PinsTransformer;
import fr.lip6.move.gal.process.CommandLine;
import fr.lip6.move.gal.process.Runner;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.expr.Op;

public class LTSminRunner extends AbstractRunner implements IRunner {

	private String solverPath;
	private boolean doPOR;
	private boolean onlyGal;
	private File workFolder;
	private Solver solver;
	private long timeout;
	private boolean isSafe;
	private SparsePetriNet spn;

	public LTSminRunner(String solverPath, Solver solver, boolean doPOR, boolean onlyGal, long timeout, boolean isSafe) {
		this.solverPath = solverPath;
		this.solver = solver;
		this.doPOR = doPOR;
		this.onlyGal = onlyGal;
		try {
			this.workFolder = Files.createTempDirectory("ltsmin").toFile();			
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
					System.out.println("Built C files in : \n" + new File(workFolder + "/"));
					
					Gal2PinsTransformerNext g2p = null;
					PetriNet2PinsTransformer p2p = null;
					if (spn == null) {
						g2p = new Gal2PinsTransformerNext();

						final Gal2SMTFrontEnd gsf = new Gal2SMTFrontEnd(solverPath, solver, timeout);
						g2p.setSmtConfig(gsf);
						g2p.initSolver();
						g2p.transform(spec, workFolder.getCanonicalPath(), doPOR, isSafe);

					} else {
						p2p = new PetriNet2PinsTransformer();
						p2p.transform(spn, workFolder.getCanonicalPath(), doPOR, false);
						
					}
					try {
						compilePINS(Math.max(1, timeout/10));
						linkPINS(Math.max(1, timeout/10));
					} catch (TimeoutException to) {
						throw new RuntimeException("Compilation or link of executable timed out." + to);
					}

					if (onlyGal) {
						System.out.println("Successfully built gal.so in :" + workFolder);
						//							System.out.println("It has labels for :" + (spec.getProperties().stream()
						//									.map(p -> p.getName().replaceAll("-", "")).collect(Collectors.toList())));
						return;
					}

					List<String> todo;
					if (spn == null) {
						todo = spec.getProperties().stream().map(p -> p.getName())
								.collect(Collectors.toList());
					} else {
						todo = spn.getProperties().stream().map(p -> p.getName())
								.collect(Collectors.toList());
					}
					todo.removeAll(doneProps.keySet());
					checkProperties(g2p, p2p, timeout,doneProps);
					todo.removeAll(doneProps.keySet());
					if (! todo.isEmpty()) {
						System.out.println("Retrying LTSmin with larger timeout "+(8*timeout)+ " s");
						checkProperties(g2p, p2p, 8 * timeout, doneProps);
					}
					todo.removeAll(doneProps.keySet());
					if ( todo.isEmpty()) {
						ender.killAll();
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					System.out.println("WARNING : LTS min runner thread was asked to interrupt. Dying gracefully.");
				} catch (RuntimeException e) {
					System.out.println("WARNING : LTS min runner thread failed on error :" + e);
					e.printStackTrace();
				}
			}

			public void checkProperties(Gal2PinsTransformerNext g2p, PetriNet2PinsTransformer p2p, long time, DoneProperties doneProps)
					throws IOException, InterruptedException {
				boolean negateResult;
				if (spn == null) {
					for (Property prop : spec.getProperties()) {
						if (prop.getBody() instanceof ReachableProp) {
							negateResult = true;
						} else  {
							negateResult = false;
						}
						String pbody = null;
						PropertyType ptype;
						if (prop.getBody() instanceof LTLProp) {
							pbody = g2p.printLTLProperty((LTLProp) prop.getBody());
							ptype = PropertyType.LTL;
						} else if (prop.getName().contains("Deadlock")) {
							ptype = PropertyType.DEADLOCK;
						} else {
							ptype = PropertyType.INVARIANT;
						}
						
						checkProperty(prop.getName(),pbody,time,negateResult, ptype);
					}

				} else {							
					for (fr.lip6.move.gal.structural.Property prop : new ArrayList<>(spn.getProperties())) {
						if (doneProps.containsKey(prop.getName())) {
							continue;
						}
						String pbody = null;
						if (prop.getType() == PropertyType.LTL)
							pbody = p2p.printLTLProperty(prop);
						
						if (prop.getBody().getOp() == Op.EF) {
							negateResult = true;
						} else {
							negateResult = false;
						}
						
						checkProperty(prop.getName(),pbody,time,negateResult, prop.getType());
					}
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
		ltsmin.addArg(BinaryToolsPlugin.getProgramURI(Tool.mc).getPath().toString());
		ltsmin.addArg("./gal.so");

		ltsmin.addArg("--threads=8");
		boolean withPOR = false;
		if (doPOR && isStutterInvariant(pbody)) {
			ltsmin.addArg("-p");
			ltsmin.addArg("--pins-guards");
			//ltsmin.addArg("--no-V");
			withPOR = true;
		}
		ltsmin.addArg("--when");
		boolean isdeadlock = false;
		boolean isLTL = false;
		if (propertyType == PropertyType.DEADLOCK) {
			ltsmin.addArg("-d");
			isdeadlock = true;
		} else if (propertyType == PropertyType.LTL) {
			ltsmin.addArg("--ltl");
			ltsmin.addArg(pbody);
			// ltsmin.addArg("--strategy=renault");
			ltsmin.addArg("--buchi-type=spotba");

			// ltsmin.addArg("--ltl-semantics");
			// ltsmin.addArg("spin");

			isLTL = true;
		} else { // INVARIANT			
			ltsmin.addArg("-i");
			ltsmin.addArg(pname.replaceAll("-", "") + "==true");
		}
		
		try {
			File outputff = Files.createTempFile("ltsrun", ".out").toFile();
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
		return pbody ==null || ! pbody.contains("X");
	}

	private void compilePINS(long timeout) throws IOException, TimeoutException, InterruptedException {
		// compile
		long time = System.currentTimeMillis();
		CommandLine clgcc = new CommandLine();
		clgcc.setWorkingDir(workFolder);
		clgcc.addArg("gcc");
		clgcc.addArg("-c");
		clgcc.addArg("-I" + BinaryToolsPlugin.getIncludeFolderURI().getPath().toString());
		clgcc.addArg("-I.");
		clgcc.addArg("-std=c99");
		clgcc.addArg("-fPIC");
		clgcc.addArg("-O2");
		clgcc.addArg("model.c");

		System.out.println("Running compilation step : " + clgcc);
		File outputff = Files.createTempFile("gccrun", ".out").toFile();
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
}
