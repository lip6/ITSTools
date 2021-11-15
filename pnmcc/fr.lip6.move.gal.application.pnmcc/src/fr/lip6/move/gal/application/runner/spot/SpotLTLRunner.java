package fr.lip6.move.gal.application.runner.spot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
import fr.lip6.move.gal.pn2pins.PetriNet2PinsTransformer;
import fr.lip6.move.gal.process.CommandLine;
import fr.lip6.move.gal.process.Runner;
import fr.lip6.move.gal.structural.PropertyType;
import fr.lip6.move.gal.structural.SparsePetriNet;
import fr.lip6.move.gal.structural.expr.Op;

public class SpotLTLRunner extends AbstractRunner implements IRunner {

	private String solverPath;
	private String workFolder;
	private long timeout;
	private boolean isSafe;
	private SparsePetriNet spn;
	private Solver solver;
	private String spotmcpath;

	public SpotLTLRunner(String solverPath, Solver solver, String workFolder, long timeout, boolean isSafe, String spotmcpath) {
		this.solverPath = solverPath;
		this.workFolder = workFolder;
		this.timeout = timeout;
		this.isSafe = isSafe;
		this.solverPath = solverPath;
		this.solver = solver;
		this.spotmcpath = spotmcpath;
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
						g2p.transform(spec, workFolder, false, isSafe);

					} else {
						p2p = new PetriNet2PinsTransformer();
						p2p.transform(spn, workFolder, false, true);
						
					}
					try {
						compilePINS(400);
						linkPINS(200);
					} catch (TimeoutException to) {
						throw new RuntimeException("Compilation or link of executable timed out." + to);
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
					checkProperties(g2p, p2p, timeout);
					todo.removeAll(doneProps.keySet());
					if (! todo.isEmpty()) {
						System.out.println("Retrying SpotMC with larger timeout "+(8*timeout)+ " s");
						checkProperties(g2p, p2p, 8 * timeout);
					}
					todo.removeAll(doneProps.keySet());
					if ( todo.isEmpty()) {
						ender.killAll();
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					System.out.println("WARNING : SpotMC runner thread was asked to interrupt. Dying gracefully.");
				} catch (RuntimeException e) {
					System.out.println("WARNING : SpotMC runner thread failed on error :" + e);
					e.printStackTrace();
				}
			}

			public void checkProperties(Gal2PinsTransformerNext g2p, PetriNet2PinsTransformer p2p, long time)
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
					for (fr.lip6.move.gal.structural.Property prop : spn.getProperties()) {
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
		CommandLine SpotMC = new CommandLine();
		SpotMC.setWorkingDir(new File(workFolder));
		SpotMC.addArg(spotmcpath);
		
		SpotMC.addArg("-m");
		
		File old = new File (workFolder+"/gal.so");
		if (old.exists()) {
			File newf = new File (workFolder+"/gal.dve2C");
			if (newf.exists())
				newf.delete();
			// Rename file (or directory)
			boolean success = old.renameTo(newf);

			if (!success) {
				// File was not successfully renamed
				throw new IOException("Could not rename "+old + " to "+newf);
			}
		}
		SpotMC.addArg("./gal.dve2C");

//		SpotMC.addArg("-p");
//		SpotMC.addArg("8");
		
		boolean withPOR = false;
		if (false && isStutterInvariant(pbody)) {
			SpotMC.addArg("-p");
			SpotMC.addArg("--pins-guards");
			//SpotMC.addArg("--no-V");
			withPOR = true;
		}
		// SpotMC.addArg("--when");
		boolean isdeadlock = false;
		boolean isLTL = false;
		if (propertyType == PropertyType.DEADLOCK) {
			SpotMC.addArg("-d");
			isdeadlock = true;
		} else if (propertyType == PropertyType.LTL) {
			SpotMC.addArg("-f");
			SpotMC.addArg("!("+pbody+")");
			SpotMC.addArg("-e");
			
			// SpotMC.addArg("--strategy=renault");
			// SpotMC.addArg("--buchi-type=spotba");

			// SpotMC.addArg("--ltl-semantics");
			// SpotMC.addArg("spin");

			isLTL = true;
		} else { // INVARIANT			
			SpotMC.addArg("-i");
			SpotMC.addArg(pname.replaceAll("-", "") + "==true");
		}
		
		try {
			File outputff = Files.createTempFile("ltsrun", ".out").toFile();
			long time = System.currentTimeMillis();
			System.out.println("Running SpotMC : " + SpotMC);
			int status = Runner.runTool(timeout, SpotMC, outputff, true);
			if (status == 137) {
				System.err.println("SpotMC failed to check property "+ pname + " due to out of memory issue (code 137).");
				return;
			}
			if (status != 0 && status != 1) {
				Files.lines(outputff.toPath()).forEach(l -> System.err.println(l));
				throw new RuntimeException("Unexpected exception when executing SpotMC :" + SpotMC + "\n" + status);				
			}
			System.out.println("SpotMC run took "+ (System.currentTimeMillis() -time) +" ms.");
			System.out.flush();
			boolean result;
			

			
			if (Files.lines(outputff.toPath()).anyMatch(output -> output.contains("Error: tree leafs table full! Change -s/--ratio"))) {
				// this is a real issue : need to bail out, result is not correct
				System.err.println("SpotMC failed to check property "+ pname + " due to out of memory issue.");
				return;
			}
			if (isdeadlock) {
				result = Files.lines(outputff.toPath()).anyMatch(line -> line.contains("Deadlock found") || line.contains("deadlock () found")); 
			} else if (isLTL) {
				// accepting cycle = counter example to
				// formula
				boolean hasViol = Files.lines(outputff.toPath()).anyMatch(output -> output.contains("an accepting run exists"));
				if (hasViol) {
					result = false ;
				} else {
					boolean noViol = Files.lines(outputff.toPath()).anyMatch(output -> output.contains("no accepting run found"));
					if (noViol) {
						result = true;
					} else {
						System.err.println("SpotMC failed to check property "+ pname + ".");
						Files.lines(outputff.toPath()).forEach(l -> System.err.println(l));
						return;
					}
				}
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
			doneProps.put(pname,result,(withPOR ? "PARTIAL_ORDER ":"") + "EXPLICIT SpotMC SAT_SMT");
			System.out.flush();
		} catch (TimeoutException to) {
			System.out.println("WARNING : SpotMC timed out (>"+timeout+" s) on command " + SpotMC);
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
		File cwd = new File(workFolder);
		clgcc.setWorkingDir(new File(workFolder));
		clgcc.addArg("g++");
		clgcc.addArg("-c");
		clgcc.addArg("-I" + BinaryToolsPlugin.getIncludeFolderURI().getPath().toString());
		clgcc.addArg("-I.");
		clgcc.addArg("-std=c++14");
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

	private void linkPINS(int timeLimit) throws IOException, TimeoutException, InterruptedException {
		// link
		long time = System.currentTimeMillis();
		CommandLine clgcc = new CommandLine();
		File cwd = new File(workFolder);
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
