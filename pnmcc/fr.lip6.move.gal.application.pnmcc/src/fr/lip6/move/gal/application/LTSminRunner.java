package fr.lip6.move.gal.application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import fr.lip6.move.gal.Comparison;
import fr.lip6.move.gal.InvariantProp;
import fr.lip6.move.gal.LTLNext;
import fr.lip6.move.gal.LTLProp;
import fr.lip6.move.gal.NeverProp;
import fr.lip6.move.gal.Property;
import fr.lip6.move.gal.ReachableProp;
import fr.lip6.move.gal.gal2pins.Gal2PinsTransformerNext;
import fr.lip6.move.gal.gal2smt.Gal2SMTFrontEnd;
import fr.lip6.move.gal.gal2smt.Solver;
import fr.lip6.move.gal.process.CommandLine;
import fr.lip6.move.gal.process.Runner;

public class LTSminRunner extends AbstractRunner implements IRunner {

	private String ltsminpath;
	private String solverPath;
	private boolean doPOR;
	private boolean onlyGal;
	private String workFolder;
	private Solver solver;
	private long timeout;

	public LTSminRunner(String ltsminpath, String solverPath, Solver solver, boolean doPOR, boolean onlyGal, String workFolder, long timeout) {
		this.ltsminpath = ltsminpath;
		this.solverPath = solverPath;
		this.solver = solver;
		this.doPOR = doPOR;
		this.onlyGal = onlyGal;
		this.workFolder = workFolder;
		this.timeout = timeout;
	}

	private static boolean isStutterInvariant(Property prop) {
		for (TreeIterator<EObject> it = prop.eAllContents(); it.hasNext();) {
			EObject obj = it.next();
			if (obj instanceof LTLNext) {
				return false;
			} else if (obj instanceof Comparison) {
				it.prune();
			}
		}
		return true;
	}

	@Override
	public void solve(Ender ender) {
		runnerThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println("Built C files in : \n" + new File(workFolder + "/"));
					final Gal2PinsTransformerNext g2p = new Gal2PinsTransformerNext();

					final Gal2SMTFrontEnd gsf = new Gal2SMTFrontEnd(solverPath, solver, timeout);
					g2p.setSmtConfig(gsf);
					g2p.initSolver();
					g2p.transform(spec, workFolder, doPOR);

					if (ltsminpath != null) {
						try {
							compilePINS(400);
							linkPINS(200);
						} catch (TimeoutException to) {
							throw new RuntimeException("Compilation or link of executable timed out." + to);
						}

						if (onlyGal) {
							System.out.println("Successfully built gal.so in :" + workFolder);
							System.out.println("It has labels for :" + (spec.getProperties().stream()
									.map(p -> p.getName().replaceAll("-", "")).collect(Collectors.toList())));
							return;
						}
						List<String> todo = spec.getProperties().stream().map(p -> p.getName())
								.collect(Collectors.toList());

						for (Property prop : spec.getProperties()) {
							checkProperty(prop,g2p,timeout);
						}
						
						todo.removeAll(doneProps);
						if (! todo.isEmpty()) {
							System.out.println("Retrying LTSmin with larger timeout "+(8*timeout)+ " s");
							for (Property prop : spec.getProperties()) {
								checkProperty(prop,g2p,8*timeout);
							}
						}
						todo.removeAll(doneProps);
						if ( todo.isEmpty()) {
							ender.killAll();
						}
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



		});
		runnerThread.start();
	}
	
	private void checkProperty(Property prop, Gal2PinsTransformerNext g2p, long timeout) throws IOException, InterruptedException {
		if (doneProps.contains(prop.getName())) {
			return;
		}
		CommandLine ltsmin = new CommandLine();
		ltsmin.setWorkingDir(new File(workFolder));
		ltsmin.addArg(ltsminpath + "/bin/pins2lts-mc");							
		ltsmin.addArg("./gal.so");

		ltsmin.addArg("--threads=8");
		if (doPOR && isStutterInvariant(prop)) {
			ltsmin.addArg("-p");
			ltsmin.addArg("--pins-guards");
		}
		ltsmin.addArg("--when");
		boolean isdeadlock = false;
		boolean isLTL = false;
		if (prop.getName().contains("Deadlock")|| prop.getName().contains("GlobalProperties")) {
			ltsmin.addArg("-d");
			isdeadlock = true;
		} else if (prop.getBody() instanceof LTLProp) {
			ltsmin.addArg("--ltl");
			ltsmin.addArg(g2p.printLTLProperty((LTLProp) prop.getBody()));
			// ltsmin.addArg("--strategy=renault");
			ltsmin.addArg("--buchi-type=spotba");

			// ltsmin.addArg("--ltl-semantics");
			// ltsmin.addArg("spin");

			isLTL = true;
		} else {
			ltsmin.addArg("-i");
			ltsmin.addArg(prop.getName().replaceAll("-", "") + "==true");
		}
		try {
			File outputff = Files.createTempFile(ltsmin.getWorkingDir().toPath(), "ltsrun", ".out").toFile();
			long time = System.currentTimeMillis();
			System.out.println("Running LTSmin : " + ltsmin);
			int status = Runner.runTool(timeout, ltsmin, outputff, true);
			if (status == 137) {
				System.err.println("LTSmin failed to check property "+ prop.getName() + " due to out of memory issue (code 137).");
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
				System.err.println("LTSmin failed to check property "+ prop.getName() + " due to out of memory issue.");
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
					if (prop.getBody() instanceof ReachableProp) {
						result = true;
					} else if (prop.getBody() instanceof NeverProp) {
						result = false;
					} else if (prop.getBody() instanceof InvariantProp) {
						result = false;
					} else {
						throw new RuntimeException("Unexpected property type " + prop);
					}
				} else {
					System.out.println("Invariant validated");
					if (prop.getBody() instanceof ReachableProp) {
						result = false;
					} else if (prop.getBody() instanceof NeverProp) {
						result = true;
					} else if (prop.getBody() instanceof InvariantProp) {
						result = true;
					} else {
						throw new RuntimeException("Unexpected property type " + prop);
					}
				}
			}
			String ress = (result + "").toUpperCase();
			System.out.println("FORMULA " + prop.getName() + " " + ress
					+ " TECHNIQUES PARTIAL_ORDER EXPLICIT LTSMIN SAT_SMT");
			doneProps.add(prop.getName());
			System.out.flush();
		} catch (TimeoutException to) {
			System.out.println("WARNING : LTSmin timed out (>"+timeout+" s) on command " + ltsmin);
			return ;
		}
	}
	
	private void compilePINS(long timeout) throws IOException, TimeoutException, InterruptedException {
		// compile
		long time = System.currentTimeMillis();
		CommandLine clgcc = new CommandLine();
		clgcc.setWorkingDir(new File(workFolder));
		clgcc.addArg("gcc");
		clgcc.addArg("-c");
		clgcc.addArg("-I" + ltsminpath + "/include");
		clgcc.addArg("-I.");
		clgcc.addArg("-std=c99");
		clgcc.addArg("-fPIC");
		clgcc.addArg("-O2");
		clgcc.addArg("model.c");

		System.out.println("Running compilation step : " + clgcc);
		int status = Runner.runTool(timeout, clgcc);
		if (status != 0) {
			throw new RuntimeException("Could not compile executable ." + clgcc);
		}
		System.out.println("Compilation finished in "+ (System.currentTimeMillis() -time) +" ms.");
		System.out.flush();
	}

	private void linkPINS(int timeLimit) throws IOException, TimeoutException, InterruptedException {
		// link
		long time = System.currentTimeMillis();
		CommandLine clgcc = new CommandLine();
		clgcc.setWorkingDir(new File(workFolder));
		clgcc.addArg("gcc");
		clgcc.addArg("-shared");
		clgcc.addArg("-o");
		clgcc.addArg("gal.so");
		clgcc.addArg("model.o");
		System.out.println("Running link step : " + clgcc);
		int status = Runner.runTool(timeout, clgcc);
		if (status != 0) {
			throw new RuntimeException("Could not link executable ." + clgcc);
		}
		System.out.println("Link finished in "+ (System.currentTimeMillis() -time) +" ms.");
		System.out.flush();
	}
}
